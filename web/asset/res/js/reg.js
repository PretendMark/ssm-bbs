$(function() {
    layui.use(['form'], function() {
        var form = layui.form;
        form.on('submit(register)', function(data) {
            //ajax
            var verifyCode = $("#L_vercode").val();
            var e = $("#L_email").val();
            var index = layer.load(2, {
                shade: [0.5, 'gray'],
                time: 20 * 1000,
                content: '处理中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '100px'
                    });
                }
            });
            $.ajax({
                url: ProPath.projectPath + "/verifyCode/regVerify",
                type: "post",
                data: "v=" + verifyCode + "&d=" + new Date() + "&e=" + e,
                dataType: "json",
                success: function(result) {
                    console.log(result);
                    if (parseInt(result.state) == 300) {
                        layer.close(index);
                        //验证正确
                        alertMsg("验证成功!", 1);
                        //获得token，提交
                        $("input[name='userToken']").val(result.token);
                        var formdata = $("#uform").serialize();
                        console.log(formdata);
                        $("#uform").submit();
                    } else {
                        layer.close(index);
                        alertMsg(result.message, 2);
                    }
                }
            });

            return false;
        });

    });

    //是否开启了邮箱验证？
    window.onload = function() {
        //当查出是关闭的
        if ($.cookie("isEnabled") == null) {
            $.get({
                url: ProPath.projectPath + "/verifyCode/emailverify",
                dataType: "json",
                success: function(result) {
                    if (result.emailverify == "false") {
                        hiddenBtn();
                        //下次刷新标识
                        $.cookie("isEnabled", false);
                    }
                },
                error: function() {
                    again();
                }
            });
        } else {
            hiddenBtn();
        }
    }

    $("#sendVerify").on("click", function() {
        var email = $("input[name='userEmail']").val();
        if (isEmail(email) && hasDefaultVal()) {
            //加载中...最大等待时长20秒
            var index = layer.load(2, {
                shade: [0.5, 'gray'],
                time: 20 * 1000,
                content: '发送中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '100px'
                    });
                }
            });
            $.ajax({
                url: ProPath.projectPath + "/verifyCode/getVerifyCode",
                type: "get",
                dataType: "json",
                data: {
                    "email": email
                },
                contentType: 'application/json;charset=utf-8',
                success: function(result) {
                    console.log(result);
                    switch (JSON.stringify(result.state)) {
                        //如果验证码冷却时间大于0,状态等于202，说明还有冷却时间没走完
                        case "202":
                            layer.msg("验证码冷却时间还剩" + result.time + "秒");
                            break;
                        case "203":
                            layer.msg("今天发送验证码次数已达到上限，明天再来吧");
                            break;
                        case "200":
                            beginCountDown(result);
                            break;
                        default:
                            layer.msg(result.message);
                            break;
                    }
                    layer.close(index);
                },
                error: function() {
                    again();
                }
            });
        }
    });

});

/**
 * 前端邮箱验证码验证
 */

function hasDefaultVal() {
    var nickName = $("#L_username").val();
    var password = $("#L_pass").val();
    var confirmPassword = $("#L_repass").val();
    if (isEmpty(nickName) || nickName.length > UserLengthLimit.NicknameMaxLength) {
        layer.msg(UserCheck.IncorrectNickname);
        return false;
    }
    if (isEmpty(password) || isEmpty(confirmPassword) || password.length < UserLengthLimit.PwMinLength || password.length > UserLengthLimit.PwMaxLength) {
        layer.msg(UserCheck.IncorrectPassword);
        return false;
    }
    if (password != confirmPassword) {
        layer.msg(UserCheck.PwInconformity);
        return false;
    }
    return true;
}



//验证码倒计时


function beginCountDown(result) {
    var btnObjId = $("#sendVerify");
    btnObjId.addClass("layui-btn-disabled");
    var time = parseInt(result.time);
    var timer = setInterval(function() {
        time--;
        $(btnObjId).text('(' + time + ')秒后重新获取');
        if (time <= 0) {
            $(btnObjId).removeClass('layui-btn-disabled').text('发送验证码');
            clearInterval(timer);
        }
    }, 1000);
}

function hiddenBtn() {
    /**
     * 隐藏发送邮件验证码按钮
     */
    $("#sendVerify").css("display", "none");
    $("#verifyCode").text("填写验证码");
    /**
     * 显示普通验证码
     */
    showImgVerifyCode();
}

function showImgVerifyCode() {
    $("#kapatchaCode").css("display", "block");
}


function again() {
    layer.msg("Error! Please try Again");
}

function isInvalidCoolingTimeCookie() {
    if ($.cookie("ct") != undefined && !isNaN($.cookie("ct") && $.cookie("ct") > 0)) {

    }
}
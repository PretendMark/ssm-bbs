ProPath = {
    projectPath: "/ssm-bbs",
    jsPath: "/asset/res/js",
    cssPath: "/asset/res/css",
    imgPath: "/asset/res/images",
    pictureUploadPath: "/user/uploadUserPicture"
};
UserLengthLimit = {
    NicknameMaxLength: 10,
    PwMinLength: 6,
    PwMaxLength: 16,
    AuthcodeMinLength: 4,
    AuthcodeMaxLength: 5,
}
UserCheck = {
    IncorrectEmail: "邮箱格式不正确!",
    IncorrectNickname: "昵称不能包含空并且长度不能大于" + UserLengthLimit.NicknameMaxLength,
    IncorrectPassword: "密码不能包含空并且长度不能低于" + UserLengthLimit.PwMinLength + "大于" + UserLengthLimit.PwMaxLength,
    PwInconformity: "两次密码不一致!",
    AuthcodeLengthError: "验证码长度错误"
}
$(function () {
    layui.use(['form', 'layer',"element"], function () {
        var element = layui.element;
        var form = layui.form;
        var layer = layui.layer;
        //前端普通验证
        form.verify({
            um: function (value, item) { //value：表单的值、item：表单的DOM对象
                if (!isEmail(value)) {
                    return UserCheck.IncorrectEmail;
                }
            },
            un: function (value, item) {
                if (isEmpty(value) || value.length > UserLengthLimit.NicknameMaxLength) {
                    return UserCheck.IncorrectNickname;
                }
            },
            pw: function (value, item) {
                if (isEmpty(value) || value.length < UserLengthLimit.PwMinLength || value.length > UserLengthLimit.PwMaxLength) {
                    return UserCheck.IncorrectPassword;
                }
            },
            repw: function (value, item) {
                if (value != $("#L_pass").val()) {
                    return UserCheck.PwInconformity;
                }
            },
            vc: function (value, item) {
                if (value.length < UserLengthLimit.AuthcodeMinLength || value.length > UserLengthLimit.AuthcodeMaxLength) {
                    return UserCheck.AuthcodeLengthError;
                }
            }
        });
    });
});

/*
*
* 截取项目根路径 协议://地址:端口/项目
* @returns {string}
*/

function getProjectUrl() {
    var content = location.href;
    for (var index = 0; index < 4; index++) {
        content = content.substring(content.indexOf("/") + 1, content.length);
    }
    return location.href.replace(content, "");
}


//改变验证码
function changeVerifyCode(obj) {
    var path = location.href;
    path.substring(0, path.inde)
    obj.src = getProjectUrl() + "/kaptcha.jpg?d=" + new Date();
}

function isEmpty(val) {
    if (val == null || containsSpace(val)) {
        return true;
    } else {
        return false;
    }
}

function isEmail(val) {
    var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
    if (reg.test(val)) {
        return true;
    } else {
        layer.msg(UserCheck.IncorrectEmail);
        return false;
    }
}

function containsSpace(val) {
    if (val == "" || val.indexOf(" ") != -1) {
        return true;
    }
    var reg = new RegExp("^[ ]+$");
    return reg.test(val);
}

//有表情
function alertMsg(msg, icon) {
    layui.use(['layer'], function () {
        var layer = layui.layer;
        var index = layer.alert(msg, {offset: 'auto', icon: icon});
        layer.style(index, {
            color: '#777'
        });
    });
}

function show_msg(msg) {
    layui.use(['layer'], function () {
        var layer = layui.layer;
        layer.msg(msg, {offset: "auto"});
    });

}

function syncAjax(url, method, data) {
    var result;
    $.ajax({
        async : false,
        url: url,
        data: data,
        type: method,
        dataType: "text",
        success: function (res) {
            if (res != null) {
                result = res;
            }
        },
        error: function () {
            show_msg("查询失败!");
        }
    });
    return JSON.parse(result);
}

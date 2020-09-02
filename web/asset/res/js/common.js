$(function(){
    layui.use(['form','layer'], function() {
        var form = layui.form;
        var layer = layui.layer;
        //前端普通验证
        form.verify({
            um: function (value, item) { //value：表单的值、item：表单的DOM对象
                if (!isEmail(value)) {
                    return "邮箱格式不正确!"
                }
            },
            un: function (value, item) {
                if (isEmpty(value) || value.length > 10) {
                    return "昵称不能包含空并且长度不能大于10";
                }
            },
            pw: function (value, item) {
                if (isEmpty(value) || value.length < 6 || value.length > 16) {
                    return "密码不能包含空并且长度不能低于6大于16";
                }
            },
            repw: function (value, item) {
                if (value != $("#L_pass").val()) {
                    return "两次密码不一致!";
                }
            },
            vc: function (value, item) {
                if (value.length < 4 || value.length > 5) {
                    return "验证码长度错误!";
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
        layer.msg("邮箱格式不正确！");
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
function alertMsg(msg,icon) {
    var index = layer.alert(msg, {  offset: 'auto' ,icon:icon});
    layer.style(index, {
        color: '#777'
    });
}
function show_msg(msg){
    layer.msg(msg,{offset:"auto"});
}

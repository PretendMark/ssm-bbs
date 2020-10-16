$(function(){
    layui.use(['form'], function() {
        var form = layui.form;

        form.on('submit(login)', function(data) {
            //ajax
            var verifyCode = $("#UV").val();
            var e = $("#UEmail").val();
            var index = layer.load(2, {
                shade: [0.5, 'gray'],
                time: 20 * 1000,
                content: '验证中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '100px'
                    });
                }
            });
            $.ajax({
                url: ProPath.projectPath + "/verifyCode/loginVerify",
                type: "post",
                data: "v=" + verifyCode + "&d=" + new Date() + "&e=" + e,
                dataType: "json",
                success: function(result) {
                    console.log(result);
                    if (parseInt(result.state) == 302) {
                        layer.close(index);
                        //验证正确
                        alertMsg(result.message, 1);
                        //RSA加密密码
                        var encrypt = new JSEncrypt();
                        encrypt.setPublicKey(result.token);
                        var encrypted = encrypt.encrypt($("#UPass").val());

                        $("#UPass").val(encrypted);
                        //$("#loginForm").serialize()
                        $("#loginForm").submit();
                    } else {
                        layer.close(index);
                        alertMsg(result.message, 2);
                    }
                }
            });

            return false;
        });

    });
});
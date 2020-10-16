$(function () {
    window.onload = initUserCity();
    /*基本信息设置*/
    //提交
    layui.use(['form'], function () {
        var form = layui.form;
        form.on('submit(updateinfo)', function (data) {
            layer.confirm("您确定更改您当前的信息吗？", {
                    cancel: function (index, layero) {
                        //cacel do something
                    },
                    btn1: ['<i class="layui-icon">&#xe605;</i>&nbsp;确定'],
                    btn1: ['<i class="layui-icon">&#xe605;</i>&nbsp;取消'],
                    title: "温馨提示"
                },
                function () {
                    layer.closeAll('dialog');
                    var un = $("input[name='username']").val();
                    var provincial = $("#L_province").val();
                    var city = $("#userCity").val();
                    var sign = $("#L_sign").val();
                    console.log("nickname：" + un);
                    console.log("provincial：" + provincial);
                    console.log("city：" + city);
                    console.log("sign：" + sign);
                });
            return false;
        });
        form.on('select(provincial)', function (data) {
            addOption();
            renderForm();
        });
    });
    //基本设置-昵称输入框鼠标移开判断
    $(document).blur(function (e) {
        var newNickname = $("#L_username").val();
        $("#isExistNickname").empty();
        if (this.defaultValue == newNickname) {
            return;
        }
        //判断是否有相同的昵称
        var res = syncAjax(ProPath.projectPath + "/user/checkNickname", "POST", "newNickname=" + newNickname);
        console.log(res.message);
        $("#isExistNickname").append(res.message);
        if (res.state == 306) {
            $("#isExistNickname").css("color", "green");
        } else {
            $("#isExistNickname").css("color", "red");
        }
    });
});

function initUserCity() {
    console.log();
    //插入省
    var select1 = document.getElementById("L_province");
    for (var i in city) {
        select1.add(new Option(i, i), null);
    }
    //选中用户所在省市
    var uCity = $("#uCity").val();
    var uProvince = $("#uProvince").val();
    $("#L_province option[value='']").removeAttr("selected");               //移除省默认选项的选中状态
    $("#userCity option[value='']").removeAttr("selected");               //移除市默认选项的选中状态
    $("#L_province option[value='" + uProvince + "']").attr("selected", "selected"); //选中用户所在省
    var userCity = document.getElementById("userCity");
    //插入用户所在省的所有市
    for (var i in city[uProvince]) {
        userCity.add(new Option(city[uProvince][i], city[uProvince][i]), null);
    }
    $("#userCity option[value='" + uCity + "']").attr("selected", "selected"); //选中用户所在市
    renderForm();
}

//插入市
function addOption() {
    var userCity = document.getElementById("userCity");
    var province = document.getElementById("L_province").value;
    console.log(province);
    userCity.length = 0; //每次都先清空一下市级菜单
    if (province != '请选择省份') {
        for (var i in city[province]) {
            userCity.add(new Option(city[province][i], city[province][i]), null);
        }
    } else {
        userCity.length = 0;
        userCity.add(new Option("请选择城市", "请选择城市"), null);
    }
}

//重新渲染select
function renderForm() {
    layui.use('form', function () {
        var form = layui.form;//高版本把括号去掉，有的低版本，需要加form()
        form.render();
    });
}
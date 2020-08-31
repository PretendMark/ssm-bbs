<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>登入</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content="fly,layui,前端社区">
    <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
    <link rel="stylesheet" href="${absolutePath}/asset/res/layui/css/layui.css">
    <link rel="stylesheet" href="${absolutePath}/asset/res/css/global.css">
</head>
<body>
<div class="fly-header layui-bg-black">
    <div class="layui-container">
        <a class="fly-logo" href="/">
            <img src="${absolutePath}/asset/res/images/logo.png" alt="layui">
        </a>
        <ul class="layui-nav fly-nav layui-hide-xs">
            <li class="layui-nav-item layui-this">
                <a href="${absolutePath}/index.jsp"><i class="iconfont icon-jiaoliu"></i>首页</a>
            </li>
            <li class="layui-nav-item">
                <a href="${absolutePath}/asset/case/case.jsp"><i class="iconfont icon-iconmingxinganli"></i>案例</a>
            </li>
            <li class="layui-nav-item">
                <a href="http://www.layui.com/" target="_blank"><i class="iconfont icon-ui"></i>框架</a>
            </li>
        </ul>

        <ul class="layui-nav fly-nav-user">
            <!-- 未登入的状态 -->
            <li class="layui-nav-item">
                <a class="iconfont icon-touxiang layui-hide-xs" href="${absolutePath}/asset/user/public/login.jsp"></a>
            </li>
            <li class="layui-nav-item">
                <a href="${absolutePath}/asset/user/public/login.jsp">登入</a>
            </li>
            <li class="layui-nav-item">
                <a href="${absolutePath}/asset/user/public/reg.jsp">注册</a>
            </li>
            <li class="layui-nav-item layui-hide-xs">
                <a href="/app/qq/" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})" title="QQ登入"
                   class="iconfont icon-qq"></a>
            </li>
            <li class="layui-nav-item layui-hide-xs">
                <a href="/app/weibo/" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})" title="微博登入"
                   class="iconfont icon-weibo"></a>
            </li>
        </ul>
    </div>
</div>

<div class="layui-container fly-marginTop">
    <div class="fly-panel fly-panel-user" pad20>
        <div class="layui-tab layui-tab-brief" lay-filter="user">
            <ul class="layui-tab-title">
                <li class="layui-this">登入</li>
                <li><a href="reg.jsp">注册</a></li>
            </ul>
            <div class="layui-tab-content" id="LAY_ucm" style="padding: 20px 0;">
                <div class="layui-tab-item layui-show">
                    <div class="layui-form-pane">
                        <form id="loginForm" class="layui-form" method="post" action="${absolutePath}/ulogin/login">

                            <div class="layui-form-item">
                                <label for="UEmail" class="layui-form-label">邮箱</label>
                                <div class="layui-input-inline">
                                    <input type="text" id="UEmail" name="userEmail" lay-verify="required|um"
                                           autocomplete="off" class="layui-input">
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <label for="UPass" class="layui-form-label">密码</label>
                                <div class="layui-input-inline">
                                    <input type="password" id="UPass" name="userPassword" lay-verify="required|pw"
                                           autocomplete="off" class="layui-input">
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <label for="UV" class="layui-form-label">验证码</label>
                                <div class="layui-input-inline">
                                    <input type="text" id="UV" name="userVerifyCode" lay-verify="required|vc"
                                           placeholder="请输入验证码" autocomplete="off" class="layui-input">
                                </div>
                                <img id="kapatchaCode" onclick="changeVerifyCode(this)" style="width:100px;height:38px;"
                                     src="${pageContext.request.contextPath}/kaptcha.jpg" alt="验证码"/>
                                <div class="layui-form-mid">

                                </div>
                            </div>
                            <div class="layui-form-item">
                                <button class="layui-btn" lay-submit lay-filter="login">立即登录</button>
                                <span style="padding-left:20px;">
                  <a href="../forget.html">忘记密码？</a>
                </span>
                            </div>
                            <div class="layui-form-item fly-form-app">
                                <span>或者使用社交账号登入</span>
                                <a href="" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})"
                                   class="iconfont icon-qq" title="QQ登入"></a>
                                <a href="" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})"
                                   class="iconfont icon-weibo" title="微博登入"></a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="fly-footer">
    <p><a href="http://fly.layui.com/" target="_blank">Fly社区</a> 2017 &copy; <a href="http://www.layui.com/"
                                                                                target="_blank">layui.com 出品</a></p>
    <p>
        <a href="http://fly.layui.com/jie/3147/" target="_blank">付费计划</a>
        <a href="http://www.layui.com/template/fly/" target="_blank">获取Fly社区模版</a>
        <a href="http://fly.layui.com/jie/2461/" target="_blank">微信公众号</a>
    </p>
</div>

<script src="${absolutePath}/asset/res/jquery/jquery-3.2.1.js" type="text/javascript"></script>
<script src="${absolutePath}/asset/res/layui/layui.js"></script>
<script src="${absolutePath}/asset/res/js/common.js"></script>
<script src="${absolutePath}/asset/res/js/jsencrypt.js"></script>
<script src="${absolutePath}/asset/res/js/lg.js"></script>
<script type="text/javascript">

    //登录失败 重定显示session错误信息
    window.onload = function () {
        if(${loginError!=null}){
            alertMsg("${loginError}",7);
            //显示错误后立马请求服务器清空这个Session，避免重复刷新信息
            $.get({
                url:project + "/user/removeLoginError"
            });
        }
    }

</script>
</body>
</html>
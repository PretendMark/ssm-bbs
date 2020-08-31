<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>注册</title>
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
        <a href="${absolutePath}/index.jsp"><i class="iconfont icon-jiaoliu"></i>交流</a>
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
        <a class="iconfont icon-touxiang layui-hide-xs" href="${absolutePath}/asset/user/login.jsp"></a>
      </li>
      <li class="layui-nav-item">
        <a href="${absolutePath}/asset/user/login.jsp">登入</a>
      </li>
      <li class="layui-nav-item">
        <a href="${absolutePath}/asset/user/reg.html">注册</a>
      </li>
      <li class="layui-nav-item layui-hide-xs">
        <a href="/app/qq/" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})" title="QQ登入" class="iconfont icon-qq"></a>
      </li>
      <li class="layui-nav-item layui-hide-xs">
        <a href="/app/weibo/" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})" title="微博登入" class="iconfont icon-weibo"></a>
      </li>
    </ul>
  </div>
</div>

<div class="layui-container fly-marginTop">
  <div class="fly-panel fly-panel-user" pad20>
    <div class="layui-tab layui-tab-brief" lay-filter="user">
      <ul class="layui-tab-title">
        <li><a href="login.jsp">登入</a></li>
        <li class="layui-this">注册</li>
      </ul>
      <div class="layui-form layui-tab-content" id="LAY_ucm" style="padding: 20px 0;">
        <div class="layui-tab-item layui-show">
          <div class="layui-form layui-form-pane">
            <form method="post"  action="${absolutePath}/user/register">
              <div class="layui-form-item">
                <label for="L_email" class="layui-form-label">邮箱</label>
                <div class="layui-input-inline">
                  <input type="text" id="L_email" name="userEmail"  lay-verify="required|um"  autocomplete="off" class="layui-input"><b style="color:red"><f:errors path="${errorlist.userEmail}"></f:errors></b>
                </div>
                <div class="layui-form-mid layui-word-aux">将会成为您唯一的登入名</div>
              </div>
              <div class="layui-form-item">
                <label for="L_username" class="layui-form-label">昵称</label>
                <div class="layui-input-inline">
                  <input type="text" id="L_username" name="userName"   lay-verify="required|un" autocomplete="off" class="layui-input"><b style="color:red"><f:errors path="${errorlist.userName}"></f:errors></b>
                </div>
              </div>
              <div class="layui-form-item">
                <label for="L_pass" class="layui-form-label">密码</label>
                <div class="layui-input-inline">
                  <input type="password" id="L_pass"   lay-verify="required|pw"  autocomplete="off" class="layui-input"><b style="color:red"><f:errors path="${errorlist.userPassword}"></f:errors></b>
                </div>
                <div class="layui-form-mid layui-word-aux">6到16个字符</div>
              </div>
              <div class="layui-form-item">
                <label for="L_repass" class="layui-form-label">确认密码</label>
                <div class="layui-input-inline">
                  <input type="password" id="L_repass"  name="userPasswordSalt" required lay-verify="required|pw|repw"  autocomplete="off" class="layui-input"><b style="color:red"><f:errors path="${errorlist.userPassword}"></f:errors></b>
                </div>
              </div>
              <div class="layui-form-item">
                <label for="L_vercode" class="layui-form-label" id="verifyCode">邮件验证码</label>
                <div class="layui-input-inline">
                  <input   type="text" id="L_vercode" name="userVerifyCode" required lay-verify="required" placeholder="输入您的验证码" autocomplete="off" class="layui-input" ><b style="color:red"><f:errors path="${errorlist.userVerifyCode}"></f:errors></b>
                </div>
                <img id="kapatchaCode"onclick="changeVerifyCode(this)" style="width:100px;height:38px;display: none"  src="${pageContext.request.contextPath}/kaptcha.jpg" alt="验证码"/>
                <button type="button" class="layui-btn layui-btn-primary"  autocomplete="off" id="sendVerify">发送验证码</button>
                <div class="layui-form-mid">

                </div>
              </div>
              <div class="layui-form-item">
                <button class="layui-btn" lay-filter="*" lay-submit>立即注册</button>
              </div>
              <div class="layui-form-item fly-form-app">
                <span>或者直接使用社交账号快捷注册</span>
                <a href="" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-qq" title="QQ登入"></a>
                <a href="" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-weibo" title="微博登入"></a>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>

</div>

<div class="fly-footer">
  <p><a href="http://fly.layui.com/" target="_blank">Fly社区</a> 2017 &copy; <a href="http://www.layui.com/" target="_blank">layui.com 出品</a></p>
  <p>
    <a href="http://fly.layui.com/jie/3147/" target="_blank">付费计划</a>
    <a href="http://www.layui.com/template/fly/" target="_blank">获取Fly社区模版</a>
    <a href="http://fly.layui.com/jie/2461/" target="_blank">微信公众号</a>
  </p>
</div>

<script src="${absolutePath}/asset/res/jquery/jquery-3.2.1.js" type="text/javascript"></script>
<script src="${absolutePath}/asset/res/jquery/jquery.cookie.js" type="text/javascript"></script>
<script src="${absolutePath}/asset/res/layui/layui.js"></script>
<script src="${absolutePath}/asset/res/js/reg.js" type="text/javascript"></script>

<script>
    layui.cache.page = 'user';
    layui.cache.user = {
        username: '游客'
        ,uid: -1
        ,avatar: '${absolutePath}/asset/res/images/avatar/00.jpg'
        ,experience: 83
        ,sex: '男'
    };
    layui.config({
        version: "3.0.0"
        ,base: '${absolutePath}/asset/res/mods/'
    }).extend({
        fly: 'index'
    }).use('fly');
</script>

</body>
</html>
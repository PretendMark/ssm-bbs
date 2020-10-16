<%--
  Created by IntelliJ IDEA.
  User: 16500
  Date: 2020/8/23
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${absolutePath}/asset/res/layui/css/layui.css">
    <link rel="stylesheet" href="${absolutePath}/asset/res/css/global.css">
</head>
<body>
<div id="layfilter" lay-filter="layfilter"></div>
<form method="post"  class="layui-form" action="/ssm-bbs/user/register">
    <input name="" value="">
    <button class="layui-btn"></button>
</form>

<table class="layui-hide" id="demo" lay-filter="test"></table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>



<script src="https://cdn.bootcss.com/jsencrypt/3.0.0-beta.1/jsencrypt.js"></script>
<script src="${absolutePath}/asset/res/jquery/jquery-3.2.1.js" type="text/javascript"></script>
<script src="${absolutePath}/asset/res/jquery/jquery.cookie.js" type="text/javascript"></script>
<script src="${absolutePath}/asset/res/layui/layui.all.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script>
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLHMHCyaQhy6xtJtiQOy3erZdc/jML8NTWYxqWoetRWvOysTBpwPNbF5vBH7TgKtP2sM4eVKwEtBPxvyJqYj5KLRLuSwZD1hIhc/6oVFinZ5NUIIqxuwxUClckbvb4XkHZUst2tgWSTsZvfk4QezBgpEDkdbSxleYYUkWQFTJWJQIDAQAB");
    var encrypted = encrypt.encrypt("yulinfeng123");
    console.log('加密后数据:%o', encrypted);
</script>
</body>
</html>

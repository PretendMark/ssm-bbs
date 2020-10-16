<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>帐号设置</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content="fly,layui,前端社区">
    <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
    <link rel="stylesheet" href="${absolutePath}/asset/res/layui/css/layui.css">
    <link rel="stylesheet" href="${absolutePath}/asset/res/css/global.css">
</head>
<body>

<div class="fly-header layui-bg-black">
    <div class="layui-container">
        <a class="fly-logo" href="${absolutePath}/index.jsp">
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
            <!-- 登入后的状态 -->
            <li class="layui-nav-item">
                <a class="fly-nav-avatar" href="javascript:;">
                    <cite class="layui-hide-xs">${userInfo.userName}</cite>
                    <i class="layui-badge fly-badge-vip layui-hide-xs">${userInfo.roleChineseName}</i>
                    <img src="${userInfo.userPicture}">
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="${absolutePath}/asset/user/private/set.jsp"><i class="layui-icon">&#xe620;</i>基本设置</a>
                    </dd>
                    <dd><a href="${absolutePath}/asset/user/private/message.jspsage.jsp"><i
                            class="iconfont icon-tongzhi" style="top: 4px;"></i>我的消息</a></dd>
                    <dd><a href="${absolutePath}/asset/user/private/home.jsp"><i class="layui-icon"
                                                                                 style="margin-left: 2px; font-size: 22px;">&#xe68e;</i>我的主页</a>
                    </dd>
                    <hr style="margin: 5px 0;">
                    <dd><a href="${absolutePath}/ulogin/logout" style="text-align: center;">退出</a></dd>
                </dl>
            </li>
        </ul>
    </div>
</div>

<div class="layui-container fly-marginTop fly-user-main">
    <ul class="layui-nav layui-nav-tree layui-inline" lay-filter="user">
        <li class="layui-nav-item">
            <a href="home.jsp">
                <i class="layui-icon">&#xe609;</i>
                我的主页
            </a>
        </li>
        <li class="layui-nav-item">
            <a href="index.jsp">
                <i class="layui-icon">&#xe612;</i>
                用户中心
            </a>
        </li>
        <li class="layui-nav-item layui-this">
            <a href="set.jsp">
                <i class="layui-icon">&#xe620;</i>
                基本设置
            </a>
        </li>
        <li class="layui-nav-item">
            <a href="message.jsp">
                <i class="layui-icon">&#xe611;</i>
                我的消息
            </a>
        </li>
    </ul>

    <div class="site-tree-mobile layui-hide">
        <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>

    <div class="site-tree-mobile layui-hide">
        <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>


    <div class="fly-panel fly-panel-user" pad20>
        <div class="layui-tab layui-tab-brief" lay-filter="user">
            <ul class="layui-tab-title" id="LAY_mine">
                <li class="layui-this" lay-id="info">我的资料</li>
                <li lay-id="avatar">头像</li>
                <li lay-id="pass">密码</li>
                <li lay-id="bind">帐号绑定</li>
            </ul>
            <div class="layui-tab-content" style="padding: 20px 0;">
                <!-- 第一个选项tab我的资料 -->
                <div class="layui-form layui-form-pane layui-tab-item layui-show">
                    <form id="userBasicsInfoForm" method="post" action="${absolutePath}/user/updateinfo">
                        <div class="layui-form-item">
                          <%--  <label for="L_email" class="layui-form-label">邮箱</label>
                            <div class="layui-input-inline">
                                <input value="${userInfo.userEmail}" type="text" id="L_email" name="email" required
                                       lay-verify="email" autocomplete="off" value="" class="layui-input">
                            </div>--%>
                            <div class="layui-form-mid layui-word-aux">如果您在邮箱已激活的情况下，变更了邮箱，需<a href="../activate.html"
                                                                                               style="font-size: 12px; color: #4f99cf;">重新验证邮箱</a>。
                            </div>
                        </div>
                        <span id="isExistNickname"></span>
                        <div class="layui-form-item">
                            <label for="L_username" class="layui-form-label">昵称</label>
                            <div class="layui-input-inline">
                                <input value="${userInfo.userName}" type="text" id="L_username" name="username" required
                                       lay-verify="required" autocomplete="off" value="" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <div class="layui-input-inline">
                                    <c:if test="${userInfo.userGender=='女'}">
                                        <input type="radio" name="sex" value="0" title="男">
                                        <input type="radio" name="sex" value="1" checked title="女">
                                    </c:if>
                                    <c:if test="${userInfo.userGender=='男'}">
                                        <input type="radio" name="sex" value="0" checked title="男">
                                        <input type="radio" name="sex" value="1" title="女">
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <!-- 初始化用户所在市 -->
                            <input id="uCity" type="hidden" value="${userInfo.city}">
                            <!-- 初始化用户所在省 -->
                            <input id="uProvince" type="hidden" value="${userInfo.provincial}">

                            <label for="L_province" class="layui-form-label" style="border-right: 0px;">城市</label>
                            <%--省--%>
                            <div class="layui-inline" id="provincialDiv">
                                <select name="provincial" id="L_province"  autocomplete="off" lay-filter="provincial" lay-search >
                                </select>
                            </div>
                            <%--市--%>
                            <div class="layui-inline" id="cityDiv">
                                <select name="city" id="userCity" lay-search autocomplete="off">
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text">
                            <label for="L_sign" class="layui-form-label">签名</label>
                            <div class="layui-input-block">
                                <textarea placeholder="随便写些什么刷下存在感" id="L_sign" name="sign" autocomplete="off"
                                          class="layui-textarea" style="height: 80px;">${userInfo.userSign}</textarea>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <button class="layui-btn"  lay-filter="updateinfo" lay-submit >确认修改</button>
                        </div>
                    </form>
                </div>
                <%--第二个选项tab--%>
                <div class="layui-form layui-form-pane layui-tab-item">
                    <div class="layui-form-item">
                        <div class="avatar-add">
                            <p>建议尺寸168*168，支持jpg、png、gif，最大不能超过50KB</p>
                            <button type="button" class="layui-btn upload-img">
                                <i class="layui-icon">&#xe67c;</i>上传头像
                            </button>
                            <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg">
                            <span class="loading"></span>
                        </div>
                    </div>
                </div>
                <%--第三个选项tab--%>
                <div class="layui-form layui-form-pane layui-tab-item">
                    <form action="${absolutePath}/asset/user/repass" method="post">
                        <div class="layui-form-item">
                            <label for="L_nowpass" class="layui-form-label">当前密码</label>
                            <div class="layui-input-inline">
                                <input type="password" id="L_nowpass" name="nowpass" required lay-verify="required"
                                       autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label for="L_pass" class="layui-form-label">新密码</label>
                            <div class="layui-input-inline">
                                <input type="password" id="L_pass" name="pass" required lay-verify="required"
                                       autocomplete="off" class="layui-input">
                            </div>
                            <div class="layui-form-mid layui-word-aux">6到16个字符</div>
                        </div>
                        <div class="layui-form-item">
                            <label for="L_repass" class="layui-form-label">确认密码</label>
                            <div class="layui-input-inline">
                                <input type="password" id="L_repass" name="repass" required lay-verify="required"
                                       autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <button class="layui-btn" key="set-mine" lay-filter="*" lay-submit>确认修改</button>
                        </div>
                    </form>
                </div>
                <%--第四个选项tab--%>
                <div class="layui-form layui-form-pane layui-tab-item">
                    <ul class="app-bind">
                        <li class="fly-msg app-havebind">
                            <i class="iconfont icon-qq"></i>
                            <span>已成功绑定，您可以使用QQ帐号直接登录Fly社区，当然，您也可以</span>
                            <a href="javascript:;" class="acc-unbind" type="qq_id">解除绑定</a>

                            <!-- <a href="" onclick="layer.msg('正在绑定微博QQ', {icon:16, shade: 0.1, time:0})" class="acc-bind" type="qq_id">立即绑定</a>
                            <span>，即可使用QQ帐号登录Fly社区</span> -->
                        </li>
                        <li class="fly-msg">
                            <i class="iconfont icon-weibo"></i>
                            <!-- <span>已成功绑定，您可以使用微博直接登录Fly社区，当然，您也可以</span>
                            <a href="javascript:;" class="acc-unbind" type="weibo_id">解除绑定</a> -->

                            <a href="" class="acc-weibo" type="weibo_id"
                               onclick="layer.msg('正在绑定微博', {icon:16, shade: 0.1, time:0})">立即绑定</a>
                            <span>，即可使用微博帐号登录Fly社区</span>
                        </li>
                    </ul>
                </div>
            </div>

        </div>
    </div>
</div>
</div>
<script src="${absolutePath}/asset/res/jquery/jquery-3.2.1.js" type="text/javascript"></script>
<script src="${absolutePath}/asset/res/js/common.js"></script>
<script src="${absolutePath}/asset/res/js/city.js"></script>
<script src="${absolutePath}/asset/res/js/set.js"></script>
<script src="${absolutePath}/asset/res/layui/layui.js" ></script>


</body>
</html>
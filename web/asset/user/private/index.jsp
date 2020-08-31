<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>用户中心</title>
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
      <!-- 登入后的状态 -->
      <li class="layui-nav-item">
        <a class="fly-nav-avatar" href="javascript:;">
          <cite class="layui-hide-xs">贤心</cite>
          <i class="iconfont icon-renzheng layui-hide-xs" title="认证信息：layui 作者"></i>
          <i class="layui-badge fly-badge-vip layui-hide-xs">VIP3</i>
          <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg">
        </a>
        <dl class="layui-nav-child">
          <dd><a href="${absolutePath}/asset/user/set.jsp"><i class="layui-icon">&#xe620;</i>基本设置</a></dd>
          <dd><a href="${absolutePath}/asset/user/message.jsp"><i class="iconfont icon-tongzhi" style="top: 4px;"></i>我的消息</a></dd>
          <dd><a href="${absolutePath}/asset/user/private/home.jsp"><i class="layui-icon" style="margin-left: 2px; font-size: 22px;">&#xe68e;</i>我的主页</a></dd>
          <hr style="margin: 5px 0;">
          <dd><a href="${absolutePath}/user/logout" style="text-align: center;">退出</a></dd>
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
    <li class="layui-nav-item layui-this">
      <a href="index.jsp">
        <i class="layui-icon">&#xe612;</i>
        用户中心
      </a>
    </li>
    <li class="layui-nav-item">
      <a href="../set.jsp">
        <i class="layui-icon">&#xe620;</i>
        基本设置
      </a>
    </li>
    <li class="layui-nav-item">
      <a href="../message.jsp">
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
    <!--
    <div class="fly-msg" style="margin-top: 15px;">
      您的邮箱尚未验证，这比较影响您的帐号安全，<a href="activate.html">立即去激活？</a>
    </div>
    -->
    <div class="layui-tab layui-tab-brief" lay-data="{url:'${absolutePath}/user/getQuestion',page:true,id:'user_question'}" lay-filter="user">
      <ul class="layui-tab-title" id="LAY_mine">
        <li data-type="mine-jie" lay-id="index" class="layui-this">我发的帖（<span>${fn:length(PublishQuestionPageInfo.list)}</span>）</li>
        <li data-type="collection" data-url="/collection/find/" lay-id="collection">我收藏的帖（<span>${fn:length(FavoriteQuestionPageInfo.list)}</span>）</li>
      </ul>
      <div class="layui-tab-content" style="padding: 20px 0;">
        <div class="layui-tab-item layui-show">
          <!-- 我发布的帖子start -->
          <ul class="mine-view jie-row">
            <c:forEach items="${PublishQuestionPageInfo.list}" var="userQuestionInfo">
              <li>
                <a class="jie-title" href="${absolutePath}/user/question/detail?qid=${userQuestionInfo.qid}" target="_blank">${userQuestionInfo.questionTitle}</a>
                <i>发布于${userQuestionInfo.questionCreateDate}</i>
                <a class="mine-edit" href="${absolutePath}/user/question/edit?qid=${userQuestionInfo.qid}">编辑</a>
                <em>${userQuestionInfo.questionAccessNum}阅/${userQuestionInfo.questionCommentNum}答</em>
              </li>
            </c:forEach>
          </ul>
          <!-- 我发布的帖子end -->
            <!-- 发布分页start -->
            <div class="layui-box layui-laypage layui-laypage-default" id="layui-laypage-1">
                <a href="${absolutePath}/user/getquestion?page=${PublishQuestionPageInfo.prePage}" class="layui-laypage-prev layui-disabled" data-page="${PublishQuestionPageInfo.prePage}">
                    <i class="layui-icon"></i>
                </a>

              <!-- 永远显示7页，当前页永远在中间 -->
              <c:choose>
                <%-- 第一种条件.起始条件--%>
                <c:when test="${PublishQuestionPageInfo.pageNum<=7}">
                  <c:if test="${PublishQuestionPageInfo.pages<7}">
                    <c:set var="begin" value="1"></c:set>
                    <c:set var="end" value="${PublishQuestionPageInfo.pages}"></c:set>
                  </c:if>
                  <c:if test="${PublishQuestionPageInfo.pages>7}">
                    <c:set var="begin" value="1"></c:set>
                    <c:set var="end" value="7"></c:set>
                  </c:if>
                </c:when>
                <%-- 第二种条件.中间条件--%>
                <c:when
                        test="${PublishQuestionPageInfo.pageNum-3>=1&&PublishQuestionPageInfo.pageNum+3<=PublishQuestionPageInfo.pages}">
                  <c:set var="begin" value="${PublishQuestionPageInfo.pageNum-3}"></c:set>
                  <c:set var="end" value="${PublishQuestionPageInfo.pageNum+3}"></c:set>
                </c:when>
                <%-- 第三种条件.末尾条件--%>
                <c:when test="${PublishQuestionPageInfo.pageNum+3>PublishQuestionPageInfo.pages}">
                  <c:set var="begin" value="${PublishQuestionPageInfo.pages-6}"></c:set>
                  <c:set var="end" value="${PublishQuestionPageInfo.pages}"></c:set>
                </c:when>
              </c:choose>
              <!-- 通用输出 -->
              <c:forEach begin="${begin}" end="${end}" var="i">
                <c:if test="${PublishQuestionPageInfo.pageNum==i}">
                  <span class="layui-laypage-curr">
                    <em class="layui-laypage-em"></em><em>${i}</em>
                  </span>
                </c:if>
                <c:if test="${PublishQuestionPageInfo.pageNum!=i}">
                  <a href="${absolutePath}/user/getquestion?page=${i}" data-page="${i}">${i}</a>
                </c:if>
              </c:forEach>
                <a href="${absolutePath}/user/getquestion?page=${PublishQuestionPageInfo.nextPage}" class="layui-laypage-next" data-page="${PublishQuestionPageInfo.nextPage}"><i class="layui-icon"></i></a>
                <!-- 跳转页 -->
                <span class="layui-laypage-skip">到第<input type="text" min="1" value="1" class="layui-input">页<button type="button" class="layui-laypage-btn">确定</button></span>
                <span class="layui-laypage-count">共 ${PublishQuestionPageInfo.size} 条</span>
                <span class="layui-laypage-limits">
                    <select lay-ignore="">
                        <option value="10" selected="">10 条/页</option>
                        <option value="20">20 条/页</option>
                        <option value="30">30 条/页</option>
                        <option value="40">40 条/页</option>
                        <option value="50">50 条/页</option>
                        <option value="60">60 条/页</option>
                        <option value="70">70 条/页</option>
                        <option value="80">80 条/页</option>
                        <option value="90">90 条/页</option>
                    </select>
                </span>
            </div>
            <!-- 发布分页end -->
          <div id="LAY_page"></div>
        </div>
        <div class="layui-tab-item">
          <ul class="mine-view jie-row">
            <!-- 我收藏的帖子start -->
            <c:forEach items="${FavoriteQuestionPageInfo.list}" var="FavoriteQuestionPageInfo">
              <li>
                <a class="jie-title" href="${absolutePath}/user/question/detail?qid=${FavoriteQuestionPageInfo.qid}" target="_blank">${FavoriteQuestionPageInfo.questionTitle}</a>
                <i>收藏于${FavoriteQuestionPageInfo.questionFavoriteDate}</i>
              </li>
            </c:forEach>
            <!-- 我收藏的帖子end -->
          </ul>
          <!-- 收藏贴分页start -->
          <div class="layui-box layui-laypage layui-laypage-default">
            <a href="${absolutePath}/user/getquestion?page=${PublishQuestionPageInfo.prePage}" class="layui-laypage-prev layui-disabled" data-page="${PublishQuestionPageInfo.prePage}">
              <i class="layui-icon"></i>
            </a>

            <!-- 永远显示7页，当前页永远在中间 -->
            <c:choose>
              <%-- 第一种条件.起始条件--%>
              <c:when test="${FavoriteQuestionPageInfo.pageNum<=7}">
                <c:if test="${FavoriteQuestionPageInfo.pages<7}">
                  <c:set var="begin" value="1"></c:set>
                  <c:set var="end" value="${FavoriteQuestionPageInfo.pages}"></c:set>
                </c:if>
                <c:if test="${FavoriteQuestionPageInfo.pages>7}">
                  <c:set var="begin" value="1"></c:set>
                  <c:set var="end" value="7"></c:set>
                </c:if>
              </c:when>
              <%-- 第二种条件.中间条件--%>
              <c:when
                      test="${FavoriteQuestionPageInfo.pageNum-3>=1&&FavoriteQuestionPageInfo.pageNum+3<=FavoriteQuestionPageInfo.pages}">
                <c:set var="begin" value="${FavoriteQuestionPageInfo.pageNum-3}"></c:set>
                <c:set var="end" value="${FavoriteQuestionPageInfo.pageNum+3}"></c:set>
              </c:when>
              <%-- 第三种条件.末尾条件--%>
              <c:when test="${FavoriteQuestionPageInfo.pageNum+3>FavoriteQuestionPageInfo.pages}">
                <c:set var="begin" value="${FavoriteQuestionPageInfo.pages-6}"></c:set>
                <c:set var="end" value="${FavoriteQuestionPageInfo.pages}"></c:set>
              </c:when>
            </c:choose>
            <!-- 通用输出 -->
            <c:forEach begin="${begin}" end="${end}" var="i">
              <c:if test="${FavoriteQuestionPageInfo.pageNum==i}">
                  <span class="layui-laypage-curr">
                    <em class="layui-laypage-em"></em><em>${i}</em>
                  </span>
              </c:if>
              <c:if test="${FavoriteQuestionPageInfo.pageNum!=i}">
                <a href="${absolutePath}/user/getquestion?page=${i}" data-page="${i}">${i}</a>
              </c:if>
            </c:forEach>
            <a href="${absolutePath}/user/getquestion?page=${FavoriteQuestionPageInfo.nextPage}" class="layui-laypage-next" data-page="${FavoriteQuestionPageInfo.nextPage}"><i class="layui-icon"></i></a>
            <!-- 跳转页 -->
            <span class="layui-laypage-skip">到第<input type="text" min="1" value="1" class="layui-input">页<button type="button" class="layui-laypage-btn">确定</button></span>
            <span class="layui-laypage-count">共 ${FavoriteQuestionPageInfo.size} 条</span>
            <span class="layui-laypage-limits">
                    <select lay-ignore="">
                        <option value="10" selected="">10 条/页</option>
                        <option value="20">20 条/页</option>
                        <option value="30">30 条/页</option>
                        <option value="40">40 条/页</option>
                        <option value="50">50 条/页</option>
                        <option value="60">60 条/页</option>
                        <option value="70">70 条/页</option>
                        <option value="80">80 条/页</option>
                        <option value="90">90 条/页</option>
                    </select>
                </span>
          </div>
          <!-- 收藏贴分页end -->
          <div id="LAY_page1"></div>
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
<script src="/ssm-bbs/asset/res/js/common.js" type="text/javascript"></script>
<script src="/ssm-bbs/asset/res/js/userIndex.js" type="text/javascript"></script>
<script src="${absolutePath}/asset/res/layui/layui.js"></script>

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
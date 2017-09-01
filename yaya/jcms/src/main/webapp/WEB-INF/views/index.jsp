<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/15
  Time: 12:00
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>YaYa_首个医生移动学术交流平台_药草园</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
</head>
<body>
<div class="aside p-re-1">
    <div class="l-menu content">
        <div class="l-list">
            <ul>
                <li class="cur">
                    <a href="${ctx}/home/index" target="mainFrame"><i></i><img src="${ctxStatic}/images/icon-left-1-hover.png" hoverSrc="${ctxStatic}/images/icon-left-1-hover.png" regionSrc="${ctxStatic}/images/icon-left-1.png" alt="" /><span>首页</span></a>
                </li>
            </ul>
        </div>
        <c:forEach items="${menuList}" var="menu" varStatus="status">
            <div class="l-list">
                <p class="m-tit">${menu.menuName}</p>
                <ul>
                    <c:forEach items="${menu.items}" var="sub"  varStatus="subStatus">
                        <li>
                            <a href="${ctx}${sub.href}" target="mainFrame"><i></i><img src="${ctxStatic}/images/${sub.icon}.png" regionSrc="${ctxStatic}/images/${sub.icon}.png" hoverSrc="${ctxStatic}/images/${sub.icon}-hover.png"  alt="" /><span>${sub.menuName}</span></a>
                        </li>
                    </c:forEach>
                    <c:if test="${status.index + 1 == fn:length(menuList) && testUnitAccount}">
                        <li>
                            <a href="${ctx}/mng/account/test/list" target="mainFrame"><i></i><img src="${ctxStatic}/images/icon-left-8.png" regionsrc="/static/images/icon-left-8.png" hoversrc="${ctxStatic}/images/icon-left-8-hover.png" alt=""><span>测试账号</span></a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </c:forEach>
        <div class="logout-btn"><a href="${ctx}/logout">退出账号</a></div>
    </div>
</div>

<iframe width="100%" name="mainFrame" id="mainFrame" frameborder="0" src="${ctx}/home/index" scrolling="auto"></iframe>

<script>
    $(function(){
        var winHeight = $(document).height();
        $("#mainFrame").attr("height", winHeight);

        $(".l-list>ul>li").click(function(){
            $(".l-list>ul>li").removeClass("cur");
            $(this).addClass("cur");
            $(".l-list>ul>li").each(function(){
                var regionSrc = $(this).find("img").attr("regionSrc");
                var hoverSrc = $(this).find("img").attr("hoverSrc");
                if($(this).hasClass("cur")){
                    $(this).find("img").attr("src",hoverSrc);
                }else{
                    $(this).find("img").attr("src",regionSrc);
                }
            });
        });
        //适配下拉
        if($(".aside").length){
            $(".aside").perfectScrollbar();
        }
    });
</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/31
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${csp_locale eq 'en_US'}">
                ${article.titleUs}
            </c:when>
            <c:when test="${csp_locale eq 'zh_TW'}">
                ${article.titleTw}
            </c:when>
            <c:otherwise>
                ${article.titleCn}
            </c:otherwise>
        </c:choose>
        - <fmt:message key="page.common.appName"/></title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <style>
        html,body { background-color:#F7F9FB;}
    </style>
</head>

<body>
<div id="wrapper">
    <%@include file="../include/index_header.jsp"%>

    <div class="admin-content bg-gray" >
        <div class="page-width clearfix">
            <div class="subPage-head item-shadow item-radius clearfix">
                <ul id="menu">
                    <li ><a href="${ctx}/index/17103116063862386794"><fmt:message key="page.index.faq"/></a></li>
                    <li ><a href="${ctx}/index/17103116065640899342"><fmt:message key="page.index.guide"/></a></li>
                    <li ><a href="${ctx}/index/17103116070973422540"><fmt:message key="page.index.service"/></a></li>
                </ul>
            </div>
            <div class="subPage-main item-shadow item-radius" >
                <c:choose>
                    <c:when test="${csp_locale eq 'en_US'}">
                        ${article.contentUs}
                    </c:when>
                    <c:when test="${csp_locale eq 'zh_TW'}">
                        ${article.contentTw}
                    </c:when>
                    <c:otherwise>
                        ${article.contentCn}
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <%@include file="../include/footer.jsp"%>
</div>
<script>
    $(function(){
        var urlstr = location.href;
        $("#menu>li").removeClass("cur");
        $("#menu>li").each(function () {
            if (urlstr.indexOf($(this).find("a").attr('href')) > -1) {
                $(this).addClass('cur');
                return false;
            }
        });
    });

</script>
</body>
</html>

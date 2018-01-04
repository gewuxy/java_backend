<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/31
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
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
                <h3 class="title">
                        <c:if test="${article.id eq '17103116215880292674'}">
                            <i class="icon icon-header-point"></i>
                        </c:if>
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

                </h3>
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

</body>
</html>

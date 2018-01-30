<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/4/18
  Time: 18:10
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${csp_locale}" scope="session"/>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<script src="${ctxStatic}/js/jquery-1.12.4.min.js"></script>
<script src="${ctxStatic}/js/util.js"></script>
<script src="${ctxStatic}/js/layer/layer.js"></script>
<script src="${ctxStatic}/js/jquery.cookie.js"></script>
<script src="${ctxStatic}/js/ajaxUtils.js"></script>

<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>
<link rel="SHORTCUT ICON" href="${ctxStatic}/images/v2/icon.ico" />

<!--[if lt IE 9]>
<script src="${ctxStatic}js/html5.js"></script>
<![endif]-->
<link rel="stylesheet" href="${ctxStatic}/css/global.css">
<link rel="stylesheet" href="${ctxStatic}/css/swiper.css">
<c:choose>
    <c:when test="${csp_locale eq 'en_US'}">
        <link rel="stylesheet" href="${ctxStatic}/css/style-EN.css">
    </c:when>
    <c:otherwise>
        <link rel="stylesheet" href="${ctxStatic}/css/style.css">
    </c:otherwise>
</c:choose>


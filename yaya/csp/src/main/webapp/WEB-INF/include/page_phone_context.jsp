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
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>
<script src="${ctxStatic}/js/jquery-1.12.4.min.js"></script>
<script src="${ctxStatic}/js/util.js"></script>
<script src="${ctxStatic}/js/layer/layer.js"></script>
<script src="${ctxStatic}/js/jquery.cookie.js"></script>
<script src="${ctxStatic}/js/ajaxUtils.js"></script>



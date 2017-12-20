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
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<script src="${ctxStatic}/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>

<link href="${ctxStatic}/bootstrap/2.3.1/css_readable/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
<!--[if lte IE 7]><link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome-ie7.min.css" type="text/css" rel="stylesheet" /><![endif]-->
<!--[if lte IE 6]><link href="${ctxStatic}/bootstrap/bsie/css/bootstrap-ie6.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script><![endif]-->
<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/common/jx.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/common/jx.js" type="text/javascript"></script>
<script src="${ctxStatic}/layer/layer.js"></script>
<link href="${ctxStatic}/layer/skin/default/layer.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/js/layer_util.js"></script>
<script src="${ctxStatic}/js/common.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-select2/3.4/select2.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/laydate/laydate.js"></script>
<link href="${ctxStatic}/jquery-select2/3.4/select2.min.css" type="text/css" rel="stylesheet" />




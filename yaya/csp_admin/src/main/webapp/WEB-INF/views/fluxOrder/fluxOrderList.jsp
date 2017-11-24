<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>流量订单列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/order/list">流量订单列表</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="pageForm" name="pageForm" action="${ctx}/csp/order/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
</form>
<form id="searchForm" method="post" action="${ctx}/csp/order/list" class="breadcrumb form-search">
    <input placeholder="订单号" value="${tradeId}" type="search" name="tradeId" maxlength="50" class="required"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>用户名</th><th>购买时间</th><th>订单状态</th><th>生效时间</th><th>订单号</th><th>流量充值</th><th>充值平台</th><shiro:hasPermission name="sys:region:edit"><th>操作</th></shiro:hasPermission></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="data">
            <tr>
                <td>${data.username}</td>
                <td><fmt:formatDate value="${data.buyTime}" type="both" dateStyle="full"/></td>
                <td>${data.state eq 0? "未到账": data.state eq 1?"已到账":data.state eq 2?"已关闭":""}</td>
                <td><fmt:formatDate value="${data.effectTime}" type="both" dateStyle="full"/></td>
                <td>${data.tradeId}</td>
                <td>${data.flux}</td>
                <td>${data.platform}</td>
                <shiro:hasPermission name="sys:hospital:edit"><td>
                    <a href="${ctx}/csp/order/check?id=${data.id}">查看</a>
                    <c:if test="${data.state eq 1}">
                    <a href="${ctx}/csp/order/close?id=${data.id}" id="closeOrder">关闭</a>
                    </c:if>
                </td></shiro:hasPermission>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${empty page.dataList}">
        <tr>
            <td colspan="7">没有查询到数据</td>
        </tr>
    </c:if>
    </tbody>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>
</body>
<script>

</script>
</html>

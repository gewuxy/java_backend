<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/sys/user/list">管理员列表</a></li>
</ul>
<form id="pageForm" name="pageForm" action="${ctx}/csp/sys/user/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input type="hidden" name="keyword" value="${keyword}">
    <input type="hidden" name="level" value="${level}">
</form>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="searchForm" method="post" class="breadcrumb form-search">
    <input placeholder="管理员帐号" value="${account}" size="40"  type="search" name="account" maxlength="50" class="required"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
    <input id="btnAdd" class="btn btn-primary" type="button" style="margin-left: 60%" value="添加管理员" onclick="window.location.href = '${ctr}/csp/sys/user/addAdmin'"/>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>帐号</th><th>姓名</th><th>电话</th><th>电子邮箱</th><th>使用状态</th><th>最后登录时间</th><th>最后登录ip地址</th></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="user">
            <tr>
                <td>${user.account}</td>
                <td>${user.userName}</td>
                <td>${user.mobile}</td>
                <td>${user.email}</td>
                <td>${user.active == true ? "使用":"禁用"}</td>
                <td><fmt:formatDate value="${user.lastLoginDate}" type="both" dateStyle="full"/></td>
                <td>${user.lastLoginIp}</td>
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
</html>

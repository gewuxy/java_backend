<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/5
  Time: 16:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>激活码列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/custom/role/list">角色列表</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="searchForm" action="${ctx}/custom/code/list" method="post" class="breadcrumb form-search">

</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>角色ID</th><th>角色名称</th><th>中文描述</th><th>角色权重</th><shiro:hasPermission name="custom:role:edit"><th>操作</th></shiro:hasPermission></tr></thead>
    <tbody>
    <c:forEach items="${list}" var="role">
        <tr>
            <td>${role.id}</td>
            <td>${role.roleName}</td>
            <td>${role.roleDesc}</td>
            <td>${role.roleWeight}</td>
            <shiro:hasPermission name="custom:role:edit">
                <td><a href="${ctx}/custom/role/assign?id=${role.id}">分配权限</a></td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>

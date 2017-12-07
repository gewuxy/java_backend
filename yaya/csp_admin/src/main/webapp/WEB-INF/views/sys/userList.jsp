<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/user/list">管理员列表</a></li>
    <li><a href="${ctx}/sys/user/add">添加管理员</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="pageForm" name="pageForm" action="${ctx}/sys/user/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input  name="userName" type="hidden" value="${userName}"/>
</form>
<form id="searchForm" method="post" action="${ctx}/sys/user/list" class="breadcrumb form-search">
    <input placeholder="管理员帐号" value="${userName}" size="40"  type="search" name="userName" maxlength="50" class="required"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>帐号</th><th>真实姓名</th><th>电话</th><th>电子邮箱</th><th>使用状态</th><th>用户角色</th><th>最后登录时间</th><th>最后登录ip地址</th><th>操作</th></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="user">
            <tr>
                <td>${user.userName}</td>
                <td>${user.realName}</td>
                <td>${user.mobile}</td>
                <td>${user.email}</td>
                <td>${user.active == true ? "使用":"禁用"}</td>
                <td>${user.roleDesc}</td>
                <td><fmt:formatDate value="${user.lastLoginDate}" type="both" dateStyle="full"/></td>
                <td>${user.lastLoginIp}</td>
                <td>
                    <shiro:hasPermission name="sys:user:edit">
                         <a href="${ctx}/sys/user/add?id=${user.id}">查看</a>
                        <c:choose>
                            <c:when test="${user.active == true}">
                                <a data-href="${ctx}/sys/user/edit?id=${user.id}&active=0"  onclick="layerConfirm('确认要禁用该用户帐号吗？', this)">禁用</a>
                            </c:when>
                            <c:otherwise>
                                <a data-href="${ctx}/sys/user/edit?id=${user.id}&active=1"  onclick="layerConfirm('确认要激活该用户帐号吗？', this)">激活</a>
                            </c:otherwise>
                        </c:choose>
                    </shiro:hasPermission>
                </td>
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

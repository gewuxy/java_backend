<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CSP用户管理</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/user/list">CSP用户列表</a></li>
</ul>
<form id="pageForm" name="pageForm" action="${ctx}/csp/user/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input type="hidden" name="keyword" value="${keyword}">
    <input type="hidden" name="level" value="${level}">
</form>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="searchForm" method="post" class="breadcrumb form-search">
    <input placeholder="输入用户名进行搜索" value="${userName}" size="40"  type="search" name="userName" maxlength="50" class="required"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
    <input id="btnAdd" class="btn btn-primary" type="button" style="margin-left: 60%" value="注册新用户" onclick="window.location.href = '${ctr}/csp/user/viewOrRegister?actionType=4'"/>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>昵称</th><th>姓名</th><th>电话</th><th>电子邮箱</th><th>注册时间</th><th>最后登录时间</th><th>是否激活</th><th>操作</th></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="user">
            <tr>
                <td>${user.nickName}</td>
                <td>${user.userName}</td>
                <td>${user.mobile}</td>
                <td>${user.email}</td>
                <td><fmt:formatDate value="${user.registerTime}" type="both" dateStyle="full"/></td>
                <td><fmt:formatDate value="${user.lastLoginTime}" type="both" dateStyle="full"/></td>
                <td>${user.active == true ? "激活":"未激活"}</td>
                <td>
                    <a href="${ctx}/csp/user/viewOrRegister?id=${user.id}&actionType=3">修改</a>
                    <a data-href="${ctx}/csp/user/update?id=${user.id}&actionType=1"  onclick="layerConfirm('确认要停用该用户帐号吗？', this)">停用</a>
                    <a data-href="${ctx}/csp/user/update?id=${user.id}&actionType=2"  onclick="layerConfirm('确认要删除该用户帐号吗？', this)">删除</a>
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

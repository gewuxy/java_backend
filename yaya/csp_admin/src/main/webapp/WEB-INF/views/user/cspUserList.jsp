<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CSP用户管理</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/csp/user/list?listType=0">国内用户列表</a></li>
    <li><a href="${ctx}/csp/user/list?listType=1">海外用户列表</a></li>
    <li><a href="${ctx}/csp/user/list?listType=2">冻结用户列表</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="pageForm" name="pageForm" action="${ctx}/csp/user/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input  name="keyWord" type="hidden" value="${keyWord}"/>
    <input  name="listType" type="hidden" value="${listType}"/>
</form>
<form id="searchForm" method="post" action="${ctx}/csp/user/list" class="breadcrumb form-search">
    <input placeholder="昵称/用户名/电话" value="${keyWord}" size="40"  type="search" name="keyWord" maxlength="50" class="required"/>
    <input  name="listType" type="hidden" value="${listType}"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>昵称</th><th>用户名</th><th>电话</th><th>电子邮件</th><th>注册时间</th><th>操作</th></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="user">
            <tr>
                <td>${user.nickName}</td>
                <td>${user.userName}</td>
                <td>${user.mobile}</td>
                <td>${user.email}</td>
                <td><fmt:formatDate value="${user.registerTime}" type="both"/></td>
                <td>
                    <shiro:hasPermission name="csp:user:edit">
                        <a href="${ctx}/csp/user/viewOrRegister?id=${user.id}&actionType=3&listType=${listType}">修改</a>
                        <c:if test="${user.active eq true}">
                            <a data-href="${ctx}/csp/user/update?id=${user.id}&actionType=1&listType=${listType}"  onclick="layerConfirm('确认要冻结该用户帐号吗？', this)">冻结</a>
                        </c:if>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="csp:user:del">
                         <a data-href="${ctx}/csp/user/update?id=${user.id}&actionType=2&listType=${listType}"  onclick="layerConfirm('确认要删除该用户帐号吗？', this)">删除</a>
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
<script>
    $(document).ready(function() {
        var active = '${listType}';
        $(".nav-tabs li:eq(" + active +")").addClass("active");
    });
</script>
</body>
</html>

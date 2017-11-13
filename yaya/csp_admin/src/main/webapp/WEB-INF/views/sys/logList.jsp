<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>日志列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/sys/log/list">日志管理</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="searchForm" method="post" class="breadcrumb form-search">
    <input placeholder="输入帐号进行搜索" value="${account}" size="40"  type="search" name="account" maxlength="50" />
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>帐号</th><th>姓名</th><th>操作url路径</th><th>操作时间</th></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="log">
            <tr>
                <td>${log.account}</td>
                <td>${log.userName}</td>
                <td>${log.action}</td>
                <td><fmt:formatDate value="${log.logDate}" type="both" dateStyle="full"/></td>
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
       initPage("/csp/sys/log/list");
    });
</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>医院列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/feedback/index">服务菜单</a></li>
</ul>
<form id="pageForm" name="pageForm" action="${ctx}/csp/feedback/index" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
</form>
<%@include file="/WEB-INF/include/message.jsp"%>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>标题(中文)</th>
        <th>標題(台灣)</th>
        <th>Title(US)</th>
        <th>操作</th>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="data">
            <tr>
                <td>
                   ${data.titleCn}
                </td>

                <td>
                   ${data.titleTw}
                </td>
                <td>
                    ${data.titleUs}
                </td>
                </td>
                <shiro:hasPermission name="sys:hospital:edit"><td>
                    <a href="${ctx}/csp/feedback/check?id=${data.id}">查看(check)</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="${ctx}/csp/feedback/edit?id=${data.id}">修改(update)</a>
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

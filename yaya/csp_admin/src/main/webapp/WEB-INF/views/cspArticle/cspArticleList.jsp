<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>服务菜单</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/csp/article/list?listType=0">服务菜单(CN)</a></li>
    <li><a href="${ctx}/csp/article/list?listType=1">服務菜單（TW）</a></li>
    <li><a href="${ctx}/csp/article/list?listType=2">Service menu(EN)</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="pageForm" name="pageForm" action="${ctx}/csp/article/list" method="post">
    <input  name="pageNum"  type="hidden"   value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden"   value="${page.pageSize}"/>
    <input  name="listType" type="hidden"   value="${listType}">
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <c:if test="${listType == 0 || listType == null}">
        <th>标题</th>
        </c:if>
        <c:if test="${listType == 1}">
        <th>標題</th>
        </c:if>
        <c:if test="${listType == 2}">
        <th>Title</th>
        </c:if>
        <th>操作</th>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="data">
            <tr>
                <c:if test="${listType == 0 || listType == null}">
                <td>
                   ${data.titleCn}
                </td>
                </c:if>
                <c:if test="${listType == 1}">
                <td>
                   ${data.titleTw}
                </td>
                </c:if>
                <c:if test="${listType == 2}">
                <td>
                    ${data.titleUs}
                </td>
                </c:if>
                </td>
                <td>
                    <shiro:hasPermission name="csp:article:view"><a href="${ctx}/csp/article/check?id=${data.id}&listType=${listType}">查看(check)</a></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;
                    <shiro:hasPermission name="csp:article:edit"><a href="${ctx}/csp/article/edit?id=${data.id}&listType=${listType}">修改(update)</a></shiro:hasPermission>
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
<script>
    $(document).ready(function() {
        var active = '${listType}';
        $(".nav-tabs li:eq(" + active +")").addClass("active");
    });
</script>
</html>

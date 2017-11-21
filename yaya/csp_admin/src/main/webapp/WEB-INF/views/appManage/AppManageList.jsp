<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>App管理列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/appManage/list">App管理列表</a></li>
</ul>
<form id="pageForm" name="pageForm" action="${ctx}/csp/appManage/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
</form>
<%@include file="/WEB-INF/include/message.jsp"%>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>应用类型</th>
        <th>版本号</th>
        <th>下载链接</th>
        <th>手机类型</th>
        <th>更新时间</th>
        <shiro:hasPermission name="sys:region:edit"><th>操作</th></shiro:hasPermission>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="app">
            <tr>
                <td>${app.appType}</td>
                <td>${app.version}</td>
                <td>${app.downLoadUrl}</td>
                <td>${app.driveTag}</td>
                <td><fmt:formatDate value="${app.updateTime}" type="both" dateStyle="full"/></td>
                <shiro:hasPermission name="sys:hospital:edit"><td>
                    <a href="${ctx}/csp/appManage/check?id=${app.id}">查看</a>
                    <a href="${ctx}/csp/appManage/delete?id=${app.id}" onclick="deleteAppManage()">删除</a>
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
    <div>
        <input id="insertApp" class="btn btn-primary" type="button"
               value="添 加" style="margin-left: 1108px"/>
    </div>

<%@include file="/WEB-INF/include/pageable.jsp"%>
</body>
<script>
        $("#insertApp").click(function () {
            location.href="${ctx}/csp/appManage/edit"
        })
    function deleteAppManage() {
        if (confirm("确定删除吗？")){
            alert("删除成功")
        }
    }
</script>
</html>

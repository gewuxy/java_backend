<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/27
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页推荐会议列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

</head>
<body>
<ul class="nav nav-tabs">

</ul>
<form id="pageForm" name="pageForm" action="${ctx}/yaya/recommendMeet/queryMeet" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
</form>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="searchForm" method="post" class="breadcrumb form-search">
    <input placeholder="会议名称" value="${meetName}" size="40"  type="search" name="meetName" maxlength="50" class="required"/>&nbsp;&nbsp;
<shiro:hasPermission name = "yaya:recommendMeet:view">
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
</shiro:hasPermission>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <form method="post" id="meetForm">
    <thead><tr>
        <th>ID</th>
        <th>会议名称</th>
        <th>操作</th>
    </tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="meet">
            <tr>
                <td id="meetId">${meet.id}</td>
                <td id="name">${meet.meetName}</td>
                <td>
                    <a href="${ctx}/yaya/recommendMeet/selectOne?id=${meet.id}" onclick="selectOneOut()">选中</a>
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
    </form>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>
</body>
<script>
    function selectOneOut() {
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
    }
</script>
</html>

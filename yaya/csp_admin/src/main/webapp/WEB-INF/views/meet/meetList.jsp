<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/meet/list">会议列表</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="pageForm" name="pageForm" action="${ctx}/csp/meet/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input  name="meetName" type="hidden" value="${meetName}"/>
</form>
<form id="searchForm" method="post" action="${ctx}/csp/meet/list" class="breadcrumb form-search">
    <input placeholder="请输入会议名称" value="${meetName}" size="40"  type="search" name="meetName" maxlength="50" class="required"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>会议名称</th><th>主办方</th><th>主讲者</th><th>科室</th><th>会议状态</th><th>开始时间</th><th>操作</th></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="meet">
            <tr>
                <td>${meet.meetName}</td>
                <td>${meet.organizer}</td>
                <td>${meet.lecturer}</td>
                <td>${meet.meetType}</td>
                <td>${meet.state eq 0? "草稿": meet.state eq 1?"未开始":meet.state eq 2?"进行中":meet.state eq 3?"已结束":meet.state eq 4?"已撤销":meet.state eq 5?"未发布":meet.state eq 6?"已关闭":"管理员删除"}</td>
                <td><fmt:formatDate value="${meet.startTime}" type="both"/></td>
                <td>
                    <a href="${ctx}/csp/meet/info?id=${meet.id}">查看</a>
                    <a href="${ctx}/csp/meet/update?id=${meet.id}&state=7">删除</a>
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

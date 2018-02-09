<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>讲本模板列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/meet/course/list">讲本列表</a></li>
    <li><a href="${ctx}/csp/meet/course/edit">编辑讲本</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="pageForm" name="pageForm" action="${ctx}/csp/meet/course/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input  name="keyword" type="hidden" value="${keyword}"/>
    <input  name="sourceType" type="hidden" value="${sourceType}"/>
</form>
<form id="searchForm" method="post" action="${ctx}/csp/meet/course/list" class="breadcrumb form-search">
    <input placeholder="请输入讲本名称" value="${keyword}" size="40"  type="search" name="keyword" maxlength="50" class="required"/>&nbsp;&nbsp;
    <select name="sourceType" id="sourceType" style="width: 150px;">
        <option value="">讲本类型</option>
        <option value="2" ${sourceType eq 2 ? 'selected':''}>贺卡模板</option>
        <option value="3" ${sourceType eq 3 ? 'selected':''}>有声红包</option>
        <option value="4" ${sourceType eq 4 ? 'selected':''}>快捷讲本</option>
    </select>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>标题</th><th>类型</th><th>封面</th><th>操作</th></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="c">
            <tr>
                <td>${c.title}</td>
                <td>
                    <c:choose>
                        <c:when test="${c.sourceType == 2}">贺卡模板</c:when>
                        <c:when test="${c.sourceType == 3}">有声红包</c:when>
                        <c:otherwise>快捷讲本</c:otherwise>
                    </c:choose>
                </td>
                <td><c:if test="${c.coverUrl != null}">${fileBase}${c.coverUrl}</c:if></td>
                <td>
                    <shiro:hasPermission name="csp:course:edit">
                        <a href="${ctx}/csp/meet/course/edit?courseId=${c.id}">修改</a>
                    </shiro:hasPermission>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${empty page.dataList}">
        <tr>
            <td colspan="4">没有查询到数据</td>
        </tr>
    </c:if>
    </tbody>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>

</body>
</html>

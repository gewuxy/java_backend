<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/11
  Time: 12:23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>数据中心</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <style type="text/css">
        .data_title{
            width:270px;
            white-space:nowrap;
            overflow:hidden;
            text-overflow:ellipsis;
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a>数据列表</a></li>
    <shiro:hasPermission name="data:file:edit"><li><a href="${ctx}/data/file/edit?categoryId=${categoryId}">添加数据</a></li></shiro:hasPermission>
</ul>
<form id="pageForm" name="pageForm" action="${ctx}/data/file/page" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input type="hidden" name="keyword" value="${keyword}">
</form>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="searchForm" action="${ctx}/data/file/page" method="post" class="breadcrumb form-search">
    <input placeholder="标题/来源/作者" value="${keyword}" size="40"  type="search" name="keyword" maxlength="50" class="required"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
</form>
<input type="hidden" id="preid" value="">
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th width="275">标题</th><th>作者</th><th>来源</th><th>修订时间</th><th>文件大小</th><shiro:hasPermission name="data:file:edit"><th>操作</th></shiro:hasPermission></tr></thead>
    <tbody>
    <c:if test="${not empty page.datas}">
        <c:forEach items="${page.datas}" var="data">
            <tr>
                <td>
                    <shiro:hasPermission name="data:file:edit"><a href="${ctx}/data/file/edit?id=${data.id}"></shiro:hasPermission>
                        <div class="data_title" title="${data.title}">${data.title}</div>
                    <shiro:hasPermission name="data:file:edit"></a></shiro:hasPermission>
                </td>
                <td>${data.author}</td>
                <td>${data.dataFrom}</td>
                <td><fmt:formatDate value="${data.updateDate}" pattern="yyyy/MM/dd"/> </td>
                <td>${data.fileSize}KB</td>

                    <td>
                        <shiro:hasPermission name="data:file:edit">
                            <a href="${ctx}/data/file/edit?id=${data.id}">修改</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="data:del">
                            <a data-href="${ctx}/data/file/delete?id=${data.id}" href="#" onclick="layerConfirm('确认要删除此内容吗？', this.data-href)">删除</a>
                        </shiro:hasPermission>
                            <a href="${ctx}/data/file/view?id=${data.id}">预览</a>
                    </td>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${empty page.datas}">
        <tr>
            <td colspan="6">没有查询到数据</td>
        </tr>
    </c:if>
    </tbody>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>
</body>
</html>

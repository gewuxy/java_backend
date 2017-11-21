<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/11
  Time: 12:23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文章列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <style type="text/css">
        .article_title{
            width:270px;
            white-space:nowrap;
            overflow:hidden;
            text-overflow:ellipsis;
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a>文章列表</a></li>
    <shiro:hasPermission name="article:edit"><li><a href="${ctx}/article/edit?categoryId=${categoryId}">添加文章</a></li></shiro:hasPermission>
</ul>
<form id="pageForm" name="pageForm" action="${ctx}/article/page" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input name="categoryId" type="hidden" value="${categoryId}">
    <input type="hidden" name="keyword" value="${keyword}">
</form>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="searchForm" action="${ctx}/article/page" method="post" class="breadcrumb form-search">
    <input type="hidden" id="categoryId" name="categoryId" value="${categoryId}">
    <select id="authed" name="authed" style="width: 100px;">
        <option value="" ${authed==null?"selected":""}>所有文章</option>
        <option value="true" ${authed?"selected":""}>已审核</option>
        <option value="false" ${authed!=null&&!authed?"selected":""}>未审核</option>
    </select>&nbsp;&nbsp;
    <input placeholder="标题/关键字/来源/作者" value="${keyword}" size="40"  type="search" name="keyword" maxlength="50" class="required"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
</form>
<input type="hidden" id="preid" value="">
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th width="275">标题</th><th>作者</th><th>来源</th><th>关键字</th><th>创建时间</th><th>审核状态</th><shiro:hasPermission name="article:edit"><th>操作</th></shiro:hasPermission></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="article">
            <tr>
                <td>
                    <shiro:hasPermission name="article:edit"><a href="${ctx}/article/edit?id=${article.id}"></shiro:hasPermission>
                        <div class="article_title" title="${article.title}">${article.title}</div>
                    <shiro:hasPermission name="article:edit"></a></shiro:hasPermission>
                </td>
                <td>${article.author}</td>
                <td>${article.xfrom}</td>
                <td>${article.keywords}</td>
                <td><fmt:formatDate value="${article.createTime}" pattern="yyyy/MM/dd"/> </td>
                <td>
                    <shiro:hasPermission name="article:auth">
                    <input type="checkbox" articleId="${article.id}" ${article.authed?"cheched":""}>
                    </shiro:hasPermission>
                    ${article.authed?"已审核":"未审核"}</td>

                    <td>
                        <shiro:hasPermission name="article:edit">
                            <a href="${ctx}/article/edit?id=${article.id}">修改</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="article:del">
                            <a data-href="${ctx}/article/delete?id=${article.id}" href="#" onclick="layerConfirm('确认要删除此内容吗？', this.data-href)">删除</a>
                        </shiro:hasPermission>
                            <a href="${ctx}/article/view?id=${article.id}">预览</a>
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

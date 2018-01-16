<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2018/1/16
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新闻列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>

<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="${ctx}/website/news/list">新闻列表</a></li>
        <shiro:hasPermission name="website:news:add">
            <li><a href="${ctx}/website/news/to/add">发布新闻</a></li>
        </shiro:hasPermission>
    </ul>

    <form id="pageForm" name="pageForm" action="${ctx}/website/news/list" method="post">
        <input name="pageNum" type="hidden" value="${page.pageNum}"/>
        <input name="pageSize" type="hidden" value="${page.pageSize}"/>
        <input name="keyword" type="hidden" value="${keyword}"/>
        <input name="categoryName" type="hidden" value="${categoryName}"/>
    </form>

    <form id="searchForm" method="post" action="${ctx}/website/news/list" class="breadcrumb form-search">
        <input placeholder="标题/类别/关键字" value="${keyword}" size="40"  type="search" name="keyword" maxlength="50" class="required"/>
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    </form>

    <table id="contentTable" class="table table-striped table-bordered table-condensed" align="center">
        <thead>
        <tr>
            <th>标题</th>
            <th>类别</th>
            <th>发布时间</th>
            <th>来源</th>
            <th>关键字</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
            <c:if test="${not empty page.dataList}">
                <c:forEach items="${page.dataList}" var="news">
                    <tr>
                        <td>${news.title}</td>
                        <td>${news.categoryName}</td>
                        <td><fmt:formatDate value="${news.createTime}" pattern="yyyy-MM-dd"/></td>
                        <td>${news.xfrom}</td>
                        <td>${news.keywords}</td>
                        <td>
                            <shiro:hasPermission name="website:news:view">
                                <a href="${ctx}/website/news/view?id=${news.id}">查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="website:news:del">
                                <a data-href="${ctx}/website/news/delete?id=${news.id}"  onclick="layerConfirm('确认要删除吗？', this)">删除</a>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty page.dataList}">
                <tr>
                    <td colspan="6">没有查询到数据</td>
                </tr>
            </c:if>
        </tbody>
    </table>

    <%@include file="/WEB-INF/include/pageable.jsp"%>
</body>
</html>

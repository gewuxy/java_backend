<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/5
  Time: 14:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>科室列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <script>
        $(function(){
            $("#category").change(function(){
                $("#searchForm").submit();
            });
        })
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/depart/list">科室列表</a></li>
    <shiro:hasPermission name="sys:depart:add"><li><a href="${ctx}/sys/region/edit">添加科室</a></li></shiro:hasPermission>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="searchForm" action="${ctx}/sys/depart/list" method="post" class="breadcrumb form-search">
    <select id="category" name="category" style="width:100px;">
        <option value="">所有科室</option>
        <c:forEach items="${categoryList}" var="cate">
            <option value="${cate}" ${cate eq category?'selected':'' }>${cate}</option>
        </c:forEach>
    </select>&nbsp;&nbsp;
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>编号</th><th>名称</th><th>类型</th><shiro:hasPermission name="sys:depart:edit"><th>操作</th></shiro:hasPermission></tr></thead>
    <tbody>
    <c:forEach items="${list}" var="depart">
        <tr>
            <td>${depart.id}</td>
            <td>${depart.name}</td>
            <td>${depart.category}</td>
            <shiro:hasPermission name="sys:depart:edit"><td>
                <a href="${ctx}/sys/region/edit?id=${region.id}">修改</a>
                <a data-href="${ctx}/sys/region/delete?id=${region.id}" href="#" onclick="layerConfirm('确认要删除该区划及所有子区划吗？', this.data-href)">删除</a>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>
</body>
</html>

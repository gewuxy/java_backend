<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2018/2/1
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>背景图片</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

</head>
<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="${ctx}/csp/course/theme/image/list">图片列表</a></li>
        <li><a href="${ctx}/csp/course/theme/image/edit">添加图片</a></li>
    </ul>

    <%@include file="/WEB-INF/include/message.jsp"%>
    <form id="pageForm" name="pageForm" action="${ctx}/csp/course/theme/image/list" method="post">
        <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
        <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
        <input  name="keyword" type="hidden" value="${keyword}"/>
    </form>

    <form id="searchForm" method="post" action="${ctx}/csp/course/theme/image/list" class="breadcrumb form-search">
        <input placeholder="图片名称" value="${keyword}" size="40"  type="search" name="keyword" maxlength="50" class="required"/>
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    </form>

    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead><tr><th>图片名称</th><th>图片大小（kb）</th><th>图片url</th><th>操作</th></tr></thead>
        <tbody>
        <c:if test="${not empty page.dataList}">
            <c:forEach items="${page.dataList}" var="img">
                <tr>
                    <td>${img.imgName}</td>
                    <td>${img.imgSize}</td>
                    <td>${img.imgUrl}</td>
                    <td>
                        <shiro:hasPermission name="theme:image:edit">
                            <a href="${ctx}/csp/course/theme/image/edit?id=${img.id}">修改</a>
                        </shiro:hasPermission>&nbsp;&nbsp;
                        <shiro:hasPermission name="theme:image:del">
                            <a data-href="${ctx}/csp/course/theme/image/delete?id=${img.id}"  onclick="layerConfirm('确认要删除吗？', this)">删除</a>
                        </shiro:hasPermission>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${empty page.dataList}">
            <tr>
                <td colspan="3">没有查询到数据</td>
            </tr>
        </c:if>
        </tbody>
    </table>
    <%@include file="/WEB-INF/include/pageable.jsp"%>

</body>
</html>

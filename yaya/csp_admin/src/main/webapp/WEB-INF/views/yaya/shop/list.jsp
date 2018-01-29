<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2018/1/26
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>

<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="${ctx}/yaya/shop/list">商品列表</a></li>
        <li><a href="${ctx}/yaya/shop/edit">添加商品</a></li>
    </ul>

    <%@include file="/WEB-INF/include/message.jsp"%>
    <form id="pageForm" name="pageForm" action="${ctx}/yaya/shop/list" method="post">
        <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
        <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
        <input  name="name" type="hidden" value="${name}"/>
    </form>

    <form id="searchForm" method="post" action="${ctx}/yaya/shop/list" class="breadcrumb form-search">
        <input placeholder="商品名称" value="${name}" size="40"  type="search" name="name" maxlength="50" class="required"/>
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    </form>

    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead><tr><th>商品名</th><th>价格</th><th>库存</th><th>类型</th><th>状态</th><th>返象数</th><th>限购数</th><th>操作</th></tr></thead>
        <tbody>
        <c:if test="${not empty page.dataList}">
            <c:forEach items="${page.dataList}" var="s">
                <tr>
                    <td>${s.name}</td>
                    <td>${s.price}</td>
                    <td>${s.stock}</td>
                    <td>${s.gtype == 0 ? "礼品" : "电子书"}</td>
                    <td>${s.status == 0 ? "下架" : "上架"}</td>
                    <td>${s.refund}</td>
                    <td>${s.buyLimit}</td>
                    <td>
                        <shiro:hasPermission name="yaya:shop:edit">
                            <a href="${ctx}/yaya/shop/edit?id=${s.id}">修改</a>&nbsp;&nbsp;
                        </shiro:hasPermission>
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

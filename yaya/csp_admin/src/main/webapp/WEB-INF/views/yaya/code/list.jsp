<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2018/1/20
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>激活码列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

</head>
<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="${ctx}/yaya/code/list">激活码列表</a></li>
        <shiro:hasPermission name="yaya:code:add">
            <li><a href="${ctx}/yaya/code/create">生成激活码</a></li>
        </shiro:hasPermission>
    </ul>

    <form id="pageForm" name="pageForm" action="${ctx}/yaya/code/list" method="post">
        <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
        <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
        <input  name="nickname" type="hidden" value="${nickname}"/>
        <input  name="used" type="hidden" value="${used}"/>
        <input  name="actived" type="hidden" value="${actived}"/>
    </form>

    <form id="searchForm" method="post" action="${ctx}/yaya/code/list" class="breadcrumb form-search">
        <input placeholder="单位号名称" value="${nickname}" size="40"  type="search" name="nickname" maxlength="50" class="required"/>
        &nbsp;&nbsp;
        <select name="used" id="used" style="width: 150px;">
            <option value="">使用状态</option>
            <option value="0" ${used eq 0 ? 'selected':''}>未使用</option>
            <option value="1" ${used eq 1 ? 'selected':''}>已使用</option>
        </select>
        &nbsp;&nbsp;
        <select name="actived" id="actived" style="width: 150px;">
            <option value="">激活状态</option>
            <option value="0" ${actived eq 0 ? 'selected':''}>未激活</option>
            <option value="1" ${actived eq 1 ? 'selected':''}>已激活</option>
        </select>
        &nbsp;&nbsp;
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    </form>

    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead><tr><th>激活码</th><th>使用状态</th><th>使用者</th><th>激活状态</th><th>发送时间</th><th>激活时间</th><th>所属单位号</th></tr></thead>
        <tbody>
        <c:if test="${not empty page.dataList}">
            <c:forEach items="${page.dataList}" var="c">
                <tr>
                    <td>${c.code}</td>
                    <td>${c.used ?'<span style="color:green">已使用</span>':'<span style="color:blue">未使用</span>'}</td>
                    <td>${c.toName}</td>
                    <td>${c.actived ?'<span style="color:green">已激活</span>':'<span style="color:blue">未激活</span>'}</td>
                    <td><fmt:formatDate value="${c.sendTime}" pattern="yyyy/MM/dd"/></td>
                    <td><fmt:formatDate value="${c.activeTime}" pattern="yyyy/MM/dd"/></td>
                    <td>${c.nickname}</td>
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

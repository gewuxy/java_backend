<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>公告列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/notify/list">公告列表</a></li>
    <li><a href="${ctx}/csp/notify/notifyInfo">发布公告</a></li>
</ul>

<form id="pageForm" name="pageForm" action="${ctx}/csp/notify/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
</form>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<table id="contentTable" class="table table-striped table-bordered table-condensed" align="center">
    <thead><tr><th>消息标题</th><th>消息内容</th><th>消息类型</th><th>消息发布时间</th><th>消息接受者</th><th>消息发送者</th><th>操作</th></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="mes">
            <tr>
                <td>${mes.title}</td>
                <td>${mes.content}</td>
                <td> ${mes.notifyType eq 0 ? "对所有人发布": mes.notifyType eq 1 ? "对个人发布": ""}</td>
                <td><fmt:formatDate value="${mes.sendTime}" type="both" dateStyle="full"/></td>
                <td>${mes.nickName}</td>
                <td>${mes.senderName}</td>
                <td>
                    <shiro:hasPermission name="csp:notify:view">
                        <a href="${ctx}/csp/notify/edit?id=${mes.id}">查看</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="csp:notify:del">
                        <a data-href="${ctx}/csp/notify/delete?id=${mes.id}"  onclick="layerConfirm('确认要删除吗？', this)">删除</a>
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
<script>
</script>
</html>

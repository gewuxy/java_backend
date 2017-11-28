<%--
  Created by IntelliJ IDEA.
  User: jianliang
  Date: 2017/11/23
  Time: 13:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页广告列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/yaya/banner/list">首页广告列表</a></li>
</ul>
<form id="pageForm" name="pageForm" action="${ctx}/yaya/banner/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="insertBanner" class="btn btn-primary" type="button" value="新 建"  name="message"  style="margin-left: 1108px"/>
</form>
<%@include file="/WEB-INF/include/message.jsp"%>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr>
        <th>是否有效</th>
        <th>Banner权重</th>
        <th>Banner类型</th>
        <th>创建时间</th>
        <th>所属公众号</th>
        <th>用途</th>
        <shiro:hasPermission name="sys:region:edit">
            <th>操作</th></shiro:hasPermission>
        </tr>
    </thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="data">
            <tr>
                <td>${data.active == false ? "关闭": data.active == true ? "开启":""}</td>
                <td>${data.weight}</td>
                <td>${data.type eq 0? "系统Banner":data.type eq 1 ? "用户banner":""}</td>
                <td><fmt:formatDate value="${data.createTime}" type="both" dateStyle="full"/></td>
                <td>${data.nickname}</td>
                <td>${data.appId eq 0? "YAYA医师":data.appId eq 1 ? "合理用药":""}</td>
                <td>
                    <a href="${ctx}/yaya/banner/check?id=${data.id}">修改</a>
                    <c:if test="${data.active == true}">
                        <a href="${ctx}/yaya/banner/close?id=${data.id}" onclick="confirm('确认关闭吗？')">关闭</a>
                    </c:if>
                    <c:if test="${data.active == false}">
                        <a href="${ctx}/yaya/banner/close?id=${data.id}" onclick="confirm('确认开启吗？')">开启</a>
                    </c:if>
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
    $("#insertBanner").click(function () {
        location.href="${ctx}/yaya/banner/edit";
    })
</script>
</html>


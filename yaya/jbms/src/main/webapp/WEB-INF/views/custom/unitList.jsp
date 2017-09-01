<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/5
  Time: 9:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <script>
        $(function(){
            initSelect();

            $("a[role='menuitem']").click(function(){
                $("#dropdownUL>li").removeClass("active");
                $(this).parent().addClass("active");
                $("#authed").val($(this).attr("data-value"));
                initSelect();
            });
        })

        function initSelect(){
            var text = $("#dropdownUL>li.active>a").text();
            $("#authed").val($("#dropdownUL>li.active>a").attr("data-value"));
            $("#selectedValue").text(text);
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/user/list">单位号列表</a></li>
    <shiro:hasPermission name="custom:unit:edit"><li><a href="${ctx}/sys/user/form">单位号添加</a></li></shiro:hasPermission>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="searchForm" action="${ctx}/custom/unit/list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

    <div class="dropdown">
        <input type="hidden" name="authed" id="authed" value="">
        <button type="button" class="btn dropdown-toggle" id="dropdownMenu1"
                data-toggle="dropdown"><span id="selectedValue">所有</span>&nbsp;<span class="caret"></span>
        </button>
        <ul class="dropdown-menu" id="dropdownUL" role="menu" aria-labelledby="dropdownMenu2">
            <li role="presentation" <c:if test="${authed == null}">class="active"</c:if>>
                <a role="menuitem" tabindex="1" href="#" data-value="">所有</a>
            </li>
            <li <c:if test="${authed == 1}">class="active"</c:if> role="presentation">
                <a role="menuitem" tabindex="2" href="#" data-value="true">已审核</a>
            </li>
            <li role="presentation" <c:if test="${authed == 0}">class="active"</c:if>>
                <a role="menuitem" tabindex="3" href="#" data-value="false">未审核</a>
            </li>
        </ul>
        <input placeholder="账号/昵称/姓名/手机/省份/城市" value="${keyword}" size="40"  type="search" name="keyword" maxlength="50" class="required"/>
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>

    </div>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>头像</th><th>账号</th><th>昵称</th><th>姓名</th><th>手机</th><th>省份</th><th>城市</th><th>状态</th><shiro:hasPermission name="custom:unit:edit"><th>操作</th></shiro:hasPermission></tr></thead>
    <tbody>
    <c:forEach items="${page.datas}" var="user">
        <tr>
            <td></td>
            <td><a href="${ctx}/custom/edit?id=${user.id}">${user.username}</a></td>
            <td>${user.nickname}</td>
            <td>${user.linkman}</td>
            <td>${user.mobile}</td>
            <td>${user.province}</td>
            <td>${user.city}</td>
			<td>${user.authed?"已审核":"未审核"}</td>
            <shiro:hasPermission name="custom:unit:edit"><td>
                <a href="${ctx}/custom/edit?id=${user.id}">修改</a>
                <a data-href="${ctx}/custom/delete?id=${user.id}" onclick="layerConfirm('确认要删除该用户吗？', this.data-href)">删除</a>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/5
  Time: 16:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>激活码列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <script>
        $(function(){
            initSelect();

            $("a[role='menuitem']").click(function(){
                $("#dropdownUL>li").removeClass("active");
                $(this).parent().addClass("active");
                $("#used").val($(this).attr("data-value"));
                initSelect();
            });
        })

        function initSelect(){
            var text = $("#dropdownUL>li.active>a").text();
            $("#used").val($("#dropdownUL>li.active>a").attr("data-value"));
            $("#selectedValue").text(text);
        }


    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/custom/code/list">激活码列表</a></li>
    <shiro:hasPermission name="custom:code:edit"><li><a href="${ctx}/custom/code/edit">生成激活码</a></li></shiro:hasPermission>
</ul>
<form id="pageForm" name="pageForm" action="${ctx}/custom/code/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input type="hidden" name="code" value="${code}">
    <input type="hidden" name="used" value="${used}">
</form>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="searchForm" action="${ctx}/custom/code/list" method="post" class="breadcrumb form-search">

    <div class="dropdown">
        <input type="hidden" name="used" id="used" value="">
        <button type="button" class="btn dropdown-toggle" id="dropdownMenu1"
                data-toggle="dropdown"><span id="selectedValue">所有</span>&nbsp;<span class="caret"></span>
        </button>
        <ul class="dropdown-menu" id="dropdownUL" role="menu" aria-labelledby="dropdownMenu2">
            <li role="presentation" <c:if test="${used == null}">class="active"</c:if>>
                <a role="menuitem" tabindex="1" href="#" data-value="">所有</a>
            </li>
            <li role="presentation" <c:if test="${used == 0}">class="active"</c:if>>
                <a role="menuitem" tabindex="1" href="#" data-value="false">未使用</a>
            </li>
            <li <c:if test="${used == 1}">class="active"</c:if> role="presentation">
                <a role="menuitem" tabindex="2" href="#" data-value="true">已使用</a>
            </li>
        </ul>
        <input placeholder="请输入激活码搜索" value="${code}" size="40"  type="search" name="code" maxlength="50" class="required"/>
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>

    </div>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>激活码</th><th>状态</th><th>生成时间</th><th>激活时间</th><shiro:hasPermission name="custom:code:edit"><th>操作</th></shiro:hasPermission></tr></thead>
    <tbody>
    <c:forEach items="${page.datas}" var="code">
        <tr>
            <td>${code.code}</td>
            <td>
            <c:choose>
                <c:when test="${code.used}">
                    <a href="${ctx}/custom/code/view?id=${code.id}">已使用</a>
                </c:when>
                <c:otherwise>
                    未使用
                </c:otherwise>
            </c:choose>
            <td><fmt:formatDate value="${code.sendTime}" pattern="yyyy-MM-dd HH:mm"/></td>
            <td><fmt:formatDate value="${code.activeTime}" pattern="yyyy-MM-dd HH:mm"/></td>
            <td>
                <shiro:hasPermission name="custom:code:edit">
                <a href="${ctx}/custom/code/edit?id=${code.id}">修改</a>
                <a data-href="${ctx}/custom/code/delete?id=${user.id}" onclick="layerConfirm('确认要删除该用户吗？', this.data-href)">删除</a>
                </shiro:hasPermission>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>
</body>
</html>

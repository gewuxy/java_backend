<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/5
  Time: 14:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>行政区划</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <script>
        $(function(){
            initSelect();

            $("a[role='menuitem']").click(function(){
                $("#dropdownUL>li").removeClass("active");
                $(this).parent().addClass("active");
                $("#level").val($(this).attr("data-value"));
                initSelect();
            });
        })

        function initSelect(){
            var text = $("#dropdownUL>li.active>a").text();
            $("#level").val($("#dropdownUL>li.active>a").attr("data-value"));
            $("#selectedValue").text(text);
        }


    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/region/list">区划列表</a></li>
    <shiro:hasPermission name="sys:region:add"><li><a href="${ctx}/sys/region/edit">添加区划</a></li></shiro:hasPermission>
</ul>
<form id="pageForm" name="pageForm" action="${ctx}/sys/region/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input type="hidden" name="keyword" value="${keyword}">
    <input type="hidden" name="level" value="${level}">
</form>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="searchForm" action="${ctx}/sys/region/list" method="post" class="breadcrumb form-search">

    <div class="dropdown">
        <input type="hidden" name="level" id="level" value="">
        <button type="button" class="btn dropdown-toggle" id="dropdownMenu1"
                data-toggle="dropdown"><span id="selectedValue">所有</span>&nbsp;<span class="caret"></span>
        </button>
        <ul class="dropdown-menu" id="dropdownUL" role="menu" aria-labelledby="dropdownMenu2">
            <li role="presentation" <c:if test="${level == null}">class="active"</c:if>>
                <a role="menuitem" tabindex="1" href="#" data-value="">所有</a>
            </li>
            <li role="presentation" <c:if test="${level == 1}">class="active"</c:if>>
                <a role="menuitem" tabindex="1" href="#" data-value="1">省/直辖市</a>
            </li>
            <li <c:if test="${level == 2}">class="active"</c:if> role="presentation">
                <a role="menuitem" tabindex="2" href="#" data-value="2">地区/市</a>
            </li>
            <li role="presentation" <c:if test="${level == 3}">class="active"</c:if>>
                <a role="menuitem" tabindex="3" href="#" data-value="3">区/县</a>
            </li>
        </ul>
        <input placeholder="区划编号/名称/拼音/首字母" value="${keyword}" size="40"  type="search" name="keyword" maxlength="50" class="required"/>
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>

    </div>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>编号</th><th>名称</th><th>拼音</th><th>级别</th><shiro:hasPermission name="sys:region:edit"><th>操作</th></shiro:hasPermission></tr></thead>
    <tbody>
    <c:forEach items="${page.datas}" var="region">
        <tr>
            <td>${region.id}</td>
            <td>${region.name}</td>
            <td>${region.spell}</td>
            <td>${region.regionType}</td>
            <shiro:hasPermission name="sys:region:edit"><td>
                <a href="${ctx}/sys/region/edit?id=${region.id}">修改</a>
                <a data-href="${ctx}/sys/region/delete?id=${region.id}" href="#" onclick="layerConfirm('确认要删除该区划及所有子区划吗？', this.data-href)">删除</a>
                <c:if test="${region.level < 3}" ><a href="${ctx}/sys/region/edit?preid=${region.id}">增加子区划</a></c:if>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>
</body>
</html>

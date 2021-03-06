<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/5
  Time: 16:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>菜单列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link href="${ctxStatic}/treeTable/themes/vsStyle/treeTable.min.css" rel="stylesheet" type="text/css" />
    <script src="${ctxStatic}/treeTable/jquery.treeTable.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#treeTable").treeTable({expandLevel : 3}).show();
        });

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/custom/menu/list">菜单列表</a></li>
    <shiro:hasPermission name="custom:menu:edit"><li><a href="${ctx}/custom/menu/edit">添加菜单</a></li></shiro:hasPermission>
</ul>
<form id="pageForm" name="pageForm" action="${ctx}/custom/code/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input type="hidden" name="code" value="${code}">
    <input type="hidden" name="used" value="${used}">
</form>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="listForm" method="post">
    <table id="treeTable" class="table table-striped table-bordered table-condensed hide">
        <thead><tr><th>名称</th><th>链接</th><th style="text-align:center;">排序</th><th>可见</th><th>权限标识</th><shiro:hasPermission name="sys:menu:edit"><th>操作</th></shiro:hasPermission></tr></thead>
        <tbody>
        <c:forEach items="${list}" var="menu">
            <tr id="${menu.id}" pId="${menu.preid}">
                <td nowrap><i></i><c:if test="${menu.icon != null}"><img src="${ctxStatic}/images/icon/${menu.icon}"/></c:if> <a href="${ctx}/custom/menu/edit?id=${menu.id}">${menu.menuName}</a></td>
                <td title="${menu.href}">${menu.href}</td>
                <td style="text-align:center;">

                    <input type="hidden" name="ids" value="${menu.id}"/>
                    <input name="sorts" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">

                </td>
                <td>${menu.hide == false?'显示':'隐藏'}</td>
                <td title="${menu.permission}">${menu.permission}</td>
                <td nowrap>
                    <a href="${ctx}/custom/menu/edit?id=${menu.id}">修改</a>
                    <!--<a data-href="${ctx}/custom/menu/delete?id=${menu.id}" style="cursor: pointer;" onclick="layerConfirm('要删除该菜单及所有子菜单项吗？', this.data-href)">删除</a>-->
                    <a href="${ctx}/custom/menu/edit?preid=${menu.id}">添加子菜单</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</form>
</body>
</html>

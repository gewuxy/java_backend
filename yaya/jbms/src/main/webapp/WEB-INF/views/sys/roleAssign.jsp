<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/3
  Time: 17:26
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色权限分配</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css"/>
    <script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/sys/role/list">角色列表</a></li>
    <shiro:hasPermission name="sys:role:add">
        <li><a href="${ctx}/sys/role/edit">添加角色</a></li>
    </shiro:hasPermission>
    <li class="active"><a>权限分配</a></li>
</ul>
<form action="${ctx}/sys/role/assign" method="POST" id="assignForm" name="assignForm">
    <input type="hidden" name="roleId" value="${roleId}">
    <div id="roleMenuTree" class="ztree" style="margin:15px;">
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="sys:role:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>


</body>
<script>
$(function(){
    var treeSetting = {
        view:{
            selectedMulti:false,dblClickExpand:false,nameIsHTML:false,
            showIcon:true,showTitle:true
        },
        data:{simpleData:{enable:true}},
        check:{
            enable: true,
            nocheckInherit: false,
            chkboxType: { "Y" : "ps", "N" : "ps" }
        }
    };
    var treeNodes=[
        {id:"0", pId:"0", name:"权限分配",checked:true, nocheck:true},
        <c:forEach items="${menuList}" var="menu">
            {id:"${menu.id}", pId:"${not empty menu.preid ? menu.preid : 0}", name:"${not empty menu.preid ? menu.name : ''}", nocheck:false, checked:"${roleMap[menu.id]}"},
        </c:forEach>
    ];
    var menuTreeselect = $.fn.zTree.init($("#roleMenuTree"), treeSetting, treeNodes);
    menuTreeselect.expandAll(true);

    $("#btnSubmit").click(function(){
        var checkedNodes = menuTreeselect.getCheckedNodes();
        var tip = layer.msg('正在提交，请稍等...',{
            icon: 16,
            shade: 0.01
        });
        for(var i in checkedNodes){
            if(checkedNodes[i].id != 0){
                $("#assignForm").append('<input type="hidden" name="menuIds" value="'+checkedNodes[i].id+'"/>');
            }
        }

        $("#assignForm").submit();
    });
});
</script>
</html>

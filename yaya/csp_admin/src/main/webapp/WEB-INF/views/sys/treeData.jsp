<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/3
  Time: 15:13
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include/page_context.jsp"%>
<link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css"/>
<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<div id="menuTreeselect" class="ztree" style="margin:15px;"></div>
<script type="text/javascript">
    var menuTreeselectSetting = {view:{selectedMulti:false,dblClickExpand:false,nameIsHTML:false,showIcon:true,showTitle:true}, data:{simpleData:{enable:true}},
        callback:{onClick:function(event, treeId, treeNode){
            menuTreeselect.expandNode(treeNode);
            if (!treeNode.isParent){
                cookie('menuId', treeNode.id, {path:'/'});
            }},
        onDblClick:function(event, treeId, treeNode){
            var preid = treeNode.id;
            var preName = treeNode.name;
            parent.setPreName(preName);
            parent.setPreId(preid);
            parent.closeIframe();
        }
    }};
    var menuTreeselectNodes=[
        {id:"0", pId:"0", name:"顶级菜单"},
            <c:forEach items="${menuList}" var="menu">
            <c:if test="${menu.hide == false}">{id:"${menu.id}", pId:"${not empty menu.preid ? menu.preid : 0}", name:"${not empty menu.preid ? menu.name : ''}"},
            </c:if>
            </c:forEach>
    ];
    var menuTreeselect = $.fn.zTree.init($("#menuTreeselect"), menuTreeselectSetting, menuTreeselectNodes);
    <c:if test="${empty parentId}">
    var menuTreeselectNodes = menuTreeselect.getNodesByParam("level", 1);
    for(var i=0; i<menuTreeselectNodes.length; i++) {
        menuTreeselect.expandNode(menuTreeselectNodes[i], true, false, false);
    }
    </c:if>
    var menuTreeselectNode = menuTreeselect.getNodeByParam("id", '${parentId != '' ? parentId : cookie.menuId.value}');
    menuTreeselect.selectNode(menuTreeselectNode, true);
    menuTreeselect.expandNode(menuTreeselectNode, true, false, false);
</script>

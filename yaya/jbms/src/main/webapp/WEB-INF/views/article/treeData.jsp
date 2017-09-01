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
<div id="categoryTree" class="ztree" style="margin:15px;"></div>
<script type="text/javascript">
    var setting = {view:{selectedMulti:false,dblClickExpand:false,nameIsHTML:false,showIcon:true,showTitle:true}, data:{simpleData:{enable:true}},
        callback:{onClick:function(event, treeId, treeNode){
            treeselect.expandNode(treeNode);
        },
        onDblClick:function(event, treeId, treeNode){
            var preid = treeNode.id;
            var preName = treeNode.name;
            parent.setPreName(preName);
            parent.setPreId(preid);
            parent.closeIframe();
        }
    }};
    var treeselectNodes=[
        {id:"0", pId:"0", name:"顶级栏目"},
            <c:forEach items="${list}" var="category">
            {id:"${category.id}", pId:"${not empty category.preId ? category.preId : 0}", name:"${not empty category.preId ? category.name : ''}"},
            </c:forEach>
    ];
    var treeselect = $.fn.zTree.init($("#categoryTree"), setting, treeselectNodes);
    <c:if test="${not empty preId}">
    var categoryTreeselectNodes = treeselect.getNodesByParam("level", 1);
    for(var i=0; i<menuTreeselectNodes.length; i++) {
        treeselect.expandNode(categoryTreeselectNodes[i], true, false, false);
    }
    </c:if>
    treeselect.expandAll(true);
</script>

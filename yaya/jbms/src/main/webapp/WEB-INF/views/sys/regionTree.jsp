<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/8
  Time: 10:24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>选择父级区划</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css"/>
    <script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
    <div id="regionTree" class="ztree" style="margin:15px;"></div>
    <script type="text/javascript">
        var setting = {view:{selectedMulti:false,dblClickExpand:false,nameIsHTML:false,showIcon:true,showTitle:true}, data:{simpleData:{enable:true}},
            callback:{onClick:function(event, treeId, treeNode){
                regionTreeselect.expandNode(treeNode);
                if (!treeNode.isParent){
                    cookie('regionId', treeNode.id, {path:'/'});
                }},
                onDblClick:function(event, treeId, treeNode){
                    var preid = treeNode.id;
                    var preName = treeNode.name;
                    parent.setPreName(preName);
                    parent.setPreId(preid);
                    parent.closeIframe();
                }
            }};
        var regionTreeselectNodes=[
            {id:"0", pId:"0", name:"中国"},
            <c:forEach items="${regions}" var="region">
                {id:"${region.id}", pId:"${region.preId}", name:"${region.name}", isParent:true},
            </c:forEach>
        ];
        var regionTreeselect = $.fn.zTree.init($("#regionTree"), setting, regionTreeselectNodes);
        <c:if test="${empty preid}">
        var regionTreeselectNodes = regionTreeselect.getNodesByParam("level", 0);
        for(var i=0; i<regionTreeselectNodes.length; i++) {
            regionTreeselect.expandNode(regionTreeselectNodes[i], true, false, false);
        }
        </c:if>

    </script>
</head>
<body>

</body>
</html>

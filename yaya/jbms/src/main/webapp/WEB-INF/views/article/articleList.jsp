<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/8
  Time: 12:19
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文章列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css"/>
    <script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>

</head>
<body>

<div id="main">
<div class="container-fluid">
    <div id="content" class="row-fluid">
        <div id="left" style="width: 180px; overflow: auto;">
            <div id="categoryTree" class="ztree" style="margin:10px;"></div>
        </div>
        <div id="openClose" class="close">&nbsp;</div>
        <div id="right">
            <iframe id="articleFrame" name="articleFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no"
                    width="100%"></iframe>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
    var setting = {
        view: {selectedMulti: false, dblClickExpand: false, nameIsHTML: false, showIcon: true, showTitle: true},
        data: {simpleData: {enable: true}},
        callback: {
        },
        async: { //动态加载
            enable: true,
            url:"${ctx}/article/category/treeData", //加载ztree
            autoParam:["id"],
            dataFilter: filter
        }
    };

    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        var retNodes = [];
        for (var i in childNodes.data) {
            var node = {};
            node.id=childNodes.data[i].id;
            node.name = childNodes.data[i].name;
            node.pId=childNodes.data[i].preId;
            node.isParent=!childNodes.data[i].leaf;
            if(!node.isParent){
                node.url="${ctx}/article/page?categoryId="+node.id;
                node.target="articleFrame";
            }
            retNodes.push(node);
        }
        return retNodes;
    }

    var treeSelectNodes = [
        {id: "0", preId: "0", name: "顶级栏目"},
        <c:forEach items="${list}" var="category">
        {
            id: "${category.id}",
            pId: "${not empty category.preId ? category.preId : 0}",
            name: "${not empty category.preId ? category.name : ''}",
            isParent:true
        },
        </c:forEach>
    ];
    var categoryTree = $.fn.zTree.init($("#categoryTree"), setting, treeSelectNodes);
    var rootNode = categoryTree.getNodeByParam("id", "0");
    categoryTree.selectNode(rootNode);
    categoryTree.expandNode(rootNode, true, false, false);

    var nodes;
    var freshtype="refresh";
    var isSlient= false;

    function refreshParentNode(isAdd) {
        nodes = categoryTree.getSelectedNodes();
        var parentNode;
        if(isAdd){
            parentNode = nodes[0];
        }else{
            parentNode = categoryTree.getNodeByTId(nodes[0].parentTId);
        }
        categoryTree.selectNode(parentNode);
        categoryTree.reAsyncChildNodes(parentNode, freshtype, isSlient);
    }

    function resizeWin(){
        var ph = $(top.window).height();
        var ww = $(top.window).width()-410;
        var wh = (ph-120)+"px";
        $("#left").css("height",wh);
        $("#right").height("height",wh);
        $("#articleFrame").attr("height", wh);
        $("#right").css("width",ww+"px");
        $("#openClose").css("height", wh);
    }

    $(function(){
        resizeWin();
        $(window).resize(function(){
            resizeWin();
        });
    });
</script>
</body>

</html>

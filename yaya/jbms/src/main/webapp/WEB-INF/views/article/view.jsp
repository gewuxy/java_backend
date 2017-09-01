<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/11
  Time: 14:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文章预览</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/article/page?categoryId=${article.categoryId}">文章列表</a></li>
    <shiro:hasPermission name="article:edit"><li><a href="${ctx}/article/edit?categoryId=${article.categoryId}">添加文章</a></li></shiro:hasPermission>
    <li class="active"><a href="${ctx}/article/view?id=${article.id}">文章预览</a></li>
</ul>
<div id="articleViewer"  style="margin:10px;text-align: center;">
<div><h3>${article.title}</h3></div>
    <div><fmt:formatDate value="${article.createTime}" pattern="yyyy/MM/dd"/>    来源：${article.xfrom}    作者：${article.author}</div>
    <div>
        <img src="http://www.medcn.cn${article.articleImg}">
    </div>
    <div style="margin:10px;text-align: left; font-size:15px;">
        ${article.content}
    </div>
</div>
<div class="form-actions">
    <shiro:hasPermission name="article:auth">
        <input id="btnSubmit" class="btn btn-primary" type="button" value="${article.authed?'取消审核':'审  核'}"/>&nbsp;
    </shiro:hasPermission>
    <shiro:hasPermission name="article:del">
        <input id="btnCancel" class="btn" type="button" value="删 除" onclick="layerConfirm('确定要删除此内容吗?',this)" data-href="${ctx}/article/category/delete?id=${category.id}&preId=${parent.id}"/>
    </shiro:hasPermission>
</div>
<script>
    $(function(){
       var ww = $(window).width();
       if(ww>1024){
           ww = 1024;
       }
       $("#articleViewer").css("width",ww+"px");
    });
</script>
</body>
</html>

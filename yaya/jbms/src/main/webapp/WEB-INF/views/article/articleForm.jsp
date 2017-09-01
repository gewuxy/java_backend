<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/2
  Time: 15:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>编辑文章</title>
    <link href="${ctxStatic}/umeditor1.2.3/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="${ctxStatic}/umeditor1.2.3/third-party/template.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/umeditor1.2.3/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/umeditor1.2.3/umeditor.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/umeditor1.2.3/lang/zh-cn/zh-cn.js"></script>
</head>
<body >
<ul class="nav nav-tabs">
    <li><a href="${ctx}/article/page?categoryId=${categoryId}">文章列表</a></li>
    <shiro:hasPermission name="article:edit"><li  class="active"><a href="${ctx}/article/edit?categoryId=${categoryId}">${article == null?'添加':'修改'}文章</a></li></shiro:hasPermission>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="inputForm" action="${ctx}/article/save" method="post" class="form-horizontal">
    <input type="hidden" name="id" value="${article.id}">
    <div class="control-group">
        <label class="control-label">所属栏目:</label>
        <div class="controls">
            <div class="input-append">
                <input type="hidden" name="categoryId" value="${categoryId}">
                <input id="categoryName" name="categoryName" readonly type="search" value="${category.name}"/>
            </div>

        </div>
    </div>

    <div class="control-group">
        <label class="control-label">标题:</label>
        <div class="controls">
            <input type="search" name="title" value="${article.title}" maxlength="50" class="required input-xxlarge"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">关键字:</label>
        <div class="controls">
            <input type="search" name="keywords" value="${article.keywords}" maxlength="50" class="input-xxlarge"/>
            <span class="help-inline"> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">来源:</label>
        <div class="controls">
            <input type="search" name="xfrom" value="${article.xfrom}" maxlength="50" class="input-xlarge"/>
            <span class="help-inline"> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">作者:</label>
        <div class="controls">
            <input type="search" name="author" value="${article.author}" maxlength="50" class="input-xlarge"/>
            <span class="help-inline"> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">权重(数字):</label>
        <div class="controls">
            <input type="search" name="weight" value="${article.weight}" maxlength="5" class="input-xlarge"/>
            <span class="help-inline"> 权重影响文章在列表中的排序，权重越大越靠前 </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">外链:</label>
        <div class="controls">
            <input type="search" id="outLink" name="outLink" class="input-xxlarge"/>
            <span class="help-inline"> 外部链接需要写绝对路径, 如果本文章不是外部链接请勿填写</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">图片:</label>
        <div class="controls">
            <input type="hidden" id="articleImg" name="articleImg" value="${article.articleImg}">
            <input type="file" name="file" class="input-xxlarge" maxlength="200"/>
            <span class="help-inline">
                ${not empty article.articleImg?"已上传":"未上传"}

            </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">摘要:</label>
        <div class="controls">
            <textarea name="summary" class="input-xxlarge" maxlength="200">${article.summary}</textarea>
            <span class="help-inline"> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">内容:</label>
        <div class="controls">
            <script type="text/plain" id="content" name="content" style="width:98%;height:240px;">
                ${article.content}
            </script>
            <span class="help-inline"> </span>
        </div>
    </div>

    <div class="form-actions">
        <shiro:hasPermission name="article:category:edit">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        </shiro:hasPermission>
        <shiro:hasPermission name="article:category:edit">
            <c:if test="${category.id != null}">
                <input id="btnCancel" class="btn" type="button" value="删 除" onclick="layerConfirm('确定要删除此栏目吗?',this)" data-href="${ctx}/article/category/delete?id=${category.id}&preId=${parent.id}"/>
            </c:if>
        </shiro:hasPermission>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function() {
        var parentHeight = $(top.window).height();
        var bodyHeight = $("body").height();
        if(bodyHeight < parentHeight - 120){
            $("body").height(parentHeight - 120);
        }
        $("#name").focus();
        $("#inputForm").validate({
            submitHandler: function(form){
                layer.msg('正在提交，请稍等...',{
                    icon: 16,
                    shade: 0.01
                });
                form.submit();
            },
            errorContainer: "#messageBox",
            errorPlacement: function(error, element) {
                $("#messageBox").text("输入有误，请先更正。");
                if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                    error.appendTo(element.parent().parent());
                } else {
                    error.insertAfter(element);
                }
            }
        });

        $("#showImgBtn").click(function(){
            top.layer.open({
                type: 1,
                title: false,
                closeBtn: 0,
                area: '516px',
                skin: 'layui-layer-nobg', //没有背景色
                shadeClose: true,
                content: $('#hideImg')
            });
        });
    });

    var um = UM.getEditor('content');

</script>
</body>
</html>

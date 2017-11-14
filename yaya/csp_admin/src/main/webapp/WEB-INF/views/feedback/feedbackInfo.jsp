<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <title>公告菜单</title>
    <script type="text/javascript">
        $(function(){
            var ww = $(window).width();
            if(ww>1024){
                ww = 1024;
            }
            $("#articleViewer").css("width",ww+"px");
        });
        
        $(function () {
            var checkObj= ${article.authed};
            if(checkObj == 1){
                $("#checked").attr("checked","checked")
                $("#unchecked").attr("disabled","disabled")
            }else {
                $("#unchecked").attr("checked","checked")
                $("#checked").attr("disabled","disabled")
            }
        })
    </script>
</head>
<script type="text/javascript" src="${ctxStatic}/ckeditor/ckeditor.js"></script>
<body>
<ul class="nav nav-tabs">
    <li class="active">服务详情</li>
</ul>
<form id="inputForm" method="post" class="form-horizontal">
    <input type="hidden" name="userId" value="${article.id}">
    <div class="control-group">
        <label class="control-label">文章标题:</label>
        <div class="controls">
            <div class="input-append">
                <input readonly id="title" name="titleCn" type="text" value="${article.titleCn}">
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">文章标题(台湾):</label>
        <div class="controls">
            <div class="input-append">
                <input readonly id="titleTw" name="titleTw" type="text" value="${article.titleTw}">
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">Title(US):</label>
        <div class="controls">
            <div class="input-append">
                <input readonly id="titleUs" name="titleUs" type="text" value="${article.titleUs}">
            </div>
        </div>
    </div>
    <div id="articleViewer"  style="margin:10px;text-align: center;">
            <label class="control-label">文章内容:</label>
            <div class="controls">
                <textarea id="contentCn" name="contentCn">${article.contentCn}</textarea>
                    <script type="text/javascript">
                    CKEDITOR.replace('contentCn');
                    $(document).ready(function() {
                        CKEDITOR.config.readOnly = true;
                    });
                </script>
            </div>
    </div>
    <div id="articleViewer"  style="margin:10px;text-align: center;">
        <label class="control-label">文章内容(台湾):</label>
        <div class="controls">
            <textarea id="contentTw" name="contentTw">${article.contentTw}</textarea>
            <script type="text/javascript">
                CKEDITOR.replace('contentTw');
                $(document).ready(function() {
                    CKEDITOR.config.readOnly = true;
                });
            </script>
        </div>
    </div>
    <div id="articleViewer"  style="margin:10px;text-align: center;">
        <label class="control-label">TitleContent:</label>
        <div class="controls">
            <textarea id="contentUs" name="contentUs">${article.contentUs}</textarea>
            <script type="text/javascript">
                CKEDITOR.replace('contentUs');
                $(document).ready(function() {
                    CKEDITOR.config.readOnly = true;
                });
            </script>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">文章图片:</label>
        <div class="controls">
           <%-- <img src="${ctx}/${article.imgUrl}" width="100" height="100">--%>
            <input readonly type="text" name="file" id="imgUrl" value="${article.imgUrl}" style="width: 300px"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否审核:</label>
        <div class="controls">
                <input id="checked" type="radio" value="1">已审核
                <input id="unchecked" type="radio" value="0">未审核
        </div>
    </div>
    <div class="form-actions">
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>

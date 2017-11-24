<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>编辑服务</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            var active = '${listType}';
            $(".nav-tabs li:eq(" + active + ")").addClass("active");
        });

        $(function () {
            var ww = $(window).width();
            if (ww > 1024) {
                ww = 1024;
            }
            $("#articleViewer").css("width", ww + "px");
        });

        $(function () {
            var checkObj = ${article.authed};
            if (checkObj == 1) {
                $("#checked").attr("checked", "checked")
            } else {
                $("#unchecked").attr("checked", "checked")
            }
        })
    </script>
</head>
<script type="text/javascript" src="${ctxStatic}/ckeditor/ckeditor.js"></script>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/csp/article/edit?id=${article.id}&listType=0">服务菜单(CN)</a></li>
    <li><a href="${ctx}/csp/article/edit?id=${article.id}&listType=1">服務菜單（TW）</a></li>
    <li><a href="${ctx}/csp/article/edit?id=${article.id}&listType=2">Service menu(EN)</a></li>
</ul>
<form id="inputForm" method="post" class="form-horizontal" action="${ctx}/csp/article/update"
      enctype="multipart/form-data">
    <input type="hidden" name="id" value="${article.id}">
    <input type="hidden" name="listType" value="${listType}">
    <c:if test="${listType == 0 || listType == null}">
        <div class="control-group">
            <label class="control-label">文章标题:</label>
            <div class="controls">
                <div class="input-append">
                    <input id="title" name="titleCn" type="text" value="${article.titleCn}">
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${listType == 1}">
        <div class="control-group">
            <label class="control-label">文章標題:</label>
            <div class="controls">
                <div class="input-append">
                    <input id="titleTw" name="titleTw" type="text" value="${article.titleTw}">
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${listType == 2}">
        <div class="control-group">
            <label class="control-label">Title(US):</label>
            <div class="controls">
                <div class="input-append">
                    <input id="titleUs" name="titleUs" type="text" value="${article.titleUs}">
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${listType == 0 || listType == null}">
        <div id="articleViewer" style="margin:10px;text-align: center;">
            <label class="control-label">文章内容:</label>
            <div class="controls">
                <textarea id="contentCn" name="contentCn">${article.contentCn}</textarea>
                <script type="text/javascript">
                    CKEDITOR.replace('contentCn');
                </script>
            </div>
        </div>
    </c:if>
    <c:if test="${listType == 1}">
        <div id="articleViewer" style="margin:10px;text-align: center;">
            <label class="control-label">文章内容:</label>
            <div class="controls">
                <textarea id="contentTw" name="contentTw">${article.contentTw}</textarea>
                <script type="text/javascript">
                    CKEDITOR.replace('contentTw');
                </script>
            </div>
        </div>
    </c:if>
    <c:if test="${listType == 2}">
        <div id="articleViewer" style="margin:10px;text-align: center;">
            <label class="control-label">TitleContent:</label>
            <div class="controls">
                <textarea id="contentUs" name="contentUs">${article.contentUs}</textarea>
                <script type="text/javascript">
                    CKEDITOR.replace('contentUs');
                </script>
            </div>
        </div>
    </c:if>
    <div class="control-group">
        <label class="control-label">图片路径:</label>
        <div class="controls">
            <input readonly type="text" name="imgUrl" value="${article.imgUrl}" size="100" style="width: 300px">
            <input type="file" name="picture">
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">是否审核:</label>
        <div class="controls">
            <input id="checked" type="radio" value="1" name="authed">已审核
            <input id="unchecked" type="radio" value="0" name="authed">未审核
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit"
                                                            value="修 改"/>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>
<%--






--%>
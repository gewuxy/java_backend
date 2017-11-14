<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>编辑文章</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <script type="text/javascript">
      /*  function checkTitle() {
            if ($("#titleCn").val() == null || $("#titleCn").val() == ""||$("#titleTw").val() == null || $("#titleTw").val() == ""
            ||$("#titleUs").val() == null || $("#titleUs").val() == "") {
                confirm("标题不能为空")
                $("#btnSubmit").attr("disabled", "disabled");
            } else {
                $("#btnSubmit").removeAttr("disabled");
            }

        }

        function checkContent() {
            if ($("#contentCn").val() == null || $("#contentCn").val() == ""||$("#contentTw").val() == null || $("#contentTw").val() == ""
            ||$("#contentUs").val() == null || $("#contentUs").val() == "") {
                confirm("内容输入有误")
                $("#btnSubmit").attr("disabled", "disabled");
            } else {
                $("#btnSubmit").removeAttr("disabled");
            }
        }

        $(document).ready(function () {
            $("#btnSubmit").click(function () {
                var checkList = $("input:text");
                var isCheckPass = true;
                for (var i = 0; i < checkList.size(); i++) {
                    if ($.trim(checkList[i].value) == "") {
                        checkList[i].value = "请不要输入空格!";
                        isCheckPass = false;
                        $("#btnSubmit").attr("disabled", "disabled");
                    } else {
                        $("#btnSubmit").removeAttr("disabled");
                    }
                }
                return isCheckPass;
            })
        })*/

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
    <li class="active">服务详情</li>
</ul>
<form id="inputForm" method="post" class="form-horizontal" action="${ctx}/csp/feedback/update" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${article.id}">
    <div class="control-group">
        <label class="control-label">文章标题:</label>
        <div class="controls">
            <div class="input-append">
                <input id="title" name="titleCn" type="text" value="${article.titleCn}">
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">文章标题(台湾):</label>
        <div class="controls">
            <div class="input-append">
                <input  id="titleTw" name="titleTw" type="text" value="${article.titleTw}">
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">Title(US):</label>
        <div class="controls">
            <div class="input-append">
                <input  id="titleUs" name="titleUs" type="text" value="${article.titleUs}">
            </div>
        </div>
    </div>
  <div id="articleViewer" style="margin:10px;text-align: center;">
        <label class="control-label">文章内容:</label>
        <div class="controls">
                <textarea id="contentCn" name="contentCn">${article.contentCn}</textarea>
                <script type="text/javascript">
                    CKEDITOR.replace('contentCn');
                </script>
       </div>
    </div>
    <div id="articleViewer"  style="margin:10px;text-align: center;">
        <label class="control-label">文章内容(台湾):</label>
        <div class="controls">
            <textarea id="contentTw" name="contentTw">${article.contentTw}</textarea>
            <script type="text/javascript">
                CKEDITOR.replace('contentTw');
            </script>
        </div>
    </div>
    <div id="articleViewer"  style="margin:10px;text-align: center;">
        <label class="control-label">TitleContent:</label>
        <div class="controls">
            <textarea id="contentUs" name="contentUs">${article.contentUs}</textarea>
            <script type="text/javascript">
                CKEDITOR.replace('contentUs');
            </script>
        </div>
    </div>
   <div class="control-group">
        <label class="control-label">文章图片:</label>
        <div class="controls">
            <input readonly type="text" name="imgUrl" value="${article.imgUrl}" size="100" style="width: 300px">
           <%-- <img src="${ctx}/pic/${article.imgUrl}" width="100" height="100">--%>
            <input type="file" name="picture">
        </div>
        <div>

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
        <shiro:hasPermission name="sys:message:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                            value="修 改"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>
<%--






--%>
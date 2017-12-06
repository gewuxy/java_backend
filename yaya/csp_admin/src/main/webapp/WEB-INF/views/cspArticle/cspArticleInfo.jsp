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
        $(document).ready(function() {
            initFormValidate();
        });

        $(document).ready(function () {
            var active = '${listType}';
            $(".nav-tabs li:eq(" + active + ")").addClass("active");
        });

        $(function () {
            var checkObj = ${article.authed};
            if (checkObj == 1) {
                $("#checked").attr("checked", "checked")
                $("#unchecked").attr("disabled", "disabled")
            } else {
                $("#unchecked").attr("checked", "checked")
                $("#checked").attr("disabled", "disabled")
            }
        })
    </script>
</head>
<script type="text/javascript" src="${ctxStatic}/jquery-plugin/jquery-form.js"></script>
<script type="text/javascript" src="${ctxStatic}/layer/layui.all.js"></script>
<link rel="stylesheet" href="${ctxStatic}/layer/css/layui.css">
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/csp/article/check?id=${article.id}&listType=0">服务菜单(CN)</a></li>
    <li><a href="${ctx}/csp/article/check?id=${article.id}&listType=1">服務菜單（TW）</a></li>
    <li><a href="${ctx}/csp/article/check?id=${article.id}&listType=2">Service menu(EN)</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp" %>
<form id="inputForm" method="post" class="form-horizontal" enctype="multipart/form-data">
    <input type="hidden" name="userId" value="${article.id}">
    <input type="hidden" name="listType" value="${listType}">
    <c:if test="${listType == 0 || listType == null}">
        <div class="control-group">
            <label class="control-label">文章标题:</label>
            <div class="controls">
                <div class="input-append">
                    <input readonly id="title" name="titleCn" type="text" value="${article.titleCn}">
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${listType == 1}">
        <div class="control-group">
            <label class="control-label">文章標題:</label>
            <div class="controls">
                <div class="input-append">
                    <input readonly id="titleTw" name="titleTw" type="text" value="${article.titleTw}">
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${listType == 2}">
        <div class="control-group">
            <label class="control-label">Title:</label>
            <div class="controls">
                <div class="input-append">
                    <input readonly id="titleUs" name="titleUs" type="text" value="${article.titleUs}">
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${listType == 0 || listType == null}">
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">内容</label>
            <div class="layui-input-block">
                <textarea class="layui-textarea layui-hide" name="contentCn" lay-verify="content" id="contentCn">${article.contentCn}</textarea>
                <script type="text/javascript">
                    layui.use(['form', 'layedit', 'laydate'], function() {
                        var form = layui.form
                            , layer = layui.layer
                            , layedit = layui.layedit
                            , laydate = layui.laydate;
                        //创建一个编辑器
                        var editIndex = layedit.build('contentCn',{
                                height:400
                            }
                        );
                    });
                </script>
            </div>
        </div>
    </c:if>
<c:if test="${listType == 1}">

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">内容</label>
        <div class="layui-input-block">
            <textarea class="layui-textarea layui-hide" name="contentTw" lay-verify="content" id="contentTw">${article.contentTw}</textarea>
            <script type="text/javascript">
                layui.use(['form', 'layedit', 'laydate'], function() {
                    var form = layui.form
                        , layer = layui.layer
                        , layedit = layui.layedit
                        , laydate = layui.laydate;
                    //创建一个编辑器
                    var editIndex = layedit.build('contentTw',{
                            height:400
                        }
                    );
                });
            </script>
        </div>
    </div>
</c:if>
<c:if test="${listType == 2}">

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">TitleContent</label>
        <div class="layui-input-block">
            <textarea class="layui-textarea layui-hide" name="contentUs" lay-verify="content" id="contentUs">${article.contentUs}</textarea>
            <script type="text/javascript">
                layui.use(['form', 'layedit', 'laydate'], function() {
                    var form = layui.form
                        , layer = layui.layer
                        , layedit = layui.layedit
                        , laydate = layui.laydate;
                    //创建一个编辑器
                    var editIndex = layedit.build('contentUs',{
                            height:400
                        }
                    );
                });
            </script>
        </div>
    </div>
</c:if>
    <div class="control-group">
        <label class="control-label">图片:</label>
        <div class="controls">
            <img  src="${imgURL}" id="imgId" width="200" height="200">
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

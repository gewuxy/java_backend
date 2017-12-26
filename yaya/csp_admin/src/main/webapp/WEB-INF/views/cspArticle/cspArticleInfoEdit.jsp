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
        $(document).ready(function() {
            initFormValidate();
        });


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

        function selectFile() {
            $("#uploadFile").trigger("click");
        }

        function fileUpload() {
            var option = {
                url: "${ctx}/csp/article/upload",
                type: 'POST',
                dataType: 'json',
                clearForm: false,
                success: function (data)  //服务器成功响应处理函数
                {
                    if (data.code == 0){
                        layer.msg("上传成功");
                        $("#imgUrl").val(data.data.imgURL);
                        //$("#imgId").removeAttr("src");
                        $("#imgId").attr("src", data.data.src);
                    }else {
                        layer.msg(data.err);
                    }
                }
            };
            $("#inputForm").ajaxSubmit(option);
            return true;
        }
    </script>
</head>
<script type="text/javascript" src="${ctxStatic}/jquery-plugin/jquery-form.js"></script>
<script type="text/javascript" src="${ctxStatic}/layer/layui.all.js"></script>
<link rel="stylesheet" href="${ctxStatic}/layer/css/layui.css">
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
                    <input id="title" name="titleCn" type="search" value="${article.titleCn}">
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${listType == 1}">
        <div class="control-group">
            <label class="control-label">文章標題:</label>
            <div class="controls">
                <div class="input-append">
                    <input id="titleTw" name="titleTw" type="search" value="${article.titleTw}">
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${listType == 2}">
        <div class="control-group">
            <label class="control-label">Title(US):</label>
            <div class="controls">
                <div class="input-append">
                    <input id="titleUs" name="titleUs" type="search" value="${article.titleUs}">
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
                        layedit.set({
                            uploadImage: {
                                url: '${ctx}/csp/article/upload' //接口url
                                ,type: 'post' //默认post
                            }
                        });
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
                        layedit.set({
                            uploadImage: {
                                url: '${ctx}/csp/article/upload' //接口url
                                ,type: 'post' //默认post
                            }
                        });
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
            <label class="layui-form-label">内容</label>
            <div class="layui-input-block">
                <textarea class="layui-textarea layui-hide" name="contentUs" lay-verify="content" id="contentUs">${article.contentUs}</textarea>
                <script type="text/javascript">
                    layui.use(['form', 'layedit', 'laydate'], function() {
                        var form = layui.form
                            , layer = layui.layer
                            , layedit = layui.layedit
                            , laydate = layui.laydate;
                        layedit.set({
                            uploadImage: {
                                url: '${ctx}/csp/article/upload' //接口url
                                ,type: 'post' //默认post
                            }
                        });
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
        <label class="control-label">上传图片:</label>
        <div class="controls">
            <input type="file" name="file" id="uploadFile" style="display:none" multiple="multiple"
                   onchange="fileUpload()">
            <input class="btn-dr" type="button" value="上传文件" onclick="selectFile()">
            <input type="hidden" id="hiUpload" value="${saveFileName}" name="uploadFile">
            <input type="hidden" name="imgUrl" id="imgUrl" value="" maxlength="50" class="required input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">图片展示:</label>
        <div class="controls">
            <img  src="" id="imgId" width="200" height="200">
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
        <shiro:hasPermission name="csp:article:edit">
        <input id="btnSubmit" class="btn btn-primary" type="submit"
                                                            value="修 改"/>
        </shiro:hasPermission>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>
<%--






--%>
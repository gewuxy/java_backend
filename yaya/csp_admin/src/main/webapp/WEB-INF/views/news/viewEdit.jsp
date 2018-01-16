<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2018/1/16
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新闻详情</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>

    <script type="text/javascript" src="${ctxStatic}/jquery-plugin/jquery-form.js"></script>
    <script type="text/javascript" src="${ctxStatic}/layer/layui.all.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/layer/css/layui.css">

    <script>
        // 是否审核的选中状态
        $(function () {
            var checkObj = ${news.authed};
            if (checkObj == 1) {
                $("#checked").attr("checked", "checked")
            } else {
                $("#unchecked").attr("checked", "checked")
            }
        })

        // 上传图片
        function selectFile() {
            $("#uploadFile").trigger("click");
        }
        function fileUpload() {
            var option = {
                url: "${ctx}/website/news/upload",
                type: 'POST',
                dataType: 'json',
                clearForm: false,
                success: function (data){  //服务器成功响应处理函数
                    if (data.code == 0){
                        layer.msg("上传成功");
                        $("#imgUrl").val(data.data.imgURL);
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

<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">发布新闻</a></li>
    </ul>

    <form id="inputForm" method="post" class="form-horizontal" action="${ctx}/website/news/update"  enctype="multipart/form-data">
        <input type="hidden" name="id" value="${news.id}"/>

        <div class="control-group">
            <label class="control-label">新闻标题:</label>
            <div class="controls">
                <input type="search" name="title"  id="title" maxlength="50" class="required input-large" value="${news.title}" >
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">新闻类别:</label>
            <div class="controls">
                <select id="type" name="type" style="width: 100px;">
                    <option value=""/>
                    -- 请选择 --
                    <option value="${news.categoryId}"/>
                    ${news.categoryName}
                </select>
                <script>
                    document.getElementById("type").value="${news.categoryName}";
                </script>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">来源:</label>
            <div class="controls">
                <input type="search" name="xfrom" value="${news.xfrom}" maxlength="50" class="required input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">作者:</label>
            <div class="controls">
                <input type="search" name="author" value="${news.author}" maxlength="50" class="required input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">摘要:</label>
            <div class="controls">
                <textarea name="summary" rows="3" maxlength="2000" class="input-xxlarge">${news.summary}</textarea>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">关键字:</label>
            <div class="controls">
                <input type="search" name="author" value="${news.keywords}" maxlength="50" class="required input-xlarge">
            </div>
        </div>

        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">内容</label>
            <div class="layui-input-block">
                <textarea class="layui-textarea layui-hide" name="content" lay-verify="content" id="content">${news.content}</textarea>
                <script type="text/javascript">
                    layui.use(['form', 'layedit', 'laydate'], function() {
                        var form = layui.form
                            , layer = layui.layer
                            , layedit = layui.layedit
                            , laydate = layui.laydate;
                        layedit.set({
                            uploadImage: {
                                url: '${ctx}/website/news/upload' //接口url
                                ,type: 'post' //默认post
                            }
                        });
                        //创建一个编辑器
                        var editIndex = layedit.build('content',{
                                height:400
                            }
                        );
                    });
                </script>
            </div>
        </div>

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
                <input id="unchecked" type="radio" value="0" name="authed">未审核
                <input id="checked" type="radio" value="1" name="authed">已审核
            </div>
        </div>


        <div class="form-actions">
            <shiro:hasPermission name="website:news:auth">
                <input id="btnSubmit" class="btn btn-primary" type="submit"
                       value="修 改"/>
            </shiro:hasPermission>&nbsp;
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </div>
    </form>
</body>
</html>

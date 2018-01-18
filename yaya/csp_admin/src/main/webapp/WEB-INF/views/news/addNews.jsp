<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2018/1/16
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>发布新闻</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <script type="text/javascript" src="${ctxStatic}/jquery-plugin/jquery-form.js"></script>
    <script type="text/javascript" src="${ctxStatic}/layer/layui.all.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/layer/css/layui.css">

    <script>
        $(document).ready(function() {

            $("#inputForm").validate({
                submitHandler: function(form){
                    if (!checkIsSelected() || !checkRadioIsChecked()){
                        return false;
                    }
                    $("#errorTips").hide();
                    layer.msg('正在提交，请稍等...',{
                        icon: 16,
                        shade: 0.01
                    });
                    form.submit();
                }
            });
        });

        function checkIsSelected() {
            var selectValue = $("#category").val();
            if (selectValue == "0"){
                $("#errorTips").show();
                return false;
            } else {
                $("#errorTips").hide();
               return true;
            }
        }

        function checkRadioIsChecked() {
            var checked = $('input:radio[name="authed"]:checked').val();
            if (checked != null && checked!='') {
                $("#errorTips1").hide();
                return true;
            } else {
                $("#errorTips1").show();
                return false;
            }

        }

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
                        $("#simgUrl").val(data.data.smallImgUrl);
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
        <li><a href="${ctx}/website/news/list">新闻列表</a></li>
        <li class="active"><a href="#">发布新闻</a></li>
    </ul>

    <form id="inputForm" method="post" class="form-horizontal" action="${ctx}/website/news/add" enctype="multipart/form-data">
        <div class="control-group">
            <label class="control-label">新闻标题:</label>
            <div class="controls">
                <input type="search" name="title"  id="title" maxlength="100" class="required input-large" value="" >
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">新闻类别:</label>
            <div class="controls">
                <select id="category" name="categoryId" style="width: 200px;">
                    <option value="0"/> -- 请选择 --
                    <c:forEach items="${categoryList}" var="c">
                        <option value="${c.id}"/>${c.name}
                    </c:forEach>
                </select>
                <span id="errorTips" class="help-inline" style="display: none"><font color="red">*必选</font> </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">来源:</label>
            <div class="controls">
                <input type="search" name="xfrom" value="" maxlength="50" class="required input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">作者:</label>
            <div class="controls">
                <input type="search" name="author" value="" maxlength="50" class="required input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">摘要:</label>
            <div class="controls">
                <textarea name="summary" rows="5" maxlength="2000" class="input-xxlarge"></textarea>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">关键字:</label>
            <div class="controls">
                <input type="search" name="keywords" value="" maxlength="50" class="required input-xlarge">
            </div>
        </div>

        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">内容</label>
            <div class="layui-input-block">
                <textarea class="layui-textarea layui-hide" name="content" lay-verify="content" id="content"></textarea>
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
                <input type="hidden" name="articleImg" id="imgUrl" value="" maxlength="50" class="required input-xlarge">
                <input type="hidden" name="articleImgS" id="simgUrl" value="" maxlength="50" class="required input-xlarge">
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
                <input type="radio" value="0" name="authed" >未审核
                <input type="radio" value="1" name="authed" >已审核
                <span id="errorTips1" class="help-inline" style="display: none"><font color="red">*必选</font> </span>
            </div>
        </div>

        <div class="form-actions">
            <shiro:hasPermission name="website:news:add">
                <input id="btnSubmit" class="btn btn-primary" type="submit"
                       value="保 存"/>
            </shiro:hasPermission>&nbsp;
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </div>
    </form>


</body>
</html>

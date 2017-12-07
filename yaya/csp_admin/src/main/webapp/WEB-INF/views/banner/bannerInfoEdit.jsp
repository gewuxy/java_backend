<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: jianliang
  Date: 2017/11/23
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>公告菜单</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>

</head>
<script type="text/javascript" src="${ctxStatic}/jquery-plugin/jquery-form.js"></script>
<script type="text/javascript" src="${ctxStatic}/layer/layui.all.js"></script>
<link rel="stylesheet" href="${ctxStatic}/layer/css/layui.css">
<body>
<script type="text/javascript">
    function selectFile() {
        $("#uploadFile").trigger("click");
    }

    function fileUpload() {
        var option = {
            url: "${ctx}/yaya/banner/upload",
            type: 'POST',
            dataType: 'json',
            clearForm: false,
            success: function (data) {
                if (data.code == 0){
                    layer.msg("上传成功");
                    $("#imgUrl").val(data.data.urlPath);
                    /*$("#imgId").removeAttr("src");*/
                    $("#imgId").attr("src", data.data.saveFileName);
                }else {
                    layer.msg(data.err);
                }
            }
        };
        $("#inputForm").ajaxSubmit(option);
        return true;
    }
</script>
<ul class="nav nav-tabs">
    <li class="active"><a href="#">新增Banner</a></li>
</ul>
<form id="inputForm" method="post" class="form-horizontal" action="${ctx}/yaya/banner/update"
      enctype="multipart/form-data">
    <input type="hidden" name="id" value="${banner.id}"/>
    <div class="control-group">
        <label class="control-label">上传图片:</label>
        <div class="controls">
            <input type="file" name="file" id="uploadFile" style="display:none" multiple="multiple"
                   onchange="fileUpload()">
            <input class="btn-dr" type="button" value="上传文件" onclick="selectFile()">
            <input type="hidden" id="hiUpload" value="${saveFileName}" name="uploadFile">
            <input type="hidden" id="imgUrl" value="${banner.imageUrl}" name="imageUrl">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">图片展示:</label>
        <div class="controls">
            <img src="${absolutelyPath}" id="imgId" width="200" height="200">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">Banner权重:</label>
        <div class="controls">
            <input type="search" name="weight"  id="weight" maxlength="50" class="required input-large" value="${banner.weight}">
            <span class="help-inline">权重越大，排序越靠前</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">Banner类型:</label>
        <div class="controls">
            <select id="type" name="type" style="width: 100px;">
                <option value=""/>
                -- 请选择 --
                <option value="0"/>
                系统Banner
                <option value="1"/>
                用户Banner
            </select>
            <script>
                document.getElementById("type").value="${banner.type}";
            </script>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否有效:</label>
        <div class="controls">
            <select id="active" name="active" style="width: 100px;">
                <option value=""/>
                -- 请选择 --
                <option value="false"/>
                已关闭
                <option value="true"/>
                已开启
            </select>
            <script>
                document.getElementById("active").value="${banner.active}";
            </script>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用途:</label>
        <div class="controls">
            <select id="appId" name="appId" style="width: 100px;">
                <option value=""/>
                -- 请选择 --
                <option value="0"/>
                YAYA医师
                <option value="1"/>
                合理用药
            </select>
            <script>
                document.getElementById("appId").value="${banner.appId}";
            </script>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">所属公众号:</label>
        <div class="controls">
            <select id="pubUser" name="pubUserId" style="width: 20%;">
                <option value=""/>-- 请选择 --
                <option value="207668">敬信药草园</option>
            </select>
            <script>
                document.getElementById("pubUser").value="${banner.pubUserId}";
            </script>
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">内容</label>
        <div class="layui-input-block">
            <textarea class="layui-textarea layui-hide" name="content" lay-verify="content" id="content">${banner.content}</textarea>
            <script type="text/javascript">
                layui.use(['form', 'layedit', 'laydate'], function() {
                    var form = layui.form
                        , layer = layui.layer
                        , layedit = layui.layedit
                        , laydate = layui.laydate;
                    //上传图片,必须放在 创建一个编辑器前面
                    layedit.set({
                        uploadImage: {
                            url: '${ctx}/yaya/banner/upload' //接口url
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
    <div class="form-actions">
<shiro:hasPermission name="yaya:banner:edit">
        <input id="btnSubmit" class="btn btn-primary" type="submit"
               value="修 改"/>
</shiro:hasPermission>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>



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
    <script type="text/javascript">
        $(document).ready(function() {
            initFormValidate();
        });
        function selectFile() {
            $("#uploadFile").trigger("click");
        }

        function fileUpload() {
            var option = {
                url: "${ctx}/yaya/banner/upload",
                type: 'POST',
                datatype: 'json',
                clearForm: false,
                success: function (data) {
                    //alert(data.savePath);
                    alert("上传成功!");
                    $("#uploadValue").val("/medcn/upload/" + data)
                    $("#imgId").attr("src", "${ctx}/pic/" + data)

                },
                error: function (map) {
                    alert("页面请求失败！");
                }
            };
            $("#inputForm").ajaxSubmit(option);
            return true;
        }


    </script>
</head>
<script type="text/javascript" src="${ctxStatic}/jquery-plugin/jquery-form.js"></script>
<script type="text/javascript" src="${ctxStatic}/ckeditor/ckeditor.js"></script>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/yaya/banner/edit">新增Banner</a></li>
</ul>
<script></script>
<form  id="inputForm" method="post" class="form-horizontal" action="${ctx}/yaya/banner/insert"
      enctype="multipart/form-data">
    <input type="hidden" name="userId" value="${page.userId}">
    <div class="control-group">
        <label class="control-label">上传图片:</label>
        <div class="controls">
            <input type="file" name="uploadFile" id="uploadFile" style="display:none" multiple="multiple"
                   onchange="fileUpload()">
            <input class="btn-dr" type="button" value="上传文件" onclick="selectFile()">
            <input type="hidden" id="hiUpload" value="${saveFileName}" name="uploadFile">
            <input type="search" name="imageUrl" id="uploadValue" maxlength="50" class="required input-xlarge">

        </div>
    </div>
    <div class="control-group">
        <label class="control-label">图片展示:</label>
        <div class="controls">
            <img src="" id="imgId" width="200" height="200">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">Banner权重:</label>
        <div class="controls">
            <input type="search" name="weight" id="weight" maxlength="50" class="required input-large">
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
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否有效:</label>
        <div class="controls">
            <select id="active" name="active" style="width: 100px;">
                <option value=""/>
                -- 请选择 --
                <option value="0"/>
                已关闭
                <option value="1"/>
                已开启
            </select>
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
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">所属公众号:</label>
        <div class="controls">
            <select id="pubUser" name="pubUserId" style="width: 20%;">
                <option value=""/>-- 请选择 --
                <c:forEach items="${appUser}" var="appUser">
                    <option value="${appUser.id}">${appUser.nickname}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div id="articleViewer" style="margin:10px;text-align: center;">
        <label class="control-label">内容详情:</label>
        <div class="controls">
            <textarea id="content" name="content"></textarea>
            <script type="text/javascript">
                CKEDITOR.replace('content');
            </script>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="yaya:banner:add">
            <input id="btnSubmit" class="btn btn-primary" type="submit"
               value="发 布"/>
        </shiro:hasPermission>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>



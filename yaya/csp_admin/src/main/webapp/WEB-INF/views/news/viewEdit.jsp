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

    <%--引入kindeditor富文本编辑器样式及js--%>
    <%@include file="/WEB-INF/include/kindeditor.jsp" %>
    <script>

        // 是否审核的选中状态
        $(function () {
            $("#imgId").attr("src", "${smallImgUrl}");

            var checkObj = ${news.authed};
            if (checkObj == 1) {
                $("#checked").attr("checked", "checked");
            } else {
                $("#unchecked").attr("checked", "checked");
            }
        });

        // 上传图片
        function selectFile() {
            $("#uploadFile").trigger("click");
        }

        function fileUpload() {
            var option = {
                url: "${ctx}/website/news/upload/img",
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
            $("#inputForms").ajaxSubmit(option);
            return true;
        }
    </script>
</head>

<body>
    <ul class="nav nav-tabs">
        <li><a href="${ctx}/website/news/list">新闻列表</a></li>
        <li class="active"><a href="#">编辑新闻</a></li>
    </ul>

    <form id="inputForms" method="post" class="form-horizontal" action="${ctx}/website/news/update"  enctype="multipart/form-data">
        <input type="hidden" name="id" value="${news.id}"/>
        <input type="hidden" name="uploadimages" id="uploadimages" value="">

        <div class="control-group">
            <label class="control-label">新闻标题:</label>
            <div class="controls">
                <input type="search" name="title"  id="title" maxlength="50" class="required input-large" value="${news.title}" >
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">新闻类别:</label>
            <div class="controls">
                <select name="categoryId" style="width: 200px;">
                    <c:forEach items="${categoryList}" var="c">
                        <option value="${c.id}" ${news.categoryId == c.id ? 'selected':''}>${c.name}</option>
                    </c:forEach>
                </select>
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
                <textarea name="summary" rows="5" maxlength="2000" class="input-xxlarge">${news.summary}</textarea>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">关键字:</label>
            <div class="controls">
                <input type="search" name="keywords" value="${news.keywords}" maxlength="50" class="required input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">内容:</label>
            <div class="controls">
                <textarea id="kindeditor" name="content" cols="200" rows="10" style="width:800px;height:360px;visibility:hidden;">${news.content}</textarea>
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

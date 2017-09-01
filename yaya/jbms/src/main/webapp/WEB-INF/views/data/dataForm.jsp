<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/2
  Time: 15:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>数据菜单</title>
    <script src="${ctxStatic}/js/ajaxfileupload.js"></script>
</head>
<body style="overflow: hidden;">
<ul class="nav nav-tabs">
    <li><a href="${ctx}/data/file/page?categoryId=${categoryId}">数据列表</a></li>
    <shiro:hasPermission name="data:file:edit"><li  class="active"><a href="${ctx}/data/file/edit?categoryId=${categoryId}">添加数据</a></li></shiro:hasPermission>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="inputForm" action="${ctx}/data/file/save" method="post" class="form-horizontal">
    <input type="hidden" name="id" value="${dataFile.id}">
    <input type="hidden" name="categoryId" id="categoryId" value="${categoryId}">
    <input type="hidden" name="fileSize" id="fileSize" value="${dataFile.fileSize}">
    <div class="control-group">
        <label class="control-label">标题:</label>
        <div class="controls">
            <input type="search" name="title" value="${dataFile.title}" maxlength="50" class="required input-xlarge"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div><div class="control-group">
    <label class="control-label">类型:</label>
    <div class="controls">
        <select id="rootCategory" name="rootCategory">
            <c:forEach items="${categoryList}" var="rootCate">
                <option value="${rootCate.id}" ${dataFile.rootCategory == rootCate.id ?"selected":""}>${rootCate.name}</option>
            </c:forEach>
        </select>
        <span class="help-inline"></span>
    </div>
</div>
    <div class="control-group">
        <label class="control-label">作者:</label>
        <div class="controls">
            <input type="search" name="author" value="${dataFile.author}" maxlength="50" class="input-xlarge"/>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">来源:</label>
        <div class="controls">
            <input type="search" id="dataFrom" name="dataFrom" value="${dataFile.dataFrom}"  class="input-xlarge"/>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">源文件:</label>
        <div class="controls">
            <input type="search" name="filePath" id="filePath" class="required input-xlarge" readonly value="${dataFile.filePath}"/>
            <input type="file" name="file" id="file">
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">摘要:</label>
        <div class="controls">
            <textarea name="summary"  class="input-xxlarge">${dataFile.summary}</textarea>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">下载费用:</label>
        <div class="controls">
            <input type="search" id="downLoadCost" name="downLoadCost" value="${dataFile.downLoadCost}" class="input-small"/>
            <span class="help-inline">单位：象数 如果免费下载, 不要填写</span>
        </div>
    </div>

    <div class="form-actions">
        <shiro:hasPermission name="data:file:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        </shiro:hasPermission>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function() {
        var parentHeight = $(top.window).height();
        var bodyHeight = $("body").height();
        if(bodyHeight < parentHeight - 120){
            $("body").height(parentHeight - 120);
        }
        $("#name").focus();
        $("#inputForm").validate({
            submitHandler: function(form){
                layer.msg('正在提交，请稍等...',{
                    icon: 16,
                    shade: 0.01
                });
                form.submit();
            },
            errorContainer: "#messageBox",
            errorPlacement: function(error, element) {
                $("#messageBox").text("输入有误，请先更正。");
                if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                    error.appendTo(element.parent().parent());
                } else {
                    error.insertAfter(element);
                }
            }
        });

        $("#file").change(function(){
            var fileName = $(this).val();
            var prefixName = fileName.substring(fileName.lastIndexOf(".")+1);
            if(prefixName.toLocaleLowerCase() != 'pdf'){
                $("#messageSpan").text("文件格式不正确,请上传pdf文件");
                $("#messageBox").show();
                return;
            }
            upload(uploadCallback);
        });

    });

    /**
     * 异步文件上传
     * @param callback
     */
    function upload(callback){
        var rootCategory = $("#rootCategory").val();
        var categoryId = $("#categoryId").val();
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.ajaxFileUpload({
            url: "${ctx}/data/file/upload?rootCategory="+rootCategory+"&categoryId="+categoryId, //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: "file", //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data)  //服务器成功响应处理函数
            {
                layer.close(index);
                //回调函数传回传完之后的URL地址
                if(data.code == 0){
                    callback(data);
                }else{
                    $("#messageSpan").text(data.msg);
                    $("#messageBox").show();
                }
            },
            error:function(data, status, e){
                $("#messageSpan").text(e);
                layer.close(index);
            }
        });
    }

    function uploadCallback(data){
        var url1 = data.data['url1'];//绝对路径
        var url2 = data.data['url2'];//相对路径
        var fileSize = data.data['fileSize']//文件大小
        $("#fileSize").val(fileSize);
        //alert(fileSize);
        $("#filePath").val(url2);

    }
</script>
</body>
</html>

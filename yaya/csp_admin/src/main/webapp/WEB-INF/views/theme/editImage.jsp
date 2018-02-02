<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2018/2/1
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <script src="${ctxStatic}/js/util.js"></script>
    <script src="${ctxStatic}/js/ajaxfileupload.js"></script>
    <title>${empty image.id ? '添加':'修改'}图片</title>

    <script>
        $(document).ready(function() {
            $("#name").focus();
            var imageUrl = "${image.imgUrl}";
            if (imageUrl == null || imageUrl=="") {
                $("#imageView").attr("src", "");
            }

            $("#inputForm").validate({
                submitHandler: function (form) {
                    layer.msg('正在提交，请稍等...', {
                        icon: 16,
                        shade: 0.01
                    });
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            if("${error}"){
                layer.msg("${error}");
            }

            $("#imgFile").change(function(){
                imageFile($("#imgFile")[0]);
            });

            function imageFile(f) {
                var fileName = $(f).val().toLowerCase();
                if (!fileName.endWith(".jpg") && !fileName.endWith(".png") && !fileName.endWith(".jpeg")){
                    layer.msg("请选择正确的图片格式");
                    return false;
                }

                var index = layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
                $.ajaxFileUpload({
                    url: "${ctx}/csp/course/theme/upload", //用于文件上传的服务器端请求地址
                    secureuri: false, //是否需要安全协议，一般设置为false
                    fileElementId: f.id, //文件上传域的ID
                    data:{"type":0},// 后台传0 代表上传图片
                    dataType: 'json', //返回值类型 一般设置为json
                    success: function (data)  //服务器成功响应处理函数
                    {
                        layer.close(index);
                        if (data.code == 0){
                            //回调函数传回传完之后的URL地址
                            fleshPage(data.data.result);
                            $("#imgName").val(data.data.fileName);
                            $("#imgSize").val(data.data.size);
                        } else {
                            layer.msg(data.err);
                        }
                    },
                    error:function(data, status, e){
                        alert(e);
                        layer.close(index);
                    }
                });
            }

            function fleshPage(data) {
                $("#imageView").attr("src", data.absolutePath);
                $("#imgUrl").val(data.relativePath);
                $("#imgFile").replaceWith('<input type="file" id="imgFile" name="file">');
                $("#imgFile").change(function(){
                    imageFile($("#imgFile")[0]);
                });

            }
        })
    </script>
</head>

<body>
    <ul class="nav nav-tabs">
        <li><a href="${ctx}/csp/course/theme/image/list">图片列表</a></li>
        <li class="active"><a href="${ctx}/csp/course/theme/image/edit">添加图片</a></li>
    </ul>
    <form id="inputForm" action="${ctx}/csp/course/theme/save" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${image.id}"/>
        <input type="hidden" name="type" value="0">

        <div class="control-group">
            <label class="control-label">背景图片:</label>
            <div class="controls">
                <img width="120" height="120" id="imageView"
                        <c:choose>
                            <c:when test="${not empty image.imgUrl}">
                                src="${fileBase}${image.imgUrl}"
                            </c:when>
                            <c:otherwise>
                                src="${fileBase}${image.imgUrl}"
                            </c:otherwise>
                        </c:choose>
                />
                <input type="file" name="file" id="imgFile">
                <span class="help-inline">图片格式为jpg、png、jpeg</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">图片url:</label>
            <div class="controls">
                <input type="search" name="imgUrl"  id="imgUrl" value="${image.imgUrl}" maxlength="100" class="required input-xlarge"/>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">图片名称:</label>
            <div class="controls">
                <input type="search" name="imgName" id="imgName" value="${image.imgName}" maxlength="50" class="required input-xlarge"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">图片大小:</label>
            <div class="controls">
                <input type="search" name="imgSize" id="imgSize" value="${image.imgSize}" maxlength="50" class="required input-xlarge"/>KB
            </div>
        </div>



        <div class="form-actions">
            <shiro:hasPermission name="theme:image:edit">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
            </shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </div>
    </form>
</body>
</html>

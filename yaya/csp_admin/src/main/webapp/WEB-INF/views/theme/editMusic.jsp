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
    <title>${empty music.id ? '添加':'修改'}音乐</title>

    <script>
        $(document).ready(function() {
            $("#name").focus();

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

            $("#musicFile").change(function(){
                musicFile($("#musicFile")[0]);
            });

            function musicFile(f) {
                var fileName = $(f).val().toLowerCase();
                if (!fileName.endWith(".mp3")){
                    layer.msg("请选择正确的音乐格式");
                    return false;
                }

                var index = layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
                $.ajaxFileUpload({
                    url: "${ctx}/csp/course/theme/upload?type=1", //用于文件上传的服务器端请求地址
                    secureuri: false, //是否需要安全协议，一般设置为false
                    fileElementId: f.id, //文件上传域的ID
                    dataType: 'json', //返回值类型 一般设置为json
                    success: function (data)  //服务器成功响应处理函数
                    {
                        layer.close(index);
                        if (data.code == 0){
                            //回调函数传回传完之后的URL地址
                            fleshPage(data.data.result);
                            $("#musicName").val(data.data.fileName);
                            $("#musicSize").val(data.data.size);
                            $("#musicDuration").val(data.data.duration);
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
                $("#musicUrl").val(data.relativePath);
                /*$("#music").prepend('<embed width="200" height="45" id="musicView"' +
                                       'src="' + data.absolutePath +'" type="audio/mpeg"' +
                                       'loop="false" autostart="false" />');*/
                $("#musicFile").replaceWith('<input type="file" id="musicFile" name="file">');
                $("#musicFile").change(function(){
                    musicFile($("#musicFile")[0]);
                });

            }
        })
    </script>
</head>

<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/csp/course/theme/music/list">音乐列表</a></li>
    <li class="active"><a href="${ctx}/csp/course/theme/music/edit">添加音乐</a></li>
</ul>
<form id="inputForm" action="${ctx}/csp/course/theme/save" method="post" class="form-horizontal">
    <input type="hidden" name="id" value="${music.id}"/>
    <input type="hidden" name="type" value="1">

    <div class="control-group">
        <label class="control-label">背景音乐:</label>
        <div class="controls" id="music">
               <%-- <embed width="200" height="45" id="musicView"
                       src="${fileBase}${music.url}" type="audio/mpeg"
                       loop="false" autostart="false" />--%>
            <input type="file" name="file" id="musicFile">
            <span class="help-inline">音乐格式为mp3</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">音乐Url:</label>
        <div class="controls">
            <input type="search" name="url" id="musicUrl" value="${music.url}" maxlength="100" class="required input-xlarge" readonly/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">音乐名称:</label>
        <div class="controls">
            <input type="search" name="name" id="musicName" value="${music.name}" maxlength="50" class="required input-xlarge"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">音乐大小:</label>
        <div class="controls">
            <input type="search" name="size" id="musicSize" value="${music.size}" maxlength="50" class="required input-xlarge" readonly/>KB
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">音乐时长:</label>
        <div class="controls">
            <input type="search" name="duration" id="musicDuration" value="${music.duration}" maxlength="50" class="required input-xlarge" readonly/>秒
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="form-actions">
        <shiro:hasPermission name="theme:music:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>

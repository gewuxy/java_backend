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
    <script src="${ctxStatic}/js/util.js"></script>
    <script src="${ctxStatic}/js/ajaxfileupload.js"></script>
    <title>${empty user.id ? '添加':'修改'}单位号</title>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    var username = $.trim($("#username").val());
                    var mobile = $.trim($("#mobile").val());
                    var nickname = $("#nickname").val();
                    if(username == '' && mobile == ''){
                        layer.msg("请输入注册邮箱或手机号");
                    }else if(username != '' && !username.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
                        layer.msg("请输入正确的邮箱");
                    }else if(mobile != "" && mobile.length != 11){
                        layer.msg("请输入正确的手机号");
                    }else if(nickname == ''){
                        layer.msg("请输入医生昵称");
                    }else{
                        layer.msg('正在提交，请稍等...',{
                            icon: 16,
                            shade: 0.01
                        });
                        form.submit();
                    }

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

            const file_size_limit = 2*1024*1024;

            $("#avatarFile").change(function(){
                avatarFile($("#avatarFile")[0]);
            });

            $("#provinceSelect").change(function(){
                $.post('${ctx}/yaya/unit/regions', {"name":$(this).val()}, function (data) {
                    if (data.code == 0){
                        $("#citySelect").html("");
                        var dataList = data.data;
                        for (var i in dataList){
                            $("#citySelect").append('<option value="'+dataList[i].name+'">'+dataList[i].name+'</option>');
                        }
                    } else {
                        layer.msg(data.err);
                    }
                },'json');
            });

            $("#citySelect").change(function(){
                $.post('${ctx}/yaya/unit/regions', {"name":$(this).val()}, function (data) {
                    if (data.code == 0){
                        $("#zoneSelect").html("");
                        var dataList = data.data;
                        for (var i in dataList){
                            $("#zoneSelect").append('<option value="'+dataList[i].name+'">'+dataList[i].name+'</option>');
                        }
                    } else {
                        layer.msg(data.err);
                    }
                },'json');
            });

            if("${error}"){
                layer.msg("${error}");
            }

            function avatarFile(f){
                var fileName = $(f).val().toLowerCase();
                if (!fileName.endWith(".jpg") && !fileName.endWith(".png") && !fileName.endWith(".jpeg")){
                    layer.msg("请选择正确的图片格式");
                    return false;
                }

                var fSize = fileSize(f);
                if (fSize > file_size_limit){
                    layer.msg("头像大小不能超过2M");
                    return false;
                }

                var index = layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
                $.ajaxFileUpload({
                    url: "${ctx}/yaya/doctor/upload/avatar", //用于文件上传的服务器端请求地址
                    secureuri: false, //是否需要安全协议，一般设置为false
                    fileElementId: f.id, //文件上传域的ID
                    dataType: 'json', //返回值类型 一般设置为json
                    success: function (data)  //服务器成功响应处理函数
                    {
                        layer.close(index);
                        if (data.code == 0){
                            //回调函数传回传完之后的URL地址
                            fleshPage(data.data);
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
                $("#avatarView").attr("src", data.absolutePath);
                $("#headimg").val(data.relativePath);
                $("#avatarFile").replaceWith('<input type="file" id="avatarFile" name="file">');
                $("#avatarFile").change(function(){
                    avatarFile($("#avatarFile")[0]);
                });

            }
        });

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/yaya/doctor/list">医生列表</a></li>
    <li  class="active"><a href="${ctx}/yaya/doctor/edit">${empty user ? '添加':'修改'}医生信息</a></li>
</ul>
<form id="inputForm" action="${ctx}/yaya/doctor/save" method="post" class="form-horizontal">
    <input type="hidden" name="id" value="${user.id}"/>
    <div class="control-group">
        <label class="control-label">注册邮箱:</label>
        <div class="controls">
            <input type="search" name="username" id="username" value="${user.username}"} maxlength="50" class="input-xlarge"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">医生昵称:</label>
        <div class="controls">
            <input type="search" name="nickname" id="nickname" value="${user.nickname}" maxlength="50" class="required input-xlarge"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">医生头像:</label>
        <div class="controls">
            <input type="hidden" name="headimg" id="headimg" value="${user.headimg}">
            <img width="120" height="120" id="avatarView"
                    <c:choose>
                        <c:when test="${not empty user.headimg}">
                            src="${appFileBase}${user.headimg}"
                        </c:when>
                        <c:otherwise>
                            src="${appFileBase}${user.headimg}"
                        </c:otherwise>
                    </c:choose>
                 />
            <input type="file" name="file" id="avatarFile">
            <span class="help-inline">头像格式为jpg、png、jpeg；不可大于2M</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">手机号:</label>
        <div class="controls">
            <input type="search" name="mobile" id="mobile" value="${user.mobile}" maxlength="11" class="input-xlarge"/>
        </div>
    </div>


    <div class="control-group">
        <label class="control-label">医院:</label>
        <div class="controls">
            <input type="search" name="hospital" value="${user.hospital}" maxlength="50" class="input-xlarge"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">科室:</label>
        <div class="controls">
            <input type="search" name="department" value="${user.department}" maxlength="50" class="input-xlarge"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">区域:</label>
        <div class="controls">
            <select name="province" id="provinceSelect" style="width: 150px;">
                <c:forEach items="${provinceList}" var="province">
                    <option value="${province.name}" ${not empty user.province && fn:contains(province.name, user.province) ? 'selected':''}>${province.name}</option>
                </c:forEach>
            </select> -
            <select name="city" id="citySelect" style="width:150px;">
                <option value="${user.city}">${not empty user.city ? user.city : '--城市--'}</option>
            </select> -
            <select name="zone" id="zoneSelect" style="width:150px;">
                <option value="${user.zone}">${not empty user.zone ? user.zone : '--区--'}</option>
            </select>
        </div>
    </div>

    <div class="form-actions">
        <shiro:hasPermission name="yaya:doctor:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>

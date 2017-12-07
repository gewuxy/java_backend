<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <title>App管理列表</title>
    <script type="text/javascript">


        $(function () {
            var checkObj = ${appVersion.forced};
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
                url: "${ctx}/csp/appManage/upload",
                type: 'POST',
                dataType: 'json',
                clearForm: false,
                success: function (data) {
                    if (data.code == 0){
                        layer.msg("上传成功")
                        //$("#downLoadId").val(${ctx}/pic/ + data)
                        var fileId = "uploadFile";
                        var dom = document.getElementById(fileId);
                        var fileSize = dom.files[0].size;
                        var size = parseInt((Math.round(fileSize) / 1024).toFixed(2));
                        $("#fileSize").val(size)
                        $("#downLoadUrl").val(data.data.downUrl);
                    }else{
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
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/menu/edit">App管理列表</a></li>
</ul>
<form id="inputForm" method="post" class="form-horizontal" action="${ctx}/csp/appManage/update"
      enctype="multipart/form-data">
    <input type="hidden" name="id" value="${appVersion.id}"/>
    <div class="control-group">
        <label class="control-label">版本号:</label>
        <div class="controls">
            <input type="search" name="versionStr" value="${appVersion.versionStr}" maxlength="50"
                   class="required input-xlarge"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">版本信息描述:</label>
        <div class="controls">
            <textarea name="details" rows="3" maxlength="2000" class="input-xxlarge" >${appVersion.details}</textarea>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">手机类型:</label>
        <div class="controls">
            <select name="driveTag" id="driveTag" style="width: 150px">
                <option value="">-- 请选择 --</option>
                <option value="ios">IOS</option>
                <option value="ipad">IPAD</option>
                <option value="android">ANDROID</option>
            </select>
            <script>
                document.getElementById("driveTag").value="${appVersion.driveTag}";
            </script>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">下载地址:</label>
        <div class="controls">
            <span class="help-inline"><font color="red">*</font> </span>
            <input type="file" name="uploadFile" id="uploadFile" style="display:none" multiple="multiple"
                   onchange="fileUpload()">
            <input class="btn-dr" type="button" value="上传文件" onclick="selectFile()">
            <input type="hidden" value="${appVersion.downLoadUrl}" name="downLoadUrl" id="downLoadUrl">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">文件大小:</label>
        <div class="controls">
            <input readonly id="fileSize" type="text" name="fileSize" value="${appVersion.fileSize}" maxlength="50"
                   class="required digits input-small"/>KB
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">应用类型:</label>
        <div class="controls">
            <%--<input name="appType" type="search" value="${appVersion.appType}" maxlength="100" class="input-xlarge"/>--%>
            <select name="appType" id="appType" style="width: 150px">
                <option value="">-- 请选择 --</option>
                <option value="yaya_yishi">YAYA_YISHI</option>
                <option value="yaya_yaoshi">YAYA_YAOSHI</option>
                <option value="hlyy">HLYY</option>
            </select>
            <script>
                document.getElementById("appType").value="${appVersion.appType}";
            </script>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">版本更新说明:</label>
        <div class="controls">
            <input name="details" type="search" value="${appVersion.details}" maxlength="100" class="input-xlarge"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否强制更新:</label>
        <div class="controls">
            <input id="checked" type="radio" value="1" name="forced">是
            <input id="unchecked" type="radio" value="0" name="forced">否
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="csp:appManage:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit"
                                                         value="修 改"/>
        </shiro:hasPermission>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>

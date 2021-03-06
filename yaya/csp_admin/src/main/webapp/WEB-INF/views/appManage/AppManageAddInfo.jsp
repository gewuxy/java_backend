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
        $(document).ready(function() {
            $("#driveTag").change(function () {
                selectChange();
            })

            /*版本号校验*/
            $("#version").blur(function () {
                var version = $("#version").val();
                var reg = /\./g;
                if (reg.test(version)){
                    var subVer = version.substr(version.indexOf("."),version.length+1)
                    if (subVer.length>4 || subVer.indexOf(".")==-1||subVer.indexOf(".")>2){
                        layer.msg("格式有误,请填入xx.x.x格式");
                        $("#btnSubmit").attr("disabled","disabled");
                    }else {
                        $("#btnSubmit").removeAttr("disabled");
                    }
                }else {
                    layer.msg("格式有误,请填入xx.x.x格式");
                    $("#btnSubmit").attr("disabled","disabled");
                }
            })

            function selectChange() {
                var selectValue = $("#driveTag").val();
                if (selectValue == "ios" || selectValue == "ipad"){
                    $("#uploadId").hide();
                    $("#uploadSizeId").hide();
                    $("#divId").show();
                    $("#downLoadUrl").removeAttr("readonly");
                }else {
                    $("#uploadId").show();
                    $("#uploadSizeId").show();
                    $("#divId").hide();
                }
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
                    //alert(data.code)
                    if (data.code == 0){
                        layer.msg("上传成功")
                        $("#downLoadUrl").val(data.data.downUrl)
                        //$("#downLoadId").val(${ctx}/pic/ + data)
                        var fileId = "uploadFile";
                        var dom = document.getElementById(fileId);
                        var fileSize = dom.files[0].size;
                        var size = parseInt((Math.round(fileSize) / 1024).toFixed(2));
                        $("#fileSize").val(size)
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
    <li class="active"><a href="#">App添加列表</a></li>
</ul>
<form id="inputForm" method="post" class="form-horizontal" action="${ctx}/csp/appManage/add"
      enctype="multipart/form-data">
    <div class="control-group">
        <label class="control-label">版本号:</label>
        <div class="controls">
            <input type="search" name="versionStr" maxlength="8"  id="version"
                   class="required input-xlarge"  onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/>
            <span class="help-inline"><font color="#a9a9a9">(必填)只允许输入数字和小数点,请填入xx.x.x格式</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">版本信息描述:</label>
        <div class="controls">
            <textarea name="details" rows="3" maxlength="2000" class="input-xxlarge"></textarea>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">手机类型:</label>
        <div class="controls">
            <select name="driveTag" id="driveTag" style="width: 100px">
                <option value="">-- 请选择 --</option>
                <option value="ios">IOS</option>
                <option value="ipad">IPAD</option>
                <option value="android">ANDROID</option>
            </select>
        </div>
    </div>
    <div class="control-group" id="divId">
        <label class="control-label">IOS下载地址:</label>
        <div class="controls">
            <input readonly name="downLoadUrl" type="search" maxlength="100" id="downLoadUrl"
                   class="input-xlarge"/>
            <span class="help-inline"><font color="#a9a9a9">ios系统输入下载地址</font> </span>
        </div>
    </div>
    <div class="control-group" id="uploadId">
        <label class="control-label">上传文件:</label>
        <div class="controls">
            <input type="file" name="uploadFile" id="uploadFile" style="display:none" multiple="multiple"
                   onchange="fileUpload()">
            <input class="btn-dr" type="button" value="上传文件" onclick="selectFile()" id="uploadBtn">
            <input type="hidden" id="hiUpload" value="${filename}" name="uploadFile">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">应用类型:</label>
        <div class="controls">
            <select name="appType" style="width: 150px">
                <option value="">-- 请选择 --</option>
                <option value="cspmeeting_cn">国内版</option>
                <option value="cspmeeting_us">海外版</option>
            </select>
        </div>
    </div>
    <div class="control-group" id="uploadSizeId">
        <label class="control-label">文件大小:</label>
        <div class="controls">
            <input readonly id="fileSize" type="search" name="fileSize" value="${fileSize}" maxlength="50"
                   class="required digits input-small"/>KB
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
        <shiro:hasPermission name="csp:appManage:add">
            <input id="btnSubmit" class="btn btn-primary" type="submit"
                   value="保 存"/>
        </shiro:hasPermission>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>
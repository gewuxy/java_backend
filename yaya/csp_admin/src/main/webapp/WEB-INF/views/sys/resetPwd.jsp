<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>修改密码 </title>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/csp/sys/user/info">个人信息</a></li>
    <li class="active"><a href="${ctx}/csp/sys/user/pwd">修改密码</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="inputForm" method="post" action="${ctr}/csp/sys/user/resetPwd" class="form-horizontal">
    <div class="control-group">
        <label class="control-label">旧密码:</label>
        <div class="controls">
            <input type="password" id="oldPassword" name="oldPassword" maxlength="50" class="required abc"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">新密码:</label>
        <div class="controls">
            <input type="password" id="newPassword" name="newPassword"  minlength="5" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">确认新密码:</label>
        <div class="controls">
            <input type="password"  name="confirm_password" equalTo="#newPassword" minlength="5" class="required'"/>
            <span class="help-inline"></span><font color="red">*</font>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
<script>
    $(document).ready(function() {
        layer.msg(".....");
        initFormValidate();
    });
</script>
</body>
</html>

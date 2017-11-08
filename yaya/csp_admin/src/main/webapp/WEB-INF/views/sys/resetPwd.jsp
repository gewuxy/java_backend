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
<form id="inputForm" method="post" action="${ctr}/csp/sys/user/resetPwd" class="form-horizontal">
    <div class="control-group">
        <label class="control-label">旧密码:</label>
        <div class="controls">
            <input type="password" name="oldPassword" maxlength="50" class="required abc"/>
            <span class="help-inline"><font color="red">*</font> </span><span>${passwordErrpor}</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">新密码:</label>
        <div class="controls">
            <input type="password" name="newPassword"  maxlength="50" class="required abc"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
<script>
    $(document).ready(function() {
        initFormValidate();
    });
</script>
</body>
</html>

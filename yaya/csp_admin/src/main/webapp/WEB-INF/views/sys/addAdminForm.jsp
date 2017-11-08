<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>个人信息</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/sys/user/info">添加管理员</a></li>
</ul><br/>
<form id="inputForm" action="${ctx}/csp/sys/user/add" method="post" class="form-horizontal">
    <div class="control-group">
        <label class="control-label">账号:</label>
        <div class="controls">
            <input type="search" name="account" maxlength="50" class="required userName" />
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">姓名:</label>
        <div class="controls">
            <input type="search" name="userName" htmlEscape="false" maxlength="50" class="required realName"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">密码:</label>
        <div class="controls">
            <input type="password" id="password" name="password" htmlEscape="false" minlength="5" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">确认密码:</label>
        <div class="controls">
            <input type="password" name="confirm_password" htmlEscape="false" minlength="5" class="{required equalTo:'#password'}"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">邮箱:</label>
        <div class="controls">
            <input type="search" name="email"  maxlength="50" class="email"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">手机:</label>
        <div class="controls">
            <input type="search" name="mobile"  maxlength="50" class="mobile"/>
        </div>
    </div>
    <div class="form-actions">
        <input type="hidden" name="active" value="1"/>
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
    </div>
</form>
<script>
    $(document).ready(function() {
        initFormValidate();
    });
</script>
</body>
</html>

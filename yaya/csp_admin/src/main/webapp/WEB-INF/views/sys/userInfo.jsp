<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>个人信息</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/sys/user/info">个人信息</a></li>
    <li><a href="${ctx}/sys/user/pwd">修改密码</a></li>
</ul><br/>
<form id="inputForm" action="${ctx}/sys/user/update" method="post" class="form-horizontal">
    <div class="control-group">
        <label class="control-label">账号:</label>
        <div class="controls">
            <input type="search" name="userName" maxlength="50" class="required" value="${user.userName}" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">姓名:</label>
        <div class="controls">
            <input type="search" name="realName" htmlEscape="false" maxlength="50" value="${user.realName}" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">邮箱:</label>
        <div class="controls">
            <input type="search" name="email" value="${user.email}" maxlength="50" class="email"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">手机:</label>
        <div class="controls">
            <input type="search" name="mobile" value="${user.mobile}" maxlength="50" class="mobile"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">上次登录:</label>
        <div class="controls">
            <label class="lbl">IP: ${user.lastLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.lastLoginDate}" type="both" dateStyle="full"/></label>
        </div>
    </div>
    <div class="form-actions">
        <input type="hidden" name="id" value="${user.id}"/>
        <shiro:hasPermission name="sys:user:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
        </shiro:hasPermission>
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

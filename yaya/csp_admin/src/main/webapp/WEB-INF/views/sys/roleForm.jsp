<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>个人信息</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/sys/role/list">角色列表</a></li>
    <li class="active"><a href="${ctx}/sys/role/add">添加角色</a></li>
</ul><br/>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="inputForm" action="${ctx}/sys/role/add" method="post" class="form-horizontal">
    <div class="control-group">
        <label class="control-label">角色英文名称:</label>
        <div class="controls">
            <input type="search" id="roleName" name="roleName" value="${role.roleName}" maxlength="50" class="required abc" />
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">角色中文名称:</label>
        <div class="controls">
            <input type="search" name="roleDesc" id="roleDesc" value="${role.roleDesc}" maxlength="50" class="required realName"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="form-actions">
        <input type="hidden" name="id" value="${role.id}"/>
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
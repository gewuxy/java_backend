<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>个人信息</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/sys/user/list">管理员列表</a></li>
    <li class="active"><a href="${ctx}/sys/user/add">添加管理员</a></li>
</ul><br/>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="inputForm" action="${ctx}/sys/user/edit" method="post" class="form-horizontal">
    <div class="control-group">
        <label class="control-label">账号:</label>
        <div class="controls">
            <input type="search"  name="userName" value="${user.userName}" maxlength="50" class="required userName" />
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">姓名:</label>
        <div class="controls">
            <input type="search" name="realName" value="${user.realName}" htmlEscape="false" maxlength="50" class="required realName"/>
            <span class="help-inline"><font color="red">*</font> </span>
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
    </div> <div class="control-group">
        <label class="control-label">角色:</label>
        <div class="controls">
            <select id="roleId" name="roleId" style="width: 20%;" type="text" class="form-control">
                <option value="">请选择</option>
                <c:if test="${not empty roleList}">
                    <c:forEach items="${roleList}" var="roleList">
                        <option value="${roleList.id}" ${roleList.id eq user.roleId?"selected":""} regionId="${roleList.id}">${roleList.roleDesc}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>
    </div>
    <c:if test="${empty user}">
        <div class="control-group">
            <label class="control-label">密码:</label>
            <div class="controls">
                <input type="password" id="password" name="password"  minlength="5" class="required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">确认密码:</label>
            <div class="controls">
                <input type="password"  name="confirm_password" equalTo="#password" minlength="5" class="required'"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </c:if>
    <div class="form-actions">
        <input type="hidden" id="id" name="id" value="${user.id}"/>
        <shiro:hasPermission name="sys:user:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
        </shiro:hasPermission>
    </div>
</form>
<script>
    $(document).ready(function() {
        initFormValidate();
    });
</script>
</body>
</html>

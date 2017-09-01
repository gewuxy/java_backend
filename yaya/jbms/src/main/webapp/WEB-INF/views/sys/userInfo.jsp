<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/3
  Time: 13:24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>个人信息</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/user/info">个人信息</a></li>
    <li><a href="${ctx}/sys/user/pwd">修改密码</a></li>
</ul><br/>
<form id="inputForm" action="${ctx}/sys/user/info" method="post" class="form-horizontal">

    <div class="control-group">
        <label class="control-label">账号:</label>
        <div class="controls">
            <input type="search" name="username" maxlength="50" class="required" value="${user.username}" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">姓名:</label>
        <div class="controls">
            <input type="search" name="realname" htmlEscape="false" maxlength="50" value="${user.realname}" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">微信:</label>
        <div class="controls">
            <input type="search" name="openid" htmlEscape="false" maxlength="50" value="${user.openid}" class="required" readonly="true"/>
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
            <input type="search" name="mobile" value="${user.mobile}" maxlength="50"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">QQ:</label>
        <div class="controls">
            <input type="search" name="qq" value="${user.qq}"  maxlength="50"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户角色:</label>
        <div class="controls">
            <label class="lbl">${user.isAdmin?"超级管理员":user.role.roleName}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">上次登录:</label>
        <div class="controls">
            <label class="lbl">IP: ${user.lastLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.lastLoginDate}" type="both" dateStyle="full"/></label>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
    </div>
</form>
</body>
</html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>会议信息</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="#">会议信息</a></li>
</ul><br/>
<form id="inputForm" action="${ctx}/csp/meet/delete" method="post" class="form-horizontal">
    <div class="control-group">
            <label class="control-label">标题:</label>
            <div class="controls">
                <input type="search" name="meetName" style="width: 300px;" maxlength="50" class="required" value="${meet.title}" />
                <label style="margin-left: 15%">是否发布:</label>&nbsp;&nbsp;&nbsp;
                <input type="search" name="organizer" style="width: 300px;" htmlEscape="false" maxlength="50" value="${meet.publishState == true ? "已发布":"未发布"}"/>
            </div>
    </div>
    <div class="control-group">
        <label class="control-label">投稿人:</label>
        <div class="controls">
            <input type="search" name="name" style="width: 300px;" htmlEscape="false" maxlength="50" value="${meet.name}" />
            <label style="margin-left: 15%">emial:</label>
            <input type="search" name="email" style="width: 300px;" htmlEscape="false" maxlength="50" value="${meet.email}"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">直播类型:</label>
        <div class="controls">
            <input type="search" name="playType" style="width: 300px;" htmlEscape="false" maxlength="50" value="${meet.playType eq 0?"录播":meet.playType eq 1?"ppt直播":"视频直播"}" />
            <label style="margin-left: 15%">emial:</label>
            <input type="search" name="email" style="width: 300px;" htmlEscape="false" maxlength="50" value="${meet.email}"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">会议简介:</label>
        <div class="controls">
            <textarea  name="contentTw" style="width: 315px;height: 100px;">${meet.info}</textarea>
        </div>
    </div>
    <div class="form-actions">
        <input type="hidden" name="id" value="${meet.id}"/>
        <shiro:hasPermission name="csp:meet:close">
             <input id="btnSubmit" class="btn btn-primary" type="submit"  value="关 闭"/>
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

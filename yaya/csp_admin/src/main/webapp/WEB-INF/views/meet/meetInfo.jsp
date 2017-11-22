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
<form id="inputForm" action="${ctx}/csp/meet/update" method="post" class="form-horizontal">
    <div class="control-group">
            <label class="control-label">会议名称:</label>
            <div class="controls">
                <input type="search" name="meetName" style="width: 300px;" maxlength="50" class="required" value="${meet.meetName}" class="required"/>
                <label style="margin-left: 15%">主办方:</label>&nbsp;&nbsp;&nbsp;
                <input type="search" name="organizer" style="width: 300px;" htmlEscape="false" maxlength="50" value="${meet.organizer}" class="required"/>
            </div>
    </div>
    <div class="control-group">
        <label class="control-label">会议科室:</label>
        <div class="controls">
            <input type="search" name="meetType" style="width: 300px;" htmlEscape="false" maxlength="50" value="${meet.meetType}" class="required"/>
            <label style="margin-left: 15%">会议类型:</label>
            <input type="search" name="userName" style="width: 300px;" htmlEscape="false" maxlength="50" value="${meet.meetType}" class="required"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">主讲人:</label>
        <div class="controls">
            <input type="search" name="lecturer" style="width: 300px;" value="${meet.lecturer}" maxlength="50" />
            <label style="margin-left: 14%">主讲人等级:</label>
            <input type="search" name="lecturerTitle" style="width: 300px;" value="${meet.lecturerTitle}" maxlength="50" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">会议开始时间:</label>
        <div class="controls">
            <input type="search" name="startTime" style="width: 300px;" value="<fmt:formatDate value='${meet.startTime}' type='both'/>"/>
             <label style="margin-left: 15%">结束时间:</label>
            <input type="search" name="endTime" style="width: 300px;" value="<fmt:formatDate value='${meet.endTime}' type='both'/>"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">会议状态:</label>
        <div class="controls">
            <select id="state" name="state" style="width: 315px;">
                <option value="0" ${meet.state == 0|| user.active == null?'selected':''}>草稿</option>
                <option value="1" ${meet.state == 1 ?'selected':''}>未开始</option>
                <option value="2" ${meet.state == 2 ?'selected':''}>进行中</option>
                <option value="3" ${meet.state == 3 ?'selected':''}>已结束</option>
                <option value="4" ${meet.state == 4 ?'selected':''}>已撤销</option>
                <option value="5" ${meet.state == 5 ?'selected':''}>未发布</option>
                <option value="6" ${meet.state == 6 ?'selected':''}>已关闭</option>
            </select>
            <label style="margin-left: 15%">会议用时:</label>
            <input type="search" name="meetTime" id="meetTime"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">会议简介:</label>
        <div class="controls">
            <textarea id="contentTw" name="contentTw" style="width: 315px;height: 100px;">${meet.introduction}</textarea>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="修  改"/>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
<script>
    $(document).ready(function() {
        initFormValidate();
        setMeetTime();
    });

    function setMeetTime(){
        var startTime = new Date('${meet.startTime}');
        var endTime = new Date('${meet.endTime}');
        var minutes =  Math.round((endTime.getTime() - startTime.getTime())/(1000*3600) *100)/100;
        $("#meetTime").val(minutes + "小时");

    }
</script>
</body>
</html>

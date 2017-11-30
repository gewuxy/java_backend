<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/27
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页推荐会议列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/yaya/recommendMeet/list">首页推荐会议列表</a></li>
</ul>
<form id="pageForm" name="pageForm" action="${ctx}/yaya/recommendMeet/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
</form>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="searchForm" method="post" class="breadcrumb form-search">
    <input placeholder="会议名称" value="${meetName}" size="40"  type="search" name="meetName" maxlength="50" class="required"/>&nbsp;&nbsp;
    <select name="recType" id="recType" style="width: 150px;">
        <option value="">推荐类型</option>
        <option value="1">会议文件夹</option>
        <option value="2">会议</option>
        <option value="3">单位号</option>
    </select>&nbsp;&nbsp;
    <select name="recFlag" id="recFlag">
        <option value="">是否推荐</option>
        <option value="false">不推荐</option>
        <option value="true">推荐</option>
    </select>&nbsp;&nbsp;
        <select id="sort" name="sort" style="width: 150px">
            <option value="">-- 排序 --</option>
            <c:forEach begin="1" end="10" step="1" var="sort">
                <option value="${sort}">${sort}</option>
            </c:forEach>
        </select>&nbsp;&nbsp;
<shiro:hasPermission name = "yaya:recommendMeet:view">
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
</shiro:hasPermission>&nbsp;&nbsp;
<shiro:hasPermission name = "yaya:recommendMeet:add">
    <input id="insertRecommendMeet" class="btn btn-primary" type="button" value="新 建"  name="recommendMeet" />
</shiro:hasPermission>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr>
        <th>会议名称</th>
        <th>推荐类型</th>
        <th>是否推荐</th>
        <th>推荐日期</th>
        <th>排序序号</th>
        <th>是否固定推荐</th>
        <th>会议状态</th>
        <th>操作</th>
    </tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="meet">
            <tr>
                <td>${meet.meetName}</td>
                <td>${meet.recType == 1 ? "会议文件夹" : meet.recType == 2 ? "会议" :meet.recType == 3 ? "单位号" :""}</td>
                <td>${meet.recFlag == false ? "不推荐" : meet.recFlag == true ? "推荐" : ""}</td>
                <td><fmt:formatDate value="${meet.recDate}" type="both" dateStyle="full"/></td>
                <td>${meet.sort}</td>
                <td>${meet.fixed == 0 ? "不固定" :meet.fixed == 1 ? "固定" :""}</td>
                <td>${meet.state == 0 ? "会议草稿" :meet.state == 1 ? "会议未开始" :meet.state == 2 ? "会议进行中" :meet.state == 3 ? "会议已结束"
                :meet.state == 4 ? "会议已撤销/已删除":meet.state == 5 ? "会议未发布":meet.state == 6 ? "会议已关闭":""}</td>
                <td>
                    <shiro:hasPermission name="yaya:recommendMeet:view">
                    <a href="${ctx}/yaya/recommendMeet/check?id=${meet.id}">查看</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="yaya:recommendMeet:close">
                    <c:if test="${meet.state != 6}">
                    <a href="${ctx}/yaya/recommendMeet/close?id=${meet.id}"  onclick="confirm('确认关闭吗？')">关闭</a>
                    </c:if>
                    </shiro:hasPermission>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${empty page.dataList}">
        <tr>
            <td colspan="7">没有查询到数据</td>
        </tr>
    </c:if>
    </tbody>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>
</body>
<script>
    $("#insertRecommendMeet").click(function () {
        location.href="${ctx}/yaya/recommendMeet/edit"
    })
</script>
</html>

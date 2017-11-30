<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: jianliang
  Date: 2017/11/23
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增推荐会议</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>

</head>
<script type="text/javascript" src="${ctxStatic}/laydate/laydate.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-plugin/jquery-form.js"></script>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/yaya/recommendMeet/edit">新增推荐会议</a></li>
</ul>
<form id="inputForm" method="post" class="form-horizontal" action="${ctx}/yaya/recommendMeet/insert">
    <div class="control-group">
        <label class="control-label">会议名称:</label>
        <div class="controls">
            <input type="search" name="meetName" id="meetName" maxlength="50" class="required input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">主讲者姓名:</label>
        <div class="controls">
            <input type="search" name="lecturer" id="lecturer" maxlength="50" class="required input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">主讲者职位:</label>
        <div class="controls">
            <input type="search" name="lecturerTile" id="lecturerTile" maxlength="50" class="required input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">主讲者所属医院:</label>
        <div class="controls">
                <input type="search" name="lecturerHos" id="lecturerHos" maxlength="50" class="required input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">主讲者所属科室:</label>
        <div class="controls">
            <select id="lecturerDepart" name="lecturerDepart" style="width: 150px;">
                <option value="">科室</option>
                <c:forEach items="${departments}" var="d">
                    <option value="${d}"}>${d}</option>
                </c:forEach>
            </select>&nbsp;&nbsp;
        </div>
    </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">会议状态:</label>
        <div class="controls">
            <select id="state" name="state" style="width: 150px">
                <option value="">会议状态</option>
                <option value="0">会议草稿</option>
                <option value="1">会议未开始</option>
                <option value="2">会议进行中</option>
                <option value="3">会议已结束</option>
                <option value="4">会议已撤销/已删除</option>
                <option value="5">会议未发布</option>
                <option value="6">会议已关闭</option>
            </select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">推荐类型:</label>
        <div class="controls">
            <select id="recType" name="recType" style="width: 150px">
                <option value="">推荐类型</option>
                <option value="1">会议文件夹</option>
                <option value="2">会议</option>
                <option value="3">单位号</option>
            </select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否推荐:</label>
        <div class="controls">
            <select id="recFlag" name="recFlag" style="width: 150px">
                <option value="">是否推荐</option>
                <option value="false">不推荐</option>
                <option value="true">推荐</option>
            </select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">排序序号:</label>
        <div class="controls">
            <input type="search" name="sort" id="sort" maxlength="50" class="required input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">推荐日期:</label>
        <div class="controls">
            <input type="text" id="recDate" class="layui-input" name="recDate" placeholder="yyyy-MM-dd HH:mm:ss">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否固定推荐:</label>
        <div class="controls">
            <select id="fixed" name="fixed" style="width: 150px">
                <option value="">是否固定推荐</option>
                <option value="0">不固定</option>
                <option value="1">固定</option>
            </select>
        </div>
    </div>
    <div class="form-actions">
<shiro:hasPermission name = "yaya:recommendMeet:add">
        <input id="btnSubmit" class="btn btn-primary" type="submit"
               value="发 布"/>
</shiro:hasPermission>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function() {
        initFormValidate();
    });
    //时间选择器
    laydate.render({
        elem: '#recDate'
        ,type: 'datetime'
    });
</script>
</body>
</html>



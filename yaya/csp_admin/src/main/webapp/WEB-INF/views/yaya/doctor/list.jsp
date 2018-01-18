<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>医生列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/yaya/doctor/list">医生列表</a></li>
    <li><a href="${ctx}/yaya/doctor/edit">添加医生</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="pageForm" name="pageForm" action="${ctx}/yaya/doctor/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input  name="keyword" type="hidden" value="${keyword}"/>
</form>
<form id="searchForm" method="post" action="${ctx}/yaya/doctor/list" class="breadcrumb form-search">
    <input placeholder="手机/邮箱/昵称" value="${keyword}" size="40"  type="search" name="keyword" maxlength="50" class="required"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>昵称</th><th>邮箱</th><th>手机</th><th>注册日期</th><th>地址</th><th>医院</th><th>科室</th><th>操作</th></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="user">
            <tr>
                <td>${user.nickname}</td>
                <td>${user.username}</td>
                <td>${user.mobile}</td>
                <td><fmt:formatDate value="${user.registerDate}" pattern="yyyy/MM/dd"/></td>
                <td>${user.province} ${user.city} ${user.zone}</td>
                <td>${user.hospital}</td>
                <td>${user.department}</td>
                <td>
                    <shiro:hasPermission name="yaya:doctor:edit">
                        <a href="${ctx}/yaya/doctor/edit?id=${user.id}">修改</a>&nbsp;&nbsp;


                        &nbsp;&nbsp;
                        <a style="cursor: pointer;" userId="${user.id}" class="resetPwd">重置密码</a>
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
<script>
    $(function(){
        $(".resetPwd").click(function(){
            var userId = $(this).attr("userId");
            top.layer.confirm("确定要重置该医生的密码？", function(){
                $.get('${ctx}/yaya/doctor/pwd/reset/' + userId, {}, function (data) {
                    if (data.code == 0){
                        top.layer.closeAll('dialog');
                        top.layer.open({
                            type: 0,
                            area:['200px', '200px'],
                            content: '密码已重置为 : ' + data.data.currentPwd //这里content是一个普通的String
                        });
                    } else {
                        layer.msg(data.err);
                        top.layer.closeAll('dialog');
                    }

                },'json');
            });

        });
    });
</script>
</body>
</html>

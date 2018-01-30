<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CSP用户管理</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/csp/user/list?listType=0">国内用户列表</a></li>
    <li><a href="${ctx}/csp/user/list?listType=1">海外用户列表</a></li>
    <li class="active"><a href="${ctx}/csp/user/list?listType=2">冻结用户列表</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="pageForm" name="pageForm" action="${ctx}/csp/user/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input  name="keyWord" type="hidden" value="${keyWord}"/>
    <input  name="listType" type="hidden" value="${listType}"/>
</form>
<form id="searchForm" method="post" action="${ctx}/csp/user/list" class="breadcrumb form-search">
    <input placeholder="昵称/用户名/电话" value="${keyWord}" size="40"  type="search" name="keyWord" maxlength="50" class="required"/>
    <input  name="listType" type="hidden" value="${listType}"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>ID</th>
        <th>昵称</th>
        <th>冻结日期</th>
        <th>冻结原因</th>
        <th>备注</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="user">
            <tr>
                <td>${user.uid}</td>
                <td>${user.nickName}</td>
                <td><fmt:formatDate value="${user.updateTime}" pattern="yyyyMMdd"/></td>
                <td>${user.frozenReason}</td>
                <td><input type="text" style="width: 70px;height: auto;" maxlength="10" value="${user.remark}"> &nbsp;<a href="#" onclick="remark(this,'${user.uid}')">备注</a></td>
                <th>
                    <div class="btn-group">
                        <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                            操作
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <shiro:hasPermission name="csp:user:frozen">
                                <li><a  data-href="${ctx}/csp/user/package?actionType=5&listType=2&userId=${user.uid}" onclick="layerConfirm('确认解冻？', this)">解冻</a></li>
                            </shiro:hasPermission>
                        </ul>
                    </div>
                </th>
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
    //修改备注
    function remark(obj,userId){
        var remark = $(obj).parent().find("input").val();
        $.ajax({
            url:'${ctx}/csp/user/remark',
            data:{"id":userId,"remark":remark},
            dataType:"json",
            type:"post",
            success:function (data) {
                if(data.code == "0"){
                    layer.msg("更新成功");
                }else{
                    layer.msg("修改备注失败，请重试");
                }
            }
        });
    }
</script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/sys/user/list">管理员列表</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="pageForm" name="pageForm" action="${ctx}/sys/user/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input  name="username" type="hidden" value="${username}"/>
</form>
<form id="searchForm" method="post" action="${ctx}/sys/user/list" class="breadcrumb form-search">
    <input placeholder="管理员帐号" value="${username}" size="40"  type="search" name="username" maxlength="50" class="required"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
    <input id="btnAdd" class="btn btn-primary" type="button" style="margin-left: 60%" value="添加管理员" onclick="window.location.href = '${ctr}/csp/sys/user/addAdmin'"/>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>帐号</th><th>真实姓名</th><th>电话</th><th>电子邮箱</th><th>使用状态</th><th>用户角色</th><th>最后登录时间</th><th>最后登录ip地址</th></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="user">
            <tr>
                <td>${user.userName}</td>
                <td>${user.realName}</td>
                <td>${user.mobile}</td>
                <td>${user.email}</td>
                <td>${user.roleDesc}</td>
                <td>${user.active == true ? "使用":"禁用"}</td>
                <td><fmt:formatDate value="${user.lastLoginDate}" type="both" dateStyle="full"/></td>
                <td>${user.lastLoginIp}</td>
                <shiro:hasPermission name="sys:user:role">
                    <td>
                        <button class="btn btn-primary btn-lg" data-toggle="modal"  onclick="setRole(${user.id},${user.roleId})">分配角色</button>
                    </td>
                </shiro:hasPermission>
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
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" style="text-align: center">
                    分配角色
                </h4>
            </div>
            <form class="form-horizontal form-bordered form-row-strippe" id="modalForm" method="post">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12" style="height: 48px;" id="isUserDiv">
                            <div class="form-group">
                                <label class="control-label col-md-3">账号是否生效：</label>
                                <div class="col-md-9">
                                    <select id="roleId" name="roleId" style="width: 40%;" type="text" class="form-control">
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer bg-info">
                    <button type="button" class="btn green" data-dismiss="modal">取消</button>
                    <button type='submit' class='btn btn-primary' id="submit">确定</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<%@include file="/WEB-INF/include/pageable.jsp"%>
<script>

    function setRole(id,roleId){
        alert(id);
        alert(roleId);
        $("#myModal").show();
    }
</script>
</body>
</html>

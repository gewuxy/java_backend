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
    <li><a href="${ctx}/csp/user/list?listType=2">冻结用户列表</a></li>
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
    <tr><th>ID</th><th>昵称</th><th>注册日期</th><th>套餐等级</th><th>套餐日期</th><th>付费次数</th>
        <th>付费总额(CNY)</th><th>付费总额(USD)</th><th>会议数</th><th>备注</th><th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="user">
            <tr>
                <td>${user.uid}</td>
                <td>${user.nickName}</td>
                <td><fmt:formatDate value="${user.registerTime}" pattern="yyyyMMdd"/></td>
                <td>${user.packageId eq 1 ? "标准版": user.packageId eq 2 ? "高级版": user.packageId eq 3 ? "专业版":"<span style='color: #9c0001'>未登录</span>"}</td>
                <td>
                    <c:if test="${not empty user.packageStart}">
                        <fmt:formatDate value="${user.packageStart}" pattern="yyyyMMdd"/>至<fmt:formatDate value="${user.packageEnd}" pattern="yyyyMMdd"/>
                    </c:if>
                    <c:if test="${empty user.packageStart && user.packageId eq 1}">
                        无期限
                    </c:if>
                    <c:if test="${empty user.packageStart && empty user.packageId}">
                        <span style='color: #9c0001'>无</span>
                    </c:if>
                </td>
                <td>${not empty user.payTimes ? user.payTimes : 0}</td></td>
                <td>${not empty user.payMoneyCn ? user.payMoneyCn : 0}</td>
                <td>${not empty user.payMoneyUs ? user.payMoneyUs : 0}</td>
                <td>${user.meets}</td>
                <td><input type="text" value="${user.remark}"></td></td>
                <th>
                    <div class="btn-group">
                        <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                            操作
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="#" onclick="changePackage(${user.uid},${user.packageId})">升级</a></li>
                            <li><a href="#">降级</a></li>
                            <li><a href="#">时间修改</a></li>
                            <li><a href="#">账号冻结</a></li>
                        </ul>
                    </div>
                </th>
                <%--<td>--%>
                    <%--<shiro:hasPermission name="csp:user:edit">--%>
                        <%--<a href="${ctx}/csp/user/viewOrRegister?id=${user.id}&actionType=3&listType=${listType}">修改</a>--%>
                        <%--<c:if test="${user.active eq true}">--%>
                            <%--<a data-href="${ctx}/csp/user/update?id=${user.id}&actionType=1&listType=${listType}"  onclick="layerConfirm('确认要冻结该用户帐号吗？', this)">冻结</a>--%>
                        <%--</c:if>--%>
                    <%--</shiro:hasPermission>--%>
                    <%--<shiro:hasPermission name="csp:user:del">--%>
                         <%--<a data-href="${ctx}/csp/user/update?id=${user.id}&actionType=2&listType=${listType}"  onclick="layerConfirm('确认要删除该用户帐号吗？', this)">删除</a>--%>
                    <%--</shiro:hasPermission>--%>
                <%--</td>--%>
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
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" style="text-align: center">升级</h4>
            </div>
            <form class="form-horizontal form-bordered form-row-strippe" id="modalForm" action="/csp/user/package" method="post">
                <div class="modal-body" >
                    <div class="control-group">
                        <label class="control-label">升级为:</label>
                        <div class="controls">
                            <select id="packageId" name="packageId" style="width: 130px;">
                                <option value="">请选择</option>
                                <option value="1">标准版</option>
                                <option value="2">高级版</option>
                                <option value="3">专业版</option>
                            </select>
                        </div>
                    </div>
                    <div>
                        <label class="control-label">有效期至:</label>
                        <div class="controls">
                            <input type="text" name="updateTimes" id="updateTimes" placeholder="点击选择开始时间">
                        </div>
                    </div>
                </div>
                <div class="modal-footer bg-info">
                    <input type="hidden" name="userId" id="userId" value=""/>
                    <button type="button" class="btn green">返回</button>
                    <button type="button" class="btn btn-primary" onclick="submitBtn()">提交</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>

    $(function () {
        var active = '${listType}';
        $(".nav-tabs li:eq(" + active +")").addClass("active");

        initLaydate("updateTimes");
    })

    function changePackage(userId,packageId){
        $("#packageId").select2().val(packageId).trigger("change");
        $("#userId").val(userId);
        $("#myModal").modal("show");
    }

    function submitBtn() {
        top.layer.confirm("确认升级吗", function(){
           $("#modalForm").submit();
           top.layer.closeAll('dialog');
        });
    }
</script>
</body>
</html>

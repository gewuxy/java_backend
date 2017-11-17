<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>编辑菜单</title>

</head>
<body>
<ul class="nav nav-tabs">
    <li  class="active"><a href="${ctx}/csp/order/check">流量订单详情</a></li>
</ul>
<form id="inputForm" method="post" class="form-horizontal" action="${ctx}/csp/order/update">
    <c:forEach items="${fluxOrderList}" var="order">
    <input type="hidden" name="id" value="${order.id}"/>
    <input type="hidden" name="userId" value="${order.userId}"/>
    <div class="control-group">
        <label class="control-label">用户名:</label>
        <div class="controls">
            <input readonly type="search" name="username" value="${order.username}" maxlength="50" class="required input-xlarge"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">订单号:</label>
        <div class="controls">
            <input readonly  type="search" name="tradeId" value="${order.tradeId}" maxlength="50" class="required input-xxlarge"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">流量总数:</label>
            <div class="controls">
                <input readonly  type="search" name="fluxTotal" value="${flux}" maxlength="50" class="required input-xxlarge"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">花费流量:</label>
        <div class="controls">
            <input readonly  type="search" name="expense" value="${order.expense}" maxlength="50" class="required input-xlarge"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">生效时间:</label>
        <div class="controls">
            <input readonly  type="search" name="effectTime" maxlength="50" value="<fmt:formatDate value="${order.effectTime}" type="both" dateStyle="full"/>" class="required input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">流量充值:</label>
        <div class="controls">
            <input id="flux"   type="search" value="${order.flux}" maxlength="50" name="flux" class="required input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">充值平台:</label>
        <div class="controls">
            <input readonly name="platform" type="search" value="${order.platform}" maxlength="50" class="required input-xlarge"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">最后记录时间:</label>
        <div class="controls">
            <input readonly type="search" name="expenseTime" value="<fmt:formatDate value="${order.expenseTime}" type="both" dateStyle="full"/>" maxlength="50" class="required input-xlarge"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">到期时间:</label>
        <div class="controls">
            <input readonly type="search" name="expireTime" value="<fmt:formatDate value="${order.expireTime}" type="both" dateStyle="full"/>" maxlength="50" class="required input-xlarge"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">充值会议:</label>
        <div class="controls">
            <input readonly  type="search" name="meetName" value="${order.meetName}" maxlength="50" class="required input-xlarge"/>
        </div>
    </div>
        <div>
            <hr style="height:3px;border:none;border-top:3px double darkorange;width: 70%" />
        </div>
    </c:forEach>
        <div class="control-group">
            <label class="control-label">订单状态:</label>
            <div class="controls">
                    <input readonly id="close" type="radio" value="2" name="state" disabled>已关闭
                    <input readonly id="checked" type="radio" value="1" name="state" disabled>已到账
                    <input readonly id="unchecked" type="radio" value="0" name="state" disabled>未到账
            </div>
        </div>
    <div class="form-actions">
        <shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="修 改"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
<script type="text/javascript">
    $(function () {
        var checkObj = ${fluxOrderList.get(0).state};
        alert(checkObj)
        if (checkObj == 1) {
            $("#checked").attr("checked", "checked")
        } else if (checkObj == 0){
            $("#unchecked").attr("checked", "checked")
        }else {
            $("#close").attr("checked", "checked")
        }

        if(checkObj == 2){
            $("#flux").attr("readonly","readonly")
            $("#btnSubmit").attr("disabled", "disabled");
        }
    })


</script>
</html>

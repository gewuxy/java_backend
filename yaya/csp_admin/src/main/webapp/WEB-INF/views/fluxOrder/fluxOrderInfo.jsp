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

    <input type="hidden" name="id" value="${fluxOrderList.get(0).id}"/>
    <input type="hidden" name="userId" value="${fluxOrderList.get(0).userId}"/>
    <div class="control-group">
        <label class="control-label">用户名:</label>
        <div class="controls">
            <input readonly type="search" name="username" value="${fluxOrderList.get(0).username}" maxlength="50" class="required input-xlarge"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">订单号:</label>
        <div class="controls">
            <input readonly  type="search" name="tradeId" value="${fluxOrderList.get(0).tradeId}" maxlength="50" class="required input-xxlarge"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">流量总数:</label>
            <div class="controls">
                <input readonly  type="search" name="fluxTotal" value="${flux}" maxlength="50" class="required input-xlarge"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">充值平台:</label>
        <div class="controls">
            <input readonly name="platform" type="search" value="${fluxOrderList.get(0).platform}" maxlength="50" class="required input-xlarge"/>
        </div>
    </div>
    <div>
        <hr style="height:3px;border:none;border-top:3px double darkorange;width: 70%" />
    </div>
    <c:forEach items="${fluxOrderList}" var="order">
    <div class="control-group">
        <label class="control-label">流量充值:</label>
        <div class="controls">
            <input id="flux"   type="search" value="${order.flux}" maxlength="50" name="flux" class="required input-xlarge">
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
                    <input  id="close" type="radio" value="2" name="state" disabled>已关闭
                    <input  id="checked" type="radio" value="1" name="state" disabled>已到账
                    <input  id="unchecked" type="radio" value="0" name="state" disabled>未到账
            </div>
        </div>
    <div class="form-actions">
        <shiro:hasPermission name="csp:order:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="修 改"/>
        </shiro:hasPermission>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
<script type="text/javascript">
    $(document).ready(function() {
        initFormValidate();
    });

    $(function () {
        var checkObj = ${fluxOrderList.get(0).state};
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

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
    <li  class="active"><a href="#">流量订单详情</a></li>
</ul>
<form id="inputForm" method="post" class="form-horizontal" action="${ctx}/csp/order/update">

    <input type="hidden" name="id" value="${fluxOrder.id}"/>
    <input type="hidden" name="userId" value="${fluxOrder.userId}"/>
    <div class="control-group">
        <label class="control-label">用户名:</label>
        <div class="controls">
            <input readonly type="search" name="nickName" value="${nickName}" maxlength="50" class="required input-xlarge"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">订单号:</label>
        <div class="controls">
            <input readonly  type="search" name="tradeId" value="${fluxOrder.tradeId}" maxlength="50" class="required input-xxlarge"/>
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
            <%--<input readonly name="platform" type="search" value="${fluxOrderList.get(0).platform}" maxlength="50" class="required input-xlarge"/>--%>

            <select readonly id="platform" name="platform" style="width: 200px; background-color: #EEEEEE;" disabled="disabled">
                <option value=""/>-- 请选择 --
                <option value="wx_pub_qr "/>微信公众扫码支付
                <option value="alipay_pc_direct"/>支付宝即时到账PC端
                <option value="applepay_upacp"/>ApplePay_银联
                <option value="alipay"/>支付宝
                <option value="paypal"/>PayPal
                <option value="upacp"/>银联
                <option value="upacp_pc"/>银联PC端
                <option value="wx"/>微信
                <option value="wx_wap"/>微信移动端
            </select>
            <script>
                document.getElementById("platform").value="${fluxOrder.platform}";
            </script>
        </div>
    </div>
    <%--<c:forEach items="${fluxOrderList}" var="order">--%>
    <div class="control-group">
        <label class="control-label">流量充值:</label>
        <div class="controls">
            <input id="flux" readonly  type="search" value="${fluxOrder.flux}" maxlength="50" name="flux" class="required input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">充值会议:</label>
        <div class="controls">
            <input readonly  type="search" name="meetName" value="${fluxOrder.meetName}" maxlength="50" class="required input-xlarge"/>
        </div>
    </div>
    <%--</c:forEach>--%>
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
    $(function () {
        var checkObj = ${fluxOrder.state};
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

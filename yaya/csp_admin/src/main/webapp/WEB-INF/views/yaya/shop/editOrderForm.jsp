<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2018/1/26
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>编辑订单</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

</head>
<body>
    <ul class="nav nav-tabs">
        <li><a href="${ctx}/yaya/shop/order/list">订单列表</a></li>
        <li class="active"><a href="${ctx}/yaya/shop/order/edit">编辑订单</a></li>
    </ul>

    <form id="inputForm" action="${ctx}/yaya/shop/order/save" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${order.id}"/>
        <div class="control-group">
            <label class="control-label">订单号:</label>
            <div class="controls">
                <input type="search" name="orderNo" value="${order.orderNo}" maxlength="50" class="required input-xlarge"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">收货人:</label>
            <div class="controls">
                <input type="search" name="receiver" value="${order.receiver}" maxlength="50" class="required input-xlarge"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">手机:</label>
            <div class="controls">
                <input type="search" name="phone" value="${order.phone}" maxlength="50" class="required input-xlarge"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">邮政编码:</label>
            <div class="controls">
                <input type="search" name="postCode" value="${order.postCode}" maxlength="50" class="required input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">订单状态:</label>
            <div class="controls">
                <select id="status" name="status" style="width: 100px;">
                    <option value="${order.status}"/>${order.statusName}
                    <option value="0"/>待处理
                    <option value="1"}/>已接受订单
                    <option value="2"/>已发货
                    <option value="3"/>已签收
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">物流单号:</label>
            <div class="controls">
                <input type="search" name="postNO" value="${order.postNO}" maxlength="50" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">物流单位:</label>
            <div class="controls">
                <input type="search" name="postUnit" value="${order.postUnit}" maxlength="11" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">配送方式:</label>
            <div class="controls">
                <input type="search" name="postType" value="${order.postType}" maxlength="8" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">省份:</label>
            <div class="controls">
                <input type="search" name="province" value="${order.province}" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">地址:</label>
            <div class="controls">
                <input type="search" name="address" value="${order.address}" />
            </div>
        </div>

        <div class="form-actions">
            <shiro:hasPermission name="yaya:shopOrder:edit">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
            </shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </div>
    </form>

</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2018/1/26
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品订单列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <script>
        function changeStatus(id) {
            var status = $('#mySelect_'+id).find('option:selected').val();

            $.ajax({
                url:'${ctx}/yaya/shop/change/status',
                data:{"id":id,"status":status,"type":"order"},
                dataType:"json",
                type:"post",
                success:function (data) {
                    if(data.code == "0"){
                        layer.msg("更新成功");
                    }else{
                        layer.msg("修改订单状态失败，请重试");
                    }
                }
            });

        }
    </script>
</head>

<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="${ctx}/yaya/shop/order/list">订单列表</a></li>
        <%--<li><a href="${ctx}/yaya/shop/order/edit">编辑订单</a></li>--%>
    </ul>

    <%@include file="/WEB-INF/include/message.jsp"%>
    <form id="pageForm" name="pageForm" action="${ctx}/yaya/shop/order/list" method="post">
        <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
        <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
        <input  name="searchKey" type="hidden" value="${searchKey}"/>
        <input  name="status" type="hidden" value="${status}"/>
    </form>

    <form id="searchForm" method="post" action="${ctx}/yaya/shop/order/list" class="breadcrumb form-search">
        <input placeholder="订单号/手机号/物流单号" value="${searchKey}" size="40"  type="search" name="searchKey" maxlength="50" class="required"/>
        &nbsp;&nbsp;
        <select name="status" style="width: 150px;">
            <option value="">订单状态</option>
            <option value="0" ${status==0 ? 'selected':''}>待处理</option>
            <option value="1" ${status==1 ? 'selected':''}>已接受</option>
            <option value="2" ${status==2 ? 'selected':''}>已发货</option>
            <option value="3" ${status==3 ? 'selected':''}>已签收</option>
        </select>
        &nbsp;&nbsp;
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    </form>

    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead><tr><th>商品</th><th>订单号</th><th>手机号</th><th>物流单号</th><th>物流单位</th><th>订单状态</th><th>接收人</th><th>操作</th></tr></thead>
        <tbody>
        <c:if test="${not empty page.dataList}">
            <c:forEach items="${page.dataList}" var="o">
                <tr>
                    <td>${o.name}</td>
                    <td>${o.orderNo}</td>
                    <td>${o.phone}</td>
                    <td>${o.postNo}</td>
                    <td>${o.postUnit}</td>
                    <td>
                        <select id="mySelect_${o.id}" onchange="changeStatus(${o.id})">
                            <option value="0" ${o.status==0 ? 'selected':''}>待处理</option>
                            <option value="1" ${o.status==1 ? 'selected':''}>已接受</option>
                            <option value="2" ${o.status==2 ? 'selected':''}>已发货</option>
                            <option value="3" ${o.status==3 ? 'selected':''}>已签收</option>
                        </select>

                    </td>
                    <td>${o.receiver}</td>
                    <td>
                        <shiro:hasPermission name="yaya:shopOrder:edit">
                            <a href="${ctx}/yaya/shop/order/edit?id=${o.id}">修改</a>&nbsp;&nbsp;
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
</html>

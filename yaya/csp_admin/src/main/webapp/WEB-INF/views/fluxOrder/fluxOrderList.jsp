<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>流量订单列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <!--新增的-->
    <link rel="stylesheet" href="${ctxStatic}/bootstrap/added/admin-style.css">
    <link href="${ctxStatic}/bootstrap/added/daterangepicker.css" rel="stylesheet">

    <script src="${ctxStatic}/bootstrap/added/moment.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/bootstrap/added/jquery.daterangepicker.js"></script>
    <script src="${ctxStatic}/bootstrap/added/echarts.min.js"></script>
</head>
<body>
<h3 class="page-title">资金概况</h3>
<div class="top-info clearfix">
    <div class="row-fluid ">
        <div <c:if test="${type == 0}"> class="row-span span6 hot "</c:if><c:if test="${type == 1}"> class="row-span span6 "</c:if> >
            <a href="${ctx}/csp/order/list?type=0" type="0" class="type">
                <h6 class="title">人民币</h6>
                <p><strong class="price"> <fmt:formatNumber type="number" value="${rmb }" pattern="0.00" maxFractionDigits="2"/></strong></p>
            </a>
        </div>
        <div <c:if test="${type == 0}"> class="row-span span6 "</c:if><c:if test="${type == 1}"> class="row-span span6 hot "</c:if>  >
            <a href="${ctx}/csp/order/list?type=1" type="1" class="type">
                <h6 class="title">美元</h6>
                <p><strong class="price"> <fmt:formatNumber type="number" value="${usd }" pattern="0.00" maxFractionDigits="2"/></strong></p>
            </a>
        </div>
    </div>
</div>
<%--<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/order/list">流量订单列表</a></li>
</ul>--%>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="pageForm" name="pageForm" action="${ctx}/csp/order/list" method="post">
    <input  name="type" type="hidden" value="${type}"/>
    <input type="hidden" name="startTime" id="startTime" value="">
    <input type="hidden" name="endTime" id="endTime" value="">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
</form>
<div class="clearfix breadcrumb">
<div class="pull-right clearfix">
<form id="search" method="post" class="breadcrumb form-search">
    <input placeholder="订单号" value="${tradeId}" type="search" name="tradeId" maxlength="50" class="required"/>
    <shiro:hasPermission name="csp:order:view">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
    </shiro:hasPermission>
</form>
</div>
</div>
<span class="time-tj">
            <label for="timeA" id="timeStart">
                <input type="text" disabled="" class="timedate-input " <c:if test="${empty startTime}">placeholder="开始时间~结束时间"</c:if>
                       id="timeA" <c:if test="${not empty startTime}"> value="${startTime}~${endTime}"</c:if>>
            </label>
    </span>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>用户名</th><th>购买时间</th><th>订单状态</th><th>生效时间</th><th>订单号</th><th>流量充值</th><th>充值平台</th><th>付款金额</th><th>操作</th></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="data">
            <tr>
                <td>${data.nickName}</td>
                <td><fmt:formatDate value="${data.buyTime}" type="both" dateStyle="full"/></td>
                <td>${data.state eq 0? "未到账": data.state eq 1?"已到账":data.state eq 2?"已关闭":""}</td>
                <td><fmt:formatDate value="${data.effectTime}" type="both" dateStyle="full"/></td>
                <td>${data.tradeId}</td>
                <td>${data.flux}</td>
                <td>${data.platform eq "wx_pub_qr" ? "微信公众扫码支付" : data.platform eq "alipay_pc_direct" ? "支付宝即时到账PC端" :
                      data.platform eq "alipay" ? "支付宝" :data.platform eq "applepay_upacp" ? "ApplePay_银联":data.platform eq "paypal" ? "PayPal":
                      data.platform eq "upacp" ? "银联":data.platform eq "upacp_pc" ? "银联PC端":data.platform eq "wx" ? "微信":data.platform eq "wx_wap" ? "微信移动端":""}</td>
                <td>

                    <fmt:formatNumber type="number" value="${data.money == null? 0 : data.money }" pattern="0.00" maxFractionDigits="2"/>

                <c:if test="${type == 0}">CNY</c:if><c:if test="${type == 1}">USD</c:if>
                </td>
                <td>
                    <shiro:hasPermission name="csp:order:view">
                        <a href="${ctx}/csp/order/check?id=${data.id}">查看</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="csp:order:close">
                    <c:if test="${data.state eq 1}">
                    <a href="${ctx}/csp/order/close?id=${data.id}" id="closeOrder">关闭</a>
                    </c:if>
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
    <c:if test="${not empty page.dataList}">
        <c:if test="${empty tradeId}">
        <tfoot>
        <tr>
            <td>交易成功金额</td>
            <td colspan="8">
                <c:if test="${startTime == null && endTime == null}">
                    <c:if test="${type == 0}"><fmt:formatNumber type="number" value="${rmb }" pattern="0.00" maxFractionDigits="2"/></c:if>
                    <c:if test="${type == 1}"><fmt:formatNumber type="number" value="${usd }" pattern="0.00" maxFractionDigits="2"/></c:if>
                </c:if>
                <c:if test="${not empty queryMoney}">
                    <fmt:formatNumber type="number" value="${queryMoney }" pattern="0.00" maxFractionDigits="2"/>
                </c:if>
                <c:if test="${type == 0}">CNY</c:if><c:if test="${type == 1}">USD</c:if>
            </td>
        </tr>
        </tfoot>
        </c:if>
    </c:if>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>
</body>
<script>

    $(function(){

        $("#btnSubmit").click(function () {
            $("#selectMoney").hide()
        })

        $("#startTime").val('${startTime}');
        $("#endTime").val('${endTime}');



        //选择时间空间加载
        $(".callTimedate").on('click',function(){
            $('#timeStart').trigger('focus');
        });
        $('#timeStart').dateRangePicker({
            singleMonth: true,
            showShortcuts: false,
            showTopbar: false,
            startOfWeek: 'monday',
            separator : ' ~ ',
            format: 'YYYY/MM/DD HH:mm',
            autoClose: false,
            time: {
                enabled: true
            }
        }).bind('datepicker-first-date-selected', function(event, obj){
            /*首次点击的时间*/
            console.log('first-date-selected',obj);
        }).bind('datepicker-change',function(event,obj){
            /* This event will be triggered when second date is selected */
            console.log('change',obj);
            $(this).find('input').val(obj.value);
            var timeArray = obj.value.split(' ~ ');
            var startTime = timeArray[0];
            var endTime = timeArray[1];
            $("#startTime").val(startTime);
            $("#endTime").val(endTime);

            window.location.href="${ctx}/csp/order/list?startTime=" + startTime + "&endTime=" + endTime + "&type=" + ${type};
            // {
            // 		date1: (开始时间),
            // 		date2: (开始时间),
            //	 	value: "2013-06-05 00:00 to 2013-06-07 00:00"
            // }
        });


        $("#search").click(function () {
            var id = $("#id").val();
            if(id == '' || id == undefined){
                layer.msg("请输入订单号");
            }else{
                window.location.href="${ctx}/csp/order/list?id=" + id + "&rmb=" + '${rmb}' + "&usd=" + '${usd}' + "&type=" + ${type};
            }
        });

    });

</script>
</html>

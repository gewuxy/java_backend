<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/21/021
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>标题根据实际情况修改</title>
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
            <a href="${ctx}/sys/package/stats/home?type=0" type="0" class="type">
                <h6 class="title">人民币</h6>
                <p><strong class="price"> <fmt:formatNumber type="number" value="${rmb }" pattern="0.00" maxFractionDigits="2"/></strong></p>
            </a>
        </div>
        <div <c:if test="${type == 0}"> class="row-span span6 "</c:if><c:if test="${type == 1}"> class="row-span span6 hot "</c:if>  >
            <a href="${ctx}/sys/package/stats/home?type=1" type="1" class="type">
                <h6 class="title">美元</h6>
                <p><strong class="price"> <fmt:formatNumber type="number" value="${usd }" pattern="0.00" maxFractionDigits="2"/></strong></p>
            </a>
        </div>
    </div>
</div>


<div class="clearfix breadcrumb">
    <div class="pull-right clearfix">
        <form id="searchForm" method="post" class=" form-search" style="margin-bottom:0;">
            <input placeholder="订单号" value="" size="40"  type="search" id="tradeId" name="tradeId" maxlength="50" class="required"/>
            <input id="search" class="btn btn-primary" type="button" value="查询"/>
        </form>
    </div>

    <c:if test="${not empty page.dataList}">
        <div class="clearfix ">
            <div class="pull-left inputTime-item">
                <input class="btn btn-primary" type="button" id="export" value="导出Excel表格"/>
            </div>
        </div>
    </c:if>

</div>


<span class="time-tj">
            <label for="timeA" id="timeStart">
                <input type="text" disabled="" class="timedate-input " <c:if test="${empty startTime}">placeholder="开始时间~结束时间"</c:if>
                       id="timeA" <c:if test="${not empty startTime}"> value="${startTime}~${endTime}"</c:if>>
            </label>
</span>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>订单号</th>
        <th>用户昵称</th>
        <th>国内/海外</th>
        <th>付款渠道</th>
        <th>购买日期</th>
        <th>购买套餐</th>
        <th>购买天数</th>
        <th>付款金额</th>
        <th>状态</th>
        <th>备注</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.dataList}" var="order">
        <c:if test="${not empty order}">
            <tr >
                <td>${order.id}</td>
                <td>${order.nickname}</td>
                <td>${order.abroad == 1 ? "海外":"国内"}</td>
                <td>
                    <c:if test="${fn:contains(order.platForm , 'alipay') }">支付宝</c:if>
                    <c:if test="${fn:contains(order.platForm , 'wx')}">微信</c:if>
                    <c:if test="${fn:contains(order.platForm , 'upacp')}">银联</c:if>
                    <c:if test="${fn:contains(order.platForm , 'paypal')}">paypal</c:if>
                </td>
                <td><fmt:formatDate value="${order.createTime}" pattern="yyyyMMdd"></fmt:formatDate> </td>
                <td><c:if test="${order.packageId == 2}">高级版</c:if><c:if test="${order.packageId == 3}">专业版</c:if> </td>
                <td><c:if test="${order.packageType == 0}">${order.num}个月</c:if><c:if test="${order.packageType == 1}">${order.num}年</c:if> </td>
                <td>
                    <c:if test="${not empty order.money}">
                        <fmt:formatNumber type="number" value="${order.money }" pattern="0.00" maxFractionDigits="2"/>
                    </c:if>
                    <c:if test="${type == 0}">CNY</c:if><c:if test="${type == 1}">USD</c:if>
                </td>
                <td><c:if test="${order.status == 1}">交易成功</c:if><c:if test="${order.status == 0}">待付款</c:if> </td>
                <td><input type="text" value="${order.remark}">&nbsp;<a href="#" onclick="remark(this,'${order.id}')">备注</a></td>
            </tr>
        </c:if>
    </c:forEach>
    <c:if test="${ empty page.dataList}">
        <tr>
            <td colspan="9">没有查询到数据</td>
        </tr>
    </c:if>
    </tbody>
    <c:if test="${not empty page.dataList && empty search}">
        <tfoot>
        <tr >
            <td>交易成功金额</td>
            <td colspan="8">
                <c:if test="${empty successSum}">
                    <c:if test="${type == 0}"><fmt:formatNumber type="number" value="${rmb }" pattern="0.00" maxFractionDigits="2"/></c:if>
                    <c:if test="${type == 1}"><fmt:formatNumber type="number" value="${usd }" pattern="0.00" maxFractionDigits="2"/></c:if>
                </c:if>
                <c:if test="${not empty successSum}">
                    <fmt:formatNumber type="number" value="${successSum }" pattern="0.00" maxFractionDigits="2"/>
                </c:if>
                <c:if test="${type == 0}">CNY</c:if><c:if test="${type == 1}">USD</c:if>
            </td>
        </tr>
        </tfoot>
    </c:if>

</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>
<form id="pageForm" name="pageForm" method="post" action="${ctx}/sys/package/stats/home">
    <input type="hidden" name="type"value="${type}">
    <input type="hidden" name="pageNum">
    <input type="hidden" name="startTime" id="startTime" value="">
    <input type="hidden" name="endTime" id="endTime" value="">
</form>
<script>
    $(function(){

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

            window.location.href="${ctx}/sys/package/stats/home?startTime=" + startTime + "&endTime=" + endTime + "&type=" + ${type};
            // {
            // 		date1: (开始时间),
            // 		date2: (开始时间),
            //	 	value: "2013-06-05 00:00 to 2013-06-07 00:00"
            // }
        });


        $("#search").click(function () {
            var tradeId = $("#tradeId").val();
            if(tradeId == '' || tradeId == undefined){
                layer.msg("请输入订单号");
            }else{
                window.location.href="${ctx}/sys/package/stats/search?tradeId=" + tradeId + "&rmb=" + '${rmb}' + "&usd=" + '${usd}' + "&type=" + ${type};
            }
        });

        $("#export").click(function () {
            window.location.href = "${ctx}/sys/package/stats/export?type=" + '${type}' + "&startTime=" + '${startTime}' + "&endTime=" + '${endTime}';
        });

    });

    //修改备注
    function remark(obj,tradeId){
        var remark = $(obj).parent().find("input").val();
        $.ajax({
            url:'${ctx}/sys/package/stats/remark',
            data:{"tradeId":tradeId,"remark":remark},
            dataType:"json",
            type:"post",
            success:function (data) {
                if(data.code == "0"){
                    layer.msg("更新成功");
                }else{
                    layer.msg(data.err);
                }
            }
        });
    }
</script>

</body>
</html>

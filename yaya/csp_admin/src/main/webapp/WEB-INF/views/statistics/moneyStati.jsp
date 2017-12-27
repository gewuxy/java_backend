<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>资金统计</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link href="${ctxStatic}/bootstrap/added/admin-style.css" type="text/css" rel="stylesheet" />
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/user/statistics/register">资金统计</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<h3 class="page-title">总进账</h3>
<div class="top-info clearfix">
    <div class="row-fluid ">
        <div class="row-span span6 hot ">
            <a href="/routes_pages/userList.html">
                <h4 class="title">人民币</h4>
                <p><strong class="price">${rmb}</strong></p>
            </a>
        </div>
        <div class="row-span span6">
            <a href="/routes_pages/userList.html">
                <h4 class="title">海外</h4>
                <p><strong class="price">${usd}</strong></p>
            </a>
        </div>
    </div>
</div>
<ul class="nav nav-tabs">
    <li class="active"><a href="/routes_pages/moneyStats.html">资金入账</a></li>
    <li><a href="/routes_pages/moneyStats-02.html">率比统计</a></li>
</ul>
<form method="post" class="breadcrumb ">
    <div class="pull-left inputTime-item">
        <ul class="nav nav-pills">
            <li class="active"><a href="javascript:;">日度</a></li>
            <li><a href="javascript:;">月度</a></li>
            <li><a href="javascript:;">年度</a></li>
        </ul>
        <span class="time-tj">
            <label for="timeA" id="timeStart">
                <input type="text" disabled="" class="timedate-input " <c:if test="${empty startTime}">placeholder="开始时间~结束时间"</c:if>
                       id="timeA" <c:if test="${not empty startTime}"> value="${startTime}~${endTime}"</c:if>>
            </label>
        </span>
    </div>
</form>
<c:if test="${not empty page.dataList}">
    <div class="clearfix ">
        <div class="pull-left inputTime-item">
            <input class="btn btn-primary" type="button" id="export" value="导出Excel表格"/>
        </div>
    </div>
</c:if>
<div class="clearfix item-margin-bottom">
    <div id="echarts-2" class="echarts echarts-3"></div>
</div>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>日期</th>
        <th>支付宝</th>
        <th>微信支付</th>
        <th>银联</th>
        <th>付款总额</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${not empty list}">
        <c:forEach items="${list}" var="list">
            <tr>
                <td><fmt:formatDate value="${list.createTime}" pattern="yyyy-MM-dd"/></td>
                <td>${list.alipayWap}</td>
                <td>${list.wxPubQr}</td>
                <td>${list.upacpWap}</td>
                <td>${list.money}</td>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${empty list}">
        <tr>
            <td colspan="5">没有查询到数据</td>
        </tr>
    </c:if>
    </tbody>
    <tfoot>
    <tr>
        <td>合计</td>
        <td colspan="4">${total}</td>
    </tr>
    </tfoot>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>
<form id="pageForm" name="pageForm" method="post" action="${ctx}/sys/package/stats/home">
    <input type="hidden" name="type"value="${type}">
    <input type="hidden" name="pageNum">
    <input type="hidden" name="startTime" id="startTime" value="">
    <input type="hidden" name="endTime" id="endTime" value="">
</form>
<script src="${ctxStatic}/bootstrap/added/echarts.min.js" type="text/javascript"></script>
<script>
    //图表加载
    var dom = document.getElementById('echarts-2');
    var myChart = echarts.init(dom);

    function fillData(list) {
        $.each(list,function(i) {
            var dataArray = new Array();
            var moneyArray = new Array();
            dataArray.push(list[i].weiXinCount);
            dataArray.join(",");
            moneyArray.push(list[i].facebookCount);
            moneyArray.join(",");
        });
        option = null;
        option = {
            color: ['#3398DB'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: ['资金入账']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    name: '日期',
                    type: 'category',
                    data: ['01-01', '01-02', '01-03', '01-04', '01-05', '01-06', '01-07'],
                }
            ],
            yAxis: [
                {
                    name: '总额',
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '资金入账',
                    type: 'bar',
                    barWidth: '60%',
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    },
                    data: [10, 52, 200, 334, 390, 330, 220]
                }
            ]
        };

        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
    }

    $(function(){
        $("#startTime").val('${startTime}');
        $("#endTime").val('${endTime}');

        //获取表格数据
        $.get('${ctx}/csp/stati/echarts/data',{}, function (data) {
            if (data.code == 0){
                fillData(data.data);
                dateArray = data.data.dateCount;
            }else{
                layer.msg("获取数据失败");
            }
        },'json');


        //选择时间空间加载
        $(".callTimedate").on('click', function () {
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
        });
    });
</script>
</body>
</html>

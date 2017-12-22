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
                <p><strong class="price">23546</strong></p>
            </a>
        </div>
        <div class="row-span span6">
            <a href="/routes_pages/userList.html">
                <h4 class="title">海外</h4>
                <p><strong class="price">235462</strong></p>
            </a>
        </div>
    </div>
</div>
<ul class="nav nav-tabs">
    <li class="active"><a href="/routes_pages/moneyStats.html">资金入账</a></li>
    <li ><a href="/routes_pages/moneyStats-02.html">率比统计</a></li>
</ul>

<form  method="post" class="breadcrumb ">
    <div class="pull-left inputTime-item">
        <ul class="nav nav-pills">
            <li class="active"><a href="javascript:;">日度</a></li>
            <li><a href="javascript:;">月度</a></li>
            <li><a href="javascript:;">年度</a></li>
        </ul>
        <span class="time-tj">
            <label for="timeA" id="timeStart">
                <input type="text" disabled="" class="timedate-input " placeholder="" id="timeA" value="2017/05/09 18:15 ~ 2017/05/18 18:15">
            </label>
        </span>
    </div>
</form>
<div class="clearfix inputButton-item">
    <div class="pull-left inputTime-item">
        <input class="btn btn-primary" type="button" value="导出Excel表格" onclick="window.location.href = '${ctr}/csp/sys/user/addAdmin'"/>
    </div>
</div>
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
    <tr>
        <td>2017-01-01</td>
        <td>666</td>
        <td>666</td>
        <td>1</td>
        <td>1333</td>
    </tr>
    <tr>
        <td colspan="5">没有查询到数据</td>
    </tr>
    </tbody>
    <tfoot>
    <tr>
        <td>合计</td>
        <td colspan="4">1332</td>
    </tr>
    </tfoot>
</table>
<script src="${ctxStatic}/bootstrap/added/echarts.min.js" type="text/javascript"></script>
<script>
    $(function(){

        //图表加载
        var dom = document.getElementById('echarts-2');
        var myChart = echarts.init(dom);
        var app = {};
        option = null;
        option = {
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data:['资金入账']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    name: '日期',
                    type : 'category',
                    data : ['01-01', '01-02', '01-03', '01-04', '01-05', '01-06', '01-07'],
                }
            ],
            yAxis : [
                {
                    name: '总额',
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'资金入账',
                    type:'bar',
                    barWidth: '60%',
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    },
                    data:[10, 52, 200, 334, 390, 330, 220]
                }
            ]
        };

        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }


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
            // {
            // 		date1: (开始时间),
            // 		date2: (开始时间),
            //	 	value: "2013-06-05 00:00 to 2013-06-07 00:00"
            // }
        });


    });
</script>
</body>
</html>

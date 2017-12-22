<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/18/018
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>敬信药草园--运营管理系统</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

    <!--新增的-->
    <link rel="stylesheet" href="${ctxStatic}/bootstrap/added/admin-style.css">
    <link href="${ctxStatic}/bootstrap/added/daterangepicker.css" rel="stylesheet">

    <script src="${ctxStatic}/bootstrap/added/moment.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/bootstrap/added/jquery.daterangepicker.js"></script>
    <script src="${ctxStatic}/bootstrap/added/echarts.min.js"></script>

</head>
<body>

<h3 class="page-title">昨天新增</h3>
<div class="top-info clearfix">
    <div class="row-fluid ">
        <div class="row-span span6 hot ">
            <a href="/routes_pages/userList.html">
                <h4 class="title">海内</h4>
                <p><strong class="price">${home}</strong></p>
            </a>
        </div>
        <div class="row-span span6">
            <a href="/routes_pages/userList.html">
                <h4 class="title">海外</h4>
                <p><strong class="price">${abroad}</strong></p>
            </a>
        </div>
    </div>
</div>
<ul class="nav nav-tabs">
    <li class="active"><a href="javascript:;">新增统计</a></li>
</ul>

<form  method="post" class="breadcrumb ">
    <div class="pull-left inputTime-item">
        <ul class="nav nav-pills">
            <li class="active"><a href="javascript:;">日</a></li>
            <li><a href="javascript:;">周</a></li>
            <li><a href="javascript:;">月</a></li>
            <li><a href="javascript:;">季</a></li>
            <li><a href="javascript:;">年</a></li>
        </ul>
        <span class="time-tj">
            <label for="timeA" id="timeStart">
                <input type="text" disabled="" class="timedate-input " placeholder="" id="timeA" value="${startTime} ~ ${endTime}">
            </label>
        </span>
    </div>
</form>
<div class="clearfix inputButton-item">
    <div class="pull-left inputTime-item">
        <input class="btn btn-primary" type="button" value="导出新增用户" onclick="window.location.href = '${ctr}/csp/sys/user/addAdmin'"/>
        <input class="btn btn-primary" type="button" value="导出新增渠道" onclick="window.location.href = '${ctr}/csp/sys/user/addAdmin'"/>
        <input class="btn btn-primary" type="button" value="导出注册转化率" onclick="window.location.href = '${ctr}/csp/sys/user/addAdmin'"/>
    </div>
</div>
<div class="clearfix item-margin-bottom">
    <div id="echarts-2" class="echarts echarts-3"></div>
    <%--<div id="echarts-3" class="echarts echarts-3"></div>--%>
    <div id="echarts-4" class="echarts echarts-3"></div>
</div>
<script>
    $(function(){

        $.get('${ctx}/sys/register/stats/newly/static',{"startTime":"2017/10/01 00:00","endTime":"2017/11/30 23:59","location":1,"grain":0}, function (data) {
            if (data.code == 0){

            }
        },'json');


        //图表加载
        var dom = document.getElementById('echarts-2');
//        var dom2 = document.getElementById('echarts-3');
        var dom3 = document.getElementById('echarts-4');
        var myChart = echarts.init(dom);
//        var myChart2 = echarts.init(dom2);
        var myChart3 = echarts.init(dom3);
        var app = {};
        option = null;
        option = {
            title: {
                text: ''
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['微博','微信','手机','邮箱','数字平台']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '20%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                name: '日期',
                type: 'category',
                data: ['01-11','01-12','01-13','01-14','01-15','01-16','01-17']
            },
            yAxis: {
                name: '单位',
                type: 'value',
                max : 200,
                min:0
            },
            series: [
                {
                    name:'微博',
                    type: 'bar',
                    stack: '单位',
                    data:[10, 30, 10, 13, 90, 23, 95]
                },
                {
                    name:'微信',
                    type:'bar',
                    stack: '单位',
                    data:[10, 20, 19, 23, 29, 33, 60]
                },
                {
                    name:'手机',
                    type:'bar',
                    stack: '单位',
                    data:[10, 20, 19, 23, 29, 33, 60]
                },
                {
                    name:'邮箱',
                    type:'bar',
                    stack: '单位',
                    data:[0, 20, 20, 15, 19, 33, 40]
                },
                {
                    name:'数字平台',
                    type:'bar',
                    stack: '单位',
                    data:[10, 20, 19, 23, 29, 33, 60]
                }
            ]
        };



        option3 = {
            title: {
                text: ''
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['注册转化率']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '20%',
                containLabel: true
            },
            xAxis: {
                name: '日期',
                type: 'category',
                data: ['01-11','01-12','01-13','01-14','01-15','01-16','01-17']
            },
            yAxis: {
                name: '百分比',
                type: 'value',
                max : 100,
                min:0
            },
            series: [
                {
                    name:'注册转化率',
                    type:'line',
                    data:[0, 30, 10, 13, 90, 23, 95]
                }
            ]
        };






        if (option && typeof option === "object") {
            myChart.setOption(option, true);
//            myChart2.setOption(option2, true);
            myChart3.setOption(option3, true);
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


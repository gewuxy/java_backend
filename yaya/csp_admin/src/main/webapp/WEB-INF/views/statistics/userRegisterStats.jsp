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
        <div <c:if test="${location == 0}">class="row-span span6 hot "</c:if> <c:if test="${location == 1}">class="row-span span6  "</c:if>>
            <a href="${ctx}/sys/register/stats/home?location=0">
                <h4 class="title">海内</h4>
                <p><strong class="price">${home}</strong></p>
            </a>
        </div>
        <div <c:if test="${location == 1}">class="row-span span6 hot "</c:if> <c:if test="${location == 0}">class="row-span span6  "</c:if>>
            <a href="${ctx}/sys/register/stats/home?location=1">
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
            <li class="active"><a href="javascript:;" grain="0">日</a></li>
            <li><a href="javascript:;" grain="1" >周</a></li>
            <li><a href="javascript:;" grain="2">月</a></li>
            <li><a href="javascript:;" grain="3">季</a></li>
            <li><a href="javascript:;" grain="4">年</a></li>
        </ul>
        <span class="time-tj">
            <label for="timeA" id="timeStart">
                <input type="text" disabled="" class="timedate-input " placeholder="" id="timeA" value="${startTime} ~ ${endTime}">
                <input type="hidden" id="startTime" value="${startTime}">
                <input type="hidden" id="endTime" value="${endTime}">
            </label>
        </span>
    </div>
</form>
<div class="clearfix inputButton-item">
    <div class="pull-left inputTime-item">
        <input class="btn btn-primary" type="button" value="导出新增用户" id="export"/>
    </div>
</div>
<div class="clearfix item-margin-bottom">
    <div id="echarts-2" class="echarts echarts-3"></div>

</div>
<script>

    //图表加载
    var dom = document.getElementById('echarts-2');
    var myChart = echarts.init(dom);
    option = null;


    $(function(){

        $.get('${ctx}/sys/register/stats/newly/static',{"startTime":"${startTime}","endTime":"${endTime}","location":"${location}","grain":0}, function (data) {
            if (data.code == 0){
                fillData(data.data);
                dateArray = data.data.dateCount;
            }else{
                layer.msg("获取数据失败");
            }
        },'json');


        $("#export").click(function () {
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
            var grain = $(".nav-pills .active").children().attr("grain");
            var location = '${location}';
            window.location.href = "${ctr}/sys/register/stats/export?location=" + location + "&startTime=" + startTime + "&endTime=" + endTime + "&grain=" + grain;

        });

        $(".nav-pills li a").click(function () {
            var grain = $(this).attr("grain");
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
            var location = '${location}';
            $(".nav-pills li").removeAttr("class","active");
            $(this).parent().attr("class","active");
            $.get('${ctx}/sys/register/stats/newly/static',{"startTime":startTime,"endTime":endTime,"location":location,"grain":grain}, function (data) {
                if (data.code == 0){
                    fillData(data.data);
                    dateArray = data.data.dateCount;
                }else{
                    layer.msg("获取数据失败");
                }
            },'json');
        });


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
            format: 'YYYY/MM/DD ',
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
            var grain = $(".nav-pills .active").children().attr("grain");
            var location = '${location}';
            $.get('${ctx}/sys/register/stats/newly/static',{"startTime":startTime,"endTime":endTime,"location":location,"grain":grain}, function (data) {
                if (data.code == 0){
                    fillData(data.data);
                    dateArray = data.data.dateCount;
                }else{
                    layer.msg("获取数据失败");
                }
            },'json');
        });

    });


    function fillData(data) {
        var dateArray = new Array();
        var weiXinArray = new Array();
        var weiBoArray = new Array();
        var facebookArray = new Array();
        var twitterArray = new Array();
        var mobileArray = new Array();
        var emailArray = new Array();
        var yaYaArray = new Array();
        var list = data.list;
        $.each(list,function(i) {
            weiXinArray.push(list[i].weiXinCount);
            weiXinArray.join(",");
            weiBoArray.push(list[i].weiBoCount);
            weiBoArray.join(",");
            facebookArray.push(list[i].facebookCount);
            facebookArray.join(",");
            twitterArray.push(list[i].twitterCount);
            twitterArray.join(",");
            mobileArray.push(list[i].mobileCount);
            mobileArray.join(",");
            emailArray.push(list[i].emailCount);
            emailArray.join(",");
            yaYaArray.push(list[i].yaYaCount);
            yaYaArray.join(",");
        });

        var location = '${location}';
        if(location == 0){
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
                    data: data.dateCount
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
                        data:weiBoArray
                    },
                    {
                        name:'微信',
                        type:'bar',
                        stack: '单位',
                        data:weiXinArray
                    },
                    {
                        name:'手机',
                        type:'bar',
                        stack: '单位',
                        data:mobileArray
                    },
                    {
                        name:'邮箱',
                        type:'bar',
                        stack: '单位',
                        data:emailArray
                    },
                    {
                        name:'数字平台',
                        type:'bar',
                        stack: '单位',
                        data:yaYaArray
                    }
                ]
            };
        }else{
            option = {
                title: {
                    text: ''
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:['facebook','twitter','手机','邮箱','数字平台']
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
                    data: data.dateCount
                },
                yAxis: {
                    name: '单位',
                    type: 'value',
                    max : 200,
                    min:0
                },
                series: [
                    {
                        name:'facebook',
                        type: 'bar',
                        stack: '单位',
                        data:facebookArray
                    },
                    {
                        name:'twitter',
                        type:'bar',
                        stack: '单位',
                        data:twitterArray
                    },
                    {
                        name:'手机',
                        type:'bar',
                        stack: '单位',
                        data:mobileArray
                    },
                    {
                        name:'邮箱',
                        type:'bar',
                        stack: '单位',
                        data:emailArray
                    },
                    {
                        name:'数字平台',
                        type:'bar',
                        stack: '单位',
                        data:yaYaArray
                    }
                ]
            };
        }


        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }

    }


</script>

</body>
</html>


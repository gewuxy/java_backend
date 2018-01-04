<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>资金统计</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link href="${ctxStatic}/bootstrap/added/admin-style.css" type="text/css" rel="stylesheet" />
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/stats/money">资金统计</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<h3 class="page-title">总进账</h3>
<div class="top-info clearfix">
    <div class="row-fluid">
        <div class="row-span span6 ">
            <a href="#">
                <h4 class="title">人民币</h4>
                <p><strong class="price">${rmb}</strong></p>
            </a>
        </div>
        <div class="row-span span6">
            <a href="#">
                <h4 class="title">海外</h4>
                <p><strong class="price">${usd}</strong></p>
            </a>
        </div>
    </div>
</div>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/csp/stats/money?type=0">资金入账</a></li>
    <li><a href="${ctx}/csp/stats/money?type=1">转化率</a></li>
    <li class="active"><a href="${ctx}/csp/stats/money?type=2">套餐分布</a></li>
    <li><a href="${ctx}/csp/stats/money?type=3">续费率</a></li>
</ul>
<form method="post" class="breadcrumb ">
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
<div class="clearfix item-margin-bottom">
    <div id="echarts-2" class="echarts echarts-3"></div>
</div>
<script src="${ctxStatic}/bootstrap/added/echarts.min.js" type="text/javascript"></script>
<script>
    //图表加载
    var dom = document.getElementById('echarts-2');
    var myChart = echarts.init(dom);

    $(function () {
        $("#startTime").val('${startTime}');
        $("#endTime").val('${endTime}');
        initEcharts(0);

        //点击刷新页面
        $(".nav-pills li a").click(function () {
            var grain = $(this).attr("grain");
            $(".nav-pills li").removeAttr("class", "active");
            $(this).parent().attr("class", "active");
            initEcharts(grain);
        });

        //选择时间空间加载
        $(".callTimedate").on('click', function () {
            $('#timeStart').trigger('focus');
        });
        $('#timeStart').dateRangePicker({
            singleMonth: true,
            showShortcuts: false,
            showTopbar: false,
            endDate: '${endTime}',
            startOfWeek: 'monday',
            separator: ' ~ ',
            format: 'YYYY-MM-DD ',
            autoClose: false,
            time: {
                enabled: false
            }
        }).bind('datepicker-change', function (event, obj) {
            $(this).find('input').val(obj.value);
            var timeArray = obj.value.split(' ~ ');
            var startTime = timeArray[0];
            var endTime = timeArray[1];
            $("#startTime").val(startTime);
            $("#endTime").val(endTime);
            var grain = $(".nav-pills .active").children().attr("grain");
            initEcharts(grain);
        });
    });

    //初始化图表
    function initEcharts(grain) {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        $.get('${ctx}/csp/stats/echarts/dist', {
            "startTime": startTime,
            "endTime": endTime,
            "grain": grain
        }, function (data) {
            if (data.code == 0) {
                fillEcharts(data.data);  //加载图表
                initExport(data.data);
            } else {
                layer.msg("获取数据失败");
            }
        }, 'json');
    }

    //加载数据生成图表
    function fillEcharts(list) {
        var dataArray = new Array();
        var staArray = new Array();
        var preArray = new Array();
        var proArray = new Array();
        $.each(list, function (i) {
            dataArray.push(list[i].register_time);
            dataArray.join(",");
            staArray.push(getValue(list[i].sta));
            staArray.join(",");
            preArray.push(getValue(list[i].pre));
            preArray.join(",");
            proArray.push(getValue(list[i].pro));
            proArray.join(",");
        });
        option = null;
        option = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['标准版','高级版','专业版']
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
                data: dataArray
            },
            yAxis: {
                name: '分布百分比',
                type: 'value',
                max : 100,
                min:0
            },
            series: [
                {
                    name:'标准版',
                    type:'line',
                    data:staArray
                },
                {
                    name:'高级版',
                    type:'line',
                    data:preArray
                },
                {
                    name:'专业版',
                    type:'line',
                    data:proArray
                }
            ]

        };
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
    }

    function getValue(data) {
        if (isEmpty(data)) {
            return 0;
        }
        return Math.floor(data * 100) / 100;
    }
</script>
</body>
</html>

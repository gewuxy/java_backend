<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册统计</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/user/statistics/register">用户注册统计</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="searchForm" method="post" class="breadcrumb form-search">
    <input type="text" id="start" placeholder="点击选择开始时间">
    ——
    <input type="text" id="end" placeholder="点击选择结束时间" >
</form>
<div id="main" style="height:400px"></div>
<script type="text/javascript" src="${ctxStatic}/laydate/laydate.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/echarts.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/echarts_common.js"></script>
<script>
    var myChart = echarts.init(document.getElementById('main'));
    $(document).ready(function() {
        initLaydate("start");
        initLaydate("end");
        initBar();
        setEcharts()
    });

    function setEcharts(){
        myChart.setOption({
            xAxis: {
                data : ["1月","2月","3月","4月","5月","6月"]
            },
            series: [{
                // 根据名字对应到相应的系列
                name: '注册量',
                data:[5, 20, 40, 10, 10, 20]
            }]
        });
    }

</script>
</body>
</html>

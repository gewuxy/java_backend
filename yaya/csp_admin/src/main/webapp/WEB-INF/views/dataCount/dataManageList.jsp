<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>数据统计表</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
</head>
<script type="text/javascript" src="${ctxStatic}/laydate/laydate.js"></script>
<script type="text/javascript" src="${ctxStatic}/layer/layui.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/echarts.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/echarts_common.js"></script>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/data/list">数据统计表</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp" %>
<form id="searchForm" method="post" class="breadcrumb form-search">
    <input id="selectTime" name="registDate" class="laydate-icon" type="text" value="${registDate}"
           placeholder="选择查询时间"/>
    <input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/>
</form>
<div id="main" style="height:400px"></div>
</body>
<script>
    laydate.render({
        elem: '#selectTime'
        , type: 'year'
    });

    var myChart = echarts.init(document.getElementById('main'));

    function setData(y) {
        var params = {};
        if (y) {
            params['registDate'] = y;
        }
        $.post("${ctx}/csp/data/select", params, function (data) {
            console.log(data)
            var x_data = [];
            var y_data = [];
            for (var i = 0; i < data.length; i++) {
                x_data.push(data[i].years + "年" + data[i].months + "月");
                y_data.push(data[i].counts);
            }
            myChart.setOption({
                tooltip: {
                    trigger: 'item'
                },
                xAxis: {
                    data: x_data
                },
                yAxis: {},
                series: [{
                    name: '下载量',
                    type: 'bar',
                    data: y_data
                }]
            });
        }, "json");
    }

    $(function () {
        setData();
    });

    $("#btnSubmit").click(function () {
        var y = $("#selectTime").val();
        setData(y);
    });

</script>
</html>

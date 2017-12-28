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
            <li class="active"><a href="javascript:;" grain="0">日度</a></li>
            <li><a href="javascript:;" grain="2">月度</a></li>
            <li><a href="javascript:;" grain="4">年度</a></li>
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
    <tbody id="tableView">
    <%--<c:if test="${not empty list}">--%>
        <%--<c:forEach items="${list}" var="list">--%>
            <%--<tr>--%>
                <%--<td><fmt:formatDate value="${list.createTime}" pattern="yyyy-MM-dd"/></td>--%>
                <%--<td>${list.alipayWap}</td>--%>
                <%--<td>${list.wxPubQr}</td>--%>
                <%--<td>${list.upacpWap}</td>--%>
                <%--<td>${list.money}</td>--%>
            <%--</tr>--%>
        <%--</c:forEach>--%>
    <%--</c:if>--%>
    <%--<c:if test="${empty list}">--%>
        <%--<tr>--%>
            <%--<td colspan="5">没有查询到数据</td>--%>
        <%--</tr>--%>
    <%--</c:if>--%>
    </tbody>
    <tfoot>
    <tr>
        <td>合计</td>
        <td colspan="4">${total}</td>
    </tr>
    </tfoot>
</table>
<div id="tablePage"></div>
<script src="${ctxStatic}/bootstrap/added/echarts.min.js" type="text/javascript"></script>
<script>
    //图表加载
    var dom = document.getElementById('echarts-2');
    var myChart = echarts.init(dom);
    var abroad = '${abroad}';

    $(function(){
        $("#startTime").val('${startTime}');
        $("#endTime").val('${endTime}');

        initEcharts(0);
        initTable(0);

        //点击刷新页面
        $(".nav-pills li a").click(function () {
            var grain = $(this).attr("grain");
            $(".nav-pills li").removeAttr("class","active");
            $(this).parent().attr("class","active");
            initEcharts(grain);
            initTable(grain);
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
            format: 'YYYY-MM-DD ',
            autoClose: false,
            time: {
                enabled: false
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
            initEcharts(grain);
            initTable(grain);
        });
    });

    //初始化图表
    function initEcharts(grain) {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        $.get('${ctx}/csp/stati/echarts/data',{"startTime":startTime,"endTime":endTime,"abroad":abroad,"grain":grain}, function (data) {
            if (data.code == 0){
                console.log(data);
                fillEcharts(data.data);  //加载图表
            }else{
                layer.msg("获取数据失败");
            }
        },'json');
    }

    //初始化table
    function initTable(grain) {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        $.get('${ctx}/csp/stati/table',{"startTime":startTime,"endTime":endTime,"abroad":abroad,"grain":grain}, function (data) {
            if (data.code == 0){
                console.log(data);
                fillTable(data.data);  //加载图表
            }else{
                layer.msg("获取数据失败");
            }
        },'json');
    }


    //加载数据生成图表
    function fillEcharts(list) {
        var dataArray = new Array();
        var moneyArray = new Array();
        $.each(list,function(i) {
            dataArray.push(dateFormat(list[i].createTime));
            dataArray.join(",");
            moneyArray.push(list[i].money);
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
                    data: dataArray
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
                    data: moneyArray
                }
            ]
        };
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
    }

    //加载table
    function fillTable(data) {
        var html = '';
        if (data.dataList.length > 0) {
            $.each(data.dataList, function (n, info) {
                html += '<tr>'
                    + '<td>' + dateFormat(info.createTime) + '</td>'
                    + '<td>' + info.alipayWap + '</td>'
                    + '<td>' + info.wxPubQr + '</td>'
                    + '<td>' + info.upacpWap + '</td>'
                    + '<td>' + info.money + '</td>'
                    + '</tr>';
            });
            initPageable(data);
        } else {
            html = "<tr> <td colspan='5'>没有查询到数据</td></tr>";
        }
        $("#tableView").html(html);
    }

    //加载分页
    function initPageable(page){
        var html = "";
        if(page.pages > 1){
            html += "<div class='pagination'><ul class='pagination'>";
            html += " <li><a ";
            if(page.pageNum > 1)  html += "href ='javascript:page(1);'";
            html += ">首 页</a></li>";
            html += " <li><a ";
            if(page.pageNum > 1)  html += "href ='javascript:page(" + page.pageNum-1 + ");'";
            html += "class='disabled'>上一页</a></li>"
            html += " <li><a ";
            if(page.pageNum < page.pages)  html += "href ='javascript:page(" + page.pageNum + 1 + ");'";
            html += ">下一页</a></li>";
            html += " <li><a ";
            if(page.pageNum < page.pages)  html += "href ='javascript:page(" + page.pageNum + 1 + ");'";
            html += "class='disabled'>尾 页</a></li>"
            html += "<li><a>共" + page.total + "条数据 - " + page.pageNum + "/"+ page.pages + "</a></li>";
            html += "</ul><br></div>";
        }
        $("#tablePage").html(html);
    }
</script>
</body>
</html>

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
    <div class="row-fluid ">
        <div class="row-span span6 hot">
            <a href="#" onclick="changeAbroad(this,0);">
                <h4 class="title">人民币</h4>
                <p><strong class="price">${empty rmb ? "0.00":rmb}</strong></p>
            </a>
        </div>
        <div class="row-span span6">
            <a href="#" onclick="changeAbroad(this,1);">
                <h4 class="title">美元</h4>
                <p><strong class="price">${empty usd ? "0.00":usd}</strong></p>
            </a>
        </div>
    </div>
</div>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/stats/money?type=0">资金入账</a></li>
    <li><a href="${ctx}/csp/stats/money?type=1">转化率</a></li>
    <li><a href="${ctx}/csp/stats/money?type=2">套餐分布</a></li>
    <li><a href="${ctx}/csp/stats/money?type=3">续费率</a></li>
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
<div class="clearfix item-margin-bottom">
    <div id="echarts-2" class="echarts echarts-3"></div>
</div>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr id="trView"></tr>
    </thead>
    <tbody id="tableView"></tbody>
    <tfoot>
    <tr>
        <td>合计</td>
        <td colspan="4" id="totals"></td>
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
    var htmlRND = "<th>日期</th><th>支付宝</th><th>微信支付</th><th>银联</th><th>付款总额</th>";
    var htmlUSD = "<th>日期</th><th>paypal</th><th>付款总额</th>";

    $(function () {
        $("#startTime").val('${startTime}');
        $("#endTime").val('${endTime}');
        $("#trView").html(htmlRND);

        initEcharts(0);
        initTable(1, 0);
        //点击刷新页面
        $(".nav-pills li a").click(function () {
            var grain = $(this).attr("grain");
            $(".nav-pills li").removeAttr("class", "active");
            $(this).parent().attr("class", "active");
            initEcharts(grain);
            initTable(1, grain);
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
            /* This event will be triggered when second date is selected */
            $(this).find('input').val(obj.value);
            var timeArray = obj.value.split(' ~ ');
            var startTime = timeArray[0];
            var endTime = timeArray[1];
            $("#startTime").val(startTime);
            $("#endTime").val(endTime);
            var grain = $(".nav-pills .active").children().attr("grain");
            initEcharts(grain);
            initTable(1, grain);
        });
    });

    //初始化图表
    function initEcharts(grain) {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        $.get('${ctx}/csp/stats/echarts/money', {
            "startTime": startTime,
            "endTime": endTime,
            "abroad": abroad,
            "grain": grain
        }, function (data) {
            if (data.code == 0) {
                fillEcharts(data.data.capital);  //加载图表
                initTotal(data.data.total);
            } else {
                layer.msg("获取数据失败");
            }
        }, 'json');
    }

    //初始化table
    function initTable(pageNum, grain) {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        $.get('${ctx}/csp/stats/table', {
            "pageNum": pageNum,
            "startTime": startTime,
            "endTime": endTime,
            "abroad": abroad,
            "grain": grain
        }, function (data) {
            if (data.code == 0) {
                fillTable(data.data);  //加载图表
                initExport(data.data.dataList);
            } else {
                layer.msg("获取数据失败");
            }
        }, 'json');
    }

    //翻页
    function pageSet(pageNum) {
        var grain = $(".nav-pills .active").children().attr("grain");
        initTable(pageNum, grain);
    }

    function initTotal(total) {
        $("#totals").html(total);
    }

    //加载数据生成图表
    function fillEcharts(list) {
        var dataArray = new Array();
        var moneyArray = new Array();
        $.each(list, function (i) {
            dataArray.push(list[i].createTime);
            dataArray.join(",");
            moneyArray.push(getValue(list[i].money));
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
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    name: initCompany(),
                    type: 'category',
                    data: dataArray
                }
            ],
            yAxis: [
                {
                    name: abroad == 0 ? '单位/人民币':"单位/美元",
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
                html += '<tr>'+ '<td>' + info.createTime + '</td>';
                if(abroad == 0){
                    html +=  '<td>' + getValue(info.alipayWap) + '</td>'
                    + '<td>' + getValue(info.wxPubQr) + '</td>'
                    + '<td>' + getValue(info.upacpWap) + '</td>';
                }else{
                    html +=  '<td>' + getValue(info.paypal) + '</td>'
                }
                html +=  '<td>' + getValue(info.money) + '</td></tr>';
            });
            initPageable(data);
        } else {
            html = "<tr> <td colspan='5'>没有查询到数据</td></tr>";
        }
        $("#tableView").html(html);
    }

    //加载分页
    function initPageable(page) {
        var html = "";
        if (page.pages > 1) {
            html += "<div class='pagination'><ul class='pagination'>";
            html += " <li><a ";
            if (page.pageNum > 1) html += "href ='javascript:pageSet(1);'";
            html += ">首 页</a></li>";
            html += " <li><a ";
            if (page.pageNum > 1) {
                var bdfore = page.pageNum - 1;
                html += "href ='javascript:pageSet(" + bdfore + ");'";
            }
            html += "class='disabled'>上一页</a></li>"
            html += " <li><a ";
            if (page.pageNum < page.pages) {
                var next = page.pageNum + 1;
                html += "href ='javascript:pageSet(" + next + ");'";
            }
            html += ">下一页</a></li>";
            html += " <li><a ";
            if (page.pageNum < page.pages) html += "href ='javascript:pageSet(" + page.pageNum + 1 + ");'";
            html += "class='disabled'>尾 页</a></li>"
            html += "<li><a>共" + page.total + "条数据 - " + page.pageNum + "/" + page.pages + "</a></li>";
            html += "</ul><br></div>";
        }
        $("#tablePage").html(html);
    }

    function initCompany() {
        var grain = $(".nav-pills .active").children().attr("grain");
        if (grain == 0) {
            return "日期";
        } else if (grain == 2) {
            return "月";
        } else {
            return "年";
        }
    }

    function getValue(data) {
        if (isEmpty(data)) {
            return 0;
        }
        return Math.floor(data * 100) / 100;
    }

    //海内海外切换
    function changeAbroad(obj,value){
        $(obj).parent().addClass('hot').siblings().removeClass("hot");
        abroad = value;
        value == 0 ? $('#trView').html("").html(htmlRND):$('#trView').html("").html(htmlUSD);
        var grain = $(".nav-pills .active").children().attr("grain");
        initEcharts(grain);
        initTable(1, grain);
    }

    //加载到处数据按钮
    function initExport(data) {
        $("#exportView").remove();
        if (data.length > 0) {
            html = '<div class="clearfix inputButton-item" id="exportView"><div class="pull-left inputTime-item"><input class="btn btn-primary" type="button" value="导出入账数据" onclick="exports()"/></div></div>';
            $(".breadcrumb").after(html);
        }
    }

    //到处数据
    function exports() {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        var grain = $(".nav-pills .active").children().attr("grain");
        window.location.href = "${ctx}/csp/stats/export/money?abroad=" + abroad + "&startTime=" + startTime + "&endTime=" + endTime + "&grain=" + grain;
    }

</script>
</body>
</html>

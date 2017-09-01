<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/6/6
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户统计</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link href="${ctxStatic}/css/daterangepicker.css" rel="stylesheet">

</head>
<body>
<div class="g-main clearfix">
    <%@include file="/WEB-INF/include/stastic_header.jsp"%>

    <div class="tj-top data-tj-wrap data-tj-wrap-1 data-tj-wrap-5 margin-top-not  clearfix">
        <div class="echart-header clearfix">
            <div class="fr">
				<span class="data-time">
					<a href="javascript:;" class="time-all t-cur" tabIndex="0">全部</a>
					<a href="javascript:;" class="time-month" tabIndex="1">本月</a>
					<a href="javascript:;" class="time-week" tabIndex="2">本周</a>
				</span>
                <span class="time-tj">
                    <label for="" id="timeStart">
                        <a href="javascript:;" class="callTimedate timedate-icon" tabIndex="3">自定义</a>
                        统计时间：
                        <input id="times" type="text" disabled="" class="timedate-input " placeholder="" value="">
                    </label>
                </span>
            </div>
            <h3 class="echart-txt">会议参与人数</h3>
        </div>

        <div id="echarts-4" class="echarts-1 echarts-2"></div>
    </div>
    <div class="tj-con subPage-marginTop">
        <div class="tj-content clearfix">
            <div class="tj-top tj-top-bottomBorder clearfix">
                <select name="selProvince" id="selProvince" class="select-bd fr">
                </select>
                <h3>用户数据分布</h3>
            </div>
            <div class="data-tj-box">
                <table class="data-table-bd">
                    <tr>
                        <td>
                            <div class="chart-div" id="chart-div-0"></div>
                        </td>
                        <td>
                            <div class="chart-div" id="chart-div-1"></div>
                        </td>
                    </tr>
                    <tr class="last">
                        <td>
                            <div class="chart-div" id="chart-div-2"></div>
                        </td>
                        <td>
                            <div class="chart-div" id="chart-div-3"></div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <div class="tj-con subPage-marginTop">
        <div class="tj-content clearfix">
            <div class="tj-top clearfix">
                <%--<a href="${ctx}/data/state/export?meetId=${meetId}" class="tj-more">导出Excel</a>--%>
                    <a href="${ctx}/data/state/export/attend/user?meetId=${meetId}" class="tj-more">导出Excel</a>
            </div>
            <table class="tj-table tj-table-re1 clearfix">
                <thead>
                <tr>
                    <td class="t-bd-1">参与日期</td>
                    <td class="t-bd-2">姓名</td>
                    <td class="t-bd-3">医院</td>
                    <td class="t-bd-4">科室</td>
                    <td class="t-bd-5">手机</td>
                    <td class="t-bd-6">联系邮箱</td>

                </tr>
                </thead>
                <tbody id="user_datas">

                </tbody>
            </table>

            <div class="page-box" id="user_pages"></div>
        </div>
    </div>
</div>
<script src="${ctxStatic}/js/jquery.min.js"></script>
<script src="${ctxStatic}/js/moment.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/js/jquery.daterangepicker.js"></script>
<script src="${ctxStatic}/laydate/laydate.js"></script>
<script src="${ctxStatic}/js/echarts.min.js"></script>
<script src="${ctxStatic}/js/map/raphael-min.js"></script>
<script src="${ctxStatic}/js/map/chinaMapConfig.js"></script>
<script src="${ctxStatic}/js/map/map.js"></script>
<script src="${ctxStatic}/js/google.charts.load.js"></script>
<script src="${ctxStatic}/js/main.js"></script>
<script src="${ctxStatic}/js/highcharts.js"></script>
<script src="${ctxStatic}/js/exporting.js"></script>
<script src="${ctxStatic}/js/provinceMap.js"></script>
<script src="${ctxStatic}/js/formatDate.js"></script>

<script>
    // 曲线图数据
    var dom = document.getElementById('echarts-4');
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    // 将X、Y 轴的数据填充在图表中
    function setChartData(xdata,ydata) {
        option = {
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis'
            },
            grid: {
                left: '1.5%',
                right: '2%',
                bottom: '3%',
                top:'5%',
                widht:'90%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : xdata
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'参会人数',
                    type:'line',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    },
                    areaStyle: {normal: {}},
                    data:ydata
                }
            ]
        };

        if (option && typeof  option === "object") {
            myChart.setOption(option, true);
        }
    }

    // 获取X、Y 轴数据
    function getLineData(tabIndex,startTime,endTime,meetId) {
        var indexNameArr = new Array();
        var countDataArr = new Array();
        $.ajax({
            url: "${ctx}/data/state/attend/meet/statistics",
            data:{tagNum:tabIndex,startTime:startTime,endTime:endTime,meetId:meetId},
            type: "post",
            dataType: "json",
            success: function (data) {
                var datas = data.data;
                var dataList = datas.list;
                if(dataList!=null && dataList.length!=0){
                    $.each(dataList,function(i,val) {
                        indexNameArr.push(dataList[i].attendIndexName);
                        indexNameArr.join(",");
                        countDataArr.push(dataList[i].attendCount);
                        countDataArr.join(",");

                    });
                }
                var stTime = datas.startTime;
                var endTime = datas.endTime;
                if(stTime.length>16 ){
                    stTime = stTime.substring(0,stTime.length-3);
                }
                if(endTime.length>16 ){
                    endTime = endTime.substring(0,endTime.length-3);
                }
                $("#times").val(stTime+'~'+endTime);

                if(indexNameArr.length==0){
                    indexNameArr = ["0"];
                }
                if(countDataArr.length==0){
                    countDataArr = ["0"];
                }
                if(indexNameArr && countDataArr){
                    setChartData(indexNameArr,countDataArr);
                } else {
                    alert("图表请求数据为空，您可以稍后再试！");
                }
            }, error: function (data) {
                alert("获取数据失败");
            }
        });
    }

    // 全部、本月、本周 点击切换数据
    var tabIndex = '';
    var startTime = '';
    var endTime = '';
    var meetId = "${meetId}";
    $(function(){
        getLineData(0,startTime,endTime,meetId);
        $(".data-time a").click(function(){
            // 全部 本周 本月切换样式高亮显示
            $(this).addClass("t-cur").siblings().removeClass("t-cur");
            //$(".time-tj a").removeClass("t-cur");

            if (tabIndex != $(this).attr("tabIndex")){
                tabIndex = $(this).attr("tabIndex");
                getLineData(tabIndex,startTime,endTime,meetId);
            }
        });

        /* $(".time-tj a").click(function(){
         $(this).addClass("t-cur").siblings().removeClass("t-cur");
         $(".data-time a").removeClass("t-cur");
         });*/
    });
</script>
<script>
    $(function(){
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
            setDate(obj.value);
            // {
            // 		date1: (开始时间),
            // 		date2: (开始时间),
            //	 	value: "2013-06-05 00:00 to 2013-06-07 00:00"
            // }
        });

    });

    function setDate(date){
        var meetId = "${meetId}";
        var startTime = $.trim(date.split("~")[0]);
        var endTime = $.trim(date.split("~")[1]);
        getLineData(3,startTime,endTime,meetId);
    }
</script>

<script>
    $(function () {
        var meetId = "${meetId}";
        // 设置省份下拉框
        $("#selProvince").prepend("<option value='全国'>全国</option>");
        $.each(ChineseDistricts.datas,function(i,val) {
            var province = ChineseDistricts.datas[i];
            $("#selProvince").append("<option value='"+province+"'>"+province+"</option>");
        });

        // load 4个饼图的数据
        getChartsData(1,meetId,'全国');
        getChartsData(2,meetId,'全国');
        getChartsData(3,meetId,'全国');
        getChartsData(4,meetId,'全国');
        // 改变省份时候 加载数据
        $("#selProvince").change(function () {
            var province = $(this).children('option:selected').val();
            getChartsData(1,meetId,province);
            getChartsData(2,meetId,province);
            getChartsData(3,meetId,province);
            getChartsData(4,meetId,province);
        });

    });

    // 获取用户地区分布
    function getChartsData(tagNum,meetId,province) {
        var getData = [];
        $.ajax({
            url: "${ctx}/data/state/user/distribution",
            data:{propNum:tagNum,meetId:meetId,province:province},
            type: "post",
            dataType: "json",
            success: function (data) {
                var dataList = data.data;
                if (dataList != null && dataList.length != 0) {
                    $.each(dataList, function (i, val) {
                        var name = dataList[i].propName;
                        var percent = dataList[i].percent;
                        getData.push({name:name,y:percent});
                    })
                   // console.log(getData);
                    var chartDivIndex ;
                    var title ;
                    if(tagNum==1){
                        chartDivIndex = 0;
                        title = '用户地区分布';
                    }else if(tagNum==2){
                        chartDivIndex = 1;
                        title = '医院级别';
                    }else if(tagNum==3){
                        chartDivIndex = 2;
                        title = '职称分布';
                    }else if(tagNum==4){
                        chartDivIndex = 3;
                        title = '科室分布';
                    }
                    setChartsData(chartDivIndex,title,getData);
                }
            }
        });
    }

    function setChartsData(chartDivIndex,title,loadData) {
        $('#chart-div-'+chartDivIndex).highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            credits:{
                enabled:false // 禁用版权信息
            },
            exporting: {
                enabled: false
            },
            title: {
                text: title
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        distance: -35,
                        formatter: function() {
                            if (this.percentage > 4) return Highcharts.numberFormat(this.point.percentage, 1) +'%';
                        },
                    },
                    showInLegend: true
                }
            },
            legend: {

                itemHoverStyle: {
                    color: '#000'
                },
                itemStyle : {
                    'fontSize' : '12px',
                    'color'    : '#444',
                    'fontWeight': '100',
                },
                layout: 'vertical',
                align: 'left',
                verticalAlign: 'middle',
                symbolHeight: 12,
                symbolWidth :12,
                symbolRadius :10,
                itemMarginTop:8,
                itemMarginBottom:8,
                itemWidth: 105,
                x: 10,
                y: 5,
                borderWidth: 0,
                labelFormatter: function () {
                    return this.name + '&nbsp';
                },
                useHTML: true
            },
            series: [{
                type: 'pie',
                name: 'Browser share',
                data: loadData
            }]
        });
    }

</script>
<script>
    $(function () {
        getAttendUserData(1);
    });

    function getAttendUserData(pageNum) {
        $.ajax({
            url: "${ctx}/data/state/user/attend?pageNum="+pageNum+"&id="+meetId,
            type: "post",
            dataType: "json",
            success: function (data) {
                var dataList = data.data.dataList;
                if (dataList != null && dataList.length != 0) {
                    $("#user_datas").html("");
                    $.each(dataList,function (i,val) {
                        var attendTime = dataList[i].attendTime;
                        if(attendTime==null){
                            attendTime = '';
                        }else{attendTime = format(attendTime);}

                        var nickName = dataList[i].nickName;
                        if (typeof (nickName)=="undefined"){
                            nickName = '';
                        }
                        var unitName = dataList[i].unitName;
                        if (typeof (unitName)=="undefined"){
                            unitName = '';
                        }
                        var subUnitName = dataList[i].subUnitName;
                        if (typeof (subUnitName)=="undefined"){
                            subUnitName = '';
                        }
                        var mobile = dataList[i].mobile;
                        if (typeof (mobile)=="undefined"){
                            mobile = '';
                        }
                        var username = dataList[i].username;
                        if (typeof (username)=="undefined"){
                            username = '';
                        }
                        $('#user_datas').append('<tr><td class="t-bd-1">'+attendTime+'</td>'
                            +'<td class="t-bd-2">'+nickName+'</td>'
                            +'<td class="t-bd-3">'+unitName+'</td>'
                            +'<td class="t-bd-4">'+subUnitName+'</td>'
                            +'<td class="t-bd-5">'+mobile+'</td>'
                            +'<td class="t-bd-6">'+username+'</td></tr>');
                    });

                    // 分页
                    var pagenum = data.data.pageNum;
                    var pages = data.data.pages;

                    var userPageHtml = "";
                    if(pages>1){
                        userPageHtml += '<a ';
                        if(pagenum>1){
                            userPageHtml += 'href="javascript:tzpage('+pages+','+(pagenum-1)+')"';
                        }
                        userPageHtml +=' class="page-list page-border page-prev"><i></i></a>'
                            +'<span class="page-list">'+pagenum+' / '+pages+'</span><a ';
                        if(pagenum<pages){
                            userPageHtml += 'href="javascript:tzpage('+pages+','+(pagenum+1)+')"';
                        }
                        userPageHtml += ' class="page-list page-border page-next"><i></i></a>'
                            +'<input class="page-list page-border" id="tzpageNum" type="text">'
                            +'<a href="javascript:var pagePageNum=$(\'#tzpageNum\').val(); tzpage('+pages+',pagePageNum)" '
                            +'class="page-list page-border page-link">跳转</a>';
                        $('#user_pages').html(userPageHtml);
                    }
                }else{
                    $('#user_pages').html("<div style='text-align:center'>暂无数据</div>");
                }
            }
        });
    }

    function tzpage(pages,pageNum) {
        if(isNaN(Number(pageNum))){
            layer.msg("请输入正确的页码");
        }
        if(pageNum > pages||pageNum == 0){
            layer.msg("请输入页码[1-"+pages+"]");
            return;
        }
        getAttendUserData(pageNum);

    }
</script>

</body>
</html>

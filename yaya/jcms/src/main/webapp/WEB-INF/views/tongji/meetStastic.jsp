<%@ page import="cn.medcn.common.utils.CalendarUtils" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/5/22
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>会议统计</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link href="${ctxStatic}/css/daterangepicker.css" rel="stylesheet">
</head>
<body>
<!-- main -->
<div class="g-main clearfix">
    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-05.png" alt="">
                </div>
                <div class="oh">
                    <p><strong>会议统计</strong></p>
                    <p>大数据系统分析，会议学习情况一目了然</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->

    <div class="tab-hd">

        <ul class="tab-list clearfix">
            <li class="cur">
                <a href="${ctx}/data/state/meet">会议统计<i></i></a>
            </li>
            <li>
                <a id="ys" href="${ctx}/data/state/doc">医生统计<i></i></a>
            </li>
        </ul>
    </div>
    <div class="data-top data-top-3 clearfix">
        <ul>
            <li>
                <p class="data-s-1">会议发布</p>
                <p class="data-num" id="mtCount">
                </p>
                <p class="data-s-2">
                    本月：<span class="color-blue" id="monthCount">
                </span>&nbsp;&nbsp;&nbsp;
                    总共：<span class="color-black" id="totalCount">
                </span></p>
                <p class="data-s-3"></p>
            </li>
            <li>
                <p class="data-s-1">参与人数</p>
                <p class="data-num" id="atCount">
                </p>
                <p class="data-s-2">
                    本月：<span class="color-blue" id="monthAtCount">
                </span>&nbsp;&nbsp;&nbsp;
                    总共：<span class="color-black" id="totalAtCount">
                </span></p>
                <p class="data-s-3"></p>
            </li>
            <li class="last">
                <p class="data-s-1">被转载次数</p>
                <p class="data-num" id="repCount">
                </p>
                <p class="data-s-2">
                    本月：<span class="color-blue" id="monthRepCount">
                    </span>&nbsp;&nbsp;&nbsp;
                    总共：<span class="color-black" id="totalRepCount">
                </span></p>
                <p class="data-s-3"></p>
            </li>

        </ul>
    </div>
    <div class="tj-top subPage-marginTop data-tj-wrap data-tj-wrap-1 data-tj-wrap-5 clearfix">
        <div class="echart-header clearfix">
            <div class="fr">
				<span class="data-time">
					<a href="javascript:;" class="time-all" tabindex="0">全部</a>
					<a href="javascript:;" class="time-month" tabindex="1">本月</a>
					<a href="javascript:;" class="time-week t-cur" tabindex="2">本周</a>
				</span>
                <span class="time-tj">
                    <label for="" id="timeStart">
                        <a href="javascript:;" class="callTimedate timedate-icon" tabindex="3">自定义</a>
                        统计时间：
                        <input id="times" type="text" disabled="" class="timedate-input " placeholder="" value="">
                    </label>
                </span>
            </div>
            <h3 class="echart-txt">会议参与人数</h3>
        </div>

        <div id="echarts-2" class="echarts-1 echarts-2"></div>
    </div>
    <div class="my-mlist my-mlist-shedow subPage-marginTop">
        <div class="my-top">
            <div class="fl">我的会议</div>
            <div class="fr"><a href="${ctx}/func/meet/list">更多</a></div>
            <div class="clearfix"></div>
        </div>
        <table class="table-box-1" id="mtlist">

        </table>
        <div class="page-box" id="mt_page"></div>

    </div>
</div>

<script src="${ctxStatic}/js/moment.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/js/jquery.daterangepicker.js"></script>
<script src="${ctxStatic}/laydate/laydate.js"></script>
<script src="${ctxStatic}/js/echarts.min.js"></script>
<script src="${ctxStatic}/js/formatDate.js"></script>

<script>
$(function () {
    $.ajax({
        url: "${ctx}/data/state/statistics",
        type: "post",
        dataType: "json",
        success: function (data) {
            $('#mtCount').text(data.data.monthMeetCount);
            $('#monthCount').text(data.data.monthMeetCount);
            $('#totalCount').text(data.data.allMeetCount);

            $('#atCount').text(data.data.monthAttendCount);
            $('#monthAtCount').text(data.data.monthAttendCount);
            $('#totalAtCount').text(data.data.allAttendCount);

            $('#repCount').text(data.data.monthReprintCount);
            $('#monthRepCount').text(data.data.monthReprintCount);
            $('#totalRepCount').text(data.data.allReprintCount);

        }, error: function (data) {
            $('#mtCount').text(0);
            $('#monthCount').text(0);
            $('#totalCount').text(0);

            $('#atCount').text(0);
            $('#monthAtCount').text(0);
            $('#totalAtCount').text(0);

            $('#repCount').text(0);
            $('#monthRepCount').text(0);
            $('#totalRepCount').text(0);
        }
    });
});
</script>

<script>
    var dom = document.getElementById('echarts-2');
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
    function getLineData(tabIndex,startTime,endTime) {
        var indexNameArr = new Array();
        var countDataArr = new Array();
        $.ajax({
            url: "${ctx}/data/state/attend/meet/statistics",
            data:{tagNum:tabIndex,startTime:startTime,endTime:endTime},
            type: "post",
            dataType: "json",
            success: function (data) {
                var dataList = data.data.list;
                if(dataList!=null && dataList.length!=0){
                    $.each(dataList,function(i,val) {
                        indexNameArr.push(dataList[i].attendIndexName);
                        indexNameArr.join(",");
                        countDataArr.push(dataList[i].attendCount);
                        countDataArr.join(",");
                        //console.log("indexNameArr:"+indexNameArr);
                        //console.log("countDataArr:"+countDataArr);
                    });
                }

                var stTime = data.data.startTime;
                var endTime = data.data.endTime;
                if(stTime != null && stTime.length>16 ){
                    stTime = stTime.substring(0,stTime.length-3);
                }
                if(endTime != null && endTime.length>16 ){
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
    $(function(){
        // 默认为本周的数据
        getLineData(2,startTime,endTime);
        $(".data-time a").click(function(){
            // 全部 本周 本月切换样式高亮显示
            $(this).addClass("t-cur").siblings().removeClass("t-cur");
            //$(".time-tj a").removeClass("t-cur");
            if (tabIndex != $(this).attr("tabindex")){
                tabIndex = $(this).attr("tabindex");
                getLineData(tabIndex,startTime,endTime);
            }

        });

        /*$(".time-tj a").click(function(){
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
        });
    });

    function setDate(date){
        var tabIndex = 3; // 自定义时间
        var startTime = $.trim(date.split("~")[0]);
        var endTime = $.trim(date.split("~")[1]);
        getLineData(tabIndex,startTime,endTime);
    }
</script>
<script>
    $(function () {
       getMeetList(1);
    });

    function getMeetList(pageNum) {
        $.ajax({
            url: "${ctx}/data/state/meetList?pageNum="+pageNum,
            type: "post",
            dataType: "json",
            success: function (data) {
                var dataList = data.data.dataList;
                if(dataList!=null){
                    $("#mtlist").html("");
                    var listHtml = '';
                    $.each(dataList,function(i,val) {
                        var mtTime = '';
                        if(dataList[i].state==0 || dataList[i].state==5){
                            mtTime = dataList[i].createTime;
                        }else{
                            mtTime = dataList[i].publishTime;
                        }
                        if (mtTime!=null && mtTime!=undefined){
                            mtTime = format(mtTime);
                        }

                        listHtml += '<tr><td class="table-td-6"><a href="${ctx}/func/meet/view?id='+dataList[i].id+'&tag=1">'
                            +'<span class="icon iconfont icon-minIcon2"></span>'+dataList[i].meetName+''
                            +'<i class="style-1 listTips">'+dataList[i].stateName+'</i></a></td>'
                            +'<td class="table-td-4">'
                            +'<span class="table-line">'+mtTime+'</span></td>'
                            +'<td class="tj-td-4 t-center">';
                        if(dataList[i].state==2 || dataList[i].state==3){
                            listHtml += '<a href="${ctx}/data/state/user/statistics?id='+dataList[i].id+'">'
                                        +'<span class="icon iconfont icon-minIcon1"></span>&nbsp;统计报告</a>';
                        }
                        listHtml += '</td></tr>';
                        $('#mtlist').html(listHtml);
                        <%--$('#mtlist').append('<tr><td class="table-td-6"><a href="${ctx}/func/meet/view?id='+dataList[i].id+'&tag=1">'--%>
                            <%--+'<span class="icon iconfont icon-minIcon2"></span>'+dataList[i].meetName+''--%>
                            <%--+'<i class="style-1 listTips">'+dataList[i].stateName+'</i></a></td>'--%>
                            <%--+'<td class="table-td-4">'--%>
                            <%--+'<span class="table-line">'+mtTime+'</span></td>'--%>
                            <%--+'<td class="tj-td-4 t-center"><a href="${ctx}/data/state/user/statistics?id='+dataList[i].id+'">'--%>
                            <%--+'<span class="icon iconfont icon-minIcon1"></span>&nbsp;统计报告</a>'--%>
                            <%--+'</td></tr>');--%>
                    });

                    // 分页
                    var pageNum = data.data.pageNum;
                    var pages = data.data.pages;

                    var mtPageHtml = "";
                    if(pages>1){
                        mtPageHtml += '<a ';
                        if(pageNum>1){
                            mtPageHtml += 'href="javascript:tzpage('+pages+','+(pageNum-1)+')"';
                        }
                        mtPageHtml +=' class="page-list page-border page-prev"><i></i></a>'
                            +'<span class="page-list">'+pageNum+' / '+pages+'</span><a ';
                        if(pageNum < pages){
                            mtPageHtml += 'href="javascript:tzpage('+pages+','+(pageNum+1)+')"';
                        }
                        mtPageHtml += ' class="page-list page-border page-next"><i></i></a>'
                            +'<input class="page-list page-border" id="tzpageNum" type="text">'
                            +'<a href="javascript:var pagePageNum=$(\'#tzpageNum\').val(); tzpage('+pages+',pagePageNum)" '
                            +'class="page-list page-border page-link">跳转</a>';
                        $('#mt_page').html(mtPageHtml);
                    }
                }else{
                    $('#mt_page').html("<div style='text-align:center'>暂无数据</div>");
                }

            }, error: function (data) {
                alert("获取数据失败");
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
        getMeetList(pageNum);

    }
</script>

</body>
</html>

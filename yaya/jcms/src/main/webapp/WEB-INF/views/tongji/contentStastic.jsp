<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/6/6
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>内容统计</title>
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
					<a href="javascript:;" class="time-all t-cur" tabindex="0">全部</a>
					<a href="javascript:;" class="time-month" tabindex="1">本月</a>
					<a href="javascript:;" class="time-week" tabindex="2">本周</a>
				</span>
                <span class="time-tj">
                    <label for="" id="timeStart">
                        <a href="javascript:;" class="callTimedate timedate-icon">自定义</a>
                        统计时间：
                        <input id="times" type="text" disabled="" class="timedate-input " placeholder=""
                               tabindex="3"  value="">
                    </label>
                </span>
            </div>
            <h3 class="echart-txt">PPT观看人数</h3>
        </div>

        <div id="echarts-3" class="echarts-1 echarts-2"></div>
    </div>

    <div class="tj-con subPage-marginTop">
        <div class="tj-content clearfix">
            <div class="tj-top clearfix">
                <h3>观看记录</h3>
                <span class="fr tj-ri-1">已观看人数：<i>${viewCount}人</i>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="${ctx}/func/meet/ppt/pptexcel?meetId=${param.id}" class="tj-more" style="margin-top:0;">下载全部数据</a>
                </span>
            </div>
            <table class="tj-table tj-table-re1 tj-table-maxSize clearfix">
                <thead>
                <tr>
                    <td class="tj-td-1">姓名</td>
                    <td class="tj-td-2 t-center">医院</td>
                    <td class="tj-td-4 t-center">科室</td>
                    <td class="tj-td-4 t-center">总时长</td>
                    <td class="tj-td-1 t-center">PPT页数</td>
                    <td class="tj-td-4 t-center">观看明细</td>
                </tr>
                </thead>
                <tbody id="user_record">

                </tbody>
            </table>
            <div class="page-box" id="user_page"></div>

        </div>
    </div>

</div>
<script src="${ctxStatic}/js/jquery.min.js"></script>
<script src="${ctxStatic}/js/moment.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/js/jquery.daterangepicker.js"></script>
<script src="${ctxStatic}/laydate/laydate.js"></script>
<script src="${ctxStatic}/js/echarts.min.js"></script>
<script src="${ctxStatic}/js/main.js"></script>
<script>
    var dom = document.getElementById('echarts-3');
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    function setChartData(xdata,ydata) {
        option = {
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '1.5%',
                right: '2%',
                bottom: '3%',
                top:'5%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    //data : ['P1', 'P2', 'P3', 'P4', 'P5', 'P6', 'P7', 'P8', 'P9', 'P10','P11', 'P12', 'P13', 'P14', 'P15', 'P16', 'P17', 'P18', 'P19', 'P20', 'P21', 'P22', 'P23', 'P24', 'P25', 'P26', 'P27'],
                    data : xdata,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'完整观看人数',
                    type:'bar',
                    barWidth: '60%',
                    //data:[417, 512, 339, 334, 290, 330, 220, 52, 200, 434, 590, 330, 417, 512, 339, 334, 290, 330, 220, 52, 200, 434, 590, 330, 220]
                    data: ydata
                }]
        };;
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
    }

    // 获取X、Y 轴数据
    function getLineData(tagNo,startTime,endTime,meetId) {
        var pptNoArr = new Array();
        var countDataArr = new Array();
        $.ajax({
            url: "${ctx}/func/meet/ppt/viewpptCount?tagNo="+tagNo+"&startTime="+startTime+"&endTime="+endTime+"&meetId="+meetId,
            type: "post",
            dataType: "json",
            success: function (data) {
                var datas = data.data;
                var dataList = datas.list;
                if(dataList!=null && dataList.length!=0){
                    $.each(dataList,function(i,val) {
                        pptNoArr.push('P'+dataList[i].pptSort);
                        pptNoArr.join(",");
                        countDataArr.push(dataList[i].userCount);
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

                if(pptNoArr.length==0){
                    pptNoArr = ["0"];
                }
                if(countDataArr.length==0){
                    countDataArr = ["0"];
                }
                if(pptNoArr && countDataArr){
                    setChartData(pptNoArr,countDataArr);
                } else {
                    alert("图表请求数据为空，您可以稍后再试！");
                }
            }, error: function (data) {
                alert("获取数据失败");
            }
        });
    }

    // 全部、本月、本周 点击切换数据
    var tagNo = '';
    var startTime = '';
    var endTime = '';
    var meetId = "${param.id}";
    $(function(){
        getLineData(0,startTime,endTime,meetId);
        $(".data-time a").click(function(){
            // 全部 本周 本月切换样式高亮显示
            $(this).addClass("t-cur").siblings().removeClass("t-cur");
            if (tagNo != $(this).attr("tabindex")){
                tagNo = $(this).attr("tabindex");
                getLineData(tagNo,startTime,endTime,meetId);
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
    })

    function setDate(date){
        var meetId = "${param.id}";
        var startTime = $.trim(date.split("~")[0]);
        var endTime = $.trim(date.split("~")[1]);
        getLineData(3,startTime,endTime,meetId);
    }
</script>

<script>
    $(function () {
        getPPtRecord(1);
    });

    function getPPtRecord(pageNum) {
        $.ajax({
            url: "${ctx}/func/meet/ppt/view/record?pageNum="+pageNum+"&id="+meetId,
            type: "post",
            dataType: "json",
            success: function (data) {
                var dataList = data.data.dataList;
                if(dataList!=null){
                    $("#user_record").html("");
                    $.each(dataList,function(i,val) {
                        var headimg = dataList[i].headimg == ''?'${ctxStatic}/img/hz-detail-img-info.jpg':dataList[i].headimg
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
                        var usedtime = dataList[i].usedtime;
                        if (typeof (usedtime)=="undefined"){
                            usedtime = '';
                        }
                        var pptCount = dataList[i].pptCount;
                        if (typeof (pptCount)=="undefined"){
                            pptCount = '';
                        }

                        $('#user_record').append('<tr><td class="tj-td-1 pic-tj"><img src="'+headimg+'" '
                            +'width="32" height="32" alt="">'+nickName+'</td>'
                            +'<td class="tj-td-2 t-center">'+unitName+'</td>'
                            +'<td class="tj-td-4 t-center">'+subUnitName+'</td>'
                            +'<td class="tj-td-4 t-center">'+usedtime+'</td>'
                            +'<td class="tj-td-4 t-center">'+pptCount+'页</td>'
                            +'<td class="tj-td-4 t-center"><a href="${ctx}/func/meet/ppt/pptexcel?userId='+dataList[i].id+'&meetId=${param.id}"'
                            +'class="check-link">下载</a></td></tr>');
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
                        $('#user_page').html(userPageHtml);
                    }
                }else{
                    $('#user_page').html("<div style='text-align:center'>暂无数据</div>");
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
        getPPtRecord(pageNum);

    }

</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/5/22
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>医生统计</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link href="${ctxStatic}/css/iconfont.css" rel="stylesheet">

</head>
<body>
<!-- main -->
<div class="g-main clearfix">
    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-04.png" alt="">
                </div>
                <div class="oh">
                    <p><strong>医生统计</strong></p>
                    <p>关注医生用户属性归类统计，掌握关注用户的属性分布</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->

    <div class="tab-hd">
        <ul class="tab-list clearfix">
            <li>
                <a href="${ctx}/data/state/meet">会议统计<i></i></a>
            </li>
            <li class="cur">
                <a href="${ctx}/data/state/doc">医生统计<i></i></a>
            </li>
        </ul>
    </div>
    <div class="data-top clearfix">
        <table class="tj-table tj-table-2 tj-table-3 clearfix">
            <tbody>
            <tr>
                <td>
                    <p class="tj-table-title">本周新增关注</p>
                    <p><span class="td-txt">
                    <c:if test="${empty userAttendDTO.weekAttendCount}">0</c:if>
                    <c:if test="${not empty userAttendDTO.weekAttendCount}">${userAttendDTO.weekAttendCount}</c:if>
                    </span></p>
                </td>
                <td>
                    <p class="tj-table-title">本月新增关注</p>
                    <p><span class="td-txt">
                     <c:if test="${empty userAttendDTO.monthAttendCount}">0</c:if>
                     <c:if test="${not empty userAttendDTO.monthAttendCount}">${userAttendDTO.monthAttendCount}</c:if>
                    </span></p>
                </td>
                <td>
                    <p class="tj-table-title">总关注</p>
                    <p><span class="td-txt">
                    <c:if test="${empty userAttendDTO.totalAttendCount}">0</c:if>
                    <c:if test="${not empty userAttendDTO.totalAttendCount}">${userAttendDTO.totalAttendCount}</c:if>
                    </span></p>
                </td>
            </tr>
            </tbody>
        </table>
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
            <div class="tj-top  clearfix">
                <h3>属性分布表</h3>

            </div>
            <div class="tab-wrap">
                <span class="tab-menu tab-cur" tabindex="1">地区分布</span>
                <span class="tab-menu" tabindex="2">医院级别</span>
                <span class="tab-menu" tabindex="3">职称分布</span>
                <span class="tab-menu" tabindex="4">科室分布</span>
            </div>
            <div class="tab-con-wrap">
                <table class="tab-table-box">
                    <thead>
                    <tr>
                        <td id="tabName">地区分布</td>
                        <td>用户数<span class="sj-btn"><i class="sj-top"></i><i class="sj-btm sj-cur"></i></span></td>
                        <td>占比</td>
                    </tr>
                    </thead>
                    <tbody id="propdata">

                    </tbody>
                </table>

                    <div class="page-box" id="pg">

                    </div>
            </div>
        </div>
    </div>

    <div class="tj-con subPage-marginTop">
        <div class="tj-content clearfix">
            <div class="tj-top clearfix">
                <h3>关注量分析</h3>
            </div>
            <div class="data-tj-box">
                <div class="regionList" id="MapControl">
                    <div class="regionListItem clearfix">
                        <ul class="list1"></ul>
                        <ul class="list2" style="display: none"></ul>
                        <ul class="list3" style="display: none"></ul>
                        <ul class="list4" style="display: none"></ul>
                    </div>
                    <c:if test="${pages>1}">
                        <div class="page-box clearfix">
                            <c:forEach var="i" begin="1" end="${pages}" step="1">
                                <a href="javascript:gettopage(${i})" class="page-list page-border"><i>${i}</i></a>
                            </c:forEach>
                        </div>
                    </c:if>

                </div>
                <div id="Region" style="position:relative; height:360px;">

                    <div class="regionMap" id="RegionMap"></div>
                    <div id="MapColor" style=" display:none; float:left; width:30px; height:180px; margin:80px 0 0 10px; display:inline; background:url(${ctxStatic}/images/map_color.png) center 0;"></div>
                </div>
            </div>
        </div>
    </div>
</div>
        <script src="${ctxStatic}/js/jquery.min.js"></script>
        <script src="${ctxStatic}/js/map/raphael-min.js"></script>
        <script src="${ctxStatic}/js/map/chinaMapConfig.js"></script>
        <script src="${ctxStatic}/js/map/map.js"></script>
        <script src="${ctxStatic}/js/google.charts.load.js"></script>
        <script src="${ctxStatic}/js/main.js"></script>
        <script src="${ctxStatic}/js/highcharts.js"></script>
        <script src="${ctxStatic}/js/exporting.js"></script>
        <script src="${ctxStatic}/js/provinceMap.js"></script>

        <script>
            $(function () {
                //console.log(chinaMapConfig.names);
                // 设置省份下拉框
                $("#selProvince").prepend("<option value='全国'>全国</option>");
                $.each(ChineseDistricts.datas,function(i,val) {
                    var province = ChineseDistricts.datas[i];
                    // console.log(province);
                    $("#selProvince").append("<option value='"+province+"'>"+province+"</option>");
                });

                // load 4个饼图的数据
                getChartsData(1,'全国');
                getChartsData(2,'全国');
                getChartsData(3,'全国');
                getChartsData(4,'全国');
                // 改变省份时候 加载数据
                $("#selProvince").change(function () {
                    var province = $(this).children('option:selected').val();
                    getChartsData(1,province);
                    getChartsData(2,province);
                    getChartsData(3,province);
                    getChartsData(4,province);
                });

            });

            // 获取用户地区分布
            function getChartsData(tagNum,province) {
                var getData = [];
                $.ajax({
                    url: "${ctx}/data/state/chartData",
                    data: {propNum:tagNum,province:province},
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
                            console.log(getData);
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
            // 属性分布数据展示
            var tagNum = 1;
            $(function () {
                getPropData(tagNum,1);
                $(".tab-wrap span").click(function() {
                    if(tagNum != $(this).attr("tabindex")){
                        tagNum = $(this).attr("tabindex");
                        // 标签 名称
                        $("#tabName").html($(this).text());
                        // 加载数据前 先清空之前的数据内容
                        $("#propdata").empty();
                        getPropData(tagNum,1);
                    }
                })
            });

            function getPropData(tagNum,pageNum){
                $.ajax({
                    url: "${ctx}/data/state/userDataProp?propNum=" + tagNum +"&pageNum="+pageNum,
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        var dataList = data.data.dataList;
                        var pageNum = data.data.pageNum;
                        if (dataList != null && dataList.length != 0) {
                            $("#propdata").html("");
                            $.each(dataList, function (i, val) {
                                $("#propdata").append("<tr><td>" + dataList[i].propName + "</td>" +
                                    "<td>" + dataList[i].userCount + "</td>" +
                                    "<td>" + (dataList[i].percent * 100).toFixed(2) + "%</td></tr>");
                            });
                        }
                        var pages = data.data.pages;
                        var pghtml = '';
                        if(pageNum>1){
                            pghtml += '<a href="javascript:;" onclick="getpage('+(parseInt(pageNum)-1)+','+pages+');" '
                                +'class="page-list page-border page-prev"><i></i></a>';
                        }
                        pghtml+='<span class="page-list">'+pageNum+' / '+pages+'</span>';
                        if(pageNum<pages){
                            pghtml+='<a href="javascript:;" onclick="getpage('+(parseInt(pageNum)+1)+','+pages+');"'
                                +'class="page-list page-border page-next"><i></i></a>';
                        }
                        pghtml+='<input class="page-list page-border" type="text" id="pgnum">'
                            +'<a href="javascript:var pgNum = $(\'#pgnum\').val(); getpage(pgNum,'+pages+')"'
                            +'class="page-list page-border page-link">跳转</a>';
                        $("#pg").html(pghtml);

                    },
                    error: function (data) {
                        alert("获取数据异常，请稍后重试！");
                    }
                });
            }

            function getpage(pageNum,pages) {
                if(isNaN(Number(pageNum))){
                    layer.msg("请输入正确的页码");
                }
                if(pageNum > pages||pageNum == 0){
                    layer.msg("请输入页码[1-"+pages+"]");
                    return;
                }
                getPropData(tagNum,pageNum);
            }

        </script>

        <script>
            function getProKey(province){
                var proKey = "";
                for(var key in chinaMapConfig.names){
                    if (chinaMapConfig.names[key] == province){
                        proKey = key;
                        break;
                    }
                }
                if(proKey == ''){
                    proKey = "NoExist";
                }
                return proKey;
            }


            // 地图分页
            function gettopage(pageNum) {
                $('#MapControl ul').hide();
                $('#MapControl .list'+pageNum).show();
            }

            // 地图数据分布
            $(function () {
                var data = {};
                <c:forEach items="${list}" var="dt" varStatus="status">
                var pkey = getProKey("${dt.propName}");
                var perct = '<fmt:formatNumber type="number" value="${dt.percent*100}" pattern="0.00" maxFractionDigits="2"/>';
                data[pkey] = {"value":perct+"%","index":"${status.index+1}","stateInitColor":"${status.index+1}"};
                </c:forEach>

                var i = 1;
                for (k in data) {
                    //console.log(k);
                    var pname = chinaMapConfig.names[k];
                    if(pname=='' || typeof(pname)=="undefined"){
                        pname = '未设置';
                    }
                    if(i<=15){
                        $('#MapControl .list1').append('<li name="' + k + '">' +
                            '<div class="mapInfo"><span class="mapInfoName">' + pname + '</span>' +
                            '<span class="progreesBar"><b style=width:' + data[k].value + '></b></span>' +
                            '<span class="mapInfoNum">' + data[k].value + '</span></div></li>');
                    }else if(i>15 && i<=30){
                       $('#MapControl .list2').append('<li name="' + k + '">' +
                            '<div class="mapInfo"><span class="mapInfoName">' + pname + '</span>' +
                            '<span class="progreesBar"><b style=width:' + data[k].value + '></b></span>' +
                            '<span class="mapInfoNum">' + data[k].value + '</span></div></li>');
                    }else if(i>30 && i<=45){
                       $('#MapControl .list3').append('<li name="' + k + '">' +
                            '<div class="mapInfo"><span class="mapInfoName">' + pname + '</span>' +
                            '<span class="progreesBar"><b style=width:' + data[k].value + '></b></span>' +
                            '<span class="mapInfoNum">' + data[k].value + '</span></div></li>');
                    }else{
                        $('#MapControl .list4').append('<li name="' + k + '">' +
                            '<div class="mapInfo"><span class="mapInfoName">' + pname + '</span>' +
                            '<span class="progreesBar"><b style=width:' + data[k].value + '></b></span>' +
                            '<span class="mapInfoNum">' + data[k].value + '</span></div></li>');
                    }
                        i++;
                 }

                var mapObj_1 = {};
                var stateColorList = ['2770b5', '0058B0', '0071E1', '1C8DFF', '51A8FF', '82C0FF', 'AAD5ee', 'AAD5FF'];

                $('#RegionMap').SVGMap({
                    external: mapObj_1,
                    mapName: 'china',
                    stateData: data,
                    // stateTipWidth: 118,
                    // stateTipHeight: 47,
                    // stateTipX: 2,
                    // stateTipY: 0,
                    stateTipHtml: function (mapData, obj) {
                        //console.log($(".svggroup svg").width());
                        var _value = mapData[obj.id].value;
                        var _idx = mapData[obj.id].index;
                        var active = '';
                        _idx < 4 ? active = 'active' : active = '';
                        var tipStr = '<div class="mapInfo"><i class="' + active + '">' + _idx + '</i><span>' + obj.name + '</span><b>' + _value + '</b></div>';
                        return tipStr;
                    }
                });
                $('#MapControl li').hover(function () {
                    var thisName = $(this).attr('name');

                    var thisHtml = $(this).html();
                    $('#MapControl li').removeClass('select');
                    $(this).addClass('select');
                    $(document.body).append('<div id="StateTip"></div>');
                    $('#StateTip').css({
                        left: $(mapObj_1[thisName].node).offset().left - 50,
                        top: $(mapObj_1[thisName].node).offset().top - 40
                    }).html(thisHtml).show();
                    mapObj_1[thisName].attr({
                        fill: '#E99A4D'
                    });
                }, function () {
                    var thisName = $(this).attr('name');

                    $('#StateTip').remove();
                    $('#MapControl li').removeClass('select');
                    mapObj_1[$(this).attr('name')].attr({
                        fill: "#" + stateColorList[data[$(this).attr('name')].stateInitColor]
                    });
                });

                $('#MapColor').show();
            });


        </script>



</body>
</html>

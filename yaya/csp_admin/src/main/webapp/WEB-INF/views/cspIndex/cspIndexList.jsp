<%--
  Created by IntelliJ IDEA.
  User: jianLiang
  Date: 2017/12/19
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <script src="${ctxStatic}/jquery/jquery-1.12.4.min.js"></script>
    <link href="${ctxStatic}/bootstrap/2.3.1/css_readable/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>

    <!--新增的-->
    <link rel="stylesheet" href="${ctxStatic}/bootstrap/added/admin-style.css">
    <link href="${ctxStatic}/bootstrap/added/daterangepicker.css" rel="stylesheet">

    <script src="${ctxStatic}/bootstrap/added/moment.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/bootstrap/added/jquery.daterangepicker.js"></script>
    <script src="${ctxStatic}/bootstrap/added/echarts.min.js"></script>

</head>
<body>
<ul class="nav nav-pills">
    <li class="active"><a href="${ctx}/csp/userInfo/list">海内</a></li>
    <li ><a href="${ctx}/csp/userInfo/list_us">海外</a></li>
</ul>
<h3 class="page-title">用户概况</h3>
<div class="top-info clearfix">
    <div class="row-fluid ">
        <div class="row-span span4">
            <h6 class="title"><a href="/routes_pages/userManage.html">昨日新增</a></h6>
            <p><strong class="price">${newUserCount}</strong></p>
        </div>
        <div class="row-span span4">
            <h6 class="title"><a href="/routes_pages/moneyStats.html">昨日进账</a></h6>
            <p><strong class="price">${newMoney}</strong></p>
        </div>
        <div class="row-span span4">
            <h6 class="title"><a href="/routes_pages/userManage.html">总用户</a></h6>
            <p><strong class="price">${allUserCount}</strong></p>
        </div>
    </div>
</div>

<div class="text-center">
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span8">
                <div id="echarts-2" class="echarts echarts-2"></div>
            </div>
            <div class="span4">
                <div class="index-box-2 text-left" >
                    <!--Body content-->
                    <h5>版本用户数</h5>
                    <p>标准版用户： ${standardEditionCount}人</p>
                    <p>高级版用户： ${premiumEditionCount}人</p>
                    <p>专业版用户： ${professionalEditionCount}人</p>
                </div>
            </div>
        </div>
    </div>

</div>
<div class=" clearfix">

    <div class="data-tj-box">
        <h4 class="text-center">用户地区分布图</h4>
        <div class="regionList" id="MapControl">
            <div class="regionListItem clearfix">
                <ul class="list1"></ul>
                <ul class="list2"  style="display: none"></ul>
                <ul class="list3"  style="display: none"></ul>
                <ul class="list4"  style="display: none"></ul>
            </div>
            <div class="pagination text-right clearfix">
                <form id="pageForm" name="pageForm" action="${ctx}/csp/userInfo/list" method="post">
                        <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
                        <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
                </form>
            </div>
            <%@include file="/WEB-INF/include/pageable.jsp"%>
        </div>
        <div id="Region" style="position:relative; height:360px; margin-top:10px;">
            <div class="regionMap" id="RegionMap"></div>
        </div>

    </div>
</div>
<script src="${ctxStatic}/bootstrap/added/map/raphael-min.js"></script>
<script src="${ctxStatic}/bootstrap/added/map/chinaMapConfig.js"></script>
<script src="${ctxStatic}/bootstrap/added/map/map.js"></script>
<script src="${ctxStatic}/js/util.js"></script>
<script>

    function getProKey(province){
        var proKey = "";
        for(var key in chinaMapConfig.names){
            if (province.startWith(chinaMapConfig.names[key])){
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
    $(function(){
        var data = {};
        <c:forEach items="${map}" var="m" varStatus="status">
        var pkey = getProKey("${m.key}");
        var perct = '<fmt:formatNumber type="number" value="${m.value}" pattern="0.00" maxFractionDigits="2"/>';
        data[pkey] = {"value":perct+"%","index":"${status.index+1}","stateInitColor":"${status.index+1}"};
        </c:forEach>

        var i = 1;
        for(k in data){
            $('#MapControl .list1').append('<li name="'+k+'"><div class="mapInfo"><span class="mapInfoName">'+chinaMapConfig.names[k]+'</span><span class="progreesBar"><b style=width:'+data[k].value+'></b></span><span class="mapInfoNum">'+data[k].value+'</span></div></li>');
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
               // console.log($(".svggroup svg").width());
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
<script>
    $(function(){

        //图表加载
        var dom = document.getElementById('echarts-2');
        var myChart = echarts.init(dom);
        var app = {};
        option = null;
        option = {
            title: {
                text: '套餐版本统计',
                left: 'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            series : [
                {
                    type: 'pie',
                    radius : '65%',
                    center: ['50%', '50%'],
                    selectedMode: 'single',
                    data:[
                        {value:${standardEditionCount}, name: '标准版'},
                        {value:${premiumEditionCount}, name: '高级版'},
                        {value:${professionalEditionCount}, name: '专业版'}
                    ],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }



    });
</script>


</body>
</html>

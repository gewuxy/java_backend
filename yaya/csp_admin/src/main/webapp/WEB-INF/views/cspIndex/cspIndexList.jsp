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
    <title>标题根据实际情况修改</title>
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
<ul class="nav nav-pills">
    <li class="active"><a href="/routes_pages/userInfo.html">海内</a></li>
    <li ><a href="/csp/sys/user/list">海外</a></li>
</ul>
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
                    <p>标准版用户： ${standardEdition}人</p>
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
                <ul class="list2"></ul>
                <ul class="list3"></ul>
                <ul class="list4"></ul>
            </div>
            <div class="pagination text-right clearfix">
                <a href="" class="page-list page-border page-prev btn"><</a>
                <span class="page-list pages-num">1 / 3</span>
                <a href="" class="page-list page-border page-next btn">&gt;</a>
                <input class="page-list page-border required pages-input" type="text">
                <button href="" class="btn btn-primary">跳转</button>
            </div>
        </div>
        <div id="Region" style="position:relative; height:360px; margin-top:10px;">
            <div class="regionMap" id="RegionMap"></div>
        </div>

    </div>
</div>
<script src="${ctxStatic}/bootstrap/added/map/raphael-min.js"></script>
<script src="${ctxStatic}/bootstrap/added/map/chinaMapConfig.js"></script>
<script src="${ctxStatic}/bootstrap/added/map/map.js"></script>
<script>
    $(function(){




// 地图分布
        var data = {"jiangsu":{"value":"30.05%","index":"1","stateInitColor":"0"},
            "henan":{"value":"19.77%","index":"2","stateInitColor":"0"},
            "anhui":{"value":"10.85%","index":"3","stateInitColor":"0"},
            "zhejiang":{"value":"10.02%","index":"4","stateInitColor":"0"},
            "liaoning":{"value":"8.46%","index":"5","stateInitColor":"0"},
            "beijing":{"value":"4.04%","index":"6","stateInitColor":"1"},
            "hubei":{"value":"3.66%","index":"7","stateInitColor":"1"},
            "jilin":{"value":"2.56%","index":"8","stateInitColor":"1"},
            "shanghai":{"value":"2.47%","index":"9","stateInitColor":"1"},
            "guangxi":{"value":"2.3%","index":"10","stateInitColor":"1"}};
        var i = 1;
        for(k in data){
            $('#MapControl .list1').append('<li name="'+k+'"><div class="mapInfo"><span class="mapInfoName">'+chinaMapConfig.names[k]+'</span><span class="progreesBar"><b style=width:'+data[k].value+'></b></span><span class="mapInfoNum">'+data[k].value+'</span></div></li>');
        }
//        for(k in data){
//            if(i <= 12){
//                var _cls = i < 4 ? 'active' : '';
//                $('#MapControl .list1').append('<li name="'+k+'"><div class="mapInfo"><i class="'+_cls+'">'+(i++)+'</i><span>'+chinaMapConfig.names[k]+'</span><b>'+data[k].value+'</b></div></li>')
//            }else if(i <= 24){
//                $('#MapControl .list2').append('<li name="'+k+'"><div class="mapInfo"><i>'+(i++)+'</i><span>'+chinaMapConfig.names[k]+'</span><b>'+data[k].value+'</b></div></li>')
//            }else{
//                $('#MapControl .list3').append('<li name="'+k+'"><div class="mapInfo"><i>'+(i++)+'</i><span>'+chinaMapConfig.names[k]+'</span><b>'+data[k].value+'</b></div></li>')
//            }
//        }

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
                console.log($(".svggroup svg").width());
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
<h3 class="page-title">新增列表</h3>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>ID</th>
        <th>昵称</th>
        <th>注册日期</th>
        <th>套餐等级</th>
        <th>套餐日期</th>
        <th>付费次数</th>
        <th>付费总额</th>
        <th>区域</th>
        <th>更多</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>123</td>
        <td>007</td>
        <td>20170111</td>
        <td>基础版</td>
        <td>2018-01-01至2018-02-01</td>
        <td>1</td>
        <td>0</td>
        <td>海内</td>
        <td>
            <div class="btn-group">
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    操作
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="#">升级</a></li>
                    <li><a href="#">降级</a></li>
                    <li><a href="#">时间修改</a></li>
                    <li><a href="#">账号冻结</a></li>
                </ul>
            </div>
        </td>
    </tr>
    <tr>
        <td colspan="9">没有查询到数据</td>
    </tr>
    </tbody>
</table>

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
                        {value:${standardEdition}, name: '标准版'},
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

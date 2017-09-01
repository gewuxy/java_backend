<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/8/1
  Time: 9:30
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>会议统计</title>
    <%@include file="/WEB-INF/include/page_context_weixin.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.css">
    <script src="${ctxStatic}/js/echarts.min.js"></script>
    <script src="${ctxStatic}/js/swiper.jquery.min.js"></script>

</head>
<body class="gary-reg">

<div class="warp">

    <div class="item">
        <div class="metting-chart">
            <h3>参会数量</h3>
            <!-- Swiper -->
            <div class="swiper-container" id="attendDataSwiper">
                <div class="swiper-wrapper">
                    <div class="swiper-slide"><div id="echarts-1" class="echarts-box"></div></div>
                    <div class="swiper-slide"><div id="echarts-2" class="echarts-box"></div></div>
                    <div class="swiper-slide"><div id="echarts-3" class="echarts-box"></div></div>
                </div>
            </div>

            <div class="metting-chart-count-box">
                <div class="flex">
                    <div class="flex-item">本周&nbsp;&nbsp;<span class="color-blue" id="attendUnitCount">0</span></div>
                    <div class="flex-item last">总数量&nbsp;&nbsp;<span class="color-blue" id="attendTotalCount">0</span></div>
                </div>
            </div>
        </div>
        <div class="metting-chart user-margin-top">
            <h3>我关注的公众号发布的会议数量</h3>
            <div class="swiper-container" id="publishSwiper">
                <div class="swiper-wrapper">
                    <div class="swiper-slide"><div id="echarts-4" class="echarts-box"></div></div>
                    <div class="swiper-slide"><div id="echarts-5" class="echarts-box"></div></div>
                    <div class="swiper-slide"><div id="echarts-6" class="echarts-box"></div></div>
                </div>
            </div>
            <div class="metting-chart-count-box">
                <div class="flex">
                    <div class="flex-item">本周&nbsp;&nbsp;<span class="color-blue" id="publishUnitCount">0</span></div>
                    <div class="flex-item last">总数量&nbsp;&nbsp;<span class="color-blue" id="publishTotalCount">0</span></div>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    /*echarts-1*/
    .metting-chart { /*margin:0 .2rem;*/ background:#fff; padding:.5rem .2rem; overflow: hidden;}
    .metting-chart h3 { text-align: center; font-size:.38rem;}
    .metting-chart-count-box .flex-item{ text-align: center; border-right:1px solid #dbdbdb; color:#666666; font-size:.34rem;}
    .metting-chart-count-box .last{ border-right:0;}
    .echarts-box { height: 3.8rem; margin-bottom: .45rem;  }
</style>
<script>
    var devicePixelRatio = window.devicePixelRatio //屏幕物理倍数
    $(function(){});

    var dom = document.getElementById('echarts-2');
    //var dom1 = document.getElementById('echarts-3');
    var dom2 = document.getElementById('echarts-5');

    var myChart = echarts.init(dom);
    //var myChart1 = echarts.init(dom1);
    var myChart2 = echarts.init(dom2);
    option = null;
    //配置图表
    option = {
        color: ['#3398DB'],
        tooltip : {
            //加大选择区域
            trigger: 'axis',
            axisPointer : {
                type : 'shadow',
                shadowStyle: {
                    opacity: 0
                }
            },
            enterable:true,
            position: ['top'],
            backgroundColor: 'rgba(0,0,0,0)',
            formatter: '{c}',
            formatter: function(params){
                params[0].value = 0;
                _current = params;
//                    console.log(params);
            }
        },
        xAxis : [
            {
                type : 'category',
                axisTick: {
                    show: false,
                    alignWithLabel: true,
                },
                axisLine: {
                    onZero: false,
                    lineStyle: {
                        color:'#dbdbdb'
                    }
                },
                axisLabel: {
                    margin: 15,
                    textStyle: {
                        color:'#888888',
                        fontSize: 9 * devicePixelRatio
                    }
                },
                offset: 15,
                boundaryGap: true,
//                    data: ['6月10日','11日','12日','13日','14日','15日','16日']
            }
        ],
        yAxis : [{
            show: false
        }],
        grid: {
            width:'100%',
            left:0
        },
        series : [
            {
                type:'bar',
                barMaxWidth: '16 * devicePixelRatio',
                itemStyle: {
                    normal: {
                        color: '#0067ef',
                        barBorderRadius: 10,
                    }
                },
                barCategoryGap: 20,

                label: {
                    normal: {
                        show: true,
                        position:'top',
                        offset: [0,-10],
                        textStyle: {
                            fontSize: 12 * devicePixelRatio,
                            color: '#0067ef'
                        }
                    },

                },
            }
        ],

    };


</script>
<script>
    var attendOffset = 0;
    var publishOffset = 0;
    const maxOffset = 20;
    function loadAttendData(offset){
        var sd  = {};
        $.ajax({
            url:'${ctx}/weixin/meet/attendStats',
            data:{'offset':offset},
            type:'post',
            dataType:'json',
            async:false,
            success:function(data){
                sd = data.data;
            }
        });
        return sd;
    }

    function loadPublishData(offset) {
        var sd  = {};
        $.ajax({
            url:'${ctx}/weixin/meet/publishStats',
            data:{'offset':offset},
            type:'post',
            dataType:'json',
            async:false,
            success:function(data){
                sd = data.data;
            }
        });
        return sd;
    }

    $(function(){
        var swiper = new Swiper('#attendDataSwiper', {
            pagination: '.swiper-pagination',
            paginationClickable: true,
            spaceBetween: 30,
            onInit:function(swiper){
                swiper.slideTo(1,0);
            },
            //切换后触发
            onSlideChangeEnd: function(swiper){
                var activeIndex = swiper.activeIndex;
                if(activeIndex < 1 && attendOffset < maxOffset){//向前滑
                    attendOffset ++;
                }else if (activeIndex > 1 && attendOffset >= 0){
                    attendOffset --;
                }
                console.log("attendOffset = "+attendOffset);
                if (attendOffset >= 0 && attendOffset <= maxOffset){
                    //初始化图表
                    if (option && typeof option === "object"){
                        var index = layer.load(1, {
                            shade: [0.1,'#000'] //0.1透明度的白色背景
                        });
                        var seriesData = loadAttendData(attendOffset);
                        $("#attendUnitCount").text(seriesData.unitCount);
                        $("#attendTotalCount").text(seriesData.totalCount);
                        //设置x轴日期
                        option.xAxis[0].data=seriesData.dateArray;

                        //设置Y轴数量
                        option.series[0].data=seriesData.countArray;

                        //将数据写入图表
                        myChart.setOption(option, true);
                        layer.close(index);
                    }
                }
                swiper.slideTo(1,0);
            },
        });

        var swiper1 = new Swiper('#publishSwiper', {
            pagination: '.swiper-pagination',
            paginationClickable: true,
            spaceBetween: 30,
            onInit:function(swiper1){
                swiper1.slideTo(1,0);
            },
            //切换后触发
            onSlideChangeEnd: function(swiper1){
                var activeIndex = swiper1.activeIndex;
                if(activeIndex < 1 && publishOffset < maxOffset){//向前滑
                    publishOffset ++;
                }else if (activeIndex > 1 && publishOffset >= 0){
                    publishOffset --;
                }
                console.log("publishOffset = "+publishOffset);
                if (publishOffset >= 0 && publishOffset <= maxOffset){
                    //初始化图表
                    if (option && typeof option === "object"){
                        var index = layer.load(1, {
                            shade: [0.1,'#000'] //0.1透明度的白色背景
                        });
                        var seriesData = loadPublishData(publishOffset);
                        $("#publishUnitCount").text(seriesData.unitCount);
                        $("#publishTotalCount").text(seriesData.totalCount);
                        //设置x轴日期
                        option.xAxis[0].data=seriesData.dateArray;

                        //设置Y轴数量
                        option.series[0].data=seriesData.countArray;

                        //将数据写入图表
                        myChart2.setOption(option, true);
                        layer.close(index);
                    }
                }
                swiper1.slideTo(1,0);
            },
        });
    });


</script>
</body>
</html>

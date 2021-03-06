<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/17
  Time: 10:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html >
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title><fmt:message key="page.meeting.button.scan_screen"/> - <fmt:message key="page.common.appName"/></title>

    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/audio.css">
    <style>
        html,body,
        #wrapper { background-color:#000}
    </style>

</head>
<body>
<div id="wrapper" >

    <%-- 投屏 --%>
    <div class="full-qrcode ${live.liveState < 3 ? '':'none'}" id="screen">
        <div class="full-qrcode-item">
            <div class="full-qrcode-box">
                <div class="qrcode" ><img src="${fileBase}${qrCodeUrl}" alt=""></div>
                <p class="t-center"><fmt:message key="page.meeting.tips.scan.continue"/></p>
            </div>
        </div>
    </div>

    <%-- 星评 --%>
    <div class="full-qrcode ${live.liveState == 3 ? '':'none'}" id="star">
        <div class="full-qrcode-item">
            <div class="full-qrcode-box">
                <div class="qrcode" ><img src="${fileBase}${starQrCodeUrl}" alt=""></div>
                <p class="t-center"><fmt:message key="page.meeting.tips.scan.star"/></p>
            </div>
        </div>
    </div>

    <%-- 结束 --%>
    <div class="full-qrcode ${live.liveState == 5 ? '':'none'}" id="cspmake">
        <div class="full-qrcode-item">
            <div class="full-qrcode-box">
                <img src="${ctxStatic}/images/full-end-logo.png" alt="">
            </div>
        </div>
    </div>


<c:choose>
    <c:when test="${live.liveState < 3}">
    <div class="swiper-fullPage">
        <div class="metting-swiper">
            <!-- Swiper -->
            <div class="swiper-container swiper-container-horizontal swiper-container-metting-full">
                <div class="swiper-wrapper" style="transform: translate3d(297.25px, 0px, 0px); transition-duration: 0ms;">
                    <c:forEach items="${course.details}" var="detail" varStatus="status">
                        <div class="swiper-slide ${status.index == 0 ? 'swiper-slide-active':''}" data-num="${status.index}"  audio-src="${detail.audioUrl}">
                            <c:choose>
                                <c:when test="${not empty detail.videoUrl}">
                                    <video src="${fileBase}${detail.videoUrl}" width="auto" height="264" controls autobuffer></video>
                                </c:when>
                                <c:otherwise>
                                    <img src="${fileBase}${detail.imgUrl}" alt="">
                                </c:otherwise>
                            </c:choose>
                            <div class="swiper-slide-metting-audio"></div>
                        </div>
                    </c:forEach>
                </div>

                <div class="metting-btn-item clearfix">
                    <span class="swiper-button-prev swiper-popup-button-prev-hook metting-button swiper-button-disabled"></span>
                    <span class="swiper-button-next swiper-popup-button-next-hook metting-button"></span>
                    <div class="swiper-pagination swiper-pagination-fraction"><span class="swiper-pagination-current">1</span> <i>|</i> <span class="swiper-pagination-total">${fn:length(course.details)}</span></div>

                </div>

            </div>
        </div>

        <div class="fullPage-button-box">
            <i class="fullPage-button fullPage-button-on fullPage-hook"></i><i class="fullPage-button fullPage-button-off fullPage-hook none"></i>
        </div>
    </div>
    </c:when>
</c:choose>
</div>
<script src="${ctxStatic}/js/audio.js"></script>
<script src="${ctxStatic}/js/swiper.jquery.js"></script>
<script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
<script src="${ctxStatic}/js/layer/layer.js"></script>
<script src="${ctxStatic}/js/screenfull.min.js"></script>
<script>
    var swiper;

    const delay = 3000;

    var needSendOrder = true;//是否需要发送指令

    var living = false;

    $(function(){


        //默认屏幕高度
        var resizeHeight = $(window).height();

        //初始化高度
        $('.swiper-fullPage').find('.swiper-container-metting-full').height(resizeHeight);

        //全屏按钮
        $('.fullPage-hook').on('click',function(){
            if (screenfull.enabled && !screenfull.element) {
                screenfull.toggle();
                $('.swiper-container-metting-full').height(window.screen.height);
            } else {
                screenfull.exit();
                $('.swiper-container-metting-full').height(resizeHeight);
            }
            $(this).addClass('none').siblings().removeClass('none');
        });

        //监控键盘ESC 返回按钮
        window.onresize = function(){
            if(!checkFull()){
                //要执行的动作
                $('.swiper-container-metting-full').height(resizeHeight);
                $(".fullPage-button-on").removeClass('none').siblings().addClass("none");
                $('.metting-btn-item').show();
                swiper.enableTouchControl();
            } else {
                $('.metting-btn-item').hide();
                swiper.disableTouchControl();
            }
        }

        function checkFull(){
            var isFull =  document.fullscreenEnabled || window.fullScreen || document.webkitIsFullScreen || document.msFullscreenEnabled;
            //to fix : false || undefined == undefined
            if(isFull === undefined) isFull = false;
            return isFull;
        }

        var prev;
        var next;
        //幻灯片轮播
        swiper = new Swiper('.swiper-container-metting-full', {
            //分页
            pagination: '.swiper-pagination',

            // 按钮
            nextButton: '.swiper-popup-button-next-hook',
            prevButton: '.swiper-popup-button-prev-hook',
            slidesPerView: 1,
            paginationType: 'fraction',
            onInit: function(swiper){
                swiper.wrapper.attr('style','transform: translate3d(0px, 0, 0);transition-duration: 0ms;');
                var playType = "${course.playType}";
                if (playType == "0"){
                    swiper.slideTo("${record.playPage}");
                } else {
                    swiper.slideTo("${live.livePage}");
                }
                needSendOrder = false;
            },
            onSlideChangeEnd:function(swiper){
                console.log("need send order = " + needSendOrder);
                if (needSendOrder && living){
                    sendOrder(swiper.activeIndex);
                }
                needSendOrder = true;
                leftTime = totalLeftTime;
            },
            onSlideChangeStart:function(swiper){
                closeVideo();
            }
        });

    });

    function closeVideo(){
        var video = $('video');
        if (video.length > 0){
            for(var i =0 ; i < video.length; i++){
                video.get(i).load();
                video.get(i).pause();
            }
        }
    }


    function skip(pageNo){
        closeVideo();
        swiper.slideTo(pageNo, 100, false);
    }

    function show(){
        scaned = true;
        $("#screen").addClass("none");
    }

    function hideFullPage(){
        $(".swiper-fullPage").addClass('none');
    }
</script>
<script type="text/javascript">
    var scaned = false;

    const totalLeftTime = 3;
    var leftTime = 3;

    var heartbeat_timer = 0;
    var last_health = -1;
    var health_timeout = 3000;
    var myWs;
    $(function(){
        //ws = ws_conn( "ws://211.100.41.186:9999" );
        myWs = ws_conn("${wsUrl}");

    });


    function keepalive( ws ){
        var time = new Date();
        if( last_health != -1 && ( time.getTime() - last_health > health_timeout ) ){
            console.log("Connection broken !!!");
        }
        else{
            console.log("Connection success ...");
        }
    }

    //websocket function
    function ws_conn( to_url ){
        to_url = to_url || "";
        if( to_url == "" ){
            return false;
        }

        clearInterval( heartbeat_timer );
        console.log("try to connect to ws url " + to_url);
        var ws = new WebSocket( to_url );

        ws.onopen=function(){
            console.log("Connected !!!")
            heartbeat_timer = setInterval( function(){keepalive(ws)}, 5000 );
        }
        ws.onerror=function(){
            console.log("Connection error !!!");
            clearInterval( heartbeat_timer );
        }
        ws.onclose=function(){
            console.log("Connection closed !!!");
            clearInterval( heartbeat_timer );
        }

        ws.onmessage=function(msg){
            var data = JSON.parse(msg.data);
            console.log("order = "+data.order + " orderFrom = " + data.orderFrom);
            if (data.order == 1 && data.orderFrom != 'web'){
                console.log("skip to page "+data.pageNum);
                needSendOrder = false;
                if (scaned){
                    skip(data.pageNum);
                }
                needSendOrder = true;
            } else if(data.order == 100){//扫码成功
                show();
            } else if(data.order == 11){//直播开始指令
                living = true;
            } else if (data.order == 13){//开启星评指令
                // 弹出扫码星评二维码界面
                hideFullPage();
                $("#screen").addClass("none");
                $("#cspmake").addClass("none");
                $("#star").removeClass("none");
            } else if(data.order == 14){//结束
                // 结束
                hideFullPage();
                $("#screen").addClass("none");
                $("#star").addClass("none");
                $("#cspmake").removeClass("none");
            }

        }

        return ws;
    }

    function sendOrder(pageNo){
        var imgUrl = $(".swiper-slide-active").find("img").attr("src");
        console.log("left time is = " + leftTime);
        if(leftTime <= 1){
            var message = {'order':1, 'courseId':${course.id}, 'pageNum':pageNo, 'orderFrom':'web', 'imgUrl':imgUrl};
            myWs.send(JSON.stringify(message));
            console.log("send sync order ");
            leftTime = totalLeftTime;
        } else {
            leftTime --;
            setTimeout(sendOrder, 1000, pageNo);
        }
    }

</script>
</body>
</html>

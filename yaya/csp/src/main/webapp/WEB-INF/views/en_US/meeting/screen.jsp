<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/17
  Time: 10:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Scan-to-Project - CSPmeeting</title>

    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">

    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.css">
    <link rel="stylesheet" href="${ctxStatic}/css/audio.css">
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
    <style>
        html,body,
        #wrapper { background-color:#000}
    </style>

</head>
<body>
<div id="wrapper" >
    <div class="full-qrcode">
        <div class="full-qrcode-item">
            <div class="full-qrcode-box">
                <div class="qrcode"><img src="${fileBase}${qrCodeUrl}" alt=""></div>
                <p class="t-center">Please scan with CSPmeeting App to continue.</p>
            </div>
        </div>
    </div>
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
    </div>
</div>
<script src="${ctxStatic}/js/audio.js"></script>
<script src="${ctxStatic}/js/swiper.jquery.js"></script>
<script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
<script src="${ctxStatic}/js/layer/layer.js"></script>
<script>
    var swiper;
    $(function(){



        //调整高度
        $('.swiper-fullPage').find('.swiper-container-metting-full').height($(window).height());



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
            },
            onSlideChangeEnd:function(swiper){
                sendOrder(swiper.activeIndex);
            },
        });

    });


    function skip(pageNo){
        swiper.slideTo(pageNo, 200, false);
    }

    function show(){
        scaned = true;
        $(".full-qrcode").addClass("none");
    }
</script>
<script type="text/javascript">
    var scaned = false;

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
            console.log("order = "+data.orderFrom);
            if (data.order == 1 && data.orderFrom == 'app'){
                console.log("skip to page "+data.pageNum);
                if (scaned){
                    skip(data.pageNum);
                }
            } else if(data.order == 100){//扫码成功
                show();
            }
        }

        return ws;
    }

    function sendOrder(pageNo){
        var message = {'order':1, 'courseId':${course.id}, 'pageNum':pageNo, 'orderFrom':'web'};
        myWs.send(JSON.stringify(message));
    }

</script>
</body>
</html>

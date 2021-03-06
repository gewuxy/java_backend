<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/9/29
  Time: 17:46
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta id="description" name="description" content="首个医学会议视频直播平台，以后医院都这样开会啦！独立直播间，同步会议现场，随时与参会医生互动，直播会议数据后台详尽记录....还等什么，快来申请使用吧" />
    <meta id="MetaKeywords" name="KEYWORDS" content="医学会议,独立直播间,医生互动" />
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>${course.title}</title>
    <link rel="stylesheet" href="${ctxStatic}/phone/css/reset.css">
    <link rel="stylesheet" href="${ctxStatic}/phone/css/swiper.css">
    <link rel="stylesheet" href="${ctxStatic}/phone/css/CSPMeeting-style.css">
    <link rel="stylesheet" href="${ctxStatic}/phone/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/phone/css/audio-black.css">

    <script src="${ctxStatic}/phone/js/screenfull.min.js"></script>
    <script src="${ctxStatic}/phone/js/audio-countDown.js"></script>
    <script src="${ctxStatic}/phone/js/swiper.jquery.js"></script>
    <script src="${ctxStatic}/phone/js/flexible.min.js"></script>
    <script src="${ctxStatic}/phone/js/perfect-scrollbar.jquery.min.js"></script>

    <script src="${ctxStatic}/phone/js/pinchzoom.min.js"></script>
    <script src="${ctxStatic}/phone/js/weui.min.js"></script>

    <!-- 高清方案 -->
    <script>!function(e){function t(a){if(i[a])return i[a].exports;var n=i[a]={exports:{},id:a,loaded:!1};return e[a].call(n.exports,n,n.exports,t),n.loaded=!0,n.exports}var i={};return t.m=e,t.c=i,t.p="",t(0)}([function(e,t){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=window;t["default"]=i.flex=function(e,t){var a=e||100,n=t||1,r=i.document,o=navigator.userAgent,d=o.match(/Android[\S\s]+AppleWebkit\/(\d{3})/i),l=o.match(/U3\/((\d+|\.){5,})/i),c=l&&parseInt(l[1].split(".").join(""),10)>=80,p=navigator.appVersion.match(/(iphone|ipad|ipod)/gi),s=i.devicePixelRatio||1;p||d&&d[1]>534||c||(s=1);var u=1/s,m=r.querySelector('meta[name="viewport"]');m||(m=r.createElement("meta"),m.setAttribute("name","viewport"),r.head.appendChild(m)),m.setAttribute("content","width=device-width,user-scalable=no,initial-scale="+u+",maximum-scale="+u+",minimum-scale="+u),r.documentElement.style.fontSize=a/2*s*n+"px"},e.exports=t["default"]}]);  flex(100, 1);</script>
    <style>
        body,html { overflow-y: hidden;}



    </style>

</head>
<body>
<div class="warp">

    <div class="CSPMeeting-gallery details-gallery <c:if test="${watermark != null && watermark.state}">
        <c:choose>
            <c:when test="${watermark.direction == 0}">logo-watermark-position-top-left</c:when>
            <c:when test="${watermark.direction == 1}">logo-watermark-position-bottom-left</c:when>
            <c:when test="${watermark.direction == 2}">logo-watermark-position-top-right</c:when>
            <c:when test="${watermark.direction == 3}">logo-watermark-position-bottom-right</c:when>
        </c:choose>
    </c:if>" >
        <!-- Swiper -->
        <div class="swiper-container gallery-top video-countDown ">
            <!--根据ID 切换 PPT列表-->
            <div class="swiper-wrapper" >
                <c:forEach items="${course.details}" var="detail" varStatus="status">
                    <div class="swiper-slide" data-num="${status.index + 1}" audio-src="${detail.temp ? '':detail.audioUrl}" istemp="${detail.temp && status.index == 0 ? 1 : 0}">
                        <c:choose>
                            <c:when test="${empty detail.videoUrl}">
                                <div class="swiper-zoom-container pinch-zoom" style="height:100%;">
                                    <div class="swiper-picture" style=" background-image:url('${detail.imgUrl}')"></div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <video src="${detail.videoUrl}"  class="video-hook" width="100%" height="100%" x5-playsinline="" playsinline="" webkit-playsinline="" poster="" preload="auto"></video><div class="isIphoneSafari"></div>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </c:forEach>
            </div>
            <!--音频文件-->
            <div class="clearfix boxAudio t-center " >
                <div class="audio-meeting-box" style="">
                    <audio controls=true id="audioPlayer" src="${course.details[0].temp ? '' : course.details[0].audioUrl}"></audio>
                </div>
            </div>
            <!--录播中音频-->
            <div class="boxAudio-loading none">
                <div class="time">
                    <span><img src="${ctxStatic}/phone/images/viedo-icon.gif" alt=""></span>
                    <span class="text">录播中</span>
                </div>
            </div>
            <!--切换菜单-->
            <div class="swiper-btn-item clearfix">
                <span class="swiper-button-prev swiper-button-disabled"></span>
                <div class="swiper-pagination"><span class="swiper-pagination-current">1</span> / <span class="swiper-pagination-total">${fn:length(course.details)}</span></div>
                <span class="swiper-button-next "></span>
            </div>

            <!--水印位置-->
            <div class="logo-watermark ">
                <div class="logo-watermark-item">${watermark.name}</div>
            </div>
        </div>

        <!--buttonBottom-->
        <div class="CSPMeeting-bottom">
            <div class="flex">
                <div class="flex-item">
                    <div class="button button-icon-info info-popup-hook">
                        <i></i>
                    </div>
                </div>
                <div class="flex-item">
                    <div class="button button-icon-volume quit-full-hook"><i class="button-icon-volume-close none"></i><i class="button-icon-volume-open "></i></div>
                </div>
                <div class="flex-item">
                    <div class="button button-icon-onlineUser"><i></i><span class="num">0</span></div>
                </div>
                <div class="flex-item">
                    <div class="button button-icon-report report-popup-button-hook"><i></i></div>
                </div>
                <div class="flex-item">
                    <div class="button button-icon-onFull changeFull-hook"><i></i></div>
                </div>

            </div>


        </div>

    </div>

    <!--新加载提示-->
    <div class="icon-added" style="display: none;"><span id="newLivePage">P&nbsp;1</span><span class="arrows"></span></div>

    <!--自动播放层-->
    <div class="html5ShadePlay"></div>

    <!--引导下载-->

</div>

<!--弹出的简介-->
<div class="CSPMeeting-meeting-info-popup meeting-info-popup">
    <div class="meeting-info-popup-main ">
        <div class="title"><h3>简介</h3></div>
        <div class="text hidden-box">

            <p>${course.info }</p>
        </div>
    </div>
</div>
<script>
    //判断访问终端
    var browser={
        versions:function(){
            var u = navigator.userAgent, app = navigator.appVersion;
            return {
                trident: u.indexOf('Trident') > -1, //IE内核
                presto: u.indexOf('Presto') > -1, //opera内核
                webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,//火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, //是否iPad
                webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
                iphoneSafari: u.indexOf('MicroMessenger') > -1
            };
        }(),
        language:(navigator.browserLanguage || navigator.language).toLowerCase()
    }

    var asAllItem = audiojs.createAll();
    var playing = false;
    var galleryTop ;
    var slideTimer ;
    var playOver = false;
    var totalPages = parseInt("${fn:length(course.details)}");
    console.log("total pages = " + totalPages);

    var heartbeat_timer = 0;
    var last_health = -1;
    var health_timeout = 3000;
    var myWs;
    var wsUrl = "${wsUrl}";
    $(function(){

        console.log("current locale is en");

        if (wsUrl){
            myWs = ws_conn(wsUrl);
        }

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
                console.log("order = "+data.order);
                if (data.onLines){
                    $(".num").text(data.onLines);
                }
                if (data.order == 0){//直播指令

                    var currentPageNo = parseInt(data.pageNum) + 1;
                    console.log("current page num = " +currentPageNo);
                    if(data.audioUrl != undefined) {
                        $(".swiper-slide[data-num='"+currentPageNo+"']").attr("audio-src", data.audioUrl);
                    }

                    console.log("data.pageNum == galleryTop.activeIndex = " + (data.pageNum == galleryTop.activeIndex));
                    if (data.pageNum == galleryTop.activeIndex){
                        swiperChangeAduio($(".swiper-wrapper"));
                    }

                } else if(data.order == 1){
                    console.log("current page num = " +totalPages);
                    var $currentPage = $(".swiper-slide:last");
                    console.log("current page is temp = " + $currentPage.attr("istemp"));
                    if ($currentPage.attr("istemp") == '1'){
                        if (data.videoUrl != null && data.videoUrl != undefined && data.videoUrl != ''){
                            $currentPage.find("video").attr("src", data.videoUrl);
                        } else {
                            $currentPage.find("div").find(".swiper-picture").css("background-image", "url('"+data.imgUrl+"')");
                        }
                        $currentPage.attr("istemp", "0");
                    } else {
                        totalPages ++;
                        $(".icon-added").show();
                        $("#newLivePage").text("P " + totalPages);

                        var newSlide = '<div class="swiper-slide" data-num="'+totalPages+'" audio-src=""><div class="swiper-zoom-container pinch-zoom" style="height:100%;"><div class="swiper-picture" style=" background-image:url('+data.imgUrl+')"></div></div></div>';
                        if (data.videoUrl){
                            newSlide = '<div class="swiper-slide" data-num="'+totalPages+'" audio-src=""><video src="'+data.videoUrl+'"  class="video-hook" width="100%" height="100%" x5-playsinline="" playsinline="" webkit-playsinline="" poster="" preload="auto"></video><div class="isIphoneSafari"></div></div>';
                        }

                        galleryTop.appendSlide(newSlide);
                        setTimeout(function(){$(".icon-added").hide()}, 5000);
                    }

                    if (playOver && !activeItemIsVideo.length){
                        galleryTop.slideNext();
                    }
                }
            }

            return ws;
        }

        function slideToNext(){
            clearTimeout(slideTimer);
            slideTimer = setTimeout(function(){galleryTop.slideNext();}, 3000);
            playOver = true;
        }

        var target = $('.layer-hospital-popup-fullSize')[0];
        var CSPMeetingGallery = $('.CSPMeeting-gallery');
        var fullState = true;
        var playerState = true;
        var swiperFullSize;
        var popupPalyer = asAllItem[asAllItem.length - 1];
        var activeItemIsVideo,prevItemIsVideo,nextItemIsVideo;
        var dataSrc ;

        var changePlayerStete = function(state){
            if(playerState || state == true){
                $('.button-icon-play').addClass('none').siblings().removeClass('none');
                playerState = false;
                //有video文件
                if(activeItemIsVideo.length > 0){
                    if(ismuted){
                        activeItemIsVideo.get(0).muted = true;
                    } else {
                        activeItemIsVideo.get(0).muted = false;
                    }
                    activeItemIsVideo.get(0).play();
                } else {
                    popupPalyer.play();
                }
            } else {
                $('.button-icon-stop').addClass('none').siblings().removeClass('none');
                playerState = true;
                //this = window

                //有video文件
                if(activeItemIsVideo.length > 0){
                    activeItemIsVideo.get(0).pause();
                } else {
                    popupPalyer.pause();
//                    $(this).on('touchstart',function(){
//                        popupPalyer.play();
//                    })
                }

            }
        }

        var prevAudioSrc;
        $("#audioPlayer")[0].addEventListener("ended", function(){
            console.log("audio ended");
            if (playing){
                if (isVideo.length == 0){
                    if($("#audioPlayer")[0].src != prevAudioSrc) {
                        galleryTop.slideNext();
                    }
                    prevAudioSrc = $("#audioPlayer")[0].src;
                }
            }
            playOver = true;
        });
//
        $("#audioPlayer")[0].addEventListener("error", function(){
            console.log("load audio source error ...");
            isVideo = $('.swiper-slide-active').find('video');
            clearTimeout(slideTimer);
            if (playing){
                if (isVideo.length == 0){
                    slideToNext();
                }
            } else {
                if (isVideo.length == 0){
                    $('.html5ShadePlay').hide();
                    popupPalyer.play();
                    playing = true;
                    changePlayerStete(false);
                    slideToNext();
                }
            }
            playOver = true;
        });



//        popupPalyer.play();
//        popupFullPalyer.play();


        var ch = window.innerHeight;

        //初始化高度
        CSPMeetingGallery.css("height", ch);

        //横竖屏切换 更换高度
        $(window).resize(function(){

            CSPMeetingGallery.css("height", window.innerHeight);
            $('.layer-hospital-popup-fullSize').show();
        });

        //播放器切换加载对应的路径
        var swiperChangeAduio = function(current){
            var swiperCurrent;

            popupPalyer.pause();
            // var swiperCurrent = current.find(".swiper-slide-active") ||  current.parents('.swiper-container-horizontal').find(".swiper-slide-active");
            if(current.find(".swiper-slide-active")){
                swiperCurrent  = current.find(".swiper-slide-active");
            }else if(current.parents('.swiper-container-horizontal').find(".swiper-slide-active")){
                swiperCurrent = current.parents('.swiper-container-horizontal').find(".swiper-slide-active");
            }
            playOver = false;

            //如果有视频
            if(activeItemIsVideo.length > 0){
                $('.boxAudio').addClass('none');
                changePlayerStete(true);
            } else {
                dataSrc = swiperCurrent.attr('audio-src');
                //如果有音频，才进行播放
                if(dataSrc.length > 0){
                    $('.boxAudio').removeClass('none');
                    popupPalyer.load(dataSrc);
                    changePlayerStete(true);
                } else {
                    popupPalyer.load('isNotSrc');
                    console.log('没加载音频');
                    $('.boxAudio').addClass('none');
                    changePlayerStete(false);
                }
            }


        }

        //初始化默认竖屏
        galleryTop = new Swiper('.gallery-top', {
            spaceBetween: 0,
            pagination: '.swiper-pagination',
            nextButton: '.swiper-button-next',
            prevButton: '.swiper-button-prev',
            paginationType: 'fraction',
            onSlideChangeStart:function(swiper){
                activeItemIsVideo = $('.swiper-slide-active').find('video');

                if(activeItemIsVideo.length > 0){
                    activeItemIsVideo.get(0).load();
                    //判断是否Iphone 的 Safari 浏览器
                    if(browser.versions.ios && browser.versions.iphoneSafari) {
                        $('.isIphoneSafari').show();
                        //判断是否已经播放完成
                        activeItemIsVideo.get(0).addEventListener('canplay',function(){
                            $('.isIphoneSafari').hide();
                        }, {once: true});
                    }
                    activeItemIsVideo.get(0).addEventListener('ended', function () {
                        console.log("video play end ...");
                        galleryTop.slideNext();
                    }, {once: true});

                }
            },
            onSlideChangeEnd:function(swiper){
                //选中的项是否有视频
                activeItemIsVideo = $('.swiper-slide-active').find('video');
                //触发切换音频
                swiperChangeAduio(swiper.wrapper.prevObject);
//                if (!dataSrc.length && !activeItemIsVideo.length){
//                    slideToNext();
//                }
                clearTimeout(slideTimer);

            },
            onSlideNextEnd:function(){
                prevItemIsVideo = $('.swiper-slide-prev').find('video');
                console.log("prevItemIsVideo = "+prevItemIsVideo.length);
                //判断前一个是否有视频
                if(prevItemIsVideo.length > 0){
                    //重新加载视频
                    prevItemIsVideo.get(0).load();
                    prevItemIsVideo.get(0).pause();
                }
            },
            onSlidePrevEnd:function(){
                nextItemIsVideo = $('.swiper-slide-next').find('video');
                console.log("nextItemIsVideo = " + nextItemIsVideo.length);
                //判断后一个是否有视频
                if(nextItemIsVideo.length > 0){
                    //重新加载视频
                    nextItemIsVideo.get(0).load();
                    nextItemIsVideo.get(0).pause();
                }
            },
            onInit: function(swiper){
                //选中的项是否有视频
                activeItemIsVideo = $('.swiper-slide-active').find('video');
                dataSrc = $('.swiper-slide-active').attr("audio-src");
                if(activeItemIsVideo.length > 0){
                    activeItemIsVideo.get(0).load();
                    //判断是否Iphone 的 Safari 浏览器
                    if(browser.versions.ios && browser.versions.iphoneSafari) {
                        $('.isIphoneSafari').show();
                    }
                    activeItemIsVideo.get(0).addEventListener('ended', function () {
                        console.log("video play end ...");
                        galleryTop.slideNext();
                    }, {once: true});

                }
//                if (!dataSrc.length && !activeItemIsVideo.length){
//                    slideToNext();
//                }

            }
        });




        //启动全屏
        $('.changeFull-hook').click(function(){
            //判断是否播放状态
            playerState ? popupPalyer.pause():popupPalyer.play();
            //是否全屏状态
            if(fullState) {
                $('.CSPMeeting-gallery').addClass('CSPMeeting-popup-fullsize');
                $('.CSPMeeting-gallery').find('.swiper-container').addClass('layer-hospital-popup');
                fullState = false;
                $(this).addClass('button-icon-onFull-off');
            } else if(fullState === false) {
//                $('.CSPMeeting-gallery-live').removeClass('popup-fullStatus');
//                galleryTop.update(true);
                $('.CSPMeeting-gallery').removeClass('CSPMeeting-popup-fullsize');
                $('.CSPMeeting-gallery').find('.swiper-container').removeClass('layer-hospital-popup');
                galleryTop.update(true);

                fullState = true;
                $(this).removeClass('button-icon-onFull-off');
            }


        });








        //超出页面下拉
        $(".hidden-box").perfectScrollbar();

        /*弹出留言*/
        $(".faq-popup-button-hook").on('click',function(){
            layer.open({
                type: 1,
                anim: 5,
                area: ['100%','100%'],
                fix: false, //不固定
                title: false,
                content: $('.faq-popup-hook'),
                success: function () {
                    popupPalyer.play();
                },
                end:function(){
                    popupPalyer.play();
                }
            })
        });

        //启动留言输入框
        $(".meeting-faq-popup-input-text").on('click',function(){
            layer.open({
                type: 1,
                anim: 2,
                area: ['100%','4rem'],
                offset:'b',
                title:false,
                content: $('.meeting-faq-popup-keyboard'),
                success: function (swiper) {
                    popupPalyer.play();
                    swiper.find('textarea').focus();
                    $(this).find('textarea').on('click',function(){
                        var target = this;
                        //解决IOS弹出输入框挡住问题
                        setTimeout(function(){
                            target.scrollIntoView(true);
                        },100)
                    });
                },
                end:function(){
                    popupPalyer.play();
                }
            })
        });

        //弹出简介
        $('.info-popup-hook').click(function(){
            layer.open({
                type: 1,
                area: ['85%','65%'],
                fix: false, //不固定
                title:false,
                skin: 'info-popup',
                content: $('.CSPMeeting-meeting-info-popup'),
                success: function (swiper) {
                    //popupPalyer.play();

                    //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
                    if(browser.isAndroid || activeItemIsVideo.length > 0){
                        activeItemIsVideo.height(0);
                    }
                },
                end:function(){
                    //popupPalyer.play();
                    //关闭时还原高度。
                    if(browser.isAndroid || activeItemIsVideo.length > 0){
                        activeItemIsVideo.height('auto');
                    }
                }
            })
        });


        //视频播放是否已结束
        var isEnded = function () {
            if(activeItemIsVideo.length > 0 && activeItemIsVideo.get(0).ended == true){
                galleryTop.slideNext();
            }
        }

        //视频网络状态
        var videoNetWorkState = function () {
            activeItemIsVideo.get(0).networkState
        }



        //播放按钮
        $(".button-icon-state").on('click',function(){
            changePlayerStete();
        })


        function isPC() {
            var userAgentInfo = navigator.userAgent;
            var Agents = ["Android", "iPhone",
                "SymbianOS", "Windows Phone",
                "iPad", "iPod"];
            var flag = true;
            for (var v = 0; v < Agents.length; v++) {
                if (userAgentInfo.indexOf(Agents[v]) > 0) {
                    flag = false;
                    break;
                }
            }
            return flag;
        }


        if (isPC()){
            $('.html5ShadePlay').hide();
            popupPalyer.play();
            playing = true;
            changePlayerStete(false);
        } else {
            //手机端 点击任何一个地方  自动播放音频
            $('.html5ShadePlay').on('touchstart',function(){
                $(this).hide();
                $('.isIphoneSafari').hide();
                galleryTop.slideTo("${fn:length(course.details) - 1}");
                popupPalyer.play();
                playing = true;
            });
        }




        var slideToPage = function (pageNum){
            galleryTop.slideTo(pageNum, 1000, false);
        };

        $('.icon-added').on('click',function(){
            //点击跳转到最后一页
            slideToPage(galleryTop.slides.length);
            swiperChangeAduio(galleryTop.wrapper.prevObject);
        });

        var ismuted = false;
        var isVideo = $('.swiper-slide-active').find('video');


        //静音
        var viedoMuted = function(){


            //直播静音
            if(activeItemIsVideo.length > 0){
                activeItemIsVideo.get(0).muted = true;
            } else {
                //音频文件静音
                popupPalyer.element.muted = true;
            }
        }

        //切换静音状态
        var changeTrack = function(){
            if(ismuted == false){
                viedoMuted();
                $('.button-icon-volume-open').addClass('none').siblings().removeClass('none');
                ismuted = true
            } else {
                if($('.swiper-slide-active').attr("audio-src") != ''){
                    popupPalyer.element.muted = false;
                } else if ($('.swiper-slide-active').find('video').length) {
                    activeItemIsVideo.get(0).muted = false;
                }
                $('.button-icon-volume-close').addClass('none').siblings().removeClass('none');
                ismuted = true
            }
        }


        $(".quit-full-hook").click(function(){
            changeTrack();
        });


        function report(type){
            $.get("${ctx}/api/meeting/report", {"type":type, "shareUrl":window.location.href, "courseId" : "${course.id}"},function (data) {
                layer.msg('Report successfully');
            },'json');
        }

        //举报按钮
        $('.report-popup-button-hook').on('click',function(){

            //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
            if(browser.isAndroid || activeItemIsVideo.length > 0){
                activeItemIsVideo.height(0);
            }
            weui.actionSheet([
                {
                    label: 'Pornography',
                    onClick: function () {
                        report(0);
                        //layer.msg('举报成功');
                    }
                }, {
                    label: 'Crime',
                    onClick: function () {
                        report(1);
                        //layer.msg('举报成功');
                    }
                }, {
                    label: 'Infringement',
                    onClick: function () {
                        report(2);
                        //layer.msg('举报成功');
                    }
                }
            ], [
                {
                    label: 'Cancel',
                    onClick: function () {
                        console.log('取消');
                        //关闭时还原高度。
                        if(browser.isAndroid || activeItemIsVideo.length > 0){
                            activeItemIsVideo.height('auto');
                        }
                    }
                }
            ], {
                className: 'custom-classname',
                onClose: function(){
                    console.log('关闭');
                    //关闭时还原高度。
                    if(browser.isAndroid || activeItemIsVideo.length > 0){
                        activeItemIsVideo.height('auto');
                    }
                }
            });
        });

        //触发图片放大缩小
        $('div.pinch-zoom').each(function () {
            new RTP.PinchZoom($(this), {});
        });

        //手机端 点击任何一个地方  自动播放音频
        $('.isIphoneSafari').on('touchstart',function(){
            $(this).hide();
            activeItemIsVideo.get(0).load();
            activeItemIsVideo.get(0).play();

        });


    });




</script>
</body>

</html>

<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/9/29
  Time: 17:46
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <meta id="description" name="description" content="首个医学会议视频直播平台，以后医院都这样开会啦！独立直播间，同步会议现场，随时与参会医生互动，直播会议数据后台详尽记录....还等什么，快来申请使用吧" />
    <meta id="KEYWORDS" name="KEYWORDS" content="医学会议,独立直播间,医生互动" />
    <%@include file="/WEB-INF/include/page_phone_context.jsp"%>
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
                    <span class="text"><fmt:message key="page.meeting.tips.recoding"/></span>
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
                <c:choose>
                    <c:when test="${course.starRateFlag}">
                        <div class="flex-item">
                            <div class="button button-icon-star star-popup-button-hook ${course.starRateFlag ? '' : 'none'}">
                                <c:choose>
                                    <c:when test="${empty rateResult.multipleResult}">
                                        <span class="off none"><i ></i><fmt:message key="page.meeting.tips.unrate"/> </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="on "><i ></i>${rateResult.multipleResult.avgScore}<fmt:message key="page.meeting.tips.score.unit"/> </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="flex-item">
                            <div class="button button-icon-info star-popup-button-hook">
                                <i></i>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div class="flex-item">
                    <div class="button button-icon-volume quit-full-hook"><i class="button-icon-volume-close none"></i><i class="button-icon-volume-open "></i></div>
                </div>
                <div class="flex-item">
                    <div class="button button-icon-onlineUser"><i></i><span class="num">0</span></div>
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

    <!--开始界面-->
    <div class="fixed-full-screen-box ${live.liveState == 0 ? '' : 'none'}" id="liveStartView">
        <div class="fixed-full-screen-logo"><div class="img"><img src="${ctxStatic}/phone/images/logo-max-img.png" alt=""></div></div>
        <div class="fixed-full-screen-main">
            <p class="t-center"><fmt:message key="page.meeting.tips.live.this"/> <fmt:formatDate value="${live.startTime}" pattern="yyyy年 MM月 dd日 HH:mm"/> <fmt:message key="page.meeting.tips.start"/></p>
        </div>
    </div>

    <!--结束界面-->
    <div class="fixed-full-screen-box ${live.liveState == 4 ? '' : 'none'}" id="liveOverView">
        <div class="fixed-full-screen-logo"><div class="img"><img src="${ctxStatic}/phone/images/logo-max-img.png" alt=""></div></div>
        <div class="fixed-full-screen-main">
            <p class="t-center live-end-icon"><img src="${ctxStatic}/phone/images/live-end-icon.png" alt=""></p>
            <p class="t-center"><fmt:message key="page.meeting.tips.total.time"/> <span id="liveDuration">${totalLiveTime}</span></p>
        </div>
    </div>

    <!--输入密码界面-->
    <div class="fixed-full-screen-box ${empty course.password || live.liveState ==4 ? 'none':''}" id="passwordView">
        <div class="fixed-full-screen-min-logo"><div class="img"><img src="${ctxStatic}/phone/images/logo-min-img.png" alt=""></div></div>
        <div class="fixed-full-screen-main fixed-full-screen-min-main">
            <p class="t-center"><fmt:message key="page.meeting.tips.watch.locked"/></p>
            <div class="fixed-row t-center pr">
                <input type="text" class="fixed-text" id="password" placeholder="<fmt:message key='page.meeting.tips.watch.password.holder'/>" maxlength=4>
            </div>
            <div class="fixed-row fixed-error error none" id="passwordError"><fmt:message key="page.meeting.tips.password.error"/></div>
            <div class="fixed-row t-center"><input type="button" onclick="checkPwd()" class="fixed-button" value="<fmt:message key='page.meeting.tips.password.confirm'/>"></div>
        </div>
    </div>
</div>

<!--弹出的简介-->
<div class="CSPMeeting-meeting-info-popup meeting-info-popup">
    <div class="meeting-info-popup-main ">
        <div class="title"><h3><fmt:message key="page.common.time"/>:<fmt:formatDate value="${live.startTime}" pattern="yyyy年MM月dd日 HH:mm"/></h3></div>
        <div class="text hidden-box">
            <p>${course.info}</p>
        </div>
        <c:if test="${course.starRateFlag}">
            <c:set var="avgScore" scope="page" value="${rateResult.multipleResult.avgScore}"/>
            <div class="star-showScore ">
                <div class="star-showScore-main clearfix">
                    <div class="fr">
                        <div class="star-box star-min">
                            <div class="star-item">
                                <c:forEach begin="1" end="5" step="1" var="curr">
                                    <c:choose>
                                        <c:when test="${avgScore >= curr}">
                                            <span class="full"></span>
                                        </c:when>
                                        <c:when test="${avgScore > curr - 1 && avgScore < curr}">
                                            <span class="half"></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="null"></span>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                            <div class="grade ">${avgScore}<fmt:message key="page.meeting.tips.score.unit"/></div>
                        </div>
                    </div>
                    <div class="star-showScore-title"><fmt:message key="page.meeting.multiple.score"/> </div>
                </div>
                <div class="star-showScore-button popup-star-button t-center">
                    <button class="button star-popup-hook"><fmt:message key="page.meeting.rate.me.want"/> </button>
                </div>
            </div>
        </c:if>
    </div>
</div>

<!--弹出选择框-->
<div class="listItme-popup">
    <div class="listItme-popup-main">
        <a href="javscript:;" class="listItme-popup-button  info-popup-hook"><fmt:message key="page.common.info"/></a>
        <c:if test="${course.starRateFlag}"><a href="javscript:;" class="listItme-popup-button star-popup-hook "><fmt:message key="page.meeting.tips.rate"/> </a></c:if>
        <a href="javscript:;" class="listItme-popup-button report-popup-button-hook"><fmt:message key="page.meeting.tips.report"/> </a>
    </div>
</div>

<!--星評彈出層-->
<div class="CSPMeeting-meeting-star-popup meeting-star-popup">
    <div class="meeting-star-popup-main ">
        <div class="meeting-star-head">
            <p><span><fmt:message key="page.meeting.rate.me.want"/></span></p>
            <h3>${course.title}</h3>
            <div class="meeting-star-img">
                <c:choose>
                    <c:when test="${empty publisher.avatar}">
                        <img src="${ctxStatic}/images/icon-video-notPlay.png" alt="">
                    </c:when>
                    <c:otherwise>
                        <img src="${publisher.avatar}"/>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="meeting-star-author"><span>${publisher.nickName}</span></div>
        </div>
        <div class="meeting-star-main clearfix">
            <!--==========================选择分数小版-->
            <c:set var="rated" scope="page" value="${not empty rateHistory}"/>
            <form id="dataForm" name="dataForm">
                <input type="hidden" name="courseId" value="${course.id}">
                <div class="meeting-star-getStarNum">
                    <c:choose>
                        <c:when test="${empty rateOptions}">
                            <div class="meeting-star-row clearfix getShowNum-min">
                                <div class="fr">
                                    <div class="star_bg">
                                        <input type="radio" id="starScore1" class="score score_1" value="1" name="score">
                                        <a href="#starScore1" class="star star_1" title="差"><label for="starScore1"></label></a>
                                        <input type="radio" id="starScore2" class="score score_2" value="2" name="score">
                                        <a href="#starScore2" class="star star_2" title="较差"><label for="starScore2"></label></a>
                                        <input type="radio" id="starScore3" class="score score_3" value="3" name="score">
                                        <a href="#starScore3" class="star star_3" title="普通"><label for="starScore3"></label></a>
                                        <input type="radio" id="starScore4" class="score score_4" value="4" name="score">
                                        <a href="#starScore4" class="star star_4" title="较好"><label for="starScore4"></label></a>
                                        <input type="radio" id="starScore5" class="score score_5" value="5" name="score">
                                        <a href="#5" class="star star_5" title="好"><label for="starScore5"></label></a>
                                    </div>
                                    <div class="grade ">${rated ? rateHistory.multipleResult.avgScore : 0}<fmt:message key="page.meeting.tips.score.unit"/> </div>
                                </div>
                                <div class="star-showScore-title"><fmt:message key="page.meeting.multiple.score"/></div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${rateOptions}" var="op" varStatus="status">
                                <div class="meeting-star-row clearfix getShowNum-min">
                                    <div class="fr">
                                        <div class="star_bg">
                                            <input type="radio" id="starDetailScore${status.index}1" ${rated && rateHistory.detailList[status.index].avgScore == 1 ? 'checked':''} class="score score_1" value="1" name="details[${status.index}].score">
                                            <a href="#starScore1" class="star star_1" title="差"><label for="starDetailScore${status.index}1"></label></a>
                                            <input type="radio" id="starDetailScore${status.index}2" ${rated && rateHistory.detailList[status.index].avgScore == 2 ? 'checked':''} class="score score_2" value="2" name="details[${status.index}].score">
                                            <a href="#starScore2" class="star star_2" title="较差"><label for="starDetailScore${status.index}2"></label></a>
                                            <input type="radio" id="starDetailScore${status.index}3" ${rated && rateHistory.detailList[status.index].avgScore == 3 ? 'checked':''} class="score score_3" value="3" name="details[${status.index}].score">
                                            <a href="#starScore3" class="star star_3" title="普通"><label for="starDetailScore${status.index}3"></label></a>
                                            <input type="radio" id="starDetailScore${status.index}4" ${rated && rateHistory.detailList[status.index].avgScore == 4 ? 'checked':''} class="score score_4" value="4" name="details[${status.index}].score">
                                            <a href="#starScore4" class="star star_4" title="较好"><label for="starDetailScore${status.index}4"></label></a>
                                            <input type="radio" id="starDetailScore${status.index}5" ${rated && rateHistory.detailList[status.index].avgScore == 5 ? 'checked':''} class="score score_5" value="5" name="details[${status.index}].score">
                                            <a href="#5" class="star star_5" title="好"><label for="starDetailScore${status.index}5"></label></a>
                                        </div>
                                        <div class="grade "><span>${!rated ? 0 : rateHistory.detailList[status.index].avgScore}</span><fmt:message key="page.meeting.tips.score.unit"/> </div>
                                    </div>
                                    <div class="star-showScore-title">${op.title}</div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </form>
        </div>
        <div class="meeting-star-button popup-star-button t-center">
            <button class="button tips ${play.playState == 4 ? '' : 'none'}" id="overBtn"><fmt:message key="page.meeting.rate.over"/></button>
            <button class="button tips ${rated ? '' : 'none'}"  id="ratedBtn"><fmt:message key="page.meeting.rate.rated"/></button>
            <button class="button disabled ${!rated && play.playState != 4 ? '' : 'none'}" onclick="doRate()" id="submitBtn"><fmt:message key="page.meeting.rate.submit"/></button>
        </div>
    </div>
</div>
<script>
    var asAllItem = audiojs.createAll();
    var playing = false;
    var galleryTop ;
    var slideTimer ;
    var playOver = false;
    var totalPages = parseInt("${fn:length(course.details)}");
    console.log("total pages = " + totalPages);

    function checkPwd(){
        var password = $("#password").val();
        if ($.trim(password) == ''){
            $("#passwordError").removeClass("none");
            return ;
        }
        $.get('${ctx}/api/meeting/share/pwd/check', {"courseId":"${course.id}", "password":password}, function (data) {
            if(data.code == 0){
                $("#passwordView").addClass("none");
            } else {
                $("#passwordError").removeClass("none");
            }
        },'json');
    }

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
                } else if(data.order == 13){//开始星评
                    // todo 弹出星评界面
                    openStarRate();
                } else if(data.order == 11){//直播開始
                    console.log("直播开始");
                    $("#liveStartView").addClass("none");
                } else if(data.order == 14){//直播结束
                    if(activeItemIsVideo.length > 0){
                        activeItemIsVideo.get(0).pause();
                    } else {
                        popupPalyer.pause();
                    }
                    getLiveDuration();
                    $("#liveOverView").removeClass("none");
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
                playerState = false;
                //有video文件
                if(activeItemIsVideo.length > 0){
                    activeItemIsVideo.get(0).muted = ismuted;
                    activeItemIsVideo.get(0).play();
                } else {
                    popupPalyer.volume = 100;
                    popupPalyer.muted = ismuted;
                    console.log("popupPalyer.muted = " + popupPalyer.muted);
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
                    layer.close(layer.index-1);
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
                $('.isIphoneSafari').hide();
                $(this).hide();
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
                ismuted = false
            }
        }


        $(".quit-full-hook").click(function(){
            changeTrack();
        });


        function report(type){
            $.get("${ctx}/api/meeting/report", {"type":type, "shareUrl":window.location.href, "courseId" : "${course.id}"},function (data) {
                layer.msg('<fmt:message key="page.meeting.report.success"/>');
            },'json');
        }

        //举报按钮
        $('.report-popup-button-hook').on('click',function(){
            layer.closeAll();
            //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
            if(browser.isAndroid || activeItemIsVideo.length > 0){
                activeItemIsVideo.height(0);
            }
            weui.actionSheet([
                {
                    label: '<fmt:message key="page.meeting.report.type.sex"/>',
                    onClick: function () {
                        report(0);
                        //layer.msg('举报成功');
                    }
                }, {
                    label: '<fmt:message key="page.meeting.report.type.crime"/>',
                    onClick: function () {
                        report(1);
                        //layer.msg('举报成功');
                    }
                }, {
                    label: '<fmt:message key="page.meeting.report.type.infringement"/>',
                    onClick: function () {
                        report(2);
                        //layer.msg('举报成功');
                    }
                }
            ], [
                {
                    label: '<fmt:message key="page.button.cancel"/>',
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

        //弹出功能选项
        $('.star-popup-button-hook').on('click',function() {
            //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
            if (browser.isAndroid || activeItemIsVideo.length > 0) {
                activeItemIsVideo.height(0);
            }
            layer.open({
                type: 1,
                anim: 5,
                area: ['90%', 'auto'],
                fix: false, //不固定
                title: false,
                shadeClose: true,
                content: $('.listItme-popup'),
                success: function () {
                    //popupPalyer.play();
                },
                end: function () {
                    //popupPalyer.play();
                    //关闭时还原高度。
                    if (browser.isAndroid || activeItemIsVideo.length > 0) {
                        activeItemIsVideo.height('auto');
                    }
                }
            })
        });

        //星评弹出
        function openStarRate() {
            layer.open({
                type: 1,
                area: ['90%', '85%'],
                fix: false, //不固定
                title: false,
                shadeClose: true,
                skin: 'info-popup',
                content: $('.CSPMeeting-meeting-star-popup'),
                success: function (swiper) {
                    layer.close(layer.index - 1);
                    //弹出星评后,关闭之前打开他的窗
//                    layer.close(layer.index-1);
//                    popupPalyer.play();
                    //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
                    if (browser.isAndroid || activeItemIsVideo.length > 0) {
                        activeItemIsVideo.height(0);
                    }
                },
                end: function () {
                    //popupPalyer.play();
                    //关闭时还原高度。
                    if (browser.isAndroid || activeItemIsVideo.length > 0) {
                        activeItemIsVideo.height('auto');
                    }
                    layer.closeAll();
                }
            })
        }

        $('.star-popup-hook').on('click', function () {
            openStarRate();
        });

        $("input[type='radio']").click(function () {
            $(this).parent().siblings("div").find("span").text($(this).val());
            var submitAble = true;
            $("input[type='radio']").parent().siblings("div").find("span").each(function () {
                if ($(this).text() == '' || $(this).text() == 0) {
                    submitAble = false;
                    return false;
                }
            });
            if (submitAble) {
                $("#submitBtn").removeClass("disabled");
            }
        });

        //评分阶段直接弹出评分
        if ("${play.playState == 3}" == "true") {
            openStarRate();
        }
    });

    //提交评分操作
    function doRate() {
        if (!$("#submitBtn").hasClass("disabled")) {
            var data = $("#dataForm").serialize();
            $.post('${ctx}/api/meeting/share/rate', data, function (result) {
                if (result.code == 0) {
                    $("#submitBtn").addClass("none");
                    $("#ratedBtn").removeClass("none");
                }
            }, 'json');
        }
    }

    function getLiveDuration(){
        var url = '${ctx}/api/meeting/share/live/duration';
        $.get(url, {"courseId":"${course.id}"}, function(data){
            if(data.code == 0){
                $("#liveDuration").text(data.data.duration);
            }
        },'json');
    }


</script>
</body>

</html>

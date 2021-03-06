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
    <%@include file="/WEB-INF/include/page_phone_context.jsp"%>
    <meta charset="UTF-8">
    <meta id="description" name="description" content="<fmt:message key='page.meeting.share.description'/>" />
    <meta id="keywords" name="keywords" content="医学会议,独立直播间,医生互动" />
    <title>${course.title}</title>
    <link rel="stylesheet" href="${ctxStatic}/phone/css/reset.css">
    <link rel="stylesheet" href="${ctxStatic}/phone/css/swiper.css">
    <link rel="stylesheet" href="${ctxStatic}/phone/css/CSPMeeting-style.css">
    <link rel="stylesheet" href="${ctxStatic}/phone/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/phone/css/audio-black-2.css">

    <script src="${ctxStatic}/phone/js/screenfull.min.js"></script>
    <script src="${ctxStatic}/phone/js/audio.js"></script>
    <script src="${ctxStatic}/phone/js/swiper.jquery.js"></script>
    <script src="${ctxStatic}/phone/js/flexible.min.js"></script>
    <script src="${ctxStatic}/phone/js/perfect-scrollbar.jquery.min.js"></script>

    <script src="${ctxStatic}/phone/js/ckplayer.js"></script>


    <script src="${ctxStatic}/phone/js/pinchzoom.min.js"></script>
    <script src="${ctxStatic}/phone/js/weui.min.js"></script>
    <%--<script src="${ctxStatic}/phone/js/vconsole.min.js"></script>--%>
    <%--<script>--%>
    <%--// init vConsole--%>
    <%--var vConsole = new VConsole();--%>
    <%--console.log('Hello world');--%>
    <%--</script>--%>

    <!-- 高清方案 -->
    <script>!function(e){function t(a){if(i[a])return i[a].exports;var n=i[a]={exports:{},id:a,loaded:!1};return e[a].call(n.exports,n,n.exports,t),n.loaded=!0,n.exports}var i={};return t.m=e,t.c=i,t.p="",t(0)}([function(e,t){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=window;t["default"]=i.flex=function(e,t){var a=e||100,n=t||1,r=i.document,o=navigator.userAgent,d=o.match(/Android[\S\s]+AppleWebkit\/(\d{3})/i),l=o.match(/U3\/((\d+|\.){5,})/i),c=l&&parseInt(l[1].split(".").join(""),10)>=80,p=navigator.appVersion.match(/(iphone|ipad|ipod)/gi),s=i.devicePixelRatio||1;p||d&&d[1]>534||c||(s=1);var u=1/s,m=r.querySelector('meta[name="viewport"]');m||(m=r.createElement("meta"),m.setAttribute("name","viewport"),r.head.appendChild(m)),m.setAttribute("content","width=device-width,user-scalable=no,initial-scale="+u+",maximum-scale="+u+",minimum-scale="+u),r.documentElement.style.fontSize=a/2*s*n+"px"},e.exports=t["default"]}]);  flex(100, 1);</script>
    <style>
        body,html { overflow-y: hidden;}



    </style>

</head>
<body>
<div class="warp">

    <div class="CSPMeeting-gallery CSPMeeting-gallery-live video-countDown " id="CSPMeeting-gallery-live">
        <!-- Swiper -->
        <div class="swiper-container gallery-top popup-volume clearfix <c:if test="${watermark != null && watermark.state}">
        <c:choose>
            <c:when test="${watermark.direction == 0}">logo-watermark-position-top-left</c:when>
            <c:when test="${watermark.direction == 1}">logo-watermark-position-bottom-left</c:when>
            <c:when test="${watermark.direction == 2}">logo-watermark-position-top-right</c:when>
            <c:when test="${watermark.direction == 3}">logo-watermark-position-bottom-right</c:when>
        </c:choose>
    </c:if>">
            <div class="swiper-wrapper" >
                <c:forEach items="${course.details}" var="detail" varStatus="status">
                    <div class="swiper-slide" data-num="${status.index + 1}" audio-src="${detail.audioUrl}" istemp="${detail.temp && status.index == 0 ? 1 : 0}">
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
            <div class="flex cspMeeting-playerItem">
                <div class="flex-item">
                    <!--音频文件-->
                    <div class="clearfix boxAudio t-center " >
                        <!--发光层-->
                        <div class="cspMeeting-black-button">
                            <!--<div class="button button-icon-state"><i class="button-icon-play"></i><i class="button-icon-stop none"></i></div>-->
                            <div class="button button-icon-volume quit-full-hook"><i class="button-icon-volume-close none"></i><i class="button-icon-volume-open "></i></div>
                        </div>
                        <div class="cspMeeting-black-blueButton"><div class="icon"></div></div>
                        <div class="audio-meeting-box" style="">
                            <!--./upload/audio/30179313.mp3-->
                            <audio controls=true id="audioPlayer" src="${course.details[0].temp ? '':course.details[0].audioUrl}"></audio>
                        </div>
                        <!--录播中音频-->
                        <div class="boxAudio-loading">
                            <div class="time">
                                <span class="text"><fmt:message key="page.meeting.tips.recoding"/>...</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cspMeeting-notice-button">
                    <!--新的更多按钮-->
                    <div class="button button-icon-info star-popup-button-hook">
                        <i></i>
                    </div>
                </div>

            </div>

            <div class="swiper-btn-item clearfix">
                <span class="swiper-button-prev swiper-button-disabled"></span>
                <div class="swiper-pagination"><span class="swiper-pagination-current">1</span> / <span class="swiper-pagination-total">${fn:length(course.details)}</span></div>
                <span class="swiper-button-next "></span>
            </div>

            <!--人数显示-->
            <div class="cspMeeting-peopleNum">
                <div class="cspMeeting-peopleNum-main">
                    <div class="icon"></div>
                    <div class="num">0</div>
                </div>

            </div>
            <!--水印位置-->
            <div class="logo-watermark ${watermark != null && watermark.state ? '' : 'none'}">
                <div class="logo-watermark-item">${watermark.name}</div>
            </div>

        </div>
        <!--添加 video-notPlay-item 背景为白色-->
        <div  class="video-play-live popup-min-screen ">
            <div id="videoWrap" class="viedoItem"></div>
            <!--断开-->
            <div class="video-notPlay-bg none"><i></i><fmt:message key="page.meeting.live.broken"/></div>
            <!--加载-->
            <div class="video-notPlay-load "><i></i></div>
            <!--默认-->
            <div class="video-notPlay ${empty live.hlsUrl ? '' : 'none'}"><i></i><fmt:message key="page.meeting.live.not.start"/></div>
        </div>
        <!--初始化视频     http://weblive.hebtv.com/live/hbws_bq/index.m3u8-->
        <script>
            var hlsUrl = 'http://17932.liveplay.myqcloud.com/live/17932_${course.id}.m3u8';
            showPlayer(hlsUrl,'videoWrap')
            function showPlayer(src, id){
                //player
                var flashvars={
                    f : '${ctxStatic}/phone/js/m3u8/m3u8.swf',
                    p : 0,
                    a : src,
                    c : 0,
                    s:4,
                    v: 0,
                    lv:1//注意，如果是直播，需设置lv:1
                };
                var params={bgcolor:'#FFF',allowFullScreen:false,allowScriptAccess:'always',wmode:'transparent'};
                var video=[src];
                CKobject.embed('${ctxStatic}/phone/js/m3u8/ckplayer.swf',id ,'ck-video','100%','100%',false, flashvars ,video, params);

                CKobject.getObjectById('ck-video').addListener("error", function(){
                    console.log("加载视频失败");
                    if ($(".video-notPlay").hasClass("none")){
                        $(".video-notPlay-bg").removeClass("none");
                        $(".video-play-live").addClass("video-notPlay-item");
                        $(".video-notPlay-load").addClass("none");
                    } else {
                        $(".video-notPlay-load").addClass("none");
                        $(".video-notPlay-bg").addClass("none");
                        $(".video-play-live").addClass("video-notPlay-item");
                    }
                    if(isAndroid){
                        //解决黑边与遮挡
//                            $("#ck-video,#videoWrap").attr('style','margin-top:9999px');
                        $("#ck-video,#videoWrap").attr('style','height:0');
                    }
                });

                CKobject.getObjectById('ck-video').addListener("ended", function(){
                    console.log("视频播放完成");
                    $(".video-notPlay-bg").removeClass("none");
                });

                CKobject.getObjectById('ck-video').addListener("play", function(){
                    //$(".video-notPlay-bg").addClass("none");
                    //$(".video-play-live").removeClass("video-notPlay-item");
                });

                CKobject.getObjectById("ck-video").addListener("sendNetStream", function(){

                });
            }
        </script>
    </div>

    <!--新加载提示-->
    <div class="icon-added" style="display: none;"><span id="newLivePage">P&nbsp;1</span><span class="arrows"></span></div>
    <div class="cspMeeting-live-title live-title-hook">
        <div class="icon live-showTitle overflowText ">${course.title}</div>
        <div class="icon live-hideTitle none"><span class="live-title-icon"></span></div>
    </div>
    <!--自动播放层-->
    <div class="html5ShadePlay"></div>

    <!--开始界面-->
    <div class="fixed-full-screen-box ${live.liveState == 0 ? '' : 'none'}" id="liveStartView">
        <div class="fixed-full-screen-logo"><div class="img"><a href="${appStoreUrl}"><img src="${ctxStatic}/phone/images/meeting-blackBg.png" alt=""></a></div></div>
        <div class="fixed-full-screen-main">
            <p class="t-center"><fmt:message key="page.meeting.tips.live.this"/> <fmt:formatDate value="${live.startTime}" pattern="yyyy年 MM月 dd日 HH:mm"/> <fmt:message key="page.meeting.tips.start"/></p>
        </div>
    </div>

    <!--结束界面-->
    <div class="fixed-full-screen-box ${live.liveState == 5 ? '' : 'none'}" id="liveOverView">
        <div class="fixed-full-screen-logo"><div class="img"><img src="${ctxStatic}/phone/images/meeting-blackBg.png" alt=""></div></div>
        <div class="fixed-full-screen-main">
            <p class="t-center live-end-icon"><img src="${ctxStatic}/phone/images/live-end-icon.png" alt=""></p>
            <p class="t-center"><fmt:message key="page.meeting.tips.total.time"/> <span id="liveDuration">${totalLiveTime}</span></p>
        </div>
    </div>

    <!--输入密码界面-->
    <div class="fixed-full-screen-box ${empty course.password ? 'none':''}" id="passwordView">
        <div class="fixed-full-screen-min-logo"><div class="img"><img src="${ctxStatic}/phone/images/logo-min-img.png" alt=""></div></div>
        <div class="fixed-full-screen-main fixed-full-screen-min-main">
            <p class="t-center"><fmt:message key="page.meeting.tips.watch.locked"/></p>
            <div class="fixed-row t-center pr">
                <input type="tel" class="fixed-text" id="password" placeholder="<fmt:message key='page.meeting.tips.watch.password.holder'/>" maxlength=4>
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
            <div class="star-showScore ">
        <c:if test="${course.starRateFlag}">
            <c:set var="avgScore" scope="page" value="${rateResult.multipleResult.avgScore}"/>
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

        </c:if>
                <div class="star-showScore-button popup-star-button t-center">
                    <c:if test="${course.starRateFlag}"><button class="button star-popup-hook"><fmt:message key="page.meeting.rate.me.want"/> </button></c:if>
                    <a href="#" class="report-button report-popup-button-hook">举报</a>
                </div>
            </div>
    </div>
</div>

<!--弹出选择框-->
<div class="listItme-popup">
    <div class="listItme-popup-main">
        <a class="listItme-popup-button  info-popup-hook"><fmt:message key="page.common.info"/></a>
        <c:if test="${course.starRateFlag}"><a class="listItme-popup-button star-popup-hook "><fmt:message key="page.meeting.tips.rate"/> </a></c:if>
        <a class="listItme-popup-button report-popup-button-hook"><fmt:message key="page.meeting.tips.report"/> </a>
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
                    <div class="fixed-box-item ${rated ? '' :'none'}"></div>
                    <c:choose>
                        <c:when test="${empty rateOptions}">
                            <div class="meeting-star-row clearfix getShowNum-min">
                                <div class="fr">
                                    <div class="star_bg">
                                        <input type="radio" id="starScore1" ${rated && rateHistory.multipleResult.avgScore == 1 ? 'checked':''} class="score score_1" value="1" name="score">
                                        <a href="#starScore1" class="star star_1" title="差"><label for="starScore1"></label></a>
                                        <input type="radio" id="starScore2" ${rated && rateHistory.multipleResult.avgScore == 2 ? 'checked':''} class="score score_2" value="2" name="score">
                                        <a href="#starScore2" class="star star_2" title="较差"><label for="starScore2"></label></a>
                                        <input type="radio" id="starScore3" ${rated && rateHistory.multipleResult.avgScore == 3 ? 'checked':''} class="score score_3" value="3" name="score">
                                        <a href="#starScore3" class="star star_3" title="普通"><label for="starScore3"></label></a>
                                        <input type="radio" id="starScore4" ${rated && rateHistory.multipleResult.avgScore == 4 ? 'checked':''} class="score score_4" value="4" name="score">
                                        <a href="#starScore4" class="star star_4" title="较好"><label for="starScore4"></label></a>
                                        <input type="radio" id="starScore5" ${rated && rateHistory.multipleResult.avgScore == 5 ? 'checked':''} class="score score_5" value="5" name="score">
                                        <a href="#5" class="star star_5" title="好"><label for="starScore5"></label></a>
                                    </div>
                                    <div class="grade "><span>${rated ? rateHistory.multipleResult.avgScore : 0}</span><fmt:message key="page.meeting.tips.score.unit"/> </div>
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
            <button class="button tips ${play.playState == 5 ? '' : 'none'}" id="overBtn"><fmt:message key="page.meeting.rate.over"/></button>
            <button class="button tips ${rated ? '' : 'none'}"  id="ratedBtn"><fmt:message key="page.meeting.rate.rated"/></button>
            <button class="button disabled ${!rated && play.playState != 5 ? '' : 'none'}" onclick="doRate()" id="submitBtn"><fmt:message key="page.meeting.rate.submit"/></button>
        </div>
    </div>
</div>

<script>
    var galleryTop;

    var totalPages = parseInt("${fn:length(course.details)}");

    var needSync = false;

    var playOver = false;
    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var activeItemIsVideo, prevItemIsVideo, nextItemIsVideo;
    function checkPwd() {
        var password = $("#password").val();
        if ($.trim(password) == '') {
            $("#passwordError").removeClass("none");
            return;
        }
        $.get('${ctx}/api/meeting/share/pwd/check', {
            "courseId": "${course.id}",
            "password": password
        }, function (data) {
            if (data.code == 0) {
                $("#passwordView").addClass("none");
                //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
                if ((isAndroid || activeItemIsVideo.length > 0) && "${live.liveState < 5}" == "true") {
                    activeItemIsVideo.attr('style', 'margin-top:0px');
                }
                if(activeItemIsVideo.length > 0){
                    activeItemIsVideo.get(0).pause();
                }
                if (isAndroid && "${live.liveState < 5}" == "true") {
                    $("#ck-video,#videoWrap").attr('style', 'margin-top:0px');
                }
            } else {
                $("#passwordError").removeClass("none");}
        }
            , 'json');
    }

    //判断手机终端类型
    var browser = {
        versions: function () {
            var u = navigator.userAgent, app = navigator.appVersion;
            return {
                trident: u.indexOf('Trident') > -1, //IE内核
                presto: u.indexOf('Presto') > -1, //opera内核
                webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,//火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, //是否iPad
                iPod: u.indexOf('iPod') > -1, //是否iPad
                webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
                iphoneSafari: u.indexOf('MicroMessenger') > -1,
                isOs69: u.match(/OS [4-9]_\d[_\d]* like Mac OS X/i)
            };
        }(),
        language: (navigator.browserLanguage || navigator.language).toLowerCase()
    }

    $(function () {
        var fullState = true;
        var ismuted = false;
        var CSPMeetingGallery = $('.CSPMeeting-gallery');
        var asAllItem = audiojs.create($("#audioPlayer"));
        var popupPalyer = asAllItem[0];


        var cH = window.innerHeight;
        var phoneDpi = window.devicePixelRatio;

        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        var isVideo = $('.swiper-slide-active').find('video');


        //直播标题点击缩放、调用的JS
        $('.live-title-hook').find('.icon').on('click',function(){
            $(this).addClass('none').siblings().removeClass('none');
        });

//        $("#audioPlayer")[0].addEventListener("ended", function(){
//
//            console.log("audio ended");
//            isVideo = $('.swiper-slide-active').find('video')
//            if (!needSync){
//                alert("skip to next page...");
//                if (isVideo.length == 0){
//                    galleryTop.slideNext();
//                }
//            }
//            playOver = true;
//        });

        CSPMeetingGallery.height(cH);
        $(window).resize(function () {
            cH = window.innerHeight;
            CSPMeetingGallery.height(cH);
        });

//        asAllItem[0].play();


        function slideToNext() {
            setTimeout(function () {
                galleryTop.slideNext();
            }, 3000);
        }

        var prevAudioSrc;
        $("#audioPlayer")[0].addEventListener("ended", function () {
            if ($("#audioPlayer")[0].src != prevAudioSrc) {
                console.log("audio ended");
                console.log("audio play over ...");
                galleryTop.slideNext();
                $(".boxAudio-loading").addClass("none");
            }
            prevAudioSrc = $("#audioPlayer")[0].src;

        });

        $("#audioPlayer")[0].addEventListener("error", function () {
            var isVideo = $('.swiper-slide-active').find('video');
            //clearTimeout(slideTimer);
            if(isVideo.length > 0){
                $(".boxAudio-loading").addClass("none");
                //$(".boxAudio").addClass("none");
            } else {
                $(".boxAudio-loading").removeClass("none");
                //$(".boxAudio").addClass("none");
            }
            //$(".boxAudio").addClass("none");
        });

        $("#audioPlayer")[0].addEventListener("loadedmetadata", function () {
            //$(".boxAudio").removeClass("none");
            $(".boxAudio-loading").addClass("none");
        });


        //手机端 点击任何一个地方  自动播放音频
        $('.html5ShadePlay').on('touchstart', function () {
            $('.isIphoneSafari').hide();
            $(this).hide();
            activeItemIsVideo = $('.swiper-slide-active').find('video');
            if (activeItemIsVideo.length > 0){
                activeItemIsVideo.get(0).load();
                activeItemIsVideo.get(0).play();
            } else {
                //播放音频
                popupPalyer.play();
                //音频文件静音
                popupPalyer.element.muted = false;
            }

            if ("${live.hlsUrl}") {
                $("#ck-video")[0].play();

                CKobject.getObjectById('ck-video').changeVolume(0);
                $("#ck-video")[0].muted = true;
            }
        });


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

        if (isPC()) {
            $('.html5ShadePlay').hide();
            popupPalyer.play();
            playing = true;
            CSPMeetingGallery.height(cH);
            $('.CSPMeeting-gallery-live').addClass("popup-fullStatus");
        } else {
            //手机端 点击任何一个地方  自动播放音频
            $('.html5ShadePlay').on('touchstart', function () {
                $(this).hide();
                if (activeItemIsVideo.length > 0){
                    activeItemIsVideo.get(0).load();
                    activeItemIsVideo.get(0).play();
                } else {
                    //播放音频
                    popupPalyer.play();
                }
                playing = true;
            });

            CSPMeetingGallery.height(cH);
            $('.CSPMeeting-gallery-live').removeClass("popup-fullStatus");
        }


        //移动到最新PPT 页
        var slideToPage = function (pageNum) {
            galleryTop.slideTo(pageNum, 1000, false);
        };

        $('.icon-added').on('click', function () {
            //点击跳转到最后一页
            galleryTop.slideTo(galleryTop.slides.length);
            //选中的项是否有视频
            activeItemIsVideo = $('.swiper-slide-active').find('video');
            //clearTimeout(slideTimer);
            swiperChangeAduio(galleryTop.wrapper.prevObject);
        });


        $(".play-hook").click(function () {

        })

        $(".full-hook").click(function () {

        })

        function mutedHanlder() {

        }

        $(".quit-full-hook").click(function () {
            if (isAndroid) {
                //解决黑边与遮挡
                $("#ck-video,#videoWrap").attr('style', 'margin-top:9999px');
                weui.actionSheet([
                    {
                        label: '<fmt:message key="page.meeting.tab.change.mute"/>',
                        onClick: function () {
                            changeTrack();
                        }
                    }, {
                        label: '<fmt:message key="page.meeting.tab.change.voice"/>',
                        onClick: function () {
                            androidChangeScreen();
                            changeScreen();
                        }
                    }
                ], [
                    {
                        label: '<fmt:message key="page.button.cancel"/>',
                        onClick: function () {
                            console.log('取消');
                            //还原设置
                            $("#ck-video,#videoWrap").attr('style', 'margin-top:0');
                        }
                    }
                ], {
                    className: 'custom-classname',
                    onClose: function () {
                        console.log('关闭');
                        $("#ck-video,#videoWrap").attr('style', 'margin-top:0');
                    }
                });
            } else {
                if (browser.versions.iPhone || browser.versions.iPad || browser.versions.iPod) {
                    // 判断系统版本号是否大于 9
                    if (browser.versions.isOs69) {
                        alert('<fmt:message key="page.meeting.warn.voice.unusable"/>');
                    } else {
                        changeTrack();
                    }
                }
            }
        });

        var autoPlay = false;

        //初始化
        galleryTop = new Swiper('.gallery-top', {
            spaceBetween: 10,
            pagination: '.swiper-pagination',
            nextButton: '.swiper-button-next',
            prevButton: '.swiper-button-prev',
            paginationType: 'fraction',
            speed:150,//解决滑动过快的BUG
            onSlideChangeStart: function (swiper) {
                activeItemIsVideo = $('.swiper-slide-active').find('video');

                if (activeItemIsVideo.length > 0) {
                    activeItemIsVideo.get(0).pause();
                    //判断是否Iphone 的 Safari 浏览器
                    if (browser.versions.ios && browser.versions.iphoneSafari) {
                        $('.isIphoneSafari').show();
                        //判断是否已经播放完成
                        activeItemIsVideo.get(0).addEventListener('canplay', function () {
                            $('.isIphoneSafari').hide();
                        }, {once: true});
                    }
                    //判断是否已经播放完成
                    activeItemIsVideo.get(0).addEventListener('ended', function () {
                        galleryTop.slideNext();
                    }, {once: true});
                }
            },
            onSlideChangeEnd: function (swiper) {
                if(autoPlay){
                    //选中的项是否有视频
                    activeItemIsVideo = $('.swiper-slide-active').find('video');

                    nextItemIsVideo = $('.swiper-slide-prev').find('video');
                    //clearTimeout(slideTimer);
                    //触发切换音频
                    swiperChangeAduio(swiper.wrapper.prevObject);
                }

                autoPlay = true;
//                CKobject.getObjectById('ck-video').play();
            },
            onSlideNextEnd: function () {
                prevItemIsVideo = $('.swiper-slide-prev').find('video');
                //判断前一个是否有视频
                if (prevItemIsVideo.length > 0) {
                    //重新加载视频
                    prevItemIsVideo.get(0).pause();
                }
            },
            onSlidePrevEnd: function () {
                nextItemIsVideo = $('.swiper-slide-next').find('video');
                //判断后一个是否有视频
                if (nextItemIsVideo.length > 0) {
                    //重新加载视频
                    nextItemIsVideo.get(0).pause();
                }
            },
            onInit: function (swiper) {
                //选中的项是否有视频
                activeItemIsVideo = $('.swiper-slide-active').find('video');
                dataSrc = $('.swiper-slide-active').attr("audio-src");
//                if (!dataSrc.length && !activeItemIsVideo.length){
//                    slideToNext();
//                }
                swiper.slideTo("${fn:length(course.details) - 1}");
                if("${live.liveState == 5}" == "true" || "${not empty course.password}" == "true"){
                    //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
                    if (isAndroid || activeItemIsVideo.length > 0) {
                        activeItemIsVideo.attr('style', 'margin-top:9999px');
                    }
                    if (isAndroid) {
                        $("#ck-video,#videoWrap").attr('style', 'margin-top:9999px');
                    }
                }
            }
        });
        //点击屏幕后自动播放
//        $('body').on('touchstart',function(){
//            //音频播放
//            popupPalyer.play();
//
//            //播放器控制条
//            CKobject.getObjectById('ck-video').allowFull(false);
//            //播放开始/暂停
//            CKobject.getObjectById('ck-video').playOrPause();
//
//
//        })


        $('.changeFull-hook').click(function () {
            if (fullState && !isPC()) {
                $('.CSPMeeting-gallery-live').addClass('popup-fullStatus');
                $('.CSPMeeting-gallery-live').find('.popup-min-screen').on('click', function () {
                    changeScreen();
//                    galleryTop.update(true);
                });
                fullState = false;
                $(this).addClass('button-icon-onFull-off');
            } else if (fullState === false && !isPC()) {
                $('.CSPMeeting-gallery-live').removeClass('popup-fullStatus');
                galleryTop.update(true);
                $('.CSPMeeting-gallery-live').find('.popup-min-screen').off('click', function () {
                    changeScreen();
                });
                fullState = true;
                $(this).removeClass('button-icon-onFull-off');
            } else {
                return false;
            }

        });

        //播放器切换加载对应的路径
        var swiperChangeAduio = function (current) {
            var swiperCurrent;
            if (current.find(".swiper-slide-active")) {
                swiperCurrent = current.find(".swiper-slide-active");
            } else if (current.parents('.swiper-container-horizontal').find(".swiper-slide-active")) {
                swiperCurrent = current.parents('.swiper-container-horizontal').find(".swiper-slide-active");
            }
            popupPalyer.pause();
            $(".swiper-slide>video").each(function(){
                $(this)[0].pause();
            });
            if(swiperCurrent.find("video").length > 0){
                //$(".boxAudio").addClass("none");
                $(".boxAudio-loading").addClass("none");
                activeItemIsVideo.get(0).load();
                activeItemIsVideo.get(0).play();
                popupPalyer.load("isNotSrc");
            } else {
                var dataSrc = swiperCurrent.attr('audio-src');
                popupPalyer.load(dataSrc);

                popupPalyer.play();
                playOver = false;
            }

        }

        //点击切换状态
        var changeScreen = function () {
            if (!isAndroid) {
                $('.popup-volume').addClass("popup-min-screen").removeClass('popup-volume');
                $(this).addClass('popup-volume').removeClass("popup-min-screen");
            }

            viedoMuted();

            if (browser.versions.iPhone || browser.versions.iPad || browser.versions.iPod) {
                // 判断系统版本号是否大于 9
                if (browser.versions.isOs69) {
                    if ($('.popup-volume').find('audio').length > 0) {
                        popupPalyer.element.play();
                        $("#ck-video")[0].load();
                        needSync = false;
                    } else if ($('.popup-volume').find('video').length) {
                        $("#ck-video")[0].play();
                        needSync = true;
                        popupPalyer.element.pause();
                    }
                }
            }
//            viedoMuted();
            if (!ismuted) {
                if ($('.popup-volume').find('audio').length > 0) {
                    popupPalyer.element.muted = false;
                    if(activeItemIsVideo.length > 0){
                        activeItemIsVideo.get(0).muted = false;
                    }
                    needSync = false;
                } else if ($('.popup-volume').find('ck-video').length) {
                    CKobject.getObjectById('ck-video').changeVolume(100);
                    $("#ck-video")[0].muted = false;
                    needSync = true;
                }
            }


            //重新渲染插件
            galleryTop.update(true);

        };
        //静音
        var viedoMuted = function () {
            //音频文件静音
            popupPalyer.element.muted = true;
            if (activeItemIsVideo.length > 0){
                activeItemIsVideo.get(0).muted = true;
            }
            //直播静音
            CKobject.getObjectById('ck-video').changeVolume(0);
            $("#ck-video")[0].muted = true;
//            CKobject.getObjectById('ck-video').play();
        }

        //兼容安卓点击按钮切换状态
        var androidChangeScreen = function () {
            $('.popup-volume').removeClass('popup-volume').addClass('popup-min-screen').siblings('div').removeClass('popup-min-screen').addClass('popup-volume');
        }

        //切换静音状态
        var changeTrack = function () {
            if (ismuted == false) {
                viedoMuted();
                $('.button-icon-volume-open').addClass('none').siblings().removeClass('none');
                $('.cspMeeting-black-blueButton').addClass('none');
                ismuted = true
            } else {
                if ($('.popup-volume').find('audio').length > 0) {
                    popupPalyer.element.muted = false;
                    if(activeItemIsVideo.length > 0){
                        activeItemIsVideo.get(0).muted = false;
                    }
                } else if ($('.popup-volume').find('ck-video').length) {
                    CKobject.getObjectById('ck-video').changeVolume(100);
                    $("#ck-video")[0].muted = false;
                }
                $('.button-icon-volume-close').addClass('none').siblings().removeClass('none');
                $('.cspMeeting-black-blueButton').removeClass('none');
                ismuted = false
            }
        }

        //绑定状态切换
        $(document).on('click', '.popup-min-screen', changeScreen);

        //阻止事件冒泡 解决iOS切换声音的BUG
        $(".quit-full-hook").click(function(evt){
            var oEvent = evt || event;
            //js阻止事件冒泡
            oEvent.cancelBubble = true;
            oEvent.stopPropagation();
        });

        //超出页面下拉
        $(".hidden-box").perfectScrollbar();

        /*弹出留言*/
        $(".faq-popup-button-hook").on('click', function () {
            layer.open({
                type: 1,
                anim: 5,
                area: ['100%', '100%'],
                fix: false, //不固定
                title: false,
                shadeClose:true,
                content: $('.faq-popup-hook'),
                success: function () {

                }
            })
        });

        //启动留言输入框
        $(".meeting-faq-popup-input-text").on('click', function () {
            layer.open({
                type: 1,
                anim: 2,
                area: ['100%', '4rem'],
                offset: 'b',
                title: false,
                shadeClose:true,
                content: $('.meeting-faq-popup-keyboard'),
                success: function (swiper) {
                    swiper.find('textarea').focus();
                    $(this).find('textarea').on('click', function () {
                        var target = this;
                        //解决IOS弹出输入框挡住问题
                        setTimeout(function () {
                            target.scrollIntoView(true);
                        }, 100)
                    });
                }
            })
        });

        var needJianjieState = true;

        //切换屏幕状态
        window.addEventListener("onorientationchange" in window ? "orientationchange" : "resize", function () {
            cH = window.innerHeight;
            if (window.orientation === 180 || window.orientation === 0) {
                console.log('竖屏状态！');
                CSPMeetingGallery.height(cH);
                $('.CSPMeeting-gallery-live').removeClass("popup-fullStatus");
            }
            if (window.orientation === 90 || window.orientation === -90) {
                console.log('横屏状态！');
                CSPMeetingGallery.height(cH);
                $('.CSPMeeting-gallery-live').addClass("popup-fullStatus");
            }
        }, false);


        $("#ck-video")[0].addEventListener('pause', function () {
            $("#ck-video")[0].play();
        })

        var heartbeat_timer = 0;
        var last_health = -1;
        var health_timeout = 3000;
        var myWs;
        var wsUrl = "${wsUrl}";
        $(function () {
            //ws = ws_conn( "ws://211.100.41.186:9999" );
            if (wsUrl) {
                myWs = ws_conn(wsUrl);
            }

        });

        function keepalive(ws) {
            var time = new Date();
            if (last_health != -1 && ( time.getTime() - last_health > health_timeout )) {
                console.log("Connection broken !!!");
            }
            else {
                console.log("Connection success ...");
            }
        }

        //websocket function
        function ws_conn(to_url) {
            to_url = to_url || "";
            if (to_url == "") {
                return false;
            }

            clearInterval(heartbeat_timer);
            var ws = new WebSocket(to_url);

            ws.onopen = function () {
                console.log("Connected !!!")
                heartbeat_timer = setInterval(function () {
                    keepalive(ws)
                }, 5000);
            }
            ws.onerror = function () {
                console.log("Connection error !!!");
                clearInterval(heartbeat_timer);
            }
            ws.onclose = function () {
                console.log("Connection closed !!!");
                clearInterval(heartbeat_timer);
            }

            ws.onmessage = function (msg) {
                var data = JSON.parse(msg.data);

                console.log("order = " + data.order);
                if (data.onLines) {
                    $(".num").text(data.onLines);
                }
                if (data.order == 0) {//直播指令
                    $("#liveStartView").addClass("none");
                    var currentPageNo = parseInt(data.pageNum) + 1;
                    console.log("data.audioUrl = " + data.audioUrl);
                    if (data.audioUrl != undefined) {
                        $(".swiper-slide[data-num='" + currentPageNo + "']").attr("audio-src", data.audioUrl);
                    }
                    if (data.pageNum == galleryTop.activeIndex && playing) {
                        swiperChangeAduio($(".swiper-wrapper"));
                    }

                    if (playOver && !needSync) {
                        galleryTop.slideNext();
                    }

                    setTimeout(function () {
                        $(".icon-added").hide()
                    }, 5000);

                } else if (data.order == 1) {//同步指令
                    $("#liveStartView").addClass("none");
                    var lastPage = $(".swiper-slide:last");
                    var temp = lastPage.attr("istemp");
                    console.log("last page is temp = " + lastPage.attr("istemp"));
                    if (temp == "1") {
                        console.log("video url = " + data.videoUrl + " - imgUrl = " + data.imgUrl);
                        if (data.videoUrl != null && data.videoUrl != undefined && data.videoUrl != '') {
                            var videoHtml = '<div class="swiper-slide" data-num="' + (totalPages) + '" audio-src=""><video src="' + data.videoUrl + '"  class="video-hook" width="100%" height="100%" x5-playsinline="" playsinline="" webkit-playsinline="" poster="" preload="auto"></video><div class="isIphoneSafari"></div></div>';
                            lastPage.html(videoHtml);
                        } else {
                            if(activeItemIsVideo.length > 0){
                                $('.swiper-slide-active').remove("video");
                                activeItemIsVideo = $('.swiper-slide-active').find("video");
                            }
                            console.log("current img url = " + data.imgUrl);
                            var audioHtml = '<div class="swiper-slide" data-num="' + (totalPages) + '" audio-src=""><div class="swiper-zoom-container pinch-zoom" style="height:100%;"><div class="swiper-picture" style=" background-image:url(' + data.imgUrl + ')"></div></div></div>';
                            lastPage.html(audioHtml);
                        }
                        lastPage.attr("istemp", "0");
                    } else {
                        $(".icon-added").show();
                        var currentPageNo = galleryTop.slides.length + 1;
                        $("#newLivePage").text("P " + currentPageNo);
                        totalPages++;
                        var newSlide = '<div class="swiper-slide" data-num="' + (totalPages) + '" audio-src=""><div class="swiper-zoom-container pinch-zoom" style="height:100%;"><div class="swiper-picture" style=" background-image:url(' + data.imgUrl + ')"></div></div></div>';
                        if (data.videoUrl != undefined && data.videoUrl != '') {
                            newSlide = '<div class="swiper-slide" data-num="' + (totalPages) + '" audio-src=""><video src="' + data.videoUrl + '"  class="video-hook" width="100%" height="100%" x5-playsinline="" playsinline="" webkit-playsinline="" poster="" preload="auto"></video><div class="isIphoneSafari"></div></div>';
                        }

                        galleryTop.appendSlide(newSlide);

                        console.log("need sync = " + needSync);

                        if (needSync) {
                            $(".boxAudio-loading").removeClass("none");
                            galleryTop.slideTo(totalPages);
                        } else {
                            $(".boxAudio-loading").addClass("none");
                        }

                        if(data.videoUrl != undefined && data.videoUrl != ''){
                            $(".boxAudio-loading").addClass("none");
                        }
                    }
                } else if (data.order == 12) {//接收到推流
                    if (playing){
                        $("#ck-video")[0].play();
                    }

                    $(".video-play-live").removeClass("video-notPlay-item");
                    $(".video-notPlay-bg").addClass("none");
                    $(".video-notPlay").addClass("none");
                    if (isAndroid) {
                        //还原
                        $("#ck-video,#videoWrap").attr('style', 'height:auto');
                    }
                } else if (data.order == 13) {//开启星评指令
                    // if (activeItemIsVideo.length > 0) {
                    //     activeItemIsVideo.get(0).pause();
                    // } else {
                    //     popupPalyer.pause();
                    // }

                    if (isAndroid) {
                        $("#ck-video,#videoWrap").attr('style', 'margin-top:9999px');
                    }
                    openStarRate();
                } else if (data.order == 11) {//直播開始
                    $("#liveStartView").addClass("none");
                } else if (data.order == 14) {//直播结束
                    if (activeItemIsVideo.length > 0) {
                        activeItemIsVideo.get(0).pause();
                    }
                    popupPalyer.pause();

                    CKobject.getObjectById('ck-video').videoClear();
                    //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
                    if (isAndroid || activeItemIsVideo.length > 0) {
                        activeItemIsVideo.attr('style', 'margin-top:9999px');
                    }
                    if (isAndroid) {
                        $("#ck-video,#videoWrap").attr('style', 'margin-top:9999px');
                    }
                    getLiveDuration();
                    $("#liveOverView").removeClass("none");
                }
            }

            return ws;
        }


        function report(type) {
            $.get("${ctx}/api/meeting/report", {
                "type": type,
                "shareUrl": window.location.href,
                "courseId": "${course.id}"
            }, function (data) {
                if (data.code == 0){
                    layer.msg('<fmt:message key="page.meeting.report.success"/>');
                } else {
                    layer.msg(data.err);
                }
            }, 'json');
        }

        //举报按钮
        $('.report-popup-button-hook').on('click', function () {
            needState = false;
            layer.closeAll();
            //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
            if (isAndroid || activeItemIsVideo.length > 0) {
                activeItemIsVideo.attr('style', 'margin-top:9999px');
            }
            //解决黑边与遮挡
            if (isAndroid) {
                $("#ck-video,#videoWrap").attr('style', 'margin-top:9999px');
            }
            weui.actionSheet([
                {
                    label: '违法犯罪',
                    onClick: function () {
                        report(1);
                        //layer.msg('举报成功');
                    }
                }, {
                    label: '侵权',
                    onClick: function () {
                        report(2);
                        //layer.msg('举报成功');
                    }
                }
            ], [
                {
                    label: '取消',
                    onClick: function () {
                        needState = true;
                        console.log('取消');
                        //关闭时还原高度。
                        if (isAndroid || activeItemIsVideo.length > 0) {
                            activeItemIsVideo.attr('style', 'margin-top:0px');
                        }
                        if (isAndroid) {
                            $("#ck-video,#videoWrap").attr('style', 'margin-top:0px');
                        }
                    }
                }
            ], {
                className: 'custom-classname',
                onClose: function () {
                    needState = true;
                    console.log('关闭');
                    //关闭时还原高度。
                    if (isAndroid || activeItemIsVideo.length > 0) {
                        activeItemIsVideo.attr('style', 'margin-top:0px');
                    }
                    if (isAndroid) {
                        $("#ck-video,#videoWrap").attr('style', 'margin-top:0');
                    }
                }
            });
        });

        //触发图片放大缩小
        $('div.pinch-zoom').each(function () {
            new RTP.PinchZoom($(this), {});
        });

        //手机端 点击任何一个地方  自动播放音频
        $('.isIphoneSafari').on('touchstart', function () {
            $(this).hide();
            activeItemIsVideo.get(0).load();
            activeItemIsVideo.get(0).play();

        });

        var needState = true;

        //弹出功能选项
        $('.star-popup-button-hook').on('click',function() {
            //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
            if (isAndroid || activeItemIsVideo.length > 0) {
                activeItemIsVideo.attr('style', 'margin-top:9999px');
            }
            if (isAndroid) {
                $("#ck-video,#videoWrap").attr('style', 'margin-top:9999px');
            }
            openInfo();
        });
        var clickOthers = true;
        //弹出简介
        function openInfo(){
            needState = false;
            layer.open({
                type: 1,
                shadeClose: true,
                area: ['85%', '65%'],
                fix: false, //不固定
                title: false,
                shadeClose:true,
                skin: 'info-popup',
                content: $('.CSPMeeting-meeting-info-popup'),
                success: function (swiper) {
                    layer.close(layer.index-1);
                    //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
                    if (isAndroid || activeItemIsVideo.length > 0) {
                        activeItemIsVideo.attr('style', 'margin-top:9999px');
                    }
                    if (isAndroid) {
                        $("#ck-video,#videoWrap").attr('style', 'margin-top:9999px');
                    }
                },
                end: function (swiper) {
                    layer.close(layer.index-1);
                    if (needJianjieState){
                        //关闭时还原高度。
                        if (isAndroid || activeItemIsVideo.length > 0) {
                            activeItemIsVideo.attr('style', 'margin-top:0px');
                        }
                        //还原设置
                        if (isAndroid) {
                            $("#ck-video,#videoWrap").attr('style', 'margin-top:0px');
                        }
                    }
                    needState = true;

                }

            })
        }
        //星评弹出
        function openStarRate() {
            needState = false;
            //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
            if (isAndroid || activeItemIsVideo.length > 0) {
                activeItemIsVideo.attr('style', 'margin-top:9999px');
            }
            if (isAndroid) {
                $("#ck-video,#videoWrap").attr('style', 'margin-top:9999px');
            }
            //打开星评之前关闭遮盖层
            $(".html5ShadePlay").hide();
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
                    needJianjieState = false;
                },
                end: function () {
                    needState = true;
                    needJianjieState = true;
                    layer.closeAll();
                    //popupPalyer.play();
                    //关闭时还原高度。
                    if (isAndroid || activeItemIsVideo.length > 0) {
                        activeItemIsVideo.attr('style', 'margin-top:0px');
                    }
                    if (isAndroid) {
                        $("#ck-video,#videoWrap").attr('style', 'margin-top:0px');
                    }
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
        if ("${live.liveState == 3 && course.starRateFlag}" == "true") {
            openStarRate();
        }

        //切换屏幕状态
        window.addEventListener("onorientationchange" in window ? "orientationchange":"resize", function(){

            if (window.orientation === 180 || window.orientation === 0) {
                setTimeout(function () {
                    console.log('竖屏状态！');
                    cH = window.innerHeight ;
                    $('.CSPMeeting-gallery-live').removeClass("popup-fullStatus");

                    CSPMeetingGallery.height(cH);

                    //重新渲染插件
                    galleryTop.update(true);
                },400);
            }
            if (window.orientation === 90 || window.orientation === -90 ){
                console.log('横屏状态！');
                cH = window.innerHeight ;
                setTimeout(function () {
                    $('.CSPMeeting-gallery-live').addClass("popup-fullStatus");
                    CSPMeetingGallery.height(cH);
                    //重新渲染插件
                    galleryTop.update(true);
                },200);
            }
        }, false);
    });

    //提交评分
    function doRate(){
        if(!$("#submitBtn").hasClass("disabled")){
            var data = $("#dataForm").serialize();
            $.post('${ctx}/api/meeting/share/rate', data, function (result) {
                if(result.code == 0){
                    $("#submitBtn").addClass("none");
                    $("#ratedBtn").removeClass("none");

                    $(".fixed-box-item").removeClass("none");
                }
            }, 'json');
        }
    }

    function getLiveDuration() {
        var url = '${ctx}/api/meeting/share/live/duration';
        $.get(url, {"courseId": "${course.id}"}, function (data) {
            if (data.code == 0) {
                $("#liveDuration").text(data.data.duration);
            }
        }, 'json');
    }

</script>
</body>
</html>

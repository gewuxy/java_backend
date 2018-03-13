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
    <meta id="KEYWORDS" name="KEYWORDS" content="医学会议,独立直播间,医生互动" />
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
    <script src="${ctxStatic}/phone/js/pinchzoom.min.js"></script>
    <script src="${ctxStatic}/phone/js/weui.min.js"></script>

    <!-- 高清方案 -->
    <script>!function(e){function t(a){if(i[a])return i[a].exports;var n=i[a]={exports:{},id:a,loaded:!1};return e[a].call(n.exports,n,n.exports,t),n.loaded=!0,n.exports}var i={};return t.m=e,t.c=i,t.p="",t(0)}([function(e,t){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=window;t["default"]=i.flex=function(e,t){var a=e||100,n=t||1,r=i.document,o=navigator.userAgent,d=o.match(/Android[\S\s]+AppleWebkit\/(\d{3})/i),l=o.match(/U3\/((\d+|\.){5,})/i),c=l&&parseInt(l[1].split(".").join(""),10)>=80,p=navigator.appVersion.match(/(iphone|ipad|ipod)/gi),s=i.devicePixelRatio||1;p||d&&d[1]>534||c||(s=1);var u=1/s,m=r.querySelector('meta[name="viewport"]');m||(m=r.createElement("meta"),m.setAttribute("name","viewport"),r.head.appendChild(m)),m.setAttribute("content","width=device-width,user-scalable=no,initial-scale="+u+",maximum-scale="+u+",minimum-scale="+u),r.documentElement.style.fontSize=a/2*s*n+"px"},e.exports=t["default"]}]);  flex(100, 1);</script>
    <style>
        body,html { overflow-y: hidden;}



    </style>

</head>
<body <c:if test="${not empty theme && not empty theme.imgUrl}">style="background: url('${theme.imgUrl}') no-repeat fixed;background-size: cover;"</c:if> >
<div class="warp">
    <div class="CSPMeeting-gallery details-gallery clearfix
    <c:if test="${watermark != null && watermark.state}">
        <c:choose>
            <c:when test="${watermark.direction == 0}">logo-watermark-position-top-left</c:when>
            <c:when test="${watermark.direction == 1}">logo-watermark-position-bottom-left</c:when>
            <c:when test="${watermark.direction == 2}">logo-watermark-position-top-right</c:when>
            <c:when test="${watermark.direction == 3}">logo-watermark-position-bottom-right</c:when>
        </c:choose>
    </c:if>
    " >
        <div class="CSPMeeting-recorded-title overflowText">
            <span class="icon ">${course.title}</span>
        </div>
        <!-- Swiper -->
        <div class="swiper-container gallery-top video-countDown ">
            <!--根据ID 切换 PPT列表-->
            <div class="swiper-wrapper" >
                <c:forEach items="${course.details}" var="detail" varStatus="status">
                    <div class="swiper-slide swiper-slide-active" data-num="0" audio-src="${detail.audioUrl}">
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
                <div class="swiper-slide swiper-slide-active" data-num="0" audio-src="">
                    <div class="swiper-picture meeting-last-img" style="display:block; background-image:url('${ctxStatic}/phone/images/meeting-blackBg.png');"></div>
                </div>
            </div>

            <div class="flex cspMeeting-playerItem">
                <div class="flex-item">
                    <!--音频文件-->
                    <div class="clearfix boxAudio t-center " >
                        <!--发光层-->
                        <div class="cspMeeting-black-button">
                            <div class="button button-icon-state"><i class="button-icon-play"></i><i class="button-icon-stop none"></i></div>

                        </div>
                        <div class="cspMeeting-black-blueButton"><div class="icon"></div></div>
                        <div class="audio-meeting-box" style="">
                            <audio controls=true id="audioPlayer" src="${course.details[0].audioUrl}"></audio>
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

            <!--录播中音频-->
            <div class="boxAudio-loading none">
                <div class="time">
                    <span><img src="${ctxStatic}/phone/images/viedo-icon.gif" alt=""></span>
                    <span class="text">Recording...</span>
                </div>
            </div>
            <!--切换菜单-->
            <div class="swiper-btn-item clearfix">
                <span class="swiper-button-prev swiper-button-disabled"></span>
                <div class="swiper-pagination"><span class="swiper-pagination-current">1</span> / <span class="swiper-pagination-total">${fn:length(course.details)}</span></div>
                <span class="swiper-button-next "></span>
            </div>

            <!--水印位置-->
            <div class="logo-watermark ${watermark != null && watermark.state ? '' : 'none'}">
                <div class="logo-watermark-item">${watermark.name}</div>
            </div>
        </div>

    </div>

    <!--自动播放层-->
    <div class="html5ShadePlay"></div>

    <!--输入密码界面-->
    <div class="fixed-full-screen-box ${empty course.password || course.password eq ''? 'none':''}" id="passwordView">
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
        <div class="title"><h3><fmt:message key="page.common.info"/> </h3></div>
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
        <a  class="listItme-popup-button  info-popup-hook"><fmt:message key="page.common.info"/></a>
        <c:if test="${course.starRateFlag}"><a class="listItme-popup-button star-popup-hook "><fmt:message key="page.meeting.tips.rate"/> </a></c:if>
        <a  class="listItme-popup-button report-popup-button-hook"><fmt:message key="page.meeting.tips.report"/> </a>
    </div>
</div>
<c:set var="rated" scope="page" value="${not empty rateHistory}"/>
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
            <form id="dataForm" name="dataForm">
                <input type="hidden" name="courseId" value="${course.id}">
            <div class="meeting-star-getStarNum">
                <div class="fixed-box-item ${rated ? '' :'none'}"></div>
                <c:choose>
                    <c:when test="${empty rateOptions}">
                        <div class="meeting-star-row clearfix getShowNum-min">
                            <div class="fr">
                                <div class="star_bg">
                                    <input type="radio" id="starScore1" class="score score_1" ${rated && rateHistory.multipleResult.avgScore == 1 ? 'checked':''} value="1" name="score">
                                    <a  class="star star_1" title="差"><label for="starScore1"></label></a>
                                    <input type="radio" id="starScore2" class="score score_2" ${rated && rateHistory.multipleResult.avgScore == 2 ? 'checked':''} value="2" name="score">
                                    <a  class="star star_2" title="较差"><label for="starScore2"></label></a>
                                    <input type="radio" id="starScore3" class="score score_3" ${rated && rateHistory.multipleResult.avgScore == 3 ? 'checked':''}  value="3" name="score">
                                    <a  class="star star_3" title="普通"><label for="starScore3"></label></a>
                                    <input type="radio" id="starScore4" class="score score_4" ${rated && rateHistory.multipleResult.avgScore == 4 ? 'checked':''}  value="4" name="score">
                                    <a  class="star star_4" title="较好"><label for="starScore4"></label></a>
                                    <input type="radio" id="starScore5" class="score score_5" ${rated && rateHistory.multipleResult.avgScore == 5 ? 'checked':''} value="5" name="score">
                                    <a  class="star star_5" title="好"><label for="starScore5"></label></a>
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
                                        <a class="star star_1" title="差"><label for="starDetailScore${status.index}1"></label></a>
                                        <input type="radio" id="starDetailScore${status.index}2"  ${rated && rateHistory.detailList[status.index].avgScore == 2 ? 'checked':''} class="score score_2" value="2" name="details[${status.index}].score">
                                        <a class="star star_2" title="较差"><label for="starDetailScore${status.index}2"></label></a>
                                        <input type="radio" id="starDetailScore${status.index}3"  ${rated && rateHistory.detailList[status.index].avgScore == 3 ? 'checked':''} class="score score_3" value="3" name="details[${status.index}].score">
                                        <a class="star star_3" title="普通"><label for="starDetailScore${status.index}3"></label></a>
                                        <input type="radio" id="starDetailScore${status.index}4"  ${rated && rateHistory.detailList[status.index].avgScore == 4 ? 'checked':''} class="score score_4" value="4" name="details[${status.index}].score">
                                        <a class="star star_4" title="较好"><label for="starDetailScore${status.index}4"></label></a>
                                        <input type="radio" id="starDetailScore${status.index}5"  ${rated && rateHistory.detailList[status.index].avgScore == 5 ? 'checked':''} class="score score_5" value="5" name="details[${status.index}].score">
                                        <a class="star star_5" title="好"><label for="starDetailScore${status.index}5"></label></a>
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
                <button class="button tips ${play.playState >= 4 ? '' : 'none'}" id="overBtn"><fmt:message key="page.meeting.rate.over"/></button>
                <button class="button tips ${rated ? '' : 'none'}"  id="ratedBtn"><fmt:message key="page.meeting.rate.rated"/></button>
                <button class="button disabled ${!rated && play.playState != 4 ? '' : 'none'}" onclick="doRate()" id="submitBtn"><fmt:message key="page.meeting.rate.submit"/></button>
        </div>

    </div>
</div>
<c:if test="${not empty theme && not empty theme.url}">
    <audio id="bgMusicAudio" src="${theme.url}" loop/>
</c:if>



<script>
    var asAllItem = audiojs.create($("#audioPlayer").get(0));
    var playing = false;
    var started = false;
    var hasAudioUrl = true;
    var slideTimer ;

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

    $(function(){
        var isVideo = $('.swiper-slide-active').find('video');

        function slideToNext(){
            clearTimeout(slideTimer);
            slideTimer = setTimeout(function(){
                if(!playerState){
                    galleryTop.slideNext();
                }
            }, 3000);
        }

        var target = $('.layer-hospital-popup-fullSize')[0];
        var CSPMeetingGallery = $('.CSPMeeting-gallery');
        var fullState = true;
        var playerState = true;
        var swiperFullSize;
        var popupPalyer = asAllItem;
        var activeItemIsVideo,prevItemIsVideo,nextItemIsVideo;
        var dataSrc ;

        var prevAudioSrc;
        $("#audioPlayer")[0].addEventListener("ended", function(){
            if(activeItemIsVideo.length == 0){
                console.log("audio play over ...");
                //if($("#audioPlayer")[0].src != prevAudioSrc){
                    galleryTop.slideNext();
                //}
                prevAudioSrc = $("#audioPlayer")[0].src;
                changeBgMusicValue(true);
            }
        });
//
        $("#audioPlayer")[0].addEventListener("error", function(){
            isVideo = $('.swiper-slide-active').find('video');
            console.log("load audio source error ...");
            console.log("is playing = " + playing);
            console.log("isVideo.length == " + isVideo.length);
            //$(".boxAudio").addClass("none");
            console.log("playerState = " + playerState);
            hasAudioUrl = false;
            changeBgMusicValue(true);
            clearTimeout(slideTimer);
            if (started){
                if (isVideo.length == 0 && !playerState){
                    slideToNext();
                }
            }
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

        var listenerEnd = false;
        //初始化默认竖屏
        var galleryTop = new Swiper('.gallery-top', {
            spaceBetween: 0,
            pagination: '.swiper-pagination',
            nextButton: '.swiper-button-next',
            prevButton: '.swiper-button-prev',
            paginationType: 'fraction',
            speed:150,//解决滑动过快的BUG

            onSlideChangeStart:function(swiper){
                activeItemIsVideo = $('.swiper-slide-active').find('video');

                if(activeItemIsVideo.length > 0){
                    activeItemIsVideo.get(0).pause();
                    //判断是否Iphone 的 Safari 浏览器
                    if(browser.versions.ios && browser.versions.iphoneSafari) {
                        $('.isIphoneSafari').show();
                        activeItemIsVideo.get(0).play();
                        //判断是否已经播放完成
                        activeItemIsVideo.get(0).addEventListener('canplay',function(){
                            $('.isIphoneSafari').hide();
                            //$('.boxAudio').addClass('none');
                        }, {once: true});
                    } else {
                        activeItemIsVideo.get(0).play();
                    }
                    activeItemIsVideo.get(0).removeEventListener('ended', videoToNextAtOnce);
                    activeItemIsVideo.get(0).addEventListener('ended', videoToNextAtOnce, {once: true});

                }
            },
            onSlideChangeEnd:function(swiper){
                //选中的项是否有视频
                activeItemIsVideo = $('.swiper-slide-active').find('video');

                nextItemIsVideo = $('.swiper-slide-prev').find('video');
                clearTimeout(slideTimer);
                //触发切换音频
                swiperChangeAduio(swiper.wrapper.prevObject);
//                if (!dataSrc.length && !activeItemIsVideo.length){
//                    slideToNext();
//                }
                if(swiper.isEnd == true){
                    $(".boxAudio").addClass("none");
                    if("${course.starRateFlag && !rated}" == "true"){
                        openStarRate();
                    }
                } else {
                    $(".boxAudio").removeClass("none");
                }

            },
            onSlideNextEnd:function(){
                prevItemIsVideo = $('.swiper-slide-prev').find('video');
                //判断前一个是否有视频
                if(prevItemIsVideo.length > 0){
                    //重新加载视频
                    prevItemIsVideo.get(0).pause();
                }

            },
            onSlidePrevEnd:function(){
                nextItemIsVideo = $('.swiper-slide-next').find('video');
                //判断后一个是否有视频
                if(nextItemIsVideo.length > 0){
                    //重新加载视频
                    nextItemIsVideo.get(0).pause();
                }
            },
            onInit: function(swiper){
                //选中的项是否有视频
                activeItemIsVideo = $('.swiper-slide-active').find('video');
                if(activeItemIsVideo.length > 0){
                    activeItemIsVideo.get(0).load();
                    //判断是否Iphone 的 Safari 浏览器
                    if(browser.versions.ios && browser.versions.iphoneSafari) {
                        $('.isIphoneSafari').show();
                    }
                    activeItemIsVideo.get(0).addEventListener('ended', videoToNextAtOnce, {once: true});

                }
                dataSrc = $('.swiper-slide-active').attr("audio-src");
//                if (!dataSrc.length && !activeItemIsVideo.length){
//                    slideToNext();
//                }
            }
        });


        function videoToNextAtOnce(){
            console.log("video play end ...");
            changeBgMusicValue(true);
            galleryTop.slideNext();
        }


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

        //弹出简介
        function openInfo(){
            clickOthers = false;
            if (browser.isAndroid || activeItemIsVideo.length > 0) {
                activeItemIsVideo.height(0);
            }
            layer.open({
                type: 1,
                area: ['95%','85%'],
                fix: false, //不固定
                title:false,
                skin: 'info-popup',
                shadeClose:true,
                content: $('.CSPMeeting-meeting-info-popup'),
                success: function (swiper) {
                    layer.close(layer.index-1);
                    //popupPalyer.play();
                    //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
                    if(browser.isAndroid || activeItemIsVideo.length > 0){
                        activeItemIsVideo.attr('style','margin-top:99999px;');
                    }
                },
                end:function(){
                    //popupPalyer.play();
                    //关闭时还原高度。
                    if(browser.isAndroid || activeItemIsVideo.length > 0){
                        if (clickOthers){
                            activeItemIsVideo.attr('style','margin-top:99999px;');
                        } else {
                            activeItemIsVideo.attr('style','margin-top:0px;');
                        }
                    }
                    clickOthers = false;
                }
            })
        }

        $('.info-popup-hook').click(function(){

        });


        //视频播放是否已结束
//        var isEnded = function () {
//            if(activeItemIsVideo.length > 0 && activeItemIsVideo.get(0).ended == true){
//                galleryTop.slideNext();
//            }
//        }

        //视频网络状态
        var videoNetWorkState = function () {
            activeItemIsVideo.get(0).networkState
        }

        const maxBgVolume = 1;
        const minBgVolume = 0.2;
        var hasBgMusic = $("#bgMusicAudio").length > 0;
        if (hasBgMusic) {
            $("#bgMusicAudio").get(0).volume = maxBgVolume;
        }

        function changeBgMusicValue(maxVolumeFlag){
            // if(hasBgMusic) {
            //     if (maxVolumeFlag){
            //         $("#bgMusicAudio").get(0).volume = maxBgVolume;
            //     } else {
            //         $("#bgMusicAudio").get(0).volume = minBgVolume;
            //     }
            // }
        }




        //播放器切换加载对应的路径
        var swiperChangeAduio = function(current){
            hasAudioUrl = true;
            playing = true;
            var swiperCurrent;

            popupPalyer.pause();
            // var swiperCurrent = current.find(".swiper-slide-active") ||  current.parents('.swiper-container-horizontal').find(".swiper-slide-active");
            if(current.find(".swiper-slide-active")){
                swiperCurrent  = current.find(".swiper-slide-active");
            }else if(current.parents('.swiper-container-horizontal').find(".swiper-slide-active")){
                swiperCurrent = current.parents('.swiper-container-horizontal').find(".swiper-slide-active");
            }

            //如果有视频
            if(activeItemIsVideo.length > 0){
                activeItemIsVideo[0].load();
                popupPalyer.load('isNotSrc');
                changePlayerStete(true);
            } else {
                dataSrc = swiperCurrent.attr('audio-src');
                //如果有音频，才进行播放
                if(dataSrc.length > 0){
                    $('.boxAudio').removeClass('none');
                    popupPalyer.load(dataSrc);
                    popupPalyer.play();
                    changePlayerStete(true);
                } else {
                    popupPalyer.load('isNotSrc');
                    console.log('没加载音频');
                    //$('.boxAudio').addClass('none');
                    changePlayerStete(true);
                    slideToNext();
                }
            }


        }

        var localFlag = false;


        //播放按钮
        $(".button-icon-state").on('click',function(){
            playing = !playing;
            changePlayerStete();
        })

        var changePlayerStete = function(state){
            if(playerState || state == true){
                playerState = false;
                started = true;
                popupPalyer.play();
                //有video文件
                if(activeItemIsVideo.length > 0){
                    //$('.boxAudio').addClass('none');
                    //activeItemIsVideo.get(0).load();
                    activeItemIsVideo.get(0).play();
                    if($("#bgMusicAudio").length > 0){
                        $("#bgMusicAudio").get(0).pause();
                    }
                } else {
                    if ($("#bgMusicAudio").length > 0){
                        $("#bgMusicAudio").get(0).play();
                    }
                    popupPalyer.play();
                    if (!hasAudioUrl){
                        slideToNext();
                    }
                }
                changeBgMusicValue(false);
            } else {
                if ($("#bgMusicAudio").length > 0){
                    $("#bgMusicAudio").get(0).pause();
                }
                playerState = true;
                //有video文件
                if(activeItemIsVideo.length > 0){
                    activeItemIsVideo.get(0).pause();
                } else {
                    popupPalyer.pause();
                }
                changeBgMusicValue(true);

            }
            if (!playerState){
                $('.button-icon-play').addClass('none').siblings().removeClass('none');
                $('.cspMeeting-black-blueButton').removeClass('none');
            } else {
                $('.button-icon-stop').addClass('none').siblings().removeClass('none');
                $('.cspMeeting-black-blueButton').addClass('none');
            }
        }


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
            changePlayerStete(true);
        } else {
            //手机端 点击任何一个地方  自动播放音频
            $('.html5ShadePlay').on('touchstart',function(){
                $('.isIphoneSafari').hide();
                if($("#bgMusicAudio").length > 0){
                    $("#bgMusicAudio").get(0).play();
                }
                $(this).hide();
                started = true;
                playing = true;
                changePlayerStete(true);
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


        //切换屏幕状态
        window.addEventListener("onorientationchange" in window ? "orientationchange":"resize", function(){
            if (window.orientation === 180 || window.orientation === 0) {
                setTimeout(function () {
                    ch = window.innerHeight;
                    console.log('进入了');
                    CSPMeetingGallery.height(ch);

                    //重新渲染插件
                    galleryTop.update(true);
                },400);
            }
            if (window.orientation === 90 || window.orientation === -90 ){
                setTimeout(function () {
                    ch = window.innerHeight;
                    CSPMeetingGallery.height(ch);
                    //重新渲染插件
                    galleryTop.update(true);
                },200);
            }
        }, false);

        function report(type){
            $.get("${ctx}/api/meeting/report", {"type":type, "shareUrl":window.location.href, "courseId" : "${course.id}"},function (data) {
                if (data.code == 0){
                    layer.msg('<fmt:message key="page.meeting.report.success"/>');
                } else {
                    layer.msg(data.err);
                }
            },'json');
        }

        //举报按钮
        $('.report-popup-button-hook').on('click',function(){
            clickOthers = true;
            layer.closeAll();
            //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
            if(browser.isAndroid || activeItemIsVideo.length > 0){
                activeItemIsVideo.attr('style','margin-top:99999px;');
            }
            weui.actionSheet([
                {
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
                            activeItemIsVideo.attr('style','margin-top:0');
                        }
                        clickOthers = false;
                    }
                }
            ], {
                className: 'custom-classname',
                onClose: function(){
                    console.log('关闭');
                    //关闭时还原高度。
                    if(browser.isAndroid || activeItemIsVideo.length > 0){
                        activeItemIsVideo.attr('style','margin-top:0');
                    }
                    clickOthers = false;
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

        var clickOthers = false;
        //弹出功能选项
        $('.star-popup-button-hook').on('click',function() {
            //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
            if(browser.isAndroid || activeItemIsVideo.length > 0){
                activeItemIsVideo.attr('style', 'margin-top:9999px');
            }
            openInfo();
        });

        //星评弹出
        function openStarRate(){
            clickOthers = true;
            $(".html5ShadePlay").hide();
            if (browser.isAndroid || activeItemIsVideo.length > 0) {
                activeItemIsVideo.height(0);
            }
            layer.open({
                type: 1,
                area: ['90%','85%'],
                fix: false, //不固定
                title:false,
                shadeClose : true,
                skin: 'info-popup',
                content: $('.CSPMeeting-meeting-star-popup'),
                success: function (swiper) {
                    layer.close(layer.index-1);
                    //弹出星评后,关闭之前打开他的窗
//                    layer.close(layer.index-1);
//                    popupPalyer.play();
                    //如果是安卓机器，而且有视频。打开后将高度设为0。为了解决遮挡的BUG
                    if(browser.isAndroid || activeItemIsVideo.length > 0){
                        activeItemIsVideo.attr('style','margin-top:99999px;');
                    }
                },
                end:function(){
                    //popupPalyer.play();
                    //关闭时还原高度。
                    if(browser.isAndroid || activeItemIsVideo.length > 0){
                        activeItemIsVideo.attr('style','margin-top:0');
                    }
                    layer.closeAll();
                    clickOthers = false;
                }
            })
        }
        $('.star-popup-hook').on('click',function(){
            openStarRate();
        });

        $("input[type='radio']").click(function(){
            $(this).parent().siblings("div").find("span").text($(this).val());
            var submitAble = true;
            $("input[type='radio']").parent().siblings("div").find("span").each(function(){
                if($(this).text() == '' || $(this).text() == 0){
                    submitAble = false;
                    return false;
                }
            });

            if(submitAble){
                $("#submitBtn").removeClass("disabled");
            }
        });

        if("${play.playState == 3 && course.starRateFlag}" == "true"){
            openStarRate();
        }
    });

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
</script>
</body>

</html>

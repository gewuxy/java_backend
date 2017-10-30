<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/6/12
  Time: 13:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title></title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.min.css" />
    <link rel="stylesheet" href="${ctxStatic}/css/audio-black.css">
    <script src="${ctxStatic}/js/jquery.min.js"></script>
    <script src="${ctxStatic}/js/slide.js"></script>
    <script src="${ctxStatic}/js/swiper.jquery.min.js"></script>
    <script src="${ctxStatic}/js/audio.js"></script>
    <script src="${ctxStatic}/js/layer/layer.js"></script>
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="${ctxStatic}/js/screenfull.min.js"></script>
    <script src="${ctxStatic}/js/popupAudioPalyer.js"></script>

</head>
<body>
<div class="layer-hospital-popup layer-black  layer-hospital-popup-hook" style="height: 800px;">
    <div class="swiperBox mettingSwiperBox clearfix"  style="height: 760px;">
        <!--预览弹出层-->
        <div class="metting-swiper">
            <!-- Swiper -->
            <div class="swiper-container swiper-container-horizontal swiper-container-hook">
                <!--根据ID 切换 PPT列表-->
                <div class="swiper-wrapper" >
                    <c:forEach items="${course.details}" var="detail">
                        <div class="swiper-slide swiper-slide-active" data-num="0" audio-src="${appFileBase}${detail.audioUrl}">
                            <div class="swiper-picture" style=" background-image:url('${appFileBase}${detail.imgUrl}')"></div>
                        </div>
                    </c:forEach>

                </div>
                <!-- Add Pagination -->

                <div class="metting-btn-item clearfix" style="z-index: 12">
                    <span class="swiper-button-prev swiper-popup-button-prev-hook metting-button swiper-button-disabled"></span>
                    <div class="swiper-pagination swiper-pagination-fraction"><span class="swiper-pagination-current">1</span> / <span class="swiper-pagination-total">${fn:length(course.details)}</span></div>
                    <span class="swiper-button-next swiper-popup-button-next-hook metting-button"></span>
                </div>

            </div>
        </div>
        <!--音频文件-->
        <div class="clearfix boxAudio t-center" style="z-index: 11">
            <div class="audio-metting-box" style="">
                <audio controls=true id="video1" src=""></audio>
            </div>
        </div>
        <!--切换全屏按钮-->
        <!--<button class="swiper-changeFullSize-button changeFullSize-icon changeFull-hook" title="全屏观看"><span></span></button>-->

        <!--标题栏-->
        <div class="swiper-slide-title" style="z-index: 11">
            <div class="title overflowText">${course.title}</div>
        </div>

    </div>
</div>


<script>
    $(".layer-hospital-popup-hook").show();
</script>
</body>
</html>


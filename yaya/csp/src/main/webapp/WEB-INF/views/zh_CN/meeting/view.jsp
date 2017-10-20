<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/20
  Time: 9:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>预览</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<div class="layer-hospital-popup-main ">
    <div class="tab-subPage-bd swiperBox mettingSwiperBox clearfix">

        <div class="metting-swiper">
            <!-- Swiper -->
            <div class="swiper-container swiper-container-horizontal swiper-container-metting">
                <div class="swiper-wrapper" style="transform: translate3d(297.25px, 0px, 0px); transition-duration: 0ms;">
                    <div class="swiper-slide swiper-slide-active" data-num="0"  audio-src="./upload/audio/30179313.mp3">
                        <img src="./upload/img/_admin_player_01.png" alt="">

                    </div>
                    <div class="swiper-slide swiper-slide-next" data-num="1"  audio-src="https://raw.githubusercontent.com/kolber/audiojs/master/mp3/bensound-dubstep.mp3">
                        <img src="./upload/img/_admin_player_01.png" alt="">
                        <div class="swiper-slide-metting-audio"></div>

                    </div>
                    <div class="swiper-slide" data-num="3"  audio-src="./upload/audio/30179313.mp3"><img src="./upload/img/_admin_player_01.png" alt=""><div class="swiper-slide-metting-audio"></div></div>
                    <div class="swiper-slide" data-num="3"  audio-src="./upload/audio/30179313.mp3"><img src="./upload/img/_admin_player_01.png" alt=""><div class="swiper-slide-metting-audio"></div></div>
                    <div class="swiper-slide" data-num="2"  ><video src="http://www.zhangxinxu.com/study/media/cat.mp4" width="auto" height="264" controls autobuffer></video><div class="swiper-slide-metting-audio"></div></div>
                    <div class="swiper-slide" data-num="3"  audio-src="./upload/audio/30179313.mp3"><img src="./upload/img/_admin_player_01.png" alt=""><div class="swiper-slide-metting-audio"></div></div>
                    <div class="swiper-slide" data-num="3"  audio-src="./upload/audio/30179313.mp3"></div>
                </div>
                <div class="clearfix t-center player-item" >
                    <div class="audio-metting-box" style="">
                        <audio controls=true id="swiperViedo" src="./upload/audio/30179313.mp3"></audio>
                    </div>
                </div>
                <!-- Add Pagination -->
                <div class="metting-btn-item clearfix">
                    <span class="swiper-button-prev swiper-popup-button-prev-hook metting-button swiper-button-disabled"></span>
                    <div class="swiper-pagination swiper-pagination-fraction"><span class="swiper-pagination-current">1</span> <i>|</i> <span class="swiper-pagination-total">4</span></div>
                    <span class="swiper-button-next swiper-popup-button-next-hook metting-button"></span>
                </div>

            </div>
        </div>

    </div>
</div>

</body>
</html>

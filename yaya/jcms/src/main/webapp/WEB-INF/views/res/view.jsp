<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/6/12
  Time: 13:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title></title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.css">
    <link rel="stylesheet" href="${ctxStatic}/css/audio.css">
    <link rel="stylesheet" href="css/iconfont.css">
    <script src="${ctxStatic}/js/slide.js"></script>
    <script src="${ctxStatic}/js/audio.js"></script>
    <script src="${ctxStatic}/js/main.js"></script>

    <style>
        .audio-metting-reSetPlay { display: none;}
        .swiper-slide-active .swiper-slide-metting-audio { visibility: visible !important;}
        .swiper-slide-metting-audio audio { display: block;  }
        .btn-metting-box{ margin: 30px auto 10px;}
        .audiojs div.play-pause { display: block }
        .audiojs .scrubber { width:580px;}
        .icon-audio-error.png { margin-bottom:50px;}


        /*.g-main { padding:0;}*/
        /*.aside { display:none;}*/
    </style>

</head>
<body style="background-color: #FFF;">
<div class="slider-box slider-wrap fx-mask-box-1">
    <ul class="slides clearfix">
        <c:forEach items="${course.details}" var="d" varStatus="status">
            <li audioUrl="${d.audioUrl}">
                <img src="${appFileBase}${d.imgUrl}">
                <div class="popup-metting-audio">
                </div>
            </li>
        </c:forEach>
    </ul>

</div>
<div class="boxAudio clearfix t-center" >

    <div class="audio-metting-box" style="">
        <audio controls=true id="video1"  src="${appFileBase}${course.details[0].audioUrl}"></audio>
    </div>
</div>

<script>
    var currentIndex = 1;
    var asAllItem;
    var audio;
    var audioArr = [];
    $(function(){
        <c:forEach items="${course.details}" var="detail">
        audioArr.push('${appFileBase}${detail.audioUrl}');
        </c:forEach>

        //初始化生成描点
        asAllItem = audiojs.createAll();
        audio = asAllItem[0];
        audio.play();

        $("li>a").click(function(){
            var audioUrl = audioArr[currentIndex-1];
            console.log("current audio stop..."+currentIndex);
            currentIndex = $(".active").text();
            audio.load(audioUrl);
            audio.play();
        });
    });
</script>
</body>
</html>


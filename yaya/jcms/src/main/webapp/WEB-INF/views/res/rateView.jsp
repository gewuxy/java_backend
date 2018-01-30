<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>查看简介和星评</title>
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
    <style>
        .layer-grade-popup { display: block;}
    </style>
</head>
<body>
<!--弹出星评-->
<div class="layer-grade-popup layer-grade-star-box">
    <div class="layer-grade-main clearfix">
        <div class="metting-grade-info hidden-box">
            <div class="title">${result.title}</div>
            <div class="main">&nbsp;&nbsp;${result.info}</div>
            <input type="hidden" name="courseId" id="courseId" value="${result.id}">
        </div>
        <div class="metting-star" id="rateDetail">
        </div>
    </div>
</div>
<script>
    $(function () {
        //如果有评分，进行加载
        if ('${result.starStatus}' == "true") {
            var vagScore = '${result.avgScore}';
            var html = '<div class="star-title">综合评分</div><div class="star-box star-max"><div class="star">';
            html = initStar(vagScore, html);
            html += '</div><div class="grade ">' + vagScore +'分</div></div>';
            var courseId = $("#courseId").val();
            $.ajax({
                url: "${ctx}/func/res/rate/detail?courseId=" + courseId,
                dataType: 'json', //返回数据类型
                type: 'POST', //请求类型
                async: false,
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    if (data.code == 0) {
                        var dataList = data.data;
                        if (dataList != null) {
                            html += '<div class="star-list hidden-box clearfix">';
                            for (var j = 0; j < dataList.length; j++) {
                                html += '<div class="star-list-row clearfix"><div class="fr"><div class="star-box star-min"><div class="star">';
                                html = initStar(dataList[j].avgScore, html);
                                html += '</div><div class="grade">' + dataList[j].avgScore + '分</div></div>';
                                html += '</div><div class="fl">' + dataList[j].title + '</div></div>';
                            }
                            html += '</div>';
                        }
                    } else {
                        layer.msg("获取星评详情失败");
                    }
                }
            });
            html += '<div class="footer-row">参与评分人数：' + ${result.scoreCount} +'</div>';
            $("#rateDetail").html(html);
        }
    });

    //加载星星
    function initStar(score, html) {
        var i = score % 1 == 0 ? 1 : 0;
        for (i; i <= score; i++) {
            if (score - i >= 1 || score - i == 0) html += '<span class="full"></span>';
            if (0 < score - i && score - i < 1) {
                html += '<span class="half"></span>';
            }
        }
        for (score % 1 == 0 ? i : i++; i <= 5; i++) {
            html += '<span class="null"></span>';
        }
        return html;
    }
</script>
</body>
</html>
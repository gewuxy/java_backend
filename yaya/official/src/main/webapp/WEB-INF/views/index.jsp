<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>首页</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
</head>
<div id="wrapper" class="v2-medcnIndex">
    <%@include file="/WEB-INF/include/header.jsp" %>
    <div class="v2-banner bg-lightBlue">
        <div class="page-width clearfix">
            <div class="fr">
                <div class="v2-index-download-area clearfix v2-hover-shadow" style="margin-bottom:20px;">
                    <p class="t-center margin-b"><img src="${ctxStatic}/images/upload/_index_minBanner_area_img1.png" alt="合理用药APP 呵护你的健康"></p>
                    <div class="fr">
                        <p class="margin-b"><a href="http://www.medcn.cn/app_jk.jsp" class="v2-index-download-button"><img src="${ctxStatic}/images/v2/download-button-apple.png" alt=""></a></p>
                        <p><a href="http://www.medcn.cn/app_jk.jsp" class="v2-index-download-button"><img src="${ctxStatic}/images/v2/download-button-android.png" alt=""></a></p>
                    </div>
                    <div class="fl t-center"><img src="${ctxStatic}/images/upload/jk-erweima.png" alt="" style="width:90px; height:90px;"><p >扫描二维码下载</p></div>
                </div>
                <div class="oh v2-hover-shadow">
                    <a href="http://www.medcn.cn/zy_login.jsp" style=" display:block; line-height: 1; margin:0; padding:0;"><img src="${ctxStatic}/images/upload/min-ad-01.jpg" alt=""></a>
                </div>
            </div>
            <div class="fl">
                <!-- S slideshow -->
                <div class="slideshow carousel clearfix" style="width:895px ;height:450px; overflow:hidden;">
                    <div id="carousel-05">
                        <div class="carousel-item">
                            <div class="carousel-img">
                                <a href="detail.html" target="_blank">
                                    <img src="${ctxStatic}/images/upload/_banner_170228_1.jpg" alt="" />
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="carousel-btn carousel-btn-fixed" id="carousel-page-05"></div>
                </div>
                <!-- E slideshow -->
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/include/footer.jsp" %>
</div>
<!--弹出层-->
<%@include file="/WEB-INF/include/markWrap.jsp" %>
<div class="gotop-wrapper index-gotop">
    <a class="gotop" href="javascript:;" >回到顶部</a>
</div>
<%@ include file="/WEB-INF/include/common_js.jsp" %>
<script type="text/javascript" src="${ctxStatic}/js/index.js"></script>
</body>
</html>
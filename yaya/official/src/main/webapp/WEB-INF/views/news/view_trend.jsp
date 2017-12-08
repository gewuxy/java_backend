<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>${news.title} - 敬信药草园</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
    <link rel="stylesheet" href="http://www.medcn.cn/jx_ycy/css/data.css">
</head>
<body>
<div id="wrapper">
    <%@include file="/WEB-INF/include/header.jsp" %>
    <div class="v2-banner v2-banner-listBg">
        <div class="page-width clearfix">
            <!-- S slideshow -->
            <div class="slideshow carousel clearfix" style="height:700px; overflow:hidden;">
                <div id="carousel-05">
                    <div class="carousel-item">
                        <div class="carousel-img">
                            <a href="#">
                                <img src="${ctxStatic}/images/upload/_list-banner.png" alt="" />
                            </a>
                        </div>
                    </div>
                </div>
                <div class="carousel-btn carousel-btn-fixed" id="carousel-page-05"></div>
            </div>
            <!-- E slideshow -->
        </div>
    </div>

    <div class="v2-sub-main" style="padding:120px 0 80px;">
        <div class="page-width clearfix" style="width:980px;">
            <div class="v2-news-detail">
                <h1>${news.title}</h1>
                <div class="v2-entry-title t-center">
                    <a href="${base}/news/trends" class="fr icon-back">查看更多公司动态</a>
                    <span  class="time fl"><fmt:formatDate value="${news.createTime}" pattern="yyyy/MM/dd"/></span>
                    <span>来源：${news.xfrom}</span>
                </div>
                <div class="v2-news-detail-item">

                    <p style="text-align: center;">
                        <c:choose>
                            <c:when test="${news.articleImg != null}">
                                <img src="${news.articleImg}" alt=""></a>
                            </c:when>
                        </c:choose>
                    </p>

                    ${news.content}
                </div>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/include/footer.jsp"%>
</div>
<div class="gotop-wrapper index-gotop">
    <a class="gotop" href="javascript:" >回到顶部</a>
</div>
<!--弹出层-->
<%@include file="/WEB-INF/include/markWrap.jsp" %>
<%@ include file="/WEB-INF/include/common_js.jsp" %>
<script type="text/javascript">
    $(function(){
        $("html, body").scrollTop( $(".v2-sub-main").offset().top);
    })
</script>
</body>
</html>

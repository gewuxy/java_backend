<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>首页</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
</head>
<div id="wrapper" class="v2-medcnIndex">
    <%@include file="/WEB-INF/include/header.jsp" %>
    <div class="v2-sub-main" style="padding:40px 0 80px;">
        <div class="page-width clearfix" >
            <div class="row">
                <div class="col-lg-8 ">
                    <div class="v2-news-detail">
                        <h1>${news.title}</h1>
                        <div class="v2-entry-title t-center">
                                <span class="v2-entry-share">
                                    <div class="bshare-custom">
                                        <a title="分享到" href="http://www.bShare.cn/" id="bshare-shareto" class="bshare-more">分享到</a>
                                        <a title="分享到QQ空间" class="bshare-qzone"></a>
                                        <a title="分享到新浪微博" class="bshare-sinaminiblog"></a>
                                        <a title="分享到人人网" class="bshare-renren"></a>
                                        <a title="分享到腾讯微博" class="bshare-qqmb"></a>
                                        <a title="分享到网易微博" class="bshare-neteasemb"></a>
                                        <a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a>
                                        <span class="BSHARE_COUNT bshare-share-count">0</span></div>
                                    </span>
                            <span class="time fl"><fmt:formatDate value="${news.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span class="fr" >来源:${news.xfrom}</span>
                        </div>
                        <!--startprint-->
                        <div class="v2-news-detail-item">
                            <p style="text-align: center;"><img src="${news.articleImg}" alt=""></p>
                            <p>${news.summary}</p>
                        </div>
                        <!--endprint-->
                        <div class="v2-news-detail-footerDesc">
                            <div class="fr">
                                <div class="v2-news-detail-print" onclick="preview()"><i class="v2-icon-print"></i>打印</div>
                            </div>
                            <div class="fl">${news.author}</div>
                        </div>
                    </div>
                </div>
                <!--右侧模块 -->
                <%@include file="/WEB-INF/include/right.jsp" %>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/include/footer.jsp" %>
</div>
<!-- 弹出层-->
<%@include file="/WEB-INF/include/markWrap.jsp" %>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
</body>
</html>
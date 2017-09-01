<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="http://www.medcn.cn/jx_ycy/css/data.css">
    <!--包含taglib c,fn,amt;项目跟路径${base};静态文件路径 ${statics}；加载静态资源-->
    <%@include file="/WEB-INF/include/staticResource2.jsp"%>

    <title>安全用药详情</title>
</head>
<body>
    <div id="wrapper" class="v2-medcnIndex">
        <%@include file="/WEB-INF/include/header_index2.jsp"%>

        <div class="v2-sub-main" style="padding:40px 0 80px;">
            <div class="page-width clearfix" >
                <div class="row">
                    <div class="col-lg-8 ">
                        <div class="v2-news-detail">
                            <h1>${article.title}</h1>
                            <div class="v2-entry-title t-center">
                                <span class="v2-entry-share">
                                    <div class="bshare-custom"><a title="分享到" href="http://www.bShare.cn/" id="bshare-shareto" class="bshare-more">分享到</a><a title="分享到QQ空间" class="bshare-qzone"></a><a title="分享到新浪微博" class="bshare-sinaminiblog"></a><a title="分享到人人网" class="bshare-renren"></a><a title="分享到腾讯微博" class="bshare-qqmb"></a><a title="分享到网易微博" class="bshare-neteasemb"></a><a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a><span class="BSHARE_COUNT bshare-share-count">0</span></div><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
                                </span>
                                <span class="time fl">
                                    <c:if test="${article.createTime!=null}">
                                        <fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
                                    </c:if>
                                </span>&nbsp;&nbsp;&nbsp;&nbsp;
                                <span class="fr" >来源：${article.xfrom}</span>
                            </div>
                            <!--startprint-->
                            <div class="v2-news-detail-item">
                                <c:if test="${article.articleImg!=null}">
                                    <p style="text-align: center;"><img id="article_img" src="${editorMediaPath}${article.articleImg}" alt=""></p>
                                </c:if>
                                <div>${article.content}</div>
                            </div>
                            <!--endprint-->
                            <div class="v2-news-detail-footerDesc">
                                <div class="fr">
                                    <div class="v2-news-detail-print" onclick="preview()"><i class="v2-icon-print"></i>打印</div>
                                </div>
                                <div class="fl">${article.author}</div>
                            </div>
                        </div>
                    </div>
                    <%@include file="/WEB-INF/include/aside2.jsp"%>
                </div>
            </div>
        </div>

        <%@include file="/WEB-INF/include/footer.jsp"%>
    </div>

    <!--弹出层-->
    <%@include file="/WEB-INF/include/popup_layer2.jsp" %>

    <div class="gotop-wrapper index-gotop">
        <a class="gotop" href="javascript:;" >回到顶部</a>
    </div>
    <script src="${statics}/js/v2/stickUp.min.js"></script>
    <script type="text/javascript">
        /*<!--轮播广告-->*/
        $(window).bind("load resize",function(){
            $("#carousel-05").carouFredSel({
                width       : '100%',
                items		: { visible	: 1 },
                auto 	  	: { pauseOnHover: true, timeoutDuration:5000 },
                swipe    	: { onTouch:true, onMouse:true },
                pagination 	: "#carousel-page-05",
//                                    prev 		: { button:"#carousel-prev-01"},
//                                    next 		: { button:"#carousel-next-01"},
                scroll 		: {	fx : "crossfade" }
            });
        });
        /*固定栏*/
        jQuery(function($) {
            $(document).ready( function() {
                $('.fixed-nav').stickUp({
                    marginTop: 'auto'
                });
            });
        });




    </script>

</body>
</html>
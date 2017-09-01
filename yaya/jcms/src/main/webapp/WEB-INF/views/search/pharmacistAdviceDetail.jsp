<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>药师建议搜索结果详情</title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="http://www.medcn.cn/jx_ycy/css/data.css">
    <%@include file="/WEB-INF/include/staticResource2.jsp"%>
</head>
<body>
    <div id="wrapper">
        <%@include file="/WEB-INF/include/header_index2.jsp"%>

        <div class="v2-sub-main clearfix" style="padding:40px 0 80px;">
            <div class="page-width clearfix" >
                <div class="row">
                    <div class="col-lg-8 ">
                        <div class="v2-news-detail ">
                            <h2>${dataFile.title}</h2>
                            <!--startprint-->
                            <div class="v2-news-detail-item">
                                ${dataFile.content}
                            </div>
                            <!--endprint-->
                            <div class="v2-news-detail-footerDesc">
                                <div class="fr">
                                    <div class="v2-news-detail-print" onclick="preview()"><i class="v2-icon-print"></i>打印</div>
                                </div>
                                <div class="fl"><span class="v2-entry-share">
                                    <div class="bshare-custom"><a title="分享到" href="http://www.bShare.cn/" id="bshare-shareto" class="bshare-more">分享到</a><a title="分享到QQ空间" class="bshare-qzone"></a><a title="分享到新浪微博" class="bshare-sinaminiblog"></a><a title="分享到人人网" class="bshare-renren"></a><a title="分享到腾讯微博" class="bshare-qqmb"></a><a title="分享到网易微博" class="bshare-neteasemb"></a><a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a><span class="BSHARE_COUNT bshare-share-count">0</span></div><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
                                    </span></div>
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

    <script src="${statics}/js/v2/stickUp.min.js"></script>
    <script src="${statics}/js/v2/jquery.fancybox-1.3.4.pack.js"></script>
    <script type="text/javascript">
        /*固定栏*/
        jQuery(function($) {
            $(document).ready( function() {
                $('.fixed-nav').stickUp({
                    topMargin: 'auto'
                });
            });
        });

    </script>

</body>
</html>
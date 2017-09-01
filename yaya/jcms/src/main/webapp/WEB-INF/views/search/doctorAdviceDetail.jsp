<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page import="cn.medcn.data.model.DataFile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>新闻详情-医生建议详情</title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="http://www.medcn.cn/jx_ycy/css/data.css">
    <%@include file="/WEB-INF/include/staticResource2.jsp"%>
</head>
<body>
    <div id="wrapper">
        <%@include file="/WEB-INF/include/header_index2.jsp"%>

        <div class="v2-sub-main" style="padding:40px 0 80px;">
            <div class="page-width clearfix" >
                <div class="row">
                    <div class="col-lg-8 ">
                        <div class="v2-news-detail ">
                            <h1>${dataFile.title}</h1>
                            <div class="v2-entry-title t-center clearfix">
                                <span id="article_keywords" class="fl">关键词：
                                    <c:if test="${keywords!=null}">
                                        <c:forEach items="${keywords}" var="keyword" varStatus="status">
                                            <%--<c:if test="${status.index%2 == 0}"></c:if>--%>
                                            <a href="#" class="color-blue">${keyword}</a>&nbsp;
                                        </c:forEach>
                                    </c:if>
                                </span>
                                <span class="fr" >来源：${dataFile.dataFrom}</span>
                            </div>
                            <!--startprint-->
                            <div class="v2-news-detail-item">
                                <c:if test="${dataFile.img!=null}">
                                    <p style="text-align: center;"><img src="${editorMediaPath}${dataFile.img}" alt=""></p>
                                </c:if>
                                <div>
                                    ${dataFile.content}
                                </div>
                            </div>
                            <!--endprint-->
                            <!-- (need to do) 这部分的内容不知道从哪找的，先留着
                            <div class="v2-news-doctorInfo">
                                <h4>YaYa医师简介</h4>
                                <p>谷欣，昆明延安医院体检中心医务科科长、副教授。 擅长急性重症胰腺炎的综合治疗，对乳腺、甲状腺及血管外科具有相当造诣。</p>
                            </div>
                            <p class="color-blue t-center">&lt;本文是YaYa医师 @谷欣授权药草园发表，并经药草园编辑，转载请注明出处和本文链接&gt;</p>
                            -->
                            <div class="v2-news-detail-footerDesc clearfix">
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
                    marginTop: 'auto'
                });
            });
        });

    </script>

</body>
</html>
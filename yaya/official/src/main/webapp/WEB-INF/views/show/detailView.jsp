<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>${news.title} - 敬信药草园</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
</head>
<body>
<div id="wrapper" class="v2-medcnIndex">
    <%@include file="/WEB-INF/include/header.jsp" %>
    <div class="v2-sub-main" style="padding:40px 0 80px;">
        <div class="page-width clearfix" >
            <div class="row">
                <div class="col-lg-8 ">
                    <div class="v2-news-detail">
                        <h1>${news.title}</h1>
                        <div class="v2-entry-title t-center">
                            <%@include file="/WEB-INF/include/share.jsp" %>
                            <span class="time fl"><fmt:formatDate value="${news.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
                            <span class="fr" >来源:${news.xfrom}</span>
                        </div>
                        <!--startprint-->
                        <div class="v2-news-detail-item">
                            <p style="text-align: center;"><img src="${fileBase}${news.articleImg}" alt=""></p>
                            ${fn:replace(news.content, news.title, "")}
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
<div class="gotop-wrapper index-gotop">
    <a class="gotop" href="javascript:" >回到顶部</a>
</div>
<!-- 弹出层-->
<%@include file="/WEB-INF/include/markWrap.jsp" %>
<%@ include file="/WEB-INF/include/common_js.jsp" %>
<script>
    $(function(){
        $(".second").addClass("current");
    })
</script>
</body>
</html>
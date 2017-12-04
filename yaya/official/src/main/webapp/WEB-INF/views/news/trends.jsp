<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>新闻列表</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
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
            <div class="row">
                <div class="col-lg-12">
                    <div class="v2-news-graphic">
                        <c:forEach items="${page.dataList}" var="news">
                            <div class="v2-news-graphic-item clearfix">
                                <div class="fl"><a href="${base}/news/viewtrend/${news.id}">
                                <c:choose>
                                    <c:when test="${news.articleImg != null}">
                                        <img src="${news.articleImg}" alt=""></a>
                                    </c:when>
                                </c:choose>
                                </div>
                                <div class="oh">
                                    <h3><a href="${base}/news/viewtrend/${news.id}">${news.title}</a></h3>
                                    <p class="v2-news-graphic-info">${news.summary}</p>
                                    <p><span class="time"><fmt:formatDate value="${news.createTime}" pattern="yyyy/MM/dd"/></span>&nbsp;&nbsp;&nbsp;&nbsp;<span>来源：${news.xfrom}</span></p>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                    <div class="v2-page-box">
                        <a <c:if test="${page.pageNum>1}"> href="javascript:page(${page.pageNum-1})"</c:if> class="v2-page-box-prev" title="上一页"></a>
                        <c:forEach begin="${page.pageNum-5>0?page.pageNum-5:1}" end="${page.pages-page.pageNum > 5?page.pageNum+5:page.pages}" var="pn">
                            <a href="javascript:page(${pn})" <c:if test="${pn==page.pageNum}">class="cur"</c:if> >${pn}</a>
                        </c:forEach>
                        <a <c:if test="${page.pageNum<page.pages}"> href="javascript:page(${page.pageNum+1})"</c:if> class="v2-page-box-next" title="下一页"></a>
                    </div>
                    <form action="${base}/news/trends" method="post" name="pageForm" id="pageForm">
                        <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
                        <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
<%@include file="/WEB-INF/include/footer.jsp"%>
</div>
<div class="gotop-wrapper index-gotop">
    <a class="gotop" href="javascript:" >回到顶部</a>
</div>
<%@ include file="/WEB-INF/include/common_js.jsp" %>
<script src="${ctxStatic}/js/v2/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript">
    function page(pageNum){
        $("#pageForm").find("input[name='pageNum']").val(pageNum);
        $("#keyWord").val($("#searchWord").val());
        $("#pageForm").submit();
    }

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

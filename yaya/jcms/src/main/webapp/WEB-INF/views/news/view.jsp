<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/3/6
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>${news.title} - 敬信药草园</title>
    <%@include file="/WEB-INF/include/staticResource.jsp"%>
    <link rel="stylesheet" href="http://www.medcn.cn/jx_ycy/css/data.css">
</head>
<body>
<div id="wrapper">
    <%@include file="/WEB-INF/include/header_common.jsp"%>

    <div class="v2-sub-main" style="padding:120px 0 80px;">
        <div class="page-width clearfix" style="width:980px;">
            <div class="v2-news-detail">
                <h1>${news.title}</h1>
                <div class="v2-entry-title t-center">
                    <span  class="time fl"><fmt:formatDate value="${news.createTime}" pattern="yyyy/MM/dd"/></span>
                    <span>来源：${news.xfrom}</span>
                </div>
                <div class="v2-news-detail-item">

                    <p style="text-align: center;"><img src="${news.articleImg}" alt=""></p>

                    ${news.content}
                </div>
            </div>
        </div>
    </div>

    <%@include file="/WEB-INF/include/footer.jsp"%>
</div>
<!--弹出层-->
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
    $(function(){
        $("html, body").scrollTop( $(".v2-sub-main").offset().top);
    })

</script>
</body>
</html>

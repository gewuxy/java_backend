<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>详情</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
</head>
<body>
<div id="wrapper">
    <%@include file="/WEB-INF/include/header.jsp" %>
    <div class="v2-sub-main clearfix" style="padding:40px 0 80px;">
        <div class="page-width clearfix" >
            <div class="row">
                <div class="col-lg-8 ">
                    <div class="v2-news-detail ">
                        <h2>${detail.title}</h2>
                        <!--startprint-->
                        <div class="v2-news-detail-item">
                            ${detail.content}
                        </div>
                        <!--share-->
                        <%@include file="/WEB-INF/include/share.jsp" %>
                    </div>
                </div>
                <!--右侧模块 -->
                <%@include file="/WEB-INF/include/right.jsp" %>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/include/footer.jsp" %>
</div>
<!--弹出层-->
<%@include file="/WEB-INF/include/markWrap.jsp" %>
<script>
    $(function(){
        $(".three").addClass("current");
    })
</script>
</body>
</html>
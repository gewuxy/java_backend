<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/9/29
  Time: 17:06
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tips</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">

    <script src="${ctxStatic}/js/jquery.min.js"></script>
    <script src="${ctxStatic}/js/commonH5.js"></script>
    <script src="${ctxStatic}/js/swiper.jquery.js"></script>
</head>
<body>
<div id="wrapper">
    <div class="login login-banner" style="height:900px;">
        <div class="page-width pr">
            <div class="login-header">
                <%@include file="/WEB-INF/views/en_US/include/login_header.jsp"%>
                <%@include file="/WEB-INF/include/switch_language.jsp"%>
            </div>
            <div class="login-box clearfix">
                <%@include file="/WEB-INF/views/en_US/include/login_left.jsp"%>
                <div class="col-lg-5 login-box-item">

                    <!--切换  重置密码-->
                    <div class="login-box-main position-message-login " style="margin-top:90px;">
                        <form action="">
                            <div class="login-form-item">
                                <div class="login-message-text" >
                                    <p class="t-center"><img src="${ctxStatic}/images/login-error-icon-02.png" alt="" style="margin-top:10px; "></p>
                                    <p class="color-red t-center">${exception.message}</p>
                                </div>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
            <%@include file="/WEB-INF/views/en_US/include/footer.jsp"%>
        </div>
    </div>
</div>

<script>
    $(function(){
        //让背景撑满屏幕
        $('.login-banner').height($(window).height());
        //让协议定位到底部
        $('.login-box-item').height($('.login-box').height());



    })
</script>
</body>
</html>


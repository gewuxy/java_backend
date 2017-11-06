<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/11/3
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <%@include file="/WEB-INF/include/page_context.jsp"%>

    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
</head>
<body>
<div id="wrapper">
    <div class="login login-banner" >
        <div class="page-width pr">
            <div class="login-header">
                <%@include file="/WEB-INF/include/switch_language.jsp"%>
            </div>
            <div class="login-box clearfix">
                <%@include file="/WEB-INF/include/login_left_en_US.jsp"%>


                <div class="col-lg-5 login-box-item">

                    <!--切换 登录-->
                    <div class="login-box-main position-button-login">
                        <a href="${ctx}/mgr/login?thirdPartyId=3" title="Login with Facebook" class=" login-button buttonBlue-02">Login with Facebook</a>
                        <a href="${ctx}/mgr/login?thirdPartyId=4" title="Login with Twitter" class=" login-button buttonBlue-03">Login with Twitter</a>
                        <a href="${ctx}/mgr/login?thirdPartyId=7" title="Login by E-mail" class=" login-button buttonBlue">Login by E-mail</a>
                        <a href="${ctx}/mgr/login?thirdPartyId=5" title="Login with Jingxin Digital Platform" class=" login-button buttonBlue-04 last">Login with Jingxin Digital Platform</a>
                    </div>

                    <%@include file="/WEB-INF/include/login_service_en_US.jsp"%>

                </div>
            </div>

            <%@include file="/WEB-INF/include/login_footer_en_US.jsp"%>
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
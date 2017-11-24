<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/16
  Time: 16:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登錄-會講</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
    <link rel="stylesheet" href="${ctxStatic}/css/style-EN.css">

</head>
<body>
<div id="wrapper">
    <div class="login login-banner" >
        <div class="page-width pr">
            <div class="login-header">
                <%@include file="/WEB-INF/include/switch_language.jsp"%>
            </div>

            <div class="login-box clearfix">
                <%@include file="../include/login_left.jsp"%>

                <div class="col-lg-5 login-box-item">
                    <!--切换 登录-->
                    <div class="login-box-main position-button-login">
                        <a href="javascript:;" title="Facebook授權登錄" class=" login-button buttonBlue-02" onclick="facebookLogin()">Facebook授權登錄</a>
                        <a href="javascript:;" id="twitter" title="Twitter授權登錄" class=" login-button buttonBlue-03" onclick="twitterLogin()">Twitter授權登錄</a>
                        <a href="${ctx}/mgr/login?thirdPartyId=7" title="郵箱登錄" class=" login-button buttonBlue">郵箱登錄</a>
                        <a href="${ctx}/mgr/login?thirdPartyId=5" title="敬信數字平臺授權登錄" class=" login-button buttonBlue-04 last">敬信數字平臺授權登錄</a>
                    </div>

                    <%@include file="../include/login_service.jsp"%>
                </div>
            </div>

            <%@include file="../include/login_footer.jsp"%>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/include/twitter_fb_form.jsp" %>

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

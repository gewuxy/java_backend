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
    <title>登錄</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
</head>
<body>
<div id="wrapper">
    <div class="login login-banner" >
        <div class="page-width pr">

            <%@include file="/WEB-INF/include/switch_language_zh_CN.jsp"%>

            <div class="login-box clearfix">
                <%@include file="/WEB-INF/include/login_left.jsp"%>

                <div class="col-lg-5 login-box-item">
                    <!--切换 登录-->
                    <div class="login-box-main position-button-login">
                        <a href="${ctx}/mgr/login?thirdPartyId=1" title="微信授權登錄" class=" login-button buttonGreen-02">微信授權登錄</a>
                        <a href="${ctx}/mgr/login?thirdPartyId=2" title="微博授權登錄" class=" login-button buttonRed">微博授權登錄</a>
                        <a href="${ctx}/mgr/login?thirdPartyId=5" title="敬信數字平台授權登錄" class=" login-button buttonBlue last">敬信數字平台授權登錄</a>
                    </div>

                    <!--登录用-->
                    <div class="login-box-other">
                        <div class="login-box-other-title t-center ">
                            <span class="login-box-other-line-l"></span>
                            <span>其他方式</span>
                            <span class="login-box-other-line-r"></span>
                        </div>
                        <div class="login-box-other-main t-center ">
                            <a href="${ctx}/mgr/login?thirdPartyId=6" title="手机登录"><img src="${ctxStatic}/images/login-phone-icon.png" alt="手机登录"></a>
                            <a href="${ctx}/mgr/login?thirdPartyId=7" title="邮箱登录"><img src="${ctxStatic}/images/login-email-icon.png" alt="邮箱登录"></a>
                        </div>
                    </div>

                    <%@include file="/WEB-INF/include/login_service.jsp"%>
                </div>
            </div>

            <%@include file="/WEB-INF/include/login_footer_zh_CN.jsp"%>
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
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
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
    <title>登录界面</title>
</head>
<body>
<div id="wrapper">
    <div class="login login-banner" >
        <div class="page-width pr">
            <div class="login-header">
                <%@include file="/WEB-INF/include/switch_language.jsp"%>
            </div>
            <div class="login-box clearfix">
                <div class="col-lg-5">
                    <div class="login-box-logo">
                        <img src="${ctxStatic}/images/login-logo.png" alt="">
                    </div>
                </div>
                <div class="col-lg-2">&nbsp;</div>
                <div class="col-lg-5 login-box-item">

                    <!--切换 登录-->
                    <div class="login-box-main position-button-login">
                        <a href="${ctx}/mgr/login?thirdPartyId=1" title="微信授权登录" class=" login-button buttonGreen-02">微信授权登录</a>
                        <a href="${ctx}/mgr/login?thirdPartyId=2" title="微博授权登录" class=" login-button buttonRed">微博授权登录</a>
                        <a href="${ctx}/mgr/login?thirdPartyId=5" title="敬信数字平台授权登录" class=" login-button buttonBlue last">敬信数字平台授权登录</a>
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
                    <div class="login-box-info t-center ">
                        <p>登录即表示您已同意 <a href="subPage-service.html" class="color-blue">《CSPmeeting服务协议》</a> </p>
                    </div>
                </div>
            </div>

            <div class="login-bottom">
                <p><%@include file="/WEB-INF/include/copy_right_zh_CN.jsp"%></p>
            </div>
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

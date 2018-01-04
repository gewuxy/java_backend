<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/19
  Time: 9:22
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta charset="UTF-8">
    <title><fmt:message key="page.reset.password.success.title"/> - <fmt:message key="page.common.appName"/></title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
</head>

<body>
<div id="wrapper">
    <div class="login login-banner" style="height:900px;">
        <div class="page-width pr">
            <div class="login-header">
                <%@include file="../include/login_header.jsp"%>
                <%@include file="/WEB-INF/include/switch_language.jsp"%>
            </div>
            <div class="login-box clearfix">
                <%@include file="../include/login_left.jsp"%>

                <div class="col-lg-5 login-box-item">

                    <!--切换  输入新密码-->
                    <div class="login-box-main position-message-login">
                        <form action="">
                            <div class="login-form-item">
                                <div class="login-message-text">
                                    <p><img src="${ctxStatic}/images/icon-succeed.png" alt=""></p>
                                    <p class="t-center"><fmt:message key="page.reset.password.success"/></p>
                                </div>
                                <input id="loginBtn" type="button" class="button login-button buttonBlue last" value="<fmt:message key="page.reset.password.login.button"/>">
                            </div>
                        </form>
                    </div>

                </div>
            </div>


            <%@include file="../include/login_footer.jsp"%>

        </div>
    </div>
</div>

<script>
    $(function(){
        //让背景撑满屏幕
        $('.login-banner').height($(window).height());
        //让协议定位到底部
        $('.login-box-item').height($('.login-box').height());

        $("#loginBtn").click(function () {
            window.location.href = "${ctx}/mgr/login?thirdPartyId=7";
        });

    })
</script>


</body>
</html>

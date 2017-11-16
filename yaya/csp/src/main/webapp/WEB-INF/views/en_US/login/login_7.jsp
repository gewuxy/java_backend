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
    <title>Login with E-mail - CSPmeeting</title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <%@include file="/WEB-INF/include/page_context.jsp"%>

    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
    <link rel="stylesheet" href="${ctxStatic}/css/style-EN.css">
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

                    <!--切换  邮箱登录-->
                    <div class="login-box-main position-phone-login">
                        <form action="${ctx}/mgr/login"  method="post" id="loginForm" name="loginForm">
                            <input type="hidden" name="thirdPartyId" value="7">
                            <div class="login-form-item">
                                <label for="email" class="cells-block pr">
                                    <input id="email" name="username" type="text" value="${username}" class="login-formInput" placeholder="E-mail Address">
                                </label>
                                <label for="pwd" class="cells-block pr">
                                    <input type="text" required placeholder="Password" class="login-formInput icon-register-hot last none" maxlength="24">
                                    <input id="pwd" name="password" type="password" required placeholder="Password" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                                    <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                                </label>
                                <span class="cells-block error ${not empty error ? '':'none'} "><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span id="errorMessage">${error}</span></span>
                                <input href="#" type="button" class="button login-button buttonBlue last" id="submitBtn" value="Login">
                            </div>
                        </form>
                    </div>


                    <!--登录用-->
                    <div class="login-box-other">
                        <div class="login-box-other-info t-center">
                            <a href="${ctx}/mgr/to/register" class="color-wathet-blue">Register</a><span class="muted">|</span><a href="${ctx}/mgr/to/reset/password" class="color-wathet-blue">Forgot Password</a>
                        </div>
                    </div>


                    <%@include file="../include/login_service.jsp"%>

                </div>
            </div>

            <div class="login-bottom">
                <%@include file="../include/copy_right.jsp"%>
            </div>
        </div>
    </div>
</div>
<script>
    $(function(){

        const classPwdOn = "pwdChange-on";
        const classPwdOff = "pwdChange-off";

        const userNameError = "Please enter email address";
        const passWordError = "Please enter password";

        //让背景撑满屏幕
        $('.login-banner').height($(window).height());
        //让协议定位到底部
        $('.login-box-item').height($('.login-box').height());

        $("#submitBtn").click(function(){
            var $username = $("#email");
            var $password = $("#pwd");
            if (!isEmail($username.val())){
                $("#errorMessage").text(userNameError);
                $("#errorMessage").parent().removeClass("none");
                $username.focus();
                return false;
            }

            if($.trim($password.val()).length > 24 || $.trim($password.val()).length < 6){
                $("#errorMessage").text(passWordError);
                $("#errorMessage").parent().removeClass("none");
                $password.focus();
                return false;
            }
            $("#errorMessage").parent().addClass("none");
            $("#loginForm").submit();

        });

        $(document).keydown(function(evt){
            if (evt.keyCode == 13){
                $("#submitBtn").trigger("click");
            }
        });

        $(".pwdChange-hook").click(function(){
            if($(this).hasClass(classPwdOn)){
                $(this).removeClass(classPwdOn);
                $(this).addClass(classPwdOff);
                $("#pwd").prop("type", "text");
            } else {
                $(this).removeClass(classPwdOff);
                $(this).addClass(classPwdOn);
                $("#pwd").prop("type", "password");
            }
        });
    })
</script>
</body>
</html>
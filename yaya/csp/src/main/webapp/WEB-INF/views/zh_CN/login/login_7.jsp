<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/16
  Time: 17:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>邮箱登录</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">

    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
</head>
<body>
<div id="wrapper">
    <div class="login login-banner" style="height:900px;">
        <div class="page-width pr">
            <div class="login-header">
                <a href="#" class="login-language" title="切换语言">中文</a>
            </div>
            <div class="login-box clearfix">
                <div class="col-lg-5">
                    <div class="login-box-logo">
                        <img src="${ctxStatic}/images/login-logo.png" alt="">
                    </div>
                </div>
                <div class="col-lg-2">&nbsp;</div>
                <div class="col-lg-5 login-box-item">


                    <!--切换  邮箱登录-->
                    <div class="login-box-main position-phone-login">
                        <form action="${ctx}/mgr/login" method="post" id="loginForm" name="loginForm">
                            <input type="hidden" name="thirdPartyId" value="7">
                            <div class="login-form-item">
                                <label for="email" class="cells-block pr">
                                    <input id="email" name="username" type="text" value="${username}" class="login-formInput" placeholder="邮箱地址">
                                </label>
                                <label for="pwd" class="cells-block pr">
                                    <input type="text" required placeholder="输入6~24位密码" class="login-formInput icon-register-hot last none" maxlength="24">
                                    <input id="pwd" name="password" type="password" required placeholder="输入6~24位密码" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                                    <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                                </label>
                                <span class="cells-block error ${not empty error ? '':'none'}" ><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span id="errorMessage">${error}</span></span>
                                <input type="button" class="button login-button buttonBlue last" id="submitBtn" value="确认登录">
                            </div>
                        </form>
                    </div>


                    <!--登录用-->
                    <div class="login-box-other">
                        <div class="login-box-other-info t-center">
                            <a href="#" class="color-wathet-blue">我要注册</a><span class="muted">|</span><a href="#" class="color-wathet-blue">忘记密码</a>
                        </div>
                    </div>
                    <div class="login-box-info t-center">
                        <p>登录即表示您已同意 <a href="subPage-service.html" class="color-blue">《CSPmeeting服务协议》</a> </p>
                    </div>
                </div>
            </div>
            <div class="login-bottom">
                <p>粤ICP备12087993号 © 2012-2017 敬信科技版权所有 </p>
            </div>
        </div>
    </div>
</div>
<script src="${ctxStatic}/js/util.js"></script>
<script>
    $(function(){



        const classPwdOn = "pwdChange-on";
        const classPwdOff = "pwdChange-off";

        const userNameError = "请输入正确的邮箱地址";
        const passWordError = "请输入正确的密码";

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
<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/20
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>YaYa医师授权</title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <style>
        #wrapper {
            width: 100%;
            margin-left: auto;
            margin-right: auto;
            background-color: #fff;
            position: relative;
        }
        .t-center {
            text-align: center;
        }
        .none {
            display: none;
        }
        .login-accredit .page-width{
            width: 850px;
            height: 100%;
            margin: 0 auto;
        }
        .login-bottom {     position: absolute;
            bottom: 50px;
            left: 0;
            width: 100%;
            text-align: center;
        }
        .login-box { position: relative; top:210px; left:0; background:#fff; padding:60px 90px; min-height: 370px; -moz-border-radius: 10px; -webkit-border-radius:10px; border-radius: 10px;}
        .login-button {
            display: block;
            margin-bottom: 25px;
            padding: 7px 0;
            width: 100%;
            border: none;
            text-align: center;
            color: #fff;
            font-size: 16px;
            -moz-border-radius: 26px;
            -webkit-border-radius: 26px;
            border-radius: 26px;
        }
        .login-form-item .login-button {padding: 12px 0;}
        .buttonBlue { background-color:#167AFE;}
        .buttonBlue:hover { background-color:#146ee3;}

        .login-form-item .login-formInput {
            width: 233px;
            padding: 11px 23px;
            border: 0;
            background: #EDF3F9;
            font-size: 16px;
            -moz-border-radius: 27px;
            -webkit-border-radius: 27px;
            border-radius: 27px; }
        .login-form-item .cells-block {
            display: block;
            margin-bottom: 25px;
        }
        .login-form-item .cells-block { display: block; }
        .login-form-item .error {color: #E43939;
            padding-left: 25px;
            margin: -15px 0 10px;
            font-size: 14px;}

        .login-form-item .error img { position: relative; top:4px;}

        .icon-pwdChange { position:absolute; right:20px; top:18px; display: inline-block; width:20px; height:10px; padding:0; }
        .pwdChange-off { background:url(${ctxStatic}/images/pwdChangeState-off.png) no-repeat center; }
        .pwdChange-on { background:url(${ctxStatic}/images/pwdChangeState-on.png) no-repeat center; }
        /*授权页面*/
        .login-accredit {}
        .login-accredit p { font-size:18px;}
        .login-accredit .login-box { top:100px;}
        .login-accredit .logo { margin-bottom:80px;}
        .login-accredit .text { margin-bottom:47px; color:#B3B3B3;}
        .login-accredit  .login-button {  -moz-border-radius: 4px; -webkit-border-radius: 4px; border-radius: 4px;}
        .login-accredit .login-form-item .login-formInput { width:226px;}
        .position-medcn-accredit { padding:0 32px;}
        .position-medcn-accredit .login-form-item .login-formInput { background-color:#F3F6F8;; -moz-border-radius: 4px; -webkit-border-radius: 4px; border-radius: 4px; }
        .login-accredit .login-bottom p{ font-size:14px;}

    </style>
</head>
<body>
<div id="wrapper">
    <div class="login login-accredit" >
        <div class="page-width pr">
            <div class="login-box clearfix">
                <div class="col-lg-3"> &nbsp; </div>

                <div class="col-lg-6 login-box-item">

                    <p class="logo t-center"><img src="${ctxStatic}/images/medcn-logo.png" alt=""></p>

                    <p class="text t-center">使用敬信数字平台账号访问${app_name}</p>


                    <!--切换  敬信数字平台登录-->
                    <div class="login-box-main position-medcn-accredit">
                        <form action="${ctx}/oauth/authorize" id="loginForm" name="loginForm" method="post">
                            <input type="hidden" name="redirect_uri" value="${redirect_uri}"/>
                            <div class="login-form-item">
                                <label for="username" class="cells-block pr">
                                    <input id="username" name="username" type="text" class="login-formInput" placeholder="用户名">
                                </label>
                                <label for="pwd" class="cells-block pr">
                                    <input type="text" required placeholder="密码" class="login-formInput icon-register-hot last none" maxlength="24">
                                    <input id="pwd" name="password" type="password" required placeholder="密码" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                                    <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                                </label>
                                <span class="cells-block error"><img id="errorIcon" src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span id="errorMessage">${message}</span></span>
                                <input type="button" class="button login-button buttonBlue last" id="submitBtn" value="确认登录">
                            </div>
                        </form>
                    </div>


                </div>
                <div class="col-lg-3"> &nbsp; </div>

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
        //让背景撑满屏幕
        $('.login-accredit').height($(window).height());
        //让协议定位到底部
        $('.login-box-item').height($('.login-box').height());

        $("#errorIcon").hide();

        $("#submitBtn").click(function(){
            var $username = $("#username");
            var $password = $("#pwd");
            if (!isEmail($username.val()) && !isMobile($username.val())){
                $("#errorIcon").show();
                $("#errorMessage").text("请输入正确的用户名");
                $username.focus();
                return false;
            }

            if($.trim($password.val()).length > 24 || $.trim($password.val()).length < 6){
                $("#errorIcon").show();
                $("#errorMessage").text("请输入正确的密码");
                $password.focus();
                return false;
            }
            $("#errorIcon").hide();
            $("#errorMessage").text("");
            $("#loginForm").submit();

        });
    })

    $(document).keydown(function(evt){
        if (evt.keyCode == 13){
            $("#submitBtn").trigger("click");
        }
    });


    const classPwdOn = "pwdChange-on";
    const classPwdOff = "pwdChange-off";

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
</script>

</body>
</html>

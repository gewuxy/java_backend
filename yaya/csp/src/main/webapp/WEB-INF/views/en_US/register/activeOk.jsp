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
    <meta charset="UTF-8">
    <title>E-mail Activation - CSPmeeting</title>
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
    <div class="login login-banner" style="height:900px;">
        <div class="page-width pr">
           <%@include file="/WEB-INF/include/switch_language.jsp"%>
           
            <div class="login-box clearfix">
                <%@include file="../include/login_left.jsp"%>
                
                <div class="col-lg-5 login-box-item">

                    <!--切换  重置密码-->
                    <div class="login-box-main position-message-login ">
                        <form action="">
                            <div class="login-form-item">
                                <div class="login-message-text" style="text-align: left !important;">
                                    <p style="font-size:16px;">Dear&nbsp;&nbsp;<span class="color-blue">${email}</span>,<br /> Welcome aboard!
                                        <a id="loginBtn" href="#" class="color-blue">Please click here to log in CSPmeeting.</a> </p>
                                </div>
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

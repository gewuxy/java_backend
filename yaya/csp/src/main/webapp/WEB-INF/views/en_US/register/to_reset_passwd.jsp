<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/27
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>忘记密码</title>
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
            <div class="login-header">
                <%@include file="../include/login_header.jsp"%>
                <%@include file="/WEB-INF/include/switch_language.jsp"%>
            </div>
            <div class="login-box clearfix">
                <%@include file="../include/login_left.jsp"%>

                <div class="col-lg-5 login-box-item">

                    <!--切换 -->
                    <div class="login-box-main position-resetEmail-login">
                        <form id="resetForm" name="resetForm">
                            <div class="login-form-item">
                                <label for="email" class="cells-block pr">
                                    <input id="email" name="email" type="text" class="login-formInput" placeholder="New Password">
                                </label>
                                <span class="cells-block error ${not empty error ? '':'none'}"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span id="errorMessage">${error}</span></span>
                                <input type="button" id="submitBtn" class="button login-button buttonBlue last" value="Submit">
                            </div>
                        </form>
                    </div>

                    <!--切换  重置密码-->
                    <div class="login-box-main position-message-login none">
                        <form action="">
                            <div class="login-form-item">
                                <div class="login-message-text">
                                    <p style="font-size:16px;">Password-reset link has been sent to your e-mail address.Please reset your password and log in again.</p>
                                </div>
                                <input id="toEmailUrl" type="button" class="button login-button buttonBlue last" value="Go to My E-mail Box">
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

        $("#submitBtn").click(function () {
            var email = $("#email").val();
            if (!isEmail(email)) {
                $("#errorMessage").text("Please enter the correct email address");
                $("#errorMessage").parent().removeClass("none");
                $("#email").focus();
                return false;
            }
          //  $("#errorMessage").parent().addClass("none");

            $.ajax({
                url: '${ctx}/api/email/findPwd?email=' + email, //目标地址
                type: "post",
                dataType: "json",
                success: function (data){
                    if (data.code == 0){
                        $(".position-resetEmail-login").addClass("none");
                        $(".position-message-login").removeClass("none");
                    } else {
                        $(".position-message-login").addClass("none");
                        layer.msg(data.err);
                    }
                },
                error: function (a, n, e) {
                    alert("获取数据异常："+a + " - "+n+" - "+e);
                }
            })
        });

        $("#toEmailUrl").click(function () {
            var email = $("#email").val();
            var url = gainEmailURL(email);
            if(url != '' && url != 'error'){
                layer.closeAll();
                window.open(url);
            }else{
                layer.msg("抱歉!未找到对应的邮箱登录地址");
            }
        });
    })
</script>

</body>
</html>

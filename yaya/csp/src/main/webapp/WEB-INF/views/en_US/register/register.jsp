<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/26
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>E-mail Registration - CSPmeeting</title>
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

                    <!--切换  邮箱注册-->
                    <div class="login-box-main position-phone-login">
                        <form action="" method="post" id="registerForm" name="registerForm">
                            <div class="login-form-item">
                                <label for="email" class="cells-block pr">
                                    <input id="email" name="email" type="text" class="login-formInput" placeholder="E-mail Address">
                                </label>
                                <label for="pwd" class="cells-block pr">
                                    <input type="text" required placeholder="Password" class="login-formInput icon-register-hot last none" maxlength="24">
                                    <input id="pwd" type="password" name="password" required placeholder="Password" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                                    <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                                </label>
                                <label for="nickname" class="cells-block pr">
                                    <input id="nickname" name="nickName" type="text" class="login-formInput" placeholder="Your Name">
                                </label>
                                <span class="cells-block error ${not empty error ? '':'none'}" ><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span id="errorMessage">${error}</span></span>

                                <input type="button" id="submitBtn" class="button login-button buttonBlue last" value="Register Now">
                            </div>
                        </form>
                    </div>

                    <!--切换  激活邮箱-->
                    <div class="login-box-main position-message-login none">
                        <form action="">
                            <div class="login-form-item">
                                <div class="login-message-text">
                                    <p style="font-size:16px;"> Activation e-mail has been sent to your mail box. Please proceed activation in e-mail and complete your registration.</p>
                                </div>
                                <input id="toEmailUrl" type="button" class="button login-button buttonBlue last" value="Go to My E-mail Box">
                            </div>
                        </form>
                    </div>


                    <%@include file="../include/login_service.jsp"%>
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

        $("#submitBtn").click(function(){
            var $username = $("#email");
            var $password = $("#pwd");
            var $nickname = $("#nickname");

            if (!isEmail($username.val())){
                $("#errorMessage").text("Please enter correct email address");
                $("#errorMessage").parent().removeClass("none");
                $username.focus();
                return false;
            }

            if(!isPassword($.trim($password.val()))){
                $("#errorMessage").text("Please enter password");
                $("#errorMessage").parent().removeClass("none");
                $password.focus();
                return false;
            }

            if (isEmpty($nickname.val())) {
                $("#errorMessage").text("Please enter your nickname");
                $("#errorMessage").parent().removeClass("none");
                $nickname.focus();
                return false;
            }

            $("#errorMessage").parent().addClass("none");

            $.ajax({
                url: '${ctx}/mgr/register', //目标地址
                data: $('#registerForm').serialize(),
                type: "post",
                dataType: "json",
                success: function (data){
                    if (data.code == 0){
                        $(".position-phone-login").addClass("none");
                        $(".t-center").addClass("none");
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
                layer.msg("I'm sorry! No corresponding mailbox login address was found");
            }
        });

        $(document).keydown(function(evt){
            if (evt.keyCode == 13){
                $("#submitBtn").trigger("click");
            }
        });

        const classPwdOn = "pwdChange-on";
        const classPwdOff = "pwdChange-off";
        //  密码切换
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

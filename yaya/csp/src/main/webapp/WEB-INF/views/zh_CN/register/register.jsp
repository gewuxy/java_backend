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
    <title>注册</title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
</head>

<body>
<div id="wrapper">
    <div class="login login-banner" style="height:900px;">
        <div class="page-width pr">
            <div class="login-header">
                <%@include file="../include/login_header.jsp"%>
                <%@include file="../include/language.jsp"%>
            </div>
            <div class="login-box clearfix">
                <%@include file="/WEB-INF/include/login_left.jsp"%>

                <div class="col-lg-5 login-box-item">

                    <!--切换  邮箱注册-->
                    <div class="login-box-main position-phone-login">
                        <form action="" method="post" id="registerForm" name="registerForm">
                            <div class="login-form-item">
                                <label for="email" class="cells-block pr">
                                    <input id="email" name="email" type="text" class="login-formInput" placeholder="邮箱地址">
                                </label>
                                <label for="pwd" class="cells-block pr">
                                    <input type="text" required placeholder="输入6~24位密码" class="login-formInput icon-register-hot last none" maxlength="24">
                                    <input id="pwd" type="password" name="password" required placeholder="输入6~24位密码" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                                    <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                                </label>
                                <label for="nickname" class="cells-block pr">
                                    <input id="nickname" name="nickName" type="text" class="login-formInput" placeholder="昵称">
                                </label>
                                <span class="cells-block error ${not empty error ? '':'none'}" ><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span id="errorMessage">${error}</span></span>

                                <input type="button" id="submitBtn" class="button login-button buttonBlue last" value="注册">
                            </div>
                        </form>
                    </div>

                    <!--切换  激活邮箱-->
                    <div class="login-box-main position-message-login none">
                        <form action="">
                            <div class="login-form-item">
                                <div class="login-message-text">
                                    <p>激活账号邮件已发送至您的邮箱，请前往激活完成注册。</p>
                                </div>
                                <input id="toEmailUrl" type="button" class="button login-button buttonBlue last" value="前往邮箱">
                            </div>
                        </form>
                    </div>

                    <%@include file="/WEB-INF/include/login_service.jsp"%>
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
                $("#errorMessage").text("请输入正确的邮箱地址");
                $("#errorMessage").parent().removeClass("none");
                $username.focus();
                return false;
            }

            if(!isPassword($.trim($password.val()))){
                $("#errorMessage").text("请输入正确的密码");
                $("#errorMessage").parent().removeClass("none");
                $password.focus();
                return false;
            }

            if (isEmpty($nickname.val())) {
                $("#errorMessage").text("请输入昵称");
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
                        alert(data.data);
                        $(".position-phone-login").addClass("none");
                        $(".t-center").addClass("none");
                        $(".position-message-login").removeClass("none");
                    } else {
                        $(".position-message-login").addClass("none");
                        alert(data.err);
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
            if(url != ''){
                layer.closeAll();
                window.open(url);
            }else{
                layer.msg("抱歉!未找到对应的邮箱登录地址");
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

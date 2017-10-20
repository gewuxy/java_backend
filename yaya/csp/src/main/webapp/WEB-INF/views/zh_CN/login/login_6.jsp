<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/19
  Time: 18:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>手机登录</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
</head>

<body>
<div id="wrapper">
    <div class="login login-banner" >
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

                    <!--切换  手机登录-->
                    <div class="login-box-main position-phone-login">
                        <form action="${ctx}/mgr/login" method="post" id="loginForm" name="loginForm">
                            <input type="hidden" name="thirdPartyId" value="6">
                            <div class="login-form-item">
                                <label for="mobile" class="cells-block pr">
                                    <input id="mobile" name="mobile" value="${mobile}" type="tel" class="login-formInput" placeholder="手机号">
                                </label>
                                <label for="captcha" class="cells-block pr">
                                    <input id="captcha" name="captcha" type="text" class="login-formInput codeInput" placeholder="验证码">
                                    <span href="javascript:;" class="code" id="btnSendCode" >获取</span>
                                </label>
                                <span class="cells-block error ${not empty error ? '':'none'}" >
                                    <img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span id="errorMessage">${error}</span>
                                </span>
                                <input type="button" id="submitBtn" class="button login-button buttonBlue last" value="确认登录">
                            </div>
                        </form>
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

<script type="text/javascript">
    const lt = 60;
    var lefttime = lt;

    function checkMobile(){
        var mobile = $("#mobile").val();
        if (isEmpty(mobile)){
            $("#errorMessage").text("请输入手机号码");
            $("#errorMessage").parent().removeClass("none");
            $("#mobile").focus();
            return false;
        }
        if (!isMobile(mobile)){
            $("#errorMessage").text("请输入正确的手机号码");
            $("#errorMessage").parent().removeClass("none");
            $("#mobile").focus();
            return false;
        }
        getVerifyCode(mobile);
    }

    function getVerifyCode(mobile) {
        $.ajax({
            url: '${ctx}/mgr/sendCaptcha?mobile='+mobile, //目标地址
            type: "post",
            dataType: "json",
            success: function (data){
                lefttime = lt;
                if (data.code == 0){
                    alert("验证码已经发送到您的手机,请注意查收");
                    $("#btnSendCode").unbind("click",checkMobile);
                    window.setTimeout("checktime()",1000);
                } else {
                    alert("验证码发送失败, 请确认您的手机号");
                }
            },
            error: function (a, n, e) {
                alert("获取数据异常："+a + " - "+n+" - "+e);
            }

        });

    }

    function checktime(){
        if (lefttime > 0){
            $("#btnSendCode").text(lefttime+"s");
            lefttime -- ;
            window.setTimeout("checktime()",1000);
        }else{
            $("#btnSendCode").text("重新发送");
            $("#btnSendCode").bind("click",checkMobile);
        }
    }

    $(function(){
        //让背景撑满屏幕
        $('.login-banner').height($(window).height());
        //让协议定位到底部
        $('.login-box-item').height($('.login-box').height());


        $("#btnSendCode").bind('click',checkMobile);

        $("#submitBtn").click(function(){
            var captcha = $("#captcha").val();

            if (!isVerifyCode(captcha)) {
                $("#errorMessage").text("请输入6位整数验证码");
                $("#errorMessage").parent().removeClass("none");
                $("#captcha").focus();
                return false;
            }

            $("#errorMessage").parent().addClass("none");
            $("#loginForm").submit();
        });

    })

    $(document).keydown(function(evt){
        if (evt.keyCode == 13){
            $("#submitBtn").trigger("click");
        }
    });
</script>
</body>
</html>

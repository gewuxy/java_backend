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
    <title>手機登錄</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
</head>

<body>
<div id="wrapper">
    <div class="login login-banner" >
        <div class="page-width pr">
            <div class="login-header">
                <%@include file="/WEB-INF/include/switch_language.jsp"%>
            </div>

            <div class="login-box clearfix">

                <%@include file="../include/login_left.jsp"%>

                <div class="col-lg-5 login-box-item">

                    <!--切换  手机登录-->
                    <div class="login-box-main position-phone-login">
                        <form action="${ctx}/mgr/login" method="post" id="loginForm" name="loginForm">
                            <input type="hidden" name="thirdPartyId" value="6">
                            <div class="login-form-item">
                                <label for="mobile" class="cells-block pr">
                                    <input id="mobile" name="mobile" value="${mobile}" type="tel" class="login-formInput" placeholder="手機號">
                                </label>
                                <label for="captcha" class="cells-block pr">
                                    <input id="captcha" name="captcha" type="text" class="login-formInput codeInput" placeholder="輸入驗證碼">
                                    <span href="javascript:;" class="code" id="btnSendCode" >獲取</span>
                                </label>
                                <span class="cells-block error ${not empty error ? '':'none'}" >
                                    <img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span id="errorMessage">${error}</span>
                                </span>
                                <input type="button" id="submitBtn" class="button login-button buttonBlue last" value="確認登錄">
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


<script type="text/javascript">
    const lt = 60;
    var lefttime = lt;

    function checkMobile(){
        var mobile = $("#mobile").val();
        if (isEmpty(mobile)){
            $("#errorMessage").text("請輸入手機號碼");
            $("#errorMessage").parent().removeClass("none");
            $("#mobile").focus();
            return false;
        }
        if (!isMobile(mobile)){
            $("#errorMessage").text("請輸入正確的手機號碼");
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
                    layer.msg("驗證碼已經發送到您的手機,請注意查收");
                    $("#btnSendCode").unbind("click",checkMobile);
                    window.setTimeout("checktime()",1000);
                } else {
                    layer.msg("驗證碼發送失敗, 請確認您的手機號");
                }
            },
            error: function (a, n, e) {
                layer.msg("獲取數據異常："+a + " - "+n+" - "+e);
            }

        });

    }

    function checktime(){
        if (lefttime > 0){
            $("#btnSendCode").text(lefttime+"s");
            lefttime -- ;
            window.setTimeout("checktime()",1000);
        }else{
            $("#btnSendCode").text("重新發送");
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
                $("#errorMessage").text("請輸入6位整數驗證碼");
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

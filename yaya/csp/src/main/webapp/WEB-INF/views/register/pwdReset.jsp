<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta charset="UTF-8">
    <title><fmt:message key="page.confirm.password.title"/><fmt:message key="page.common.appName"/></title>
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
                        <form action="${ctx}/api/email/doReset" name="resetForm" id="resetForm" method="post">
                            <input type="hidden" name="code" value="${code}">
                            <div class="login-form-item">
                                <label for="pwd" class="cells-block pr">
                                    <input type="text" required placeholder="<fmt:message key="page.input.newPassword"/>" class="login-formInput icon-register-hot last none" maxlength="24">
                                    <input id="pwd" type="password" name="password" required placeholder="<fmt:message key="page.input.newPassword"/>" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                                    <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                                </label>
                                <span class="cells-block error ${not empty message ? '':'none'}" ><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span id="errorMessage">${message}</span></span>
                                <input id="submitBtn" type="button" class="button login-button buttonBlue last" value="<fmt:message key="page.confirm.password.button"/>">
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


        $("#submitBtn").click(function(){
            var passwd = $("#pwd").val();

            if(!isPassword($.trim(passwd))){
                $("#errorMessage").text("<fmt:message key="page.password.format"/>");
                $("#errorMessage").parent().removeClass("none");
                $("#pwd").focus();
                return false;
            }

            $("#errorMessage").parent().addClass("none");
            $("#resetForm").submit();

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
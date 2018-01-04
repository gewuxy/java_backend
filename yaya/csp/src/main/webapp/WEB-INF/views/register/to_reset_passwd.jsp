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
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta charset="UTF-8">
    <title><fmt:message key="page.reset.password.title"/> - <fmt:message key="page.common.appName"/></title>
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

                    <!--切换 -->
                    <div class="login-box-main position-resetEmail-login">
                        <form id="resetForm" name="resetForm">
                            <div class="login-form-item">
                                <label for="email" class="cells-block pr">
                                    <input id="email" name="email" type="text" class="login-formInput" placeholder="<fmt:message key="page.email.login.address"/>">
                                </label>
                                <span class="cells-block error ${not empty error ? '':'none'}"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span id="errorMessage">${error}</span></span>
                                <input type="button" id="submitBtn" class="button login-button buttonBlue last" value="<fmt:message key="page.reset.password.button"/>">
                            </div>
                        </form>
                    </div>

                    <!--切换  重置密码-->
                    <div class="login-box-main position-message-login none">
                        <form action="">
                            <div class="login-form-item">
                                <div class="login-message-text">
                                    <c:choose>
                                        <c:when test="${csp_locale eq 'en_US'}"><p style="font-size:16px;"></c:when>
                                        <c:otherwise><p></c:otherwise>
                                    </c:choose>
                                    <fmt:message key="page.reset.password.tips"/></p>
                                </div>
                                <input id="toEmailUrl" type="button" class="button login-button buttonBlue last" value="<fmt:message key="page.email.register.toEmail"/>">
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
                $("#errorMessage").text("<fmt:message key="page.email.login.userNameError"/>");
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
                    alert("<fmt:message key="page.common.exception"/>"+"："+a + " - "+n+" - "+e);
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
                layer.msg("<fmt:message key="page.email.url.notfount"/>");
            }
        });
    })
</script>

</body>
</html>

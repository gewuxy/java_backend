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
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <title><fmt:message key="page.title.email.bind.success"/> -<fmt:message key="page.common.appName"/> </title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />

    <script src="${ctxStatic}/js/commonH5.js"></script>

</head>
<body>
<div id="wrapper">
    <div class="login login-banner" style="height:900px;">
        <div class="page-width pr">
            <div class="login-box clearfix">
                <%@include file="../include/login_left.jsp"%>

                <div class="col-lg-5 login-box-item">

                    <!--切换  重置密码-->
                    <div class="login-box-main position-message-login ">
                        <form action="">
                            <div class="login-form-item">
                                <div class="login-message-text" style="text-align: left !important;">
                                    <c:if test="${csp_locale == 'en_US'}">
                                        <p style="font-size:16px;">Dear&nbsp;&nbsp;<span class="color-blue">${user}</span>,<br /> Welcome aboard! <a href="${ctx}/mgr/login" class="color-blue">Please click here to login CSPmeeting.</a> </p>
                                    </c:if>
                                    <c:if test="${csp_locale != 'en_US'}">
                                        <p style="font-size:16px;"><fmt:message key="page.words.email.bind.tips1"/> &nbsp;&nbsp;<span class="color-blue">${user}</span></p>
                                        <p style="font-size:16px;"><fmt:message key="page.words.email.bind.tips2"/> </p>
                                        <input  type="button" id="login" class="button login-button buttonBlue last" value="<fmt:message key="page.button.email.bind.tips3"/>">
                                    </c:if>

                                </div>

                            </div>
                        </form>
                    </div>

                </div>
            </div>
            <%@include file="../include/footer.jsp"%>
        </div>
    </div>
</div>

<script>
    $(function(){
        //让背景撑满屏幕
        $('.login-banner').height($(window).height());
        //让协议定位到底部
        $('.login-box-item').height($('.login-box').height());

        $("#login").click(function () {
            window.location.href="${ctx}/mgr/login";
        });


    })
</script>
</body>
</html>
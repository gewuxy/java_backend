<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/22
  Time: 18:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <title>404</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">

    <style>
        html,body { background-color:#fff;}
    </style>
</head>
<body >
<div id="wrapper">
    <div class="admin-content" >
        <div class="login-box-main position-message-login " style="margin-top:50px;">
            <form action="">
                <div class="login-form-item login-error-page">
                    <div class="login-message-text" >
                        <p class="t-center"><img src="${ctxStatic}/images/404-img.gif" alt="" style="margin-top:10px; margin-bottom:20px;"></p>
                        <p>Page Not Found <Br /> 您访问的页面不存在</p>
                    </div>
                    <p class="t-center"><a onclick="history.back();" class="button" >&nbsp;</a></p>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>

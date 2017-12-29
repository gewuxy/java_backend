<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/9/29
  Time: 17:06
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>异常提示</title>
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
        <div class="login-box-main position-message-login " style="margin-top:90px;">
            <form action="">
                <div class="login-form-item login-error-page">
                    <div class="login-message-text" >
                        <p class="t-center"><img src="${ctxStatic}/images/error-img.png" alt="" style="margin-top:10px; margin-bottom:20px;"></p>
                        <p class=" t-center">${empty exception.message ? "未知异常 (Unknown Exception)" : exception.message}</p>
                        <p class="t-center"><a onclick="history.back();" class="button" >&nbsp;</a></p>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>


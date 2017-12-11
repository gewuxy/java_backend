<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/12
  Time: 17:15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>${banner.title}</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta http-equiv="X-UA-Compatible" content="chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="stylesheet" href="${ctxStatic}/css/reset-h5.css">
</head>
<body>
<div id="header" style="text-align: center;">
    <h3>${banner.title}</h3>
</div>
<div style="padding: 0 10px;">
${banner.content}
</div>
</body>
</html>

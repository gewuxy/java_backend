<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/16
  Time: 17:22
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>登录界面</title>
    <decorator:head />
</head>
<body>
<div id="wrapper">
    <div class="login login-banner" >
        <div class="page-width pr">
            <div class="login-header">
                <a href="#" class="login-language" title="切换语言">中文</a>
            </div>
            <decorator:body />
            <div class="login-bottom">
                <p><%@include file="/WEB-INF/include/copy_right.jsp"%></p>
            </div>
        </div>
    </div>
</div>
<script>
    $(function(){
        //让背景撑满屏幕
        $('.login-banner').height($(window).height());
        //让协议定位到底部
        $('.login-box-item').height($('.login-box').height());

    })
</script>
</body>
</html>

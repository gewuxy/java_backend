<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>YaYa医师</title>
    <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0'/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/regist/css/css.css">
    <script src="js/jquery-1.10.2.js"></script>
    <script src=" https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <style>


        .ani{
            position:absolute;
        }



    </style>
</head>

<body class="layout" style="background:url(images/s_bg.png) no-repeat; background-size:cover;">
<div class="success-logoImage">
    <img class=" ani resize" src="${ctxStatic}/regist/images/YA.png"  >
</div>
<div class="text" style="top:120px; left:15px; right:15px; margin:0 auto;">
    <h1 class="h_one">恭喜您，注册成功！ </h1>
    <p class="t-center">您的用户名:${param.mobile}，</p>
    <p class="t-center">请妥善保管。</p>
    <p class="t-center">现在您可以前往YaYa医师App登录使用啦！</p>
</div>


<script>
    $(document).ready(function(){
        scaleW=window.innerWidth/320;
        scaleH=window.innerHeight/480;
        var resizes = document.querySelectorAll('.resize');
        for (var j=0; j<resizes.length; j++) {
            resizes[j].style.width=parseInt(resizes[j].style.width)*scaleW+'px';
            resizes[j].style.height=parseInt(resizes[j].style.height)*scaleH+'px';
            resizes[j].style.top=parseInt(resizes[j].style.top)*scaleH+'px';
            resizes[j].style.left=parseInt(resizes[j].style.left)*scaleW+'px';
            console.log("width:"+parseInt(resizes[j].style.width)*scaleW+'px');
            console.log("height:"+parseInt(resizes[j].style.height)*scaleH+'px');
            console.log("top:"+parseInt(resizes[j].style.top)*scaleH+'px');
            console.log("left:"+parseInt(resizes[j].style.left)*scaleW+'px');
        }

    });

</script>
</body>
</html>


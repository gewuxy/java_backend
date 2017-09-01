<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
	<%@include file="/WEB-INF/include/page_context.jsp"%>
<link rel="stylesheet"  href="${ctxStatic}/regist/css/style.css">

</head>

<body>

<img class="ani resize" style="width:320px; height:480px; top:0; left:0;" src="${ctxStatic}/regist/images/bg.jpg" >
<h1 class="ani resize ys" style="top:25px; left:20px; color:#FFF; font-size:18px;">医师同仁：</h1>
<p class="ani resize tex" style="top:55px; left:20px; color:#FFF; font-size:16px;text-indent:2em; width:280px; right:20px; line-height:25px;">

		您好！价值69.9元激活码已经全部注册完毕，感谢您的支持！</p>

<p class="ani resize " style="top:170px;color:#FFF; font-size:16px; right:20px; line-height:25px;">YaYa医师</p>
<h1 class="ani resize jh" style="top:400px; left:20px; color:#FFF; font-size:25px; width:280px; right:20px; text-align:center;">激活码余额为0</h1>
<script>  

scaleW=window.innerWidth/320;
scaleH=window.innerHeight/480;
var resizes = document.querySelectorAll('.resize');
          for (var j=0; j<resizes.length; j++) {
           resizes[j].style.width=parseInt(resizes[j].style.width)*scaleW+'px';
		   resizes[j].style.height=parseInt(resizes[j].style.height)*scaleH+'px';
		   resizes[j].style.top=parseInt(resizes[j].style.top)*scaleH+'px';
		   resizes[j].style.left=parseInt(resizes[j].style.left)*scaleW+'px'; 
          }
		  
 
</script>
</body>
</html>

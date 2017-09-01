<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>浏览器下载App</title>
  </head>
  
  <body onload="alert('由于微信的限制，点击右上角按钮并选择‘在浏览器中打开’下载安装包')">
    
  </body>
</html>

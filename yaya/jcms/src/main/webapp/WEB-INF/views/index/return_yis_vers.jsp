<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String userAgent = request.getHeader("User-Agent");
if(userAgent!=null && (userAgent.contains("iPhone")|| userAgent.contains("iPod")||userAgent.contains("iPad"))){
	response.sendRedirect("https://itunes.apple.com/cn/app/id669352079");
}else{
	if(userAgent.indexOf("MicroMessenger")>=0){//如果是微信扫描
		request.getRequestDispatcher("wx_android.jsp").forward(request, response);
	}else{
		response.sendRedirect("http://139.199.170.178/apkfile/YaYa_v7.1.70.apk");
	}
}

%>

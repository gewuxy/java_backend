<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/1/5
  Time: 9:55
--%>

<%
    //将项目的根路径放入到当前页面的上下文中，
    // 其他页面通过引入这个jsp文件可以直接用el表达式获取项目跟路径
    // ${base} ${statics}
    String basePath = request.getContextPath();
    pageContext.setAttribute("base",basePath);
    pageContext.setAttribute("statics",basePath+"/static");
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="${statics}/js/v2/jquery.min.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit"/>
<!--<meta name="viewport" content="width=device-width, initial-scale=1">-->
<link rel="stylesheet" type="text/css" href="${statics}/js/layer/skin/default/layer.css"/>
<link rel="shortcut icon" href="${statics}/favicon.ico">

<link href="${statics}/css/v2/global-v2.css" rel="stylesheet">
<link href="${statics}/css/v2/style-v2.css" rel="stylesheet">
<link href="${statics}/css/v2/menu-v2.css" rel="stylesheet">
<script src="${statics}/js/v2/jquery.min.js"></script>
<script src="${statics}/js/v2/jquery-migrate-1.1.0.min.js"></script>
<script src="${statics}/js/layer/layer.js"></script>
<!--[if lt IE 9]>
<script src="${statics}/js/v2/html5.js"></script>
<![endif]-->
<script>

</script>

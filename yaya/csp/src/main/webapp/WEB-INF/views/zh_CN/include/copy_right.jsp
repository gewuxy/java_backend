<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/16
  Time: 16:56
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="now" class="java.util.Date" scope="page"/>

<a href="http://www.beian.gov.cn/" target="_blank"><img src="${ctxStatic}/images/icp.png" align="absmiddle"/>粤公网安备 44010602003231号</a> <a href="http://www.miibeian.gov.cn/" target="_blank">粤ICP备12087993号</a> © 2012-<fmt:formatDate value="${now}" pattern="yyyy"/> 敬信科技版权所有




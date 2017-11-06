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
Copyright © 2012-<fmt:formatDate value="${now}" pattern="yyyy"/> Jingxin Tech. All Rights Reserved.
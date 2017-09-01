<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/7/20
  Time: 18:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<form action="${ctx}/test/save" method="post">
    ID:<input type="text" name="id"><br>
    NAME:<input type="text" name="name"><br>
    AGE:<input type="text" name="age"><br>
    DATE:<input type="text" name="date"><br>
    <button name="submit" >submit</button>
</form>
</body>
</html>

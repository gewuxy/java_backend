<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/19
  Time: 17:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${dataFile.title}</title>
</head>
<body oncontextmenu="return false">
<embed width="100%"
       height="100%"
       name="plugin"
       src="${dataFile.filePath}"
       type="application/pdf"
/>
</body>
</html>

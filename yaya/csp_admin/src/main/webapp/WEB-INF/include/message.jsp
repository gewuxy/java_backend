<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/3
  Time: 14:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctype" value="${code == 0?'success':'error'}"/>
<div id="messageBox" class="alert alert-${ctype} hide" style="display: none;"><button data-dismiss="alert" class="close">×</button><span id="messageSpan">${err}</span></div>
<c:if test="${not empty err}">
    <script type="text/javascript">
            $("#messageBox").show();
            setTimeout(function(){
                $("#messageBox").hide();
            },3000);
    </script>
</c:if>

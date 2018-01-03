<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/11/1
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="login-box-info t-center "
<c:choose>
    <c:when test="${csp_locale eq 'en_US'}">
        <div class="login-box-info t-center " style="width:279px;">
        <p><fmt:message key="page.login.service.start"/> <br/>
    </c:when>
    <c:otherwise><div class="login-box-info t-center " ><p><fmt:message key="page.login.service.start"/></c:otherwise>
</c:choose>
    <a href="${ctx}/index/17103116062545591360" class="color-blue"><fmt:message key="page.login.service.end"/></a>
</p>
</div>
<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/30
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<a href="${ctx}/login" class="login-header-button" title="<fmt:message key='page.button.login'/>">
    <c:choose>
        <c:when test="${not empty username}">
            ${username}
        </c:when>
        <c:otherwise>
            <fmt:message key="page.button.login"/>
        </c:otherwise>
    </c:choose>&nbsp;&nbsp;
    <span><img src="${ctxStatic}/images/login-user-icon.png" alt=""></span></a>

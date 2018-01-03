<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/11/1
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-lg-5">
    <div class="login-box-logo">
        <c:choose>
            <c:when test="${csp_locale eq 'en_US'}">
                <img src="${ctxStatic}/images/login-logo-en.png" alt="">
            </c:when>
            <c:when test="${csp_locale eq 'zh_TW'}">
                <img src="${ctxStatic}/images/login-logo-tw.png" alt="">
            </c:when>
            <c:otherwise>
                <img src="${ctxStatic}/images/login-logo.png" alt="">
            </c:otherwise>
        </c:choose>

    </div>
</div>
<div class="col-lg-2">&nbsp;</div>

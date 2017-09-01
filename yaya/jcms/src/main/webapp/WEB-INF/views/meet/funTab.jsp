<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/27
  Time: 16:43
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="table-top-box clearfix">
    <c:if test="${funId == 1 && course == null}"><span class="fr frButton"><a style="cursor: pointer;" class="color-blue fx-btn-1" id="quoteBtn">引用资源</a></span></c:if>
    <c:forEach items="${modules}" var="m">
        <a href="${ctx}/func/meet/config?meetId=${meetId}&moduleId=${m.id}" class="mask-le ${m.functionId == funId?'cur':''}"><span class="icon iconfont icon-minIcon${m.functionId}"></span>${m.moduleName}</a>
    </c:forEach>
    <a href="${ctx}/func/meet/materials?meetId=${meetId}" class="mask-le ${funId == null?'cur':''}"><span class="icon iconfont icon-minIcon7"></span>课件</a>
</div>
<div class="formrow-hr"></div>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="user-left-box">
    <div class="user-userinfo clearfix item-radius">
        <div class="img"><a href="${ctx}/mgr/user/toAvatar" class="admin-user-head" title="<fmt:message key="page.label.words"/>"><img <c:if test="${not empty dto.avatar}">src="${dto.avatar}"</c:if>
        <c:if test="${empty dto.avatar}">src="${ctxStatic}/images/img/user-default.png"</c:if>
               id="image" alt="" style="widtH:126px; height:126px;"><i></i></a></div>
        <div class="name" id="name">${dto.nickName}</div>
        <div class="email">${dto.email == null ? dto.mobile:dto.email}</div>
        <div class="binding">
            <c:if test="${fn:length(dto.bindInfoList) != 0}">
                    <c:forEach items="${dto.bindInfoList}" var="list">
                        <c:if test="${list.thirdPartyId == 1}">
                            <img src="${ctxStatic}/images/icon-user-wechat.png" alt="">
                        </c:if>
                        <c:if test="${list.thirdPartyId == 2}">
                            <img src="${ctxStatic}/images/icon-user-weibo.png" alt="">
                        </c:if>
                        <c:if test="${list.thirdPartyId == 5}">
                            <img src="${ctxStatic}/images/icon-user-medcn.png"  alt="">
                        </c:if>
                        <c:if test="${list.thirdPartyId == 4}">
                            <img src="${ctxStatic}/images/icon-user-twitter.png" alt="">
                        </c:if>
                        <c:if test="${list.thirdPartyId == 3}">
                            <img src="${ctxStatic}/images/icon-user-facebook.png" alt="">
                        </c:if>
                    </c:forEach>
            </c:if>
            <c:if test="${not empty dto.email}">
                <img src="${ctxStatic}/images/icon-user-email.png"  alt="">
            </c:if>
            <c:if test="${not empty dto.mobile}">
                <img src="${ctxStatic}/images/icon-user-phone.png"  alt="">
            </c:if>
            <c:if test="${fn:length(dto.bindInfoList) == 0 && empty email && empty mobile}">
                <img src="${ctxStatic}/images/default_blank.png" alt="" width="34" height="34">
            </c:if>
        </div>
        <%--<img src="./images/icon-user-facebook.png" alt="">--%>
    </div>
    <div class="user-statistics item-radius">
        <div class="title"><fmt:message key="page.words.ppt"/> </div>
        <div class="main line"><fmt:message key="page.words.upload"/> <img src="${ctxStatic}/images/user-statistics-icon.png" alt="" class="icon"><span class="item"><span class="num">${dto.pptCount}</span><fmt:message key="page.words.upload.count"/> </span></div>
        <div class="main"><fmt:message key="page.words.share"/> <img src="${ctxStatic}/images/user-statistics-icon.png" alt="" class="icon"><span class="item"><span class="num">${dto.shareCount}</span><fmt:message key="page.words.share.count"/> </span></div>
    </div>
</div>


    <script>
        $(function(){




        })



    </script>
</body>
</html>
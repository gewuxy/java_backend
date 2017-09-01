<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/8/4
  Time: 10:40
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>热门会议</title>
    <%@include file="/WEB-INF/include/page_context_weixin.jsp"%>
</head>
<body>
<div class="warp ">

    <div class="item">
        <div class="metting-list">
            <ul>
                <li>
                    <a>
                        <div class="fl">
                            <div class="metting-list-img metting-file-max">
                                <img src="${ctxStatic}/images/icon-max-file.png" alt="" class="img-responsive" >
                            </div>
                        </div>
                        <div class="oh metting-file-content">
                            <div class="title">${folder.meetName}</div>
                            <div class="info last"><span class="metting-number">${folder.meetCount}个会议&nbsp;》</span></div>
                        </div>
                    </a>
                </li>

                <c:forEach items="${list}" var="meet">
                    <c:choose>
                        <c:when test="${meet.type == 0}">
                            <li>
                                <a href="${ctx}/weixin/meet/sublist?id=${meet.id}&rootId=${rootId}">
                                    <div class="fl">
                                        <div class="metting-list-img">
                                            <img src="${ctxStatic}/images/icon-min-file.png" alt="" class="img-responsive" >
                                        </div>
                                    </div>
                                    <div class="oh">
                                        <div class="title overflowText">${meet.meetName}</div>
                                        <div class="info last"><span class="color-blue metting-number">${meet.meetCount}个会议&nbsp;》</span></div>
                                    </div>
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a href="${ctx}/weixin/meet/info?meetId=${meet.id}" >
                                    <div class="fr">
                                        <div class="metting-list-img">
                                            <img src="${meet.lecturerHead}" alt="" class="img-responsive" >
                                        </div>
                                    </div>
                                    <div class="oh metting-list-content">
                                        <c:if test="${meet.state == 1}">
                                            <c:set var="meetState" value="未开始"/>
                                            <c:set var="color" value="color-blue-3"/>
                                        </c:if>
                                        <c:if test="${meet.state == 2}">
                                            <c:set var="meetState" value="进行中"/>
                                            <c:set var="color" value="color-yellow"/>
                                        </c:if>
                                        <c:if test="${meet.state == 3}">
                                            <c:set var="meetState" value="精彩回顾"/>
                                            <c:set var="color" value="color-blue"/>
                                        </c:if>
                                        <div class="title overflowText">${meet.meetName}</div>
                                        <div class="info"><span class="status ${color}">${meetState}</span>&nbsp;<span class="time"><fmt:formatDate value="${meet.startTime}" pattern="yy/MM/dd HH:mm"/></span>&nbsp;<span class="YaYa-metting-label">${meet.meetType}&nbsp;&nbsp;
                                            <c:if test="${meet.rewardCredit}"><span class="metting-label metting-label-CME">CME</span>&nbsp;</c:if>
                                            <c:if test="${meet.xsCredits != null && meet.xsCredits > 0}"><span class="metting-label metting-label-money"><i></i>&nbsp;象数</span></span></c:if>
                                        </div>
                                        <div class="info overflowText last">${meet.lecturer}  ${meet.lecturerTitle}  ${meet.organizer}</div>
                                    </div>
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </div>
    </div>



</div>
<script>
    $(function() {
    });
</script>

</body>
</html>

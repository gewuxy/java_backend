<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/8/2
  Time: 15:00
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>会议详情</title>
    <%@include file="/WEB-INF/include/page_context_weixin.jsp"%>
</head>
<body>
<div class="warp">
    <div class="YaYa-metting-details">
        <div class="YaYa-header">
            <div class="YaYa-cover-img "><img src="${meet.coverUrl}" class="img-responsive" alt=""></div>
            <!--<div class="YaYa-cover-img "><img src="http://file.medyaya.cn//course/100035/ppt/17071419064898541589.jpg" class="img-responsive" alt=""></div>-->
            <div class="YaYa-metting-time"><span id="meetStartDate"><fmt:formatDate value="${meet.startTime}" pattern="M月d日 HH:mm"/></span> &nbsp;&nbsp;时长${meetDuration}</div>
            <div class="YaYa-collect"><a href="javascript:;" class="${meet.stored == 1?'current ':''}collect-hook"><i class="icon-cllect"></i></a></div>
        </div>
        <div class="YaYa-metting-info">
            <h1 class="title">${meet.meetName}</h1>
            <div class="YaYa-metting-label">${empty meet.meetType?"其他":meet.meetType}&nbsp;&nbsp;
                <c:if test="${meet.rewardCredit}"><span class="metting-label metting-label-CME">CME</span>&nbsp;</c:if>
                <c:if test="${meet.requiredXs}"><span class="metting-label metting-label-money"><i></i>&nbsp;象数</span></c:if>
            </div>
            <c:if test="${meet.rewardCredit}">
                <p class="min-size">完成本次会议可获得${meet.eduCredits}学分, 剩余${meet.remainAwardCreditCount}个名额</p>
            </c:if>
            <c:if test="${meet.requiredXs}">
                <p class="min-size">完成本次会议可获得${meet.xsCredits}象数, 剩余${meet.remainAwardXsCount}个名额</p>
            </c:if>
            <c:if test="${!meet.requiredXs && meet.xsCredits != null && meet.xsCredits>0}">
                <p class="min-size">完成本次会议需支付${meet.xsCredits}象数</p>
            </c:if>
        </div>
        <div class="YaYa-metting-intro clearfix">
            <div class="fl">
                <img class="img-radius" src="${meet.headimg}" alt="">
            </div>
            <div class="oh">
                <h4>${meet.organizer}</h4>
                <p class="color-gray-2">主办单位号</p>
            </div>
        </div>
        <div class="YaYa-split cleafix"></div>
        <div class="YaYa-metting-detail">
            <div class="YaYa-metting-title">
                <h3>会议简介</h3>
            </div>
            <div class="YaYa-metting-author clearfix">
                <div class="fl">
                    <img src="${meet.lecturerHead}" alt="">
                </div>
                <div class="oh">
                    <h4>${empty meet.lecturer ?"未知讲者":meet.lecturer}&nbsp;&nbsp;${meet.lecturerTitle}</h4>
                    <p class="color-gray-2">${empty meet.lecturerHos ?"未知单位":meet.lecturerHos}</p>
                </div>
            </div>
            <div class="YaYa-metting-detail-main">
                ${meet.introduction}
            </div>
        </div>
        <div class="YaYa-fixed-button">
            <div class="flex ">
                <c:forEach items="${meet.modules}" var="module">
                    <c:choose>
                        <c:when test="${module.functionId == 1 }">
                            <c:set var="icon" value="icon-metting"/>
                        </c:when>
                        <c:when test="${module.functionId == 2 }">
                            <c:set var="icon" value="icon-exam"/>
                        </c:when>
                        <c:when test="${module.functionId == 3}">
                            <c:set var="icon" value="icon-exam"/>
                        </c:when>
                        <c:when test="${module.functionId == 4}">
                            <c:set var="icon" value="icon-faq"/>
                        </c:when>
                        <c:when test="${module.functionId == 5}">
                            <c:set var="icon" value="icon-faq"/>
                        </c:when>
                    </c:choose>
                    <div class="${module.functionId == 1?'flex-item ':''}t-center"><a href="${ctx}/weixin/app/download" class="YaYa-button-item${module.functionId==1?' YaYa-button-hot':''}"><i class="${icon}"></i>${module.moduleName}</a></div>
                </c:forEach>

            </div>
        </div>
    </div>
</div>

<script>
    $(function(){
        $('.collect-hook').on('click',function(){
            storeOrNot();
        });

        function storeOrNot(){
            var meetId = "${meet.id}";
            $.get('${ctx}/weixin/meet/favorite/operate',{'meetId':meetId},function (data) {
                $('.collect-hook').toggleClass('current');
            },'json');
        }
    });
</script>

</body>
</html>

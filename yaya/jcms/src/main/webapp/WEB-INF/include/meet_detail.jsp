<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/6/8
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="fl metting-detail">
    <div class="metting-detail-item">
        <div class="metting-detail-cover">
            <img src="${ctxStatic}/img/_inputImage2.png" width="100%" alt="">
        </div>
        <div class="metting-detail-info clearfix">
            <div class="metting-detail-time">
                <fmt:formatDate value="${meet.meetProperty.startTime}" pattern="yyyy/MM/dd HH:mm"/><i class="rowSpace">|</i>时长:${meet.duration}<i class="style-2 listTips">${meet.stateName}</i><i class="style-1 listTips">${meet.meetType}</i>
            </div>
            <c:choose>
                <c:when test="${meet.meetSetting.requiredXs != null && meet.meetSetting.requiredXs == 0}">
                    <p>本次会议需支付象数：<span class="color-blue">${meet.meetSetting.xsCredits}</span></p>
                </c:when>
                <c:when test="${meet.meetSetting.requiredXs != null && meet.meetSetting.requiredXs == 1}">
                    <p>本次会议奖励象数：<span class="color-blue">${meet.meetSetting.xsCredits}</span>，
                        还有 <span class="color-blue">${attendCount>meet.meetSetting.awardLimit?0:meet.meetSetting.awardLimit-attendCount}</span> 人能够获得奖励</p>
                </c:when>
            </c:choose>
            <div class="metting-detail-introBox  clearfix">
                <div class="fl">
                    <img
                    <c:choose>
                    <c:when test="${empty pubUserHeadImg}">
                            src="${ctxStatic}/img/hz-detail-img-info.jpg"
                    </c:when>
                    <c:otherwise>
                            src="${pubUserHeadImg}"
                    </c:otherwise>
                    </c:choose>
                            alt="">
                </div>
                <div class="oh">
                    <h4>${meet.organizer}</h4>
                    <p>主办方</p>
                </div>
            </div>
        </div>
        <div class="metting-detail-content clearfix">
            <h3 class="mettingTitle"><span>会议简介</span></h3>
            <div class="metting-detail-introBox metting-detail-introBox-max clearfix">
                <div class="fl">
                    <img
                    <c:choose>
                    <c:when test="${empty meet.lecturer.headimg}">
                            src="${ctxStatic}/img/hz-detail-img-info.jpg"
                    </c:when>
                    <c:otherwise>
                            src="${appFileBase}${meet.lecturer.headimg}"
                    </c:otherwise>
                    </c:choose> alt="">
                </div>
                <div class="oh">
                    <h4>${meet.lecturer.name}&nbsp;&nbsp;${meet.lecturer.title}</h4>
                    <p>${meet.lecturer.hospital}</p>
                </div>
            </div>
            <div class="metting-detail-main">
                <p>${meet.introduction}</p>
            </div>
            <div class="metting-detail-state">
                <p>本站所有会议、视频、科研资料、题库等资料均由内容提供方版权所有，未经允许不可用作其
                    他商业用途，敬请熟知。</p>
            </div>
        </div>
    </div>
</div>

<div class="fl metting-detail-rightItem">
    <c:forEach items="${modules}" var="module">
        <div class="wx-box clearfix"  >
            <div class="fl">
                <p class="iconImg"><span class="icon iconfont icon-minIcon15"></span></p>
                <p>${module.moduleName}管理</p>
            </div>
            <div class="oh">
                <a  href="${ctx}/func/meet/config?meetId=${meet.id}&moduleId=${module.id}" class="color-blue"><span class="icon iconfont icon-minIcon9"></span>设置</a>
            </div>
        </div>
    </c:forEach>
    <div class="wx-box clearfix" id="kj">
        <div class="fl">
            <p class="iconImg"><span class="icon iconfont icon-minIcon15"></span></p>
            <p>课件管理</p>
        </div>
        <div class="oh">
            <a id="kj1" href="${ctx}/func/meet/materials?meetId=${meet.id}" class="color-blue"><span class="icon iconfont icon-minIcon9"></span>上传课件</a>
        </div>
    </div>
    <div class="wx-box clearfix">
        <p class="t-center">
            <img src="${qrCodePath}" alt="">
        </p>
        <p class="t-center">
            H5会议分享链接
        </p>
    </div>
</div>

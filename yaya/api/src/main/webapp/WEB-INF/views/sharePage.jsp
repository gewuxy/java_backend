<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>YaYa会议微信详情</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/YaYa-metting-h5.css">
    <!--&lt;!&ndash; 高清方案 &ndash;&gt;-->
    <script>!function(e){function t(a){if(i[a])return i[a].exports;var n=i[a]={exports:{},id:a,loaded:!1};return e[a].call(n.exports,n,n.exports,t),n.loaded=!0,n.exports}var i={};return t.m=e,t.c=i,t.p="",t(0)}([function(e,t){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=window;t["default"]=i.flex=function(e,t){var a=e||100,n=t||1,r=i.document,o=navigator.userAgent,d=o.match(/Android[\S\s]+AppleWebkit\/(\d{3})/i),l=o.match(/U3\/((\d+|\.){5,})/i),c=l&&parseInt(l[1].split(".").join(""),10)>=80,p=navigator.appVersion.match(/(iphone|ipad|ipod)/gi),s=i.devicePixelRatio||1;p||d&&d[1]>534||c||(s=1);var u=1/s,m=r.querySelector('meta[name="viewport"]');m||(m=r.createElement("meta"),m.setAttribute("name","viewport"),r.head.appendChild(m)),m.setAttribute("content","width=device-width,user-scalable=no,initial-scale="+u+",maximum-scale="+u+",minimum-scale="+u),r.documentElement.style.fontSize=a/2*s*n+"px"},e.exports=t["default"]}]);  flex(100, 1);</script>
    <!--<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>-->
</head>
<body>
    <class class="warp">
        <div class="YaYa-metting-details">
            <div class="YaYa-header">
                <div class="YaYa-cover-img "><img src="http://www.medcn.cn/images/meeting/meeting_intr_header.png" class="img-responsive" alt=""></div>
                <div class="YaYa-metting-time"><fmt:formatDate value="${meet.meetProperty.startTime}" pattern="yyyy/MM/dd HH:mm"/> &nbsp;&nbsp;时长:${meet.duration}</div>
            </div>
            <div class="YaYa-metting-info">
                <h1 class="title">${meet.meetName}</h1>
                <c:choose>
                    <c:when test="${meet.meetProperty.eduCredits == null ||meet.meetProperty.eduCredits==0}">
                <p class="min-size">本次会议需支付象数：<span class="color-blue">${meet.meetProperty.xsCredits}</span></p>
                </c:when>
                <c:otherwise>
                    <p class="min-size">本次会议奖励象数：<span class="color-blue">${meet.meetProperty.xsCredits}</span>，还有 <span class="color-blue">${attendCount>meet.meetProperty.awardLimit?0:meet.meetProperty.awardLimit-attendCount}</span> 人能够获得奖励</p>
                </c:otherwise>
                </c:choose>

                <p><span>${meet.meetType}</span></p>
            </div>
            <div class="YaYa-metting-intro clearfix">
                <div class="fl">
                    <img class="img-radius"
                    <c:choose>
                    <c:when test="${empty pubUserHeadImg}">
                         src="${ctxStatic}/images/hz-detail-img-info.jpg"
                    </c:when>
                    <c:otherwise>
                         src="${pubUserHeadImg}"
                    </c:otherwise>
                    </c:choose>
                         alt="">
                </div>
                <div class="oh">
                    <h4>${linkman}</h4>
                    <p class="color-gary">主办方</p>
                </div>
            </div>
            <div class="YaYa-split cleafix"></div>
            <div class="YaYa-metting-detail">
                <div class="YaYa-metting-title">
                    <h3>会议简介</h3>
                </div>
                <div class="YaYa-metting-author clearfix">
                    <div class="fl">
                        <img
                        <c:choose>
                        <c:when test="${empty meet.lecturer.headimg}">
                                src="${ctxStatic}/images/hz-detail-img-info.jpg"
                        </c:when>
                        <c:otherwise>
                                src="${appFileBase}${meet.lecturer.headimg}"
                        </c:otherwise>
                        </c:choose>
                                alt="">
                    </div>
                    <div class="oh">
                        <h4>${meet.lecturer.name}&nbsp;&nbsp;${meet.lecturer.title}</h4>
                        <p>${meet.lecturer.hospital}</p>
                    </div>
                </div>
                <div class="YaYa-metting-detail-main">
                    <p>${meet.introduction}</p>

                </div>
            </div>
            <div class="YaYa-footer">
                <div class="YaYa-erweima ">
                    <p class="t-center"><img src="${ctxStatic}/images/QRcode.png" alt=""></p>
                    <p class="t-center">扫一扫，下载YaYa医师App</p>
                </div>
                <div class="YaYa-bottom ">
                    <p class="t-center">广州敬信药草园信息科技有限公司</p>
                    <p class="t-center"><a href="www.medcn.com" target="_blank">www.medcn.com</a></p>
                </div>
            </div>
        </div>
    </class>
</body>
</html>
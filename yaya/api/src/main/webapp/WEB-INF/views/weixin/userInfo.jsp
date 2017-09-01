<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/7/28
  Time: 9:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>个人中心</title>
    <%@include file="/WEB-INF/include/page_context_weixin.jsp"%>

    <script src="${ctxStatic}/js/layer/layer.js"></script>
</head>
<body class="gary-reg">

<div class="warp ">

    <div class="item">
        <div class="user-details">
            <div class="user-header">
                <div class="user-edit"><a href="${ctx}/weixin/user/modify" class="icon-user-edit"></a></div>
                <div class="user-img">
                    <img src="${appFileBase}${appUser.headimg}" alt="">
                </div>
                <div class="user-title">
                    <h2>${appUser.nickname}</h2>
                </div>
                <div class="user-num">${userDetail.unitName}</div>
                <div class="user-label"><span class="user-label-box">${userDetail.title}</span><span class="user-label-box">${userDetail.specialtyName}</span></div>
            </div>
            <div class="user-margin-top user-intro">
                <h3>学术专长</h3>
                <p>${empty userDetail.major ? "未设置":userDetail.major}</p>
            </div>
            <div class="user-margin-top user-list">
                <ul>
                    <li><span class="rowTitle">就职医院</span><span class="rowMain">${userDetail.unitName}</span></li>
                    <li class="last overflowText"><span class="rowTitle">所在地区</span><span class="rowMain ">${appUser.province}&nbsp;${appUser.city}&nbsp;${appUser.zone}</span></li>
                </ul>
            </div>
        </div>
    </div>


</div>
</body>

</html>

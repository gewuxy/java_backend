<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/20
  Time: 9:33
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

    <title>更多</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">


    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
</head>
<body style="background-color: white;">
<div class="layer-hospital-popup-main ">
    <div class="more-popup-list clearfix">
        <ul id="more_popup_ul">
            <li>
                <a href="javascript:;">
                    <img src="${ctxStatic}/images/_wechat-icon.png" alt="">
                    <p>微信好友</p>
                </a>
            </li>
            <li>
                <a href="javascript:;">
                    <img src="${ctxStatic}/images/_friends-icon.png" alt="">
                    <p>朋友圈</p>
                </a>
            </li>
            <li>
                <a href="javascript:;">
                    <img src="${ctxStatic}/images/_weibo-icon.png" alt="">
                    <p>微博</p>
                </a>
            </li>
            <li>
                <a href="javascript:;">
                    <img src="${ctxStatic}/images/_twitter-icon.png" alt="">
                    <p>Twitter</p>
                </a>
            </li>
            <li>
                <a href="javascript:;">
                    <img src="${ctxStatic}/images/_facebook-icon.png" alt="">
                    <p>Facebook</p>
                </a>
            </li>
            <li>
                <a href="javascript:;">
                    <img src="${ctxStatic}/images/_copyLink-icon.png" alt="">
                    <p>复制链接</p>
                </a>
            </li>
            <li>
                <a href="javascript:;" class="copy-hook">
                    <img src="${ctxStatic}/images/_copy-icon.png" alt="">
                    <p>复制副本</p>
                </a>
            </li>
            <li>
                <a>
                    <img src="${ctxStatic}/images/_edit-icon.png" alt="">
                    <p>编辑</p>
                </a>
            </li>
            <li>
                <a href="javascript:;">
                    <img src="${ctxStatic}/images/_delete-icon.png" alt="">
                    <p>删除</p>
                </a>
            </li>
        </ul>
    </div>
</div>

</body>
</html>

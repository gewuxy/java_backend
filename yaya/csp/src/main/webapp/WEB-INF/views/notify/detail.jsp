<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>通知详情-会讲</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <style>
        html,body { background-color:#F7F9FB;}
    </style>
</head>
<body >
<%@include file="../include/header.jsp" %>
    <div id="wrapper">
        <div class="admin-content bg-gray" >
            <div class="page-width clearfix">
                <div class="subPage-head item-shadow item-radius clearfix">
                    <h3 class="title"><i class="icon icon-header-message"></i>通知</h3>
                </div>
                <div class="subPage-main item-shadow item-radius" >
                    <div class="message-detail">
                        <div class="message-detail-crumbs ">
                            <a href="${ctx}/mgr/message/list" class="color-gray">通知</a> <i class="rowSpace">&gt;</i> <span>正文</span>
                        </div>
                        <div class="message-detail-title">
                            <h1>${notify.title}</h1>
                        </div>
                        <div class="message-detail-main">
                            <p>&nbsp;&nbsp;&nbsp;&nbsp;${notify.content}</p>
                            <p>${notify.senderName}<br /><fmt:formatDate value="${notify.sendTime}" pattern="yyyy年MM月dd日"></fmt:formatDate></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../include/footer.jsp"%>
    </div>
</body>
</html>
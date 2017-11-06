<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>通知 - 详情</title>
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
<%@include file="/WEB-INF/include/header_zh_CN.jsp" %>
    <div id="wrapper">
        <div class="admin-content bg-gray" >
            <div class="page-width clearfix">
                <div class="subPage-head item-shadow item-radius clearfix">
                    <h3 class="title"><i class="icon icon-header-message"></i>Message</h3>
                </div>
                <div class="subPage-main item-shadow item-radius" >
                    <div class="message-detail">
                        <div class="message-detail-crumbs ">
                            <a href="${ctx}/mgr/message/list" class="color-gray">Message</a> <i class="rowSpace">&gt;</i> <span>Content</span>
                        </div>
                        <div class="message-detail-title">
                            <h1>${notify.title}</h1>
                            <div class="message-detail-info">
                                <span class="color-gray">${notify.sendTimeStr} </span>
                            </div>
                        </div>
                        <div class="message-detail-main">
                            <p>&nbsp;&nbsp;&nbsp;&nbsp;${notify.content}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="/WEB-INF/include/footer_zh_CN.jsp"%>
    </div>


    <script>
        $(function(){


        })
    </script>
</body>
</html>
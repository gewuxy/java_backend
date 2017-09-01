<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/8/11
  Time: 9:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>群发通知-已发送成功</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
</head>
<body>
<div class="g-main clearfix">
    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-02.png" alt="" />
                </div>
                <div class="oh">
                    <p><strong>群发消息</strong></p>
                    <p>重要消息网站直接推送到手机app，不错过每一条通知</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->

    <div class="tab-hd">
        <ul class="tab-list clearfix">
            <li class="cur">
                <a href="${ctx}/func/msg/add">群发消息<i></i></a>
            </li>
            <li >
                <a href="${ctx}/func/msg/list">已发送<i></i></a>
            </li>
        </ul>
    </div>

    <div class="tab-bd">

        <div class="table-box-div1 ">
            <div class="normalBoxItem">
                <p><img src="${ctxStatic}/images/icon-send-1.png" alt=""></p>
                <p style="font-size:22px; color:#45ccce;">已发送消息</p>
                <div class="formControls t-center" style="margin-top:60px;">
                    <a  class="formButton formButton-max formButtonBlue-02" onclick="window.history.back()">返回继续</a>
                </div>
            </div>

        </div>
    </div>
</div>

</body>
</html>

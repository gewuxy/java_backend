<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/17
  Time: 9:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>会议管理</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">


    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
</head>
<body>
<div id="wrapper">
    <%@include file="/WEB-INF/include/header_zh_CN.jsp" %>
    <div class="admin-content bg-gray">
        <div class="page-width clearfix">
            <div class="admin-row clearfix pr">
                <div class="admin-screen-area">
                    <ul>
                        <li class="first cur"><a href="javascript:;" class="screen-all ">全部</a></li>
                        <li><a href="javascript:;" class="screen-viedo"><i></i>投屏录播</a></li>
                        <li><a href="javascript:;" class="screen-live"><i></i>投屏直播</a></li>
                        <li class="last"><a href="javascript:;" class="screen-time"><i></i>创建时间排序</a></li>
                    </ul>
                </div>
                <div class="admin-search">
                    <form action="" method="post" id="yPsearchForm" target="_blank" name="yPsearchForm">
                        <div class="search-form search-form-responsive item-radius clearfix">
                            <input type="text" placeholder="搜索会议名字" name="ypname" id="ypname" class="form-text">
                            <button type="submit" class="form-btn"><span></span></button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="admin-metting-list">
                <div class="row clearfix">
                    <c:forEach items="${page.dataList}" var="course">
                        <div class="col-lg-4">

                            <div class="resource-list-item item-radius clearfix">
                                <div class="resource-img ">
                                    <img src="${ctxStatic}/upload/img/_admin_metting_01.png" alt="" class="img-response">
                                    <div class="resource-link">
                                        <a href="#" class="resource-icon-play popup-player-hook">
                                            <i></i>
                                            预览
                                        </a><a href="${ctx}/mgr/meet/screen/${course.id}" target="_blank" class="resource-icon-qrcode">
                                        <i></i>
                                        扫码投屏
                                    </a>
                                    </div>
                                </div>
                                <h3 class="resource-title overflowText">${course.title}</h3>
                                <div class="resource-label">
                                    <span><i class="hot">3</i><i class="muted">|</i>12</span>
                                    <span>03'26''</span>
                                    <span>录播</span>
                                </div>
                                <div class="resource-menu">
                                    <div class="col-lg-6">
                                        <a href="javascript:;" class="contribute-hook">投稿</a>
                                    </div>
                                    <div class="col-lg-6">
                                        <a href="javascript:;" class="more more-hook"><i></i>更多</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </div>

            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/include/footer_zh_CN.jsp"%>
</div>
</body>
</html>

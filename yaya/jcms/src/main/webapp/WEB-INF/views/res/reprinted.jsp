<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/25
  Time: 11:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>被转载资源</title>
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
                    <img src="${ctxStatic}/images/subPage-header-image-03.png" alt="">
                </div>
                <div class="oh">
                    <p><strong>资源平台</strong></p>
                    <p>医学会议、科教培训、医学资料跨医院共享的学术共享平台</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->

    <div class="tab-hd">

        <ul class="tab-list clearfix">
            <li>
                <a href="${ctx}/func/res/list">资源转载<i></i></a>
            </li>
            <li class="cur">
                <a >我的管理<i></i></a>
            </li>
        </ul>
    </div>
    <div class="tab-bd">
        <div class="table-box-div1 mar-btm-1">
            <div class="table-top-box clearfix">
                <a href="${ctx}/func/res/reprints" class="mask-le"><span class="icon iconfont icon-minIcon3"></span>已转载资源</a>
                <a href="${ctx}/func/res/shared" class="mask-le "><span class="icon iconfont icon-minIcon8"></span>我的资源分享</a>
                <a class="mask-le cur"><span class="icon iconfont icon-minIcon8"></span>被转载记录</a>

                <form action="${ctx}/func/res/reprinted" method="post">
                    <span class="search-box">
                        <input type="text" class="sear-txt" name="keyword" value="${keyword}" placeholder="搜索">
                        <button class="sear-button"></button>
                    </span>
                </form>
            </div>

            <table class="table-box-3">
                <thead>
                <tr>
                    <td class="tb-w-1">单位号</td>
                    <td class="tb-w-3">转载时间</td>
                    <td class="tb-w-3">资源名称</td>
                    <%--<td class="tb-w-2 ">统计</td>--%>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.dataList}" var="res">
                    <tr>
                        <td class="tb-w-1"><img src="${ctxStatic}/img/_link-01.png" alt="">&nbsp;&nbsp;<span>${res.pubUserName}</span></td>
                        <td class="tb-w-3"><fmt:formatDate value="${res.createTime}" pattern="yyyy年MM月dd日 HH:mm"/></td>
                        <td class="tb-w-3">
                            <a >${res.title}</a>
                        </td>
                        <td class="tb-w-2 ">
                            <%--<a class="fx-btn-2 zai" href="detail-3.html"><span class="icon iconfont icon-minIcon5"></span>转载统计</a>--%>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
            <%@include file="/WEB-INF/include/pageable.jsp"%>
        </div>
    </div>
</div>
<form id="pageForm" name="pageForm" action="${ctx}/func/res/reprinted" method="post">
    <input type="hidden" name="pageNum" id="pageNum" value="${page.pageNum}">
    <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
    <input type="hidden" name="keyword" value="${keyword}">
</form>
<script src="${ctxStatic}/js/slide.js"></script>
</body>
</html>

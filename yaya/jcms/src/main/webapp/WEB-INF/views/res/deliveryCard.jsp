<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <title> 资源平台 - CSP投屏 </title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.min.css" />
    <link rel="stylesheet" href="${ctxStatic}/css/audio-black.css">
</head>
<body>

<!-- main -->
<div class="g-main clearfix">
    <!-- header -->
    <%@include file="resHeader.jsp" %>
    <!-- header end -->
    <div class="tab-bd">
        <div class="table-box-div1 mar-btm-1 meeting-contribute">
            <div class="table-top-box clearfix">
                <a href="#" class="changeListButton"><i class="icon-card "></i><i class="icon-list none"></i></a>
                <a href="#" id="exportBtn" class="mask-le cur">导出Excel投稿历史</a>
                <span class="search-box">
                     <form id="searchForm" action="${ctx}/func/res/list" method="post">
                        <input type="text" name="keyWord" class="sear-txt" placeholder="请输入会议名称或讲者姓名" value="${keyWord}">
                        <input type="hidden" name="isOpen" value="1">
                         <input type="hidden" name="viewType" value="${viewType}">
                        <input type="button" class="sear-button" onclick="submit()">
                     </form>
				</span>
                <div class="formrow t-right icon-fixed">
					<span class="checkboxIcon">
						<input type="checkbox" id="popup_checkbox_2"
                        <c:if test="${flag == 1}"> checked </c:if> class="chk_1 chk-hook">
						<label for="popup_checkbox_2" class="popup_checkbox_hook"><i class="ico"></i>&nbsp;&nbsp;开启CSPmeeting来稿功能</label>
					</span>&nbsp;
                    <span class="question-tipsHover-hook">
                        <img src="${ctxStatic}/images/icon-question.png" alt="">
                    </span>
                </div>
            </div>
            <div class="resource-list clearfix" id="deliveryList">
                <div class="row clearfix">
                    <c:forEach items="${page.dataList}" var="d">
                        <div class="col-lg-response">
                            <div class="resource-list-box">
                                <div class="resource-list-item">
                                    <div class="resource-img ">
                                        <img src="${d.coverUrl}" alt="" class="img-response">
                                        <div class="resource-link">
                                            <a href="#" class="resource-icon-play popup-player-hook" courseId="${d.id}">
                                                <i></i>
                                                预览
                                            </a><a href="${ctx}/func/meet/edit?courseId=${d.id}" class="resource-icon-edit">
                                            <i></i>
                                            立即发布
                                        </a>
                                        </div>
                                        <c:if test="${d.playType == 2}">
                                            <div class="resource-state"><span class="icon iconfont icon-minIcon26"></span></div>
                                        </c:if>
                                        <c:if test="${d.starRateFlag == true}">
                                            <div class="grade-state star-hook" courseId="${d.id}"><span class="icon-grade-star"><i></i>${d.score}&nbsp;分</span></div>
                                        </c:if>
                                        <c:if test="${d.starRateFlag == false}">
                                            <div class="grade-state info-hook" courseId="${d.id}"><span class="icon-grade-info"></span></div>
                                        </c:if>
                                    </div>
                                    <div class="resource-info">
                                        <div class="fl">
                                            <img src="${d.avatar}" alt="">
                                        </div>
                                        <div class="oh">
                                            <div class="row clearfix">
                                                <div class="col-lg-10">
                                                    <c:if test="${empty d.name}">
                                                        <h3>&nbsp;</h3>
                                                    </c:if>
                                                    <c:if test="${not empty d.name}">
                                                        <h3>${d.name}</h3>
                                                    </c:if>
                                                    <c:if test="${empty d.email}">
                                                        <p>&nbsp;</p>
                                                    </c:if>
                                                    <c:if test="${not empty d.email}">
                                                        <p>${d.email}</p>
                                                    </c:if>
                                                </div>
                                                <c:if test="${d.playType != 0}">
                                                    <div class="col-lg-2">
                                                        <div class="state">直播</div>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <%@include file="/WEB-INF/include/pageable.jsp" %>
        </div>
    </div>
</div>
<form id="pageForm" name="pageForm" action="${ctx}/func/res/list" method="post">
    <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
    <input type="hidden" name="pageNum" id="pageNum" <c:if test="${page.pages>1}">value="${page.pageNum}"</c:if>>
    <input type="hidden" name="keyWord" value="${keyWord}">
    <input type="hidden" name="pages" id="pages" value="${page.pages}">
    <input type="hidden" name="viewType" id="viewType" value="${viewType}">
    <input type="hidden" name="isOpen" id="isOpen" value="1">
</form>

<script src="${ctxStatic}/js/jquery.min.js"></script>
<script src="${ctxStatic}/js/slide.js"></script>
<script src="${ctxStatic}/js/swiper.jquery.min.js"></script>
<script src="${ctxStatic}/js/audio.js"></script>
<script src="${ctxStatic}/js/layer/layer.js"></script>
<script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
<script src="${ctxStatic}/js/screenfull.min.js"></script>
</body>
</html>
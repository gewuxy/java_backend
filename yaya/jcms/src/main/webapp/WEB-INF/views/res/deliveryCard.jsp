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
                <a href="#" class="changeListButton"><i class="icon-card none"></i><i class="icon-list"></i></a>
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
            <table class="table-box-1 star-table-list" id="deliveryList">
                <colgroup>
                    <col class="table-td-9">
                    <col class="table-td-6">
                    <col class="table-td-2">
                    <col class="table-td-4">
                    <col class="table-td-5">
                </colgroup>
                <tbody>
                <c:forEach items="${page.dataList}" var="d">
                    <tr>
                        <td class="table-td-9">
                            <c:if test="${d.starRateFlag == true}">
                                <div class="star"><span class="full"></span></div><div class="grade ">${d.score}&nbsp;分</div>
                            </c:if>
                            <c:if test="${d.starRateFlag == false}">
                                <span class="star-info info-hook"></span>
                            </c:if>
                        </td>
                        <td class="table-td-6">
                            <a href="">${d.title}</a>
                        </td>
                        <td class="table-td-2">
                            <span>${d.category}</span>
                        </td>
                        <td class="table-td-4 tb-popupBox-img">
							<span class="img">
								<img src="${ctxStatic}/img/hz-detail-img-info.jpg" class="img-radius" width="32" height="32" alt="">
							</span>
                            <span class="text">${d.category}</span>
                        </td>
                        <td class="table-td-5">
                            <a href="javascript:;" class="popup-player-hook" courseId="${d.id}">预览</a><i class="rowSpace">|</i><a href="${ctx}/func/meet/edit?courseId=${d.id}">立即发布</a>
                        </td>
                </c:forEach>
                </tbody>
            </table>
            <%@include file="/WEB-INF/include/pageable.jsp" %>
        </div>
    </div>
</div>
<form id="pageForm" name="pageForm" action="${ctx}/func/res/list" method="post">
    <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
    <input type="hidden" name="pageNum" id="pageNum" value="<c:if test="${page.pages>1}"> ${page.pageNum}</c:if>">
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

<script>
    $(function(){
        //上传Hover 提示
        $(".question-tipsHover-hook").mouseenter(function(){
            layer.tips('CSPmeeting是全球领先的云同步回放会议系统，开通该功能可接收来自全球各地用户的学术会议投稿。', '.question-tipsHover-hook', {
                tips: [3, '#333'],
                time:2000
            });
        });

        $("#popup_checkbox_2").click(function(){
            var flag = 0;
            if($('#popup_checkbox_2').is(':checked')){
                flag = 1;
            }
            $.ajax({
                //服务器的地址
                url:"${ctx}/func/res/change?flag="+flag,
                dataType:'json', //返回数据类型
                type:'POST', //请求类型
                contentType: "application/json; charset=utf-8",
                success: function(data) {
                    if(data.code == 0){
                       if(flag == 0){
                           //关闭投稿
                           $("#deliveryList").empty();
                           $(".page-box").empty();
                       }else{
                           window.location.href='${ctx}/func/res/list?isOpen='+flag;
                       }
                    }
                }
            });
        });
    });
</script>
</body>
</html>
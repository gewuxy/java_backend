<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/17
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> 会议资源 - 共享资源 </title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.min.css" />
    <link rel="stylesheet" href="${ctxStatic}/css/audio-black.css">


    <script>
        var resId = 0;
        $(function(){
            if("${err}"){
                layer.msg("${err}");
            }

            $("#categoryList>a").click(function(){
                var category = $(this).attr("category");
                $("#pageNum").val(1);
                $("#category").val(category);
                $("#pageForm").submit();
            });

            // 点击获取按钮
            $(".fx-btn-3").click(function(){
                resId = $(this).attr("resId");
                openReprint($(this).attr("shareType"), $(this).attr("credits"));
            });

            $(".close-btn-fx,.close-button-fx").click(function(){
                closeDialog();
            });

            // 点击确认按钮
            $("#reprintBtn").click(function(){
                closeDialog();
                window.location.href = '${ctx}/func/res/reprint?id='+resId;
            });

            // 点击预览按钮
            $(".popup-player-hook").click(function(){
                var courseId = $(this).attr("courseId");
                layer.open({
                    type:2,
                    area: ['860px', '800px'],
                    fix: false, //不固定
                    fixed:true,
                    offset: '100px',
                    title:false,
                    content:'${ctx}/func/res/view?courseId='+courseId
                });
            });

            // 点击充值按钮
            $("#charge").click(function () {
                window.open("${ctx}/mng/account/xsInfo");
                // 切换 是否充值成功的提示框
                closeDialog();
                showChargeDialog();
            });
            // 关闭充值弹框
            $("#rechargeBtn,#rechargeOK,#rechargeFail").click(function(){
                closeChargeDialog();
            });
        });

        // 点击获取按钮 弹出提示框内容
        function openReprint(shareType, credits){
            $("#leftCredits").text('${credit}');
            if(shareType == 1){
                $("#requiredPrefix").text("转载该会议需要支付");
                $("#requiredCredits").text(credits+"象数");
            }else if(shareType == 2){
                $("#requiredPrefix").text("转载该会议奖励");
                $("#requiredCredits").text(credits+"象数");
            }else{
                $("#requiredPrefix").text("可免费转载该会议");
                $("#requiredCredits").text("免费转载");
            }
            if(shareType == 1 && credits > parseInt('${credit}')){
                $("#reprintBtn").hide();
            }else{
                $("#reprintBtn").show();
            }
            $('.mask-wrap').addClass('dis-table');
            $('.fx-mask-box-3').show();
        }

        function closeDialog(){
            $("#reprintBtn").show();
            $('.fx-mask-box-3').hide();
            $('.mask-wrap').removeClass('dis-table');
        }

        function showChargeDialog() {
            $('.mask-wrap').addClass('dis-table');
            $('.fx-mask-box-5').show();
        }

        function closeChargeDialog() {
            $('.mask-wrap').removeClass('dis-table');
            $('.fx-mask-box-5').hide();
        }
    </script>
</head>

<body>
    <!-- main -->
    <div class="g-main clearfix">
        <!-- header -->
        <%@include file="resHeader.jsp"%>
        <div class="tab-bd">
            <div class="table-box-div1 mar-btm-1">
                <div class="table-top-box clearfix">
                    <a href="${ctx}/func/res/share/list" class="mask-le cur"><span class="icon iconfont icon-minIcon3"></span>共享资源库</a>
                    <a href="${ctx}/func/res/acquired/list" class="mask-le "><span class="icon iconfont icon-minIcon8"></span>已获取</a>
                    <form action="${ctx}/func/res/share/list" method="post">
                        <span class="search-box">
                            <input type="text" class="sear-txt" name="keyword" value="${keyword}" placeholder="搜索关键字">
                            <input type="hidden" name="category" value="${category}">
                            <button class="sear-button"></button>
                        </span>
                    </form>
                </div>
                <div class="sd-list bg-change" id="categoryList">
                    <a category="" class="${empty category?'cur':''}" style="cursor: pointer;">全部</a>
                    <c:forEach items="${categoryList}" var="cate">
                        <a category="${cate.name}"  class="${category eq cate.name?'cur':''}"  style="cursor: pointer;" >${cate.name}<c:if test="${not empty cate.name}"></c:if></a>
                    </c:forEach>
                </div>
                <div class="resource-list clearfix">
                    <div class="row clearfix">
                    <c:choose>
                        <c:when test="${page.dataList != null && page.dataList.size()!=0}">
                        <c:forEach items="${page.dataList}" var="res">
                            <div class="col-lg-response">
                                <div class="resource-list-box">
                                    <div class="resource-list-item">
                                        <div class="resource-img ">
                                            <img src="${res.coverUrl}" alt="" class="img-response">
                                            <div class="resource-link">
                                                <a href="javascript:;" class="resource-icon-play popup-player-hook" courseId="${res.id}">
                                                    <i></i>预览
                                                </a><a style="cursor: pointer;"  class="resource-icon-download fx-btn-3" resId="${res.id}" credits="${res.credits}" shareType="${res.shareType}" >
                                                <i></i>
                                                获取</a>
                                            </div>
                                            <span></span>
                                        </div>
                                        <div class="resource-info">
                                            <h3 class="overflowText">${res.title}</h3>
                                            <p>${res.category}
                                                <c:choose>
                                                    <c:when test="${res.shareType == 1}">
                                                        <i class="rowSpace">|</i>
                                                        <span class="color-green-up">支付象数：${res.credits}</span>
                                                    </c:when>
                                                    <c:when test="${res.shareType == 2}">
                                                        <i class="rowSpace">|</i>
                                                        <span class="color-yellow-up">奖励象数：${res.credits}</span>
                                                    </c:when>
                                                </c:choose>
                                            </p>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="normalBoxItem">
                                <p><img src="${ctxStatic}/images/not-search.png" alt=""></p>
                                <p style="color:#acacac; font-size:16px;">搜索不到结果</p>
                            </div>
                        </c:otherwise>

                    </c:choose>

                    </div>
                </div>

                <%@include file="/WEB-INF/include/pageable.jsp"%>
        </div>
    </div>
    <!-- main end-->
    <form id="pageForm" name="pageForm" action="${ctx}/func/res/share/list" method="post">
        <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageNum" id="pageNum">
        <input type="hidden" name="keyword" value="${keyword}">
        <input type="hidden" name="category" id="category" value="${category}">
    </form>

    <div class="mask-wrap">
        <div class="dis-tbcell">
            <div class="distb-box fx-mask-box-3">
                <div class="mask-hd clearfix">
                    <h3 class="font-size-1">温馨提示</h3>
                    <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
                </div>
                <div class="mask-share-box">
                    <p class="top-txt color-black" id="requiredPrefix">转载该会议需要支付 <span class="color-blue-up" id="requiredCredits">5000象数</span>，是否立即支付？</p>
                    <p>账户剩余<span class="color-black" id="leftCredits">350象数</span>，
                        可继续<a href="javascript:;" class="c-3  fx-btn-5" id="charge">充值</a>增加象数值</p>
                </div>
                <div class="sb-btn-box p-btm-1 t-right">
                    <button class="close-button-fx cur" id="reprintBtn">确认</button>
                    <button class="close-button-fx" id="cancelBtn">取消</button>
                </div>
            </div>

            <div class="distb-box fx-mask-box-5">
                <div class="mask-hd clearfix">
                    <h3 class="font-size-1">温馨提示</h3>
                    <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png" id="rechargeBtn"></span>
                </div>
                <div class="mask-share-box">
                    <p class="top-txt color-black">充值是否成功</p>
                    <p>如未显示相应充值的象数，<span class="color-black">先关闭窗口后，再点击刷新按钮</span></p>
                </div>
                <div class="sb-btn-box p-btm-1 t-right">
                    <button class="close-button-fx cur" id="rechargeOK">充值成功</button>
                    <button class="close-button-fx" id="rechargeFail">充值失败</button>
                </div>
            </div>
        </div>
    </div>


    <script src="${ctxStatic}/js/jquery.min.js"></script>
    <script src="${ctxStatic}/js/slide.js"></script>
    <script src="${ctxStatic}/js/swiper.jquery.min.js"></script>
    <script src="${ctxStatic}/js/audio.js"></script>
    <script src="${ctxStatic}/js/layer/layer.js"></script>
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="${ctxStatic}/js/screenfull.min.js"></script>
    <script src="${ctxStatic}/js/main.js"></script>
    <script src="${ctxStatic}/js/popupAudioPalyer.js"></script>

</body>
</html>

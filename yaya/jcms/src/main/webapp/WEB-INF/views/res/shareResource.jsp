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
    <script src="${ctxStatic}/js/slide.js"></script>

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
                // TODO 需完善预览方法
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
                    <a href="${ctx}/func/res/share/list" class="mask-le "><span class="icon iconfont icon-minIcon8"></span>已获取</a>
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
                    <c:forEach items="${page.dataList}" var="res">
                        <div class="col-lg-response">
                            <div class="resource-list-box">
                                <div class="resource-list-item">
                                    <div class="resource-img ">
                                        <%--<img src="${res.coverUrl}" alt="" class="img-response">--%>
                                        <img src="${ctxStatic}/img/_resource-img-01.jpg" alt="" class="img-response">
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
                                        <h3>${res.title}</h3>
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
                        可继续<a href="${ctx}/mng/account/xsInfo" class="c-3  fx-btn-5">充值</a>增加象数值</p>
                </div>
                <div class="sb-btn-box p-btm-1 t-right">
                    <button class="close-button-fx cur" id="reprintBtn">确认</button>
                    <button class="close-button-fx" id="cancelBtn">取消</button>
                </div>
            </div>

            <div class="distb-box fx-mask-box-5">
                <div class="mask-hd clearfix">
                    <h3 class="font-size-1">温馨提示</h3>
                    <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
                </div>
                <div class="mask-share-box">
                    <p class="top-txt color-black">充值是否成功</p>
                    <p>如未显示相应充值的象数，<span class="color-black">先关闭窗口后，再点击刷新按钮</span></p>
                </div>
                <div class="sb-btn-box p-btm-1 t-right">
                    <button class="close-button-fx cur">充值成功</button>
                    <button class="close-button-fx">充值失败</button>
                </div>
            </div>
        </div>
    </div>

    <!--弹出播放器-->
    <div class="layer-hospital-popup layer-black  layer-hospital-popup-hook">
        <div class="swiperBox mettingSwiperBox clearfix">
            <!--预览弹出层-->
            <div class="metting-swiper">
                <!-- Swiper -->
                <div class="swiper-container swiper-container-horizontal swiper-container-hook">
                    <!--根据ID 切换 PPT列表-->
                    <div class="swiper-wrapper" >
                        <div class="swiper-slide swiper-slide-active" data-num="0" audio-src="./upload/audio/30179313.mp3">
                            <div class="swiper-picture" style=" background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div>
                        </div>
                        <div class="swiper-slide swiper-slide-next" data-num="1"  audio-src="https://raw.githubusercontent.com/kolber/audiojs/master/mp3/bensound-dubstep.mp3">
                            <div class="swiper-picture" style="background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div>
                        </div>
                        <div class="swiper-slide" data-num="2" audio-src="./upload/audio/30179313.mp3"><div class="swiper-picture" style="display:block; background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div><div class="swiper-slide-metting-audio"></div></div>
                        <div class="swiper-slide" data-num="3" audio-src="./upload/audio/30179313.mp3"><div class="swiper-picture" style="display:block; background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div><div class="swiper-slide-metting-audio"></div></div>
                    </div>
                    <!-- Add Pagination -->

                    <div class="metting-btn-item clearfix">
                        <span class="swiper-button-prev swiper-popup-button-prev-hook metting-button swiper-button-disabled"></span>
                        <div class="swiper-pagination swiper-pagination-fraction"><span class="swiper-pagination-current">1</span> / <span class="swiper-pagination-total">4</span></div>
                        <span class="swiper-button-next swiper-popup-button-next-hook metting-button"></span>
                    </div>

                </div>
            </div>
            <!--音频文件-->
            <div class="clearfix boxAudio t-center" >
                <div class="audio-metting-box" style="">
                    <audio controls=true id="video1" src=""></audio>
                </div>
            </div>
            <!--切换全屏按钮-->
            <button class="swiper-changeFullSize-button changeFullSize-icon changeFull-hook" title="全屏观看"><span></span></button>

            <!--标题栏-->
            <div class="swiper-slide-title">
                <a href="#" class="fr">立即发布</a>
                <div class="title overflowText">赛默飞联合阜外医院分子诊断中心安塞物共建精诊断中心安塞物共建精诊断中赛默飞联合阜外医院分子诊断中心安塞物共建精诊断中心安塞物共建精诊断中</div>
            </div>

        </div>
    </div>

    <div class="layer-hospital-popup layer-black  layer-hospital-popup-fullSize">
        <div class="swiperBox mettingSwiperBox clearfix">
            <!--预览弹出层-->
            <div class="metting-swiper">
                <!-- Swiper -->
                <div class="swiper-container swiper-container-horizontal swiper-full-container-hook">
                    <!--根据ID 切换 PPT列表-->
                    <div class="swiper-wrapper" >
                        <div class="swiper-slide swiper-slide-active" data-num="0" audio-src="./upload/audio/30179313.mp3">
                            <div class="swiper-picture" style=" background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div>
                        </div>
                        <div class="swiper-slide swiper-slide-next" data-num="1"  audio-src="https://raw.githubusercontent.com/kolber/audiojs/master/mp3/bensound-dubstep.mp3">
                            <div class="swiper-picture" style="background-image:url('./upload/images/_hyfb-03-1-img.jpg"></div>
                        </div>
                        <div class="swiper-slide" data-num="2" audio-src="./upload/audio/30179313.mp3"><div class="swiper-picture" style="display:block; background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div><div class="swiper-slide-metting-audio"></div></div>
                        <div class="swiper-slide" data-num="3" audio-src="./upload/audio/30179313.mp3"><div class="swiper-picture" style="display:block; background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div><div class="swiper-slide-metting-audio"></div></div>
                    </div>
                    <!-- Add Pagination -->
                    <span class="swiper-button-prev metting-button swiper-popup-full-button-prev swiper-button-disabled"></span>
                    <div class="metting-btn-item clearfix">
                        <div class="swiper-pagination swiper-pagination-fraction"><span class="swiper-pagination-current">1</span> / <span class="swiper-pagination-total">4</span></div>
                    </div>
                    <span class="swiper-button-next metting-button swiper-popup-full-button-next"></span>

                </div>
            </div>
            <!--音频文件-->
            <div class="clearfix boxAudio t-center" >
                <div class="audio-metting-box" style="">
                    <audio controls=true id="video2" src=""></audio>
                </div>
            </div>
            <!--切换全屏按钮-->
            <button class="swiper-changeFullSize-button changeFullSize-icon changeFull-hook" title="全屏观看"><span></span></button>
            <!--标题栏-->
            <div class="swiper-slide-title">
                <a href="#" class="fr">立即发布</a>
                <div class="title overflowText">赛默飞联合阜外医院分子诊断中心安塞物共建精诊断中心安塞物共建精诊断中赛默飞联合阜外医院分子诊断中心安塞物共建精诊断中心安塞物共建精诊断中</div>
            </div>
        </div>
    </div>

    <script src="js/jquery.min.js"></script>
    <script src="js/slide.js"></script>
    <script src="js/swiper.jquery.min.js"></script>
    <script src="js/audio.js"></script>
    <script src="js/layer/layer.js"></script>
    <script src="js/perfect-scrollbar.jquery.min.js"></script>
    <script src="js/screenfull.min.js"></script>
    <script src="js/main.js"></script>
    <script src="js/popupAudioPalyer.js"></script>

</body>
</html>

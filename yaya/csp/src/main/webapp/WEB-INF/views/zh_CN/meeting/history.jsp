<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>投稿历史</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.css">
    <link rel="stylesheet" href="${ctxStatic}/css/audio.css">
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">

    <script src="${ctxStatic}/js/audio.js"></script>
    <script src="${ctxStatic}/js/swiper.jquery.js"></script>
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
</head>
<body>
<div id="wrapper">
    <%@include file="/WEB-INF/include/header_zh_CN.jsp" %>
    <c:if test="${fn:length(acceptList) != 0}" >
        <div class="admin-content bg-gray">
            <div class="page-width clearfix">
                <div class="admin-history-list item-radius ">
                    <div class="row clearfix">
                        <div class="col-lg-4">
                            <div class="admin-history-tabList">
                                <ul>
                                    <c:forEach items="${acceptList}" var="accept" varStatus="status">
                                        <li <c:if test="${status.index == 0}"> class="current" </c:if> <c:if test="${status.end}"> class="last" </c:if>>
                                            <a href="${ctx}/mgr/delivery/history?pageNum=1&pageSize=${page.pageSize}&acceptId=${accept.id}" >
                                                <div class="clearfix">
                                                    <div class="fl">
                                                        <img src="${accept.headimg}" alt="" height="30" width="30">
                                                    </div>
                                                    <div class="oh">
                                                        <h4>${accept.nickname}</h4>
                                                        <p>${accept.sign}</p>
                                                    </div>
                                                </div>
                                            </a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                        <div class="col-lg-8 ">
                            <c:forEach items="${page.dataList}" varStatus="status" step="2">
                                <div class="row clearfix">
                                    <c:forEach items="${page.dataList}"  var="meet" begin="${status.index}" end="${status.index+1}">
                                        <div class="col-lg-6">
                                            <div class="resource-list-item item-radius clearfix">
                                                <div class="resource-img ">
                                                    <img src="${meet.coverUrl}" alt="" class="img-response">
                                                    <div class="resource-link">
                                                        <a href="#" class="resource-icon-play popup-player-hook">
                                                            <i></i>
                                                            预览
                                                        </a>
                                                    </div>
                                                </div>
                                                <h3 class="resource-title overflowText popup-player-hook">${meet.title}</h3>
                                                <div class="resource-label">
                                                    <c:if test="${meet.publishState == true}">
                                                        <span class="issue">已发布</span>
                                                    </c:if>
                                                    <c:if test="${ meet.publishState == false && meet.viewState == true}">
                                                        <span class="look">已查阅</span>
                                                    </c:if>
                                                    <c:if test="${ meet.publishState == false && meet.viewState == false}">
                                                        <span>未查阅</span>
                                                    </c:if>
                                                    <c:if test="${meet.playType == 0}">
                                                        <span>录播</span>
                                                    </c:if>
                                                    <c:if test="${meet.playType != 0}">
                                                        <span>直播</span>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>



                            </c:forEach>

                        </div>
                    </div>
                    <%@include file="/WEB-INF/include/pageable_zh_CN.jsp"%>
                    <form id="pageForm" name="pageForm" method="post" action="${ctx}/mgr/delivery/history">
                        <input type="hidden" name="pageNum">
                        <input type="hidden" name="acceptId" value="${current}">
                    </form>
                </div>
            </div>
        </div>

    </c:if>
    <c:if test="${fn:length(acceptList) == 0}">
        <div>
            <div class="admin-content bg-gray">
                <div class="page-width clearfix">

                    <div class="admin-row clearfix">
                        <div class="admin-empty-data">
                            <p><img src="${ctxStatic}/images/admin-empty-data-02.png" alt=""></p>
                            <p> -无投稿记录 -</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

</div>

<%@include file="/WEB-INF/include/footer_zh_CN.jsp"%>







<script>
    $(function(){

        var asAllItem = audiojs.createAll();

        $(".hidden-box").perfectScrollbar();

        //触发弹出窗
        //投稿
        $('.contribute-hook').on('click',function(){
            layer.open({
                type: 1,
                area: ['1080px', '748px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                content: $('.contribute-popup-box'),
                success:function(){

                },
                cancel :function(){

                },
            });
        });


        $('.copy-hook').on('click',function(){
            layer.open({
                type: 1,
                area: ['609px', '278px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                content: $('.copy-popup-box'),
                success:function(){

                },
                cancel :function(){

                },
            });
        });

        $('.more-hook').on('click',function(){
            layer.open({
                type: 1,
                area: ['618px', '378px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                content: $('.more-popup-box'),
                success:function(){

                },
                cancel :function(){

                },
            });
        });

        $('.popup-player-hook').on('click',function(){
            layer.open({
                type: 1,
                area: ['1080px', '816px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                content: $('.player-popup-box'),
                success:function(){

                    var added = 105;

                    //幻灯片轮播
                    var swiper = new Swiper('.swiper-container-metting', {
                        //分页
                        pagination: '.swiper-pagination',

                        // 按钮
                        nextButton: '.swiper-popup-button-next-hook',
                        prevButton: '.swiper-popup-button-prev-hook',
                        slidesPerView: 'auto',
                        centeredSlides: true,
                        spaceBetween: 62,
                        paginationClickable: true,
                        paginationType: 'fraction',
                        onInit: function(swiper){
                            //设置偏移值
                            swiper.wrapper.attr('style','-webkit-transform: translate3d(175px, 0, 0);-moz-transform: translate3d(175px, 0, 0);-o-transform: translate3d(175px, 0, 0);-ms-transform: translate3d(175px, 0, 0);transform: translate3d(175px, 0, 0);transition-duration: 0ms;');
                            //获取默认偏移值
                            var defaultOffset = swiper.snapGrid;
                            for(var i =0; i<defaultOffset.length; i++){
                                defaultOffset[i] = defaultOffset[i] - added;
                            }
                            //更新偏移值
                            var updateOffset = defaultOffset.slice(1);
                            var newOffset= [-175];
                            newOffset = newOffset.concat(updateOffset);
                            //赋值给插件
                            swiper.snapGrid = newOffset;
                            swiper.slidesGrid = newOffset;
                        },
                        onSlideChangeEnd:function(swiper){
                            var dataSrc = $(".swiper-slide-active").attr('audio-src');
                            asAllItem[0].load(dataSrc);
                            asAllItem[0].play();
                        },
                    });
//                        $(".player-popup-box").find('.swiper-wrapper').attr('style','transform: translate3d(160px, 0, 0);transition-duration: 0ms;');

                },
                cancel :function(){

                },
            });
        });


//            $('.main-nav ul.sf-menu > li').last().addClass('last').end().hover(function(){ $(this).addClass('nav-hover'); },function(){ $(this).removeClass('nav-hover'); });
    })


</script>
</body>
</html>
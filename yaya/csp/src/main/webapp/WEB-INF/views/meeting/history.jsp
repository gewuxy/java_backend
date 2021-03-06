<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html >
<head>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <title><fmt:message key="page.header.delivery.history"/>-<fmt:message key="page.common.appName"/> </title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />

    <link rel="stylesheet" href="${ctxStatic}/css/audio.css">

    <script src="${ctxStatic}/js/audio.js"></script>
    <script src="${ctxStatic}/js/swiper.jquery.js"></script>
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
</head>
<body>
<div id="wrapper">
    <%@include file="../include/header.jsp" %>
    <c:if test="${fn:length(acceptList) != 0}" >
        <div class="admin-content bg-gray">
            <div class="page-width clearfix">
                <div class="admin-history-list item-radius ">
                    <div class="row clearfix">
                        <div class="col-lg-4">
                            <div class="admin-history-tabList">
                                <ul>
                                    <c:forEach items="${acceptList}" var="accept" varStatus="status">
                                        <li id="${accept.id}" <c:if test="${status.first}">  </c:if> <c:if test="${status.last}"> class="last" </c:if>>
                                            <a href="${ctx}/mgr/delivery/history?pageNum=1&pageSize=${page.pageSize}&acceptId=${accept.id}" >
                                                <div class="clearfix">
                                                    <div class="fl">
                                                        <img src="${accept.headimg}" alt="" height="30" width="30">
                                                    </div>
                                                    <div class="oh">
                                                        <h4 class="overflowText">${accept.nickname}</h4>
                                                        <c:if test="${empty accept.sign}">
                                                            <p class="overflowText-nowrap-multi">&nbsp;</p>
                                                        </c:if>
                                                        <c:if test="${not empty accept.sign}">
                                                            <p class="overflowText-nowrap-multi">${accept.sign}</p>
                                                        </c:if>
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
                                                    <c:if test="${empty meet.coverUrl}">
                                                        <img src="${ctxStatic}/upload/img/_admin_metting_01.png" alt="" class="img-response">
                                                    </c:if>
                                                    <c:if test="${not empty meet.coverUrl}">
                                                        <img src="${meet.coverUrl}" alt="" class="img-response">
                                                    </c:if>
                                                    <div class="resource-link">
                                                        <a href="#" class="resource-icon-play popup-player-hook" courseId="${meet.id}">
                                                            <i></i>
                                                            <fmt:message key="page.meeting.button.view"/>
                                                        </a>
                                                    </div>
                                                </div>
                                                <h3 class="resource-title overflowText popup-player-hook" courseId="${meet.id}">${meet.title}</h3>
                                                <div class="resource-label">
                                                    <c:if test="${meet.publishState == true}">
                                                        <span class="issue"><fmt:message key="page.words.has.publish"/> </span>
                                                    </c:if>
                                                    <c:if test="${ meet.publishState == false && meet.viewState == true}">
                                                        <span class="look"><fmt:message key="page.words.has.read"/> </span>
                                                    </c:if>
                                                    <c:if test="${ meet.publishState == false && meet.viewState == false}">
                                                        <span><fmt:message key="page.words.no.read"/> </span>
                                                    </c:if>
                                                    <c:if test="${meet.playType == 0}">
                                                        <span><fmt:message key="page.meeting.tab.record"/> </span>
                                                    </c:if>
                                                    <c:if test="${meet.playType != 0}">
                                                        <span><fmt:message key="page.meeting.tab.live"/></span>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>



                            </c:forEach>

                        </div>
                    </div>
                    <%@include file="../include/pageable.jsp"%>
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
                            <p> - <fmt:message key="page.words.no.delivery"/> - </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

</div>

<div class="player-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <div class="tab-subPage-bd swiperBox mettingSwiperBox clearfix">

                <div class="metting-swiper">
                    <!-- Swiper -->
                    <div class="swiper-container swiper-container-horizontal swiper-container-metting">
                        <div class="swiper-wrapper" id="mySwiper" style="transform: translate3d(297.25px, 0px, 0px); transition-duration: 0ms;">
                        </div>
                        <div class="clearfix t-center player-item" >
                            <div class="audio-metting-box" style="">
                                <audio controls=true id="swiperViedo" src=""></audio>
                            </div>
                        </div>
                        <!-- Add Pagination -->
                        <div class="metting-btn-item clearfix">
                            <span class="swiper-button-prev swiper-popup-button-prev-hook metting-button swiper-button-disabled"></span>
                            <div class="swiper-pagination swiper-pagination-fraction"><span class="swiper-pagination-current">1</span> <i>|</i> <span class="swiper-pagination-total">4</span></div>
                            <span class="swiper-button-next swiper-popup-button-next-hook metting-button"></span>
                        </div>

                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<%@include file="../include/footer.jsp"%>







<script>
    $(function(){


        $(".icon-folder").parent().removeClass();
        $(".icon-contribute").parent().attr("class","current");

        if('${current}'){
            if($("#${current}").attr("class") == "last"){
                $("#${current}").addClass("current last");
            }else{
                $("#${current}").addClass("current");
            }
        }


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
            var popupWidth = '1080px';
            var popupHeight = '816px';
            var added = 608;
            var defaultFirstPosition = -160;
            var editFirstPosition = 175;



            //判断小屏幕修改尺寸
            if( window.innerHeight <  816) {
                popupHeight = '600px';
                popupWidth = '789px';
                editFirstPosition = 120;
                added = 460;
                defaultFirstPosition = -118;
            }
            var course = loadCourseInfo($(this).attr("courseId"));
            if (course == undefined){
                layer.msg('<fmt:message key="page.meeting.tips.error"/>');
                return false;
            }
            initSwiper(course);
            layer.open({
                type: 1,
                area: [popupWidth, popupHeight],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                anim:2,
                shadeClose:true,
                content: $('.player-popup-box'),
                success:function(){
                    var newOffset;

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
                            swiper.wrapper.attr('style','-webkit-transform: translate3d('+editFirstPosition+'px, 0, 0);-moz-transform: translate3d('+editFirstPosition+'px, 0, 0);-o-transform: translate3d('+editFirstPosition+'px, 0, 0);-ms-transform: translate3d('+editFirstPosition+'px, 0, 0);transform: translate3d('+editFirstPosition+'px, 0, 0);transition-duration: 0ms;');
                            //获取默认偏移值
                            var defaultOffset = swiper.snapGrid;
                            //新增
                            defaultOffset[0] = defaultFirstPosition;
                            for(var i =1; i<defaultOffset.length; i++){
                                defaultOffset[i] = defaultOffset[i-1] + added ;
                            }
                            //更新偏移值
                            var updateOffset = defaultOffset.slice(1);
                            newOffset= [-editFirstPosition];
                            newOffset = newOffset.concat(updateOffset);

                            //赋值给插件
                            swiper.snapGrid = newOffset;
                            swiper.slidesGrid = newOffset;

                        },
                        onSlideChangeEnd:function(swiper){
                            var dataSrc = $(".swiper-slide-active").attr('audio-src');
                            asAllItem[0].load(dataSrc);
                            asAllItem[0].play()

                            //赋值给插件
                            swiper.snapGrid = newOffset;
                            swiper.slidesGrid = newOffset;
                            if(dataSrc) {
                                $('.audio-metting-box').css('opacity','1');
                            }

                        },
                        onTouchStart:function(swiper) {
                            //赋值给插件
                            swiper.snapGrid = newOffset;
                            swiper.slidesGrid = newOffset;
                            $('.audio-metting-box').css('opacity','0');
                        }

                    });

                },
                cancel :function(){

                    $("#mySwiper").html("");
                },
            });


        });


        function loadCourseInfo(courseId){
            var course ;
            $.ajax({
                url:'${ctx}/mgr/meet/view/'+courseId,
                dataType:'json',
                async:false,
                type:'get',
                success:function (data) {
                    course = data.data;
                },
                error:function(e, n, a){
                    alert(a);
                }
            });
            return course;
        }

        function initSwiper(course){
            $("#mySwiper").html("");
            for(var index in course.details){
                var detail = course.details[index];
                if (detail.videoUrl != undefined){//视频
                    $("#mySwiper").append('<div class="swiper-slide" data-num="'+index+'"  ><video src="'+detail.videoUrl+'" width="auto" height="264" controls autobuffer></video>'
                        +'<div class="swiper-slide-metting-audio"></div></div>');
                } else {
                    $("#mySwiper").append('<div class="swiper-slide swiper-slide-active" data-num="'+index+'"  audio-src="'+detail.audioUrl+'">'
                        +'<img src="'+detail.imgUrl+'" alt=""></div>');
                }
            }
        }

    })


</script>
</body>
</html>
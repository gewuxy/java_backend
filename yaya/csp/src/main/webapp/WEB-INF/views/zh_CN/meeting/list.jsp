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

    <script src="${ctxStatic}/js/audio.js"></script>
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script>
        $(function(){

            //初始化音频
            var asAllItem = audiojs.createAll();
            //切换yi
            $(".swiper-popup-button-next-hook,.swiper-popup-button-prev-hook").on('click',function(){
                var dataSrc = $(".swiper-slide-active").attr('audio-src');
                asAllItem[0].load(dataSrc);
                asAllItem[0].play();
            });



            //超出出现下拉框
            $(".hidden-box").perfectScrollbar();

            //触发弹出窗
            //投稿
            $('.contribute-hook').on('click',function(){
                var courseId = $(this).attr("courseId");
                $("#courseId").val(courseId);
                layer.open({
                    type: 1,
                    area: ['1080px', '748px'],
                    anim:5,
                    fix: false, //不固定
                    isOutAnim: true,
                    title:false,
                    closeBtn:0,
                    content: $('.contribute-popup-box'),
                    success:function(){

                    },
                    cancel :function(){

                    },
                });
            });

            $("#submitBtn").click(function () {
                var selectNum = $("input[name='accepts']:checked").length;
                if(selectNum < 1){
                    layer.msg("请选择要投稿的单位号");
                    $("#contribute").submit(function (e) {
                        e.preventDefault();
                    });
                }
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
                    area: ['618px', '398px'],
                    fix: false, //不固定
                    title:false,
                    anim:5,
                    closeBtn:0,
                    content: $('.more-popup-box'),
                    success:function(){

                    },
                    cancel :function(){

                    },
                });
            });

            $("#more_popup_ul li a").click(function(){
                alert(123);
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
                                    defaultOffset[i] = defaultOffset[i] - added ;
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

                    },
                    cancel :function(){

                    },
                });
            });


        })
    </script>
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
                                        <a href="javascript:;" class="contribute-hook" courseId="${course.id}">投稿</a>
                                    </div>
                                    <div class="col-lg-6">
                                        <a href="javascript:;" class="more more-hook"><i></i>更多</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </div>
                <%@include file="/WEB-INF/include/pageable_zh_CN.jsp"%>
                <form id="pageForm" name="pageForm" method="post" action="${ctx}/mgr/meet/list">
                    <input type="hidden" name="pageNum">
                    <input type="hidden" name="keyword" value="${keyword}">
                </form>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/include/footer_zh_CN.jsp"%>
</div>

<!--弹出 投稿平台-->
<div class="contribute-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>投稿平台</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-subName">
            <p>单位号需在“资源平台”打开“投稿箱”功能才能接受投稿。</p>
        </div>
        <form id="contribute" name="contribute" action="${ctx}/mgr/delivery/contribute" method="post">
            <input type="hidden" id="courseId" name="courseId" value="">
            <div class="layer-hospital-popup-main hidden-box">
                <div class="row clearfix">
                    <c:forEach items="${accepterList}" var="a">
                        <div class="col-lg-4">
                            <div class="contribute-list-item">
	                         <span class="checkboxIcon">
	                            <input type="checkbox" id="checkbox_${a.id}" name="accepts" class="chk_1 chk-hook" value="${a.id}">
	                            <label for="checkbox_${a.id}" class="formTitle ">
	                                <i class="ico"></i>
	                                <div class="clearfix">
	                                    <div class="fl">
	                                        <img src="${a.headimg}" alt="" width="30" height="30">
	                                    </div>
	                                    <div class="oh">
	                                        <h4>${a.nickname}</h4>
	                                        <p>${a.sign}</p>
	                                    </div>
	                                </div>
	                            </label>
	                        </span>
                            </div>
                        </div>
                    </c:forEach>

                </div>
            </div>
            <div class="layer-hospital-popup-bottom">
                <div class="fr">
                    <input type="submit" class="button buttonBlue min-btn" id="submitBtn" value="确认投稿">
                </div>
            </div>
        </form>

    </div>
</div>

<div class="more-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <div class="more-popup-list clearfix">
                <ul id="more_popup_ul">
                    <li>
                        <a href="javascript:;">
                            <img src="${ctxStatic}/images/_wechat-icon.png" alt="">
                            <p>微信好友</p>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <img src="${ctxStatic}/images/_friends-icon.png" alt="">
                            <p>朋友圈</p>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <img src="${ctxStatic}/images/_weibo-icon.png" alt="">
                            <p>微博</p>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <img src="${ctxStatic}/images/_twitter-icon.png" alt="">
                            <p>Twitter</p>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <img src="${ctxStatic}/images/_facebook-icon.png" alt="">
                            <p>Facebook</p>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <img src="${ctxStatic}/images/_copyLink-icon.png" alt="">
                            <p>复制链接</p>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;" class="copy-hook">
                            <img src="${ctxStatic}/images/_copy-icon.png" alt="">
                            <p>复制副本</p>
                        </a>
                    </li>
                    <li>
                        <a>
                            <img src="${ctxStatic}/images/_edit-icon.png" alt="">
                            <p>编辑</p>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <img src="${ctxStatic}/images/_delete-icon.png" alt="">
                            <p>删除</p>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>



</body>
</html>

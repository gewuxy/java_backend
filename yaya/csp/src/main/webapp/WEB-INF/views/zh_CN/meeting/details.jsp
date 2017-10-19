<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/18
  Time: 18:08
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>上传预览</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
    <link rel="stylesheet" href="${ctxStatic}/js/layer/skin/style.css">

    <script src="${ctxStatic}/js/swiper.jquery.js"></script>
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="${ctxStatic}/js/layer/layer.js"></script>
    <!--[if lt IE 9]>
    <script src="${ctxStatic}/js/html5.js"></script>
    <![endif]-->
</head>
<body>
<div id="wrapper">
    <%@include file="/WEB-INF/include/header_zh_CN.jsp"%>
    <div class="admin-content bg-gray">
        <div class="page-width clearfix">
            <div class="admin-module clearfix item-radius admin-metting-player">
                <div class="metting-swiper">
                    <!-- Swiper -->
                    <div class="swiper-container swiper-container-horizontal swiper-container-metting">
                        <div class="swiper-wrapper" style="transform: translate3d(297.25px, 0px, 0px); transition-duration: 0ms;">
                            <c:forEach items="${course.details}" var="detail" varStatus="status">
                                <div class="swiper-slide ${status.index == 0 ? 'swiper-slide-active':''}" data-num="${status.index}"  audio-src="${detail.audioUrl}">
                                    <c:choose>
                                        <c:when test="${not empty detail.videoUrl}">
                                            <video src="${fileBase}${detail.videoUrl}" width="auto" height="264" controls autobuffer></video>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${fileBase}${detail.imgUrl}" alt="">
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="swiper-slide-metting-move"><a href="javascript:;" class="delFile fx-btn-3" title="删除" detailId="${detail.id}"><span class="icon iconfont icon-minIcon16"></span></a></div>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="metting-btn-item clearfix pr">
                            <div class="addppt-button"><label for="file1"><input type="file" class="none" id="file1">插入幻灯片</label></div>
                            <span class="swiper-button-prev swiper-popup-button-prev-hook metting-button swiper-button-disabled"></span>
                            <div class="swiper-pagination swiper-pagination-fraction"><span class="swiper-pagination-current">1</span> <i>|</i> <span class="swiper-pagination-total">4</span></div>
                            <span class="swiper-button-next swiper-popup-button-next-hook metting-button"></span>
                        </div>

                    </div>
                </div>
                <div class="admin-line"></div>
                <div class="admin-button t-center">
                    <a href="${ctx}/mgr/meet/edit?courseId=${course.id}" class="button color-blue min-btn cancel-hook" >返回</a>&nbsp;&nbsp;
                    <input type="submit" class="button buttonBlue item-radius min-btn" onclick="window.location.href = '${ctx}/mgr/meet/edit?courseId=${course.id}'" value="保存">
                </div>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/include/footer_zh_CN.jsp"%>
</div>


<!--弹出 副本-->
<div class="cancel-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="cancel-popup-main">
                    <p><img src="${ctxStatic}/images/question-32x32.png" alt="">是否放弃修改？</p>
                    <div class="admin-button t-right">
                        <a href="javascript:;" class="button color-blue min-btn layui-layer-close" >取消</a>
                        <input type="submit" class="button buttonBlue item-radius min-btn" value="确认">
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>

<div class="cancel-popup-box" id="del-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="cancel-popup-main">
                    <p><img src="${ctxStatic}/images/question-32x32.png" alt="">是否确定删除？</p>

                </div>

            </form>
        </div>
    </div>
</div>

<script>
    $(function(){


        var added = 105;
        var activeIndex = "${param.index}";
        if (activeIndex == ''){
            activeIndex = 0;
        }

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
            initialSlide:activeIndex,
            onInit: function(swiper){

            },
            onSlideChangeEnd:function(swiper){
            },
        });


        //触发弹出窗
        //投稿
//        $('.cancel-hook').on('click',function(){
//            layer.open({
//                type: 1,
//                area: ['440px', '224px'],
//                fix: false, //不固定
//                title:false,
//                closeBtn:0,
//                content: $('.cancel-popup-box'),
//                success:function(){
//
//                },
//                cancel :function(){
//
//                },
//            });
//        });


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

        $('.delFile').on('click',function(){
            var detailId = $(this).attr("detailId");
            layer.open({
                type: 1,
                area: ['300px', '250px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                anim: 5,
                content: $('#del-popup-box'),
                btn : ['确定', '取消'],
                yes :function(){
                    window.location.href = '${ctx}/mgr/meet/detail/del/${course.id}/'+detailId;
                },

                cancel :function(){
                }
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

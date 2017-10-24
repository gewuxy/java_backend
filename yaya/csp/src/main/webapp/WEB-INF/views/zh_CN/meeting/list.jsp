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
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.css">
    <link rel="stylesheet" href="${ctxStatic}/css/audio.css">

    <link rel="stylesheet" href="${ctxStatic}/css/style.css">

    <script src="${ctxStatic}/js/audio.js"></script>
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="${ctxStatic}/js/swiper.jquery.js"></script>
    <script src="${ctxStatic}/js/zclip/jquery.zclip.min.js"></script>

    <script id="-mob-share" src="http://f1.webshare.mob.com/code/mob-share.js"></script>


    <script>
        const shareSdkAppKey = "21454499cef00";
        var courseId;
        var courseTitle = "";
        var shareUrl = "";
        var coverUrl = "";

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
                }else{
                    $("#contribute").submit(function (e) {
                        e.preventDefault(); //阻止表单自动提交
                    });
                    $.post($("#contribute").attr('action'),$("#contribute").serialize(),function(result){
                        if (result.code == 0){//成功
                            layer.msg("投稿成功",{time:300},function () {
                                window.parent.location.reload();
                            });
                        }else{//失败
                            layer.msg(result.err);
                        }
                    },'json');
                }
            });


            $('.copy-hook').on('click',function(){
                layer.open({
                    type: 1,
                    area: ['609px', '278px'],
                    fix: false, //不固定
                    title:false,
                    anim:5,
                    closeBtn:0,
                    content: $('.copy-popup-box'),
                    success:function(){
                        $("#courseTitle").val(courseTitle + "_副本");
                    },
                    cancel :function(){

                    },
                });
            });

            $('.more-hook').on('click',function(){
                courseId = $(this).attr("courseId");
                courseTitle = $(this).attr("courseTitle");
                shareUrl = getShareUrl();
                $("#copyShareUrl").val(shareUrl);
                mobShare.config({
                    debug: true, // 开启调试，将在浏览器的控制台输出调试信息
                    appkey: shareSdkAppKey, // appkey
                    params: {
                        url: shareUrl, // 分享链接
                        title: courseTitle, // 分享标题
                        description: courseTitle, // 分享内容
                        pic: '', // 分享图片，使用逗号,隔开
                        reason:'',//自定义评论内容，只应用与QQ,QZone与朋友网
                    },

                    callback: function( plat, params ) {
                    }
                });
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

            $('.popup-player-hook').on('click',function(){
                var course = loadCourseInfo($(this).attr("courseId"));
                if (course == undefined){
                    layer.msg("获取会议信息失败");
                    return false;
                }
                initSwiper(course);
                layer.open({
                    type: 1,
                    area: ['1080px', '816px'],
                    fix: false, //不固定
                    title:false,
                    closeBtn:0,
                    anim:2,
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
                        $("#mySwiper").html("");
                    },
                });


            });

            $("#copyShareUrlBtn").click(function (){
                $("#copyShareUrl").show();
                $("#copyShareUrl")[0].select();
                var tag = document.execCommand("Copy");
                if (tag){
                    layer.msg("已经成功复制到剪切板");
                }
                $("#copyShareUrl").hide();
            });

            $("#copyBtn").click(function(){
                $.ajax({
                    url:'${ctx}/mgr/meet/copy/'+courseId,
                    data:{'title': $("#courseTitle").val()},
                    type : "POST",
                    dataType : 'json',
                    success:function(data){
                        if (data.code == 0){
                            window.location.reload();
                        } else {
                            layer.msg(data.err);
                        }
                    }
                });
            });
        });

        const accessUrl = '${ctx}/mgr/meet/list';

        function changePlayType(playType){
            var targetUrl = accessUrl;
            if (playType != undefined){
                targetUrl += '?playType='+playType;
            }
            window.location.href = targetUrl;
        }
        
        function getShareUrl(){
            var shareUrl = '';
            $.ajax({
                url : '${ctx}/mgr/meet/share/'+courseId,
                dataType:'json',
                async:false,
                success : function (data) {
                    shareUrl = data.data.shareUrl;
                }
            });
            return shareUrl;
        }

        var sortType = '${sortType}';

        function sortList(){
            sortType = sortType == 'asc' ? 'desc' : 'asc';
            var targetUrl = accessUrl;
            targetUrl += '?sortType='+sortType;
            window.location.href = targetUrl;
        }


        function delCourse(){
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
                    $.get('${ctx}/mgr/meet/del/'+courseId, {}, function (data) {
                        if (data.code == 0){
                            window.location.reload();
                        } else {
                            layer.msg(data.err);
                        }
                    }, 'json');

                },

                cancel :function(){
                }
            });
        }

        function edit(){
            window.location.href = '${ctx}/mgr/meet/edit?courseId='+courseId;
        }
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
                        <li class="first ${empty playType ? 'cur' : ''}"><a href="javascript:;" class="screen-all " onclick="changePlayType()">全部</a></li>
                        <li ${playType == 0? "class='cur'" : ''}><a href="javascript:;" class="screen-viedo " onclick="changePlayType(0)"><i></i>投屏录播</a></li>
                        <li ${playType > 0? "class='cur'" : ''}><a href="javascript:;" class="screen-live " onclick="changePlayType(1)"><i></i>投屏直播</a></li>
                        <li class="last ${sortType == 'desc' ? 'cur' : ''}"><a href="javascript:;" class="screen-time" onclick="sortList()"><i></i>创建时间排序</a></li>
                    </ul>
                </div>
                <div class="admin-search">
                    <form action="${ctx}/mgr/meet/list" method="post" id="yPsearchForm" name="yPsearchForm">
                        <div class="search-form search-form-responsive item-radius clearfix">
                            <input type="text" placeholder="搜索会议名字" name="keyword" id="keyword" value="${keyword}" class="form-text">
                            <button type="submit" class="form-btn"><span></span></button>
                        </div>
                    </form>
                </div>
            </div>
            <c:choose>
                <c:when test="${empty page.dataList}">
                    <div class="admin-row clearfix">
                        <div class="admin-empty-data">
                            <p><img src="${ctxStatic}/images/admin-empty-data-01.png" alt=""></p>
                            <p> -无会议内容 -</p>
                        </div>
                    </div>

                </c:when>
                <c:otherwise>
                    <div class="admin-metting-list">
                        <div class="row clearfix">
                            <c:forEach items="${page.dataList}" var="course">
                                <div class="col-lg-4">

                                    <div class="resource-list-item item-radius clearfix">
                                        <div class="resource-img ">
                                            <c:choose>
                                                <c:when test="${not empty course.coverUrl}">
                                                    <img src="${course.coverUrl}" alt="" class="img-response">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${ctxStatic}/upload/img/_admin_metting_01.png" alt="" class="img-response">
                                                </c:otherwise>
                                            </c:choose>

                                            <div class="resource-link">
                                                <a style="cursor: pointer;" courseId="${course.id}" class="resource-icon-play popup-player-hook">
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
                                    <span><i class="hot">
                                        <c:choose>
                                            <c:when test="${course.playType == 0}">
                                                ${course.playPage + 1}
                                            </c:when>
                                            <c:otherwise>
                                                ${course.livePage + 1}
                                            </c:otherwise>
                                        </c:choose>
                                    </i><i class="muted">|</i>${course.pageCount}</span>
                                            <span>${course.playTime}</span>
                                            <span>
                                        <c:choose>
                                            <c:when test="${course.playType == 0}">录播</c:when>
                                            <c:otherwise>直播</c:otherwise>
                                        </c:choose>
                                    </span>
                                        </div>
                                        <div class="resource-menu">
                                            <div class="col-lg-6">
                                                <a href="javascript:;" class="contribute-hook" courseId="${course.id}">投稿</a>
                                            </div>
                                            <div class="col-lg-6">
                                                <a href="javascript:;" class="more more-hook" courseId="${course.id}" courseTitle="${course.title}"><i></i>更多</a>
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
                </c:otherwise>
            </c:choose>

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
                <ul id="more_popup_ul" class="-mob-share-list">
                    <li  class="-mob-share-weixin">
                        <a href="javascript:;">
                            <img src="${ctxStatic}/images/_wechat-icon.png" alt="">
                            <p>微信好友</p>
                        </a>
                    </li>
                    <%--<li class="-mob-share-qq">--%>
                        <%--<a href="javascript:;">--%>
                            <%--<img src="${ctxStatic}/images/_friends-icon.png" alt="">--%>
                            <%--<p>朋友圈</p>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                    <li class="-mob-share-weibo">
                        <a href="javascript:;">
                            <img src="${ctxStatic}/images/_weibo-icon.png" alt="">
                            <p>微博</p>
                        </a>
                    </li>
                    <li class="-mob-share-twitter">
                        <a href="javascript:;">
                            <img src="${ctxStatic}/images/_twitter-icon.png" alt="">
                            <p>Twitter</p>
                        </a>
                    </li>
                    <li  class="-mob-share-facebook">
                        <a href="javascript:;">
                            <img src="${ctxStatic}/images/_facebook-icon.png" alt="">
                            <p>Facebook</p>
                        </a>
                    </li>
                    <li>
                        <a style="cursor: pointer;" id="copyShareUrlBtn">
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
                        <a href="javascript:;" onclick="edit()">
                            <img src="${ctxStatic}/images/_edit-icon.png" alt="">
                            <p>编辑</p>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;" onclick="delCourse()">
                            <img src="${ctxStatic}/images/_delete-icon.png" alt="">
                            <p>删除</p>
                        </a>
                    </li>
                </ul>
                <input type="text" style="display: none; width: 485px;" id="copyShareUrl">
            </div>
        </div>
    </div>
</div>
<div class="copy-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
                <div class="copy-popup-main">
                    <label for="courseTitle" class="cells-block pr"><input id="courseTitle" type="text" class="login-formInput" value=""></label>
                    <input type="button" class="button login-button buttonBlue last" id="copyBtn" value="确认复制">
                </div>
        </div>
    </div>
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



</body>
</html>

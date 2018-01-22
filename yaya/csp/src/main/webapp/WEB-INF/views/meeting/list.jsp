<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/17
  Time: 9:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!Doctype html>
<html >
<head>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <title><fmt:message key="page.header.meet"/> - <fmt:message key="page.common.appName"/> </title>


    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/audio.css">



    <script src="${ctxStatic}/js/audio.js"></script>
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="${ctxStatic}/js/swiper.jquery.js"></script>
    <script src="${ctxStatic}/js/zclip/jquery.zclip.min.js"></script>

    <script id="-mob-share" src="//f1.webshare.mob.com/code/mob-share.js"></script>

    <script>
        const shareSdkAppKey = "21454499cef00";
        var courseId;
        var courseTitle = "";
        var shareUrl = "";
        var coverUrl = "";

        $(function(){
            if("${err}"){
                layer.msg("${err}");
            }

            //判断是否新用户，新用户弹出购买套餐
            if(${newUser}){
                layer.open({
                    type: 2,
                    title: false,
                    fix: false, //不固定
                    skin: 'member-popup-zIndex',
                    shadeClose: false,
                    offset: '70px',
                    closeBtn: 0, //不显示关闭按钮
                    shade: 0.1,
                    area: ['1116px', '930px'],
                    content: '${ctx}/mgr/pay/mark',
                    success:function(layero,index){
                        //付款弹出层
                        var body = layer.getChildFrame('body', index);
                        body.find(".cancel-hook").on('click',function(){
                            var packageId = body.find("#packageId").val();
                            if(packageId == 0){
                                ajaxPost('${ctx}/mgr/pay/standard', {},function(data){
                                    if (data.code == "0"){
                                        window.location.href = '${ctx}/mgr/meet/list';
                                    }
                                });
                                return false;
                            }
                            layer.open({
                                type: 1,
                                area: ['560px', '300px'],
                                fix: false, //不固定
                                title:false,
                                closeBtn:0,
                                content: $('#pkBuyMsg')
                            });
                        });
                    }
                })
            }

            var pkSuccessMsg = '${successMsg}';
            //购买提示不为空显示
            if(isNotEmpty(pkSuccessMsg)){
                //弹出提示
                layer.open({
                    type: 1,
                    area: ['440px', '240px'],
                    fix: false, //不固定
                    title:false,
                    closeBtn:0,
                    content: $('#pkSuccessMsg'),
                    success:function(){
                        $("#backMsg").html(pkSuccessMsg);
                    },
                });
            }

            //初始化音频
            var asAllItem = audiojs.createAll();
            //切换yi
            $(".swiper-popup-button-next-hook,.swiper-popup-button-prev-hook").on('click',function(){
                var dataSrc = $(".swiper-slide-active").attr('audio-src');
                asAllItem[0].load(dataSrc);
                asAllItem[0].play();
            });

            //清除提示信息
            $(".clearMsg").on('click',function(){
                ajaxGet('${ctx}/mgr/pay/update/msg', {}, function(data){});
            });

            //超出出现下拉框
            $(".hidden-box").perfectScrollbar();

            //触发弹出窗
            //投稿
            $('.contribute-hook').on('click',function(){
                var popupHeight = '748px'
                var popupListHeight = '450px';

                //判断小屏幕修改尺寸
                if( window.innerHeight <  748) {
                    popupHeight = '90%';
                }
                var courseId = $(this).attr("courseId");
                $("#courseId").val(courseId);
                layer.open({
                    type: 1,
                    area: ['1080px', popupHeight],
                    anim:5,
                    fix: false, //不固定
                    isOutAnim: true,
                    title:false,
                    closeBtn:0,
                    shadeClose:true,
                    content: $('.contribute-popup-box'),
                    success:function(layero,index){
                        var popupHeight = layero[0].clientHeight;
                        popupHeight = popupHeight * 0.5;
                        //判断小屏幕修改尺寸
                        if( window.innerHeight <  748) {
                            $('.contribute-popup-box').find(".hidden-box").height(popupHeight);
                        } else {
                            $('.contribute-popup-box').find(".hidden-box").height(popupListHeight);
                        }
                    },
                    cancel :function(){

                    },
                });
            });

            $("#submitBtn").click(function () {
                var selectNum = $("input[name='accepts']:checked").length;
                if(selectNum < 1){
                    layer.msg('<fmt:message key="page.delivery.tips.chose_users"/>');
                    $("#contribute").submit(function (e) {
                        e.preventDefault();
                    });
                }else{
                    $("#contribute").submit(function (e) {
                        e.preventDefault(); //阻止表单自动提交
                    });
                    ajaxPost($("#contribute").attr('action'),$("#contribute").serialize(),function(result){
                        if (result.code == 0){//成功
                            layer.msg('<fmt:message key="page.delivery.tips.success"/>',{time:300},function () {
                                layer.closeAll();
                            });
                        }else{//失败
                            layer.msg(result.err);
                        }
                    });
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
                        $("#courseTitle").val(courseTitle + "_" + '<fmt:message key="page.meeting.button.copy_suffix"/>');
                    },
                    cancel :function(){

                    },
                });
            });

            $('.more-hook').on('click',function(){
                courseId = $(this).attr("courseId");
                courseTitle = $(this).attr("courseTitle");
                var info = $(this).attr("info");
                var locked = $(this).attr("locked");
                if (locked == "true"){
                    $("#copyLi").hide();
                    $("#editLi").hide();
                } else {
                    $("#copyLi").show();
                    $("#editLi").show();
                }

                shareUrl = getShareUrl();
                coverUrl = $("#cover_"+courseId).attr("src");
                $("#copyShareUrl").val(shareUrl);
                mobShare.config({
                    debug: true, // 开启调试，将在浏览器的控制台输出调试信息
                    appkey: shareSdkAppKey, // appkey
                    params: {
                        url: shareUrl, // 分享链接
                        title: '会讲 | ' + courseTitle, // 分享标题
                        description: info,
                        pic: coverUrl, // 分享图片，使用逗号,隔开
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

                ajaxSyncGet('${ctx}/mgr/meet/view/'+courseId, {}, function(data){
                    course = data.data;
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
                        if (detail.audioUrl == undefined){
                            detail.audioUrl = "";
                        }
                        $("#mySwiper").append('<div class="swiper-slide swiper-slide-active" data-num="'+index+'"  audio-src="'+detail.audioUrl+'">'
                            +'<img src="'+detail.imgUrl+'" alt=""></div>');
                    }
                }
            }

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

            $("#copyShareUrlBtn").click(function (){
                $("#copyShareUrl").show();
                $("#copyShareUrl")[0].select();
                var tag = document.execCommand("Copy");
                if (tag){
                    layer.msg('<fmt:message key="page.meeting.tips.copy_success"/>');
                }
                $("#copyShareUrl").hide();
            });

            $("#copyBtn").click(function(){
                ajaxPost('${ctx}/mgr/meet/copy/'+courseId, {'title': $("#courseTitle").val()}, function(data){
                    if (data.code == 0){
                        window.location.href = "${ctx}/mgr/meet/list";
                    } else {
                        layer.msg(data.err);
                    }
                });
            });

            //弹出锁定的元素，弹出提示
            $('.meeting-lock-item').on('click',function(){
                //弹出提示
                layer.open({
                    type: 1,
                    area: ['440px', '340px'],
                    fix: false, //不固定
                    title:false,
                    closeBtn:0,
                    btn: ["<fmt:message key='page.common.upgrade'/>"],
                    content: $('#meetCountOut'),
                    success:function(){

                    },
                    yes:function(){
                        //成功跳去会员页面，让用户升级
                        window.location.href='${ctx}/mgr/user/memberManage';
                    },
                    cancel :function(){

                    },
                });
            });


            //弹出提示
            if ("${meetCountOut}" && "${param.keyword}" == '' && "${param.playType}" == "" && "${param.sortType}" == '' && "${param.pageNum}" == ''){
                layer.open({
                    type: 1,
                    area: ['440px', '320px'],
                    fix: false, //不固定
                    title:false,
                    closeBtn:0,
                    btn: ["<fmt:message key='page.common.upgrade'/>"],
                    content: $('#meetCountOut'),
                    success:function(){

                    },
                    yes:function(){
                        //成功跳去会员页面，让用户升级
                        window.location.href='${ctx}/mgr/user/memberManage';
                    },
                    cancel :function(){
                        layer.closeAll();
                    },
                });
            }


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

            ajaxSyncGet('${ctx}/mgr/meet/share/'+courseId, {}, function(data){
                shareUrl = data.data.shareUrl;
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

            ajaxGet("${ctx}/mgr/meet/delete/able/" + courseId, {}, function (data) {
                if (data.code == "0"){
                    layer.open({
                        type: 1,
                        area: ['300px', '250px'],
                        fix: false, //不固定
                        title:false,
                        closeBtn:0,
                        anim: 5,
                        content: $('#del-popup-box'),
                        btn : ['<fmt:message key="page.button.sure"/>', '<fmt:message key="page.button.cancel"/>'],
                        yes :function(){
                            $.get('${ctx}/mgr/meet/del/'+courseId, {}, function (data) {
                                if (data.code == 0){
                                    window.location.href = "${ctx}/mgr/meet/list?showWarn=false";
                                } else {
                                    layer.msg(data.err);
                                }
                            }, 'json');

                        },

                        cancel :function(){
                        }
                    });
                } else {
                    layer.msg(data.err);
                }
            });
        }

        function edit(){
            ajaxGet("${ctx}/mgr/meet/editable/" + courseId, {}, function(data) {
                if (data.code == "0"){
                    window.location.href = '${ctx}/mgr/meet/edit?courseId='+courseId;
                } else {
                    layer.msg(data.err);
                }
            });
        }

        function closeMeetCountTips(){
            $.get('${ctx}/mgr/meet/tips/close', {}, function (data) {
                $("#meetCountTips").hide();
            }, 'json');
        }




        function pkTimeClose(){
            $("#pkTime").hide();
        }
        function standardClose(){
            $("#standard").hide();
        }

        function unlimitedClose(){
            $("#unlimited").hide();
        }

        function closeClick(){
            $("#note").hide();
        }



        $(function () {
            var pkId = ${packageId};
            var meetCount = ${cspPackage.usedMeetCount + cspPackage.hiddenMeetCount}
            if (pkId == 1 && 3 >= meetCount ){
                $("#pkTime").hide();
                $("#note").hide();
                $("#unlimited").hide();
                $("#meetCountTips").hide();
            }else if(pkId == 1 && 3 < meetCount){
                $("#pkTime").hide();
                $("#note").hide();
                $("#unlimited").hide();
                $("#standard").hide();
                $("#meetCountTips").show();
            }else{
                $("#meetCountTips").hide();
                $("#standard").hide();
            }

            if("${undoneWarn}" == "true"){
                layer.open({
                    type: 1,
                    area: ['400px', '280px'],
                    fix: false, //不固定
                    title:false,
                    closeBtn:0,
                    btn: ["<fmt:message key='page.common.continue'/>","<fmt:message key='page.common.cancel'/>"],
                    content: $('#undoneWarn'),
                    success:function(){

                    },
                    yes:function(){
                        //成功跳去会员页面，让用户升级
                        window.location.href='${ctx}/mgr/meet/edit';
                    },
                    cancel :function(){
                        layer.closeAll();
                    },
                });
            }

        })



    </script>
</head>
<body>
<div id="wrapper">
    <%@include file="../include/header.jsp" %>
    <div class="admin-content bg-gray">
        <div class="page-width clearfix pr">
            <c:if test="${(cspPackage.usedMeetCount + cspPackage.hiddenMeetCount) > cspPackage.limitMeets && packageId !=3}">
                <div class="admin-tips" id="meetCountTips">
                    <span class="admin-tips-main"> <a href="${ctx}/mgr/user/memberManage"><fmt:message key="page.list.remind.meetCount"/></a> </span>
                    <span class="admin-tips-close" onclick="closeMeetCountTips()"></span>
                </div>
            </c:if>

            <c:if test="${expireTimeCount <= 5  && expireTimeCount >=0}">
                <div class="admin-tips" id="note">
                    <span class="admin-tips-main" > <a href="${ctx}/mgr/user/memberManage"><fmt:message key="page.expire.less"/> <strong class="color-blue">${expireTimeCount}</strong> <fmt:message key="page.expire.less.five.day"/></a> </span>
                    <span class="admin-tips-close" onclick="closeClick()"></span>
                </div>
            </c:if>

            <c:if test="${expireTimeCount > 5}">
                <div class="admin-tips" id="pkTime">
                    <span class="admin-tips-main"  id="yearTime">
                        <a href="${ctx}/mgr/user/memberManage"><fmt:message key="page.expire"/> <strong class="color-blue">${expireTimeCount}</strong> <fmt:message key="page.expire.day"/></a>
                    </span>
                    <span class="admin-tips-close" onclick="pkTimeClose()"></span>
                </div>
                <script>
                    $(function () {
                        var expireTimeCount = ${expireTimeCount}
                        if(expireTimeCount>365){
                            var yearCount =  Math.floor(expireTimeCount / 365);
                            var mounthCount =  Math.floor(((expireTimeCount / 365) - yearCount)*365/31);
                            if(mounthCount<=0){
                                $("#yearTime").html('<a href="${ctx}/mgr/user/memberManage"><fmt:message key="page.expire"/> <strong class="color-blue">'+yearCount+'<fmt:message key="page.expire.year"/></strong></a>')
                            }else {
                                $("#yearTime").html('<a href="${ctx}/mgr/user/memberManage"><fmt:message key="page.expire"/>  <strong class="color-blue">'+yearCount+'</strong> <fmt:message key="page.expire.year"/> <strong class="color-blue">'+mounthCount+'</strong> <fmt:message key="page.expire.month"/></a>')
                            }
                        }
                    })

                </script>
            </c:if>
            <c:if test="${packageId == 1 && (cspPackage.usedMeetCount + cspPackage.hiddenMeetCount <= 3)}">
                <div class="admin-tips" id="standard">
                    <span class="admin-tips-main" > <a href="${ctx}/mgr/user/memberManage"><fmt:message key="page.remind.standard.time"/> </a> </span>
                    <span class="admin-tips-close" onclick="standardClose()"></span>
                </div>
            </c:if>
            <c:if test="${cspPackage.unlimited == true && packageId != 1}">
                <div class="admin-tips" id="unlimited">
                    <span class="admin-tips-main" > <a href="${ctx}/mgr/user/memberManage"><fmt:message key="page.remind.professional.time"/> </a> </span>
                    <span class="admin-tips-close" onclick="unlimitedClose()"></span>
                </div>
            </c:if>
            <div class="admin-row clearfix pr">
                <div class="admin-screen-area">
                    <ul>
                        <li class="first ${empty playType ? 'cur' : ''}"><a href="javascript:;" class="screen-all " onclick="changePlayType()"><fmt:message key="page.meeting.tab.all"/></a></li>
                        <li ${playType == 0? "class='cur'" : ''}><a href="javascript:;" class="screen-viedo " onclick="changePlayType(0)"><i></i><fmt:message key="page.meeting.tab.screen_record"/></a></li>
                        <li ${playType > 0? "class='cur'" : ''}><a href="javascript:;" class="screen-live " onclick="changePlayType(1)"><i></i><fmt:message key="page.meeting.tab.screen_live"/></a></li>
                        <li class="last ${sortType == 'desc' ? 'cur' : ''}"><a href="javascript:;" class="screen-time" onclick="sortList()"><i></i><fmt:message key="page.meeting.tab.orderby"/></a></li>
                    </ul>
                </div>
                <div class="admin-search">
                    <form action="${ctx}/mgr/meet/list" method="post" id="yPsearchForm" name="yPsearchForm">
                        <div class="search-form search-form-responsive item-radius clearfix">
                            <input type="text" placeholder="<fmt:message key='page.meeting.tab.search'/>" name="keyword" id="keyword" value="${keyword}" class="form-text">
                            <button type="submit" class="form-btn"><span></span></button>
                        </div>
                    </form>
                </div>
            </div>
            <c:choose>
                <c:when test="${empty page.dataList}">
                    <div class="admin-metting-list">
                        <div class="row clearfix meeting-row-hock">
                            <div class="col-lg-4">
                                <div class="resource-list-item item-radius clearfix">
                                    <a href="${ctx}/mgr/meet/edit" class="resource-NowButton">
                                        <div class="resource-NowButton-box">
                                            <div class="title"><fmt:message key="page.meeting.button.create"/></div>
                                            <div class="main"><img src="${ctxStatic}/images/icon-succeed.png" alt=""></div>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                </c:when>
                <c:otherwise>
                    <div class="admin-metting-list">

                            <c:forEach items="${page.dataList}" var="course" varStatus="status">
                                ${(status.index) % 3 == 0 ? '<div class="row clearfix meeting-row-hock">':''}
                                <div class="col-lg-4" id="courseView_${course.id}" pwd="${course.password}">

                                    <div class="resource-list-item item-radius clearfix ${course.locked ? 'meeting-lock' : ''}">
                                        <div class="resource-img ">
                                            <c:choose>
                                                <c:when test="${not empty course.coverUrl}">
                                                    <img src="${course.coverUrl}" alt="${course.info}" class="img-response" id="cover_${course.id}">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${ctxStatic}/upload/img/_admin_metting_01.png" alt="${course.info}" class="img-response"  id="cover_${course.id}">
                                                </c:otherwise>
                                            </c:choose>

                                            <div class="resource-link">
                                                <a style="cursor: pointer;" courseId="${course.id}" class="resource-icon-play popup-player-hook">
                                                    <i></i>
                                                    <fmt:message key="page.meeting.button.view"/>
                                                </a><a href="${ctx}/mgr/meet/screen/${course.id}" class="resource-icon-qrcode">
                                                <i></i>
                                                <fmt:message key="page.meeting.button.scan_screen"/>
                                            </a>
                                            </div>
                                            <c:choose>
                                                <c:when test="${course.starRateFlag}">

                                                    <c:choose>
                                                        <c:when test="${course.avgScore > 0}">
                                                            <div class="resource-fixed-icon star-hook" courseId="${course.id}">
                                                                <div class="resource-star "><span class="icon-resource-star">${course.avgScore}<fmt:message key="page.meeting.tips.score.unit"/> </span></div>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="resource-fixed-icon star-hook" courseId="${course.id}">
                                                                <div class="resource-star "><span class="icon-resource-star"><fmt:message key="page.meeting.tips.unrate"/> </span></div>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="resource-fixed-icon info-hook" courseId="${course.id}">
                                                        <div class="resource-icon-info "></div>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>

                                        </div>
                                        <h3 class="resource-title overflowText">${course.title}</h3>
                                        <div class="resource-label">
                                    <span><i class="hot">
                                        <c:choose>
                                            <c:when test="${course.playType == 0}">
                                                ${course.playPage}
                                            </c:when>
                                            <c:otherwise>
                                                ${course.livePage}
                                            </c:otherwise>
                                        </c:choose>
                                    </i><i class="muted">|</i>${course.pageCount}</span>
                                            <span>${course.playTime}</span>
                                            <span>
                                        <c:choose>
                                            <c:when test="${course.playType == 0}"><fmt:message key="page.meeting.tab.record"/></c:when>
                                            <c:otherwise><fmt:message key="page.meeting.tab.live"/> </c:otherwise>
                                        </c:choose>
                                    </span>
                                        <i class="lock ${not empty course.password ? '':'none'}"></i>
                                        </div>
                                        <div class="resource-menu">
                                            <div class="col-lg-6">
                                                <a href="javascript:;" class="contribute-hook" courseId="${course.id}"><fmt:message key="page.meeting.button.delivery"/></a>
                                            </div>
                                            <div class="col-lg-6">
                                                <a href="javascript:;" class="more more-hook" courseId="${course.id}" courseTitle="${course.title}" info="${course.info}" locked="${course.locked == null ? false : course.locked}"><i></i><fmt:message key="page.meeting.button.more"/> </a>
                                            </div>
                                        </div>
                                        <c:if test="${course.locked}">
                                            <div class="meeting-lock-item"></div>
                                        </c:if>
                                    </div>
                                </div>
                                <!-- 如果会议不满6个 则添加一个新建会议图片 -->
                                <c:if test="${fn:length(page.dataList) != 6 && status.index + 1 == fn:length(page.dataList)}">
                                    <c:if test="${(status.index + 1) % 3 == 0 }">
                                        </div>
                                        <div class="row clearfix meeting-row-hock">
                                    </c:if>
                                    <div class="col-lg-4">
                                        <div class="resource-list-item item-radius clearfix">
                                            <a href="${ctx}/mgr/meet/edit" class="resource-NowButton">
                                                <div class="resource-NowButton-box">
                                                    <div class="main"><img src="${ctxStatic}/images/icon-metting-add.png" alt=""></div>
                                                    <div class="title"><fmt:message key="page.meeting.button.create"/></div>
                                                </div>
                                            </a>
                                        </div>
                                    </div>

                                </c:if>
                                ${(status.index + 1) % 3 == 0 || status.index + 1 == fn:length(page.dataList) ? "</div>":""}

                            </c:forEach>

                        <%@include file="../include/pageable.jsp"%>
                        <form id="pageForm" name="pageForm" method="post" action="${ctx}/mgr/meet/list">
                            <input type="hidden" name="pageNum">
                            <input type="hidden" name="keyword" value="${keyword}">
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
    <%@include file="../include/footer.jsp"%>
</div>

<!--弹出 投稿平台-->
<div class="contribute-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong><fmt:message key="page.delivery.tips.flat"/></strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-subName">
            <p><fmt:message key="page.delivery.tips.required"/></p>
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
	                                        <h4 class="overflowText">${a.nickname}</h4>
	                                        <c:if test="${empty a.sign}">
                                                <p class="overflowText-nowrap-multi">&nbsp;</p>
                                            </c:if>
                                            <c:if test="${not empty a.sign}">
                                                <p class="overflowText-nowrap-multi">${a.sign}</p>
                                            </c:if>
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
                    <input type="submit" class="button buttonBlue min-btn" id="submitBtn" value="<fmt:message key='page.delivery.button.confirm'/>">
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
                    <c:choose>
                        <c:when test="${csp_locale eq 'zh_CN'}">
                            <li  class="-mob-share-weixin">
                                <a href="javascript:;">
                                    <img src="${ctxStatic}/images/_wechat-icon.png" alt="">
                                    <p>微信好友</p>
                                </a>
                            </li>
                            <li class="-mob-share-weibo">
                                <a href="javascript:;">
                                    <img src="${ctxStatic}/images/_weibo-icon.png" alt="">
                                    <p>微博</p>
                                </a>
                            </li>
                            <%--<li class="-mob-share-dingtalk">--%>
                                <%--<a href="javascript:;">--%>
                                    <%--<img src="${ctxStatic}/images/_weibo-icon.png" alt="">--%>
                                    <%--<p>钉钉</p>--%>
                                <%--</a>--%>
                            <%--</li>--%>
                        </c:when>
                        <c:otherwise>
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
                            <li class="-mob-share-linkedin" >
                                <a href="javascript:;">
                                    <img src="${ctxStatic}/images/icon-user-linkedin.png" alt="">
                                    <p>LinkedIn</p>
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    <li>
                        <a style="cursor: pointer;" id="copyShareUrlBtn">
                            <img src="${ctxStatic}/images/_copyLink-icon.png" alt="">
                            <p><fmt:message key="page.meeting.button.copy_link"/></p>
                        </a>
                    </li>
                    <%--<li id="copyLi">--%>
                        <%--<a href="javascript:;" class="copy-hook">--%>
                            <%--<img src="${ctxStatic}/images/_copy-icon.png" alt="">--%>
                            <%--<p><fmt:message key="page.meeting.button.duplicate"/></p>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                    <li>
                        <a href="javascript:;" class="lock-hook" onclick="openPasswordView()">
                            <img src="${ctxStatic}/images/_lock-icon.png" alt="">
                            <p><fmt:message key="page.meeting.button.watch.password"/></p>
                        </a>
                    </li>

                    <li id="editLi">
                        <a href="javascript:;" onclick="edit()">
                            <img src="${ctxStatic}/images/_edit-icon.png" alt="">
                            <p><fmt:message key="page.meeting.button.edit"/></p>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;" onclick="delCourse()">
                            <img src="${ctxStatic}/images/_delete-icon.png" alt="">
                            <p><fmt:message key="page.meeting.button.del"/></p>
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
                    <input type="button" class="button login-button buttonBlue last" id="copyBtn" value="<fmt:message key='page.meeting.copy.confirm'/>">
                </div>
        </div>
    </div>
</div>

<!--弹窗密码框-->
<div class="lock-popup-box">
    <div class="layer-hospital-popup lock-popup clearfix">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="lock-popup-main login-form-item pr">
                    <label for="randomNum" class="cells-block pr ">
                        <input id="randomNum" type="text" class="login-formInput" value="" placeholder="<fmt:message key='page.meeting.tips.watch.password.holder'/>" maxlength=4>
                        <span href="javascript:;" class="code" id="btnSendCode" onclick="randomNum()"><fmt:message key='page.meeting.button.auto.create'/></span>
                    </label>
                    <span class="cells-block hiht"><fmt:message key='page.meeting.tips.watch.password'/></span>
                    <span class="cells-block error none" id="passwordError"><fmt:message key='page.meeting.tips.watch.password.holder'/></span>
                    <div class="layer-hospital-popup-bottom">
                        <div class="fr">
                            <span class="button min-btn layui-layer-close"><fmt:message key="page.common.cancel"/> </span>
                            <a href="javascript:;" class="button buttonBlue min-btn lock-succeed-hook" value="" onclick="modifyPassword()"><fmt:message key="page.meeting.button.password.sure"/> </a>
                        </div>
                    </div>
                </div>

            </form>
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
                    <p><img src="${ctxStatic}/images/question-32x32.png" alt=""><fmt:message key="page.common.delete.confirm"/></p>

                </div>

            </form>
        </div>
    </div>
</div>

<!--弹出 提示-->
<div class="cancel-popup-box" id="meetCountOut">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><a ><img src="${ctxStatic}/images/popup-close.png" alt=""></a></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="cancel-popup-main">
                    <p><fmt:message key="meet.error.count.out"/></p>
                </div>
            </form>
        </div>
    </div>
</div>

<!--弹出有未完成课件 提示-->
<div class="cancel-popup-box" id="undoneWarn">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><a ><img src="${ctxStatic}/images/popup-close.png" alt=""></a></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="cancel-popup-main">
                    <p><fmt:message key="page.meeting.undone.warn"/></p>
                </div>
            </form>
        </div>
    </div>
</div>

<!--弹出 充值-->
<div class="cancel-popup-box" id="pkBuyMsg">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><a href="${ctx}/mgr/meet/list"><img src="${ctxStatic}/images/popup-close.png" alt=""></a></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form >
                <div class="cancel-popup-main">
                    <p>请在充值页面完成付款，付款完成前请不要关闭此窗口</p>
                    <div class="admin-button t-right">
                        <a href="${ctx}/mgr/meeting/list"  class="button color-blue min-btn layui-layer-close" >付款遇到问题，重试</a>
                        <input type="submit"  type="reLoad" class="button buttonBlue item-radius min-btn"  value="我已付款成功">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!--弹出 提示-->
<div class="cancel-popup-box" id="pkSuccessMsg">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close clearMsg"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="cancel-popup-main">
                    <p id="backMsg"></p>
                </div>
                <div class="admin-button t-right " >
                    <input type="button" class="button buttonBlue item-radius min-btn layui-layer-close clearMsg" value="知道了"/>
                </div>
            </form>
        </div>
    </div>
</div>


<!--弹窗星评-->
<div class="layer-grade-star-box" id="starRate">
    <div class="layer-hospital-popup layer-grade-popup clearfix">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <div class="metting-grade-info ">
                <div class="title"></div>
                <div class="main"></div>
            </div>
            <div class="metting-star none">
                <div class="star-title"><fmt:message key="page.meeting.multiple.score"/> </div>
                <div class="star-box star-max"><div class="star"><span class="null maxStar"></span><span class="null maxStar"></span><span class="null maxStar"></span><span class="null maxStar"></span><span class="null maxStar"></span></div><div class="grade "><span id="avgScoreSpan">0</span><fmt:message key="page.meeting.tips.score.unit"/></div></div>
                <div class="star-list  clearfix">
                    <div class="star-list-row clearfix" index="0">
                        <div class="fr">
                            <div class="star-box star-min"><div class="star"><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span></div><div class="grade "><span class="detailScore">0</span><fmt:message key="page.meeting.tips.score.unit"/></div></div>
                        </div>
                        <div class="fl"> </div>
                    </div>
                    <div class="star-list-row clearfix" index="1">
                        <div class="fr">
                            <div class="star-box star-min"><div class="star"><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span></div><div class="grade "><span class="detailScore">0</span><fmt:message key="page.meeting.tips.score.unit"/></div></div>
                        </div>
                        <div class="fl"> </div>
                    </div>
                    <div class="star-list-row clearfix" index="2">
                        <div class="fr">
                            <div class="star-box star-min"><div class="star"><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span></div><div class="grade "><span class="detailScore">0</span><fmt:message key="page.meeting.tips.score.unit"/></div></div>
                        </div>
                        <div class="fl"> </div>
                    </div>
                    <div class="star-list-row clearfix" index="3">
                        <div class="fr">
                            <div class="star-box star-min"><div class="star"><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span></div><div class="grade "><span class="detailScore">0</span><fmt:message key="page.meeting.tips.score.unit"/></div></div>
                        </div>
                        <div class="fl"> </div>
                    </div>
                    <div class="star-list-row clearfix" index="4">
                        <div class="fr">
                            <div class="star-box star-min"><div class="star"><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span></div><div class="grade "><span class="detailScore">0</span><fmt:message key="page.meeting.tips.score.unit"/></div></div>
                        </div>
                        <div class="fl"> </div>
                    </div>
                    <div class="star-list-row clearfix">
                        <div class="fr">
                            <div class="star-box star-min"><div class="star"><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span></div><div class="grade "><span class="detailScore">0</span><fmt:message key="page.meeting.tips.score.unit"/></div></div>
                        </div>
                        <div class="fl"> </div>
                    </div>
                </div>
                <div class="footer-row"><fmt:message key="page.meeting.star.rate.attend"/><span id="scoreCount">0</span><fmt:message key="page.meeting.tips.score.user.unit"/></div>
            </div>
        </div>
    </div>
</div>

<!--密码框弹出成功-->
<div class="lock-popup-box-succeed">
    <div class="layer-hospital-popup lock-popup clearfix">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="lock-popup-main login-form-item pr">
                    <div class="cells-block t-center">
                        <p><img src="${ctxStatic}/images/icon-succeed.png" alt=""></p>
                        <p class="hiht"><fmt:message key="page.meeting.tips.password.success"/></p>
                    </div>
                    <div class="cells-block lock-popup-showRandomNum"></div>
                    <span class="cells-block hiht t-center"><fmt:message key="page.meeting.tips.password.delete"/></span>
                    <div class="layer-hospital-popup-bottom clearfix">
                        <div class="fr">
                            <a href="javascript:;" type="submit" class="button buttonBlue min-btn lock-hook" onclick="deletePassword()"><fmt:message key="page.meeting.button.password.cancel"/></a>
                        </div>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>



<script>
    //随机数函数（根据自己的思路来，我这里只是简单呈现效果）
    var randomNum = function() {
        var num = "";
        for(var i=0;i<4;i++){
            num += Math.floor(Math.random()*10)
        }
        $('#randomNum').val(num);

        console.log($('#randomNum').val());
        $('.lock-popup-showRandomNum').text(num)

    }


    function openPasswordView(){
        var pwd = $("#courseView_" + courseId).attr("pwd");
        $("#passwordError").addClass("none");
        $("#randomNum").val(pwd);
        //弹出观看密码
        layer.open({
            type: 1,
            area: ['609px', '328px'],
            fix: false, //不固定
            title:false,
            anim:5,
            isOutAnim: false,
            closeBtn:0,
            shadeClose:true,
            content: $('.lock-popup-box'),
            success:function(){
                layer.close(layer.index-1);
            },
            cancel :function(){
                $("#passwordError").addClass("none");
            }
        });


    }

    function modifyPassword(){
        var pwd = $.trim($("#randomNum").val());
        if(pwd == ''){
            $("#passwordError").removeClass("none");
            return;
        }
        ajaxGet('${ctx}/mgr/meet/password/modify/'+courseId, {"password":pwd}, function(data){
            if (data.code == 0){
                $("#courseView_" + courseId).find(".lock").removeClass("none");
                $("#courseView_" + courseId).attr("pwd", pwd);
                $(".lock-popup-showRandomNum").text(pwd);
                openConfirmPasswordView();
            } else {
                layer.msg(data.err);
            }

        });
    }

    function openConfirmPasswordView(){
        //弹出观看密码成功
        layer.open({
            type: 1,
            area: ['609px', '430px'],
            fix: false, //不固定
            title:false,
            closeBtn:0,
            shadeClose:true,
            content: $('.lock-popup-box-succeed'),
            success:function(){
                layer.close(layer.index-1);
                //清空原来已设置的密码
                $('#randomNum').val('');
            },
            cancel :function(){
                layer.closeAll();
            },
        });

    }

    function deletePassword(){
        ajaxGet('${ctx}/mgr/meet/password/del/'+courseId, {}, function(data){
            if (data.code == 0){
                $("#courseView_" + courseId).find(".lock").addClass("none");
                $("#courseView_" + courseId).attr("pwd", "");
                openPasswordView();
            } else {
                layer.msg(data.err);
            }

        });
    }

    $(function(){

        function handleMultipleResult(result){
            var $maxStar = $(".star-max").find(".star");
            $maxStar.find(".maxStar").removeClass("full").removeClass("half").addClass("null");
            $("#avgScoreSpan").text(0);
            $("#scoreCount").text(0);
            if(result != undefined && result.avgScore > 0){
                var avgScore = result.avgScore;
                $("#avgScoreSpan").text(avgScore);
                $("#scoreCount").text(result.scoreCount);
                var index = 1;
                $maxStar.find(".maxStar").removeClass("full").removeClass("half").addClass("null");
                if(avgScore > 0){
                    $maxStar.find(".maxStar").each(function(){
                        if(avgScore > index){
                            $(this).removeClass("null").addClass("full");
                        } else if(avgScore == index){
                            $(this).removeClass("null").addClass("full");
                            return false;
                        } else {
                            $(this).removeClass("null").addClass("half");
                            return false;
                        }
                        index ++;
                    });
                }

            }

        }


        function handleDetailResult(result){
            $(".detailScore").text("0");
            $(".star-list-row .fl").text(" ");
            $(".star-list-row").addClass("none");
            if (result != undefined && result.length > 0){
                for(var index in result){
                    var detail = result[index];
                    var avgScore = detail.avgScore;
                    var $currentRow = $(".star-list-row[index='"+index+"']");
                    $currentRow.find(".fl").text(" " + detail.title);
                    $currentRow.find(".detailScore").text(avgScore);
                    var index = 1;
                    $currentRow.find(".star").find("span").removeClass("full").removeClass("half").addClass("null");
                    if(avgScore > 0){
                        $currentRow.find(".star").find("span").each(function(){
                            if(avgScore > index){
                                $(this).removeClass("null").addClass("full");
                            } else if(avgScore == index){
                                $(this).removeClass("null").addClass("full");
                                return false;
                            } else {
                                $(this).removeClass("null").addClass("half");
                                return false;
                            }
                            index ++;
                        });
                    }
                    $currentRow.removeClass("none");
                }
            }
        }

        $(".star-hook, .info-hook").click(function(){
            var loading = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            var id = $(this).attr("courseId");
            ajaxGet('${ctx}/mgr/meet/course_info/' + id, {}, function(data){
                $(".metting-grade-info").find(".title").text(data.data.title);
                $(".metting-grade-info").find(".main").text(data.data.info);
                handleMultipleResult(data.data.multipleResult);

                handleDetailResult(data.data.detailList);
                if(data.data.starRateFlag){
                    $(".metting-star").removeClass("none");
                } else {
                    $(".metting-star").addClass("none");
                }
                layer.open({
                    type: 1,
                    area: ['670px', '90%'],
                    fix: false, //不固定
                    title:false,
                    anim:5,
                    closeBtn:0,
                    shadeClose:true,
                    content: $('.layer-grade-star-box'),
                    success:function(){
                        layer.close(loading);
                    },
                    cancel :function(){

                    },
                });
            });

        });
    });
</script>
</body>
</html>

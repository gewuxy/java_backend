<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/23
  Time: 10:49
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>会议内容编辑</title>

    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
    <link href="${ctxStatic}/css/daterangepicker.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.css">
    <script src="${ctxStatic}/js/ajaxfileupload.js"></script>
    <script src="${ctxStatic}/js/swiper.jquery.min.js"></script>
    <script src="${ctxStatic}/js/moment.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/js/slide.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/css/audio.css">
    <script src="${ctxStatic}/js/audio.js"></script>
    <script src="${ctxStatic}/js/HZRecorder.js"></script>

    <script>
        var detailId = 0;
        var courseId = "${course.id}";
        var addtype = 2;
        var meetId = '${meetId}';
        var moduleId = '${moduleId}';

        var progressError = false;

        var activeIndex = 0;

        $(function(){

            $("#finishBtn").click(function(){
                window.location.href = '${ctx}/func/meet/ppt/finish?meetId=${meetId}&moduleId=${moduleId}&courseId=${course.id}';
            });

            activeIndex = '${param.activeIndex}';
            if(activeIndex == ''){
                activeIndex = 0;
            }
            //幻灯片轮播
            var swiper = new Swiper('.swiper-container', {
                //分页
                pagination: '.swiper-pagination',

                // 按钮
                nextButton: '.swiper-button-next',
                prevButton: '.swiper-button-prev',
                slidesPerView: 2,
                centeredSlides: true,
                paginationClickable: true,
                spaceBetween: 50,
                paginationType: 'fraction',
                simulateTouch : false,
                initialSlide:activeIndex
            });

            var slideDataNum = 0;
            $(".swiper-slide").each(function(){
                $(this).attr("data-num",slideDataNum);
                slideDataNum++;
            });

            function isPic(fileName){
                return fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".jpeg")
            }

            function isVideo(fileName){
                var isVideo = fileName.endsWith(".mp4") || fileName.endsWith(".avi") || fileName.endsWith(".wmv") || fileName.endsWith("mpg") || fileName.endsWith("flv");
                return isVideo;
            }

            $("#addImgFile").change(function(){
                var val = $(this).val();
                val = val.toLowerCase();
                if(!isPic(val) && !isVideo(val)){
                    layer.msg("请上传jpg|png|jpeg<br>或者mp4|avi|wmv|mpg|flv文件");
                    return;
                }
                addPpt(addtype);
            });

            $("#changeAudioFile").change(function(){
                var val = $(this).val();
                if(!val.endsWith(".mp3") && !val.endsWith(".MP3")){
                    layer.msg("请上传mp3文件");
                    return;
                }
                changeAudio();
            });

            $("#changeImgFile").change(function(){
                var val = $(this).val();
                if(!isPic(val) && !isVideo(val)){
                    layer.msg("请上传jpg|png|jpeg<br>或者mp4|avi|wmv|mpg|flv文件");
                    return;
                }
                changePPT();
            });

            $("#delBtn").click(function(){
                var detailId = getCurrentDetailId();
                top.layer.confirm("确定要删除此幻灯片吗？",function(){
                    top.layer.closeAll();
                    window.location.href='${ctx}/func/meet/ppt/del?meetId=${meetId}&moduleId=${moduleId}&detailId='+detailId;
                });
            });

            $(".swiper-slide-metting-move").click(function(){
                openDelDialog($(this).attr("detailId"));
            });

            $("#quoteBtn").click(function(){
                openQuote();
            });

            $(".close-btn-fx").click(function(){
                closeQuote();
            });

            $("#uploadPptBtn").click(function(){
                $('.mask-wrap').addClass('dis-table');
                $('.fx-mask-box-2').show();
            });

            $("#cancelUpload").click(function(){
                $('.mask-wrap').removeClass('dis-table');
                $('.fx-mask-box-2').hide();
            });

            $("#delPPTBtn").click(function(){
                var detailId = $(this).attr("detailId");
                window.location.href='${ctx}/func/meet/ppt/del?meetId=${meetId}&moduleId=${moduleId}&detailId='+detailId;
            });

            $("#uploadPptFile").change(function(){
                var fileName = $(this).val();
                fileName = fileName.substring(fileName.lastIndexOf("/"));
                if(!fileName.toLowerCase().endsWith(".ppt") && !fileName.toLowerCase().endsWith(".pptx")){
                    layer.msg("请选择ppt或者pptx格式的文件");
                    return ;
                }
                progressError = false;
                showUploadPPTProgressDiv();
                $.ajaxFileUpload({
                    url: '${ctx}/func/meet/ppt/uploadPPT?meetId='+meetId+'&courseId='+courseId, //用于文件上传的服务器端请求地址
                    secureuri: false, //是否需要安全协议，一般设置为false
                    fileElementId: "uploadPptFile", //文件上传域的ID
                    dataType: 'json', //返回值类型 一般设置为json
                    success: function (data)  //服务器成功响应处理函数
                    {
                        //回调函数传回传完之后的URL地址
                        if(data.code == 0){
                            window.location.reload();
                        }else{
                            layer.msg(data.err);
                            progressError = true;
                        }
                    },
                    error:function(data, status, e){
                        alert(e);
                        progressError = true
                    }
                });
            });

            //上传Hover 提示
            $(".changeButton-tipsHover-hook").mouseenter(function(){
                layer.tips('可更换1M以内的图片文件或50m以内的视频文件', '.changeButton-tipsHover-hook', {
                    tips: [3, '#333'],
                    time:2000
                });
            });


            $(".changeButton-tipsHover2-hook").mouseenter(function(){
                layer.tips('可添加1M以内的图片文件或50m以内的视频文件', '.changeButton-tipsHover2-hook', {
                    tips: [3, '#333'],
                    time:2000
                });
            });

        });


        function openDelDialog(detailId){
            $("#delPPTBtn").attr("detailId", detailId);
            $('.mask-wrap').addClass('dis-table');
            $('.fx-mask-box-3').show();
        }

        function closeDelDialog(){
            $('.mask-wrap').removeClass('dis-table');
            $('.fx-mask-box-3').hide();
        }


        function showUploadPPTProgressDiv(fileName){
            $('.mask-wrap').removeClass('dis-table');
            $('.fx-mask-box-2').hide();
            $("#showProgress").show();
            $("#uploadFileName").text(fileName);
            showUploadProgress();
        }

        function showUploadProgress(){
            if(!progressError){
                $.get('${ctx}/file/uploadStatus',{}, function (data) {
                    $("#uploadProgress").text(data.data.progress);
                    $("#progressBar").css("width",data.data.progress);
                    console.log("上传进度 = "+data.data.progress);
                    if(data.data.progress != '100.0%'){
                        setTimeout(showUploadProgress(),2000);
                    }else{
                        showConvertProgress();
                    }
                },'json');

            }
        }

        function showConvertProgress(){
            if(!progressError){
                $.get('${ctx}/func/meet/ppt/convertStatus',{}, function (data) {
                    console.log("转换进度 = "+data.data.progress);
                    if(data.data.progress == '100.0%'){
                        $("#uploadStatus").text("转换完成,正在更新数据 ...");
                    }else{
                        $("#progressBar").css("width",data.data.progress);
                        $("#uploadProgress").text(data.data.progress);
                        if(data.data.finished == 0){
                            $("#uploadStatus").text("上传完成,正在转换请稍候...");
                        }else{
                            $("#uploadProgress").text(data.data.progress);
                            $("#uploadStatus").text("共"+data.data.total+"页,正在转换第"+data.data.finished+"页");
                        }
                        setTimeout(showConvertProgress(), 2000);
                    }
                },'json');
            }
        }

        function closeQuote(){
            $("#quoteFrame").attr("src","");
            $('.mask-wrap').removeClass('dis-table');
            $('.fx-mask-box-1').hide();
        }

        function openQuote(){
            $("#quoteFrame").attr("src","${ctx}/func/meet/delivery/forCSP?meetId=${meetId}&moduleId=${moduleId}");
            $('.mask-wrap').addClass('dis-table');
            $('.fx-mask-box-1').show();
        }

        function getCurrentDetailId(){
            var did = $(".swiper-slide-active").attr("detailId");
            if(did == undefined){
                did = 0;
            }
            return did;
        }

        function getCurrentIndex(){
            var currindex = $(".swiper-slide-active").attr("data-num");
            if(currindex == undefined){
                currindex = -1;
            }
            currindex = parseInt(currindex);
            return currindex;
        }

        function changeAudio(){
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajaxFileUpload({
                url: '${ctx}/func/meet/ppt/changeAudio?courseId='+courseId+'&detailId='+getCurrentDetailId(), //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: "changeAudioFile", //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                    layer.close(index);
                    //回调函数传回传完之后的URL地址
                    if(data.code == 0){
                        window.location.href='${ctx}/func/meet/config?meetId='+meetId+'&moduleId='+moduleId+'&activeIndex='+getCurrentIndex();
                    }else{
                        layer.msg(data.msg);
                    }
                },
                error:function(data, status, e){
                    layer.msg(e);
                    layer.close(index);
                }
            });
        }

        function changePPT(){
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajaxFileUpload({
                url: '${ctx}/func/meet/ppt/changeImg?courseId='+courseId+'&detailId='+getCurrentDetailId(), //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: "changeImgFile", //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                    layer.close(index);
                    //回调函数传回传完之后的URL地址
                    if(data.code == 0){
                        window.location.href='${ctx}/func/meet/config?meetId='+meetId+'&moduleId='+moduleId+'&activeIndex='+getCurrentIndex();
                    }else{
                        layer.msg(data.msg);
                    }
                },
                error:function(data, status, e){
                    layer.msg(e);
                    layer.close(index);
                }
            });
        }

        function addPpt(addtype){
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajaxFileUpload({
                url: '${ctx}/func/meet/ppt/add?courseId='+courseId+'&meetId='+meetId+'&addtype='+addtype+'&moduleId='+moduleId+'&detailId='+getCurrentDetailId(), //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: "addImgFile", //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                    layer.close(index);
                    //回调函数传回传完之后的URL地址
                    if(data.code == 0){
                        window.location.href='${ctx}/func/meet/config?meetId='+meetId+'&moduleId='+moduleId+'&activeIndex='+(getCurrentIndex()+1);
                    }else{
                        layer.msg(data.msg);
                    }
                },
                error:function(data, status, e){
                    layer.msg(e);
                    layer.close(index);
                }
            });
        }

    </script>
    <script>
        $(function(){
            //绑定按钮
            $('.record-hook').bind("click",startRecording);
            $(".recordEnd-hook").bind('click',uploadAudio);



            ///////初始化播放器
            var asAllItem = audiojs.createAll();
            var currentAudio = $(".swiper-slide-active").attr("audio-src");
            //默认加载
            playAudio(currentAudio);
            //切换加载对应的路径
            $(".swiper-button-next,.swiper-button-prev").on('click',function(){
                var swiperCurrent = $(".swiper-slide-active");
                var dataSrc = swiperCurrent.attr('audio-src');
                playAudio(dataSrc);
            });


            function playAudio(dataSrc){
                if(asAllItem[0]){
                    asAllItem[0].pause();
                    if(dataSrc == ''){
                        $(".audio-metting-box").hide();
                    }else{
                        dataSrc = "${appFileBase}"+dataSrc;
                        $(".audio-metting-box").show();
                        asAllItem[0].load(dataSrc);
                        asAllItem[0].play();
                    }
                }
            }

            //录音按钮
            function startRecording() {
                HZRecorder.get(function (rec) {
                    recorder = rec;
                    recorder.start();
                });
                //按钮状态
                $(this).addClass('none');
                $(this).next('.recordEnd-hook').removeClass('none');
            }

            function uploadAudio() {
                var index = layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
                recorder.upload("${ctx}/func/meet/ppt/uploadRecord?courseId=${course.id}&detailId="+getCurrentDetailId(), function (state, e) {
                    switch (state) {
                        case 'uploading':
                            //var percentComplete = Math.round(e.loaded * 100 / e.total) + '%';
                            break;
                        case 'ok':
                            //alert(e.target.responseText);
                            //layer.msg(e.code);
                            window.location.href='${ctx}/func/meet/config?meetId='+meetId+'&moduleId='+moduleId+'&activeIndex='+getCurrentIndex();
                            break;
                        case 'error':
                            layer.close(index);
                            layer.msg("上传失败");
                            break;
                        case 'cancel':
                            layer.close(index);
                            layer.msg("上传被取消");
                            break;
                    }
                });
                //按钮状态
                $(this).addClass('none');
                $(this).prev('.record-hook').removeClass('none');
            }

        });
    </script>
</head>
<body>
<div class="g-main  mettingForm mettingSwiperBox clearfix">
    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-09.png" alt="">
                </div>
                <div class="oh">
                    <p><strong>会议发布</strong></p>
                    <p>快速发布在线会议，下载手机app端即可查看PPT、直播、调研、考试等会议</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->
    <%@include file="meetAddHead.jsp"%>
    <div class="tab-bd">
        <div class=" clearfix metting-process">
            <div class=" clearfix link-justified metting-process-01">
                <ul>
                    <li class="cur">创建会议</li>
                    <li>设置功能</li>
                    <li>发布预览</li>
                </ul>
            </div>
        </div>
        <%@include file="funTab.jsp"%>

        <div id="showProgress" style="display: none;">

            <div class="metting-progreesItem clearfix">
                <p><h3 id="uploadFileName"></h3></p>
                <p>上传文件的过程中，请勿关闭或刷新网页</p>
                <p><span class="metting-progreesBar"><i style="width:0%" id="progressBar"></i></span>&nbsp;&nbsp;<span id="uploadProgress">10%</span>&nbsp;&nbsp;<span class="color-blue" id="uploadStatus"></span></p>

            </div>
            <div class="formrow-hr"></div>
        </div>
        <div class="tab-subPage-bd swiperBox clearfix">
            <div class="metting-swiper">
                <c:choose>
                    <c:when test="${course == null || fn:length(course.details) == 0}">
                        <div class="swiper-container swiper-container-horizontal swiper-slide-notImg">
                            <div class="swiper-wrapper">
                                <div class="swiper-slide swiper-slide-active notImg" data-num="0" style="width: 544.5px; margin-right: 50px;">
                                </div>
                            </div>
                            <!-- Add Pagination -->
                            <div class="swiper-pagination swiper-pagination-fraction"><span class="swiper-pagination-current">1</span> / <span class="swiper-pagination-total">1</span></div>
                            <div class="metting-btn-item">
                                <span class="swiper-button-prev metting-button isImgButton swiper-button-disabled ">上一页</span>
                                <label class="isImgButton-false"><input type="file" name="file" detailId="0" id="addImgFile" class="none"><span class="metting-changeImage-btn metting-button bottom-blue"><span class="icon iconfont icon-minIcon18"></span>&nbsp;添加幻灯片</span></label>

                                <span class="swiper-button-next metting-button isImgButton">下一页</span>
                            </div>

                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="swiper-container swiper-container-horizontal">
                                <div class="swiper-wrapper" style="transform: translate3d(297.25px, 0px, 0px); transition-duration: 0ms;">
                                <c:forEach items="${course.details}" var="detail" varStatus="status">
                                    <div class="swiper-slide swiper-slide-active" data-num="${status.index}" style="width: 544.5px; margin-right: 50px;" detailId="${detail.id}" audio-src="${detail.audioUrl}">
                                        <c:choose>
                                            <c:when test="${not empty detail.videoUrl}">
                                                <video src="${appFileBase}/${detail.videoUrl}" width="720" height="280" controls poster="${appFileBase}/${detail.imgUrl}"></video>
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${appFileBase}/${detail.imgUrl}" alt="">
                                            </c:otherwise>
                                        </c:choose>

                                        <div class="swiper-slide-metting-move" detailId="${detail.id}"><a href="javascript:;" class="delFile fx-btn-3"><span class="icon iconfont icon-minIcon16"></span>删除</a></div>
                                        <%--<div class="swiper-slide-metting-audio">--%>
                                            <%--<audio controls="" src="${appFileBase}/${detail.audioUrl}"></audio>--%>
                                        <%--</div>--%>
                                    </div>
                                </c:forEach>
                                </div>

                                <!-- Add Pagination -->
                                <div class="swiper-pagination swiper-pagination-fraction"><span class="swiper-pagination-current">1</span> / <span class="swiper-pagination-total">${fn:length(course.details)}</span></div>
                                <div class="metting-btn-item">
                                    <span class="swiper-button-prev metting-button swiper-button-disabled">上一页</span>
                                    <label><input type="file" name="file" id="changeImgFile" class="none"><span class="metting-changeImage-btn metting-button bottom-blue changeButton-tipsHover-hook"><span class="icon iconfont icon-minIcon18"></span>&nbsp;更换</span></label>
                                    <label><input type="file" name="file" class="none" id="addImgFile"><span class="metting-changeImage-btn metting-button bottom-blue changeButton-tipsHover2-hook"><span class="icon iconfont icon-minIcon18"></span>&nbsp;添加</span></label>

                                    <c:if test="${empty live}">
                                        <label><input type="file" name="file" id="changeAudioFile" class="none">
                                            <span class="metting-button bottom-blue"><span class="icon iconfont icon-minIcon9"></span>&nbsp;上传音频</span></label>
                                        <a href="javascript:;" class="metting-button bottom-blue record-hook"><span class="icon iconfont icon-minIcon21"></span>&nbsp;开始录音</a>
                                    </c:if>
                                    <a href="javascript:;" class="metting-button bottom-blue recordEnd-hook none"><span class="icon iconfont icon-minIcon20"></span>&nbsp;完成录音</a>
                                    <span class="swiper-button-next metting-button">下一页</span>
                                </div>

                        </div>

                        <c:if test="${empty live}">
                            <div class="clearfix t-center" style="padding: 0 0 40px; min-height: 80px;">
                                <!--<ul id="recordingslist"></ul>-->
                                <div class="audio-metting-box" style="">
                                    <audio controls=true id="video1"></audio>
                                </div>

                            </div>
                        </c:if>

                    </c:otherwise>
                </c:choose>

                <!-- Swiper -->
        </div>
        <div class="buttonArea clearfix" style="margin: 20px 25px 40px;">
            <div class="formrow">
                <div class="fr clearfix">
                    <input type="button" id="uploadPptBtn" class="formButton formButton-max fx-btn-2" value="上传PPT">
                </div>
                <div class="fl clearfix">
                    <input type="button" class="formButton formButton-max" onclick="window.location.href='${ctx}/func/meet/edit?id=${meetId}'" value="上一步">&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" class="formButton formButton-max" id="finishBtn" value="下一步">
                </div>
            </div>
        </div>
    </div>
</div>
<div class="mask-wrap">
    <div class="dis-tbcell">
        <div class="distb-box distb-box-tips fx-mask-box-2">
            <div class="mask-hd clearfix">
                <h3 class="font-size-1">温馨提示</h3>
                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
            </div>
            <div class="mask-share-box">
                <p class="top-txt color-black">会覆盖当前的PPT文件，是否继续？</p>
                <p class="top-txt color-blue" style="font-size:14px;">建议将ppt格式另存为pptx格式再上传!</p>
            </div>
            <div class="sb-btn-box p-btm-1 t-right">
                <label for="uploadPptFile" class="button color-blue">继续
                    <input id="uploadPptFile" name="file" type="file" class="none">
                </label>
                <label class="button" id="cancelUpload">取消</label>
            </div>
        </div>
        <div class="distb-box distb-box-tips fx-mask-box-3">
            <div class="mask-hd clearfix">
                <h3 class="font-size-1">温馨提示</h3>
                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
            </div>
            <div class="mask-share-box">
                <p class="top-txt color-black">删除当前页面(包含本页图片与录音)，是否删除？</p>
            </div>
            <div class="sb-btn-box p-btm-1 t-right">
                <button class="close-button-fx cur" id="delPPTBtn" detailId="0">删除</button>
                <button class="close-button-fx" onclick="closeDelDialog()">否</button>
            </div>
        </div>

        <div class="distb-box metting-pupop-box distb-box-max fx-mask-box-1">
            <div class="mask-hd clearfix">
                <h3 class="font-size-1">转载资源</h3>
                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
            </div>
            <div class="tab-wrap popup-tab-list">
                <span class="tab-menu tab-cur">CSP投屏</span>
                <span class="tab-menu">共享资源库</span>
                <span class="tab-menu">获取历史</span>
            </div>
            <iframe id="quoteFrame" name="quoteFrame" frameborder="0" width="100%" height="620" scrolling="false"/>
        </div>
    </div>
</div>


</body>
</html>

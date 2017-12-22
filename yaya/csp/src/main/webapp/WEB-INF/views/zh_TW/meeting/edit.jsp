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
    <title>發布會議-CSPmeeting</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">


    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.css">
    <link rel="stylesheet" href="${ctxStatic}/css/audio.css">
    <link rel="stylesheet" href="${ctxStatic}/css/daterangepicker.css">

    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
    <style>
        html,body { background-color:#F7F9FB;}
    </style>

</head>
<body>
<div id="wrapper">
    <%@include file="../include/header.jsp" %>
    <div class="admin-content bg-gray" >
        <div class="page-width clearfix">
            <div class="admin-module clearfix item-radius">
                <div class="row clearfix">
                    <div class="col-lg-5">
                        <div class="upload-ppt-box">
                            <c:choose>
                                <c:when test="${fn:length(course.details) > 0}">
                                    <div class="upload-ppt-area upload-ppt-area-finish logo-watermark">
                                        <img src="${fileBase}${course.details[0].imgUrl}" alt="">
                                        <div <c:if test="${empty watermark || watermark.direction == 2}" >class="logo-watermark-item watermark-position-right "</c:if>
                                             <c:if test="${watermark.direction == 0}" >class="logo-watermark-item watermark-position-left "</c:if>
                                             <c:if test="${watermark.direction == 1}" >class="logo-watermark-item watermark-position-left-bottom "</c:if>
                                             <c:if test="${watermark.direction == 3}" >class="logo-watermark-item watermark-position-right-bottom "</c:if>
                                        >
                                            <div class="logo-watermark-main">
                                                <c:if test="${packageId == 1}">
                                                    <span class="logo-watermark-main-text logo-watermark-tips-hook" default-title='會講'>${empty watermark?"會講":empty watermark.name?"會講":watermark.name}</span>
                                                    <div class="logo-watermark-tips watermark-tips-hook">升級版本，可關閉/自定義水印</div>
                                                    <div class="logo-watermark-tips-border watermark-tips-hook"></div>
                                                </c:if>
                                                <c:if test="${packageId != 1}">
                                                    <span class="logo-watermark-main-text" default-title='會講'>${empty watermark?"會講":empty watermark.name?"會講":watermark.name}</span>
                                                    <div class="logo-watermark-edit watermark-edit-hook">
                                                        <c:if test="${packageId == 3 || packageId == 4}">
                                                            <div class="logo-watermark-input">
                                                                <label for="watermark-input">
                                                                    <input type="text" name="" id="watermark-input" placeholder="輸入水印" maxlength="18">
                                                                </label>
                                                            </div>
                                                        </c:if>
                                                        <div class="logo-watermark-edit-position">
                                                            <div class="logo-watermark-edit-position-title">
                                                                選擇水印顯示位置
                                                            </div>
                                                            <div class="logo-watermark-edit-position-item">
                                                                <label for="positionTopLeft" class="watermark-radio watermark-radio-topLeft ">
                                                                    <input type="radio" name="watermark" class="none" id="positionTopLeft" value="0" >
                                                                    <span class="icon"></span>
                                                                </label>
                                                                <label for="positionTopRight" class="watermark-radio watermark-radio-topRight">
                                                                    <input type="radio" name="watermark" class="none" id="positionTopRight" value="2">
                                                                    <span class="icon"></span>
                                                                </label>
                                                                <label for="positionBottomLeft" class="watermark-radio watermark-radio-bottomLeft">
                                                                    <input type="radio" name="watermark" class="none" id="positionBottomLeft" value="1">
                                                                    <span class="icon"></span>
                                                                </label>
                                                                <label for="positionBottomRight" class="watermark-radio watermark-radio-bottomRight">
                                                                    <input type="radio" name="watermark" class="none" id="positionBottomRight" value="3">
                                                                    <span class="icon"></span>
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:if>

                                                <div class="logo-watermark-border watermark-edit-hook"></div>
                                                <div class="logo-watermark-outerBorder watermark-edit-hook"></div>
                                            </div>

                                        </div>
                                    </div>

                                </c:when>
                                <c:otherwise>
                                    <div class="upload-ppt-area">
                                        <label for="uploadFile">
                                            <input type="file" name="file" class="none" id="uploadFile">
                                            <p class="img"><img src="${ctxStatic}/images/upload-ppt-area-img.png" alt=""></p>
                                            <p>或拖动PDF／PPT到此区域上传</p>
                                        </label>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <div class="upload-main">
                                <div class="metting-progreesItem clearfix t-left none">
                                    <span id="uploadAlt">上傳進度</span> <span class="color-blue" id="progressS">0%</span>
                                    <p><span class="metting-progreesBar"><i style="width:0%" id="progressI"></i></span></p>

                                </div>
                                <div class="admin-button t-center">
                                <c:choose>
                                    <c:when test="${not empty course.details}">
                                        <label for="reUploadFile"><input type="file" name="file" class="none" id="reUploadFile"><span class="button min-btn">重新上傳</span>&nbsp;&nbsp;&nbsp;</label>
                                            <a href="${ctx}/mgr/meet/details/${course.id}" class="button color-blue min-btn">編輯</a>
                                    </c:when>
                                    <c:otherwise>
                                        <label for="reUploadFile2"><input type="file" name="file" class="none" id="reUploadFile2"><span class="button color-blue min-btn" >上傳演講文檔</span></label>
                                    </c:otherwise>
                                </c:choose>
                                </div>
                                <c:if test="${empty course.details}">
                                    <p class="color-gray-02">選擇小於100M的文件</p>
                                </c:if>

                                <span class="cells-block error none" id="detailsError"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;請上傳演講檔案</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-7">
                        <form action="${ctx}/mgr/meet/save" method="post" id="courseForm" name="courseForm">
                            <input type="hidden" name="course.id" value="${course.id}">
                            <input type="hidden" name="watermark.direction" id="direction" value="2">
                            <input type="hidden" name="watermark.state" id="state" value="1">
                            <input type="hidden" name="watermark.name" id="name" value="會講">
                            <div class="meeting-form-item login-form-item">
                                <label for="courseTitle" class="cells-block pr"><input id="courseTitle" type="text" class="login-formInput" name="course.title" placeholder="會議名稱" value="${course.title}"></label>
                                <span class="cells-block error none"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;輸入會議名稱</span>

                                <div class="textarea">
                                    <textarea name="course.info" id="courseInfo" cols="30" maxlength="600" rows="10">${course.info}</textarea>
                                    <p class="t-right" id="leftInfoCount">600</p>
                                </div>
                                <span class="cells-block error none"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;輸入會議簡介</span>

                                <div class="cells-block clearfix meeting-classify meeting-classify-hook">
                                    <span class="subject">分類&nbsp;&nbsp;|<i id="rootCategory">${not empty courseCategory ? courseCategory.parent.nameCn : rootList[0].nameCn}</i></span><span class="office" id="subCategory">${empty course.category ? subList[0].nameCn : course.category}</span>
                                    <input type="hidden" id="courseCategoryId" name="course.categoryId" value="${not empty course.categoryId ? course.categoryId : subList[0].id}">
                                    <input type="hidden" id="courseCategoryName" name="course.category" value="${not empty course.category ? course.category : subList[0].nameCn}">
                                </div>
                                <c:if test="${ not empty course.details && packageId > 1}">
                                    <div class="cells-block meeting-watermark">
                                        <span class="subject">水印&nbsp;&nbsp;<em class="muted">|</em>
                                            <c:if test="${packageId == 2}">
                                                <input type="text" class="classify-inputText expert-text"  placeholder="會講" id="waterName" value="${empty watermark ? "會講":empty watermark.name ?"會講":watermark.name}" disabled >
                                            </c:if>
                                            <c:if test="${packageId > 2}">
                                                <input type="text" class="classify-inputText" placeholder="輸入水印" id="waterName"  value="${empty watermark ? "會講":watermark.name}" maxlength="18">
                                            </c:if>
                                            <div class="weui-cell__ft">
                                                <label for="switchCP" class="mui-switch-box">
                                                    <input type="checkbox" name="" id="switchCP"  class="mui-switch none" <c:if test="${empty watermark || watermark.state == true}">checked</c:if> >
                                                    <div class="weui-switch-cp__box"></div>
                                                </label>
                                            </div>
                                        </span>
                                    </div>

                                </c:if>

                                <div class="meeting-tab clearfix">
                                    <label for="recorded" class="recorded-btn ${course.playType == 0 ? 'cur' : ''}">
                                        <input id="recorded" type="radio" name="course.playType" value="0" ${course.playType == null || course.playType == 0 ?'checked':''}>
                                        <div class="meeting-tab-btn"><i></i>投屏錄播</div>

                                    </label>
                                    <label for="live" class="live-btn ${course.playType > 0 ? 'cur' : ''}" >
                                        <input id="live" type="radio" name="course.playType" value="1" ${course.playType > 0 ? 'checked' : ''}>
                                        <div class="meeting-tab-btn"><i></i>投屏直播</div>
                                    </label>
                                        <div class="meeting-tab-main ${course.playType == 0 ? 'none':''}">
                                            <div class="clearfix">
                                                <div class="formrow">
                                                    <div class="formControls">
                                                            <span class="time-tj">
                                                                <label for="" id="timeStart">
                                                                    時間<input type="text"  readonly class="timedate-input " name="liveTime" placeholder="開始時間 - 結束時間"
                                                                    <c:if test="${not empty live.startTime}">value="<fmt:formatDate value="${live.startTime}" pattern="yyyy/MM/dd HH:mm:ss"/> 至 <fmt:formatDate value="${live.endTime}" pattern="yyyy/MM/dd HH:mm:ss"/>"</c:if>
                                                                >
                                                                </label>
                                                            </span>
                                                        <span class="cells-block error none"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;開始時間至少為發佈當天後一天的0點，時長最長24小時</span>
                                                        <input type="hidden" ${course.playType == 0 ? 'disabled':''} name="live.startTime" id="liveStartTime" value="${live.startTime}">
                                                        <input type="hidden" ${course.playType == 0 ? 'disabled':''} name="live.endTime" id="liveEndTime" value="${live.endTime}">
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="cells-block clearfix checkbox-box" style="display: none;">
                                                    <span class="checkboxIcon">
                                                        <input type="checkbox" id="popup_checkbox_2" name="openLive" value="1" class="chk_1 chk-hook" ${course.playType == 2 ? 'checked' : ''} disabled>
                                                        <label for="popup_checkbox_2" class="popup_checkbox_hook"><i class="ico checkboxCurrent"></i>&nbsp;&nbsp;開啟視頻直播</label>
                                                    </span>
                                                <div class="checkbox-main">
                                                    <p>直播碼率為500kbps的情况下，直播時長為1小時，觀看人數為100人，則消耗的流量約為：22.5GB</p>
                                                    <div class="text">流量剩餘<span class="color-blue" id="myFlux">${flux == null ? 0 : flux}</span>G <a href="${ctx}/mgr/user/toFlux" target="_blank" class="cancel-hook">立即充值</a></div>
                                                </div>
                                            </div>
                                        </div>
                                </div>

                                <%--<span class="cells-block error one"><img src="images/login-error-icon.png" alt="">&nbsp;输入正确密码</span>--%>
                                <input type="button" class="button login-button buttonBlue last" value="確認提交">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@include file="../include/footer.jsp"%>
</div>

<div class="meeting-classify-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>選擇分類</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <div class="metting-classify-popup-main">
                <div class="fl clearfix">

                    <div class="metting-classify-popup-tab">
                        <ul id="rootList">
                            <c:set var="rootId" value="${rootList[0].id}"/>
                            <c:forEach items="${rootList}" var="c" varStatus="status">
                                <li cid="${c.id}"
                                    <c:choose>
                                        <c:when test="${not empty courseCategory}">
                                            <c:if test="${courseCategory.parentId == c.id }">
                                                class="cur"
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${status.index == 0}">class="cur"</c:if>
                                        </c:otherwise>
                                    </c:choose>
                                ><a href="javascript:void (0);">${c.nameCn}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <div class="oh clearfix">

                    <div class="metting-classify-popup-tab-item">
                        <ul id="subList">

                            <c:forEach items="${subList}" var="cc" varStatus="status">
                            <li parentId="${cc.parentId}" categoryId="${cc.id}"
                                <c:choose>
                                    <c:when test="${not empty courseCategory}">
                                        <c:if test="${cc.id == courseCategory.id}">
                                            class="cur"
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${status.index == 0}">class="cur"</c:if>
                                    </c:otherwise>
                                </c:choose>

                                <c:if test="${cc.parentId != rootId}">style="display: none;" </c:if>
                            ><a href="javascript:void (0);">${cc.nameCn}</a></li>
                            </c:forEach>

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--弹出 充值-->
<div class="cancel-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
                <div class="cancel-popup-main">
                    <p>請在充值頁面完成付款，付款完成前請不要關閉此窗口</p>
                    <div class="admin-button t-right">
                        <a href="javascript:;" class="button color-blue min-btn layui-layer-close" >付款遇到問題，重試</a>
                        <input type="button" onclick="freshFlux()" class="button buttonBlue item-radius min-btn" value="我已付款成功">
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>


<script src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script src="${ctxStatic}/js/moment.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
<script src="${ctxStatic}/js/jquery.daterangepicker.js"></script>
<script src="${ctxStatic}/js/ajaxUtils.js"></script>
<script>
    const file_size_limit = 100*1024*1024;

    var uploadOver = false;

    $("#uploadFile, #reUploadFile, #reUploadFile2").change(function(){
        var id = $(this).attr("id");
        uploadFile(document.getElementById(id));
    });

    function freshFlux(){
        ajaxGet("${ctx}/mgr/meet/flux/fresh", {}, function(data){
            $("#myFlux").text(data.data);
            layer.closeAll();
        });
    }

    function fleshPage(){
        var reloadUrl = window.location.href;
        if (reloadUrl.indexOf("?") > 0){
            reloadUrl = reloadUrl + "&time="+new Date().getTime();
        } else {
            reloadUrl = reloadUrl + "?time="+new Date().getTime();
        }
        setTimeout(function (){
            window.location.href = reloadUrl;
        }, 2000);
    }

    function uploadFile(f){
        var fSize = fileSize(f);
        if (fSize > file_size_limit){
            layer.msg("請上傳小於100M的文件");
            return false;
        }
        var fileName = $(f).val().toLowerCase();
        if (!fileName.endWith(".ppt") && !fileName.endWith(".pptx") && !fileName.endWith(".pdf")){
            layer.msg("請選擇ppt|pptx|pdf格式文件");
            return false;
        }
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });

        showUploadProgress();
        $.ajaxFileUpload({
            url: "${ctx}/mgr/meet/upload"+"?courseId=${course.id}", //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: f.id, //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data)  //服务器成功响应处理函数
            {
                layer.close(index);
                if (data.code == 0){
                    //回调函数传回传完之后的URL地址
                    fleshPage();
                } else {
                    uploadOver = true;
                    layer.msg(data.err);
                }
            },
            error:function(data, status, e){
                uploadOver = true;
                alert(e);
                layer.close(index);
            }
        });
    }

    function showUploadProgress(){
        $(".metting-progreesItem").removeClass("none");
        $.get('${ctx}/mgr/meet/upload/progress', {}, function (data) {
            $("#progressS").text(data.data.progress);
            $("#progressI").css("width", data.data.progress);
            if (data.data.progress.indexOf("100") != -1){
                $.get('${ctx}/mgr/meet/upload/clear', {}, function (data1) {
                }, 'json');
                showConvertProgress();
                $("#progressS").text("0%");
                $("#progressI").css("width", "0%");
            } else {
                setTimeout(showUploadProgress, 200);
            }
        }, 'json');
    }


    function showConvertProgress(){
        $.get('${ctx}/mgr/meet/convert/progress', {}, function (data) {
            console.log("convert progress = "+data.data.progress);
            $("#uploadAlt").text("轉換進度");
            $("#progressS").text(data.data.progress);
            $("#progressI").css("width", data.data.progress);
            if (data.data.progress.indexOf("100") != -1){
                $.get('${ctx}/mgr/meet/convert/clear', {}, function (data1) {
                }, 'json');
                fleshPage();
            } else {
                setTimeout(showConvertProgress, 500);
            }
        }, 'json');
    }

    $(function(){
        //拖动上传
        var oFileSpan = $(".upload-ppt-area");					//选择文件框



        //是否需要显示水印
        var needShow = '${watermark.state}';
        if(needShow == undefined || needShow == '' || needShow == "true"){
            $('.logo-watermark-item').show();
        }else{
            $('.logo-watermark-item').hide();
        }


        //拖拽外部文件，进入目标元素触发
        oFileSpan.on("dragenter",function(){
            $(this).css("border-color","#167AFE");
        });

        //拖拽外部文件，进入目标、离开目标之间，连续触发
        oFileSpan.on("dragover",function(){
            return false;
        });

        //拖拽外部文件，离开目标元素触发
        oFileSpan.on("dragleave",function(){
            $(this).css("border-color","#EDF3F9");
        });

        //拖拽外部文件，在目标元素上释放鼠标触发
        oFileSpan.on("drop",function(ev){
            var fs = ev.originalEvent.dataTransfer.files[0];
            uploadByDrag(fs);

            console.log(fs);
            $(this).css("border-color","#167AFE");
            return false;
        });

        function uploadByDrag(f){

            if (!f.name.endWith(".ppt") && !f.name.endWith(".pptx") && !f.name.endWith(".pdf")){
                layer.msg("請選擇ppt|pptx|pdf格式文件");
                return false;
            }

            var filesize = Math.floor((f.size)/1024);
            if(filesize>file_size_limit){
                layer.msg("請上傳小於100M的文件");
                return false;
            }
            //上传

            xhr = new XMLHttpRequest();
            xhr.open("post", "${ctx}/mgr/meet/upload?courseId=${course.id}", true);
            xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");

            var fd = new FormData();
            fd.append('file', f);

            xhr.send(fd);
            showUploadProgress();
        }


        showInfoLeftCount();

        function showInfoLeftCount(){
            var usedLen = $("#courseInfo").val().length;
            $("#leftInfoCount").text(600 - usedLen);
        }

        $("#courseInfo").keyup(function(){
            showInfoLeftCount();
        });

        var contributed = '${empty contributed ? "false" : contributed}';
        if (contributed == 'true'){
            $("input[name='course.playType']").unbind("click");
        } else {
            $("input[name='course.playType']").bind("click",function(){
                var playType = $(this).val();
                $("input[name='course.playType']").removeAttr("checked");
                $(this).prop("checked", "true");
                if (playType == 0){
                    $("#liveStartTime").attr("disabled", "true");
                    $("#liveEndTime").attr("disabled", "true");
                    $(this).parents('.meeting-tab').find(".meeting-tab-main").addClass("none");
                } else {
                    $("#liveStartTime").removeAttr("disabled");
                    $("#liveEndTime").removeAttr("disabled");
                    $(this).parents('.meeting-tab').find(".meeting-tab-main").removeClass("none");
                }
                $(this).parent().siblings().removeClass("cur");
                $(this).parent().addClass("cur");



            });
        }

        $('.cancel-hook').on('click',function(){
            layer.open({
                type: 1,
                area: ['560px', '250px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                anim:5,
                content: $('.cancel-popup-box'),
                success:function(){

                },
                cancel :function(){

                },
            });
        });


        $(".login-button").click(function(){
            var $courseTitle = $("#courseTitle");
            var $courseInfo = $("#courseInfo");
            var $timedate = $(".timedate-input");

            var detailsLength = "${fn:length(course.details)}";
            if (detailsLength == 0){
                $("#detailsError").removeClass("none");
                return;
            }

            if ($.trim($courseTitle.val()) == ''){
                $courseTitle.focus();
                $courseTitle.parent().next(".error").removeClass("none");
                return;
            } else {
                $courseTitle.parent().next(".error").addClass("none");
            }

            if ($.trim($courseInfo.val()) == ''){
                $courseInfo.focus();
                $courseInfo.parent().next(".error").removeClass("none");
                return;
            } else {
                $courseInfo.parent().next(".error").addClass("none");
            }

            var playType = $("input[name='course.playType']:checked").val();
            if (playType == 1){
                var startTime = $("#liveStartTime").val();
                var endTime = $("#liveEndTime").val();
                var dateBeforeNow = new Date(Date.parse(startTime)).getDate() <= new Date().getDate();
                if(startTime >= endTime || Date.parse(endTime) - Date.parse(startTime) > 24 * 3600 * 1000 ||dateBeforeNow){
                    $timedate.focus();
                    $timedate.parent().parent().next(".error").removeClass("none");
                    return;
                } else {
                    $timedate.parent().parent().next(".error").addClass("none");
                }
            } else {
                $timedate.parent().parent().next(".error").addClass("none");
            }

            //设置水印的位置,状态
            if(${packageId == 1}){  //标准版
                if(${not empty watermark}){
                    var watermark = '${watermark}';
                    $("#direction").val(watermark.direction);
                    $("#name").val(watermark.name);
                    $("#state").val(watermark.state);
                }else{
                    $("#direction").val(2);
                    $("#name").val("會講");
                    $("#state").val(1);
                }
            }else{
                var direction = $('input:radio[name="watermark"]:checked').val();
                $("#direction").val(direction);
                var isCheck = $("#switchCP").is(":checked");
                $("#state").val(isCheck ? 1 : 0);
                if(${packageId == 2}){
                    if(${not empty watermark}){
                        $("#name").val('${watermark.name}');
                    }else{
                        $("#name").val("會講");
                    }
                }else{
                    $("#name").val($("#waterName").val() == '' ? "會講":$("#waterName").val());
                }
            }

            $("#courseForm").submit();
        });

        $("#rootList>li").click(function(){
            $("#rootList>li").removeClass("cur");
            $(this).addClass("cur");
            $("#rootCategory").text($(this).find("a").text());
            $("#subList>li").hide();
            $("#subList>li[parentId='"+$(this).attr("cid")+"']").show();
        });

        $("#subList>li").click(function(){
            $("#subList>li").removeClass("cur");
            var categoryId = $(this).attr("categoryId");
            var category = $(this).find("a").text();
            $(this).addClass("cur");
            $("#subCategory").text(category);

            $("#courseCategoryId").val(categoryId);
            $("#courseCategoryName").val(category);
            layer.closeAll();
        });



        $('.meeting-classify-hook').on('click',function(){
            layer.open({
                type: 1,
                area: ['732px', '90%'],
                fix: false, //不固定
                title:false,
                anim:5,
                closeBtn:0,
                content: $('.meeting-classify-popup-box'),
                success:function(){

//弹出层高度 - （标题 + 标题到内容的间距 + 弹出层的内边距)
                    var popupHeight = layero.height() - 85;
                    //触发滚动条控件
                    $('.hidden-box').perfectScrollbar();
                    //设置两栏的高度不超过弹出层高度
                    layero.find('.metting-classify-popup-tab').height(popupHeight * 0.8);
                    layero.find('.metting-classify-popup-tab-item').height(popupHeight * 0.8);

                },
                cancel :function(){

                },
            });
        });


        $('#timeStart').dateRangePicker({
            singleMonth: true,
            showShortcuts: false,
            showTopbar: false,
            startOfWeek: 'monday',
            separator : ' 至 ',
            format: 'YYYY/MM/DD HH:mm:ss',
            autoClose: false,
            startDate:new Date(),
            time: {
                enabled: true
            }
        }).bind('datepicker-first-date-selected', function(event, obj){
            /*首次点击的时间*/
            console.log('first-date-selected',obj);
        }).bind('datepicker-change',function(event,obj){
            console.log('change',obj);
            var timeArray = obj.value.split(" 至 ");
            $("#liveStartTime").val(timeArray[0]);
            $("#liveEndTime").val(timeArray[1]);
            $(this).find('input').val(obj.value);
        });

        showLiveMessage();

        function showLiveMessage(){
            if($(".chk-hook").is(":checked")) {
                $(".checkbox-main").show();
            } else {
                $(".checkbox-main").hide();
            }
        }

        $(".chk-hook").change(function(){
            showLiveMessage();
        });

        var watermarkItem = $('.logo-watermark-item');
        var watermarkItemTitle = watermarkItem.find('.logo-watermark-main-text');
        var watermarkItemEditBr = watermarkItem.find('.watermark-edit-hook');
        var watermarkRadio = watermarkItem.find('.watermark-radio');
        var watermarkIsShow = false;
        var defaultTitle = "會講"

        $('.logo-watermark-tips-hook').hover(function(){
            $('.watermark-tips-hook').show();
        },function(){
            $('.watermark-tips-hook').hide();
        });


        if(${packageId != 1}){
            //水印编辑区显示
            watermarkItemTitle.on('click',function(){
                if(watermarkIsShow == false){
                    watermarkItemEditBr.show();
                    watermarkIsShow = true;
                } else if(watermarkIsShow == true) {
                    watermarkItemEditBr.hide();
                    watermarkIsShow = false;
                }
            });
        }

        //水印位置
        watermarkRadio.off('click').on('click',function(){
            if($(this).hasClass('watermark-radio-topLeft')){
                $(this).parents('.logo-watermark-item').addClass('watermark-position-left').removeClass('watermark-position-right watermark-position-right-bottom watermark-position-left-bottom');
            } else if ($(this).hasClass('watermark-radio-topRight')) {
                $(this).parents('.logo-watermark-item').addClass('watermark-position-right').removeClass('watermark-position-left watermark-position-right-bottom watermark-position-left-bottom');
            } else if ($(this).hasClass('watermark-radio-bottomLeft')) {
                $(this).parents('.logo-watermark-item').addClass('watermark-position-left-bottom').removeClass('watermark-position-right watermark-position-left watermark-position-right-bottom');
            } else if ($(this).hasClass('watermark-radio-bottomRight')) {
                $(this).parents('.logo-watermark-item').addClass('watermark-position-right-bottom').removeClass('watermark-position-right watermark-position-left watermark-position-left-bottom');
            }
            $(this).addClass('radio-on').siblings().removeClass('radio-on');
        });

        //水印里的输入框
        $('.logo-watermark-input').find('input').on('change',function(){
            if($(this).val() == "" || $(this).val() == null) {
                watermarkItemTitle.text(defaultTitle);
                $('.classify-inputText').val("");
            } else {
                watermarkItemTitle.text($(this).val());
                $('.classify-inputText').val($(this).val());
            }
        });

        //表单的输入框
        $('.classify-inputText').on('change',function(){
            if($(this).val() == "" || $(this).val() == null) {
                watermarkItemTitle.text(defaultTitle);
                $('.logo-watermark-input').find('input').val("");
            } else {
                watermarkItemTitle.text($(this).val());
                $('.logo-watermark-input').find('input').val($(this).val());
            }
        });
        $('.mui-switch').on('click',function(){
            if($(this).is(':checked')){
                $('.logo-watermark-item').show();
            } else {
                $('.logo-watermark-item').hide();
            }
        });

        if(${not empty watermark}){
            var direction = "${watermark.direction}";
            if(direction == 0){
                $("#positionTopLeft").attr("checked","checked");
                $("#positionTopLeft").parent().attr("class","watermark-radio watermark-radio-topLeft radio-on ");
            }else if(direction == 1){
                $("#positionBottomLeft").attr("checked","checked");
                $("#positionBottomLeft").parent().attr("class","watermark-radio watermark-radio-bottomLeft radio-on ");
            }else if(direction == 2){
                $("#positionTopRight").attr("checked","checked");
                $("#positionTopRight").parent().attr("class","watermark-radio watermark-radio-topRight radio-on ");
            }else{
                $("#positionBottomRight").attr("checked","checked");
                $("#positionBottomRight").parent().attr("class","watermark-radio watermark-radio-bottomRight radio-on ");
            }
        }else{
            $("#positionTopRight").attr("checked","checked");
            $("#positionTopRight").parent().attr("class","watermark-radio watermark-radio-topRight radio-on ");
        }

    });
</script>
</body>
</html>

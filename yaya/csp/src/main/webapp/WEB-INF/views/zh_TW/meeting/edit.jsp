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
                                    <div class="upload-ppt-area upload-ppt-area-finish">
                                        <img src="${fileBase}${course.details[0].imgUrl}" alt="">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="upload-ppt-area">
                                        <label for="uploadFile">
                                            <input type="file" name="file" class="none" id="uploadFile">
                                            <p class="img"><img src="${ctxStatic}/images/upload-ppt-area-img.png" alt=""></p>
                                            <p>或拖動PDF／PPT到此區域上傳</p>
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
                                        <label for="reUploadFile"><input type="file" name="file" class="none" id="reUploadFile2"><span class="button color-blue min-btn" >上傳演講文檔</span></label>
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
                            <div class="meeting-form-item login-form-item">
                                <label for="courseTitle" class="cells-block pr"><input id="courseTitle" type="text" class="login-formInput" name="course.title" placeholder="會議名稱" value="${course.title}"></label>
                                <span class="cells-block error none"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;輸入會議名稱</span>

                                <div class="textarea">
                                    <textarea name="course.info" id="courseInfo" cols="30" maxlength="600" rows="10">${course.info}</textarea>
                                    <p class="t-right" id="leftInfoCount">600</p>
                                </div>
                                <span class="cells-block error none"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;輸入會議簡介</span>

                                <div class="cells-block clearfix meeting-classify meeting-classify-hook">
                                    <span class="subject">分類&nbsp;&nbsp;|<i id="rootCategory">${rootList[0].nameCn}</i></span><span class="office" id="subCategory">${empty course.category ? subList[0].nameCn : course.category}</span>
                                    <input type="hidden" id="courseCategoryId" name="course.categoryId" value="${course.categoryId}">
                                    <input type="hidden" id="courseCategoryName" name="course.category" value="${course.category}">
                                </div>
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
                                                        <span class="cells-block error none"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;請選擇直播開始結束時間</span>
                                                        <input type="hidden" ${course.playType == 0 ? 'disabled':''} name="live.startTime" id="liveStartTime" value="${live.startTime}">
                                                        <input type="hidden" ${course.playType == 0 ? 'disabled':''} name="live.endTime" id="liveEndTime" value="${live.endTime}">
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="cells-block clearfix checkbox-box">
                                                    <span class="checkboxIcon">
                                                        <input type="checkbox" id="popup_checkbox_2" name="openLive" value="1" class="chk_1 chk-hook" ${course.playType == 2 ? 'checked' : ''}>
                                                        <label for="popup_checkbox_2" class="popup_checkbox_hook"><i class="ico checkboxCurrent"></i>&nbsp;&nbsp;開啟視頻直播</label>
                                                    </span>
                                                <div class="checkbox-main">
                                                    <p>流量消耗每人約1.7G/1小時，例如：本次直播時長30分鐘，如100人在線預計消耗85G流量。</p>
                                                    <div class="text">流量剩餘<span class="color-blue">${flux == null ? 0 : flux}</span>G <a href="${ctx}/mgr/user/toFlux" target="_blank" class="cancel-hook">立即充值</a></div>
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
            <form action="">
                <div class="cancel-popup-main">
                    <p>請在充值頁面完成付款，付款完成前請不要關閉此窗口</p>
                    <div class="admin-button t-right">
                        <a href="javascript:;" class="button color-blue min-btn layui-layer-close" >付款遇到問題，重試</a>
                        <input type="submit" class="button buttonBlue item-radius min-btn" value="我已付款成功">
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>


<script src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script src="${ctxStatic}/js/moment.min.js" type="text/javascript"></script>

<script src="${ctxStatic}/js/jquery.daterangepicker.js"></script>

<script>
    const file_size_limit = 100*1024*1024;

    var uploadOver = false;

    $("#uploadFile, #reUploadFile, #reUploadFile2").change(function(){
        var id = $(this).attr("id");
        uploadFile(document.getElementById(id));
    });

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
                    window.location.reload();
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
                window.location.reload();
            } else {
                setTimeout(showConvertProgress, 500);
            }
        }, 'json');
    }

    $(function(){
        //拖动上传
        var oFileSpan = $(".upload-ppt-area");					//选择文件框

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
                if(startTime == endTime){
                    $timedate.focus();
                    $timedate.parent().parent().next(".error").removeClass("none");
                    return;
                } else {
                    $timedate.parent().parent().next(".error").addClass("none");
                }
            } else {
                $timedate.parent().parent().next(".error").addClass("none");
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
                area: ['732px', '916px'],
                fix: false, //不固定
                title:false,
                anim:5,
                closeBtn:0,
                content: $('.meeting-classify-popup-box'),
                success:function(){



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
    });
</script>
</body>
</html>

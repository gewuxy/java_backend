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
    <title>Release Meeting - CSPmeeting</title>
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
                            <div class="upload-ppt-area">
                                <label for="uploadFile">
                                    <input type="file" name="file" class="none" id="uploadFile">
                                    <p class="img"><img src="${ctxStatic}/images/upload-ppt-area-img.png" alt=""></p>
                                    <p>You can drag PDF/PPT into this area to upload.</p>
                                </label>
                            </div>
                            <div class="upload-main">
                                <div class="metting-progreesItem clearfix t-left none">
                                    <span id="uploadAlt">Progress of Uploading</span> <span class="color-blue" id="progressS">0%</span>
                                    <p><span class="metting-progreesBar"><i style="width:0%" id="progressI"></i></span></p>

                                </div>
                                <div class="admin-button t-center">
                                <c:choose>
                                    <c:when test="${not empty course.details}">
                                            <a href="javascript:;" class="button min-btn" onclick="uploadFile()">Upload Again</a>&nbsp;&nbsp;&nbsp;
                                            <a href="${ctx}/mgr/meet/details/${course.id}" class="button color-blue min-btn">Edit</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="javascript:;" class="button color-blue min-btn"  onclick="uploadFile()">Upload Presentation File</a>
                                    </c:otherwise>
                                </c:choose>
                                </div>
                                <p class="color-gray-02">Please select a file less than 100M.</p>

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-7">
                        <form action="${ctx}/mgr/meet/save" method="post" id="courseForm" name="courseForm">
                            <input type="hidden" name="course.id" value="${course.id}">
                            <div class="meeting-form-item login-form-item">
                                <label for="courseTitle" class="cells-block pr"><input id="courseTitle" type="text" class="login-formInput" name="course.title" placeholder="Meeting Name" value="${course.title}"></label>
                                <span class="cells-block error none"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;Input meeting name</span>

                                <div class="textarea">
                                    <textarea name="course.info" id="courseInfo" cols="30" maxlength="600" rows="10">${course.info}</textarea>
                                    <p class="t-right" id="leftInfoCount">600</p>
                                </div>
                                <span class="cells-block error none"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;Input session introduction</span>

                                <div class="cells-block clearfix meeting-classify meeting-classify-hook">
                                    <span class="subject">Category&nbsp;&nbsp;|<i id="rootCategory">${rootList[0].nameCn}</i></span><span class="office" id="subCategory">${empty course.category ? subList[0].nameEn : course.category}</span>
                                    <input type="hidden" id="courseCategoryId" name="course.categoryId" value="${course.categoryId}">
                                    <input type="hidden" id="courseCategoryName" name="course.category" value="${course.category}">
                                </div>
                                <div class="meeting-tab clearfix">
                                    <label for="recorded" class="recorded-btn ${course.playType == 0 ? 'cur' : ''}">
                                        <input id="recorded" type="radio" name="course.playType" value="0">
                                        <div class="meeting-tab-btn"><i></i>Projective Recording</div>

                                    </label>
                                    <label for="live" class="live-btn ${course.playType > 0 ? 'cur' : ''}" >
                                        <input id="live" type="radio" name="course.playType" value="1">
                                        <div class="meeting-tab-btn"><i></i>Projective Live Stream</div>
                                        <div class="meeting-tab-main ${course.playType == 0 ? 'none':''}">
                                            <div class="clearfix">
                                                <div class="formrow">
                                                    <div class="formControls">
                                                            <span class="time-tj">
                                                                <label for="" id="timeStart">
                                                                    Time<input type="text"  readonly class="timedate-input " name="liveTime" placeholder="Start Time - End time"
                                                                    <c:if test="${not empty live.startTime}">value="<fmt:formatDate value="${live.startTime}" pattern="yyyy/MM/dd HH:mm:ss"/> to <fmt:formatDate value="${live.endTime}" pattern="yyyy/MM/dd HH:mm:ss"/>"</c:if>
                                                                >
                                                                </label>
                                                            </span>
                                                        <span class="cells-block error none"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;Please select the beginning time and end time.</span>
                                                        <input type="hidden" ${course.playType == 0 ? 'disabled':''} name="live.startTime" id="liveStartTime" value="${live.startTime}">
                                                        <input type="hidden" ${course.playType == 0 ? 'disabled':''} name="live.endTime" id="liveEndTime" value="${live.endTime}">
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="cells-block clearfix checkbox-box">
                                                    <span class="checkboxIcon">
                                                        <input type="checkbox" id="popup_checkbox_2" name="openLive" value="1" class="chk_1 chk-hook" ${course.playType == 2 ? 'checked' : ''}>
                                                        <label for="popup_checkbox_2" class="popup_checkbox_hook"><i class="ico checkboxCurrent"></i>&nbsp;&nbsp;Video Live Stream</label>
                                                    </span>
                                                <div class="checkbox-main">
                                                    <p>Generally 1 audience takes 0.5G network flow per hour. Your live is set to 30 minutes. It is estimated to take 25G network flow given 100 audience(s) online.</p>
                                                    <div class="text">Network Flow Balance<span class="color-blue">${flux}</span>G <a href="${ctx}/mgr/user/toFlux" target="_blank" class="cancel-hook">Recharge Now</a></div>
                                                </div>
                                            </div>
                                        </div>
                                    </label>
                                </div>

                                <%--<span class="cells-block error one"><img src="images/login-error-icon.png" alt="">&nbsp;输入正确密码</span>--%>
                                <input type="button" class="button login-button buttonBlue last" value="Submit">
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
            <strong>Select</strong>
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
                                ><a href="javascript:void (0);">${c.nameEn}</a></li>
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
                            ><a href="javascript:void (0);">${cc.nameEn}</a></li>
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
                    <p>Please complete the payment in the recharge page, please don't close the window before the completion of the payment</p>
                    <div class="admin-button t-right">
                        <a href="javascript:;" class="button color-blue min-btn layui-layer-close" >Payment problems, try again</a>
                        <input type="submit" class="button buttonBlue item-radius min-btn" value="I have paid successfully">
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

    function uploadFile(){
        var fSize = fileSize($("#uploadFile").get(0));
        if (fSize > file_size_limit){
            layer.msg("Please upload files less than 100M");
            return false;
        }
        var fileName = $("#uploadFile").val().toLowerCase();
        if (!fileName.endWith(".ppt") && !fileName.endWith(".pptx") && !fileName.endWith(".pdf")){
            layer.msg("Please select the ppt|pptx|pdf format file");
            return false;
        }
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $(".metting-progreesItem").removeClass("none");
        showUploadProgress();
        $.ajaxFileUpload({
            url: "${ctx}/mgr/meet/upload"+"?courseId=${course.id}", //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: "uploadFile", //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data)  //服务器成功响应处理函数
            {
                layer.close(index);
                if (data.code == 0){
                    //回调函数传回传完之后的URL地址
                    window.location.reload();
                } else {
                    layer.msg(data.err);
                }
            },
            error:function(data, status, e){
                alert(e);
                layer.close(index);
            }
        });
    }

    function showUploadProgress(){
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
            $("#uploadAlt").text("Progress of converting");
            $("#progressS").text(data.data.progress);
            $("#progressI").css("width", data.data.progress);
            if (data.data.progress.indexOf("100") != -1){
                $.get('${ctx}/mgr/meet/convert/clear', {}, function (data1) {
                }, 'json');
            } else {
                setTimeout(showConvertProgress, 500);
            }
        }, 'json');
    }

    $(function(){
        showInfoLeftCount();

        function showInfoLeftCount(){
            var usedLen = $("#courseInfo").val().length;
            $("#leftInfoCount").text(600 - usedLen);
        }

        $("#courseInfo").keyup(function(){
            showInfoLeftCount();
        });

        $("input[name='course.playType']").click(function(){
            var playType = $(this).val();
            $("input[name='course.playType]").removeAttr("checked");
            $(this).prop("checked", "true");
            if (playType == 0){
                $("#liveStartTime").attr("disabled", "true");
                $("#endStartTime").attr("disabled", "true");
            } else {
                $("#liveStartTime").removeAttr("disabled");
                $("#endStartTime").removeAttr("disabled");
            }
            $(this).parent().siblings().removeClass("cur");
            $(this).parent().addClass("cur");

            $(this).parent().siblings().find(".meeting-tab-main").addClass("none");
            $(this).siblings(".meeting-tab-main").removeClass("none");
        });

        $('.cancel-hook').on('click',function(){
            layer.open({
                type: 1,
                area: ['660px', '350px'],
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
            if ($.trim($timedate.val()) == '' && playType == 1){
                $timedate.focus();
                $timedate.parent().parent().next(".error").removeClass("none");
                return;
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
            separator : ' to ',
            format: 'YYYY/MM/DD HH:mm:ss',
            autoClose: false,
            time: {
                enabled: true
            }
        }).bind('datepicker-first-date-selected', function(event, obj){
            /*首次点击的时间*/
            console.log('first-date-selected',obj);
        }).bind('datepicker-change',function(event,obj){
            console.log('change',obj);
            var timeArray = obj.value.split(" to ");
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

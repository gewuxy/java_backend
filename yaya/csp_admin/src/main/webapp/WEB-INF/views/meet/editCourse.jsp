<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link href="${ctxStatic}/css/global.css" type="text/css" rel="stylesheet" />
    <link href="${ctxStatic}/css/style.css" type="text/css" rel="stylesheet" />
    <script src="${ctxStatic}/js/util.js"></script>
    <script>
        $(document).ready(function() {
            var coverUrl = "${course.coverUrl}";
            if (coverUrl == null || coverUrl=="") {
                $("#coverView").attr("src", "");
            }

            $("#courseForm").validate({
                submitHandler: function (form) {
                    layer.msg('正在提交，请稍等...', {
                        icon: 16,
                        shade: 0.01
                    });
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            if("${error}"){
                layer.msg("${error}");
            }

        });
    </script>
</head>
<body>
    <ul class="nav nav-tabs">
        <li><a href="${ctx}/csp/meet/course/list">讲本列表</a></li>
        <li class="active"><a href="${ctx}/csp/meet/course/edit">编辑讲本</a></li>
    </ul>
    <div class="admin-module clearfix item-radius">
        <div class="col-lg-5">
            <div class="upload-ppt-box">
                <c:choose>
                    <c:when test="${fn:length(course.details) > 0}">
                        <div class="upload-ppt-area upload-ppt-area-finish">
                            <img src="${fileBase}${course.details[0].imgUrl}" alt="" id="detailImgUrl">
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="upload-ppt-area">
                            <label for="uploadFile">
                                <input type="file" name="file" class="none" id="uploadFile">
                                <p class="img"><img src="${ctxStatic}/images/upload-ppt-area-img.png" alt="" id="detailImgUrl"></p>
                                <p id="uploadTipView">或拖动PDF／PPT到此区域上传</p>
                                <p class="error" id="sugest">建议使用.pdf或.pptx文件效果更佳</p>
                            </label>
                        </div>
                    </c:otherwise>
                </c:choose>


                <div class="upload-main">
                    <div class="admin-button t-center">
                        <label for="reUploadFile"><input type="file" name="file" class="none" id="reUploadFile">
                            <span  class="button min-btn" id="uploadTextView">
                                <c:choose>
                                    <c:when test="${empty course.details}">
                                        上传演讲文档
                                    </c:when>
                                    <c:otherwise>
                                        重新上传
                                    </c:otherwise>
                                </c:choose>
                            </span>
                            <c:if test="${empty course.details}">
                                <p class="color-gray-02 ${empty course.details ? '' : 'none'}" id="limitView">选择小于100M的文件</p>
                            </c:if>
                        </label>
                    </div>
                    <div class="metting-progreesItem clearfix t-left none">
                        <span id="uploadAlt">上传进度</span> <span class="color-blue" id="progressS">0%</span>
                        <p><span class="metting-progreesBar"><i style="width:0%" id="progressI"></i></span></p>

                    </div>
                    <span class="cells-block error none" id="detailsError"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;请上传演讲文档</span>
                </div>
            </div>
            <div class="control-group"></div>

            <div class="upload-metting-star" id="dataFlash" style="padding-top: 10px">
                <div class="control-group">
                    <div class="controls">
                        上传讲本封面：
                        <input type="hidden" name="coverUrl" id="coverUrl" value="${course.coverUrl}">
                        <img width="200" height="200" id="coverView"
                                <c:choose>
                                    <c:when test="${not empty course.coverUrl}">
                                        src="${fileBase}${course.coverUrl}"
                                    </c:when>
                                    <c:otherwise>
                                        src=""
                                    </c:otherwise>
                                </c:choose>
                        />&nbsp;
                        <input type="file" name="file" id="coverFile" style="width:180px">
                        <span class="help-inline">图片格式为jpg、png、jpeg</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-lg-7">
            <form action="${ctx}/csp/meet/course/save" method="post" id="courseForm" name="courseForm" onsubmit="return false;">
                <input type="hidden" name="id" id="courseId" value="${course.id}"/>
                <input type="hidden" name="coverUrl" id="cover_url" value="${course.coverUrl}">

                <div class="meeting-form-item login-form-item">
                    <div class="control-group">
                        <div class="controls">
                            <font color="red">*</font>&nbsp;讲本标题：
                            <input id="courseTitle" type="text" style="height:40px;width: 350px" class="required input-xlarge" name="title" value="${course.title}">
                            <span class="cells-block error none"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;输入讲本标题</span>
                        </div>
                    </div>

                    <div class="control-group" style="padding-top:10px;">
                        <div class="controls">
                            <font color="red">*</font>&nbsp;讲本简介：
                            <textarea name="info" id="courseInfo" style="width: 450px" class="required input-xlarge" rows="5" maxlength="600">${course.info}</textarea>
                            <span class="t-right" id="leftInfoCount">600字</span>
                            <span class="cells-block error none"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;输入讲本简介</span>
                        </div>
                    </div>

                    <div class="control-group" style="padding-top:10px">
                        <div class="controls">
                            <font color="red">*</font>&nbsp;讲本类型：
                            <select id="sourceType" name="sourceType" style="width: 130px;" class="required input-xlarge">
                                <option value=""/>选择类型
                                <option value="2"/>贺卡模板
                                <option value="3"/>有声红包
                                <option value="4"/>快捷讲本
                            </select>
                            <script>
                                document.getElementById("sourceType").value="${course.sourceType}";
                            </script>

                        </div>
                    </div>

                    <div class="control-group" style="padding-top:10px;">
                        <div class="controls"  id="music">背景音乐：
                            <input type="text" style="height:30px" id="musicName" name="musicName" value="${courseTheme.name}" readonly>
                            <input type="hidden" name="musicId" id="musicId" value="${courseTheme.musicId}">&nbsp;
                            <input type="button" class="btn btn-primary" value="选择音乐" onclick="selectMusic();">
                            <input type="button" class="btn btn-primary" value="清除音乐" onclick="clearMusic();">
                        </div>
                    </div>

                    <div class="control-group" style="padding-top:10px;">
                        <div class="controls"  id="image">背景图片：
                            <input type="text" style="height:30px" id="imageName" name="imageName" value="${courseTheme.imgName}" readonly>
                            <input type="hidden" name="imageId" id="imageId" value="${courseTheme.imageId}">&nbsp;
                            <input type="button" class="btn btn-primary" value="选择图片" onclick="selectImage();">
                            <input type="button" class="btn btn-primary" value="清除图片" onclick="clearImage();">
                        </div>
                    </div>
                </div>

                <input id="saveSubmit" type="submit" class="btn btn-primary" style="margin-left: 250px" value=" 确认提交 ">
                &nbsp;&nbsp;
                <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)">
            </form>
        </div>

    </div>

    <div class="cancel-popup-box" id="uploadSuccess">
        <div class="layer-hospital-popup">
            <div class="layer-hospital-popup-title">
                <strong>&nbsp;</strong>
                <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
            </div>
            <div class="layer-hospital-popup-main ">
                <div class="cancel-popup-main">
                    <p>上传成功！</p>
                </div>

            </div>
        </div>
    </div>

<script src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script src="${ctxStatic}/js/ajaxUtils.js"></script>
<script>
    const file_size_limit = 100*1024*1024;
    var uploadOver = false;
    var success = false;

    $("#uploadFile, #reUploadFile").change(function(){
        var id = $(this).attr("id");
        uploadFile(document.getElementById(id));
    });

    function fleshPage(data){
        layer.open({
            type: 1,
            area: ['300px', '260px'],
            fix: false, //不固定
            title:false,
            closeBtn:0,
            anim:5,
            btn: ["确定"],
            content: $('#uploadSuccess'),
            success:function(){
               // $(".icon-tips-blue").hide();
            },
            yes:function(){
                success = false;
                $(".upload-ppt-area").addClass(" upload-ppt-area-finish");
                $("#detailImgUrl").attr("src", data.detailImgUrl);
                $("#uploadTipView").remove();
                $("#sugest").remove();

                $("#courseTitle").val(data.course.title);
                $("#courseId").val(data.course.id);

                $("#uploadFile").replaceWith('<input type="file" name="file" class="none" id="uploadFile">');
                $("#uploadFile").change(function(){
                    uploadFile($("#uploadFile")[0]);
                });
                $("#reUploadFile").replaceWith('<input type="file" name="file" class="none" id="reUploadFile">');
                $("#reUploadFile").change(function(){
                    uploadFile($("#reUploadFile")[0]);
                });
                layer.closeAll();
            },
            cancel :function(){

            },
        });
    }

    function uploadFile(f){
        var fSize = fileSize(f);
        if (fSize > file_size_limit){
            layer.msg("选择小于100M的文件");
            return false;
        }
        var fileName = $(f).val().toLowerCase();
        if (!fileName.endWith(".ppt") && !fileName.endWith(".pptx") && !fileName.endWith(".pdf")){
            layer.msg("请选择ppt|pptx|pdf格式文件");
            return false;
        }
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        showUploadProgress();
        $.ajaxFileUpload({
            url: "${ctx}/csp/meet/upload"+"?courseId=${course.id}", //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: f.id, //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data)  //服务器成功响应处理函数
            {
                layer.close(index);
                if (data.code == 0){
                    //回调函数传回传完之后的URL地址
                    fleshPage(data.data);
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
        $.get('${ctx}/csp/meet/upload/progress', {}, function (data) {
            $("#progressS").text(data.data.progress);
            $("#progressI").css("width", data.data.progress);
            if (data.data.progress.indexOf("100") != -1){
                $.get('${ctx}/csp/meet/upload/clear', {}, function (data1) {
                }, 'json');
                showConvertProgress();
                $("#progressS").text("0%");
                $("#progressI").css("width", "0%");
            } else {
                if (!uploadOver){
                    setTimeout(showUploadProgress, 200);
                }
            }
        }, 'json');
    }


    function showConvertProgress(){
        $.get('${ctx}/csp/meet/convert/progress', {}, function (data) {
            console.log("convert progress = "+data.data.progress);
            $("#uploadAlt").text("处理中");
            $("#progressS").text(data.data.progress);
            $("#progressI").css("width", data.data.progress);
            if (data.data.progress.indexOf("100") != -1){
                $.get('${ctx}/csp/meet/convert/clear', {}, function (data1) {
                }, 'json');
            } else {
                if(!uploadOver){
                    setTimeout(showConvertProgress, 500);
                }
            }
        }, 'json');
    }

    var detailsLength = "${fn:length(course.details)}";
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
                layer.msg("请选择ppt|pptx|pdf格式文件");
                return false;
            }

            var filesize = f.size;
            if(filesize>file_size_limit){
                layer.msg("选择小于100M的文件");
                return false;
            }

            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            //上传
            xhr = new XMLHttpRequest();
            xhr.open("post", "${ctx}/csp/meet/upload?courseId=${course.id}", true);
            xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            var fd = new FormData();
            fd.append('file', f);

            xhr.send(fd);
            var data = xhr.response;
            xhr.onloadend = function(data){
                fleshPage(data.data);
            }

            showUploadProgress();
        }
    });

    // 上传封面图片
    $(document).ready(function() {
        $("#coverFile").change(function(){
            coverFile($("#coverFile")[0]);
        });

        function coverFile(f) {
            var fileName = $(f).val().toLowerCase();
            if (!fileName.endWith(".jpg") && !fileName.endWith(".png") && !fileName.endWith(".jpeg")){
                layer.msg("请选择正确的图片格式");
                return false;
            }

            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajaxFileUpload({
                url: "${ctx}/csp/meet/upload/cover", //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: f.id, //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                    layer.close(index);
                    if (data.code == 0){
                        //回调函数传回传完之后的URL地址
                        fleshCover(data.data);
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

        function fleshCover(data) {
            $("#coverView").attr("src", data.absolutePath);
            $("#coverUrl").val(data.relativePath);
            $("#cover_url").val(data.relativePath);
            $("#coverFile").replaceWith('<input type="file" id="coverFile" name="file" style="width:180px">');
            $("#coverFile").change(function(){
                coverFile($("#coverFile")[0]);
            });

        }
    });


    // 选择背景音乐
    function selectMusic() {
        layer.open({
            type: 2, //ifream窗口
            title: '背景音乐',
            shadeClose: true,
            shade: 0.8,
            area: ['1000px', '90%'],
            content: '${ctx}/csp/meet/background/music',
        });
    }

    function clearMusic() {
        $("#musicId").val("");
        $("#musicName").val("");
    }

    // 选择背景图片
    function selectImage() {
        layer.open({
            type: 2, //ifream窗口
            title: '背景图片',
            shadeClose: true,
            shade: 0.8,
            area: ['1000px', '90%'],
            content: '${ctx}/csp/meet/background/image',
        });
    }

    function clearImage() {
        $("#imageId").val("");
        $("#imageName").val("");
    }
</script>


</body>
</html>

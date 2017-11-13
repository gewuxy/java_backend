<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>頭像設置-個人中心-CSPmeeting</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">

</head>


<body>
<div id="wrapper">
    <%@include file="../include/header.jsp" %>
    <div class="admin-content bg-gray" >
        <div class="page-width clearfix">
            <div class="user-module clearfix">
                <div class="row clearfix">
                    <div class="col-lg-4">
                        <%@include file="left.jsp"%>
                    </div>
                    <div class="col-lg-8">
                        <%@include file="user_include.jsp" %>
                        <div class="user-content user-content-levelHeight item-radius">
                            <div class="user-portrait-upload">
                                <c:if test="${empty dto.avatar}">
                                    <div class="user-portrait-area item-radius">
                                        <p>上傳頭像</p>
                                    </div>
                                    <p>選擇JPG、PNG格式，小於1M的圖片</p>
                                    <input type="file" id="headimg" style="display:none" name="file" onchange="toUpload()">
                                    <input href="#" type="button"  class="button login-button buttonBlue last" onclick="headimg.click()" value="上傳頭像">
                                </c:if>
                                <c:if test="${not empty dto.avatar}">
                                    <div class="user-portrait-area item-radius user-portrait-finish">
                                        <img src="${dto.avatar}" alt="">
                                    </div>
                                    <p>選擇JPG、PNG格式，小於1M的圖片</p>
                                    <input href="#" type="button" class="button login-button buttonBlue last" value="更換頭像">
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="../include/footer.jsp"%>
</div>

<script src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script>

    $("#config_2").parent().attr("class","cur");
    var fileUploadUrl = "${ctx}/mgr/user/updateAvatar";
    $(function () {

    });

    function toUpload(){
        var filename = $("#headimg").val();
        var extStart = filename.lastIndexOf(".");
        var ext = filename.substring(extStart,filename.length).toUpperCase();
        if(ext != ".BMP" && ext != ".PNG" && ext != ".JPG" && ext != ".JPEG"&&ext != ".TIFF"){
            layer.msg("文件格式不支持，請上傳圖片類型的文件");
        }else{
            //ajaxFileUpload的回調要改為data.code，之前是data.status，會報錯
            upload("headimg","headimg",1*1024*1024,uploadHandler);
        }
    }

    function uploadHandler(result){
        $("#image").attr("src",result.data);
        $("#head_img").attr("src",result.data);
        layer.msg("修改成功");
    }

</script>

</body>
</html>




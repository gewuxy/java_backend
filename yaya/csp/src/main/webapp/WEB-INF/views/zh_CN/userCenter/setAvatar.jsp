<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>头像设置-个人中心-CSPmeeting</title>
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
                                        <p>上传头像</p>
                                    </div>
                                    <p>选择JPG、PNG格式，小于1M的图片</p>
                                    <input type="file" id="headimg" style="display:none" name="file" onchange="toUpload()">
                                    <input href="#" type="button"  class="button login-button buttonBlue last" onclick="headimg.click()" value="上传头像">
                                </c:if>
                                <c:if test="${not empty dto.avatar}">
                                    <div class="user-portrait-area item-radius user-portrait-finish">
                                        <img src="${dto.avatar}" alt="" id="show">
                                    </div>
                                    <p>选择JPG、PNG格式，小于1M的图片</p>
                                    <input type="file" id="headimg" style="display:none" name="file" onchange="toUpload()">
                                    <input href="#" type="button" class="button login-button buttonBlue last" onclick="headimg.click()" value="更换头像">
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
            layer.msg("文件格式不支持，请上传图片类型的文件");
        }else{
            //ajaxFileUpload的回调要改为data.code，之前是data.status，会报错
            upload("headimg","headimg",1*1024*1024,uploadHandler);
        }
    }

    function uploadHandler(result){
        $("#image").attr("src",result.data);
        $("#head_img").attr("src",result.data);
        $("#show").attr("src",result.data);
        layer.msg("修改成功");
    }

</script>

</body>
</html>




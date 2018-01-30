<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html >
<head>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <title><fmt:message key="page.header.avatar"/> -<fmt:message key="page.title.user.title"/> -<fmt:message key="page.common.appName"/> </title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />

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
                                        <p><fmt:message key="page.words.upload.avatar"/> </p>
                                    </div>
                                    <p><fmt:message key="page.words.upload.limit"/> </p>
                                    <input type="file" id="headimg" style="display:none" name="file" onchange="toUpload()">
                                    <input href="#" type="button"  class="button login-button buttonBlue last" onclick="headimg.click()" value="<fmt:message key="page.words.upload.avatar"/>">
                                </c:if>
                                <c:if test="${not empty dto.avatar}">
                                    <div class="user-portrait-area item-radius user-portrait-finish">
                                        <img src="${dto.avatar}" alt="" id="show">
                                    </div>
                                    <p><fmt:message key="page.words.upload.limit"/></p>
                                    <input type="file" id="headimg" style="display:none" name="file" onchange="toUpload()">
                                    <input href="#" type="button" class="button login-button buttonBlue last" onclick="headimg.click()" value="<fmt:message key="page.words.update.avatar"/>">
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
            layer.msg("<fmt:message key="page.words.format.error"/>");
        }else{
            //ajaxFileUpload的回调要改为data.code，之前是data.status，会报错
            upload("headimg","headimg",1*1024*1024,uploadHandler);
        }
    }

    function uploadHandler(result){
        window.location.reload();
    }

</script>

</body>
</html>




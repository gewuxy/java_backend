<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>个人中心 - 修改密码</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">

</head>



<div id="wrapper">
    <%@include file="/WEB-INF/include/header_zh_CN.jsp" %>
    <div class="admin-content bg-gray" >
        <div class="page-width clearfix">
            <div class="user-module clearfix">
                <div class="row clearfix">
                    <div class="col-lg-4">
                        <%@include file="left.jsp"%>
                    </div>
                    <div class="col-lg-8">
                        <%@include file="user_include.jsp" %>
                        <c:if test="${not empty needBind}">
                            <div class="user-content user-content-levelHeight item-radius">
                                <div class="formrow">
                                    <a href="user-05-binding.html" type="button" class="button login-button buttonBlue last" >绑定邮箱</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img
                                        src="${ctxStatic}/images/user-email-binding.png" alt="">&nbsp;&nbsp;修改密码需绑定邮箱后才能继续操作。
                                </div>

                            </div>
                        </c:if>
                        <c:if test="${empty needBind}">
                            <div class="user-content user-content-levelHeight item-radius">
                                <div class="user-resetPassword clearfix">
                                    <form action="">
                                        <div class="login-form-item">
                                            <h3>修改密码</h3>
                                            <label for="pwd" class="cells-block pr">
                                                <input type="text" required="" placeholder="旧密码" class="login-formInput icon-register-hot last none" maxlength="24">
                                                <input id="pwd" type="password" required="" placeholder="旧密码" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                                                <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                                            </label>

                                            <label for="repwd" class="cells-block pr">
                                                <input type="text" required="" placeholder="新密码" class="login-formInput icon-register-hot last none" maxlength="24">
                                                <input id="repwd" type="password" required="" placeholder="新密码" class="login-formInput icon-register-hot hidePassword1 last" maxlength="24">
                                                <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook1 "></a>
                                            </label>

                                            <span class="cells-block error none "><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;请输入正确的邮箱地址</span>
                                            <input href="#" type="button" class="button login-button buttonBlue last" value="修改">
                                        </div>
                                    </form>
                                </div>

                            </div>

                        </c:if>

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script src="${ctxStatic}/js/commonH5.js"></script>
<script>

    var fileUploadUrl = "${ctx}/mgr/user/updateAvatar";
    $(function () {
        //调用密码切换
        $('.pwdChange-hook').on('click',function() {
            changePassWordStatus($('.hidePassword'));
        });

        //调用密码切换
        $('.pwdChange-hook1').on('click',function() {
            changePassWordStatus($('.hidePassword1'));
        });

    });





</script>






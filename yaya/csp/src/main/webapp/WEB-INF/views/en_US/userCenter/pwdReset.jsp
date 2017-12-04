<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Change Password - Profile - CSPmeeting</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style-EN.css">

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
                        <c:if test="${not empty needBind}">
                            <div class="user-content user-content-levelHeight item-radius">
                                <div class="formrow">
                                    <a href="#" type="button" id="bindEmail" class="button login-button buttonBlue last" >Bind E-mail Address</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img
                                        src="${ctxStatic}/images/user-email-binding.png" alt="">&nbsp;&nbsp;Please bind your e-mail address before changing password.
                                </div>

                            </div>
                        </c:if>
                        <c:if test="${empty needBind}">
                            <div class="user-content user-content-levelHeight item-radius">
                                <div class="user-resetPassword clearfix">
                                    <form id="submitForm" action="" method="post">
                                        <div class="login-form-item">
                                            <h3>Change Password</h3>
                                            <label for="pwd" class="cells-block pr">
                                                <%--class 为隐藏状态不能添加required属性--%>
                                                <input type="text" placeholder="Original Password" class="login-formInput icon-register-hot last none"  maxlength="24">
                                                <input id="pwd" type="password" name="oldPwd" required="" placeholder="Original Password" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                                                <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                                            </label>
                                            <span id="oldSpan" class="cells-block error none "><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span>Please enter the correct email address</span></span>

                                            <label for="rePwd" class="cells-block pr">
                                                    <%--class 为隐藏状态不能添加required属性--%>
                                                <input type="text"  placeholder="New Password" class="login-formInput icon-register-hot last none" maxlength="24">
                                                <input id="rePwd" type="password" name="newPwd" required="" placeholder="New Password" class="login-formInput icon-register-hot hidePassword1 last" maxlength="24">
                                                <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook1 "></a>
                                            </label>
                                            <span id="newSpan" class="cells-block error none "><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span>Please enter the correct email address</span></span>
                                            <input href="#" type="button" id="submitBtn" class="button login-button buttonBlue last" value="Confirm New Password">
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
    <%@include file="../include/footer.jsp"%>
</div>

<!--弹出绑定邮箱step01-->
<div class="email-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <div class="login-form-item">
                <label for="email" class="cells-block pr">
                    <input id="email" type="text" class="login-formInput" placeholder="E-mail Address">
                </label>
                <span class="cells-block error none" id="emailSpan"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span>Enter the correct password</span></span>
                <label for="password" class="cells-block pr">
                    <input type="text" required="" placeholder="Password" class="login-formInput icon-register-hot last none" maxlength="24">
                    <input id="password" type="password" required="" placeholder="Password" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                    <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                </label>
                <span class="cells-block error none" id="passwordSpan"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span>Enter the correct password</span></span>
                <input href="#" type="button" id="emailBtn" class="button login-button buttonBlue email-hook-02 last" value="Bind E-mail Address">
            </div>
        </div>
    </div>
</div>
<!--弹出绑定邮箱step02-->
<div class="email-popup-box-02">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <div class="login-form-item">
                <div class="login-message-text">
                    <p> Activation e-mail has been sent to your mail box. Please proceed，activation in e-mail and complete your registration.</p>
                </div>
                <input href="#" type="button" id="goToEmail" class="button login-button buttonBlue close-button layui-layer-close last" value="Go to My E-mail Box">
            </div>
        </div>
    </div>
</div>

<script src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script src="${ctxStatic}/js/commonH5.js"></script>
<script>
    const classPwdOn = "pwdChange-on";
    const classPwdOff = "pwdChange-off";
    $("#config_5").parent().attr("class","cur last");
    var fileUploadUrl = "${ctx}/mgr/user/updateAvatar";
    $(function () {
        $(".pwdChange-hook").click(function(){
            if($(this).hasClass(classPwdOn)){
                $(this).removeClass(classPwdOn);
                $(this).addClass(classPwdOff);
                $("#pwd").prop("type", "text");
            } else {
                $(this).removeClass(classPwdOff);
                $(this).addClass(classPwdOn);
                $("#pwd").prop("type", "password");
            }
        });

        $(".pwdChange-hook1").click(function(){
            if($(this).hasClass(classPwdOn)){
                $(this).removeClass(classPwdOn);
                $(this).addClass(classPwdOff);
                $("#rePwd").prop("type", "text");
            } else {
                $(this).removeClass(classPwdOff);
                $(this).addClass(classPwdOn);
                $("#rePwd").prop("type", "password");
            }
        });

        $("#emailBtn").click(function () {
            if(isEmail()){
                var email = $("#email").val();
                var password = $("#password").val();
                if(checkPwd()){
                    ajaxGet('${ctx}/mgr/user/bindEmail',{"email":email,"password":password}, function (data) {
                        if (data.code == 0){
                            layer.open({
                                type: 1,
                                area: ['609px', '278px'],
                                fix: false, //不固定
                                title:false,
                                closeBtn:0,
                                content: $('.email-popup-box-02'),
                                success:function(layero, index){
                                    layer.close(1);
                                },
                                cancel :function(){
                                    layer.closeAll();
                                },
                            });

                        }else{
                            layer.msg(data.err);
                        }
                    });
                }

            }
        });

        $("#goToEmail").click(function () {
            var email = $("#email").val();
            var url = gainEmailURL(email);
            if(url != ''){
                layer.closeAll();
                window.open( url);
            }else{
                layer.msg("email login address not found");
            }
        });


        //弹出绑定邮箱step01
        $('#bindEmail').on('click',function(){
            layer.open({
                type: 1,
                area: ['609px', '340px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                content: $('.email-popup-box'),
                success:function(layero, index){

                },
                cancel :function(){

                },
            });
        });



        $("#pwd").blur(function () {
            oldPwdValid();
        });

        $("#rePwd").blur(function () {
           newPwdValid();
        });

        $("#submitBtn").click(function () {
            if(check()){
                ajaxPost('${ctx}/mgr/user/resetPwd',$("#submitForm").serialize(),function(result){
                    if (result.code == 0){//成功
                        $("#pwd").val("");
                        $("#rePwd").val("");
                        layer.msg("update success");
                    }else{//失败
                        layer.msg(result.err);
                    }
                });
            }
        });


    });


    function check() {
       if(!oldPwdValid()){
           return false;
       }
       if(!newPwdValid()){
           return false;
       }
        return true;
    }

    function oldPwdValid() {
        var password = $("#pwd").val();
        var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
        if(reg.test($.trim(password))){
            $("#oldSpan").find('span').html("Password can not contain Chinese");
            $("#oldSpan").attr("class","cells-block error");
            return false;
        }
        if ($.trim(password)==''){
            $("#oldSpan").find('span').html("please enter old password");
            $("#oldSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password)!= password){
            $("#oldSpan").find('span').html("old password can't contain blank");
            $("#oldSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password).length < 6){
            $("#oldSpan").find('span').html("Please enter the 6~24 digit password");
            $("#oldSpan").attr("class","cells-block error");

        }else{
            $("#oldSpan").attr("class","cells-block error none");
            return true;
        }
    }

    function newPwdValid() {
        var newPwd = $("#rePwd").val();
        var password = $("#pwd").val();
        var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
        if(reg.test($.trim(newPwd))){
            $("#newSpan").find('span').html("The new password can not contain Chinese");
            $("#newSpan").attr("class","cells-block error");
            return false;
        }
        if ($.trim(newPwd)==''){
            $("#newSpan").find('span').html("please enter new password");
            $("#newSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(newPwd)!= newPwd){
            $("#newSpan").find('span').html("new password can't contain blank");
            $("#newSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(newPwd).length < 6){
            $("#newSpan").find('span').html("Please enter the 6~24 digit password");
            $("#newSpan").attr("class","cells-block error");

        }else if(newPwd == password){
            $("#newSpan").find('span').html("The new password is the same as the old password");
            $("#newSpan").attr("class","cells-block error");

        }else{
            $("#newSpan").attr("class","cells-block error none");
            return true;
        }
    }

    function checkPwd() {
        var password = $("#password").val();
        var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
        if(reg.test($.trim(password))){
            $("#passwordSpan").find('span').html("The password can not contain Chinese");
            $("#passwordSpan").attr("class","cells-block error");
            return false;
        }
        if ($.trim(password)==''){
            $("#passwordSpan").find('span').html("please enter password");
            $("#passwordSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password)!= password){
            $("#passwordSpan").find('span').html("password can't contain blank");
            $("#passwordSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password).length < 6){
            $("#passwordSpan").find('span').html("Please enter the 6~24 digit password");
            $("#passwordSpan").attr("class","cells-block error");

        }else{
            $("#passwordSpan").attr("class","cells-block error none");
            return true;
        }
    }

    function isEmail() {
        var email = $("#email").val();
        if($.trim(email) == ''){
            $("#emailSpan").attr("class","cells-block error ");
            $("#emailSpan").find('span').html("please enter email address");
            return false;
        }
        if(!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
            $("#emailSpan").attr("class","cells-block error ");
            $("#emailSpan").find('span').html("please enter true email address");
            return false;
        }
        $("#emailSpan").attr("class","cells-block error none");
        return true;
    }


</script>
</body>
</html>





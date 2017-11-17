<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>修改密碼-個人中心-CSPmeeting</title>
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
                        <c:if test="${empty email}">
                            <div class="user-content user-content-levelHeight item-radius">
                                <div class="formrow">
                                    <a href="#" type="button" id="bindEmail" class="button login-button buttonBlue last" >綁定郵箱</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img
                                        src="${ctxStatic}/images/user-email-binding.png" alt="">&nbsp;&nbsp;修改密碼需綁定郵箱後才能繼續操作。
                                </div>

                            </div>
                        </c:if>
                        <c:if test="${not empty email}">
                            <div class="user-content user-content-levelHeight item-radius">
                                <div class="user-resetPassword clearfix">
                                    <form id="submitForm" action="" method="post">
                                        <div class="login-form-item">
                                            <h3>修改密碼</h3>
                                            <label for="pwd" class="cells-block pr">
                                                <%--class 为隐藏状态不能添加required属性--%>
                                                <input type="text" placeholder="舊密碼" class="login-formInput icon-register-hot last none"  maxlength="24">
                                                <input id="pwd" type="password" name="oldPwd" required="" placeholder="舊密碼" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                                                <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                                            </label>
                                            <span id="oldSpan" class="cells-block error none "><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span>請輸入正確的郵箱地址</span></span>

                                            <label for="rePwd" class="cells-block pr">
                                                    <%--class 为隐藏状态不能添加required属性--%>
                                                <input type="text"  placeholder="新密碼" class="login-formInput icon-register-hot last none" maxlength="24">
                                                <input id="rePwd" type="password" name="newPwd" required="" placeholder="新密碼" class="login-formInput icon-register-hot hidePassword1 last" maxlength="24">
                                                <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook1 "></a>
                                            </label>
                                            <span id="newSpan" class="cells-block error none "><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span>請輸入正確的郵箱地址</span></span>
                                            <input href="#" type="button" id="submitBtn" class="button login-button buttonBlue last" value="修改">
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
                    <input id="email" type="text" class="login-formInput" placeholder="郵箱地址">
                </label>
                <span class="cells-block error none" id="emailSpan"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span>輸入正確密碼</span></span>
                <label for="password" class="cells-block pr">
                    <input type="text" required="" placeholder="密碼" class="login-formInput icon-register-hot last none" maxlength="24">
                    <input id="password" type="password" required="" placeholder="密碼" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                    <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                </label>
                <span class="cells-block error none" id="passwordSpan"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span>輸入正確密碼</span></span>
                <input href="#" type="button" id="emailBtn" class="button login-button buttonBlue email-hook-02 last" value="綁定郵箱">
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
                    <p>激活賬號郵件已發送至您的郵箱，<br />請前往激活完成註冊。</p>
                </div>
                <input href="#" type="button" id="goToEmail" class="button login-button buttonBlue close-button layui-layer-close last" value="前往郵箱">
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
                    $.get('${ctx}/mgr/user/bindEmail',{"email":email,"password":password}, function (data) {
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
                    },'json');
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
                layer.msg("抱歉！未找到對應的郵箱登錄地址");
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
                $.post('${ctx}/mgr/user/resetPwd',$("#submitForm").serialize(),function(result){
                    if (result.code == 0){//成功
                        $("#pwd").val("");
                        $("#rePwd").val("");
                        layer.msg("修改成功");
                    }else{//失败
                        layer.msg(result.err);
                    }
                },'json');
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
            $("#oldSpan").find('span').html("密碼不能包含中文");
            $("#oldSpan").attr("class","cells-block error");
            return false;
        }
        if ($.trim(password)==''){
            $("#oldSpan").find('span').html("舊密碼不能為空");
            $("#oldSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password)!= password){
            $("#oldSpan").find('span').html("舊密碼不能包含空格");
            $("#oldSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password).length < 6){
            $("#oldSpan").find('span').html("請輸入6〜24位密碼");
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
            $("#newSpan").find('span').html("新密碼不能包含中文");
            $("#newSpan").attr("class","cells-block error");
            return false;
        }
        if ($.trim(newPwd)==''){
            $("#newSpan").find('span').html("新密碼不能為空");
            $("#newSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(newPwd)!= newPwd){
            $("#newSpan").find('span').html("新密碼不能包含空格");
            $("#newSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(newPwd).length < 6){
            $("#newSpan").find('span').html("請輸入6〜24位密碼");
            $("#newSpan").attr("class","cells-block error");

        }else if(password == newPwd){
            $("#newSpan").find('span').html("新密碼和舊密碼相同");
            $("#newSpan").attr("class","cells-block error");

        }else{
            $("#newSpan").attr("class","cells-block error none");
            return true;
        }
    }

    function checkPwd() {
        var password = $("#password").val();
        if ($.trim(password)==''){
            $("#passwordSpan").find('span').html("密碼不能為空");
            $("#passwordSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password)!= password){
            $("#passwordSpan").find('span').html("密碼不能包含空格");
            $("#passwordSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password).length < 6){
            $("#passwordSpan").find('span').html("請輸入6〜24位密碼");
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
            $("#emailSpan").find('span').html("郵箱不能為空");
            return false;
        }
        if(!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
            $("#emailSpan").attr("class","cells-block error ");
            $("#emailSpan").find('span').html("郵箱格式不正確");
            return false;
        }
        $("#emailSpan").attr("class","cells-block error none");
        return true;
    }


</script>
</body>
</html>





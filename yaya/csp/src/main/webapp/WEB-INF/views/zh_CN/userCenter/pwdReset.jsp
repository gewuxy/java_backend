<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
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


<body>
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
                                    <form id="submitForm" action="" method="post">
                                        <div class="login-form-item">
                                            <h3>修改密码</h3>
                                            <label for="pwd" class="cells-block pr">
                                                <%--class 为隐藏状态不能添加required属性--%>
                                                <input type="text" placeholder="旧密码" class="login-formInput icon-register-hot last none"  maxlength="24">
                                                <input id="pwd" type="password" name="oldPwd" required="" placeholder="旧密码" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                                                <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                                            </label>
                                            <span id="oldSpan" class="cells-block error none "><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;请输入正确的邮箱地址</span>

                                            <label for="rePwd" class="cells-block pr">
                                                    <%--class 为隐藏状态不能添加required属性--%>
                                                <input type="text"  placeholder="新密码" class="login-formInput icon-register-hot last none" maxlength="24">
                                                <input id="rePwd" type="password" name="newPwd" required="" placeholder="新密码" class="login-formInput icon-register-hot hidePassword1 last" maxlength="24">
                                                <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook1 "></a>
                                            </label>
                                            <span id="newSpan" class="cells-block error none "><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;请输入正确的邮箱地址</span>
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

</div>

<script src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script src="${ctxStatic}/js/commonH5.js"></script>
<script>
    const classPwdOn = "pwdChange-on";
    const classPwdOff = "pwdChange-off";
    $("#config_5").parent().attr("class","cur last");
    var fileUploadUrl = "${ctx}/mgr/user/updateAvatar";
    $(function () {
        $(".pwdChange-hook, .pwdChange-hook1").click(function(){
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
//        //调用密码切换
//        $('.pwdChange-hook').on('click',function() {
//            changePassWordStatus($('.hidePassword'));
//        });
//
//        //调用密码切换
//        $('.pwdChange-hook1').on('click',function() {
//            changePassWordStatus($('.hidePassword1'));
//        });

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
        if ($.trim(password)==''){
            $("#oldSpan").html("旧密码不能为空");
            $("#oldSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password)!= password){
            $("#oldSpan").html("旧密码不能包含空格");
            $("#oldSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password).length < 6){
            $("#oldSpan").html("请输入6~24位密码");
            $("#oldSpan").attr("class","cells-block error");

        }else{
            $("#oldSpan").attr("class","cells-block error none");
            return true;
        }
    }

    function newPwdValid() {
        var newPwd = $("#rePwd").val();
        if ($.trim(newPwd)==''){
            $("#newSpan").html("新密码不能为空");
            $("#newSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(newPwd)!= newPwd){
            $("#newSpan").html("新密码不能包含空格");
            $("#newSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(newPwd).length < 6){
            $("#newSpan").html("请输入6~24位密码");
            $("#newSpan").attr("class","cells-block error");

        }else{
            $("#newSpan").attr("class","cells-block error none");
            return true;
        }
    }


</script>
</body>
</html>





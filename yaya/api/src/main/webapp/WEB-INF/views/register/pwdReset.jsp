<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/19
  Time: 9:22
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta charset="utf-8" />
    <meta name="keywords" content="社交，社交平台，医生，论坛，专属医生社交平台，专业医师，专业药师，执业医师，执业药师，执业资格证，医疗工具，实用，药草园">
    <title>重设密码</title>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/index.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/completer.css"/>
</head>

<body class="bg-gray">
<div id="header" class="header-border">
    <div class="h_con">
        <a href="http://www.medcn.com" class="c_left fl">
            <img src="${ctxStatic}/images/logo.png" alt="logo" />
        </a>
    </div>
</div>
<!--内容区域-->
<div class="warp ">
    <div class="page-width">
        <div class="reg-index-box clearfix">

            <h3>重置密码</h3>
            <div class="reg-index-main" style="padding:80px 270px;">
                <fieldset>
                    <form action="${ctx}/api/email/pwd/doReset" name="resetForm" id="resetForm" onsubmit="return checkPwd(this)" method="post">
                        <input type="hidden" name="email" value="${email}">
                        <input type="hidden" name="code" value="${code}">
                        <div class="formrow clearfix">
                            <label for="" class="formTitle">请输入新密码</label>
                            <div class="formControls">
                                <p><input type="password" class="textInput" style="width: 200px;" id="password" name="password" data-type="password"><span class="formIconTips none" id="success-password"><img src="${ctxStatic}/images/icon_true.png" alt=""></span></p>
                                <p class="none" id="error-password"><img src="${ctxStatic}/images/icon_tips.png" alt="">请输入6-30位的密码</p>
                            </div>
                        </div>
                        <div class="formrow clearfix">
                            <label for="" class="formTitle">确认密码</label>
                            <div class="formControls">
                                <p><input type="password" class="textInput"  style="width: 200px;" id="repassword" name="repassword" data-type="repassword"><span class="formIconTips none" id="success-repassword"><img src="${ctxStatic}/images/icon_true.png" alt=""></span></p>
                                <p class="none" id="error-repassword"><img src="${ctxStatic}/images/icon_tips.png" alt="">请确保两次输入的密码一致</p>
                            </div>
                        </div>
                        <div class="t-center formMargin-top">
                            <button class="index-reg-button" >确认</button>
                        </div>
                    </form>

                </fieldset>
            </div>

        </div>
    </div>
</div>
<script>
    $(function(){
        if("${message}"){
            $("#error-password").show();
        }
        $("#password").change(function(){
            var val = $(this).val();
            if(!isPassword(val)){
                $("#error-password").removeClass("none");
                $("#success-password").addClass("none")
            }else{
                $("#error-password").addClass("none");
                $("#success-password").removeClass("none")
            }
        });

        $("#repassword").change(function(){
            var val = $(this).val();
            var pwd = $("#password").val();
            if(!isPassword(val)|| val!=pwd){
                $("#error-repassword").removeClass("none");
                $("#success-repassword").addClass("none")
            }else{
                $("#error-repassword").addClass("none");
                $("#success-repassword").removeClass("none")
            }
        });
    });

    function checkPwd(){
        var pwd = $("#password").val();
        var repwd = $("#repassword").val();
        if (!isPassword(pwd)){
            $("#error-password").removeClass("none");
            $("#success-password").addClass("none");
            return false;
        }
        if(pwd != repwd){
            $("#error-repassword").removeClass("none");
            $("#success-repassword").addClass("none");
            return false;
        }
        return true;
    }
</script>
</body>
</html>

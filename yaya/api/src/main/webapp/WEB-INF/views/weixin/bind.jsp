<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/7/25
  Time: 15:32
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>绑定账号</title>
    <%@include file="/WEB-INF/include/page_context_weixin.jsp"%>

    <script src="${ctxStatic}/js/layer/layer.js"></script>
</head>
<body class="blue-reg">

<div class="warp">
    <div class="header">
        <div class="staus-bar"><strong>如果没有YaYa医师账号</strong> &nbsp;<a href="${ctx}/weixin/register" class="color-blue">请注册</a></div>
        <div class="logo"><img src="${ctxStatic}/images/logo.png" alt=""></div>
    </div>

    <div class="item">
        <div class="item-area login-input">
            <form action="${ctx}/weixin/bind" id="bindForm" name="bindForm" method="post">
                <div class="formrow radius login-input-item">
                    <label for=""><input type="text" name="username" id="username" value="${username}" class="icon-login-user login-max-padding" placeholder="请输入邮箱或者手机号"></label>
                    <div class="cells-error none">请输入正确的邮箱或者手机号</div>
                    <label for="password" class="pr">
                        <input type="password" id="password" name="password" value="${password}" placeholder="请输入密码" class="icon-login-pwd login-max-padding last">
                        <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                    </label>
                    <div class="cells-error none">请输入6-24位密码</div>
                </div>

                <div class="formrow t-center">
                    <input type="button" id="bindBtn" class="button blue-button radius" value="绑定并登录">
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    function checkForm() {
        var username = $("#username").val();
        if(!isEmail(username) && !isMobile(username)){
            $("#username").parent().next().removeClass("none");
            $("#username").focus();
            return false;
        }else{
            $("#username").parent().next().addClass("none");
        }

        var password = $("#password").val();
        if(!(password.length>=6 && password.length <=24)){
            $("#password").parent().next().removeClass("none");
            $("#password").focus();
            return false;
        }else{
            $("#password").parent().next().addClass("none");
        }

        return true;
    }

    $(function(){
        if ("${err}"){
            layer.msg("${err}");
        }
        //调用密码切换
        $('.pwdChange-hook').on('click',function() {
            var inputType = $("#password").attr("type");
            if (inputType == 'password'){
                $("#password").prop("type", "text");
                $(this).removeClass('pwdChange-on').addClass('pwdChange-off');
            }else{
                $("#password").prop("type", "password");
                $(this).removeClass('pwdChange-off').addClass('pwdChange-on');
            }
        });

        $("#bindForm").find("input").change(function(){
            checkForm();
        });

        $("#bindBtn").click(function () {
           if (checkForm()){
               $("#bindForm").submit();
           }
        });
    });
</script>
</body>
</html>

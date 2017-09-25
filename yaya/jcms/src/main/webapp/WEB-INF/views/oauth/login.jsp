<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/4/18
  Time: 14:33
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta name="keywords" content="社交，社交平台，医生，论坛，专属医生社交平台，专业医师，专业药师，执业医师，执业药师，执业资格证，医疗工具，实用，药草园">
    <title>YaYa_首个医生移动学术交流平台_药草园</title>
    <link rel="shortcut icon" href="http://www.medcn.cn/favicon.ico">
    <link rel="icon" type="image/ico" href="http://www.medcn.cn/favicon.ico" >
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/login/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/login/css/index.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/login/css/completer.css"/>
</head>

<body>


<!--内容区域-->
<div class="warp">
    <div class="main-item">
        <div class="items_1">
            <!--登陆框-->
            <form action="${ctx}/oauth/authorize" id="loginForm" name="loginForm" method="post">
                <input type="hidden" name="redirect_uri" value="${redirect_uri}"/>
                <div class="items_wrap">
                    <div class="login_con fr">
                        <div class="login_form">
                            <p class="fs14 login_box">账号登录</p>
                            <div class="login_box login_tip login_tip_error">${message}</div>
                            <p class="f_input f_account">
                                <input type="text" name="username" id="username" placeholder="公众号/医者号/会员" />
                                <label for="username" class="ico"></label>
                            <div id="accountTip"></div>

                            </p>
                            <p class="f_input f_password login_box">
                                <input type="password" name="password" id="password" placeholder="请输入密码" />
                                <label for="password" class="ico"></label>

                            </p>

                            <div class="login_box">
                                <a href="javascript:void(0);" class="login_btn" onclick="loginCheck();">登录</a>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    function loginCheck(){
        var $username = $("#username");
        var $password = $("#password");

        if ($.trim($username.val()) == ''){
            $(".login_tip_error").text("用户名不能为空");
            return;
        }

        if($.trim($password.val()) == ''){
            $(".login_tip_error").text("密码不能为空");
            return;
        }

        $(".login_tip_error").text("");
        $("#loginForm").submit();

    }
</script>
</body>
</html>

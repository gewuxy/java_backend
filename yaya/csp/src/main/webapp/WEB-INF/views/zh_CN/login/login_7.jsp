<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/16
  Time: 17:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>邮箱登录</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<div id="wrapper">
    <div class="login login-banner" style="height:900px;">
        <div class="page-width pr">
            <div class="login-header">
                <a href="#" class="login-language" title="切换语言">中文</a>
            </div>
            <div class="login-box clearfix">
                <div class="col-lg-5">
                    <div class="login-box-logo">
                        <img src="${ctxStatic}/images/login-logo.png" alt="">
                    </div>
                </div>
                <div class="col-lg-2">&nbsp;</div>
                <div class="col-lg-5 login-box-item">


                    <!--切换  邮箱登录-->
                    <div class="login-box-main position-phone-login">
                        <form action="">
                            <div class="login-form-item">
                                <label for="email" class="cells-block pr"><input id="email" type="text" class="login-formInput" placeholder="邮箱地址"></label>
                                <label for="pwd" class="cells-block pr">
                                    <input type="text" required placeholder="输入6~24位密码" class="login-formInput icon-register-hot last none" maxlength="24">
                                    <input id="pwd" type="password" required placeholder="输入6~24位密码" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                                    <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                                </label>
                                <span class="cells-block error  "><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;请输入正确的邮箱地址</span>
                                <input href="#" type="button" class="button login-button buttonBlue last" value="确认登录">
                            </div>
                        </form>
                    </div>


                    <!--登录用-->
                    <div class="login-box-other">
                        <div class="login-box-other-info t-center">
                            <a href="#" class="color-wathet-blue">我要注册</a><span class="muted">|</span><a href="#" class="color-wathet-blue">忘记密码</a>
                        </div>
                    </div>
                    <div class="login-box-info t-center">
                        <p>登录即表示您已同意 <a href="subPage-service.html" class="color-blue">《CSPmeeting服务协议》</a> </p>
                    </div>
                </div>
            </div>
            <div class="login-bottom">
                <p><%@include file="/WEB-INF/include/copy_right.jsp"%></p>
            </div>
        </div>
    </div>
</div>

<script>
    $(function(){
        //让背景撑满屏幕
        $('.login-banner').height($(window).height());
        //让协议定位到底部
        $('.login-box-item').height($('.login-box').height());




        //调用密码切换
        $('.pwdChange-hook').on('click',function() {
            changePassWordStatus($('.hidePassword'));
        });

    })
</script>
</body>
</html>

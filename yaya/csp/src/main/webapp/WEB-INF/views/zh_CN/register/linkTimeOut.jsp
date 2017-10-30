<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/19
  Time: 9:22
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>超时提示界面</title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
</head>


<body>
<div id="wrapper">
    <div class="login login-banner" style="height:900px;">
        <div class="page-width pr">
            <div class="login-header">
                <%@include file="/WEB-INF/include/login_header_zh_CN.jsp"%>
                <%@include file="/WEB-INF/include/switch_language_zh_CN.jsp"%>
            </div>
            <div class="login-box clearfix">
                <div class="col-lg-5">
                    <div class="login-box-logo">
                        <img src="${ctxStatic}/images/login-logo.png" alt="">
                    </div>
                </div>
                <div class="col-lg-2">&nbsp;</div>
                <div class="col-lg-5 login-box-item">

                    <!--切换  重置密码-->
                    <div class="login-box-main position-message-login ">
                        <form action="">
                            <div class="login-form-item">
                                <div class="login-message-text" style="text-align: left !important;">
                                    <div class="fl" style="margin-right:30px;">
                                        <img src="${ctxStatic}/images/login-error-icon-02.png" alt="" style="margin-top:10px;">
                                    </div>
                                    <div class="oh">
                                        <p class="color-red">链接已超时</p>
                                        <p class="color-red">请重新验证！</p>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
            <div class="login-bottom">
                <p><%@include file="/WEB-INF/include/copy_right_zh_CN.jsp"%></p>
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



    })
</script>

</body>
</html>

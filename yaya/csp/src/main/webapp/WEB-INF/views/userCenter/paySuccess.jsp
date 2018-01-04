<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/19
  Time: 9:22
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="page.words.weiXin.tips7"/> </title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="${ctxStatic}/js/commonH5.js"></script>


</head>
<body>
<div id="wrapper">
    <div class="login login-banner" style="height:900px;">
        <div class="page-width pr">
            <div class="login-header">
                <%@include file="../include/login_header.jsp"%>
                <%@include file="/WEB-INF/include/switch_language.jsp"%>
            </div>
            <div class="login-box clearfix">
                <%@include file="../include/login_left.jsp"%>

                <div class="col-lg-5 login-box-item">

                    <!--切换  重置密码-->
                    <div class="login-box-main position-message-login ">
                        <form action="">
                            <div class="login-form-item">
                                <div class="login-message-text login-message-text-2">
                                    <p><img src="${ctxStatic}/images/icon-succeed.png" alt=""></p>
                                    <p class="t-center color-blue"><fmt:message key="page.words.weiXin.tips7"/> ！</p>
                                    <p ><fmt:message key="page.words.weiXin.tips8"/> &nbsp;<span class="color-blue">${money}<fmt:message key="page.words.weiXin.tips9"/> </span>&nbsp;<fmt:message key="page.words.weiXin.tips10"/> </p>
                                </div>
                                <input  type="button" class="button login-button buttonBlue last" id="time" value="<fmt:message key="page.words.success.close"/>">
                            </div>
                        </form>

                    </div>

                </div>
            </div>
            <%@include file="../include/footer.jsp"%>
        </div>
    </div>
</div>

<script>
    var n = 5;
    $(function(){
        //让背景撑满屏幕
        $('.login-banner').height($(window).height());

        //让协议定位到底部
        $('.login-box-item').height($('.login-box').height());

        var timer=setTimeout(myClose,1000);

        $("#time").click(function () {
            close();
        });

    })

    //定义函数myClose关闭当前窗口
    function myClose(){
        n--;//将n-1
        //如果n==0,关闭页面
        //否则, 将n+秒钟后自动关闭，再保存回time的内容中
        if(n>0){
            var locale = '${csp_locale}';
            if(locale == 'en_US'){
                $("#time").val("Return in "+ n+"s...");
            }else if(locale == 'zh_CN'){
                $("#time").val(n+ "s后自动关闭");
            }else if(locale == 'zh_TW'){
                $("#time").val(n+ "s後自動關閉");
            }else{
                $("#time").val("Return in "+ n+"s...");
            }
            timer=setTimeout(myClose,1000);
        }else{
            close();
        }
    }
</script>
</body>
</html>
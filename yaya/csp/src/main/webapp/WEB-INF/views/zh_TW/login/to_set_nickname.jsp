<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/19
  Time: 18:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>第壹次手機登錄 - 需要輸入昵稱-會講</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
</head>

<body>
<div id="wrapper">
    <div class="login login-banner" >
        <div class="page-width pr">

            <div class="login-header">
                <%@include file="../include/login_header.jsp"%>
                <%@include file="/WEB-INF/include/switch_language.jsp"%>
            </div>

            <div class="login-box clearfix">

                <%@include file="../include/login_left.jsp"%>

                <div class="col-lg-5 login-box-item">

                    <!--切换 输入昵称 -->
                    <div class="login-box-main position-resetEmail-login" >
                        <form action="${ctx}/mgr/login/addNickName" method="post" id="loginForm" name="loginForm">
                        <input type="hidden" name="id" value="${id}">
                        <div class="login-form-item">
                                <label for="nickname" class="cells-block pr">
                                    <input id="nickname" name="nickName" value="${nickName}" type="text" class="login-formInput" placeholder="昵稱" maxlength="18">
                                </label>
                                <span class="cells-block error ${not empty error ? '':'none'}" >
                                    <img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span id="errorMessage">${error}</span>
                                </span>
                                <input id="submitBtn" type="button" class="button login-button buttonBlue last" value="確認提交">
                            </div>
                        </form>
                    </div>

                    <%@include file="../include/login_service.jsp"%>

                </div>
            </div>

            <%@include file="../include/login_footer.jsp"%>

        </div>
    </div>
</div>


<script type="text/javascript">
    $(function(){
        //让背景撑满屏幕
        $('.login-banner').height($(window).height());
        //让协议定位到底部
        $('.login-box-item').height($('.login-box').height());


        $("#submitBtn").click(function(){
            var nick = $("#nickname").val();

            if (isEmpty(nick)) {
                $("#errorMessage").text("請輸入昵稱");
                $("#errorMessage").parent().removeClass("none");
                $("#nickname").focus();
                return false;
            } else if (nick.indexOf(" ") != -1) {
                $("#errorMessage").text("昵稱不能輸入空格");
                $("#errorMessage").parent().removeClass("none");
                $("#nickname").focus();
                return false;
            } else if ($.trim(nick).length > 18) {
                $("#errorMessage").text("昵稱長度不能超過18位");
                $("#errorMessage").parent().removeClass("none");
                $("#nickname").focus();
                return false;
            }

            $("#errorMessage").parent().addClass("none");
            $("#loginForm").submit();
        });

    })

    $(document).keydown(function(evt){
        if (evt.keyCode == 13){
            $("#submitBtn").trigger("click");
        }
    });
</script>
</body>
</html>

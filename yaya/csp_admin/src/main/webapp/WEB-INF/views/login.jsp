<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include/taglib.jsp"%>
<html lang="en">
<head>
    <title>敬信药草园运营管理系统</title>
    <%@include file="/WEB-INF/include/common_css.jsp"%>
    <style type="text/css">
        html,body,table{background-color:#f5f5f5;width:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px;color:#0663a2;}
        .form-signin{position:relative;text-align:left;width:300px;padding:25px 29px 29px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
            -webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);}
        .form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
        .form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
        .form-signin .btn.btn-large{font-size:16px;} .form-signin #themeSwitch{position:absolute;right:15px;bottom:10px;}
        .form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
        .header{height:80px;padding-top:20px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
        label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
    </style>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#loginForm").validate({
                messages: {
                    username: {required: "请填写用户名."},password: {required: "请填写密码."},
                    validateCode: {required: "请填写验证码"},
                },
                errorLabelContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    error.appendTo($("#loginError").parent());
                }
            });
        });
        // 如果在框架或在对话框中，则弹出提示并跳转到首页
        if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
            layer.msg('您的会话已失效,请重新登录',{time:1500},function(){
                top.location = "${ctx}";
            })
        }
    </script>
</head>
<body>
<div class="header" style="height: 110px;">
    <div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
        <label id="loginError" class="error" style="text-align: left;">${message}</label>
    </div>
</div>
<%-- <h1 class="form-signin-heading">${fns:getConfig('productName')}</h1> --%>
<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
    <label class="input-label" for="username">登录名</label>
    <input type="text" id="username" name="username" class="input-block-level required" value="${username}">
    <label class="input-label" for="password">密码</label>
    <input type="password" id="password" name="password" class="input-block-level required">
    <label class="input-label mid" for="validateCode">验证码</label>
    <div class="validateCode">
        <input type="text" id="validateCode" name="validateCode" maxlength="6" class="txt required" style="font-weight:bold;width:60px;"/>
        <img src="${ctx}/validateCode" onclick="$('.validateCodeRefresh').click();" class="mid validateCode"/>
        <a href="javascript:" onclick="$('.validateCode').attr('src','${ctx}/validateCode?'+new Date().getTime());" class="mid validateCodeRefresh">看不清</a>
    </div>
    <input class="btn btn-large btn-primary" type="submit" value="登 录"/>&nbsp;&nbsp;
</form>
<%@include file="/WEB-INF/include/common_js.jsp"%>
</body>
</html>

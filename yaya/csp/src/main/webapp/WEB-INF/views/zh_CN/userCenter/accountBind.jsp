<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>账号管理-个人中心-会讲</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>

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
                        <div class="user-content user-content-levelHeight item-radius">
                            <div class="binding-list">
                                <ul>
                                    <li class="phone">
                                        <c:if test="${not empty dto.mobile}">
                                            <a href="#" class="fr binding-btn " type="6"  action_type="unbind">解绑</a>
                                            <img src="${ctxStatic}/images/icon-user-phone.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty dto.mobile}">
                                            <a href="#" class="fr binding-btn color-blue" id="bindPhone" type="6" >绑定</a>
                                            <img src="${ctxStatic}/images/icon-user-phone.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${empty dto.mobile?"未绑定":dto.mobile}</span>
                                    </li>
                                    <li class="wechat">
                                        <c:if test="${not empty weChat}">
                                            <a href="#" class="fr binding-btn " type="1" id="unbind_1" action_type="unbind">解绑</a>
                                            <img src="${ctxStatic}/images/icon-user-wechat.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty weChat}">
                                            <a href="#" class="fr binding-btn color-blue" type="1" action_type="bind">绑定</a>
                                            <img src="${ctxStatic}/images/icon-user-wechat.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${empty weChat ?"未绑定":weChat}</span>
                                    </li>
                                    <li class="weibo">
                                        <c:if test="${not empty weiBo}">
                                            <a href="#" class="fr binding-btn " type="2" id="unbind_2" action_type="unbind">解绑</a>
                                            <img src="${ctxStatic}/images/icon-user-weibo.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty weiBo}">
                                            <a href="#" class="fr binding-btn color-blue" type="2" action_type="bind">绑定</a>
                                            <img src="${ctxStatic}/images/icon-user-weibo.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${empty weiBo ?"未绑定":weiBo}</span>
                                    </li>
                                    <li class="email">
                                        <c:if test="${not empty dto.email}">
                                            <a href="#" class="fr binding-btn " type="7" id="unbind_7" action_type="unbind">解绑</a>
                                            <img src="${ctxStatic}/images/icon-user-email.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty dto.email}">
                                            <a href="#" class="fr binding-btn color-blue" type="7" id="bindEmail" >绑定</a>
                                            <img src="${ctxStatic}/images/icon-user-email.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${empty dto.email ?"未绑定":dto.email}</span>
                                    </li>
                                    <li class="medcn">
                                        <c:if test="${not empty YaYa}">
                                            <a href="#" class="fr binding-btn " type="5" id="unbind_5" action_type="unbind">解绑</a>
                                            <img src="${ctxStatic}/images/icon-user-medcn.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty YaYa}">
                                            <a href="#" class="fr binding-btn color-blue" type="5" action_type="bind">绑定</a>
                                            <img src="${ctxStatic}/images/icon-user-medcn.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${empty YaYa ?"未绑定":"敬信数字平台"}</span>
                                    </li>
                                </ul>
                                <span class="line-bg"></span>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <%@include file="../include/footer.jsp"%>
</div>

<!--弹出绑定手机-->
<div class="phone-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="login-form-item">
                    <label for="phone" class="cells-block pr">
                        <input id="phone" type="tel" class="login-formInput" placeholder="手机号"></label>
                    <span id="phoneSpan" class="cells-block error none "><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;</span>
                    <label for="captcha" class="cells-block pr">
                        <input id="captcha" type="text" class="login-formInput codeInput" placeholder="输入验证码">
                        <span href="javascript:;" class="code" id="btnSendCode"  onclick="sendMessage()">获取</span>
                    </label>
                    <span id="captchaSpan" class="cells-block error none "><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;验证码错误</span>
                    <input href="#" type="button" id="phoneBtn" class="button login-button buttonBlue last" value="绑定手机">
                </div>
            </form>
        </div>
    </div>
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
                    <input id="email" type="text" class="login-formInput" placeholder="邮箱地址">
                </label>
                <span class="cells-block error none" id="emailSpan"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;输入正确密码</span>
                <label for="password" class="cells-block pr">
                    <input type="text" required="" placeholder="密码" class="login-formInput icon-register-hot last none" maxlength="24">
                    <input id="password" type="password" required="" placeholder="密码" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                    <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                </label>
                <span class="cells-block error none" id="passwordSpan"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;输入正确密码</span>
                <input href="#" type="button" id="emailBtn" class="button login-button buttonBlue email-hook-02 last" value="绑定邮箱">
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
                    <p>激活账号邮件已发送至您的邮箱，<br />请前往激活完成注册。</p>
                </div>
                <input href="#" type="button" id="goToEmail" class="button login-button buttonBlue close-button layui-layer-close last" value="前往邮箱">
            </div>
        </div>
    </div>
</div>

<!--弹出解绑-->
<div class="cancel-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="cancel-popup-main">
                    <p><img src="${ctxStatic}/images/question-32x32.png" alt="">是否确定解绑此账号？</p>
                </div>

            </form>
        </div>
    </div>
</div>


<script>

    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount;//当前剩余秒数



    $(function () {
        $("#config_3").parent().attr("class","cur");

        if(${not empty err}){
            layer.msg('${err}');
        }

        const classPwdOn = "pwdChange-on";
        const classPwdOff = "pwdChange-off";
        $(".pwdChange-hook").click(function(){
            if($(this).hasClass(classPwdOn)){
                $(this).removeClass(classPwdOn);
                $(this).addClass(classPwdOff);
                $("#password").prop("type", "text");
            } else {
                $(this).removeClass(classPwdOff);
                $(this).addClass(classPwdOn);
                $("#password").prop("type", "password");
            }
        });

        //解绑操作
        $("a[action_type='unbind']").click(function () {
            var type = $(this).attr("type");
            layer.open({
                type: 1,
                area: ['440px', '250px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                btn: ["确认","取消"],
                content: $('.cancel-popup-box'),
                yes:function () {
                    $.get('${ctx}/mgr/user/unbind',{"type":type}, function (data) {
                        if (data.code == 0){
                            layer.msg('解绑成功',{
                                time: 300
                            },function(){
                                window.location.href = "${ctx}/mgr/user/toAccount";
                            });
                        }else{
                            layer.msg(data.err);
                        }
                    },'json');
                },

            });

        });


        //绑定操作
        $("a[action_type='bind']").click(function () {
            var type = $(this).attr("type");

            $.get('${ctx}/mgr/user/jumpOauth',{"type":type}, function (data) {
                if (data.code == 0){
                    window.location.href=data.data;
                }else{
                    layer.msg(data.err);
                }
            },'json');
        });

        //弹出绑定手机
        $('#bindPhone').on('click',function(){
            layer.open({
                type: 1,
                area: ['609px', '340px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                content: $('.phone-popup-box'),
                success:function(layero, index){

                },
                cancel :function(){

                },
            });
        });
        
        $("#phone").blur(function () {
            isPhone();
        });

        $("#email").blur(function () {
            isEmail();
        });

        $("#phoneBtn").click(function () {
            if(isPhone()){
                var mobile = $("#phone").val();
                var captcha = $("#captcha").val();
                if(checkCaptcha()){
                    $.get('${ctx}/mgr/user/bindMobile',{"mobile":mobile,"captcha":captcha}, function (data) {
                        if (data.code == 0){
                            layer.closeAll();
                            layer.msg("绑定成功",{ time: 800},function () {
                               window.location.href="${ctx}/mgr/user/toAccount";
                            });
                        }else{
                            layer.msg(data.err);
                        }
                    },'json');
                }


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
                layer.msg("抱歉!未找到对应的邮箱登录地址");
            }
        });


    });

    function isPhone() {
        var phone = $("#phone").val();
        if($.trim(phone) == ''){
            $("#phoneSpan").attr("class","cells-block error");
            $("#phoneSpan").html("手机号不能为空");
            return false;
        }
        var reg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        if(!reg.test(phone)){
            $("#phoneSpan").attr("class","cells-block error");
            $("#phoneSpan").html("请输入正确的手机格式");
            return false;
        }
        $("#phoneSpan").attr("class","cells-block error none");
        return true;
    }

    function isEmail() {
        var email = $("#email").val();
        if($.trim(email) == ''){
            $("#emailSpan").attr("class","cells-block error ");
            $("#emailSpan").html("邮箱不能为空");
            return false;
        }
        if(!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
            $("#emailSpan").attr("class","cells-block error ");
            $("#emailSpan").html("邮箱格式不正确");
            return false;
        }
        $("#emailSpan").attr("class","cells-block error none");
        return true;
    }

    function checkCaptcha() {
        var captcha = $("#captcha").val();
        if($.trim(captcha) == '' ){
            $("#captchaSpan").attr("class","cells-block error");
            $("#captchaSpan").html("验证码不能为空");
            return false;
        }

        var re = /^[0-9]+.?[0-9]*$/;
        if(!re.test(captcha)){
            $("#captchaSpan").attr("class","cells-block error");
            $("#captchaSpan").html("验证码格式不正确");
            return false;
        }

        return true;
    }


    function checkPwd() {
        var password = $("#password").val();
        if ($.trim(password)==''){
            $("#passwordSpan").html("密码不能为空");
            $("#passwordSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password)!= password){
            $("#passwordSpan").html("密码不能包含空格");
            $("#passwordSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password).length < 6){
            $("#passwordSpan").html("请输入6~24位密码");
            $("#passwordSpan").attr("class","cells-block error");
            return false;
        }else if(isChinesePassword($.trim(password))){
            $("#passwordSpan").html("密码不能包含中文");
            $("#passwordSpan").attr("class","cells-block error");
            return false;
        }else{
            $("#passwordSpan").attr("class","cells-block error none");
            return true;
        }

    }

    function sendMessage() {
        if(isPhone()){
            curCount = count;
            //设置button效果，开始计时
            $("#btnSendCode").attr("disabled", "true");
            $("#btnSendCode").text(curCount + "s" );
            $("#btnSendCode").addClass("getCodeButton-current");
            InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
            //向后台发送处理数据
            var mobile =  $("#phone").val();
            $.get('${ctx}/api/user/sendCaptcha',{"mobile":mobile,"type":1}, function (data) {
                if (data.code == 0){
                    layer.msg("验证码已经发送到您的手机,请注意查收");
                    $("#btnSendCode").removeAttr("onclick");
                }else{
                    layer.msg(data.err);
                }
            },'json');

        }

    }

    //timer处理函数
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj);//停止计时器
            $("#btnSendCode").removeClass("getCodeButton-current");
            $("#btnSendCode").removeAttr("disabled");//启用按钮
            $("#btnSendCode").text("重新发送");
        }
        else {
            curCount--;
            $("#btnSendCode").text(curCount + "s");
        }
    }




</script>

</body>
</html>




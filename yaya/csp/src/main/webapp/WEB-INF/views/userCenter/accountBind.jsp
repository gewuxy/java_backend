<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html >
<head>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <title><fmt:message key="page.header.account"/> -<fmt:message key="page.title.user.title"/> -<fmt:message key="page.common.appName"/> </title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
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
                                    <c:if test="${csp_locale == 'zh_CN'}">
                                        <li class="phone">
                                            <c:if test="${not empty dto.mobile}">
                                                <a href="#" class="fr binding-btn " type="6"  action_type="unbind"><fmt:message key="page.words.unbind"/> </a>
                                                <img src="${ctxStatic}/images/icon-user-phone.png" alt="">
                                                <span class="status status-on"></span>
                                            </c:if>
                                            <c:if test="${empty dto.mobile}">
                                                <a href="#" class="fr binding-btn color-blue" id="bindPhone" type="6" ><fmt:message key="page.words.bind"/> </a>
                                                <img src="${ctxStatic}/images/icon-user-phone.png" alt="">
                                                <span class="status status-off"></span>
                                            </c:if>
                                            <c:if test="${empty dto.mobile}">
                                                <span class="main"><fmt:message key="page.words.not.bind"/> </span>
                                            </c:if>
                                            <c:if test="${not empty dto.mobile}">
                                                <span class="main">${dto.mobile} </span>
                                            </c:if>
                                        </li>
                                        <li class="wechat">
                                            <c:if test="${not empty weChat}">
                                                <a href="#" class="fr binding-btn " type="1" id="unbind_1" action_type="unbind"><fmt:message key="page.words.unbind"/> </a>
                                                <img src="${ctxStatic}/images/icon-user-wechat.png" alt="">
                                                <span class="status status-on"></span>
                                            </c:if>
                                            <c:if test="${empty weChat}">
                                                <a href="#" class="fr binding-btn color-blue" type="1" action_type="bind"><fmt:message key="page.words.bind"/> </a>
                                                <img src="${ctxStatic}/images/icon-user-wechat.png" alt="">
                                                <span class="status status-off"></span>
                                            </c:if>
                                            <c:if test="${empty weChat}">
                                                <span class="main"><fmt:message key="page.words.not.bind"/> </span>
                                            </c:if>
                                            <c:if test="${not empty weChat}">
                                                <span class="main">${weChat} </span>
                                            </c:if>
                                        </li>
                                        <li class="weibo">
                                            <c:if test="${not empty weiBo}">
                                                <a href="#" class="fr binding-btn " type="2" id="unbind_2" action_type="unbind"><fmt:message key="page.words.unbind"/></a>
                                                <img src="${ctxStatic}/images/icon-user-weibo.png" alt="">
                                                <span class="status status-on"></span>
                                            </c:if>
                                            <c:if test="${empty weiBo}">
                                                <a href="#" class="fr binding-btn color-blue" type="2" action_type="bind"><fmt:message key="page.words.bind"/></a>
                                                <img src="${ctxStatic}/images/icon-user-weibo.png" alt="">
                                                <span class="status status-off"></span>
                                            </c:if>
                                            <c:if test="${empty weiBo}">
                                                <span class="main"><fmt:message key="page.words.not.bind"/> </span>
                                            </c:if>
                                            <c:if test="${not empty weiBo}">
                                                <span class="main">${weiBo} </span>
                                            </c:if>
                                        </li>
                                    </c:if>
                                    <c:if test="${csp_locale != 'zh_CN'}">
                                        <li class="facebook">
                                            <c:if test="${not empty facebook}">
                                                <a href="#" class="fr binding-btn " type="3"  action_type="unbind"><fmt:message key="page.words.unbind"/></a>
                                                <img src="${ctxStatic}/images/icon-user-facebook.png" alt="">
                                                <span class="status status-on"></span>
                                            </c:if>
                                            <c:if test="${empty facebook}">
                                                <a href="#" class="fr binding-btn color-blue"  type="3" onclick="facebookLogin()"><fmt:message key="page.words.bind"/></a>
                                                <img src="${ctxStatic}/images/icon-user-facebook.png" alt="">
                                                <span class="status status-off"></span>
                                            </c:if>
                                            <c:if test="${empty facebook}">
                                                <span class="main"><fmt:message key="page.words.not.bind"/> </span>
                                            </c:if>
                                            <c:if test="${not empty facebook}">
                                                <span class="main">${facebook} </span>
                                            </c:if>
                                        </li>
                                        <li class="twitter">
                                            <c:if test="${not empty twitter}">
                                                <a href="#" class="fr binding-btn " type="4" id="unbind_4" action_type="unbind"><fmt:message key="page.words.unbind"/></a>
                                                <img src="${ctxStatic}/images/icon-user-twitter.png" alt="">
                                                <span class="status status-on"></span>
                                            </c:if>
                                            <c:if test="${empty twitter}">
                                                <a href="#" class="fr binding-btn color-blue" id="twitter"  onclick="twitterLogin()"><fmt:message key="page.words.bind"/></a>
                                                <img src="${ctxStatic}/images/icon-user-twitter.png" alt="">
                                                <span class="status status-off"></span>
                                            </c:if>
                                            <c:if test="${empty twitter}">
                                                <span class="main"><fmt:message key="page.words.not.bind"/> </span>
                                            </c:if>
                                            <c:if test="${not empty twitter}">
                                                <span class="main">${twitter} </span>
                                            </c:if>
                                        </li>
                                    </c:if>
                                    <li class="email">
                                        <c:if test="${not empty dto.email}">
                                            <a href="#" class="fr binding-btn " type="7" id="unbind_7" action_type="unbind"><fmt:message key="page.words.unbind"/></a>
                                            <img src="${ctxStatic}/images/icon-user-email.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty dto.email}">
                                            <a href="#" class="fr binding-btn color-blue" type="7" id="bindEmail" ><fmt:message key="page.words.bind"/></a>
                                            <img src="${ctxStatic}/images/icon-user-email.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <c:if test="${empty dto.email}">
                                            <span class="main"><fmt:message key="page.words.not.bind"/> </span>
                                        </c:if>
                                        <c:if test="${not empty dto.email}">
                                            <span class="main">${dto.email} </span>
                                        </c:if>
                                    </li>
                                    <li class="medcn">
                                        <c:if test="${not empty YaYa}">
                                            <a href="#" class="fr binding-btn " type="5" id="unbind_5" action_type="unbind"><fmt:message key="page.words.unbind"/></a>
                                            <img src="${ctxStatic}/images/icon-user-medcn.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty YaYa}">
                                            <a href="#" class="fr binding-btn color-blue" type="5" action_type="bind"><fmt:message key="page.words.bind"/></a>
                                            <img src="${ctxStatic}/images/icon-user-medcn.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <c:if test="${empty YaYa}">
                                            <span class="main"><fmt:message key="page.words.not.bind"/> </span>
                                        </c:if>
                                        <c:if test="${not empty YaYa}">
                                            <span class="main"><fmt:message key="page.words.jingXin"/> </span>
                                        </c:if>
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
    <c:if test="${csp_locale != 'zh_CN'}">
        <%@include file="/WEB-INF/include/twitter_fb_form.jsp" %>
    </c:if>
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
                        <input id="phone" type="tel" class="login-formInput" placeholder="<fmt:message key="page.words.mobile"/>"></label>
                    <span id="phoneSpan" class="cells-block error none "><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;</span>
                    <label for="captcha" class="cells-block pr">
                        <input id="captcha" type="text" class="login-formInput codeInput" placeholder="<fmt:message key="page.words.input.captcha"/>">
                        <span href="javascript:;" class="code" id="btnSendCode"  onclick="sendMessage()"><fmt:message key="page.words.get.captcha"/></span>
                    </label>
                    <span id="captchaSpan" class="cells-block error none "><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<fmt:message key="page.words.captcha.error"/> </span>
                    <input href="#" type="button" id="phoneBtn" class="button login-button buttonBlue last" value="<fmt:message key="page.words.bind.mobile"/>">
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
                    <input id="email" type="text" class="login-formInput" placeholder="<fmt:message key="page.email.login.address"/>">
                </label>
                <span class="cells-block error none" id="emailSpan"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;输入正确密码</span>
                <label for="password" class="cells-block pr">
                    <input type="text" required="" placeholder="<fmt:message key="page.label.password"/>" class="login-formInput icon-register-hot last none" maxlength="24">
                    <input id="password" type="password" required="" placeholder="<fmt:message key="page.label.password"/>" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                    <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                </label>
                <span class="cells-block error none" id="passwordSpan"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;输入正确密码</span>
                <input href="#" type="button" id="emailBtn" class="button login-button buttonBlue email-hook-02 last" value="<fmt:message key="page.button.bind.email"/>">
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
                    <p><fmt:message key="page.words.email.tips1"/> <br /><fmt:message key="page.words.email.tips2"/></p>
                </div>
                <input href="#" type="button" id="goToEmail" class="button login-button buttonBlue close-button layui-layer-close last" value="<fmt:message key="page.words.to.email"/>">
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
                    <p><img src="${ctxStatic}/images/question-32x32.png" alt=""><fmt:message key="page.words.unbind.account"/> </p>
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
                area: ['650px', '250px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                btn: ['<fmt:message key="page.common.confirm"/>','<fmt:message key="page.common.cancel"/>'],
                content: $('.cancel-popup-box'),
                yes:function () {
                    ajaxGet('${ctx}/mgr/user/unbind',{"type":type}, function (data) {
                        if (data.code == 0){
                            layer.msg('<fmt:message key="page.words.unbind.success"/>',{
                                time: 300
                            },function(){
                                window.location.href = "${ctx}/mgr/user/toAccount";
                            });
                        }else{
                            layer.msg(data.err);
                        }
                    });
                },

            });

        });


        //绑定操作
        $("a[action_type='bind']").click(function () {
            var type = $(this).attr("type");
            ajaxGet('${ctx}/mgr/user/jumpOauth',{"type":type}, function (data) {
                if (data.code == 0){
                    window.location.href=data.data;
                }else{
                    layer.msg(data.err);
                }
            });
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
                    ajaxGet('${ctx}/mgr/user/bindMobile',{"mobile":mobile,"captcha":captcha}, function (data) {
                        if (data.code == 0){
                            layer.closeAll();
                            layer.msg('<fmt:message key="page.words.bind.success"/>',{ time: 800},function () {
                                window.location.href="${ctx}/mgr/user/toAccount";
                            });
                        }else{
                            layer.msg(data.err);
                        }
                    });
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
                    ajaxGet('${ctx}/mgr/user/bindEmail',{"email":email,"password":password}, function (data) {
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
                    });
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
                layer.msg('<fmt:message key="page.words.email.address.error"/>');
            }
        });


    });

    function isPhone() {
        var phone = $("#phone").val();
        if($.trim(phone) == ''){
            $("#phoneSpan").attr("class","cells-block error");
            $("#phoneSpan").html("<fmt:message key="page.words.mobile.none"/>");
            return false;
        }
        var reg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        if(!reg.test(phone)){
            $("#phoneSpan").attr("class","cells-block error");
            $("#phoneSpan").html("<fmt:message key="page.words.mobile.format.error"/>");
            return false;
        }
        $("#phoneSpan").attr("class","cells-block error none");
        return true;
    }

    function isEmail() {
        var email = $("#email").val();
        if($.trim(email) == ''){
            $("#emailSpan").attr("class","cells-block error ");
            $("#emailSpan").html("<fmt:message key="page.words.email.none"/>");
            return false;
        }
        if(!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
            $("#emailSpan").attr("class","cells-block error ");
            $("#emailSpan").html("<fmt:message key="page.words.email.format.error"/>");
            return false;
        }
        $("#emailSpan").attr("class","cells-block error none");
        return true;
    }

    function checkCaptcha() {
        var captcha = $("#captcha").val();
        if($.trim(captcha) == '' ){
            $("#captchaSpan").attr("class","cells-block error");
            $("#captchaSpan").html("<fmt:message key="page.words.captcha.none"/>");
            return false;
        }

        var re = /^[0-9]+.?[0-9]*$/;
        if(!re.test(captcha)){
            $("#captchaSpan").attr("class","cells-block error");
            $("#captchaSpan").html("<fmt:message key="page.words.captcha.format.error"/>");
            return false;
        }

        return true;
    }


    function checkPwd() {
        var password = $("#password").val();
        if ($.trim(password)==''){
            $("#passwordSpan").html("<fmt:message key="page.words.password.none"/>");
            $("#passwordSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password)!= password){
            $("#passwordSpan").html("<fmt:message key="page.words.password.blank"/>");
            $("#passwordSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password).length < 6){
            $("#passwordSpan").html("<fmt:message key="page.words.password.length"/>");
            $("#passwordSpan").attr("class","cells-block error");
            return false;
        }else if(isChinesePassword($.trim(password))){
            $("#passwordSpan").html("<fmt:message key="page.words.password.chinese"/>");
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
            ajaxGet('${ctx}/api/user/sendCaptcha',{"mobile":mobile,"type":1}, function (data) {
                if (data.code == 0){
                    layer.msg("<fmt:message key="page.words.captcha.send"/>");
                    $("#btnSendCode").removeAttr("onclick");
                }else{
                    layer.msg(data.err);
                }
            });
        }

    }

    //timer处理函数
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj);//停止计时器
            $("#btnSendCode").removeClass("getCodeButton-current");
            $("#btnSendCode").removeAttr("disabled");//启用按钮
            $("#btnSendCode").text("<fmt:message key="page.words.resend"/>");
        }
        else {
            curCount--;
            $("#btnSendCode").text(curCount + "s");
        }
    }




</script>

</body>
</html>




<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>个人中心 - 账号绑定</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style-EN.css">
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="http://adodson.com/hello.js/dist/hello.all.js"></script>

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
                                    <li class="facebook">
                                        <c:if test="${not empty facebook}">
                                            <a href="#" class="fr binding-btn " type="3"  action_type="unbind">Unbind</a>
                                            <img src="${ctxStatic}/images/icon-user-facebook.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty facebook}">
                                            <a href="#" class="fr binding-btn color-blue" id="" type="3" action_type="bind">Bind</a>
                                            <img src="${ctxStatic}/images/icon-user-facebook.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${empty facebook?"Not Bound":facebook}</span>
                                    </li>
                                    <li class="twitter">
                                        <c:if test="${not empty twitter}">
                                            <a href="#" class="fr binding-btn " type="4" id="unbind_4" action_type="unbind">Unbind</a>
                                            <img src="${ctxStatic}/images/icon-user-twitter.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty twitter}">
                                            <a href="#" class="fr binding-btn color-blue" type="4" action_type="bind">Bind</a>
                                            <img src="${ctxStatic}/images/icon-user-twitter.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${empty twitter ?"Not Bound":twitter }</span>
                                    </li>
                                    <li class="email">
                                        <c:if test="${not empty dto.email}">
                                            <a href="#" class="fr binding-btn " type="7" id="unbind_7" action_type="unbind">Unbind</a>
                                            <img src="${ctxStatic}/images/icon-user-email.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty dto.email}">
                                            <a href="#" class="fr binding-btn color-blue" type="7" id="bindEmail" >Bind</a>
                                            <img src="${ctxStatic}/images/icon-user-email.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${empty dto.email ?"Not Bound":dto.email}</span>
                                    </li>
                                    <li class="medcn">
                                        <c:if test="${not empty YaYa}">
                                            <a href="#" class="fr binding-btn " type="5" id="unbind_5" action_type="unbind">Unbind</a>
                                            <img src="${ctxStatic}/images/icon-user-medcn.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty YaYa}">
                                            <a href="#" class="fr binding-btn color-blue" type="5" action_type="bind">Bind</a>
                                            <img src="${ctxStatic}/images/icon-user-medcn.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${empty YaYa ?"Not Bound":"Login with Jingxin Digital Platform"}</span>
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

<form action="${ctx}/mgr/twitterCallback" id="twitterForm" name="twitterForm" method="post">
    <input type="hidden" id="str" name="str">
</form>

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
                    <input id="email" type="text" class="login-formInput" placeholder="E-mail Address">
                </label>
                <span class="cells-block error none" id="emailSpan"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;Enter the correct password</span>
                <label for="password" class="cells-block pr">
                    <input type="text" required="" placeholder="Password" class="login-formInput icon-register-hot last none" maxlength="24">
                    <input id="password" type="password" required="" placeholder="Password" class="login-formInput icon-register-hot hidePassword last" maxlength="24">
                    <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook "></a>
                </label>
                <span class="cells-block error none" id="passwordSpan"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;Enter the correct password</span>
                <input href="#" type="button" id="emailBtn" class="button login-button buttonBlue email-hook-02 last" value="Bind E-mail Address">
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
                    <p> Activation e-mail has been sent to your mail box. Please proceed，activation in e-mail and complete your registration.。</p>
                </div>
                <input href="#" type="button" id="goToEmail" class="button login-button buttonBlue close-button layui-layer-close last" value="Go to My E-mail Box">
            </div>
        </div>
    </div>
</div>

<script>

    window.twttr = (function(d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0],
            t = window.twttr || {};
        if (d.getElementById(id)) return t;
        js = d.createElement(s);
        js.id = id;
        js.src = "https://platform.twitter.com/widgets.js";
        fjs.parentNode.insertBefore(js, fjs);

        t._e = [];
        t.ready = function(f) {
            t._e.push(f);
        };

        return t;
    }(document, "script", "twitter-wjs"));

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
            $.get('${ctx}/mgr/user/unbind',{"type":type}, function (data) {
                if (data.code == 0){
                    layer.msg('unbind success',{
                        time: 300
                    },function(){
                           window.location.href = "${ctx}/mgr/user/toAccount";
                    });
                }else{
                    layer.msg(data.err);
                }
            },'json');
        });




        //绑定操作
        $("a[action_type='bind']").click(function () {
            var type = $(this).attr("type");

            if(type == 4){
                var log = console.log;
                hello.init({
                        'twitter' : 's024Uf0tlvwtDwzKqKLat56Zm'
                    },
                    {
//                      redirect_uri:'/', //代理后的重定向路径，可不填
                        oauth_proxy: 'https://auth-server.herokuapp.com/proxy' //这里使用默认的代理
                    });

                function login_twitter(network){  //登录方法，并将twitter 作为参数传入
                    // Twitter instance
                    var twitter = hello(network);
                    // Login
                    twitter.login().then( function(r){
                        // Get Profile
                        return twitter.api('/me');
                    }, log ) .then( function(p){
                        console.log("Connected to "+ network+" as " + p.name);
                        var res = JSON.stringify(p);//因为得不到token，但是这步已经得到用户所有信息，所以将用户信息转成JSON字符串给后台
                        console.log(res);
                            $("#str").val(res);
                            $("#twitterForm").submit();
                        <%--self.location= '${ctx}/mgr/twitterCallback?str='+res;--%>
                    }, log );
                }
                login_twitter("twitter");


            }else{
                $.get('${ctx}/mgr/user/jumpOauth',{"type":type}, function (data) {
                    if (data.code == 0){
                        window.location.href=data.data;
                    }else{
                        layer.msg(data.err);
                    }
                },'json');
            }


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
        


        $("#email").blur(function () {
            isEmail();
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
                layer.msg("Email login address not found");
            }
        });


    });



    function isEmail() {
        var email = $("#email").val();
        if($.trim(email) == ''){
            $("#emailSpan").attr("class","cells-block error ");
            $("#emailSpan").html("please enter email");
            return false;
        }
        if(!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
            $("#emailSpan").attr("class","cells-block error ");
            $("#emailSpan").html("please enter true email address");
            return false;
        }
        $("#emailSpan").attr("class","cells-block error none");
        return true;
    }



    function checkPwd() {
        var password = $("#password").val();
        if ($.trim(password)==''){
            $("#passwordSpan").html("password can't empty");
            $("#passwordSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password)!= password){
            $("#passwordSpan").html("password can't contain blank");
            $("#passwordSpan").attr("class","cells-block error");
            return false;
        }else if($.trim(password).length < 6){
            $("#passwordSpan").html("Please enter the 6~24 digit password");
            $("#passwordSpan").attr("class","cells-block error");

        }else{
            $("#passwordSpan").attr("class","cells-block error none");
            return true;
        }
    }




</script>

</body>
</html>




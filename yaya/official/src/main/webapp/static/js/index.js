
$(function(){
    var type = $("#type").val();
    if(type != null){
        $(".fx-btn-" + type).click();
        msgTips("error",$("#message").val());
    }
})

function loginCheck() {
    var account = $("#account").val();
    var password = $("#password").val();
    if($.trim(account) == ''){
        msgTips("error","用户名不能为空！");
        $("#account").focus();
        return false;
    }
    if($.trim(password) == '' ){
        msgTips("error","密码不能为空！");
        $("#password").focus();
        return false;
    }
    //将最后一个用户信息写入到Cookie
    SetLastUser(account);

    //如果记住密码选项被选中
    if (document.getElementById('checkbox_2').checked == true) {
        //取密码值
        var pwd = document.getElementById('password').value;
        //alert("密码："+pwd);
        var expdate = new Date();
        expdate.setTime(expdate.getTime() + 14 * (24 * 60 * 60 * 1000));
        var usr = $("#account").val();
        //将用户名和密码写入到Cookie
        SetCookie(usr, pwd, expdate);
    } else {
        //如果没有选中记住密码,则立即过期
        ResetCookie();
    }
    $("#loginForm").submit();
}

//写入到Cookie
function SetCookie(name, value, expires) {
    var argv = SetCookie.arguments;
    //本例中length = 3
    var argc = SetCookie.arguments.length;
    var expires = (argc > 2) ? argv[2] : null;
    var path = (argc > 3) ? argv[3] : null;
    var domain = (argc > 4) ? argv[4] : null;
    var secure = (argc > 5) ? argv[5] : false;
    if (name!=null&&name!="") {
        document.cookie = escape(name) + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + ((path == null) ? "" : ("; path=" + path)) + ((domain == null) ? "" : ("; domain=" + domain)) + ((secure == true) ? "; secure" : "");
    }
}

function ResetCookie() {
    var usr = document.getElementById('account').value;
    var expdate = new Date();
    SetCookie(usr, null, expdate);
}

function msgTips(type,content){
    $(".login_tip").show();
    $(".login_tip_"+type).html("<i class='ico ico_"+type+"'></i>"+content);
}

function SetLastUser(usr) {
    //var id = "49BAC005-7D5B-4231-8CEA-16939BEACD67";
    var id = "jxloginname";
    var expdate = new Date();
    //当前时间加上两周的时间
    expdate.setTime(expdate.getTime() + 14 * (24 * 60 * 60 * 1000));
    SetCookie(id, usr, expdate);
}

var InterValObj; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount;//当前剩余秒数

function sendMessage() {
    var mobile = $("#mobile").val();
    if (!isMobile(mobile)){
        $("#mobile").parent().next().removeClass("none");
        $("#mobile").focus();
        return false;
    }else{
        $("#mobile").parent().next().addClass("none");
    }
    curCount = count;
    //设置button效果，开始计时
    $("#btnSendCode").attr("disabled", "true");
    $("#btnSendCode").text(curCount + "秒后再次获取");
    $("#btnSendCode").addClass("getCodeButton-current");
    InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    //向后台发送处理数据
    $.ajax({
        type: "POST", //用POST方式传输
        dataType: "json", //数据格式:JSON
        url: '${ctx}/regist/get_captcha', //目标地址
        data: {'mobile':mobile, 'type':0},
        error: function (XMLHttpRequest, textStatus, errorThrown) { },
        success: function (data){
            if(data.code != 0){
                layer.msg(data.err);
            }else{
                layer.msg("验证码已发送到您的手机");
            }
        }
    });
}

//timer处理函数
function SetRemainTime() {
    if (curCount == 0) {
        window.clearInterval(InterValObj);//停止计时器
        $("#btnSendCode").removeClass("getCodeButton-current");
        $("#btnSendCode").removeAttr("disabled");//启用按钮
        $("#btnSendCode").val("重新发送");
    }
    else {
        curCount--;
        $("#btnSendCode").val(curCount + "秒后再次获取");
    }
}
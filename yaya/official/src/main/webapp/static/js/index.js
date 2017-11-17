
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
    if(!isMobile(account) && !isEmail(account)){
        msgTips("error","请输入正确的手机号或者密码！");
        return false;
    }
    $(".login_tip").hide();
    //将最后一个用户信息写入到Cookie
    SetLastUser(account);

    //如果记住密码选项被选中
    if (document.getElementById('rememberMe').checked == true) {
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
    $.post($("#loginForm").attr("action"), $("#loginForm").serialize(),function (data) {
        if(data.code == 0){
            alert("success");
        }else{
            msgTips("error",data.err);
        }
    },'json');
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
    $(".login_tip_"+type).html("<i  class='ico ico_"+type+"'></i>"+ "<span style='color:red;'>" + content + "</span>");
}

function SetLastUser(usr) {
    //var id = "49BAC005-7D5B-4231-8CEA-16939BEACD67";
    var id = "jxloginname";
    var expdate = new Date();
    //当前时间加上两周的时间
    expdate.setTime(expdate.getTime() + 14 * (24 * 60 * 60 * 1000));
    SetCookie(id, usr, expdate);
}

//------------------------验证码----------------------

var InterValObj; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount;//当前剩余秒数

function sendMessage() {
    var account = $("#registaccount").val();
    if (!isMobile(account) && !isEmail(account)){
        styleChange("registaccount",1,"请填写正确的手机或邮箱")
        return false;
    }else{
        styleChange("registaccount",1,"")
    }
    var type = 1;  //手机
    if(isEmail(account)) type = 2;  //邮箱
    //如果省份为空加载省份列表
    setProvince();
    curCount = count;
    //设置button效果，开始计时
    $("#btnSendCode").attr("disabled", "true");
    $("#btnSendCode").text(curCount + "秒后再次获取");
    $("#btnSendCode").addClass("getCodeButton-current");
    InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    //向后台发送处理数据
     $.ajax({
         url: ctx + "/regist/get_captcha", //目标地址
         type: "POST", //用POST方式传输
         dataType: "json", //数据格式:JSON
         data: {'account':account,'type':type},
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


//-----------------校验注册表单-----------------------

function checkForm(){
    var mobile = $("#mobile").val();
    if (!isMobile(mobile)){
        styleChange("mobile",1,"请填写正确的手机号码");
        return false;
    }else{
        styleChange("mobile",2,"");
    }
    var captcha = $("#captcha").val();
    if (!/^\d{6}/g.test(captcha)){
        styleChange("captcha",1,"验证码格式不对");
        return false;
    }else{
        styleChange("captcha",2,"");
    }
    var password = $("#registPassword").val();
    if($.trim(password) == '' ){
        styleChange("registPassword",1,"密码不能为空");
        return false;
    }
    if (!(password.length>=6 && password.length <=24)){
        styleChange("registPassword",1,"密码格式长度为6-24位");
        return false;
    }else{
        styleChange("registPassword",2,"");
    }
    var agentPass = $("#commitPassword").val();
    if(agentPass != password){
        styleChange("commitPassword",1,"两次密码输入不一致");
        return false;
    }else{
        styleChange("commitPassword",2,"");
    }
    if(!$('#checkbox_3').is(':checked')){
        $('#checkbox_3').next().next().removeClass("hide").html("请同意注册条款");
        return false;
    }else{
        $('#checkbox_3').next().next().addClass("hide").html("");
    }
    return true;
}

function styleChange(id,type,msg){
    type == 1? $("#" + id).focus().next().addClass("errorIcon").next().removeClass("hide").html(msg) : $("#" + id).next().removeClass("errorIcon").next().addClass("hide")
}

//注册按钮
$("#registBtn").click(function(){
    if(checkForm()){
        $.post($("#registForm").attr("action"), $("#registForm").serialize(),function (data) {
            if(data.code == 0){
                layer.msg("success");
                window.location.href = ctx + "/user/userInfo";
            }else{
                styleChange(data.data.id,1,data.data.msg);
            }
        },'json');
    }
});

//-------------选择控件加载数据------------
//省份
function setProvince(){
    var province = $("#province").val();
    if($.trim(province) == ""){
        $.ajax({
            url:ctx + "/regist/getProvince",
            dataType:"json",
            success:function (data) {
                $("#province").empty();
                var list = data.data.dataList;
                for(var index in list){
                    $("#province").append('<option value="'+list[index].name+'">'+list[index].name+'</option>');
                }
            }
        });
    }
}
//根据省份城市更改选项
function changeOption(name){
    $.ajax({
        url:ctx + "/regist/searchOption",
        data:{"name":name},
        dataType:"json",
        type:"post",
        success:function (data) {
            $("#city").empty();
            for(var index in data.data){
                $("#city").append('<option value="'+data.data[index].name+'">'+data.data[index].name+'</option>');
            }
        }
    });
}


//-----------------轮播广告-----------------


/*<!--轮播广告-->*/
$(window).bind("load resize",function(){
    $("#carousel-05").carouFredSel({
        width       : '100%',
        items		: { visible	: 1 },
        auto 	  	: { pauseOnHover: true, timeoutDuration:5000 },
        swipe    	: { onTouch:true, onMouse:true },
        pagination 	: "#carousel-page-05",
//                                    prev 		: { button:"#carousel-prev-01"},
//                                    next 		: { button:"#carousel-next-01"},
        scroll 		: {	fx : "crossfade" }
    });
});
/*固定栏*/
jQuery(function($) {
    $(document).ready( function() {
        $('.fixed-nav').stickUp({
            marginTop: 'auto'
        });
    });
});

$(function(){
    $.get('/news/ajaxTrends',{'pageNum':1,'pageSize':6}, function (data) {
        if(data.code == 0){
            for(var index in data.data.dataList){
                $("#trendsUL").append('<li title="'+data.data.dataList[index].title+'" class="shenglue"><a href="/news/viewtrend/'+data.data.dataList[index].id+'">'+data.data.dataList[index].title+'</a></li>');
            }
        }
    },'json');

    $.get('/news/pagenews',{'pageNum':pageNum,'pageSize':11},function (data) {
        if(data.code == 0){
            showNews(data);
        }
    },'json');

    $(".v2-more-button").click(function(){
        $.get('/news/pagenews',{'pageNum':pageNum+1,'pageSize':12},function (data) {
            if(data.code == 0){
                showNews(data);
                autoPageable = true;
                pageNum++;
            }
        },'json');
    });
});

var pageNum = 1;
var autoPageable = true;
var autoPageTimes = 0;
$(function(){
    $(window).scroll(function () {
        if(autoPageable){
            if(($(window).scrollTop()) >= ($(document).height() - $(window).height())){
                // alert(pag    eNum+1);
                $.get('/news/pagenews',{'pageNum':pageNum+1,'pageSize':12},function (data) {
                    if(data.code == 0){
                        autoPageable = false;
                        showNews(data);
                        if(autoPageTimes >=2 ){
                            autoPageable = false;
                            autoPageTimes = 0;
                        }else{
                            autoPageable = true;
                            autoPageTimes++;
                        }
                        pageNum++;
                    }
                },'json');
            }
        }
    });
});

function showNews(data){
    var parentContainer;
    if(data.data.dataList.length > 0){
        for(var index in data.data.dataList){
            var keyword =data.data.dataList[index].keywords.split(/[,|，| ]/)[0];
            var addhtml = '<div class="col-lg-3">'
                +'<div class="v2-entry-item">'
                +'<div class="v2-entry-img">'
                +'<a href="/news/view/'+data.data.dataList[index].id+'" target="_blank"><img src="'+data.data.dataList[index].articleImg+'" alt=""></a>'
                +'<i class="v2-entry-classIcon"><a href="/news/view/'+data.data.dataList[index].id+'">'+keyword+'</a></i>'
                +'</div>'
                +'<div class="v2-entry-title">'
                +'<h5><a href="/news/view/'+data.data.dataList[index].id+'" target="_blank">'+data.data.dataList[index].title+'</a></h5>'
                +'<span class="time">'+formatSimple(data.data.dataList[index].createTime)+'</span>'
                +'</div>'
                +'</div>'
                +'</div>';
            if(index < 3 && data.data.pageNum == 1){
                $("#firstRow").prepend(addhtml);
            }else{
                if((index-3)%4==0&&data.data.pageNum == 1 || index%4==0&&data.data.pageNum != 1){
                    parentContainer = $('<div class="row"></div>');
                }
                parentContainer.append(addhtml);
                $(".v2-entry-area").append(parentContainer);
            }
        }
    }
}



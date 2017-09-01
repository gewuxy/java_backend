<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/7/11
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>注册</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/regist/css/css.css">
    <link href="${ctxStatic}/regist/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctxStatic}/js/jquery-1.4.1.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/js/jsGB2312ForSort.js"></script>
    <script type="text/javascript" src="${ctxStatic}/js/abbreviation_City.js"></script>
    <script src="http://static.geetest.com/static/tools/gt.js"></script>
    <script type="text/javascript" src="${ctxStatic}/js/distpicker.data.js"></script>

    <style type="text/css" >
        .gt_holder.gt_popup { z-index: 4;}
    </style>
    <script type="text/javascript">
        $(document).ready(function(){
            //这个是他之前计算高宽的
            scaleW=window.innerWidth/320;
            scaleH=window.innerHeight/480;
            var resizes = document.querySelectorAll('.resize');
            for (var j=0; j<resizes.length; j++) {
                resizes[j].style.width=parseInt(resizes[j].style.width)*scaleW+'px';
                resizes[j].style.height=parseInt(resizes[j].style.height)*scaleH+'px';
                resizes[j].style.top=parseInt(resizes[j].style.top)*scaleH+'px';
                resizes[j].style.left=parseInt(resizes[j].style.left)*scaleW+'px';
            }
            var ua = navigator.userAgent.toLowerCase();
            if (ua.indexOf("mac")!=-1) { //IE7.0要重新计算margin-top
                $(".icon_down").css("display", "block");
                $(".row select").css("background", "none");
            }
            getKsList();
        });

    </script>
    <script type="text/javascript">
        //根据 省市 获取医院
        function getHospitalByCity() {
            var p = jQuery(":input[id$='province1']").val();
            var c = jQuery(":input[id$='city1']").val();
            $.ajax({
                type : 'post',
                url : '${ctx}/regist/hospital?province='+encodeURI(encodeURI(p))+'&city='+encodeURI(encodeURI(c)),
                dataType : 'json',	//xml, html, script, json, jsonp, text
                async : false,	//同步加载数据。发送请求时锁住浏览器。需要锁定用户交互操作时使用同步方式
                //processData: false,	//设置 processData 选项为 false，防止自动转换数据格式，可以避免把自动把数据转换为字符串
                success : function(data, textStatus, xhr) {
                    if (textStatus == "success") {
                        $("#hosName").html("");
                        for(var i = 0;i<data.data.length;i++){
                            $("#hosName").append('<option value="'+(data.data)[i].name+'" '+'>'+(data.data)[i].name+'</option>');
                        }

                    }
                }
                , error : function(xhr, textStatus, errorThrown) {
                    layer.msg(errorThrown);
                }
                ,complete : function(xhr, textStatus) {
                    //alert(typeof(xhr.responseText) + "--" + textStatus);
                }
            });
        }


        //获取科室
        function getKsList(){
            $.ajax({
                url:'${ctx}/regist/department',
                type:'post',
                dataType:'json',
                contentType: "application/json; charset=utf-8",
                success:function(data){
                    $("#ksName").html("");
                    for(var i = 0;i<data.data.length;i++){
                        $("#ksName").append('<option value="'+(data.data)[i].name+'" '+'>'+(data.data)[i].name+'</option>');
                    }
                },
                error:function(data){
                    layer.msg("Error");
                }
            });
        }
    </script>

    <!-- 验证判断 -->
    <script type="text/javascript">
        // 检查用户名是否有效
        function checkUname(){
            var uname = document.getElementById("username").value;
            //var usernamets = document.getElementById("usernamets").innerHTML;
            // alert('test uname: '+uname+'usernamets: '+usernamets);
            var myreg = /^([a-zA-Z0-9]+[-_|\-_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[-_|\-_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if(uname.length==0){
                document.getElementById("usernamets").innerHTML="请填写用户账号";
                return false;
            }else if(!myreg.test(uname.replace(/\s+/g, ""))){
                document.getElementById("usernamets").innerHTML = "请使用有效的邮箱";
                return false;
            }else {
                document.getElementById("usernamets").innerHTML ="";
                return true;
            }

        }

        // 验证用户名是否 已被使用
        function checkUserName(){
            //alert('验证用户名是否被使用 '+checkUname());
            var flag = this.checkUname();
            if (flag==true ) {
                var name = jQuery(":input[id$='username']").val();
                name = name.replace(/\s+/g, "");
                $.ajax({
                    //服务器的地址
                    url:"${ctx}/regist/checkUser?username="+name,
                    dataType:'json', //返回数据类型
                    type:'POST', //请求类型
                    contentType: "application/json; charset=utf-8",
                    success: function(data) {
                        //alert('fz tsts: '+data.result);
                        //alert('=====fz====='+ document.getElementById("namests").value);
                            if(data.code == 0){
                                $("#usernamets")[0].innerHTML="";
                                document.getElementById("namests").value = "";
                            }else{
                                $("#usernamets")[0].innerHTML=data.err;
                                document.getElementById("namests").value = data.err;
                            }

                    }

                });
            }
        }


        function checkUnamets(){
            var namets = document.getElementById("namests").value;
            //alert('test checkUnamets: '+namets);
            if(namets!="" && namets=="用户名已被使用"){
                return false;
            }else{
                return true;
            }
        }
        function CheckUtil() {
        }
        CheckUtil.isBlank = function(obj) {
            if ($.trim(obj) == '')
                return true;
            else
                return false;
        };
        //验证 真实姓名不能为拼音
        function checkRealNameFlag(flag){
            var zs_name=document.getElementById("name").value;
            if (jQuery.trim(zs_name).length==0) {
                flag = false;
                flag=CheckUtil.isBlank(zs_name);
                if (flag == false) {
                    $("#xmtts").innerHTML="请输入真实姓名";
                } else {
                    $("#xmts").innerHTML="";
                }
            } else {
                if(!/^[\u4e00-\u9fa5]+$/gi.test(zs_name)){
                    document.getElementById("xmtts").innerHTML="含有非汉字字符";
                    flag = false;
                }else{
                    document.getElementById("xmtts").innerHTML="";
                }
            }
            return flag;
        }

        //检查验证姓名
        function checkName(){
            var flag = true;
            if (flag){
                flag = checkRealNameFlag(flag);
            }
            return flag;
        }
        //检查验证密码
        function checkPwd(){
            var pwd = document.getElementById("password").value;
            if (pwd.length <6){
                document.getElementById("pwts").innerHTML="请使用6~12位密码，支持字母、数字和特殊字符";
                return false;
            } else if  (pwd.length >12) {
                document.getElementById("pwts").innerHTML="密码长度不能超过12位";
                return false;
            } else {
                document.getElementById("pwts").innerHTML="";
                return true;
            }
        }

        //检查验证 确认密码
        function checkRepwd() {
            var pwd = document.getElementById("password").value;
            var repwd = document.getElementById("qrpassword").value;
            if(repwd!=pwd){
                document.getElementById("repts").innerHTML="两次输入的密码不一致";
                return false;
            }else{
                document.getElementById("repts").innerHTML="";
                return true;
            }
        }

        //检查验证职业资格证号
        function checkZyzgz(){
            var reg = new RegExp("^[0-9]*$");//验证至少有15位数字
            var zgz = document.getElementById("zgz").value;
            //alert('zgzh： '+zgz);
            if(zgz.length==0){
                document.getElementById("zgzts").innerHTML = "*";
                return false;
            }else if(!reg.test(zgz)){
                document.getElementById("checkzgzhts").innerHTML = "请输入数字";
                return false;
            }else if(zgz.length < 15 || zgz.length > 27){
                //alert(zgz.length+": 小于或大于15");
                document.getElementById("checkzgzhts").innerHTML = "请输入15~27位数字";
                return false;
            }else {
                document.getElementById("checkzgzhts").innerHTML = "";
                return true;
            }
        }



        //检查验证医院
        function checkYy(){
            var yy = document.getElementById("hospital").value;
            //alert('test checkYy: '+yy);
            if(yy.length==0){
                return false;
            }else if(yy=="手动输入"){
                return false;
            }else{
                return true;
            }
        }

        //检查验证科室
        function checkKs(){
            var ks = document.getElementById("department").value;
            //alert('test checkKs: '+ks);
            if(ks.length==0){
                return false;
            }else if(ks=="手动输入"){
                return false;
            }else{
                return true;
            }
        }

        function checkAll(){
            var flag = true;
            if (flag) {

                checkUserName();
                flag = checkUnamets()&&checkName()&&checkPwd()
                    &&checkRepwd()&&checkYy()&&checkKs()&&checkMobile()&&checkVerifyCode();
            }
            return flag;

        }

        var msg ="";
        function submitForm() {
            // alert('tests ');
            if (checkAll()==true) {
                $.post($("#forms").attr("action"), $("#forms").serialize(),function (data) {
                    if(data.code == 0){
                        window.location.href = '${ctx}/regist/registSuccess?username='+$("#username").val();
                    }else{
                        layer.msg(data.err);
                    }
                },'json');
            }else{
                if(checkUnamets()==false){
                    var namets = document.getElementById("namests").value;
                    layer.msg(namets);
                }else{
                    layer.msg("您的信息未填写完整");
                }
            }
        }


    </script>
    <SCRIPT LANGUAGE="JavaScript">
        function sortRule(a,b) {
            var x = a._text;
            var y = b._text;
            return x.localeCompare(y);
        }
        function op(){
            var _value;
            var _text;
        }
        function sortOption(id){
            var obj = document.getElementById(id);
            var tmp = [];
            for(var i=0;i<obj.options.length;i++){
                var ops = new op();
                ops._value = obj.options[i].value;
                ops._text = obj.options[i].text;
                tmp.push(ops);
            }
            tmp.sort(sortRule);
            for(var j=0;j<tmp.length;j++){
                obj.options[j].value = tmp[j]._value;
                obj.options[j].text = tmp[j]._text;
            }
        }
    </SCRIPT>
    <script>
        var captcha ;
        const getCodeSplitTime = 60;
        var getCodeLeftTime = getCodeSplitTime;
        $(function(){
            if(""){
                alert("");
            }
            $("#mobile").blur(function(){
                checkMobile();
            });

            $("#verifyCode").change(function(){
                checkVerifyCode();
            });


            $("#username").blur(function(){
                checkUserName();
            });

            $("#name").blur(function(){
                checkName();
            });
            $("#password").blur(function(){
                checkPwd();
            });
            $("#qrpassword").blur(function(){
                checkRepwd();
            });

            geettest();
        });



        function getVerifyCode(result){
            $.get('${ctx}/web/verify/gencode',{"mobile":$("#mobile").val(),'geetest_challenge': result.geetest_challenge,
                'geetest_validate': result.geetest_validate,
                'geetest_seccode': result.geetest_seccode},function(data){
                if (data.code == 0){
                    layer.msg("验证码已经发送到您的手机,请注意查收");
                    changeCodeBtnText();
                }else{
                    layer.msg("验证码发送失败, 请确认您的手机号");
                    $("#codeerror").text(data.msg);
                    $("#loading-tip").bind("click", showCaptcha);
                }
            },'json');
        }

        function changeCodeBtnText(){
            if(getCodeLeftTime>0){
                $("#loading-tip").text(getCodeLeftTime+"秒后再次获取");
                console.log("定时器运行中 left time = "+getCodeLeftTime);
                setTimeout("changeCodeBtnText()",1000);
                getCodeLeftTime--;
                $("#loading-tip").unbind("click");
            }else{
                getCodeLeftTime = getCodeSplitTime;
                $("#loading-tip").text("重新获取");
                $("#loading-tip").bind("click", showCaptcha);
            }
        }

        function checkVerifyCode(){
            var code = $("#verifyCode").val();
            var reg = /^[0-9]{6}$/g;
            var codeCanUse = false;
            var mobile = $("#mobile").val();
            if(!/^1[3|5|7|8][0-9]{9}$/g.test(mobile)){
                $("#codeerror").text("请先输入正确的手机号");
                return false;
            }
            if(reg.test(code)){
                codeCanUse = true;
                $("#codeerror").text("");
            }else{
                $("#codeerror").text("请输入正确的验证码，6位数字");
                codeCanUse = false;
            }
            return codeCanUse;
        }

        function checkMobile(){
            var mobile = $("#mobile").val();
            var reg = /^1[3|5|7|8][0-9]{9}$/g;
            var mobileCanUse = false;
            if(reg.test($.trim(mobile))){
                var mobileCanUse = true;
            }else{
                $("#mobileerror").text("手机号格式不正确");
                mobileCanUse = false;
            }
            $.ajax({
                //服务器的地址
                url:"${ctx}/regist/checkMobile?mobile="+mobile,
                dataType:'json', //返回数据类型
                type:'POST', //请求类型
                contentType: "application/json; charset=utf-8",
                async:false,
                success: function(data) {
                    if(data.code != 0){
                        $("#mobileerror").text(data.err);
                        mobileCanUse = false;

                    }else{
                        $("#mobileerror").text("");
                    }

                }

            });

            return mobileCanUse;
        }

        function aa(obj,inid)
        {
            inid.value=obj.value;
            obj.val("");
        }

        function geettest(){
            $.ajax({
                url: "${ctx}/web/verify/geetest?t=" + (new Date()).getTime(), // 加随机数防止缓存
                type: "get",
                dataType: "json",
                async: false,
                success: function (data) {
                    // 使用initGeetest接口
                    // 参数1：配置参数
                    // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它做appendTo之类的事件
                    initGeetest({
                        gt: data.gt,
                        challenge: data.challenge,
                        product: "popup", // 产品形式，包括：float，embed，popup。注意只对PC版验证码有效
                        offline: !data.success // 表示用户后台检测极验服务器是否宕机，一般不需要关注
                        // 更多配置参数请参见：http://www.geetest.com/install/sections/idx-client-sdk.html#config
                    }, function (captchaObj) {
                        captcha = captchaObj;
                        // 省略其他方法的调用
                        $("#loading-tip").click(function(){
                            if(checkMobile()){
                                showCaptcha();
                            }
                        });
                        captchaObj.appendTo('#loading-tip');
                        //正确之后后去验证码
                        captchaObj.onSuccess(function () {
                            var result = captchaObj.getValidate();
                            getVerifyCode(result);
                        });
                    });
                }
            });
        }



        function showCaptcha(){
            captcha.show();
        }
    </script>


</head>

<body>

<div class="c-top">
    <div class="wenzi"><img src="${ctxStatic}/regist/images/welc_1.png" style=" height:90px" /></div>
</div>
<div class="gzhmztj">
    由<span style="color:#0099FF">
    <c:forEach items="${nameList}" var="name" varStatus="status">
        <c:choose>
            <c:when test="${status.last}">
                ${name}
            </c:when>
            <c:otherwise>
                ${name},
            </c:otherwise>
        </c:choose>

    </c:forEach>
    </span>免费为您提供价值69.9元激活码。
</div>
<div class="r-nav">

    <form id="forms" action="${ctx}/regist/doRegist" method="post">
        <input type="hidden" id="namests" value="">
        <input type="hidden" name="gzhUid" value="${param['gzhUid'] }"/>
        <input type="hidden" name="tag" value="${param['tag'] }"/>
        <c:forEach items="${masterId}" var="id">
            <input type="hidden" name="masterId" value="${id}"/>
        </c:forEach>

        <div class="row">
            <p ><input type="text" name="username" id="username" placeholder="账号（有效电子邮箱）"><i class="icon-required"></i><div class="error-tips" id="usernamets"></div></p>
        </div>
        <div class="row">
            <p><input type="text" name="linkman" id="name" maxlength="6" placeholder="姓名（真实姓名）"><i class="icon-required"></i><div class="error-tips" id="xmtts"></div></p>
        </div>
        <div class="row">
            <p><input type="password" name="password" id="password" maxlength="20" placeholder="密码（6位-12位的密码）"><i class="icon-required"></i><div class="error-tips" id="pwts"></div></p>
        </div>
        <div class="row">
            <p><input type="password" name="qrpassword" id="qrpassword" maxlength="20" placeholder="确认密码（保持一致）"><i class="icon-required"></i><div class="error-tips" id="repts"></div></p>
        </div>
        <div class="row">
            <p><input type="text" name="mobile" id="mobile" placeholder="手机号" maxlength="11"><i class="icon-required"></i><div class="error-tips" id="mobileerror"></div></p>
        </div>
        <div class="row ">
            <div class="captcha"><i class="icon-required"></i><input type="text" name="verifyCode" maxlength="6" id="verifyCode"  class="geetest_captcha" placeholder="请输入正确验证码"><span class="btn-captcha" id="loading-tip" >获取验证码
      </span></div>
            <div class="error-tips" id="codeerror"></div>
        </div>

        <div class="row choose" >
            <i class="icon-required"></i>
            <img class="icon_down" src="${ctxStatic}/regist/images/down.png" width="15" style="display:none;">
            <select id="province1" class="form-control" name="province" >
            </select>
        </div>
        <div class="row choose">
            <i class="icon-required"></i>
            <img class="icon_down" src="${ctxStatic}/regist/images/down.png" width="15"  style="display:none;" >
            <select id="city1" class="form-control" name="city" ></select>
        </div>
        <div class="row choose">
            <i class="icon-required"></i>
            <img class="icon_down" src="${ctxStatic}/regist/images/down.png" width="15"  style="display:none;" >
            <select id="district1" class="form-control" name="zone" ></select>
        </div>


        <div class="row choose">
            <i class="icon-required"></i>
            <img class="icon_down" src="${ctxStatic}/regist/images/down.png" width="15"  style="display:none;" >
            <p><input type="text" name="hospital" id="hospital" onblur="checkYy()" class="suibian" placeholder="请输入或者选择医院" ></p>
            <select name="hosName" id="hosName" onchange="aa(this, hospital)" onblur="checkYy()" ></select>

            <div class="error-tips"></div>
        </div>
        <div class="row choose">
            <i class="icon-required"></i>
            <img class="icon_down" src="${ctxStatic}/regist/images/down.png" width="15"  style="display:none;">
            <p><input type="text" name="department" id="department"  class="suibian" onFocus="if(this.value=='')this.value='';" value="" onblur="CheckKs()" placeholder="输入或者选择科室" ></p>
            <select name="ksName" id="ksName" onchange="aa(this, department)" onblur="checkKs()"></select>
            <c:forEach items="${ksList}" var="depart">
                <option value="${depart.name}" ${meet.meetType eq depart.name || course.category eq depart.name ? 'selected':''}>${depart.name}[${depart.category}]</option>
            </c:forEach>
            <div class="error-tips"></div>
        </div>
        <div class="row">
            <p><input type="text" name="cmeId" id="zgz"  placeholder="执业资格证号（选填）"></p>
        </div>
        <div class="row t-center">
            <a onclick="submitForm()" class="button-green"><span>立即注册</span></a>
        </div>
    </form>
</div>

<div class="bottom">
    <a href="http://www.medyaya.cn" target="_blank">www.medyaya.cn</a>
</div>

<script type="text/javascript">
    $(function(){
        var allPro = ChineseDistricts[86];
        var allCitis;
        var allDist;
        var pcode;
        var ccode;
        // 通过调用新浪IP地址库接口查询用户当前所 省份、城市
        $.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js',function(){
            var province = remote_ip_info.province;
            var city = remote_ip_info.city;
            for(var pk in allPro){
                if(allPro[pk].indexOf(province)>-1){
                    pcode = pk;
                    break;
                }
            }
            if(pcode){
                allCitis = ChineseDistricts[pcode];
                for(var ck in allCitis){
                    if(allCitis[ck].indexOf(city)>-1){
                        ccode = ck;
                        //alert(allCitis[ccode]);
                        break;
                    }
                }
            }
            if(ccode){
                allDist = ChineseDistricts[ccode];
            }
            changeDist(pcode, ccode);
            changeZone();
            getHospitalByCity();
        });

        function changeDist(pcode, ccode){
            $("#province1").html("");
            var pselected = '';
            for(var pk in allPro){
                pselected = pcode == pk?'selected':'';
                $("#province1").append('<option value="'+allPro[pk]+'" '+pselected+'>'+allPro[pk]+'</option>');
            }

            allCitis = ChineseDistricts[pcode];
            if(!ccode){
                for(var ck in allCitis){
                    ccode = ck;
                    break;
                }
            }
            $("#city1").html("");
            var cselected = '';
            for(var ck in allCitis){
                cselected = ccode == ck?'selected':'';
                $("#city1").append('<option value="'+allCitis[ck]+'" '+cselected+'>'+allCitis[ck]+'</option>');
            }

            allDist = ChineseDistricts[ccode];
            $("#district1").html("");
            for(var ck in allDist){
                $("#district1").append('<option value="'+allDist[ck]+'">'+allDist[ck]+'</option>');
            }
        }


    });
    $("#province1").change(function(){
        changeCity();
        changeZone();
        getHospitalByCity();

    });

    $("#city1").change(function(){
        changeZone();
        getHospitalByCity();

    });

    function changeCity(){
            var province = $("#province1").val();
            if(province == "" || province == null){
                layer.msg("请选择省份");
            }else{
                $.ajax({
                    url:'${ctx}/regist/cities?province='+encodeURI(encodeURI(province)),
                    type:'post',
                    dataType:'json',
                    async : false,
                    success:function(data){
                        if (data.code == 0){
                            $("#city1").html("");
                            for(var i = 0;i<data.data.length;i++){
                                $("#city1").append('<option value="'+(data.data)[i].name+'" '+'>'+(data.data)[i].name+'</option>');
                            }
                        }else{
                            layer.msg("获取城市信息失败");
                        }
                    },
                    error:function(data){
                        layer.msg("Error");
                    }
                });

            }
        }

    function changeZone(){
        var city = $("#city1").val();
        if(city == "" || city == null){
            layer.msg("请选择城市");
        }else{
            $.ajax({
                url:'${ctx}/regist/zones?city='+encodeURI(encodeURI(city)),
                type:'post',
                dataType:'json',
                async : false,
                success:function(data){
                    if (data.code == 0){
                        $("#district1").html("");
                        for(var i = 0;i<data.data.length;i++){
                            $("#district1").append('<option value="'+(data.data)[i].name+'" '+'>'+(data.data)[i].name+'</option>');
                        }
                    }else{
                        layer.msg("获取信息失败");
                    }
                },
                error:function(data){
                    layer.msg("Error");
                }
            });

        }
    }


</script>

</body>
</html>

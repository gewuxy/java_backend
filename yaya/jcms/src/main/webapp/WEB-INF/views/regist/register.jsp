<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/7/26
  Time: 9:27
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>注册YaYa医师</title>
    <%@include file="/WEB-INF/include/page_context_weixin.jsp"%>
    <script src="${ctxStatic}/js/layer/layer.js"></script>
    <script src="${ctxStatic}/js/util.js"></script>
</head>
<body class="blue-reg">

<div class="warp">
    <div class="header">
        <div class="staus-bar"><strong>填写以下信息进行注册</strong></div>
    </div>

    <div class="item">
        <div class="item-area login-input">
            <form action="${ctx}/regist/do_register" method="post" id="bindForm" name="bindForm">
                <div class="formrow radius login-input-item">
                    <label for="" class="cells-block pr"><input class="icon-register-hot" type="tel" name="mobile" id="mobile" value="${appUser.mobile}" maxlength="11" placeholder="请输入手机号码"></label>
                    <div class="cells-error none">请输入正确的手机号码</div>
                    <label for="" class="cells-block pr"><input type="text" placeholder="输入6位验证码" maxlength="6" value="${captcha}" name="captcha" id="captcha" class="icon-register-hot"><input type="button" id="btnSendCode" class="qrcode radius " value="&nbsp;发送验证码&nbsp;"  onclick="sendMessage()"></label>
                    <div class="cells-error none">请输入正确的验证码</div>
                    <label for="password" class="cells-block pr">
                        <input type="password" id="password" placeholder="输入6~24位密码" value="${appUser.password}" name="password" class="icon-register-hot last" maxlength="24">
                        <a href="javascript:;" class="icon-pwdChange pwdChange-on pwdChange-hook " id="changePwdType"></a>
                    </label>
                    <div class="cells-error none">请输入6-24位密码</div>
                    <label for="" class="cells-block"><input class="icon-register-hot" name="linkman" value="${appUser.linkman}" id="linkman" type="text" placeholder="输入真实姓名" ></label>
                    <div class="cells-error none">请输入正确的姓名</div>
                    <input type="hidden" name="province" id="province" value="${appUser.province}">
                    <input type="hidden" name="city" id="city" value="${appUser.city}">
                    <input type="hidden" name="zone" id="zone" value="${appUser.zone}">
                    <label for="" class="cells-block pr"><a href="javascript:;" class="select-box-item overflowText icon-register-hot" type="text" class="address-hook" id="addressSelect" placeholder=""><span class="select-content" id="addressSpan">请选择省市区</span><span class="select-box-icon icon-sekect-arrows"></span></a></label>
                    <div class="cells-error none">请选择省市区</div>
                    <label for="" class="cells-block pr">
                        <!--回调用户选中项赋值到Input中-->
                        <input id="hospital" name="hospital" value="${appUser.hospital}" hidden="hidden">
                        <input id="hosLevel" name="hosLevel" value="${appUser.hosLevel}" hidden="hidden">
                        <!--回调用户选中项赋值到Input中-->
                        <input id="handle_status" value="" hidden="hidden">

                        <a href="javascript:;" class="select-box-item icon-register-hot overflowText" type="text" class="hospital-hook" id="hospitalSelect" placeholder="">
                            <span class="select-content">选择医院</span>
                            <span class="icon-hospitalLevel"></span>
                            <span class="select-box-icon icon-sekect-arrows"></span>
                        </a>
                    </label>
                    <div class="cells-error none">请选择医院</div>
                    <label for="" class="cells-block pr"><a href="javascript:;" class="select-box-item overflowText icon-register-hot" type="text" class="subject-hook" id="subjectSelect" placeholder=""><span class="select-content" id="subjectContent"></span><span class="select-box-icon icon-sekect-arrows"></span></a></label>
                    <input type="hidden" id="category" name="category" value="${appUser.category}">
                    <input type="hidden" id="name" name="name" value="${appUser.name}">
                    <div class="cells-error none">请选择专科信息</div>
                    <label for="" class="cells-block pr"><a href="javascript:;" class="select-box-item overflowText icon-register-hot" type="text" class="medical-hook" id="medicalSelect" placeholder=""><span class="select-content" id="titleContent"></span><span class="select-box-icon icon-sekect-arrows"></span></a></label>
                    <input type="hidden" name="title" id="title" value="${appUser.title}">
                    <div class="cells-error none">请选择职称</div>
                    <label for="" class="cells-block pr"><input type="text" value="${appUser.department}" placeholder="输入科室" name="department"></label>
                    <c:if test="${empty nameList}">
                        <label for="" class="cells-block pr"><input type="text" class="icon-register-hot" name="invite" value="${invite}" id="invite" placeholder="激活码"><a
                                href="javascript:;" class="activationCode color-blue">获取激活码</a></label>
                        <div class="cells-error none">请输入激活码</div>
                    </c:if>
                    <c:if test="${not empty nameList}">
                        <c:forEach items="${masterId}" var="id">
                            <input id="masterId" name="masterId" value="${id}" hidden="hidden">
                        </c:forEach>
                        <p class="cells-block" style="margin-top:.4rem;">该帐号价值 69.9元，由 <span class="color-blue">
                            <c:forEach items="${nameList}" var="name" varStatus="status">
                            <c:choose>
                                <c:when test="${status.last}">
                                    ${name}
                                </c:when>
                                <c:otherwise>
                                    ${name},
                                </c:otherwise>
                            </c:choose>

                        </c:forEach></span>为您免费提供。</p>
                    </c:if>
                </div>

                <div class="formrow t-center">
                    <input type="button" class="button radius blue-button" value="注册绑定" id="bindBtn"/>
                </div>
            </form>
        </div>
    </div>

    <!--弹出医院地址-->
    <div class="layer-hospital-popup" style="display: none;">
        <div class="login-input ">
                <div class="formrow login-input-item">
                    <div class="search-bar">
                        <div class="search-bar-box">
                                <label for="" class="search-bar-label">
                                    <input type="search" name="keyword" id="keyword" class="serach-bar-input search-hook" placeholder="搜索医院">
                                    <input type="button" class="search-bar-button" value="" id="searchBtn">
                                    <a href="javascript:;" class="search-clear-button" ></a>
                                </label>
                        </div>
                    </div>
                    <div class="cells-title">推荐医院</div>
                    <div class="search-hospital-list search-hospital-hook" id="hotHospital">

                    </div>
                    <div class="cells-title">附近医院</div>
                    <div class="search-hospital-list search-hospital-hook" id="nearByHospital">

                    </div>

                    <div class="cells-title" style="height:1rem; left:0; bottom: 0px; position: fixed; width:100%; text-align: center;" id="moreHospital">加载更多</div>

                </div>
        </div>
    </div>


</div>
<script src="//api.map.baidu.com/api?v=1.2" type="text/javascript"></script>
<script type="text/javascript">

    var longitude = 0;
    var latitude = 0;
    var province;
    var city;
    var zone;
    $(function(){
        function getLocation(){
            var options={
                enableHighAccuracy:true,
                maximumAge:1000
            }
            if(navigator.geolocation){
                //浏览器支持geolocation
                navigator.geolocation.getCurrentPosition(onSuccess,onError,options);
            }else{
                //浏览器不支持geolocation
                alert('您的浏览器不支持地理位置定位');
            }
        }

        getLocation();
        //成功时
        function onSuccess(position){
            //返回用户位置
            //经度
            longitude =position.coords.longitude;
            //纬度
            latitude = position.coords.latitude;

            //根据经纬度获取地理位置，不太准确，获取城市区域还是可以的
            var map = new BMap.Map("allmap");
            var point = new BMap.Point(longitude,latitude);
            var gc = new BMap.Geocoder();
            gc.getLocation(point, function(rs){
                var addComp = rs.addressComponents;
                province = addComp.province;
                city = addComp.city;
                zone = addComp.district;

                reSetAddress(province, city, zone);
            });
        }
        //失败时
        function onError(error){	       
            switch(error.code){
                case 1:
                    alert("位置服务被拒绝,请手动选择位置");
                    break;
                case 2:
                    alert("暂时获取不到位置信息");
                    break;
                case 3:
                    alert("获取信息超时");
                    break;
                case 4:
                    alert("未知错误");
                    break;
            }
        }
    })
    //    alert(window.devicePixelRatio);

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
</script>
<script>
    var success, currentLevel, currentName;

    function checkForm(){
        var mobile = $("#mobile").val();
        if (!isMobile(mobile)){
            $("#mobile").parent().next().removeClass("none");
            $("#mobile").focus();
            return false;
        }else{
            $("#mobile").parent().next().addClass("none");
        }
        var captcha = $("#captcha").val();
        if (!/^\d{6}/g.test(captcha)){
            $("#captcha").parent().next().removeClass("none");
            $("#captcha").focus();
            return false;
        }else{
            $("#captcha").parent().next().addClass("none");
        }

        var password = $("#password").val();
        if (!(password.length>=6 && password.length <=24)){
            $("#password").parent().next().removeClass("none");
            $("#password").focus();
            return false;
        }else{
            $("#password").parent().next().addClass("none");
        }

        var linkman = $("#linkman").val();
        if(!isChineseName(linkman)){
            $("#linkman").parent().next().removeClass("none");
            $("#linkman").focus();
            return false;
        }else{
            $("#linkman").parent().next().addClass("none");
        }

        var hospital = $("#hospital").val();
        if(hospital == ''){
            $("#hospital").parent().next().removeClass("none");
            $("#hospital").focus();
            return false;
        }else{
            $("#hospital").parent().next().addClass("none");
        }

        var invite = $("#invite").val();
        if(hospital == ''){
            $("#invite").parent().next().removeClass("none");
            $("#invite").focus();
            return false;
        }else{
            $("#invite").parent().next().addClass("none");
        }
        return true;
    }
    var selectedProvinceId  = '';
    var selectedCityId = '';
    var refreshProvinceData = false;
    var provincePickerData = [];

    var selectedProvince = province;
    var selectedCity = city;
    var titleData = [];
    var specialtyArray = [];
    var hos_level = ['三级','二级','一级','社区卫生服务中心（站）','卫生院','诊所','其他'];
    var pageNo = 0;

    function reSetAddress(p, c, z){
        $("#province").val(p);
        $("#city").val(c);
        $("#zone").val(z);
        $("#addressSpan").text(p+"  "+c+"  "+z);
    }

    function reSetHospitalInfo(c, l, n){
        $("#handle_status").val(c);
        $("#handle_status").attr('hospital-level',l);
        $("#handle_status").attr('currentName',n);
        $("#hospital").val(n);
        $("#hosLevel").val(hos_level[l-1]);
    }


    function showHospitalLevel(f){
        var _currentHospitalName =f;
        console.log(_currentHospitalName)
        layer.open({
            type: 1,
            title:'请选择医院级别',
            skin: 'layui-layer-popup layui-layer-popup1', //样式类名
            area: ['80%'],
            fixed: true,
//            offset:'t',
            anim: 2,
            scrollbar:true,
            shadeClose: true,
            zIndex:19891020,
//          shadeClose: true, //开启遮罩关闭
            content: '<div class="hospital-level-list"> <ul> <li><a href="javascript:;" hospital-level="1">三级</a></li> <li><a href="javascript:;" hospital-level="2">二级</a></li> <li><a href="javascript:;" hospital-level="3">一级</a></li> <li><a href="javascript:;" hospital-level="4">社区卫生服务中心（站）</a></li> <li><a href="javascript:;" hospital-level="5">卫生院</a></li> <li><a href="javascript:;" hospital-level="6">诊所</a></li> <li><a href="javascript:;" hospital-level="7">其他</a></li> </ul> </div>',
            success:function(layero,index){
//                    alert(layero[0].offsetTop);
                var current = layero.find(".hospital-level-list a");
                current.on('click', function(){

                    //这里可以赋值到一个参数里，返回到
                    console.log($(this).attr('hospital-level'));
                    console.log($(this).text());
                    currentLevel = $(this).attr('hospital-level');
                    success = '1';
                    layer.close(index);
                });
                return success;
            },
            end:function(){
                //选中选项，1为成功，2为失败，返回值到父级
                //    success = '{sh:$success}';
                if ( success == '1' ) {
                    reSetHospitalInfo(1, currentLevel, _currentHospitalName);

                    layer.closeAll()
                } else if( success == '2' ) {
                    parent.$("#handle_status").val('2');
                    layer.closeAll()
                }

            }
        });
    }

    $(function(){
        if ("${err}"){
            layer.msg("${err}");
        }

        $("#bindBtn").click(function(){
            if (checkForm()){
                $.post($("#bindForm").attr("action"), $("#bindForm").serialize(),function (data) {
                    if(data.code == 0){
                        window.location.href = '${ctx}/regist/registSuccess?mobile='+$("#mobile").val();
                    }else{
                        layer.msg(data.err);
                    }
                },'json');
            }
        });

        loadTitleData();
        reSetTitle(titleData[0].label, titleData[0].children[0].label);

        loadSpecialtyData();
        reSetSubjectData(specialtyArray[0].label, specialtyArray[0].children[0].label);


        function loadNearby(keyword){
            $("#moreHospital").unbind("click", nextPage);
            $("#hotHospital").html("");
            if(pageNo == 0){
                $("#nearByHospital").html("");
            }
            var data = {};
            data.query = keyword;
            data.pageNo = pageNo;
            data.x = latitude;
            data.y = longitude;
            $.ajax({
                url:'${ctx}/regist/nearby/hospital',
                data:data,
                async:false,
                type:'post',
                dataType:'json',
                success:function(data){
                    var nearBy = data.data.results;
                    //设置推荐医院

                    for (var index in nearBy){
                        var hospitalDiv = '<div class="search-hospital-list-box '+(index == 0?"last":"")+'" onclick="showHospitalLevel(\''+nearBy[index].name+'\')">'
                            +'<div class="title"><strong>'+nearBy[index].name+'</strong></div>'
                            +'<div class="distance "><span class="icon-address"></span><span>'+nearBy[index].detail_info.distance+'</span>&nbsp;&nbsp;m</div>'
                            +'<div class="address">'+nearBy[index].address+'</div>'
                            +'</div>';
                        if(index == 0){
                            $("#hotHospital").append(hospitalDiv);
                        }else{
                            $("#nearByHospital").append(hospitalDiv);
                        }
                    }
                    if(nearBy.length >= 10){
                        $("#moreHospital").text("点击加载更多");
                        $("#moreHospital").bind("click", nextPage);
                    }else{
                        $("#moreHospital").text("没有更多了");
                        $("#moreHospital").unbind("click");
                    }
                },error:function(a,n,e){
                        alert(a+" - "+e+" - "+n);
                }
            });
        }

        function nextPage(){
            var keyword = $.trim($("#keyword").val());
            pageNo++;
            loadNearby(keyword);
        }

        //医院弹出
        $('#hospitalSelect').on('click', function(){
            pageNo = 0;
            loadNearby('');
            layer.open({
                title:'选择医院',
                type: 1,
                skin: 'layui-layer-popup',
                area: ['100%', '100%'],
                fix: false, //不固定
                maxmin: true,
//                title:false,
                content: $('.layer-hospital-popup'),
                success:function(layero,index){

                },
                end:function(){
                    var _this = $("#hospitalSelect");
                    var handle_status = $("#handle_status").val();
                    var _currentName = $("#handle_status").attr('currentname');
                    var _level = $("#handle_status").attr('hospital-level');
                    if ( handle_status == '1' ) {
                        layer.msg('选择成功！',{
                            time: 800 //2秒关闭（如果不配置，默认是3秒）
                        },function(){
//                            history.go(0);
                        });
                        _this.find('.select-content').text(_currentName);
                        _this.addClass('select-box-onColorChange');
                        _this.find('.icon-hospitalLevel').attr('class','icon-hospitalLevel icon-hospitalLevel-'+_level);
                        console.log($(this));
                        console.log(handle_status);
                    } else if ( handle_status == '2' ) {
                        layer.msg('选择失败！',{
                            time: 800 //2秒关闭（如果不配置，默认是3秒）
                        },function(){
//                            history.go(0);
                        });
                    }
                }
            });
        });


        


        $("#searchBtn").click(function(){
            pageNo = 0;
            var keyword = $.trim($("#keyword").val());
            if(keyword == ''){
                layer.msg("请输入关键字");
            }else{
                loadNearby(keyword);
                layer.confirm("是否将此医院设为您的医院？", {
	                	area: '80%',
                        skin: 'layui-layer-confirm'
                    },
                    function(a){
                        showHospitalLevel(keyword);
                    });

            }
        });

        //调用密码切换
        $('#changePwdType').on('click',function() {
            var inputType = $("#password").attr("type");
            if (inputType == 'password'){
                $("#password").prop("type", "text");
                $(this).removeClass('pwdChange-on').addClass('pwdChange-off');
            }else{
                $("#password").prop("type", "password");
                $(this).removeClass('pwdChange-off').addClass('pwdChange-on');
            }
        });


        function loadRegionData(){
            if (provincePickerData.length == 0){
                $.ajax({
                    url:'${ctx}/regist/regions',
                    async:false,
                    dataType:'json',
                    success:function(data){
                        var regions = data.data;
                        pushRegion(regions);
                    },error:function(a, n, e){
                        alert(e);
                    }
                });
            }
        }

        function pushRegion(regions){
            for(var index in regions){
                provincePickerData.push({label:regions[index].name, value:regions[index].name});
                if(regions[index].details != undefined){
                    provincePickerData[index].children = [];
                    for(var cityIndex in  regions[index].details){
                        provincePickerData[index].children.push({label : regions[index].details[cityIndex].name, value : regions[index].details[cityIndex].name});
                        if(regions[index].details[cityIndex].details != undefined){
                            provincePickerData[index].children[cityIndex].children = [];
                            for(var zoneIndex in regions[index].details[cityIndex].details){
                                provincePickerData[index].children[cityIndex].children.push({label : regions[index].details[cityIndex].details[zoneIndex].name, value : regions[index].details[cityIndex].details[zoneIndex].name})
                            }
                        }
                    }
                }
            }
        }


        function regionChange(){
            weui.picker(provincePickerData, {
                container: 'body',
                depth:3,
                defaultValue: [province,city,zone],
                onChange: function (result) {
                    console.log(result);
                    province = result[0].label;
                    selectedProvinceId = result[0].value;
                    city = result[1] != undefined?result[1].label : '';
                    selectedCityId = result[1] == undefined ? '': result[1].value;
                },
                onConfirm: function (result) {
                    //确认后获取值
                    //省市区
                    var _province = result[0].label;
                    var _city =result[1].label;
                    var _region =result[2] == undefined?'':result[2].label;
                    reSetAddress(_province, _city, _region);
                    console.log(_province + ' ' + _city +' '+ _region);

                    $('#addressSelect').find(".select-content").attr("class",'select-box-onColorChange');
                }
            });
        }

        //地区联动下拉
        $('#addressSelect').on('click',function(){
            refreshProvinceData = false;
            loadRegionData();
            regionChange();
        });


        function loadSpecialtyData(){
            if(specialtyArray.length == 0){
                $.ajax({
                    url:'${ctx}/regist/specialties',
                    async:false,
                    dataType:'json',
                    success:function(data){
                        var specialty = data.data;
                        var sort = 0;
                        for(var index in  specialty){
                            specialtyArray.push({label:index, value:index});
                            if(specialty[index].length > 0){
                                specialtyArray[sort].children = [];
                                for(var subIndex in specialty[index]){
                                    specialtyArray[sort].children.push({label:specialty[index][subIndex], value:specialty[index][subIndex]});
                                }
                            }
                            sort ++;
                        }
                    },error:function(a, n, e){
                        alert(e);
                    }
                });
            }
        }
        //科目联动下拉
        $('#subjectSelect').on('click',function(){
            fillSubjectSelect();
        });

        function reSetSubjectData(r1, r2){
            $("#category").val(r1);
            $("#name").val(r2);
            $("#subjectContent").html(r1 + '\&nbsp;\&nbsp;' + r2) ;

        }

        function fillSubjectSelect(){
            loadSpecialtyData();
            weui.picker(specialtyArray,{
                container: 'body',
                depth:2,
                defaultValue: [specialtyArray[0].label,specialtyArray[0].children[0].label],
                onChange: function (result) {
                    //打开获取值
                    console.log(result);
                },
                onConfirm: function (result) {
                    //确认后获取值
                    console.log(result);
                    //省市区
                    reSetSubjectData(result[0].label, result[1].label);
                    $("#subjectSelect").find(".select-content").attr("class", 'select-box-onColorChange');
                }
            });

        }


        function loadTitleData(){
            if(titleData.length == 0){
                $.ajax({
                    url:'${ctx}/regist/title',
                    async:false,
                    dataType:'json',
                    success:function(data){

                        var titles = data.data;
                        var index = 0;
                        for(var key in titles){
                            titleData.push({label:titles[key].title, value:titles[key].title});
                            titleData[index].children = [];
                            var grades = titles[key].grade;
                            for(var grade in grades){
                                titleData[index].children.push({label:grades[grade], value:grades[grade]});
                            }
                            index ++;
                        }
                    }
                });
            }
        }

        function reSetTitle(v1, v2){
            $("#title").val(v1+" "+v2);
            $("#titleContent").html(v1 + '\&nbsp;\&nbsp;' + v2) ;
        }

        function fillTitle(){
            loadTitleData();
            weui.picker(titleData, {
                container: 'body',
                depth:2,
                defaultValue: [titleData[0].label,titleData[0].children[0].label],
                onChange: function (result) {
                    //打开获取值
                    console.log(result);
                },
                onConfirm: function (result) {
                    //确认后获取值
                    console.log(result);
                    reSetTitle(result[0].label, result[1].label);
                    $("#medicalSelect").find(".select-content").attr("class", 'select-box-onColorChange');
                }
            });
        }
        //职称联动下拉
        $('#medicalSelect').on('click',function(){
            fillTitle();
        });




    })
</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/7/28
  Time: 11:59
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>个人中心 - 修改</title>
    <%@include file="/WEB-INF/include/page_context_weixin.jsp"%>
    <script src="${ctxStatic}/js/layer/layer.js"></script>
</head>
<body class="gary-reg">

<div class="warp ">

    <div class="item">
        <div class="user-details">
            <c:set var="integrity" value="${appUser.integrity}"/>
            <div class="user-progress" <c:if test="${integrity == 100}">style="display: none;" </c:if> ><span class="user-progress-title">您的个人资料完整度&nbsp;&nbsp;<span id="integrity">${integrity}</span>%</span><div class="user-progress-percent" style="width:${integrity}%"></div></div>
            <div class="user-list">
                <ul>
                    <li>
                        <span class="rowTitle user-img-title">头像</span>
                        <span class="rowMain ">
                            <label class="form-label" for="changeIMG">
                                <input type="file" name="file" id="changeIMG" class="none">
                                <span class="user-img">
                                     <img src="${appFileBase}${appUser.headimg}" alt="" id="headImage">
                                </span>
                            </label>

                        </span>
                    </li>
                    <li><span class="rowTitle">姓名</span><span class="rowMain">${appUser.linkman}</span></li>
                    <li class="pr"><span class="rowTitle">单位</span>
                        <span class="rowMain ">
                            <a href="javascript:;" class="overflowText" type="text" class="hospital-hook" id="hospitalSelect" placeholder="">
                                <span class="select-content user-hospital-select">${appUser.hospital}</span>
                                <span class="icon-hospitalLevel icon-hospitalLevel-1"></span>
                                <span class="select-box-icon icon-sekect-arrows"></span>
                            </a>
                        </span>
                    </li>
                    <li class="pr">
                        <span class="rowTitle">科室</span><span class="rowMain">
                            <a href="javascript:;" class="overflowText" type="text" id="officeHook" class="office-hook" >
                                <span class="select-content">${appUser.department}</span>
                                <span class="select-box-icon icon-sekect-arrows"></span>
                            </a>
                        </span>
                    </li>
                    <li class="last pr overflowText "><span class="rowTitle">所在地区</span>
                        <span class="rowMain ">
                            <a href="javascript:;"  type="text" class="address-hook" id="addressSelect" placeholder=""><span class="select-content" id="addressSpan">${appUser.province}&nbsp;&nbsp;${appUser.city}&nbsp;&nbsp;${appUser.zone}</span><span class="select-box-icon icon-sekect-arrows"></span></a>
                        </span>
                    </li>
                </ul>
            </div>
            <div class="user-margin-top user-list">
                <ul>
                    <li class="pr">
                        <span class="rowTitle">专科</span>
                        <span class="rowMain">
                            <a href="javascript:;"  type="text" class="subject-hook" id="subjectSelect" >
                                <span class="select-content" id="subjectContent">${appUser.category}&nbsp;&nbsp;${appUser.name}</span>
                                <span class="select-box-icon icon-sekect-arrows"></span>
                            </a>
                        </span>
                    </li>
                    <li class="pr">
                        <span class="rowTitle">CME卡号</span><span class="rowMain">
                            <a href="javascript:;" class="overflowText" type="text" id="CMENumber" class="CMENumber-hook" >
                                <span class="select-content">${appUser.cmeId}</span>
                                <span class="select-box-icon icon-sekect-arrows"></span>
                            </a>
                        </span>
                    </li>
                    <li class="pr">
                        <span class="rowTitle">执业资格证号</span><span class="rowMain">
                            <a href="javascript:;" class="overflowText" type="text" id="certificate" class="certificate-hook" >
                                <span class="select-content">${appUser.licence}</span>
                                <span class="select-box-icon icon-sekect-arrows"></span>
                            </a>
                        </span>
                    </li>
                    <li class="last pr overflowText">
                        <span class="rowTitle">职称</span>
                        <span class="rowMain ">
                            <a href="javascript:;"  type="text" class="medical-hook" id="medicalSelect"><span class="select-content" id="titleContent">${appUser.title}</span><span class="select-box-icon icon-sekect-arrows"></span></a>
                        </span>
                    </li>
                </ul>
            </div>
            <div class="user-margin-top user-intro">
                <h3>学术专长</h3>
                <div class="textarea">
                    <textarea class="" id="major">${appUser.major}</textarea>
                </div>

            </div>
        </div>
    </div>
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
<script src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script src="//api.map.baidu.com/api?v=2.0&ak=XlgUFkD2Gir0u83w725EiRkOK4FX3OQj" type="text/javascript"></script>
<script>
    var longitude = 0;
    var latitude = 0;
    var province;
    var city;
    var zone;
    $(function(){

        var _hos_level = "${appUser.hosLevel}";
        if(_hos_level != ''){
            for(var key in hos_level){
                if (_hos_level == hos_level[key]){
                    $("#hospitalSelect").find(".icon-hospitalLevel").attr('class','icon-hospitalLevel icon-hospitalLevel-'+(parseInt(key)+1));
                    break;
                }
            }
        }
        //alert(hos_level[_hos_level]);

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
            var point = new BMap.Point(longitude,latitude);
            var gc = new BMap.Geocoder();
            gc.getLocation(point, function(rs){
                var addComp = rs.addressComponents;
                province = addComp.province;
                city = addComp.city;
                zone = addComp.district;

                //reSetAddress(province, city, zone);
            });
        }
        //失败时
        function onError(error){
            switch(error.code){
                case 1:
                    alert("位置服务被拒绝");
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
    });

</script>
<script>
    var success, currentLevel, currentName;

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

    function modify(data){
        $.post('${ctx}/weixin/user/modify', data, function (data) {
            if (data.code != 0){
                layer.msg(data.err);
            }else{
                if (data.data == 100){
                    $(".user-progress").hide();
                } else {
                    $("#integrity").text(data.data);
                    $(".user-progress-percent").css("width", data.data+"%");
                }
            }
        },'json');
    }

    function reSetAddress(p, c, z){
        $("#addressSpan").html(p+"&nbsp;&nbsp;"+c+"&nbsp;&nbsp;"+z);
        var data = {};
        data.province = p;
        data.city = c;
        data.zone = z;
        modify(data);
    }

    function reSetHospitalInfo(c, l, n){
        $("#hospitalSelect").find(".select-content").text(n);
        $("#hospitalSelect").find(".icon-hospitalLevel").attr('class','icon-hospitalLevel icon-hospitalLevel-'+l);
        var data = {};
        data.hospital = n;
        data.hosLevel = hos_level[l-1];
        modify(data);
    }

    function reSetSubjectData(r1, r2){
        $("#subjectContent").html(r1 + '&nbsp;&nbsp;' + r2) ;
        var data = {};
        data.category = r1;
        data.name = r2;
        modify(data);
    }

    function reSetTitle(v1, v2){
        $("#titleContent").html(v1 + '&nbsp;&nbsp;' + v2) ;
        var data = {};
        data.title = v1+"  "+v2;
        modify(data);
    }

    function reSetDepart(text){
        $('#officeHook').find('.select-content').text(text);
        var data = {};
        data.department = text;
        modify(data);
    }

    function reSetCMEID(text){
        $('#CMENumber').find('.select-content').text(text);
        var data = {};
        data.cmeId = text;
        modify(data);
    }

    function reSetLicence(text){
        $('#certificate').find('.select-content').text(text);
        var data = {};
        data.licence = text;
        modify(data);
    }

    function saveMajor(text){
        var data = {};
        data.major = text;
        modify(data);
    }


    function showHospitalLevel(f){

        var _currentHospitalName = f;
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
                $("#bindForm").submit();
            }
        });

        loadTitleData();
        //reSetTitle(titleData[0].label, titleData[0].children[0].label);

        loadSpecialtyData();
        //reSetSubjectData(specialtyArray[0].label, specialtyArray[0].children[0].label);


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
                url:'${ctx}/api/register/nearby/hospital',
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
                        _this.addClass('select-box-onColorChange');
                        _this.find('.select-content').text(_currentName);
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
                    skin: 'layui-layer-confirm'
                },function(a){
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
                    url:'${ctx}/api/register/regions',
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

                    //$('#addressSelect').find(".select-content").attr("class",'select-box-onColorChange');
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
                    url:'${ctx}/api/register/specialties',
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
                    //$("#subjectSelect").find(".select-content").attr("class", 'select-box-onColorChange');
                }
            });

        }


        function loadTitleData(){
            if(titleData.length == 0){
                $.ajax({
                    url:'${ctx}/api/register/title',
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
                    //$("#medicalSelect").find(".select-content").attr("class", 'select-box-onColorChange');
                }
            });
        }
        //职称联动下拉
        $('#medicalSelect').on('click',function(){
            fillTitle();
        });


        //科室弹出修改
        $('#officeHook').on('click',function(){
            //prompt层
            layer.prompt({
                title: '修改科室',
                formType: 0,
                skin: 'layui-layer-popup',
            }, function(text, index){
                reSetDepart(text);
                layer.close(index);
            });
        });

        //CME卡号
        $('#CMENumber').on('click',function(){
            //prompt层
            layer.prompt({
                title: '修改CME卡号',
                formType: 0,
                skin: 'layui-layer-popup',
            }, function(text, index){
                reSetCMEID(text);
                layer.close(index);
            });
        });

        //执业资格证号
        $('#certificate').on('click',function(){
            //prompt层
            layer.prompt({
                title: '修改执业证号',
                formType: 0,
                skin: 'layui-layer-popup',
            }, function(text, index){
                reSetLicence(text);
                layer.close(index);
            });
        });

        function uploadHead(cf){
            var fileName = cf.val();
            var reg = /^.+\.(jpg|png)$/g;
            var validFileSuffix = reg.test(fileName.toLowerCase());
            if (validFileSuffix){
                $.ajaxFileUpload({
                    url: '${ctx}/weixin/user/modify/head', //用于文件上传的服务器端请求地址
                    secureuri: false, //是否需要安全协议，一般设置为false
                    fileElementId: "changeIMG", //文件上传域的ID
                    dataType: 'json', //返回值类型 一般设置为json
                    success: function (data)  //服务器成功响应处理函数
                    {
                        //回调函数传回传完之后的URL地址
                        if(data.code == 0){
                            $("#headImage").attr("src", data.data.absolutePath);
                        }else{
                            layer.msg(data.err);
                        }
                        cf.replaceWith('<input type="file" name="file" id="changeIMG">');
                        $("#changeIMG").on('change',function(){
                            uploadHead($(this));
                        });
                    },
                    error:function(data, status, e){
                        alert(e);
                    }
                });
            }else{
                layer.msg("请选择jpg、png格式");
            }
        }

        $("#changeIMG").change(function(){
            uploadHead($(this));
        });




        $("#major").change(function(){
            var major = $.trim($("#major").val());
            if (major != ''){
                saveMajor(major);
            }
        });
    })
</script>
</body>

</html>

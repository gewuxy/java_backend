<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/23
  Time: 10:49
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>签到信息编辑</title>

    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
    <link href="${ctxStatic}/css/daterangepicker.css" rel="stylesheet">
</head>
<body>
<div class="g-main  mettingForm clearfix">

    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-03.png" alt="">
                </div>
                <div class="oh">
                    <p><strong>会议发布</strong></p>
                    <p>快速发布在线会议，下载手机app端即可查看PPT、直播、调研、考试等会议</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->

    <div class="tab-hd">

        <ul class="tab-list clearfix">
            <li class="cur">
                <a>会议发布<i></i></a>
            </li>
            <li>
                <a href="${ctx}/func/meet/draft">草稿箱<i></i></a>
            </li>
            <li>
                <a href="${ctx}/func/meet/list">已发布会议<i></i></a>
            </li>
        </ul>
    </div>

    <div class="tab-bd" style="padding-bottom:500px;">

        <div class=" clearfix metting-process">
            <div class=" clearfix link-justified metting-process-02">
                <ul>
                    <li class="cur">创建会议</li>
                    <li>功能设置</li>
                    <li>发布预览</li>
                </ul>
            </div>
        </div>



        <%@include file="funTab.jsp"%>
        <form action="${ctx}/func/meet/position/save" method="post" name="positionForm" id="positionForm">
        <input type="hidden" name="meetId" value="${meetId}">
        <input type="hidden" name="moduleId" value="${moduleId}">
        <input type="hidden" name="id" value="${position.id}">
        <div class="formrow formrow-del-box formrow-del-hook">
            <label for="" class="formTitle formTitle-style2 formTItleMargin">签到设置</label>
            <div class="formControls">
                <div class="formControls">
                    <p class="color-gray-2-up">签到时间&nbsp;<span class="time-tj">
									<label for="" id="timeStart1">
										<a href="javascript:;" class="callTimedate timedate-icon">选择日期</a><input type="text" readonly name="startTime" id="startTime" value="${startTime}" class="timedate-input " placeholder=""  style="width: 120px;"> ~ <input type="text" readonly name="endTime" style="width: 120px;" class="timedate-input " value="${endTime}" placeholder="" id="endTime">
									</label>
								</span></p>
                    <p>会场经度&nbsp;&nbsp;<input type="text" placeholder="会场经度" name="positionLng" readonly id="positionLng" value="${position.positionLng}" class="textInput" data-type="email"></p>
                    <p>会场纬度&nbsp;&nbsp;<input type="text" placeholder="会场纬度" name="positionLat" readonly id="positionLat" value="${position.positionLat}" class="textInput" data-type="email"></p>
                    <p>会场地址&nbsp;&nbsp;<input type="text" placeholder="会场地址" name="positionName" readonly id="positionName" value="${position.positionName}" class="textInput" data-type="email"></p>
                    <div class="metting-Map clearfix pr">
                        <script type="text/javascript" src="//api.map.baidu.com/api?v=2.0&ak=8khx0tj4mlproerlsppHpVfMuCK2pabX"></script>
                        <div style="" id="allmap" class="map-box"></div>
                        <div id="r-result" class="map-inputCurrent" style="">&nbsp;&nbsp;<input type="text" id="suguestId" value="123123" name="suguestId" size="50"  style="width:250px; height: 30px; position:relative;top:-10px;" /></div>
                        <div id="searchResultPanel" ></div>

                    </div>
                </div>
            </div>
        </div>
        <div class="buttonArea clearfix" style="margin: 20px 25px 40px;">
            <div class="formrow">
                <div class="fl clearfix">
                    <input type="button" onclick="window.location.href='${ctx}/func/meet/edit?id=${meetId}'" class="formButton formButton-max" value="上一步">&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" id="submitBtn" class="formButton formButton-max" value="下一步">
                </div>
            </div>
        </div>
    </form>
</div>
<script src="${ctxStatic}/js/moment.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/js/jquery.daterangepicker.js"></script>
<script>

    function checkForm(){
        var startTime = $("#startTime").val();
        if(startTime == ''){
            layer.tips("请设置签到时间","#startTime");
            return false;
        }

        var positionLng = $("#positionLng").val();
        if(positionLng == ''){
            layer.tips("请设置会场经度", "#positionLng");
            return false;
        }

        var positionLat = $("#positionLat").val();
        if(positionLat == ''){
            layer.tips("请设置会场纬度", "#positionLat");
            return false;
        }

        var positionName = $("#positionName").val();
        if(positionName == ''){
            layer.tips("请设置会场地址", "#positionName");
            return false;
        }

        return true;
    }

    $(function(){

        $("#submitBtn").click(function(){

            if(checkForm()){
                $.post($("#positionForm").attr("action"), $("#positionForm").serialize(), function () {
                    window.location.href = "${ctx}/func/meet/finish?meetId=${meetId}&moduleId=${moduleId}";
                },'json');
            }
        });

        /*$("#draftBtn").click(function(){
            if(checkForm()){
                $.post($("#positionForm").attr("action"), $("#positionForm").serialize(), function () {
                    layer.msg("保存草稿成功");
                },'json');
            }
        });*/
        $(".callTimedate").on('click',function(){
            $('#timeStart1').trigger('focus');
        });
        $('#timeStart1').dateRangePicker({
            singleMonth: true,
            showShortcuts: false,
            showTopbar: false,
            startOfWeek: 'monday',
            separator : ' ~ ',
            format: 'YYYY/MM/DD HH:mm',
            autoClose: false,
            time: {
                enabled: true
            }
        }).bind('datepicker-first-date-selected', function(event, obj){
            /*首次点击的时间*/
        }).bind('datepicker-change',function(event,obj){
            /* This event will be triggered when second date is selected */
            var timeArr = obj.value.split("~");
            $("#startTime").val($.trim(timeArr[0]));
            $("#endTime").val($.trim(timeArr[1]));
            // {
            // 		date1: (开始时间),
            // 		date2: (开始时间),
            //	 	value: "2013-06-05 00:00 to 2013-06-07 00:00"
            // }
        });















    });


    var mp = new BMap.Map("allmap");
    var point = new BMap.Point(116.404, 39.915);
    mp.centerAndZoom(point, 14);
    mp.addOverlay(new BMap.Marker(point));
    console.log(mp);

    mp.enableScrollWheelZoom();
    mp.enableInertialDragging();

    mp.enableContinuousZoom();

    // 添加带有定位的导航控件
    var navigationControl = new BMap.NavigationControl({
        // 靠左上角位置
        anchor: BMAP_ANCHOR_TOP_RIGHT,
        // LARGE类型
        type: BMAP_NAVIGATION_CONTROL_LARGE,
        // 启用显示定位
        enableGeolocation: true
    });
    mp.addControl(navigationControl);
    mp.addEventListener("click", function(e){
        var pt = e.point;
        mp.clearOverlays();    //清除地图上所有覆盖物
        mp.addOverlay(new BMap.Marker(pt));    //添加标注
//        alert(pt.lng + ", " + pt.lat);
        var geoc = new BMap.Geocoder();
        geoc.getLocation(pt, function(rs){
            var addComp = rs.addressComponents;
            var addCompInfo = addComp.city  + addComp.district  + addComp.street + addComp.streetNumber;
            $("#positionLat").val(pt.lat);
            $("#positionLng").val(pt.lng);
            G("searchResultPanel").innerHTML = addCompInfo;
            $("#positionName").val(addCompInfo);
            G("suguestId").value = addCompInfo ;

        });
    });

    // 百度地图API功能
    function G(id) {
        return document.getElementById(id);
    }

    var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
        {"input" : "suguestId"
            ,"location" : mp
        });

    ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
        var str = "";
        var _value = e.fromitem.value;
        var value = "";
        if (e.fromitem.index > -1) {
            value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        }
        str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;

        value = "";
        if (e.toitem.index > -1) {
            _value = e.toitem.value;
            value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        }
        str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
        G("searchResultPanel").innerHTML = str;
    });

    var myValue;
    ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
        var _value = e.item.value;
        myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
        setPlace();
    });

    function setPlace(){
        mp.clearOverlays();    //清除地图上所有覆盖物
        function myFun(){
            var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
            mp.centerAndZoom(pp, 18);
            mp.addOverlay(new BMap.Marker(pp));    //添加标注
            //alert(pp.lng + ", " + pp.lat);
            var geoc = new BMap.Geocoder();
            geoc.getLocation(pp, function(rs){
                var addComp = rs.addressComponents;
                //alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber);
            });
        }
        var local = new BMap.LocalSearch(mp, { //智能搜索
            onSearchComplete: myFun
        });
        local.search(myValue);
    }

    var size = new BMap.Size(10, 20);
    mp.addControl(new BMap.CityListControl({
        anchor: BMAP_ANCHOR_TOP_LEFT,
        offset: size,
//         切换城市之间事件
//         onChangeBefore: function(){
//            alert('before');
//         },
//         切换城市之后事件
//         onChangeAfter:function(){
//           alert('after');
//         }

    }));
</script>




</body>
</html>

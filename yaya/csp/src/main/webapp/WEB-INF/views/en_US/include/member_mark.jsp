<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
    <style>
        html { background: #fff; }
        body { min-width: auto;}
        .member-popup-box { display: block;}
        .user-content .time-mode-list .item{display: inline-block; padding:5px 10px; margin: 0 25px 5px 5px; width:100px; text-align: center; border:2px solid #fff;   vertical-align: top; cursor: pointer;}
        .user-content .time-mode-list .pay-on {  border:2px solid #167AFE; }
        .user-content .time-mode-list { padding:0;}
        .member-buy-content .user-content .time-mode-list .item {  padding:5px 10px ; margin:0 10px;}
    </style>
    <script>
        var selectPk = 1;  //当前选中的套餐
        var limitTimes = 1; //当前点击套餐时长
        var flag = "hg"; //当前选中套餐
        var initId = 1;

        //获取金额
        function sumMoney(){
            var currency = $("#" + flag +  "View").find('input[name='+flag+'Currency]:checked').val();
            ajaxSyncPost('${ctx}/mgr/pay/getMoney', {'version': selectPk,"limitTimes":limitTimes,"currency":currency}, function(data){
                if (data.code == 0){
                    selectPk == 1 ? $("#hgTotal").html(data.data): $("#pfTotal").html(data.data);
                } else {
                    layer.msg(data.err);
                }
            });
        }

        function initPackage(userPackage){
            //低版本不能选择
            if(userPackage != undefined){
                initId = userPackage.packageId;
                var tabsMainNum = $(".member-buy-tabs-main").find('.member-buy-content');
                if(initId != 1) tabsMainNum.eq(initId - 1).removeClass('none').siblings().addClass('none');
                for(var k = 0;k < initId;k++){
                    $("#menu" + initId).addClass("member-buy-disabled");
                }
            }

        }

        $(function () {
            var tabsMainNum = $(".member-buy-tabs-main").find('.member-buy-content');
            tabsMainNum.eq(1).removeClass('none').siblings().addClass('none');
            //获取套餐信息
            ajaxSyncGet('${ctx}/mgr/pay/package', {}, function(data){
                var course = data.data;
                if (course == undefined){
                    layer.msg("获取套餐信息失败，请刷新重试");
                    return false;
                }
                //初始化套餐选择
                initPackage(course.package);
                //加载套餐数据
                initSwiper(course);
            });

            //选购套餐提交
            $('input[name="commitPay"]').click(function(){
                //低于当前版本不能购买
                if(initId < selectPk + 1){
                    layer.msg("当前套餐版本低于之前版本，不能购买");
                    return false;
                }
                if(selectPk != 0) {
                    var limitTime =  $("#" + flag + "View").find('input[name='+flag+'TimeMode]:checked').val();
                    var payType =  $("#" + flag + "View").find('input[name='+flag+'PayMode]:checked').val();
                    var currency =  $("#" + flag + "View").find('input[name='+flag+'Currency]:checked').val();
                    $("#limitTime").val(limitTime);
                    $("#payType").val(payType);
                    $("#currency").val(currency);
                }
                $("#packageId").val(selectPk);
                $("#rechargeFrom").submit();
            });

            //选择购买时长
            $(".time-mode-list label").click(function(){
                $(this).addClass('pay-on').siblings().removeClass('pay-on');
                limitTimes = $(this).children().eq(0).val();
                sumMoney();
            });

            //支付方式
            $(".pay-mode-list label").click(function(){
                $(this).addClass('pay-on').siblings().removeClass('pay-on');
            });

            //货币切换
            $(".money-state label").click(function(){
                $(this).addClass('on').siblings().removeClass('on');
                var currencyValue = $(this).parents('.pay-mode').find('input[name='+flag+'Currency]:checked').val();
                if( currencyValue == 'CN'){
                    $(this).parents('.pay-mode').find('.CN-hook').removeClass('none').siblings().addClass('none');
                } else if ( currencyValue =='EN' ){
                    $(this).parents('.pay-mode').find('.EN-hook').removeClass('none').siblings().addClass('none');
                }
                sumMoney();
            });

            //选择不同版本
            $(".member-buy-tabs-menu").find('.index-buy-item').on('click',function(){
                var index = $(this).index();
                selectPk = index;
                flag = selectPk == 1 ? "hg": "pf";
                tabsMainNum.eq(index).removeClass('none').siblings().addClass('none');
                $(this).addClass('index-buy-item-current').siblings().removeClass('index-buy-item-current');
                sumMoney();
            })
        })

        //加载选购套餐信息
        function initSwiper(course) {
            var package = course.packages;
            for(var i = 0;i < package.length;i++){
                var packageId = package[i].id;
                if(packageId == 2){
                    $("#price" + packageId).html(package[i].monthUsd + "USD");
                    $("#hgTotal").html(package[i].monthUsd);
                }
                if(packageId == 3) {
                    $("#price" + packageId).html(package[i].monthUsd + "USD/" + package[i].yearUsd + "USD");
                    $("#pfTotal").html(package[i].monthUsd);
                }
                $("#meets" + packageId).html(package[i].limitMeets == 0? "Unlimited Number of Meetings":package[i].limitMeets + "Meetings");
            }
            var info = course.infos;
            for(var j = 0;j < info.length;j++){
                var id = info[j].packageId;
                if (info[j].state == true) $("#info" + id).append('<li class="icon-li-selected">' + info[j].descriptUs + '</li>');
            }
        }
    </script>
</head>
<body>
<!--弹出购买会员项-->
<div class="member-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-main member-buy-popup-main">
            <div class="member-buy-header">
                <h6 class="title">Please select the edition you want</h6>
                <div class="member-buy-tabs-menu clearfix" >
                    <div class="index-buy-item ">
                        <div class="index-buy-header">
                            <h4>Standard Edition</h4>
                            <h3 class="price">Free</h3>
                        </div>
                        <div class="index-buy-main">
                            <div class="index-buy-info">
                                <p>Permanent Usage</p>
                                <p id="meets1"></p>
                            </div>
                            <div class="index-buy-text">
                                <ul id="info1">
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="index-buy-item  index-buy-item-current ">
                        <div class="index-buy-header">
                            <h4>Premium Edition</h4>
                            <h3 class="price" id="price2"></h3>
                        </div>
                        <div class="index-buy-main">
                            <div class="index-buy-info">
                                <p>One-Month Usage</p>
                                <p id="meets2"></p>
                            </div>
                            <div class="index-buy-text">
                                <ul id="info2">
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="index-buy-item last">
                        <div class="index-buy-header">
                            <h4>Professional Edition</h4>
                            <h3 class="price" id="price3"></h3>
                        </div>
                        <div class="index-buy-main">
                            <div class="index-buy-info">
                                <p>One-Month / One-Year Usage</p>
                                <p id="meets3">Unlimited Number of Meetings</p>
                            </div>
                            <div class="index-buy-text">
                                <ul id="info3">
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="member-buy-tabs-main">
                <div class="member-buy-content">
                    <div class="user-content item-radius pay-mode member-buy-disabled" id="menu1">
                        <div class="formrow">
                            <div class="formTitle color-black">Valid Through</div>
                            <div class="formControls">
                                <div class="time-mode-list">
                                    <label for="tid1" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="1" id="tid1">
                                        1 month
                                    </label>
                                    <label for="tid2" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="tid2">
                                        3 month
                                    </label>
                                    <label for="tid3" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="6" id="tid3">
                                        6 month
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow" >
                            <div class="formTitle color-black">Recharge Channel</div>
                            <div class="formControls">
                                <div class="pay-mode-list CN-hook">
                                    <label for="id11" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="1" id="id11">
                                        <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                    </label>
                                    <label for="id21" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="2" id="id21">
                                        <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                    </label>
                                    <label for="id31" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="id31">
                                        <img src="${ctxStatic}/images/img/user-icon-unionpay.png" alt="">
                                    </label>
                                </div>
                                <div class="pay-mode-list EN-hook none">
                                    <label for="id4" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="4" id="id4">
                                        <img src="${ctxStatic}/images/img/user-icon-visa.png" alt="">
                                    </label>
                                    <label for="id5" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="5" id="id5">
                                        <img src="${ctxStatic}/images/img/user-icon-paypal.png" alt="">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow money">
                            <div class="formTitle color-black">Amount</div>
                            <div class="formControls">
                                <span class="payNum">0.00</span>
                                <span class="money-state">
                                        <label for="currency-cn" class="cn on">
                                            <input type="radio" name="currency" id="currency-cn" checked="checked" class="none" value="CN">
                                            CNY
                                        </label>
                                        <label for="currency-en" class="en">
                                            <input type="radio" name="currency" id="currency-en" class="none" value="EN">
                                            USD
                                        </label>
                                    </span>
                            </div>
                        </div>
                        <div class="formrow t-center last">
                            <input href="#" type="button" class="button login-button buttonBlue cancel-hook last" name="commitPay" value="Try Now" style="position: relative; z-index:3;">
                        </div>
                        <div class="member-buy-disabled-item"></div>
                    </div>
                </div>
                <div class="member-buy-content none" id="hgView">
                    <div class="user-content item-radius pay-mode" id="menu2">
                        <div class="formrow">
                            <div class="formTitle color-black">Valid Through</div>
                            <div class="formControls">
                                <div class="time-mode-list">
                                    <label for="2tid1" class="item item-radius pay-on">
                                        <input type="radio" name="hgTimeMode" class="none" checked="checked" value="1" id="2tid1">
                                        1 month
                                    </label>
                                    <label for="2tid2" class="item item-radius">
                                        <input type="radio" name="hgTimeMode" class="none" value="3" id="2tid2">
                                        3 month
                                    </label>
                                    <label for="2tid3" class="item item-radius">
                                        <input type="radio" name="hgTimeMode" class="none" value="6" id="2tid3">
                                        6 month
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow " >
                            <div class="formTitle color-black">Recharge Channel</div>
                            <div class="formControls">
                                <div class="pay-mode-list CN-hook">
                                    <label for="2id11" class="item item-radius pay-on">
                                        <input type="radio" name="hgPayMode" class="none" checked="checked" value="alipay_wap" id="2id11">
                                        <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                    </label>
                                    <label for="2id21" class="item item-radius">
                                        <input type="radio" name="hgPayMode" class="none" value="wx_pub_qr" id="2id21">
                                        <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                    </label>
                                    <label for="2id31" class="item item-radius">
                                        <input type="radio" name="hgPayMode" class="none" value="upacp_wap" id="2id31">
                                        <img src="${ctxStatic}/images/img/user-icon-unionpay.png" alt="">
                                    </label>
                                </div>
                                <div class="pay-mode-list EN-hook none">
                                    <label for="2id4" class="item item-radius pay-on">
                                        <input type="radio" name="hgPayMode" class="none" value="4" id="2id4">
                                        <img src="${ctxStatic}/images/img/user-icon-visa.png" alt="">
                                    </label>
                                    <label for="2id5" class="item item-radius">
                                        <input type="radio" name="hgPayMode" class="none" value="5" id="2id5">
                                        <img src="${ctxStatic}/images/img/user-icon-paypal.png" alt="">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow money">
                            <div class="formTitle color-black">Amount</div>
                            <div class="formControls">
                                <span class="payNum" id="hgTotal"></span>
                                <span class="money-state">
                                        <label for="currency-cn2" class="cn on">
                                            <input type="radio" name="hgCurrency" id="currency-cn2" checked="checked" class="none" value="CN">
                                            CNY
                                        </label>
                                        <label for="currency-en2" class="en">
                                            <input type="radio" name="hgCurrency" id="currency-en2" class="none" value="EN">
                                            USD
                                        </label>
                                    </span>
                            </div>
                        </div>
                        <div class="formrow t-center last">
                            <input href="#" type="button" class="button login-button buttonBlue cancel-hook last" name="commitPay" value="Confirm Payment">
                        </div>
                    </div>
                </div>
                <div class="member-buy-content none" id="pfView">
                    <div class="user-content item-radius pay-mode" id="menu3">
                        <div class="formrow">
                            <div class="formTitle color-black">Valid Through</div>
                            <div class="formControls">
                                <div class="time-mode-list">
                                    <label for="3tid1" class="item item-radius pay-on">
                                        <input type="radio" name="pfTimeMode" class="none"  checked="checked"  value="1" id="3tid1" >
                                        1 month
                                    </label>
                                    <label for="3tid2" class="item item-radius">
                                        <input type="radio" name="pfTimeMode" class="none" value="3" id="3tid2">
                                        3 month
                                    </label>
                                    <label for="3tid3" class="item item-radius">
                                        <input type="radio" name="pfTimeMode" class="none" value="6" id="3tid3">
                                        6 month
                                    </label>
                                    <label for="3tid4" class="item item-radius">
                                        <input type="radio" name="pfTimeMode" class="none" value="12" id="3tid4">
                                        1 year
                                    </label>
                                    <label for="3tid5" class="item item-radius">
                                        <input type="radio" name="pfTimeMode" class="none" value="24" id="3tid5">
                                        2 year年
                                    </label>
                                    <label for="3tid6" class="item item-radius">
                                        <input type="radio" name="pfTimeMode" class="none" value="36" id="3tid6">
                                        3 year
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow " >
                            <div class="formTitle color-black">充值方式</div>
                            <div class="formControls">
                                <div class="pay-mode-list CN-hook">
                                    <label for="3id11" class="item item-radius pay-on">
                                        <input type="radio" name="pfPayMode" class="none"  checked="checked" value="alipay_wap" id="3id11">
                                        <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                    </label>
                                    <label for="3id21" class="item item-radius">
                                        <input type="radio" name="pfPayMode" class="none" value="wx_pub_qr" id="3id21">
                                        <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                    </label>
                                    <label for="3id31" class="item item-radius">
                                        <input type="radio" name="pfPayMode" class="none" value="upacp_wap" id="3id31">
                                        <img src="${ctxStatic}/images/img/user-icon-unionpay.png" alt="">
                                    </label>
                                </div>
                                <div class="pay-mode-list EN-hook none">
                                    <label for="3id4" class="item item-radius pay-on">
                                        <input type="radio" name="pfPayMode" class="none" value="4" id="3id4">
                                        <img src="${ctxStatic}/images/img/user-icon-visa.png" alt="">
                                    </label>
                                    <label for="3id5" class="item item-radius">
                                        <input type="radio" name="pfPayMode" class="none" value="5" id="3id5">
                                        <img src="${ctxStatic}/images/img/user-icon-paypal.png" alt="">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow money">
                            <div class="formTitle color-black">Recharge Channel</div>
                            <div class="formControls">
                                <span class="payNum" id="pfTotal"></span>
                                <span class="money-state">
                                        <label for="currency-cn3" class="cn on">
                                            <input type="radio" name="pfCurrency" id="currency-cn3" checked="checked" class="none" value="CN">
                                            CNY
                                        </label>
                                        <label for="currency-en3" class="en">
                                            <input type="radio" name="pfCurrency" id="currency-en3" class="none" value="EN">
                                            USD
                                        </label>
                                    </span>
                            </div>
                        </div>
                        <div class="formrow t-center last">
                            <input href="#" type="button" class="button login-button buttonBlue cancel-hook last" name="commitPay" value="Confirm Payment">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<form id="rechargeFrom" type="hidden" name="rechargeForm" method="post" action="${ctx}/mgr/pay/toPay" target="_blank">
    <input type="hidden" name="limitTime" id="limitTime" value="">
    <input type="hidden" name="payType" id="payType" value="">
    <input type="hidden" name="currency" id="currency" value="">
    <input type="hidden" name="packageId" id="packageId" value="">
</form>
</body>
</html>





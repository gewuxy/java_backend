<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/style-EN.css">
    <style>
        html { background: #fff; }
        body { min-width: auto;}
        .member-popup-box { display: block;}
        .user-content .time-mode-list .item{display: inline-block; padding:5px 5px; margin: 0 25px 5px 5px; width:100px; text-align: center; border:2px solid #fff;   vertical-align: top; cursor: pointer;}
        .user-content .time-mode-list .pay-on {  border:2px solid #167AFE; }
        .user-content .time-mode-list { padding:0;}
        .member-buy-content .user-content .time-mode-list .item {  padding:5px 5px ; margin:0 10px;}
    </style>
    <script>
        var selectPk = 1;  //当前选中的套餐
        var limitTimes = 1; //当前点击套餐时长
        var flag = "hg"; //当前选中套餐
        var initId = 1;

        //获取金额
        function sumMoney() {
            var currency = $("#" + flag + "View").find('input[name=' + flag + 'Currency]:checked').val();
            ajaxSyncPost('${ctx}/mgr/pay/money ', {
                'version': selectPk,
                "limitTimes": limitTimes,
                "currency": currency
            }, function (data) {
                if (data.code == 0) {
                    selectPk == 1 ? $("#hgTotal").html(data.data) : $("#pfTotal").html(data.data);
                } else {
                    layer.msg(data.err);
                }
            });
        }

        //初始化版本选择
        function initPackage(packageId) {
            //低版本不能选择
            initId = packageId;
            if (packageId != undefined) {
                if (initId != 1) {
                    $(".member-buy-tabs-menu").find('.index-buy-item').eq(initId - 1).addClass('index-buy-item-current').siblings().removeClass('index-buy-item-current');
                    if (initId != 1) $(".member-buy-tabs-main").find('.member-buy-content').eq(initId - 1).removeClass('none').siblings().addClass('none');
                    selectPk = initId - 1;
                    flag = initId == 3 ? "pf" : "hg";
                }
                //是否需要不能选择
                if(initId == 3) $("#disabledItem2").addClass("member-buy-disabled-item");
            } else {
                $("#newUser").css("position"," relative").css("z-index","3");
                //新用户隐藏取消按钮
                $(".layui-layer-close").remove();
            }
            $("#disabledItem1").addClass("member-buy-disabled-item");
        }

        $(function () {
            var tabsMainNum = $(".member-buy-tabs-main").find('.member-buy-content');
            tabsMainNum.eq(1).removeClass('none').siblings().addClass('none');
            //获取套餐信息
            ajaxSyncGet('${ctx}/mgr/pay/package', {}, function (data) {
                var course = data.data;
                if (course == undefined) {
                    layer.msg("获取套餐信息失败，请刷新重试");
                    return false;
                }
                //初始化套餐选择
                initPackage(course.package);
                //加载套餐数据
                initSwiper(course);
            });

            //取消选购
            $(".layui-layer-close").click(function () {
                parent.layer.closeAll();
            });

            //选购套餐提交
            $('input[name="commitPay"]').click(function () {
                $("#packageId").val(selectPk);
                if(selectPk == 0){  //标准版提交
                    return false;
                }
                //高级版跟专业版
                var limitTime = $("#" + flag + "View").find('input[name=' + flag + 'TimeMode]:checked').val();
                var payType = $("#" + flag + "View").find('input[name=' + flag + 'PayMode]:checked').val();
                var currency = $("#" + flag + "View").find('input[name=' + flag + 'Currency]:checked').val();
                $("#limitTime").val(limitTime);
                $("#payType").val(payType);
                $("#currency").val(currency);
                $("#packageId").val(selectPk);
                $("#rechargeFrom").submit();
            });

            //选择购买时长
            $(".time-mode-list label").click(function () {
                $(this).addClass('pay-on').siblings().removeClass('pay-on');
                limitTimes = $(this).children().eq(0).val();
                sumMoney();
            });

            //支付方式
            $(".pay-mode-list label").click(function () {
                $(this).addClass('pay-on').siblings().removeClass('pay-on');
            });

            //货币切换
            $(".money-state label").click(function () {
                $(this).addClass('on').siblings().removeClass('on');
                var currencyValue = $(this).parents('.pay-mode').find('input[name=' + flag + 'Currency]:checked').val();
                if (currencyValue == 0) { //rnb
                    $("#" + flag + "View").find('input[name=' + flag + 'PayMode][value=paypal]').attr("checked",true);
                    $(this).parents('.pay-mode').find('.CN-hook').removeClass('none').siblings().addClass('none');
                } else if (currencyValue == 1) {  //usd
                    $("#" + flag + "View").find('input[name=' + flag + 'PayMode][value=alipay_wap]').attr("checked",true);
                    $(this).parents('.pay-mode').find('.EN-hook').removeClass('none').siblings().addClass('none');
                }
                sumMoney();
            });

            //选择不同版本
            $(".member-buy-tabs-menu").find('.index-buy-item').on('click', function () {
                var index = $(this).index();
                selectPk = index;
                flag = selectPk == 1 ? "hg" : "pf";
                tabsMainNum.eq(index).removeClass('none').siblings().addClass('none');
                $(this).addClass('index-buy-item-current').siblings().removeClass('index-buy-item-current');
                sumMoney();
            })
        })

        //加载选购套餐信息
        function initSwiper(course) {
            var package = course.packages;
            for (var i = 0; i < package.length; i++) {
                var packageId = package[i].id;
                if (packageId == 2) {
                    $("#price" + packageId).html(package[i].monthUsd + "USD");
                    $("#hgTotal").html(package[i].monthUsd);
                }
                if (packageId == 3) {
                    $("#price" + packageId).html(package[i].monthUsd + "/" + package[i].yearUsd + "USD");
                    $("#pfTotal").html(package[i].monthUsd);
                }
                $("#meets" + packageId).html(package[i].limitMeets == 0 ? "Unlimited Number of Meetings" : package[i].limitMeets + " Meetings");
            }
            var info = course.infos;
            for (var j = 0; j < info.length; j++) {
                var id = info[j].packageId;
                if (info[j].state == true) {
                    if(info[j].iden == "SYB" && id == 3){
                        break;
                    }
                    $("#info" + id).append('<li class="icon-li-selected">' + info[j].descriptUs + '</li>');
                }
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
                    <div class="index-buy-item  index-buy-item-current">
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
                    <div class="user-content item-radius pay-mode member-buy-disabled">
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
                                <div class="pay-mode-list CN-hook none">
                                    <label for="id11" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="1" id="id11">
                                        <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                    </label>
                                    <label for="id21" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="2" id="id21">
                                        <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                    </label>
                                </div>
                                <div class="pay-mode-list EN-hook">
                                    <label for="id41" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" checked="checked" value="paypal" id="id41">
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
                                        <label for="currency-cn" class="cn ">
                                            <input type="radio" name="currency" id="currency-cn" class="none" value="0">
                                            CNY
                                        </label>
                                        <label for="currency-en" class="en on">
                                            <input type="radio" name="currency" id="currency-en" checked="checked" class="none" value="1">
                                            USD
                                        </label>
                                    </span>
                            </div>
                        </div>
                        <div class="formrow t-center last" id="newUser">
                            <a href="javascript:;" class="button login-button layui-layer-close" style="position: relative; z-index:3;">Cancel</a>
                            <input href="#" type="button" class="button login-button buttonBlue cancel-hook last" name="commitPay" value="Try Now"/>
                        </div>
                        <div id="disabledItem1"></div>
                    </div>
                </div>
                <div class="member-buy-content none" id="hgView">
                    <div class="user-content item-radius pay-mode member-buy-disabled">
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
                        <div class="formrow" >
                            <div class="formTitle color-black">Recharge Channel</div>
                            <div class="formControls">
                                <div class="pay-mode-list CN-hook none">
                                    <label for="2id11" class="item item-radius pay-on">
                                        <input type="radio" name="hgPayMode" class="none" value="alipay_wap" id="2id11">
                                        <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                    </label>
                                    <label for="2id21" class="item item-radius">
                                        <input type="radio" name="hgPayMode" class="none" value="wx_pub_qr" id="2id21">
                                        <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                    </label>
                                </div>
                                <div class="pay-mode-list EN-hook">
                                    <label for="2id41" class="item item-radius pay-on">
                                        <input type="radio" name="hgPayMode" class="none" checked="checked" value="paypal" id="2id41">
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
                                        <label for="currency-cn2" class="cn">
                                            <input type="radio" name="hgCurrency" id="currency-cn2"  class="none" value="0">
                                            CNY
                                        </label>
                                        <label for="currency-en2" class="en on">
                                            <input type="radio" name="hgCurrency" id="currency-en2" checked="checked" class="none" value="1">
                                            USD
                                        </label>
                                    </span>
                            </div>
                        </div>
                        <div class="formrow t-center last">
                            <a href="javascript:;" class="button login-button layui-layer-close" style="position: relative; z-index:3;">Cancel</a>
                            <input href="#" type="button" class="button login-button buttonBlue cancel-hook last" name="commitPay" value="Confirm Payment"/>
                        </div>
                        <div id="disabledItem2"></div>
                    </div>
                </div>
                <div class="member-buy-content none" id="pfView">
                    <div class="user-content item-radius pay-mode">
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
                                        2 year
                                    </label>
                                    <label for="3tid6" class="item item-radius">
                                        <input type="radio" name="pfTimeMode" class="none" value="36" id="3tid6">
                                        3 year
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow " >
                            <div class="formTitle color-black">Recharge Channel</div>
                            <div class="formControls">
                                <div class="pay-mode-list CN-hook none">
                                    <label for="3id11" class="item item-radius pay-on">
                                        <input type="radio" name="pfPayMode" class="none" value="alipay_wap" id="3id11">
                                        <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                    </label>
                                    <label for="3id21" class="item item-radius">
                                        <input type="radio" name="pfPayMode" class="none" value="wx_pub_qr" id="3id21">
                                        <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                    </label>
                                </div>
                                <div class="pay-mode-list EN-hook">
                                    <label for="3id41" class="item item-radius pay-on">
                                        <input type="radio" name="pfPayMode" class="none" checked="checked" value="paypal" id="3id41">
                                        <img src="${ctxStatic}/images/img/user-icon-paypal.png" alt="">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow money">
                            <div class="formTitle color-black">Amount</div>
                            <div class="formControls">
                                <span class="payNum" id="pfTotal"></span>
                                <span class="money-state">
                                        <label for="currency-cn3" class="cn">
                                            <input type="radio" name="pfCurrency" id="currency-cn3"  class="none" value="0">
                                            CNY
                                        </label>
                                        <label for="currency-en3" class="en on">
                                            <input type="radio" name="pfCurrency" id="currency-en3" checked="checked" class="none" value="1">
                                            USD
                                        </label>
                                    </span>
                            </div>
                        </div>
                        <div class="formrow t-center last" style="position: relative; z-index:3;">
                            <a href="javascript:;" class="button login-button layui-layer-close" >Cancel</a>
                            <input href="#" type="button" class="button login-button buttonBlue cancel-hook last" name="commitPay" value="Confirm Payment">
                        </div>
                        <div id="disabledItem3"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<form id="rechargeFrom" type="hidden" name="rechargeForm" method="post" action="${ctx}/mgr/pay/pay" target="_blank">
    <input type="hidden" name="limitTime" id="limitTime" value="">
    <input type="hidden" name="payType" id="payType" value="">
    <input type="hidden" name="currency" id="currency" value="">
    <input type="hidden" name="packageId" id="packageId" value="">
</form>
</body>
</html>





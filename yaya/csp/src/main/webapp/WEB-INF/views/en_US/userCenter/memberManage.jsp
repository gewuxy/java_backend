<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/13
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>个人中心 - 会员权限</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <%--<link rel="SHORTCUT ICON" href="./images/v2/icon.ico" />--%>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style-EN.css">

    <script src="${ctxStatic}/js/jquery.min.js"></script>
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="${ctxStatic}/js/layer/layer.js"></script>
    <!--[if lt IE 9]>
    <script src="${ctxStatic}/js/html5.js"></script>
    <![endif]-->
</head>
<body >
<div id="wrapper">
    <%@include file="../include/header.jsp" %>
    <div class="admin-content bg-gray" >
        <div class="page-width clearfix">
            <div class="user-module clearfix">
                <div class="row clearfix">
                    <div class="col-lg-4">
                        <%@include file="./left.jsp" %>
                    </div>
                    <div class="col-lg-8">
                        <%@include file="user_include.jsp" %>
                        <div class="user-content user-content-levelHeight item-radius member-mode">
                            <div class="member-mode-header clearfix">
                                <div class="fr">
                                    <div class="resource-label">
                                        <c:if test="${cspPackage.packageUs == 'standardEdition' || cspPackage.packageUs == 'premiumEdition'}" >
                                            <span>

                                                    <c:if test="${cspPackage.usedMeetCount > cspPackage.limitMeets}">
                                                        <i class="hot" style="color: red">
                                                                ${cspPackage.usedMeetCount}
                                                        </i>
                                                    </c:if>
                                                <c:if test="${cspPackage.usedMeetCount <= cspPackage.limitMeets}">
                                                        <i class="hot">
                                                                ${cspPackage.usedMeetCount}
                                                        </i>
                                                </c:if>


                                                <i class="muted">|</i>${cspPackage.limitMeets}</span>
                                        </c:if>
                                        <c:if test="${cspPackage.packageUs == 'professionalEdition'}">
                                            <span><i class="hot">${cspPackage.usedMeetCount}</i><i class="muted">|</i>∞</span>
                                        </c:if>
                                    </div>
                                    <p class="t-center">Number of Meetings</p>
                                </div>
                                <div class="fl">
                                    <div class="clearfix">
                                        <div class="fl"></div>
                                        <div class="oh">
                                            <c:if test="${cspPackage.packageUs == 'standardEdition'}">
                                                <div class="oh">
                                                    <h5 class="title">${cspPackage.packageUs}</h5>
                                                    <div class="member-mode-tips">Valid</div>
                                                </div>
                                            </c:if>
                                            <c:if test="${cspPackage.packageUs == 'premiumEdition' || cspPackage.packageUs == 'professionalEdition' }">
                                                <div class="oh">
                                                    <h5 class="title">${cspPackage.packageUs}</h5>
                                                    <div class="member-mode-tips"><fmt:formatDate value="${cspPackage.packageStart}" type="both" pattern="yyyy-MM-dd"/>至<fmt:formatDate value="${cspPackage.packageEnd}" type="both" pattern="yyyy-MM-dd"/></div>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="member-mode-main">
                                <div class="member-mode-fnList">
                                    <c:if test="${cspPackage.packageUs == 'standardEdition'}">
                                        <ul>
                                            <li>
                                                <p><img src="${ctxStatic}/images/member-icon-01.png" alt=""></p>
                                                <p class="text">Projective<br />Record</p>
                                            </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-02.png" alt=""></p>
                                            <p class="text">Projective <br />Live Stream</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-05.png" alt=""></p>
                                            <p class="text">3 Meetings</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-03-not.png" alt=""></p>
                                            <p class="color-gray-03 text" >Ads Free</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-04-not.png" alt=""></p>
                                            <p class="color-gray-03 text" >Closable <br /> Watermark</p>
                                        </li>
                                        </ul>
                                    </c:if>
                                    <c:if test="${cspPackage.packageUs == 'premiumEdition'}">
                                        <ul>
                                            <li>
                                                <p><img src="${ctxStatic}/images/member-icon-01.png" alt=""></p>
                                                <p class="text">Projective<br />Record</p>
                                            </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-02.png" alt=""></p>
                                            <p class="text">Projective <br />Live Stream</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-06.png" alt=""></p>
                                            <p class="text">10 Meetings</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-03.png" alt=""></p>
                                            <p class="text" >Ads Free</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-04.png" alt=""></p>
                                            <p class="text" >Closable <br /> Watermark</p>
                                        </li>
                                        </ul>
                                    </c:if>
                                    <c:if test="${cspPackage.packageUs == 'professionalEdition'}">
                                        <ul>
                                            <li>
                                                <p><img src="${ctxStatic}/images/member-icon-01.png" alt=""></p>
                                                <p class="text">Projective<br />Record</p>
                                            </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-02.png" alt=""></p>
                                            <p class="text">Projective <br />Live Stream</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-07.png" alt=""></p>
                                            <p class="text">Unlimited</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-03.png" alt=""></p>
                                            <p class="text" >Ads Free</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-04.png" alt=""></p>
                                            <p class="text" >Customized <br /> Watermark</p>
                                        </li>
                                        </ul>
                                    </c:if>
                                </div>
                            </div>
                            <div class="member-mode-footer member-footer-position t-center">
                                <a href="javascript:;" type="button" class="button login-button buttonBlue member-buy-hook">Upgrade / Renew</a>
                                <p><a href="${ctx}/index/17103116063862386794" target="_blank">Need help?</a></p>

                            </div>


                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="admin-bottom">
        <div class="page-width clearfix">
            <p class="t-center">Copyright © 2012-2017 Jingxin Tech. All Rights Reserved.</p>
        </div>

    </div>
</div>

<!--弹出购买会员项-->
<div class="member-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-main member-buy-popup-main">
            <div class="member-buy-header">
                <h6 class="title">Please select the edition you want</h6>
                <div class="member-buy-tabs-menu clearfix">
                    <div class="index-buy-item ">
                        <div class="index-buy-header">
                            <h4>Standard Edition</h4>
                            <h3 class="price">Free</h3>
                        </div>
                        <div class="index-buy-main">
                            <div class="index-buy-info">
                                <p>Permanent Usage</p>
                                <p>3 Meetings</p>
                            </div>
                            <div class="index-buy-text">
                                <ul>
                                    <li class="icon-li-selected">Projective Record</li>
                                    <li class="icon-li-selected">Projective Live Stream</li>
                                    <li class="icon-li-selected">Watermark of CSPmeeting</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="index-buy-item  index-buy-item-current ">
                        <div class="index-buy-header">
                            <h4>Premium Edition</h4>
                            <h3 class="price">2.5 USD</h3>
                        </div>
                        <div class="index-buy-main">
                            <div class="index-buy-info">
                                <p>One-Month Usage</p>
                                <p>10 Meetings</p>
                            </div>
                            <div class="index-buy-text">
                                <ul>
                                    <li class="icon-li-selected">Projective Record</li>
                                    <li class="icon-li-selected">Projective Live Stream</li>
                                    <li class="icon-li-selected">Watermark of Nickname</li>
                                    <li class="icon-li-selected">Ads free</li>
                                </ul>
                            </div>
                        </div>

                    </div>
                    <div class="index-buy-item last">
                        <div class="index-buy-header">
                            <h4>Professional Edition</h4>
                            <h3 class="price">9.9 / 99 USD</h3>
                        </div>
                        <div class="index-buy-main">
                            <div class="index-buy-info">
                                <p>One-Month / One-Year Usage</p>
                                <p>Unlimited Number of Meetings</p>
                            </div>
                            <div class="index-buy-text">
                                <ul>
                                    <li class="icon-li-selected">Projective Record</li>
                                    <li class="icon-li-selected">Projective Live Stream</li>
                                    <li class="icon-li-star">Customized Watermark</li>
                                    <li class="icon-li-selected">Ads free</li>
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
                                <div class="pay-mode-list time-mode-list">
                                    <label for="tid1" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="1" id="tid1">
                                        1 month
                                    </label>
                                    <label for="tid2" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="2" id="tid2">
                                        3 month
                                    </label>
                                    <label for="tid3" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="tid3">
                                        6 month
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow " >
                            <div class="formTitle color-black">Recharge Channel</div>
                            <div class="formControls">
                                <div class="pay-mode-list CN-hook none">
                                    <label for="id11" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="1" id="id11">
                                        <img src="./images/img/user-icon-alipay.png" alt="">
                                    </label>
                                    <label for="id21" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="2" id="id21">
                                        <img src="./images/img/user-icon-wechat.png" alt="">
                                    </label>
                                    <label for="id31" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="id31">
                                        <img src="./images/img/user-icon-unionpay.png" alt="">
                                    </label>
                                </div>
                                <div class="pay-mode-list EN-hook ">
                                    <label for="id5" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="5" id="id5">
                                        <img src="./images/img/user-icon-paypal.png" alt="">
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
                                            <input type="radio" name="currency" id="currency-cn" class="none" value="CN">
                                            CNY
                                        </label>
                                        <label for="currency-en" class="en on">
                                            <input type="radio" name="currency" id="currency-en" class="none" value="EN">
                                            USD
                                        </label>
                                    </span>
                            </div>
                        </div>
                        <div class="formrow t-center last">
                            <input href="#" type="button" class="button login-button buttonBlue cancel-hook last" value="Try Now" style="position: relative; z-index:3;">
                        </div>
                        <div class="member-buy-disabled-item"></div>
                    </div>
                </div>
                <div class="member-buy-content none">
                    <div class="user-content item-radius pay-mode">
                        <div class="formrow">
                            <div class="formTitle color-black">Valid Through</div>
                            <div class="formControls">
                                <div class="pay-mode-list time-mode-list">
                                    <label for="2tid1" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="1" id="2tid1">
                                        1 month
                                    </label>
                                    <label for="2tid2" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="2" id="2tid2">
                                        3 month
                                    </label>
                                    <label for="2tid3" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="2tid3">
                                        6 month
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow " >
                            <div class="formTitle color-black">Recharge Channel</div>
                            <div class="formControls">
                                <div class="pay-mode-list CN-hook none">
                                    <label for="2id11" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="1" id="2id11">
                                        <img src="./images/img/user-icon-alipay.png" alt="">
                                    </label>
                                    <label for="2id21" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="2" id="2id21">
                                        <img src="./images/img/user-icon-wechat.png" alt="">
                                    </label>
                                    <label for="2id31" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="2id31">
                                        <img src="./images/img/user-icon-unionpay.png" alt="">
                                    </label>
                                </div>
                                <div class="pay-mode-list EN-hook ">
                                    <label for="2id5" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="5" id="2id5">
                                        <img src="./images/img/user-icon-paypal.png" alt="">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow money">
                            <div class="formTitle color-black">Amount</div>
                            <div class="formControls">
                                <span class="payNum">0.00</span>
                                <span class="money-state">
                                        <label for="currency-cn2" class="cn ">
                                            <input type="radio" name="currency" id="currency-cn2" class="none" value="CN">
                                            CNY
                                        </label>
                                        <label for="currency-en2" class="en on">
                                            <input type="radio" name="currency" id="currency-en2" class="none" value="EN">
                                            USD
                                        </label>
                                    </span>
                            </div>
                        </div>
                        <div class="formrow t-center last">
                            <!--返回按钮-->
                            <!--<a href="javascript:;" class="button login-button layui-layer-close">Cancel</a>-->
                            <input href="#" type="button" class="button login-button buttonBlue cancel-hook last" value="Confirm Payment">
                        </div>
                    </div>
                </div>
                <div class="member-buy-content none">
                    <div class="user-content item-radius pay-mode">
                        <div class="formrow">
                            <div class="formTitle color-black">Valid Through</div>
                            <div class="formControls">
                                <div class="pay-mode-list time-mode-list">
                                    <label for="3tid1" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="1" id="3tid1" >
                                        1 month
                                    </label>
                                    <label for="3tid2" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="2" id="3tid2">
                                        3 month
                                    </label>
                                    <label for="3tid3" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="3tid3">
                                        6 month
                                    </label>
                                    <label for="3tid4" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="1" id="3tid4">
                                        1 year
                                    </label>
                                    <label for="3tid5" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="2" id="3tid5">
                                        2 year
                                    </label>
                                    <label for="3tid6" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="3tid6">
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
                                        <input type="radio" name="payMode" class="none" value="1" id="3id11">
                                        <img src="./images/img/user-icon-alipay.png" alt="">
                                    </label>
                                    <label for="3id21" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="2" id="3id21">
                                        <img src="./images/img/user-icon-wechat.png" alt="">
                                    </label>
                                    <label for="3id31" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="3id31">
                                        <img src="./images/img/user-icon-unionpay.png" alt="">
                                    </label>
                                </div>
                                <div class="pay-mode-list EN-hook ">
                                    <label for="3id5" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="5" id="3id5">
                                        <img src="./images/img/user-icon-paypal.png" alt="">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow money">
                            <div class="formTitle color-black">Amount</div>
                            <div class="formControls">
                                <span class="payNum">0.00</span>
                                <span class="money-state">
                                        <label for="currency-cn3" class="cn ">
                                            <input type="radio" name="currency" id="currency-cn3" class="none" value="CN">
                                            CNY
                                        </label>
                                        <label for="currency-en3" class="en on">
                                            <input type="radio" name="currency" id="currency-en3" class="none" value="EN">
                                            USD
                                        </label>
                                    </span>
                            </div>
                        </div>
                        <div class="formrow t-center last">
                            <!--返回按钮-->
                            <!--<a href="javascript:;" class="button login-button layui-layer-close">Cancel</a>-->
                            <input href="#" type="button" class="button login-button buttonBlue cancel-hook last" value="Confirm Payment">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(function(){
        $("#config_6").parent().attr("class","cur");
        //缓存tabs内容区
        var tabsMainNum = $(".member-buy-tabs-main").find('.member-buy-content');


        //弹出购买套餐
        $('.member-buy-hook').on('click',function(){
            layer.open({
                type: 1,
                area: ['1116px', '935px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                skin: 'member-popup-zIndex',
                offset: '70px',
                content: $('.member-popup-box'),
                success:function(layero, index){

                },
                cancel :function(){

                },
            });
        });
        //选择充值方式
        $(".pay-mode-list label").click(function(){
            $(this).addClass('pay-on').siblings().removeClass('pay-on');
            console.log($('input[name="payMode"]:checked').val());
        });
        //货币切换
        $(".money-state label").click(function(){
            $(this).addClass('on').siblings().removeClass('on');
            var currencyValue = $(this).parents('.pay-mode').find('input[name="currency"]:checked').val()
            if( currencyValue == 'CN'){
                $(this).parents('.pay-mode').find('.CN-hook').removeClass('none').siblings().addClass('none');
            } else if ( currencyValue =='EN' ){
                $(this).parents('.pay-mode').find('.EN-hook').removeClass('none').siblings().addClass('none');
            }
        });

        //初始化高级版选中
        tabsMainNum.eq(1).removeClass('none').siblings().addClass('none');

        //选择不同版本
        $(".member-buy-tabs-menu").find('.index-buy-item').on('click',function(){
            var index = $(this).index();
            tabsMainNum.eq(index).removeClass('none').siblings().addClass('none');
            $(this).addClass('index-buy-item-current').siblings().removeClass('index-buy-item-current');
        });



    })
</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: jianliang
  Date: 2017/12/11
  Time: 14:07
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
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">

    <script src="${ctxStatic}/js/jquery.min.js"></script>
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="${ctxStatic}/js/layer/layer.js"></script>
    <!--[if lt IE 9]>
    <script src="${ctxStatic}/js/html5.js"></script>
    <![endif]-->
</head>
<script>
    $("#tid1").click(function () {
        var tid1Val = $('input:radio:checked').val();
        alert(tid1Val);
    })
    $("#tid2").click(function () {
        var tid2Val = $('input:radio:checked').val();
        alert(tid2Val);
    })
</script>
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
                        <div class="user-menu clearfix item-radius">
                            <ul>
                                <li class="first"><a href="${ctx}/mgr/user/info">我的信息</a></li>
                                <li><a href="${ctx}/mgr/user/toAccount">账号管理</a></li>
                                <li><a href="${ctx}/mgr/user/toFlux">流量管理</a></li>
                                <li class="cur"><a href="${ctx}/mgr/user/memberManage">会员管理</a></li>
                                <li class="last"><a href="${ctx}/mgr/user/toReset">修改密码</a></li>

                            </ul>
                        </div>
                        <div class="user-content user-content-levelHeight item-radius member-mode">
                            <div class="member-mode-header clearfix">
                                <div class="fr">
                                    <div class="resource-label">
                                        <c:if test="${cspPackage.packageCn == '标准版' || cspPackage.packageCn == '高级版'}" >
                                            <span>

                                                    <c:if test="${cspPackage.usedMeetCount > cspPackage.limitMeets}">
                                                        <i class="hot" style="color: red">
                                                                ${cspPackage.usedMeetCount}
                                                            </i>
                                                    </c:if>
                                                    <i class="hot">
                                                            ${cspPackage.usedMeetCount}
                                                    </i>

                                                <i class="muted">|</i>${cspPackage.limitMeets}</span>
                                        </c:if>
                                        <c:if test="${cspPackage.packageCn == '专业版'}">
                                            <span><i class="hot">${cspPackage.usedMeetCount}</i><i class="muted">|</i>∞</span>
                                        </c:if>
                                    </div>
                                    <p class="t-center">会议数量</p>
                                </div>
                                <div class="fl">
                                    <div class="clearfix">
                                        <div class="fl"></div>

                                            <c:if test="${cspPackage.packageCn == '标准版'}">
                                        <div class="oh">
                                                <h5 class="title">${cspPackage.packageCn}</h5>
                                                <div class="member-mode-tips">已生效</div>
                                        </div>
                                            </c:if>
                                            <c:if test="${cspPackage.packageCn == '高级版' || cspPackage.packageCn == '专业版' }">
                                                <div class="fl member-grade"><img src="${ctxStatic}/images/member-icon-grade-01.png" alt=""></div>
                                                    <div class="oh">
                                                        <h5 class="title">${cspPackage.packageCn}</h5>
                                                        <div class="member-mode-tips"><fmt:formatDate value="${cspPackage.packageStart}" type="both" pattern="yyyy-MM-dd"/>至<fmt:formatDate value="${cspPackage.packageEnd}" type="both" pattern="yyyy-MM-dd"/></div>
                                                    </div>
                                            </c:if>

                                    </div>
                                </div>
                            </div>
                            <div class="member-mode-main">
                                <div class="member-mode-fnList">
                                    <c:if test="${cspPackage.packageCn == '标准版'}">
                                    <ul>
                                        <li>
                                            <p><img src="${ctxStatic}/images/member-icon-01.png" alt=""></p>
                                            <p>投屏录播</p>
                                        </li><li>
                                        <p><img src="${ctxStatic}/images/member-icon-02.png" alt=""></p>
                                        <p>投屏直播</p>
                                    </li><li>
                                        <p><img src="${ctxStatic}/images/member-icon-05.png" alt=""></p>
                                        <p>3个会议</p>
                                    </li><li>
                                        <p><img src="${ctxStatic}/images/member-icon-03-not.png" alt=""></p>
                                        <p class="color-gray-03">无广告</p>
                                    </li><li>
                                        <p><img src="${ctxStatic}/images/member-icon-04-not.png" alt=""></p>
                                        <p class="color-gray-03">可关闭水印</p>
                                    </li>
                                    </ul>
                                    </c:if>
                                    <c:if test="${cspPackage.packageCn == '高级版'}">
                                        <ul>
                                            <li>
                                                <p><img src="${ctxStatic}/images/member-icon-01.png" alt=""></p>
                                                <p>投屏录播</p>
                                            </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-02.png" alt=""></p>
                                            <p>投屏直播</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-06.png" alt=""></p>
                                            <p>10个会议</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-03.png" alt=""></p>
                                            <p>无广告</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-04-not.png" alt=""></p>
                                            <p class="color-gray-03">可关闭水印</p>
                                        </li>
                                        </ul>
                                    </c:if>
                                    <c:if test="${cspPackage.packageCn == '专业版'}">
                                        <ul>
                                            <li>
                                                <p><img src="${ctxStatic}/images/member-icon-01.png" alt=""></p>
                                                <p>投屏录播</p>
                                            </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-02.png" alt=""></p>
                                            <p>投屏直播</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-07.png" alt=""></p>
                                            <p>无限会议</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-03.png" alt=""></p>
                                            <p>无广告</p>
                                        </li><li>
                                            <p><img src="${ctxStatic}/images/member-icon-04.png" alt=""></p>
                                            <p>可关闭水印</p>
                                        </li>
                                        </ul>
                                    </c:if>
                                </div>
                            </div>
                            <div class="member-mode-footer member-footer-position t-center">
                                <a href="javascript:;" type="button" class="button login-button buttonBlue member-buy-hook">升级续费</a>
                                <p><a href="${ctx}/index/17103116063862386794" target="_blank">有疑问，帮助中心</a></p>

                            </div>


                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="admin-bottom">
        <div class="page-width clearfix">
            <p class="t-center">粤ICP备12087993号 © 2012-2017 敬信科技 版权所有 </p>
        </div>

    </div>
</div>

<!--弹出购买会员项-->
<div class="member-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-main member-buy-popup-main">
            <div class="member-buy-header">
                <h6 class="title">请选择您购买的套餐</h6>
                <div class="member-buy-tabs-menu clearfix">
                    <div class="index-buy-item ">
                        <div class="index-buy-header">
                            <h4>标准版</h4>
                            <h3 class="price">免费</h3>
                        </div>
                        <div class="index-buy-main">
                            <div class="index-buy-info">
                                <p>不限时长</p>
                                <p>3个会议</p>
                            </div>
                            <div class="index-buy-text">
                                <ul>
                                    <li class="icon-li-selected">启用投屏录播</li>
                                    <li class="icon-li-selected">启用投屏直播</li>
                                    <li class="icon-li-selected">会讲水印</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="index-buy-item  index-buy-item-current ">
                        <div class="index-buy-header">
                            <h4>高级版</h4>
                            <h3 class="price">16.67元</h3>
                        </div>
                        <div class="index-buy-main">
                            <div class="index-buy-info">
                                <p>1个月有效</p>
                                <p>10个会议</p>
                            </div>
                            <div class="index-buy-text">
                                <ul>
                                    <li class="icon-li-selected">启用投屏录播</li>
                                    <li class="icon-li-selected">启用投屏直播</li>
                                    <li class="icon-li-selected">昵称水印</li>
                                    <li class="icon-li-selected">无广告</li>
                                </ul>
                            </div>
                        </div>

                    </div>
                    <div class="index-buy-item last">
                        <div class="index-buy-header">
                            <h4>专业版</h4>
                            <h3 class="price">66元/660元</h3>
                        </div>
                        <div class="index-buy-main">
                            <div class="index-buy-info">
                                <p>1个月/1年有效</p>
                                <p>不限会议</p>
                            </div>
                            <div class="index-buy-text">
                                <ul>
                                    <li class="icon-li-selected">启用投屏录播</li>
                                    <li class="icon-li-selected">启用投屏直播</li>
                                    <li class="icon-li-star">自定义水印</li>
                                    <li class="icon-li-selected">无广告</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="member-buy-tabs-main">

                <%--标准版--%>
                <div class="member-buy-content">
                    <div class="user-content item-radius pay-mode member-buy-disabled">
                        <div class="formrow">
                            <div class="formTitle color-black">使用时长</div>
                            <div class="formControls">
                                <div class="pay-mode-list time-mode-list">
                                    <label for="tid1" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="1" id="tid1">
                                        1个月
                                    </label>
                                    <label for="tid2" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="tid2">
                                        3个月
                                    </label>
                                    <label for="tid3" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="6" id="tid3">
                                        6个月
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow " >
                            <div class="formTitle color-black">充值方式</div>
                            <div class="formControls">
                                <div class="pay-mode-list CN-hook">
                                    <label for="id11" class="item item-radius pay-on">
                                        <input type="radio" name="platForm" class="none" value="alipay" id="id11">
                                        <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                    </label>
                                    <label for="id21" class="item item-radius">
                                        <input type="radio" name="platForm" class="none" value="wechat" id="id21">
                                        <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                    </label>
                                    <label for="id31" class="item item-radius">
                                        <input type="radio" name="platForm" class="none" value="unionpay" id="id31">
                                        <img src="${ctxStatic}/images/img/user-icon-unionpay.png" alt="">
                                    </label>
                                </div>
                                <div class="pay-mode-list EN-hook none">
                                    <label for="id5" class="item item-radius pay-on">
                                        <input type="radio" name="platForm" class="none" value="paypal" id="id5">
                                        <img src="${ctxStatic}/images/img/user-icon-paypal.png" alt="">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow money">
                            <div class="formTitle color-black">支付金额</div>
                            <div class="formControls">
                                <span class="payNum">0.00</span>
                                <script>

                                </script>
                                <span class="money-state">
                                        <label for="currency-cn" class="cn on">
                                            <input type="radio" name="currencyType" id="currency-cn" class="none" value="0">
                                            CNY
                                        </label>
                                        <label for="currency-en" class="en">
                                            <input type="radio" name="currencyType" id="currency-en" class="none" value="1">
                                            USD
                                        </label>
                                    </span>
                            </div>
                        </div>
                        <div class="formrow t-center last">
                            <input href="#" type="button" id="freeButton" class="button login-button buttonBlue cancel-hook last" value="免费体验" style="position: relative; z-index:3;">
                            <script>
                                $("#freeButton").click(function () {
                                    location.href="${ctx}/mgr/user/memberManage";
                                })
                            </script>
                        </div>
                        <div class="member-buy-disabled-item"></div>
                    </div>
                </div>


                <%--高级版--%>

                <div class="member-buy-content none">
                    <div class="user-content item-radius pay-mode">
                        <form action="${ctx}/mgr/pay/premiumPay" method="post">
                        <div class="formrow">
                            <div class="formTitle color-black">使用时长</div>
                            <div class="formControls">
                                <div class="pay-mode-list time-mode-list" id="payModeSelectId">
                                    <label for="2tid1" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="1" id="2tid1" checked="checked">
                                        1个月
                                    </label>
                                    <label for="2tid2" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="2tid2">
                                        3个月
                                    </label>
                                    <label for="2tid3" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="6" id="2tid3">
                                        6个月
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow " >
                            <div class="formTitle color-black">充值方式</div>
                            <div class="formControls">
                                <div class="pay-mode-list CN-hook" id="changePayMode">
                                    <label for="2id11" class="item item-radius pay-on">
                                        <input type="radio" name="platForm" class="none" value="alipay" id="2id11" checked="checked">
                                        <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                    </label>
                                    <label for="2id21" class="item item-radius">
                                        <input type="radio" name="platForm" class="none" value="wechat" id="2id21">
                                        <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                    </label>
                                    <label for="2id31" class="item item-radius">
                                        <input type="radio" name="platForm" class="none" value="unionpay" id="2id31">
                                        <img src="${ctxStatic}/images/img/user-icon-unionpay.png" alt="">
                                    </label>
                                </div>

                                <div class="pay-mode-list EN-hook none">
                                    <label for="2id5" class="item item-radius pay-on">
                                        <input type="radio" name="platForm" class="none" value="paypal" id="2id5">
                                        <img src="${ctxStatic}/images/img/user-icon-paypal.png" alt="">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow money">
                            <div class="formTitle color-black">支付金额</div>
                            <div class="formControls">
                                <input value="" name="shouldPay" id="shouldPay" type="hidden">
                                <span class="payNum" id="money">
                                    16.67</span>
                                <span class="money-state">
                                        <label for="currency-cn2" class="cn on">
                                            <input type="radio" name="currencyType" id="currency-cn2" class="none" value="0" checked="checked">
                                            CNY
                                        </label>
                                        <label for="currency-en2" class="en">
                                            <input type="radio" name="currencyType" id="currency-en2" class="none" value="1">
                                            USD
                                        </label>
                                    </span>
                            </div>
                        </div>
                        <div class="formrow t-center last">
                            <!--返回按钮-->
                            <%--<a href="javascript:;" class="button login-button layui-layer-close">取消</a>--%>
                            <input type="submit" class="button login-button buttonBlue cancel-hook last" value="确认支付" id="payBtn">
                            <script>
                                $(document).ready(function(){
                                    var moneyVal = document.getElementById("money").innerText;
                                    var payMode = $('input[name="payMode"]:checked').val();
                                    $("#shouldPay").val((moneyVal*payMode).toFixed(2));
                                    $("#payModeSelectId").click(function () {
                                        var payMode = $('input[name="payMode"]:checked').val();
                                         $("#money").html((moneyVal*payMode).toFixed(2));
                                    })
                                });
                            </script>
                        </div>
                        </form>
                    </div>
                </div>


                <%--专业版--%>
                <div class="member-buy-content none">
                    <div class="user-content item-radius pay-mode">
                        <form action="">
                        <div class="formrow">
                            <div class="formTitle color-black">使用时长</div>
                            <div class="formControls">
                                <div class="pay-mode-list time-mode-list">
                                    <label for="3tid1" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="1" id="3tid1" >
                                        1个月
                                    </label>
                                    <label for="3tid2" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="2" id="3tid2">
                                        3个月
                                    </label>
                                    <label for="3tid3" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="3tid3">
                                        6个月
                                    </label>
                                    <label for="3tid4" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="12" id="3tid4">
                                        1年
                                    </label>
                                    <label for="3tid5" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="24" id="3tid5">
                                        2年
                                    </label>
                                    <label for="3tid6" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="36" id="3tid6">
                                        3年
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow " >
                            <div class="formTitle color-black">充值方式</div>
                            <div class="formControls">
                                <div class="pay-mode-list CN-hook">
                                    <label for="3id11" class="item item-radius pay-on">
                                        <input type="radio" name="platForm" class="none" value="alipay" id="3id11">
                                        <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                    </label>
                                    <label for="3id21" class="item item-radius">
                                        <input type="radio" name="platForm" class="none" value="wechat" id="3id21">
                                        <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                    </label>
                                    <label for="3id31" class="item item-radius">
                                        <input type="radio" name="platForm" class="none" value="unionpay" id="3id31">
                                        <img src="${ctxStatic}/images/img/user-icon-unionpay.png" alt="">
                                    </label>
                                </div>
                                <div class="pay-mode-list EN-hook none">
                                    <label for="3id5" class="item item-radius pay-on">
                                        <input type="radio" name="platForm" class="none" value="5" id="3id5">
                                        <img src="${ctxStatic}/images/img/user-icon-paypal.png" alt="">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow money">
                            <div class="formTitle color-black">支付金额</div>
                            <div class="formControls">
                                <span class="payNum">0.00</span>
                                <span class="money-state">
                                        <label for="currency-cn3" class="cn on">
                                            <input type="radio" name="currencyType" id="currency-cn3" class="none" value="0">
                                            CNY
                                        </label>
                                        <label for="currency-en3" class="en">
                                            <input type="radio" name="currencyType" id="currency-en3" class="none" value="1">
                                            USD
                                        </label>
                                    </span>
                            </div>
                        </div>
                        <div class="formrow t-center last">
                            <!--返回按钮-->
                            <!--<a href="javascript:;" class="button login-button layui-layer-close">取消</a>-->
                            <input href="#" type="button" class="button login-button buttonBlue cancel-hook last" value="确认支付">
                        </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    $(function(){
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
            console.log($('input[name="platForm"]:checked').val());
        });
        //货币切换
        $(".money-state label").click(function(){
            $(this).addClass('on').siblings().removeClass('on');
            var currencyValue = $(this).parents('.pay-mode').find('input[name="currency"]:checked').val();
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

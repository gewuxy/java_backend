<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <div class="member-buy-content">
                    <div class="user-content item-radius pay-mode member-buy-disabled">
                        <div class="formrow">
                            <div class="formTitle color-black">购买时长</div>
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
                            <div class="formTitle color-black">支付金额</div>
                            <div class="formControls">
                                <span class="payNum">0.00</span>
                                <span class="money-state">
                                        <label for="currency-cn" class="cn on">
                                            <input type="radio" name="currency" id="currency-cn" class="none" value="CN">
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
                            <input href="#" type="button" class="button login-button buttonBlue cancel-hook last" value="免费体验" style="position: relative; z-index:3;">
                        </div>
                        <div class="member-buy-disabled-item"></div>
                    </div>
                </div>
                <div class="member-buy-content none">
                    <div class="user-content item-radius pay-mode">
                        <div class="formrow">
                            <div class="formTitle color-black">购买时长</div>
                            <div class="formControls">
                                <div class="pay-mode-list time-mode-list">
                                    <label for="2tid1" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="1" id="2tid1">
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
                                <div class="pay-mode-list CN-hook">
                                    <label for="2id11" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="1" id="2id11">
                                        <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                    </label>
                                    <label for="2id21" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="2" id="2id21">
                                        <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                    </label>
                                    <label for="2id31" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="2id31">
                                        <img src="${ctxStatic}/images/img/user-icon-unionpay.png" alt="">
                                    </label>
                                </div>
                                <div class="pay-mode-list EN-hook none">
                                    <label for="2id4" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="4" id="2id4">
                                        <img src="${ctxStatic}/images/img/user-icon-visa.png" alt="">
                                    </label>
                                    <label for="2id5" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="5" id="2id5">
                                        <img src="${ctxStatic}/images/img/user-icon-paypal.png" alt="">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow money">
                            <div class="formTitle color-black">支付金额</div>
                            <div class="formControls">
                                <span class="payNum" id="hgTotal"></span>
                                <span class="money-state">
                                        <label for="currency-cn2" class="cn on">
                                            <input type="radio" name="currency" id="currency-cn2" class="none" value="CN">
                                            CNY
                                        </label>
                                        <label for="currency-en2" class="en">
                                            <input type="radio" name="currency" id="currency-en2" class="none" value="EN">
                                            USD
                                        </label>
                                    </span>
                            </div>
                        </div>
                        <div class="formrow t-center last">
                            <input href="#" type="button" class="button login-button buttonBlue cancel-hook last" value="确认支付">
                        </div>
                    </div>
                </div>
                <div class="member-buy-content none">
                    <div class="user-content item-radius pay-mode">
                        <div class="formrow">
                            <div class="formTitle color-black">购买时长</div>
                            <div class="formControls">
                                <div class="pay-mode-list time-mode-list">
                                    <label for="3tid1" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="1" id="3tid1" >
                                        1个月
                                    </label>
                                    <label for="3tid2" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="3tid2">
                                        3个月
                                    </label>
                                    <label for="3tid3" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="6" id="3tid3">
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
                                        <input type="radio" name="payMode" class="none" value="1" id="3id11">
                                        <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                    </label>
                                    <label for="3id21" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="2" id="3id21">
                                        <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                    </label>
                                    <label for="3id31" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="3" id="3id31">
                                        <img src="${ctxStatic}/images/img/user-icon-unionpay.png" alt="">
                                    </label>
                                </div>
                                <div class="pay-mode-list EN-hook none">
                                    <label for="3id4" class="item item-radius pay-on">
                                        <input type="radio" name="payMode" class="none" value="4" id="3id4">
                                        <img src="${ctxStatic}/images/img/user-icon-visa.png" alt="">
                                    </label>
                                    <label for="3id5" class="item item-radius">
                                        <input type="radio" name="payMode" class="none" value="5" id="3id5">
                                        <img src="${ctxStatic}/images/img/user-icon-paypal.png" alt="">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="formrow money">
                            <div class="formTitle color-black">支付金额</div>
                            <div class="formControls">
                                <span class="payNum" id="pfTotal">0.00</span>
                                <span class="money-state">
                                        <label for="currency-cn3" class="cn on">
                                            <input type="radio" name="currency" id="currency-cn3" class="none" value="CN">
                                            CNY
                                        </label>
                                        <label for="currency-en3" class="en">
                                            <input type="radio" name="currency" id="currency-en3" class="none" value="EN">
                                            USD
                                        </label>
                                    </span>
                            </div>
                        </div>
                        <div class="formrow t-center last">
                            <input href="#" type="button" class="button login-button buttonBlue cancel-hook last" value="确认支付">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

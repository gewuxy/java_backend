<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/31
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>會講-全球領先的雲同步回放演講系統</title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
    <script src="${ctxStatic}/js/animate.min.js"></script>
</head>
<body>
<div id="wrapper" class="index-box">
    <div class="header">
        <div class="page-width pr">
            <div class=" index-login">
                <div class="fr clearfix">
                    <%@include file="/WEB-INF/include/switch_language.jsp"%>&nbsp;&nbsp;&nbsp;
                    <a href="${ctx}/login" class="user-login-button"><strong>${not empty username ? username : "登錄"}</strong>&nbsp;&nbsp;<i></i> </a>
                    <a href="javascript:;" class="index-download index-qrcode">下載App<span class="qrcode-01 qrcode-top"><img src="${ctxStatic}/upload/img/qrcode.png" alt=""></span></a>

                </div>
            </div>
        </div>
        <div class="banner index-banner-img">
            <div class="index-banner-content not-animated" data-animate="fadeIn" data-delay="200">
                <h3 class="fontStyleSubHead not-animated" data-animate="fadeInDown" data-delay="300"><div class="img">
                    <img src="${ctxStatic}/images/index-logo.png" alt=""></div></h3>
                <h4 class="not-animated" data-animate="fadeInDown" data-delay="300">用手機講PPT，隨講隨錄隨分享！</h4>
                <h2 class="not-animated" data-animate="fadeIn" data-delay="500">妳的智慧，將被更多人看見</h2>
                <div class="index-buy-button not-animated" data-animate="fadeInUp" data-delay="400">
                    <a href="${ctx}/login" class="button item-radius">免費體驗</a>
                </div>
            </div>

        </div>

    </div>
    <div class="content">

        <div class="module-section ">

            <div class="module-section-inner">
                <div class="page-width">
                    <div class="module-section-title-wrapper clearfix">
                        <div class="module-section-title  clearfix">
                            <h2>功能特性</h2>
                            <h3>Features</h3>
                        </div>
                    </div>
                    <div class="module-section-content ">
                        <div class="container-fluid">
                            <div class="index-functional clearfix">
                                <div class="row shadow-layer-top">


                                    <div class="col-lg-4">
                                        <div class="functional-item">
                                            <div class="functional-item-bg"></div>
                                            <div class="functional-icon-item">
                                                <div class="functional-icon-bg">
                                                    <span class="functional-icon functional-icon-01"></span>
                                                </div>
                                            </div>
                                            <h3 class="title">輕設備</h3>
                                            <div class="main">
                                                <p>同步完成會議主持、錄制、發布，只要壹部手機+會講 App
                                                </p>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-4">
                                        <div class="functional-item">
                                            <div class="functional-item-bg"></div>
                                            <div class="functional-icon-item">
                                                <div class="functional-icon-bg">
                                                    <span class="functional-icon functional-icon-02"></span>
                                                </div>
                                            </div>
                                            <h3 class="title">多場景智能會議模式</h3>
                                            <div class="main">
                                                <p>多種開會場景，錄播/直播、語音/視頻，任意切換</p>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-4">
                                        <div class="functional-item">
                                            <div class="functional-item-bg"></div>
                                            <div class="functional-icon-item">
                                                <div class="functional-icon-bg">
                                                    <span class="functional-icon functional-icon-03"></span>
                                                </div>
                                            </div>
                                            <h3 class="title">實時雲同步</h3>
                                            <div class="main">
                                                <p>會議現場投屏直播，雲端實時傳輸，即錄即存即播</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-lg-4">
                                        <div class="functional-item">
                                            <div class="functional-item-bg"></div>
                                            <div class="functional-icon-item">
                                                <div class="functional-icon-bg">
                                                    <span class="functional-icon functional-icon-04"></span>
                                                </div>
                                            </div>
                                            <h3 class="title">直播同步回放</h3>
                                            <div class="main">
                                                <p>不用等到結束，會講支持在直播時對剛剛錯過的內容進行立即回看</p>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-4">
                                        <div class="functional-item">
                                            <div class="functional-item-bg"></div>
                                            <div class="functional-icon-item">
                                                <div class="functional-icon-bg">
                                                    <span class="functional-icon functional-icon-05"></span>
                                                </div>
                                            </div>
                                            <h3 class="title">音視頻優化</h3>
                                            <div class="main">
                                                <p>高品質編解碼算法，智能回聲消除、自動增益控制、自動舒適噪聲……確保會議高質量</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-4">
                                        <div class="functional-item ">
                                            <div class="functional-item-bg"></div>
                                            <div class="functional-icon-item">
                                                <div class="functional-icon-bg">
                                                    <span class="functional-icon functional-icon-06"></span>
                                                </div>
                                            </div>
                                            <h3 class="title">壹鍵分享</h3>
                                            <div class="main">
                                                <p>鏈接全球主流社交平臺Wechat / Facebook / Twitter，壹鍵輕松分享，擴大影響，打造個人品牌</p>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="module-section module-section-gray">

            <div class="module-section-inner">
                <div class="page-width">
                    <div class="module-section-title-wrapper clearfix">
                        <div class="module-section-title clearfix">
                            <h2>應用場景</h2>
                            <h3>Application Scenarios</h3>
                        </div>
                    </div>
                    <div class="module-section-content">
                        <div class="index-application clearfix">
                            <ul>
                                <li>
                                    <div class="application-item clearfix" >
                                        <div class="clearfix">
                                            <div class="application-img">
                                                <img src="${ctxStatic}/images/img/apply-img-01.png" alt="">
                                            </div>
                                            <div class="application-hover-text">
                                                <h4><span>個人學術交流</span></h4>
                                                <p><span>輕輕松松錄制、分享，多場景會議模式任妳選擇，</span></p>
                                                <p><span>傳播學術見解，樹立個人品牌。</span></p>
                                            </div>
                                            <h3>個人學術交流</h3>
                                        </div>
                                        <div class="area-shade"></div>
                                    </div>
                                </li>
                                <li class="last">
                                    <div class="application-item clearfix " >
                                        <div class="clearfix">
                                            <div class="application-img">
                                                <img src="${ctxStatic}/images/img/apply-img-02.png" alt="">
                                            </div>
                                            <div class="application-hover-text">
                                                <h4><span>教學培訓</span></h4>
                                                <p><span>用手機講PPT，教學內容隨堂錄制，壹鍵轉發，</span></p>
                                                <p><span>學生隨時查看，滿足教學單位高質量授課需求。</span></p>
                                            </div>
                                            <h3>教學培訓</h3>
                                        </div>
                                        <div class="area-shade"></div>
                                    </div>
                                </li>
                                <li>
                                    <div class="application-item clearfix">
                                        <div class="clearfix">
                                            <div class="application-img">
                                                <img src="${ctxStatic}/images/img/apply-img-03.png" alt="">
                                            </div>
                                            <div class="application-hover-text">
                                                <h4><span>企業會議</span></h4>
                                                <p><span>內部培訓、多方會議，不限人次，實時傳達，</span></p>
                                                <p><span>支持直播+互動，讓溝通立體、高效。</span></p>
                                            </div>
                                            <h3>企業會議</h3>
                                        </div>
                                        <div class="area-shade"></div>
                                    </div>
                                </li>
                                <li class="last">
                                    <div class="application-item clearfix" >
                                        <div class="fl">
                                            <div class="application-img">
                                                <img src="${ctxStatic}/images/img/apply-img-04.png" alt="">
                                            </div>
                                            <div class="application-hover-text">
                                                <h4><span>大型直播</span></h4>
                                                <p><span>以更少的流量成本支撐更加流暢的播出體驗，</span></p>
                                                <p><span>大型直播更加身臨其境，更受用戶歡迎。</span></p>
                                            </div>
                                            <h3>大型直播</h3>
                                        </div>
                                        <div class="area-shade"></div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="module-section" style="background:#ffffff url(${ctxStatic}/images/index-module-bg-01.jpg) no-repeat center top;">
            <div class="module-section-inner">

                <div class="page-width">
                    <div class="module-section-title-wrapper clearfix">
                        <div class="module-section-title  clearfix">
                            <h2>產品優勢</h2>
                            <h3>Our Advantages</h3>
                        </div>
                    </div>
                    <div class="module-section-content">
                        <div class="row">
                            <div class="col-lg-4" >
                                <h3 style="line-height: 1.4; margin-bottom:20px; font-size: 26px; font-weight: normal;" class="color-blue">更實時</h3>
                                <p style="font-size:16px; padding-right:50px;">CSP通過App與Web雲技術的創新結合，
                                    突破傳統直播在設備、帶寬、進程方面的限制，
                                    真正實現實時雲端同步，
                                    可在直播當時進行內容回看。
                                </p>
                            </div>
                            <div class="col-lg-4" >
                                <h3 style="line-height: 1.4; margin-bottom:20px; font-size: 26px; font-weight: normal;" class="color-blue">更節省</h3>
                                <p style="font-size:16px;  padding-right:50px;">領先的流媒體處理技術及“PPT+音頻”的呈現形式，
                                    在保持會議高畫質、
                                    低噪音的同時，
                                    比同類視頻直播節省至少80%的流量。
                                </p>
                            </div>
                            <div class="col-lg-4" >
                                <h3 style="line-height: 1.4; margin-bottom:20px;font-size: 26px; font-weight: normal;" class="color-blue">更便捷</h3>
                                <p style="font-size:16px;  padding-right:30px;">多場景會議模式，
                                    多終端支持分享， 壹鍵傳播，
                                    快速提升影響力。
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>




        <div class="module-section">
            <div class="module-section-inner">

                <div class="page-width">
                    <div class="module-section-title-wrapper clearfix">
                        <div class="module-section-title  clearfix">
                            <h2>產品售價</h2>
                            <h3>Price</h3>
                        </div>
                    </div>
                    <div class="module-section-content index-buy clearfix">
                        <div class="index-buy-item  ">
                            <div class="index-buy-header">
                                <h4>標準版</h4>
                                <h3 class="price">免費</h3>
                            </div>
                            <div class="index-buy-main">
                                <div class="index-buy-info">
                                    <p>1個月有效</p>
                                    <p>3個會議</p>
                                </div>
                                <div class="index-buy-text">
                                    <ul>
                                        <li class="icon-li-selected">啟用投屏錄播</li>
                                        <li class="icon-li-close">啟用投屏直播</li>
                                        <li class="icon-li-selected">會講水印</li>
                                        <li class="icon-li-selected">廣告接入</li>
                                    </ul>
                                </div>
                                <div class="index-button">
                                    <a href="javascript:;" class="button ">敬請期待</a>
                                </div>
                            </div>
                        </div>
                        <div class="index-buy-item  ">
                            <div class="index-buy-header">
                                <h4>高級版</h4>
                                <h3 class="price">16.67元</h3>
                            </div>
                            <div class="index-buy-main">
                                <div class="index-buy-info">
                                    <p>1個月有效</p>
                                    <p>10個會議</p>
                                </div>
                                <div class="index-buy-text">
                                    <ul>
                                        <li class="icon-li-selected">啟用投屏錄播</li>
                                        <li class="icon-li-selected">啟用投屏直播</li>
                                        <li class="icon-li-selected">會講水印</li>
                                        <li class="icon-li-close">廣告接入</li>
                                    </ul>
                                </div>
                                <div class="index-button">
                                    <a href="javascript:;" class="button ">敬請期待</a>
                                </div>
                            </div>

                        </div>
                        <div class="index-buy-item last">
                            <div class="index-buy-header">
                                <h4>專業版</h4>
                                <h3 class="price">66元/660元</h3>
                            </div>
                            <div class="index-buy-main">
                                <div class="index-buy-info">
                                    <p>1個月/1年有效</p>
                                    <p>不限會議</p>
                                </div>
                                <div class="index-buy-text">
                                    <ul>
                                        <li class="icon-li-selected">啟用投屏錄播</li>
                                        <li class="icon-li-selected">啟用投屏直播</li>
                                        <li class="icon-li-star">自定義水印</li>
                                        <li class="icon-li-close">廣告接入</li>
                                    </ul>
                                </div>
                                <div class="index-button">
                                    <a href="javascript:;" class="button ">敬請期待</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="module-section module-section-gray" style="background:#F9FCFF url(${ctxStatic}/images/img/index-shadow-bg.png) no-repeat center;">

            <div class="module-section-inner">
                <div class="page-width">
                    <div class="module-section-content index-info">
                        <div class="row">
                            <div class="col-lg-7">
                                <h3 class="color-black">全球領先的<br />雲同步回放演講系統</h3>
                                <p  >Global Leading Meeting System with <br />Cloud Synchronization Playback</p>
                            </div>
                            <div class="col-lg-5 pr">
                                <div class="index-playback-img"><img src="${ctxStatic}/images/img/Shadow-tw.png" alt=""></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <div class="footer" >
            <div class="page-width">
                <p class="t-center"><a href="${ctx}/index/17103116215880292674" class="color-black">關於我們</a><span class="muted">|</span><a href="${ctx}/index/17103116063862386794" class="color-black">幫助中心</a></p>
                <p class="t-center icon-row">
                    <a href="javascript:;"><img src="${ctxStatic}/images/index-icon-wechat.png" alt=""></a>
                    <a href="javascript:;"><img src="${ctxStatic}/images/index-icon-weibo.png" alt=""></a>
                    <a href="javascript:;"><img src="${ctxStatic}/images/index-icon-facebook.png" alt=""></a>
                    <a href="javascript:;"><img src="${ctxStatic}/images/index-icon-twitter.png" alt=""></a>
                    <a href="javascript;" class="index-qrcode">
                        <img src="${ctxStatic}/images/icon-indexDown.png" alt="">
                        <span class="qrcode-01 qrcode-bottom"><img src="${ctxStatic}/upload/img/qrcode.png" alt=""></span>
                    </a>
                </p>
                <p class="t-center"><%@include file="../include/copy_right.jsp"%></p>
            </div>
        </div>
    </div>

<script type="text/javascript">
    $(function(){
        //內容加載後的運動效果
        dataAnimate();

        var qrcode = $('.index-qrcode');
        qrcode.on({
            mouseenter:function() {
                $(this).find('.qrcode-01').show();
            },
            mouseleave:function() {
                $(this).find('.qrcode-01').hide();
            }
        })

    });


</script>
</body>
</html>
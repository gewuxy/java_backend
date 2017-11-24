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
    <title>CSPmeeting - Globally Leading Cloud Synchronization Playback System</title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style-EN.css">
    <script src="${ctxStatic}/js/animate.min.js"></script>
</head>
<body>
<div id="wrapper" class="index-box">
    <div class="header">
        <div class="page-width pr">
            <div class=" index-login">
                <div class="fr clearfix">
                    <%@include file="/WEB-INF/include/switch_language.jsp"%>
                    <a href="${ctx}/login" class="user-login-button"><strong>${not empty username ? username : "Login"}</strong>&nbsp;&nbsp;<i></i> </a>
                    <a href="javascript:;" class="index-download index-qrcode">Download App<span class="qrcode-01 qrcode-top"><img src="${ctxStatic}/upload/img/qrcode.png" alt=""></span></a>
                </div>
            </div>
        </div>
        <div class="banner index-banner-img">
            <div class="index-banner-content not-animated" data-animate="fadeIn" data-delay="200">
                <h3 class="fontStyleSubHead not-animated" data-animate="fadeInDown" data-delay="300"><img src="${ctxStatic}/images/index-logo-en.png" alt=""></h3>
                <h2 class="not-animated" data-animate="fadeInDown" data-delay="300">Spread your insights</h2>
                <h4 class="not-animated" data-animate="fadeIn" data-delay="500">Deliver your presentation with mobile&nbsp;:&nbsp;presenting, recording and  <br />sharing anytime, anywhere! </h4>
                <div class="index-buy-button not-animated" data-animate="fadeInUp" data-delay="400">
                    <a href="${ctx}/login" class="button item-radius">Free Trial</a>
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
                            <h2>Features</h2>
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
                                            <h3 class="title">Undemanding Device Requirement</h3>
                                            <div class="main">
                                                <p>Everyone can preside, record and release a meeting simultaneously with no more than one mobile <br /> phone + one App.</p>
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
                                            <h3 class="title">Meetings in Multiple Scenarios</h3>
                                            <div class="main">
                                                <p>Free switching between meeting record and projection live stream, or between audio and video in multiple meeting scenarios.</p>
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
                                            <h3 class="title">Real-time Cloud Synchronization</h3>
                                            <div class="main">
                                                <p>Projective live stream on site with real-time transmit to cloud allows you to record, preserve and play simultaneously.</p>
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
                                            <h3 class="title">Synchronizing Playback of Live Stream</h3>
                                            <div class="main">
                                                <p>No need to wait for the end of live stream – CSP supports instant playback of anything <br /> you just missed.</p>
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
                                            <h3 class="title">Audio& Video Optimization</h3>
                                            <div class="main">
                                                <p>The advanced coding algorithms, including intelligent echo elimination, automatic gain control and automatic white noise, ensure the quality <br /> of your meeting.</p>
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
                                            <h3 class="title">Sharing by One-Click</h3>
                                            <div class="main">
                                                <p>CSP connects to global mainstream social platform (e.g. Facebook, Twitter and Wechat). By one-click sharing, you can enhance your meeting <br/> influence and build your personal brand.</p>
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
                            <h2>Application Scenarios</h2>
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
                                                <h4><span>Individual Academic Communication</span></h4>
                                                <p><span>Switching among multiple scenarios to record</span></p>
                                                <p><span> and share your meetings with ease.</span></p>
                                                <p><span>CSP helps you on personal brand building by </span></p>
                                                <p><span>spreading your academic insights. </span></p>
                                            </div>
                                            <h3>Individual Academic Communication</h3>
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
                                                <h4><span>Education & Training</span></h4>
                                                <p><span> CSP enables a new form of PPT presentation with mobile phone.</span></p>
                                                <p><span> With the help of recording-in-lecturing and one-click-sharing,</span></p>
                                                <p><span> students can review the contents any time. </span></p>
                                                <p><span> Academic institution can ensure the quality of training with CSP.</span></p>

                                            </div>
                                            <h3>Education & Training</h3>
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
                                                <h4><span>Corporate Conference</span></h4>
                                                <p><span> Internal trainings and multilateral conferences could</span></p>
                                                <p><span> proceed in CSP regardless of location and number of attendees.</span></p>
                                                <p><span>  Real-time assignment delivery and live stream + chat-room</span></p>
                                                <p><span> communication make business conference more </span></p>
                                                <p><span> diverse and more efficient. </span></p>

                                            </div>
                                            <h3>Corporate Conference</h3>
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
                                                <h4><span>Large-Scale Video Live Stream</span></h4>
                                                <p><span>Smoother live experience supported by lower network</span></p>
                                                <p><span>flow cost brings immersive large-scale live stream </span></p>
                                                <p><span> which will be more welcome by your spectators.</span></p>

                                            </div>
                                            <h3>Large-Scale Video Live Stream</h3>
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
                            <h2>Our Advantages</h2>
                        </div>
                    </div>
                    <div class="module-section-content">
                        <div class="row">
                            <div class="col-lg-4" >
                                <h3 style="line-height: 1.4; margin-bottom:20px; font-size: 26px; font-weight: normal;" class="color-blue">Higher Speed</h3>
                                <p style="font-size:16px; padding-right:50px;">Through the integration of web cloud technology and App, CSP breaks through the limitation of device, bandwidth and process in traditional video conference, realizing essential real-time synchronization in cloud and playback.
                                </p>
                            </div>
                            <div class="col-lg-4" >
                                <h3 style="line-height: 1.4; margin-bottom:20px; font-size: 26px; font-weight: normal;" class="color-blue">Less Consumption</h3>
                                <p style="font-size:16px;  padding-right:50px;">The leading real-time stream processing technology and expressive form of “PPT + audio” saves you at least 80% of network flow comparing to other live stream service, while remaining the high image quality and low noise.
                                </p>
                            </div>
                            <div class="col-lg-4" >
                                <h3 style="line-height: 1.4; margin-bottom:20px;font-size: 26px; font-weight: normal;" class="color-blue">More Convenience</h3>
                                <p style="font-size:16px;  padding-right:30px;">Multiple meeting modes and one-click sharing to multiple terminals enhance your influence rapidly.
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
                            <h2>Price</h2>
                        </div>
                    </div>
                    <div class="module-section-content index-buy clearfix">
                        <div class="index-buy-item  ">
                            <div class="index-buy-header">
                                <h4>Standard Edition</h4>
                                <h3 class="price">Free</h3>
                            </div>
                            <div class="index-buy-main">
                                <div class="index-buy-info">
                                    <p>One-Month Usage</p>
                                    <p>3 Meetings</p>
                                </div>
                                <div class="index-buy-text">
                                    <ul>
                                        <li class="icon-li-selected">Projective Record</li>
                                        <li class="icon-li-close">Projective Live Stream</li>
                                        <li class="icon-li-selected">Watermark of CSPmeeting</li>
                                        <li class="icon-li-selected">Ads</li>
                                    </ul>
                                </div>
                                <div class="index-button" style="text-align:center;">
                                    <a href="javascript:;" class="button ">Coming Soon</a>
                                </div>
                            </div>
                        </div>
                        <div class="index-buy-item  ">
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
                                        <li class="icon-li-selected">Watermark of CSPmeeting</li>
                                        <li class="icon-li-close">Ads</li>
                                    </ul>
                                </div>
                                <div class="index-button" style="text-align:center;">
                                    <a href="javascript:;" class="button ">Coming Soon</a>
                                </div>
                            </div>

                        </div>
                        <div class="index-buy-item last">
                            <div class="index-buy-header">
                                <h4>Professional Edition</h4>
                                <h3 class="price">9.99 / 99.9 USD</h3>
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
                                        <li class="icon-li-close">Ads</li>
                                    </ul>
                                </div>
                                <div class="index-button" style="text-align:center;">
                                    <a href="javascript:;" class="button ">Coming Soon</a>
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
                                <h3 class="color-black">Global Leading Meeting System<br />with Cloud Synchronization Playback</h3>
                            </div>
                            <div class="col-lg-5 pr">
                                <div class="index-playback-img"><img src="${ctxStatic}/images/img/Shadow-en.png" alt=""></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div class="footer" >
        <div class="page-width">
            <p class="t-center"><a href="${ctx}/index/17103116215880292674" class="color-black">About Us</a><span class="muted">|</span><a href="${ctx}/index/17103116063862386794" class="color-black">Support</a></p>
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
        //内容加载后的运动效果
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
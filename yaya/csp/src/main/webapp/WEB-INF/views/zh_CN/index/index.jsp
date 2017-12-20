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
    <title>会讲-全球领先的云同步回放演讲系统</title>
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
                    <%@include file="/WEB-INF/include/switch_language.jsp"%>
                    <a href="${ctx}/login" class="user-login-button"><strong>${not empty username ? username : "登录"}</strong>&nbsp;&nbsp;<i></i> </a>
                    <a href="javascript:;" class="index-download index-qrcode">下载App<span class="qrcode-01 qrcode-top"><img id="qrCode" src="${ctxStatic}/upload/img/qrcode.png" alt=""></span></a>
                </div>
            </div>
        </div>
        <div class="banner index-banner-img">
            <div class="index-banner-content not-animated" data-animate="fadeIn" data-delay="200">
                <h3 class="fontStyleSubHead not-animated" data-animate="fadeInDown" data-delay="300"><div class="img">
                    <img src="${ctxStatic}/images/index-logo.png" alt=""></div></h3>
                <h4 class="not-animated" data-animate="fadeInDown" data-delay="300">用手机讲PPT，随讲随录随分享！</h4>
                <h2 class="not-animated" data-animate="fadeIn" data-delay="500">你的智慧，将被更多人看见</h2>
                <div class="index-buy-button not-animated" data-animate="fadeInUp" data-delay="400">
                    <a href="${ctx}/login" class="button item-radius">免费体验</a>
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
                                            <h3 class="title">轻设备</h3>
                                            <div class="main">
                                                <p>同步完成会议主持、录制、发布，只要一部手机+会讲 App</p>
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
                                            <h3 class="title">多场景智能会议模式</h3>
                                            <div class="main">
                                                <p>多种开会场景，录播/直播、语音/视频，任意切换</p>
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
                                            <h3 class="title">实时云同步</h3>
                                            <div class="main">
                                                <p>会议现场投屏直播，云端实时传输，即录即存即播</p>
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
                                                <p>不用等到结束，会讲支持在直播时对刚刚错过的内容进行立即回看</p>
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
                                            <h3 class="title">音视频优化</h3>
                                            <div class="main">
                                                <p>高品质编解码算法，智能回声消除、自动增益控制、自动舒适噪声……确保会议高质量</p>
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
                                            <h3 class="title">一键分享</h3>
                                            <div class="main">
                                                <p>链接全球主流社交平台Wechat / Facebook / Twitter，一键轻松分享，扩大影响，打造个人品牌</p>
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
                            <h2>应用场景</h2>
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
                                                <h4><span>个人学术交流</span></h4>
                                                <p><span>轻轻松松录制、分享，多场景会议模式任你选择，</span></p>
                                                <p><span>传播学术见解，树立个人品牌。</span></p>
                                            </div>
                                            <h3>个人学术交流</h3>
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
                                                <h4><span>教学培训</span></h4>
                                                <p><span>用手机讲PPT，教学内容随堂录制，一键转发，</span></p>
                                                <p><span>学生随时查看，满足教学单位高质量授课需求。</span></p>
                                            </div>
                                            <h3>教学培训</h3>
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
                                                <h4><span>企业会议</span></h4>
                                                <p><span>内部培训、多方会议，不限人次，实时传达，</span></p>
                                                <p><span>支持直播+互动，让沟通立体、高效。</span></p>
                                            </div>
                                            <h3>企业会议</h3>
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
                                                <p><span>以更少的流量成本支撑更加流畅的播出体验，</span></p>
                                                <p><span>大型直播更加身临其境，更受用户欢迎。</span></p>
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
                            <h2>产品优势</h2>
                            <h3>Our Advantages</h3>
                        </div>
                    </div>
                    <div class="module-section-content">
                        <div class="row">
                            <div class="col-lg-4" >
                                <h3 style="line-height: 1.4; margin-bottom:20px; font-size: 26px; font-weight: normal;" class="color-blue">更实时</h3>
                                <p style="font-size:16px; padding-right:50px;">会讲通过App与Web云技术的创新结合，
                                    突破传统直播在设备、带宽、进程方面的限制，
                                    真正实现实时云端同步，
                                    可在直播当时进行内容回看。
                                </p>
                            </div>
                            <div class="col-lg-4" >
                                <h3 style="line-height: 1.4; margin-bottom:20px; font-size: 26px; font-weight: normal;" class="color-blue">更节省</h3>
                                <p style="font-size:16px;  padding-right:50px;">领先的流媒体处理技术及“PPT+音频”的呈现形式，
                                    在保持会议高画质、
                                    低噪音的同时，
                                    比同类视频直播节省至少80%的流量。
                                </p>
                            </div>
                            <div class="col-lg-4" >
                                <h3 style="line-height: 1.4; margin-bottom:20px;font-size: 26px; font-weight: normal;" class="color-blue">更便捷</h3>
                                <p style="font-size:16px;  padding-right:30px;">多场景会议模式，
                                    多终端支持分享， 一键传播，
                                    快速提升影响力。
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
                            <h2>产品售价</h2>
                            <h3>Price</h3>
                        </div>
                    </div>
                    <div class="module-section-content index-buy clearfix">
                        <div class="index-buy-item ">
                            <div class="index-buy-header">
                                <h4>标准版</h4>
                                <h3 class="price">免费</h3>
                            </div>
                            <div class="index-buy-main">
                                <div class="index-buy-info">
                                    <p>1个月有效</p>
                                    <p>3个会议</p>
                                </div>
                                <div class="index-buy-text">
                                    <ul>
                                        <li class="icon-li-selected">启用投屏录播</li>
                                        <li class="icon-li-close">启用投屏直播</li>
                                        <li class="icon-li-selected">会讲水印</li>
                                        <li class="icon-li-selected">广告接入</li>
                                    </ul>
                                </div>
                                <div class="index-button">
                                    <a href="javascript:;" class="button ">敬请期待</a>
                                </div>
                            </div>
                        </div>
                        <div class="index-buy-item  ">
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
                                        <li class="icon-li-selected">会讲水印</li>
                                        <li class="icon-li-close">广告接入</li>
                                    </ul>
                                </div>
                                <div class="index-button">
                                    <a href="javascript:;" class="button ">敬请期待</a>
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
                                        <li class="icon-li-close">广告接入</li>
                                    </ul>
                                </div>
                                <div class="index-button">
                                    <a href="javascript:;" class="button ">敬请期待</a>
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
                                <h3 class="color-black">全球领先的<br />云同步回放演讲系统</h3>
                                <p  >Global Leading Meeting System with <br />Cloud Synchronization Playback</p>
                            </div>
                            <div class="col-lg-5 pr">
                                <div class="index-playback-img"><img src="${ctxStatic}/images/img/Shadow.png" alt=""></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div class="footer" >
        <div class="page-width">
            <p class="t-center"><a href="${ctx}/index/17103116215880292674" class="color-black">关于我们</a><span class="muted">|</span><a href="${ctx}/index/17103116063862386794" class="color-black">帮助中心</a></p>
            <p class="t-center icon-row">
                <a href="javascript:;"><img src="${ctxStatic}/images/index-icon-wechat.png" alt=""></a>
                <a href="javascript:;"><img src="${ctxStatic}/images/index-icon-weibo.png" alt=""></a>
                <a href="javascript:;"><img src="${ctxStatic}/images/index-icon-facebook.png" alt=""></a>
                <a href="javascript:;"><img src="${ctxStatic}/images/index-icon-twitter.png" alt=""></a>
                <a href="javascript:;" class="index-qrcode"><img src="${ctxStatic}/images/icon-indexDown.png" alt="">
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


        // 先读取缓存
        function getCookie(cookieName){
            var temp ;
            $.ajax({
                url:'${ctx}/cookie/get',
                data:{'cookieName':cookieName},
                async:false,
                success:function (data) {
                    temp = data;
                }
            });
            return temp;
        }

        var cookie_local = getCookie('_local');

        if (cookie_local == "en_US" || cookie_local == "zh_TW") {
            $("#qrCode").attr("src","${ctxStatic}/upload/img/qrcode_abroad.png");
        } else {
            $("#qrCode").attr("src","${ctxStatic}/upload/img/qrcode.png");
        }

    });

</script>

</body>
</html>
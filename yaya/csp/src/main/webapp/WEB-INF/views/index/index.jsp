<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/31
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html >
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta charset="UTF-8">
    <title><fmt:message key="page.common.appName"/> - <fmt:message key="page.title.index"/> </title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <script src="${ctxStatic}/js/animate.min.js"></script>
</head>
<body>
<div id="wrapper" class="index-box">
    <div class="header">
        <div class="page-width pr">
            <div class=" index-login">
                <div class="fr clearfix">
                    <%@include file="/WEB-INF/include/switch_language.jsp"%>
                    <a href="${ctx}/login" class="user-login-button"><strong>
                        <c:if test="${empty username}">
                            <fmt:message key="page.button.login"/>
                        </c:if>
                        <c:if test="${not empty username}">
                            ${username}
                        </c:if>
                    </strong>&nbsp;&nbsp;<i></i> </a>
                    <a href="javascript:;" class="index-download index-qrcode"><fmt:message key="page.button.download"/> App<span class="qrcode-01 qrcode-top"><img id="qrCode" src="${ctxStatic}/upload/img/qrcode.png" alt=""></span></a>
                </div>
            </div>
        </div>
        <div class="banner index-banner-img">
            <div class="index-banner-content not-animated" data-animate="fadeIn" data-delay="200">
                <h3 class="fontStyleSubHead not-animated" data-animate="fadeInDown" data-delay="300">
                    <c:choose>
                        <c:when test="${csp_locale eq 'en_US'}">
                            <img src="${ctxStatic}/images/index-logo-en.png" alt="">
                        </c:when>
                        <c:otherwise>
                            <div class="img"><img src="${ctxStatic}/images/index-logo.png" alt=""></div>
                        </c:otherwise>
                    </c:choose>
                </h3>

                <c:choose>
                    <c:when test="${csp_locale eq 'en_US'}">
                        <h2 class="not-animated" data-animate="fadeInDown" data-delay="300"><fmt:message key="page.index.content.line1"/></h2>
                        <h4 class="not-animated" data-animate="fadeIn" data-delay="500"><fmt:message key="page.index.content.line2"/></h4>
                    </c:when>
                    <c:otherwise>
                        <h4 class="not-animated" data-animate="fadeInDown" data-delay="300"><fmt:message key="page.index.content.line1"/></h4>
                        <h2 class="not-animated" data-animate="fadeIn" data-delay="500"><fmt:message key="page.index.content.line2"/></h2>
                    </c:otherwise>
                </c:choose>

                <div class="index-buy-button not-animated" data-animate="fadeInUp" data-delay="400">
                    <a href="${ctx}/login" class="button item-radius"><fmt:message key="page.button.taste" /></a>
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
                            <c:choose>
                                <c:when test="${csp_locale eq 'en_US'}">
                                    <h2><fmt:message key="page.index.content.line3-1"/></h2>
                                </c:when>
                                <c:otherwise>
                                    <h2><fmt:message key="page.index.content.line3"/></h2>
                                    <h3><fmt:message key="page.index.content.line3-1"/></h3>
                                </c:otherwise>
                            </c:choose>

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
                                            <h3 class="title"><fmt:message key="page.index.content.line4"/></h3>
                                            <div class="main">
                                                <p><fmt:message key="page.index.content.line5"/></p>
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
                                            <h3 class="title"><fmt:message key="page.index.content.line6"/></h3>
                                            <div class="main">
                                                <p><fmt:message key="page.index.content.line7"/></p>
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
                                            <h3 class="title"><fmt:message key="page.index.content.line8"/></h3>
                                            <div class="main">
                                                <p><fmt:message key="page.index.content.line9"/></p>
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
                                            <h3 class="title"><fmt:message key="page.index.content.line10"/></h3>
                                            <div class="main">
                                                <p><fmt:message key="page.index.content.line11"/></p>
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
                                            <h3 class="title"><fmt:message key="page.index.content.line12"/></h3>
                                            <div class="main">
                                                <p><fmt:message key="page.index.content.line13"/></p>
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
                                            <h3 class="title"><fmt:message key="page.index.content.line14"/></h3>
                                            <div class="main">
                                                <p><fmt:message key="page.index.content.line15"/></p>
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
                            <c:choose>
                                <c:when test="${csp_locale eq 'en_US'}">
                                    <h2><fmt:message key="page.index.content.line16-1"/></h2>
                                </c:when>
                                <c:otherwise>
                                    <h2><fmt:message key="page.index.content.line16"/></h2>
                                    <h3><fmt:message key="page.index.content.line16-1"/></h3>
                                </c:otherwise>
                            </c:choose>
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
                                                <h4><span><fmt:message key="page.index.content.line17"/></span></h4>
                                                <p><span><fmt:message key="page.index.content.line18"/></span></p>
                                                <p><span><fmt:message key="page.index.content.line19"/></span></p>
                                            </div>
                                            <h3><fmt:message key="page.index.content.line20"/></h3>
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
                                                <h4><span><fmt:message key="page.index.content.line21"/></span></h4>
                                                <p><span><fmt:message key="page.index.content.line22"/></span></p>
                                                <p><span><fmt:message key="page.index.content.line23"/></span></p>
                                                <c:choose>
                                                    <c:when test="${csp_locale eq 'en_US'}">
                                                        <p><span><fmt:message key="page.index.content.line23-1"/></span></p>
                                                        <p><span><fmt:message key="page.index.content.line23-2"/></span></p>
                                                    </c:when>
                                                </c:choose>
                                            </div>
                                            <h3><fmt:message key="page.index.content.line24"/></h3>
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
                                                <h4><span><fmt:message key="page.index.content.line25"/></span></h4>
                                                <p><span><fmt:message key="page.index.content.line26"/></span></p>
                                                <p><span><fmt:message key="page.index.content.line27"/></span></p>
                                                <c:choose>
                                                    <c:when test="${csp_locale eq 'en_US'}">
                                                        <p><span><fmt:message key="page.index.content.line27-1"/></span></p>
                                                        <p><span><fmt:message key="page.index.content.line27-2"/></span></p>
                                                        <p><span><fmt:message key="page.index.content.line27-3"/></span></p>
                                                    </c:when>
                                                </c:choose>
                                            </div>
                                            <h3><fmt:message key="page.index.content.line28"/></h3>
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
                                                <h4><span><fmt:message key="page.index.content.line29"/></span></h4>
                                                <p><span><fmt:message key="page.index.content.line30"/></span></p>
                                                <p><span><fmt:message key="page.index.content.line31"/></span></p>
                                                <c:choose>
                                                    <c:when test="${csp_locale eq 'en_US'}">
                                                        <p><span><fmt:message key="page.index.content.line31-1"/></span></p>
                                                    </c:when>
                                                </c:choose>
                                            </div>
                                            <h3><fmt:message key="page.index.content.line32"/></h3>
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
                            <c:choose>
                                <c:when test="${csp_locale eq 'en_US'}">
                                    <h2><fmt:message key="page.index.content.line33-1"/></h2>
                                </c:when>
                                <c:otherwise>
                                    <h2><fmt:message key="page.index.content.line33"/></h2>
                                    <h3><fmt:message key="page.index.content.line33-1"/></h3>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="module-section-content">
                        <div class="row">
                            <div class="col-lg-4" >
                                <h3 style="line-height: 1.4; margin-bottom:20px; font-size: 26px; font-weight: normal;" class="color-blue"><fmt:message key="page.index.content.line34"/></h3>
                                <p style="font-size:16px; padding-right:50px;"><fmt:message key="page.index.content.line35"/>
                                </p>
                            </div>
                            <div class="col-lg-4" >
                                <h3 style="line-height: 1.4; margin-bottom:20px; font-size: 26px; font-weight: normal;" class="color-blue"><fmt:message key="page.index.content.line36"/></h3>
                                <p style="font-size:16px;  padding-right:50px;"><fmt:message key="page.index.content.line37"/>
                                </p>
                            </div>
                            <div class="col-lg-4" >
                                <h3 style="line-height: 1.4; margin-bottom:20px;font-size: 26px; font-weight: normal;" class="color-blue"><fmt:message key="page.index.content.line38"/></h3>
                                <p style="font-size:16px;  padding-right:30px;"><fmt:message key="page.index.content.line39"/>
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
                            <c:choose>
                                <c:when test="${csp_locale eq 'en_US'}">
                                    <h2><fmt:message key="page.index.content.line40-1"/></h2>
                                </c:when>
                                <c:otherwise>
                                    <h2><fmt:message key="page.index.content.line40"/></h2>
                                    <h3><fmt:message key="page.index.content.line40-1"/></h3>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="module-section-content index-buy clearfix">
                        <div class="index-buy-item  ">
                            <div class="index-buy-header">
                                <h4><fmt:message key="page.index.content.line41"/></h4>
                                <h3 class="price"><fmt:message key="page.index.content.line42"/></h3>
                            </div>
                            <div class="index-buy-main">
                                <div class="index-buy-info ">
                                    <p><fmt:message key="page.index.content.line43"/></p>
                                    <p><fmt:message key="page.index.content.line44"/></p>
                                </div>
                                <div class="index-buy-text">
                                    <ul>
                                        <li class="icon-li-selected"><fmt:message key="page.index.content.line45"/></li>
                                        <li class="icon-li-selected"><fmt:message key="page.index.content.line46"/></li>
                                        <c:choose>
                                            <c:when test="${csp_locale eq 'zh_CN'}">
                                                <li >&nbsp;</li>
                                                <li >&nbsp;</li>
                                            </c:when>
                                            <c:when test="${csp_locale eq 'zh_TW'}">
                                                <li >&nbsp;</li>
                                            </c:when>
                                        </c:choose>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="index-buy-item  ">
                            <div class="index-buy-header">
                                <h4><fmt:message key="page.index.content.line47"/></h4>
                                <h3 class="price"><fmt:message key="page.index.content.line48"/></h3>
                            </div>
                            <div class="index-buy-main">
                                <div class="index-buy-info">
                                    <p><fmt:message key="page.index.content.line49"/></p>
                                    <p><fmt:message key="page.index.content.line50"/></p>
                                </div>
                                <div class="index-buy-text">
                                    <ul>
                                        <li class="icon-li-selected"><fmt:message key="page.index.content.line51"/></li>
                                        <li class="icon-li-selected"><fmt:message key="page.index.content.line52"/></li>
                                        <li class="icon-li-selected"><fmt:message key="page.index.content.line53"/></li>
                                        <li class="icon-li-selected"><fmt:message key="page.index.content.line54"/></li>
                                    </ul>
                                </div>
                            </div>

                        </div>
                        <div class="index-buy-item last">
                            <div class="index-buy-header">
                                <h4><fmt:message key="page.index.content.line55"/></h4>
                                <h3 class="price"><fmt:message key="page.index.content.line56"/></h3>
                            </div>
                            <div class="index-buy-main">
                                <div class="index-buy-info">
                                    <p><fmt:message key="page.index.content.line57"/></p>
                                    <p><fmt:message key="page.index.content.line58"/></p>
                                </div>
                                <div class="index-buy-text">
                                    <ul>
                                        <li class="icon-li-selected"><fmt:message key="page.index.content.line59"/></li>
                                        <li class="icon-li-selected"><fmt:message key="page.index.content.line60"/></li>
                                        <li class="icon-li-star"><fmt:message key="page.index.content.line61"/></li>
                                        <li class="icon-li-selected"><fmt:message key="page.index.content.line62"/></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="index-buy-button t-center" >
                        <a href="${ctx}/login" class="button item-radius"><fmt:message key="page.index.content.line63"/></a>
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
                                <c:choose>
                                    <c:when test="${csp_locale eq 'en_US'}">
                                        <h3 class="color-black"><fmt:message key="page.index.content.line64-1"/></h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3 class="color-black"><fmt:message key="page.index.content.line64"/></h3>
                                        <p  ><fmt:message key="page.index.content.line64-1"/></p>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                            <div class="col-lg-5 pr">
                                <div class="index-playback-img">
                                <c:choose>
                                    <c:when test="${csp_locale eq 'en_US'}">
                                        <img src="${ctxStatic}/images/img/Shadow-en.png" alt="">
                                    </c:when>
                                    <c:when test="${csp_locale eq 'zh_TW'}">
                                        <img src="${ctxStatic}/images/img/Shadow-tw.png" alt="">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${ctxStatic}/images/img/Shadow.png" alt="">
                                    </c:otherwise>
                                </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div class="footer" >
        <div class="page-width">
            <p class="t-center">
                <a href="${ctx}/index/17103116215880292674" class="color-black"><fmt:message key="page.index.content.line65"/></a>
                <span class="muted">|</span>
                <a href="${ctx}/index/17103116063862386794" class="color-black"><fmt:message key="page.index.content.line66"/></a>
            </p>
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
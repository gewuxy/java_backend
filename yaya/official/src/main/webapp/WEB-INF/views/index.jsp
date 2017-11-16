<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>首页</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
</head>
<div id="wrapper" class="v2-medcnIndex">
    <div class="v2-top v2-header1 fixed-nav">
        <div class="v2-top-main">
            <div class="page-width clearfix">
                <div class="logo" >
                    <a href="/" >
                        <img src="${ctxStatic}/images/v2/logo.png" alt=""  />
                    </a>
                </div><!-- end of logo -->

                <div class="v2-top-item">
                    <!-- S nav -->
                    <nav class="v2-nav">
                        <div class="main-nav clearfix" >
                            <ul class="sf-menu" >
                                <li class="current">
                                    <a class="first-level" target="_blank" href="index.html"><strong class="first-level-min">首页</strong></a><i></i>
                                </li>
                                <li >
                                    <a class="first-level" target="_blank" href="javascript:;"><strong
                                            class="first-level-min">健康动态</strong></a><i></i>
                                    <ul>
                                        <li><a href="newList.html">医药动态</a></li>
                                        <li><a href="newList.html">安全用药</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <a class="first-level" href="javascript:;"><strong class="first-level-min">我的工具</strong></a><i></i>
                                    <ul>
                                        <li><a href="searchItem-01.html">药师建议</a></li>
                                        <li><a href="searchItem-03.html">医师建议</a></li>
                                        <li><a href="searchItem-02.html">对症下药</a></li>
                                    </ul>
                                </li>
                            </ul>
                            </hal>
                        </div>
                    </nav>
                    <!-- E nav-->
                    <!--search-->
                    <div class="v2-top-search clearfix">
                        <div class="v2-search-form v2-search-form-responsive clearfix">
                            <input type="text"  placeholder="药品通用名/商品名/批准文号" name="" id=""  class="form-text" >
                            <button type="submit" class="form-btn" ><span></span></button>
                        </div>
                    </div>
                    <div class="top-widget clearfix">
                        <p><a href="javascript:;" class="button button-color fx-btn-1">登录</a><a href="javascript:;" class="button color-blue fx-btn-2">注册</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="v2-banner bg-lightBlue">
        <div class="page-width clearfix">
            <div class="fr">
                <div class="v2-index-download-area clearfix v2-hover-shadow" style="margin-bottom:20px;">
                    <p class="t-center margin-b"><img src="${ctxStatic}/images/upload/_index_minBanner_area_img1.png" alt="合理用药APP 呵护你的健康"></p>
                    <div class="fr">
                        <p class="margin-b"><a href="http://www.medcn.cn/app_jk.jsp" class="v2-index-download-button"><img src="${ctxStatic}/images/v2/download-button-apple.png" alt=""></a></p>
                        <p><a href="http://www.medcn.cn/app_jk.jsp" class="v2-index-download-button"><img src="${ctxStatic}/images/v2/download-button-android.png" alt=""></a></p>
                    </div>
                    <div class="fl t-center"><img src="${ctxStatic}/images/upload/jk-erweima.png" alt="" style="width:90px; height:90px;"><p >扫描二维码下载</p></div>
                </div>
                <div class="oh v2-hover-shadow">
                    <a href="http://www.medcn.cn/zy_login.jsp" style=" display:block; line-height: 1; margin:0; padding:0;"><img src="${ctxStatic}/images/upload/min-ad-01.jpg" alt=""></a>
                </div>
            </div>
            <div class="fl">
                <!-- S slideshow -->
                <div class="slideshow carousel clearfix" style="width:895px ;height:450px; overflow:hidden;">
                    <div id="carousel-05">
                        <div class="carousel-item">
                            <div class="carousel-img">
                                <a href="detail.html" target="_blank">
                                    <img src="${ctxStatic}/images/upload/_banner_170228_1.jpg" alt="" />
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="carousel-btn carousel-btn-fixed" id="carousel-page-05"></div>

                </div>
                <!-- E slideshow -->
            </div>
        </div>

    </div>

    <div class="v2-main">
        <div class="page-width clearfix">
            <div class="clearfix " style="margin-bottom:20px;">
                <div class="row">
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_01.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_01.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_01.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-news-list">
                            <div class="v2-news-title">
                                <a href="list.html" class="more fr">更多&gt;&gt;</a>
                                <h3>公司动态</h3>
                            </div>
                            <div class="v2-news-main">
                                <ul>
                                    <li><a href="detail.html">"YaYa医师"亮相第六届中国国际版权</a></li>
                                    <li><a href="detail.html">"YaYa医师"亮相第六届中国国际版权</a></li>
                                    <li><a href="detail.html">"YaYa医师"亮相第六届</a></li>
                                    <li><a href="detail.html">"YaYa医师"亮相第六届中国国际版权</a></li>
                                    <li><a href="detail.html">"YaYa医师"亮相第六届中国国际版权</a></li>
                                    <li><a href="detail.html">"YaYa医师"亮相第六届中国国际版权</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="v2-entry-area clearfix">
                <div class="row">
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_01.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_02.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">艾滋病</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">肝脏病变 安眠药惹的祸！</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_03.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_04.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">艾滋病</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">肝脏病变 安眠药惹的祸！</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_01.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_02.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">艾滋病</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">肝脏病变 安眠药惹的祸！</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_03.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_04.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">艾滋病</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">肝脏病变 安眠药惹的祸！</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_01.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_02.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">艾滋病</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">肝脏病变 安眠药惹的祸！</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_03.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_04.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">艾滋病</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">肝脏病变 安眠药惹的祸！</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_01.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_02.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">艾滋病</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">肝脏病变 安眠药惹的祸！</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_03.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_04.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">艾滋病</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">肝脏病变 安眠药惹的祸！</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_01.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_02.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">艾滋病</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">肝脏病变 安眠药惹的祸！</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_03.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_04.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">艾滋病</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">肝脏病变 安眠药惹的祸！</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_01.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_02.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">艾滋病</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">肝脏病变 安眠药惹的祸！</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_03.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">吃药</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">联合国报告：全球接受艾滋病“救命疗法”的人数翻倍</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="v2-entry-item">
                            <div class="v2-entry-img">
                                <a href="newDetail.html"><img src="${ctxStatic}/images/upload/_index_list_img_04.jpg" alt=""></a>
                                <i class="v2-entry-classIcon"><a href="newDetail.html">艾滋病</a></i>
                            </div>
                            <div class="v2-entry-title">
                                <h5><a href="newDetail.html">肝脏病变 安眠药惹的祸！</a></h5>
                                <span class="time">12月15日   18:00</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="t-center">
                <a href="#" class="v2-more-button">点击查看更多</a>
            </div>
        </div>
    </div>
    <div class="v2-bottom">
        <div class="page-width clearfix">
            <div class="row">
                <div class="col-lg-2">
                    <h4><a href="about.html">关于我们</a></h4>
                    <ul>
                        <li><a href="about.html">公司简介</a></li>
                        <li><a href="about.html#about-maps-02">公司动态</a></li>
                        <li><a href="about.html#about-maps-03">合作伙伴</a></li>
                        <li><a href="about.html#about-maps-01">我们团队</a></li>
                        <li><a href="about.html#about-maps-04">联系我们</a></li>
                    </ul>
                </div>
                <div class="col-lg-2">
                    <h4><a href="phone.html">移动中心</a></h4>
                    <ul>
                        <li><a href="http://www.medcn.cn/app_yis.jsp">YaYa医师</a></li>
                        <li><a href="http://www.medcn.cn/app_yaos.jsp">YaYa药师</a></li>
                        <li><a href="phone.html">合理用药</a></li>
                        <li><a href="#">YaYa助手</a></li>
                    </ul>
                </div>
                <div class="col-lg-3">
                    <h4><a href="http://ansongd.com/yyks/#g=1&p=欢迎登录页">数字平台</a></h4>
                    <ul>
                        <li><a href="http://www.medcn.cn/zy_login.jsp">专业医学会议数字化管理平台</a></li>
                        <li><a href="http://ansongd.com/yyks/#g=1&p=欢迎登录页">PRM患者管理系统</a></li>
                    </ul>
                </div>
                <div class="col-lg-2">
                    <h4><a href="data.html">数据中心</a></h4>
                    <ul>
                        <li><a href="http://www.medcn.cn/jx_ycy/jsp/integration/home.jsp">医药数据</a></li>
                    </ul>
                </div>
                <div class="col-lg-2">
                    <h4><a href="help-01.html">帮助与反馈</a></h4>
                    <ul>
                        <li><a href="help-01.html">产品解答</a></li>
                        <li><a href="help-02.html">服务与收费</a></li>
                        <li><a href="help-03.html">意见反馈</a></li>
                        <li><a href="help-04.html">我要投稿</a></li>
                    </ul>
                </div>
                <div class="col-lg-1">
                    <h4><a href="#">象城</a></h4>
                </div>
            </div>
        </div>
    </div>
    <div class="v2-footer">
        <div class="page-width clearfix">
            <p class="t-center">
                <a href="http://www.miibeian.gov.cn/" target="_blank">粤ICP备12087993号</a>
                <a target="_blank" href="statement.html">免责声明</a> | <a href="update.html">更新日志</a>
            </p>
        </div>
    </div>
</div>
<!--弹出层-->
<div class="mask-wrap">
    <div class="dis-tbcell">
        <div class="distb-box fx-mask-box-1 v2-data-login pr">
            <div class="fx-mask-box clearfix" >
                <span class="close-btn-fx"><img src="${ctxStatic}/images/v2/icon-close.png"></span>
                <div class="formrow">
                    <div class="t-center " style="margin-bottom:25px;"><img src="${ctxStatic}/images/v2/logo-login.png" alt=""></div>
                    <div class="login_box login_tip login_tip_error"></div>
                    <input type="hidden" id="message" value="${message}"/>
                    <input type="hidden" id="type" value="${type}"/>
                    <form action="${ctx}/login" id="loginForm" name="loginForm" method="post">
                        <div class="v2-login_box"><input type="text" id="account" name="account" class="input-border textInput" placeholder="输入手机号或邮箱登录"></div>
                        <div class="v2-login_box"><input type="password" id="password" name="password" class="input-border textInput" placeholder="密码"></div>
                        <div class="v2-login_box" style="position:relative">
                            <div class="fl color-gray">
                                <input type="checkbox" id="checkbox_2" class="chk_1">
                                <label for="checkbox_2">
                                    <i class="ico"></i>
                                    记住我
                                </label>
                            </div>
                            <div class="fr">
                                <a href="http://www.medcn.cn/jiankangjsp/forget_pword.jsp" class="fr color-blue">忘记密码</a>
                            </div>
                            <div class="clear"></div>
                        </div>
                        <div class="clearfix" style="margin-bottom:5px;">
                            <a href="javascript:void(0);" class="v2-login-button v2-blue-button" onclick="loginCheck();">登录</a>
                        </div>
                    </form>
                    <div class="v2-login_box" style="margin-bottom:35px;">
                        <p class="fz12 color-gray">提示：使用该账号可登录 <a href="#" class=" underline">合理用药APP客户端</a> </p>
                    </div>
                    <div class="v2-login_bottom">
                        <div class="fl">
                            <span class="fl">还没有账号？</span>
                            <a href="javascript:;" class="color-blue  fx-btn-2">立即注册</a>
                        </div>
                        <div class="fr">
                            <a href="#" class="v2-login-icon v2-weixin"></a>&nbsp;&nbsp;&nbsp;<a href="#" class="v2-login-icon v2-qq"></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="distb-box fx-mask-box-2 pr">
            <span class="close-btn-fx"><img src="${ctxStatic}/images/v2/icon-close.png"></span>
            <div class="fx-mask-box fx-mask-box-max clearfix" >
                <div class="row">
                    <div class="col-lg-6">
                        <div class="formrow v2-reg-area">
                            <p><span class="mintitle">填写注册信息</span></p>
                            <form action="${ctx}/login" id="registerForm" name="loginForm" method="post">
                                <div class="v2-login_box">
                                    <p>
                                        <label for="" class="pr">
                                            <input type="text" id="mobile" name="mobile" class="input-border textInput" placeholder="输入手机号或邮箱"><span class="formIconTips"></span><span class="errorText hide">该手机号码已被注册</span>
                                        </label>
                                    </p>
                                </div>
                                <div class="v2-login_box">
                                    <p>
                                        <input id="btnSendCode" class="getCodeButton fr" type="button" value="获取验证码" onclick="sendMessage()" />
                                        <span  class="pr">
                                                <input type="text" class="textInput input-border textInput-min" placeholder="验证码"><span class="formIconTips errorIcon"></span><span class="errorText" style="left:4%">验证码错误</span>
                                            </span>
                                    </p>
                                </div>
                                <div class="v2-login_box">
                                    <p>
                                        <label for="" class="pr">
                                            <input type="text" class="input-border textInput" placeholder="设置密码"><span class="formIconTips errorIcon"></span><span class="errorText">密码不一致</span>
                                        </label>
                                    </p>
                                </div>
                                <div class="v2-login_box">
                                    <p>
                                        <label for="" class="pr">
                                            <input type="text" class="input-border textInput" placeholder="确认密码"><span class="formIconTips errorIcon"></span><span class="errorText">密码不一致</span>
                                        </label>
                                    </p>
                                </div>
                                <div class="v2-login_box">
                                    <a href="javascript:;" class="pr textSelect-a">
                                        <i class="textSelect-arrow"></i>
                                        <select name="" class="textSelect textSelect-min">

                                            <option value="请选择">请选择</option>
                                            <option value="合理用药">合理用药</option>
                                            <option value="YaYa医师">YaYa医师</option>
                                            <option value="YaYa药师">YaYa药师</option>
                                            <option value="YaYa助手">YaYa助手</option>
                                            <option value="YaYa医学直播">YaYa医学直播</option>
                                        </select>
                                    </a>
                                    <span style="margin:0 2%;">省</span>
                                    <a href="javascript:;" class="pr textSelect-a">
                                        <i class="textSelect-arrow"></i>
                                        <select name="" class="textSelect textSelect-min">
                                            <option value="请选择">请选择</option>
                                            <option value="合理用药">合理用药</option>
                                            <option value="YaYa医师">YaYa医师</option>
                                            <option value="YaYa药师">YaYa药师</option>
                                            <option value="YaYa助手">YaYa助手</option>
                                            <option value="YaYa医学直播">YaYa医学直播</option>
                                        </select>
                                    </a>
                                    <span style="margin:0 2%;">市</span>
                                </div>
                                <div class="v2-login_box" style="position:relative">
                                    <div class="fl color-gray">
                                        <input type="checkbox" id="checkbox_3" class="chk_1">
                                        <label for="checkbox_3">
                                            <i class="ico"></i>
                                            我已阅读并接受《协议书》
                                        </label>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="clearfix" style="margin-bottom:5px;">
                                    <a href="javascript:void(0);" class="v2-login-button v2-blue-button" >注册</a>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="col-lg-5 col-lg-offset-1">
                        <div class="formrow">
                            <div class="t-center " style="margin:60px 0 105px;"><img src="${ctxStatic}/images/v2/logo-login.png" alt=""></div>
                            <p class="t-center fz12">第三方账户登录</p>
                            <div class="t-center" style="margin-bottom:105px;">
                                <a href="#" class="v2-login-icon v2-login-icon-max v2-weixin-max"></a>&nbsp;&nbsp;&nbsp;<a href="#" class="v2-login-icon v2-login-icon-max v2-qq-max"></a>
                            </div>
                            <div class="v2-login_box t-center" style="margin-bottom:35px;">
                                <p class="fz12">提示：使用该账号可登录 <a href="#" class=" underline fx-btn-2">合理用药APP客户端</a> </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="gotop-wrapper index-gotop">
    <a class="gotop" href="javascript:;" >回到顶部</a>
</div>
<%@ include file="/WEB-INF/include/common_js.jsp" %>
<script type="text/javascript" src="${ctxStatic}/js/index.js"></script>
</body>
</html>
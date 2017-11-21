<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                                <a class="first-level" href="${ctx}/index"><strong class="first-level-min">首页</strong></a><i></i>
                            </li>
                            <li >
                                <a class="first-level"  href="javascript:;">
                                    <strong class="first-level-min">健康动态</strong>
                                </a><i></i>
                                <ul>
                                    <li><a href="${ctx}/news/list?type=1">医药动态</a></li>
                                    <li><a href="${ctx}/news/list?type=2">安全用药</a></li>
                                </ul>
                            </li>
                            <li>
                                <a class="first-level" href="javascript:;">
                                    <strong class="first-level-min">我的工具</strong>
                                </a>
                                <i></i>
                                <ul>
                                    <li><a href="${ctx}/search/view?searchType=2">药师建议</a></li>
                                    <li><a href="${ctx}/search/view?searchType=1">医师建议</a></li>
                                    <li><a href="${ctx}/search/view?searchType=3">对症下药</a></li>
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
                <shiro:authenticated>
                    <!--登录后-->
                    <div class="top-widget clearfix" style="padding-top:14px;">
                        <div class="inLogin fr">
                            <ul class="sf-menu">
                                <li class="">
                                    <a href="javascript:;" class="first-level">
                                        <span><img src="${ctxStatic}/images/upload/_user-img.png" alt=""></span>
                                    </a>
                                    <ul>
                                        <li><a href="#"><span><em>我的中心</em></span></a></li>
                                        <li><a href="${ctx}/logout"><span><em>退出登录</em></span></a></li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </shiro:authenticated>
                <shiro:notAuthenticated>
                    <div class="top-widget clearfix">
                        <p><a href="javascript:;" class="button button-color fx-btn-1">登录</a><a href="javascript:;" class="button color-blue fx-btn-2">注册</a></p>
                    </div>
                </shiro:notAuthenticated>

            </div>
        </div>
    </div>
</div>
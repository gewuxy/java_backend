<%--
  Created by IntelliJ IDEA.
  User: weilong
  Date: 2017/7/25
  Time: 13:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="v2-top v2-header1 fixed-nav">
    <div class="v2-top-main">
        <div class="page-width clearfix">
            <div class="logo" >
                <a href="${statics}/" >
                    <img src="${statics}/images/v2/logo.png" alt=""  />
                </a>
            </div><!-- end of logo -->

            <div class="v2-top-item">
                <!-- S nav -->
                <nav class="v2-nav">
                    <div class="main-nav clearfix" >
                        <ul class="sf-menu" >
                            <li >
                                <a class="first-level" target="_blank" href="/"><strong class="first-level-min">首页</strong></a><i></i>
                            </li>
                            <li >
                                <a class="first-level" target="_blank" href="javascript:;"><strong
                                        class="first-level-min">健康动态</strong></a><i></i>
                                <ul>
                                    <li><a href="${base}/search/news/list">医药动态</a></li>
                                    <li><a href="${base}/search/safe/medication/list">安全用药</a></li>
                                </ul>
                            </li>
                            <li class="current">
                                <a class="first-level" href="javascript:;"><strong class="first-level-min">我的工具</strong></a><i></i>
                                <ul>
                                    <li><a href="${base}/search/pharmacist/advice/home">药师建议</a></li>
                                    <li><a href="${base}/search/doctor/advice/home">医师建议</a></li>
                                    <li><a href="${base}/search/find/medication/home">对症找药</a></li>
                                </ul>
                            </li>
                        </ul>
                        </hal>
                    </div>
                </nav>
                <!-- E nav-->

                <!--search-->
                <div class="v2-top-search clearfix">
                    <form action="${base}/search/news/list" method="post" id="yPsearchForm" onsubmit="return checkSearch()" target="_blank" name="yPsearchForm">
                        <div class="v2-search-form v2-search-form-responsive clearfix">
                            <!--药品通用名/商品名/批准文号-->
                            <input type="text"  placeholder="查找新闻" id="searchingNews" name="searchingNews" class="form-text" >
                            <button type="submit" class="form-btn" ><span></span></button>
                        </div>
                    </form>
                </div>
                <script>
                    function checkSearch(){
                        //var searchingNews = $.trim($("#searchingNews").val());
                        var searchingNews = $("#searchingNews").val();
                        if(searchingNews == ''){
                            layer.tips('请输入搜索条件', '#searchingNews', {
                                tips: [1, '#3595CC'],
                                time: 3000
                            });
                            return false;
                        }
                        return true;
                    }
                </script>

                <!--新增的登录注册内容-->
                <div class="top-widget clearfix">
                    <p><a href="javascript:;" class="button button-color fx-btn-1">登录</a><a href="javascript:;" class="button color-blue fx-btn-2">注册</a></p>
                </div>
                <!--登录后-->
                <!--<div class="top-widget clearfix" style="padding-top:0;">-->
                <!--&lt;!&ndash; S inLogin &ndash;&gt;-->
                <!--<div class="inLogin fr">-->
                <!--<ul class="sf-menu">-->
                <!--<li class="">-->
                <!--<a href="javascript:;" class="first-level">-->
                <!--<span><img src="./images/upload/_user-img.png" alt=""></span>-->
                <!--</a>-->
                <!--<ul>-->
                <!--<li><a href="#"><span><em>我的中心</em></span></a></li>-->
                <!--<li><a href="#"><span><em>退出登录</em></span></a></li>-->
                <!--</ul>-->
                <!--</li>-->
                <!--</ul>-->
                <!--</div>-->
                <!--&lt;!&ndash; E inLogin &ndash;&gt;-->
                <!--</div>-->
            </div>
        </div>
    </div>
</div>


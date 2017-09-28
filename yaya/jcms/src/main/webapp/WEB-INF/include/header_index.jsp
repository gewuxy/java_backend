<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/3/6
  Time: 13:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="v2-top v2-header1 fixed-nav">
    <div class="v2-top-main">
        <div class="page-width clearfix">
            <div class="logo" >
                <a href="${base}/" >
                    <img src="${statics}/images/v2/logo.png" alt=""  />
                </a>
            </div><!-- end of logo -->

            <div class="v2-top-item">
                <!-- S nav -->
                <nav class="nav">
                    <div class="main-nav clearfix" >
                        <ul class="sf-menu" >
                            <!--<li class="current">
                                <a class="first-level" target="_blank" href="${base}/"><strong class="first-level-min">首页</strong></a><i></i>
                            </li>-->
                            <!-- todo 注释掉官网头部的菜单 -->
                            <!--<li>
                                <a class="first-level" style="cursor: pointer;"><strong
                                        class="first-level-min">健康动态</strong></a><i></i>
                                <ul>
                                    <li><a href="http://www.medcn.cn/txw!getYydtNewsList">医药动态</a></li>
                                    <li><a href="http://www.medcn.cn/anquanxinwen.html">安全用药</a></li>
                                </ul>
                            </li>
                            <li>
                                <a class="first-level" style="cursor:pointer;"><strong class="first-level-min">我的工具</strong></a><i></i>
                                <ul>
                                    <li><a href="http://www.medcn.cn/pharmacist.html">药师建议</a></li>
                                    <li><a href="http://www.medcn.cn/cyp!findDoctAdvice">医师建议</a></li>
                                    <li><a href="http://www.medcn.cn/jiankangjsp/dzzy_select.jsp">对症找药</a></li>
                                </ul>
                            </li>
                            -->
                        </ul>
                        </hal>
                    </div>
                </nav>
                <!-- E nav-->

                <!--search-->
                <%@include file="/WEB-INF/include/search.jsp"%>
            </div>
        </div>
    </div>
</div>

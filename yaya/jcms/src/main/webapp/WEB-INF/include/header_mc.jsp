<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/3/6
  Time: 17:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="v2-top v2-header1 fixed-nav">
    <div class="v2-top-main">
        <div class="page-width clearfix">
            <div class="logo" >
                <a href="${base}/" >
                    <img src="${statics}/images/v2/logo-phone.png" alt=""  />
                </a>
            </div><!-- end of logo -->
            <div class="v2-top-item">
                <!-- S nav -->
                <nav class="nav">
                    <div class="main-nav phonePage-main-nav clearfix" >
                        <ul class="sf-menu" >
                            <li <c:if test="${type == 'hlyy'}">class="current"</c:if> >
                                <a class="first-level" href="${base}/mc/hlyy"><strong>合理用药</strong></a>
                            </li>
                            <li <c:if test="${type=='yis'}">class="current"</c:if>>
                                <a class="first-level last" href="${base}/mc/yis"><strong>YaYa医师</strong></a>

                            </li>
                            <!--<li <c:if test="${type=='yaos'}">class="current"</c:if>>
                                <a class="first-level" href="${base}/mc/yaos"><strong>YaYa药师</strong></a>
                            </li>-->
                        </ul>
                    </div>
                </nav>
                <!-- E nav-->
            </div>
        </div>
    </div>
</div>

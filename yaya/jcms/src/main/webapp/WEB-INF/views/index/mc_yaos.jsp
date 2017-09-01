<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/3/6
  Time: 17:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>移动中心</title>
    <%@include file="/WEB-INF/include/staticResource.jsp"%>
    <link rel="stylesheet" href="${statics}/css/jk-app.css">
    <link href="${statics}/css/yaos-app.css" rel="stylesheet" type="text/css" />
    <!--旧版本兼容-->
    <style>
        .content,.download .dl-content,.download .dl-btn, .intr-bj, .intr-bj-2,.intr-bj-3,.rjts .tese,.gzwm,.ewm,.weibo,.number,.lxwm,.lxhm,.yjfk-title,.yjfk-lxfs,.yjfk-tx,.ndyj,.yis-content,.yis-intr-bj, .yis-intr-bj-2,.yis-intr-bj-3
        ,.yaos-content,.yaos-intr-bj,.yaos-intr-bj-2,.yaos-intr-bj-3,.qa,.zg-content,.zg-zw,.zw-title,.gxrz,.mzsm{
            float: none;
            width:1024px;
            margin-right: auto;
            margin-left: auto;}
    </style>
</head>
<body>
<!--头部 -->
<div id="wrapper">
    <%@include file="/WEB-INF/include/header_mc.jsp"%>
    <div class="v2-sub-main clearfix">
        <!--顶部介绍-->
        <div class="yaos-intr">
            <div class="yaos-content">
                <div class="yaos">
                    <img src="${statics}/images/yaoshi.png"  />
                    <div class="yaospt">
                        <p>YaYa药师</p>
                        <p>因为有您</p>
                    </div>
                    <div class="yaerw">
                        <div class="yaosdown">
                            <p><a href="http://www.medcn.cn/apksoft/yaoshiban_1.1.2.apk" > <input name="button" type="button" class="yaos-azxz" value="Android下载" /></a> </p>
                            <p><a href="http://itunes.apple.com/cn/app/id717593961" target="_blank"><input name="" type="button" class="yaos-iosxz" value="iPhone下载" /></a></p>
                        </div>
                        <a href="http://www.medcn.cn/return_yaos_vers.jsp"><img src="${statics}/images/yao-erweima.png" width="130"/></a>
                    </div>
                </div>
            </div>
        </div>

        <!--详情介绍-->
        <div class="function-intr-w">
            <div class="yaos-intr-bj">
                <div class="yaos-intr-wz"><p><span>药师建议 </span></p>
                    <h1>由国家注册的执业药师持续提供的专业建议，给您日常健康指导，生活安心无忧。</h1>
                </div>
            </div>
        </div>
        <div class="function-intr">
            <div class="yaos-intr-bj-2">
                <div class="yaos-intr-wz">
                    <p><span>医师建议</span></p>
                    <h1>由国家注册的执业药师持续提供的专业建议，给您日常健康指导，生活安心无忧。</h1>
                </div>
            </div>
        </div>
        <div class="function-intr-w">
            <div class="yaos-intr-bj-3">
                <div class="yaos-intr-wz"><p><span>药品说明书</span></p>
                    <h1>国家食品药品监督管理总局（CFDA）批准的说明书85501份。</h1>
                </div>
            </div>
        </div>

        <!--软件特色-->
        <%@include file="/WEB-INF/include/feature.jsp"%>

    </div>
</div>
<%@include file="/WEB-INF/include/footer.jsp"%>
</div>

<div class="gotop-wrapper">
    <a class="gotop" href="javascript:;" >回到顶部</a>
</div>
<div class="v2-help-fixed">
    <a href="http://www.medcn.cn/app_bz.jsp?tag=jk" class="v2-help-button">帮助中心</a>
</div>
</body>
</html>

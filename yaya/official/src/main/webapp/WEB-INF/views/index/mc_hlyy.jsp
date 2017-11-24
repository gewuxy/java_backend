<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/3/6
  Time: 17:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>专业用药指导及健康管理平台-合理用药 | 敬信科技</title>
    <meta name="description" content="合理用药，呵护您的用药健康，带给您最贴心的用药指导。">
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
    <%@ include file="/WEB-INF/include/common_js.jsp" %>
    <link rel="stylesheet" href="${ctxStatic}/css/jk-app.css">
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
<div id="wrapper">
    <%@include file="/WEB-INF/include/header_mc.jsp"%>

    <div class="v2-sub-main clearfix">
        <!--顶部介绍-->
        <div class="intr">
            <div class="content">
                <div class="heli">
                    <img src="http://www.medcn.cn/aimages/heli.png"  />
                    <div class="hehu">
                        <p>合理用药</p>
                        <p>呵护您的用药健康</p>
                    </div>
                    <div class="heli-intr">
                        <p>合理用药YaYa服务于全体有用药需求的人群，以涵盖市售99%</p>
                        <p>以上的药品数据及强大的专业医药人士支撑，带给您最贴心的</p>
                        <p>用药指导。</p>
                    </div>

                    <div class="erw">
                        <div class="jkdown">
                            <p>支持平台：</p>
                            <p>扫描二维码下载>></p>
                        </div>
                        <a href="http://www.medcn.cn/return_jk_vers.jsp"><img src="http://www.medcn.cn/aimages/jk-erweima.png" width="130"/></a>
                    </div>

                </div>
            </div>
        </div>

        <!--下载按钮-->
        <div class="download">
            <div class="dl-content">
                <span>ios版</span> <span> iPad版 </span><span>Andriod版</span>
            </div>

            <div class="dl-btn">
                <div class="dl-wz1"><a href="https://itunes.apple.com/cn/app/he-li-yong-yao-yaya/id589496796?l=en&mt=8" target="_blank">免费下载<span></span></a></div>
                <div class="dl-wz2"><a href="https://itunes.apple.com/cn/app/id672613903" target="_blank">免费下载<span> </span></a><span></span></div>
                <div class="dl-wz3"><a href="http://www.medcn.cn/apksoft/HeLiYongYao_3.2.5.apk">免费下载<span> </span></a><span></span></div>
            </div>
        </div>

        <!--详情介绍-->
        <div class="function-intr-w">
            <div class="intr-bj">
                <div class="intr-wz"><span>焕然一新</span>体验升级  更加贴心</div>
            </div>
        </div>

        <div class="function-intr">
            <div class="intr-bj-2"><div class="intr-wz">
                <p> 医师建议  药师建议  药品说明书</p><span>应有尽有</span></div>
            </div>
        </div>

        <div class="function-intr-w">
            <div class="intr-bj-3">
                <div class="intr-wz" style="width:240px"><p><span>我的病历</span></p>
                    记录我的病历  有惊喜哦！
                </div>
            </div>
        </div>

        <!--软件特色-->
        <%@include file="/WEB-INF/include/feature.jsp"%>

    </div>


    <%@include file="/WEB-INF/include/footer.jsp"%>
</div>
<script src="${ctxStatic}/js/v2/stickUp.min.js"></script>
<script src="${ctxStatic}/js/v2/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript">
    /*固定栏*/
    jQuery(function($) {
        $(document).ready( function() {
            $('.fixed-nav').stickUp({
                marginTop: 'auto'
            });
        });
    });


</script>
<!--旧版本代码-->
<!--回到顶部-->
<div class="gotop-wrapper">
    <a class="gotop" href="javascript:;" >回到顶部</a>
</div>
<div class="v2-help-fixed">
    <a href="http://www.medcn.cn/app_bz.jsp?tag=jk" class="v2-help-button">帮助中心</a>
</div>
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<%
    String userAgent = request.getHeader("User-Agent");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>中国首个医生移动学术会议交流平台-YaYa医师 | 敬信科技</title>
    <meta name="description" content="查找您感兴趣的学术会议，寻找探讨专业知识的群体。">
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
    <%@ include file="/WEB-INF/include/common_js.jsp" %>
    <link rel="stylesheet" href="${ctxStatic}/css/jk-app.css">
    <link href="${ctxStatic}/css/yis-app.css" rel="stylesheet" type="text/css" />

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

    <!--顶部介绍-->
    <div class="v2-sub-main clearfix">
        <div class="yis-intr">
            <div class="yis-content">
                <div class="yis">
                    <img src="${ctxStatic}/images/yaya.png"  />
                    <div class="yispt">
                        <p>YaYa医师</p>
                        <p>每位医生都是一个品牌</p>
                    </div>
                    <div class="what">
                        <a id="jxvideo" href="http://www.medcn.cn/aimages/yaya.mp4"><img src="${ctxStatic}/images/what.png"/><font style="color:#fff;">点击了解[YaYa]</font></a>
                    </div>
                    <div class="yierw">
                        <div class="yisdown">
                            <p><a href="http://139.199.170.178/apkfile/YaYa_v7.1.3.apk"> <input name="button" type="button" class="yis-azxz" value="Android下载" /></a></p>
                            <p><a href="https://itunes.apple.com/cn/app/id669352079" target="_blank"><input name="" type="button" class="yis-iosxz" value="iPhone下载" /></a></p>
                        </div>
                        <a href="http://www.medcn.cn/return_yis_vers.jsp"><img src="${ctxStatic}/images/erweima.png" width="130"/></a>
                    </div>
                </div>
            </div>
        </div>

        <!--详情介绍-->
        <div class="function-intr-w">
            <div class="yis-intr-bj">
                <div class="yis-intr-wz">
                    <p><span>柳叶叨 </span></p>
                    <p>及时获取当前最前言、</p>
                    <p>最热门的医疗资讯、</p>
                    <p>话题和最专业临床技术与经验</p>
                </div>
            </div>
        </div>

        <div class="function-intr">
            <div class="yis-intr-bj-2">
                <div class="yis-intr-wz">
                    <p><span>探索</span></p>
                    <p>公众号、医药新闻、圈子、数据中心</p>
                    <p>更多爱不释手的功能等你来。</p>

                </div>
            </div>
        </div>

        <div class="function-intr-w">
            <div class="yis-intr-bj-3">
                <div class="yis-intr-wz">
                    <p><span>我的会议</span></p>
                    <p>查找您有兴趣的学术会议，</p>
                    <p>寻找探讨专业知识的群体</p>
                </div>
            </div>
        </div>
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
<script src="${ctxStatic}/js/v2/jquery.fancybox-1.3.4.pack.js"></script>
</body>
</html>

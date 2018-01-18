<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/18
  Time: 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title></title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.min.css" />
    <link rel="stylesheet" href="${ctxStatic}/css/audio-black.css">
    <script src="${ctxStatic}/js/jquery.min.js"></script>
    <script src="${ctxStatic}/js/slide.js"></script>
    <script src="${ctxStatic}/js/swiper.jquery.min.js"></script>
    <script src="${ctxStatic}/js/audio.js"></script>
    <script src="${ctxStatic}/js/layer/layer.js"></script>
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="${ctxStatic}/js/screenfull.min.js"></script>
    <script src="${ctxStatic}/js/popupAudioPalyer.js"></script>

</head>
<body>
<!--弹出星评-->
<div class="layer-grade-popup layer-grade-star-box">
    <div class="layer-hospital-popup-title">
        <strong>&nbsp;</strong>
        <div class="layui-layer-close"><img src="./images/popup-close.png" alt=""></div>
    </div>
    <div class="layer-grade-main clearfix">
        <div class="metting-grade-info hidden-box">
            <div class="title">【新手指引】您好，会讲</div>
            <div class="main">&nbsp;&nbsp;CSPmeeting主要以邮箱作为账号进行注册，注册后您的邮箱将会收到一封激活邮件，点击链接即可完成账号激活；同时支持使用手机验证码或敬信数字平台（含YaYa医师会议管理系统和PRM患者管理系统）/微信/微博/Facebook/Twitter等第三方账号授权登录。包括本协议期限内的用户所使用的各项服务和软件的升级和更新。</div>
        </div>
        <div class="metting-star">
            <div class="star-title">综合评分</div>
            <div class="star-box star-max"><div class="star"><span class="full"></span><span class="half"></span><span class="null"></span><span class="null"></span><span class="null"></span></div><div class="grade ">3.6分</div></div>
            <div class="star-list hidden-box clearfix">
                <div class="star-list-row clearfix">
                    <div class="fr">
                        <div class="star-box star-min"><div class="star"><span class="full"></span><span class="half"></span><span class="null"></span><span class="null"></span><span class="null"></span></div><div class="grade ">3.6分</div></div>
                    </div>
                    <div class="fl"> 内容实用</div>
                </div>
                <div class="star-list-row clearfix">
                    <div class="fr">
                        <div class="star-box star-min"><div class="star"><span class="full"></span><span class="half"></span><span class="null"></span><span class="null"></span><span class="null"></span></div><div class="grade ">3.6分</div></div>
                    </div>
                    <div class="fl"> 整体安排</div>
                </div>
                <div class="star-list-row clearfix">
                    <div class="fr">
                        <div class="star-box star-min"><div class="star"><span class="full"></span><span class="half"></span><span class="null"></span><span class="null"></span><span class="null"></span></div><div class="grade ">3.6分</div></div>
                    </div>
                    <div class="fl"> 语言表达</div>
                </div>
                <div class="star-list-row clearfix">
                    <div class="fr">
                        <div class="star-box star-min"><div class="star"><span class="full"></span><span class="half"></span><span class="null"></span><span class="null"></span><span class="null"></span></div><div class="grade ">3.6分</div></div>
                    </div>
                    <div class="fl"> 课件质量</div>
                </div>
                <div class="star-list-row clearfix">
                    <div class="fr">
                        <div class="star-box star-min"><div class="star"><span class="full"></span><span class="half"></span><span class="null"></span><span class="null"></span><span class="null"></span></div><div class="grade ">3.6分</div></div>
                    </div>
                    <div class="fl"> 学员互动</div>
                </div>
            </div>
            <div class="footer-row">参与评分人数：2人</div>
        </div>
    </div>
</div>
<script>

</script>
</body>
</html>
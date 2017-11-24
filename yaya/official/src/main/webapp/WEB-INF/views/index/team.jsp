<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/3/6
  Time: 17:13
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>组员列表</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
    <%@ include file="/WEB-INF/include/common_js.jsp" %>
</head>
<body>
<div id="wrapper">
    <%@include file="/WEB-INF/include/header_common.jsp"%>

    <div class="v2-banner v2-banner-notPadding">

        <!-- S slideshow -->
        <div class="slideshow carousel clearfix" style="height:140px; overflow:hidden;">
            <div id="carousel-05">
                <div class="carousel-item">
                    <div class="carousel-img">
                        <a href="${base}/joinus">
                            <img src="${statics}/images/upload/teamListBanner.jpg" alt="" />
                        </a>
                    </div>
                </div>
            </div>
            <div class="carousel-btn carousel-btn-fixed" id="carousel-page-05"></div>

        </div>
        <!-- E slideshow -->


    </div>

    <div class="v2-sub-main">


        <div class="v2-full-area v2-banner-gray v2-about-team" id="about-maps-01" style="padding-bottom:20px;">
            <div class="page-width clearfix">
                <div class="v2-about-team-item">
                    <ul>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team1.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">王昶</span><br />防禁自心</div>
                            </a>
                        </li>

                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team14.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">崔秋红</span><br />成功源于不懈的努力</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team26.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">陈玉玲</span><br />我们必须拿我们所有的，去换我们所没有的</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team16.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">胡湛</span><br />积一时之跬步,臻千里之遥程。</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team8.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">黄妹仕</span><br />懂得倾听别人的忠告。</div>
                            </a>
                        </li>
                        <li class="last">
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team7.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">黄幸欣</span><br />越努力越幸运！</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team10.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">姜宇坤</span><br />你有多努力就有多幸运</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team2.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">李欣</span><br />一切胜利都是制度的胜利</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team21.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">李轩</span><br />志高山峰矮，路从脚下伸。</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team15.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">梁少兵</span><br />宠辱不惊，看庭前花开花落；去留无意，望天空云卷云舒。</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team19.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">梁胜蓝</span><br />接受自己的极限，我们才能超越自己</div>
                            </a>
                        </li>
                        <li class="last">
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team3.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">林晓美</span><br />财上平如水，人中直似衡</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team25.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">林桢</span><br />不卑不亢，不骄不躁</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team12.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">刘长玲</span><br />等不到天黑，烟火不会太完美!</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team9.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">陆海升</span><br />一切皆有可能！</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team6.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">谭清月</span><br />好的创意都不是脱离逻辑的天马行空</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team24.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">佟一鹏</span><br />世界上没有什么事是一顿饭不能解决的，如果有，那就两顿！</div>
                            </a>
                        </li>
                        <li  class="last">
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team17.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">蔡曲</span><br />若不给自己设限，则人生中就没有限制你发挥的藩篱。</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team5.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">王东</span><br />想象力比知识更重要。</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team13.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">王三妹</span><br />阳光总在风雨后</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team20.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">巫彩祥</span><br />书山有路勤为径，学海无崖苦作舟</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team23.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">吴玉姬</span><br />Wisdom is to the mind what health is to the body.</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team11.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">郑航</span><br />沉醉天空海阔，斟酌一盏清波。</div>
                            </a>
                        </li>
                        <li class="last">
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team22.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">郑斌</span><br />天下没有白吃的午餐，只有白喝的开水</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${statics}/images/upload/team18.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">周星杰</span><br />你不坚持和做逃兵有什么区别</div>
                            </a>
                        </li>
                    </ul>

                </div>
            </div>
        </div>
    </div>

    <%@include file="/WEB-INF/include/footer.jsp"%>
</div>
<!--弹出层-->
<script src="${statics}/js/v2/stickUp.min.js"></script>
<script src="${statics}/js/v2/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript">
    /*固定栏*/
    jQuery(function($) {
        $(document).ready( function() {
            $('.fixed-nav').stickUp({
                marginTop: 'auto'
            });
        });
    });

    $(window).bind("load resize",function(){
        $("#carousel-05").carouFredSel({
            width       : '100%',
            items		: { visible	: 1 },
            auto 	  	: { pauseOnHover: true, timeoutDuration:5000 },
            swipe    	: { onTouch:true, onMouse:true },
            pagination 	: "#carousel-page-05"
            //prev 		: { button:"#carousel-prev-05"},
            //next 		: { button:"#carousel-next-05"},
            //scroll 		: {	fx : "crossfade" }
        });
    });


</script>
</body>
</html>

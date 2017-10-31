<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/17
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> 会议资源 - 共享资源 - 已获取 </title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.min.css" />
    <link rel="stylesheet" href="${ctxStatic}/css/audio-black.css">
</head>

<body>
    <!-- main -->
    <div class="g-main clearfix">
        <!-- header -->
        <%@include file="resHeader.jsp"%>

        <div class="tab-bd">
            <div class="table-box-div1 mar-btm-1">
                <div class="table-top-box clearfix">
                    <a href="${ctx}/func/res/share/list" class="mask-le "><span class="icon iconfont icon-minIcon3"></span>共享资源库</a>
                    <a href="${ctx}/func/res/acquired/list" class="mask-le cur"><span class="icon iconfont icon-minIcon8"></span>已获取</a>
                </div>
                <div class="resource-list clearfix">
                    <div class="row clearfix">
                        <c:forEach items="${page.dataList}" var="r">
                            <div class="col-lg-response">
                                <div class="resource-list-box">
                                    <div class="resource-list-item">
                                        <div class="resource-img ">
                                            <img src="${ctxStatic}/img/_resource-img-01.jpg" alt="" class="img-response">
                                            <%--<img src="${r.coverUrl}">--%>
                                            <div class="resource-link">
                                                <a href="#" class="resource-icon-play popup-player-hook" courseId="${r.id}">
                                                    <i></i>
                                                    预览
                                                </a><a href="${ctx}/func/meet/edit?courseId=${r.id}" class="resource-icon-edit">
                                                <i></i>
                                                立即发布
                                            </a>
                                            </div>
                                        </div>
                                        <div class="resource-info">
                                            <div class="fl">
                                                <img src="${ctxStatic}/img/_metting-min-img.jpg" alt="">
                                                    <%--<img src="${r.avatar}" alt="">--%>
                                            </div>
                                            <div class="oh">
                                                <h3 >${r.pubUserName}</h3>
                                                <p>${r.username}</p>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                </div>

                <%@include file="/WEB-INF/include/pageable.jsp"%>
            </div>
        </div>
    </div>
    <!-- main end-->
    <form id="pageForm" name="pageForm" action="${ctx}/func/res/acquired/list" method="post">
        <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageNum" id="pageNum">
        <input type="hidden" name="jump" value="0">
    </form>

    <div class="mask-wrap">
        <div class="dis-tbcell">
            <div class="distb-box fx-mask-box-1">
                <div class="mask-hd clearfix">
                    <h3 class="font-size-1">内容预览</h3>
                    <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
                </div>
                <div class="slider-box slider-wrap fx-mask-box-1">
                    <ul class="slides">
                        <li>
                            <img src="http://img.emao.net/act/material/nd/kn/nkrg.jpg">
                            <span class="txt">
                    	文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述
                    	</span>
                        </li>
                        <li>
                            <img src="http://img.emao.net/act/material/nd/kn/nkrg.jpg">
                            <span class="txt">
                    	文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述
                    	</span>
                        </li>
                        <li>
                            <img src="http://img.emao.net/act/material/nd/kn/nkrg.jpg">
                            <span class="txt">
                    	文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述
                    	</span>
                        </li>
                        <li>
                            <img src="http://img.emao.net/act/material/nd/kn/nkrg.jpg">
                            <span class="txt">
                    	文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述
                    	</span>
                        </li>
                        <li>
                            <img src="http://img.emao.net/act/material/nd/kn/nkrg.jpg">
                            <span class="txt">
                    	文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述文字内容描述
                    	</span>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="distb-box fx-mask-box-2">
                <div class="mask-hd clearfix">
                    <h3 class="font-size-1">骨科常见通病的处理-会议转载</h3>
                    <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
                </div>
                <div class="mask-share-box">
                    <p class="top-txt color-black"><i class="color-blue">免费分享</i>会议转载后可立即使用</p>
                    <p>账户剩余<span class="color-black">350象数</span>，可继续<a href="" class="color-blue">充值</a>增加象数值</p>
                </div>
                <div class="sb-btn-box p-btm-1 t-right">
                    <button class="close-button-fx cur">确认</button>
                    <button class="close-button-fx">取消</button>
                </div>
            </div>
            <div class="distb-box fx-mask-box-3">
                <div class="mask-hd clearfix">
                    <h3 class="font-size-1">全院血糖管理-会议转载</h3>
                    <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
                </div>
                <div class="mask-share-box">
                    <p class="top-txt color-black">转载会议需使用<i class="color-blue">5000象数</i>，是否立即支付？</p>
                    <p>账户剩余<span class="color-black">350象数</span>，可继续<a href="" class="c-3">充值</a>增加象数值</p>
                </div>
                <div class="sb-btn-box p-btm-1 t-right">
                    <button class="close-button-fx cur">确认</button>
                    <button class="close-button-fx">取消</button>
                </div>
            </div>
            <div class="distb-box fx-mask-box-4">
                <div class="mask-hd clearfix">
                    <h3 class="font-size-1">2015年度关节质控会议-会议转载</h3>
                    <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
                </div>
                <div class="mask-share-box">
                    <p class="top-txt color-black">转载会议需将对每位参与者支付<i class="color-blue">200象数</i></p>
                    <p class="top-txt">请设定参与人数为<input type="text" class="bor-input-box">, 需支付<span>0象数</span></p>
                    <p>账户剩余<span class="color-black">350象数</span>，可继续<a href="" class="color-blue">充值</a>增加象数值</p>
                </div>
                <div class="sb-btn-box p-btm-1 t-right">
                    <button class="close-button-fx cur">确认</button>
                    <button class="close-button-fx">取消</button>
                </div>
            </div>
        </div>
    </div>
    <!--弹出播放器-->
    <div class="layer-hospital-popup layer-black  layer-hospital-popup-hook">
        <div class="swiperBox mettingSwiperBox clearfix">
            <!--预览弹出层-->
            <div class="metting-swiper">
                <!-- Swiper -->
                <div class="swiper-container swiper-container-horizontal swiper-container-hook">
                    <!--根据ID 切换 PPT列表-->
                    <div class="swiper-wrapper" >
                        <div class="swiper-slide swiper-slide-active" data-num="0" audio-src="${ctxStatic}/upload/audio/30179313.mp3">
                            <div class="swiper-picture" style=" background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div>
                        </div>
                        <div class="swiper-slide swiper-slide-next" data-num="1"  audio-src="https://raw.githubusercontent.com/kolber/audiojs/master/mp3/bensound-dubstep.mp3">
                            <div class="swiper-picture" style="background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div>
                        </div>
                        <div class="swiper-slide" data-num="2" audio-src="${ctxStatic}//upload/audio/30179313.mp3"><div class="swiper-picture" style="display:block; background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div><div class="swiper-slide-metting-audio"></div></div>
                        <div class="swiper-slide" data-num="3" audio-src="${ctxStatic}//upload/audio/30179313.mp3"><div class="swiper-picture" style="display:block; background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div><div class="swiper-slide-metting-audio"></div></div>
                    </div>
                    <!-- Add Pagination -->

                    <div class="metting-btn-item clearfix">
                        <span class="swiper-button-prev swiper-popup-button-prev-hook metting-button swiper-button-disabled"></span>
                        <div class="swiper-pagination swiper-pagination-fraction"><span class="swiper-pagination-current">1</span> / <span class="swiper-pagination-total">4</span></div>
                        <span class="swiper-button-next swiper-popup-button-next-hook metting-button"></span>
                    </div>

                </div>
            </div>
            <!--音频文件-->
            <div class="clearfix boxAudio t-center" >
                <div class="audio-metting-box" style="">
                    <audio controls=true id="video1" src=""></audio>
                </div>
            </div>
            <!--切换全屏按钮-->
            <button class="swiper-changeFullSize-button changeFullSize-icon changeFull-hook" title="全屏观看"><span></span></button>

            <!--标题栏-->
            <div class="swiper-slide-title">
                <a href="#" class="fr">立即发布</a>
                <div class="title overflowText">赛默飞联合阜外医院分子诊断中心安塞物共建精诊断中心安塞物共建精诊断中赛默飞联合阜外医院分子诊断中心安塞物共建精诊断中心安塞物共建精诊断中</div>
            </div>

        </div>
    </div>
    <div class="layer-hospital-popup layer-black  layer-hospital-popup-fullSize">
        <div class="swiperBox mettingSwiperBox clearfix">
            <!--预览弹出层-->
            <div class="metting-swiper">
                <!-- Swiper -->
                <div class="swiper-container swiper-container-horizontal swiper-full-container-hook">
                    <!--根据ID 切换 PPT列表-->
                    <div class="swiper-wrapper" >
                        <div class="swiper-slide swiper-slide-active" data-num="0" audio-src="${ctxStatic}/upload/audio/30179313.mp3">
                            <div class="swiper-picture" style=" background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div>
                        </div>
                        <div class="swiper-slide swiper-slide-next" data-num="1"  audio-src="https://raw.githubusercontent.com/kolber/audiojs/master/mp3/bensound-dubstep.mp3">
                            <div class="swiper-picture" style="background-image:url('./upload/images/_hyfb-03-1-img.jpg"></div>
                        </div>
                        <div class="swiper-slide" data-num="2" audio-src="${ctxStatic}/upload/audio/30179313.mp3"><div class="swiper-picture" style="display:block; background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div><div class="swiper-slide-metting-audio"></div></div>
                        <div class="swiper-slide" data-num="3" audio-src="${ctxStatic}/upload/audio/30179313.mp3"><div class="swiper-picture" style="display:block; background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div><div class="swiper-slide-metting-audio"></div></div>
                    </div>
                    <!-- Add Pagination -->
                    <span class="swiper-button-prev metting-button swiper-popup-full-button-prev swiper-button-disabled"></span>
                    <div class="metting-btn-item clearfix">
                        <div class="swiper-pagination swiper-pagination-fraction"><span class="swiper-pagination-current">1</span> / <span class="swiper-pagination-total">4</span></div>
                    </div>
                    <span class="swiper-button-next metting-button swiper-popup-full-button-next"></span>

                </div>
            </div>
            <!--音频文件-->
            <div class="clearfix boxAudio t-center" >
                <div class="audio-metting-box" style="">
                    <audio controls=true id="video2" src=""></audio>
                </div>
            </div>
            <!--切换全屏按钮-->
            <button class="swiper-changeFullSize-button changeFullSize-icon changeFull-hook" title="全屏观看"><span></span></button>
            <!--标题栏-->
            <div class="swiper-slide-title">
                <a href="#" class="fr">立即发布</a>
                <div class="title overflowText">赛默飞联合阜外医院分子诊断中心安塞物共建精诊断中心安塞物共建精诊断中赛默飞联合阜外医院分子诊断中心安塞物共建精诊断中心安塞物共建精诊断中</div>
            </div>
        </div>
    </div>
    <script src="${ctxStatic}/js/jquery.min.js"></script>
    <script src="${ctxStatic}/js/slide.js"></script>
    <script src="${ctxStatic}/js/swiper.jquery.min.js"></script>
    <script src="${ctxStatic}/js/audio.js"></script>
    <script src="${ctxStatic}/js/layer/layer.js"></script>
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="${ctxStatic}/js/screenfull.min.js"></script>
    <script src="${ctxStatic}/js/main.js"></script>
    <script src="${ctxStatic}/js/popupAudioPalyer.js"></script>
<script>
    $(function(){
        // 点击预览按钮
        $(".popup-player-hook").click(function(){
            var courseId = $(this).attr("courseId");
            top.layer.open({
                type:2,
                area: ['860px', '800px'],
                fix: false, //不固定
                fixed:true,
                offset: '100px',
                title:false,
                content:'${ctx}/func/res/view?courseId='+courseId
            });
        });
    });
</script>
</body>
</html>

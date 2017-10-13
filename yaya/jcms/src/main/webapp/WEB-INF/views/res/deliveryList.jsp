<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <title> 资源平台 - CSP投屏 </title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.min.css" />
    <link rel="stylesheet" href="${ctxStatic}/css/audio-black.css">
</head>
<body>


<!-- main -->
<div class="g-main clearfix">
    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-03.png" alt="">
                </div>
                <div class="oh">
                    <p><strong>资源平台</strong></p>
                    <p>医学会议、科教培训、医学资料跨医院共享的学术共享平台</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->

    <div class="tab-hd">

        <ul class="tab-list clearfix">
            <li class="cur">
                <a href="zypt.html">CSP投屏<i></i></a>
            </li>
            <li >
                <a href="hz-gxzy.html">共享资源<i></i></a>
            </li>
        </ul>
    </div>
    <div class="tab-bd">
        <div class="table-box-div1 mar-btm-1">
            <div class="table-top-box clearfix">
                <div class="formrow t-right">
					<span class="checkboxIcon">
                        <input type="checkbox" id="popup_checkbox_2" <c:if test="${flag == 1}"> checked </c:if> class="chk_1 chk-hook">
						<label for="popup_checkbox_2" class="popup_checkbox_hook"><i class="ico"></i>&nbsp;&nbsp;开启CSPmeeting来稿功能</label>
					</span>&nbsp;
                    <span class="question-tipsHover-hook"><img src="${ctxStatic}/images/icon-question.png" alt=""></span>
                </div>
            </div>
            <div class="resource-list clearfix" id="deliveryList">
                <div class="row clearfix">
                    <c:forEach items="${page.dataList}" var="d">
                        <div class="col-lg-response">
                            <div class="resource-list-box">
                                <div class="resource-list-item">
                                    <div class="resource-img ">
                                        <img src="${d.coverUrl}" alt="" class="img-response">
                                        <div class="resource-link">
                                            <a href="#" class="resource-icon-play popup-player-hook">
                                                <i></i>
                                                预览
                                            </a><a href="metting-now-step01.html" class="resource-icon-edit">
                                            <i></i>
                                            立即发布
                                        </a>
                                        </div>
                                    </div>
                                    <div class="resource-info">
                                        <div class="fl">
                                            <img src="${d.avatar}" alt="">
                                        </div>
                                        <div class="oh">
                                            <h3 >${d.name}</h3>
                                            <p>${d.email}</p>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>

                    </c:forEach>

                    <div class="col-lg-response">
                        <div class="resource-list-box">
                            <div class="resource-list-item">
                                <div class="resource-img ">
                                    <img src="${ctxStatic}/img/_resource-img-01.jpg" alt="" class="img-response">
                                    <div class="resource-link">
                                        <a href="#" class="resource-icon-play popup-player-hook">
                                            <i></i>
                                            预览
                                        </a><a href="metting-now-step01.html" class="resource-icon-edit">
                                        <i></i>
                                        立即发布
                                    </a>
                                    </div>
                                </div>
                                <div class="resource-info">
                                    <div class="fl">
                                        <img src="${ctxStatic}/img/_metting-min-img.jpg" alt="">
                                    </div>
                                    <div class="oh">
                                        <h3 >赵悦宾</h3>
                                        <p>zhaoyuebin@126.com</p>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-response">
                        <div class="resource-list-box">
                            <div class="resource-list-item">
                                <div class="resource-img ">
                                    <img src="${ctxStatic}/img/_resource-img-01.jpg" alt="" class="img-response">
                                    <div class="resource-link">
                                        <a href="#" class="resource-icon-play popup-player-hook">
                                            <i></i>
                                            预览
                                        </a><a href="metting-now-step01.html" class="resource-icon-edit">
                                        <i></i>
                                        立即发布
                                    </a>
                                    </div>
                                </div>
                                <div class="resource-info">
                                    <div class="fl">
                                        <img src="${ctxStatic}/img/_metting-min-img.jpg" alt="">
                                    </div>
                                    <div class="oh">
                                        <h3 >赵悦宾</h3>
                                        <p>zhaoyuebin@126.com</p>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%@include file="/WEB-INF/include/pageable.jsp"%>

        </div>
    </div>
</div>

<form id="pageForm" name="pageForm" action="${ctx}/func/meet/delivery/list" method="post">
    <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
    <input type="hidden" name="pageNum" id="pageNum">
    <input type="hidden" name="isOpen" id="isOpen" value="1">
</form>


<!--弹出播放器-->
<div class="layer-hospital-popup layer-black  layer-hospital-popup-hook">
    <div class="swiperBox mettingSwiperBox clearfix">
        <!--预览弹出层-->
        <div class="metting-swiper">
            <!-- Swiper -->
            <div class="swiper-container swiper-container-horizontal swiper-container-hook">
                <!--根据ID 切换 PPT列表-->
                <div class="swiper-wrapper" >
                    <div class="swiper-slide swiper-slide-active" data-num="0" audio-src="./upload/audio/30179313.mp3">
                        <div class="swiper-picture" style=" background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div>
                    </div>
                    <div class="swiper-slide swiper-slide-next" data-num="1"  audio-src="https://raw.githubusercontent.com/kolber/audiojs/master/mp3/bensound-dubstep.mp3">
                        <div class="swiper-picture" style="background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div>
                    </div>
                    <div class="swiper-slide" data-num="2" audio-src="./upload/audio/30179313.mp3"><div class="swiper-picture" style="display:block; background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div><div class="swiper-slide-metting-audio"></div></div>
                    <div class="swiper-slide" data-num="3" audio-src="./upload/audio/30179313.mp3"><div class="swiper-picture" style="display:block; background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div><div class="swiper-slide-metting-audio"></div></div>
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
                    <div class="swiper-slide swiper-slide-active" data-num="0" audio-src="./upload/audio/30179313.mp3">
                        <div class="swiper-picture" style=" background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div>
                    </div>
                    <div class="swiper-slide swiper-slide-next" data-num="1"  audio-src="https://raw.githubusercontent.com/kolber/audiojs/master/mp3/bensound-dubstep.mp3">
                        <div class="swiper-picture" style="background-image:url('./upload/images/_hyfb-03-1-img.jpg"></div>
                    </div>
                    <div class="swiper-slide" data-num="2" audio-src="./upload/audio/30179313.mp3"><div class="swiper-picture" style="display:block; background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div><div class="swiper-slide-metting-audio"></div></div>
                    <div class="swiper-slide" data-num="3" audio-src="./upload/audio/30179313.mp3"><div class="swiper-picture" style="display:block; background-image:url('./upload/images/_hyfb-03-1-img.jpg')"></div><div class="swiper-slide-metting-audio"></div></div>
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
<script src="${ctxStatic}/js/popupAudioPalyer.js"></script>

<script>
    $(function(){
        //上传Hover 提示
        $(".question-tipsHover-hook").mouseenter(function(){
            layer.tips('CSPmeeting是全球领先的云同步回放会议系统，开通该功能可接收来自全球各地用户的学术会议投稿。', '.question-tipsHover-hook', {
                tips: [3, '#333'],
                time:2000
            });
        });

        $("#popup_checkbox_2").click(function(){
            var flag = 0;
            if($('#popup_checkbox_2').is(':checked')){
                flag = 1;
            }

            $.ajax({
                //服务器的地址
                url:"${ctx}/func/meet/delivery/change?flag="+flag,
                dataType:'json', //返回数据类型
                type:'POST', //请求类型
                contentType: "application/json; charset=utf-8",
                success: function(data) {
                    if(data.code == 0){
                       if(flag == 0){
                           //关闭投稿
                           $("#deliveryList").empty();
                           $(".page-box").empty();
                       }else{
                           window.location.href='${ctx}/func/meet/delivery/list?isOpen='+flag;
                       }
                    }
                }

            });
        });

    })
</script>

</body>
</html>
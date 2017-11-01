/**
 * Created by xingjie on 2017/9/13.
 *
 *
 */
var asAllItem = audiojs.createAll();

$(function(){

    if(asAllItem.length != 0) {
        var popupSize = $('.layer-hospital-popup').height();
        var popupPlayHeight = $('.layer-hospital-popup .boxAudio').height() + $('.swiper-slide-title').height();
        var target = $('.layer-hospital-popup-fullSize')[0];
        var popupPalyer = asAllItem[asAllItem.length - 2];
        var popupFullPalyer = asAllItem[asAllItem.length - 1];


        //播放器切换加载对应的路径
        var swiperChangeAduio = function(current){
            var swiperCurrent;

            popupPalyer.pause();
            popupFullPalyer.pause();
            // var swiperCurrent = current.find(".swiper-slide-active") ||  current.parents('.swiper-container-horizontal').find(".swiper-slide-active");
            if(current.find(".swiper-slide-active")){
                swiperCurrent  = current.find(".swiper-slide-active");
            }else if(current.parents('.swiper-container-horizontal').find(".swiper-slide-active")){
                swiperCurrent = current.parents('.swiper-container-horizontal').find(".swiper-slide-active");
            }
            var dataSrc = swiperCurrent.attr('audio-src');
            popupPalyer.load(dataSrc);
            popupFullPalyer.load(dataSrc);


            popupPalyer.play();
            popupFullPalyer.play();
        }


        //点击全屏
        $('.changeFull-hook').on('click', function() {
            //设置第一页
            if (screenfull.enabled && !screenfull.element) {
                screenfull.toggle(target);
                $(".layer-hospital-popup-fullSize").find('.swiper-wrapper').attr('style','transform: translate3d(0, 0, 0);transition-duration: 0ms;');
                $('.layer-hospital-popup-fullSize .swiperBox').height(window.screen.height - popupPlayHeight);
                $('.layer-hospital-popup-fullSize').attr('style','display:block');
                //全屏后默认静音
                popupPalyer.element.muted = true;
                popupFullPalyer.element.muted = false;
            } else {
                screenfull.exit();
                //摧毁设置后的高度
                $('.layer-hospital-popup-fullSize .swiperBox').height('')
                $('.layer-hospital-popup-hook .swiperBox').height(popupSize - popupPlayHeight);
                $('.layer-hospital-popup-fullSize').attr('style','display:none');
                //退出后全屏静音
                popupFullPalyer.element.muted = true;
                popupPalyer.element.muted = false;
            }
        });






        //触发弹出窗
        $('.popup-player-hook').on('click',function(){
            layer.open({
                type: 1,
                area: ['900px', '800px'],
                fix: false, //不固定
                title:false,
                content: $('.layer-hospital-popup-hook'),
                success:function(){


                    //弹出预览层
                    var swiperPopup = new Swiper('.swiper-container-hook', {
                        //分页
                        pagination: '.swiper-pagination',
                        // 按钮
                        nextButton: '.swiper-popup-button-next-hook',
                        prevButton: '.swiper-popup-button-prev-hook',
                        slidesPerView:1,
                        initialSlide: 0,
                        spaceBetween: 0,
                        paginationType: 'fraction',
                        centeredSlides: false,
                        inltialSlide:0,
                        slidesOffsetBefore:0,
                        onSlideChangeEnd:function(swiper){
                            swiperChangeAduio(swiper.wrapper.prevObject);
                        },
                        onSlideNextEnd: function(swiper){
                            swiperChangeAduio(swiper.wrapper.prevObject);
                            if(popupFullPalyer.element.muted == true){
                                popupFullPalyer.element.muted = true;
                            } else if(popupPalyer.element.muted == true){
                                popupPalyer.element.muted = true;
                            }
                        },
                        onSlidePrevEnd: function(swiper){
                            swiperChangeAduio(swiper.wrapper.prevObject);
                            if(popupFullPalyer.element.muted == true){
                                popupFullPalyer.element.muted = true;
                            } else if(popupPalyer.element.muted == true){
                                popupPalyer.element.muted = true;
                            }
                        },
                        onInit: function(swiper){
                            swiper.wrapper.attr('style','transform: translate3d(0, 0, 0);transition-duration: 0ms;');
                        }
                    });
                    //最大化预览层
                    var swiperFullSize = new Swiper('.swiper-full-container-hook', {
                        //分页
                        pagination: '.swiper-pagination',

                        // 按钮
                        nextButton: '.swiper-popup-full-button-next',
                        prevButton: '.swiper-popup-full-button-prev',
                        slidesPerView:1,
                        initialSlide: 0,
                        spaceBetween: 0,
                        paginationType: 'fraction',
                        inltialSlide:0,
                        slidesOffsetBefore:0,
                        onSlideChangeEnd:function(swiper){
                            swiperChangeAduio(swiper.wrapper.prevObject);
                        },
                        onSlideNextEnd: function(swiper){
                            swiperChangeAduio(swiper.wrapper.prevObject);
                            if(popupFullPalyer.element.muted == true){
                                popupFullPalyer.element.muted = true;
                            } else if(popupPalyer.element.muted == true){
                                popupPalyer.element.muted = true;
                            }
                        },
                        onSlidePrevEnd: function(swiper){
                            swiperChangeAduio(swiper.wrapper.prevObject);
                            if(popupFullPalyer.element.muted == true){
                                popupFullPalyer.element.muted = true;
                            } else if(popupPalyer.element.muted == true){
                                popupPalyer.element.muted = true;
                            }
                        },
                        onInit: function(swiper){
                            swiper.wrapper.attr('style','transform: translate3d(0, 0, 0);transition-duration: 0ms;');
                        }
                    });



                    //弹出窗默认高地
                    popupSize = 800;

                    //计算幻灯片减去播放器后高度
                    $('.layer-hospital-popup-hook .swiperBox').height(popupSize - popupPlayHeight);

                    //弹出后加载第一个
                    var audioDefaultLoad = $('.layer-hospital-popup-hook').find('.swiper-slide-active').attr('audio-src');
                    var audioFullDefaultLoad = $('.layer-hospital-popup-fullSize').find('.swiper-slide-active').attr('audio-src');

                    //默认加载
                    popupPalyer.load(audioDefaultLoad);
                    popupFullPalyer.load(audioFullDefaultLoad);

                    //默认播放
                    popupPalyer.play();
                    popupFullPalyer.play();

                    // var dataSrc = swiperCurrent.attr('audio-src');
                    // popupPalyer.load(dataSrc);
                    // popupPalyer.play();
                    // popupFullPalyer.load(dataSrc);
                    // popupFullPalyer.play();

                    //初始化全屏静音
                    popupFullPalyer.element.muted = true;

                    //双向控制
                    swiperPopup.params.control = swiperFullSize;
                    swiperFullSize.params.control = swiperPopup;



                },
                cancel :function(){
                    //摧毁设置的高度
                    $('.layer-hospital-popup .swiperBox').height('');
                    //摧毁播放器
                    popupFullPalyer.pause();
                    popupPalyer.pause();
                },
            });
        });





    }





});
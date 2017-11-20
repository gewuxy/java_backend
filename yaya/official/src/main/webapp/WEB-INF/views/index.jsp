<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>首页</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
</head>
<div id="wrapper" class="v2-medcnIndex">
    <%@include file="/WEB-INF/include/header.jsp" %>
    <div class="v2-banner bg-lightBlue">
        <div class="page-width clearfix">
            <div class="fr">
                <div class="v2-index-download-area clearfix v2-hover-shadow" style="margin-bottom:20px;">
                    <p class="t-center margin-b"><img src="${ctxStatic}/images/upload/_index_minBanner_area_img1.png" alt="合理用药APP 呵护你的健康"></p>
                    <div class="fr">
                        <p class="margin-b"><a href="http://www.medcn.cn/app_jk.jsp" class="v2-index-download-button"><img src="${ctxStatic}/images/v2/download-button-apple.png" alt=""></a></p>
                        <p><a href="http://www.medcn.cn/app_jk.jsp" class="v2-index-download-button"><img src="${ctxStatic}/images/v2/download-button-android.png" alt=""></a></p>
                    </div>
                    <div class="fl t-center"><img src="${ctxStatic}/images/upload/jk-erweima.png" alt="" style="width:90px; height:90px;"><p >扫描二维码下载</p></div>
                </div>
                <div class="oh v2-hover-shadow">
                    <a href="http://www.medcn.cn/zy_login.jsp" style=" display:block; line-height: 1; margin:0; padding:0;"><img src="${ctxStatic}/images/upload/min-ad-01.jpg" alt=""></a>
                </div>
            </div>
            <div class="fl">
                <!-- S slideshow -->
                <div class="slideshow carousel clearfix" style="width:895px ;height:450px; overflow:hidden;">
                    <div id="carousel-05">
                        <div class="carousel-item">
                            <div class="carousel-img">
                                <a href="detail.html" target="_blank">
                                    <img src="${ctxStatic}/images/upload/_banner_170228_1.jpg" alt="" />
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="carousel-btn carousel-btn-fixed" id="carousel-page-05"></div>
                </div>
                <!-- E slideshow -->
            </div>
        </div>
    </div>
    <div class="v2-main">
        <div class="page-width clearfix">
            <div class="clearfix " style="margin-bottom:20px;">
                <div class="row" id="firstRow">
                    <div class="col-lg-3">
                        <div class="v2-news-list">
                            <div class="v2-news-title">
                                <a href="list.html" class="more fr">更多&gt;&gt;</a>
                                <h3>公司动态</h3>
                            </div>
                            <div class="v2-news-main">
                                <ul>
                                    <li><a href="detail.html">"YaYa医师"亮相第六届中国国际版权</a></li>
                                    <li><a href="detail.html">"YaYa医师"亮相第六届中国国际版权</a></li>
                                    <li><a href="detail.html">"YaYa医师"亮相第六届</a></li>
                                    <li><a href="detail.html">"YaYa医师"亮相第六届中国国际版权</a></li>
                                    <li><a href="detail.html">"YaYa医师"亮相第六届中国国际版权</a></li>
                                    <li><a href="detail.html">"YaYa医师"亮相第六届中国国际版权</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="v2-entry-area clearfix"></div>
            <div class="t-center">
                <a href="#" class="v2-more-button">点击查看更多</a>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/include/footer.jsp" %>
</div>
<!--弹出层-->
<%@include file="/WEB-INF/include/markWrap.jsp" %>
<%@ include file="/WEB-INF/include/common_js.jsp" %>
<script>
    $(function(){
        $.get('/news/ajaxTrends',{'pageNum':1,'pageSize':6}, function (data) {
            if(data.code == 0){
                for(var index in data.data.dataList){
                    $("#trendsUL").append('<li title="'+data.data.dataList[index].title+'" class="shenglue"><a href="/news/viewtrend/'+data.data.dataList[index].id+'">'+data.data.dataList[index].title+'</a></li>');
                }
            }
        },'json');

        $.get('/news/pagenews',{'pageNum':pageNum,'pageSize':11},function (data) {
            if(data.code == 0){
                showNews(data);
            }
        },'json');

        $(".v2-more-button").click(function(){
            $.get('/news/pagenews',{'pageNum':pageNum+1,'pageSize':12},function (data) {
                if(data.code == 0){
                    showNews(data);
                    autoPageable = true;
                    pageNum++;
                }
            },'json');
        });
    });

    var pageNum = 1;
    var autoPageable = true;
    var autoPageTimes = 0;
    $(function(){
        $(window).scroll(function () {
            if(autoPageable){
                if(($(window).scrollTop()) >= ($(document).height() - $(window).height())){
                    // alert(pag    eNum+1);
                    $.get('/news/pagenews',{'pageNum':pageNum+1,'pageSize':12},function (data) {
                        if(data.code == 0){
                            autoPageable = false;
                            showNews(data);
                            if(autoPageTimes >=2 ){
                                autoPageable = false;
                                autoPageTimes = 0;
                            }else{
                                autoPageable = true;
                                autoPageTimes++;
                            }
                            pageNum++;
                        }
                    },'json');
                }
            }
        });
    });

    function showNews(data){
        var parentContainer;
        if(data.data.dataList.length > 0){
            for(var index in data.data.dataList){
                var keyword =data.data.dataList[index].keywords.split(/[,|，| ]/)[0];
                var addhtml = '<div class="col-lg-3">'
                    +'<div class="v2-entry-item">'
                    +'<div class="v2-entry-img">'
                    +'<a href="/news/view/'+data.data.dataList[index].id+'" target="_blank"><img src="'+data.data.dataList[index].articleImg+'" alt=""></a>'
                    +'<i class="v2-entry-classIcon"><a href="/news/view/'+data.data.dataList[index].id+'">'+keyword+'</a></i>'
                    +'</div>'
                    +'<div class="v2-entry-title">'
                    +'<h5><a href="/news/view/'+data.data.dataList[index].id+'" target="_blank">'+data.data.dataList[index].title+'</a></h5>'
                    +'<span class="time">'+formatSimple(data.data.dataList[index].createTime)+'</span>'
                    +'</div>'
                    +'</div>'
                    +'</div>';
                if(index < 3 && data.data.pageNum == 1){
                    $("#firstRow").prepend(addhtml);
                }else{
                    if((index-3)%4==0&&data.data.pageNum == 1 || index%4==0&&data.data.pageNum != 1){
                        parentContainer = $('<div class="row"></div>');
                    }
                    parentContainer.append(addhtml);
                    $(".v2-entry-area").append(parentContainer);
                }
            }
        }
    }
</script>
</body>
</html>
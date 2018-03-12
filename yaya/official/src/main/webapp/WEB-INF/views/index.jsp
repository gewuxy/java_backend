<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/3/3
  Time: 18:59
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>敬信药草园-国内首家关注合理用药的信息平台 | 敬信科技官网</title>
    <meta name="description" content="敬信·药草园致力于指导安全合理用药，为社会公众提供健康资讯、专业用药指导；为医学、药学、医务工作者提供专业的医学文献及临床指南信息服务。"/>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
    <style type="text/css">
        .shenglue{
            width: 280px;  /*必须设置宽度*/
            overflow: hidden;  /*溢出隐藏*/
            text-overflow: ellipsis; /*以省略号...显示*/
            white-space: nowrap;  /*强制不换行*/
        }
    </style>
</head>
<body>
<div id="wrapper" class="v2-medcnIndex">
    <%@include file="/WEB-INF/include/header.jsp"%>

    <div class="v2-banner bg-lightBlue">
        <div class="page-width clearfix">
            <div class="fr">
                <div class="v2-index-download-area clearfix v2-hover-shadow" style="margin-bottom:20px;">
                    <p class="t-center margin-b"><img src="${ctxStatic}/images/upload/_index_minBanner_area_img1.png" alt="合理用药APP 呵护你的健康"></p>
                    <div class="fr">
                        <p class="margin-b"><a href="${ctx}/mc/hlyy" class="v2-index-download-button"><img src="${ctxStatic}/images/v2/download-button-apple.png" alt=""></a></p>
                        <p><a href="${ctx}/mc/hlyy" class="v2-index-download-button"><img src="${ctxStatic}/images/v2/download-button-android.png" alt=""></a></p>
                    </div>
                    <div class="fl t-center"><img src="${ctxStatic}/images/upload/jk-erweima.png" alt="" style="width:90px; height:90px;"><p >扫描二维码下载</p></div>
                </div>
                <div class="oh v2-hover-shadow">
                    <a href="https://www.medyaya.cn/yyys/" style=" display:block; line-height: 1; margin:0; padding:0;"><img src="${ctxStatic}/images/upload/min-ad-01.jpg" alt=""></a>
                </div>
            </div>
            <div class="fl">
                <!-- S slideshow -->
                <div class="slideshow carousel clearfix" style="width:895px ;height:450px; overflow:hidden;">
                    <div id="carousel-05">
                        <div class="carousel-item">
                            <div class="carousel-img">
                                <img src="${ctxStatic}/images/upload/_banner_170228_1.jpg" alt="" />
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
                                <a href="${ctx}/news/trends" class="more fr">更多&gt;&gt;</a>
                                <h3>公司动态</h3>
                            </div>
                            <div class="v2-news-main">
                                <ul id="trendsUL">
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="v2-entry-area clearfix">
            </div>
            <div class="t-center" id="moreDiv">
                <a style="cursor: pointer;" class="v2-more-button">点击查看更多</a>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/include/footer.jsp"%>
</div>
<div class="gotop-wrapper index-gotop">
    <a class="gotop" href="javascript:" >回到顶部</a>
</div>
<%@include file="/WEB-INF/include/common_js.jsp"%>
<script src="${ctxStatic}/js/v2/stickUp.min.js"></script>
<script type="text/javascript">
    /*<!--轮播广告-->*/
    $(window).bind("load resize",function(){
        $("#carousel-05").carouFredSel({
            width       : '100%',
            items		: { visible	: 1 },
            auto 	  	: { pauseOnHover: true, timeoutDuration:5000 },
            swipe    	: { onTouch:true, onMouse:true },
            pagination 	: "#carousel-page-05",
            scroll 		: {	fx : "crossfade" }
        });
    });
    /*固定栏*/
    jQuery(function($) {
        $(document).ready( function() {
            $('.fixed-nav').stickUp({
                marginTop: 'auto'
            });
        });
    });

    $(function(){

        $(function(){
            $(".first").addClass("current");
        })

        // 加载公司动态
        $.get('${ctx}/news/ajaxTrends',{'pageNum':1,'pageSize':6,'type':"GSDT"}, function (data) {
            if(data.code == 0){
                for(var index in data.data.dataList){
                    $("#trendsUL").append('<li title="'+data.data.dataList[index].title+'" class="shenglue"><a href="${ctx}/news/viewtrend/'+data.data.dataList[index].id+'">'+data.data.dataList[index].title+'</a></li>');
                }
            }
        },'json');

        $.get('${ctx}/news/pagenews',{'pageNum':pageNum,'pageSize':11},function (data) {
            if(data.code == 0){
                showNews(data);
            }
        },'json');

        $(".v2-more-button").click(function(){
            $.get('${ctx}/news/pagenews',{'pageNum':pageNum+1,'pageSize':12},function (data) {
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
                    +'<a href="${ctx}/news/detail/'+data.data.dataList[index].id+'" target="_blank"><img src="'+data.data.dataList[index].articleImg+'" alt=""></a>'
                    +'<i class="v2-entry-classIcon"><a href="${ctx}/news/detail/'+data.data.dataList[index].id+'">'+keyword+'</a></i>'
                    +'</div>'
                    +'<div class="v2-entry-title">'
                    +'<h5><a href="${ctx}/news/detail/'+data.data.dataList[index].id+'" target="_blank">'+data.data.dataList[index].title+'</a></h5>'
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

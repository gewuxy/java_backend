<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>安全用药列表</title>
    <script type="text/javascript">

        var InterValObj; //timer变量，控制时间
        var count = 60; //间隔函数，1秒执行
        var curCount;//当前剩余秒数

        function sendMessage() {
            curCount = count;
            //设置button效果，开始计时
            $("#btnSendCode").attr("disabled", "true");
            $("#btnSendCode").val(curCount + "S");
            $("#btnSendCode").addClass("getCodeButton-current");
            InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
            //向后台发送处理数据
//            $.ajax({
//                type: "POST", //用POST方式传输
//                dataType: "text", //数据格式:JSON
//                url: 'Login.ashx', //目标地址
//                data: "dealType=" + dealType +"&uid=" + uid + "&code=" + code,
//                error: function (XMLHttpRequest, textStatus, errorThrown) { },
//                success: function (msg){ }
//            });
        }

        //timer处理函数
        function SetRemainTime() {
            if (curCount == 0) {
                window.clearInterval(InterValObj);//停止计时器
                $("#btnSendCode").removeClass("getCodeButton-current");
                $("#btnSendCode").removeAttr("disabled");//启用按钮
                $("#btnSendCode").val("重新发送验证码");
            }
            else {
                curCount--;
                $("#btnSendCode").val(curCount + "S");
            }
        }
    </script>
    <%@include file="/WEB-INF/include/staticResource2.jsp"%>
</head>
<body>
<div id="wrapper" class="v2-medcnIndex">
    <%@include file="/WEB-INF/include/header_index2.jsp"%>

    <div class="v2-sub-main" style="padding:40px 0 80px;">
        <div class="page-width clearfix" >
            <div class="row">
                <div class="col-lg-8 ">
                    <div class="v2-subPage-title-left">
                        <h3>安全用药</h3>
                    </div>
                    <div id="articles_div" class="v2-news-graphic v2-news-graphic-extend">
                        <!--以下模板会被copy并重设数据-->
                        <div id="article_model_div" class="v2-news-graphic-item clearfix">
                            <div class="fl v2-news-graphic-img">
                                <a id="article_detail_url" href="${base}/search/safe/medication/detail">
                                    <img id="article_img" src="${statics}/images/upload/_news-list-img01.jpg" alt="">
                                </a>
                                <i id="article_keyword" class="v2-news-graphic-classIcon"></i>
                            </div>
                            <div class="oh">
                                <h3><a id="article_title" href="${base}/search/safe/medication/detail"></a></h3>
                                <p id="article_summary" class="v2-news-graphic-info"></p>
                                <p id="article_keywords" ></p>
                                <p><span id="article_create_time" class="time fr"></span><span id="article_xfrom"></span></p>
                            </div>
                        </div>

                    </div>
                    <div id="paging_button_div" class="v2-page-box t-center">
                        <!--页面加载完后再生成分页按钮-->
                    </div>
                </div>
                <%@include file="/WEB-INF/include/aside2.jsp"%>
            </div>
        </div>
    </div>

    <%@include file="/WEB-INF/include/footer.jsp"%>
</div>
<!--数据显示控制-->
<script type="text/javascript">
    test=true;
    t=function(o){if(test){alert(JSON.stringify(o))};};
    myPage=<%=JSONArray.toJSONString(request.getAttribute("myPage"))%>
    //t(myPage);
    //t(myPage.pageSize);
    $(function(){

        //显示主题内容
        if(typeof(myPage.dataList)!="undefined"){
            $("#article_model_div").css("display","none");//隐藏模板
            $articles_div=$("#articles_div");
            for(var i in myPage.dataList){
                var article=myPage.dataList[i];
                var $article=create_article_div(article);
                $articles_div.append($article);
            }
        }

        //分页按钮
        var url="${base}/search/safe/medication/list";//指定跳转路径
        init_paging_button_div(${myPage.pageNum},${myPage.pageSize},${myPage.total},url);
    });

    function create_article_div(article){
        var $article=$("#article_model_div").clone(true);
        //$article.find("div").eq(0)
        $article.attr("id","article_"+article.id);
        $article.find("#article_detail_url").attr("href","${base}/search/safe/medication/detail?id="+article.id);
        $article.find("#article_img").attr("src",typeof(article.articleImg)=="undefined"?"":("${editorMediaPath}"+article.articleImg));
        $article.find("#article_title").attr("href","${base}/search/safe/medication/detail?id="+article.id);
        $article.find("#article_title").html(typeof(article.title)=="undefined"?"":article.title);
        var show_summary="";
        if(typeof(article.summary)!="undefined"){
            show_summary=article.summary.length>100?article.summary.substr(0,100)+"...":article.summary;
        }
        $article.find("#article_summary").html(show_summary);
        if(typeof(article.keywords)!="undefined") {
            var keywords = article.keywords.split('，');
            for (var i in keywords) {
                keywords[i] = "<a href='#' class='color-blue'>" + keywords[i] + "</a>"
            }
            var keywordsStr = keywords.join("<span class='color-blue gap'>|</span>");
            $article.find("#article_keyword").html(keywords.length > 0 ? keywords[0] : "");
            $article.find("#article_keywords").html("关键字：" + keywordsStr);
        }
        if(typeof(article.createTime)!="undefined"&&article.createTime.length>=10) {
            $article.find("#article_create_time").html(article.createTime.substr(0,10));
        }else{
            $article.find("#article_create_time").html("");//没有时间数据或数据有错
        }
        $article.find("#article_xfrom").html("来源："+(typeof(article.xfrom)=="undefined"?"":article.xfrom));
        $article.css("display","block");
        return $article;
    }

    //生成分页按钮，需要传入四个参数：第几页（1-n)，每页行数，总数，点击按钮跳转的url
    //点击产生的跳转链接为：url+"?pageNum=" + 第几页(1 - n) + "&pageSize=" + 每页行数
    function init_paging_button_div(pageNum,pageSize,total,url){
        //t(pageNum+","+pageSize+","+total+","+url);
        var $paging_button_div=$("#paging_button_div");
        if(total==0){
            $paging_button_div.html("T_T 没找到您要的内容哦！");
        }

        url+="?pageSize="+pageSize+"&pageNum=";
        var startPage=1;
        var endPage=1;
        var cur_index=0;
        if(pageNum==1){
            startPage=1;
        }else if(pageNum==2){
            startPage=1;
        }else if(pageNum>2){
            startPage=pageNum-2;
        }
        var allPage=Math.ceil(total/pageSize);
        if(pageNum==allPage){
            endPage=pageNum;
        }else if(pageNum+1==allPage){
            endPage=pageNum+1;
        }else if(pageNum+1<allPage){
            endPage=pageNum+2;
        }else{
            endPage=allPage;//pageNum>allPage,超出有内容以外的页面
        }


        $paging_button_div.html("");
        if(pageNum>1){
            $paging_button_div.append('<a href="'+url+(pageNum-1)+'" class="v2-page-box-prev" title="上一页"></a>');
        }
        for(var i=startPage;i<=endPage;i++){
            if(i==pageNum){
                $paging_button_div.append('<a href="#" class="cur">'+i+'</a>');
            }else{
                $paging_button_div.append('<a href="'+url+i+'">'+i+'</a>');
            }
        }
        if(pageNum<allPage){
            $paging_button_div.append('<a href="'+url+(pageNum+1)+'" class="v2-page-box-next" title="下一页"></a>');
        }

        $paging_button_div.append('&nbsp; &nbsp;<input type="number" id="page_jump_input" style="width:80px;">');
        $paging_button_div.append('<a onclick="jump_page()" class="cur" style="cursor: pointer">go</a>');
        $paging_button_div.append('<span href="'+url+(allPage)+'">共'+allPage+'页</span>');
        jump_page=function(){
            var jump_page_num = $("#page_jump_input").val();
            if (jump_page_num == "" || jump_page_num < 1 || jump_page_num > allPage) {
                layer.tips('请输入正确的页码', '#page_jump_input', {
                    tips: [1, '#3595CC'],
                    time: 3000
                });
                return;
            }
            window.location = url + jump_page_num;
        }
    }

</script>

<!--弹出层-->
<%@include file="/WEB-INF/include/popup_layer2.jsp" %>

<div class="gotop-wrapper index-gotop">
    <a class="gotop" href="javascript:;" >回到顶部</a>
</div>
<script src="${statics}/js/v2/stickUp.min.js"></script>
<script type="text/javascript">
    /*<!--轮播广告-->*/
    $(window).bind("load resize",function(){
        $("#carousel-05").carouFredSel({
            width       : '100%',
            items		: { visible	: 1 },
            auto 	  	: { pauseOnHover: true, timeoutDuration:5000 },
            swipe    	: { onTouch:true, onMouse:true },
            pagination 	: "#carousel-page-05",
//                                    prev 		: { button:"#carousel-prev-01"},
//                                    next 		: { button:"#carousel-next-01"},
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



</script>


</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/17
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <ul class="tab-list clearfix" id="menu">
        <li>
            <a href="${ctx}/func/res/list">CSP投屏<i></i></a>
        </li>
        <li>
            <a href="${ctx}/func/res/share/list">共享资源<i></i></a>
        </li>
    </ul>
</div>

<script>
    $(function(){
        var urlstr = location.href;
        $("#menu>li").removeClass("cur");
        $("#menu>li").each(function () {
            if (urlstr.indexOf($(this).find("a").attr('href')) > -1) {
                $(this).addClass('cur');
            }else if (urlstr.indexOf('/acquired/list') > -1){
                $(this).addClass("cur").siblings().removeClass("cur");
            }
        });

        // 点击切换卡片列表
        $(".changeListButton").click(function(){
            if($("#pages").val() <= 1){
                $("#pageForm").find("input[name='pageNum']").val(1);
            }
            $("#viewType").val() == 0 ?  $("#viewType").val(1): $("#viewType").val(0);
            $("#pageForm").submit();
        });

        //点击预览
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

        //简介和星评
        $(".grade-state").click(function(){
            var courseId = $(this).attr("courseId");
            top.layer.open({
                type:2,
                area: ['860px', '800px'],
                fix: false, //不固定
                fixed:true,
                offset: '100px',
                title:false,
                content:'${ctx}/func/res/rate/view?courseId='+courseId
            });
        });



        $("#exportBtn").click(function(){
            window.location.href = "${ctx}/func/res/export";
        });
    });

    //点击搜索
    function submit(){
        $("#searchForm").submit();
    }
</script>
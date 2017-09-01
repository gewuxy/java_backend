<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/25
  Time: 11:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>资源平台</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
    <script src="${ctxStatic}/js/slide.js"></script>
    <script>
        var resId = 0;
        $(function(){
            if("${err}"){
                layer.msg("${err}");
            }

            $("#categoryList>a").click(function(){
                var category = $(this).attr("category");
                $("#category").val(category);
                $("#pageForm").submit();
            });

            $(".fx-btn-2").click(function(){
                resId = $(this).attr("resId");
                if($(this).attr("reprinted") == 'false'){
                    openReprint($(this).attr("title"), $(this).attr("shareType"), $(this).attr("credits"));
                }
            });

            $(".close-btn-fx,.close-button-fx").click(function(){
                closeDialog();
            });

            $("#reprintBtn").click(function(){
                closeDialog();
                window.location.href = '${ctx}/func/res/reprint?id='+resId;
            });


            $(".yl").click(function(){
                var courseId = $(this).attr("courseId");
                $('.mask-wrap').addClass('dis-table');
                $('.fx-mask-box-1').show();
                $("#audioFrame").attr("src","${ctx}/func/res/view?courseId="+courseId);
            });
        });

        function openReprint(title, shareType, credits){
            $("#reprintResTitle").text(title);
            $("#leftCredits").text('${credit}');
            if(shareType == 1){
                $("#requiredPrefix").text("转载会议需使用");
                $("#requiredCredits").text(credits+"象数");
            }else if(shareType == 2){
                $("#requiredPrefix").text("转载会议奖励");
                $("#requiredCredits").text(credits+"象数");
            }else{
                $("#requiredPrefix").text("");
                $("#requiredCredits").text("免费转载");
            }
            if(shareType == 1 && credits > parseInt('${credit}')){
                $("#reprintBtn").hide();
            }else{
                $("#reprintBtn").show();
            }
            $('.mask-wrap').addClass('dis-table');
            $('.fx-mask-box-2').show();
        }

        function closeDialog(){
            $("#reprintBtn").show();;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
            $('.fx-mask-box-1,.fx-mask-box-2').hide();
            $('.mask-wrap').removeClass('dis-table');
            $("#audioFrame").attr("src","");
        }
    </script>
</head>
<body>
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
                <a >资源转载<i></i></a>
            </li>
            <li>
                <a href="${ctx}/func/res/reprints">我的管理<i></i></a>
            </li>
        </ul>
    </div>
    <div class="tab-bd">
        <div class="table-box-div1 mar-btm-1">
            <div class="table-top-box clearfix">
                <div class="top-ri-txt">采集资源可作为内容进行管理</div>
                <a class="mask-le cur"><span class="icon iconfont icon-minIcon3"></span>所有资源</a>
                <form action="${ctx}/func/res/list" method="post">
                    <span class="search-box">
                        <input type="text" class="sear-txt" name="keyword" value="${keyword}" placeholder="搜索">
                        <input type="hidden" name="category" value="${category}">
                        <button class="sear-button"></button>
                    </span>
                </form>
            </div>
            <div class="sd-list bg-change" id="categoryList">
                <a category="" class="${empty category?'cur':''}" style="cursor: pointer;">全部</a>
                <c:forEach items="${categoryList}" var="cate">
                    <a category="${cate.name}"  class="${category eq cate.name?'cur':''}"  style="cursor: pointer;" >${cate.name}(${cate.count})</a>
                </c:forEach>
            </div>
            <table class="table-box-3">
                <thead>
                <tr>
                    <td class="tb-w-1"><i>资源名称</i></td>
                    <td class="tb-w-2">科目</td>
                    <td class="tb-w-3">来源</td>
                    <td class="tb-w-4">设定</td>
                    <td class="tb-w-3 p-le-1">操作</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.dataList}" var="res">
                    <tr>
                        <td class="tb-w-1">
                            <a>${res.title}</a>
                        </td>
                        <td class="tb-w-2"><i class="ks-txt">${res.category}</i></td>
                        <td class="tb-w-3">${res.pubUserName}</td>
                        <td class="tb-w-4">
                            <c:choose>
                                <c:when test="${res.shareType == 0}">
                                    免费分享
                                </c:when>
                                <c:when test="${res.shareType == 1}">
                                    支付${res.credits}象数
                                </c:when>
                                <c:when test="${res.shareType == 2}">
                                    奖励${res.credits}象数
                                </c:when>
                            </c:choose>
                        </td>
                        <td class="tb-w-5">
                            <a href="javascript:" class="fx-btn-1 yl" courseId="${res.id}"><span class="icon iconfont icon-minIcon4"></span>预览</a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <c:choose>
                                <c:when test="${res.reprinted ||  currentUserId == res.owner}">
                                    <span class="icon iconfont icon-minIcon5"></span>已转载
                                </c:when>
                                <c:otherwise>
                                    <a class="fx-btn-2 zai color-gray" style="cursor: pointer;" credits="${res.credits}" title="${res.title}" resId="${res.id}" shareType="${res.shareType}" reprinted="${res.reprinted}"><span class="icon iconfont icon-minIcon5"></span>转载</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
            <%@include file="/WEB-INF/include/pageable.jsp"%>
        </div>
    </div>
</div>

<div class="mask-wrap">
    <div class="dis-tbcell">
        <div class="distb-box fx-mask-box-1" style="width: 650px">


            <div class="mask-hd clearfix">
                <h3 class="font-size-1">内容预览</h3>
                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
            </div>
            <iframe frameborder="0" width="650" height="420" id="audioFrame" scrolling="false" name="audioFrame" ></iframe>

        </div>
        <div class="distb-box fx-mask-box-2">
            <div class="mask-hd clearfix">
                <h3 class="font-size-1" id="reprintResTitle"></h3>
                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
            </div>
            <div class="mask-share-box">
                <p class="top-txt color-black"><span id="requiredPrefix"></span><i class="color-blue" id="requiredCredits"></i>&nbsp;是否立即转载？</p>
                <p>账户剩余<span class="color-black" id="leftCredits">0</span>象数，可继续<a href="${ctx}/mng/account/xsInfo" class="color-blue">充值</a>增加象数值</p>
            </div>
            <div class="sb-btn-box p-btm-1 t-right">
                <button class="close-button-fx cur" id="reprintBtn">确认</button>
                <button class="close-button-fx" id="cancelBtn">取消</button>
            </div>
        </div>
    </div>
</div>
<form id="pageForm" name="pageForm" action="${ctx}/func/res/list" method="post">
    <input type="hidden" name="pageNum" id="pageNum" value="${page.pageNum}">
    <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
    <input type="hidden" name="keyword" value="${keyword}">
    <input type="hidden" name="category" id="category" value="${category}">
</form>

</body>
</html>

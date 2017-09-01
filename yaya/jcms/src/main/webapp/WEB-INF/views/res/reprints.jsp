<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/25
  Time: 11:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>已转载资源</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
    <script src="${ctxStatic}/js/slide.js"></script>
    <script>
        $(function(){
            $(".yl").click(function(){
                var courseId = $(this).attr("courseId");
                $('.mask-wrap').addClass('dis-table');
                $('.fx-mask-box-1').show();
                $("#audioFrame").attr("src","${ctx}/func/res/view?courseId="+courseId);
            });

            $(".close-btn-fx, #cancelBtn").click(function(){
                closeDialog();
            });

            $(".zai").click(function(){
                var courseId = $(this).attr("courseId");
                window.location.href = '${ctx}/func/meet/edit?courseId='+courseId;
            });
        });

        function closeDialog(){
            $("#reprintBtn").show();
            $('.fx-mask-box-1,.fx-mask-box-2').hide();
            $("#audioFrame").attr("src","");
            $('.mask-wrap').removeClass('dis-table');
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
            <li>
                <a href="${ctx}/func/res/list">资源转载<i></i></a>
            </li>
            <li class="cur">
                <a >我的管理<i></i></a>
            </li>
        </ul>
    </div>
    <div class="tab-bd">
        <div class="table-box-div1 mar-btm-1">
            <div class="table-top-box clearfix">
                <a class="mask-le cur"><span class="icon iconfont icon-minIcon3"></span>已转载资源</a>
                <a href="${ctx}/func/res/shared" class="mask-le "><span class="icon iconfont icon-minIcon8"></span>我的资源分享</a>
                <a href="${ctx}/func/res/reprinted" class="mask-le"><span class="icon iconfont icon-minIcon8"></span>被转载记录</a>

                <form action="${ctx}/func/res/reprints" method="post">
                    <span class="search-box">
                        <input type="text" class="sear-txt" name="keyword" value="${keyword}" placeholder="搜索">
                        <button class="sear-button"></button>
                    </span>
                </form>
            </div>

            <table class="table-box-3">
                <thead>
                <tr>
                    <td class="tb3-td-1">资源名称</td>
                    <td class="tb3-td-2">发布单位</td>
                    <td class="tb3-td-3 ">操作</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.dataList}" var="res">
                    <tr>
                        <td class="tb3-td-1">
                            <a href="">${res.title}</a>
                        </td>
                        <td class="tb3-td-2"><img src="${ctxStatic}/img/_link-01.png" alt="">&nbsp;&nbsp;<span>${res.pubUserName}</span></td>
                        <td class="tb3-td-3 ">
                            <a style="cursor: pointer;" class="fx-btn-1 yl" courseId="${res.id}"><span class="icon iconfont icon-minIcon4"></span>预览</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="fx-btn-2 zai" courseId="${res.id}" style="cursor: pointer;"><span class="icon iconfont icon-minIcon5"></span>立即发布</a>
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
<form id="pageForm" name="pageForm" action="${ctx}/func/res/reprints" method="post">
    <input type="hidden" name="pageNum" id="pageNum" value="${page.pageNum}">
    <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
    <input type="hidden" name="keyword" value="${keyword}">
</form>

</body>
</html>

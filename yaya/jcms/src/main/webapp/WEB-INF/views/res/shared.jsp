<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/25
  Time: 11:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我分享的资源</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
    <script>

        var resId = 0;
        $(function(){
            if("${err}"){
                layer.msg("${err}");
            }
            $(".fx-btn-2").click(function(){
                resId = $(this).attr("resId");
                if($(this).attr("shared") == 'true'){
                    window.location.href = '${ctx}/func/res/modifyShared?id='+resId;
                }else{
                    openShare($(this).attr("title"));
                }
            });

            $(".close-btn-fx, #cancelBtn").click(function(){
                closeDialog();
            });

            $("input[name='shareType']").change(function(){
                var shareType = $("input[name='shareType']:checked").val();
                if(shareType == 1){
                    $("#credit").val("");
                    $("#shareSetting").show();
                }else{
                    $("#shareSetting").hide();
                }
            });

            $("#shareBtn").click(function(){
                var shareType = $("input[name='shareType']:checked").val();
                var credit = 0;
                if(shareType == 1){
                    credit = $("#shoufei").val();
                }else if(shareType == 2){
                    credit = $("#jiangli").val();
                }
                if(checkCredit()){
                    window.location.href = '${ctx}/func/res/modifyShared?id='+resId+'&shareType='+shareType+'&credit='+credit;
                }
            });

            $(".yl").click(function(){
                var courseId = $(this).attr("courseId");
                $('.mask-wrap').addClass('dis-table');
                $('.fx-mask-box-1').show();
                $("#audioFrame").attr("src","${ctx}/func/res/view?courseId="+courseId);
            });
        });

        function checkCredit(){
            var shareType = $("input[name='shareType']:checked").val();
            if(shareType == undefined){
                layer.tips("请选择共享方式","#mianfei");
                return false;
            }
            if(shareType == 1){
                var credit = $("#shoufei").val();
                var reg = /^[1-9]+[0-9]*$/g;
                if(!reg.test(credit)){
                    $("#shoufei").val("");
                    $("#shoufei").focus();
                    layer.tips("请输入正确的整数", "#shoufei");
                    return false;
                }
            }else if(shareType == 2){
                var credit = $("#jiangli").val();
                var reg = /^[1-9]+[0-9]*$/g;
                if(!reg.test(credit)){
                    $("#jiangli").val("");
                    $("#jiangli").focus();
                    layer.tips("请输入正确的整数", "#jiangli");
                    return false;
                }
            }
            return true;
        }

        function openShare(title){
            $("#sharedResTitle").text(title);
            $('.mask-wrap').addClass('dis-table');
            $('.fx-mask-box-2').show();
        }

        function closeDialog(){
            $("#reprintBtn").show();;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
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
                <a href="${ctx}/func/res/reprints" class="mask-le"><span class="icon iconfont icon-minIcon3"></span>已转载资源</a>
                <a class="mask-le cur"><span class="icon iconfont icon-minIcon8"></span>我的资源分享</a>
                <a href="${ctx}/func/res/reprinted" class="mask-le"><span class="icon iconfont icon-minIcon8"></span>被转载记录</a>

                <form action="${ctx}/func/res/shared" method="post">
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
                    <td class="tb3-td-2">被转载次数</td>
                    <td class="tb3-td-3 ">操作</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.dataList}" var="res">
                    <tr>
                        <td class="tb3-td-1">
                            <a href="">${res.title}</a>
                        </td>
                        <td class="tb3-td-2"><span>${res.reprintCount}</span></td>
                        <td class="tb3-td-3 ">
                            <a href="javascript:" class="fx-btn-1 yl" courseId="${res.id}"><span class="icon iconfont icon-minIcon4"></span>预览</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="fx-btn-2 zai" style="cursor: pointer;" shared="${res.shared}" resId="${res.id}" title="${res.title}"><span class="icon iconfont icon-minIcon5"></span>${res.shared?'关闭共享':'打开共享'}</a>

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
                <h3 class="font-size-1" id="sharedResTitle"></h3>
                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
            </div>
            <div class="mask-share-box">
                <p class="color-black">
					<span class="radioboxIcon">
						<input type="radio" name="shareType" id="checkbox_3" value="0" class="chk_1 chk-hook">
						<label for="checkbox_3" class="formTitle "><i class="ico checkboxCurrent"></i>&nbsp;免费分享
                        <input type="text" style="border-color: #ffffff" id="mianfei" class="text-input text-input-num">
                        </label>
					</span>
                </p>
                <p class="color-black">
					<span class="radioboxIcon">
						<input type="radio" name="shareType" id="checkbox_4" value="1" class="chk_1 chk-hook">
						<label for="checkbox_4" class="formTitle "><i class="ico checkboxCurrent"></i>&nbsp;收取象数&nbsp;&nbsp; <input
                                type="text" id="shoufei" class="text-input text-input-num" placeholder="0"></label>
					</span>
                </p>
                <p class="color-black">
					<span class="radioboxIcon">
						<input type="radio" name="shareType" id="checkbox_5" value="2" class="chk_1 chk-hook">
						<label for="checkbox_5" class="formTitle "><i class="ico checkboxCurrent"></i>&nbsp;奖励象数&nbsp;&nbsp; <input
                                type="text" id="jiangli" class="text-input text-input-num" placeholder="0"><span class="color-gray"></span></label>
					</span>
                </p>
            </div>

            <div class="sb-btn-box p-btm-1 t-right">
                <button class="close-button-fx cur" id="shareBtn">确认</button>
                <button class="close-button-fx" id="cancelBtn">取消</button>
            </div>
        </div>
    </div>
</div>
<form id="pageForm" name="pageForm" action="${ctx}/func/res/shared" method="post">
    <input type="hidden" name="pageNum" id="pageNum" value="${page.pageNum}">
    <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
    <input type="hidden" name="keyword" value="${keyword}">
    <input type="hidden" name="category" id="category" value="${category}">
</form>
<script src="${ctxStatic}/js/slide.js"></script>

</body>
</html>

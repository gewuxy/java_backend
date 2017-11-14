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
    <%@include file="resHeader.jsp"%>
    <!-- header end -->
    <div class="tab-bd">
        <div class="table-box-div1 mar-btm-1">
            <div class="table-top-box clearfix">
                <div class="formrow t-right">
					<span class="checkboxIcon">
                        <input type="checkbox" id="popup_checkbox_2" <c:if test="${flag == 1}"> checked </c:if> class="chk_1 chk-hook">
						<label for="popup_checkbox_2" class="popup_checkbox_hook"><i class="ico"></i>&nbsp;&nbsp;开启CSPmeeting来稿功能</label>
					</span>&nbsp;
                    <span class="question-tipsHover-hook">
                        <img src="${ctxStatic}/images/icon-question.png" alt="">
                    </span>
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
                                            <a href="#" class="resource-icon-play popup-player-hook" courseId="${d.id}">
                                                <i></i>
                                                预览
                                            </a><a href="${ctx}/func/meet/edit?courseId=${d.id}" class="resource-icon-edit">
                                            <i></i>
                                            立即发布
                                        </a>
                                        </div>
                                        <c:if test="${d.playType == 2}">
                                            <div class="resource-state"><span class="icon iconfont icon-minIcon26"></span></div>
                                        </c:if>
                                    </div>
                                    <div class="resource-info">
                                        <div class="fl">
                                            <img src="${d.avatar}" alt="">
                                        </div>
                                        <div class="oh">
                                            <div class="row clearfix">
                                                <div class="col-lg-10">
                                                    <h3 >${d.name}</h3>
                                                    <p>${d.email}</p>
                                                </div>
                                                <c:if test="${d.playType != 0}" >
                                                    <div class="col-lg-2">
                                                        <div class="state">直播</div>
                                                    </div>
                                                </c:if>
                                            </div>

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


<script src="${ctxStatic}/js/jquery.min.js"></script>
<script src="${ctxStatic}/js/slide.js"></script>
<script src="${ctxStatic}/js/swiper.jquery.min.js"></script>
<script src="${ctxStatic}/js/audio.js"></script>
<script src="${ctxStatic}/js/layer/layer.js"></script>
<script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
<script src="${ctxStatic}/js/screenfull.min.js"></script>

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
                url:"${ctx}/func/res/change?flag="+flag,
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
                           window.location.href='${ctx}/func/res/list?isOpen='+flag;
                       }
                    }
                }

            });
        });

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

    })
</script>

</body>
</html>
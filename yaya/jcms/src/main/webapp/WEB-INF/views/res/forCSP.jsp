<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/6/12
  Time: 15:52
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">

</head>
<body>
    <div class="popup-tab-main clearfix">
    <table class="table-box-1">
        <colgroup>
            <col class="table-td-6">
            <col class="table-td-2">
            <col class="table-td-7">
        </colgroup>
        <tbody>
        <c:forEach items="${page.dataList}" var="res">
            <tr id="course_${res.courseId}">
                <input type="hidden" id="playType" value="${res.playType}">
                <input type="hidden" id="startTime" value = "<fmt:formatDate value="${res.startTime}" pattern="MM月dd日 HH:mm"/>">
                <input type="hidden" id="endTime" value = "<fmt:formatDate value="${res.endTime}" pattern="MM月dd日 HH:mm"/>">
                <td class="table-td-6" >
                    <a href="javascript:;" class="popup-player-hook"> ${res.title}
                        <c:if test="${res.playType != 0}"><i class="style-1 listTips">直播</i>&nbsp;</c:if>
                        <c:if test="${res.playType == 2}"><span class="icon iconfont icon-minIcon26"></span></a></c:if>
                </td>
                <td class="table-td-2">
                    <span>${res.name}</span>
                </td>
                <td class="table-td-7">
                    <a href="javascript:;" class="popup-player-hook" courseId="${res.courseId}">预览</a><i class="rowSpace">|</i><a href="javascript:;" class="popup-hint" courseId="${res.courseId}">转载</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
    </div>
    <%@include file="/WEB-INF/include/pageable.jsp"%>

    <form id="pageForm" name="pageForm" action="${ctx}/func/meet/delivery/forCSP" method="post">
        <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageNum" id="pageNum">
        <input type="hidden" name="meetId" value="${meetId}">
        <input type="hidden" name="moduleId" id="moduleId" value="${moduleId}">
    </form>

<div class="mask-wrap">
    <div class="dis-tbcell">
        <div class="distb-box distb-box-tips fx-mask-box-2">
            <div class="mask-hd clearfix">
                <h3 class="font-size-1">温馨提示</h3>
                <span class="layui-layer-close"><img src="${ctxStatic}/images/cha.png"></span>
            </div>
            <div class="mask-share-box">
                <p class="top-txt color-black">会覆盖当前的PPT文件，是否继续？</p>
                <p id="liveTime">直播时间： 06月12日 15:00 至 07月12日 14:06</p>
            </div>
            <div class="sb-btn-box p-btm-1 t-right">
                <button class="layui-layer-close cur" id="confirmBtn" courseId="" playType="">继续</button>
                <button class="layui-layer-close" id="cancelBtn">否</button>
            </div>
        </div>
    </div>
</div>



<script>
    $(function(){
        $("#confirmBtn").click(function(){
           var courseId = $(this).attr("courseId");
           var playType = $(this).attr("playType");
           $.get('${ctx}/func/meet/ppt/quote',{'courseId':courseId,'meetId':'${meetId}','moduleId':'${moduleId}','playType':playType},function (data) {
               if(data.code == 0){
                   window.parent.location.reload();
               }else{
                   top.layer.msg(data.err);
               }
           },'json');
        });

        $(".popup-hint").click(function () {
            $('.mask-wrap').addClass('dis-table');
            var courseId = $(this).attr("courseId");
            var playType = $("#course_"+courseId).find("#playType").val();
            if(playType != 0){  //直播
                var startTime = $("#course_"+courseId).find("#startTime").val();
                var endTime = $("#course_"+courseId).find("#endTime").val();
                $("#liveTime").text("直播时间:  "+ startTime + " 至 " + endTime);
                $("#confirmBtn").attr("courseId",courseId);
                $("#confirmBtn").attr("playType",playType);
            }else{
                $("#liveTime").remove();
                $("#confirmBtn").attr("courseId",courseId);
            }
            $('.fx-mask-box-2').show();
        })

        //关闭窗口
        $(".layui-layer-close").click(function(){
            $('.mask-wrap').removeClass('dis-table');
            $('.fx-mask-box-2').hide();
        });

    });
</script>
</body>
</html>

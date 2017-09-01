<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/15
  Time: 16:56
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>单位号首页</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

    <script>
        function preViewCourse(id){
            $('.mask-wrap').addClass('dis-table');
            $('.fx-mask-box-1').show();
            $("#audioFrame").attr("src","${ctx}/func/res/view?courseId="+id);
        }

        function closeView(){
            $('.mask-wrap').removeClass('dis-table');
            $('.fx-mask-box-1').hide();
            //$("#audioFrame").attr("src","");
        }
    </script>
</head>
<body>
<div class="g-main main-wrap clearfix">
<div class="my-mbox clearfix">
    <div class="mbox-top clearfix">
			<span class="fl mbox-top-le">
				<span class="fl"><img
                <c:choose>
                        <c:when test="${empty headimg}">
                        src="${ctxStatic}/img/hz-detail-img-info.jpg"
                </c:when>
                        <c:otherwise>
                        src="${headimg}"
                </c:otherwise>
                </c:choose>
                        alt=""></span>
				<div class="oh mbox-in">
					<p><strong>${nickname}</strong></p>
					<p>关注医生<i>${attention}人</i></p>
				</div>
			</span>
        <span class="fr mbox-top-ri">
				<span class="total">象数积分<i>${credits.credit}</i></span>

        <a href="message-list.html" class="link message-tips" style="display: none;">
            <span class="message-icon "></span>
            <span class="icon iconfont icon-minIcon23"></span><br />
            消息
        </a>
        <a href="${ctx}/help/solution" class="link" target="_blank">
            <span class="icon iconfont icon-minIcon25"></span><br />
            帮助
        </a>

    </div>
    <ul class="my-mbox-ul">
        <li>
            <p class="mbox-pic"><img src="${ctxStatic}/images/index-itemImg-01.png" alt=""></p>
            <h3 class="mbox-txt">会议发布</h3>
            <p class="mbox-txt-1">快速发布在线会议，下载手机app端即可查看PPT、直播、调研、考试等会议</p>
            <p class="nbox-btn"><a href="${ctx}/func/meet/edit" class="mbox-button">立即发布</a></p>
        </li>
        <li>
            <p class="mbox-pic"><img src="${ctxStatic}/images/index-itemImg-02.png" alt=""></p>
            <h3 class="mbox-txt">资源平台</h3>
            <p class="mbox-txt-1">医学会议、科教培训、医学资料跨医院共享的学术共享平台</p>
            <p class="nbox-btn"><a href="${ctx}/func/res/list" class="mbox-button">查看资源</a></p>
        </li>
        <li>
            <p class="mbox-pic"><img src="${ctxStatic}/images/index-itemImg-03.png" alt=""></p>
            <h3 class="mbox-txt">群发消息</h3>
            <p class="mbox-txt-1">编辑新闻资讯，实时广播至关注用户，重要消息及时传递</p>
            <p class="nbox-btn"><a href="${ctx}/func/msg/add" class="mbox-button">立即发布</a></p>
        </li>
        <li class="last">
            <p class="mbox-pic"><img src="${ctxStatic}/images/index-itemImg-04.png" alt=""></p>
            <h3 class="mbox-txt">消息通知</h3>
            <p class="mbox-txt-1">与医生消息文字即时互动，解答关注用户的疑问与建议问题</p>
            <p class="nbox-btn"><a href="${ctx}/func/msg/list" class="mbox-button">查看消息</a></p>
        </li>


    </ul>
</div>
<div class="my-mlist my-mlist-shedow my-index-box">
    <div class="my-top">
        <div class="fl">我的会议</div>
        <div class="fr"><a href="${ctx}/func/meet/list">更多</a></div>
        <div class="clearfix"></div>
    </div>
    <table class="table-box-1">
        <colgroup>
            <col class="table-td-6">
            <col class="table-td-4">
            <col class="table-td-5">
        </colgroup>
        <tbody>
        <c:forEach items="${meetList}" var="meet">
            <tr>
                <td class="table-td-6">
                    <span class="minIcon minIcon-item-01"></span>
                    <a href="${ctx}/func/meet/view?id=${meet.id}&tag=1">${meet.meetName}<i class="style-1 listTips">${meet.stateName}</i></a>
                </td>
                <td class="table-td-4">
                    <span ><fmt:formatDate value="${meet.startTime}" pattern="yyyy/MM/dd HH:mm"/> </span>
                </td>
                <td class="table-td-5">
                    <a href="${ctx}/func/meet/view?id=${meet.id}&tag=1"><span class="minIcon minIcon-item-02"></span>&nbsp;统计报告</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="page-box">

    </div>

</div>
<div class="my-mlist my-mlist-shedow my-index-box">
    <div class="my-top">
        <div class="fl">最新会议资源</div>
        <div class="fr"><a href="${ctx}/func/res/list">更多</a></div>
        <div class="clearfix"></div>
    </div>
    <table class="table-box-1">
        <colgroup>
            <col class="table-td-6">
            <col class="table-td-4">
            <col class="table-td-2">
        </colgroup>
        <tbody>
        <c:forEach items="${resList}" var="res">
            <tr>
                <td class="table-td-6">
                    <a style="cursor: pointer;" onclick="preViewCourse(${res.id})">${res.title}</a>
                </td>
                <td class="table-td-4">
                    <img src="${ctxStatic}/img/_link-01.png" alt="">
                    <span>${res.pubUserName}</span>
                </td>
                <td class="table-td-2">
                    <c:choose>
                        <c:when test="${res.shareType == 0}">
                            <span>免费分享</span>
                        </c:when>
                        <c:when test="${res.shareType == 1}">
                            <span class="color-green">收取${res.credits}象数</span>
                        </c:when>
                        <c:when test="${res.shareType == 2}">
                            <span class="color-blue">奖励${res.credits}象数</span>
                        </c:when>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
    <div class="page-box">

    </div>

</div>
</div>
<div class="mask-wrap">
    <div class="dis-tbcell">
        <div class="distb-box fx-mask-box-1" style="width: 650px">


            <div class="mask-hd clearfix">
                <h3 class="font-size-1">内容预览</h3>
                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png" onclick="closeView()"></span>
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
                <p>账户剩余<span class="color-black" id="leftCredits">0</span>象数，可继续<a href="" class="color-blue">充值</a>增加象数值</p>
            </div>
            <div class="sb-btn-box p-btm-1 t-right">
                <button class="close-button-fx cur" id="reprintBtn">确认</button>
                <button class="close-button-fx" id="cancelBtn">取消</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>

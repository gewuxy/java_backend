<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/24
  Time: 11:56
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>会议发布预览</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
</head>
<body>
<div class="g-main  mettingForm clearfix">

    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-03.png" alt="">
                </div>
                <div class="oh">
                    <p><strong>会议发布</strong></p>
                    <p>快速发布在线会议，下载手机app端即可查看PPT、直播、调研、考试等会议</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->

    <div class="tab-hd">

        <ul class="tab-list clearfix">
            <li class="cur">
                <a >会议发布<i></i></a>
            </li>
            <li>
                <a href="${ctx}/func/meet/draft">草稿箱<i></i></a>
            </li>
            <li>
                <a href="${ctx}/func/meet/list">已发布会议<i></i></a>
            </li>
        </ul>
    </div>
    <div class="tab-bd">
        <div class=" clearfix metting-process">
            <div class=" clearfix link-justified metting-process-03">
                <ul>
                    <li class="cur">创建会议</li>
                    <li>会议详情</li>
                    <li>发布预览</li>
                </ul>
            </div>
        </div>
    </div>
    <div class="clearfix subPage-marginTop">
        <%@include file="/WEB-INF/include/meet_detail.jsp"%>
    </div>

    <div class="buttonArea clearfix" style="width:750px;     margin: 20px 25px 40px;">
        <div class="formrow">
            <div class="fr clearfix">
                <input type="button" id="draftBtn" class="formButton formButton-max" value="保存草稿">
            </div>
            <div class="fl clearfix">
                <input type="button" onclick="window.location.href='${ctx}/func/meet/config?meetId=${meet.id}'" class="formButton formButton-max" value="上一步">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" id="finishBtn" class="formButton formButton-max" value="完成">
            </div>
        </div>
    </div>



</div>
<script>

    $(function(){
        $("#finishBtn").click(function(){
            $.get('${ctx}/func/meet/publish',{'meetId':'${meet.id}','saveAction':1},function (data) {
                if(data.code == 0){
                    window.location.href = '${ctx}/func/meet/list';
                }else{
                    top.layer.msg(data.err);
                }
            },'json');
        });

        $("#draftBtn").click(function(){
            $.get('${ctx}/func/meet/publish',{'meetId':'${meet.id}','saveAction':0},function (data) {
                if(data.code == 0){
                    window.location.href = '${ctx}/func/meet/draft';
                }else{
                    top.layer.msg(data.err);
                }
            },'json');
        });
    })
</script>
</body>
</html>

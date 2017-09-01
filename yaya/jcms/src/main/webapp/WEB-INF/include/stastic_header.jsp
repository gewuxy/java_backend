<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/6/7
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="detail-box clearfix">
    <div class="fr">
        <span class="backPage">
            <a href="javascript:history.go(-1)"><span class="icon iconfont icon-minIcon11"></span>&nbsp;返回上级</a>
        </span>
        <span class="detail-status-icon">资源平台共享: <i class="open-btn">已开启</i><i class="icon"></i></span>

    </div>
    <div class="fl fl-txt">
        <p class="detail-txt-1" id="meetname"></p>
        <p class="detail-box-1">
            <span id="meetmodule"></span><i class="rowSpace">|</i>
            <span id="meettype"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="meettime"></span>
        </p>
    </div>
</div>


<div class="tab-hd">
    <ul class="tab-list clearfix" id="menu">
        <li class="cur">
            <a href="${ctx}/func/meet/view?id=${param.id}&tag=1">会议详情<i></i></a>
        </li>
        <li>
            <a href="${ctx}/data/state/user/statistics?id=${param.id}">用户统计<i></i></a>
        </li>
        <li id="ppt" style="display: none">
            <a href="${ctx}/func/meet/ppt/content/statistics?id=${param.id}">内容统计<i></i></a>
        </li>
        <li id="exam" style="display: none">
            <a href="${ctx}/func/meet/exam/statistics?id=${param.id}">考题统计<i></i></a>
        </li>
        <li id="survey" style="display: none">
            <a href="${ctx}/func/meet/survey/statistics?id=${param.id}">问卷统计<i></i></a>
        </li>
        <li id="sign" style="display: none">
            <a href="${ctx}/func/meet/position/sign/statistics?id=${param.id}">签到统计<i></i></a>
        </li>
        <li id="video" style="display: none">
            <a href="${ctx}/func/meet/video/statistics?id=${param.id}">视频统计<i></i></a>
        </li>
    </ul>
</div>

<script>
    $(function () {
        var meetId = "${param.id}";
        $.ajax({
            url: "${ctx}/data/state/meets?meetId="+meetId,
            type: "post",
            dataType: "json",
            success: function (data) {
                var datas = data.data;
                console.log(datas);
                if(datas!=null){
                    $('#meetname').text(datas[0].meetName);
                    var mod = datas[datas.length-1].moduleNames;
                    mod = mod.substring(0,mod.lastIndexOf("/"));
                    if(mod!=null && mod!='null' && mod.length!=0){
                        $('#meetmodule').text(mod);
                    }else{
                        $('#meetmodule').text('');
                    }


                    $('#meettype').text(datas[0].meetType);
                    $('#meettime').text('会议时间：'+datas[0].meetTime);
                    $.each(datas, function (i, val) {
                        var mdName = datas[i].moduleName;
                        if(mdName=='微课'){
                            $('#ppt').css("display","block");
                        }
                        if(mdName=='考试'){
                            $('#exam').css("display","block");
                        }
                        if(mdName=='问卷'){
                            $('#survey').css("display","block");
                        }
                        if(mdName=='签到'){
                            $('#sign').css("display","block");
                        }
                        if(mdName=='视频'){
                            $('#video').css("display","block");
                        }
                    });
                    setCur();
                }
            }
        });
    });

    function setCur(){
        var urlstr = location.href;
        $("#menu>li").removeClass("cur");
        $("#menu>li").each(function () {
            if (urlstr.indexOf($(this).find("a").attr('href')) > -1) {
                $(this).addClass('cur');
                return false;
            }
        });

    }

</script>

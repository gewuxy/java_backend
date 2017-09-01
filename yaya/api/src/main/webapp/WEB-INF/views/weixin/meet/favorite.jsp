<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/8/2
  Time: 9:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>我的收藏</title>
    <%@include file="/WEB-INF/include/page_context_weixin.jsp"%>
    <script src="${ctxStatic}/js/radialIndicator.min.js"></script>
    <script src="${ctxStatic}/js/util.js"></script>
</head>
<body>
<div class="warp ">

    <div class="item">
        <div class="metting-list">
            <ul id="favoriteUL">
            </ul>
        </div>
    </div>



</div>

</body>

<script>
    var pageNo = 1;
    const pageSize = 8;
    var hasMore = true;

    function pageFavorite(){
        if (hasMore){
            $.get('${ctx}/weixin/meet/favorite/page', {'pageNum':pageNo, 'pageSize':pageSize}, function (data) {
                var favorites = data.data;
                hasMore = favorites.length == pageSize;
                showList(favorites);
                pageNo++;
            },'json');
        }
    }

    function getStateLabel(state){
        var stateLabel = '';
        switch (state){
            case 0:
                stateLabel = "草稿";
                break;
            case 1:
                stateLabel = "未开始";
                break;
            case 2:
                stateLabel = "进行中";
                break;
            case 3:
                stateLabel = "精彩回顾";
                break;
            case 4:
                stateLabel = "已关闭";
                break;
            default:
                stateLabel = "已废弃";
                break;
        }
        return stateLabel;
    }

    function getColorStatus(state){
        var colorClass = '';
        switch (state){
            case 0:
                colorClass = "color-gray";
                break;
            case 1:
                colorClass = "color-blue-3";
                break;
            case 2:
                colorClass = "color-yellow";
                break;
            case 3:
                colorClass = "color-blue";
                break;
            default:
                colorClass = "color-gray";
                break;
        }
        return colorClass;
    }

    function showList(favorites){
        var liHtml;
        var currentFavorite;
        for(var index in favorites){
            currentFavorite = favorites[index];
            var lecturer = currentFavorite.lecturer == undefined?"":currentFavorite.lecturer;
            var lecturerTitle = currentFavorite.lecturerTitle == undefined?"":currentFavorite.lecturerTitle;
            var organizer = currentFavorite.organizer == undefined?"":currentFavorite.organizer;
            var cme = currentFavorite.eduCredits == undefined ||currentFavorite.eduCredits == '' ? "" : '<span class="metting-label metting-label-CME">CME</span>';
            var xs = currentFavorite.xsCredits == undefined || currentFavorite.xsCredits == '' ? "" : '<span class="metting-label metting-label-money"><i></i>&nbsp;象数</span>';
            var startTime = new Date(currentFavorite.startTime).format("MM/dd hh:mm");
            liHtml = '<li><a href="${ctx}/weixin/meet/info?meetId='+currentFavorite.id+'" ><div class="fr" id="fr_'+currentFavorite.id+'"><div class="indicatorContainerLayout">'
                +'<div id="indicatorContainer-'+currentFavorite.id+'"><span class="indicator-complete" ><span></span></span></div></div></div>'
                +'<div class="oh metting-list-content"><div class="title overflowText">'+currentFavorite.meetName+'</div>'
                +'<div class="info"><span class="status '+getColorStatus(currentFavorite.state)+'">'+getStateLabel(currentFavorite.state)+'</span>&nbsp;<span class="time">'+startTime+'</span>&nbsp;'
                +'<span class="YaYa-metting-label">'+currentFavorite.meetType+'&nbsp;&nbsp;'+cme+'&nbsp;'+xs+'</span></div>'
                +'<div class="info overflowText last">'+lecturer+'  '+lecturerTitle+'  '+organizer+'</div></div></a></li>';
            $("#favoriteUL").append(liHtml);
            var completeProgress = currentFavorite.completeProgress;
            if (completeProgress != undefined && completeProgress!=''){
                var cp = parseInt(completeProgress);
                $("#indicatorContainer-"+currentFavorite.id).radialIndicator({initValue : parseInt(currentFavorite.completeProgress), displayNumber:cp == 100});
            }else{
                $("#fr_"+currentFavorite.id).html('<div class="metting-list-img"><img src="'+currentFavorite.lecturerHead+'" alt="" class="img-responsive" ></div>');
            }
        }

    }

    $(function(){
        pageFavorite();
    })

    $(window).scroll(function () {
        //已经滚动到上面的页面高度
        var scrollTop = $(this).scrollTop();
        //页面高度
        var scrollHeight = $(document).height();
        //浏览器窗口高度
        var windowHeight = $(this).height();
        //此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
        if (scrollTop + windowHeight == scrollHeight) {
            //dragThis.insertDom();
            pageFavorite();
        }
    });
</script>
</html>

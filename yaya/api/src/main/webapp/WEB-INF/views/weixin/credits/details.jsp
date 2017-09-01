<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/7/31
  Time: 15:20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的象数 - 交易明细</title>
    <%@include file="/WEB-INF/include/page_context_weixin.jsp"%>
    <script src="${ctxStatic}/js/util.js"></script>
</head>
<body class="gary-reg">

<div class="warp ">

    <div class="item">
        <div class="item-area">
            <div class="user-money-details" id="detailContainer">
                <c:forEach items="${page.dataList}" var="detail">
                    <div class="user-money-details-time"><span class="item"><fmt:formatDate value="${detail.costTime}" pattern="HH:ss"/></span></div>
                    <div class="user-money-details-item">
                        <div class="title"><strong>${detail.payType}成功</strong></div>
                        <div class="time"><fmt:formatDate value="${detail.costTime}" pattern="yyyy年M月d日"/> </div>
                        <div class="number">
                            ${detail.type == 0? "-":"+"} ${detail.cost}
                        </div>
                        <div class="user-money-details-list">
                            <ul>
                                <li>
                                    <div class="rowTitle fl">${detail.payType}成功：</div>
                                    <div class="oh">${detail.description}</div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>


</div>
<script>
    var pageNum = 1;
    var hasMore = parseInt("${page.pages}") > 1;
    var pageSize = parseInt("${page.pageSize}");

    function pageDetails() {
        if (hasMore){
            $.get('${ctx}/weixin/user/credits/details/page',{"pageNum":pageNum}, function (data) {
                if (data.code == 0){
                    pageNum ++;
                    hasMore = data.data.length == pageSize;
                    fillView(data.data);
                }
            },'json');
        }
    }

    function fillView(details){

        for (var index in details){
            var detail = details[index];
            var costTimeSimple = new Date(detail.costTime).format("hh:ss");
            var costTime = new Date(detail.costTime).format("yyyy年M月d日");
            var costDetail = (detail.type == 0?"-":"+")+detail.cost;

            var html = '<div class="user-money-details-time"><span class="item">'+costTimeSimple+'</span></div>'
                +'<div class="user-money-details-item"><div class="title"><strong>'+detail.payType+'成功</strong></div>'
                +'<div class="time">'+costTime+' </div><div class="number">'
                +costDetail+'</div><div class="user-money-details-list"><ul><li><div class="rowTitle fl">'+detail.payType+'成功：</div>'
                +'<div class="oh">'+detail.description+'</div></li></ul></div></div>';
            $("#detailContainer").append(html);
        }
    }

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
            pageDetails();
        }
    });
</script>
</body>
</html>

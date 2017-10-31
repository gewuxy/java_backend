<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/18
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>转载资源</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">

</head>
<body>
    <div class="popup-tab-main clearfix">
        <table class="table-box-1">
            <colgroup>
                <col class="table-td-6">
                <col class="table-td-8">
                <col class="table-td-3">
                <col class="table-td-7">
            </colgroup>
            <tbody>
                <c:forEach items="${page.dataList}" var="a">
                    <tr>
                        <td class="table-td-6">
                            <a href="javascript:;" class="popup-player-hook">${a.title}</a>
                        </td>
                        <td class="table-td-8">
                            <span class="overflowText-nowrap-flex">${a.pubUserName}</span>
                        </td>
                        <td class="table-td-3">
                            <span class="color-orange">共享资源库</span>
                        </td>
                        <td class="table-td-7">
                            <a href="javascript:;" class="popup-player-hook" courseId="${a.id}">预览</a><i class="rowSpace">|</i><a href="javascript:;" class="popup-hint" courseId="${a.id}">引用</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <%@include file="/WEB-INF/include/pageable.jsp"%>


<form id="pageForm" name="pageForm" action="${ctx}/func/res/acquired/list" method="post">
    <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
    <input type="hidden" name="pageNum" id="pageNum">
    <input type="hidden" name="jump" value="1">
</form>

<div class="distb-box distb-box-tips fx-mask-box-2">
    <div class="mask-hd clearfix">
        <h3 class="font-size-1">温馨提示</h3>
        <span class="layui-layer-close"><img src="${ctxStatic}/images/cha.png"></span>
    </div>
    <div class="mask-share-box">
        <p class="top-txt color-black">会覆盖当前的PPT文件，是否继续？</p>
    </div>
    <div class="sb-btn-box p-btm-1 t-right">
        <button class="layui-layer-close cur" id="confirmBtn">继续</button>
        <button class="layui-layer-close" id="cancelBtn">否</button>
    </div>
</div>

    <script>
        $(function () {
            $('.popup-hint').on('click',function(){
                layer.open({
                    type: 1,
                    fix: false, //不固定
                    title: false,
                    area: ['382px'],
                    content: $('.fx-mask-box-2')
                });
            })


            $('.popup-hint').click(function () {
                var courseId = $(this).attr("courseId");
                $("#confirmBtn").click(function(){
                    $.get('${ctx}/func/meet/ppt/quote',{'courseId':courseId,'meetId':'${meetId}','moduleId':'${moduleId}'},function (data) {
                        if(data.code == 0){
                            window.parent.location.reload();
                        }else{
                            top.layer.msg(data.err);
                        }
                    },'json');
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

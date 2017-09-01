<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/22
  Time: 17:43
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>草稿箱</title>
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
            <li >
                <a href="${ctx}/func/meet/edit">会议发布<i></i></a>
            </li>
            <li  class="cur">
                <a>草稿箱<i></i></a>
            </li>
            <li>
                <a href="${ctx}/func/meet/list">已发布会议<i></i></a>
            </li>
        </ul>
    </div>

    <div class="tab-bd">
        <div class="my-mlist-shedow ">
            <div class="my-top">
                <div class="fl">草稿箱</div>
                <div class="clearfix"></div>
            </div>
            <table class="table-box-1">
                <colgroup>
                    <col class="table-td-6">
                    <col class="table-td-4">
                    <col class="table-td-5">
                </colgroup>
                <tbody>
                <c:forEach items="${page.dataList}" var="meet">
                    <tr>
                        <td class="table-td-6">
                            <a href="${ctx}/func/meet/edit?id=${meet.id}">${meet.meetName}<i class="style-1 listTips">${meet.stateName}</i></a>
                        </td>
                        <td class="table-td-4">
                            <span>不限参与</span><i class="rowSpace">|</i><span ><fmt:formatDate value="${meet.createTime}" pattern="yyyy-MM-dd HH:mm"/> </span>
                        </td>
                        <td class="table-td-5"><a href="${ctx}/func/meet/edit?id=${meet.id}"><span class="icon iconfont icon-minIcon"></span>&nbsp;编辑</a><i class="rowSpace">|</i><a href="${ctx}/func/meet/draft/copy?id=${meet.id}">复制草稿</a><i class="rowSpace">|</i><a style="cursor: pointer;" class="del" meetId="${meet.id}">彻底删除</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%@include file="/WEB-INF/include/pageable.jsp"%>
            <form id="pageForm" name="pageForm" method="get" action="${ctx}/func/meet/draft">
                <input type="hidden" name="pageNum" id="pageNum" value="${page.pageNum}" />
                <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
            </form>
        </div>


    </div>
</div>

</div>
<script>
    $(function(){
        $(".del").click(function(){
            var meetId=  $(this).attr("meetId");
            top.layer.confirm("此操作不可逆,是否确定进行此操作？",function(){
               top.layer.closeAll();
               $.get('${ctx}/func/meet/del',{'meetId':meetId}, function (data) {
                   if(data.code == 0){
                       window.location.reload();
                   }else{
                       top.layer.msg(data.err);
                   }
               },'json');
            });
        });
    });
</script>
</body>
</html>

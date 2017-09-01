<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/6/8
  Time: 10:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>已发送记录</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<div class="g-main clearfix">
    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-02.png" alt="" />
                </div>
                <div class="oh">
                    <p><strong>群发消息</strong></p>
                    <p>重要消息网站直接推送到手机app，不错过每一条通知</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->

    <div class="tab-hd">
        <ul class="tab-list clearfix">
            <li >
                <a href="${ctx}/func/msg/add">群发消息<i></i></a>
            </li>
            <li class="cur">
                <a  >已发送<i></i></a>
            </li>
        </ul>
    </div>

    <div class="tab-bd">
        <div class="table-box-div1">
            <div class="table-top-box table-top-box-spcing clearfix">
                <strong>已发送</strong>
            </div>
            <table class="table-box-3">
                <thead>
                <tr>
                    <td class="tb-w-7"><i>消息</i></td>
                    <td class="tb-w-3">会议链接</td>
                    <td class="tb-w-4">接收者</td>
                    <td class="tb-w-3">发送时间</td>
                    <td class="tb-w-4">状态</td>
                </tr>
                </thead>
                <tbody>
                <input value="0" type="hidden" id="classNum">
                <c:forEach items="${page.dataList}" var="msg">
                    <tr>
                        <td class="tb-w-7" title="${msg.content}"><strong>${msg.title}</strong><br>${msg.content}</td>
                        <td class="tb-w-3">
                            <c:choose>
                                <c:when test="${msg.msgType == 0}">
                                    无会议链接
                                </c:when>
                                <c:when test="${msg.msgType == 1 && not empty(msg.meetId)}">
                                    <a href="${ctx}/func/meet/view?id=${msg.meetId}&tag=1">${msg.meetName}</a>
                                </c:when>
                            </c:choose>
                        </td>
                        <td class="tb-w-4"><span class="color-blue"><strong>${msg.receiveName}</strong></span></td>
                        <td class="tb-w-3"><fmt:formatDate value="${msg.sendTime}" pattern="yyyy/MM/dd HH:mm"/> </td>
                        <td class="tb-w-4">${msg.state==0?'失败':'成功'}</td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
            <%@include file="/WEB-INF/include/pageable.jsp"%>
            <form id="pageForm" name="pageForm" action="${ctx}/func/msg/list" method="post">
                <input type="hidden" name="pageNum" value="${page.pageNum}">
                <input type="hidden" name="pageSize" value="${page.pageSize}">
            </form>
        </div>
    </div>
</div>

</body>
</html>

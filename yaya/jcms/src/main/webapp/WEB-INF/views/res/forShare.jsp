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
<div class="tab-con-wrap" style="display: block;">
    <div class="popup-tab-main clearfix">
        <table class="table-box-1">
            <colgroup>
                <col class="table-td-6">
                <col class="table-td-8">
                <col class="table-td-3">
                <col class="table-td-7">
            </colgroup>
            <tbody>
            <c:forEach items="${page.dataList}" var="s">
                <tr>
                    <td class="table-td-6">
                        <a href="javascript:;" class="popup-player-hook">${s.title}</a>
                    </td>
                    <td class="table-td-8">
                        <span class="overflowText-nowrap-flex">${s.pubUserName}</span>
                    </td>
                    <td class="table-td-3">
                        <c:choose>
                            <c:when test="${s.shareType == 1}">
                                <span class="color-orange">支付象数：${s.credits}</span>
                            </c:when>
                            <c:when test="${s.shareType == 2}">
                                <span class="color-green">奖励象数：${s.credits}</span>
                            </c:when>
                            <c:when test="${s.shareType == 0}">
                                <span>&nbsp;</span>
                            </c:when>
                        </c:choose>
                    </td>
                    <td class="table-td-7">
                        <a href="javascript:;" class="popup-player-hook">预览</a><i class="rowSpace">|</i><a href="javascript:;" class="popup-hint">转载</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <%@include file="/WEB-INF/include/pageable.jsp"%>
</div>
<form id="pageForm" name="pageForm" action="${ctx}/func/res/share/list" method="post">
    <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
    <input type="hidden" name="pageNum" id="pageNum">
    <input type="hidden" name="jump" value="1">
</form>
</body>
</html>

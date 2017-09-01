<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/6/12
  Time: 9:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>签到统计</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>

<body>
<div class="g-main clearfix">
    <%@include file="/WEB-INF/include/stastic_header.jsp"%>

    <div class="tj-con subPage-marginTop">
        <div class="tj-content clearfix">
            <div class="tj-top clearfix">
                <h3>签到数据统计</h3>
                <a href="${ctx}/func/meet/position/exportSign?meetId=${param.id}" class="tj-more">导出Excel</a>
            </div>
            <table class="tj-table tj-table-re1 tj-table-maxSize clearfix">
                <thead>
                <tr>
                    <td class="tj-td-1 t-center">姓名</td>
                    <td class="tj-td-2 t-center">医院</td>
                    <td class="tj-td-3 t-center">科室</td>
                    <td class="tj-td-4 t-center">签到时间</td>
                    <td class="tj-td-4 t-center">是否签到成功</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.dataList}" var="s">
                    <tr>
                        <td class="tj-td-1 t-center">${s.nickname}</td>
                        <td class="tj-td-2 t-center">${s.unitName}</td>
                        <td class="tj-td-3 t-center">${s.subUnitName}</td>
                        <td class="tj-td-4 t-center"><fmt:formatDate value="${s.signTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <td class="tj-td-5 t-center">
                            <span class="color-green-2-up">
                                <c:if test="${s.signFlag==true}">成功</c:if>
                                <c:if test="${s.signFlag==false}">失败</c:if>
                            </span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <form id="pageForm" name="pageForm" action="${ctx}/func/meet/position/sign/statistics" method="post">
                <input type="hidden" name="pageNum" id="pageNum" value="${page.pageNum}">
                <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
                <input type="hidden" name="id" value="${param.id}">
            </form>
            <%@include file="/WEB-INF/include/pageable.jsp"%>
        </div>
    </div>
</div>

</body>
</html>

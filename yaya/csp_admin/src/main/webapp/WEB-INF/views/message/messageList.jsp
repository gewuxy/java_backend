<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>公告列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/message/list">公告列表</a></li>
</ul>

<form id="pageForm" name="pageForm" action="${ctx}/message/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="sendMessage" value="发布公告" size="40"  type="button" name="message" maxlength="50" class="required" style="margin-left: 1108px;color: darkorange"/>
</form>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width: 90%;" align="center">
    <thead><tr><th>公告标题</th><th width="35%">公告内容</th><th>公告发布人</th><th>公告发布时间</th><th>公告修改时间</th><shiro:hasPermission name="sys:notice:edit"><th>操作</th></shiro:hasPermission></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="mes">
            <tr style="width: 100%">
                <td width="10%">
                        <input type="text" readonly value=" ${mes.messageTitle}" style="text-align:center;width: 100%">
                </td>
                <td>
                     <textarea readonly rows="2" maxlength="2000"  class="input-xxlarge">${mes.messageContent}</textarea>
                </td>
                <td width="10%">
                        <input type="text" readonly value=" ${mes.username}" style="text-align:center;width: 100%">
                </td>
                <td width="15%">
                        <input type="text" readonly
                               value="<fmt:formatDate value="${mes.creatTime}" pattern="yyyy-MM-dd HH:mm"/>" style="text-align:center;width: 100%" />
                </td>
                <td width="15%">
                    <input type="text" readonly
                           value="<fmt:formatDate value="${mes.updateTime}" pattern="yyyy-MM-dd HH:mm"/>" style="text-align:center;width: 100%" />
                </td>
                <%--TODO  权限--%>
                <shiro:hasPermission name="sys:hospital:edit"><td width="15%">
                    <div style="text-align: center">
                    <a href="${ctx}/message/edit?id=${mes.id}">修改</a>
                    <a href="${ctx}/message/delete?id=${mes.id}" onclick="deleteMessage()">删除</a>
                    </div>
                </td></shiro:hasPermission>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${empty page.dataList}">
        <tr>
            <td colspan="7">没有查询到数据</td>
        </tr>
    </c:if>
    </tbody>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>
</body>
<script>
   $("#sendMessage").click(function () {
       location.href="${ctx}/message/messageInfo";
    })

   function deleteMessage() {
      if (confirm("确认删除该公告吗？")){
          alert("删除成功");
      }
   }
</script>
</html>

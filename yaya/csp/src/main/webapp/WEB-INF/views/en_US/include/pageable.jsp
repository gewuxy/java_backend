<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/19
  Time: 18:22
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="page-box-re">
    <a <c:if test="${page.pageNum > 1}"> href="javascript:page(1)"</c:if> >Top</a>
    <c:forEach begin="1" step="1" end="${page.pages}" var="pageN">
    <a <c:if test="${page.pageNum != pageN}"> href="javascript: page(${pageN})" </c:if> <c:if test="${page.pageNum == pageN}" >class="cur"</c:if>>${pageN}</a>
    </c:forEach>
    <a <c:if test="${page.pageNum < page.pages}"> href="javascript:page(${page.pages})"</c:if> >End</a>
</div>

<script>
    function page(pageNo){
        $("#pageForm").find("input[name='pageNum']").val(pageNo);
        $("#pageForm").submit();
    }
</script>


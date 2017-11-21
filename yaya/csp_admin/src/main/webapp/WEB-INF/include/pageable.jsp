<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/5
  Time: 11:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${page.pages > 1}">
    <div class="pagination">
        <ul class="pagination">
            <li><a <c:if test="${(page.pageNum > 1)}">href="javascript:page(${1});"</c:if>>首 页</a></li>
            <li><a <c:if test="${(page.pageNum > 1)}">href="javascript:page(${page.pageNum-1});"</c:if>class="disabled">上一页</a></li>
            <c:forEach begin="${page.pageNum-5>0?page.pageNum-5:1}" var="p" end="${page.pages-page.pageNum > 5?page.pageNum+5:page.pages}">
                <li <c:if test="${page.pageNum == p}">class="active"</c:if> ><a href="javascript:page(${p});">${p}</a></li>
            </c:forEach>
            <li><a <c:if test="${(page.pageNum < page.pages)}">href="javascript:page(${page.pageNum+1});"</c:if>>下一页</a></li>
            <li><a <c:if test="${(page.pageNum < page.pages)}">href="javascript:page(${page.pages});"</c:if> class="disabled">尾 页</a></li>
            <li><a>共${page.total}条数据 - ${page.pageNum} / ${page.pages}</a></li>
        </ul><br>
    </div>
</c:if>
<script>
    function page(pageNum){
        $("#pageForm").find("input[name='pageNum']").val(pageNum);
        $("#pageForm").submit();
    }
</script>

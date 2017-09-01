<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/5
  Time: 11:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${page.pages>1}">

<div class="page-box">
    <a
            <c:if test="${page.pageNum>1}">
                  href="javascript:page(${page.pageNum - 1})" </c:if> class="page-list page-border page-prev"><i></i></a>
    <span class="page-list">${page.pageNum} / ${page.pages}</span>
    <a
            <c:if test="${page.pageNum<page.pages}">
                href="javascript:page(${page.pageNum+1})" </c:if>
                class="page-list page-border page-next"><i></i></a>
    <input class="page-list page-border" id="pagePageNum" type="text">
    <a href="javascript:var pagePageNum = $('#pagePageNum').val(); page(pagePageNum)" class="page-list page-border page-link">跳转</a>
</div>

<script>
    function page(pageNum){
        var pages = parseInt("${page.pages}");
        if(isNaN(Number(pageNum))){
            layer.msg("请输入正确的页码");
        }
        if(pageNum > pages||pageNum == 0){
            layer.msg("请输入页码[1-"+pages+"]");
            return;
        }
        $("#pageForm").find("input[name='pageNum']").val(pageNum);
        $("#pageForm").submit();
    }
</script>
</c:if>


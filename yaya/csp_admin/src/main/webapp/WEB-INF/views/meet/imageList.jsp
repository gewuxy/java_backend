<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2018/2/1
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>背景图片</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<form id="pageForm" name="pageForm" action="${ctx}/csp/meet/background/image" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input  name="keyword" type="hidden" value="${keyword}"/>
</form>

<form id="searchForm" method="post" action="${ctx}/csp/meet/background/image" class="breadcrumb form-search">
    <input placeholder="图片名称" value="${name}" size="40"  type="search" name="keyword" maxlength="50" class="required"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>选择</th><th>图片名称</th><th>图片url</th><th>排序</th></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="img">
            <tr>
                <td>
                    <input type="radio" name="imageId" value="${img.id}">
                </td>
                <td>${img.imgName}</td>
                <td>${img.imgUrl}</td>
                <td>${img.sort}</td>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${empty page.dataList}">
        <tr>
            <td colspan="4">没有查询到数据</td>
        </tr>
    </c:if>
    </tbody>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>

<script>
    $(function(){
        $(":radio").click(function(){
            var imgId = $(this).val();
            $.ajax({
                url: '${ctx}/csp/meet/sel/image?imageId='+imgId, //目标地址
                type: "post",
                dataType: "json",
                success: function (data){
                    if (data.code == 0){
                        $("#imageName", parent.document).val(data.data.imgName);//jQuery写法给父页面传值
                        $("#imageId", parent.document).val(data.data.id);
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index);
                    }
                },
                error: function (a, n, e) {
                    layer.msg("获取数据异常："+a + " - "+n+" - "+e);
                }
            })
        });
    });

</script>
</body>
</html>

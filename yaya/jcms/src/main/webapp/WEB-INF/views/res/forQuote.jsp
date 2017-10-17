
<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/6/12
  Time: 15:52
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>资源引用</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">

</head>
<body>
<div class="metting-pupopbox-bd">
    <table class="table-box-3">
        <thead>
        </thead>
        <tbody>
        <c:forEach items="${page.dataList}" var="res">
            <tr>
                <td class="tb-w-5" style="padding: 15px;">
                    ${res.title}
                </td>
                <td class="tb-w-4" style="padding: 15px;"><a class="fx-btn-2 zai" style="cursor: pointer;" courseId="${res.id}">选择</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%@include file="/WEB-INF/include/pageable.jsp"%>
</div>
<script>
    $(function(){
        $(".zai").click(function(){
           var courseId = $(this).attr("courseId");
           $.get('${ctx}/func/meet/ppt/quote',{'courseId':courseId,'meetId':'${meetId}','moduleId':'${moduleId}'},function (data) {
               if(data.code == 0){
                   window.parent.location.reload();
               }else{
                   top.layer.msg(data.err);
               }
           },'json');
        });
    });
</script>
</body>
</html>

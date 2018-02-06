<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>会议列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/meet/list">会议列表</a></li>
    <li><a href="${ctx}/csp/meet/list">编辑讲本模板</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="pageForm" name="pageForm" action="${ctx}/csp/meet/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input  name="keyword" type="hidden" value="${keyword}"/>
    <input  name="deleted" type="hidden" value="${deleted}"/>
</form>
<form id="searchForm" method="post" action="${ctx}/csp/meet/list" class="breadcrumb form-search">
    <input placeholder="请输入会议名称" value="${keyword}" size="40"  type="search" name="keyword" maxlength="50" class="required"/>&nbsp;&nbsp;
    <select name="deleted" id="deleted" style="width: 150px;">
        <option value="">会议状态</option>
        <option value="0" ${deleted eq 0 ? 'selected':''}>正常</option>
        <option value="1" ${deleted eq 1 ? 'selected':''}>关闭</option>
    </select>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>主题</th><th>类型</th><th>播放类型</th><th>发布者</th><th>是否删除</th><th>视频直播开始时间</th><th>操作</th></tr></thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="meet">
            <tr>
                <td>${meet.title}</td>
                <td>${meet.category}</td>
                <td>${meet.playType eq 0 ? "录播" : meet.playType eq 1 ? "ppt" : "视频"}</td>
                <td>${meet.userName}</td>
                <td>${meet.deleted == true ? "关闭":"正常"}</td>
                <td>
                    <c:if test="${meet.playType == 2}">
                        <fmt:formatDate value="${meet.startTime}" pattern="yyyy/MM/dd HH:mm" ></fmt:formatDate>
                    </c:if>
                </td>
                <td>
                    <shiro:hasPermission name="csp:meet:view">
                        <a href="${ctx}/csp/meet/info?id=${meet.id}">查看</a>
                    </shiro:hasPermission>&nbsp;&nbsp;&nbsp;
                    <shiro:hasPermission name="csp:meet:close">
                        <c:if test="${meet.deleted == false}">
                            <a data-href="${ctx}/csp/meet/delete?id=${meet.id}&status=1"  onclick="layerConfirm('确认要关闭该会议吗？', this)">关闭</a>
                        </c:if>
                        <c:if test="${meet.deleted != false}">
                            <a data-href="${ctx}/csp/meet/delete?id=${meet.id}&status=0"  onclick="layerConfirm('确认撤销关闭该会议吗？', this)">撤销关闭</a>
                        </c:if>
                    </shiro:hasPermission>
                    <c:if test="${not empty meet.startTime && meet.playType == 2}">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" userId="${meet.userId}" courseId="${meet.id}" class="download">生成视频下载地址</a>
                    </c:if>
                </td>
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
</html>

<script>
    $(function () {
        $(".download").click(function () {
            var userId = $(this).attr("userId");
            var courseId = $(this).attr("courseId");
            $.get('${ctx}/csp/meet/download/address',{"userId":userId,"courseId":courseId}, function (data) {
                if (data.code == 0){
                  layer.alert("视频下载地址：" + data.data);
                }else{
                    layer.msg(data.err);
                }
            },'json');
        });
    });

</script>
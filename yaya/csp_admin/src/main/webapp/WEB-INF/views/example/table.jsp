<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>医院列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/region/list">医院列表</a></li>
</ul>
<form id="pageForm" name="pageForm" action="${ctx}/sys/hospital/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input type="hidden" name="keyword" value="${keyword}">
    <input type="hidden" name="level" value="${level}">
    <input type="hidden" name="province" value="${province}">
    <input type="hidden" name="city" value="${city}">
    <input type="hidden" name="preid" value="${preid}">
</form>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="searchForm" method="post" class="breadcrumb form-search">
    <input type="hidden" id="preid" name="preid" value="${preid}">
    <select id="level" name="level" >
        <option value="">所有级别</option>
        <option value="一级" ${level eq '一级'?"selected":""}>一级医院</option>
        <option value="二级" ${level eq '二级'?"selected":""}>二级医院</option>
        <option value="三级" ${level eq '三级'?"selected":""}>三级医院</option>
        <option value="其他" ${level eq '其他'?"selected":""}>其他医院</option>
    </select>&nbsp;&nbsp;
    <select id="province" name="province" style="width: 150px;">
        <option value="">所有省份</option>
        <c:forEach items="${provinces}" var="p">
            <option value="${p.name}" ${p.name eq province?"selected":""} regionId="${p.id}">${p.name}</option>
        </c:forEach>
    </select>&nbsp;&nbsp;
    <select id="city" name="city" style="width: 150px;">
        <option value="">所有城市</option>
        <c:forEach items="${cities}" var="c">
            <option value="${c.name}" ${city eq c.name?"selected":''}>${c.name}</option>
        </c:forEach>
    </select>&nbsp;&nbsp;
    <input placeholder="医院名称" value="${keyword}" size="40"  type="search" name="keyword" maxlength="50" class="required"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
</form>
<input type="hidden" id="preid" value="">
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>名称</th><th>级别</th><th>电话</th><th>省份</th><th>城市</th><th>详细地址</th><shiro:hasPermission name="sys:region:edit"><th>操作</th></shiro:hasPermission></tr></thead>
    <tbody>
    <c:if test="${not empty page.datas}">
        <c:forEach items="${page.datas}" var="hos">
            <tr>
                <td>${hos.name}</td>
                <td>${hos.level}</td>
                <td>${hos.tel}</td>
                <td>${hos.province}</td>
                <td>${hos.city}</td>
                <td>${hos.address}</td>
                <shiro:hasPermission name="sys:hospital:edit"><td>
                    <a href="${ctx}/sys/hospital/edit?id=${hos.id}">修改</a>
                    <a data-href="${ctx}/sys/region/delete?id=${hos.id}" href="#" onclick="layerConfirm('确认要删除该医院信息吗？', this.data-href)">删除</a>

                </td></shiro:hasPermission>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${empty page.datas}">
        <tr>
            <td colspan="7">没有查询到数据</td>
        </tr>
    </c:if>
    </tbody>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>
</body>
<script>
    $(function(){
        $("#province").change(function(){
            var regionId = $(this).find("option:selected").attr("regionId");
            $("#preid").val(regionId);
            showCities(regionId);
        });
    });
    function showCities(preid) {
        var selectedCity = "${city}";
        if(preid != undefined){
            $.get('${ctx}/sys/hospital/cities',{"preid":preid}, function (data) {
                $('#city').html("");
                for(var index in data.data){
                    var selectedFlag = selectedCity == data.data[index].name?'selected':'';
                    $("#city").append('<option value="'+data.data[index].name+'" '+selectedFlag+'>'+data.data[index].name+'</option>');
                }
            },"json");
        }else{
            $('#city').html("");
        }
    }
</script>
</html>

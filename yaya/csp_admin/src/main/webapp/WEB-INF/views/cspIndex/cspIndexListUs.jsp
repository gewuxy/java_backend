<%--
  Created by IntelliJ IDEA.
  User: jianLiang
  Date: 2017/12/19
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <script src="${ctxStatic}/jquery/jquery-1.12.4.min.js"></script>
    <link href="${ctxStatic}/bootstrap/2.3.1/css_readable/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>

    <!--新增的-->
    <link rel="stylesheet" href="${ctxStatic}/bootstrap/added/admin-style.css">
    <link href="${ctxStatic}/bootstrap/added/daterangepicker.css" rel="stylesheet">

    <script src="${ctxStatic}/bootstrap/added/moment.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/bootstrap/added/jquery.daterangepicker.js"></script>
    <script src="${ctxStatic}/bootstrap/added/echarts.min.js"></script>

</head>
<body>
<ul class="nav nav-pills">
    <li><a href="${ctx}/csp/userInfo/list">海内</a></li>
    <li class="active"><a href="${ctx}/csp/userInfo/list_us">海外</a></li>
</ul>
<h3 class="page-title">用户概况</h3>
<div class="top-info clearfix">
    <div class="row-fluid ">
        <div class="row-span span4">
            <h6 class="title"><a href="/routes_pages/userManage.html">昨日新增</a></h6>
            <p><strong class="price">${newUserCount}</strong></p>
        </div>
        <div class="row-span span4">
            <h6 class="title"><a href="/routes_pages/moneyStats.html">昨日进账</a></h6>
            <p><strong class="price">${newMoney}</strong></p>
        </div>
        <div class="row-span span4">
            <h6 class="title"><a href="/routes_pages/userManage.html">总用户</a></h6>
            <p><strong class="price">${allUserCount}</strong></p>
        </div>
    </div>
</div>

<div class="text-center">
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span8">
                <div id="echarts-2" class="echarts echarts-2"></div>
            </div>
            <div class="span4">
                <div class="index-box-2 text-left" >
                    <!--Body content-->
                    <h5>版本用户数</h5>
                    <p>标准版用户： ${standardEditionCount}人</p>
                    <p>高级版用户： ${premiumEditionCount}人</p>
                    <p>专业版用户： ${professionalEditionCount}人</p>
                </div>
            </div>
        </div>
    </div>

</div>
<script src="${ctxStatic}/bootstrap/added/map/raphael-min.js"></script>
<script src="${ctxStatic}/bootstrap/added/map/chinaMapConfig.js"></script>
<script src="${ctxStatic}/bootstrap/added/map/map.js"></script>
<script src="${ctxStatic}/js/util.js"></script>

<h3 class="page-title">海外用户新增列表</h3>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>ID</th>
        <th>昵称</th>
        <th>注册日期</th>
        <th>套餐等级</th>
        <th>套餐日期</th>
        <th>付费次数</th>
        <th>付费总额</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
    <c:forEach var="user" items="${page.dataList}">
    <tr>
                <td>${user.uid}</td>
                <td>${user.nickName}</td>
                <td><fmt:formatDate value="${user.registerTime}" type="both" dateStyle="full"/></td>
                <td>${user.packageId eq 1 ? "标准版": user.packageId eq 2 ? "高级版": user.packageId eq 3 ? "专业版":"<span style='color: #9c0001'>未登录</span>"}</td>
                <td>
                    <c:if test="${not empty user.packageStart}">
                    <fmt:formatDate value="${user.packageStart}" pattern="yyyy-MM-dd"/>至<fmt:formatDate value="${user.packageEnd}" pattern="yyyy-MM-dd"/>
                    </c:if>
                    <c:if test="${empty user.packageStart && user.packageId eq 1}">
                        无期限
                    </c:if>
                    <c:if test="${empty user.packageStart && empty user.packageId}">
                        <span style='color: #9c0001'>无</span>
                    </c:if>
                </td>
                <td>${not empty user.payTimes ? user.payTimes : 0}</td>
                <td>${not empty user.payMoneyUs ? user.payMoneyUs : 0}</td>
    </tr>
    </c:forEach>
    </c:if>
    <tr>
        <c:if test="${empty page.dataList}">
    <tr>
        <td colspan="7">没有查询到数据</td>
    </tr>
    </c:if>
    </tr>
    </tbody>
</table>
<%@include file="/WEB-INF/include/pageable.jsp"%>


<script>
    $(function(){

        //图表加载
        var dom = document.getElementById('echarts-2');
        var myChart = echarts.init(dom);
        var app = {};
        option = null;
        option = {
            title: {
                text: '套餐版本统计',
                left: 'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            series : [
                {
                    type: 'pie',
                    radius : '65%',
                    center: ['50%', '50%'],
                    selectedMode: 'single',
                    data:[
                        {value:${standardEditionCount}, name: '标准版'},
                        {value:${premiumEditionCount}, name: '高级版'},
                        {value:${professionalEditionCount}, name: '专业版'}
                    ],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }



    });
</script>


</body>
</html>

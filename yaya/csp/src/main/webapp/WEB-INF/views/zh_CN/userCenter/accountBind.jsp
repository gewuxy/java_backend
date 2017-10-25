<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>个人中心 - 账号绑定</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>

</head>


<body>
<div id="wrapper">
    <%@include file="/WEB-INF/include/header_zh_CN.jsp" %>
    <div class="admin-content bg-gray" >
        <div class="page-width clearfix">
            <div class="user-module clearfix">
                <div class="row clearfix">
                    <div class="col-lg-4">
                        <%@include file="left.jsp"%>
                    </div>
                    <div class="col-lg-8">
                        <%@include file="user_include.jsp" %>
                        <div class="user-content user-content-levelHeight item-radius">
                            <div class="binding-list">
                                <ul>
                                    <li class="phone">
                                        <c:if test="${not empty dto.mobile}">
                                            <a href="#" class="fr binding-btn ">解绑</a>
                                            <img src="${ctxStatic}/images/icon-user-phone.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty dto.mobile}">
                                            <a href="#" class="fr binding-btn ">绑定</a>
                                            <img src="${ctxStatic}/images/icon-user-phone.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${dto.mobile}</span>
                                    </li>
                                    <li class="wechat">
                                        <a href="#" class="fr binding-btn ">解绑</a>
                                        <img src="${ctxStatic}/images/icon-user-wechat.png" alt="">
                                        <span class="status status-on"></span>
                                        <span class="main">赵悦宾</span>
                                    </li>
                                    <li class="weibo">
                                        <a href="#" class="fr binding-btn ">解绑</a>
                                        <img src="${ctxStatic}/images/icon-user-weibo.png" alt="">
                                        <span class="status status-on"></span>
                                        <span class="main">zhaoyeunobin</span>
                                    </li>
                                    <li class="email">
                                        <a href="#" class="fr binding-btn ">解绑</a>
                                        <img src="${ctxStatic}/images/icon-user-email.png" alt="">
                                        <span class="status status-on"></span>
                                        <span class="main">15646157@126.com</span>
                                    </li>
                                    <li class="medcn">
                                        <a href="#" class="fr binding-btn color-blue">绑定</a>
                                        <img src="${ctxStatic}/images/icon-user-medcn.png" alt="">
                                        <span class="status status-off"></span>
                                        <span class="main">敬信数字平台</span>
                                    </li>
                                </ul>
                                <span class="line-bg"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script>

    $(function () {

        $("#config_1").parent().attr("class","cur");

        $("#update").click(function () {
            var userName = $.trim($("#userName").val());
            if(userName == ''){
                $("#nameSpan").attr("class","cells-block error");
                return;
            }
            var info = $("#info").val();
            $.get('${ctx}/mgr/user/updateInfo',{"userName":userName,"info":info}, function (data) {
                if (data.code == 0){
                    $("#name", window.parent.document).html(userName);
                    layer.msg("修改成功");
                }else{
                    layer.msg("修改失败");
                }
            },'json');

        });
    });
</script>

</body>
</html>




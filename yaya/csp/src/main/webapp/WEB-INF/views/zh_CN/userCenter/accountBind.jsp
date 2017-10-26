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
                                            <a href="#" class="fr binding-btn " type="6" id="unbind_6" action_type="unbind">解绑</a>
                                            <img src="${ctxStatic}/images/icon-user-phone.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty dto.mobile}">
                                            <a href="#" class="fr binding-btn color-blue" type="6" action_type="bind">绑定</a>
                                            <img src="${ctxStatic}/images/icon-user-phone.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${empty dto.mobile?"未绑定":dto.mobile}</span>
                                    </li>
                                    <li class="wechat">
                                        <c:if test="${not empty weChat}">
                                            <a href="#" class="fr binding-btn " type="1" id="unbind_1" action_type="unbind">解绑</a>
                                            <img src="${ctxStatic}/images/icon-user-wechat.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty weChat}">
                                            <a href="#" class="fr binding-btn color-blue" type="1" action_type="bind">绑定</a>
                                            <img src="${ctxStatic}/images/icon-user-wechat.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${empty weChat ?"未绑定":weChat}</span>
                                    </li>
                                    <li class="weibo">
                                        <c:if test="${not empty weiBo}">
                                            <a href="#" class="fr binding-btn " type="2" id="unbind_2" action_type="unbind">解绑</a>
                                            <img src="${ctxStatic}/images/icon-user-weibo.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty weiBo}">
                                            <a href="#" class="fr binding-btn color-blue" type="2" action_type="bind">绑定</a>
                                            <img src="${ctxStatic}/images/icon-user-weibo.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${empty weiBo ?"未绑定":weiBo}</span>
                                    </li>
                                    <li class="email">
                                        <c:if test="${not empty dto.email}">
                                            <a href="#" class="fr binding-btn " type="7" id="unbind_7" action_type="unbind">解绑</a>
                                            <img src="${ctxStatic}/images/icon-user-email.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty dto.email}">
                                            <a href="#" class="fr binding-btn color-blue" type="7" action_type="bind">绑定</a>
                                            <img src="${ctxStatic}/images/icon-user-email.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${empty dto.email ?"未绑定":dto.email}</span>
                                    </li>
                                    <li class="medcn">
                                        <c:if test="${not empty YaYa}">
                                            <a href="#" class="fr binding-btn " type="5" id="unbind_5" action_type="unbind">解绑</a>
                                            <img src="${ctxStatic}/images/icon-user-medcn.png" alt="">
                                            <span class="status status-on"></span>
                                        </c:if>
                                        <c:if test="${empty YaYa}">
                                            <a href="#" class="fr binding-btn color-blue" type="5" action_type="bind">绑定</a>
                                            <img src="${ctxStatic}/images/icon-user-medcn.png" alt="">
                                            <span class="status status-off"></span>
                                        </c:if>
                                        <span class="main">${empty YaYa ?"未绑定":"敬信数字平台"}</span>
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
        $("#config_3").parent().attr("class","cur");

        if(${not empty err}){
            layer.msg('${err}');
        }

        //解绑操作
        $("a[action_type='unbind']").click(function () {
            var type = $(this).attr("type");
            $.get('${ctx}/mgr/user/unbind',{"type":type}, function (data) {
                if (data.code == 0){
                    layer.msg('解绑成功',{
                        time: 300
                    },function(){
                           window.location.href = "${ctx}/mgr/user/toAccount";
                    });
                }else{
                    layer.msg(data.err);
                }
            },'json');
        });


        //绑定操作
        $("a[action_type='bind']").click(function () {
            var type = $(this).attr("type");

            $.get('${ctx}/mgr/user/jumpOauth',{"type":type}, function (data) {
                if (data.code == 0){
                    window.location.href=data.data;
                }else{
                    layer.msg(data.err);
                }
            },'json');
        });

    });
</script>

</body>
</html>




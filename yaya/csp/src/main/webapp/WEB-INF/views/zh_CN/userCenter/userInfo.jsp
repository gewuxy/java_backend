<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>个人中心 - 我的信息</title>
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
<body >
    <div id="wrapper">
        <%@include file="/WEB-INF/include/header_zh_CN.jsp" %>
        <div class="admin-content bg-gray" >
            <div class="page-width clearfix">
                <div class="user-module clearfix">
                    <div class="row clearfix">
                        <div class="col-lg-4">
                            <div class="user-left-box">
                                <div class="user-userinfo clearfix item-radius">
                                    <div class="img"><img src="${dto.avatar}" alt="" style="widtH:126px; height:126px;"></div>
                                    <div class="name">${dto.userName}</div>
                                    <div class="email">${dto.email}</div>
                                    <c:if test="${fn:length(dto.bindInfoList) != 0}">
                                    <div class="binding">
                                        <c:forEach items="${dto.bindInfoList}" var="list">
                                            <c:if test="${list.thirdPartyId == 1}">
                                               <img src="./images/icon-user-wechat.png" alt="">
                                            </c:if>
                                            <c:if test="${list.thirdPartyId == 2}">

                                            </c:if>
                                                    <img src="./images/icon-user-weibo.png" alt="">
                                        </c:forEach>
                                    </div>
                                    </c:if>
                                    <%--<img src="./images/icon-user-facebook.png" alt="">--%>
                                </div>
                                <div class="user-statistics item-radius">
                                    <div class="title">PPT统计</div>
                                    <div class="main line">上传数量 <img src="./images/user-statistics-icon.png" alt="" class="icon"><span class="item"><span class="num">${dto.pptCount}</span>个</span></div>
                                    <div class="main">分享次数 <img src="./images/user-statistics-icon.png" alt="" class="icon"><span class="item"><span class="num">${dto.shareCount}</span>个</span></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-8">
                            <div class="user-menu clearfix item-radius">
                                <ul>
                                    <li  class="cur"><a href="user-01.html">我的信息</a></li>
                                    <li><a href="user-02.html">头像设置</a></li>
                                    <li ><a href="user-03.html">账号管理</a></li>
                                    <li><a href="user-04.html">流量管理</a></li>
                                    <li class="last"><a href="user-05.html">修改密码</a></li>
                                </ul>
                            </div>
                            <div class="user-content user-content-levelHeight item-radius">
                                <div class="formrow">
                                    <div class="formTitle">姓名</div>
                                    <div class="formControls">
                                        <input type="text" id="userName" class="textInput" placeholder="赵悦宾" value="${dto.userName}">
                                    </div>
                                </div>
                                <div class="formrow">
                                    <div class="formTitle">简介</div>
                                    <div class="formControls">
                                        <textarea name="" id="info" cols="30" rows="10" class="textInput">${dto.info}</textarea>
                                    </div>
                                </div>
                                <div class="formrow">
                                    <input id="update" type="button" class="button login-button buttonBlue last" value="保存">
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <%@include file="/WEB-INF/include/footer_zh_CN.jsp"%>


    <script>
        $(function(){

            $("#update").click(function () {
                var userName = $("#userName").val();
                var info = $("#info").val();
                $.get('${ctx}/mgr/user/updateInfo',{"userName":userName,"info":info}, function (data) {
                    if (data.code == 0){
                        layer.msg("修改成功");
                    }else{
                        layer.msg("修改失败");
                    }
                },'json');

            });

        })

    </script>
</body>
</html>
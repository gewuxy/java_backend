<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                        <div class="user-content user-content-levelHeight item-radius" >
                            <div class="formrow login-form-item">
                                <div class="formTitle">姓名</div>
                                <div class="formControls">
                                    <input type="text" id="userName" class="textInput" placeholder="" maxlength="18" value="${dto.userName}">
                                    <span class="cells-block error none" id="nameSpan"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;姓名不能为空</span>
                                </div>
                            </div>
                            <div class="formrow">
                                <div class="formTitle">简介</div>
                                <div class="formControls">
                                    <textarea name="" id="info" cols="30" rows="10" class="textInput" placeholder="" maxlength="300">${dto.info}</textarea>
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

<script>

    $(function () {

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






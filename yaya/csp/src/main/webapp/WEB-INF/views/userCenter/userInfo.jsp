<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html >
<head>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <title><fmt:message key="page.header.me"/> -<fmt:message key="page.title.user.title"/> -<fmt:message key="page.common.appName"/> </title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
</head>


<body>
<div id="wrapper">
    <%@include file="../include/header.jsp" %>
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
                                <div class="formTitle"><fmt:message key="page.words.nickname"/> </div>
                                <div class="formControls">
                                    <input type="text" id="nickName" class="textInput" placeholder="" maxlength="18" value="${dto.nickName}">
                                    <span class="cells-block error none" id="nameSpan"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<fmt:message key="page.words.nickname.none"/> </span>
                                </div>
                            </div>
                            <div class="formrow">
                                <div class="formTitle"><fmt:message key="page.words.info"/> </div>
                                <div class="textarea" id="wordCount">
                                    <textarea name="" id="info" cols="30" rows="10" class="textInput" placeholder="" maxlength="300">${dto.info}</textarea>
                                    <span class="t-right"><var class="word">300</var></span>
                                </div>
                            </div>
                            <div class="formrow">
                                <input id="update" type="button" class="button login-button buttonBlue last" value="<fmt:message key="page.common.save"/>">
                            </div>

                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <%@include file="../include/footer.jsp"%>
</div>

<script>

    $(function () {

        $("#config_1").parent().attr("class","cur");

        $("#update").click(function () {
            var nickName = $.trim($("#nickName").val());
            if(nickName == ''){
                $("#nameSpan").attr("class","cells-block error");
                return;
            }
            $("#nameSpan").attr("class","cells-block error none");
            var info = $("#info").val();
            ajaxPost('${ctx}/mgr/user/updateInfo',{"nickName":nickName,"info":info}, function (data) {
                if (data.code == 0){
                    $("#name", window.parent.document).html(nickName);
                    layer.msg("<fmt:message key="page.words.update.success"/>");
                }else{
                    layer.msg("page.words.update.fail");
                }
            });

        });

        //可输入字数统计
        var wordCount = $("#wordCount"),
            textArea = wordCount.find("textarea"),
            word = wordCount.find(".word");
        //调用
        statInputNum(textArea,word);
    });

    /*

     * 剩余字数统计

     * 注意 最大字数只需要在放数字的节点哪里直接写好即可 如：<var class="word">200</var>

     */

    function statInputNum(textArea,numItem) {

        var max = numItem.text(),

            curLength;

        textArea[0].setAttribute("maxlength", max);

        curLength = textArea.val().length;

        numItem.text(max - curLength);

        textArea.on('input propertychange', function () {

            numItem.text(max - $(this).val().length);

        });

    }

</script>

</body>
</html>




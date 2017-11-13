<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <title>公告修改菜单</title>
    <script type="text/javascript">
        function checkTitle() {
            if ($("#title").val() == null || $("#title").val() == "") {
                confirm("标题不能为空")
                $("#btnSubmit").attr("disabled", "disabled");
            } else {
                $("#btnSubmit").removeAttr("disabled");
            }

        }

        function checkContent() {
            if ($("#content").val() == null || $("#content").val() == "" || $("#content").length > 65535) {
                confirm("内容输入有误")
                $("#btnSubmit").attr("disabled", "disabled");
            } else {
                $("#btnSubmit").removeAttr("disabled");
            }
        }

        function checkUsername() {
            if ($("#name").val() == null || $("#name").val() == "") {
                confirm("发布人不能为空")
                $("#btnSubmit").attr("disabled", "disabled");
            } else {
                $("#btnSubmit").removeAttr("disabled");
            }
        }

        $(document).ready(function () {
            $("#btnSubmit").click(function () {
                var checkList = $("input:text");
                var isCheckPass = true;
                for (var i = 0; i < checkList.size(); i++) {
                    if ($.trim(checkList[i].value) == "") {
                        checkList[i].value = "请不要输入空格!";
                        isCheckPass = false;
                        $("#btnSubmit").attr("disabled", "disabled");
                    }else {
                        $("#btnSubmit").removeAttr("disabled");
                    }
                }
                return isCheckPass;
            })
        })
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/message/edit">公告菜单修改</a></li>
</ul>
<form id="inputForm" method="post" class="form-horizontal" action="${ctx}/csp/message/update">
    <input type="hidden" id="edit_id" name="id" value="${message.id}"/>
    <div class="control-group">
        <label class="control-label">公告标题:</label>
        <div class="controls">
            <div class="input-append">
                <input id="title" name="messageTitle" type="text" value="${message.messageTitle}" onblur="checkTitle()">
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">公告内容:</label>
        <div class="controls">
            <textarea name="messageContent" rows="3" maxlength="2000" class="input-xxlarge" id="content"
                      onblur="checkContent()">${message.messageContent}</textarea>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">发布者:</label>
        <div class="controls">
            <input readonly type="text" id="name" name="sendMessageName" value="${message.username}" onblur="checkUsername()"/>
        </div>
    </div>
    <div class="form-actions">
        <%--TODO 权限--%>
        <shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                         value="保存修改"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>

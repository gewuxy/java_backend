<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <title>公告菜单</title>
    <script type="text/javascript">
        function checkTitle() {
            if ($("#title").val() == null || $("#title").val() == "") {
                //confirm("标题不能为空")
                $("#btnSubmit").attr("disabled", "disabled");
            } else {
                $("#btnSubmit").removeAttr("disabled");
            }

        }

        function checkContent() {
            if ($("#content").val() == null || $("#content").val() == "") {
                // confirm("内容输入有误")
                $("#btnSubmit").attr("disabled", "disabled");
            } else {
                $("#btnSubmit").removeAttr("disabled");
            }
        }

        function checkUsername() {
            if ($("#name").val() == null || $("#name").val() == "") {
                //confirm("发布人不能为空")
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
                    } else {
                        $("#btnSubmit").removeAttr("disabled");
                    }
                }
                return isCheckPass;
            })
        })

        $("#checkedAll").click(function () {
            $("#name").attr("readonly", "readonly")
        })
        $("#checked").click(function () {
            $("#name").removeAttr("readonly")
        })

        $("#name").blur(function () {
            var obj = $("#name").val();
            $.post("${ctx}/csp/notify/checkout", {"userName": obj}, function (data) {
                if (data == null || data == "") {
                    alert("用户名不存在")
                }
            })
        })
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active">公告详情</li>
</ul>
<form id="inputForm" method="post" class="form-horizontal" action="${ctx}/csp/notify/insert">
    <div class="control-group">
        <label class="control-label">消息标题:</label>
        <div class="controls">
            <div class="input-append">
                <input id="title" name="title" type="text" onblur="checkTitle()">
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">消息内容:</label>
        <div class="controls">
            <textarea id="content" name="content" rows="3" maxlength="2000" class="input-xxlarge"
                      onblur="checkContent()"></textarea>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">消息类型:</label>
        <div class="controls">
            <input readonly id="checkedAll" type="radio" value="0" name="notifyType">对所有人
            <input readonly id="checked" type="radio" value="1" name="notifyType">对个人
        </div>

    </div>
    <div class="control-group">
        <label class="control-label">接收消息者(个人):</label>
        <div class="controls">
            <input type="text" name="userName" id="name"/>
        </div>

    </div>
    <div class="form-actions">
        <shiro:hasPermission name="sys:message:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                            value="发 布"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>

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
        $(document).ready(function() {
            initFormValidate();
        });

      /*  $("#checkedCn").click(function () {
            $("#userName").attr("disabled", "disabled")
        })
        $("#checkedUs").click(function () {
            $("#userName").attr("disabled", "disabled")
        })
        $("#checked").click(function () {
            $("#userName").removeAttr("disabled", "disabled")
        })*/

        function checkedCnUser() {
            $("#userName").attr("disabled", "disabled")
        }
        function checkedUsUser() {
            $("#userName").attr("disabled", "disabled")
        }
        function checkedPer() {
            $("#userName").removeAttr("disabled", "disabled")
        }

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="#">公告详情</a></li>
</ul>
<form id="inputForm" method="post" class="form-horizontal" action="${ctx}/csp/notify/insert">
    <div class="control-group">
        <label class="control-label">消息标题:</label>
        <div class="controls">
            <div class="input-append">
                <input id="title" name="title" type="text">
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">消息内容:</label>
        <div class="controls">
            <textarea id="content" name="content" rows="3" maxlength="2000" class="input-xxlarge"
                     ></textarea>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">消息类型:</label>
        <div class="controls">
            <input  id="checkedCn" type="radio" value="0" name="notifyType" onclick="checkedCnUser()">对国内用户
            <input  id="checked" type="radio" value="1" name="notifyType" onclick="checkedPer()">对个人
            <input  id="checkedUs" type="radio" value="2" name="notifyType" onclick="checkedUsUser()">对国外用户
        </div>

    </div>
    <div class="control-group">
        <label class="control-label">接收消息者:</label>
        <div class="controls">
                <input id="userName" class="btn btn-primary" type="button" value="查询" onclick="selectName()" disabled="disabled"/>
                <input id="acceptId" name="acceptId" type="hidden" value="">
                <input readonly id="name" name="userName" type="search" value="">
            <script>
                function selectName() {
                    layer.open({
                        type: 2,
                        title: '用户列表页',
                        shadeClose: true,
                        shade: 0.8,
                        area: ['1000px', '600px'],
                        offset: ['100px', '350px'],
                        content: '${ctx}/csp/notify/userList',
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                            var data = body.html()//得到iframe页的body内容
                            console.log(data);
                            var name = JSON.parse(data);
                            var val =  name.nickName;
                            console.log(val);
                            var id =  name.id;
                            console.log(id);
                            $("#name").val(val);
                            $("#acceptId").val(id);
                        }
                    });
                }
            </script>
            <div>

            </div>
        </div>
    </div>
    <div class="form-actions">
<shiro:hasPermission name="csp:notify:add">
        <input id="btnSubmit" class="btn btn-primary" type="submit"
                                                            value="发 布"/>
</shiro:hasPermission>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>

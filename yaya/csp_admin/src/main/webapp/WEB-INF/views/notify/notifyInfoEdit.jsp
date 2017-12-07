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
        /*-- 修改校验  start--*/
        $(document).ready(function() {
            initFormValidate();
        });
        /*-- end--*/

        function checkedAllPeo() {
            $("#userName").attr("disabled", "disabled")
        }
        function checkedPer() {
            $("#userName").removeAttr("disabled", "disabled")
        }

        /*-- 单选回显  start--*/


        $(function () {
            var type =
            ${notify.notifyType}
            if (type == 0) {
                $("#checkedAll").attr("checked", "checked")
            } else {
                $("#checked").attr("checked", "checked")
            }
        })

        /*--end--*/
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/csp/notify/edit">公告菜单修改</a></li>
</ul>
<form id="inputForm" method="post" class="form-horizontal" action="${ctx}/csp/notify/update">
    <input type="hidden" id="edit_id" name="id" value="${notify.id}"/>
    <div class="control-group">
        <label class="control-label">消息标题:</label>
        <div class="controls">
            <div class="input-append">
                <input id="title" name="title" type="text" value="${notify.title}" onblur="checkTitle()">
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">消息内容:</label>
        <div class="controls">
            <textarea id="content" name="content" rows="3" maxlength="2000" class="input-xxlarge"
                      onblur="checkContent()">${notify.content}</textarea>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">消息类型:</label>
        <div class="controls">
            <input readonly id="checkedAll" type="radio" value="0" name="notifyType" onclick="checkedAllPeo()">对所有人
            <input readonly id="checked" type="radio" value="1" name="notifyType" onclick="checkedPer()">对个人
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">接收消息者:</label>
        <div class="controls">
            <input id="userName" class="btn btn-primary" type="button" value="查询" onclick="selectName()"/>
            <input id="acceptId" name="acceptId" type="hidden" value="${notify.acceptId}">
            <input readonly id="name" name="userName" type="search" value="${userName}">
            <script>
                function selectName() {
                    layer.open({
                        type: 2,
                        title: '用户列表页',
                        shadeClose: true,
                        shade: 0.8,
                        area: ['1000px', '90%'],
                        content: '${ctx}/csp/notify/userList',
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                            var data = body.html()//得到iframe页的body内容
                            console.log(data);
                            var name = JSON.parse(data);
                            var val =  name.userName;
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
        <shiro:hasPermission name="csp:notify:edit">
        <input id="btnSubmit" class="btn btn-primary" type="submit"
                                                         value="保存修改"/>
        </shiro:hasPermission>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>

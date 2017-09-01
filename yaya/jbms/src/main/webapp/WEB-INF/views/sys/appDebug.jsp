<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/4
  Time: 13:32
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>接口调试</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <script src="${ctxStatic}/js/cookieUtil.js"></script>
    <script>
        const jx_token = "jx_token";
        $(function(){

            $("#btnSubmit").click(function(){
                var baseUrl = "${baseUrl}";
                var params = $("#params").val();
                var type = $("#requestMethod").val();
                var url = baseUrl+$("#requestUrl").val();
                if($.trim(url) == ''){
                    layer.tips("请输入接口地址","#requestUrl");
                }
                var data = {};
                data.params = params;
                data.type = type;
                data.url = url;
                var token = getCookie(jx_token);
                if(token != null && token != undefined){
                    data.token = token;
                }
                $.ajax({
                    url:"${ctx}/sys/app/doDebug",
                    data:data,
                    dataType:"json",
                    type:"post",
                    success:function (data) {
                        $("#debugResult").val(JSON.stringify(data));
                        if(url.indexOf("login")>0 && data.data.token!= null){
                            setCookie(jx_token,data.data.token);
                        }
                    },error:function(){
                        $("#debugResult").val("服务器异常");
                    }
                });
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/menu/">医师版接口调试</a></li>
    <li><a href="${ctx}/sys/menu/edit">合理用药接口调试</a></li>
</ul>
<form id="inputForm" method="post" class="form-horizontal">
    <input type="hidden" name="token" id="token" value=""/>

    <div class="control-group">
        <label class="control-label">接口URL:</label>
        <div class="controls">
            <input type="search" name="requestUrl" id="requestUrl" maxlength="200" class="required input-xlarge"/>
            <span class="help-inline">接口的相对路径，例如：api/login, 在做其他接口之前请先调试登录接口,登录之后所有操作自动带有token</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">请求方式:</label>
        <div class="controls">
            <select id="requestMethod" name="requestMethod">
                <option value="getMethod">GET</option>
                <option value="postMethod">POST</option>
            </select>
            <span class="help-inline"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">请求参数:</label>
        <div class="controls">
            <textarea name="params"rows="3" id="params" maxlength="200" class="input-xxlarge"></textarea>
            <span class="help-inline">JSON格式的参数，例如：{"username":"xxxx@qq.com", "password":"xxxxxx"} (注意双引号)</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">返回结果:</label>
        <div class="controls">
            <textarea name="menuDesc"rows="3" id="debugResult" maxlength="200" class="input-xxlarge"></textarea>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="sys:app:yaya:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="调  试"/>&nbsp;</shiro:hasPermission>
    </div>
</form>
</body>
</html>

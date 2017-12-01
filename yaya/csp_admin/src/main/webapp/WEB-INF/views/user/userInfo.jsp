<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>用户信息</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="#">CSP用户信息</a></li>
</ul><br/>
<form id="inputForm" action="${ctx}/csp/user/update" method="post" class="form-horizontal">
    <div class="control-group">
        <label class="control-label">昵称:</label>
        <div class="controls">
            <input type="search" name="nickName" maxlength="50" class="required" value="${user.nickName}" class="required"/>
            <font color="red">*</font>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">姓名:</label>
        <div class="controls">
            <input type="search" name="userName" htmlEscape="false" maxlength="50" value="${user.userName}" class="required"/>
            <font color="red">*</font>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">邮箱:</label>
        <div class="controls">
            <input type="search" name="email" value="${user.email}" maxlength="50" class="email"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">手机:</label>
        <div class="controls">
            <input type="search" name="mobile" value="${user.mobile}" maxlength="50" class="mobile"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">国家:</label>
        <div class="controls">
            <input type="search" name="country" value="${user.country}" maxlength="50" class="realName"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">地址:</label>
        <div class="controls">
            <select id="province" name="province" style="width: 150px;" onchange="changeOption(this.options[this.options.selectedIndex].value,'#city')">
                <option value="">省份</option>
                <c:if test="${not empty province.dataList}">
                    <c:forEach items="${province.dataList}" var="p">
                        <option value="${p.name}" ${p.name eq user.province?"selected":""} regionId="${p.id}">${p.name}</option>
                    </c:forEach>
                </c:if>
            </select>&nbsp;&nbsp;
            <select id="city" name="city" style="width: 150px;" onchange="changeOption(this.options[this.options.selectedIndex].value,'#district')">
                <option value="">城市</option>
                <c:if test="${not empty city}">
                    <c:forEach items="${city}" var="c">
                        <option value="${c.name}" ${c.name eq user.city?"selected":''}>${c.name}</option>
                    </c:forEach>
                </c:if>
            </select>&nbsp;&nbsp;
            <select id="district" name="district" style="width: 150px;">
                <option value="">区</option>
                <c:if test="${not empty district}">
                    <c:forEach items="${district}" var="d">
                        <option value="${d.name}" ${d.name eq user.district?"selected":''}>${d.name}</option>
                    </c:forEach>
                </c:if>
            </select>&nbsp;&nbsp;
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">激活状态:</label>
        <div class="controls">
            <select id="active" name="active" style="width: 80px;">
                <option value="0" ${user.active == false|| user.active == null?'selected':''}>冻结</option>
                <option value="1" ${user.active == true ?'selected':''}>激活</option>
            </select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">类型:</label>
        <div class="controls">
            <select id="abroad" name="abroad" >
                <option value="0" ${user.abroad == false|| user.abroad == null?'selected':''}>国内</option>
                <option value="1" ${user.abroad == true ?'selected':''}>海外</option>
            </select>
        </div>
    </div>
    <c:if test="${actionType == 3}">
        <div class="control-group">
            <label class="control-label">是否重置密码:</label>
            <div class="controls">
                <select  name="isReset" style="width: 80px;" onchange="showMes(this.options[this.options.selectedIndex].value)">
                    <option value="0">不重置</option>
                    <option value="1">重置</option>
                </select>
                <span id="mess" style="color: red"></span>
            </div>
        </div>
    </c:if>
    <c:if test="${actionType == 1 }">
        <div class="control-group">
            <label class="control-label">上次登录:</label>
            <div class="controls">
                <label class="lbl">IP: ${user.lastLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.lastLoginTime}" type="both" dateStyle="full"/></label>
            </div>
        </div>
    </c:if>
    <div class="form-actions">
        <input type="hidden" value="${user.id}" name="id">
        <input type="hidden" value="${actionType}" name="actionType"/>
        <input type="hidden" value="${listType}" name="listType"/>
        <c:if test="${actionType == 3}">
            <shiro:hasPermission name="csp:user:edit">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="修  改"/>
            </shiro:hasPermission>
        </c:if>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
<script>
    $(document).ready(function() {
        initFormValidate();
    });

    function showMes(value){
        value == 1? $("#mess").html("重置默认密码为“111111”") : $("#mess").html("");
    }

    //根据省份城市更改选项
    function changeOption(name,id){
        $.ajax({
            url:"${ctx}/csp/user/searchOption",
            data:{"name":name},
            dataType:"json",
            type:"post",
            success:function (data) {
                    $(id).empty().append("<option value='' selected='true'>请选择</option>").select2().first().trigger("change");
                    for(var index in data.data){
                        $(id).append('<option value="'+data.data[index].name+'">'+data.data[index].name+'</option>');
                    }
                }
        });
    }
</script>
</body>
</html>

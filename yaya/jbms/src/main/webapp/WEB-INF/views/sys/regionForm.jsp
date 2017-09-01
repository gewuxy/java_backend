<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/2
  Time: 15:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>编辑菜单</title>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    layer.msg('正在提交，请稍等...',{
                        icon: 16,
                        shade: 0.01
                    });
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            $("#parentButton").click(function(){
                selecteIframe = layer.open({
                    title:"选择父区划【双击选择】",
                    type: 2,
                    area: ['300px', '400px'],
                    fixed: false,
                    shadeClose: true,
                    shade: false,
                    maxmin: false,
                    content: '${ctx}/sys/region/tree?preid=0'
                });
            });
        });
        var selecteIframe;
        function closeIframe(){
            if(selecteIframe!=null && selecteIframe != undefined){
                layer.close(selecteIframe);
            }
        }

        function setPreName(prename){
            $("#preName").val(prename);
        }

        function setPreId(preid){
            $("#preid").val(preid);
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/sys/region/list">区划列表</a></li>
    <li  class="active"><a href="${ctx}/sys/region/edit">区划${region.id == null?"添加":"修改"}</a></li>
</ul>
<form id="inputForm" action="${ctx}/sys/region/save" method="post" class="form-horizontal">
    <div class="control-group">
        <label class="control-label">区划类型:</label>
        <div class="controls">
            <select id="level" name="level" style="width: 100px;">
                <option value="1" ${region.level == 1?"selected":""}>省/直辖市</option>
                <option value="2" ${region.level == 2?"selected":""}>地区/市</option>
                <option value="3" ${region.level == 3?"selected":""}>区/县</option>
            </select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">父级区划:</label>
        <div class="controls">
            <div class="input-append">

                <input id="preid" name="preid" type="hidden" value="${parent.id}"/>

                <c:set var="parentName" scope="page" value="${parent == null?'中国':parent.name}"/>

                <input id="preName" name="preName" readonly type="search" value="${parentName}"
                       class="required" /><a id="parentButton" href="javascript:" class="btn">&nbsp;<i class="icon-search"></i>&nbsp;</a>&nbsp;&nbsp;
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">区划编码:</label>
        <div class="controls">
            <input type="search" name="id" value="${region.id}" maxlength="6" ${region.id != null?"readonly":""} class="required input-number"/>
            <span class="help-inline"><font color="red">* 例如北京区划编码为：110000</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">名称:</label>
        <div class="controls">
            <input type="search" name="name" value="${region.name}" maxlength="50" class="required input-xlarge"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">全拼:</label>
        <div class="controls">
            <input type="search" name="spell" value="${region.spell}" maxlength="50" class="required input-xlarge"/>
            <span class="help-inline">区划名称全拼</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">首拼:</label>
        <div class="controls">
            <input type="search" name="alpha" value="${region.alpha}" maxlength="50" class="required input-xlarge"/>
            <span class="help-inline">区划名称拼音首字母</span>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="sys:region:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>

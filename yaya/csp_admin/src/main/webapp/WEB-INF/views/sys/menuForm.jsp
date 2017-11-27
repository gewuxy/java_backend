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
                    title:"选择父菜单【双击选择】",
                    type: 2,
                    area: ['300px', '400px'],
                    fixed: false,
                    shadeClose: true,
                    shade: [0.4, '#000'],
                    maxmin: false,
                    content: '${ctx}/sys/menu/treeData?preid=${menu.preid}'
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
    <li><a href="${ctx}/sys/menu/list">菜单列表</a></li>
    <li  class="active"><a href="${ctx}/sys/menu/edit">菜单添加</a></li>
</ul>
<form id="inputForm" action="${ctx}/sys/menu/save" method="post" class="form-horizontal">
    <input type="hidden" name="id" value="${menu.id}"/>
    <div class="control-group">
        <label class="control-label">父菜单:</label>
        <div class="controls">
            <div class="input-append">

                <input id="preid" name="preid" type="hidden" value="${parent.id}"/>

                <c:set var="parentName" scope="page" value="${parent == null?'顶级菜单':parent.name}"/>

                <input id="preName" name="preName" readonly type="search" value="${parentName}"
                       class="required" /><a id="parentButton" href="javascript:" class="btn">&nbsp;<i class="icon-search"></i>&nbsp;</a>&nbsp;&nbsp;
            </div>
            <%--<sys:treeselect id="menu" name="parent.id" value="${menu.parent.id}" labelName="parent.name" labelValue="${menu.parent.name}"--%>
                            <%--title="菜单" url="/sys/menu/treeData" extId="${menu.id}" cssClass="required"/>--%>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">名称:</label>
        <div class="controls">
            <input type="search" name="name" value="${menu.name}" maxlength="50" class="required input-xlarge"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">链接:</label>
        <div class="controls">
            <input type="search" name="url"  maxlength="2000" value="${menu.url}" class="input-xxlarge"/>
            <span class="help-inline">点击菜单跳转的页面</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">目标:</label>
        <div class="controls">
            <input type="radio" name="target" value="mainFrame" <c:if test="${menu.target eq 'mainFrame' || menu.target == null}">checked</c:if> > <span class="help-inline">内嵌网页</span> <input type="radio" name="target" value="_blank" <c:if test="${menu.target eq '_blank'}">checked</c:if>> <span class="help-inline">新开网页</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">排序:</label>
        <div class="controls">
            <input type="search" name="sort" value="${menu.sort}" maxlength="50" class="required digits input-small"/>
            <span class="help-inline">排列顺序，升序。</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">隐藏:</label>
        <div class="controls">
            <input type="checkbox" value="true" name="hide" <c:if test="${menu.hide}">checked</c:if> >
            <span class="help-inline">该菜单或操作是否显示到系统菜单中</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">权限标识:</label>
        <div class="controls">
            <input name="perm" type="search" value="${menu.perm}" maxlength="100" class="input-xxlarge"/>
            <span class="help-inline">控制器中定义的权限标识，如：sys:menu:add</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注:</label>
        <div class="controls">
            <textarea name="menuDesc"rows="3" maxlength="200" class="input-xxlarge"></textarea>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="sys:menu:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>

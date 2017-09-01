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

</head>
<body style="overflow: hidden;">
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="inputForm" action="${ctx}/data/category/save" method="post" class="form-horizontal">
    <div class="control-group">
        <label class="control-label">栏目ID:</label>
        <div class="controls">
            <input id="id" name="id" type="search" value="${category.id}" readonly/>
            <span class="help-inline"> 系统自动生成，不可修改 </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">父栏目名称:</label>
        <div class="controls">
            <div class="input-append">
                <c:set var="parentName" scope="page" value="${parent == null?'顶级栏目':parent.name}"/>
                <input id="preName" name="preName" readonly type="search" value="${parentName}"
                       class="required" />
            </div>

        </div>
    </div>
    <div class="control-group">
        <label class="control-label">父栏目ID:</label>
        <div class="controls">
                <input id="preId" name="preId" type="search" value="${parent.id}" class="required input-xlarge"/>
                <span class="help-inline"><font color="red">*</font> 可以将其他栏目ID复制到这里来改变父级栏目 </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">名称:</label>
        <div class="controls">
            <input type="search" name="name" value="${category.name}" maxlength="50" class="required input-xlarge"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">排序:</label>
        <div class="controls">
            <input type="search" name="sort" value="${category.sort}" maxlength="50" class="required digits input-small"/>
            <span class="help-inline">排列顺序，升序。</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">叶子节点:</label>
        <div class="controls">
            <input type="checkbox" id="leaf" name="leaf" value="true" ${category.leaf?"checked":""}/>
            <span class="help-inline">叶子节点不能再创建子节点</span>
        </div>
    </div>

    <div class="form-actions">
        <shiro:hasPermission name="article:category:edit">
            <c:if test="${category.id ne '0'}">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
            </c:if>
        </shiro:hasPermission>
        <shiro:hasPermission name="article:category:add">
            <c:if test="${category.id != null && !category.leaf}"><input id="addBtn" class="btn" type="button" onclick="window.location.href = '${ctx}/data/category/edit?preId=${category.id}'" value="新 增"/>&nbsp;
            </c:if>
        </shiro:hasPermission>
        <shiro:hasPermission name="article:category:edit">
            <c:if test="${category.id != null && category.id ne '0'}">
                <input id="btnCancel" class="btn" type="button" value="删 除" onclick="layerConfirm('确定要删除此栏目吗?',this)" data-href="${ctx}/data/category/delete?id=${category.id}&preId=${parent.id}"/>
            </c:if>
        </shiro:hasPermission>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function() {
        var parentHeight = $(top.window).height();
        var bodyHeight = $("body").height();
        if(bodyHeight < parentHeight - 120){
            $("body").height(parentHeight - 120);
        }
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
        $("#preId").val(preid);
    }

    $(function(){
        if("${err}"){
            parent.refreshParentNode("${err}".indexOf("添加")>-1);
        }
    })
</script>
</body>
</html>

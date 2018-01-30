<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>单位号列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/yaya/unit/list">单位号列表</a></li>
    <li><a href="${ctx}/yaya/unit/edit">添加单位号</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="pageForm" name="pageForm" action="${ctx}/yaya/unit/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input  name="keyword" type="hidden" value="${keyword}"/>
</form>
<form id="searchForm" method="post" action="${ctx}/yaya/unit/list" class="breadcrumb form-search">
    <select name="testFlag" id="testFlag" style="width: 150px;">
        <option value="">账号类型</option>
        <option value="false" ${not empty testFlag && !testFlag ? 'selected':''}>正式账号</option>
        <option value="true" ${not empty testFlag && testFlag  ? 'selected':''}>测试账号</option>
    </select>
    /
    <select name="rcd" id="rcd" style="width: 150px;">
        <option value="">推荐状态</option>
        <option value="true" ${not empty rcd && rcd ? 'selected':''}>已推荐</option>
        <option value="false" ${not empty rcd && !rcd  ? 'selected':''}>未推荐</option>
    </select>
    /
    <input placeholder="单位号/邮箱" value="${keyword}" size="40"  type="search" name="keyword" maxlength="50" class="required"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    <shiro:hasPermission name="yaya:unit:edit">
        <input id="importBtn" class="btn btn-primary" type="button" value="导入粉丝"/>&nbsp;&nbsp;
    </shiro:hasPermission>
    <shiro:hasPermission name="yaya:code:add">
        <input id="codeBtn" class="btn btn-primary" type="button" value="生成激活码"/>
    </shiro:hasPermission>
</form>

<form id="importForm" name="importForm">
    <input type="hidden" name="fans" id="fans" value="0">
    <input type="hidden" name="codeNum" id="codeNum" value="0">
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead><tr><th width="50" valign="top"><input type="checkbox" id="checkAll"></th><th>账号</th><th>单位号</th><th>注册日期</th><th>激活码数量</th><th>是否推荐</th><th>账号类型</th><th>操作</th></tr></thead>
        <tbody>
        <c:if test="${not empty page.dataList}">
            <c:forEach items="${page.dataList}" var="user">
                <tr>
                    <td><input type="checkbox" name="unitIds" value="${user.id}"></td>
                    <td>${user.username}</td>
                    <td>${user.nickname}</td>
                    <td><fmt:formatDate value="${user.registDate}" pattern="yyyy/MM/dd"/></td>
                    <td>${empty user.activeStore ? '0': user.activeStore}</td>
                    <td>${user.tuijian ? '<span style="color:green">已推荐</span>':'<span style="color:blue">未推荐</span>'}</td>
                    <td>${user.testFlag ? '<span style="color:blue">测试账号</span>' : '<span style="color:green">正式账号</span>'}</td>
                    <td>
                        <shiro:hasPermission name="yaya:unit:edit">
                            <a href="${ctx}/yaya/unit/edit?id=${user.id}">修改</a>&nbsp;&nbsp;

                            <c:choose>
                                <c:when test="${user.tuijian}">
                                    <a style="cursor: pointer;" data-href="${ctx}/yaya/unit/rcd/${user.id}"  onclick="layerConfirm('确认要取消推荐该单位号吗？', this)">取消推荐</a>
                                </c:when>
                                <c:otherwise>
                                    <a style="cursor: pointer;" data-href="${ctx}/yaya/unit/rcd/${user.id}"  onclick="layerConfirm('确认要将该单位号设为推荐吗？', this)">设为推荐</a>
                                </c:otherwise>
                            </c:choose>
                            &nbsp;&nbsp;
                            <a style="cursor: pointer;" userId="${user.id}" class="resetPwd">重置密码</a>
                        </shiro:hasPermission>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${empty page.dataList}">
            <tr>
                <td colspan="7">没有查询到数据</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</form>
<%@include file="/WEB-INF/include/pageable.jsp"%>
<script>
    $(function(){
        $(".resetPwd").click(function(){
            var userId = $(this).attr("userId");
            top.layer.confirm("确定要重置该单位号的密码？", function(){
                $.get('${ctx}/yaya/unit/pwd/reset/' + userId, {}, function (data) {
                    if (data.code == 0){
                        top.layer.closeAll('dialog');
                        top.layer.open({
                            type: 0,
                            area:['200px', '200px'],
                            content: '密码已重置为 : ' + data.data.currentPwd //这里content是一个普通的String
                        });
                    } else {
                        layer.msg(data.err);
                        top.layer.closeAll('dialog');
                    }

                },'json');
            });

        });


        $("#checkAll").change(function(){
            if ($(this).is(":checked")){
                $("input[name='unitIds']").prop("checked", "true");
            } else {
                $("input[name='unitIds']").removeAttr("checked");
            }
        });

        // 导入粉丝
        $("#importBtn").click(function(){
            var checked = false;
            $("input[name='unitIds']").each(function(){
                if ($(this).is(":checked")){
                    checked = true;
                    return false;
                }
            });

            if (!checked){
                top.layer.msg("请勾选要导入粉丝的单位号");
                return false;
            } else {
                top.layer.prompt({title: '输入要导入的粉丝数量[1-500]，并确认', formType: 0}, function(pass, index){
                    var reg = /^[1-9]+[0-9]*$/g
                    if (!reg.test(pass)){
                        top.layer.msg("请输入正确的数字");
                    } else if(pass > 500){
                        top.layer.msg("请输入500或以下的数字");
                    } else {
                        top.layer.closeAll();
                        $("#fans").val(pass);
                        var index = top.layer.load(1, {
                            shade: [0.1,'#fff'] //0.1透明度的白色背景
                        });
                        $.post('${ctx}/yaya/unit/batch/fans', $("#importForm").serialize(), function (data) {
                            top.layer.close(index);
                            if(data.code == 0){
                                top.layer.msg('操作成功', {time:2000}, function () {
                                    window.location.reload();
                                });
                            } else {
                                top.layer.msg(data.err);
                            }
                        }, 'json');
                    }


                });
            }
        });


        // 批量生成激活码
        $("#codeBtn").click(function(){
            var checked = false;
            $("input[name='unitIds']").each(function(){
                if ($(this).is(":checked")){
                    checked = true;
                    return false;
                }
            });

            if (!checked){
                top.layer.msg("请勾选要生成激活码的单位号");
                return false;
            } else {
                top.layer.prompt({title: '输入要生成的激活码数量[1-500]，并确认', formType: 0}, function(pass, index){
                    var reg = /^[1-9]+[0-9]*$/g
                    if (!reg.test(pass)){
                        top.layer.msg("请输入正确的数字");
                    } else if(pass > 500){
                        top.layer.msg("请输入500或以下的数字");
                    } else {
                        top.layer.closeAll();
                        $("#codeNum").val(pass);
                        var index = top.layer.load(1, {
                            shade: [0.1,'#fff'] //0.1透明度的白色背景
                        });

                        // 批量生成激活码
                        $.post('${ctx}/yaya/code/batch/create', $("#importForm").serialize(), function (data) {
                            top.layer.close(index);
                            if(data.code == 0){
                                top.layer.msg('操作成功,请在左侧导航栏【激活码管理】查看生成的激活码', {time:3000}, function () {
                                    window.location.reload();
                                });
                            } else {
                                top.layer.msg(data.err);
                            }
                        }, 'json');
                    }


                });
            }
        });
    });
</script>
</body>
</html>

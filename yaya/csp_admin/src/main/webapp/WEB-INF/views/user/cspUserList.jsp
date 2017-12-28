<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html >
<head>
    <title>CSP用户管理</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/csp/user/list?listType=0">国内用户列表</a></li>
    <li><a href="${ctx}/csp/user/list?listType=1">海外用户列表</a></li>
    <li><a href="${ctx}/csp/user/list?listType=2">冻结用户列表</a></li>
</ul>
<%@include file="/WEB-INF/include/message.jsp"%>
<form id="pageForm" name="pageForm" action="${ctx}/csp/user/list" method="post">
    <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
    <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input  name="keyWord" type="hidden" value="${keyWord}"/>
    <input  name="listType" type="hidden" value="${listType}"/>
</form>
<form id="searchForm" method="post" action="${ctx}/csp/user/list" class="breadcrumb form-search">
    <input placeholder="昵称/用户名/电话" value="${keyWord}" size="40"  type="search" name="keyWord" maxlength="50" class="required"/>
    <input  name="listType" type="hidden" value="${listType}"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr><th>ID</th><th>昵称</th><th>注册日期</th><th>套餐等级</th><th>套餐日期</th><th>付费次数</th>
        <th>付费总额(CNY)</th><th>付费总额(USD)</th><th>会议数</th><th>备注</th><th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="user">
            <tr>
                <td>${user.uid}</td>
                <td>${user.nickName}</td>
                <td><fmt:formatDate value="${user.registerTime}" pattern="yyyyMMdd"/></td>
                <td>${user.packageId eq 1 ? "标准版": user.packageId eq 2 ? "高级版": user.packageId eq 3 ? "专业版":"<span style='color: #9c0001'>未登录</span>"}</td>
                <td>
                    <c:choose>
                        <c:when test="${user.unlimited eq true}">
                            无期限
                        </c:when>
                        <c:otherwise>
                            <c:if test="${not empty user.packageStart}">
                                <fmt:formatDate value="${user.packageStart}" pattern="yyyyMMdd"/>至<fmt:formatDate value="${user.packageEnd}" pattern="yyyyMMdd"/>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${not empty user.payTimes ? user.payTimes : 0}</td>
                <td>${not empty user.payMoneyCn ? user.payMoneyCn : 0}</td>
                <td>${not empty user.payMoneyUs ? user.payMoneyUs : 0}</td>
                <td>${user.meets}</td>
                <td><input type="text" style="width: 70px;height: auto;" maxlength="10" value="${user.remark}"> &nbsp;<a href="#" onclick="remark(this,'${user.uid}')">备注</a></td>
                <th>
                    <div class="btn-group">
                        <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                            操作
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <shiro:hasPermission name="csp:user:edit">
                                <li><a href="#" onclick="changePackages(1,'${user.uid}','${user.packageId}',dateToStrings('${user.packageEnd}'),'${user.unlimited}')">升级</a></li>
                                <li><a href="#" onclick="changePackages(2,'${user.uid}','${user.packageId}',dateToStrings('${user.packageEnd}'),'${user.unlimited}')">降级</a></li>
                                <li><a href="#" onclick="changePackages(3,'${user.uid}','${user.packageId}',dateToStrings('${user.packageEnd}'),'${user.unlimited}')">时间修改</a></li>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="csp:user:active">
                                <li><a href="#" onclick="active(4,'${user.uid}')">账号冻结</a></li>
                            </shiro:hasPermission>
                        </ul>
                    </div>
                </th>
                <%--<td>--%>
                    <%--<shiro:hasPermission name="csp:user:edit">--%>
                        <%--<a href="${ctx}/csp/user/viewOrRegister?id=${user.id}&actionType=3&listType=${listType}">修改</a>--%>
                        <%--<c:if test="${user.active eq true}">--%>
                            <%--<a data-href="${ctx}/csp/user/update?id=${user.id}&actionType=1&listType=${listType}"  onclick="layerConfirm('确认要冻结该用户帐号吗？', this)">冻结</a>--%>
                        <%--</c:if>--%>
                    <%--</shiro:hasPermission>--%>
                    <%--<shiro:hasPermission name="csp:user:del">--%>
                         <%--<a data-href="${ctx}/csp/user/update?id=${user.id}&actionType=2&listType=${listType}"  onclick="layerConfirm('确认要删除该用户帐号吗？', this)">删除</a>--%>
                    <%--</shiro:hasPermission>--%>
                <%--</td>--%>
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
<%@include file="/WEB-INF/include/pageable.jsp"%>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" style="text-align: center">升级</h4>
            </div>
            <form class="form-horizontal form-bordered form-row-strippe" id="modalForm" action="${ctx}/csp/user/package" method="post">
                <div class="modal-body">
                    <div id="packageChange">
                        <div class="control-group">
                            <label class="control-label packageLable">升级为:</label>
                            <div class="controls">
                                <select id="packageId" name="packageId" style="width: 130px;">
                                    <option value="">未登录</option>
                                    <option value="1">标准版</option>
                                    <option value="2">高级版</option>
                                    <option value="3">专业版</option>
                                </select>
                            </div>
                        </div>
                        <div>
                            <label class="control-label timeLable">有效期至:</label>
                            <div class="controls">
                               <span class="time-tj">
                                    <label for="times" id="updateTimes">
                                        <input type="text" disabled="" class="timedate-input " placeholder="" id="times" name="times">
                                    </label>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div id="dongjie">
                        <label class="control-label">冻结原因:</label>
                        <div class="controls">
                            <input type="search" placeholder="冻结原因" id="frozenReason" name="frozenReason">
                        </div>
                    </div>
                </div>
                <div class="modal-footer bg-info">
                    <input type="hidden" name="userId" id="userId" value=""/>
                    <input type="hidden" name="packageEnd" id="packageEnd" value=""/>
                    <input type="hidden" name="actionType" id="actionType" value=""/>
                    <input type="hidden" name="oldId" id="oldId" value=""/>
                    <input type="hidden" name="unlimited" id="unlimited" value=""/>
                    <input type="hidden" name="listType"  value="${listType}"/>
                    <button type="button" class="btn green" data-dismiss="modal">返回</button>
                    <button type="button" class="btn btn-primary" onclick="submitBtn()">提交</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>

    $(function () {
        var active = '${listType}';
        $(".nav-tabs li:eq(" + active +")").addClass("active");
        initDateRangePicker("packageEnd");
    })

    //初始化冻结表单
    function active(actionType,userId){
        $("#packageChange").hide();
        $("#dongjie").show();
        $("#actionType").val(actionType);
        $("#packageEnd").val(dateToStrings(""));
        $("#userId").val(userId);
        $(".modal-title").html("冻结");
        $("#myModal").modal("show");
    }

    //初始化升级降级修改时间列表
    function changePackages(actionType,userId,packageId,packageEnd,unlimited){
        $("#packageChange").show();
        $("#dongjie").hide();
        if(packageId != undefined){
            $("#packageId").select2().val(packageId).trigger("change");
        }
        $("#packageEnd").val(packageEnd);
        $("#times").val(packageEnd);
        $("#userId").val(userId);
        $("#actionType").val(actionType);
        $("#oldId").val(packageId);
        $("#unlimited").val(unlimited);
        if(actionType == 1){ //升级
            $(".modal-title").html("升级");
            $(".packageLable").html("升级为：");
            $(".timeLable").html("有效期至：");
        }else if(actionType == 2){
            $(".modal-title").html("降级");
            $(".packageLable").html("降级为：");
            $(".timeLable").html("有效期至：");
        }else{
            if(isEmpty(packageId) || packageId == 1){
                layer.msg("该版本不能修改时间");
                return false;
            }
            $("#packageId").prop("disabled", true);
            $(".modal-title").html("修改时间");
            $(".packageLable").html("当前为：");
            $(".timeLable").html("修改时间至：");
        }
        $("#myModal").modal("show");
    }

    function submitBtn() {
        top.layer.confirm("确认提交吗", function(){
            top.layer.closeAll('dialog');
            var actionType = $("#actionType").val();
            var oldId = $("#oldId").val();
            var packageId = $("#packageId").val();
            var unlimited = $("#unlimited").val();
            if(actionType == 1 || actionType == 2) {
                if(actionType == 1){  // 升级
                    if (packageId == "" || packageId <= oldId) {
                        layer.msg("请选择比当前高的套餐后进行升级");
                        return false;
                    }
                }else{
                    if(packageId >= oldId){
                        layer.msg("请选择比当前低的套餐后进行降级");
                        return false;
                    }
                    if(packageId == ""){
                        layer.msg("降级最低版本为标准版");
                        return false;
                    }
                }
                if(packageId != 1){
                    if(isEmpty($("#packageEnd").val())){
                        layer.msg("有效期时间不能为空");
                        return false;
                    }
                }
            }
            //數字平臺不能操作
            if (oldId == 3 && unlimited == "true"){
                layer.msg("不能对敬信数字平台用户进行操作");
                return false;
            }
            $("#packageEnd").val($("#packageEnd").val() + " 23:59:59");
            $("#packageId").prop("disabled", false);
           $("#modalForm").submit();
        });
    }

    //初始化时间时间空间
    function initDateRangePicker(id){
        $('#updateTimes').dateRangePicker({
            singleMonth: true,
            showShortcuts: false,
            showTopbar: false,
            format: 'YYYY-MM-DD',
            autoClose: false,
            singleDate:true,
            startDate: nextDay(),
            time: {
                enabled: true
            }
        }).bind('datepicker-change',function(event,obj){
            //console.log(obj.value)
            $(this).find("input").val(obj.value);
            $("#" + id).val(obj.value);
        });
    }

    //修改备注
    function remark(obj,userId){
        var remark = $(obj).parent().find("input").val();
        $.ajax({
            url:'${ctx}/csp/user/remark',
            data:{"id":userId,"remark":remark},
            dataType:"json",
            type:"post",
            success:function (data) {
                if(data.code == "0"){
                    layer.msg("更新成功");
                }else{
                    layer.msg("修改备注失败，请重试");
                }
            }
        });
    }
</script>
</body>
</html>

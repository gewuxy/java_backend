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
    <input placeholder="昵称/电话/邮箱" value="${keyWord}" size="40"  type="search" name="keyWord" maxlength="50" class="required"/>
    <input  name="listType" type="hidden" value="${listType}"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr><th>ID</th><th>昵称</th><th>注册日期</th><th>电话</th><th>邮箱</th><th>套餐等级</th><th>套餐日期</th><th>付费次数</th>
        <th>总额 ￥</th><th>总额 $</th><th>会议数</th><th>备注</th><th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${not empty page.dataList}">
        <c:forEach items="${page.dataList}" var="user">
            <tr>
                <td>${user.uid}</td>
                <td>
                    <c:if test="${fn:length(user.nickName)>8 }">
                        ${fn:substring(user.nickName, 0, 8)}...
                    </c:if>
                    <c:if test="${fn:length(user.nickName)<=8 }">
                        ${user.nickName}
                    </c:if>
                </td>
                <td><fmt:formatDate value="${user.registerTime}" pattern="yyyyMMdd"/></td>
                <td>${user.mobile}</td>
                <td>${user.email}</td>
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
                            <shiro:hasPermission name="csp:user:frozen">
                                <li><a href="#" onclick="active(4,'${user.uid}')">账号冻结</a></li>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="csp:user:edit">
                                <li><a href="#" onclick="view('${user.uid}','${user.abroad}','${user.mobile}','${user.email}')">第三方登录</a></li>
                            </shiro:hasPermission>
                        </ul>
                    </div>
                </th>
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
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display:none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" style="text-align: center" id="title">升级</h4>
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
<!-- 第三方绑定信息窗口-->
<div class="modal fade" id="viewModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display:none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="bindTitle">第三方登录</h4>
            </div>
            <form class="form-horizontal form-bordered form-row-strippe" action="">
                <div class="modal-body">
                    <div>
                        <img src="${ctxStatic}/images/icon-user-phone.png" alt="" style="width: 30px;">
                        <span class="bindInfo" style="margin-left: 3%;position: relative;top:2px;" id="mobile"></span>
                        <span style="float:right;position: relative;top:3px;color: grey">未绑定</span>
                    </div>
                    <div style="margin-top: 2%;">
                        <img src="${ctxStatic}/images/icon-user-wechat.png" alt="" style="width: 30px;">
                        <span class="bindInfo" style="margin-left: 3%;position: relative;top:2px;" id="wechat"></span>
                        <span style="float:right;position: relative;top:3px;color: grey">未绑定</span>
                    </div>
                    <div style="margin-top: 2%;">
                        <img src="${ctxStatic}/images/icon-user-weibo.png" alt="" style="width: 30px;">
                        <span class="bindInfo" style="margin-left: 3%;position: relative;top:2px;" id="weibo"></span>
                        <span style="float:right;position: relative;top:3px;color: grey">未绑定</span>
                    </div>
                    <div style="margin-top: 2%;">
                        <img src="${ctxStatic}/images/icon-user-facebook.png" alt="" style="width: 30px;">
                        <span class="bindInfo" style="margin-left: 3%;position: relative;top:2px;" id="facebook"></span>
                        <span style="float:right;position: relative;top:3px;color: grey">未绑定</span>
                    </div>
                    <div style="margin-top: 2%;">
                        <img src="${ctxStatic}/images/icon-user-twitter.png" alt="" style="width: 30px;">
                        <span class="bindInfo" style="margin-left: 3%;position: relative;top:2px;" id="twitter"></span>
                        <span style="float:right;position: relative;top:3px;color: grey">未绑定</span>
                    </div>
                    <div style="margin-top: 2%;">
                        <img src="${ctxStatic}/images/icon-user-email.png" alt="" style="width: 30px;">
                        <span class="bindInfo" style="margin-left: 3%;position: relative;top:2px;" id="email"></span>
                        <span style="float:right;position: relative;top:3px;color: grey">未绑定</span>
                    </div>
                    <div style="margin-top: 2%;">
                        <img src="${ctxStatic}/images/icon-user-medcn.png" alt="" style="width: 30px;">
                        <span class="bindInfo" style="margin-left: 3%;position: relative;top:2px;" id="medcn"></span>
                        <span  style="float:right;position: relative;top:3px;color: grey">未绑定</span>
                    </div>
                </div>
                <div class="modal-footer bg-info">
                    <button type="button" class="btn green" data-dismiss="modal">返回</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    $(function () {
        var active = '${listType}';
        $(".nav-tabs li:eq(" + active + ")").addClass("active");
        initDateRangePicker("packageEnd");
    })

    //初始化冻结表单
    function active(actionType, userId) {
        $("#packageChange").hide();
        $("#dongjie").show();
        $("#actionType").val(actionType);
        $("#packageEnd").val(dateToStrings(""));
        $("#userId").val(userId);
        $("#title").html("冻结");
        $("#myModal").modal("show");
    }

    //初始化升级降级修改时间列表
    function changePackages(actionType, userId, packageId, packageEnd, unlimited) {
        $("#packageChange").show();
        $("#dongjie").hide();
        if (packageId != undefined) {
            $("#packageId").select2().val(packageId).trigger("change");
        }
        $("#packageEnd").val(packageEnd);
        $("#times").val(packageEnd);
        $("#userId").val(userId);
        $("#actionType").val(actionType);
        $("#oldId").val(packageId);
        $("#unlimited").val(unlimited);
        if (actionType == 1) { //升级
            $("#title").html("升级");
            $(".packageLable").html("升级为：");
            $(".timeLable").html("有效期至：");
        } else if (actionType == 2) {
            $("#title").html("降级");
            $(".packageLable").html("降级为：");
            $(".timeLable").html("有效期至：");
        } else {
            if (isEmpty(packageId) || packageId == 1) {
                layer.msg("该版本不能修改时间");
                return false;
            }
            $("#packageId").prop("disabled", true);
            $("#title").html("修改时间");
            $(".packageLable").html("当前为：");
            $(".timeLable").html("修改时间至：");
        }
        $("#myModal").modal("show");
    }

    function submitBtn() {
        top.layer.confirm("确认提交吗", function () {
            top.layer.closeAll('dialog');
            var actionType = $("#actionType").val();
            var oldId = $("#oldId").val();
            var packageId = $("#packageId").val();
            var unlimited = $("#unlimited").val();
            if (actionType == 1 || actionType == 2) {
                if (actionType == 1) {  // 升级
                    if (packageId == "" || packageId <= oldId) {
                        layer.msg("请选择比当前高的套餐后进行升级");
                        return false;
                    }
                } else {
                    if (packageId >= oldId) {
                        layer.msg("请选择比当前低的套餐后进行降级");
                        return false;
                    }
                    if (packageId == "") {
                        layer.msg("降级最低版本为标准版");
                        return false;
                    }
                }
                if (packageId != 1) {
                    if (isEmpty($("#packageEnd").val())) {
                        layer.msg("有效期时间不能为空");
                        return false;
                    }
                }
            }
            //數字平臺不能操作
            if (oldId == 3 && unlimited == "true") {
                layer.msg("不能对敬信数字平台用户进行操作");
                return false;
            }
            $("#packageEnd").val($("#packageEnd").val() + " 23:59:59");
            $("#packageId").prop("disabled", false);
            $("#modalForm").submit();
        });
    }

    //初始化时间时间空间
    function initDateRangePicker(id) {
        $('#updateTimes').dateRangePicker({
            singleMonth: true,
            showShortcuts: false,
            showTopbar: false,
            format: 'YYYY-MM-DD',
            autoClose: false,
            singleDate: true,
            startDate: nextDay(),
            time: {
                enabled: true
            }
        }).bind('datepicker-change', function (event, obj) {
            $(this).find("input").val(obj.value);
            $("#" + id).val(obj.value);
        });
    }

    //修改备注
    function remark(obj, userId) {
        var remark = $(obj).parent().find("input").val();
        $.ajax({
            url: '${ctx}/csp/user/remark',
            data: {"id": userId, "remark": remark},
            dataType: "json",
            type: "post",
            success: function (data) {
                if (data.code == "0") {
                    layer.msg("更新成功");
                } else {
                    layer.msg("修改备注失败，请重试");
                }
            }
        });
    }

    //查看第三方登录
    function view(userId, abroad, mobile, email) {
        delBindStyle();
        if (abroad == "false") {
            $("#bindTitle").html("第三方登录(国内版)");
        } else {
            $("#bindTitle").html("第三方登录(海外版)");
        }
        if (!isEmpty(mobile)) {
            $("#mobile").text(mobile);
            addBindStyle("mobile");
        }
        if (!isEmpty(email)) {
            $("#email").text(email);
            addBindStyle("email");
        }
        $.ajax({
            url: '${ctx}/csp/user/bind/view',
            data: {"userId": userId},
            dataType: "json",
            type: "post",
            success: function (data) {
                if (data.code == "0") {
                    initView(data.data);
                } else {
                    layer.msg("获取用户绑定信息失败");
                }
            }
        });
        $("#viewModal").modal("show");
    }

    //加载绑定信息
    function initView(data) {
        for (var i = 0; i < data.length; i++) {
            var nickName = data[i].nickName;
            switch (data[i].thirdPartyId) {
                case 1:
                    addBindInfo("wechat", nickName);
                    break;
                case 2:
                    addBindInfo("weibo", nickName);
                    break;
                case 3:
                    addBindInfo("facebook", nickName);
                    break;
                case 4:
                    addBindInfo("twitter", nickName);
                    break;
                case 5:
                    addBindInfo("medcn", nickName);
                    break;
            }
        }
    }

    function addBindInfo(name, nickName) {
        $("#" + name).text(nickName);
        addBindStyle(name);
    }

    //添加样式
    function addBindStyle(name) {
        $("#" + name).next().text("绑定").css("color", "#167afe");
    }

    //去除样式
    function delBindStyle() {
        $(".bindInfo").text("");
        $(".bindInfo").next().text("未绑定").css("color", "grey");
    }
</script>
</body>
</html>

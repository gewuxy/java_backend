<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/8/23
  Time: 16:34
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>管理-医生管理</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <meta name="Keywords" content="">
    <meta name="description" content="">
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">

</head>
<body>
<!-- main -->
<div class="g-main max-height select-table clearfix">

    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-08.png" alt="">
                </div>
                <div class="oh">
                    <p><strong>测试账号管理</strong></p>
                    <p>用于测试组管理测试账号</p>
                </div>
            </div>
        </div>
    </header>

    <div class="tab-hd">
        <ul class="tab-list clearfix">
            <li class="cur">
                <a >测试账号管理<i></i></a>
            </li>
        </ul>
    </div>

    <div class="tab-bd clearfix">

    <div class="select-table-box" style="margin-left:0px">
        <div class="tb-contacts-head">
            <table class=" tb-contacts-list" cellspacing="0">
                <thead>
                <tr>
                    <th class="col1">&nbsp;</th>
                    <th class="col1"></th>
                    <th class="col5" colspan="7" style="display:none; padding-left: 10px;"></th>
                    <th class="col5" colspan="7"></th>
                    <th>
                        <div class="tb-search">
                            <span class="search-box ">
                                <form id="searchForm" action="${ctx}/mng/account/test/list" method="post">
                                    <label for="keyword" class="search-text-hook">
                                        <input name="keyword" id="keyword" type="text" class="sear-txt"
                                               placeholder="姓名/邮箱/手机号" value=""/>
                                        <input type="reset" class="search-empty none" value="X"/>
                                    </label>
                                    <input class="sear-button" type="button" onclick="searchSubmit()"/>
                                </form>
                            </span>
                        </div>
                    </th>
                </tr>
                </thead>
            </table>
        </div>


        <table cellspacing="0" class="tb-contacts-list">
            <thead>
            <th align="left" style="padding-left: 5px;">姓名</th>
            <th align="left">邮箱</th>
            <th align="left">手机号</th>
            <th align="left">医院</th>
            <th align="left">操作</th>
            </thead>

            <tbody>
            <c:forEach items="${page.dataList}" var="p">
                <tr doctorId="${p.id}">

                    <td  style="padding-left: 5px;">
                            ${p.linkman}
                    </td>
                    <td>
                            ${p.username}
                    </td>
                    <td><span class="">${p.mobile} </span></td>
                    <td>
                            ${p.hospital}
                    </td>
                    <td class="tb-edit">
                        <span><a style="cursor: pointer;" class="color-blue fx-btn-5" doctorId="${p.id}">删除</a></span>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>
        <%@include file="/WEB-INF/include/pageable.jsp" %>
        <form id="pageForm" name="pageForm" method="get" action="${ctx}/mng/account/test/list">
            <input type="hidden" name="groupId" value="${groupId}"/>
            <input type="hidden" name="pageNum" id="pageNum" value="${page.pageNum}"/>
            <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
            <input type="hidden" name="keyword" value="${keyword}">
        </form>

    </div>
    </div>
</div>
<script>
    $(function(){

        $(".fx-btn-5").click(function(){
            var doctorId = $(this).attr("doctorId");
            top.layer.confirm("此操作不可逆,是否确定要删除？",function(){
                top.layer.closeAll();
                $.post('${ctx}/mng/account/test/del',{'userId':doctorId}, function(){
                    layer.msg("删除成功");
                    $("tr[doctorId='"+doctorId+"']").remove();
                },'json');
            });
        });


    });

    function searchSubmit() {
        $("#searchForm").submit();
    }
</script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>管理-医生管理</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
	<meta name="Keywords" content="">
	<meta name="description" content="">
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">

</head>
<body >
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
                    <p><strong>医生管理</strong></p>
                    <p>关注医生分组管理，群发通知便捷有效</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->


    <div class="tab-hd">
        <ul class="tab-list clearfix">
            <li class="cur">
                <a href="${ctx}/mng/doc/list">医生管理<i></i></a>
            </li>
            <li >
                <a href="${ctx}/mng/doc/import">导入医生<i></i></a>
            </li>
        </ul>
    </div>

    <div class="tab-bd clearfix">

        <div class="groupBox fl clearfix">
            <ul id="groupUL">
                <li <c:if test="${groupId == null }">class="cur"</c:if> groupId="" >
                    <span class="fr">${count}</span>
                    <span class="fl">
                    <strong><a href="javascript:" class="title">全部医生</a></strong>
                    </span>
                    <i></i>
                </li>

                <li groupId="0" <c:if test="${groupId == 0}">class="cur"</c:if>>
                    <span class="fr">${num}</span>
                    <span class="fl">
                        <strong><a href="javascript:" class="title">未分组</a></strong>
                    </span>
                    <i></i>
                </li>
                <li groupId="1" <c:if test="${groupId == 1}">class="cur"</c:if>>
                    <span class="fr">${bindWxCount}</span>
                    <span class="fl">
                        <strong><a href="javascript:" class="title">已绑定微信</a></strong>
                    </span>
                    <i></i>
                </li>
                <c:forEach items="${groupList}" var="g">
                    <li groupId="${g.id}" <c:if test="${g.id == groupId}">class="cur"</c:if>>
                        <span class="fr">${g.memberCount}</span>
                        <span class="fl">
                        <strong><a href="javascript:" class="title">${g.groupName}</a></strong>
                        <p><a  class="fx-btn-4" groupId="${g.id}" href="#"><img src="${ctxStatic}/images/u6154.png" alt="">修改</a></p>
                    </span>
                        <i></i>
                    </li>
                </c:forEach>
            </ul>
            <p class="t-center margin-t-b"><a href="#" class="add-icon fx-btn-1">添加分组</a></p>
        </div>


        <div class="select-table-box">
            <div class="tb-contacts-head">
                <div class="tb-search">
                    <span class="search-box ">
                        <form id="searchForm" action="${ctx}/mng/doc/list" method="post">
                            <label for="search-text" class="search-text-hook">
                                <input name="searchName" id="search-text" type="text" class="sear-txt" placeholder="请搜索医师姓名" value="" />
                                <input type="reset" class="search-empty none" value="X" />
                            </label>
                            <input class="sear-button" type="button" onclick="searchSubmit()" />
                        </form>
                    </span>
                </div>

                <table class=" tb-contacts-list" cellspacing="0">
                    <thead>
                    <tr>
                        <th class="col1">&nbsp;</th>
                        <th class="col1"><i class="tb-icon icon-check select-all"></i></th>
                        <th class="col5" colspan="7" style="display:none; padding-left: 10px;"><span class="margin-r"><a href="#" class="button-middle button-green fx-btn-3">移至分组</a></span></th>
                        <th class="col5" colspan="7">全选</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <c:choose>
            <c:when test="${page.dataList!=null && page.dataList.size()!=0}">
            <div class="tb-contacts-main">
                <table cellspacing="0" class="tb-contacts-list">
                    <colgroup>
                        <col class="col1">
                        <col class="col1">
                        <col class="col4">
                        <col class="col7">
                        <col class="col3">
                        <col class="col5">
                        <col class="col8">
                    </colgroup>

                    <tbody class="ui-selectable">
                    <c:forEach items="${page.dataList}" var="p">
                        <tr doctorId="${p.id}">
                            <td><i class="tb-icon icon-drag"></i></td>
                            <td><i class="tb-icon icon-check"></i></td>
                            <td class="t-center ">
                                <div class="tb-popupBox-hook">
                                    <input type="hidden" id="popup" value="${p.id}">
                                    <img class="img-radius" <c:choose><c:when test="${empty p.headimg}">src="${ctxStatic}/img/hz-detail-img-info.jpg"</c:when><c:otherwise>src="${p.headimg}"</c:otherwise></c:choose> width="32" height="32" alt="">
                                    <!--S 弹出详情-->
                                    <div class="tb-popupBox">
                                        <div class="tb-popupBox-bg">
                                            <div class="tb-popupBox-border"></div>
                                            <div class="tb-popupBox-outerBorder"></div>
                                            <div class="tb-popupBox-item clearfix">
                                                <div class="fl clearfix">
                                                    <img  class="img-radius" <c:choose><c:when test="${empty p.headimg}">src="${ctxStatic}/img/hz-detail-img-info.jpg"</c:when><c:otherwise>src="${p.headimg}"</c:otherwise></c:choose> width="32" height="32" alt="">
                                                </div>
                                                <div class="oh clearfix">
                                                    <h3>${p.linkman}</h3>
                                                    <p><span class="color-gray">学习时长：<span class="color-blue" id="learnTime${p.id}">${p.time}</span></span><i class="rowSpace">|</i><span class="color-gray">已参加：<span class="color-blue" id="attend${p.id}"></span></span></p>
                                                </div>
                                            </div>
                                            <div class="tb-popupBox-info clearfix">
                                                <ul>
                                                    <li><span>科室：</span>${p.type}</li>
                                                    <li><span>医院：</span>${p.hospital}</li>
                                                    <li id="address${p.id}"><span>地址：</span></li>
                                                </ul>
                                                <ul>
                                                    <li id="mobile${p.id}"><span >手机：</span></li>
                                                    <li id="mail${p.id}"><span>邮箱：</span></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <!--E 弹出详情-->
                                </div>
                            </td>
                            <td>
                                <p>
                                    <a href="${ctx}/mng/doc/info?doctorId=${p.id}" class="color-black">${p.linkman}<c:if test="${p.title != null && p.title != ''}">(${p.title})</c:if></a><br />
                                    <span>${p.hospital}</span>
                                </p>
                            </td>
                            <td ><span class="">${p.type} </span></td>
                            <td >
                                <span class="margin-r fx-btn-3" groupId="${p.groupId}" doctorId="${p.id}">
                                    ${p.unionid == null || p.unionid == "" ? "" : "已绑定微信"}
                                    <a href="#">${p.groupName == null?'未分组':p.groupName}</a>
                                </span>
                            </td>
                            <td class="tb-edit">
                                <span><a href="${ctx}/mng/doc/info?doctorId=${p.id}" class="color-blue">查看详情</a></span>&nbsp;&nbsp;&nbsp;
                                <span ><a href="#" class="color-blue fx-btn-3" groupId="${p.groupId}" doctorId="${p.id}">分组</a></span>&nbsp;&nbsp;&nbsp;
                                <span ><a href="#" class="color-blue fx-btn-5" groupId="${p.groupId}" doctorId="${p.id}">删除</a></span>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>
                <%@include file="/WEB-INF/include/pageable.jsp"%>
                <form id="pageForm" name="pageForm" method="get" action="${ctx}/mng/doc/list">
                    <input type="hidden" name="groupId" value="${groupId}"/>
                    <input type="hidden" name="pageNum" id="pageNum" value="${page.pageNum}" />
                    <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
                </form>
            </div>
            </c:when>
            <c:otherwise>
            <div class="normalBoxItem">
                    <p><img src="${ctxStatic}/images/not-search.png" alt=""></p>
                    <p style="color:#acacac; font-size:16px;">搜索不到结果</p>
                </div>
            </c:otherwise>
            </c:choose>
        </div>
    </div>

</div>


<div class="mask-wrap">
    <div class="dis-tbcell">
        <div class="distb-box distb-box-min fx-mask-box-1" id="newgroupform">
            <div class="mask-hd clearfix">
                <h3 class="font-size-1">新建分组</h3>
                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
            </div>
            <div class="fx-mask-box clearfix t-center" >
                <span class="fSize-max td-title">分组名称</span><span><input type="text" id="groupName" name="groupName" class="text-input" placeholder="18个中文字符以内（必填）"></span>
            </div>
            <div class="sb-btn-box p-btm-1">
                <button class="close-button-fx cur"  id="newgroupbtn">确认</button>
                <button class="close-button-fx">取消</button>
            </div>
        </div>
        <div class="distb-box distb-box-min fx-mask-box-3 ">
            <div class="mask-hd clearfix">
                <h3 class="font-size-1">设置分组</h3>
                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
            </div>
            <div class="clearfix hidden-box">
                <form action="" id="setgroupFrom" name="setgroupFrom">
                    <input type="hidden" name="doctorId" id="doctorId" value="">
                    <div id="doctorIdsDiv"></div>
                    <ul class="fx-li-box" id="setgroupul">
                        <c:forEach items="${groupList}" var="g">
                            <li><label><input  name="groupId" type="radio" value="${g.id}" class="tb-icon" />${g.groupName}</label></li>
                        </c:forEach>
                    </ul>
                </form>

            </div>

            <div class="sb-btn-box  p-btm-1">
                <button class="close-button-fx cur" id="setgroupbtn">确认</button>
                <button class="close-button-fx">取消</button>
            </div>
        </div>
        <div class="distb-box distb-box-min fx-mask-box-4" id="updategroupform">
            <div class="mask-hd clearfix">
                <h3 class="font-size-1">设置分组</h3>
                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
            </div>
            <div class="fx-mask-box clearfix t-center" >
                <span class="fSize-max td-title">分组名称</span><span><input type="text" class="text-input" id="modifyGroupName" name="modifyGroupName" placeholder="" maxlength="18" value="糖尿病人"></span>
            </div>
            <div class="sb-btn-box p-btm-1 t-right">
                <button class="close-button-fx" id="deleteGroupbtn">删除分组</button>
                <button class="close-button-fx cur" id="modifygroupbtn">确认</button>
            </div>
        </div>
    </div>
</div>
<script src="${ctxStatic}/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
<script src="${ctxStatic}/js/main.js"></script>
<script>
    function searchSubmit() {
        $("#searchForm").submit();
    }
</script>
<script>
    var currentGroupId = 0;
    $(function(){
        if("${msg}"){
            layer.msg("${msg}");
        }

        $("#newgroupbtn").click(function(){
            var groupName = $("#newgroupform").find("#groupName").val();
            if($.trim(groupName).length ==0){
                layer.msg("请输入分组名称");
            }else{
                $.post('${ctx}/mng/doc/saveGroup',{'groupName':groupName},function(data){
                    window.location.href = '${ctx}/mng/doc/list?groupId=${groupId}';
                },'json');
            }
        });

        $(".fx-btn-3").click(function(){
            var groupId = $(this).attr("groupId");
            var doctorId = $(this).attr("doctorId");
            <!-- 批量移动分组时设置doctorIds-->
            if(doctorId == undefined){
                $("#doctorIdsDiv").html("");
                $(".ui-selectable tr").each(function(){
                    if($(this).hasClass("selectTr")){
                        $("#doctorIdsDiv").append('<input type="hidden" name="doctorId" value="'+$(this).attr("doctorId")+'"/>');
                    }
                });
                <!-- 单个移动分组-->
            }else{
                $("#doctorId").val(doctorId);
                $("#setgroupul li label input").removeAttr("checked");
                $("#setgroupul li label input[value="+groupId+"]").prop("checked","true");
            }
        });

        $(".fx-btn-5").click(function(){
            var doctorId = $(this).attr("doctorId");
            top.layer.confirm("确定要删除此用户吗？",function(){
                top.layer.closeAll();
                $.post('${ctx}/mng/doc/cancelAttention',{'doctorId':doctorId}, function(){
                    layer.msg("删除成功",{time:400},function () {
                        window.location.href="${ctx}/mng/doc/list?groupId=${groupId}";
                    });
                },'json');
            });

        });


        $("#setgroupbtn").click(function(){
            $.post('${ctx}/mng/doc/allot',$("#setgroupFrom").serialize(),function(){
                window.location.href = '${ctx}/mng/doc/list?groupId=${groupId}';
            },'json');
        });

        $("#groupUL li").click(function(){
            var groupId= $(this).attr("groupId");
            if(groupId!=''){
                window.location.href = '${ctx}/mng/doc/list?groupId='+groupId;
            }else{
                window.location.href = '${ctx}/mng/doc/list';
            }
        });

        $(".fx-btn-4").click(function(event){
            event.stopPropagation();
            var groupId = $(this).attr("groupId");
            $.get('${ctx}/mng/doc/editGroup',{'groupId':groupId},function(data){
                $("#modifyGroupName").val(data.data.groupName);
                currentGroupId = data.data.id;
            },'json');
        });

        $("#modifygroupbtn").click(function(){
            var groupName = $("#modifyGroupName").val();
            if ($.trim(groupName) == ''){
                layer.msg("请输入分组名称");
            }else{
                $.post('${ctx}/mng/doc/saveGroup',{'groupName':groupName,'id':currentGroupId},function(data){
                    window.location.href = '${ctx}/mng/doc/list?groupId=${groupId}';
                },'json');
            }
        });

        $("#deleteGroupbtn").click(function(){
            top.layer.confirm("确定要删除此分组吗？",function(){
                top.layer.closeAll();
                $.post('${ctx}/mng/doc/deleteGroup',{'groupId':currentGroupId}, function(){
                    window.location.href = '${ctx}/mng/doc/list?groupId=${groupId}';
                },'json');
            });
        });

        var selectTableAll = $(".tb-contacts-head").find(".select-all");
        var selectTable = $(".select-table");
        var selectList = selectTable.find(".ui-selectable").find('.tb-icon');
        var selectTrNum = selectList.parents("tr").length;
        var selectHead = $(".tb-contacts-head").find("th");




        /*隔行换色*/
        $(".ui-selectable tr:odd").addClass("td-bg");


        //全选按钮选中
        selectTableAll.on("click",function(){
            $(this).toggleClass("select");
            selectTableAll.removeClass("select-notAll");
            var $tr = $(".select-table").find(".ui-selectable tr");
            if( $tr.hasClass("selectTr") && selectTrNum != $(".selectTr").length){
                $tr.addClass("selectTr");
                $tr.find(".icon-drag").addClass("icon-drag-check");
                $tr.find(".icon-check").addClass("select");
            } else {
                $tr.toggleClass("selectTr");
                $tr.find(".icon-drag").toggleClass("icon-drag-check");
                $tr.find(".icon-check").toggleClass("select");
            }
            //选中显示修改分组
            if($(this).hasClass("select")){
                selectHead.filter(":eq(2)").show();
                selectHead.filter(":gt(2)").hide();
            } else {
                selectHead.filter(":eq(2)").hide();
                selectHead.filter(":gt(2)").show();
            }
        });
        //点击选中状态
        selectList.on("click",function(){
            $(this).closest("tr").toggleClass("selectTr");
            $(this).closest("tr").find(".icon-drag").toggleClass("icon-drag-check");
            $(this).closest("tr").find(".icon-check").toggleClass("select");

            if($(".selectTr").length == selectTrNum){
                selectTableAll.addClass("select");
            } else {
                selectTableAll.removeClass("select");
            }

            if($(".selectTr").length > 0){
                selectTableAll.addClass("select-notAll");
                selectHead.filter(":eq(2)").show();
                selectHead.filter(":gt(2)").hide();
            } else {
                selectTableAll.removeClass("select-notAll");
                selectHead.filter(":eq(2)").hide();
                selectHead.filter(":gt(2)").show();
            }
        });

        //拖动触发
        selectList.draggable({
            appendTo:"body",
            zIndex: 20,
            revertDuration: 5,
            helper: function(event, ui) {
               // contactid = $(this).attr("doctorId");
                var contactid = [];
                if( $(this).closest("tr").hasClass("selectTr") == false ){
                    $(this).closest("tr").toggleClass("selectTr");
                    $(this).closest("tr").find(".icon-drag").toggleClass("icon-drag-check");
                    $(this).closest("tr").find(".icon-check").toggleClass("select");
                }
                if( $(this).closest("tr").find(".icon-check").hasClass("select-all") ){
                    alert("选中");
                }
                if( $(this).parents("tr").find(".icon-check").hasClass("select") ){
                    $(".ui-selectable .select").each(function(){
                        contactid.push($(this).closest("tr").attr("doctorId"));
                    });
                }
                return $("<p class='drag_table' style='position:absolute; left:100px; top:100px;' userid='"+contactid+"' >" +"正在拖动" + contactid.length + "个患者" +"</p>");

            },
            revert: true,
            connectToSortable: ".groupBox li",
            cursor: "move"
        });

        //将分组改为能填充物体
        $(".groupBox li").droppable({
            hoverClass: "dragSelect",
            tolerance: "intersect",
            drop: function(event, ui) {
                var groupid = $(this).attr("groupId");
                var cID = ui.helper.attr("userid");
                console.log(groupid);
                console.log(cID);
                savaGroundSelect(groupid,cID);
            }
        });
        //保存函数
        function savaGroundSelect(groupid,cID){
            console.log("触发函数");
            //调用数据
            var params = {};
                params.groupId = groupid;
                params.doctorId = cID;
            $.ajax({
                url: '${ctx}/mng/doc/allot',
                type: 'POST',
                dataType:'JSON',
                data: params,
                beforeSend:function(){
                    $('body').append('<div class="tips_cover"></div><div class="cover_content"><div class="coverIng"><span>正在保存到群组，请稍候...</span></div></div>');
                },
                success: function(data){
                    if (data.code== "0") {
                        window.location.href = '${ctx}/mng/doc/list';

                    }
                }
            });

        }

        // 换弹出窗形态
        var screenHeight = $(window).height();
        var listPopupBox = $(".tb-popupBox-hook");

        listPopupBox.mouseenter(function(){
            $(this).find(".tb-popupBox").show();
            var popupBoxTopHeight = $(this).offset().top - $(window).scrollTop();
            var popupBoxHeight = $(this).find(".tb-popupBox").height();
            if((popupBoxTopHeight + popupBoxHeight) > screenHeight) {
                $(this).find(".tb-popupBox").addClass("tb-popupBox-button");
            } else {
                $(this).find(".tb-popupBox").addClass("tb-popupBox-top");
            }
            var docId = $(this).children("#popup").val();
            $.get("${ctx}/mng/doc/popupInfo",{"docId":docId},function (data) {
                var info = data.data;
                var id = info.id;
                var learnTime = info.time;
                var learnTimeId = "learnTime"+id;
                $("#"+learnTimeId).html(learnTime);

                var attend = info.attendCount;
                var attendId = "attend"+id;
                $("#"+attendId).html(attend+"个会议");

                var address = (info.province == null?"":info.province)+(info.city == null?"":info.city)+(info.zone == null?"":info.zone);
                var addressId = "address"+id;
                if(typeof(address) != "undefined"){
                    $("#"+addressId).html("<span>地址：</span>"+address);
                }

                var mobile = info.mobile;
                var mobileId = "mobile"+id;
                if(typeof(mobile) != "undefined"){
                    $("#"+mobileId).html("<span >手机：</span>"+mobile);
                }

                var mail = info.username;
                var mailId = "mail"+id;
                if(typeof(mail) != "undefined"){
                    $("#"+mailId).html("<span>邮箱：</span>"+mail);
                }
            },'json')
        }).mouseleave(function(){
            $(this).find(".tb-popupBox").hide();
            $(this).find(".tb-popupBox").removeClass("tb-popupBox-top tb-popupBox-button")
        });

        if($(".hidden-box").length > 0)
            $(".hidden-box").perfectScrollbar();

    })
</script>



</body>
</html>
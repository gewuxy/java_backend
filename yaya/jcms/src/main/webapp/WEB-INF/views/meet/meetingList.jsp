<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<title>已发布会议列表</title>
	<%@include file="/WEB-INF/include/page_context.jsp"%>
	<link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">

</head>
<body>


<!-- main -->
<div class="g-main  mettingForm clearfix">
	<form id="pageForm" name="pageForm" action="${ctx}/func/meet/list" method="post">
		<input type="hidden" name="pageNum" id="pageNum" value="${page.pageNum}">
		<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
		<input type="hidden" name="preId" id="preId" value="0" >
	</form>
	<!-- header -->
	<header class="header">
		<div class="header-content">
			<div class="clearfix">
				<div class="fl clearfix">
					<img src="${ctxStatic}/images/subPage-header-image-09.png" alt="">
				</div>
				<div class="oh">
					<p><strong>会议发布</strong></p>
					<p>快速发布在线会议，下载手机app端即可查看PPT、直播、调研、考试等会议</p>
				</div>
			</div>
		</div>
	</header>
	<!-- header end -->

	<div class="tab-hd">

		<ul class="tab-list clearfix">
			<li >
				<a href="${ctx}/func/meet/edit">会议发布<i></i></a>
			</li>
			<li >
				<a href="${ctx}/func/meet/draft">草稿箱<i></i></a>
			</li>
			<li class="cur">
				<a>已发布会议<i></i></a>
			</li>
		</ul>
	</div>

	<div class="tab-bd">
		<div class="my-mlist-shedow ">
			<div class="clearfix" style="margin:30px 0 40px 20px;">
				<a href="javascript:;" class="formButton formButton-min formButtonGreen-current"><span class="icon iconfont icon-minIcon3"></span>&nbsp;&nbsp;已发布会议</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" class="formButton fx-btn-1" id="addFolderBtn">新建文件夹</a>
				<div class="metting-bootstrap">
					<a  href="${ctx}/func/meet/list?preId=0" style="cursor: pointer;" >根目录<i class="rowSpace">\</i></a><span id="filePath"></span>
				</div>
			</div>

			<table class="table-box-1">
				<colgroup>
					<col class="table-td-6">
					<col class="table-td-5">
				</colgroup>
				<tbody>
				<c:forEach items="${page.dataList}" var="meet">
					<c:if test="${meet.type == 0}">
						<tr>
							<td class="table-td-6">
								<a href="${ctx}/func/meet/list?preId=${meet.id}"  class="metting-file-list-li">
									<div class="clearfix">
										<div class="fl"><span class="icon iconfont icon-minIcon19"></span></div>
										<div class="oh"><span>${meet.meetName}<br /><i >${meet.meetCount}个会议 </i></span></div>
									</div>

								</a>
							</td>
							<td class="table-td-5">
								<a  href="#" class="fx-btn-2"  onclick="openUpdateFolder('${meet.id}','${meet.meetName}')">修改</a><i class="rowSpace">|</i><a href="javascript:;" class="fx-btn-3" onclick="openDelFolder('${meet.id}','${meet.meetName}')">删除</a>
							</td>
						</tr>
					</c:if>
					<c:if test="${meet.type == 1}" >
						<tr>
							<td class="table-td-6">
								<a href="${ctx}/func/meet/view?id=${meet.id}&tag=1">${meet.meetName}</a>
							</td>
							<td class="table-td-5">
								<a href="javascript:;" class="fx-btn-1" onclick="openMoveFolder('${meet.id}')">移动</a><i class="rowSpace">|</i><a href="${ctx}/func/meet/view?id=${meet.id}&tag=1">会议详情</a><i class="rowSpace">|</i><a href="javascript:;" onclick="deleteMeet('${meet.id}','${meet.meetName}')" class="fx-btn-3">删除</a>
							</td>
						</tr>
					</c:if>

				</c:forEach>
				</tbody>
			</table>
			<%@include file="/WEB-INF/include/pageable.jsp"%>
		</div>


	</div>
</div>
<!--mask-wrap-->
<div class="mask-wrap">
	<div class="dis-tbcell">
		<div class="distb-box distb-box-min fx-mask-box-1 " >
			<form id="moveForm" name="moveForm" method="post">
				<input type="hidden" name="meetId">
				<div class="mask-hd clearfix">
					<h3 class="font-size-1">移动会议</h3>
					<span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
				</div>
				<div class="clearfix hidden-box">
					<ul class="fx-module-list">
						<li class="folderClass folderRoot">
							<div class="fx-module-title">
								<span class="radioboxIcon">
									<input type="radio" name="folderId" id="0" checked  class="chk_1 chk-hook" value="0">
									<label class="inline" for="0" ><i class="ico"></i>&nbsp;&nbsp;根目录</label>
								</span>
							</div>

						</li>
						<c:forEach var="folder" items="${folderList}">
							<li class="folderClass folderSubRow">
								<div class="fx-module-title">
								<span class="radioboxIcon">
									<input type="radio" name="folderId" id="${folder.id}" checked  class="chk_1 chk-hook" value="${folder.id}">
									<label class="inline" for="${folder.id}" ><i class="ico"></i>&nbsp;&nbsp;${folder.infinityName}
										<c:if test="${not empty folder.treeList}"><span class="fx-drop-icon"></span></c:if>
										</label>
								</span>
								</div>


								<div class="fx-module-setBox">
									<ul>
										<c:if test="${not empty folder.treeList}">
											<c:forEach items="${folder.treeList}" var="fol" >
												<li>
										<span class="radioboxIcon color-black">
											<input type="radio" name="folderId" id="${fol.id}" checked  class="chk_1 chk-hook" value="${fol.id}" >
											<label class="inline" for="${fol.id}" ><i class="ico"></i>&nbsp;&nbsp;${fol.infinityName}</label>
										</span>
												</li>
											</c:forEach>

										</c:if>
									</ul>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="sb-btn-box  p-btm-1">
					<button class="close-button-fx cur" id="moveSubmitBtn" value="确认">确认</button>
					<input type="button" class="close-button-fx button" name="cancelBtn" value="取消">
				</div>
			</form>
		</div>


		<div class="distb-box distb-box-min fx-mask-box-2">
			<form id="updateForm" name="updateForm" method="post">
				<input type="hidden" name="id">
				<div class="mask-hd clearfix">
					<h3 class="font-size-1">设置文件夹</h3>
					<span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
				</div>
				<div class="fx-mask-box clearfix t-center" >
					<span class="fSize-max td-title">文件名称</span><span><input type="text" name="infinityName" class="text-input" maxlength="32" placeholder="32个中文字符以内（必填）" ></span>
				</div>
				<div class="sb-btn-box p-btm-1">
					<button class="close-button-fx cur" id="updateSubmitBtn" value="确认">确认</button>
					<input type="button" class="close-button-fx button" name="cancelBtn" value="取消">
				</div>
			</form>

		</div>
		<div class="distb-box distb-box-tips fx-mask-box-3">
			<form id="delForm" name="delForm" method="post">
				<input type="hidden" name="id" value="0">
				<div class="mask-hd clearfix">
					<h3 class="font-size-1">删除文件夹</h3>
					<span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
				</div>
				<div class="fx-mask-box clearfix" id="delInfo">
				</div>
				<div class="sb-btn-box p-btm-1">
					<button class="close-button-fx cur" id="delSubmitBtn" value="确认">确认</button>
					<input type="button" class="close-button-fx button"  name="cancelBtn" value="取消">
				</div>
			</form>

		</div>

		<div class="distb-box distb-box-min fx-mask-box-4">
			<form id="folderForm" name="folderForm" method="post">
				<input type="hidden" name="preid" value="0">
				<div class="mask-hd clearfix">
					<h3 class="font-size-1">新建文件夹</h3>
					<span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
				</div>
				<div class="fx-mask-box clearfix t-center" >
					<span class="fSize-max td-title">文件名称</span><span><input type="text" class="text-input"  name="infinityName" maxlength="32" placeholder="32个中文字符以内（必填）" ></span>
				</div>
				<div class="sb-btn-box p-btm-1">
					<button class="close-button-fx cur" id="folderSubmitBtn" value="确认">确认</button>
					<input type="button" class="close-button-fx button"  name="cancelBtn" value="取消">
				</div>
			</form>
		</div>
	</div></div>
	<!--/mask-wrap end-->
	<style>

	</style>
	<script>
        $(function(){

            //tab 点击切换
            $(document).on('click',".fx-module-title label",function(){
                $(this).parents(".fx-module-list").find(".fx-module-setBox").hide().parents().removeClass("current");
                if($(this).parents(".fx-module-title").next(".fx-module-setBox").find("li").length > 0) {
                    $(this).parents(".fx-module-title").next(".fx-module-setBox").toggle().parent().addClass("current");
                }
            });

        })
        var preId = 0;
        $(function () {
			if(${folderName != null}){
                modifyPath('${folderName}');
            }

            $("#pageForm").find("input[name='preId']").val('${preId}');
            preId =  $("#pageForm").find("input[name='preId']").val();

            //弹出新增框
            $("#addFolderBtn").click(function(){
                openAddFolder();
            });

            $(".close-btn-fx").click(function(){
                closeDialog();
            });

            $("input[name='cancelBtn']").click(function(){
                closeDialog();
            });

            //移动会议
            $("#moveSubmitBtn").click(function () {
                var index = layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
                $.ajax({
                    url:'${ctx}/func/meet/moveMeet',
                    data: $("#moveForm").serialize(),
                    type:'post',
                    dataType:'json',
                    async : false,
                    success:function(data){
                        if (data.code == 0){
                            layer.close(index);
                            var preId = data.data.folderId;
                            window.location.href = '${ctx}/func/meet/list?preId='+preId;
                        }else{
                            layer.msg(data.err);

                        }
                    },
                    error:function(a, n, e){
                        layer.close(index);
                        alert(e);
                    }
                });
            });

            //确认新增文件夹
            $("#folderSubmitBtn").click(function(){
                if($.trim($("#folderForm").find("input[name='infinityName']").val()) == ''){
                    layer.msg("请输入文件夹名称");
                    $("#folderForm").find("input[name='infinityName']").focus();
                    return false;
                }
                saveFolder($("#folderForm"));
                closeDialog();
                return false;
            });

            //确认修改文件夹
            $("#updateSubmitBtn").click(function(){
                if($.trim($("#updateForm").find("input[name='infinityName']").val()) == ''){
                    layer.msg("请输入文件夹名称");
                    $("#updateForm").find("input[name='infinityName']").focus();
                    return false;
                }
                saveFolder($("#updateForm"));
                closeDialog();
                return false;
            });

            $("#delSubmitBtn").click(function () {
                var index = layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
                $.ajax({
                    url:'${ctx}/func/meet/delete',
                    data: $("#delForm").serialize(),
                    type:'post',
                    dataType:'json',
                    async : false,
                    success:function(data){
                        if (data.code == 0){
                            layer.close(index);
                            window.location.href = '${ctx}/func/meet/list';
                        }else{
                            layer.msg(data.err);

                        }
                    },
                    error:function(a, n, e){
                        layer.close(index);
                        alert(e);
                    }
                });
            });


        })


        function deleteMeet(id,name) {
            var info = "是否删除" + "\"" + name + "\"" + "在线会议";
            top.layer.confirm(info,function () {
                top.layer.closeAll();
                $.get('${ctx}/func/meet/deleteMeet',{'meetId':id},function (data) {
                    if(data.code == 0){
                        window.location.reload();
                    }else{
                        top.layer.msg(data.err);
                    }
                },'json');
            })
        }



        //弹出移动框
        function openMoveFolder(meetId) {
            if(${folderList == null}) {
                top.layer.confirm("尚未创建文件夹，是否创建？",function () {
                    top.layer.closeAll();
                    openAddFolder();
                })
            }else{
                $('.mask-wrap').addClass('dis-table');
                $('.fx-mask-box-1').show();
                $("#moveForm").find("input[name='meetId']").val(meetId);
            }




        }

        //弹出新增框
        function openAddFolder(){
            $('.mask-wrap').addClass('dis-table');
            $('.fx-mask-box-4').show();
            var p = $("#pageForm").find("input[name='preId']").val();
            $("#folderForm").find("input[name='preid']").val(p);
        }

        //弹出删除框
        function openDelFolder(id,name) {
            $('.mask-wrap').addClass('dis-table');
            $('.fx-mask-box-3').show();
            $("#delForm").find("input[name='id']").val(id);
            $("#delInfo").html("删除后，会议将移动到根目录，确定删除？");
        }
        //弹出修改框
        function openUpdateFolder(id,name){
            $('.mask-wrap').addClass('dis-table');
            $('.fx-mask-box-2').show();
            $("#updateForm").find("input[name='id']").val(id);
            $("#updateForm").find("input[name='infinityName']").val(name);

        }

        function closeDialog(){
            $('.mask-wrap').removeClass('dis-table');
            $('.fx-mask-box-1').hide();
            $('.fx-mask-box-2').hide();
            $('.fx-mask-box-3').hide();
            $('.fx-mask-box-4').hide();
            $("#folderForm")[0].reset();
            $("#updateForm")[0].reset();
            $("#moveForm")[0].reset();
        }

        //保存文件夹
        function saveFolder(form){
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajax({
                url:'${ctx}/func/meet/saveFolder',
                data:form.serialize(),
                type:'post',
                dataType:'json',
                async : false,
                success:function(data){
                    if (data.code == 0){
                        layer.close(index);
                        window.location.href = '${ctx}/func/meet/list?preId='+preId;
                    }else{
                        layer.msg(data.err);

                    }
                },
                error:function(a, n, e){
                    layer.close(index);
                    alert(e);
                }
            });
            return false;
        }



        function modifyPath(folderName){
            $("#filePath").html("");
            var s = folderName;
            var strs= new Array();
            var strs2= new Array();
            strs=s.split("_");
            if(strs.length == 2){
					$('#addFolderBtn').remove();
			}
            for (i=0;i<strs.length ;i++ )
            {
                strs2 = strs[i].split("-");
                $("#filePath").append('<a href="${ctx}/func/meet/list?preId='+strs2[1]+'" class="color-blue" style="cursor:pointer;">'+strs2[0]+'</a><i class="rowSpace" > \\ </i>');
            }

        }




	</script>


</body>
</html>
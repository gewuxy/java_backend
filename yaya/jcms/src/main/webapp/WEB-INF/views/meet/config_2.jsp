<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/23
  Time: 10:49
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>会议内容编辑</title>

    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
    <link href="${ctxStatic}/css/daterangepicker.css" rel="stylesheet">
</head>
<body>
<div class="g-main  mettingForm clearfix">

    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-03.png" alt="">
                </div>
                <div class="oh">
                    <p><strong>会议发布</strong></p>
                    <p>快速发布在线会议，下载手机app端即可查看PPT、直播、调研、考试等会议</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->



    <div class="tab-bd" style="padding-bottom:500px;">

        <div class=" clearfix metting-process">
            <div class=" clearfix link-justified metting-process-02">
                <ul>
                    <li class="cur">创建会议</li>
                    <li>功能设置</li>
                    <li>发布预览</li>
                </ul>
            </div>
        </div>



        <%@include file="funTab.jsp"%>
        <div class="clearfix" style="margin:40px 0 0 25px;">
            <a href="javascript:" class="formButton fx-btn-2" id="addVideoBtn">添加视频</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:" class="formButton fx-btn-1" id="addFolderBtn">新建文件夹</a>
            <div class="metting-bootstrap">
                <a style="cursor: pointer;" detailId="0" onclick="showSublist(0,'根目录')">根目录 <i > \ </i></a><span id="filePath"></span>
            </div>
        </div>
        <div class="clearfix" style="margin:40px 20px 60px 25px;">
            <table class="table-box-3 mettingForm">
                <thead>
                <tr>
                    <td class="tb-w-1-title"><i>名字</i></td>
                    <td class="tb-w-2">总时间（秒）</td>
                    <td class="tb-w-6">消耗流量（M）</td>
                    <td class="tb-w-2 p-le-1">操作</td>
                </tr>
                </thead>
                <tbody id="details">
                <c:forEach items="${details}" var="detail">
                    <tr detailId="${detail.id}">
                        <td class="tb-w-1-title">
                            <a class="detailTitle" onclick="showSublist(${detail.id}, '${detail.name}')" detailId="${detail.id}" detailName="${detail.name}" style="cursor: pointer;"><span class="icon iconfont ${detail.type == 0?'icon-minIcon8':'icon-minIcon2'}" id="detail_name_${detail.id}"></span>${detail.name}</a>
                        </td>
                        <td class="tb-w-2" id="detail_duration_${detail.id}">
                                <c:if test="${detail.type == 1}">
                                    ${detail.duration} 秒
                                </c:if>
                        </td>
                        <td class="tb-w-6" id="detail_videoType_${detail.id}">
                            <c:choose>
                                <c:when test="${detail.fileSize == null}">
                                    外链无流量消耗
                                </c:when>
                                <c:otherwise>${detail.fileSizeStr}</c:otherwise>
                            </c:choose>
                        </td>
                        <td class="tb-w-2 p-le-1">
                            <a style="cursor: pointer;" id="detail_modify_${detail.id}" detailId="${detail.id}" class="yl fx-btn-4" onclick="loadDetailInfo(${detail.id})">修改</a><i class="rowSpace">|</i><a class="fx-btn-2 zai" style="cursor: pointer" id="detail_del_${detail.id}" onclick="delFile(${detail.id})">删除</a>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
        <div class="buttonArea clearfix" style="margin: 20px 25px 40px;">
            <div class="formrow">
                <div class="fl clearfix">
                    <input type="button" onclick="window.location.href='${ctx}/func/meet/edit?id=${meetId}'" class="formButton formButton-max" value="上一步">&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" id="finishBtn" class="formButton formButton-max" value="下一步">
                </div>
            </div>
        </div>
    </div>
</div>
<!--mask-wrap-->
<div class="mask-wrap">
    <div class="dis-tbcell">
        <div class="distb-box distb-box-min fx-mask-box-1">
            <form id="folderForm" name="folderForm" method="post">
                <input type="hidden" name="id" >
                <input type="hidden" name="preId" value="0">
                <input type="hidden" name="type" value="0">
                <input type="hidden" name="courseId" value="${course.id}">
                <div class="mask-hd clearfix">
                    <h3 class="font-size-1">新建文件夹</h3>
                    <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
                </div>
                <div class="fx-mask-box clearfix t-center" >
                    <span class="fSize-max td-title">文件夹名称</span><span><input type="text" name="name" maxlength="50" class="text-input" placeholder="50个中文字符以内（必填）"></span>
                </div>
                <div class="sb-btn-box p-btm-1">
                    <input type="button" class="close-button-fx cur button" id="folderSubmitBtn" value="确认">
                    <input type="button" class="close-button-fx button" id="folderCancelBtn" value="取消">
                </div>
            </form>
        </div>
        <div class="distb-box distb-box-addViedoBox fx-mask-box-2">
            <form id="videoForm" name="videoForm" method="post">
                <input type="hidden" name="id" id="videoDetailId">
                <input type="hidden" name="type" value="1">
                <input type="hidden" name="videoType" value="1"/>
                <input type="hidden" name="preId" value="0"  id="videoPreId">
                <input type="hidden" name="courseId" value="${course.id}">
                <div class="mask-hd clearfix">
                    <h3 class="font-size-1">添加视频</h3>
                    <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
                </div>
                <div class="metting-popupbox-bd">
                    <div class="tj-content clearfix">
                        <div class="tab-wrap popup-tab-list">
                            <span class="tab-menu tab-cur" index="0">本地上传</span>
                            <span class="tab-menu" index="1">URL链接</span>
                        </div>
                        <div class="tab-con-wrap" style="display: block;" id="localVideoView">
                            <div class="metting-upload-viedo-item">
                                <label for="uploadVideo">
                                    <input type="file" name="file" class="none" id="uploadVideo">
                                    <h3 class="t-center">添加视频</h3>
                                    <p class="t-center">视频大小最大500M</p>
                                </label>
                            </div>
                            <!--完成上传后-->
                            <div class="metting-progreesItem clearfix" id="uploadProgress" style="display: none;">
                                <p><h4 id="uploadFileName"></h4></p>
                                <p>上传文件的过程中，请勿关闭网页</p>
                                <p><span class="metting-progreesBar"><i id="progressI" style="width:0%"></i></span>&nbsp;&nbsp;<span id="progressView">0%</span>&nbsp;&nbsp;<span class="color-blue">上传中...</span></p>
                            </div>
                        </div>
                        <div class="tab-con-wrap" style="display: none;" id="normalVideoView">
                            <div class="formrow clearfix">
                                <label for="" class="formTitle">文件名</label>
                                <div class="formControls">
                                    <input type="text" class="textInput" name="name" maxlength="50" placeholder="视频名">
                                </div>
                            </div>
                            <div class="formrow clearfix">
                                <label for="" class="formTitle">视频URL</label>
                                <div class="formControls">
                                    <input type="text" class="textInput" name="url" maxlength="250" placeholder="请设置视频的地址">
                                </div>
                            </div>
                            <div class="formrow clearfix">
                                <label for="" class="formTitle">总时间（秒）</label>
                                <div class="formControls">
                                    <input type="text" class="textInput" maxlength="20" name="duration" placeholder="请设置视频长度">
                                </div>
                            </div>
                            <div class="sb-btn-box p-btm-1 t-right">
                                <input type="button" class="close-button-fx cur button" id="videoSubmitBtn" value="确认">
                                <input type="button" class="close-button-fx button" id="videoCancelBtn" value="取消">
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <div class="distb-box distb-box-min fx-mask-box-3">
                <div class="mask-hd clearfix">
                    <h3 class="font-size-1">操作提示</h3>
                    <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
                </div>
                <div class="fx-mask-box clearfix t-center" >
                    <span class="fSize-max td-title">此操作不可逆,您确定要删除此文件吗？</span>
                </div>
                <div class="sb-btn-box p-btm-1">
                    <input type="button" class="close-button-fx cur button" id="delSubmitBtn" value="确认">
                    <input type="button" class="close-button-fx button" id="delCancelBtn" onclick="closeDialog()" value="取消">
                </div>
        </div>
    </div>
</div>

<script src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script>

    var preId = 0;
    var currentDetailId = 0;
    $(function(){
       $("#addFolderBtn").click(function(){
           $("#folderForm").find("input[name='id']").val("");
           openAddFolder();
       });

       $("#addVideoBtn").click(function(){
           $("#videoForm").find("input[name='id']").val("");
           openAddLocalVideo();
       });

       $(".close-btn-fx").click(function(){
           closeDialog();
       });

       $("#folderCancelBtn").click(function(){
           closeDialog();
       });

       $("#videoCancelBtn").click(function(){
           closeDialog();
           return false;
       });

        $("#finishBtn").click(function(){
            window.location.href = '${ctx}/func/meet/video/finish?meetId=${meetId}&moduleId=${moduleId}&courseId=${course.id}';
        });


       $("#videoSubmitBtn").click(function(){
           if($.trim($("#videoForm").find("input[name='name']").val()) == ''){
               layer.msg("请输入文件标题");
               $("#videoForm").find("input[name='name']").focus();
               return false;
           }
           if($.trim($("#videoForm").find("input[name='url']").val()) == ''){
               layer.msg("请输入视频URL");
               $("#videoForm").find("input[name='url']").focus();
               return false;
           }
           if($.trim($("#videoForm").find("input[name='duration']").val()) == ''){
               layer.msg("请输入视频时长");
               $("#videoForm").find("input[name='duration']").focus();
               return false;
           }
           saveFolderOrVideo($("#videoForm"));
       });

       $("#folderSubmitBtn").click(function(){
           if($.trim($("#folderForm").find("input[name='name']").val()) == ''){
               layer.msg("请输入文件标题");
               $("#folderForm").find("input[name='name']").focus();
               return false;
           }
           saveFolderOrVideo($("#folderForm"));
           return false;
       });

       $("#delSubmitBtn").click(function(){
           $.get('${ctx}/func/meet/video/del',{'id':currentDetailId},function (data) {
               $("tr[detailId='"+currentDetailId+"']").remove();
               currentDetailId = 0;
               closeDialog();
           },'json');
       });

       $(".popup-tab-list span").click(function () {
           var index = $(this).attr("index");
           if (index == 0){
               openAddLocalVideo();
           } else {
               openAddVideo();
           }

       });

       $("#uploadVideo").change(function(){
            uploadVideo($(this));
       });

    });

    function uploadVideo(e){
        var fileName = $(e).val().toLowerCase();
        fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
        if (!fileName.endsWith(".mp4") && !fileName.endsWith(".avi")){
            layer.msg("请上传正确的视频文件");
            return false;
        }
        $("#uploadFileName").text(fileName);
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        var detailId = $("#videoDetailId").val();
        var preId = $("#videoPreId").val();
        $("#progressI").css("width", "0%");
        $("#progressView").text("0%");
        uploadOver = false;
        showUploadProgress();
        $.ajaxFileUpload({
            url: '${ctx}/func/meet/video/upload?courseId=${course.id}&preId='+preId+'&detailId='+detailId, //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: "uploadVideo", //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data)  //服务器成功响应处理函数
            {
                layer.close(index);
                //回调函数传回传完之后的URL地址
                if(data.code == 0){
                    addRow(data.data);
                    $("#uploadProgress").hide();
                    closeDialog();
                }else{
                    layer.msg(data.msg);
                }
                $("#uploadVideo").replaceWith('<input type="file" id="uploadVideo" name="file" class="none">');
                $("#uploadVideo").change(function(){
                    uploadVideo($("#uploadVideo"));
                });
            },
            error:function(data, status, e){
                alert(e);
                layer.close(index);
            }
        });
    }

    var uploadOver = false;

    function showUploadProgress(){
        $("#uploadProgress").show();
        if (!uploadOver){
            $.get('${ctx}/file/uploadStatus', {}, function (data) {
                var progress = data.data.progress;
                console.log("progress : "+progress);
                $("#progressI").css("width", progress);
                $("#progressView").text(progress);
                if (progress != '100.0%'){
                    setTimeout(showUploadProgress(), 200);
                } else {
                    uploadOver = true;
                    $("#uploadProgress").hide();
                    $.get('${ctx}/file/cleanProgress', {}, function (data) {

                    }, 'json');
                }
            }, 'json');
        }
    }

    function saveFolderOrVideo(form){
        $.post('${ctx}/func/meet/video/save',form.serialize(),function (data) {
            if(data.code == 0){
                addRow(data.data);
                closeDialog();
            }else{
                layer.msg(data.err);
            }
        },'json');
        return false;
    }

    function showSublist(detailId, detailName){
        if(preId != detailId){
            $.get('${ctx}/func/meet/video/sublist',{'preId':detailId, 'courseId':'${course.id}'}, function (data) {
                if(data.code == 0){
                    preId = detailId;
                    $("#details").html("");
                    for(var index in data.data){
                        addRow(data.data[index]);
                    }
                    modifyPath(detailId, detailName);
                }
            },'json');
        }
    }


    function modifyPath(detailId, detailName){
        if(detailId == undefined || detailId == null || detailId == 0){
            $("#filePath").html("");
        }else{
            if($("#filePath").find("a[detailId='"+detailId+"']").length > 0){
                $("#filePath").find("a[detailId='"+detailId+"']").nextAll().remove();
                $("#filePath").append('<i class="rowSpace" > \\ </i>');
            }else{
                $("#filePath").append('<a onclick="showSublist('+detailId+',\''+detailName+'\')" detailId="'+detailId+'" detailName="'+detailName+'" class="color-blue" style="cursor:pointer;">'+detailName+'</a><i class="rowSpace" > \\ </i>');
            }
        }
    }

    function addRow(data){
        var icon = data.type == 0?'icon-minIcon8':'icon-minIcon2';
        $("tr[detailId='"+data.id+"']").remove();
        var clickfun = data.type == 0?'onclick="showSublist('+data.id+',\''+data.name+'\')" detailId="'+data.id+'"':'';
        var html = '<tr detailId="'+data.id+'"><td class="tb-w-1-title"><a class="detailTitle" '+clickfun+'  detailName="'+data.name+'" style="cursor: pointer;">' +
            '<span class="icon iconfont '+icon+'" id="detail_name_'+data.id+'"></span>'+data.name+'</a>'+
            '</td><td class="tb-w-2" id="detail_duration_'+data.id+'">'+
            (data.type == 0 ?"":(data.duration+"秒"))+
            '</td><td class="tb-w-6" id="detail_videoType_'+data.id+'">'+
            (data.fileSizeStr == null ? "外链无流量消耗" : data.fileSizeStr)+
            '</td><td class="tb-w-2 p-le-1">'+
            '<a style="cursor: pointer;" id="detail_modify_'+data.id+'" detailId="'+data.id+'" class="yl fx-btn-4" onclick="loadDetailInfo('+data.id+')">修改</a><i class="rowSpace">|</i><a class="fx-btn-2 zai" onclick="delFile('+data.id+')" style="cursor: pointer" id="detail_del_'+data.id+'">删除</a>'+
            '</td></tr>';
        $("#details").append(html);
    }


    function loadDetailInfo(id){
        $.get('${ctx}/func/meet/video/edit',{"detailId":id}, function (data) {
            initData(data.data);
            //alert(dataList.dataList.name);
        },'json');
    }

    function initData(detail){
        if(detail.type == 0){
            $("#folderForm").find("input[name='name']").val(detail.name);
            $("#folderForm").find("input[name='id']").val(detail.id);
            openAddFolder();
        }else{
            $("#videoForm").find("input[name='name']").val(detail.name);
            $("#videoForm").find("input[name='url']").val(detail.url);
            $("#videoForm").find("input[name='duration']").val(detail.duration);
            $("#videoForm").find("input[name='videoType']").val(detail.videoType);
            $("#videoForm").find("input[name='id']").val(detail.id);
            if (detail.videoType == '0'){//内部链接
                openAddLocalVideo();
            } else {
                openAddVideo();
            }
        }
    }

    function delFile(detailId){
        if(detailId != undefined && detailId !=''){
            openConfirm(detailId);
        }
    }

    function closeDialog(){
        $('.mask-wrap').removeClass('dis-table');
        $('.fx-mask-box-1').hide();
        $('.fx-mask-box-2').hide();
        $('.fx-mask-box-3').hide();
        $("#folderForm")[0].reset();
        $("#videoForm")[0].reset();
    }

    function openAddFolder(){
        $('.mask-wrap').addClass('dis-table');
        $('.fx-mask-box-1').show();
        $("#folderForm").find("input[name='preId']").val(preId);
    }

    function openAddVideo(){
        //$("#videoForm")[0].reset();
        $('.mask-wrap').addClass('dis-table');
        $('.fx-mask-box-2').show();
        $("#videoForm").find("input[name='preId']").val(preId);
        $("#videoForm").find("input[name='videoType']").val(1);
        $("#normalVideoView").show();
        $("#localVideoView").hide();
        $(".popup-tab-list span[index='1']").siblings().removeClass("tab-cur");
        $(".popup-tab-list span[index='1']").addClass("tab-cur");
    }

    function openAddLocalVideo() {
        //$("#videoForm")[0].reset();
        $('.mask-wrap').addClass('dis-table');
        $('.fx-mask-box-2').show();
        $("#videoForm").find("input[name='preId']").val(preId);

        $("#videoForm").find("input[name='videoType']").val(0);

        $("#normalVideoView").hide();
        $("#localVideoView").show();

        $(".popup-tab-list span[index='0']").siblings().removeClass("tab-cur");
        $(".popup-tab-list span[index='0']").addClass("tab-cur");
    }

    function openConfirm(detailId){
        $('.mask-wrap').addClass('dis-table');
        $('.fx-mask-box-3').show();
        currentDetailId = detailId;
    }
</script>
</body>
</html>

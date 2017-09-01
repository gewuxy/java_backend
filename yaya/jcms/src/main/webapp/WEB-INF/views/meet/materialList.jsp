<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/6/9
  Time: 18:43
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>会议资料</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
    <link rel="stylesheet" href="${ctxStatic}/js/uploadify/uploadify.css">
</head>
<body>
<div class="g-main  mettingForm mettingSwiperBox clearfix">
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
    <%@include file="meetAddHead.jsp"%>
    <div class="tab-bd">
        <div class=" clearfix metting-process">
            <div class=" clearfix link-justified metting-process-01">
                <ul>
                    <li class="cur">创建会议</li>
                    <li>设置功能</li>
                    <li>发布预览</li>
                </ul>
            </div>
        </div>
        <%@include file="funTab.jsp"%>
        <div class="tab-bd clearfix">
            <div class="tab-lebar xsgl-box">
                <div class="table-search clearfix">
                <span class="table-checkall" >

                    <div id="uploadify" class="" style="height: 30px; width: 120px; float:left;"><div id="uploadify-button" class="uploadify-button " style="cursor:pointer;height: 30px; line-height: 30px; width: 120px;"><label class="uploadify-button-text" style="cursor: pointer;"><input type="file" id="material" class="hide" name="file">上传文件</label></div></div>
                    <span class="file-img-txt" style="float:left; position:relative; top:10px; left:10px">文件大小: 不超过8M,格式 : PDF、PPT、Word</span>
                </span>
                </div>
                <table class="table-box-3">
                    <thead>
                    <tr>
                        <td class="tb-w-1">文件名称</td>
                        <td class="tb-w-4">上传时间</td>
                        <td class="tb-w-4">文件大小</td>
                        <td class="tb-w-4 p-le-1">操作</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="ma">
                        <tr>
                            <td class="tb-w-1">
                                <a  ><span class="icon iconfont icon-minIcon6"></span>${ma.name}</a>
                            </td>
                            <td class="tb-w-4"><fmt:formatDate value="${ma.createTime}" pattern="yyyy/MM/dd HH:mm"/></td>
                            <td class="tb-w-4">${ma.fileSizeStr}</td>
                            <td class="tb-w-4">
                                <a href="javascript:" class="fx-btn-1" id="${ma.id}">删除</a>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
        <div class="buttonArea clearfix" style="margin: 20px 25px 40px;">
            <div class="formrow">
                <%--<div class="fr clearfix">
                    <input type="button" id="draftBtn" class="formButton formButton-max" value="保存草稿">
                </div>--%>
                <div class="fl clearfix">
                    <input type="button" class="formButton formButton-max" onclick="window.location.href='${ctx}/func/meet/edit?id=${meetId}'" value="上一步">&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" class="formButton formButton-max" id="finishBtn" value="下一步">
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script>
    $(function(){
        if("${err}"){
            top.layer.msg("${err}");
        }

        /*$("#draftBtn").click(function(){
            top.layer.msg("保存草稿成功");
        });*/

        $("#finishBtn").click(function(){
            window.location.href = '${ctx}/func/meet/view?id=${meetId}';
        });

        $("#material").change(function(){
            uploadMaterial();
        });

        $(".fx-btn-1").click(function(){
            var materialId = $(this).attr("id");
            layer.confirm("确定要删除此课件吗？",function(){
                layer.closeAll();
                window.location.href='${ctx}/func/meet/delMaterial?meetId=${meetId}&id='+materialId;
            });
        });

    });

    function checkType(lowerName){
        if(!lowerName.endsWith(".pptx") && !lowerName.endsWith(".ppt") && !lowerName.endsWith(".pdf")  && !lowerName.endsWith(".doc") && !lowerName.endsWith(".docx")){
            return false;
        }
        return true;
    }

    function uploadMaterial(){
        var fileName = $("#material").val();
        var lowerName = fileName.toLowerCase();
        if(checkType(lowerName)){
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajaxFileUpload({
                url: '${ctx}/func/meet/uploadMetarial?meetId=${meetId}', //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: "material", //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                    layer.close(index);
                    $("#material").replaceWith('<input type="file" id="material" class="hide" name="file">');
                    $("#material").change(function(){
                        uploadMaterial();
                    });
                    //回调函数传回传完之后的URL地址
                    if(data.code == 0){
                        window.location.reload();
                    }else{
                        top.layer.msg(data.err);
                    }
                },
                error:function(data, status, e){
                    layer.msg(e);
                    layer.close(index);
                }
            });
        }else{
            top.layer.msg("文件格式不正确");
        }
    }
</script>
</body>
</html>

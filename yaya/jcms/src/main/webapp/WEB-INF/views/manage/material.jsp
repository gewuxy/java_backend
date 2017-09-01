<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title> 学术资料 </title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta name="Keywords" content="">
    <meta name="description" content="">
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
    <link rel="stylesheet" href="${ctxStatic}/js/uploadify/uploadify.css">

</head>
<body>



<!-- main -->
<div class="g-main clearfix">
    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-04.png" alt="">
                </div>
                <div class="oh">
                    <p><strong>资料管理</strong></p>
                    <p>学术资料即时上传，让医生获取更多的资料</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->

    <div class="tab-hd">

        <ul class="tab-list clearfix">
            <li class="cur">
                <a href="">学术资料<i></i></a>
            </li>
        </ul>
    </div>
    <div class="tab-bd clearfix">
        <div class="tab-lebar xsgl-box">
            <div class="table-search clearfix">
                 <span class="table-checkall" >
                    <!-- <span class="file-upload file-btn-bd"><input type="file">上传文件</span> -->
                     <input type="file" id="material" style="display:none" name="file" onchange="toUpload()">
                    <div id="uploadify" class="" style="height: 30px; width: 120px; float:left;"><div id="uploadify-button" class="uploadify-button " style="height: 30px; line-height: 30px; width: 120px;"><span class="uploadify-button-text" ><button type="button" class="uploadify-button-text" onclick="material.click()">上传文件</button></span></div></div>
                     <span class="file-img-txt" style="float:left; position:relative; top:10px; left:10px">文件大小: 不超过100M,格式 : PDF、PPT、Word</span>
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
                <c:forEach items="${page.dataList}" var="p">
                    <tr mId = "${p.id}">
                        <td class="tb-w-1">
                            <a href="${fileBase}${p.materialUrl}"><span class="icon iconfont icon-minIcon6"></span>${p.materialName}.${p.materialType}</a>
                        </td>
                        <td class="tb-w-4"><fmt:formatDate value="${p.createTime}" pattern="yyyy / MM / dd"></fmt:formatDate> </td>
                        <c:if test="${p.fileSize/1024>=1024}">
                            <td class="tb-w-4"><fmt:formatNumber type="number" value="${p.fileSize/1024/1024}" maxFractionDigits="0"></fmt:formatNumber>M</td>
                        </c:if>
                        <c:if test="${p.fileSize/1024<1024}">
                            <td class="tb-w-4"><fmt:formatNumber type="number" value="${p.fileSize/1024}" maxFractionDigits="0"></fmt:formatNumber>K</td>
                        </c:if>
                        <td class="tb-w-4">
                            <a href="javascript:" class="fx-btn-1" id="${p.id}">删除</a>
                        </td>
                    </tr>
                </c:forEach>


                </tbody>
            </table>
            <%@include file="/WEB-INF/include/pageable.jsp"%>
            <form id="pageForm" name="pageForm" method="get" action="${ctx}/mng/material/list">
                <input type="hidden" name="pageNum" id="pageNum" value="${page.pageNum}" />
                <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
            </form>
        </div>
    </div>



</div>


<script src="${ctxStatic}/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${ctxStatic}/js/main.js"></script>
<script src="${ctxStatic}/js/util.js"></script>
<script src="${ctxStatic}/js/ajaxfileupload.js"></script>

<script>

    $(function () {
        if("${err}"){
            top.layer.msg("${err}");
        }
    });

    var fileUploadUrl = "${ctx}/mng/material/upload";
    function toUpload(){
            var filename = $("#material").val();
            var extStart = filename.lastIndexOf(".");
            var ext = filename.substring(extStart,filename.length).toUpperCase();
            if(ext != ".PDF" && ext != ".PPT" && ext != ".DOC"&& ext != ".PPTX"&& ext != ".DOCX"&& ext != ".XLS"&& ext != ".XLSX"){
                layer.msg("文件格式不支持，请上传文档类型的文件");
            }else{
                upload("material","material",100*1024*1024,uploadHandler);
            }
    }

    function uploadHandler(result){
        window.location.reload();
    }

    $(".fx-btn-1").click(function(){
        var materialId = $(this).attr("id");
        top.layer.confirm("确定要删除此资料吗？",function(){
            top.layer.closeAll();
            window.location.href='${ctx}/mng/material/delete?materialId='+materialId;
        });
    });


</script>
</body>
</html>
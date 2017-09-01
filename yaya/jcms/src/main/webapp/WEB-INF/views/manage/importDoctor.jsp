<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

    <title>导入医师</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta name="Keywords" content="">
    <meta name="description" content="">
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
</head>
<body>


<!-- main -->
<div class="g-main  clearfix">

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
            <li >
                <a href="${ctx}/mng/doc/list">医生管理<i></i></a>
            </li>
            <li class="cur">
                <a href="${ctx}/mng/doc/import">导入医生<i></i></a>
            </li>
        </ul>
    </div>
    <div class="tj-con mar-top-bd">
        <div class=" tj-content clearfix">
            <div class="excel-item">
                <div class="fl">
                    <img src="${ctxStatic}/img/_img-excel.jpg" alt="">
                </div>
                <div class="oh clearfix">
                    <h2>Excel导入</h2>
                    <div class="excel-info">
                        您可以先下载Excel模板，按照指定格式编辑组织及人员信息，<Br />
                        然后导入系统。
                    </div>
                    <div class="excel-inputWrap">
                        <a href="javascript:void(0);" class="button-middle button-bg-blue" onclick="DoctorExcel.click()">导入数据</a>&nbsp;&nbsp;&nbsp;
                        <input type="file" name="file" id="DoctorExcel" style="display: none" onchange="uploadTemp()">
                        <a href="${ctxStatic}/upload/temp/temp.xlsx" class="button-middle button-bg-gary">下载模版</a>
                    </div>
                </div>
            </div>

        </div>
    </div>




</div>
<div class="mask-wrap">
    <div class="dis-tbcell">
        <div class="distb-box fx-mask-box-2">
            <div class="sb-btn-box p-btm-1">
                <div class="column clearfix column-last">
                    <div class="col-2-1 t-left">
                        <button class="close-button-fx">批量处理</button>
                        <strong><a href="#" class="color-blue">点击此处</a></strong>
                        下载excel模版
                    </div>
                    <div class="col-2-1 t-right last">
                        <button class="close-button-fx">继续添加</button>
                        <button class="close-button-fx cur">确认</button>

                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<script src="${ctxStatic}/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${ctxStatic}/js/main.js"></script>
<script src="${ctxStatic}/js/util.js"></script>
<script src="${ctxStatic}/js/ajaxfileupload.js"></script>
<style>

</style>
<script>
    var fileUploadUrl = "${ctx}/mng/doc/uploadTemp";

    function uploadTemp(){
        var fileName = $("#DoctorExcel").val();
        var extStart = fileName.lastIndexOf(".");
        var ext = fileName.substring(extStart,fileName.length).toUpperCase();
        if(ext != ".XLS"&& ext != ".XLSX"){
            layer.msg("文件格式不支持，请上传excel类型的文件");
        }else{
            upload("DoctorExcel","DoctorExcel",5*1024*1024,uploadHandler);
        }
    }

    function uploadHandler(result){
        if(result.code == 0){
            layer.msg(result.data,{time:2000},function () {
                window.location.href="${ctx}/mng/doc/list";
            });

        }else{
            layer.msg("导入失败");
        }
    }


</script>

</body>
</html>
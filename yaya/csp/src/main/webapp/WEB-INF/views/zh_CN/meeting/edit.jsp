<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/17
  Time: 9:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>会议管理</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">


    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
</head>
<body>
<div id="wrapper">
    <%@include file="/WEB-INF/include/header_zh_CN.jsp" %>
    <div class="admin-content bg-gray" >
        <div class="page-width clearfix">
            <div class="admin-module clearfix item-radius">
                <div class="row clearfix">
                    <div class="col-lg-5">
                        <div class="upload-ppt-box">
                            <div class="upload-ppt-area">
                                <label for="uploadFile">
                                    <input type="file" name="file" class="none" id="uploadFile">
                                    <p class="img"><img src="${ctxStatic}/images/upload-ppt-area-img.png" alt=""></p>
                                    <p>或拖动PDF／PPT到此区域上传</p>
                                </label>
                            </div>
                            <div class="upload-main">
                                <div class="metting-progreesItem clearfix t-left none">
                                    上传进度<span class="color-blue">30%</span>
                                    <p><span class="metting-progreesBar"><i style="width:30%"></i></span></p>

                                </div>
                                <div class="admin-button t-center">
                                <c:choose>
                                    <c:when test="${not empty course.details}">
                                            <a href="javascript:;" class="button min-btn" onclick="uploadFile()">重新上传</a>&nbsp;&nbsp;&nbsp;
                                            <a href="${ctx}/mgr/meet/details/${course.id}" class="button color-blue min-btn">编辑</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="javascript:;" class="button color-blue min-btn"  onclick="uploadFile()">上传演讲文档</a>
                                    </c:otherwise>
                                </c:choose>
                                </div>
                                <p class="color-gray-02">选择小于100M的文件</p>

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-7">
                        <form action="">
                            <div class="meeting-form-item login-form-item">
                                <label for="email2" class="cells-block pr"><input id="email2" type="text" class="login-formInput" placeholder="会议名称" value="${course.title}"></label>
                                <div class="textarea">
                                    <textarea name="" id="" cols="30" rows="10">${course.info}</textarea>
                                    <p class="t-right">600</p>
                                </div>
                                <div class="cells-block clearfix meeting-classify meeting-classify-hook">
                                    <span class="subject">分类&nbsp;&nbsp;|<i>医学类</i></span><span class="office">内科系统</span>
                                </div>
                                <div class="meeting-tab clearfix">
                                    <label for="recorded" class="recorded-btn">
                                        <input id="recorded" type="radio" name="mettingType">
                                        <div class="meeting-tab-btn"><i></i>投屏录播</div>

                                    </label>
                                    <label for="live" class="live-btn cur" >
                                        <input id="live" type="radio" name="mettingType" checked="true">
                                        <div class="meeting-tab-btn"><i></i>投屏直播</div>
                                        <div class="meeting-tab-main">
                                            <div class="clearfix">
                                                <div class="formrow">
                                                    <div class="formControls">
                                                            <span class="time-tj">
                                                                <label for="" id="timeStart">
                                                                    时间<input type="text" disabled="" class="timedate-input " placeholder="2017-01-01 00:00 至 2017-01-01 00:00">
                                                                </label>
                                                            </span>
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="cells-block clearfix checkbox-box">
                                                    <span class="checkboxIcon">
                                                        <input type="checkbox" id="popup_checkbox_2" class="chk_1 chk-hook">
                                                        <label for="popup_checkbox_2" class="popup_checkbox_hook"><i class="ico checkboxCurrent"></i>&nbsp;&nbsp;开启视频直播</label>
                                                    </span>
                                                <div class="checkbox-main">
                                                    <p>流量消耗每人约0.5G/1小时，本次直播时长30分钟，如100人在线预计消耗25G流量。</p>
                                                    <div class="text">流量剩余<span class="color-blue">20</span>G <a href="javascript:;" class="cancel-hook">立即充值</a></div>
                                                </div>
                                            </div>
                                        </div>
                                    </label>
                                </div>

                                <%--<span class="cells-block error one"><img src="images/login-error-icon.png" alt="">&nbsp;输入正确密码</span>--%>
                                <input href="#" type="button" class="button login-button buttonBlue last" value="确认提交">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@include file="/WEB-INF/include/footer_zh_CN.jsp"%>
</div>
<script src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script>

    function uploadFile(){
        var fileName = $("#uploadFile").val().toLowerCase();
        if (!fileName.endWith(".ppt") && !fileName.endWith(".pptx") && !fileName.endWith(".pdf")){
            layer.msg("请选择ppt|pptx|pdf格式文件");
            return false;
        }
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.ajaxFileUpload({
            url: "${ctx}/mgr/meet/upload"+"?courseId=${course.id}", //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: "uploadFile", //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data)  //服务器成功响应处理函数
            {
                layer.close(index);
                if (data.code == 0){
                    //回调函数传回传完之后的URL地址
                    window.location.reload();
                } else {
                    layer.msg(data.err);
                }
            },
            error:function(data, status, e){
                alert(e);
                layer.close(index);
            }
        });
    }

    $(function(){

    });
</script>
</body>
</html>

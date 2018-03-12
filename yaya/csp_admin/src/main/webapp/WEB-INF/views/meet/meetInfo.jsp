<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>会议信息</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="#">会议信息</a></li>
</ul><br/>
<form id="inputForm" action="${ctx}/csp/meet/delete" method="post" class="form-horizontal">
    <input type="hidden" name="status" value="" id="status">
    <div class="control-group">
        <label class="control-label">标题:</label>
        <div class="controls">
            <input type="search" name="meetName" style="width: 300px;" maxlength="50" class="required" value="${meet.title}" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否发布:</label>
        <div class="controls">
            <input type="search" name="organizer" style="width: 300px;" htmlEscape="false" maxlength="50" value="${meet.publishState == true ? "已发布":"未发布"}"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">投稿人:</label>
        <div class="controls">
            <input type="search" name="name" style="width: 300px;" htmlEscape="false" maxlength="50" value="${meet.name}" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">emial:</label>
        <div class="controls">
            <input type="search" name="email" style="width: 300px;" htmlEscape="false" maxlength="50" value="${meet.email}"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">类型:</label>
        <div class="controls">
            <input type="search" name="playType" style="width: 300px;" htmlEscape="false" maxlength="50" value="${meet.playType eq 0?"录播":meet.playType eq 1?"ppt直播":"视频直播"}" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">预览:</label>
        <div class="controls">
            <input  class="btn btn-primary" style="width: 100px;" onclick="showView()" value="预  览"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">会议简介:</label>
        <div class="controls">
            <textarea  name="contentTw" style="width: 315px;height: 100px;">${meet.info}</textarea>
        </div>
    </div>
    <div class="form-actions">
        <input type="hidden" name="id" value="${meet.id}"/>
        <shiro:hasPermission name="csp:meet:close">
             <input id="btnSubmit" class="btn btn-primary" type="submit"  value="关 闭"/>
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" style="text-align: center">
                    预览
                </h4>
            </div>
            <div class="modal-body" style="height: 400px;">
                <div id="myCarousel" class="carousel slide">
                    <!-- 轮播（Carousel）指标 -->
                    <ol class="carousel-indicators" id="index">
                    </ol>
                    <!-- 轮播（Carousel）项目 -->
                    <div class="carousel-inner" id="courseId">
                    </div>
                    <a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
                    <a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
                </div>
            </div>
            <div class="modal-footer bg-info">
                <button type="button" class="btn green" data-dismiss="modal">返回</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<script>
    $(document).ready(function() {
        initFormValidate();
    });

    function showView(){
        var course = loadCourseInfo();
        if (course == undefined){
            layer.msg("获取会议信息失败");
            return false;
        }
        initSwiper(course);
        $("#myModal").modal("show");
    }

    function initSwiper(course){
        $("#courseId").html("");
        $("#index").html("");
        var num = course.details.length;
        for(var index in course.details){
            var detail = course.details[index];
            var count = parseInt(index) + 1;
            var indexHtml = "";
            var courseHtml = ""
            if(index == 0){
                indexHtml +='<li data-target="#myCarousel" data-slide-to="' + index + '" class="active"></li>';
                courseHtml +='<div class="item active">';
            }else{
                indexHtml += '<li data-target="#myCarousel" data-slide-to="'+ index + '"></li>';
                courseHtml += '<div class="item">';
            }
            if (detail.videoUrl != undefined){//视频
                courseHtml += '<video src="'+detail.videoUrl+'" width="auto" height="400px" controls autobuffer></video>';
            } else {
                courseHtml += '<img src="' + detail.imgUrl+ '">';
            }
            courseHtml += '<div class="carousel-caption">'+ count + '/' + num + '</div></div>';
            $("#courseId").append(courseHtml);
            $("#index").append(indexHtml);
        }
    }

    function loadCourseInfo(){
        var course ;
        $.ajax({
            url:'${ctx}/csp/meet/view/${meet.id}',
            dataType:'json',
            async:false,
            type:'get',
            success:function (data) {
                course = data.data;
            },
            error:function(e, n, a){
                layer.msg(a);
            }
        });
        return course;
    }

    $("#btnSubmit").click(function () {
        $("#status").val(1);
    })
</script>
</body>
</html>

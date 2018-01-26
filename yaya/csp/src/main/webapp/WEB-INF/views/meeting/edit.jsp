<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/17
  Time: 9:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html >
<head>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <title><fmt:message key="page.meeting.title.publish"/> - <fmt:message key="page.common.appName"/></title>

    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/swiper.css">
    <link rel="stylesheet" href="${ctxStatic}/css/audio.css">
    <link rel="stylesheet" href="${ctxStatic}/css/daterangepicker.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">

    <style>
        html,body { background-color:#F7F9FB;}
    </style>

</head>
<body onbeforeunload=" if (success == false) return '' ">
<div id="wrapper">
    <%@include file="../include/header.jsp" %>
    <div class="admin-content bg-gray" >
        <c:set var="isZh" value="${csp_locale eq 'zh_CN' || csp_locale eq 'zh_TW'}"/>
        <div class="page-width clearfix">
            <div class="admin-module clearfix item-radius">
                <div class="row clearfix">
                    <div class="col-lg-5">
                        <div class="upload-ppt-box">
                            <c:choose>
                                <c:when test="${fn:length(course.details) > 0}">
                                    <div class="upload-ppt-area upload-ppt-area-finish logo-watermark">
                                        <img src="${fileBase}${course.details[0].imgUrl}" alt="" id="cover">
                                        <div <c:if test="${empty watermark || watermark.direction == 2}" >class="logo-watermark-item watermark-position-right "</c:if>
                                             <c:if test="${watermark.direction == 0}" >class="logo-watermark-item watermark-position-left "</c:if>
                                             <c:if test="${watermark.direction == 1}" >class="logo-watermark-item watermark-position-left-bottom "</c:if>
                                             <c:if test="${watermark.direction == 3}" >class="logo-watermark-item watermark-position-right-bottom "</c:if>
                                        >
                                            <div class="logo-watermark-main">
                                                <c:if test="${packageId == 1}">
                                                    <span class="logo-watermark-main-text logo-watermark-tips-hook" default-title='${appName}'>${empty watermark? appName:empty watermark.name?appName:watermark.name}</span>
                                                    <div class="logo-watermark-tips watermark-tips-hook"><fmt:message key="page.meeting.watermark.tips.upgrade"/></div>
                                                    <div class="logo-watermark-tips-border watermark-tips-hook"></div>
                                                </c:if>
                                                <c:if test="${packageId != 1}">
                                                    <span class="logo-watermark-main-text" default-title='${appName}'>${empty watermark?appName:empty watermark.name?appName:watermark.name}</span>
                                                    <div class="logo-watermark-edit watermark-edit-hook">
                                                        <c:if test="${packageId == 3 || packageId == 4}">
                                                            <div class="logo-watermark-input">
                                                                <label for="watermark-input">
                                                                    <input type="text" name="" id="watermark-input" placeholder="<fmt:message key='page.meeting.watermark.input.holder'/>" maxlength="18">
                                                                </label>
                                                            </div>
                                                        </c:if>
                                                        <div class="logo-watermark-edit-position">
                                                            <div class="logo-watermark-edit-position-title">
                                                                <fmt:message key="page.meeting.watermark.position"/>
                                                            </div>
                                                            <div class="logo-watermark-edit-position-item">
                                                                <label for="positionTopLeft" class="watermark-radio watermark-radio-topLeft ">
                                                                    <input type="radio" name="watermark" class="none" id="positionTopLeft" value="0" >
                                                                    <span class="icon"></span>
                                                                </label>
                                                                <label for="positionTopRight" class="watermark-radio watermark-radio-topRight">
                                                                    <input type="radio" name="watermark" class="none" id="positionTopRight" value="2">
                                                                    <span class="icon"></span>
                                                                </label>
                                                                <label for="positionBottomLeft" class="watermark-radio watermark-radio-bottomLeft">
                                                                    <input type="radio" name="watermark" class="none" id="positionBottomLeft" value="1">
                                                                    <span class="icon"></span>
                                                                </label>
                                                                <label for="positionBottomRight" class="watermark-radio watermark-radio-bottomRight">
                                                                    <input type="radio" name="watermark" class="none" id="positionBottomRight" value="3">
                                                                    <span class="icon"></span>
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:if>

                                                <div class="logo-watermark-border watermark-edit-hook"></div>
                                                <div class="logo-watermark-outerBorder watermark-edit-hook"></div>
                                            </div>

                                        </div>
                                    </div>

                                </c:when>
                                <c:otherwise>
                                    <div class="upload-ppt-area">
                                        <label for="uploadFile">
                                            <input type="file" name="file" class="none" id="uploadFile">
                                            <p class="img"><img src="${ctxStatic}/images/upload-ppt-area-img.png" alt="" id="cover"></p>
                                            <p id="uploadTipView"><fmt:message key="page.meeting.drag.upload"/></p>
                                        </label>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <div class="upload-main">
                                <div class="metting-progreesItem clearfix t-left none">
                                    <span id="uploadAlt"><fmt:message key="page.meeting.upload.progress"/></span> <span class="color-blue" id="progressS">0%</span>
                                    <p><span class="metting-progreesBar"><i style="width:0%" id="progressI"></i></span></p>

                                </div>
                                <div class="admin-button t-center">
                                    <label for="reUploadFile"><input type="file" name="file" class="none" id="reUploadFile">
                                    <span  class="button min-btn" id="uploadTextView">
                                        <c:choose>
                                            <c:when test="${empty course.details}">
                                                <fmt:message key="page.meeting.upload.doc"/>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:message key="page.meeting.button.reload"/>
                                            </c:otherwise>
                                        </c:choose>

                                    </span>&nbsp;&nbsp;&nbsp;</label>
                                    <a href="${ctx}/mgr/meet/details/${course.id}" class="button color-blue min-btn ${not empty course.details?'':'none'}" id="editBtn"><fmt:message key="page.meeting.button.edit"/></a>

                                </div>
                                <c:if test="${empty course.details}">
                                    <p class="color-gray-02 ${empty course.details ? '' : 'none'}" id="limitView"><fmt:message key="page.meeting.upload.limit100"/></p>
                                </c:if>

                                <span class="cells-block error none" id="detailsError"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<fmt:message key="page.meeting.upload.warn.nofile"/></span>
                            </div>
                        </div>

                        <%--456星评功能--%>
                        <div class="upload-metting-star" id="dataFlash">
                            <div class="upload-metting-star-head" >
                                <div class="fr upload-metting-star-switch">
                                    <div class="weui-cell__ft">
                                        <label for="switchCPStar" class="mui-switch-box">
                                            <input type="checkbox" name="starRateFlag" id="switchCPStar" class="mui-switch none" value="${course.starRateFlag}">
                                            <div class="weui-switch-cp__box"></div>
                                        </label>
                                    </div>
                                    <span class="subject" id="starOpen"><fmt:message key="page.meeting.star.off"/></span>
                                </div>
                                <div class="title" id="starTitle"><fmt:message key="page.meeting.star.rate"/></div>
                            </div>
                            <div class="upload-metting-star-main">
                                <div id="starDiv">
                                <div class="upload-metting-star-row" >
                                    <div class="fr">
                                        <div class="star-box star-max"><div class="star"><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span></div><div class="grade "><fmt:message key="page.meeting.star.rate.none" /></div></div>
                                    </div>
                                    <div class="title"><fmt:message key="page.meeting.multiple.score"/></div>
                                </div>


                                        <c:forEach var="option" items="${options}">
                                    <div class="upload-metting-star-row">
                                        <div class="fr">
                                            <div class="star-box star-max"><div class="star"><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span><span class="null"></span></div><div class="grade "><fmt:message key="page.meeting.star.rate.none" /></div></div>
                                        </div>
                                        <div class="title"><div class="star-remove-button"  optionId="${option.id}"></div> ${option.title}</div>
                                    </div>
                                        </c:forEach>
                                    <script>
                                        $(".star-remove-button").click(function () {
                                            var oid = $(this).attr("optionId");
                                            ajaxGet('${ctx}/mgr/meet/star/del/'+oid, {}, function(data){
                                                if (data.code == 0){
                                                    layer.msg("<fmt:message key="page.meeting.star.rate.del" />");
                                                    $(".star-remove-button[optionId='"+oid+"']").parent().parent().remove();
                                                    $("#limitFive").addClass("none");
                                                    $("#submitOption").show();
                                                    $("#limitInsert").text("<fmt:message key="page.meeting.star.rate.Add" />");
                                                } else {
                                                    layer.msg(data.err);
                                                }
                                            })
                                        })
                                    </script>

                                </div>
                                <div class="upload-metting-star-row star-input-box" id="submitOption" hidden>
                                    <div class="fr">
                                        <div class="star-input-button"><input class="button" type="submit" value="<fmt:message key="page.common.save" />" id="btnStar"><a href="javascript:;" class="close" id="cancelStar"><fmt:message key="page.common.cancel" /></a></div>
                                    </div>
                                    <c:if test="${csp_locale eq 'zh_CN' || csp_locale eq 'zh_TW'}">
                                    <div class="oh">
                                        <div class="star-input"><input type="text" placeholder="<fmt:message key="page.meeting.star.rate.characters.allowed"/>" maxlength="6" id="limitOptionCn" name="title" onkeyup="this.value=this.value.replace(/\s+/g,'')"></div>
                                    </div>
                                    </c:if>
                                    <c:if test="${csp_locale eq 'en_US'}">
                                        <div class="oh">
                                            <div class="star-input"><input type="text" placeholder="<fmt:message key="page.meeting.star.rate.characters.allowed" />" maxlength="12" id="limitOptionUs" onkeyup="this.value=this.value.replace(/\s+/g,'')"></div>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="upload-metting-star-footer" id="insertOption" >
                                <div class="cells-block error none" id="limitFive"><fmt:message key="page.meeting.star.rate.limit.five"/></div>
                                <a href="javascript:;" class="upload-metting-star-add-button cells-block" id="submitCPStar"><span id="limitInsert"><fmt:message key="page.meeting.star.rate.Add" /></span></a>
                            </div>
                        </div>
                    </div>



                    <div class="col-lg-7">
                        <form action="${ctx}/mgr/meet/save" method="post" id="courseForm" name="courseForm" onsubmit="return false;">
                            <input type="hidden" name="course.id" value="${course.id}">
                            <input type="hidden" name="watermark.direction" id="direction" value="2">
                            <input type="hidden" name="watermark.state" id="state" value="1">
                            <input type="hidden" name="watermark.name" id="name" value="${appName}">
                            <input type="hidden" name="starRateFlag" id="starRateFlag" value="${course.starRateFlag}">
                            <script>
                                $(function () {
                                    var isStarCheck = $("#switchCPStar").is(":checked");
                                    var starRateFlag = ${course.starRateFlag};
                                    //var checkOpen = $("#switchCPStar").val();
                                    $("#starRateFlag").val(isStarCheck);
                                    if (isStarCheck){
                                        $("#starRateFlag").val(isStarCheck);
                                    }else{
                                        $("#starRateFlag").val(starRateFlag);
                                    }
                                })
                            </script>
                            <div class="meeting-form-item login-form-item">

                                <label for="courseTitle" class="cells-block pr"><input id="courseTitle" type="text" class="login-formInput" name="course.title" placeholder="<fmt:message key='page.meeting.update.warn.notitle'/>" value="${course.title}"><span class="icon-metting-lock lock-hook" id="lookPwd"><fmt:message key="page.meeting.button.watch.password"/></span></label>
                                <span class="cells-block error none"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<fmt:message key="page.meeting.update.warn.notitle"/></span>

                                <div class="textarea">
                                    <textarea name="course.info" id="courseInfo" cols="30" maxlength="600" rows="10">${course.info}</textarea>
                                    <p class="t-right" id="leftInfoCount">600</p>
                                </div>
                                <span class="cells-block error none"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<fmt:message key="page.meeting.update.warn.noinfo"/></span>

                                <div class="cells-block clearfix meeting-classify meeting-classify-hook">
                                    <span class="subject"><fmt:message key="page.meeting.tab.category"/>&nbsp;&nbsp;|<i id="rootCategory">${not empty courseCategory ? (isZh ? courseCategory.parent.nameCn:courseCategory.parent.nameEn) : (isZh ? rootList[0].nameCn:rootList[0].nameEn)}</i></span><span class="office" id="subCategory">${empty course.category ? (isZh ? subList[0].nameCn : subList[0].nameEn) : course.category}</span>
                                    <input type="hidden" id="courseCategoryId" name="course.categoryId" value="${not empty course.categoryId ? course.categoryId : subList[0].id}">
                                    <input type="hidden" id="courseCategoryName" name="course.category" value="${not empty course.category ? course.category : (isZH ? subList[0].nameCn : subList[0].nameEn)}">
                                </div>
                                <c:if test="${ not empty course.details && packageId > 1}">
                                    <div class="cells-block meeting-watermark">
                                        <span class="subject"><fmt:message key="page.meeting.watermark"/>&nbsp;&nbsp;<em class="muted">|</em>
                                            <c:if test="${packageId == 2}">
                                                <input type="text" class="classify-inputText expert-text"  placeholder="${appName}" id="waterName" value="${empty watermark ? appName:empty watermark.name ?appName:watermark.name}" disabled >
                                            </c:if>
                                            <c:if test="${packageId > 2}">
                                                <input type="text" class="classify-inputText" placeholder="<fmt:message key='page.meeting.watermark.input.holder'/>" id="waterName"  value="${empty watermark ? appName:watermark.name}" maxlength="18">
                                            </c:if>
                                            <div class="weui-cell__ft">
                                                <label for="switchCP" class="mui-switch-box">
                                                    <input type="checkbox" name="" id="switchCP"  class="mui-switch none" <c:if test="${empty watermark || watermark.state == true}">checked</c:if> >
                                                    <div class="weui-switch-cp__box"></div>
                                                </label>
                                            </div>
                                        </span>
                                    </div>

                                </c:if>


                                <input type="hidden" name="course.playType" id="coursePlayType" value="${course.playType}">
                                <div class="meeting-tab clearfix">
                                    <label for="recorded" class="recorded-btn ${course.playType == 0 ? 'cur' : ''}" >
                                        <input id="recorded" type="radio" class="course_play" name="course.playType" value="0" ${course.playType == null || course.playType == 0 ? 'checked':''} ${course != null && course.published ? 'disabled':''}>
                                        <div class="meeting-tab-btn"><i></i><fmt:message key="page.meeting.tab.screen_record"/></div>

                                    </label>
                                    <label for="live" class="live-btn ${course.playType > 0 ? 'cur' : ''}" >
                                        <input id="live" type="radio" class="course_play" name="course.playType" value="1" ${course.playType > 0 ? 'checked':''} ${course != null && course.published ? 'disabled':''}>
                                        <div class="meeting-tab-btn"><i></i><fmt:message key="page.meeting.tab.screen_live"/></div>

                                    </label>
                                    <div class="meeting-tab-main ${course.playType == 0 ? 'none':''}">
                                        <div class="clearfix">
                                            <div class="formrow">
                                                <div class="formControls">
                                                            <span class="time-tj">
                                                                <c:choose>
                                                                    <c:when test="${live.liveState == 0 || live.liveState == 1 || live.liveState == 2 }">
                                                                        <label for="liveTimeSelector">
                                                                    <fmt:message key="page.common.time"/>
                                                                    <input type="text"  readonly class="timedate-input " id="liveStartTime" name="live.startTime" placeholder="<fmt:message key='page.common.time.select.start'/>"
                                                                           <c:if test="${not empty live.startTime}">value="<fmt:formatDate value="${live.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"</c:if>
                                                                    >
                                                                </label>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <label for="liveTimeSelector" id="timeStart">
                                                                    <fmt:message key="page.common.time"/>
                                                                    <input type="text"  readonly class="timedate-input " id="liveStartTime" name="live.startTime" placeholder="<fmt:message key='page.common.time.select.start'/>"
                                                                           <c:if test="${not empty live.startTime}">value="<fmt:formatDate value="${live.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"</c:if>
                                                                    >
                                                                </label>
                                                                    </c:otherwise>
                                                                </c:choose>

                                                            </span>
                                                    <span class="cells-block error none"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<fmt:message key="page.meeting.tips.time_limit"/></span>
                                                    <%--<input type="hidden" name="live.startTime" ${course.playType == '0' ? 'disabled':''} id="liveStartTime" value="<fmt:formatDate value='${live.startTime}' pattern="yyyy/MM/dd HH:mm:ss"/>">--%>
                                                    <%--<input type="hidden" name="live.endTime"  ${course.playType == '0' ? 'disabled':''}  id="liveEndTime" value="<fmt:formatDate value='${live.endTime}' pattern="yyyy/MM/dd HH:mm:ss"/>">--%>
                                                </div>


                                            </div>
                                        </div>
                                        <div class="cells-block clearfix checkbox-box" style="display: block;">
                                                    <span class="checkboxIcon">
                                                        <input type="checkbox" id="popup_checkbox_2" name="openLive" value="1" class="chk_1 chk-hook" ${course.playType == 2 ? 'checked' : ''} >
                                                        <label for="popup_checkbox_2" class="popup_checkbox_hook"><i class="ico checkboxCurrent"></i>&nbsp;&nbsp;<fmt:message key="page.meeting.live.flag"/></label>
                                                    </span>
                                            <div class="checkbox-main">
                                                <p><fmt:message key="page.meeting.live.flux_tips"/></p>
                                                <div class="text"><fmt:message key="page.meeting.live.flux_balance"/><span class="color-blue" id="myFlux">${flux == null ? 0 : flux}</span>G <a href="${ctx}/mgr/user/toFlux" target="_blank" class="cancel-hook"><fmt:message key="page.charge.recharge"/></a></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%--<div class="clearfix cells-block">
                                    <input type="button" class="button login-button buttonBlue last displayInline" value="<fmt:message key='page.common.submit.confirm'/>"><span class="icon-tips-blue"><i></i>内容已更新，请 <a href="index.html" class="color-blue">返回首页</a></span>
                                </div>--%>

                            <%--<span class="cells-block error one"><img src="images/login-error-icon.png" alt="">&nbsp;输入正确密码</span>--%>
                                <input id="saveSubmit"  type="button" class="button login-button buttonBlue last displayInline" value="<fmt:message key='page.common.submit.confirm'/>"><span class="icon-tips-blue" hidden><i></i><fmt:message key="page.meeting.star.rate.update"/> <a href="${ctx}/mgr/meet/list" class="color-blue"><fmt:message key="page.meeting.star.save.return"/></a></span>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@include file="../include/footer.jsp"%>
</div>

<div class="meeting-classify-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong><fmt:message key="page.meeting.category.choose"/></strong>
            <div class="layui-layer-close" onclick="layer.closeAll()"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <div class="metting-classify-popup-main">
                <div class="fl clearfix">

                    <div class="metting-classify-popup-tab hidden-box">
                        <ul id="rootList">
                            <c:choose>
                                <c:when test="${not empty courseCategory.parentId}">
                                    <c:set var="rootId" value="${courseCategory.parentId}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="rootId" value="${rootList[0].id}"/>
                                </c:otherwise>
                            </c:choose>

                            <c:forEach items="${rootList}" var="c" varStatus="status">
                                <li cid="${c.id}"
                                        <c:choose>
                                            <c:when test="${not empty courseCategory}">
                                                <c:if test="${courseCategory.parentId == c.id }">
                                                    class="cur"
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${status.index == 0}">class="cur"</c:if>
                                            </c:otherwise>
                                        </c:choose>
                                ><a href="javascript:void (0);">${isZh ? c.nameCn : c.nameEn}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <div class="oh clearfix">

                    <div class="metting-classify-popup-tab-item hidden-box">
                        <ul id="subList">

                            <c:forEach items="${subList}" var="cc" varStatus="status">
                                <li parentId="${cc.parentId}" categoryId="${cc.id}"
                                        <c:choose>
                                            <c:when test="${not empty courseCategory}">
                                                <c:if test="${cc.id == courseCategory.id}">
                                                    class="cur"
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${status.index == 0}">class="cur"</c:if>
                                            </c:otherwise>
                                        </c:choose>

                                    <c:if test="${cc.parentId != rootId}">style="display: none;" </c:if>
                                ><a href="javascript:void (0);">${isZh ? cc.nameCn : cc.nameEn}</a></li>
                            </c:forEach>

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--弹出 充值-->
<div class="cancel-popup-box" id="fluxTips">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <div class="cancel-popup-main">
                <p><fmt:message key="page.words.charge.tips"/></p>
                <div class="admin-button t-right">
                    <a href="javascript:;" class="button color-blue min-btn layui-layer-close" ><fmt:message key="page.words.charge.problem"/></a>
                    <input type="button" onclick="freshFlux()" class="button buttonBlue item-radius min-btn" value="<fmt:message key='page.words.charge.success'/>">
                </div>
            </div>

        </div>
    </div>
</div>

<div class="cancel-popup-box" id="uploadSuccess">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <div class="cancel-popup-main">
                <p><fmt:message key="page.meeting.upload.success"/></p>
            </div>

        </div>
    </div>
</div>


<%--密码提示  和   星评提示--%>

<!--弹窗密码框-->
<div class="lock-popup-box">
    <div class="layer-hospital-popup lock-popup clearfix">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="lock-popup-main login-form-item pr">
                    <label for="randomNum" class="cells-block pr ">
                        <input id="randomNum" name="password" type="text" class="login-formInput" value="${course.password}" placeholder="<fmt:message key='page.meeting.tips.watch.password.holder'/>" maxlength=4>
                        <span href="javascript:;" class="code" id="btnSendCode"><fmt:message key='page.meeting.button.auto.create'/></span>
                    </label>
                    <span class="cells-block hiht"><fmt:message key='page.meeting.tips.watch.password'/></span>
                    <span class="cells-block error none" id="passwordError"><fmt:message key='page.meeting.tips.watch.password.holder'/></span>
                    <div class="layer-hospital-popup-bottom">
                        <div class="fr">
                            <span class="button min-btn layui-layer-close"><fmt:message key='page.meeting.button.cancel.password'/></span>
                            <!--<input type="submit" class="button buttonBlue min-btn lock-succeed-hook" value="设置密码"> -->
                            <a href="javascript:;" class="button buttonBlue min-btn lock-succeed-hook" value="" id="modifyPwd"><fmt:message key='page.meeting.button.password.sure'/></a>
                        </div>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>

<!--密码框弹出成功-->
<div class="lock-popup-box-succeed">
    <div class="layer-hospital-popup lock-popup clearfix">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="lock-popup-main login-form-item pr">
                    <div class="cells-block t-center modify-success">
                        <p><img src="${ctxStatic}/images/icon-succeed.png" alt=""></p>
                        <p class="hiht"><fmt:message key='page.meeting.tips.password.success'/></p>
                    </div>
                    <div class="cells-block lock-popup-showRandomNum"></div>
                    <span class="cells-block hiht t-center"><fmt:message key='page.meeting.tips.password.delete'/></span>
                    <div class="layer-hospital-popup-bottom clearfix">
                        <div class="fr">
                            <!--<input type="submit" class="button buttonBlue min-btn" value="取消密码">-->
                            <a href="javascript:;" type="submit" class="button buttonBlue min-btn lock-hook" id="delPwd"><fmt:message key='page.meeting.button.password.cancel'/></a>
                        </div>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>

<!--弹出 星评提示1-->
<div class="succeed-01-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="succeed-popup-main">
                    <p><fmt:message key="page.meeting.star.rate.close"/></p>
                    <!--<div class="admin-button t-right">-->
                    <!--<a href="javascript:;" class="button color-blue min-btn layui-layer-close" >取消</a>-->
                    <!--<input type="submit" class="button buttonBlue item-radius min-btn" value="确认">-->
                    <!--</div>-->
                </div>

            </form>
        </div>
    </div>
</div>

<!--弹出 星评提示2 重新开启星评，将清空历史评分-->
<div class="succeed-02-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="succeed-popup-main">
                    <p><fmt:message key="page.meeting.star.start.no.rate"/></p>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="succeed-03-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="succeed-popup-main">
                    <p><fmt:message key="page.meeting.star.clean.point"/></p>
                </div>
            </form>
        </div>
    </div>
</div>



<script src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script src="${ctxStatic}/js/moment.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
<script src="${ctxStatic}/js/daterangepicker.js"></script>
<script src="${ctxStatic}/js/ajaxUtils.js"></script>

<script>
    const file_size_limit = 100*1024*1024;

    var uploadOver = false;

    var success = false;

    $("#uploadFile, #reUploadFile, #reUploadFile2").change(function(){
        var id = $(this).attr("id");
        uploadFile(document.getElementById(id));
    });

    function freshFlux(){
        ajaxGet("${ctx}/mgr/meet/flux/fresh", {}, function(data){
            $("#myFlux").text(data.data);
            layer.closeAll();
        });
    }

    function fleshPage(data){
        detailsLength = 1;
        $(".metting-progreesItem").addClass("none");
        $("#editBtn").removeClass("none");

        $("#limitView").addClass("none");
        var reloadText = '<fmt:message key="page.meeting.button.reload"/>';
        $("#uploadTextView").text(reloadText);
        $("#courseTitle").val(data.title);
        $("#cover").attr("src", data.coverUrl);
        $(".upload-ppt-area").addClass("upload-ppt-area upload-ppt-area-finish logo-watermark");
        $("#uploadTipView").addClass("none");

        $("#uploadFile").replaceWith('<input type="file" id="uploadFile" name="file" class="none">');
        $("#reUploadFile").replaceWith('<input type="file" id="reUploadFile" name="file" class="none">');
        $("#uploadFile, #reUploadFile").change(function(){
            var id = $(this).attr("id");
            uploadFile(document.getElementById(id));
        });


        layer.open({
            type: 1,
            area: ['300px', '260px'],
            fix: false, //不固定
            title:false,
            closeBtn:0,
            anim:5,
            btn: ["<fmt:message key='page.button.sure'/>"],
            content: $('#uploadSuccess'),
            success:function(){

            },
            yes:function(){
                layer.closeAll();
            },
            cancel :function(){

            },
        });
    }

    function uploadFile(f){
        var fSize = fileSize(f);
        if (fSize > file_size_limit){
            layer.msg("<fmt:message key='page.meeting.upload.limit100'/>");
            return false;
        }
        var fileName = $(f).val().toLowerCase();
        if (!fileName.endWith(".ppt") && !fileName.endWith(".pptx") && !fileName.endWith(".pdf")){
            layer.msg("<fmt:message key='page.meeting.warn.format'/>");
            return false;
        }
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        showUploadProgress();
        $.ajaxFileUpload({
            url: "${ctx}/mgr/meet/upload"+"?courseId=${course.id}", //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: f.id, //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data)  //服务器成功响应处理函数
            {
                layer.close(index);
                if (data.code == 0){
                    //回调函数传回传完之后的URL地址
                    fleshPage(data.data);
                } else {
                    uploadOver = true;
                    layer.msg(data.err);
                }
            },
            error:function(data, status, e){
                uploadOver = true;
                alert(e);
                layer.close(index);
            }
        });
    }

    function showUploadProgress(){
        $(".metting-progreesItem").removeClass("none");
        $.get('${ctx}/mgr/meet/upload/progress', {}, function (data) {
            $("#progressS").text(data.data.progress);
            $("#progressI").css("width", data.data.progress);
            if (data.data.progress.indexOf("100") != -1){
                $.get('${ctx}/mgr/meet/upload/clear', {}, function (data1) {
                }, 'json');
                showConvertProgress();
                $("#progressS").text("0%");
                $("#progressI").css("width", "0%");
            } else {
                if (!uploadOver){
                    setTimeout(showUploadProgress, 200);
                }
            }
        }, 'json');
    }


    function showConvertProgress(){
        $.get('${ctx}/mgr/meet/convert/progress', {}, function (data) {
            console.log("convert progress = "+data.data.progress);
            $("#uploadAlt").text("<fmt:message key='page.meeting.convert.progress'/>");
            $("#progressS").text(data.data.progress);
            $("#progressI").css("width", data.data.progress);
            if (data.data.progress.indexOf("100") != -1){
                $.get('${ctx}/mgr/meet/convert/clear', {}, function (data1) {
                }, 'json');
            } else {
                if(!uploadOver){
                    setTimeout(showConvertProgress, 500);
                }
            }
        }, 'json');
    }

    var detailsLength = "${fn:length(course.details)}";
    $(function(){

        //拖动上传
        var oFileSpan = $(".upload-ppt-area");					//选择文件框

        //是否需要显示水印
        var needShow = '${watermark.state}';
        if(needShow == undefined || needShow == '' || needShow == "true"){
            $('.logo-watermark-item').show();
        }else{
            $('.logo-watermark-item').hide();
        }





        //拖拽外部文件，进入目标元素触发
        oFileSpan.on("dragenter",function(){
            $(this).css("border-color","#167AFE");
        });

        //拖拽外部文件，进入目标、离开目标之间，连续触发
        oFileSpan.on("dragover",function(){
            return false;
        });

        //拖拽外部文件，离开目标元素触发
        oFileSpan.on("dragleave",function(){
            $(this).css("border-color","#EDF3F9");
        });

        //拖拽外部文件，在目标元素上释放鼠标触发
        oFileSpan.on("drop",function(ev){
            var fs = ev.originalEvent.dataTransfer.files[0];
            uploadByDrag(fs);

            console.log(fs);
            $(this).css("border-color","#167AFE");
            return false;
        });

        function uploadByDrag(f){

            if (!f.name.endWith(".ppt") && !f.name.endWith(".pptx") && !f.name.endWith(".pdf")){
                layer.msg("<fmt:message key='page.meeting.warn.format'/>");
                return false;
            }

            var filesize = Math.floor((f.size)/1024);
            if(filesize>file_size_limit){
                layer.msg("<fmt:message key='page.meeting.upload.limit100'/>");
                return false;
            }

            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            //上传

            xhr = new XMLHttpRequest();
            xhr.open("post", "${ctx}/mgr/meet/upload?courseId=${course.id}", true);
            xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            var fd = new FormData();
            fd.append('file', f);

            xhr.send(fd);
            var data = xhr.response;
            xhr.onloadend = function(data){
                fleshPage(data.data);
            }

            showUploadProgress();
        }

        showInfoLeftCount();

        function showInfoLeftCount(){
            var usedLen = $("#courseInfo").val().length;
            $("#leftInfoCount").text(600 - usedLen);
        }

        $("#courseInfo").keyup(function(){
            showInfoLeftCount();
        });

        $("input[name='course.playType'][type='radio']").click(function(){
            var playType = $(this).val();
            $("input[name='course.playType']").removeAttr("checked");
            $(this).attr("checked", "true");
            $("#coursePlayType").val(playType);
            if (playType == 0){
                $("#coursePlayType").val();
                $("#liveStartTime").attr("disabled", "true");
                /*$("#liveEndTime").attr("disabled", "true");*/
                $(".meeting-tab-main").addClass("none");
            } else {
                $("#liveStartTime").removeAttr("disabled");
                /*$("#liveEndTime").removeAttr("disabled");*/
                $(".meeting-tab-main").removeClass("none");
            }
            $(this).parent().siblings().removeClass("cur");
            $(this).parent().addClass("cur");



        });

        $('.cancel-hook').on('click',function(){
            layer.open({
                type: 1,
                area: ['560px', '250px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                anim:5,
                content: $('#fluxTips'),
                success:function(){

                },
                cancel :function(){

                },
            });
        });


        $(".login-button").click(function(){
            var $courseTitle = $("#courseTitle");
            var $courseInfo = $("#courseInfo");
            var $timedate = $(".timedate-input");

            if (detailsLength == 0){
                $("#detailsError").removeClass("none");
                return;
            }
            if ($.trim($courseTitle.val()) == ''){
                $courseTitle.focus();
                $courseTitle.parent().next(".error").removeClass("none");
                return;
            } else {
                $courseTitle.parent().next(".error").addClass("none");
            }

            if ($.trim($courseInfo.val()) == ''){
                $courseInfo.focus();
                $courseInfo.parent().next(".error").removeClass("none");
                return;
            } else {
                $courseInfo.parent().next(".error").addClass("none");
            }

            var playType =  $("#coursePlayType").val();

            if(playType == 2){//视频直播需检测流量
                var remainFlux = 0;
                ajaxSyncGet("${ctx}/mgr/meet/flux/fresh", {}, function(data){
                    remainFlux = data.data;
                });
                if(remainFlux < 10){
                    layer.msg("<fmt:message key='page.meeting.flux.minlimit'/>");
                    return ;
                }
            }

            if (playType >= 1){
                var startTime = $("#liveStartTime").val();
                /*var endTime = $("#liveEndTime").val();*/
                //var dateBeforeNow = new Date(Date.parse(startTime)).getTime() <= new Date().getTime();
                if(startTime == ""  || startTime == null){
                    $timedate.focus();
                    $timedate.parent().parent().next(".error").removeClass("none");
                    return;
                } else {
                    $timedate.parent().parent().next(".error").addClass("none");
                }
            } else {
                $timedate.parent().parent().next(".error").addClass("none");
            }
            //设置水印的位置,状态
            if(${packageId == 1}){  //标准版
                if(${not empty watermark}){
                    var watermark = '${watermark}';
                    $("#direction").val(watermark.direction);
                    $("#name").val(watermark.name);
                    $("#state").val(watermark.state);
                }else{
                    $("#direction").val(2);
                    $("#name").val("${appName}");
                    $("#state").val(1);
                }
            }else{
                var direction = $('input:radio[name="watermark"]:checked').val();
                $("#direction").val(direction);
                var isCheck = $("#switchCP").is(":checked");
                $("#state").val(isCheck ? 1 : 0);
                if(${packageId == 2}){
                    if(${not empty watermark}){
                        $("#name").val('${watermark.name}');
                    }else{
                        $("#name").val("${appName}");
                    }
                }else{
                    $("#name").val($("#waterName").val() == '' ? "${appName}":$("#waterName").val());
                }
            }
            $("#courseForm").submit();
        });

        $("#rootList>li").click(function(){
            $("#rootList>li").removeClass("cur");
            $(this).addClass("cur");
            $("#rootCategory").text($(this).find("a").text());
            $("#subList>li").hide();
            $("#subList>li[parentId='"+$(this).attr("cid")+"']").show();
        });

        $("#subList>li").click(function(){
            $("#subList>li").removeClass("cur");
            var categoryId = $(this).attr("categoryId");
            var category = $(this).find("a").text();
            $(this).addClass("cur");
            $("#subCategory").text(category);

            $("#courseCategoryId").val(categoryId);
            $("#courseCategoryName").val(category);
            layer.closeAll();
        });



        $('.meeting-classify-hook').on('click',function(){
            layer.open({
                type: 1,
                area: ['732px', '90%'],
                fix: false, //不固定
                title:false,
                anim:5,
                closeBtn:0,
                content: $('.meeting-classify-popup-box'),
                success:function(layero){

                    //弹出层高度 - （标题 + 标题到内容的间距 + 弹出层的内边距)
                    var popupHeight = layero.height() - 85;
                    //触发滚动条控件
                    $('.hidden-box').perfectScrollbar();
                    //设置两栏的高度不超过弹出层高度
                    layero.find('.metting-classify-popup-tab').height(popupHeight * 0.8);
                    layero.find('.metting-classify-popup-tab-item').height(popupHeight * 0.8);

                },
                cancel :function(){

                },
            });
        });

        //随机数函数（根据自己的思路来，我这里只是简单呈现效果） onclick="randomNum()"
        $("#btnSendCode").click(function () {
            var num = "";
            for(var i=0;i<4;i++){
                num += Math.floor(Math.random()*10)
            }
            $('#randomNum').val(num);

            console.log($('#randomNum').val());
            $('.lock-popup-showRandomNum').text(num)
        })


        $("#lookPwd").click(function () {
            $("#passwordError").addClass("none");
            if($("input[name='password']").val() == ''){
                //弹出观看密码
                openPasswordView();
            } else {//已经存在密码的情况
                $(".lock-popup-showRandomNum").text($("input[name='password']").val());
                openCancelPasswordView();
            }
        })

        function openCancelPasswordView(){
            //弹出观看密码成功
            layer.open({
                type: 1,
                area: ['609px', '320px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                shadeClose:true,
                content: $('.lock-popup-box-succeed'),
                success:function(){
                    layer.close(layer.index-1);
                    $(".modify-success").addClass("none");
                    //清空原来已设置的密码
                    $('#randomNum').val('');
                },
                cancel :function(){
                    layer.closeAll();
                },
            });

        }

        function openPasswordView(){
            layer.open({
                type: 1,
                area: ['609px', '328px'],
                fix: false, //不固定
                title:false,
                anim:5,
                isOutAnim: false,
                closeBtn:0,
                shadeClose:true,
                content: $('.lock-popup-box'),
                success:function(){
                    layer.close(layer.index-1);
                },
                cancel :function(){
                    $("#passwordError").addClass("none");
                }
            });
        }

        $("#modifyPwd").click(function () {
            var pwd = $.trim($("#randomNum").val());
            if (pwd == '') {
                $("#passwordError").removeClass("none");
                return;
            }
            ajaxGet('${ctx}/mgr/meet/password/modify/'+${course.id}, {"password":pwd}, function(data){
                if (data.code == 0){
                    $("#randomNum").find(".lock").removeClass("none");
                    $("#randomNum").attr("name", pwd);
                    $(".lock-popup-showRandomNum").text(pwd);
                    openConfirmPasswordView();
                } else {
                    layer.msg(data.err);
                }
            });
        })


        function openConfirmPasswordView(){
            //弹出观看密码成功
                layer.open({
                    type: 1,
                    area: ['609px', '420px'],
                    fix: false, //不固定
                    title:false,
                    closeBtn:0,
                    shadeClose:true,
                    content: $('.lock-popup-box-succeed'),
                    success:function(){
                        layer.close(layer.index-1);
                        $(".modify-success").removeClass("none");
                        //清空原来已设置的密码
                        $('#randomNum').val('');
                    },
                    cancel :function(){
                        layer.closeAll();
                    },
                });
        }

        $("#delPwd").click(function () {
            ajaxGet('${ctx}/mgr/meet/password/del/'+${course.id}, {}, function(data){
                if (data.code == 0){
                    $("#randomNum").find(".lock").addClass("none");
                    $("#randomNum").attr("pwd", "");
                    openPasswordView();
                } else {
                    layer.msg(data.err);
                }
            })
        })

        /*123 星评功能*/

        $(function(){
            $("#switchCPStar").click(function () {
                var isStarCheck = $("#switchCPStar").is(":checked");
                $("#starRateFlag").val(isStarCheck);
                var starFlag = ${course.starRateFlag};
                var size = ${size}
                var published = ${course.published}
                var count = ${count}
                if(published == false || starFlag == false){
                    if (isStarCheck){
                        starTips();
                    }else {
                        $("#starOpen").text("<fmt:message key="page.meeting.star.off"/>");
                        $("#starTitle").html("<fmt:message key="page.meeting.star.rate"/>");
                        $("#insertOption").show();
                        $(".star-remove-button").removeClass("none");
                    }
                }else {
                    if ((size == 0||size >0 ) && isStarCheck && starFlag==true){
                        if(count>0){
                            delTips();
                        }else{
                            $("#switchCPStar").prop("checked", true);
                            $("#starOpen").text("<fmt:message key="page.meeting.open.star"/>");
                            $("#insertOption").hide();
                            $("#submitOption").hide();
                            $(".half").removeClass("half").addClass("null");
                            $(".full").removeClass("half").addClass("null");
                            $(".star-remove-button").addClass("none");
                            $(".grade").html("<fmt:message key="page.meeting.star.rate.none"/>")
                        }
                    }else if ((size == 0||size >0 ) && starFlag==false && isStarCheck){
                        if (count>0){
                            starTips();
                        }else{
                            $("#switchCPStar").prop("checked", true);
                            $("#starOpen").text("<fmt:message key="page.meeting.open.star"/>");
                            $("#starTitle").html("<fmt:message key="page.meeting.star.rate.attend"/>" +0+"<fmt:message key="page.meeting.tips.score.user.unit"/>");
                            $("#insertOption").hide();
                            var isStarCheck = $("#switchCPStar").is(":checked");
                            $("#switchCPStar").val(isStarCheck);
                            var starRateFlag=$("#switchCPStar").val();
                            $("#starRateFlag").val(starRateFlag)
                            $("#submitOption").hide();
                            $(".star-remove-button").addClass("none");
                        }

                    }else {
                        if (count>0){
                            closeTips();
                        }else{
                            $("#switchCPStar").removeAttr("checked");
                            $("#starOpen").text("<fmt:message key="page.meeting.star.off"/>");
                            if (${size>0}){
                                $(".star-remove-button").removeClass("none");
                            }
                            $("#insertOption").show();
                        }

                    }
                }
            })
        })

        $(function () {
            var flag = ${course.starRateFlag};
            var state = ${course.published};
            if(state == true){
                $.ajax({
                    url: "${ctx}/mgr/meet/course_info/" + ${course.id},
                    dataType: 'json', //返回数据类型 icon-tips-blue
                    type: 'GET', //请求类型
                    success: function (data) {
                        if (data.code == 0) {
                            var detailList = data.data.detailList;
                            var score =data.data.multipleResult.scoreCount;
                            if(flag == true){
                                $("#switchCPStar").attr("checked",true);
                                $("#starOpen").text("<fmt:message key="page.meeting.open.star"/>");
                                $("#starTitle").html("<fmt:message key="page.meeting.star.rate.attend"/>" +score+"<fmt:message key="page.meeting.tips.score.user.unit"/>");
                                $("#insertOption").hide();
                                $("#submitOption").hide();
                                $(".star-remove-button").addClass("none");
                            }else{
                                $("#starOpen").text("<fmt:message key="page.meeting.star.off"/>");
                                $("#starTitle").html("<fmt:message key="page.meeting.star.rate"/>");
                                $("#insertOption").show();
                                $(".star-remove-button").removeClass("none");
                            }
                            var avgScore =data.data.multipleResult.avgScore;
                            var optionId = data.data.multipleResult.optionId;
                            var html = '<div class="upload-metting-star-row" >\n' +
                                '                                    <div class="fr">\n' +
                                '                                        <div class="star-box star-max"><div class="star">'
                            html = initStar(avgScore,html);
                            html +='</div><div class="grade ">' +avgScore+'<fmt:message key="page.meeting.tips.score.unit"/>'+'</div></div></div><div class="title"><fmt:message key="page.meeting.multiple.score"/></div></div>'
                            if (detailList != null) {
                                //html += '<div class="star-list hidden-box clearfix">';
                                for (var j = 0; j < detailList.length; j++) {
                                    html += '<div class="upload-metting-star-row">\n' +
                                        '                                        <div class="fr">\n' +
                                        '                                        <div class="star-box star-max">\n' +
                                        '                                            <div class="star">';
                                    html = initStar(detailList[j].avgScore, html);
                                    html += '</div><div class="grade">' + detailList[j].avgScore + '<fmt:message key="page.meeting.tips.score.unit"/>'+'</div></div>';
                                    html += '</div><div class="title"><span class="star-remove-button" optionId="'+detailList[j].id+'"></span>' + detailList[j].title + '</div></div>';
                                }
                                html += '</div>';
                                $("#starDiv").html(html);
                                if (${course.starRateFlag ==true}){
                                    $(".star-remove-button").addClass("none");
                                }else{
                                    $(".star-remove-button").removeClass("none");
                                }
                                if(score == 0){
                                    $(".grade").html("<fmt:message key="page.meeting.star.rate.none"/>")
                                }
                                $(".star-remove-button").click(function () {
                                    var oid = $(this).attr("optionId");
                                    ajaxGet('${ctx}/mgr/meet/star/del/'+oid, {}, function(data){
                                        if (data.code == 0){
                                            layer.msg("<fmt:message key="page.meeting.star.rate.del"/>");
                                            $(".star-remove-button[optionId='"+oid+"']").parent().parent().remove();
                                            $("#limitFive").addClass("none");
                                            $("#submitOption").show();
                                            $("#limitInsert").text("<fmt:message key="page.meeting.star.rate.Add"/>");
                                        } else {
                                            layer.msg(data.err);
                                        }
                                    })
                                })

                            }
                        } else {
                            layer.msg("<fmt:message key="page.meeting.star.fail"/>");
                        }
                    }
                });


            }

            function initStar(score, html) {
                var i = score % 1 == 0 ? 1 : 0;
                for (i; i <= score; i++) {
                    if (score - i >= 1 || score - i == 0) html += '<span class="full"></span>';
                    if (0 < score - i && score - i < 1) {
                        html += '<span class="half"></span>';
                    }
                }
                for (score % 1 == 0 ? i : i++; i <= 5; i++) {
                    html += '<span class="null"></span>';
                }
                return html;
            }
        })


        $("#submitCPStar").click(function () {
            var length = $(".grade").length
            if (length >5){
                $("#submitOption").hide();
            }else{
                $("#submitOption").show();
            }
        })

        $("#cancelStar").click(function () {
            $("#submitOption").hide();
        })

        $("#btnStar").click(function () {
            var starOption;
             if (${csp_locale eq 'zh_CN' || csp_locale eq 'zh_TW'}){
                 starOption =  $("#limitOptionCn").val();
                 $("#limitOptionCn").val("")
             }else{
                 starOption =  $("#limitOptionUs").val();
                 $("#limitOptionUs").val("");
             }
            var starLength = starOption.length;
            //var reg = /^[\u4e00-\u9fa5^%&',;=?$\x22]{1,6}$|^[a-zA-Z^%&',;=?$\x22]{1,12}$/;
           // var  regStarOption= reg.test(starOption);
            //regStarOption = spaceReg.test(starOption);
            var optionId ;
            if (starOption != "" ){
                ajaxGet('${ctx}/mgr/meet/star/save/'+${course.id}, {"title":starOption}, function(data){
                    console.log(data)
                    if (data.code == 0){
                        $("#submitOption").hide();
                        $('<div class="upload-metting-star-row">' +
                            '<div class="fr"><div class="star-box star-max"><div class="star"><span class="null">' +
                            '</span><span class="null"></span><span class="null"></span><span class="null">' +
                            '</span><span class="null"></span></div><div class="grade ">'+'<fmt:message key="page.meeting.star.rate.none"/>'+'</div></div></div><div class="title"><span class="star-remove-button"></span> '+data.data.title+'</div></div>').insertBefore($("#submitOption"));
                            optionId = data.data.id;
                        $(".star-remove-button").click(function () {
                            ajaxGet('${ctx}/mgr/meet/star/del/'+optionId, {}, function(data){
                                if (data.code == 0){
                                    layer.msg("<fmt:message key="page.meeting.star.rate.del"/>");
                                } else {
                                    layer.msg(data.err);
                                }
                            })
                            $(this).parent().parent().remove();
                            $("#limitFive").addClass("none");
                            $("#submitOption").show();
                            $("#limitInsert").text("<fmt:message key="page.meeting.star.rate.Add"/>");
                        })
                    } else {
                        layer.msg(data.err);
                    }
                })


            }else{
                layer.msg("<fmt:message key="page.meeting.star.rate.not.none"/>");
            }

            var length = $(".grade").length;

            if((length > 5 || length == 5 )&& starOption != ""){
                $("#limitFive").removeClass("none");
                $("#submitOption").hide();
                $("#limitInsert").text("<fmt:message key="page.meeting.star.allow"/>");
            }else{
                $("#submitOption").show();
                $("#limitFive").addClass("none");
                $("#limitInsert").text("<fmt:message key="page.meeting.star.rate.Add"/>");
            }
        })




        //星标提示语1
        //投稿
            function closeTips() {
                layer.open({
                    type: 1,
                    area: ['440px', '280px'],
                    fix: false, //不固定
                    title:false,
                    closeBtn:0,
                    shadeClose:true,
                    btn: ["<fmt:message key="page.common.confirm"/>","<fmt:message key="page.common.cancel"/>"],
                    content: $('.succeed-01-popup-box'),
                    yes:function(){
                        $("#switchCPStar").removeAttr("checked");
                        $("#starOpen").text("<fmt:message key="page.meeting.star.off"/>");
                        if (${size>0}){
                            $(".star-remove-button").removeClass("none");
                        }
                        $("#insertOption").show();
                        $("#submitOption").hide();

                        layer.closeAll();
                    },
                    btn2:function () {
                        $("#switchCPStar").prop("checked", true);
                        $("#starOpen").text("<fmt:message key="page.meeting.open.star"/>");
                        $("#insertOption").hide();
                        layer.closeAll();
                    },
                    cancel :function(){
                        $("#switchCPStar").prop("checked", true);
                        $("#insertOption").hide();
                        $("#starOpen").text("<fmt:message key="page.meeting.open.star"/>");
                    },
                });
            }

            //succeed-03-popup-box

        function delTips() {
            layer.open({
                type: 1,
                area: ['440px', '250px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                shadeClose:true,
                btn: ["<fmt:message key="page.common.confirm"/>","<fmt:message key="page.common.cancel"/>"],
                content: $('.succeed-03-popup-box'),
                yes:function(){
                    $("#switchCPStar").prop("checked", true);
                    $("#starOpen").text("<fmt:message key="page.meeting.open.star"/>");
                    if (${size>0}){
                        $(".star-remove-button").addClass("none");
                    }
                   $.ajax({
                        type:'GET',
                        url:'${ctx}/mgr/meet/starDetail/del/'+${course.id},
                        dataType:'json',
                        async: false,
                        success:function(data){
                            if(data.code==0){
                                $("#switchCPStar").prop("checked", true);
                                $("#starOpen").text("<fmt:message key="page.meeting.open.star"/>");
                                $("#insertOption").hide();
                                $("#submitOption").hide();
                                $(".half").removeClass("half").addClass("null");
                                $(".full").removeClass("half").addClass("null");
                                $(".grade").html("<fmt:message key="page.meeting.star.rate.none"/>")
                            }else{
                                layer.msg("<fmt:message key="page.meeting.star.on.fail"/>");
                            }
                        },
                    });
                    layer.closeAll();
                },
                btn2 :function(){
                    $("#switchCPStar").removeAttr("checked");
                    $("#starOpen").text("<fmt:message key="page.meeting.star.off"/>");
                    if (${size>0}){
                        $(".star-remove-button").removeClass("none");
                    }
                    $("#insertOption").show();
                    $("#submitOption").hide();
                    layer.closeAll();
                },
                cancel :function(){
                    $("#switchCPStar").removeAttr("checked");
                    $("#starOpen").text("<fmt:message key="page.meeting.star.off"/>");
                    if (${size>0}){
                        $(".star-remove-button").removeClass("none");
                    }
                    $("#insertOption").show();
                    $("#submitOption").hide();
                    layer.closeAll();
                },
            });
        }


        /*创建会议的提示语  $('.succeed-02-hook').on('click',function(){*/
        function starTips() {
            layer.open({
                type: 1,
                area: ['440px', '270px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                shadeClose:true,
                btn: ["<fmt:message key="page.common.confirm"/>","<fmt:message key="page.common.cancel"/>"],
                content: $('.succeed-02-popup-box'),
                yes: function(index, layero){
                                //var score =data.data.multipleResult.scoreCount;
                                $("#switchCPStar").prop("checked", true);
                                $("#starOpen").text("<fmt:message key="page.meeting.open.star"/>");
                                $("#starTitle").html("<fmt:message key="page.meeting.star.rate.attend"/>" +0+"<fmt:message key="page.meeting.tips.score.user.unit"/>");
                                $("#insertOption").hide();
                                var isStarCheck = $("#switchCPStar").is(":checked");
                                $("#switchCPStar").val(isStarCheck);
                                var starRateFlag=$("#switchCPStar").val();
                                $("#starRateFlag").val(starRateFlag)
                                $("#submitOption").hide();
                                $(".star-remove-button").addClass("none");
                                layer.closeAll()
                },
                btn2: function(index, layero){
                    $("#switchCPStar").prop("checked", false);
                    $("#starOpen").text("<fmt:message key="page.meeting.star.off"/>");
                    $("#starTitle").html("<fmt:message key="page.meeting.star.rate"/>");
                    $("#insertOption").show();
                    var isStarCheck = $("#switchCPStar").is(":checked");
                    $("#switchCPStar").val(isStarCheck);
                    var starRateFlag=$("#switchCPStar").val();
                    $("#starRateFlag").val(starRateFlag)
                    $("#submitOption").hide();
                    $(".star-remove-button").removeClass("none");
                    layer.close(layer.index-1);
                },
                cancel: function(index,layero){ //按右上角“X”按钮
                    $("#switchCPStar").prop("checked", false);
                    $("#starOpen").text("<fmt:message key="page.meeting.star.off"/>");
                    $("#starTitle").html("<fmt:message key="page.meeting.star.rate"/>");
                    $("#submitCPStar").show();
                    var isStarCheck = $("#switchCPStar").is(":checked");
                    $("#switchCPStar").val(isStarCheck);
                    var starRateFlag=$("#switchCPStar").val();
                    $("#submitOption").hide();
                    $(".star-remove-button").removeClass("none");
                    layer.close(layer.index-1);
                },
            });
        }






        var beginTimeTake;
        /*123*/
        $('#timeStart').daterangepicker({
            singleDatePicker: true,
            showDropDowns: true,
            autoUpdateInput: false,
            timePicker24Hour : true,
            timePicker : true,
            minDate:new Date(),
            "locale": {
                format: 'YYYY-MM-DD HH:mm:ss',
                applyLabel: "<fmt:message key='page.meeting.time.start.plugin.true'/>",
                cancelLabel: "<fmt:message key='page.meeting.time.start.plugin.false'/>",
                weekLabel: 'W',
                customRangeLabel: "<fmt:message key='page.meeting.time.start.plugin.select'/>",
                daysOfWeek:["<fmt:message key='page.meeting.time.start.plugin.sun'/>",
                                "<fmt:message key='page.meeting.time.start.plugin.mon'/>",
                                "<fmt:message key='page.meeting.time.start.plugin.tues'/>",
                                "<fmt:message key='page.meeting.time.start.plugin.wed'/>",
                                "<fmt:message key='page.meeting.time.start.plugin.thur'/>",
                                "<fmt:message key='page.meeting.time.start.plugin.fri'/>",
                                "<fmt:message key='page.meeting.time.start.plugin.sat'/>"],
                monthNames: ["01","02","03","04","05","06","07","08","09","10","11","12"],
            }
        },function(start,end,label) {
            beginTimeTake = start;
            console.log(this.startDate.format(this.locale.format));
            console.log(1);
            if(!this.startDate){
                this.element.find('.timedate-input').val('');
            }else{
               this.element.find('.timedate-input').val(this.startDate.format(this.locale.format));
               $("#liveStartTime").val(this.startDate.format(this.locale.format))
            }
        });
        //上传Hover 提示
        $("#timeStart").mouseenter(function(){
            layer.tips("<fmt:message key='page.meeting.time.start.tips'/>", $(this), {
                tips: [2,'#8abcfe'],
                time:2000
            });
        });

        if("${course != null && course.published && course.playType > 0}" == "true") {
            showLiveMessage();
        }

        if("${course.playType == 2}" == "true"){
            $(".checkbox-main").show();
        }
        function showLiveMessage(){
            if($(".chk-hook").is(":checked")) {
                $("#coursePlayType").val("2");
                $(".checkbox-main").show();
            } else {
                $("#coursePlayType").val("1");
                $(".chk-hook").removeAttr("checked");
                $(".checkbox-main").hide();
            }

        }

        $(".chk-hook").change(function(){
            showLiveMessage();
        });

        var watermarkItem = $('.logo-watermark-item');
        var watermarkItemTitle = watermarkItem.find('.logo-watermark-main-text');
        var watermarkItemEditBr = watermarkItem.find('.watermark-edit-hook');
        var watermarkRadio = watermarkItem.find('.watermark-radio');
        var watermarkIsShow = false;
        var defaultTitle = "${appName}"

        $('.logo-watermark-tips-hook').hover(function(){
            $('.watermark-tips-hook').show();
        },function(){
            $('.watermark-tips-hook').hide();
        });


        if(${packageId != 1}){
            //水印编辑区显示
            watermarkItemTitle.on('click',function(){
                if(watermarkIsShow == false){
                    watermarkItemEditBr.show();
                    watermarkIsShow = true;
                } else if(watermarkIsShow == true) {
                    watermarkItemEditBr.hide();
                    watermarkIsShow = false;
                }
            });
        }

        //水印位置
        watermarkRadio.off('click').on('click',function(){
            if($(this).hasClass('watermark-radio-topLeft')){
                $(this).parents('.logo-watermark-item').addClass('watermark-position-left').removeClass('watermark-position-right watermark-position-right-bottom watermark-position-left-bottom');
            } else if ($(this).hasClass('watermark-radio-topRight')) {
                $(this).parents('.logo-watermark-item').addClass('watermark-position-right').removeClass('watermark-position-left watermark-position-right-bottom watermark-position-left-bottom');
            } else if ($(this).hasClass('watermark-radio-bottomLeft')) {
                $(this).parents('.logo-watermark-item').addClass('watermark-position-left-bottom').removeClass('watermark-position-right watermark-position-left watermark-position-right-bottom');
            } else if ($(this).hasClass('watermark-radio-bottomRight')) {
                $(this).parents('.logo-watermark-item').addClass('watermark-position-right-bottom').removeClass('watermark-position-right watermark-position-left watermark-position-left-bottom');
            }
            $(this).addClass('radio-on').siblings().removeClass('radio-on');
        });

        //水印里的输入框
        $('.logo-watermark-input').find('input').on('change',function(){
            if($(this).val() == "" || $(this).val() == null) {
                watermarkItemTitle.text(defaultTitle);
                $('.classify-inputText').val("");
            } else {
                watermarkItemTitle.text($(this).val());
                $('.classify-inputText').val($(this).val());
            }
        });

        //表单的输入框
        $('.classify-inputText').on('change',function(){
            if($(this).val() == "" || $(this).val() == null) {
                watermarkItemTitle.text(defaultTitle);
                $('.logo-watermark-input').find('input').val("");
            } else {
                watermarkItemTitle.text($(this).val());
                $('.logo-watermark-input').find('input').val($(this).val());
            }
        });
        $('#switchCP').on('click',function(){
            if($(this).is(':checked')){
                $('.logo-watermark-item').show();
            } else {
                $('.logo-watermark-item').hide();
            }
        });

        if(${not empty watermark}){
            var direction = "${watermark.direction}";
            if(direction == 0){
                $("#positionTopLeft").attr("checked","checked");
                $("#positionTopLeft").parent().attr("class","watermark-radio watermark-radio-topLeft radio-on ");
            }else if(direction == 1){
                $("#positionBottomLeft").attr("checked","checked");
                $("#positionBottomLeft").parent().attr("class","watermark-radio watermark-radio-bottomLeft radio-on ");
            }else if(direction == 2){
                $("#positionTopRight").attr("checked","checked");
                $("#positionTopRight").parent().attr("class","watermark-radio watermark-radio-topRight radio-on ");
            }else{
                $("#positionBottomRight").attr("checked","checked");
                $("#positionBottomRight").parent().attr("class","watermark-radio watermark-radio-bottomRight radio-on ");
            }
        }else{
            $("#positionTopRight").attr("checked","checked");
            $("#positionTopRight").parent().attr("class","watermark-radio watermark-radio-topRight radio-on ");
        }

        /*页面提交*/
        $(function () {
            success = true;
            $("#limitFive").addClass("none");

            $("#saveSubmit").click(function () {
                var submitFormState=  ${course.published};
                    var $courseInfo = $("#courseInfo");
                    var startTime = $("#liveStartTime").val();
                    var $timedate = $(".timedate-input");
                    if ($.trim($courseInfo.val()) == ''){
                        $courseInfo.focus();
                        $courseInfo.parent().next(".error").removeClass("none");
                        return;
                    } else if(startTime == ""  || startTime == null){
                        $timedate.focus();
                        $timedate.parent().parent().next(".error").removeClass("none");
                        return;
                    }else {
                        $courseInfo.parent().next(".error").addClass("none");
                        $timedate.parent().parent().next(".error").addClass("none");
                        if(submitFormState == false){
                            saveFormNoPublished();

                        }else {
                            registPost();
                        }
                        $courseInfo.parent().next(".error").addClass("none");
                    }



            })

            function registPost () {
                $.ajax({
                    type: "post",
                    url: "${ctx}/mgr/meet/save",
                    data: $('#courseForm').serialize(),
                    dataType:"JSON",
                    success: function(data) {
                    if(data.code=="0"){
                        var isStarCheck = $("#switchCPStar").is(":checked")
                        $(".icon-tips-blue").show();
                        if (isStarCheck == false){
                            $(".star-remove-button").removeClass("none");
                            $("#insertOption").show();
                        }else{
                            $(".star-remove-button").addClass("none");
                            $("#insertOption").hide();
                            $("#submitOption").hide();
                        }

                    }else{
                        layer.msg("<fmt:message key='page.meeting.star.save.fail'/>")
                    }
                }

                })
            }

            function saveFormNoPublished () {
                $.ajax({
                    type: "post",
                    url: "${ctx}/mgr/meet/save",
                    data: $('#courseForm').serialize(),
                    dataType:"JSON",
                    success: function(data) {
                        console.log(data.code)
                        if(data.code=="0"){
                            location.href="${ctx}/mgr/meet/list"
                        }else{
                            layer.msg("<fmt:message key='page.meeting.star.save.fail'/>")
                        }
                    }

                })
            }

        })


    });
</script>
</body>
</html>

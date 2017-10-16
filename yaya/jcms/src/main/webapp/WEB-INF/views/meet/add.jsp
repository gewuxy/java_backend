<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/22
  Time: 17:15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
    <link href="${ctxStatic}/css/daterangepicker.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/wangEditor/css/wangEditor.min.css">
    <style type="text/css">
        .container {
            width: 750px;
        }
        #div1 {
            width: 100%;
            height: 300px;
        }
    </style>

</head>
<body>
<!-- main -->
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

    <div class="tab-hd">

        <ul class="tab-list clearfix">
            <li class="cur">
                <a>会议发布<i></i></a>
            </li>
            <li>
                <a href="${ctx}/func/meet/draft">草稿箱<i></i></a>
            </li>
            <li>
                <a href="${ctx}/func/meet/list">已发布会议<i></i></a>
            </li>
        </ul>
    </div>

    <div class="tab-bd">

        <div class=" clearfix metting-process">
            <div class=" clearfix link-justified metting-process-01">
                <ul>
                    <li class="cur">修改会议</li>
                    <li>设置功能</li>
                    <li>发布预览</li>
                </ul>
            </div>
        </div>


        <form action="${ctx}/func/meet/add" method="post" id="meetingForm" name="meetingForm">
            <input type="hidden" name="id" value="${meet.id}">
            <input type="hidden" name="meetProperty.id" value="${meet.meetProperty.id}">
            <input type="hidden" name="meetProperty.meetId" value="${meet.id}">
            <input type="hidden" name="courseId" value="${courseId}">
            <div class="clearfix">
                <div class="formrow">
                    <label for=""  class="formTitle formTitle-style2">会议名称 <span class="color-green">*</span> </label>
                    <div class="formControls">
                        <p>
                            <input type="text" name="meetName" id="meetName" class="textInput"
                                   value="${empty meet.meetName?(not empty course?course.title:''):meet.meetName}"
                                   maxlength="50" placeholder="填写会议名称(不超过50字)">
                            <select class="wdzxtx-xl text-input" name="meetType">
                                <c:forEach items="${departs}" var="depart">
                                    <option value="${depart.name}" ${meet.meetType eq depart.name || course.category eq depart.name ? 'selected':''}>${depart.name}[${depart.category}]</option>
                                </c:forEach>
                            </select>
                            <span class="formIconTips none" ><img src="${ctxStatic}/images/icon-error.png" alt="">&nbsp;&nbsp;&nbsp;请输入会议名称</span>
                        </p>
                    </div>
                </div>
            </div>
            <div class="formrow-hr"></div>
            <div class="clearfix">
                <div class="formrow">
                    <label for=""  class="formTitle formTitle-style2">起止时间 <span class="color-green">*</span></label>
                    <div class="formControls">
						<span class="time-tj">
							<label for="" id="timeStart">
								<a href="javascript:;" class="callTimedate timedate-icon">选择日期</a>会议时间：
                                <input type="text"  name="meetProperty.startTime" id="startTime" style="width: 115px;"
                                       <c:if test="${not empty liveStartTime}"> value="${liveStartTime}" readonly="readonly"</c:if>
                                <c:if test="${empty liveStartTime}"> value="${startTime}" readonly </c:if>class="timedate-input ">
                                <span> ~ </span>
                                <input type="text"  name="meetProperty.endTime" id="endTime" style="width: 115px;"
                                        <c:if test="${not empty liveEndTime}"> value="${liveEndTime}" readonly="readonly"</c:if>
                                <c:if test="${empty liveEndTime}"> value="${endTime}" readonly </c:if>class="timedate-input " >
                                <span class="formIconTips none" ><img src="${ctxStatic}/images/icon-error.png" alt="">&nbsp;&nbsp;&nbsp;请输入会议起止时间</span>
							</label>
						</span>
                    </div>

                </div>
            </div>
            <div class="formrow-hr"></div>
            <div class="clearfix">
                <div class="formrow">
                    <label for="" class="formTitle formTitle-style2">主讲嘉宾</label>
                    <div class="formControls">
                        <div class="formGuest" style="margin-top: 1px;">
                            <div class="fl clearfix">
                                <div class="formGuest-img clearfix">
                                    <c:set var="hasHeadimg" value="${meet.lecturer.headimg}"/>

                                    <img
                                            <c:if test="${empty hasHeadimg}">src="${ctxStatic}/images/metting-img-man.jpg"</c:if>
                                            <c:if test="${not empty hasHeadimg}">src="${appFileBase}${meet.lecturer.headimg}"</c:if>
                                            alt="" width="110" height="110" id="lecturerHead">
                                </div>
                                <p class="t-center" id="sex-name-hook" style="margin:5px 0 0; ${not empty hasHeadimg?'display:none':'display:bolck' }">
                                    <span class="color-gray-2-up sex-name-hook">男${hasHeadimg}</span>&nbsp;&nbsp;<a style="cursor: pointer;" class="color-blue change-sex-hook">[切换]</a>
                                </p>
                                <p class="t-center">
                                    <label for="changeImg" class="changeImg ">
                                        <input type="file" id="changeImg" name="file" class="none">
                                        <span class="button uploadFile-button">上传图片</span>
                                    </label>
                                </p>
                                <p class="color-gray-up t-center" style="font-size:12px;">300 X 300px <Br />正方形照片
                                </p>
                                <p>
                                    <input type="hidden" name="lecturer.headimg" id="hiddenHeadImg" class="textInput" style="width: 0px;" value="${not empty hasHeadimg?hasHeadimg:'others/metting-img-man.jpg'}">
                                    <span class="formIconTips none" >&nbsp;请上传头像</span>
                                </p>
                            </div>
                            <div class="oh">
                                <div class="formControls">
                                    <input type="hidden" name="lecturer.id" value="${meet.lecturer.id}">
                                    <input type="hidden" name="lecturer.meetId" value="${meet.id}">
                                    <p>
                                        <input type="text" placeholder="主讲姓名" name="lecturer.name" id="lecturerName" value="${meet.lecturer.name}" class="textInput" data-type="email">&nbsp;&nbsp;<span class="color-green">*</span>
                                        <span class="formIconTips none" ><img src="${ctxStatic}/images/icon-error.png" alt="">&nbsp;&nbsp;&nbsp;请输入主讲名称</span>
                                    </p>
                                    <p><input type="text" placeholder="职称" name="lecturer.title" id="lecturerTitle" value="${meet.lecturer.title}" class="textInput" data-type="email"><span class="formIconTips none" ><img src="${ctxStatic}/images/icon-error.png" alt="">&nbsp;&nbsp;&nbsp;请输入职称</span></p>
                                    <p><input type="text" placeholder="在职医院" name="lecturer.hospital" id="lecturerHospital" value="${meet.lecturer.hospital}" class="textInput" data-type="email"><span class="formIconTips none" ><img src="${ctxStatic}/images/icon-error.png" alt="">&nbsp;&nbsp;&nbsp;请输入在职医院</span></p>
                                    <p><input type="text" placeholder="所在科室" name="lecturer.depart" id="lecturerDepart" value="${meet.lecturer.depart}" class="textInput" data-type="email"><span class="formIconTips none" ><img src="${ctxStatic}/images/icon-error.png" alt="">&nbsp;&nbsp;&nbsp;请输入所在科室</span></p>

                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

            <div class="formrow-hr"></div>
            <div class="clearfix">
                <div class="formrow">
                    <label for="" class="formTitle formTitle-style2">会议简介</label>
                    <div class="formControls">
                        <p>会议资料说明，包括主讲介绍、会议详情说明、会议日程等相关信息</p>
                        <div class="container">
                            <div id="introduction" style="width: 100%;height: 250px;">${meet.introduction}</div>
                            <textarea style="display: none;" id="introductionContainer" name="introduction"></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div class="formrow-hr"></div>

            <div class="clearfix">
                <div class="formrow">
                    <label for="" class="formTitle formTitle-style2">参会人员</label>
                    <div class="formControls">
                        <div class="formRadiobox ">
                            <span class="radioboxIcon color-black">
								<input type="radio" name="meetProperty.memberLimitType"  ${meet.meetProperty.memberLimitType == null || meet.meetProperty.memberLimitType == 0?'checked':''}   value="0" id="memberLimitType0"  class="chk_1 chk-hook" >
								<label for="memberLimitType0" ><i class="ico checkboxCurrent"></i>&nbsp;&nbsp;不限制参与</label>
							</span>
                            <div class="formRadiobox-item clearfix"></div>
                            <span class="radioboxIcon color-black">
								<input type="radio" name="meetProperty.memberLimitType"  ${meet.meetProperty.memberLimitType == 1?'checked':''}   value="1" id="memberLimitType1"  class="chk_1 chk-hook" >
								<label for="memberLimitType1" ><i class="ico checkboxCurrent"></i>&nbsp;&nbsp;指定参与者</label>
							</span>
                            <div class="formRadiobox-item clearfix">
                                <span class="text">省份</span>
                                <span class="formPage-select-item  pr">
									<i class="formPage-select-arrow"></i>
									<select name="meetProperty.specifyProvince" preid="memberLimitType0" id="specifyProvince" class="text-input formPage-select formPage-select-hook">
										<option value="">全国</option>
										<c:forEach items="${provinces}" var="p">
                                            <option value="${p.name}" ${meet.meetProperty.memberLimitType==1 && meet.meetProperty.specifyProvince eq p.name?'selected':''} pid="${p.id}">${p.name}</option>
                                        </c:forEach>
									</select>
								</span>
                                <span class="text">城市</span>
                                <span class="formPage-select-item  pr">
									<i class="formPage-select-arrow"></i>
									<select name="meetProperty.specifyCity"  preid="memberLimitType0" id="specifyCity" class="text-input formPage-select formPage-select-hook">
										<option value="">不限</option>
                                        <c:forEach items="${cities}" var="city">
                                            <option value="${city.name}" ${meet.meetProperty.memberLimitType==1 && meet.meetProperty.specifyCity eq city.name?'selected':''} >${city.name}</option>
                                        </c:forEach>
									</select>
								</span>
                                <span class="text">科室</span>
                                <span class="formPage-select-item  pr">
									<i class="formPage-select-arrow"></i>
									<select name="meetProperty.specifyDepart" class="text-input formPage-select formPage-select-hook"  preid="memberLimitType0">
										<option value="">所有科室</option>
                                        <c:forEach items="${departs}" var="depart">
                                            <option value="${depart.name}" ${meet.meetProperty.memberLimitType==1 && meet.meetProperty.specifyDepart eq depart.name?'selected':''}>${depart.name}</option>
                                        </c:forEach>
									</select>
								</span>

                            </div>
                            <span class="radioboxIcon color-black">
								<input type="radio" name="meetProperty.memberLimitType" ${meet.meetProperty.memberLimitType == 2?'checked':''} value="2" id="memberLimitType2" class="chk_1 chk-hook">
								<label for="memberLimitType2" ><i class="ico checkboxCurrent"></i>&nbsp;&nbsp;指定医生分组</label>
							</span>
                            <div class="formRadiobox-item clearfix">
                                <div class="formrow">
                                    <div class="formControls">
                                        <!--已有目录-->
                                        <div class="fl">
                                            <a href="javascript:;" class="black-button formButton fx-btn-1 " id="choseGroupBtn">选择分组</a>
                                        </div>
                                        <div class="oh" style="line-height: 32px;">
                                            <div class="metting-bootstrap metting-bootstrap-formFile">
                                                选中的分组：<span id="chosedGrups">
                                                <c:if test="${not empty meet.meetProperty.groupIds}">
                                                    <c:forEach items="${meet.meetProperty.groupIds}" var="gid" varStatus="gstatus">
                                                        <input type="hidden" name="meetProperty.groupIds" value="${gid}"/>${groupMap[gid].groupName}${gstatus.index == fn:length(meet.meetProperty.groupIds) - 1 ? "" : ","}&nbsp;&nbsp;
                                                    </c:forEach>
                                                </c:if>
                                            </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        <%--<div class="formRadiobox-item clearfix">--%>
								<%--<span class="formPage-select-item select-max pr" style="margin-left:30px;" >--%>
									<%--<i class="formPage-select-arrow"></i>--%>
									<%--<select name="meetProperty.groupId" class="text-input formPage-select formPage-select-hook" preid="memberLimitType1">--%>
										<%--<c:choose>--%>
                                            <%--<c:when test="${group != null && group.id != 0}">--%>
                                                <%--<option value="${group.id}">${group.groupName}</option>--%>
                                            <%--</c:when>--%>
                                        <%--</c:choose>--%>
                                        <%--<option value="0">全部关注医生</option>--%>
                                        <%--<c:forEach items="${groups}" var="group">--%>
                                            <%--<option value="${group.id}" ${meet.meetProperty.memberLimitType == 1 && meet.meetProperty.groupId == group.id?'selected':''}>${group.groupName} [${group.memberCount}]</option>--%>
                                        <%--</c:forEach>--%>
									<%--</select>--%>
								<%--</span>--%>
                            <%--</div>--%>
                        </div>

                    </div>
                </div>
            </div>
            <div class="formrow-hr"></div>
            <div class="clearfix">
                <div class="formrow formrow-del-box formrow-del-hook">
                    <label for="" class="formTitle formTitle-style2 formTItleMargin">添加象数</label>
                    <div class="formControls">
                        <p class="rowMargin"><span class="formPage-select-item  pr">
                            <i class="formPage-select-arrow"></i>
                            <select name="meetSetting.requiredXs" id="requiredXsSelect" class="text-input formPage-select formPage-select-colorBlack formPage-select-hook">
                                <option value="0" ${meet.meetSetting.requiredXs ==0?'selected':''}>学习费用</option>
                                <option value="1" ${meet.meetSetting.requiredXs ==1?'selected':''}>赠送象数</option>
                                <option value="" ${meet.meetSetting.requiredXs == null?'selected':''}>未设置</option>
                            </select></span>&nbsp;&nbsp;&nbsp;
                            <span class="color-black" id="userCredit">当前有${credits == null?0:credits}象数</span>&nbsp;&nbsp;&nbsp;
                            <a href="javascript:;" id="xsbox" class="color-blue fx-btn-3">象数管理</a>&nbsp;&nbsp;&nbsp;
                            <a href="javascript:;" class="color-blue" id="refresh">刷新</a>
                        </p>
                        <p class="color-gray-2-up" id="credits1"  style="display:${meet.meetSetting.requiredXs == 1?'block':'none'}">
                            前<input type="text" id="award_limit" name="meetSetting.awardLimit" ${meet.meetSetting.requiredXs != 1?'disabled':''}
                                   class="gary-input" value="${meet.meetSetting.awardLimit}" > 位参与者&nbsp;
                            <span class="formIconTips none" ><img src="${ctxStatic}/images/icon-error.png" alt="">&nbsp;&nbsp;&nbsp;请输入奖励人数</span>
                            赠予 <input type="text" id="award_xs" name="meetSetting.xsCredits" ${meet.meetSetting.requiredXs != 1?'disabled':''}
                                      class="gary-input" value="${meet.meetSetting.xsCredits}"> 象数
                            <span class="formIconTips none" ><img src="${ctxStatic}/images/icon-error.png" alt="">&nbsp;&nbsp;&nbsp;请输入奖励象数</span>
                        </p>
                        <p class="color-gray-2-up" id="credits0" style="display:${meet.meetSetting.requiredXs == 0?'block':'none'}">
                            参与会议 消耗&nbsp;<input type="text" id="pay_xs" class="gary-input" ${meet.meetSetting.requiredXs == 1?'disabled':''}
                                    name="meetSetting.xsCredits" value="${meet.meetSetting.xsCredits}"> 象数
                            <span class="formIconTips none" ><img src="${ctxStatic}/images/icon-error.png" alt="">&nbsp;&nbsp;&nbsp;请输入消耗象数</span>
                        </p>
                    </div>
                </div>
            </div>
            <div class="formrow-hr"></div>
            <div class="clearfix">
                <div class="formrow formrow-del-box formrow-del-hook">
                    <label for="" class="formTitle formTitle-style2 formTItleMargin">添加学分</label>
                    <div class="formControls">
                        <p class="rowMargin">
                            <span class="formPage-select-item  pr">
                                <i class="formPage-select-arrow"></i>
                                <select name="meetSetting.rewardCredit" id="rewardCreditSelect" class="text-input formPage-select formPage-select-colorBlack formPage-select-hook">
                                    <option value="2" ${meet.meetSetting.rewardCredit == 2?'selected':''}>I 类学分</option>
                                    <option value="3" ${meet.meetSetting.rewardCredit == 3?'selected':''}>II 类学分</option>
                                    <option value="0" ${meet.meetSetting.rewardCredit == null?'selected':''}>未设置</option>
                                </select>
                            </span>
                        </p>
                        <p class="color-gray-2-up" id="award" style="display:${meet.meetSetting.rewardCredit > 1 ?'block':'none'}" >
                            前 <input type="text" id="award_credit_limit" name="meetSetting.awardCreditLimit"
                                     class="gary-input" ${meet.meetSetting.awardCreditLimit < 1?'disabled':''} value="${meet.meetSetting.awardCreditLimit}" > 位参与者&nbsp;
                            <span class="formIconTips none" ><img src="${ctxStatic}/images/icon-error.png" alt="">&nbsp;&nbsp;&nbsp;请输入奖励人数</span>
                            赠予 <input type="text" id="award_credit" name="meetSetting.eduCredits"
                                     class="gary-input" ${meet.meetSetting.eduCredits < 1?'disabled':''} value="${meet.meetSetting.eduCredits}"> 学分
                            <span class="formIconTips none" ><img src="${ctxStatic}/images/icon-error.png" alt="">&nbsp;&nbsp;&nbsp;请输入奖励学分数</span>
                        </p>

                    </div>
                </div>
            </div>
            <div class="formrow-hr"></div>
            <div class="clearfix">
                <div class="formrow" style="margin-bottom:50px;">
                    <label for=""  class="formTitle formTitle-style2">功能选择 <span class="color-green">&nbsp;</span></label>
                    <div class="formControls">
                        <div class="formImgRadio">
                            <div class="formImgRadio-item${moduleMap['1'] || not empty course?' current':''}" funid="1">
                                <input type="checkbox" name="funIds" class="hide" value="1" ${moduleMap['1'] || not empty course?'checked':''} id="radio1">
                                <label for="radio1">
                                    <span class="formImgRadio-img formImgRadio-icon-04"></span>
                                    <span class="formImgRadio-title mettingCheck-icon"> 添加课件 </span>
                                </label>
                            </div>
                            <div class="formImgRadio-item${moduleMap['3']?' current':''}" funid="3" >
                                <input type="checkbox" name="funIds"  value="3" ${moduleMap['3']?'checked':''} class="hide" id="radio2">
                                <label for="radio2">
                                    <span class="formImgRadio-img formImgRadio-icon-05"></span>
                                    <span class="formImgRadio-title mettingCheck-icon "> 考试答卷 </span>
                                </label>
                            </div>
                            <div class="formImgRadio-item${moduleMap['4']?' current':''}"  funid="4">
                                <input type="checkbox" name="funIds" value="4" class="hide" ${moduleMap['4']?'checked':''} id="radio3">
                                <label for="radio3">
                                    <span class="formImgRadio-img formImgRadio-icon-03"></span>
                                    <span class="formImgRadio-title mettingCheck-icon"> 调查问卷 </span>
                                </label>
                            </div>
                            <div class="formImgRadio-item${moduleMap['5']?' current':''}" funid="5" >
                                <input type="checkbox" name="funIds" value="5" class="hide" ${moduleMap['5']?'checked':''} id="radio4">
                                <label for="radio4">
                                    <span class="formImgRadio-img formImgRadio-icon-06"></span>
                                    <span class="formImgRadio-title mettingCheck-icon"> 会议签到 </span>
                                </label>
                            </div>
                            <div class="formImgRadio-item${moduleMap['2']?' current':''}" funid="2" >
                                <input type="checkbox" name="funIds" value="2" class="hide" ${moduleMap['2']?'checked':''} id="radio5">
                                <label for="radio5">
                                    <span class="formImgRadio-img formImgRadio-icon-07"></span>
                                    <span class="formImgRadio-title mettingCheck-icon"> 添加视频 </span>
                                </label>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

            <div class="clearfix">
                <div class="formrow" style="margin-bottom:70px;">
                    <label for="" class="formTitle">会议目录</label>
                    <input type="hidden" name="folderId" id="folInput" value="">
                    <div class="formControls">
                        <c:if test="${not empty folderList}">
                            <!--已有目录-->
                            <a href="javascript:;" class="black-button fx-btn-1" onclick="openFolder()">选择目录</a>
                            <div class="metting-bootstrap metting-bootstrap-formFile">
                                根目录<i class="rowSpace">\</i><span id="filePath"></span>
                            </div>
                        </c:if>
                        <c:if test="${empty folderList}">
                            <!--文件夹没有任何目录-->
                            <a href="javascript:;"  onclick="openFolder()" class="black-button fx-btn-2">新建目录</a>
                        </c:if>

                    </div>
                </div>
            </div>

            <div class="clearfix">
                <div class="formrow">
                    <label for="" class="formTitle">&nbsp;</label>
                    <div class="formControls">
                        <input type="button" id="submitBtn" class="formButton formButton-max" value="下一步">
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<div class="mask-wrap">
    <div class="dis-tbcell">
        <div class="distb-box fx-mask-box-3">
            <div class="mask-hd clearfix">
                <h3 class="font-size-1">温馨提示</h3>
                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png" id="closeBtn"></span>
            </div>
            <div class="mask-share-box">
                <p class="top-txt color-black" id="xsTips">象数不足，本次会议需扣除5000象数</p>
                <p>账户剩余<span class="color-black" id="xsValue">350象数</span>，可继续<a href="javascript:;" class="c-3  fx-btn-5" id="charge">充值</a>增加象数值</p>
            </div>
            <div class="sb-btn-box p-btm-1 t-right">
                <button class="close-button-fx cur fx-btn-5" id="chargeBtn">充值</button>
                <button class="close-button-fx" id="cancelCreditsBtn">取消</button>
            </div>
        </div>
        <div class="distb-box fx-mask-box-5">
            <div class="mask-hd clearfix">
                <h3 class="font-size-1">温馨提示</h3>
                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png" id="rechargeBtn"></span>
            </div>
            <div class="mask-share-box">
                <p class="top-txt color-black">充值是否成功</p>
                <p>如未显示相应充值的象数，<span class="color-black">先关闭窗口后，再点击刷新按钮</span></p>
            </div>
            <div class="sb-btn-box p-btm-1 t-right">
                <button class="close-button-fx cur" id="rechargeOK">充值成功</button>
                <button class="close-button-fx" id="rechargeFail">充值失败</button>
            </div>
        </div>

        <div class="distb-box distb-box-min fx-mask-box-1 " >
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
                            <div class="fx-module-title"  data-name=${folder.infinityName}>
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
                <button class="close-button-fx" name="cancelBtn" value="取消">取消</button>
            </div>
        </div>

        <div class="distb-box distb-box-min fx-mask-box-2">
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
                    <button class="close-button-fx" name="cancelBtn" value="取消">取消</button>
                </div>
            </form>
        </div>

        <div class="distb-box distb-box-min fx-mask-box-6" >
            <div class="mask-hd clearfix">
                <h3 class="font-size-1">设置分组</h3>
                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
            </div>
            <div class="clearfix hidden-box">
                <div class="formrow popup-checkbox">

                    <form action="" id="groupForm" name="groupForm">
                        <ul class="fx-li-box" >

                            <li>
                                <span class="checkboxIcon">
                                    <input type="checkbox" id="popup_checkbox_all" class="chk_1 chk-hook">
                                    <label for="popup_checkbox_all" class="popup_checkbox_all_hook"><i class="ico"></i>&nbsp;全部关注医生</label>
                                </span>
                            </li>
                            <c:forEach items="${groups}" var="grp" varStatus="status">
                                <li>
                                <span class="checkboxIcon">
                                    <input type="checkbox" name="choseGroupIds" id="popup_checkbox_${status.index}" class="chk_1 chk-hook" sub="true" value="${grp.id}">
                                    <label for="popup_checkbox_${status.index}"  class="popup_checkbox_hook"><i class="ico"></i>&nbsp;${grp.groupName}</label>
                                </span>
                                </li>
                            </c:forEach>

                        </ul>
                    </form>

                </div>

            </div>
            <div class="sb-btn-box  p-btm-1 t-right">
                <button class="close-button-fx cur" id="choseGroupOk">确认</button>
                <button class="close-button-fx" id="choseGroupCancel">取消</button>
            </div>
        </div>
    </div>



</div>

<script src="${ctxStatic}/js/moment.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/js/jquery.daterangepicker.js"></script>
<script src="${ctxStatic}/wangEditor/js/wangEditor.min.js"></script>
<script src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script src="${ctxStatic}/laydate/laydate.js"></script>
<script src="${ctxStatic}/js/slide.js"></script>
<script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>

<script>
    const imagBase = '${ctxStatic}/images/';
    const defaultBoyImg = 'metting-img-man.jpg';
    const defaultGirlImg = 'metting-img-girl.jpg';
    const boy = "男";
    const girl = "女";
    $(function(){
        var popupCheckbox = $('label.popup_checkbox_hook');
        var popupCheckboxNum = popupCheckbox.length - 1;

        //弹出窗 - 点击全选触发
        $('.popup_checkbox_all_hook').on('click',function(){
            $(this).toggleClass('checkboxAllCurrent');
            if($(this).hasClass('checkboxAllCurrent')){
                popupCheckbox.find('.ico').addClass('checkboxCurrent');
                popupCheckbox.prev().prop("checked","true");
            } else {
                popupCheckbox.find('.ico').removeClass('checkboxCurrent');
                popupCheckbox.prev().removeProp("checked");
            }
        });
        //弹出窗 - 点击每个选项触发
        popupCheckbox.each(function() {
            $(this).off('.click').on('click', function () {

                if (!($(this).find('.ico').hasClass('checkboxCurrent')) && $(".popup_checkbox_hook .checkboxCurrent").length == popupCheckboxNum) {

                    $('.popup_checkbox_all_hook').addClass('checkboxAllCurrent').find('.ico').addClass('checkboxCurrent');
                    $('.popup_checkbox_all_hook').prev().prop("checked","true");
                } else {
                    $('.popup_checkbox_all_hook').removeClass('checkboxAllCurrent').find('.ico').removeClass('checkboxCurrent');
                    $('.popup_checkbox_all_hook').prev().removeProp("checked");
                }
            })
        });


        $("#choseGroupBtn").click(function(){
            if ($("#memberLimitType2").is(":checked")){
                $('.mask-wrap').addClass('dis-table');
                $('.fx-mask-box-6').show();
            }
        });

        $("#choseGroupCancel").click(function(){
            $('.mask-wrap').removeClass('dis-table');
            $('.fx-mask-box-6').hide();
        });

        $("#choseGroupOk").click(function(){
            var groupIds = $("#groupForm").find("input[name='choseGroupIds']:checked");
            if (groupIds.length == 0){
                top.layer.msg("请选择分组");
                //alert("请选择分组");
                return;
            }
            $("#chosedGrups").html("");

            for(var i = 0 ; i < groupIds.length ; i ++){
                $("#chosedGrups").append('<input type="hidden" name="meetProperty.groupIds" value="'+$(groupIds[i]).val()+'"/>' + $(groupIds[i]).siblings("label").text());
                if (i < groupIds.length - 1){
                    $("#chosedGrups").append('&nbsp;&nbsp;,&nbsp;&nbsp;');
                }
            }
            $('.mask-wrap').removeClass('dis-table');
            $('.fx-mask-box-6').hide();
        });

        //回显文件夹目录
        if(${preName != null}){
            var pName = "${preName}";
            var strs = new Array();
            var strs2 = new Array();
            var name = "";
            strs = pName.split("_");
            for(i=0;i<strs.length ;i++ )
            {
                strs2 = strs[i].split("-");
                name += strs2[0] + "\\";
            }
            $("#filePath").text(name);
        }


        //tab 点击切换
        $(document).on('click',".fx-module-title label",function(){
            $(this).parents(".fx-module-list").find(".fx-module-setBox").hide().parents().removeClass("current");
            if($(this).parents(".fx-module-title").next(".fx-module-setBox").find("li").length > 0) {
                $(this).parents(".fx-module-title").next(".fx-module-setBox").toggle().parent().addClass("current");
            }
        });

        //关闭窗口
        $(".close-btn-fx").click(function(){
            $('.mask-wrap').removeClass('dis-table');
            $('.fx-mask-box-1').hide();
            $("#moveForm")[0].reset();
        });

        //确认新增文件夹
        $("#folderSubmitBtn").click(function(){
            if($.trim($("#folderForm").find("input[name='infinityName']").val()) == ''){
                layer.msg("请输入文件夹名称");
                $("#folderForm").find("input[name='infinityName']").focus();
                return false;
            }
            saveFolder($("#folderForm"));
            $('.mask-wrap').removeClass('dis-table');
            $('.fx-mask-box-2').hide();
            $("#folderForm")[0].reset();
            return false;
        });

        //确认移动会议
        $("#moveSubmitBtn").click(function () {
            var pre = $("input[name='folderId']:checked").parents(".folderClass").find(".fx-module-title").attr('data-name');
            var  select = $(".fx-module-setBox input[name='folderId']:checked").next().text();
            $("#filePath").html("");
            if(typeof(pre) != "undefined"){
                if(typeof(select) != "undefined"){
                    $("#filePath").text(pre+"\\"+select);
                }else{
                    $("#filePath").text(pre);
                }
            }
            //添加hidden input 的folderId
            var a =  $("input[name='folderId']:checked").val();
            $("#folInput").val(a);
            $('.mask-wrap').removeClass('dis-table');
            $('.fx-mask-box-1').hide();
        });

        //点击取消按钮，关闭窗口
        $("button[name='cancelBtn']").click(function(){
            $('.mask-wrap').removeClass('dis-table');
            $('.fx-mask-box-1').hide();
            $("#moveForm")[0].reset();
        });

        $(".change-sex-hook").click(function(){
            var sex = $(".sex-name-hook").text();
            if(boy == sex){
                $(".sex-name-hook").text(girl);
                $("#lecturerHead").attr("src", imagBase+defaultGirlImg);
                $("#hiddenHeadImg").val("others/"+defaultGirlImg);
            }else{
                $(".sex-name-hook").text(boy);
                $("#lecturerHead").attr("src", imagBase+defaultBoyImg);
                $("#hiddenHeadImg").val("others/"+defaultBoyImg);
            }

        });

        var editor = new wangEditor('introduction');

        // 自定义菜单
        editor.config.menus = [
            'source',
            '|',
            'bold',
            'underline',
            'italic',
            'strikethrough',
            'eraser',
            'forecolor',
            'bgcolor'
        ];

        editor.create();

        $(".callTimedate").on('click',function(){
            var isNull = ${empty liveStartTime};
            if(isNull){
                $('#timeStart').trigger('focus');
                $('#timeStart').dateRangePicker({
                    singleMonth: true,
                    showShortcuts: false,
                    showTopbar: false,
                    startOfWeek: 'monday',
                    separator : ' ~ ',
                    format: 'YYYY/MM/DD HH:mm',
                    autoClose: false,
                    time: {
                        enabled: true
                    }
                }).bind('datepicker-first-date-selected', function(event, obj){
                    /*首次点击的时间*/
                    console.log('first-date-selected',obj);
                }).bind('datepicker-change',function(event,obj){
                    /* This event will be triggered when second date is selected */
                    var timeArr = obj.value.split("~");
                    $("#startTime").val($.trim(timeArr[0]));
                    $("#endTime").val($.trim(timeArr[1]));
                });
            }
        });


        $(".formImgRadio-item").click(function(){
            if ($(this).hasClass("current")) {
                $(this).removeClass("current");
            } else {
                $(this).addClass("current");
            }
            $(this).find("input[name='funIds']").prop("checked",$(this).hasClass("current"));

            return false;
        });

    });

</script>
<script>
    $(function(){
        $("#specifyProvince").change(function(){
            var pid = $("#specifyProvince").find("option:selected").attr("pid");
            if(pid != undefined && pid != ''){
                loadCities(pid);
            }
        });

        $("#changeImg").change(function(){
            uploadHead();
        });

        $("#requiredXsSelect").change(function(){
            var requiredXs = $(this).val();
            if (requiredXs == "") { // 不奖励也不支付象数
                $("#credits0").hide();
                $("#credits0").find("input").attr("disabled","true");
                $("#credits1").hide();
                $("#credits1").find("input").attr("disabled","true");
            } else if(requiredXs == 1){// 奖励象数
                $("#credits1").show();
                $("#credits1").find("input").removeAttr("disabled");
                $("#credits0").hide();
                $("#credits0").find("input").attr("disabled","true");
            }else{ // 支付象数
                $("#credits0").show();
                $("#credits0").find("input").removeAttr("disabled");
                $("#credits1").hide();
                $("#credits1").find("input").attr("disabled","true");
            }
        });

        $("#rewardCreditSelect").change(function(){
           var rewardCredit = $(this).val();
           if (rewardCredit == 0) { // 不奖励学分
               $("#award").hide();
               $("#award").find("input").attr("disabled","true");
           } else {
               $("#award").show();
               $("#award").find("input").removeAttr("disabled");
           }
        });

        $(".gary-input").keyup(function(){
            var val = $(this).val();
            if(!/^[1-9]+[0-9]*$/g.test(val)){
                $(this).val("");
            }
        });

        $("#submitBtn").click(function(){
            if(checkForm()){
                $("#introductionContainer").val($("#introduction").html());
                $("#meetingForm").submit();
            }
        });

//        $("input[type='checkbox']").change(function(){
//            alert(1);
//        });

        $("#xsbox").click(function(){
            showCreditsDialog();
        });

        $("#closeBtn,#cancelCreditsBtn").click(function (){
            closeCreditsDialog();
        });

        $("#charge,#chargeBtn").click(function () {
            window.open("${ctx}/mng/account/xsInfo");
            // 切换 是否充值成功的提示框
            closeCreditsDialog();
            showChargeDialog();
        });

        $("#rechargeBtn,#rechargeOK,#rechargeFail").click(function(){
            closeChargeDialog();
        });
        // 刷新用户象数
        $("#refresh").click(function () {
            checkUserXs(0,0,0);
        })
    });

    function closeCreditsDialog(){
        $('.mask-wrap, .mask-wrap-2').removeClass('dis-table');
        $('.fx-mask-box-3').hide();
    }

    function showCreditsDialog(awardLimit,awardXs){
        var userXs = $("#userCredit").text();
        var reg = /\d+/g; // 提取象数数字
        userXs = userXs.match(reg);
        if ((awardLimit != null && awardLimit!='' && awardLimit != 0)
            && (awardXs != null && awardXs!='' && awardXs != 0)) {
            $("#xsTips").text("象数不足，本次会议需扣除"+(awardLimit * awardXs)+"象数");
            $("#xsValue").text(userXs+"象数");
            $("#xsTips").show();
        } else { // 没有设置奖励象数时 点击象数管理弹出框 隐藏“象数不足...”那句话
            $("#xsTips").hide();
            $("#xsValue").text(userXs+"象数");
        }
        $('.mask-wrap, .mask-wrap-2').addClass('dis-table');
        $('.fx-mask-box-3').show();
    }

    function showChargeDialog() {
        $('.mask-wrap, .mask-wrap-2').addClass('dis-table');
        $('.fx-mask-box-5').show();
    }

    function closeChargeDialog() {
        $('.mask-wrap, .mask-wrap-2').removeClass('dis-table');
        $('.fx-mask-box-5').hide();
    }

    // 提交表单 检查
    function checkForm(){

        $(".formIconTips").addClass("none");
        var meetName = $("#meetName").val();
        var endTime = $("#endTime").val();
        if($.trim(meetName) == ''){
            $("#meetName").siblings(".formIconTips").removeClass("none");
            $("#meetName").focus();
            return false;
        }
        if(endTime == ''){
            $("#endTime").siblings(".formIconTips").removeClass("none");
            $("#startTime").focus();
            return false;
        }
        var lecturerName = $.trim($("#lecturerName").val());
        if(lecturerName == ''){
            $("#lecturerName").siblings(".formIconTips").removeClass("none");
            $("#lecturerName").focus();
            return false;
        }

        // 是否奖励或支付象数
        var requiredXs = $("#requiredXsSelect").val();
        if (requiredXs != '' && requiredXs == 0){
            var payXs = $.trim($("#pay_xs").val());
            if (payXs == '') {
                $("#pay_xs").siblings(".formIconTips").removeClass("none");
                $("#pay_xs").focus();
                return false;
            }
        } else if(requiredXs == 1){
            var awardLimit = $.trim($("#award_limit").val());
            var awardXs = $.trim($("#award_xs").val());
            if (awardLimit == '' || awardLimit == 0) {
                $("#award_limit").siblings(".formIconTips").removeClass("none");
                $("#award_limit").focus();
                return false;
            }
            if (awardXs == '' || awardXs == 0) {
                $("#award_xs").siblings(".formIconTips").removeClass("none");
                $("#award_xs").focus();
                return false;
            }
            if ((awardLimit != '' && awardLimit != 0)
                && (awardXs != '' && awardXs != 0)) {
                var xsFlag = true;
                // 象数是否足够
                xsFlag = checkUserXs(requiredXs,awardXs,awardLimit);
                if (!xsFlag){
                    showCreditsDialog(awardLimit,awardXs);
                    return false;
                }
            }
        }

        // 是否奖励学分
        var rewardCredit = $("#rewardCreditSelect").val();
        if (rewardCredit > 1){
            var awardCreditLimit = $.trim($("#award_credit_limit").val());
            var awardCredit =  $.trim($("#award_credit").val());
            if (awardCreditLimit == '' || awardCreditLimit == 0) {
                $("#award_credit_limit").siblings(".formIconTips").removeClass("none");
                $("#award_credit_limit").focus();
                return false;
            }
            if (awardCredit == '' || awardCredit == 0) {
                $("#award_credit").siblings(".formIconTips").removeClass("none");
                $("#award_credit").focus();
                return false;
            }
        }

        // 选择功能模块
        var hasFun = false;
        $("input[name='funIds']").each(function(){
            if($(this).is(":checked")){
                hasFun = true;
                return false;
            }
        });
        if(!hasFun){
            top.layer.msg("请至少选中一个功能");
            return false;
        }

        //csp投稿或者引用资源，必须选择添加课件功能
        if(${not empty courseId}){
            if(!$("#radio1").is(':checked')){  //没有选择添加课件
                top.layer.msg("请选中添加课件功能");
                return false;
            }
        }
        return true;
    }

    // 检查用户象数
    function checkUserXs(requiredXs,awardLimit,awardCredit){
        var canSubmit = true;
        $.ajax({
            url: '${ctx}/func/meet/check/user/credit',
            data: {requiredXs:requiredXs,xsCredits:awardCredit,limit:awardLimit},
            type: 'post',
            dataType: 'json',
            async: false, // 设置为同步请求
            success: function (data) {
                // 点击刷新时赋值
                $("#userCredit").text("当前有"+data.data+"象数");
                if (data.code != null && data.code == "-1") {
                    canSubmit = false;
                }
            },
            error:function(data, status, e){
                canSubmit = false;
            }
        });
        return canSubmit;
    }

    function saveDraft(cb){
        var data = $("#meetingForm").serialize();
        $.post('${ctx}/func/meet/saveDraft',data, function () {
            cb(data);
        }, 'json');
    }


    function uploadHead(){
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.ajaxFileUpload({
            url: '${ctx}/func/meet/lecturer/uphead', //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: "changeImg", //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data)  //服务器成功响应处理函数
            {
                layer.close(index);
                //回调函数传回传完之后的URL地址
                if(data.code == 0){
                    handleUploadFinished(data);
                }else{
                    layer.msg(data.msg);
                }
            },
            error:function(data, status, e){
                layer.msg(e);
                layer.close(index);
            }
        });
    }

    function handleUploadFinished(data){
        $("#sex-name-hook").hide();
        $("#lecturerHead").attr("src", data.data['url1']);
        $("#hiddenHeadImg").val(data.data['url2']);
        $("#changeImg").replaceWith('<input type="file" id="changeImg" name="file" class="none">');
        $("#changeImg").change(function(){
            uploadHead();
        });
    }


    function loadCities(pid){
        $.get('${ctx}/region/list',{'preId':pid}, function (data) {
            $("#specifyCity").html("");
            $("#specifyCity").append('<option value="">不限</option>');
            for(var index in data.data){
                $("#specifyCity").append('<option value='+data.data[index].name+'>'+data.data[index].name+'</option>');
            }
        },'json');
    }

    function openFolder() {
        $('.mask-wrap').addClass('dis-table');
        if(${folderList == null}) {
            $('.fx-mask-box-2').show();
        }else{
            if(${folderId != null}){
                var fId = "${folderId}";
                $("input[id="+fId+"]").prop("checked", "checked");
                $("input[id="+fId+"]").parents('.fx-module-setBox').show();
            }
            $('.fx-mask-box-1').show();
        }

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
</script>

</body>

</html>

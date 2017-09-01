<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>医生详情</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
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
                    <img src="images/subPage-header-image-08.png" alt="">
                </div>
                <div class="oh">
                    <p><strong>医生管理</strong></p>
                    <p>关注医生分组管理，群发通知便捷有效</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->

    <div class="tab-hd ">
        <ul class="tab-list clearfix">
            <li>
                <a href="${ctx}/mng/doc/list">医生管理<i></i></a>
            </li>
            <li>
                <a href="${ctx}/mng/doc/import">导入医生<i></i></a>
            </li>
            <li class="cur">
                <a href="${ctx}/mng/doc/info?doctorId=${info.id}">医生详情<i></i></a>
            </li>
        </ul>
    </div>
    <div class="tj-con mar-top-bd">
        <div class="tj-content clearfix">
            <table class="tj-table tj-table-4 clearfix">
                <colgroup>
                    <col class="col3">
                    <col class="col3">
                    <col class="col3">
                </colgroup>
                <tbody>
                <tr>
                    <td>
                        <div class="fl clearfix">
                            <div class="img"><img
                                    <c:choose>
                                    <c:when test="${!empty info.headimg}">src="${info.headimg}" </c:when>
                                        <c:otherwise>src="${ctxStatic}/img/hz-detail-img100.jpg"</c:otherwise>
                            </c:choose> alt=""></div>

                        </div>
                        <div class="oh clearfix">
                            <h2>${info.linkman}</h2>
                            <p>${info.type}&nbsp;&nbsp;${info.title}</p>
                        </div>
                    </td>
                    <td>
                        <h4>${info.hospital}</h4>
                        <p>${info.province}${info.city}${info.zone}</p>
                    </td>
                    <td>
                        <div class="fl clearfix">
                            分组：
                        </div>
                        <div class="oh clearfix">
                            ${info.groupName == null || info.groupName == "" ? "" : "未分组"}
                            ${info.unionid == null || info.unionid == "" ? "" : "、 已绑定微信"}
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="tab-hd ">
        <ul class="tab-list clearfix" id="changeViewTab">
            <li class="cur" id="study">
                <a style="cursor:pointer;">学习统计<i></i></a>
            </li>
            <li id="send">
                <a style="cursor: pointer;">发送通知<i></i></a>
            </li>
        </ul>
    </div>
    <div id="study_view">
        <div class="tj-con">
            <div class="tj-content  clearfix ">
                <table class="tj-table tj-table-2 clearfix">
                    <tbody>
                    <tr>
                        <td class="notTopBorder">
                            <p class="tj-table-title">完成会议学习</p>
                            <p><span class="td-txt">${info.finishedNum}</span>个</p>
                        </td>
                        <td class="notTopBorder">
                            <p class="tj-table-title">视频学习时间</p>
                            <p><span class="td-txt" id="hour"></span>小时 <span class="td-txt" id="minute"></span>分</p>
                        </td>
                        <td class="notTopBorder">
                            <p class="tj-table-title">考试平均得分</p>
                            <p><span class="td-txt">${info.average}</span>分</p>
                        </td>
                        <td class="notTopBorder">
                            <p class="tj-table-title">总计学习时长</p>
                            <p><span class="td-txt" id="hour1"></span>小时 <span class="td-txt" id="minute1"></span>分</p>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="tj-con subPage-marginTop">
            <div class="tj-content clearfix">
                <div class="tj-top clearfix">
                    <h3>会议历史</h3>
                    <a href="${ctx}/mng/doc/export?docId=${info.id}&total=${meetPage.pages*meetPage.pageSize}&type=meet"
                       class="tj-more">导出Excel</a>
                </div>
                <table class="tj-table tj-table-re1 clearfix" id="meetTable">
                    <thead>
                    <tr>
                        <td class="tj-td-1">会议类型</td>
                        <td class="tj-td-3">会议名称</td>
                        <td class="tj-td-1">开场时间</td>
                        <td class="tj-td-1">学习时长</td>
                    </tr>
                    </thead>
                    <tbody id="meetTBody">
                    <c:forEach items="${meetPage.dataList}" var="p">
                        <tr meetId="${p.meetId}">
                            <td class="tj-td-1 color-black"><span class="icon iconfont icon-minIcon3"></span>微课</td>
                            <td class="tj-td-3"><a href="${ctx}/func/meet/view?id=${p.meetId}&tag=1">${p.meetName}</a>
                            </td>
                            <td class="tj-td-1"><fmt:formatDate value="${p.publishTime}"
                                                                pattern="yyyy-MM-dd HH:mm"/></td>
                            <td class="tj-td-1"><span>${p.time}</span></td>
                        </tr>

                    </c:forEach>

                    </tbody>
                </table>
                <%@include file="/WEB-INF/include/meetPageable.jsp" %>
                <form id="meetForm" name="meetForm" method="post">
                    <input type="hidden" name="doctorId" value="${info.id}"/>
                    <input type="hidden" name="pageNum" id="pageNum" value="${meetPage.pageNum}"/>
                    <input type="hidden" name="pageSize" id="pageSize" value="${meetPage.pageSize}">
                </form>
            </div>
        </div>

        <div class="tj-con subPage-marginTop">
            <div class="tj-content clearfix">
                <div class="tj-top clearfix">
                    <h3>答题历史</h3>
                    <a href="${ctx}/mng/doc/export?docId=${info.id}&total=${examPage.pages*examPage.pageSize}&type=exam"
                       class="tj-more">导出Excel</a>
                </div>
                <table class="tj-table tj-table-re1 clearfix" id="examTable">
                    <thead>
                    <tr>
                        <td class="tj-td-1">会议类型</td>
                        <td class="tj-td-3">答题名称</td>
                        <td class="tj-td-2 t-center">答题时间</td>
                        <td class="tj-td-2 t-center">考试分数</td>
                        <td class="tj-td-2 t-center">考试明细</td>
                    </tr>
                    </thead>
                    <tbody id="examTBody">
                    <c:forEach items="${examPage.dataList}" var="p">
                        <tr examId="${p.id}">
                            <td class="tj-td-1 color-black"><span class="icon iconfont icon-minIcon8"></span>考试</td>
                            <td class="tj-td-3 color-black">${p.paperName}</td>
                            <td class="tj-td-2 t-center"><span>${p.time}</span></td>
                            <td class="tj-td-2 t-center"><span>${p.score}</span></td>
                            <td class="tj-td-2 t-center"><a href="${ctx}/mng/doctor/examDetail?historyId=${p.id}"
                                                            class="check-link" target="_blank">查看</a></td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
                <%@include file="/WEB-INF/include/examPageable.jsp" %>
                <form id="examForm" name="examForm" method="post">
                    <input type="hidden" name="doctorId" value="${info.id}"/>
                    <input type="hidden" name="pageNum" id="pageNum2" value="${examPage.pageNum}"/>
                    <input type="hidden" name="pageSize" id="pageSize2" value="${examPage.pageSize}">
                </form>
            </div>
        </div>

        <div class="tj-con subPage-marginTop">
            <div class="tj-content clearfix">
                <div class="tj-top clearfix">
                    <h3>问卷历史</h3>
                    <a href="${ctx}/mng/doc/export?docId=${info.id}&total=${surveyPage.total}&type=survey"
                       class="tj-more">导出Excel</a>
                </div>
                <table class="tj-table tj-table-re1 clearfix" id="surveyTable">
                    <thead>
                    <tr>
                        <td class="tj-td-1">会议类型</td>
                        <td class="tj-td-3">问卷名称</td>
                        <td class="tj-td-2 t-center">问卷时间</td>
                        <td class="tj-td-2 t-center">问卷明细</td>
                    </tr>
                    </thead>
                    <tbody id="surveyTBody">
                    <c:forEach items="${surveyPage.dataList}" var="p">
                        <tr surveyId="${p.id}">
                            <td class="tj-td-1 color-black"><span class="icon iconfont icon-minIcon17"></span>问卷</td>
                            <td class="tj-td-3 color-black">${p.paperName}</td>
                            <td class="tj-td-2 t-center"><span>${p.time}</span></td>
                            <td class="tj-td-2 t-center"><a href="${ctx}/mng/doctor/surveyDetail?historyId=${p.id}"
                                                            class="check-link" target="_blank">查看</a></td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
                <%@include file="/WEB-INF/include/surveyPageable.jsp" %>
                <form id="surveyForm" name="surveyForm" method="post">
                    <input type="hidden" name="doctorId" value="${info.id}"/>
                    <input type="hidden" name="pageNum" id="pageNum3" value="${surveyPage.pageNum}"/>
                    <input type="hidden" name="pageSize" id="pageSize3" value="${surveyPage.pageSize}">
                </form>
            </div>
        </div>
    </div>

    <div class="tab-bd" style="display: none;" id="send_view">
        <form action="${ctx}/func/msg/save" method="post" id="msgForm" name="msgForm">
            <input type="hidden" name="sendType" value="0">
            <input type="hidden" name="receiver" value="${info.id}">
            <div class="table-box-div1 ">
                <div class="table-top-box table-top-box-1pxBorder clearfix formrow">
                    <strong class="margin-r" id="choiceSendFlat">
                    <span class="checkboxIcon">
                        <input type="checkbox" id="flats_wx" name="flats" value="1" class="chk_1 chk-hook">
                        <label for="flats_wx" class="inline "><i class="ico"></i>&nbsp;&nbsp;发送到微信</label>
                    </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <span class="checkboxIcon">
                        <input type="checkbox" id="flats_app" name="flats" value="0" class="chk_1 chk-hook">
                        <label for="flats_app" class=" inline"><i class="ico"></i>&nbsp;&nbsp;发送到YaYa医师APP</label>
                    </span>
                    </strong>
                </div>

                <div class="sftz-box clearfix">
                    <div class="hz-detail-message">

                        <div class="formrow formTitle-sftz ">
                            <div class="formControls ">
                            <span class="formPage-select-item  pr">
                                <i class="formPage-select-arrow"></i>
                                <select name="notifyType" class="text-input formPage-select textareaTitle-hook">
                                    <option value="0" i="0">会议提醒</option>
                                    <option value="1" i="1">考试提醒</option>
                                    <!--<option value="textareaTitle3" i="2">调查问卷消息</option>-->
                                </select>
                            </span>
                            </div>
                        </div>

                        <div class="textareaForm">
                            <textarea name="content" cols="70" id="content" rows="10" placeholder="填写消息内容(不超过140字)"
                                      maxlength="140"></textarea>
                        </div>

                        <div class="formrow">
                        <span class="checkboxIcon">
                            <input type="hidden" name="msgType" value="1">
                            <input type="checkbox" id="msgTypeBox" value="1" checked disabled class="chk_1 chk-hook"/>
                            <label for="msgTypeBox" class="formTitle "><i class="ico"></i>&nbsp;会议链接</label>
                        </span>
                            <div class="formControls">
                            <span class="formPage-select-item  pr">
                                <i class="formPage-select-arrow"></i>
                                <select name="meetId" id="meetId"
                                        class="text-input formPage-select formPage-select-hook">
                                        <option value=""> --请选择会议-- </option>
                                        <c:forEach items="${meets}" var="meet">
                                            <option value="${meet.id}">${meet.meetName}</option>
                                        </c:forEach>
                                    </select>
                            </span>
                            </div>
                        </div>
                        </span>
                    </div>
                    <hr class="formHr"/>
                    <div class="btn-wrap">
                        <input type="button" class="button back-btn" style="cursor: pointer;" id="submitBtn"
                               value="确认设置">
                    </div>

                </div>


            </div>
        </form>
    </div>

    <div class="tab-bd" id="successView" style="display: none;">

        <div class="table-box-div1 ">
            <div class="normalBoxItem">
                <p><img src="${ctxStatic}/images/icon-send-1.png" alt=""></p>
                <p style="font-size:22px; color:#45ccce;">已发送消息</p>
                <div class="formControls t-center" style="margin-top:60px;">
                    <a  class="formButton formButton-max formButtonBlue-02" id="successBtn">返回继续</a>
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

<script>

    function checkForm() {
        var hasFlats = false;
        $("input[name='flats']").each(function () {
            if ($(this).is(":checked")) {
                hasFlats = true;
                return false;
            }
        });
        if (!hasFlats) {
            layer.tips("必须选中至少一个平台", "#choiceSendFlat", {tips: [1, '#000000']});
            return false;
        }

        var content = $("#content").val();
        if ($.trim(content) == '') {
            layer.tips("请输入消息内容", "#content", {tips: [1, '#000000']});
            $("#content").focus();
            return false;
        }


        if ($("#meetId").val() == '') {
            layer.tips("请选择会议", "#meetId");
            return false;
        }

        return true;
    }

    $(function () {
        $("#changeViewTab li").click(function () {
            var currId = $(this).attr("id");
            var otherId = $(this).siblings().attr("id");
            $(this).siblings().removeClass("cur");
            $(this).addClass("cur");
            $("#" + currId + "_view").show();
            $("#" + otherId + "_view").hide();
        });

        $("#msgType").change(function () {
            var checked = $(this).is(":checked");
            if (checked) {
                $("#meetId").removeAttr("disabled");
            } else {
                $("#meetId").attr("disabled", "true");
            }
        });

        $("#successBtn").click(function(){
            $("#successView").hide();
            $("#send_view").show();
        });

        $("#submitBtn").click(function () {
            if (checkForm()) {
                var index = layer.load(1, {
                    shade: [0.1, '#fff'] //0.1透明度的白色背景
                });
                $.post($("#msgForm").attr("action"), $("#msgForm").serialize(), function (data) {
                    layer.close(index);
                    $("#successView").show();
                    $("#send_view").hide();
                }, 'json');
            }
        });

        <!-- 秒转为时分-->
        function getTime(seconds) {
            var hour = 0;
            var minute = 0;
            if (seconds > 60) {
                minute = parseInt(seconds / 60);
                seconds = parseInt(seconds % 60);
                if (minute > 60) {
                    hour = parseInt(minute / 60);
                    minute = parseInt(minute % 60);
                }
                if (minute < 10) {
                    minute = "0" + minute;
                }
            }
            return [hour, minute];
        }

        var time1 = getTime(${info.videoTime});
        $('#hour').html(time1[0]);
        $('#minute').html(time1[1]);
        var time2 = getTime(${info.learnTime});
        $('#hour1').html(time2[0]);
        $('#minute1').html(time2[1]);

    })

</script>


</body>
</html>
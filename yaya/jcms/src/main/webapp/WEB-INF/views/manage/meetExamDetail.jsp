<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title> 考试弹出明细 </title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta name="Keywords" content="">
    <meta name="description" content="">
    <link href="${ctxStatic}/css/wenjuan_ht.css" rel="stylesheet" >
</head>
<body>


<!-- main -->
<div class="subPage-wrap clearfix">

    <div class="detail-box clearfix">
        <div class="fr" style="margin-top:12px;">
            <span class="mbox-top-ri"><img src="${detail.headimg}" class="img-radius img" width="32" height="32" alt="">&nbsp;</span>&nbsp;&nbsp;<a href="" class="color-blue">${detail.nickname}</a>&nbsp;&nbsp;${detail.title}&nbsp;&nbsp;
        </div>
        <div class="fl fl-txt">
            <span class="detail-status-icon">考试详情</span>
        </div>
    </div>

    <div class="tab-bd">

        <div class="tab-bd mettingForm">
            <div class="page-width clearfix">
                <div class="exam-info clearfix">
                    <p >你的得分&nbsp;&nbsp;<span class="num">${detail.score}</span>&nbsp;&nbsp;分</p>

                </div>

                <div class="add-metting-faq-content disabled-faq" >
                    <c:forEach items="${examList}" var="el" varStatus="status">
                        <div class="movie_box" style="border: 1px solid rgb(255, 255, 255);">
                            <div class="wjdc_list">
                                <li> <div class="tm_btitlt"><i class="nmb">${status.index+1}</i>. <i class="btwenzi">${el.title}</i></div> </li>&nbsp;&nbsp;
                                <c:choose>
                                    <c:when test="${el.qtype == 0}">
                                        <c:forEach items="${el.optionList}" var="ol">
                                            <c:if test="${ol.key == el.rightKey && el.rightKey == el.answer}">
                                                <li class="true"><label> <input name="${el.id}" type="radio" value="" disabled checked><span>${ol.key}  ${ol.value}</span>&nbsp;[正确]</label></li>
                                            </c:if>
                                            <c:if test="${ol.key == el.rightKey && el.rightKey != el.answer}">
                                                <li class="true"><label> <input name="${el.id}" type="radio" value="" disabled ><span>${ol.key}  ${ol.value}</span>&nbsp;[正确]</label></li>
                                            </c:if>
                                            <c:if test="${ol.key != el.rightKey && ol.key == el.answer}">
                                                <li class="error"><label> <input name="${el.id}" type="radio" value="" disabled checked><span>${ol.key}  ${ol.value}</span><span>&nbsp;[错误]</span></label></li>
                                            </c:if>
                                            <c:if test="${ol.key != el.rightKey && ol.key != el.answer }">
                                                <li><label> <input name="${el.id}" type="radio" value="" disabled><span>${ol.key}  ${ol.value}</span></label></li>
                                            </c:if>
                                        </c:forEach>
                                    </c:when>
                                    <c:when test="${el.qtype == 1}">
                                        <c:forEach items="${el.optionList}" var="ol">
                                            <c:if test="${fn:contains(el.rightKey,ol.key ) && fn:contains(el.answer,ol.key )}">
                                                <li class="true"><label> <input name="a" type="checkbox" value="" disabled checked><span>${ol.key} ${ol.value}</span>&nbsp;[正确]</label></li>
                                            </c:if>
                                            <c:if test="${fn:contains(el.rightKey,ol.key ) && !fn:contains(el.answer,ol.key )}">
                                                <li class="true"><label> <input name="a" type="checkbox" value="" disabled ><span>${ol.key} ${ol.value}</span>&nbsp;[正确]</label></li>
                                            </c:if>
                                            <c:if test="${!fn:contains(el.rightKey,ol.key ) && fn:contains(el.answer,ol.key )}">
                                                <li class="error"><label> <input name="a" type="checkbox" value="" disabled checked><span>${ol.key} ${ol.value}</span>&nbsp;[错误]</label></li>
                                            </c:if>
                                            <c:if test="${!fn:contains(el.rightKey,ol.key ) && !fn:contains(el.answer,ol.key )}">
                                                <li><label> <input name="a" type="checkbox" value="" disabled ><span>${ol.key} ${ol.value}</span></label></li>
                                            </c:if>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                                <li><textarea name="" cols="" rows="" class="input_wenbk btwen_text btwen_text_tk" disabled="disabled">${el.answer}</textarea></li>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="dx_box" data-t="0"></div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
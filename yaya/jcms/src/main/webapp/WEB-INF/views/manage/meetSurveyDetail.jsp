<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title> 问卷弹出明细 </title>
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
            <span class="mbox-top-ri"><img src="${detail.headimg}" class="img-radius img" width="32" height="32" alt="">&nbsp;</span>&nbsp;&nbsp;&nbsp;<a href="" class="color-blue" >${detail.nickname}</a>&nbsp;&nbsp;${detail.title}&nbsp;&nbsp;
        </div>
        <div class="fl fl-txt">
            <span class="detail-status-icon">问卷详情</span>
        </div>
    </div>

    <div class="tab-bd">
        <div class="tab-bd mettingForm">
            <div class="page-width clearfix">
                <div class="add-metting-faq-content disabled-faq" >
                    <c:forEach items="${questionList}" var="ql" varStatus="status">
                        <div class="movie_box" style="border: 1px solid rgb(255, 255, 255);">
                            <div class="wjdc_list">
                                <li> <div class="tm_btitlt"><i class="nmb">${status.index+1}</i>. <i class="btwenzi">${ql.title}</i></div> </li>&nbsp;&nbsp;
                            <c:choose>
                                <c:when test="${ql.qtype==0}" >
                                    <c:forEach items="${ql.optionList}" var="ol">
                                        <c:if test="${ql.answer == ol.key}">
                                            <li class="cur"><label> <input name="${ql.id}" type="radio" value="" disabled checked><span>${ol.key}.${ol.value}</span><span>&nbsp;[已选]</span></label></li>
                                        </c:if>
                                        <c:if test="${ql.answer != ol.key}">
                                            <li><label> <input name="${ql.id}" type="radio" value="" disabled><span>${ol.key}.${ol.value}</span></label></li>
                                        </c:if>
                                    </c:forEach>
                                </c:when>
                                <c:when test="${ql.qtype==1}">
                                    <c:forEach items="${ql.optionList}" var="ol">
                                        <c:if test="${fn:contains(ql.answer,ol.key )}">
                                            <li class="cur"><label> <input name="a" type="checkbox" value="" disabled checked><span>${ol.key}.${ol.value}</span>&nbsp;[已选]</label></li>
                                        </c:if>
                                        <c:if test="${!fn:contains(ql.answer,ol.key )}">
                                            <li><label> <input name="a" type="checkbox" value="" disabled ><span>${ol.key}.${ol.value}</span></label></li>
                                        </c:if>
                                    </c:forEach>

                                </c:when>

                                <c:otherwise>
                                <li><textarea name="" cols="" rows="" class="input_wenbk btwen_text btwen_text_tk" disabled="disabled">${ql.answer}</textarea></li>
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
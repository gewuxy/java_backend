<%--
  Created by IntelliJ IDEA.
  User: jianliang
  Date: 2017/12/11
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <title><fmt:message key="page.title.user.title"/> - <fmt:message key="page.title.authority"/></title>
    <%--<link rel="SHORTCUT ICON" href="./images/v2/icon.ico" />--%>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />

    <script src="${ctxStatic}/js/jquery.min.js"></script>
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="${ctxStatic}/js/layer/layer.js"></script>
    <!--[if lt IE 9]>
    <script src="${ctxStatic}/js/html5.js"></script>
    <![endif]-->
</head>
<script>
    $("#tid1").click(function () {
        var tid1Val = $('input:radio:checked').val();
        alert(tid1Val);
    })
    $("#tid2").click(function () {
        var tid2Val = $('input:radio:checked').val();
        alert(tid2Val);
    })
</script>
<body >
<div id="wrapper">
    <%@include file="../include/header.jsp" %>
    <div class="admin-content bg-gray" >
        <div class="page-width clearfix">
            <div class="user-module clearfix">
                <div class="row clearfix">
                    <div class="col-lg-4">
                        <%@include file="./left.jsp" %>
                    </div>
                    <div class="col-lg-8">
                        <%@include file="user_include.jsp" %>
                        <div class="user-content user-content-levelHeight item-radius member-mode">
                            <div class="member-mode-header clearfix">
                                <div class="fr">
                                    <div class="resource-label">
                                        <c:if test="${cspPackage.id == 1 || cspPackage.id == 2}" >
                                            <span>
                                                    <c:if test="${(cspPackage.usedMeetCount + cspPackage.hiddenMeetCount) > cspPackage.limitMeets}">
                                                        <i class="hot" style="color: red">
                                                                ${cspPackage.usedMeetCount + cspPackage.hiddenMeetCount}
                                                            </i>
                                                    </c:if>
                                                <c:if test="${cspPackage.usedMeetCount + cspPackage.hiddenMeetCount <= cspPackage.limitMeets}">
                                                        <i class="hot">
                                                                ${cspPackage.usedMeetCount}
                                                        </i>
                                                </c:if>
                                                <i class="muted">|</i>${cspPackage.limitMeets}</span>
                                        </c:if>
                                        <c:if test="${cspPackage.id == 3}">
                                            <span><i class="hot">${cspPackage.usedMeetCount + cspPackage.hiddenMeetCount}</i><i class="muted">|</i>∞</span>
                                        </c:if>
                                    </div>
                                    <p class="t-center"><fmt:message key="page.remind.meet.count"/></p>
                                </div>
                                <div class="fl">
                                    <div class="clearfix">
                                        <div class="fl"></div>
                                        <c:choose>
                                            <c:when test="${cspPackage.id == 1}">
                                                <div class="oh">
                                                    <h5 class="title"><fmt:message key="package.standard"/></h5>
                                                    <div class="member-mode-tips"><fmt:message key="page.remind.standard.time"/></div>
                                                </div>
                                            </c:when>
                                            <c:when test="${cspPackage.id == 3}">
                                                <c:choose>
                                                    <c:when test="${cspUserPackage.unlimited == true}">
                                                        <div class="fl member-grade"><img src="${ctxStatic}/images/member-icon-grade-02.png" alt=""></div>
                                                        <div class="oh">
                                                            <h5 class="title"><fmt:message key="package.professional"/></h5>
                                                            <div class="member-mode-tips"><fmt:message key="page.remind.professional.time"/></div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="fl member-grade"><img src="${ctxStatic}/images/member-icon-grade-02.png" alt=""></div>
                                                        <div class="oh" id="time">
                                                            <h5 class="title" ><fmt:message key="package.professional"/></h5>
                                                            <div class="member-mode-tips">${dateFormat}</div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="fl member-grade"><img src="${ctxStatic}/images/member-icon-grade-01.png" alt=""></div>
                                                <div class="oh">
                                                    <h5 class="title"><fmt:message key="package.premium"/></h5>
                                                    <div class="member-mode-tips">${dateFormat}</div>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>

                                    </div>
                                </div>
                            </div>
                            <div class="member-mode-main">
                                <div class="member-mode-fnList">
                                    <ul>
                                        <c:forEach var="info" items="${cspPackageInfos}">
                                        <c:if test="${info.iden =='LB'}">
                                        <li>
                                            <p><img src="${ctxStatic}/images/member-icon-01.png" alt=""></p>
                                            <p><fmt:message key="page.remind.member.descript.record"/></p>
                                        </li>
                                        </c:if>
                                        <c:if test="${info.iden =='ZB'}"><li>
                                        <p><img src="${ctxStatic}/images/member-icon-02.png" alt=""></p>
                                        <p><fmt:message key="page.remind.member.descript.live"/></p>
                                        </li>
                                        </c:if>
                                        <c:if test="${info.iden =='ZB' && info.packageId != 3}"><li>
                                        <p><img src="${ctxStatic}/images/member-icon-05.png" alt=""></p>
                                        <p>${limitMeets}</p>
                                        </li>
                                        </c:if>
                                        <c:if test="${info.iden =='ZB' && info.packageId == 3}"><li>
                                            <p><img src="${ctxStatic}/images/member-icon-05.png" alt=""></p>
                                            <p><fmt:message key="page.remind.professional.meet"/></p>
                                          </li>
                                        </c:if>
                                        <c:if test="${info.iden =='GG' && info.state == false}">
                                        <li>
                                        <p><img src="${ctxStatic}/images/member-icon-03-not.png" alt=""></p>
                                        <p class="color-gray-03"><fmt:message key="page.remind.member.descript.ads"/></p>
                                        </li>
                                        </c:if>
                                            <c:if test="${info.iden =='GG' && info.state == true}">
                                                <li>
                                                    <p><img src="${ctxStatic}/images/member-icon-03.png" alt=""></p>
                                                    <p><fmt:message key="page.remind.member.descript.ads"/></p>
                                                </li>
                                            </c:if>
                                        <c:if test="${info.iden =='SYB' && info.state == false}">
                                        <li>
                                        <p><img src="${ctxStatic}/images/member-icon-04-not.png" alt=""></p>
                                        <p class="color-gray-03"><fmt:message key="page.remind.member.descript.watermark.switch"/></p>
                                        </li>
                                        </c:if>
                                            <c:if test="${info.iden =='SYB' && info.state == true}">
                                                <li>
                                                    <p><img src="${ctxStatic}/images/member-icon-04.png" alt=""></p>
                                                    <p><fmt:message key="page.remind.member.descript.watermark.switch"/></p>
                                                </li>
                                            </c:if>
                                            <c:if test="${(info.iden =='SY' && info.state == true) || (info.iden =='SY' && info.packageId == 3)}">
                                                <li>
                                                    <p><img src="${ctxStatic}/images/member-icon-04.png" alt=""></p>
                                                    <p><fmt:message key="page.remind.member.descript.watermark"/></p>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                            <div class="member-mode-footer member-footer-position t-center">
                                <a href="javascript:;" type="button" class="button login-button buttonBlue member-buy-hook" id="btn"><fmt:message key="page.button.up.package"/></a>
                                <p><a href="${ctx}/index/17103116063862386794" target="_blank"><fmt:message key="page.question.help"/></a></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="admin-bottom">
        <div class="page-width clearfix">
            <p class="t-center"><fmt:message key="page.foot.company.message"/></p>
        </div>
    </div>
</div>
<!--弹出 充值-->
<div class="cancel-popup-box" id="pkBuyMsg">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><a href="${ctx}/mgr/user/memberManage"><img src="${ctxStatic}/images/popup-close.png" alt=""></a></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form >
                <div class="cancel-popup-main">
                    <p><fmt:message key="page.remind.pay"/></p>
                    <div class="admin-button t-right">
                        <a href="${ctx}/mgr/user/memberManage"  class="button color-blue min-btn layui-layer-close" ><fmt:message key="page.remind.pay.question"/></a>
                        <input type="submit"  type="reLoad" class="button buttonBlue item-radius min-btn"  value="<fmt:message key='page.remind.pay.success'/>">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!--弹出 提示-->
<div class="cancel-popup-box" id="pkSuccessMsg">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close clearMsg"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="cancel-popup-main">
                    <p id="backMsg"></p>
                </div>
                <div class="admin-button t-right">
                    <input type="button" class="button buttonBlue item-radius min-btn layui-layer-close clearMsg" value="<fmt:message key='page.button.pay.tips'/>"/>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    $(function(){
        var pkSuccessMsg = '${successMsg}';
        //购买提示不为空显示
        if(isNotEmpty(pkSuccessMsg)){
            //弹出提示
            layer.open({
                type: 1,
                area: ['450px', '250px'],
                fix: false, //不固定
                title:false,
                closeBtn:0,
                content: $('#pkSuccessMsg'),
                success:function(){
                    $("#backMsg").html(pkSuccessMsg);
                },
            });
        }

        //清除提示信息
        $(".clearMsg").on('click',function(){
            ajaxGet('${ctx}/mgr/pay/update/msg', {}, function(data){});
        });

        $("#config_6").parent().attr("class","cur");
        $("#btn").click(function () {
            layer.open({
                type: 2,
                title: false,
                fix: false, //不固定
                skin: 'member-popup-zIndex', //没有背景色
                shadeClose: false,
                offset: '70px',
                closeBtn: 0, //不显示关闭按钮
                shade: 0.1,
                area: ['1116px', '930px'],
                content: '${ctx}/mgr/pay/mark',
                success:function(layero,index){
                    //付款弹出层
                    var body = layer.getChildFrame('body', index);
                    body.find(".cancel-hook").on('click',function(){
                        layer.open({
                            type: 1,
                            area: ['500px', '250px'],
                            fix: false, //不固定
                            title:false,
                            closeBtn:0,
                            content: $('#pkBuyMsg')
                        });
                    })
                }
            })
        });
    })
</script>
</body>
</html>
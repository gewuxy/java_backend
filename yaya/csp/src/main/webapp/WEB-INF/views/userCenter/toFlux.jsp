<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html >
<head>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <title><fmt:message key="page.header.flux"/>-<fmt:message key="page.title.user.title"/>-<fmt:message key="page.common.appName"/> </title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="https://js.braintreegateway.com/web/3.24.1/js/client.min.js"></script>
    <script src="https://js.braintreegateway.com/web/3.24.1/js/paypal-checkout.min.js"></script>
    <script src="https://www.paypalobjects.com/api/checkout.js" data-version-4></script>

</head>


<body>
<c:set var="isCN" value="${csp_locale eq 'zh_CN'}"/>
<div id="wrapper">
    <%@include file="../include/header.jsp" %>
    <div class="admin-content bg-gray" >
        <div class="page-width clearfix">
            <div class="user-module clearfix">
                <div class="row clearfix">
                    <div class="col-lg-4">
                        <%@include file="left.jsp"%>
                    </div>
                    <div class="col-lg-8">
                        <%@include file="user_include.jsp" %>
                        <div class="user-content item-radius">
                            <h4><fmt:message key="page.words.video.record"/> <span><fmt:message key="page.words.remaining.flux"/> <i><fmt:formatNumber type="number" value="${flux/1024}" maxFractionDigits="2"/></i>G</span></h4>
                            <table class="table-box-1">
                                <colgroup>
                                    <col class="col-w-4">
                                    <col class="col-w-2">
                                    <col class="col-w-2">
                                    <col class="col-w-2">
                                </colgroup>
                                <tbody>
                                <c:if test="${page.dataList != null && fn:length(page.dataList) != 0}">
                                    <c:forEach items="${page.dataList}" var="v">
                                        <tr>
                                            <td class="col-w-4 color-black">${v.meetName}</td>
                                            <c:if test="${csp_locale =='zh_CN'}">
                                                <td class="col-w-3"><fmt:message key="page.words.flux.usage"/>${v.expense}M</td>
                                            </c:if>
                                            <c:if test="${csp_locale !='zh_CN'}">
                                                <td class="col-w-3">${v.expense}M<fmt:message key="page.words.flux.usage"/></td>
                                            </c:if>

                                            <c:if test="${not empty v.replayUrl}">
                                                <c:if test="${v.downloadCount < 5}">
                                                    <input type="hidden" id="count_${v.courseId}" value="${v.downloadCount}">
                                                    <td class="col-w-2 t-right"><a href="javascript:;" id="download_${v.courseId}" count="${v.downloadCount}" meetName="${v.meetName}" courseId="${v.courseId}" class="color-blue downButton-hook"><fmt:message key="page.words.video.download"/></a></td>

                                                </c:if>
                                                <c:if test="${v.downloadCount >= 5}">
                                                    <td class="col-w-2 t-right"><a href="javascript:;" class="color-gray videoListEmail-hook"><fmt:message key="page.words.get.videoUrl"/></a></td>
                                                </c:if>
                                            </c:if>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                            <%@include file="../include/pageable.jsp"%>
                            <form id="pageForm" name="pageForm" method="post" action="${ctx}/mgr/user/toFlux">
                                <input type="hidden" name="pageNum">
                            </form>

                        </div>


                        <div class="user-content item-radius pay-mode">
                            <form action="${ctx}/mgr/charge/toCharge" name="submitForm" id="submitForm" method="post" target="_blank">
                            <div class="formrow flow">

                                <div class="formTitle color-black" <c:if test="${csp_locale == 'en_US'}">style="line-height:1.3;"</c:if>>
                                    <fmt:message key="page.words.flux.charge"/>
                                </div>


                                <div class="formControls">
                                    <div class="pay-mode-list flow-mode-list" >
                                        <label for="tid1" class="item item-radius" >
                                            <input type="radio" name="flux" class="none" value="5" id="tid1">
                                            5G
                                        </label>
                                        <label for="tid2" class="item item-radius" >
                                            <input type="radio" name="flux" class="none" value="25" id="tid2" >
                                            25G
                                        </label>
                                        <label for="tid3" class="item item-radius pay-on" >
                                            <input type="radio" name="flux" class="none" value="100" id="tid3" checked>
                                            100G
                                        </label>
                                        <label for="tid4" class="item item-radius" >
                                            <input type="radio" name="flux" class="none" value="500" id="tid4" >
                                            500G
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <div class="formrow " >
                                <div class="formTitle color-black" <c:if test="${csp_locale == 'en_US'}">style="line-height:1.3;"</c:if>>
                                    <fmt:message key="page.words.charge.method"/>
                                </div>
                                <div class="formControls" >
                                    <div class="pay-mode-list CN-hook ${isCN ? '':'none'}" id="chinese">
                                        <label for="id11" class="item item-radius pay-on">
                                            <input type="radio" name="channel" class="none" value="alipay_pc_direct" id="id11" checked>
                                            <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                        </label>
                                        <label for="id21" class="item item-radius">
                                            <input type="radio" name="channel" class="none" value="wx_pub_qr" id="id21">
                                            <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                        </label>
                                        <%--<label for="id31" class="item item-radius">--%>
                                            <%--<input type="radio" name="channel" class="none" value="upacp_pc" id="id31">--%>
                                            <%--<img src="${ctxStatic}/images/img/user-icon-unionpay.png" alt="">--%>
                                        <%--</label>--%>
                                    </div>
                                    <div class="pay-mode-list EN-hook ${!isCN ? '':'none'}">
                                        <label for="id5" class="item item-radius pay-on">
                                            <input type="radio" name="channel" class="none" value="paypal" id="id5">
                                            <img src="${ctxStatic}/images/img/user-icon-paypal.png" alt="">
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="formrow money">
                                <div class="formTitle color-black"><fmt:message key="page.words.charge.amount"/></div>
                                <div class="formControls">
                                    <span class="color-black">
                                        <c:if test="${csp_locale == 'zh_CN'}">
                                        <span class="payNum">200</span> <span id="yuan"><fmt:message key="page.words.charge.currency"/></span></span> <span class="color-green">(100G<fmt:message key="page.words.flux"/>)
                                        </c:if>
                                        <c:if test="${csp_locale != 'zh_CN'}">
                                            <span class="payNum">35</span> <span id="yuan"><fmt:message key="page.words.charge.currency"/></span></span> <span class="color-green">(100G)
                                        </c:if>

                                    </span>
                                    <span class="money-state">
                                        <label for="currency-cn" class="cn ${isCN ? 'on':''}">
                                            <input type="radio" name="currency" id="currency-cn" class="none" value="CN">
                                            CNY
                                        </label>
                                        <label for="currency-en" class="en ${!isCN ? 'on':''}">
                                            <input type="radio" name="currency" id="currency-en" class="none" value="EN">
                                            USD
                                        </label>
                                    </span>
                                </div>
                            </div>
                            <div class="formrow last">
                                <input  type="button" class="button login-button buttonBlue cancel-hook last" id="submitBtn" value="<fmt:message key="page.words.charge.confirm"/>">
                            </div>
                            </form>
                        </div>

                    </div>

                </div>
            </div>
        </div>
    </div>
    <%@include file="../include/footer.jsp"%>
</div>


<!--弹出 充值-->
<div class="cancel-popup-box" style="display: none;">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><a href="${ctx}/mgr/user/toFlux"><img src="${ctxStatic}/images/popup-close.png" alt=""></a></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form >
                <div class="cancel-popup-main">
                    <p><fmt:message key="page.words.charge.tips"/></p>
                    <div class="admin-button t-right">
                        <a href="${ctx}/mgr/user/toFlux"  class="button color-blue min-btn layui-layer-close" ><fmt:message key="page.words.charge.problem"/></a>
                        <input type="submit"  type="reLoad" class="button buttonBlue item-radius min-btn"
                               value="<fmt:message key="page.words.charge.success"/>">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!--弹出 邮件获取-->
<div class="videoListEmail-popup-box" style="display: none;">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="cancel-popup-main">
                    <c:if test="${csp_locale == 'en_US'}">
                        <p class="color-black" style="font-size:20px; margin-bottom:10px;"><fmt:message key="page.words.get.videoUrl.tips1"/></p>
                        <p><fmt:message key="page.words.get.videoUrl.tips2"/> service@CSPmeeting.com. <fmt:message key="page.words.get.videoUrl.tips3"/></p>
                    </c:if>
                    <c:if test="${csp_locale != 'en_US'}">
                        <p class="color-black" style="font-size:20px; margin-bottom:10px;"><fmt:message key="page.words.get.videoUrl.tips1"/></p>
                        <p><fmt:message key="page.words.get.videoUrl.tips2"/></p>
                        <p><a href="mailto:service@CSPmeeting.com">service@CSPmeeting.com</a></p>
                        <p><fmt:message key="page.words.get.videoUrl.tips3"/></p>
                    </c:if>

                </div>

            </form>
        </div>
    </div>
</div>



<script>

    var local = '${csp_locale}';
    var currency;
    if(local == "zh_CN"){
        currency = 1;
        $("#chinese").attr("class","pay-mode-list CN-hook");
        $("#abroad").attr("class","pay-mode-list EN-hook none");
    }else{
        currency = 2;
        $("#chinese").attr("class","pay-mode-list CN-hook none");
        $("#abroad").attr("class","pay-mode-list EN-hook");
    }


    $(function(){
        $.ajaxSetup({
            async : false
        });

        $("#config_4").parent().attr("class", "cur");

        $(".pay-mode-list label").click(function(){
            $(this).addClass('pay-on').siblings().removeClass('pay-on');
            console.log($('input[name="channel"]:checked').val());
        });
        


        $("#submitBtn").click(function () {

            if(currency == 1){  //人民币支付
                var channel = $('input[name="channel"]:checked').val();
                if(channel == "paypal"){
                    $("#id5").removeAttr("checked");
                    $('#chinese').find('label[class="item item-radius pay-on"]').find('input').prop("checked",true);
                }
                $("#submitForm").submit();
            }else{
                var flux = $('input[name="flux"]:checked').val();
                window.open("${ctx}/mgr/charge/createOrder?flux="+flux);
            }
                $("#submitForm").submit();
                //触发支付弹出窗
                    layer.open({
                        type: 1,
                        area: ['560px', '250px'],
                        fix: false, //不固定
                        title:false,
                        closeBtn:0,
                        content: $('.cancel-popup-box'),
                        success:function(){
                        },
                        cancel :function(){
                        },
                    });


        });

        $("input[type='reLoad']").click(function () {
            window.location.href="${ctx}/mgr/user/toFlux";
        });


        $('input[name="flux"]').click(function () {
            var flux = $(this).val();
            changeMoney(flux,currency);

        });

        $(".downButton-hook").click(function () {
            var meetName = $(this).attr("meetName");
            var courseId = $(this).attr("courseId");
            var count = $("#count_"+courseId).val();

            if(parseInt(count) >= 5){
                layer.open({
                    type: 1,
                    area: ['560px', '320px'],
                    fix: false, //不固定
                    title:false,
                    closeBtn:0,
                    btn: ["<fmt:message key="page.words.layer.btn"/>"],
                    content: $('.videoListEmail-popup-box'),
                    success:function(){

                    },
                    cancel :function(){

                    },
                });

                $(this).attr("class","color-gray videoListEmail-hook");
                $(this).html("<fmt:message key="page.words.get.videoUrl"/>");
            }else{
                layer.msg("<fmt:message key="page.words.video.download.tips1"/>"+ count+"<fmt:message key="page.words.video.download.tips2"/>",{time:1000},function () {
                    $("#count_"+courseId).val(parseInt(count) + 1);
                    window.location.href="${ctx}/mgr/user/download?courseId="+courseId+"&meetName="+meetName;
                });
            }

        });

    });



    function changeMoney(flux,currency){
        if(currency == 1){  //中文
            var channel = $('input[name="channel"]:checked').val();
            $(".payNum").html(flux*2);
            if(local == "zh_CN"){
                $("#yuan").html("元");
                $(".color-green").html("("+flux+"G流量)");
            }else{
                $("#yuan").html("CNY");
                $(".color-green").html("("+flux+"G)");
            }
        }else{
            if(flux == 5){
                $(".payNum").html(1.75);
            }else if(flux == 25){
                $(".payNum").html(8.75);
            }else if(flux == 100){
                $(".payNum").html(35);
            }else{
                $(".payNum").html(175);
            }
            if(local == "zh_CN"){
                $("#yuan").html("美元");
                $(".color-green").html("("+flux+"G流量)");
            }else{
                $("#yuan").html("USD");
                $(".color-green").html("("+flux+"G)");
            }

        }
    }





    //货币切换
    $(".money-state label").click(function(){
        $(this).addClass('on').siblings().removeClass('on');
        var currencyValue = $(this).parents('.pay-mode').find('input[name="currency"]:checked').val();
        if( currencyValue == 'CN'){
            currency = 1;
            $(this).parents('.pay-mode').find('.CN-hook').removeClass('none').siblings().addClass('none');
            //替换货币说明
            $(this).parents('.pay-mode').find('.explain-cn-hook').removeClass('none').siblings().addClass('none');
        } else if ( currencyValue =='EN' ){
            currency = 2;
            $(this).parents('.pay-mode').find('.EN-hook').removeClass('none').siblings().addClass('none');
            //替换货币说明
            $(this).parents('.pay-mode').find('.explain-en-hook').removeClass('none').siblings().addClass('none');
        }
        var flux = $('input[name="flux"]:checked').val();
        changeMoney(flux,currency);
    });


    //邮件获取
    $('.videoListEmail-hook').on('click',function(){
        layer.open({
            type: 1,
            area: ['600px', '400px'],
            fix: false, //不固定
            title:false,
            closeBtn:0,
            btn: ["<fmt:message key="page.words.layer.btn"/>"],
            content: $('.videoListEmail-popup-box'),
            success:function(){

            },
            cancel :function(){

            },
        });
    });




</script>

</body>
</html>




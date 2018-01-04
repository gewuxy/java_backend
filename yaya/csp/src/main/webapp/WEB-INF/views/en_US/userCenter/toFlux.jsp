<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Network Flow Management - Profile - CSPmeeting</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style-EN.css">
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>
    <script src="https://js.braintreegateway.com/web/3.24.1/js/client.min.js"></script>
    <script src="https://js.braintreegateway.com/web/3.24.1/js/paypal-checkout.min.js"></script>
    <script src="https://www.paypalobjects.com/api/checkout.js" data-version-4></script>

</head>


<body>
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
                            <h4>Live Stream History <span>Network Flow Balance: <i><fmt:formatNumber type="number" value="${flux/1024}" maxFractionDigits="2"/></i>G</span></h4>
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
                                            <td class="col-w-3">${v.expense}M consumed</td>
                                            <c:if test="${not empty v.replayUrl}">
                                                <c:if test="${v.downloadCount < 5}">
                                                    <input type="hidden" id="count_${v.courseId}" value="${v.downloadCount}">
                                                    <td class="col-w-2 t-right"><a href="javascript:;" id="download_${v.courseId}" count="${v.downloadCount}" meetName="${v.meetName}" courseId="${v.courseId}" class="color-blue downButton-hook">Video Download</a></td>

                                                </c:if>
                                                <c:if test="${v.downloadCount >= 5}">
                                                    <td class="col-w-2 t-right"><a href="javascript:;" class="color-gray videoListEmail-hook">Expired</a></td>
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
                                    <div class="formTitle color-black" style="line-height:1.3;">Recharge Amount</div>
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
                                    <div class="formTitle color-black" style="line-height:1.3;">Recharge<br />Channel</div>
                                    <div class="formControls" >
                                        <div class="pay-mode-list CN-hook none" id="chinese">
                                            <label for="id11" class="item item-radius pay-on">
                                                <input type="radio" name="channel" class="none" value="alipay_pc_direct" id="id11" checked>
                                                <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                            </label>
                                            <label for="id21" class="item item-radius">
                                                <input type="radio" name="channel" class="none" value="wx_pub_qr" id="id21">
                                                <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                            </label>
                                        </div>
                                        <div class="pay-mode-list EN-hook " id="abroad">
                                            <label for="id5" class="item item-radius pay-on">
                                                <input type="radio" name="channel" class="none" value="paypal" id="id5">
                                                <img src="${ctxStatic}/images/img/user-icon-paypal.png" alt="">
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="formrow money">
                                    <div class="formTitle color-black">Payment</div>
                                    <div class="formControls">
                                        <span class="color-black"><span class="payNum">35</span> <span id="yuan">USD</span></span> <span class="color-green">(100G)</span>
                                        <span class="money-state">
                                        <label for="currency-cn" class="cn">
                                            <input type="radio" name="currency" id="currency-cn" class="none" value="CN">
                                            CNY
                                        </label>
                                        <label for="currency-en" class="en on">
                                            <input type="radio" name="currency" id="currency-en" class="none" value="EN">
                                            USD
                                        </label>
                                    </span>
                                    </div>
                                </div>
                                <div class="formrow last">
                                    <input  type="button" class="button login-button buttonBlue cancel-hook last" id="submitBtn" value="Confirm">
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
<div class="cancel-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><a href="${ctx}/mgr/user/toFlux"><img src="${ctxStatic}/images/popup-close.png" alt=""></a></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form >
                <div class="cancel-popup-main">
                    <p>Please complete the payment in the page of recharge. DO NOT close this window until done.</p>
                    <div class="admin-button t-right">
                        <a href="${ctx}/mgr/user/toFlux"  class="button color-blue min-btn layui-layer-close" >Fail & Retry</a>
                        <input type="submit"  type="reLoad" class="button buttonBlue item-radius min-btn" value="Done">
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
                    <p class="color-black" style="font-size:20px; margin-bottom:10px;">You have used up the allowed download times. Please apply download link via email.</p>
                    <p>You may send email with title as "user nickname + meeting name + live date" to service@CSPmeeting.com. We would verify and send back a download link to your email as soon as possible.</p>
                    <!--<div class="admin-button t-right">-->
                    <!--<a href="javascript:;" class="button color-blue min-btn layui-layer-close" >付款遇到问题，重试</a>-->
                    <!--<input type="submit" class="button buttonBlue item-radius min-btn" value="我已付款成功">-->
                    <!--</div>-->
                </div>

            </form>
        </div>
    </div>
</div>



<script>

    var currency = 2;

    $(function(){
        $.ajaxSetup({
            async : false
        });

        $("#config_4").parent().attr("class", "cur");

        $(".pay-mode-list label").click(function(){
            $(this).addClass('pay-on').siblings().removeClass('pay-on');
            console.log($('input[name="channel"]:checked').val());
        });
        
        $("#flux").blur(function () {
            checkFlux();
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
                    btn: ["OK"],
                    content: $('.videoListEmail-popup-box'),
                    success:function(){

                    },
                    cancel :function(){

                    },
                });

                $(this).attr("class","color-gray videoListEmail-hook");
                $(this).html("邮件获取");
            }else{
                layer.msg("Live video download: "+ count +"/5 times",{time:1000},function () {
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
            $("#yuan").html("CNY");
            $(".color-green").html("("+flux+"G)");
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
            $("#yuan").html("USD");
            $(".color-green").html("("+flux+"G)");
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
            area: ['560px', '360px'],
            fix: false, //不固定
            title:false,
            closeBtn:0,
            btn: ["OK"],
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




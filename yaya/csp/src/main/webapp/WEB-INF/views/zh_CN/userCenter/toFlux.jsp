<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>流量管理-个人中心-会讲</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
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
                            <h4>视频直播记录 <span>剩余流量 <i><fmt:formatNumber type="number" value="${flux/1024}" maxFractionDigits="2"/></i>G</span></h4>
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
                                            <td class="col-w-3">消耗${v.expense}M</td>
                                            <c:if test="${not empty v.replayUrl}">
                                                <c:if test="${v.downloadCount < 5}">
                                                    <input type="hidden" id="count_${v.courseId}" value="${v.downloadCount}">
                                                    <td class="col-w-2 t-right"><a href="javascript:;" id="download_${v.courseId}" count="${v.downloadCount}" meetName="${v.meetName}" courseId="${v.courseId}" class="color-blue downButton-hook">下载视频</a></td>

                                                </c:if>
                                                <c:if test="${v.downloadCount >= 5}">
                                                    <td class="col-w-2 t-right"><a href="javascript:;" class="color-gray videoListEmail-hook">邮件获取</a></td>
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
                                <div class="formTitle color-black">充值流量</div>
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
                                <div class="formTitle color-black">充值方式</div>
                                <div class="formControls" >
                                    <div class="pay-mode-list CN-hook" id="chinese">
                                        <label for="id11" class="item item-radius pay-on">
                                            <input type="radio" name="channel" class="none" value="alipay_pc_direct" id="id11" checked>
                                            <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                        </label>
                                        <label for="id21" class="item item-radius">
                                            <input type="radio" name="channel" class="none" value="wx_pub_qr" id="id21">
                                            <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                        </label>
                                        <label for="id31" class="item item-radius">
                                            <input type="radio" name="channel" class="none" value="upacp_pc" id="id31">
                                            <img src="${ctxStatic}/images/img/user-icon-unionpay.png" alt="">
                                        </label>
                                    </div>
                                    <div class="pay-mode-list EN-hook none">
                                        <label for="id5" class="item item-radius pay-on">
                                            <input type="radio" name="channel" class="none" value="paypal" id="id5">
                                            <img src="${ctxStatic}/images/img/user-icon-paypal.png" alt="">
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="formrow money">
                                <div class="formTitle color-black">支付金额</div>
                                <div class="formControls">
                                    <span class="color-black"><span class="payNum">200</span> <span id="yuan">元</span></span> <span class="color-green">(100G流量)</span>
                                    <span class="money-state">
                                        <label for="currency-cn" class="cn on">
                                            <input type="radio" name="currency" id="currency-cn" class="none" value="CN">
                                            CNY
                                        </label>
                                        <label for="currency-en" class="en">
                                            <input type="radio" name="currency" id="currency-en" class="none" value="EN">
                                            USD
                                        </label>
                                    </span>
                                </div>
                            </div>
                            <div class="formrow last">
                                <input  type="button" class="button login-button buttonBlue cancel-hook last" id="submitBtn" value="确认支付">
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
                    <p>请在充值页面完成付款，付款完成前请不要关闭此窗口</p>
                    <div class="admin-button t-right">
                        <a href="${ctx}/mgr/user/toFlux"  class="button color-blue min-btn layui-layer-close" >付款遇到问题，重试</a>
                        <input type="submit"  type="reLoad" class="button buttonBlue item-radius min-btn"
                               value="我已付款成功">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!--弹出 邮件获取-->
<div class="videoListEmail-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><img src="${ctxStatic}/images/popup-close.png" alt=""></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form action="">
                <div class="cancel-popup-main">
                    <p class="color-black" style="font-size:20px; margin-bottom:10px;">已超出下载权限，如需下载可通过邮件申请。</p>
                    <p>发送邮件 “用户名+会议名称+直播日期” 至邮箱</p>
                    <p><a href="mailto:service@CSPmeeting.com">service@CSPmeeting.com</a></p>
                    <p>我们会尽快审核并返回下载链接到您的发件邮箱。</p>
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

    var currency = 1;

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
                    btn: ["我知道了"],
                    content: $('.videoListEmail-popup-box'),
                    success:function(){

                    },
                    cancel :function(){

                    },
                });

                $(this).attr("class","color-gray videoListEmail-hook");
                $(this).html("邮件获取");
            }else{
                layer.msg("会议视频提供5次有效下载，已下载"+ count+"次",{time:1000},function () {
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
            $("#yuan").html("元");
            $(".color-green").html("("+flux+"G流量)");
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
            $("#yuan").html("美元");
            $(".color-green").html("("+flux+"G流量)");
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
            area: ['560px', '320px'],
            fix: false, //不固定
            title:false,
            closeBtn:0,
            btn: ["我知道了"],
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




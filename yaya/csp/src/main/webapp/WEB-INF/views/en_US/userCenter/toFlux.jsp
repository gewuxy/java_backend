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
                            <h4>Live Stream History <span>Network Flow Balance: <i>${flux == 0 || empty flux? 0:flux/1024}</i>G</span></h4>
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
                                            <td class="col-w-4 color-black">${v.meetName}.avi</td>
                                            <td class="col-w-3">${v.expense}M consumed</td>
                                            <c:if test="${v.expireDay > 0}">

                                                <td class="col-w-2"><a href="${v.downloadUrl}" class="color-blue">	Video Download</a></td>
                                                <td class="col-w-2 color-green1">Expired in ${v.expireDay} day(s)</td>
                                            </c:if>
                                            <c:if test="${v.expireDay == 0}">
                                                <td class="col-w-2"><span class="color-gray">	Expired</span></td>
                                                <td class="col-w-2 color-green1">&nbsp;</td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>
                                    <%@include file="../include/pageable.jsp"%>
                                    <form id="pageForm" name="pageForm" method="post" action="${ctx}/mgr/user/toFlux">
                                        <input type="hidden" name="pageNum">
                                    </form>
                                </c:if>
                                </tbody>
                            </table>

                        </div>
                        <div class="user-content item-radius pay-mode">
                            <form action="${ctx}/mgr/charge/toCharge" name="submitForm" id="submitForm" method="post" target="_blank">
                            <div class="formrow flow login-form-item">
                                <div class="formTitle color-black" style="line-height: 1.3;">Recharge Network Flow</div>
                                <div class="formControls">
                                    <label for="" class="pr">
                                        <input type="text" class="textInput" placeholder="Input the amount" id="flux" name="flux" oninput="fill()">
                                        <span >G</span>
                                    </label>
                                    <p ><span class="explain">1 GB = 1 USD</span></p>
                                    <span class="cells-block error none" id="errSpan"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span>姓名不能为空</span> </span>
                                </div>
                            </div>
                            <div class="formrow " style="margin-bottom:20px;" >
                                <div class="formTitle color-black" style="line-height: 1.8;">Recharge<br />
                                    Channel</div>
                                <div class="formControls">
                                    <div class="pay-mode-list" style="width:80%" onclick="checkChannel()">
                                        <label for="paypal" class="item item-radius">
                                            <input type="radio" name="channel" class="none" value="paypal" id="paypal">
                                            <img src="${ctxStatic}/images/img/user-icon-paypal.png" alt="">
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="formrow money">
                                <div class="formTitle color-black">Payment&nbsp;&nbsp;¥</div>
                                <div class="formControls">
                                    <div class="payNum" id="money">0.00</div>
                                </div>
                            </div>
                                <div class="formrow last">
                                    <input type="button" class="button login-button buttonBlue cancel-hook last" id="submitBtn" value="Confirm">
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

<form target="_blank" action="${ctx}/mgr/charge/createOrder" name="paypalForm" id="paypalForm" method="post">
    <input type="hidden" class="flux" name="flux" value="0">
</form>

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




<script>

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
            if(checkFlux()){
                var channel = $('input[name="channel"]:checked').val();
                    var flux = $("#flux").val();
                    $(".flux").val(flux);
                    $("#paypalForm").submit();

                //触发充值弹出窗
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
            }

        });

        $("input[type='reLoad']").click(function () {
            window.location.href="${ctx}/mgr/user/toFlux";
        });



    });

    function fill(){
        if(checkFlux()){
            checkChannel();
        }else{
            $("#money").html("0.00");
        }
    }

    function checkChannel() {
        var channel = $('input[name="channel"]:checked').val();
        $("#money").html(($("#flux").val())*1 + ".00");

    }

    function checkFlux() {
        if(!Number.isInteger($("#flux").val()/1)){
            $("#errSpan").attr("class","cells-block error");
            $("#errSpan").find('span').html("Please Enter Integer");
            return false;
        }else if(($("#flux").val()/1)<1){
            $("#errSpan").attr("class","cells-block error");
            $("#errSpan").find('span').html("Recharge flow must be greater than 1G");
            return false;
        } else if(($("#flux").val()/1)>100000){
            layer.msg("Recharge flow can not exceed 100 thousand ");
            return false;
        }else{
            $("#errSpan").attr("class","cells-block error none");
            return true;
        }
    }



</script>

</body>
</html>




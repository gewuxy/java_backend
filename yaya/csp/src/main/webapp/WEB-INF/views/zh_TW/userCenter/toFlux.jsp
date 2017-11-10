<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>流量管理-個人中心-CSPmeeting</title>
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
                            <h4>視頻直播記錄 <span>剩余流量 <i>${flux == 0 || empty flux? 0:flux/1024}</i>G</span></h4>
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
                                            <td class="col-w-3">消耗${v.expense}M</td>
                                            <c:if test="${v.expireDay > 0}">

                                                <td class="col-w-2"><a href="${v.downloadUrl}" class="color-blue">下載視頻</a></td>
                                                <td class="col-w-2 color-green1">${v.expireDay}天後過期</td>
                                            </c:if>
                                            <c:if test="${v.expireDay == 0}">
                                                <td class="col-w-2"><span class="color-gray">已過期</span></td>
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
                                    <div class="formTitle color-black">充值流量</div>
                                    <div class="formControls">
                                        <label for="" class="pr">
                                            <input type="text" class="textInput" placeholder="輸入需充值的流量" id="flux" name="flux" oninput="fill()">
                                            <span >G</span>
                                        </label>
                                        <p ><span class="explain">1G流量=2元</span></p>
                                        <span class="cells-block error none" id="errSpan"><img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;<span>姓名不能為空</span> </span>
                                    </div>
                                </div>
                                <div class="formrow " style="margin-bottom:20px;" >
                                    <div class="formTitle color-black">充值方式</div>
                                    <div class="formControls">
                                        <div class="pay-mode-list" style="width:80%" onclick="checkChannel()">
                                            <label for="id1" class="item item-radius pay-on">
                                                <input type="radio" name="channel" class="none" value="alipay_pc_direct" checked="checked" id="id1">
                                                <img src="${ctxStatic}/images/img/user-icon-alipay.png" alt="">
                                            </label>
                                            <label for="id2" class="item item-radius">
                                                <input type="radio" name="channel" class="none" value="wx_wap" id="id2">
                                                <img src="${ctxStatic}/images/img/user-icon-wechat.png" alt="">
                                            </label>
                                            <label for="id3" class="item item-radius">
                                                <input type="radio" name="channel" class="none" value="upacp_pc" id="id3">
                                                <img src="${ctxStatic}/images/img/user-icon-unionpay.png" alt="">
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="formrow money">
                                    <div class="formTitle color-black">支付&nbsp;&nbsp;¥</div>
                                    <div class="formControls">
                                        <div class="payNum" id="money">0.00</div>
                                    </div>
                                </div>
                                <div class="formrow last">
                                    <input type="button" class="button login-button buttonBlue cancel-hook last" id="submitBtn" value="確認支付">
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

<!--彈出 充值-->
<div class="cancel-popup-box">
    <div class="layer-hospital-popup">
        <div class="layer-hospital-popup-title">
            <strong>&nbsp;</strong>
            <div class="layui-layer-close"><a href="${ctx}/mgr/user/toFlux"><img src="${ctxStatic}/images/popup-close.png" alt=""></a></div>
        </div>
        <div class="layer-hospital-popup-main ">
            <form >
                <div class="cancel-popup-main">
                    <p>請在充值頁面完成付款，付款完成前請不要關閉此窗口</p>
                    <div class="admin-button t-right">
                        <a href="${ctx}/mgr/user/toFlux"  class="button color-blue min-btn layui-layer-close" >付款遇到問題，重試</a>
                        <input type="submit"  type="reLoad" class="button buttonBlue item-radius min-btn" value="我已付款成功">
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
                if(channel != "paypal"){
                    $("#submitForm").submit();
                }else{
                    var flux = $("#flux").val();
                    $(".flux").val(flux);
                    $("#paypalForm").submit();

                }

                //觸發彈出窗
                //投稿
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
            var channel = $('input[name="channel"]:checked').val();
            if(channel != "paypal"){
                $("#money").html(($("#flux").val())*2 + ".00");
            }else{
                $("#money").html(($("#flux").val())*1 + ".00");
            }
        }else{
            $("#money").html("0.00");
        }
    }

    function checkChannel() {
        var channel = $('input[name="channel"]:checked').val();
        if(channel != "paypal"){
            $("#money").html(($("#flux").val())*2 + ".00");
        }else{
            $("#money").html(($("#flux").val())*1 + ".00");
        }
    }

    function checkFlux() {
        if(!Number.isInteger($("#flux").val()/1)){
            $("#errSpan").attr("class","cells-block error");
            $("#errSpan").find('span').html("請輸入整數");
            return false;
        }else if(($("#flux").val()/1)<1){
            $("#errSpan").attr("class","cells-block error");
            $("#errSpan").html("充值流量必須大於1G");
            return false;
        } else if(($("#flux").val()/1)>100000){
            layer.msg("充值流量不能超過10萬");
            return false;
        }else{
            $("#errSpan").attr("class","cells-block error none");
            return true;
        }
    }



</script>

</body>
</html>




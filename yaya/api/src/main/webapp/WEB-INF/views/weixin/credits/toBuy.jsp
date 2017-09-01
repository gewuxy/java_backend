<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/7/31
  Time: 15:19
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>象数充值</title>
    <%@include file="/WEB-INF/include/page_context_weixin.jsp"%>
</head>
<body class="gary-reg">

<div class="warp ">

    <div class="item">
        <div class="user-details login-input">
            <div class="user-list">
                <ul>
                    <li class="last t-left"><span class="rowTitle">象数余额</span><span class="color-blue">${credits.credit}</span></li>
                </ul>
            </div>
            <div class="user-list user-margin-top">
                <ul>
                    <li class=""><span class="rowTitle">充值象数</span><span class="oh pr"><label for="" ><input class="t-right" type="tel" placeholder="0" name="credit" id="credit" readonly></label>&nbsp;象数</span></li>
                    <li class="last  overflowText"><span class="rowTitle">支付金额</span><label for="" ><input class="t-right" type="tel" placeholder="请输入金额" name="cost" id="cost">&nbsp;元</label></li>
                </ul>
            </div>
            <div class="user-list login-input-item user-margin-top">
                <ul>
                    <!--<li class="user-pay-cells">
                        <span class="rowTitle">
                            <span class="icon-alipay"></span>&nbsp;&nbsp;支付宝</span>
                        <span class="radiobox-rightIcon color-black">
                            <input type="radio" name="payType" id="checkbox_1_2" value="1" class="chk_1 radio-hook">
                            <label for="checkbox_1_2"> <i class="ico checkboxCurrent"></i></label>
                        </span>
                    </li>-->
                    <li class="user-pay-cells last ">
                        <span class="rowTitle"><span class="icon-wechat"></span>&nbsp;&nbsp;微信</span>
                        <span class="radiobox-rightIcon color-black">
                            <input type="radio" name="payType" id="checkbox_1_3" checked value="0" class="chk_1 radio-hook">
                            <label for="checkbox_1_3"> <i class="ico checkboxCurrent"></i></label>
                        </span>
                    </li>
                </ul>
            </div>
            <div class="item-area">
                <div class="formrow t-center">
                    <input type="button" class="button radius blue-button" value="充值象数" id="submitBtn"/>
                </div>
            </div>
        </div>
    </div>


</div>
<script>
    var intReg = /^[1-9][\d]*$/i;

    var wxPayRequest =
    {
        "appId":"",//公众号名称，由商户传入
        "timeStamp":"",//时间戳，自1970年以来的秒数
        "nonceStr":"",//随机串
        "package":"",
        "signType":"",//微信签名方式：MD5
        "paySign":""//微信签名
    }

    function onBridgeReady(){
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest',
            {
                "appId":wxPayRequest.appId,
                "timeStamp":wxPayRequest.timeStamp,
                "nonceStr":wxPayRequest.nonceStr,
                "package":wxPayRequest.package,
                "signType":"MD5",
                "paySign":wxPayRequest.paySign
            },
            function(res){
                if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                    window.location.href = "${ctx}/weixin/user/credits?refresh=true";
                }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                    layer.msg('支付取消');
                }else if(res.err_msg == "get_brand_wcpay_request:fail" ){
                    layer.msg(res.err_desc);
                }
                // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
            }
        );
    }

    function callWxPay(){
        if (typeof WeixinJSBridge == "undefined"){
            if( document.addEventListener ){
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            }else if (document.attachEvent){
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        }else{
            onBridgeReady();
        }
    }


    function unifiedOrder(payType, cost){

        if (payType == 0){
            $.get('${ctx}/weixin/credits/unified/order',{'payType':payType, "total_fee":cost}, function (data) {
                if (data.code == 0){
                    var payRequest = data.data;
                    wxPayRequest.appId = payRequest.appId;
                    wxPayRequest.timeStamp = payRequest.timeStamp;
                    wxPayRequest.nonceStr = payRequest.nonceStr;
                    wxPayRequest.signType = "MD5";
                    wxPayRequest.package = payRequest.package;
                    wxPayRequest.paySign = payRequest.paySign;
                    //alert(JSON.stringify(wxPayRequest));
                    callWxPay();
                } else {//失败
                    layer.msg("微信统一下单失败");
                }
            },'json');
        }
    }

    $(function(){
        $("#cost").keyup(function(){
            var cost = $(this).val();
            if(!intReg.test(cost)){
                $(this).val("");
                cost = 0;
            }
            $("#credit").val(cost*10);
        });



        $("#submitBtn").click(function () {
            var cost = $.trim($("#cost").val());
            if (!intReg.test(cost)){
                layer.msg("请输入金额");
                return false;
            }
            var payType = getPayType();
            unifiedOrder(payType, cost);

        });

        function getPayType(){
            var payType = 0;
            $("input[name='payType']").each(function () {
                if ($(this).prop("checked")){
                    payType = $(this).val();
                    return false;
                }
            });
            return payType;
        }
    });

</script>
</body>

</html>

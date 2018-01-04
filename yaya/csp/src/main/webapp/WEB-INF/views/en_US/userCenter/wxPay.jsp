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
    <script type="text/javascript" src="${ctxStatic}/js/qrcode.js"></script>

</head>


<body>
<div id="wrapper">
    <%@include file="../include/header.jsp" %>
    <div class="admin-content bg-gray" >
        <div class="page-width clearfix">
            <div class="subPage-head item-shadow item-radius clearfix">
                <h3 class="title"><i class="icon icon-header-point"></i>Wechat Payment</h3>
            </div>
            <!--正常流程-->
            <div class="subPage-main item-shadow item-radius wechat-state" >
                <div id="codeDiv">
                    <h3 class="title" id="title"></h3>
                    <div class="img t-center" id="code"></div>
                    <p><img src="${ctxStatic}/images/icon-payment.png" alt="">&nbsp;Scan with WeChat App</p>
                    <p id="active">Expiring in <span class="color-red" id="time">50</span> s. Please refresh to retrieve the QR code if expired.</p>
                    <p class="color-red" id="inActive" style="display: none">QR code expired. Click to <a href="#" class="color-blue" onclick="useOldCode()">retrieve</a> the code.</p>
                </div>

                <!--切换  输入新密码-->
                <div class="login-box-main position-message-login" id="successDiv" style="display: none">
                    <form action="">
                        <div class="login-form-item">
                            <div class="login-message-text login-message-text-2">
                                <p><img src="${ctxStatic}/images/icon-succeed.png" alt=""></p>
                                <p class="t-center color-blue">Payment completed！</p>
                                <p>Recharge amount：&nbsp;<span class="color-blue" id="successAmount">200</span>&nbsp;CNY</p>
                            </div>
                            <input href="login-01.html" type="button" id="close" class="button login-button buttonBlue last" style="width:500px; margin:0 auto;" value="关闭页面">
                        </div>
                    </form>
                </div>

            </div>




        </div>
    </div>

    <%@include file="../include/footer.jsp"%>
</div>







<script>

    var InterValObj; //timer变量，控制时间
    var InterFinish;
    var count = 50; //间隔函数，1秒执行
    var curCount;//当前剩余秒数
    var money ;
    var oldCodeUrl;
    var finish = false;


    $(function () {
        curCount = count;
        var charge = ${charge};
        var credential = charge.credential;
        var qrcodeUrl = credential.wx_pub_qr;
        oldCodeUrl = qrcodeUrl;
        createQrCode(qrcodeUrl,"code");

        var amount = (charge.amount)/100;
        money = amount;
        $("#title").append('Amount：<strong class="price color-blue">' + amount +'</strong>&nbsp;CNY');


        $("#time").text(curCount);
        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
        var tradeId = charge.order_no;
        //异步查询订单状态
        InterFinish = window.setInterval(checkStatus,3000,tradeId);

        $("#close").click(function () {
            close();
        });

    })





    //timer处理函数
    function SetRemainTime() {
        if (curCount == 0) {
            //二维码超时
            window.clearInterval(InterValObj);//停止计时器
            if(finish){
                //订单已完成
                window.clearInterval(InterFinish);
                $("#codeDiv").attr("style","display: none");
                $("#successAmount").text(money);
                $("#successDiv").removeAttr("style");
            }else{
                //用无效二维码代替原来的二维码
                $("#code").empty();
                createQrCode("weixin://wxpay/bizpayurl?pr=sdfgdgfsdg","code");
                $("#inActive").removeAttr("style");
                $("#active").attr("style","display: none");
            }

        }
        else {
            if(finish){
                window.clearInterval(InterFinish);
                $("#codeDiv").attr("style","display: none");
                $("#successAmount").text(money);
                $("#successDiv").removeAttr("style");
            }else{
                curCount--;
                $("#time").text(curCount);
            }
        }
    }

    function checkStatus(tradeId){
        $.ajax({
            type: "GET",
            url: "${ctx}/mgr/charge/orderStatus",
            data: {"tradeId":tradeId,"time":new Date()},
            dataType: "json",
            timeout: 4000,
            success: function(result) {
                if(result.code==0){
                    finish = true;
                }
            }
        });

    }

    function createQrCode(url,elementId) {
        var qrcode = new QRCode(document.getElementById(elementId), {
            width : 300,//设置宽高
            height : 300
        });
        qrcode.makeCode(url);
        $('.wechat-state').find('.img').find('img').attr('style','margin: 15px auto 30px');
    }

    function useOldCode() {
        $("#code").empty();
        if(oldCodeUrl != undefined && oldCodeUrl != ''){
            createQrCode(oldCodeUrl,"code");
            $("#active").removeAttr("style");
            $("#inActive").attr("style","display: none");
            curCount = count;
            InterValObj = window.setInterval(SetRemainTime, 1000);
        }else{
            window.location.reload();
        }

    }




</script>

</body>
</html>




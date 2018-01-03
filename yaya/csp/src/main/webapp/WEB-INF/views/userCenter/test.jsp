<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/7/007
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript">
        function onVisaCheckoutReady(){
            V.init( {
                apikey: "25GGEV6PG0FDLK8DV2JJ21jXR0iSxqZ0SGes9KX06xl1wVTVI",
                paymentRequest:{
                    currencyCode: "USD",
                    subtotal: "11.00"
                }
            });
            V.on("payment.success", function(payment)
            {alert(JSON.stringify(payment)); });
            V.on("payment.cancel", function(payment)
            {alert(JSON.stringify(payment)); });
            V.on("payment.error", function(payment, error)
            {alert(JSON.stringify(error)); });
        }
    </script>
</head>

<body>
<img alt="Visa Checkout" class="v-button" role="button"
     src="https://sandbox.secure.checkout.visa.com/wallet-services-web/xo/button.png"/>
<script type="text/javascript"
        src="https://sandbox-assets.secure.checkout.visa.com/
checkout-widget/resources/js/integration/v1/sdk.js">
</script>
</body>
</html>

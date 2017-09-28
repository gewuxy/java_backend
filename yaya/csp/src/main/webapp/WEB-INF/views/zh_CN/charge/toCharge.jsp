
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta charset="utf-8" />
    <meta name="keywords" content="社交，社交平台，医生，论坛，专属医生社交平台，专业医师，专业药师，执业医师，执业药师，执业资格证，医疗工具，实用，药草园">
    <title>重设密码</title>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/index.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/completer.css"/>
</head>
<body class="bg-gray">
<div id="header" class="header-border">
    <div class="h_con">
        <a href="http://www.medcn.com" class="c_left fl">
            <img src="${ctxStatic}/images/logo.png" alt="logo" />
        </a>
    </div>
</div>
<!--内容区域-->
<div class="warp ">
    <div class="page-width">
        <div class="reg-index-box clearfix">

            <h3>重置密码</h3>
            <div class="reg-index-main" style="padding:80px 270px;">
                <fieldset>
                    <form  name="resetForm" id="resetForm"  method="post">
                        <div class="formrow clearfix">
                            金额:<input type="text"  name="amount"/>
                            支付方式：<input type="text" name="channel">
                        </div>
                        <div class="t-center formMargin-top">
                            <button class="index-reg-button" id="submit">确认</button>
                        </div>
                    </form>

                </fieldset>
            </div>

        </div>
    </div>
</div>
<script src="${ctxStatic}/js/pingpp.js"></script>
<script>

    $(function(){
        $("#submit").click(function(){
                $.post('${ctx}/api/charge/toCharge',{'amount':1,'channel':'alipay_pc_direct'},function(result){
                    alert("te");
                    if (result.code == 0){
                        alert(123);
                        pingpp.createPayment(result.data, function(result, err){
                            console.log(result);
                            console.log(err.msg);
                            console.log(err.extra);
                            if (result == "success") {
                                // 只有微信公众账号 wx_pub 支付成功的结果会在这里返回，其他的支付结果都会跳转到 extra 中对应的 URL。
                            } else if (result == "fail") {
                                alert("error");
                                // charge 不正确或者微信公众账号支付失败时会在此处返回
                            } else if (result == "cancel") {
                                // 微信公众账号支付取消支付
                            }
                        });
                    }
                },'json');


        });
    });


</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/11/1
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="admin-header">
    <div class="page-width clearfix">
        <div class="fr">
            <!--登录前-->
            <div class="login-header">
                <a href="${ctx}/login" class="login-header-button" title="登录">${not empty username ? username : "登录"}&nbsp;&nbsp;<span><img src="${ctxStatic}/images/admin-user-icon.png" alt=""></span></a>
                <a href="javascript:;" class="index-download index-qrcode">下载App<span class="qrcode-01 qrcode-top"><img src="${ctxStatic}/upload/img/qrcode.png" alt=""></span></a>
                <%@include file="/WEB-INF/include/switch_language.jsp"%>
            </div>
        </div>
        <div class="fl">
            <a class="logo" href="${ctx}"></a>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        var qrcode = $('.index-qrcode');
        qrcode.on({
            mouseenter:function() {
                $(this).find('.qrcode-01').show();
            },
            mouseleave:function() {
                $(this).find('.qrcode-01').hide();
            }
        })

    });

</script>
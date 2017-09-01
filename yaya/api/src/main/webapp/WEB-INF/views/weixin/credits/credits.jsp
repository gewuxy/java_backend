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
    <title>我的象数</title>
    <%@include file="/WEB-INF/include/page_context_weixin.jsp"%>
</head>
<body>

<div class="warp ">

    <div class="item">
        <div class="user-money">
            <div class="user-money-numberItem" >
                <div class="user-money-numberItem-bottom"></div>
                <div class="user-money-numberItem-bottom-drop"></div>
                <div class="user-money-numberItem-box">
                    <div class="user-money-numberItem-title">我的象数</div>
                    <div class="user-money-numberItem-num">${credits.credit}</div>
                </div>
                <!--<div class="user-moeny-bg echarts-box" id="echartsMoney"></div>-->
            </div>
            <div class="item-area">
                <div class="formrow t-center">
                    <a href="${ctx}/weixin/credits/pay" class="button radius blue-button" >充值象数</a>
                    <a href="${ctx}/weixin/user/credits/details" class="button radius" style="margin-top:.3rem;">象数明细</a>
                </div>
            </div>
            <div class="t-center" style="margin:.6rem 0; padding-bottom:.5rem; font-size:.3rem;">
                <p>了解象数 <a href="${ctx}/view/article/17061510101320742806" class="color-blue">使用规则</a>&nbsp;<span class="icon-issue"></span></p>
            </div>
        </div>
    </div>


</div>
<style>
    /*.echarts-box { height: 3.8rem; margin-bottom: .45rem; width:100%  }*/
    /*.echarts-box div{ width:100%  }*/
</style>
<script>
    $(function(){
        if("${param.refresh}"){
            layer.msg("充值象数成功");
        }
    });
</script>
</body>

</html>

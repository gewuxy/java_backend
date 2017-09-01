<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

	<title>象数管理</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta name="Keywords" content="">
    <meta name="description" content="">
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
</head>
<body>



<!-- main -->
<div class="g-main clearfix">
    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-07.png" alt="">
                </div>
                <div class="oh">
                    <p><strong>账号管理</strong></p>
                    <p>账号信息管理</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->

    <div class="tab-hd">

        <ul class="tab-list clearfix">
            <li>
                <a href="${ctx}/mng/account/info">账号设置<i></i></a>
            </li>
            <li class="cur">
                <a href="${ctx}/mng/account/xsInfo">象数管理<i></i></a>
            </li>
        </ul>
    </div>

    <div class="tj-con clearfix">
        <div class="tj-content clearfix">
            <div class="fl clearfix pay-fl-box">
                <h4 class="pay-title">象数积分</h4>
                <div class="pay-num">
                    <strong class="num">${credit.credit}</strong>
                </div>
                <p class="color-gray t-center">了解如何使用请阅读 <a href="http://www.medcn.com:8080/v7/view/article/17061510101320742806" class="color-blue" target="_blank">使用说明书</a></p>
            </div>
            <div class="oh clearfix">
                <table class="tj-table tj-table-re1 clearfix" style="width: 100%;" id="xsTable">
                    <thead class="thead-div notBorder">
                    <tr>
                        <td >象数交易事件</td>
                        <td>时间</td>
                    </tr>
                    </thead>
                    <tbody id="xsTBody">
                    <c:forEach items="${history.dataList}" var="p">
                        <tr>
                            <td>${p.description}</td>
                            <td class="color-gray"><fmt:formatDate value="${p.costTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <%--<tr>
                        <td>参加骨科会议支付 <strong class="color-black">100</strong> 象数</td>
                        <td class="color-gray">2016-11-18 18:27</td>
                    </tr>
--%>
                </table>
                <%@include file="/WEB-INF/include/xsPageable.jsp"%>
                <form id="xsForm" name="xsForm" method="post" >
                    <input type="hidden" name="pageNum" id="pageNum" value="${history.pageNum}" />
                    <input type="hidden" name="pageSize" id="pageSize" value="${history.pageSize}">
                </form>
            </div>
        </div>
    </div>
    <div class="tj-con mar-top-bd subPage-marginTop clearfix">
        <!--<div class="xs-con-box my-mlist-shedow clearfix">-->
            <!--<div class="fl pay-le-box">-->
                <!--<div class="xs-fl-box">-->
                    <!--<p class="clearfix">-->
                        <!--<span>-->
                            <!--象数积分-->
                            <!--<i class="xs-txt-1">588</i>-->
                        <!--</span>-->
                    <!--</p>-->
                    <!--<p class="xs-p-lin clearfix">-->
                        <!--了解如何使用请阅读<a href="" class="sy-info">使用说明</a>-->
                    <!--</p>-->
                    <!--<h3 class="pay-txt">支付方式</h3>-->
                    <!--<p class="pay-bank">-->
                        <!--<input type="radio" name="pay" checked><img src="${ctxStatic}/images/u11810.png" alt="">-->
                    <!--</p>-->
                <!--</div>-->
            <!--</div>-->
            <!--<div class="fl pay-ri-box">-->
                <!--<h3 class="pay-txt">充值数量</h3>-->
                <!--<p class="pay-bank clearfix">-->
                    <!--<input type="text" class="border-input pay-input" placeholder="10的倍数">-->
                    <!--<span class="pay-money">-->
                        <!--<span>换算人民币 <i>0 元</i></span>-->
                        <!--10 象数= 人民币1 元-->
                    <!--</span>-->
                <!--</p>-->
                <!--<p class="clearfix">-->
                    <!--<button class="queren-btn">确认充值</button>-->
                <!--</p>-->
                <!--<p class="clearfix">充值超过100元可以开具发票，一年内有效</p>-->
                <!--<p>详情请联系客服:020-38601688</p>-->
            <!--</div>-->
        <!--</div>-->
        <div class="my-top clearfix">
            <div class="fl">支付方式</div>
        </div>
        <div class="xs-con-box my-mlist-shedow clearfix">
            <div class="fl pay-le-box">
                <div class="xs-fl-box">
                    <p class="pay-bank">
                        <span class="radioboxIcon">
						<input type="radio" name="radio" checked="true" id="checkbox_3" class="chk_1 chk-hook">
						<label for="checkbox_3" class="formTitle "><i class="ico checkboxCurrent"></i>&nbsp;<img src="${ctxStatic}/images/zfb_icon.png" alt=""></label>
					</span>
                    </p>
                </div>
            </div>
            <div class="fl pay-ri-box">
                <h3 class="pay-txt">充值数量</h3>
                <p class="pay-bank clearfix">
                    <input type="text" class="border-input pay-input" placeholder="最少充值10象数" id="amount"  oninput="fill()">
                    <span class="pay-money">
                        <span>换算人民币 <i class="color-blue" id="money">0 </i>元</span>
                        10 象数= 人民币1 元
                    </span>
                </p>
                <p class="clearfix">
                    <button class="cancel-btn"  type="button" onclick="submitForm()">确认充值</button>
                </p>
                <form id="rechargeFrom" name="rechargeForm" method="post" action="${ctx}/web/alipay/recharge" target="_blank">
                    <input type="hidden" name="totalAmount" id="totalAmount" value="">
                    <input type="hidden" name="subject" id="subject" value="象数充值">
                </form>
            </div>
        </div>
    </div>
</div>
<!--mask-wrap-->
<div class="mask-wrap">
    <div class="m-layer-bd-1">
        <div class="m-tb-div">
            <div class="table-box-div1 ra-border table-box-div2 sj-box-tb m-table-box">
                <table class="table-box-3">
                    <thead>
                        <tr>
                            <td colspan="3">
                                <div class="tb-prelative">
                                    交易记录
                                    <span class="close-btn-1 cancel-button"><img src="${ctxStatic}/images/cha.png"></span>
                                </div>
                            </td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>充值金额</td>
                            <td>获得象数</td>
                            <td>时间</td>
                        </tr>
                        <tr>
                            <td>
                                100元
                            </td>
                            <td>100象数</td>
                            <td>
                                2016-10-09 21:30
                            </td>
                        </tr>
                        <tr>
                            <td>
                                100元
                            </td>
                            <td>100象数</td>
                            <td>
                                2016-10-09 21:30
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<!--/mask-wrap end-->
<script src="${ctxStatic}/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${ctxStatic}/js/main.js"></script>

<script>


    <!--将充值数量同步为金额-->
    function fill(){
        $("#money").html(($("#amount").val())/10);
    }

    <!--提交支付表单-->
    function submitForm(){
        if(!Number.isInteger($("#amount").val()/1)){
            layer.msg("请输入整数");
        }else if($("#amount").val()<10){
            layer.msg("充值数量必须大于10");
        }
        else if($("#amount").val()>100000){
            layer.msg("充值数量不能超过10万");
        }else{
            $("#totalAmount").val(($("#amount").val())/10);
            $("#rechargeFrom").submit();
        }
    }
</script>
</body>
</html>
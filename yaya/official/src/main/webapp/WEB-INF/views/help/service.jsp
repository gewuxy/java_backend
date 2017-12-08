<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/3/7
  Time: 10:33
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>服务与收费 | 敬信科技</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
    <%@ include file="/WEB-INF/include/common_js.jsp" %>
</head>
<body>
<div id="wrapper">
<%@include file="/WEB-INF/include/header_help.jsp"%>
<div class="v2-helpPage-main clearfix">
    <div class="page-width clearfix">
        <div class="v2-helpPage-Menu clearfix">
            <ul>
                <li ><a href="${ctx}/help/solution">产品解答</a></li>
                <li class="current"><a >服务与收费</a></li>
                <li><a href="${ctx}/help/feedback">意见反馈</a></li>
                <li><a href="${ctx}/help/contribute">我要投稿</a></li>
            </ul>
        </div>
        <div class="v2-helpPage-item clearfix">
            <h6>Q：PRM患者管理系统如何收费？</h6>
            <p>A：PRM系统为用户提供5种套餐服务，分别为基础版、季度版、年费版、高级版、定制版。具体收费和享受服务如下：</p>
            <div class="formrow clearfix">
                <ul class="formPriceList clearfix">
                    <li>
                        <a href="javascript:;" data-text="免费使用">
                            <h4 class="title ">基础版</h4>
                            <p class="price"><em>0</em>元/不限时<br>免费使用</p>
                            <p class="info">·3个在线会议<br>·500人会议上限<br>·微信通知</p>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;" data-text="799">
                            <h4 class="title" style="">季度版</h4>
                            <p class="price price-maxHeight"><em>799</em>元/3个月</p>
                            <p class="info">·10个在线会议<br>·2000人会议上限<br>·微信通知</p>
                        </a>
                    </li>
                    <li class="cur">
                        <a href="javascript:;" data-text="2599">
                            <h4 class="title">年费版</h4>
                            <p class="price price-maxHeight"><em>2599</em>元/年</p>
                            <p class="info">·20个在线会议<br>·2000人会议上限<br>·微信通知</p>
                        </a>
                    </li>
                    <li class="">
                        <a href="javascript:;" data-text="3999">
                            <h4 class="title">高级版</h4>
                            <p class="price price-maxHeight"><em>3999</em>元/年</p>
                            <p class="info">·30个在线会议<br>·3000人会议上限<br>·微信通知</p>
                        </a>
                    </li>
                    <li class="last">
                        <a href="javascript:;" data-text="4999">
                            <h4 class="title">定制版</h4>
                            <p class="price price-maxHeight"><em>4999</em>元/年</p>
                            <p class="info">·不限会议数量<br>·3000人会议上限<br>·微信通知</p>
                        </a>
                    </li>
                </ul>

            </div>
        </div>
        <div class="v2-helpPage-item clearfix">
            <p style="margin-bottom:20px;">Q：YaYa医师专业医学会议数字化管理平台如何收费？</p>
            <div class="formrow">
                <table class="formPriceTable fs14 formInfoTable" style="width:100%" cellspacing="0">
                    <colgroup>
                        <col class="col3">
                        <col class="col2">
                        <col class="col4">
                        <col class="col4">
                    </colgroup>
                    <tbody>
                    <tr>
                        <td colspan="2"><strong>服务说明</strong></td>
                        <td class="color-green1"><strong>基础版</strong></td>
                        <td class="color-blue"><strong>专业版</strong></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="color-blue"><strong>价格</strong></td>
                        <td><strong><em>10000元</em></strong></td>
                        <td><strong><em>50000元</em></strong></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="color-blue"><strong>使用时间</strong></td>
                        <td><strong>12个月</strong></td>
                        <td><strong>12个月</strong></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="color-blue"><strong>会议人数</strong></td>
                        <td><strong>300人</strong></td>
                        <td><strong>不限</strong></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="color-blue"><strong>升级服务</strong></td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                    </tr>
                    <tr>
                        <td rowspan="7"><strong>会议功能</strong></td>
                        <td>在线发布会议数量</td>
                        <td><strong>50个/年</strong></td>
                        <td><strong>不限</strong></td>
                    </tr>
                    <tr>
                        <td>非视频会议</td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                    </tr>
                    <tr>
                        <td>病例讨论/考试</td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                    </tr>
                    <tr>
                        <td>问卷投票</td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                    </tr>
                    <tr>
                        <td>视频会议</td>
                        <td colspan="2" rowspan="2">
                            <strong>按实际流量计费，<br>平均每人两小时消耗大概1G流量，1G=10象数</strong>
                        </td>
                    </tr>
                    <tr>
                        <td>现场直播</td>
                    </tr>
                    <tr>
                        <td>会议签到</td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                    </tr>
                    <tr>
                        <td><strong>学术资料空间</strong></td>
                        <td>资料上传</td>
                        <td><strong>1G课件上传</strong></td>
                        <td><strong>10G课件上传</strong></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><strong>资源平台</strong></td>
                        <td>学术会议转载</td>
                        <td colspan="2"><strong>学术内容流转平台<br>公众号自行设定</strong></td>
                    </tr>
                    <tr>
                        <td>专业考试题库</td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                    </tr>
                    <tr>
                        <td rowspan="5"><strong>数据分析</strong></td>
                        <td>单个会议数据分析</td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                    </tr>
                    <tr>
                        <td>会议总量数据分析</td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                    </tr>
                    <tr>
                        <td>用户维度分析</td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                    </tr>
                    <tr>
                        <td>内容维度分析</td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                    </tr>
                    <tr>
                        <td>转载数据分析</td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><strong>互动消息</strong></td>
                        <td>一对一消息</td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                    </tr>
                    <tr>
                        <td>群发消息</td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                        <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                    </tr>

                    </tbody>
                </table>
            </div>


        </div>
    </div>

</div>
</div>
    <%@include file="/WEB-INF/include/footer.jsp"%>
</div>
<div class="gotop-wrapper index-gotop">
    <a class="gotop" href="javascript:;" >回到顶部</a>
</div>
<%@ include file="/WEB-INF/include/common_js.jsp" %>
</body>
</html>

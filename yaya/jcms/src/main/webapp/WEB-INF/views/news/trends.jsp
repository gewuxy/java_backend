<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/3/6
  Time: 14:38
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>新闻列表</title>
    <%@include file="/WEB-INF/include/staticResource.jsp"%>
</head>
<body>
<div id="wrapper">
    <%@include file="/WEB-INF/include/header_common.jsp"%>
    <div class="v2-banner v2-banner-listBg">
        <div class="page-width clearfix">

            <!-- S slideshow -->
            <div class="slideshow carousel clearfix" style="height:700px; overflow:hidden;">
                <div id="carousel-05">
                    <div class="carousel-item">
                        <div class="carousel-img">
                            <a href="#">
                                <img src="${statics}/images/upload/_list-banner.png" alt="" />
                            </a>
                        </div>
                    </div>
                </div>
                <div class="carousel-btn carousel-btn-fixed" id="carousel-page-05"></div>

            </div>
            <!-- E slideshow -->


        </div>

    </div>

    <div class="v2-sub-main" style="padding:120px 0 80px;">
        <div class="page-width clearfix" style="width:980px;">
            <div class="row">
                <div class="col-lg-12">
                    <div class="v2-news-graphic">
                        <c:forEach items="${page.dataList}" var="news">
                            <div class="v2-news-graphic-item clearfix">
                                <div class="fl"><a href="${base}/news/viewtrend/${news.id}">
                                <c:choose>
                                    <c:when test="${news.articleImg != null}">
                                        <img src="${news.articleImg}" alt=""></a>
                                    </c:when>
                                </c:choose>
                                </div>
                                <div class="oh">
                                    <h3><a href="${base}/news/viewtrend/${news.id}">${news.title}</a></h3>
                                    <p class="v2-news-graphic-info">${news.summary}</p>
                                    <p><span class="time"><fmt:formatDate value="${news.createTime}" pattern="yyyy/MM/dd"/></span>&nbsp;&nbsp;&nbsp;&nbsp;<span>来源：${news.xfrom}</span></p>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                    <div class="v2-page-box">
                        <a <c:if test="${page.pageNum>1}">href="javascript:page(${page.pageNum-1})"</c:if> class="v2-page-box-prev" title="上一页"></a>
                            <c:forEach begin="1" step="1" end="${page.pages}" var="pn">
                                <a href="javascript:page(${pn})" <c:if test="${pn==page.pageNum}">class="cur"</c:if> >${pn}</a>
                            </c:forEach>
                        <a <c:if test="${page.pageNum<page.pages}">href="javascript:page(${page.pageNum+1})"</c:if> class="v2-page-box-next" title="下一页"></a>
                    </div>
                    <form action="${base}/news/trends" method="post" name="pageForm" id="pageForm">
                        <input type="hidden" name="pageNo" id="pageNo" value="${page.pageNum}"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
<%@include file="/WEB-INF/include/footer.jsp"%>
</div>
<div class="gotop-wrapper index-gotop">
    <a class="gotop" href="javascript:" >回到顶部</a>
</div>
<script src="${statics}/js/v2/stickUp.min.js"></script>
<script src="${statics}/js/v2/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript">
    function page(pn){
        $("#pageNo").val(pn);
        $("#pageForm").submit();
    }

    /*固定栏*/
    jQuery(function($) {
        $(document).ready( function() {
            $('.fixed-nav').stickUp({
                marginTop: 'auto'
            });
        });
    });


</script>
<script type="text/javascript">

    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount;//当前剩余秒数

    function sendMessage() {
        curCount = count;
        //设置button效果，开始计时
        $("#btnSendCode").attr("disabled", "true");
        $("#btnSendCode").val(curCount + "S");
        $("#btnSendCode").addClass("getCodeButton-current");
        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
        //向后台发送处理数据
//            $.ajax({
//                type: "POST", //用POST方式传输
//                dataType: "text", //数据格式:JSON
//                url: 'Login.ashx', //目标地址
//                data: "dealType=" + dealType +"&uid=" + uid + "&code=" + code,
//                error: function (XMLHttpRequest, textStatus, errorThrown) { },
//                success: function (msg){ }
//            });
    }

    //timer处理函数
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj);//停止计时器
            $("#btnSendCode").removeClass("getCodeButton-current");
            $("#btnSendCode").removeAttr("disabled");//启用按钮
            $("#btnSendCode").val("重新发送验证码");
        }
        else {
            curCount--;
            $("#btnSendCode").val(curCount + "S");
        }
    }
</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/3/7
  Time: 10:33
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>意见反馈 | 敬信科技</title>
    <%@include file="/WEB-INF/include/staticResource.jsp"%>
</head>
<body>
<div id="wrapper">
<%@include file="/WEB-INF/include/header_help.jsp"%>
    <div class="v2-helpPage-main clearfix">
        <div class="page-width clearfix">
            <div class="v2-helpPage-Menu clearfix">
                <ul>
                    <li><a href="${base}/help/solution">产品解答</a></li>
                    <li><a href="${base}/help/service">服务与收费</a></li>
                    <li class="current"><a>意见反馈</a></li>
                    <li><a href="${base}/help/contribute">我要投稿</a></li>
                </ul>
            </div>
            <div class="v2-helpPage-item clearfix">
                <h2 class="t-center"><img src="${statics}/images/upload/help-3-title.png" alt="欢迎反馈意见，我们倾听您的心声"></h2>
                <form action="${base}/help/feedback" method="post" id="feedForm" name="feedForm">
                    <h6>意见反馈描述：</h6>
                    <div class="v2-helpPage-margin clearfix">
                                <textarea name="content" id="content" cols="30" rows="10" class="v2-helpPage-textarea" placeholder="详细描述你遇到的问题，方便我们进行有效的解决。如具体描述您遇到的问题？问题出现前您做了哪些操作？[2000字以内]" maxlength="2000"></textarea>
                    </div>
                    <h6>请您选择产品类型：</h6>
                    <div class="v2-helpPage-margin clearfix">
                                <span class="pr v2-helpPage-select-a">
                                    <i class="v2-helpPage-select-arrow"></i>
                                    <select name="app" class="v2-helpPage-select">
                                        <option value=" -- ">请选择</option>
                                        <option value="合理用药">合理用药</option>
                                        <option value="YaYa医师">YaYa医师</option>
                                        <option value="YaYa药师">YaYa药师</option>
                                        <option value="YaYa助手">YaYa助手</option>
                                        <option value="YaYa医学直播">YaYa医学直播</option>
                                    </select>
                                </span>
                    </div>

                    <h6>如果您希望得到反馈，请留下您的联系方式：</h6>
                    <div class="v2-helpPage-margin clearfix">
                        <div class="row">
                            <div class="col-lg-4">
                                <div class="clearfix">
                                    <input type="text" name="nickName" class="v2-helpPage-input" placeholder="昵称">
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="clearfix">
                                    <input type="text" name="qq" class="v2-helpPage-input" placeholder="QQ">
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="clearfix">
                                    <input type="text" name="email" class="v2-helpPage-input" placeholder="E-mail">
                                </div>
                            </div>
                        </div>
                    </div>
                    <input class="v2-helpPage-full-button button-color" id="submitBtn" value="提交意见">
                </form>
            </div>
        </div>

    </div>
    <%@include file="/WEB-INF/include/footer.jsp"%>
</div>
<div class="gotop-wrapper index-gotop">
    <a class="gotop" href="javascript:;" >回到顶部</a>
</div>
<script src="${statics}/js/v2/stickUp.min.js"></script>
<script src="${statics}/js/v2/jquery.fancybox-1.3.4.pack.js"></script>
<script src="${statics}/js/v2/custom.js"></script>
<script src="${statics}/js/v2/superfish.js"></script>
<script src="${statics}/js/v2/jquery.carouFredSel.js"></script>
<script src="${statics}/js/v2/jquery.tools.min.js"></script>

<script type="text/javascript">
    /*固定栏*/
    jQuery(function($) {
        $(document).ready( function() {
            $('.fixed-nav').stickUp({
                marginTop: 'auto'
            });

            $("#submitBtn").click(function(){
                var content = $.trim($("#content").val());
                if(content == ''){
                    layer.msg("请输入您的反馈内容");
                    $("#content").focus();
                    return false;
                }
                $.post('${base}/help/feedback',$("#feedForm").serialize(), function (data) {
                    if (data.status == 0){
                        layer.msg("发送意见反馈成功.");
                    }else{
                        layer.msg(data.msg);
                    }
                },'json');
            });
        });
    });


</script>

</body>
</html>

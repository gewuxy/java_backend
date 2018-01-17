<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/10/17
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header class="header">
    <div class="header-content">
        <div class="clearfix">
            <div class="fl clearfix">
                <img src="${ctxStatic}/images/subPage-header-image-03.png" alt="">
            </div>
            <div class="oh">
                <p><strong>资源平台</strong></p>
                <p>医学会议、科教培训、医学资料跨医院共享的学术共享平台</p>
            </div>
        </div>
    </div>
</header>
<!-- header end -->

<div class="tab-hd">
    <ul class="tab-list clearfix" id="menu">
        <li>
            <a href="${ctx}/func/res/list">CSP投屏<i></i></a>
        </li>
        <li>
            <a href="${ctx}/func/res/share/list">共享资源<i></i></a>
        </li>
        <div class="table-top-box clearfix" style="padding: 15px 50px;">
            <div class="formrow t-right">
					<span class="checkboxIcon">
                        <input type="checkbox" id="popup_checkbox_2"
                        <c:if test="${flag == 1}"> checked </c:if> class="chk_1 chk-hook">
						<label for="popup_checkbox_2" class="popup_checkbox_hook"><i class="ico"></i>&nbsp;&nbsp;开启CSPmeeting来稿功能</label>
					</span>&nbsp;
                <span class="question-tipsHover-hook">
                        <img src="${ctxStatic}/images/icon-question.png" alt="">
                    </span>
            </div>
        </div>
    </ul>
</div>

<script>
    $(function(){
        var urlstr = location.href;
        $("#menu>li").removeClass("cur");
        $("#menu>li").each(function () {
            if (urlstr.indexOf($(this).find("a").attr('href')) > -1) {
                $(this).addClass('cur');
            }else if (urlstr.indexOf('/acquired/list') > -1){
                $(this).addClass("cur").siblings().removeClass("cur");
            }
        });
    });
</script>
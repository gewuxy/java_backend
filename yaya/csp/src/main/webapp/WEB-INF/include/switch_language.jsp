<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/11/3
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div href="javascript:;" class="login-language-item userInfo" title="切换语言" >
    <ul class="sf-menu" >
        <li class="current">
            <a class="first-level" target="_blank" href="javascript:;"></a>
            <div class="tb-popupBox-bg clearfix">
                <ul class="item-radius" id="ulitem">
                    <li class="first"><a href="#">简体</a></li>
                    <li><a href="#">繁體</a></li>
                    <li class="last"><a href="#">EN</a></li>
                </ul>
                <div class="tb-popupBox-border"></div>
                <div class="tb-popupBox-outerBorder"></div>
            </div>
        </li>
    </ul>
</div>
<script>
$(function () {
    // 先读取缓存
    var cookie_local = $.cookie('_local');
    if (cookie_local == "en_US") {
        cookie_local = "EN";
    } else if (cookie_local == "zh_TW") {
        cookie_local = "繁體";
    } else {
        cookie_local = "简体";
    }
    // 设置选中缓存中的local
    $(".first-level").text(cookie_local);
    $("#ulitem li").each(function () {
        var avl = $(this).text();
        if (avl == cookie_local){
            $(this).addClass("current").siblings().removeClass("current");
        }
    })

    // 点击切换时，设置local
    $("#ulitem li a").click(function () {
        $("#ulitem li").removeClass("current");
        $(this).parent().addClass("current");
        $(".first-level").text($(this).text());

        var local = $(this).text();
        if(local == "EN"){
            local = "en_US";
        } else if (local == "繁體"){
            local = "zh_TW";
        } else {
            local = "zh_CN";
        }
        // 链接跳转相应的字体页面
        $(this).attr("href",window.location.href);

        // 创建一个cookie并设置有效时间为1天:
        $.cookie('_local', local, { expires: 1 });

        // 读取cookie:
//        alert('change: '+$.cookie('_local'));

    })

})

</script>

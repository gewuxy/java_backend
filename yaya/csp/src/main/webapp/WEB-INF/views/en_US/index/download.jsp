<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/11/27
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

    <title>瀏覽器下載app</title>
    <script language="javascript">
        window.onload = function() {
            var u = navigator.userAgent;
            if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) { //安卓手机
                window.location.href = "http://139.199.170.178/apkfile/YaYa_v7.1.3.apk";

            } else if (u.indexOf('iPhone') > -1) { //苹果手机
                window.location.href = "https://itunes.apple.com/cn/app/id669352079";
            }
        }

        $(window).on("load", function() {
            var winHeight = $(window).height();
            function is_weixin() {
                var ua = navigator.userAgent.toLowerCase();
                if (ua.match(/MicroMessenger/i) == "micromessenger") {
                    return true;
                } else {
                    return false;
                }
            }

            var isWeixin = is_weixin();
            //alert(isWeixin);
            if (isWeixin) {
                // $(".ts").show();
                alert('由於微信的限制，點擊右上角按鈕並選擇‘在瀏覽器中打開’下載安裝包');
            }

        })

    </script>
</head>

<body>
<div class="ts" style="display: none;">
    <p>1.点击右上角"..."按钮</p>
    <p>2.选择"在浏览器中打开"并安装</p>
</div>

</body>
</html>

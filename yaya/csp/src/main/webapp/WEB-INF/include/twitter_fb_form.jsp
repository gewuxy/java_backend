<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/11/3
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include/page_context.jsp" %>

<form action="${ctx}/mgr/jsCallback" id="jsForm" name="jsForm" method="post">
    <input type="hidden" id="uid" name="uid">
    <input type="hidden" id="nickname" name="nickname" >
    <input type="hidden" id="gender" name="gender" >
    <input type="hidden" id="platformId" name="platformId" >
    <input type="hidden" id="iconUrl" name="iconUrl" >
</form>

<script src="${ctxStatic}/js/oauth.twitter.js"></script>
<script src="//adodson.com/hello.js/dist/hello.all.js"></script>
<script>

    var twitterKey;
    var fbKey;
    $(function () {
        $.get('${ctx}/mgr/user/twitter/facebook',{}, function (data) {
            if (data.code == 0){
                twitterKey = data.data.twitter;
                fbKey = data.data.fb;
            }
        },'json');

    });

    function twitterFormSubmit(str){
        $("#uid").val(str.id_str);
        $("#nickname").val(str.screen_name);
        $("#gender").val(str.gender);
        $("#platformId").val(4);
        $("#iconUrl").val(str.profile_image_url);
        $("#jsForm").submit();
    }

    function facebookFormSubmit(str){
        $("#uid").val(str.id);
        $("#nickname").val(str.name);
        $("#gender").val(str.gender);
        $("#platformId").val(3);
        $("#iconUrl").val(str.picture.data.url);
        $("#jsForm").submit();
    }

</script>
<script src="${ctxStatic}/js/oauth.twitter.js"></script>
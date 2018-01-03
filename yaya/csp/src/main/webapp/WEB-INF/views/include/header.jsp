<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/17
  Time: 9:29
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="admin-header">
    <div class="page-width clearfix">
        <div class="fr">
            <div class="userInfo">
                <ul class="sf-menu" >
                    <li class="current">
                        <a class="first-level" >
                        <img id="head_img" src="${principal.avatar}" alt=""></a>
                        <div class="tb-popupBox-bg clearfix">
                            <ul class="item-radius">
                                <li class="first"><a href="${ctx}/mgr/user/info"><fmt:message key="page.header.me"/> </a></li>
                                <li><a href="${ctx}/mgr/user/toAvatar"><fmt:message key="page.header.avatar"/> </a></li>
                                <li><a href="${ctx}/mgr/user/toAccount"><fmt:message key="page.header.account"/></a></li>
                                <li><a href="${ctx}/mgr/user/toFlux"><fmt:message key="page.header.flux"/> </a></li>
                                <li><a href="${ctx}/mgr/user/memberManage"><fmt:message key="page.header.member"/> </a></li>
                                <li><a href="${ctx}/index/17103116063862386794"><fmt:message key="page.header.support"/> </a></li>
                                <li class="last"><a href="${ctx}/mgr/user/logout"><fmt:message key="page.header.logout"/> </a></li>
                            </ul>
                            <div class="tb-popupBox-border"></div>
                            <div class="tb-popupBox-outerBorder"></div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="message"><a href="${ctx}/mgr/message/list" class="icon-message"><span id="msg" class="none"></span></a></div>
            <div class="addMetting"><a href="${ctx}/mgr/meet/edit"  class="icon-addMetting"></a></div>
        </div>
        <div class="fl">
            <a class="logo" href="${ctx}"></a>
            <div class="nav clearfix">
                <ul>
                    <li><a href="${ctx}/mgr/meet/list" class=" current"><span class="icon-folder"></span><fmt:message key="page.header.meet"/> </a></li>
                    <li><a href="${ctx}/mgr/delivery/history" ><span class="icon-contribute"></span><fmt:message key="page.header.delivery.history"/> </a></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<script>

    $(function () {
        $.get('${ctx}/mgr/message/status', function (data) {
            if (data.code == 0){
                var unreadCount = data.data;
                if(unreadCount != 0){
                    $("#msg").removeClass("none");
                }
            }
        },'json');
    });
</script>


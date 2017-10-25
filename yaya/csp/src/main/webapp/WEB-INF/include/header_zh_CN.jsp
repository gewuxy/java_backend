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
                        <a class="first-level" target="_blank" href="javascript:;" >
                        <img id="head_img" src="<shiro:principal property='avatar' defaultValue='${ctxStatic}/images/admin-userImg.png'/>" alt=""></a>
                        <div class="tb-popupBox-bg clearfix">
                            <ul class="item-radius">
                                <li class="first"><a href="${ctx}/mgr/user/info">我的信息</a></li>
                                <li><a href="${ctx}/mgr/user/toAvatar">头像设置</a></li>
                                <li><a href="">账号管理</a></li>
                                <li><a href="">流量管理</a></li>
                                <li><a href="newList.html">帮助中心</a></li>
                                <li class="last"><a href="${ctx}/mgr/logout">退出账号</a></li>
                            </ul>
                            <div class="tb-popupBox-border"></div>
                            <div class="tb-popupBox-outerBorder"></div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="message"><a href="#" class="icon-message"><span></span></a></div>
            <div class="addMetting"><a href="${ctx}/mgr/meet/edit"  class="icon-addMetting"></a></div>
        </div>
        <div class="fl">
            <a class="logo" href="/"></a>
            <div class="nav clearfix">
                <ul>
                    <li><a href="${ctx}/mgr/meet/list" class=" current"><span class="icon-folder"></span>会议管理</a></li>
                    <li><a href="${ctx}/mgr/delivery/history" ><span class="icon-contribute"></span>投稿历史</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>



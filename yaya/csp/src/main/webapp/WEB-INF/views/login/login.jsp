<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/10/16
  Time: 16:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>

    <meta charset="UTF-8">
    <title><fmt:message key="page.login.title"/></title>
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
</head>
<body>
<div id="wrapper">
    <div class="login login-banner" >
        <div class="page-width pr">
            <div class="login-header">
                <%@include file="/WEB-INF/include/switch_language.jsp"%>
            </div>

            <div class="login-box clearfix">
                <%@include file="../include/login_left.jsp"%>

                <div class="col-lg-5 login-box-item">
                    <!--切换 登录-->
                        <c:choose>
                            <%-- 英文页面 --%>
                            <c:when test="${csp_locale eq 'en_US'}">
                                <div class="login-box-main position-button-login">
                                    <a href="javascript:;" title="Login with Facebook" class=" login-button login-facebook"
                                       onclick="facebookLogin()"><i></i><fmt:message key="page.login.facebook"/></a>
                                    <a href="javascript:;" id="twitter" title="Login with Twitter"
                                       class=" login-button login-twitter" onclick="twitterLogin()"><i></i>
                                        <fmt:message key="page.login.twitter"/></a>
                                    <a href="${ctx}/mgr/login?thirdPartyId=7" title="Login by E-mail"
                                       class=" login-button login-email"><i></i><fmt:message key="page.login.email"/></a>
                                    <a href="${ctx}/mgr/login?thirdPartyId=5" title="Login with Jingxin Platform"
                                       class=" login-button login-medcn last"><i></i><fmt:message key="page.login.Jingxin"/></a>
                                    <span class="cells-block error ${not empty error ? '':'none'} ">
                                        <img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;
                                        <span id="errorMessage">${error}</span>
                                    </span>
                                </div>
                            </c:when>
                            <%-- 繁体页面 --%>
                            <c:when test="${csp_locale eq 'zh_TW'}">
                                <div class="login-box-main position-button-login">
                                    <a href="javascript:;" title="Login with Facebook" class=" login-button login-facebook"
                                       onclick="facebookLogin()"><i></i><fmt:message key="page.login.facebook"/></a>
                                    <a href="javascript:;" id="twitter" title="Login with Twitter"
                                       class=" login-button login-twitter" onclick="twitterLogin()"><i></i>
                                        <fmt:message key="page.login.twitter"/></a>
                                    <a href="${ctx}/mgr/login?thirdPartyId=7" title="Login by E-mail"
                                       class=" login-button login-email"><i></i><fmt:message key="page.login.email"/></a>
                                    <a href="${ctx}/mgr/login?thirdPartyId=5" title="Login with Jingxin Platform"
                                       class=" login-button login-medcn last"><i></i><fmt:message key="page.login.Jingxin"/></a>
                                    <span class="cells-block error ${not empty error ? '':'none'} ">
                                        <img src="${ctxStatic}/images/login-error-icon.png" alt="">&nbsp;
                                        <span id="errorMessage">${error}</span>
                                    </span>
                                </div>
                            </c:when>

                            <%-- 中文页面 --%>
                            <c:otherwise>
                                <div class="login-box-main position-button-login">
                                    <a href="${ctx}/mgr/login?thirdPartyId=1" title="微信授权登录"
                                       class=" login-button login-wechat"><i></i><fmt:message key="page.login.weChat"/></a>
                                    <a href="${ctx}/mgr/login?thirdPartyId=2" title="微博授权登录"
                                       class=" login-button login-weibo"><i></i><fmt:message key="page.login.weiBo"/> </a>
                                    <a href="${ctx}/mgr/login?thirdPartyId=5" title="敬信数字平台授权登录"
                                       class=" login-button login-medcn last"><i></i><fmt:message key="page.login.Jingxin"/> </a>
                                </div>
                                <!--登录用-->
                                <div class="login-box-other">
                                    <div class="login-box-other-title t-center ">
                                        <span class="login-box-other-line-l"></span>
                                        <span>其他方式</span>
                                        <span class="login-box-other-line-r"></span>
                                    </div>
                                    <div class="login-box-other-main t-center ">
                                        <a href="${ctx}/mgr/login?thirdPartyId=6" title="手机登录"><img
                                                src="${ctxStatic}/images/login-phone-icon.png"
                                                alt="<fmt:message key="page.login.mobile"/>"></a>
                                        <a href="${ctx}/mgr/login?thirdPartyId=7" title="邮箱登录"><img
                                                src="${ctxStatic}/images/login-email-icon.png"
                                                alt="<fmt:message key="page.login.email"/>"></a>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>

                    <%@include file="../include/login_service.jsp"%>

                </div>
            </div>

            <%@include file="../include/login_footer.jsp"%>
        </div>
    </div>
</div>

<script>
    $(function(){
        //让背景撑满屏幕
        $('.login-banner').height($(window).height());
        //让协议定位到底部
        $('.login-box-item').height($('.login-box').height());

    })
</script>
</body>
</html>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/2
  Time: 14:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include/page_context.jsp" %>
<html>
<head>
    <title>敬信药草园--运营管理系统</title>
    <style type="text/css">
        #main {
            padding: 0;
            margin: 0;
        }

        #main .container-fluid {
            padding: 0 4px 0 6px;
        }

        #header {
            margin: 0 0 8px;
            position: static;
        }

        #header li {
            font-size: 14px;
            _font-size: 12px;
        }

        #header .brand {
            font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
            font-size: 26px;
            padding-left: 33px;
            padding-top: 1px;
            padding-bottom: 1px;
        }

        #footer {
            margin: 8px 0 0 0;
            padding: 3px 0 0 0;
            font-size: 11px;
            text-align: center;
            border-top: 2px solid #0663A2;
        }

        #footer, #footer a {
            color: #999;
        }

        #left {
            overflow-x: hidden;
            overflow-y: auto;
        }

        #left .collapse {
            position: static;
        }

        #userControl > li > a { /*color:#fff;*/
            text-shadow: none;
        }

        #userControl > li > a:hover, #user #userControl > li.open > a {
            background: transparent;
        }
    </style>
    <script src="${ctxStatic}/js/dateformat.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {


        });

    </script>
</head>
<body>
<div id="main">
    <div id="header" class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="brand"><a href="http://www.medcn.com" target="_blank">
                <img src="${ctxStatic}/images/logo.png" alt="logo"/></a>
            </div>
            <ul id="userControl" class="nav pull-right">
                <li id="themeSwitch" class="dropdown">
                </li>
                <li id="userInfo" class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息"><i class="icon-user"></i>&nbsp;您好, <shiro:principal property="account" />&nbsp;<span id="notifyNum" class="label label-info hide"></span></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="${ctx}/csp/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a>
                        </li>
                        <li>
                            <a target="mainFrame" href="${ctx}/csp/sys/user/pwd" ><i class="icon-lock"></i>&nbsp; 修改密码</a>
                        </li>
                    </ul>
                </li>
                <li><a data-href="${ctx}/logout" title="退出登录" onclick="layerConfirm('确认退出吗？', this)"><i class="icon-off"></i>&nbsp;退出</a></li>
                <li>&nbsp;</li>
            </ul>

            <div class="nav-collapse">
                <ul id="menu" class="nav" style="*white-space:nowrap;float:none;">
                    <c:set var="firstMenu" value="true"/>
                    <c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
                        <c:if test="${menu.preid == 0}">
                            <li class="menu ${not empty firstMenu && firstMenu ? ' active' : ''}">
                                <c:if test="${empty menu.url}">
                                    <a class="menu" href="javascript:" data-href="${ctx}/sys/menu/tree/${menu.id}"
                                       data-id="${menu.id}"><span>${menu.name}</span></a>
                                </c:if>
                                <c:if test="${not empty menu.url}">
                                    <a class="menu" href="${fn:indexOf(menu.url, '://') eq -1 ? ctx : ''}${menu.url}"
                                       data-id="${menu.id}" target="mainFrame"><span>${menu.name}</span></a>
                                </c:if>
                            </li>
                            <c:if test="${firstMenu}">
                                <c:set var="firstMenuId" value="${menu.id}"/>
                            </c:if>
                            <c:set var="firstMenu" value="false"/>
                        </c:if>
                    </c:forEach>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </div>
    <div class="container-fluid">
        <div id="content" class="row-fluid">
            <div id="left">
                <div class="accordion" id="menu-1">
                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#menu-1" data-href="#collapse-1" href="#collapse-1" title="">
                                <i class="icon-chevron-down"></i>&nbsp;菜单导航
                            </a>
                        </div>
                        <div id="collapse-${menu.id}"
                             class="accordion-body collapse in">
                            <div class="accordion-inner">
                                <ul class="nav nav-list">
                                    <li>
                                        <a href="${ctx}/example/table" target="mainFrame">
                                            <i class="icon-circle-arrow-right"></i>&nbsp;表格示例
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/example/form" target="mainFrame">
                                            <i class="icon-circle-arrow-right"></i>&nbsp;表单示例
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/csp/sys/user/list" target="mainFrame">
                                            <i class="icon-circle-arrow-right"></i>&nbsp;管理员账号
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/csp/user/list?listType=0" target="mainFrame">
                                           <i class="icon-circle-arrow-right"></i>&nbsp;CSP账号管理
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/csp/sys/log/list" target="mainFrame">
                                            <i class="icon-circle-arrow-right"></i>&nbsp;日志管理
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/csp/meet/list" target="mainFrame">
                                            <i class="icon-circle-arrow-right"></i>&nbsp;审议会议
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/csp/message/list" target="mainFrame">
                                            <i class="icon-circle-arrow-right"></i>&nbsp;消息公告
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/user/statistics" target="mainFrame">
                                            <i class="icon-circle-arrow-right"></i>&nbsp;账号数据统计
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/csp/appManage/list" target="mainFrame">
                                            <i class="icon-circle-arrow-right"></i>&nbsp;APP上架
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/csp/feedback/index" target="mainFrame">
                                            <i class="icon-circle-arrow-right"></i>&nbsp;帮助与反馈
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/example/form" target="mainFrame">
                                            <i class="icon-circle-arrow-right"></i>&nbsp;数据统计
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/csp/order/list" target="mainFrame">
                                            <i class="icon-circle-arrow-right"></i>&nbsp;流量订单管理
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="openClose" class="close">&nbsp;</div>
            <div id="right">
                <iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
            </div>
        </div>
        <div id="footer" class="row-fluid">
            Copyright &copy; 2012-<span id="copyRightLastYear"></span> - Powered By
            <a href="http://www.medcn.com"target="_blank">广州敬信药草园科技有限公司</a>
        </div>
    </div>
</div>
<script type="text/javascript">
    var leftWidth = 160; // 左侧窗口大小
    var tabTitleHeight = 33; // 页签的高度
    var htmlObj = $("html"), mainObj = $("#main");
    var headerObj = $("#header"), footerObj = $("#footer");
    var frameObj = $("#left, #openClose, #right, #right iframe");
    function wSize() {
        var minHeight = 500, minWidth = 980;
        var strs = getWindowSize().toString().split(",");
        htmlObj.css({
            "overflow-x": strs[1] < minWidth ? "auto" : "hidden",
            "overflow-y": strs[0] < minHeight ? "auto" : "hidden"
        });
        mainObj.css("width", strs[1] < minWidth ? minWidth - 10 : "auto");
        frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height() - (strs[1] < minWidth ? 42 : 28));
        $("#openClose").height($("#openClose").height() - 5);// <c:if test="${tabmode eq '1'}">
        $(".jericho_tab iframe").height($("#right").height() - tabTitleHeight); // </c:if>
        wSizeWidth();
    }
    function wSizeWidth() {
        if (!$("#openClose").is(":hidden")) {
            var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
            $("#right").width($("#content").width() - leftWidth - $("#openClose").width() - 5);
        } else {
            $("#right").width("100%");
        }
    }
</script>
<script src="${ctxStatic}/common/wsize.js" type="text/javascript"></script>
</body>
</html>

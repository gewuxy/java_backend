<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/18/018
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>敬信药草园--运营管理系统</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>


    <style type="text/css">
        #main {padding:0;margin:0;} #main .container-fluid{padding:0 4px 0 6px;}
        #header {margin:0 0 8px; position: static; height:65px;} #header li {font-size:14px;_font-size:12px;}
        #header .brand {font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:26px;padding-left:33px; padding-top: 1px;padding-bottom: 1px;}
        #footer {margin:8px 0 0 0;padding:3px 0 0 0;font-size:11px;text-align:center;border-top:2px solid #0663A2;}
        #footer, #footer a {color:#999;} #left{overflow-x:hidden;overflow-y:auto;} #left .collapse{position:static;}
        #userControl>li>a{/*color:#fff;*/text-shadow:none;} #userControl>li>a:hover, #user #userControl>li.open>a{background:transparent;}
    </style>
    <script src="${ctxStatic}/js/dateformat.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {

            var currentYear = new Date().Format("yyyy");
            $("#copyRightLastYear").text(currentYear);

            // 绑定菜单单击事件
            $("#menu a.menu").click(function(){
                // 一级菜单焦点
                $("#menu li.menu").removeClass("active");
                $(this).parent().addClass("active");
                // 左侧区域隐藏
                if ($(this).attr("target") == "mainFrame"){
                    $("#left,#openClose").hide();
                    wSizeWidth();
                    //
                    return true;
                }
                // 左侧区域显示
                $("#left,#openClose").show();
                if(!$("#openClose").hasClass("close")){
                    $("#openClose").click();
                }
                // 显示二级菜单
                var menuId = "#menu-" + $(this).attr("data-id");
                if ($(menuId).length > 0){
                    $("#left .accordion").hide();
                    $(menuId).show();
                    // 初始化点击第一个二级菜单
                    if (!$(menuId + " .accordion-body:first").hasClass('in')){
                        $(menuId + " .accordion-heading:first a").click();
                    }
                    if (!$(menuId + " .accordion-body li:first ul:first").is(":visible")){
                        $(menuId + " .accordion-body a:first i").click();
                    }
                    // 初始化点击第一个三级菜单
                    $(menuId + " .accordion-body li:first li:first a:first i").click();
                }else{
                    // 获取二级菜单数据
                    $.get($(this).attr("data-href"), function(data){
                        $("#left .accordion").hide();
                        $("#left").append(data);
                        // 链接去掉虚框
                        $(menuId + " a").bind("focus",function() {
                            if(this.blur) {this.blur()};
                        });
                        // 二级标题
                        $(menuId + " .accordion-heading a").click(function(){
                            $(menuId + " .accordion-toggle i").removeClass('icon-chevron-down').addClass('icon-chevron-right');
                            if(!$($(this).attr('data-href')).hasClass('in')){
                                $(this).children("i").removeClass('icon-chevron-right').addClass('icon-chevron-down');
                            }
                        });
                        // 二级内容
                        $(menuId + " .accordion-body a").click(function(){
                            $(menuId + " li").removeClass("active");
                            $(menuId + " li i").removeClass("icon-white");
                            $(this).parent().addClass("active");
                            $(this).children("i").addClass("icon-white");
                        });
                        // 展现三级
                        $(menuId + " .accordion-inner a").click(function(){
                            var href = $(this).attr("data-href");
                            if($(href).length > 0){
                                $(href).toggle().parent().toggle();
                                return false;
                            }
                        });
                        // 默认选中第一个菜单
                        $(menuId + " .accordion-body a:first i").click();
                        $(menuId + " .accordion-body li:first li:first a:first i").click();
                    });
                }
                // 大小宽度调整
                wSizeWidth();
                return false;
            });


            // 初始化点击第一个一级菜单
            $("#menu a.menu:first span").click();

            // 鼠标移动到边界自动弹出左侧菜单
            $("#openClose").mouseover(function(){
                if($(this).hasClass("open")){
                    $(this).click();
                }
            });

        });

    </script>


    <!--新增的-->
    <link rel="stylesheet" href="${ctxStatic}/bootstrap/added/admin-style.css">
    <link href="${ctxStatic}/bootstrap/added/daterangepicker.css" rel="stylesheet">

    <script src="${ctxStatic}/bootstrap/added/moment.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/bootstrap/added/jquery.daterangepicker.js"></script>
    <script src="${ctxStatic}/bootstrap/added/echarts.min.js"></script>



</head>
<body>
<div id="main">
    <div id="header" class="navbar navbar-fixed-top"></div>
    <div class="container-fluid">
        <div id="content" class="row-fluid">
            <div id="left"></div>
            <div id="openClose" class="close">&nbsp;</div>
            <div id="right" class="hidden-box">
                <iframe id="mainFrame" name="mainFrame" src="routes_pages/userInfo.html" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
            </div>
        </div>
        <div id="footer" class="row-fluid"></div>
    </div>
</div>
<!--原本默认-->
<script type="text/javascript">
    var leftWidth = 160; // 左侧窗口大小
    var tabTitleHeight = 33; // 页签的高度
    var htmlObj = $("html"), mainObj = $("#main");
    var headerObj = $("#header"),
        footerObj = $("#footer");
    var frameObj = $("#left, #openClose, #right, #right iframe");
    function wSize(){
        var minHeight = 500, minWidth = 980;
        var strs = getWindowSize().toString().split(",");
        htmlObj.css({"overflow-x":strs[1] < minWidth ? "auto" : "hidden", "overflow-y":strs[0] < minHeight ? "auto" : "hidden"});
        mainObj.css("width",strs[1] < minWidth ? minWidth - 10 : "auto");
        frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height() - (strs[1] < minWidth ? 42 : 28));
        $("#openClose").height($("#openClose").height() - 5);//
        wSizeWidth();
        console.log(frameObj.height());
        console.log(headerObj.height());
    }
    function wSizeWidth(){
        if (!$("#openClose").is(":hidden")){
            var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
            $("#right").width($("#content").width()- leftWidth - $("#openClose").width() -25);
        }else{
            $("#right").width("100%");
        }
    }
</script>
<script src="./common/wsize.min.js" type="text/javascript"></script>

<script>
    //全局地址，正式调用时可忽略
    iframe_Path = location.protocol + '//' + location.host + '';
</script>

<!--静态路由 正式调用时可忽略-->
<script src="./routes/route_public.js"></script>







</body>
</html>


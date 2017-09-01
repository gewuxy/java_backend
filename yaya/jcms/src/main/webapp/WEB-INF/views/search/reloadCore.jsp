<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page import="cn.medcn.data.model.DataFile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>新闻详情-医生建议详情</title>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="http://www.medcn.cn/jx_ycy/css/data.css">
    <%@include file="/WEB-INF/include/staticResource2.jsp" %>
    <style type="text/css">
        input{border: solid 1px #aaa}
    </style>
</head>
<body>
<div id="wrapper">

    <div class="v2-sub-main" style="padding:40px 0 80px;">
        <div class="page-width clearfix">
            <div class="row">
                <div class="col-lg-8 ">
                    用户名：<input type="text" id="manager"> &nbsp; &nbsp;
                    密码：<input type="password" id="password"><br>
                    <br>
                    要操作的检索库：
                    <select id="core">
                        <option value="article">article检索库</option>
                        <option value="dataFile">dataFile检索库</option>
                    </select><br>
                    状态：<span id="schedule">a</span><br>
                    <input value="更新" id="update" type="button">
                    <input value="停止" id="stop" type="button">
                    <input value="刷新状态" id="getSchedule" type="button">
                </div>
                <script>
                    $(function () {

                        $("#core").change(function () {
                            clearTimeout(timeOutId);
                        });

                        $("#update").click(function () {
                            var core = $("#core").val();
                            $.post("${base}/search/reload/" + core, {
                                user: $("#manager").val(),
                                password: $("#password").val(),
                            }, function (res) {
                                $("#schedule").html(res);
                                if (res.indexOf(core + "数据更新中") >= 0) {
                                    timeOutId = setTimeout(function () {
                                        getSchedule(core);
                                    }, 1000);
                                }
                            });
                        });

                        $("#getSchedule").click(function () {
                            var core = $("#core").val();
                            getSchedule(core)
                        });

                        $("#stop").click(function () {

                            $.post("${base}/search/stop/reload/" + $("#core").val(), {
                                user: $("#manager").val(),
                                password: $("#password").val(),
                            }, function (res) {
                                $("#schedule").html(res);
                            });
                        });

                    })

                    function getSchedule(core) {
                        $.post("${base}/search/get/schedule/" + core, {
                            user: $("#manager").val(),
                            password: $("#password").val(),
                        }, function (res) {
                            $("#schedule").html(res);
                            if (res.indexOf(core + "数据更新中") >= 0) {
                                timeOutId = setTimeout(function () {
                                    getSchedule(core);
                                }, 1000);
                            }
                        });
                    }

                </script>

            </div>
        </div>
    </div>

</div>

<!--弹出层-->
<%@include file="/WEB-INF/include/popup_layer2.jsp" %>

<script src="${statics}/js/v2/stickUp.min.js"></script>
<script src="${statics}/js/v2/jquery.fancybox-1.3.4.pack.js"></script>


</body>
</html>
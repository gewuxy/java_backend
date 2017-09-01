<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/4/25
  Time: 13:41
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML >
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>${exam.examPaper.paperName}</title>
    <meta name="keywords" content="社交，社交平台，医生，论坛，专属医生社交平台，专业医师，专业药师，执业医师，执业药师，执业资格证，医疗工具，实用，药草园">
    <link href="${ctxStatic}/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctxStatic}/js/lhgdialog/lhgdialog.min.js?self=true&skin=blue"></script>

    <style>
        .ani{
            position: fixed;
            margin:auto;
            left:0;
            right:0;
            top:0;
        }
        .bun{
            text-align:center;
            padding:10px 0;
        }
        .bun a{
            width:110px;
            text-align:center;
            color:#FFF;
            padding:10px 0;
            border:none; margin:0 10px;
            display: inline-block;
            text-decoration:none;
        }

    </style>


    <SCRIPT type="text/javascript">
        var t = "${time}";
        //	alert('tsst: '+t);
        var maxtime = t*60 ;
        //var maxtime = 10*30;
        //半个小时，按秒计算，自己调整!
        //alert(maxtime);
        function Daojishi(){
            if(maxtime>=0){
                minutes = Math.floor(maxtime/60);
                seconds = Math.floor(maxtime%60);
                msg = "倒计时："+minutes+"分"+seconds+"秒";
                //	document.all["timer"].innerHTML=msg;   倒计时：00:30:00
                document.getElementById("timer").innerHTML=msg;
                if(maxtime == 5*60)
                    alert('注意，还有5分钟!');
                --maxtime;
            }   else{
                clearInterval(timer);
                //qr_score();
                //alert("时间到，结束!");
            }
        }
        timer = setInterval("Daojishi()",1000);

        //倒计时结束  自动弹出分数  提示用户是否确认分数 或者重新考试
        function qr_score(){
            alert("考试时间到了");
            $("#forms").submit();
        }

        function continuetext(){
            $("#confirmtip").hide();
        }

        function commit(){
            $("#forms").submit();
        }

    </SCRIPT>

    <SCRIPT type="text/javascript">
        //用户在考试时间内 点击提交考卷  弹出一个询问框，有确定和取消按钮
        function firm() {
            var tip = "";
            var q = $("input[name^='qid']");
            for(var a=0 ; a<q.length ;a++){
                var qid = q.eq(a).val();
                if(!$('input[name=answer_'+qid+']:checked').length) {
                    //没完成的题号
                    var no = $("#num"+qid+"").val();
                    if(tip==""){
                        tip = "" + no;
                    }else{
                        tip = tip+ "," + no ;
                    }
                }
            }
            //利用对话框返回的值 （true 或者 false）
            var text = "";
            if(tip != ""){
                text = "您还有第"+tip+"题未完成,你确定提交吗？";
            }else{
                text = "你确定提交吗？";
            }
            $("#ptip").text(text);
            $("#confirmtip").show();
        }
    </SCRIPT>

</head>

<body class="test-bj">
<form id="forms" action=""  method="post" theme="simple">
    <div class="t-top" style="z-index:111111111;">
        <img src="${ctxStatic}/images/time.png" />
        <strong id="timer"></strong>
        <input class="t-tj" type="button" value="交卷" id="tj" onclick="firm()" />
    </div>
    <div style="position: absolute;left: 0px;right: 0px;top: 30px;">
        <div class="t-tilte">
            <p class="tm">${exam.examPaper.paperName}</p>
            <p>${principal.nickname}，您好</p>
            <p>考试时间：${exam.usetime}分钟</p>
        </div>
        <div class="t-nav">
            <c:forEach items="${exam.examPaper.questionList}" var="q" varStatus="status" >

                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>${status.index+1} . ${q.title}
                            <span class="dxuan">【${q.qtypeName}】</span>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${q.qtype == 0}">
                                    <c:forEach items="${q.optionList}" varStatus="ostatus" var="o">
                                        <input type="radio" name="question_${q.id}" value="${o.key}"/> ${o.value}<br>
                                    </c:forEach>
                                </c:when>
                                <c:when test="${q.qtype == 1}">
                                    <c:forEach items="${q.optionList}" var="o">
                                        <input type="checkbox" name="question_${q.id}" value="${o.key}"/> ${o.value}<br>
                                    </c:forEach>
                                </c:when>
                                <c:when test="${q.qtype == 2}">
                                    <input type="text" name="question_${q.id}">
                                </c:when>
                                <c:when test="${q.qtype == 3}">
                                    <textarea name="question_${q.id}"></textarea>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </table>
            </c:forEach>
        </div>
    </div>
</form>
<div id="confirmtip" style="position: fixed;left: 0px;right: 0px;margin: 0px auto;background: rgba(125, 125, 125, 0.8) none repeat scroll 0% 0%;width: 100%;top: 0px;height: 100%;padding-top:250px;display: none;">
    <div class="ani resize" style="width:280px; left:20px; right:20px; background:#FFF; border-radius:5px 5px 5px; top:80px; overflow:hidden;">
        <div style="width:100%; color:#FFF; font-size:20px; text-align:center; padding:10px 0; color:#000;">温馨提示</div>
        <div style="padding:10px;">
            <p align="center" style="line-height:25px; font-size:15px;" id="ptip">您还有1,2题未完成，您确定提交吗？</p>
        </div>
        <div class="bun">
            <a onclick="continuetext();" style="background:#ccc;cursor: pointer;">继续考试</a>
            <a onclick="commit();" style="background:#27b1d8;cursor: pointer;">交卷</a>
        </div>
    </div>
</div>
<div class="t-bottom">www.medyaya.cn</div>
</body>
</html>

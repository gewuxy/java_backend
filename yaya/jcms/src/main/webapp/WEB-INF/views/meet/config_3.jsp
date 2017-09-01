<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/23
  Time: 10:49
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <title>会议内容编辑</title>

    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
    <link href="${ctxStatic}/css/daterangepicker.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctxStatic}/css/wenjuan_ht.css">
    <script src="${ctxStatic}/js/ajaxfileupload.js"></script>
    <script>
        var meetId = '${meetId}';
        var moduleId = '${moduleId}';
        var examId = '${exam.id}';
        var paperId = '${paper.id}';

        $(function(){
            $("#uploadPaper").change(function(){
                var val = $(this).val();
                if(!val.endsWith(".xls") && !val.endsWith(".xlsx")){
                    layer.msg("请选择xls或者xlsx格式的文件");
                    return ;
                }
                batchePaper();
            });


            $(".btnAdd-metting-faq").click(function(){
                $('.mask-wrap').addClass('dis-table');
                $('.m-layer-bd').show();
                $("#questionEditFrame").attr("src","${ctx}/func/meet/exam/edit?paperId=${paper.id}");
            });

            $(".bianji").click(function(){
                $('.mask-wrap').addClass('dis-table');
                $('.m-layer-bd').show();
                $("#questionEditFrame").attr("src","${ctx}/func/meet/exam/edit?paperId=${paper.id}&questionId="+$(this).attr("qid"));
            });

            $(".close-btn").click(function(){
                closeDialog();
            });

            $(".movie_box").mouseenter(function(){
                $(this).css({"border":"1px solid #dbdbdb"});
                $(".kzqy_czbut").hide();
                $(this).find(".kzqy_czbut").show();
            }).mouseleave(function(){
                $(this).css({"border":"1px solid #fff"});
                $(".kzqy_czbut").hide();
            });


            $("#passScore").change(function(){
                if(checkPassScore()){
                    $.get('${ctx}/func/meet/exam/passScore',{'examId':'${exam.id}', 'passScore':$("#passScore").val() }, function () {

                    },'json');

                }
            });

            function checkPassScore(){
                var passScore = $("#passScore").val();
                if(!/^\d+$/g.test(passScore)){
                    layer.tips("请设置正确的及格分数", "#passScore");
                    return false;
                }
                if(parseInt('${paper.totalPoint}') < passScore){
                    layer.tips("及格分数不能大于总分", "#passScore");
                    return false;
                }
                if(parseInt('${paper.totalPoint}') <= 0){
                    layer.tips("及格分数必须大于0", "#passScore");
                    return false;
                }
                return true;
            }

            function checkResitTimes(){
                var resitTimes = $("#resitTimes").val();
                if(!/^\d+$/g.test(resitTimes)){
                    layer.tips("请设置正确的重考次数", "#resitTimes");
                    return false;
                }
                return true;
            }

            function checkUseTime(){
                var usetime = $("#usetime").val();
                if(!/^\d+$/g.test(usetime)){
                    layer.tips("请设置正确考试用时", "#usetime");
                    return false;
                }
                return true;
            }

            $("#resitTimes").change(function(){
                if(checkResitTimes()){
                    $.get('${ctx}/func/meet/exam/resitTimes',{'examId':'${exam.id}', 'resitTimes':$("#resitTimes").val() }, function () {

                    },'json');

                }
            });

            $("#usetime").change(function(){
                if(checkUseTime()){
                    $.get('${ctx}/func/meet/exam/usetime',{'examId':'${exam.id}', 'usetime':$("#usetime").val() }, function () {

                    },'json');
                }
            });

            $(".del").click(function(){
                var questionId = $(this).attr("qid");
                top.layer.confirm("确定要删除此试题吗？",function(){
                   $.get('${ctx}/func/meet/exam/del',{'paperId':'${paper.id}','questionId':questionId},function () {
                       top.layer.closeAll();
                       window.location.reload();
                   }, 'json');
                });
            });

            $("#finishBtn").click(function(){
                if(checkUseTime() && checkPassScore() && checkResitTimes()){
                    window.location.href = '${ctx}/func/meet/finish?meetId=${meetId}&moduleId=${moduleId}';
                }

            });
        });

        function closeDialog(){
            $('.mask-wrap').removeClass('dis-table');
        }


        function batchePaper(){
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajaxFileUpload({
                url: '${ctx}/func/meet/exam/batch?meetId='+meetId+'&moduleId='+moduleId, //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: "uploadPaper", //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                    layer.close(index);
                    //回调函数传回传完之后的URL地址
                    if(data.code == 0){
                        window.location.href='${ctx}/func/meet/config?meetId='+meetId+'&moduleId='+moduleId;
                    }else{
                        layer.msg(data.msg);
                    }
                },
                error:function(data, status, e){
                    layer.msg(e);
                    layer.close(index);
                }
            });
        }
    </script>
</head>
<body>
<div class="g-main  mettingForm clearfix">

    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-03.png" alt="">
                </div>
                <div class="oh">
                    <p><strong>会议发布</strong></p>
                    <p>快速发布在线会议，下载手机app端即可查看PPT、直播、调研、考试等会议</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->

    <div class="tab-hd">

        <ul class="tab-list clearfix">
            <li class="cur">
                <a>会议发布<i></i></a>
            </li>
            <li>
                <a href="${ctx}/func/meet/draft">草稿箱<i></i></a>
            </li>
            <li>
                <a href="${ctx}/func/meet/list">已发布会议<i></i></a>
            </li>
        </ul>
    </div>

    <div class="tab-bd" style="padding-bottom:500px;">

        <div class=" clearfix metting-process">
            <div class=" clearfix link-justified metting-process-02">
                <ul>
                    <li class="cur">创建会议</li>
                    <li>功能设置</li>
                    <li>发布预览</li>
                </ul>
            </div>
        </div>



        <%@include file="funTab.jsp"%>
        <div class="metting-info clearfix">
            <p class="rowMargin"><label class="formButton">上传试卷<input type="file" name="file" id="uploadPaper" class="none"></label>&nbsp;&nbsp;&nbsp;&nbsp;考试总成绩为 <span class="color-green" id="totalPoint">${paper.totalPoint}</span> 分，点击 <a href="${ctxStatic}/upload/temp/paper_template.xlsx" class="color-blue">考题模版</a> 并下载，以最后一次上传为准</p>
            <p class="color-gray-2-up">考试用时 <input type="text " class="gary-input" id="usetime" name="usetime" maxlength="3" value="${exam.usetime}"> 分钟，及格分数 <input type="text " class="gary-input" id="passScore" name="passScore" maxlength="3" value="${exam.passScore}"> 分，不及格可重考 <input type="text" class="gary-input" id="resitTimes" name="resitTimes" value="${exam.resitTimes}" maxlength="2"> 次</p>

        </div>

        <div class="tab-bd mettingForm">
            <div class="page-width clearfix">
                <c:forEach items="${paper.questionList}" var="question" varStatus="status">
                    <div class="add-metting-faq-content" >
                        <div class="movie_box" style="border: 1px solid rgb(255, 255, 255);">
                            <div class='kzqy_czbut' style="top: 10px; display:none;"><a href='javascript:void(0)'  class='bianji color-blue-up' qid="${question.id}">编辑</a><a href='javascript:void(0)' class='del' qid="${question.id}" >删除</a></div>
                            <div class="wjdc_list">
                                <li><span class="fr"><span class="num color-green">${question.point}</span>&nbsp;分</span> <div class="tm_btitlt"><i class="nmb">${status.index+1}</i>. <i class="btwenzi">${question.title}</i></div> </li>&nbsp;&nbsp;
                                <c:choose>
                                    <c:when test="${question.qtype == 0}">
                                        <c:forEach items="${question.optionList}" var="op">
                                            <li><label> <input name="option[${question.id}]" type="radio" ${question.rightKey eq op.key?'checked':''} value=""><span>${op.key} . ${op.value}</span></label></li>
                                        </c:forEach>
                                    </c:when>
                                    <c:when test="${question.qtype == 1}">
                                        <c:forEach items="${question.optionList}" var="op">
                                            <li><label> <input name="option[${question.id}]" type="checkbox" ${fn:contains(question.rightKey, op.key)?'checked':''} value=""><span>${op.key} . ${op.value}</span></label></li>
                                        </c:forEach>
                                    </c:when>
                                    <c:when test="${question.qtype == 2 || question.qtype == 3}">
                                        <textarea name=""  cols="" rows="" class="input_wenbk btwen_text btwen_text_tk" >${question.rightKey}</textarea>
                                    </c:when>
                                </c:choose>
                            </div>
                            <div class="dx_box" data-t="0"></div>
                        </div>
                    </div>
                </c:forEach>
                <div class="btnAdd-metting-faq-area">
                    <a style="cursor: pointer;" class="btnAdd-metting-faq">添加题目</a>
                </div>

            </div>
        </div>


        <div class="buttonArea clearfix" style="margin: 20px 25px 40px;">
            <div class="formrow">
                <div class="fl clearfix">
                    <input type="button"  onclick="window.location.href='${ctx}/func/meet/edit?id=${meetId}'" class="formButton formButton-max" value="上一步">&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" id="finishBtn"  class="formButton formButton-max" value="下一步">
                </div>
            </div>
        </div>





    </div>
</div>



    </div>
</div>
<div class="mask-wrap">
    <div class="m-layer-bd"  style="height: 650px;">
        <div class="mask-hd clearfix"  style="padding: 0px 20px 32px 0px;">
            <a  class="mask-le"><img src="${ctxStatic}/images/u-2.png"> 添加/编辑试题 </a>
            <span class="close-btn"><img src="${ctxStatic}/images/cha.png"></span>
        </div>
        <iframe src="" frameborder="0" id="questionEditFrame" name="questionEditFrame" width="100%" height="615"/>
    </div>

    <div class="distb-box distb-box-min fx-mask-box-4">
        <div class="mask-hd clearfix">
            <h3 class="font-size-1">设置分组</h3>
            <span class="close-btn-fx"><img src="images/cha.png"></span>
        </div>
        <div class="fx-mask-box clearfix t-center" >
            <span class="fSize-max td-title">分组名称</span><span><input type="text" class="text-input" placeholder="" value="糖尿病人"></span>
        </div>
        <div class="sb-btn-box p-btm-1 t-right">
            <button class="close-button-fx">删除分组</button>
            <button class="close-button-fx cur">确认</button>
        </div>
    </div>

</div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/6/5
  Time: 11:16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include/page_context.jsp"%>
<link rel="stylesheet" href="${ctxStatic}/css/wenjuan_ht.css">
<body style="background-color: #ffffff">
    <form action="${ctx}/func/meet/exam/save" method="post" id="questionForm" name="questionForm">
    <input type="hidden" name="id" value="${question.id}">
    <input type="hidden" name="paperId" value="${paperId}">
    <div class="metting-addContent"  style="display:block;">
        <div class="movie_box xxk_box" style="margin-top: 0px;">
            <ul class="xxk_title">
                <li <c:if test="${qtype == 0}">class="on"</c:if>  data-t="0">单选</li>
                <li <c:if test="${qtype == 1}">class="on"</c:if> data-t="1">多选</li>
                <li <c:if test="${qtype == 2}">class="on"</c:if> data-t="2">填空</li>
                <input type="hidden" name="qtype" value="${qtype}" id="qtype"/>
                <span class="fr">分数：&nbsp;<input type="text" class="input_wenbk" name="point" maxlength="3" id="point" value="${question.point}">&nbsp;分</span>
            </ul>
            <div class="xxk_conn">
                <!--单选----------------------------------------------------------------------------------------------------------------------------------------->
                <div class="xxk_xzqh_box ">
                    <textarea name="title" cols="" rows=""  class="input_wenbk btwen_text btwen_text_dx" id="title" placeholder="问题内容">${question.title}</textarea>

                            <c:if test="${qtype == 0 || qtype == 1}">
                                <div class="title_itram" id="optionView">
                                <c:forEach items="${question.optionList}" var="op" varStatus="opstatus">
                                    <div class="kzjxx_iteam">
                                        <input name="rightKeyArr" type="${question.qtype == 0?'radio':'checkbox'}"  value="${opstatus.index+1}"
                                            <c:if test="${question.qtype == 0 && question.rightKey eq op.key}">checked</c:if>
                                               <c:if test="${question.qtype == 1 && fn:contains(question.rightKey, op.key)}">checked</c:if>
                                               class="dxk">
                                        <input name="optionArr" type="text" class="input_wenbk" value="${op.value}" placeholder="选择项内容">
                                        <a href="javascript:void(0);" class="del_xm">删除</a> </div>
                                </c:forEach>
                                </div>
                                <a href="javascript:void(0)" class="zjxx">+&nbsp;&nbsp;增加选项</a>
                            </c:if>

                    <!--完成编辑-->
                    <div class="bjqxwc_box"> <a href="javascript:void(0);" class="qxbj_but">取消编辑</a> <a style="cursor: pointer;" class="add_swcbj_but" > 完成编辑</a> </div>
                </div>

            </div>
        </div>
    </div>

    </form>
    <script>
        $(function(){
            $(".qxbj_but").click(function(){
                parent.closeDialog();
            });

            $(".zjxx").click(function(){
                var qtype = $("#qtype").val();
                var qsort = parseInt($(".kzjxx_iteam").find("input[name='rightKeyArr']").length)+1;
                var inputType = qtype == 0?'radio':'checkbox'
                var contentHtml = '<div class="kzjxx_iteam">'+
                        '<input name="rightKey" type="'+inputType+'"  value="'+qsort+'"  class="dxk">'+
                        '<input name="optionArr" type="text" class="input_wenbk" value="${op.value}" placeholder="选择项内容">'+
                        '<a href="javascript:void(0);" class="del_xm">删除</a> </div>';
                $("#optionView").append(contentHtml);
                $(".del_xm").click(function(){
                    delOption($(this));
                });
            });

            $(".del_xm").click(function(){
                delOption($(this));
            });

            $(".add_swcbj_but").click(function(){
                if(checkForm()){
                    $.post('${ctx}/func/meet/exam/save',$("#questionForm").serialize(), function(){
                        parent.window.location.reload();
                    },'json');
                }
            });
        });

        function checkForm(){
            //检测分数是否合法
            var point = $("#point").val();
            if(!/^\d+$/g.test(point)){
                layer.tips("请设置合适的分数","#point");
                return false;
            }
            //检测问题内容
            var title = $("#title").val();
            if($.trim(title) == ''){
                layer.tips('请输入问题内容', '#title', {
                    tips: [1, '#000000'] //还可配置颜色
                });
                return false;
            }
            //检测正确答案是否设置
            var rightKeychecked = false;
            $("input[name='rightKeyArr']").each(function(){
                if($(this).is(":checked")){
                    rightKeychecked = true;
                    return false;
                }
            });
            if(!rightKeychecked){
                layer.tips("请设置正确答案",'.dxk',{tips: [1, '#000000']});
                return false;
            }
            //检测选项是否为空
            var hasContent = true;
            $(".option").each(function(){
                if($(this).val() == ''){
                    hasContent = false;
                    return false;
                }
            });
            if(!hasContent){
                layer.tips("请完善选项",'.option');
                return false;
            }
            return true;
        }

        function delOption(tar){
            $(tar).parent().remove();
            var index = 1;
            $(".kzjxx_iteam").find("input[name='rightKeyArr']").each(function(){
                $(this).val(index);
                index++;
            });
        }
    </script>
</body>
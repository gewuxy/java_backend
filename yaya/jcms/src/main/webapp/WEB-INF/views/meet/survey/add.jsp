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
    <form action="${ctx}/func/meet/survey/save" method="post" id="questionForm" name="questionForm">
        <input type="hidden" name="paperId" value="${paperId}">
    <div class="metting-addContent"  style="display:block;">
        <div class="movie_box xxk_box" style="margin-top: 0px;">
            <ul class="xxk_title">
                <li <c:if test="${qtype == 0}">class="on"</c:if>  data-t="0">单选</li>
                <li <c:if test="${qtype == 1}">class="on"</c:if> data-t="1">多选</li>
                <li <c:if test="${qtype == 2}">class="on"</c:if> data-t="2">填空</li>
                <input type="hidden" name="qtype" value="${qtype}" id="qtype"/>

            </ul>
            <div class="xxk_conn">
                <!--单选----------------------------------------------------------------------------------------------------------------------------------------->
                <div class="xxk_xzqh_box ">
                    <textarea name="title" id="title" cols="" rows=""  class="input_wenbk btwen_text btwen_text_dx" placeholder="问题内容">${question.title}</textarea>

                            <c:if test="${qtype == 0 || qtype == 1}">
                                <div class="title_itram" id="optionView">
                                    <div class="kzjxx_iteam">
                                        <input name="optionArr" type="text" class="input_wenbk option" value="${op.value}" placeholder="选择项内容">
                                        <a href="javascript:void(0);" class="del_xm">删除</a> </div>
                                    <div class="kzjxx_iteam">
                                        <input name="optionArr" type="text" class="input_wenbk option" value="${op.value}" placeholder="选择项内容">
                                        <a href="javascript:void(0);" class="del_xm">删除</a> </div>
                                    <div class="kzjxx_iteam">
                                        <input name="optionArr" type="text" class="input_wenbk option" value="${op.value}" placeholder="选择项内容">
                                        <a href="javascript:void(0);" class="del_xm">删除</a> </div>
                                    <div class="kzjxx_iteam">
                                        <input name="optionArr" type="text" class="input_wenbk option" value="${op.value}" placeholder="选择项内容">
                                        <a href="javascript:void(0);" class="del_xm">删除</a> </div>
                                </div>
                                <a href="javascript:void(0)" class="zjxx">+&nbsp;&nbsp;增加选项</a>
                            </c:if>

                    <!--完成编辑-->
                    <div class="bjqxwc_box"> <a href="javascript:void(0);" class="qxbj_but">取消编辑</a> <a  style="cursor: pointer;" class="add_swcbj_but" > 完成编辑</a> </div>
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

            $(".xxk_title>li").click(function(){
                var qtype = $(this).attr('data-t');
                window.location.href = '${ctx}/func/meet/survey/edit?paperId=${paperId}&qtype='+qtype;
            });

            $(".zjxx").click(function(){
                var qtype = $("#qtype").val();
                var contentHtml = '<div class="kzjxx_iteam">'+
                    '<input name="optionArr" type="text" class="input_wenbk option" value="${op.value}" placeholder="选择项内容">'+
                    '<a href="javascript:void(0);" class="del_xm">删除</a> </div>';
                $("#optionView").append(contentHtml);
                $(".del_xm").click(function(){
                    delOption($(this));
                });
            });

            $(".add_swcbj_but").click(function(){
                if(checkForm()){
                    $.post('${ctx}/func/meet/survey/save',$("#questionForm").serialize(), function(){
                        parent.window.location.reload();
                    },'json');
                }
            });

            $(".del_xm").click(function(){
                delOption($(this));
            });
        });

        function checkForm(){
            //检测问题内容
            var title = $("#title").val();
            if($.trim(title) == ''){
                layer.tips('请输入问题内容', '#title', {
                    tips: [1, '#000000'] //还可配置颜色
                });
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
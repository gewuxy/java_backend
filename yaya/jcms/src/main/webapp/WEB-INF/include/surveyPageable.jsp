<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/5
  Time: 11:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${surveyPage.pages>1}">

<div class="page-box" >
    <a id="left3"
            <c:if test="${surveyPage.pageNum>1}">
                  href="javascript:page3(${surveyPage.pageNum - 1})" </c:if> class="page-list page-border page-prev"><i></i></a>
    <span class="page-list" id="pageList3">${surveyPage.pageNum} / ${surveyPage.pages}</span>
    <a id="right3"
            <c:if test="${surveyPage.pageNum<surveyPage.pages}">
                href="javascript:page3(${surveyPage.pageNum+1})" </c:if>
                class="page-list page-border page-next"><i></i></a>
    <input class="page-list page-border" id="pagePageNum3" type="text">
    <a href="javascript:var pagePageNum = $('#pagePageNum3').val(); page3(pagePageNum)" class="page-list page-border page-link">跳转</a>
</div>

<script>

    function page3(pageNum){
        var pages = parseInt("${surveyPage.pages}");
        if(isNaN(Number(pageNum))){
            layer.msg("请输入正确的页码");
        }
        if(pageNum > pages||pageNum == 0){
            layer.msg("请输入页码[1-"+pages+"]");
            return;
        }
        $("#surveyForm").find("input[id='pageNum3']").val(pageNum);

        // 使用 jQuery异步提交表单
                $.ajax({
                    url:'${ctx}/mng/doc/surveyHistory',
                    data:$('#surveyForm').serialize(),
                    type:"POST",
                    dataType:'JSON',
                    success:function(data)
                    {
                        buildTbody3(data);
                        $("#pageList3").html(data.pageNum+" / "+${surveyPage.pages});

                        if(data.pageNum > 1){

                            $("#left3").attr("href","javascript:page3("+(data.pageNum-1)+")");
                        }
                        if(data.pageNum < ${surveyPage.pages}){

                            $("#right3").attr("href","javascript:page3("+(data.pageNum+1)+")");
                        }
                    }
                });
    }

    function buildTbody3(data){
       var html = template("survey",data);
       $("#surveyTable").html(html);
    }
</script>
</c:if>



<script id="survey" type="text/html">

    <table class="tj-table tj-table-re1 clearfix" id="surveyTable">
        <thead>
        <tr>
            <td class="tj-td-1">会议类型</td>
            <td class="tj-td-3">问卷名称</td>
            <td class="tj-td-2 t-center">问卷时间</td>
            <td class="tj-td-2 t-center">问卷明细</td>
        </tr>
        </thead>
        <tbody id="surveyTBody">
        {{each dataList as survey}}
            <tr surveyId = "{{survey.id}}">
                <td class="tj-td-1 color-black"><span class="icon iconfont icon-minIcon17"></span>问卷</td>
                <td class="tj-td-3 color-black">{{survey.paperName}}</td>
                <td class="tj-td-2 t-center"><span>{{survey.time}}</span></td>
                <td class="tj-td-2 t-center"><a href="${pageContext.request.contextPath}/mng/doctor/surveyDetail?historyId={{survey.id}}" class="check-link" target="_blank">查看</a></td>
            </tr>
        {{/each}}

        </tbody>
    </table>

</script>




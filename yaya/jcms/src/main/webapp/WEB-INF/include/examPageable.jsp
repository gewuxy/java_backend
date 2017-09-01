<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/5
  Time: 11:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${examPage.pages>1}">

<div class="page-box" >
    <a id="left2"
            <c:if test="${examPage.pageNum>1}">
                  href="javascript:page2(${examPage.pageNum - 1})" </c:if> class="page-list page-border page-prev"><i></i></a>
    <span class="page-list" id="pageList2">${examPage.pageNum} / ${examPage.pages}</span>
    <a id="right2"
            <c:if test="${examPage.pageNum<examPage.pages}">
                href="javascript:page2(${examPage.pageNum+1})" </c:if>
                class="page-list page-border page-next"><i></i></a>
    <input class="page-list page-border" id="pagePageNum2" type="text">
    <a href="javascript:var pagePageNum = $('#pagePageNum2').val(); page2(pagePageNum)" class="page-list page-border page-link">跳转</a>
</div>

<script>

    function page2(pageNum){
        var pages = parseInt("${examPage.pages}");
        if(isNaN(Number(pageNum))){
            layer.msg("请输入正确的页码");
        }
        if(pageNum > pages||pageNum == 0){
            layer.msg("请输入页码[1-"+pages+"]");
            return;
        }
        $("#examForm").find("input[id='pageNum2']").val(pageNum);

        // 使用 jQuery异步提交表单
                $.ajax({
                    url:'${ctx}/mng/doc/examHistory',
                    data:$('#examForm').serialize(),
                    type:"POST",
                    dataType:'JSON',
                    success:function(data)
                    {
                        buildTbody2(data);
                        $("#pageList2").html(data.pageNum+" / "+${examPage.pages});

                        if(data.pageNum > 1){

                            $("#left2").attr("href","javascript:page2("+(data.pageNum-1)+")");
                        }
                        if(data.pageNum < ${examPage.pages}){

                            $("#right2").attr("href","javascript:page2("+(data.pageNum+1)+")");
                        }
                    }
                });
    }

    function buildTbody2(data){
       var html = template("exam",data);
       $("#examTable").html(html);
    }
</script>
</c:if>



<script id="exam" type="text/html">

    <table class="tj-table tj-table-re1 clearfix" id="examTable">
        <thead>
        <tr>
            <td class="tj-td-1">会议类型</td>
            <td class="tj-td-3">答题名称</td>
            <td class="tj-td-2 t-center">答题时间</td>
            <td class="tj-td-2 t-center">考试分数</td>
            <td class="tj-td-2 t-center">考试明细</td>
        </tr>
        </thead>
        <tbody id="examTBody">
        {{each dataList as exam}}
            <tr examId = "{{exam.id}}">
                <td class="tj-td-1 color-black"><span class="icon iconfont icon-minIcon8"></span>考试</td>
                <td class="tj-td-3 color-black">{{exam.paperName}}</td>
                <td class="tj-td-2 t-center"><span>{{exam.time}}</span></td>
                <td class="tj-td-2 t-center"><span>{{exam.score}}</span></td>
                <td class="tj-td-2 t-center"><a href="${pageContext.request.contextPath}/mng/doctor/examDetail?historyId={{exam.id}}" class="check-link" target="_blank">查看</a></td>
            </tr>
        {{/each}}
        </tbody>
    </table>


</script>




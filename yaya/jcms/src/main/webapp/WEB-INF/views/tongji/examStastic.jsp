<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/6/12
  Time: 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>考题统计</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>
<script src="${ctxStatic}/js/formatDate.js"></script>

<body>
    <div class="g-main clearfix">

        <%@include file="/WEB-INF/include/stastic_header.jsp"%>

        <div class="tj-con subPage-marginTop margin-top-not">
            <div class="tj-content clearfix">
                <div class="tj-top clearfix">
                    <h3>考试成绩公布</h3>
                    <a href="${ctx}/func/meet/exam/exportExcel?meetId=${param.id}" class="tj-more">导出Excel</a>
                </div>
                <table class="tj-table tj-table-re1 tj-table-maxSize clearfix">
                    <thead>
                    <tr>
                        <td class="tj-td-1">姓名</td>
                        <td class="tj-td-2">提交答卷时间</td>
                        <td class="tj-td-3">医院</td>
                        <td class="tj-td-4 t-center">考试分数</td>
                        <td class="tj-td-5 t-center">考试明细</td>
                    </tr>
                    </thead>
                    <tbody id="ks">  </tbody>
                </table>
                <div class="page-box" id="ksPage">
                </div>
            </div>
        </div>

        <div class="tj-con subPage-marginTop ">
            <div class="tj-content clearfix">
                <div class="tj-top clearfix">
                    <h3>考题数据统计</h3>
                    <a href="${ctx}/func/meet/exam/export?meetId=${param.id}" class="tj-more">导出Excel</a>
                </div>
                <table class="tj-table tj-table-re1 tj-table-maxSize clearfix">
                    <thead>
                    <tr>
                        <td class="tj-td-1 t-center">题号</td>
                        <td class="tj-td-2 t-center">答对次数</td>
                        <td class="tj-td-3 t-center">答错次数</td>
                        <td class="tj-td-4 t-center">答错概率</td>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${page.dataList}" var="e">
                            <tr>
                                <td class="tj-td-1 t-center">${e.sort}</td>
                                <td class="tj-td-2 t-center">${e.rightCount}</td>
                                <td class="tj-td-3 t-center">${e.errorCount}</td>
                                <td class="tj-td-4 t-center color-blue">${e.errorPercent}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <form id="pageForm" name="pageForm" action="${ctx}/func/meet/exam/statistics" method="post">
                    <input type="hidden" name="pageNum" id="pageNum" value="${page.pageNum}">
                    <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
                    <input type="hidden" name="id" value="${param.id}">
                </form>
                <%@include file="/WEB-INF/include/pageable.jsp"%>
            </div>
        </div>

    </div>

<script>
    var meetId = "${param.id}";
    $(function () {
        getExamRecord(1);
    });

    function getExamRecord(pageNum) {
        $.ajax({
            url: '${ctx}/func/meet/exam/history?pageNum=' + pageNum + '&id=' + meetId,
            type: "post",
            dataType: "json",
            success: function (data) {
                var dataList = data.data.dataList;
                if (dataList != null) {
                    $('#ks').html("");
                    $.each(dataList, function (i, val) {
                        var subTime = dataList[i].submitTime;
                        if (subTime == null) {
                            subTime = '';
                        } else {
                            subTime = format(subTime);
                        }
                        var unitName = dataList[i].unitName;
                        if (typeof (unitName) == "undefined") {
                            unitName = '';
                        }
                        $('#ks').append('<tr><td class="tj-td-1">' + dataList[i].nickname + '</td>'
                            + '<td class="tj-td-2">' + subTime + '</td>'
                            + '<td class="tj-td-3">' + unitName + '</td>'
                            + '<td class="tj-td-4 t-center">' + dataList[i].score + '</td>'
                            + '<td class="tj-td-5 t-center"><a href="${ctx}/mng/doctor/examDetail?historyId=' + dataList[i].id + '" '
                            + 'class="check-link" target="_blank">查看</a></td></tr>');
                    });

                    // 分页
                    var pagenum = data.data.pageNum;
                    var pages = data.data.pages;

                    var ksPageHtml = "";
                    if (pages > 1) {
                        ksPageHtml += '<a ';
                        if (pagenum > 1) {
                            ksPageHtml += 'href="javascript:tzpage(' + pages + ',' + (pagenum - 1) + ')"';
                        }
                        ksPageHtml += ' class="page-list page-border page-prev"><i></i></a>'
                            + '<span class="page-list">' + pagenum + ' / ' + pages + '</span><a ';
                        if (pagenum < pages) {
                            ksPageHtml += 'href="javascript:tzpage(' + pages + ',' + (pagenum + 1) + ')"';
                        }
                        ksPageHtml += ' class="page-list page-border page-next"><i></i></a>'
                            + '<input class="page-list page-border" id="tzpageNum" type="text">'
                            + '<a href="javascript:var pagePageNum=$(\'#tzpageNum\').val(); tzpage(' + pages + ',pagePageNum)" '
                            + 'class="page-list page-border page-link">跳转</a>';
                        $('#ksPage').html(ksPageHtml);
                    }

                } else {
                    $('#ksPage').html("<div style='text-align:center'>暂无数据</div>");
                }
            }, error: function (data) {
                alert("获取数据失败");
            }
        });
    }

    function tzpage(pages,pageNum){
        if(isNaN(Number(pageNum))){
            layer.msg("请输入正确的页码");
        }
        if(pageNum > pages||pageNum == 0){
            layer.msg("请输入页码[1-"+pages+"]");
            return;
        }
        getExamRecord(pageNum);
    }

    function add0(m){return m<10?'0'+m:m }

</script>
</body>
</html>

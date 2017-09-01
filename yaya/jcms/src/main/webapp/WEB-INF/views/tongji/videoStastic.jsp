<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/6/12
  Time: 9:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>视频统计</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>

<body>
<div class="g-main clearfix">
    <%@include file="/WEB-INF/include/stastic_header.jsp"%>

    <div class="tj-con subPage-marginTop margin-top-not">
        <div class="tj-content clearfix">
            <div class="tj-top clearfix">
                <h3>视频观看统计</h3>
                <a href="${ctx}/func/meet/video/exportvideo?meetId=${param.id}" class="tj-more">导出Excel</a>
            </div>
            <table class="tj-table tj-table-re1 tj-table-maxSize clearfix">
                <thead>
                <tr>
                    <td class="tj-td-3">视频名称</td>
                    <td class="tj-td-4">视频时长</td>
                    <td class="tj-td-4">观看人数</td>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${page.dataList}" var="v">
                        <tr>
                            <td class="tj-td-3">${v.name}</td>
                            <td class="tj-td-4">${v.duration}</td>
                            <td class="tj-td-4">${v.totalCount}人</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <form id="pageForm" name="pageForm" action="${ctx}/func/meet/video/statistics" method="post">
                <input type="hidden" name="pageNum" id="pageNum" value="${page.pageNum}">
                <input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
                <input type="hidden" name="id" value="${param.id}">
            </form>
            <%@include file="/WEB-INF/include/pageable.jsp"%>

        </div>
    </div>
    <div class="tj-con subPage-marginTop">
        <div class="tj-content clearfix">
            <div class="tj-top clearfix">
                <h3>观看进度统计</h3>
                <a href="${ctx}/func/meet/video/exportProgress?meetId=${param.id}" class="tj-more">导出Excel</a>
            </div>
            <table class="tj-table tj-table-re1 tj-table-maxSize clearfix">
                <thead>
                <tr>
                    <td class="tj-td-3">视频名称</td>
                    <td class="tj-td-4">视频时长</td>
                    <td class="tj-td-4">姓名</td>
                    <td class="tj-td-4">观看时长</td>
                    <td class="tj-td-4">完成进度</td>
                </tr>
                </thead>
                <tbody id="vpdata">

                </tbody>
            </table>
            <div class="page-box" id="vpage">

            </div>
        </div>
    </div>
</div>
<script>
    var meetId = "${param.id}";
    $(function () {
        getVideoData(1);
    });

    function getVideoData(pageNum) {
        $.ajax({
            url:'${ctx}/func/meet/video/view/progress?pageNum='+pageNum+'&id='+meetId,
            type: "post",
            dataType: "json",
            success: function (data) {
                var datas = data.data.dataList;
                // datas = eval(datas);
                console.log(datas);
                if(datas!=null){
                    $('#vpdata').html("");
                    $.each(datas,function(i,val) {
                        var name = datas[i].name;
                        $('#vpdata').append('<tr><td class="tj-td-3">'+datas[i].name+'</td>'
                            +'<td class="tj-td-4">'+datas[i].duration+'</td>'
                            +'<td class="tj-td-4">'+datas[i].nickname+'</td>'
                            +'<td class="tj-td-4">'+datas[i].usedtime+'</td>'
                            +'<td class="tj-td-4"><Strong>'+datas[i].viewProgress+'</Strong></td></tr>');
                    });
                    // 分页
                    var pagenum = data.data.pageNum;
                    var pages = data.data.pages;

                    var vpPageHtml = "";
                    if(pages>1){
                        vpPageHtml += '<a ';
                        if(pagenum>1){
                            vpPageHtml += 'href="javascript:tzpage('+pages+','+(pagenum-1)+')"';
                        }
                        vpPageHtml +=' class="page-list page-border page-prev"><i></i></a>'
                            +'<span class="page-list">'+pagenum+' / '+pages+'</span><a ';
                        if(pagenum<pages){
                            vpPageHtml += 'href="javascript:tzpage('+pages+','+(pagenum+1)+')"';
                        }
                        vpPageHtml += ' class="page-list page-border page-next"><i></i></a>'
                            +'<input class="page-list page-border" id="tzpageNum" type="text">'
                            +'<a href="javascript:var pagePageNum=$(\'#tzpageNum\').val(); tzpage('+pages+',pagePageNum)" '
                            +'class="page-list page-border page-link">跳转</a>';
                        $('#vpage').html(vpPageHtml);
                    }

                }else{
                    $('#vpage').html("<div style='text-align:center'>暂无数据</div>");
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
        getVideoData(pageNum);
    }
</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/5
  Time: 11:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${history.pages>1}">

<div class="page-box" >
    <a id="left"
            <c:if test="${history.pageNum>1}">
                  href="javascript:page(${history.pageNum - 1})" </c:if> class="page-list page-border page-prev"><i></i></a>
    <span class="page-list" id="pageList">${history.pageNum} / ${history.pages}</span>
    <a id="right"
            <c:if test="${history.pageNum<history.pages}">
                href="javascript:page(${history.pageNum+1})" </c:if>
                class="page-list page-border page-next"><i></i></a>
    <input class="page-list page-border" id="pagePageNum" type="text">
    <a href="javascript:var pagePageNum = $('#pagePageNum').val(); page(pagePageNum)" class="page-list page-border page-link">跳转</a>
</div>

<script>

    function page(pageNum){
        var pages = parseInt("${history.pages}");
        if(isNaN(Number(pageNum))){
            layer.msg("请输入正确的页码");
        }
        if(pageNum > pages||pageNum == 0){
            layer.msg("请输入页码[1-"+pages+"]");
            return;
        }
        $("#xsForm").find("input[name='pageNum']").val(pageNum);

        // 使用 jQuery异步提交表单
                $.ajax({
                    url:'${ctx}/mng/account/history',
                    data:$('#xsForm').serialize(),
                    type:"POST",
                    dataType:'JSON',
                    success:function(data)
                    {
                        buildTbody(data);
                        $("#pageList").html(data.pageNum+" / "+${history.pages});

                        if(data.pageNum > 1){

                            $("#left").attr("href","javascript:page("+(data.pageNum-1)+")");
                        }
                        if(data.pageNum < ${history.pages}){

                            $("#right").attr("href","javascript:page("+(data.pageNum+1)+")");
                        }
                    }
                });
    }

    function buildTbody(data){
       var html = template("xs",data);
       $("#xsTable").html(html);
    }
</script>
</c:if>


<script src="${ctxStatic}/js/template-simple.js"></script>

<script id="xs" type="text/html">

        <table class="tj-table tj-table-re1 clearfix" style="width: 100%;" id="xsTable">
            <thead class="thead-div notBorder">
            <tr>
                <td >象数交易事件</td>
                <td>时间</td>
            </tr>
            </thead>
            <tbody id="xsTBody">
            {{each dataList as trade}}
                <tr >
                    <td>{{trade.description}}</td>
                    <td class="color-gray">{{dateFormat(trade.costTime,'yyyy-MM-dd hh:mm')}}</td>
                </tr>
            {{/each}}
            </tbody>

    </table>





</script>

<script>
    /**
     * 对日期进行格式化，
     * @param date 要格式化的日期
     * @param format 进行格式化的模式字符串
     *     支持的模式字母有：
     *     y:年,
     *     M:年中的月份(1-12),
     *     d:月份中的天(1-31),
     *     h:小时(0-23),
     *     m:分(0-59),
     *     s:秒(0-59),
     *     S:毫秒(0-999),
     *     q:季度(1-4)
     * @return String
     * @author yanis.wang
     * @see    http://yaniswang.com/frontend/2013/02/16/dateformat-performance/
     */
    template.helper('dateFormat', function (date, format) {

        if (typeof date === "string") {
            var mts = date.match(/(\/Date\((\d+)\)\/)/);
            if (mts && mts.length >= 3) {
                date = parseInt(mts[2]);
            }
        }
        date = new Date(date);
        if (!date || date.toUTCString() == "Invalid Date") {
            return "";
        }

        var map = {
            "M": date.getMonth() + 1, //月份
            "d": date.getDate(), //日
            "h": date.getHours(), //小时
            "m": date.getMinutes(), //分
            "s": date.getSeconds(), //秒
            "q": Math.floor((date.getMonth() + 3) / 3), //季度
            "S": date.getMilliseconds() //毫秒
        };


        format = format.replace(/([yMdhmsqS])+/g, function(all, t){
            var v = map[t];
            if(v !== undefined){
                if(all.length > 1){
                    v = '0' + v;
                    v = v.substr(v.length-2);
                }
                return v;
            }
            else if(t === 'y'){
                return (date.getFullYear() + '').substr(4 - all.length);
            }
            return all;
        });
        return format;
    });
</script>


<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/5
  Time: 11:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${meetPage.pages>1}">

<div class="page-box" >
    <a id="left"
            <c:if test="${meetPage.pageNum>1}">
                  href="javascript:page(${meetPage.pageNum - 1})" </c:if> class="page-list page-border page-prev"><i></i></a>
    <span class="page-list" id="pageList">${meetPage.pageNum} / ${meetPage.pages}</span>
    <a id="right"
            <c:if test="${meetPage.pageNum<meetPage.pages}">
                href="javascript:page(${meetPage.pageNum+1})" </c:if>
                class="page-list page-border page-next"><i></i></a>
    <input class="page-list page-border" id="pagePageNum" type="text">
    <a href="javascript:var pagePageNum = $('#pagePageNum').val(); page(pagePageNum)" class="page-list page-border page-link">跳转</a>
</div>

<script>

    function page(pageNum){
        var pages = parseInt("${meetPage.pages}");
        if(isNaN(Number(pageNum))){
            layer.msg("请输入正确的页码");
        }
        if(pageNum > pages||pageNum == 0){
            layer.msg("请输入页码[1-"+pages+"]");
            return;
        }
        $("#meetForm").find("input[name='pageNum']").val(pageNum);

        // 使用 jQuery异步提交表单
                $.ajax({
                    url:'${ctx}/mng/doc/meetHistory',
                    data:$('#meetForm').serialize(),
                    type:"POST",
                    dataType:'JSON',
                    success:function(data)
                    {
                        buildTbody(data);
                        $("#pageList").html(data.pageNum+" / "+${meetPage.pages});

                        if(data.pageNum > 1){
                            $("#left").attr("href","javascript:page("+(data.pageNum-1)+")");
                        }
                        if(data.pageNum < ${meetPage.pages}){

                            $("#right").attr("href","javascript:page("+(data.pageNum+1)+")");
                        }
                    }
                });
    }

    function buildTbody(data){
       var html = template("meet",data);
       $("#meetTable").html(html);
    }
</script>
</c:if>


<script src="${ctxStatic}/js/template-simple.js"></script>

<script id="meet" type="text/html">
    <table class="tj-table tj-table-re1 clearfix" id="meetTable">
        <thead>
        <tr>
            <td class="tj-td-1">会议类型</td>
            <td class="tj-td-3">会议名称</td>
            <td class="tj-td-1">开场时间</td>
            <td class="tj-td-1">学习时长</td>
        </tr>
        </thead>
        <tbody id="meetTBody">
        {{each dataList as meet}}
        <tr meetId="{{meet.meetId}}">
            <td class="tj-td-1 color-black"><span class="icon iconfont icon-minIcon3"></span>微课</td>
            <td class="tj-td-3"><a href="${ctx}/func/meet/view?id={{meet.meetId}}&tag=1" >{{meet.meetName}}</a></td>
            <td class="tj-td-1" > {{dateFormat(meet.publishTime,'yyyy-MM-dd hh:mm')}}</td>
            <td class="tj-td-1"><span>{{meet.time}}</span></td>
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


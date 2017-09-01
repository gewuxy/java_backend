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
    <title>问卷统计</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
</head>

<body>
    <div class="g-main clearfix">
        <%@include file="/WEB-INF/include/stastic_header.jsp"%>

        <div class="tj-con subPage-marginTop margin-top-not">
            <div class="tj-content clearfix">
                <div class="tj-top clearfix">
                    <h3>问卷统计</h3>
                    <a href="${ctx}/func/meet/survey/exportExcel?meetId=${param.id}" class="tj-more">导出Excel</a>
                </div>
                <table class="tj-table tj-table-re1 tj-table-maxSize clearfix">
                    <thead>
                    <tr>
                        <td class="tj-td-1">提交答卷时间</td>
                        <td class="tj-td-2">姓名</td>
                        <td class="tj-td-3">医院</td>
                        <td class="tj-td-4">科室</td>
                        <td class="tj-td-5 t-center">问卷明细</td>
                    </tr>
                    </thead>
                    <tbody id="sv_record">

                    </tbody>
                </table>

                <div class="page-box" id="sv_page"></div>
            </div>
        </div>
        <div class="tj-con subPage-marginTop ">
            <div class="tj-content clearfix">
                <div class="tj-top clearfix">
                    <a href="${ctx}/func/meet/survey/exportSvyData?meetId=${param.id}" class="tj-more">导出Excel</a>
                </div>

                <div id="surveyData">

                </div>

                <div class="page-box" id="surveyPage">

                </div>
            </div>
        </div>
    </div>

    <script src="${ctxStatic}/js/jquery.min.js"></script>
    <script src="${ctxStatic}/js/moment.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/js/jquery.daterangepicker.js"></script>
    <script src="${ctxStatic}/laydate/laydate.js"></script>
    <script src="${ctxStatic}/js/echarts.min.js"></script>
    <script src="${ctxStatic}/js/main.js"></script>
    <script src="${ctxStatic}/js/formatDate.js"></script>

    <script>

        var meetId = "${param.id}";
        $(function () {
            getSurveyRecord(1);
        });


        function getSurveyRecord(pageNum){
            $.ajax({
                url:'${ctx}/func/meet/survey/record?pageNum='+pageNum+'&id='+meetId,
                dataType:"json",
                success:function (data) {
                    var datas = data.data.dataList;
                    console.log(datas);
                    if(datas!=null && datas.length!=0){
                        $('#sv_record').html("");
                        $.each(datas,function (i,val) {
                            var subTime = datas[i].submitTime;
                            if(subTime==null){
                                subTime = '';
                            }else{subTime = format(subTime);}

                            var nickName = datas[i].nickname;
                            if (typeof (nickName)=="undefined"){
                                nickName = '';
                            }
                            var unitName = datas[i].unitName;
                            if (typeof (unitName)=="undefined"){
                                unitName = '';
                            }
                            var subUnitName = datas[i].subUnitName;
                            if (typeof (subUnitName)=="undefined"){
                                subUnitName = '';
                            }

                            $('#sv_record').append('<tr><td class="tj-td-1">'+subTime+'</td>'
                                            +'<td class="tj-td-2">'+nickName+'</td>'
                                            +'<td class="tj-td-3">'+unitName+'</td>'
                                            +'<td class="tj-td-4">'+subUnitName+'</td>'
                                            +'<td class="tj-td-5 t-center">'
                                            +'<a href="${ctx}/mng/doctor/surveyDetail?historyId='+datas[i].id+'" '
                                            +' class="check-link" target="_blank">查看</a></td></tr>'
                                            );
                        });

                        // 分页
                        var pagenum = data.data.pageNum;
                        var pages = data.data.pages;

                        var svPageHtml = "";
                        if(pages>1){
                            svPageHtml += '<a ';
                            if(pagenum>1){
                                svPageHtml += 'href="javascript:tzpage('+pages+','+(pagenum-1)+',1)"';
                            }
                            svPageHtml +=' class="page-list page-border page-prev"><i></i></a>'
                                +'<span class="page-list">'+pagenum+' / '+pages+'</span><a ';
                            if(pagenum<pages){
                                svPageHtml += 'href="javascript:tzpage('+pages+','+(pagenum+1)+',1)"';
                            }
                            svPageHtml += ' class="page-list page-border page-next"><i></i></a>'
                                +'<input class="page-list page-border" id="svpageNum" type="text">'
                                +'<a href="javascript:var pagePageNum=$(\'#svpageNum\').val(); tzpage('+pages+',pagePageNum,1)" '
                                +'class="page-list page-border page-link">跳转</a>';
                            $('#sv_page').html(svPageHtml);
                        }
                    }else{
                        $('#sv_page').html("<div style='text-align:center'>暂无数据</div>");
                    }


                }, error: function (data) {
                    alert("获取数据失败");
                }
            });
        }
    </script>
    <script>
        var meetId = "${param.id}";
        $(function () {
            getSurveyData(1);
        });
        function getSurveyData(pageNum) {
            $.ajax({
                url:'${ctx}/func/meet/survey/option/statistics?pageNum='+pageNum+'&id='+meetId,
                type: "post",
                dataType: "json",
                success: function (data) {
                    var datas = data.data.dataList;
                    console.log(datas);
                    if(datas!=null){
                        $('#surveyData').html("");
                        var optionsArr = new Array();
                        var countsArr = new Array();
                        $.each(datas,function(i,val) {
                            optionsArr = [];
                            countsArr = [];
                            var qtype = datas[i].qtype;
                            var type ;
                            switch (qtype){
                                case 0:
                                    type = "单选题";
                                    break;
                                case 1:
                                    type = "多选题";
                                    break;
                                case 2:
                                    type = "填空题";
                                    break;
                                case 3:
                                    type = "问答题";
                                    break;
                            }

                            var surveyHtml = '<div class="tj-top echarts-faq-row  clearfix">'
                                +'<h3 class="echart-txt"><span class="color-blue">【'+type+'】-&nbsp;</span>'
                                +'Q'+datas[i].sort+'：'+datas[i].title+'</h3>'
                                +'<div id="echarts-'+i+'" class="echarts-faq clearfix"></div>'
                                +'<div class="echarts-result"><ul id="l"'+i+'>';
                                $.each(datas[i].surveyRecordItemDTO,function (x,val) {
                                    surveyHtml += '<li><strong>'+datas[i].surveyRecordItemDTO[x].selCount+'人</strong>'
                                    +'<span>选择'+datas[i].surveyRecordItemDTO[x].optkey+'-</span>'+datas[i].surveyRecordItemDTO[x].option+''
                                    +'</li>';
                                })
                                surveyHtml += '</ul></div></div>';
                                $('#surveyData').append(surveyHtml);

                                $.each(datas[i].surveyRecordItemDTO,function (v,val) {
                                    optionsArr.push('选择'+datas[i].surveyRecordItemDTO[v].optkey);
                                    optionsArr.join(",");
                                    countsArr.push(datas[i].surveyRecordItemDTO[v].selCount);
                                    countsArr.join(",");
                                });
                                if(optionsArr && countsArr){
                                    chartModel('echarts-'+i,optionsArr,countsArr);
                                }
                        });

                        // 分页
                        var pagenum = data.data.pageNum;
                        var pages = data.data.pages;

                        var svPageHtml = "";
                        if(pages>1){
                            svPageHtml += '<a ';
                            if(pagenum>1){
                                svPageHtml += 'href="javascript:tzpage('+pages+','+(pagenum-1)+',2)"';
                            }
                            svPageHtml +=' class="page-list page-border page-prev"><i></i></a>'
                                    +'<span class="page-list">'+pagenum+' / '+pages+'</span><a ';
                            if(pagenum<pages){
                                svPageHtml += 'href="javascript:tzpage('+pages+','+(pagenum+1)+',2)"';
                            }
                            svPageHtml += ' class="page-list page-border page-next"><i></i></a>'
                                +'<input class="page-list page-border" id="tzpageNum" type="text">'
                                +'<a href="javascript:var pagePageNum=$(\'#tzpageNum\').val(); tzpage('+pages+',pagePageNum,2)" '
                                +'class="page-list page-border page-link">跳转</a>';
                            $('#surveyPage').html(svPageHtml);
                        }

                    }else{
                        $('#surveyPage').html("<div style='text-align:center'>暂无数据</div>");
                        if(optionsArr.length==0){
                            optionsArr = ["0"];
                        }
                        if(countsArr.length==0){
                            countsArr = ["0"];
                        }
                        if(optionsArr && countsArr){
                            setChartData(optionsArr,countsArr);
                        }

                    }

                }, error: function (data) {
                    alert("获取数据失败");
                }
            });
        }

        function tzpage(pages,pageNum,tag){
            if(isNaN(Number(pageNum))){
                layer.msg("请输入正确的页码");
            }
            if(pageNum > pages||pageNum == 0){
                layer.msg("请输入页码[1-"+pages+"]");
                return;
            }
            if(tag==1){
                getSurveyRecord(pageNum);
            }else{
                getSurveyData(pageNum);
            }

        }

        //执行插件方法
        function chartModel(dom,optionData,seriesData){
            var modelDom = document.getElementById(dom);
            var modelInit = echarts.init(modelDom);

            var optionItem = {
                color: ['#3398DB'],
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    top:'5%'
                },
                yAxis : [
                    {
                        type : 'category',
                        data : optionData
                    }
                ],
                xAxis : [
                    {
                        type : 'value',
                        boundaryGap : [0, 0.01]
                    }
                ],
                series : [
                    {
                        name:'选择人数',
                        type:'bar',
                        data:seriesData
                    }
                ]
            };

            if (optionItem && typeof optionItem === "object") {
                modelInit.setOption(optionItem, true);
            }

        }


    </script>
</body>
</html>

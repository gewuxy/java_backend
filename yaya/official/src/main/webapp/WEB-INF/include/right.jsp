<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-lg-3 col-lg-offset-1">
    <div class="v2-news-list v2-news-list-garyStyle">
        <div class="v2-news-title">
            <a href="${ctx}/news/list?type=1" class="more fr">更多&gt;&gt;</a>
            <h3>在线购药指南</h3>
        </div>
        <div class="v2-news-main">
            <ul id="safeMedication">

            </ul>
        </div>
    </div>
    <div class="v2-news-list v2-news-list-garyStyle">
        <div class="v2-news-title">
            <a href="${ctx}/search/searchList?searchType=4&keyWord=阿司匹林" class="more fr">更多&gt;&gt;</a>
            <h3>用药常识</h3>
        </div>
        <div class="v2-news-main">
            <ul id="MedicationKnowledge">
            </ul>
        </div>
    </div>
    <div class="v2-news-list v2-news-list-garyStyle">
        <div class="v2-news-title">
            <a href="newList.html" class="more fr">更多&gt;&gt;</a>
            <h3>热门医药新闻</h3>
        </div>
        <div class="v2-news-main">
            <ul id="hotNews">
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctxStatic}/js/v2/jquery.min.js"></script>
<script>
    //加载新闻列表
    $(function () {
        addNewList("/news/ajaxTrends","safeMedication");
        addNewList("/news/ajaxTrends","MedicationKnowledge");
        addNewList("/news/ajaxTrends","hotNews");
    })

    function addNewList(url,id){
        $.get('${base}' + url,{'pageNum':1,'pageSize':6}, function (data) {
            if(data.code == 0){
                for(var index in data.data.dataList){
                    $("#" + id).append('<li><a href="${base}/news/detail/'+data.data.dataList[index].id+'">' + getBeforeTitle(data.data.dataList[index].title)+'</a></li>');
                }
            }
        },'json');
    }

    function getBeforeTitle(title){
        return title.length > 14? title.substring(0,14) + "...":title;
    }
</script>

<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>搜索结果-文字列表</title>
    <%@include file="/WEB-INF/include/staticResource2.jsp" %>
</head>
<body>
<div id="wrapper" class="v2-medcnIndex">
    <%@include file="/WEB-INF/include/header_index2.jsp" %>

    <div class="v2-sub-main" style="padding:40px 0 80px;">
        <div class="page-width clearfix">
            <div class="row">
                <div class="col-lg-8 ">
                    <div class="v2-subPage-searchItem">

                        <div class="v2-subPage-searchTitle clearfix">
                            <img src="${statics}/images/v2/searchItem-title2.png" alt="">
                        </div>
                        <div class="v2-subPage-serarch-content v2-helpPage-item">
                            <form action="${base}/search/pharmacist/advice/list" method="post">
                                <span class="pr v2-helpPage-select-a">
                                    <i class="v2-helpPage-select-arrow"></i>
                                    <select name="categoryId" id="child_category" class="v2-helpPage-select">
                                        <c:if test="${childCategoryList==null}">
                                            <option value="0">分类查找</option>
                                        </c:if>
                                        <c:if test="${childCategoryList!=null}">
                                            <option value="0">分类查找</option>
                                            <c:forEach items="${childCategoryList}" var="childCategory" varStatus="status">
                                                <option value="${childCategory.id}">${childCategory.name}</option>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                </span>
                                <div class="v2-search-form clearfix">
                                    <input id="searchingText" name="searchingText" type="text" placeholder="药品通用名/商品名/批准文号" class="form-text">
                                    <button id="search_btn" type="submit" class="form-btn"><span></span></button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <p class="v2-searchText-error none">没有查找到相关结果，我们正在努力搜集数据!</p>
                    <p class="v2-searchText ">
                        <c:if test="${searchingText!=null}">
                            搜索结果：${searchingText}
                        </c:if>
                    </p>
                    <div class="v2-newsList-bottomBorder">
                        <ul>
                            <c:if test="${myPage.dataList!=null}">
                                <c:forEach items="${myPage.dataList}" var="dataFile" varStatus="status">
                                    <%--<c:if test="${status.index%2 == 0}"></c:if>--%>
                                    <li><a href="${base}/search/pharmacist/advice/detail?id=${dataFile.id}">${dataFile.title}</a></li>
                                </c:forEach>
                            </c:if>
                        </ul>
                    </div>
                    <div id="paging_button_div" class="v2-page-box t-center">
                        <!--页面加载完后再生成分页按钮-->
                    </div>
                </div>
                <%@include file="/WEB-INF/include/aside2.jsp" %>
            </div>
        </div>
    </div>

    <%@include file="/WEB-INF/include/footer.jsp" %>
</div>
<script type="text/javascript">
    myPage = <%=JSONArray.toJSONString(request.getAttribute("myPage"))%>;
    $(function () {

        //初始化分类设置
        <c:if test="${childCategoryId != null}">
            $("#child_category").val("${childCategoryId}");
        </c:if>

        //提交搜索
        $("#search_btn").click(function () {
            var searchingText = $("#searchingText").val();
            if (searchingText == "") {
                layer.tips('请输入搜索内容', '#searchingText', {
                    tips: [1, '#3595CC'],
                    time: 3000
                });
                return false;
            }
            return true;
        });

        //分页按钮
        var url = "${base}/search/pharmacist/advice/list";//指定跳转路径
        init_paging_button_div(${myPage.pageNum}, ${myPage.pageSize}, ${myPage.total}, url);

    });


    //生成分页按钮，需要传入四个参数：第几页（1-n)，每页行数，总数，点击按钮跳转的url
    //点击产生的跳转链接为：url+"?pageNum=" + 第几页(1 - n) + "&pageSize=" + 每页行数
    function init_paging_button_div(pageNum, pageSize, total, url) {
        //t(pageNum+","+pageSize+","+total+","+url);
        var $paging_button_div = $("#paging_button_div");
        if(total==0){
            $paging_button_div.html("T_T 没找到您要的内容哦！");
            return;
        }

        url += "?pageSize=" + pageSize + "&pageNum=";
        var startPage = 1;
        var endPage = 1;
        var cur_index = 0;
        if (pageNum == 1) {
            startPage = 1;
        } else if (pageNum == 2) {
            startPage = 1;
        } else if (pageNum > 2) {
            startPage = pageNum - 2;
        }
        var allPage = Math.ceil(total / pageSize);
        if (pageNum == allPage) {
            endPage = pageNum;
        } else if (pageNum + 1 == allPage) {
            endPage = pageNum + 1;
        } else if (pageNum + 1 < allPage) {
            endPage = pageNum + 2;
        } else {
            endPage = allPage;//pageNum>allPage,超出有内容以外的页面
        }

        $paging_button_div.html("");
        if (pageNum > 1) {
            $paging_button_div.append('<a href="' + url + (pageNum - 1) + '" class="v2-page-box-prev" title="上一页"></a>');
        }
        for (var i = startPage; i <= endPage; i++) {
            if (i == pageNum) {
                $paging_button_div.append('<a href="#" class="cur">' + i + '</a>');
            } else {
                $paging_button_div.append('<a href="' + url + i + '">' + i + '</a>');
            }
        }
        if (pageNum < allPage) {
            $paging_button_div.append('<a href="' + url + (pageNum + 1) + '" class="v2-page-box-next" title="下一页"></a>');
        }

        $paging_button_div.append('&nbsp; &nbsp;<input type="number" id="page_jump_input" style="width:80px;">');
        $paging_button_div.append('<a onclick="jump_page()" class="cur" style="cursor: pointer">go</a>');
        $paging_button_div.append('<span href="' + url + (allPage) + '">共' + allPage + '页</span>');
        jump_page=function(){
            var jump_page_num = $("#page_jump_input").val();
            if (jump_page_num == "" || jump_page_num < 1 || jump_page_num > allPage) {
                layer.tips('请输入正确的页码', '#page_jump_input', {
                    tips: [1, '#3595CC'],
                    time: 3000
                });
                return;
            }
            window.location = url + jump_page_num;
        }
    }


</script>

<!--弹出层-->
<%@include file="/WEB-INF/include/popup_layer2.jsp" %>

<div class="gotop-wrapper index-gotop">
    <a class="gotop" href="javascript:;">回到顶部</a>
</div>
<script src="${statics}/js/v2/stickUp.min.js"></script>
<script type="text/javascript">
    /*<!--轮播广告-->*/
    $(window).bind("load resize", function () {
        $("#carousel-05").carouFredSel({
            width: '100%',
            items: {visible: 1},
            auto: {pauseOnHover: true, timeoutDuration: 5000},
            swipe: {onTouch: true, onMouse: true},
            pagination: "#carousel-page-05",
//                                    prev 		: { button:"#carousel-prev-01"},
//                                    next 		: { button:"#carousel-next-01"},
            scroll: {fx: "crossfade"}
        });
    });
    /*固定栏*/
    jQuery(function ($) {
        $(document).ready(function () {
            $('.fixed-nav').stickUp({
                marginTop: 'auto'
            });
        });
    });


</script>


</body>
</html>
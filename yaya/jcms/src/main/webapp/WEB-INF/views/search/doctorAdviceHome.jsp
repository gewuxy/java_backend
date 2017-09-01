<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>搜索-对症找药</title>
    <%@include file="/WEB-INF/include/staticResource2.jsp" %>
</head>
<body>
<div id="wrapper" class="v2-medcnIndex">
    <%@include file="/WEB-INF/include/header_index2.jsp" %>

    <div class="v2-sub-main v2-searchBg">
        <div class="page-width clearfix">
            <div class="row">
                <div class="col-lg-12">
                    <div class="v2-fullSearch">
                        <div class="v2-fullSearch-title"><img src="${statics}/images/v2/fullSearch-title1.png" alt="">
                        </div>
                        <div class="v2-subPage-searchItem">
                            <div class="v2-subPage-serarch-content v2-helpPage-item">
                                <form action="${base}/search/doctor/advice/list" method="post">
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
                                        <input id="searchingText" name="searchingText" type="text" placeholder="输入需要医师提供建议的疾病名称" class="form-text">
                                        <button id="search_btn" type="submit" class="form-btn"><span></span></button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/include/footer.jsp" %>
</div>
<script type="text/javascript">
    $(function () {

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

    })
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

    /*自动拉伸高度*/
    var mainItem = $(".v2-searchBg");
    var totalHeight = mainItem.outerHeight(true) + $(".v2-top").outerHeight(true) + $(".v2-bottom").outerHeight(true) + $(".v2-footer").outerHeight(true);
    $(window).height() > totalHeight ? mainItem.height($(window).height() - ($(".v2-top").outerHeight(true) + $(".v2-bottom").outerHeight(true) + $(".v2-footer").outerHeight(true))) : "";


</script>


</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>首页</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
</head>
<body>
<div id="wrapper" class="v2-medcnIndex">
    <%@include file="/WEB-INF/include/header.jsp" %>
    <div class="v2-sub-main" style="padding:40px 0 80px;">
        <div class="page-width clearfix" >
            <div class="row">
                <div class="col-lg-8 ">
                    <div class="v2-subPage-searchItem">
                        <div class="v2-subPage-searchTitle clearfix">
                            <img src="${ctxStatic}/images/v2/searchItem-title-${searchType}.png" alt="">
                        </div>
                        <form action="${ctx}/search/searchList" method="post">
                            <div class="v2-subPage-serarch-content v2-helpPage-item">
                                    <span class="pr v2-helpPage-select-a">
                                        <i class="v2-helpPage-select-arrow"></i>
                                        <select name="classify" id="classify" class="v2-helpPage-select">
                                            <option value="">分类查询</option>
                                            <c:if test="${not empty list}">
                                                <c:forEach items="${list}" var="list">
                                                    <option value="${list.id}" ${list.id eq categoryId?"selected":""}>${list.name}</option>
                                                </c:forEach>
                                            </c:if>
                                        </select>
                                    </span>
                                <div class="v2-search-form clearfix">
                                    <input type="text"  placeholder="药品通用名/商品名/批准文号" name="keyWord" id="searchWord" value="${keyWord}" class="form-text" >
                                    <input  name="searchType" type="hidden" value="${searchType}"/>
                                    <button type="submit" class="form-btn" id="btnSearch"><span></span></button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <c:choose>
                        <c:when test="${not empty page.dataList}">
                            <p class="v2-searchText ">搜索结果：${keyWord} </p>
                        </c:when>
                        <c:otherwise>
                            <p class="v2-searchText-error">没有查找到相关结果，我们正在努力搜集数据!</p>
                        </c:otherwise>
                    </c:choose>
                    <div class="v2-newsList-bottomBorder" id="listView">
                        <c:if test="${not empty page.dataList}">
                            <c:choose>
                                <c:when test="${searchType eq 'YSJY'}">
                                    <ul>
                                        <c:forEach items="${page.dataList}" var="result">
                                            <li><a href="${ctx}/search/detail/${result.id}">${result.title}</a></li>
                                        </c:forEach>
                                    </ul>
                                </c:when>
                                <c:when test="${searchType eq 'YISJY'}">
                                    <c:forEach items="${page.dataList}" var="news">
                                        <div class="v2-news-graphic-item clearfix">
                                            <div class="fl v2-news-graphic-img">
                                                <a href="${ctx}/news/detail/${news.id}"><img src="${ctx}${news.articleImg}" alt=""></a>
                                                <i class="v2-news-graphic-classIcon"><a href="#">${fn:split(news.keywords, "，")[0]}</a></i>
                                            </div>
                                            <div class="oh">
                                                <h3><a href="${ctx}/news/detail/${news.id}">${news.title}</a></h3>
                                                <p class="v2-news-graphic-info">${news.summary}</p>
                                                <p >关键字：
                                                    <c:forEach items="${fn:split(news.keywords,'，')}" var="words" varStatus="stat">
                                                        <a href="#" class="color-blue"> ${words} </a>
                                                        <c:if test="${fn:length(fn:split(news.keywords,'，')) - 1  != stat.index}">
                                                            |
                                                        </c:if>
                                                    </c:forEach>
                                                </p>
                                                <p><span class="time fr"><fmt:formatDate value="${news.createTime}" pattern="yyyy/MM/dd"/></span><span>来源：${news.xfrom}</span></p>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <ul>
                                        <c:forEach items="${page.dataList}" var="result">
                                            <li><a href="${ctx}/search/detail/${result.id}">${result.title}</a></li>
                                        </c:forEach>
                                    </ul>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </div>
                    <div class="v2-page-box">
                        <a <c:if test="${page.pageNum>1}"> href="javascript:page(${page.pageNum-1})"</c:if> class="v2-page-box-prev" title="上一页"></a>
                        <c:forEach begin="${page.pageNum-5>0?page.pageNum-5:1}" end="${page.pages-page.pageNum > 5?page.pageNum+5:page.pages}" var="pn">
                            <a href="javascript:page(${pn})" <c:if test="${pn==page.pageNum}">class="cur"</c:if> >${pn}</a>
                        </c:forEach>
                        <a <c:if test="${page.pageNum<page.pages}"> href="javascript:page(${page.pageNum+1})"</c:if> class="v2-page-box-next" title="下一页"></a>
                    </div>
                    <form action="${base}/search/searchList" method="post" name="pageForm" id="pageForm">
                        <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
                        <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input  name="searchType" type="hidden" value="${searchType}"/>
                        <input  id="dataList" name="page" type="hidden" value="${page.dataList}"/>
                        <input  name="keyWord" type="hidden" value="${keyWord}"/>
                    </form>
                </div>
                <!--右侧模块 -->
                <%@include file="/WEB-INF/include/right.jsp" %>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/include/footer.jsp" %>
</div>
<!-- 弹出层-->
<%@include file="/WEB-INF/include/markWrap.jsp" %>
<script>
    $(function(){
        $(".three").addClass("current");

        var id = $("#dataList").val();
        console.info(id);
    })

    function page(pageNum){
        $("#pageForm").find("input[name='pageNum']").val(pageNum);
        $("#keyWord").val($("#searchWord").val());
        $("#pageForm").submit();
    }
</script>
</body>
</html>
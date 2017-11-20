<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>首页</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
</head>
<div id="wrapper" class="v2-medcnIndex">
    <%@include file="/WEB-INF/include/header.jsp" %>
    <div class="v2-sub-main" style="padding:40px 0 80px;">
        <div class="page-width clearfix" >
            <div class="row">
                <div class="col-lg-8 ">
                    <div class="v2-subPage-searchItem">
                        <div class="v2-subPage-searchTitle clearfix">
                            <img src="${ctxStatic}/images/v2/searchItem-title2.png" alt="">
                        </div>
                        <div class="v2-subPage-serarch-content v2-helpPage-item">
                                <span class="pr v2-helpPage-select-a">
                                    <i class="v2-helpPage-select-arrow"></i>
                                    <select name="" id="" class="v2-helpPage-select">
                                    <option value="">分类查找</option>
                                    <option value="">常用药品</option>
                                    <option value="">儿童常用药品</option>
                                    <option value="">用药指南</option>
                                    <option value="">在线购药指南</option>
                                </select>
                                </span>
                            <div class="v2-search-form clearfix">
                                <input type="text"  placeholder="药品通用名/商品名/批准文号" name="searchWord" id="searchWord" value="${keyWords}" class="form-text" >
                                <button type="submit" class="form-btn" id="btnSearch"><span></span></button>
                            </div>
                        </div>
                    </div>
                    <c:if test="${not empty page.dataList}">
                        <p class="v2-searchText ">搜索结果：${keyWords} </p>
                    </c:if>
                    <c:if test="${empty page.dataList}">
                        <p class="v2-searchText-error none">没有查找到相关结果，我们正在努力搜集数据!</p>
                    </c:if>
                    <div class="v2-newsList-bottomBorder">
                        <ul>
                            <c:if test="${not empty page.dataList}">
                                <c:forEach items="${page.dataList}" var="result">
                                    <li><a href="${ctx}/search/deatil/${result.id}">${result.title}</a></li>
                                </c:forEach>
                            </c:if>
                        </ul>
                    </div>
                    <div class="v2-page-box">
                        <a <c:if test="${page.pageNum>1}"> href="javascript:page(${page.pageNum-1})"</c:if> class="v2-page-box-prev" title="上一页"></a>
                        <c:forEach begin="${page.pageNum-5>0?page.pageNum-5:1}" end="${page.pages-page.pageNum > 5?page.pageNum+5:page.pages}" var="pn">
                            <a href="javascript:page(${pn})" <c:if test="${pn==page.pageNum}">class="cur"</c:if> >${pn}</a>
                        </c:forEach>
                        <a <c:if test="${page.pageNum<page.pages}"> href="javascript:page(${page.pageNum+1})"</c:if> class="v2-page-box-next" title="下一页"></a>
                    </div>
                    <form action="${base}/news/list" method="post" name="pageForm" id="pageForm">
                        <input  name="pageNum" type="hidden" value="${page.pageNum}"/>
                        <input  name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input  name="type" type="hidden" value="${type}"/>
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
    function page(pageNum){
        $("#pageForm").find("input[name='pageNum']").val(pageNum);
        $("#submitWord").val($("#searchWord").val());
        $("#pageForm").submit();
    }
</script>
</body>
</html>
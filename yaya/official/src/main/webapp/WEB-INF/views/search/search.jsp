<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>我的工具</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
</head>
<body>
<div id="wrapper" class="v2-medcnIndex">
    <%@include file="/WEB-INF/include/header.jsp" %>
    <div class="v2-sub-main v2-searchBg">
        <div class="page-width clearfix" >
            <div class="row">
                <div class="col-lg-12">
                    <div class="v2-fullSearch">
                        <div class="v2-fullSearch-title"><img src="${ctxStatic}/images/v2/fullSearch-title${searchType}.png" alt=""></div>
                        <div class="v2-subPage-searchItem">
                            <div class="v2-subPage-serarch-content v2-helpPage-item">
                                <form action="${ctx}/search/searchList" method="post">
                                    <span class="pr v2-helpPage-select-a">
                                        <i class="v2-helpPage-select-arrow"></i>
                                        <select name="search" id="search" class="v2-helpPage-select">
                                                <option value="">分类查找</option>
                                                <option value="">常用药品</option>
                                                <option value="">儿童常用药品</option>
                                                <option value="">用药指南</option>
                                                <option value="">在线购药指南</option>
                                        </select>
                                    </span>
                                    <div class="v2-search-form clearfix">
                                        <c:if test="${searchType eq 1}">
                                            <input type="text"  placeholder="输入需要药师提供建议的药品通用名" name="keyWord" id="keyWord"  class="form-text" >
                                        </c:if>
                                        <c:if test="${searchType eq 2}">
                                            <input type="text"  placeholder="输入症状名称" name="keyWord" id="keyWord"  class="form-text" >
                                        </c:if>
                                        <c:if test="${searchType eq 3}">
                                            <input type="text"  placeholder="输入需要医师提供建议的疾病名称" name="keyWord" id="keyWord"  class="form-text" >
                                        </c:if>
                                        <input type="hidden" name="searchType" value="${searchType}"/>
                                        <button type="submit" class="form-btn"><span></span></button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="v2-hot-search">
                            <span class="color-blue">热门搜索：</span>
                            <c:if test="${not empty hotList}">
                                <c:forEach items="${hotList}" var="hot" varStatus="stat">
                                    <a href="${ctx}/search/searchList/?keyWord=${hot.search}">${hot.search}</a>
                                    <c:if test="${!stat.last}">
                                        <span class="muted">|</span>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/include/footer.jsp" %>
</div>
<!--弹出层-->
<%@include file="/WEB-INF/include/markWrap.jsp" %>
<%@ include file="/WEB-INF/include/common_js.jsp" %>
</body>
</html>
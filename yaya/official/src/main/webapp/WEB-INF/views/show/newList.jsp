<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html lang="en">
<head>
    <title>${title} - 敬信药草园</title>
    <meta id="MetaDescription" name="DESCRIPTION" content="敬信药草园医药动态资讯平台，为医生和科研工作者提供最新的医药行业信息，内容覆盖社会政策、科研进展、新药上市、临床研究等方面。" />
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
</head>
<body>
<div id="wrapper" class="v2-medcnIndex">
    <%@include file="/WEB-INF/include/header.jsp" %>
    <div class="v2-sub-main" style="padding:40px 0 80px;">
        <div class="page-width clearfix" >
            <div class="row">
                <div class="col-lg-8 ">
                    <div class="v2-subPage-title-left">
                        <h3>${title}</h3>
                    </div>
                    <div class="v2-news-graphic v2-news-graphic-extend">
                        <c:forEach items="${page.dataList}" var="news">
                            <div class="v2-news-graphic-item clearfix">
                                <div class="fl v2-news-graphic-img">
                                    <a href="${ctx}/news/detail/${news.id}"><img src="${fileBase}${news.articleImg}" alt=""></a>
                                    <i class="v2-news-graphic-classIcon"><a href="${ctx}/news/detail/${news.id}">${fn:split(news.keywords, "，")[0]}</a></i>
                                </div>
                                <div class="oh">
                                    <h3><a href="${ctx}/news/detail/${news.id}">${news.title}</a></h3>
                                    <p class="v2-news-graphic-info">
                                            ${fn:substring(news.summary,0,100)}
                                        <c:if test="${fn:length(news.summary) > 70}">
                                             ....
                                        </c:if>
                                    </p>
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
<%@ include file="/WEB-INF/include/common_js.jsp" %>
<script>
    $(function(){
        $(".second").addClass("current");
    })
    function page(pageNum){
        $("#pageForm").find("input[name='pageNum']").val(pageNum);
        $("#pageForm").submit();
    }
</script>
</body>
</html>
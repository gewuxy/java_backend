<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>通知 - 列表</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/menu.css">
    <link rel="stylesheet" href="${ctxStatic}/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctxStatic}/css/style.css">
    <script src="${ctxStatic}/js/perfect-scrollbar.jquery.min.js"></script>

    <style>
        html,body { background-color:#F7F9FB;}
    </style>
</head>
<body >
<%@include file="../include/header.jsp" %>
    <div id="wrapper">
        <div class="admin-content bg-gray" >
            <div class="page-width clearfix">
                <div class="subPage-head item-shadow item-radius clearfix">
                    <h3 class="title"><i class="icon icon-header-message"></i>Message</h3>
                </div>
                <div class="subPage-main item-shadow item-radius" >
                    <c:if test="${not empty page.dataList}">
                        <div class="message-list">
                            <ul>
                                <c:forEach items="${page.dataList}" var="m">
                                    <li ><span class="fr">${m.sendTimeStr}</span>
                                        <c:if test="${m.isRead != true}">
                                            <a href="${ctx}/mgr/message/detail?id=${m.id}" class="message" ><span class="icon"></span>${m.title}</a>
                                        </c:if>
                                         <c:if test="${m.isRead == true}">
                                            <a href="${ctx}/mgr/message/detail?id=${m.id}" class="messageEnd" ><span class="icon"></span>${m.title}</a>
                                         </c:if>
                                            </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <%@include file="../include/pageable.jsp"%>
                        <form id="pageForm" name="pageForm" method="post" action="${ctx}/mgr/message/list">
                            <input type="hidden" name="pageNum">
                        </form>
                    </c:if>

                    <c:if test="${empty page.dataList}">
                        <div class="admin-row clearfix">
                            <div class="admin-empty-data">
                                <p><img src="${ctxStatic}/images/admin-empty-data-02.png" alt=""></p>
                                <p> - No Record -</p>
                            </div>
                        </div>
                    </c:if>

                </div>
            </div>
        </div>
        <%@include file="../include/footer.jsp"%>
    </div>


    <script>
        $(function(){


        })
    </script>
</body>
</html>
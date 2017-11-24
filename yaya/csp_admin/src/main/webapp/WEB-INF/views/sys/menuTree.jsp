<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/4/19
  Time: 13:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="accordion" id="menu-${preid}"><c:set var="firstMenu" value="true"/>
    <c:forEach items="${menuList}" var="menu" varStatus="idxStatus"><c:if
            test="${menu.id == (preid!=null ? preid:0)&&menu.hide == false}">
        <div class="accordion-group">
            <div class="accordion-heading">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#menu-${preid}"
                   data-href="#collapse-${menu.id}" href="#collapse-${menu.id}" title="${menu.menuDesc}"><i
                        class="icon-chevron-${not empty firstMenu && firstMenu ? 'down' : 'right'}"></i>&nbsp;${menu.name}
                </a>
            </div>
            <div id="collapse-${menu.id}"
                 class="accordion-body collapse ${not empty firstMenu && firstMenu ? 'in' : ''}">
                <div class="accordion-inner">
                    <ul class="nav nav-list"><c:forEach items="${menuList}" var="menu2"><c:if
                            test="${menu2.preid eq menu.id&&menu2.hide == false}">
                        <li><a data-href=".menu3-${menu2.id}"
                               href="${fn:indexOf(menu2.url, '://') eq -1 ? ctx : ''}${not empty menu2.url ? menu2.url : '/404'}"
                               target="${not empty menu2.target ? menu2.target : 'mainFrame'}"><i
                                class="icon-${not empty menu2.icon ? menu2.icon : 'circle-arrow-right'}"></i>&nbsp;${menu2.name}
                        </a>
                            <ul class="nav nav-list hide" style="margin:0;padding-right:0;">
                                <c:forEach items="${menuList}" var="menu3"><c:if
                                        test="${menu3.preid eq menu2.id&&menu3.hide == false}">
                                    <li class="menu3-${menu2.id} hide"><a
                                            href="${fn:indexOf(menu3.url, '://') eq -1 ? ctx : ''}${not empty menu3.url ? menu3.url : '/404'}"
                                            target="${not empty menu3.target ? menu3.target : 'mainFrame'}"><i
                                            class="icon-${not empty menu3.icon ? menu3.icon : 'circle-arrow-right'}"></i>&nbsp;${menu3.name}
                                    </a></li>
                                </c:if>
                                </c:forEach></ul>
                        </li>
                        <c:set var="firstMenu" value="false"/></c:if></c:forEach></ul>
                </div>
            </div>
        </div>
    </c:if>
    </c:forEach></div>

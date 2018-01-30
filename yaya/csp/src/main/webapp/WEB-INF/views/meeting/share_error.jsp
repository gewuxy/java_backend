<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/11/17
  Time: 10:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html >
<head>
    <%@include file="/WEB-INF/include/page_phone_context.jsp"%>
    <meta charset="UTF-8">
    <title>${course.title}</title>
    <meta id="MetaDescription" name="DESCRIPTION" content="首个医学会议视频直播平台，以后医院都这样开会啦！独立直播间，同步会议现场，随时与参会医生互动，直播会议数据后台详尽记录....还等什么，快来申请使用吧" />
    <meta id="MetaKeywords" name="KEYWORDS" content="医学会议,独立直播间,医生互动" />


    <!-- 高清方案 -->
    <script>!function(e){function t(a){if(i[a])return i[a].exports;var n=i[a]={exports:{},id:a,loaded:!1};return e[a].call(n.exports,n,n.exports,t),n.loaded=!0,n.exports}var i={};return t.m=e,t.c=i,t.p="",t(0)}([function(e,t){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=window;t["default"]=i.flex=function(e,t){var a=e||100,n=t||1,r=i.document,o=navigator.userAgent,d=o.match(/Android[\S\s]+AppleWebkit\/(\d{3})/i),l=o.match(/U3\/((\d+|\.){5,})/i),c=l&&parseInt(l[1].split(".").join(""),10)>=80,p=navigator.appVersion.match(/(iphone|ipad|ipod)/gi),s=i.devicePixelRatio||1;p||d&&d[1]>534||c||(s=1);var u=1/s,m=r.querySelector('meta[name="viewport"]');m||(m=r.createElement("meta"),m.setAttribute("name","viewport"),r.head.appendChild(m)),m.setAttribute("content","width=device-width,user-scalable=no,initial-scale="+u+",maximum-scale="+u+",minimum-scale="+u),r.documentElement.style.fontSize=a/2*s*n+"px"},e.exports=t["default"]}]);  flex(100, 1);</script>

    <style>
        html, body, div, span, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, address, cite, code, del, dfn, em, img, ins, kbd, q, samp, small, strong, sub, sup, var, b, i, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td {
            border: 0 none;
            font-size: inherit;
            color: inherit;
            margin: 0;
            padding: 0;
            vertical-align: baseline;

            max-height:100%
        }

        h1, h2, h3, h4, h5, h6 {
            font-weight: normal;
        }

        em, strong {
            font-style: normal;
        }

        ul, ol, li {
            list-style: none;
        }

        body {
            font-family: "Microsoft YaHei","Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","\5FAE\8F6F\96C5\9ED1",Arial,sans-serif;
            line-height: 1.7;
            color: #333;
            background-color: #fff;
            font-size: 0.24rem;
        }

        a {
            text-decoration: none;
        }
        :focus { outline: 0; }
        /*page style*/
        .error-item { text-align: center;}
        .logo-error { color:#666;  font-size:.28rem; margin-top:28%;}
        .logo-error img{ width:1.04rem; height:1.04rem !important;}
        .main-error { font-size:.36rem; margin-top:10%;}
    </style>

</head>
<body >

<div class="warp" >

    <div class="item">
        <div class="error-item">
            <div class="logo-error">
                <p><img src="${ctxStatic}/images/logo-csp.png" alt=""></p>
                <p><fmt:message key="page.common.appName"/></p>
            </div>
            <div class="main-error">
                <p>${error}</p>
            </div>
        </div>
    </div>


</div>

</body>
</html>

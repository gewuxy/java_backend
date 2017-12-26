<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2017/11/2
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta id="MetaDescription" name="DESCRIPTION" content="首个医学会议视频直播平台，以后医院都这样开会啦！独立直播间，同步会议现场，随时与参会医生互动，直播会议数据后台详尽记录....还等什么，快来申请使用吧" />
    <meta id="MetaKeywords" name="KEYWORDS" content="医学会议,独立直播间,医生互动" />
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1,minimum-scale=1">
    <title>${article.titleCn}</title>
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
            font-weight: bold;
            margin-bottom:10px;
        }

        em, strong {
            font-style: normal;
        }

        ul, ol, li {
            list-style: none;
        }

        body {
            font-family: "Microsoft YaHei","Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","\5FAE\8F6F\96C5\9ED1",Arial,sans-serif;
            line-height: 1.8;
            color: #333;
            background-color: #f2f2f2;
            font-size: 16px;
        }

        a {
            text-decoration: none;
        }
        :focus { outline: 0; }


        p { margin-bottom:10px;}
        h3 { font-size:20px; font-weight: bold;}


    </style>
</head>
<body >
<div class="news-details-main" style="padding:0 10px;">
${article.contentCn}
</div>
</body>
</html>

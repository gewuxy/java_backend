<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2018/1/16
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>发布新闻</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>
    <script type="text/javascript" src="${ctxStatic}/jquery-plugin/jquery-form.js"></script>
    <script type="text/javascript" src="${ctxStatic}/layer/layui.all.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/layer/css/layui.css">
    <script>

    </script>
</head>

<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">发布新闻</a></li>
    </ul>

    <form id="inputForm" method="post" class="form-horizontal" action="${ctx}/website/news/add" enctype="multipart/form-data">
        <div class="control-group">
            <label class="control-label">新闻标题:</label>
            <div class="controls">
                <input type="search" name="title"  id="title" maxlength="100" class="required input-large" value="" >
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">新闻类别:</label>
            <div class="controls">
                <select id="type" name="categoryName" style="width: 200px;">
                    <option value=""/> -- 请选择 --
                    <c:forEach items="${categoryList}" var="c">
                        <option value="${c.id}"/>${c.name}
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">来源:</label>
            <div class="controls">
                <input type="search" name="xfrom" value="" maxlength="50" class="required input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">作者:</label>
            <div class="controls">
                <input type="search" name="author" value="" maxlength="50" class="required input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">摘要:</label>
            <div class="controls">
                <textarea name="summary" rows="3" maxlength="2000" class="input-xxlarge"></textarea>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">关键字:</label>
            <div class="controls">
                <input type="search" name="author" value="" maxlength="50" class="required input-xlarge">
            </div>
        </div>

        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">内容</label>
            <div class="layui-input-block">
                <textarea class="layui-textarea layui-hide" name="content" lay-verify="content" id="content"></textarea>
                <script type="text/javascript">
                    layui.use(['form', 'layedit', 'laydate'], function() {
                        var form = layui.form
                            , layer = layui.layer
                            , layedit = layui.layedit
                            , laydate = layui.laydate;
                        layedit.set({
                            uploadImage: {
                                url: '${ctx}/website/news/upload' //接口url
                                ,type: 'post' //默认post
                            }
                        });
                        //创建一个编辑器
                        var editIndex = layedit.build('content',{
                                height:400
                            }
                        );
                    });
                </script>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">上传图片:</label>
            <div class="controls">
                <input type="file" name="file" id="uploadFile" style="display:none" multiple="multiple"
                       onchange="fileUpload()">
                <input class="btn-dr" type="button" value="上传文件" onclick="selectFile()">
                <input type="hidden" id="hiUpload" value="${saveFileName}" name="uploadFile">
                <input type="hidden" name="imgUrl" id="imgUrl" value="" maxlength="50" class="required input-xlarge">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">图片展示:</label>
            <div class="controls">
                <img  src="" id="imgId" width="200" height="200">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">是否审核:</label>
            <div class="controls">
                <input id="unchecked" type="radio" value="0" name="authed">未审核
                <input id="checked" type="radio" value="1" name="authed">已审核
            </div>
        </div>

        <div class="form-actions">
            <shiro:hasPermission name="website:news:add">
                <input id="btnSubmit" class="btn btn-primary" type="submit"
                       value="保 存"/>
            </shiro:hasPermission>&nbsp;
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </div>
    </form>


</body>
</html>

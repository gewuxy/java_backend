<%--
  Created by IntelliJ IDEA.
  User: Liuchangling
  Date: 2018/1/26
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <script src="${ctxStatic}/js/util.js"></script>
    <script src="${ctxStatic}/js/ajaxfileupload.js"></script>
    <title>${empty goods.id ? '添加':'修改'}商品</title>
    <script>
        $(document).ready(function() {
            $("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    layer.msg('正在提交，请稍等...', {
                        icon: 16,
                        shade: 0.01
                    });
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            if("${error}"){
                layer.msg("${error}");
            }

            $("#pictureFile").change(function(){
                pictureFile($("#pictureFile")[0]);
            });

            function pictureFile(f) {
                var fileName = $(f).val().toLowerCase();
                if (!fileName.endWith(".jpg") && !fileName.endWith(".png") && !fileName.endWith(".jpeg")){
                    layer.msg("请选择正确的图片格式");
                    return false;
                }

                var index = layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
                $.ajaxFileUpload({
                    url: "${ctx}/yaya/shop/upload/picture", //用于文件上传的服务器端请求地址
                    secureuri: false, //是否需要安全协议，一般设置为false
                    fileElementId: f.id, //文件上传域的ID
                    dataType: 'json', //返回值类型 一般设置为json
                    success: function (data)  //服务器成功响应处理函数
                    {
                        layer.close(index);
                        if (data.code == 0){
                            //回调函数传回传完之后的URL地址
                            fleshPage(data.data);
                        } else {
                            layer.msg(data.err);
                        }
                    },
                    error:function(data, status, e){
                        alert(e);
                        layer.close(index);
                    }
                });
            }

            function fleshPage(data) {
                $("#pictureView").attr("src", data.absolutePath);
                $("#picture").val(data.relativePath);
                $("#pictureFile").replaceWith('<input type="file" id="pictureFile" name="file">');
                $("#pictureFile").change(function(){
                    pictureFile($("#pictureFile")[0]);
                });

            }
        })
    </script>
</head>

<body>
    <ul class="nav nav-tabs">
        <li><a href="${ctx}/yaya/shop/list">商品列表</a></li>
        <li class="active"><a href="${ctx}/yaya/shop/edit">添加商品</a></li>
    </ul>

    <form id="inputForm" action="${ctx}/yaya/shop/save" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${goods.id}"/>
        <div class="control-group">
            <label class="control-label">商品名:</label>
            <div class="controls">
                <input type="search" name="name" value="${goods.name}" maxlength="50" class="required input-xlarge"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">作者:</label>
            <div class="controls">
                <input type="search" name="author" value="${goods.author}" maxlength="50" class="required input-xlarge"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">价格:</label>
            <div class="controls">
                <input type="search" name="price" value="${goods.price}" maxlength="50" class="required input-xlarge"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">供货商:</label>
            <div class="controls">
                <input type="search" name="provider" value="${goods.provider}" maxlength="50" class="required input-xlarge"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">库存:</label>
            <div class="controls">
                <input type="search" name="stock" value="${goods.stock}" maxlength="50" class="required input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">商品类型:</label>
            <div class="controls">
                <select id="gtype" name="gtype" style="width: 100px;">
                    <option value=""/>选择类型
                    <option value="0"/>礼品
                    <option value="1"/>电子书
                </select>
                <script>
                    document.getElementById("gtype").value="${goods.gtype}";
                </script>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">上下架:</label>
            <div class="controls">
                <select id="status" name="status" style="width: 100px;">
                    <option value=""/>是否上架
                    <option value="1"/>上架
                    <option value="0"/>下架
                </select>
                <script>
                    document.getElementById("status").value="${goods.status}";
                </script>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">描述:</label>
            <div class="controls">
                <textarea name="descrip" rows="3" maxlength="200" class="required input-xxlarge">${goods.descrip}</textarea>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">商品返还象数:</label>
            <div class="controls">
                <input type="search" name="refund" value="${goods.refund}" maxlength="8" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">限购数:</label>
            <div class="controls">
                <input type="search" name="buyLimit" value="${goods.buyLimit}" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">商品图片:</label>
            <div class="controls">
                <input type="hidden" name="picture" id="picture" value="${goods.picture}">
                <img width="120" height="120" id="pictureView"
                        <c:choose>
                            <c:when test="${not empty goods.picture}">
                                src="${appFileBase}${goods.picture}"
                            </c:when>
                            <c:otherwise>
                                src="${appFileBase}${goods.picture}"
                            </c:otherwise>
                        </c:choose>
                />
                <input type="file" name="file" id="pictureFile">
                <span class="help-inline">图片格式为jpg、png、jpeg</span>
            </div>
        </div>
        <div class="form-actions">
            <shiro:hasPermission name="yaya:shop:add">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
            </shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </div>
    </form>
</body>
</html>

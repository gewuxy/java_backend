<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: jianliang
  Date: 2017/11/23
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改推荐会议</title>
    <%@include file="/WEB-INF/include/page_context.jsp" %>

</head>
<script type="text/javascript" src="${ctxStatic}/laydate/laydate.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-plugin/jquery-form.js"></script>
<body>
<script type="text/javascript">
    function selectFile() {
        $(document).ready(function() {
            initFormValidate();
        });

        $("#uploadFile").trigger("click");
    }

    function fileUpload() {
        var option = {
            url: "${ctx}/yaya/recommendMeet/upload",
            type: 'POST',
            dataType: 'json',
            clearForm: false,
            success: function (data) {
                if (data.code == 0){
                    layer.msg("上传成功");
                    $("#imgUrl").val(data.data.imgURL);
                    $("#imgId").attr("src", data.data.imgPath);
                }else {
                    layer.msg(data.err);
                }
            }
        };
        $("#inputForm").ajaxSubmit(option);
        return true;
    }
</script>
<ul class="nav nav-tabs">
    <li class="active"><a href="#">修改推荐会议</a></li>
</ul>
<form id="inputForm" method="post" class="form-horizontal" action="${ctx}/yaya/recommendMeet/update" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${recommend.id}" />
    <input type="hidden" name="lecturerId" value="${lecturer.id}" />
    <div class="control-group">
        <label class="control-label">会议名称:</label>
        <div class="controls">
            <input type="hidden" name="resourceId" value="${recommend.resourceId}">
            <input readonly type="search" name="meetName" id="meetName" value="${meet.meetName}" maxlength="50" class="required input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">主讲者姓名:</label>
        <div class="controls">
            <input  type="search" name="name" id="lecturer" value="${lecturer.name}" maxlength="50" class="required input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">主讲者职位:</label>
        <div class="controls">
            <input  type="search" name="title" id="lecturerTile" value="${lecturer.title}" maxlength="50" class="required input-xlarge">
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">上传头像:</label>
        <div class="controls">
            <input type="file" name="file" id="uploadFile" style="display:none" multiple="multiple"
                   onchange="fileUpload()">
            <input class="btn-dr" type="button" value="上传头像" onclick="selectFile()">
            <input type="hidden" id="hiUpload" value="${saveFileName}" name="uploadFile">
            <input type="hidden" id="imgUrl" value="" name="headimg">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">头像展示:</label>
        <div class="controls">
            <img src="${imgPath}" id="imgId" width="200" height="200">
        </div>
    </div>


    <div class="control-group">
        <label class="control-label">主讲者所属医院:</label>
        <div class="controls">
            <input  type="search" name="hospital" id="lecturerHos" value="${lecturer.hospital}" maxlength="50" class="required input-xlarge">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">主讲者所属科室:</label>
        <div class="controls">
            <select id="lecturerDepart" name="depart" style="width: 200px; background-color: #EEEEEE;" disabled="disabled">
                <option value="">科室</option>
                <c:forEach items="${departments}" var="d">
                    <option value="${d}"}>${d}</option>
                </c:forEach>
                <script>
                    document.getElementById("lecturerDepart").value="${lecturer.depart}";
                </script>
            </select>&nbsp;&nbsp;
        </div>
    </div>
    </div>
    </div>
    <div class="control-group">
        <label class="control-label">会议状态:</label>
        <div class="controls">
            <select id="state" name="state" style="width: 200px; background-color: #EEEEEE;" disabled="disabled">
                <option value="">会议状态</option>
                <option value="0">会议草稿</option>
                <option value="1">会议未开始</option>
                <option value="2">会议进行中</option>
                <option value="3">会议已结束</option>
                <option value="4">会议已撤销/已删除</option>
                <option value="5">会议未发布</option>
                <option value="6">会议已关闭</option>
            </select>
            <script>
                document.getElementById("state").value="${meet.state}";
            </script>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">推荐类型:</label>
        <div class="controls">
            <select id="recType" name="recType" style="width: 150px">
                <option value="">推荐类型</option>
                <option value="1">会议文件夹</option>
                <option value="2">  会议  </option>
                <option value="3">单位号   </option>
            </select>
            <script>
                document.getElementById("recType").value="${recommend.recType}";
            </script>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否推荐:</label>
        <div class="controls">
            <select id="recFlag" name="recFlag" style="width: 150px">
                <option value="">是否推荐</option>
                <option value="false">不推荐</option>
                <option value="true">推荐</option>
            </select>
            <script>
                document.getElementById("recFlag").value="${recommend.recFlag}";
            </script>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">排序序号:</label>
        <div class="controls">
            <input type="search" name="sort" id="sort" value="${recommend.sort}" maxlength="50" class="required input-xlarge" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
            <span class="help-inline"><font color="#a9a9a9">只允许输入1~10之间的数字*</font> </span>
            <script type="text/jscript" language="javascript">
                //                onafterpaste = "this.value=this.value.replace(/\D/g,'')"
                $('#sort').keyup(function () {
                    //                    alert(1);
                    var inputdata = $(this).val().replace(/\D/g, '');
                    console.info(inputdata);
                    if (inputdata != '' && inputdata < 1) {
                        inputdata = 1;
                    }
                    if (inputdata != '' && inputdata > 10) {
                        inputdata = 10;
                    }
                    $(this).val(inputdata);
                });
            </script>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">推荐日期:</label>
        <div class="controls">
            <input readonly type="text" id="recDate" value="${format}" class="layui-input" name="recDate" placeholder="yyyy-MM-dd HH:mm:ss">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否固定推荐:</label>
        <div class="controls">
            <select id="fixed" name="fixed"  style="width: 200px;">
                <option value="">是否固定推荐</option>
                <option value="0">不固定</option>
                <option value="1">固定</option>
            </select>
            <script>
                document.getElementById("fixed").value="${recommend.fixed}";
            </script>
        </div>
    </div>
    <div class="form-actions">
    <shiro:hasPermission name = "yaya:recommendMeet:edit">
        <input id="btnSubmit" class="btn btn-primary" type="submit"
               value="修 改"/>
    </shiro:hasPermission>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function() {
        initFormValidate();
    });
    //时间选择器
   /* laydate.render({
        elem: '#recDate'
        ,type: 'datetime'
    });*/
</script>
</body>
</html>



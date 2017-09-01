<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/27
  Time: 16:46
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="buttonArea clearfix">
    <div class="formrow">
        <div class="fr clearfix">
            <input type="button" id="draftBtn" class="formButton formButton-max" value="保存草稿">
        </div>
        <div class="fl clearfix">
            <input type="button" id="prevBtn" onclick="window.location.href='${ctx}/func/meet/edit?id=${meetId}'" class="formButton formButton-max" value="上一步">&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" id="nextBtn" class="formButton formButton-max" value="下一步">
        </div>
    </div>
</div>

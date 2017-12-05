<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function checkSearch(){
        var ypname = $.trim($("#ypname").val());
        if(ypname == ''){
            layer.tips('请输入搜索条件', '#ypname', {
                tips: [1, '#3595CC'],
                time: 3000
            });
            return false;
        }
        return true;
    }
    function yPsearchSubmit(){
        if(checkSearch()){
            $("#yPsearchForm").submit();
        }
    }
</script>
<div class="v2-top-search clearfix">
    <form action="${ctx}/search/" method="post" id="yPsearchForm" onsubmit="return yPsearchSubmit()" target="_blank" name="yPsearchForm">
        <div class="v2-search-form v2-search-form-responsive clearfix">
            <input type="text"  placeholder="药品通用名/商品名/批准文号" name="ypname" id="ypname"  class="form-text" >
            <button type="submit" class="form-btn" ><span></span></button>
        </div>
    </form>
</div>

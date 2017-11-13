/**
 * 初始化form表单校验
 */
function initFormValidate(){
    $("#inputForm").validate({
        submitHandler: function(form){
            layer.msg('正在提交，请稍等...',{
                icon: 16,
                shade: 0.01
            });
            form.submit();
        }
    });
}

/**
 * 初始化翻页地址
 * @param action
 */
function initPage(action){
    $("#pageForm").attr("action", ctx + action);
}
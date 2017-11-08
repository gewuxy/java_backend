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
        },
        errorContainer: "#messageBox",
        errorPlacement: function(error, element) {
            $("#messageBox").text("输入有误，请先更正。");
            if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        }
    });
}
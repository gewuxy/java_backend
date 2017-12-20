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

/**
 * 初始化日期控件
 * @param id
 */
function initLaydate(id){
    laydate.render({
        elem: '#' + id
    });
}

function initDateRangePicker(id){
    $('#updateTimes').dateRangePicker({
        singleMonth: true,
        showShortcuts: false,
        showTopbar: false,
        format: 'YYYY-MM-DD 23:59:59',
        autoClose: false,
        singleDate:true,
        time: {
            enabled: true
        }
    }).bind('datepicker-change',function(event,obj){
        $(this).find("input").val(obj.value);
        $("#" + id).val(obj.value);
    });;
}

function dateToString(now){
    var year = now.getFullYear();
    var month =(now.getMonth() + 1).toString();
    var day = (now.getDate()).toString();
    var hour = (now.getHours()).toString();
    var minute = (now.getMinutes()).toString();
    var second = (now.getSeconds()).toString();
    if (month.length == 1) {
        month = "0" + month;
    }
    if (day.length == 1) {
        day = "0" + day;
    }
    if (hour.length == 1) {
        hour = "0" + hour;
    }
    if (minute.length == 1) {
        minute = "0" + minute;
    }
    if (second.length == 1) {
        second = "0" + second;
    }
    var dateTime = year + "-" + month + "-" + day +" "+ hour +":"+minute+":"+second;
    return dateTime;
}

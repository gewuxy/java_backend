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
 * 初始化日期控件
 * @param id
 */
function initLaydate(id){
    laydate.render({
        elem: '#' + id
    });
}

function isEmpty(value) {
    if (value != null && value !== undefined && value!== ''){
        return false;
    }
    return true;
}

function dateToStrings(str){
    var span = Date.parse(str + ' GMT +8');
    return datetimeFormat(span);
}

function datetimeFormat(longTypeDate){
    var datetimeType = "";
    var date = new Date();
    date.setTime(longTypeDate);
    datetimeType+= date.getFullYear();  //年
    datetimeType+= "-" + getMonth(date); //月
    datetimeType += "-" + getDay(date);  //日
    datetimeType+= "  " + getHours(date);  //时
    datetimeType+= ":" + getMinutes(date);   //分
    datetimeType+= ":" + getSeconds(date);   //分
    return datetimeType;
}
//返回 01-12 的月份值
function getMonth(date){
    var month = "";
    month = date.getMonth() + 1; //getMonth()得到的月份是0-11
    if(month<10){
        month = "0" + month;
    }
    return month;
}
//返回01-30的日期
function getDay(date){
    var day = "";
    day = date.getDate();
    if(day<10){
        day = "0" + day;
    }
    return day;
}
//返回小时
function getHours(date){
    var hours = "";
    hours = date.getHours();
    if(hours<10){
        hours = "0" + hours;
    }
    return hours;
}
//返回分
function getMinutes(date){
    var minute = "";
    minute = date.getMinutes();
    if(minute<10){
        minute = "0" + minute;
    }
    return minute;
}
//返回秒
function getSeconds(date){
    var second = "";
    second = date.getSeconds();
    if(second<10){
        second = "0" + second;
    }
    return second;
}


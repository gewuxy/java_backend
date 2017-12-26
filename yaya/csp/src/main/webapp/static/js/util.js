Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

var emailMap = {
    'qq.com':'http://mail.qq.com',
    'gmail.com': 'http://mail.google.com',
    'sina.com': 'http://mail.sina.com.cn',
    '163.com': 'http://mail.163.com',
    '126.com': 'http://mail.126.com',
    'yeah.net': 'http://www.yeah.net/',
    'sohu.com': 'http://mail.sohu.com/',
    'tom.com': 'http://mail.tom.com/',
    'sogou.com': 'http://mail.sogou.com/',
    '139.com': 'http://mail.10086.cn/',
    'hotmail.com': 'http://www.hotmail.com',
    'live.com': 'http://login.live.com/',
    'live.cn': 'http://login.live.cn/',
    'live.com.cn': 'http://login.live.com.cn',
    '189.com': 'http://webmail16.189.cn/webmail/',
    'yahoo.com.cn': 'http://mail.cn.yahoo.com/',
    'yahoo.cn': 'http://mail.cn.yahoo.com/',
    'eyou.com': 'http://www.eyou.com/',
    '21cn.com': 'http://mail.21cn.com/',
    '188.com': 'http://www.188.com/',
    'foxmail.com': 'http://www.foxmail.com',
    'medcn.cn':'http://mail.medcn.cn/'
};

/**
 * 判断非空
 *
 * @param val
 * @returns {Boolean}
 */
function isEmpty(val) {
    val = $.trim(val);
    if (val == null){
        return true;
    }
    if (val == undefined || val == 'undefined'){
        return true;
    }
    if (val == ""){
        return true;
    }
    if (val.length == 0){
        return true;
    }
    if (!/[^(^\s*)|(\s*$)]/.test(val)){
        return true;
    }
    return false;
}


function isNotEmpty(val) {
    return !isEmpty(val);
};

/**
 * 检测手机号码合法性
 * @param mobile
 * @returns {boolean}
 */
function isMobile(mobile){
    var reg = /^1[3|5|7|8][0-9]{9}$/g;
    return reg.test(mobile);
}

/**
 * 检测邮箱合法性
 * @param email
 * @returns {boolean}
 */
function isEmail(email){
    var reg = /^[a-zA-Z0-9]+([._\\-]*[a-zA-Z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-zA-Z0-9]+$/g;
    return reg.test(email);
}

/**
 * 是否是合法的验证码
 * @param verifyCode
 * @returns {boolean}
 */
function isVerifyCode(verifyCode){
    var reg = /^[0-9]{6}$/g;
    return reg.test(verifyCode);
}

/**
 * 根据邮箱获取该邮箱的网站地址
 * @param email
 * @returns {string}
 */
function gainEmailURL(email){
    var emailsuffix = email.split('@')[1];
    var emailURL = "error";
    if(emailMap[emailsuffix] != null && emailMap[emailsuffix] != ''){
        emailURL = emailMap[emailsuffix];
    }
    return emailURL;
}


function checkFrom(f, cur){
    if(cur == undefined){
        return doCheckForm(f);
    }else{
        checkItem(true, cur);
    }
}

function doCheckForm(f){
    var submitAble = true;
    var $form = $(f);
    $form.find("input").each(function(){
        submitAble = checkItem(submitAble, $(this));
        if (!submitAble){
            return false;
        }
    });
    return submitAble;
}

function checkItem(submitAble, cur){
    var datatype = cur.attr("data-type");
    var val = cur.val();
    var id = cur.attr("id");
    if (id == undefined)
        return true;
    if(cur.attr("type")=="file"){
        return true;
    }
    console.log("cur.attr(escapeCheck)="+cur.attr("escapeCheck"));
    if(cur.attr("escapeCheck") != undefined){
        return true;
    }
    if(datatype == undefined || datatype == ''){
        submitAble = !isEmpty(val);
    }else{
        switch (datatype){
            case "mobile":
                submitAble = isMobile(val);
                break;
            case "email":
                submitAble = isEmail(val);
                $("#error-"+id).find("span").text("请输入正确的邮箱地址");
                break;
            case "verifyCode":
                submitAble = isVerifyCode(val);
                break;
            case "password":
                submitAble = $.trim(val).length >= 6 && $.trim(val).length<=32;
                if (!submitAble){
                    $("#error-"+id).find("span").text("输入密码长度不在6-32位之间");
                }else{
                    var reg = /^[a-zA-Z0-9]+$/g;
                    submitAble = reg.test(val);
                    if (!submitAble){
                        $("#error-"+id).find("span").text("请检查密码是否包含特殊字符");
                    }
                }
                break;
            case "repassword":
                submitAble = $.trim(val).length >= 6 && $.trim(val).length<=32&&$("#password").val()==val;
                break;
            case "realname":
                submitAble = isChinseName(val);
                break;
            default:
                break;
        }
    }
    console.log("submitAble = "+submitAble+" - "+id);
    if (!submitAble){
        $("#error-"+id).removeClass("none");
        $("#success-"+id).addClass("none");
        return false;
    }else{
        $("#success-"+id).removeClass("none");
        $("#error-"+id).addClass("none");
        return true;
    }
}

function isPassword(val){
    var reg = /^[a-zA-Z0-9]{6,24}$/;
    return reg.test(val);
}

function isChinesePassword(val) {
    var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
    return reg.test(val);
}

function isChinseName(val){
    var reg = /^[\u4e00-\u9fa5]+$/i;
    return reg.test(val);
}

function simpleCheckForm(form){
    var $form = $(form);
    var submitAble = false;
    $form.find("input:not([type='file'])").each(function(){
        var datatype = $(this).attr("data-type");
        var showmsg = "";
        var val = $(this).val();
        var id = $(this).attr("id");
        if (id == undefined)
            return true;
        if (datatype == undefined||datatype == ''){
            submitAble = !isEmpty(val);
        }else{
            switch (datatype){
                case "mobile":
                    submitAble = isMobile(val);
                    break;
                case "email":
                    submitAble = isEmail(val);
                    break;
                case "verifyCode":
                    submitAble = isVerifyCode(val);
                    break;
                case "password":
                    submitAble = $.trim(val).length >= 6 && $.trim(val).length<=32;
                    break;
                case "repassword":
                    submitAble = $.trim(val).length >= 6 && $.trim(val).length<=32&&$("#password").val()==$(this).val();
                    break;
                case "realname":
                    submitAble = isChinseName(val);
                    break;
                default:
                    break;
            }
        }
        if (!submitAble){
            $(this).siblings(".formIconTips").addClass("none");
            $(this).parent().siblings("p:last").removeClass("none");
            return false;
        }else{
            $(this).siblings(".formIconTips").removeClass("none");
            $(this).parent().siblings("p:last").addClass("none");
        }
    });
    return submitAble;
}

/**
 * 异步文件上传
 * @param fileId
 * @param fileType
 * @param callback
 */
function upload(fileId,fileType,limitSize,callback){
    var index = layer.load(1, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
    $.ajaxFileUpload({
        url: fileUploadUrl+"?fileType="+fileType+"&limitSize="+limitSize, //用于文件上传的服务器端请求地址
        secureuri: false, //是否需要安全协议，一般设置为false
        fileElementId: fileId, //文件上传域的ID
        dataType: 'json', //返回值类型 一般设置为json
        success: function (data)  //服务器成功响应处理函数
        {
            layer.close(index);
            //回调函数传回传完之后的URL地址
            if(data.code == 0){
                callback(data);
            }else{
                layer.msg(data.err);
            }
        },
        error:function(data, status, e){
            alert(e);
            layer.close(index);
        }
    });
}


function fileSize(target) {
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
    var fileSize = 0;
    if (isIE && !target.files) {
        var filePath = target.value;
        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
        var file = fileSystem.GetFile (filePath);
        fileSize = file.Size;
    } else {
        fileSize = target.files[0].size;
    }
    return fileSize;
}

String.prototype.endWith = function(str){
    if(str==null || str=="" || this.length == 0 ||str.length > this.length){
        return false;
    }
    if(this.substring(this.length - str.length) == str){
        return true;
    }else{
        return false;
    }
    return true;
};

String.prototype.startWith = function(str){
    if(str == null || str== "" || this.length== 0 || str.length > this.length){
        return false;
    }
    if(this.substr(0,str.length) == str){
        return true;
    }else{
        return false;
    }
    return true;
};


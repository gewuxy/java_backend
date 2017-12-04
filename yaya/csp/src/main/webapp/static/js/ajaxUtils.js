/**
 * 依赖于jquery
 * 必须在此文件之前引入jquery
 */

const postMethod = "POST";
const getMethod = "GET";

function ajaxPost(url, params, async, onSuccess, onError){
    doAjax(url, params, postMethod, async, onSuccess, onError);
}

function ajaxGet(url, params, async, onSuccess, onError){
    doAjax(url, params, getMethod, async, onSuccess, onError);
}

function ajaxPost(url, params, onSuccess, onError){
    doAjax(url, params, postMethod, true, onSuccess, onError);
}

function ajaxGet(url, params, onSuccess, onError){
    doAjax(url, params, getMethod, true, onSuccess, onError);
}


function doAjax(url, params, method, async, onSuccess, onError){
    if (method == undefined || (method != postMethod && method != getMethod)){
        method = getMethod;
    }

    if (async == undefined){
        async = true;
    }

    $.ajax({
        url : url,
        data : params || {},
        dataType : 'json',
        type : method,
        async : async,
        success : function(data){
            if (data.code == "100"){
                layer.msg(data.err || '登录已超时，请重新登录', {'time':1000}, function () {
                    window.location.href = "/";
                });
            } else {

                if (typeof onSuccess == "function") {
                    onSuccess(data);
                }
            }


        },
        error : function (a, n, e) {
            if (typeof onError == "function"){
                onError(a, n, e);
            }
        }
    });
}
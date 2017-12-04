/**
 * 依赖于jquery
 * 必须在此文件之前引入jquery
 */

const postMethod = "POST";
const getMethod = "GET";
/**
 * 异步POST
 * @param url
 * @param params
 * @param onSuccess
 * @param onError
 */
function ajaxPost(url, params, onSuccess, onError){
    doAjax(url, params, postMethod, true, onSuccess, onError);
}
/**
 * 异步GET
 * @param url
 * @param params
 * @param onSuccess
 * @param onError
 */
function ajaxGet(url, params, onSuccess, onError){
    doAjax(url, params, getMethod, true, onSuccess, onError);
}
/**
 * 同步POST
 * @param url
 * @param params
 * @param onSuccess
 * @param onError
 */
function ajaxSyncPost(url, params, onSuccess, onError){
    doAjax(url, params, postMethod, false, onSuccess, onError);
}
/**
 * 同步GET
 * @param url
 * @param params
 * @param onSuccess
 * @param onError
 */
function ajaxSyncGet(url, params, onSuccess, onError){
    doAjax(url, params, getMethod, false, onSuccess, onError);
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
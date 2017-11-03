/**
 * 依赖jquery layer
 */

/**
 *
 * @param href
 */
function layerConfirm(msg, tar){
    top.layer.confirm(msg, function(){
        var href = $(tar).attr("data-href");
        window.location.href = href;
        top.layer.closeAll('dialog');
    });
}
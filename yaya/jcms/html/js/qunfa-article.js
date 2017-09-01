function previewImage(file,imgNum) {
    var MAXWIDTH  = 200; 
    var MAXHEIGHT = 200;
    var div = document.getElementById('preview'+imgNum);
    if (file.files && file.files[0]) {
        div.innerHTML ='<img id=imghead'+imgNum+'>';
        var img = document.getElementById('imghead'+imgNum+'');
        img.onload = function(){
            var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
        }
        var reader = new FileReader();
        reader.onload = function(evt){img.src = evt.target.result;}
        reader.readAsDataURL(file.files[0]);
    }else {
        var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
        file.select();
        var src = document.selection.createRange().text;
        div.innerHTML = '<img id=imghead'+imgNum+'>';
        var img = document.getElementById('imghead2');
        img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
        status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
        div.innerHTML = "<div id=divhead"+imgNum+" style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
    }
}

function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight ) {
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;

        if( rateWidth > rateHeight ) {
            param.width =  maxWidth;
            param.height = Math.round(height / rateWidth);
        }else {
            param.width = Math.round(width / rateHeight);
            param.height = maxHeight;
        }
    }
    param.left = Math.round((maxWidth - param.width) / 2);
    param.top = Math.round((maxHeight - param.height) / 2);
    return param;
}

function showTitle(id){
    var title = $('#title'+id).val();
    if ($.trim(title).length>10) {
        title = $.trim(title).substring(0,10)+'...';
        $('#t'+id).text(title);
    } else {
        if ($.trim(title).length==0) {
            $('#t'+id).text('标题');
        } else {
            $('#t'+id).text($.trim(title));
        }
    }
}

// 添加
function showAdd(id){
    var img_id = parseInt(id);
    if (img_id-1!=0) {
        var title = '';
        var content = '';
        var fileName = '';
        if (img_id==6) {
            title = $('#title8').val();
            content = $('#content8').val();
            fileName = $('#imghead8').attr('src');
            if ($.trim(title).length==0||$.trim(content).length==0||fileName=='') {
                alert("请完善上一条消息!");
            } else {
                $('#add-'+(img_id-1)).show().siblings('.file-upload-1').hide();
                $('#sand-box-'+img_id).show();
            }
        } else if (img_id==5) {
            title = $('#title6').val();
            content = $('#content6').val();
            fileName = $('#imghead6').attr('src');
            if ($.trim(title).length==0||$.trim(content).length==0||fileName=='') {
                alert("请完善上一条消息!");
            } else {
                $('#add-'+(img_id-1)).show().siblings('.file-upload-1').hide();
                $('#sand-box-'+img_id).show();
            }
        } else if (img_id==4) {
            title = $('#title5').val();
            content = $('#content5').val();
            fileName = $('#imghead5').attr('src');
            if ($.trim(title).length==0||$.trim(content).length==0||fileName=='') {
                alert("请完善上一条消息!");
            } else {
                $('#add-'+(img_id-1)).show().siblings('.file-upload-1').hide();
                $('#sand-box-'+img_id).show();
            }
        } else if (img_id==3) {
            title = $('#title4').val();
            content = $('#content4').val();
            fileName = $('#imghead4').attr('src');
            if ($.trim(title).length==0||$.trim(content).length==0||fileName=='') {
                alert("请完善上一条消息!");
            } else {
                $('#add-'+(img_id-1)).show().siblings('.file-upload-1').hide();
                $('#sand-box-'+img_id).show();
            }
        } else if (img_id==2) {
            title = $('#title3').val();
            content = $('#content3').val();
            fileName = $('#imghead3').attr('src');
            if ($.trim(title).length==0||$.trim(content).length==0||fileName=='') {
                alert("请完善上一条消息!");
            } else {
                $('#add-'+(img_id-1)).show().siblings('.file-upload-1').hide();
                $('#sand-box-'+img_id).show();
            }
        }
    } else {
        if (img_id==1) {
            title = $('#title2').val();
            content = $('#content2').val();
            fileName = $('#imghead2').attr('src');
            if ($.trim(title).length==0||$.trim(content).length==0||fileName=='') {
                alert("请完善上一条消息!");
            } else {
                $('#add-'+(img_id)).hide();
                $('#sand-box-'+img_id).show();
            }
        }
    }
}

// 删除
function deleteOper(id) {
    $('#sand-box-'+id).hide();
    $('#title'+id).val('');
    $('#file-'+id).val('');
    $('#content'+id).val('');
    $('#link'+id).val('');
    $('#imghead'+id).attr('src','');
}

function showRight(id){
    var array = ["1","2","3","4","5","6","7","8"];
    jQuery.each(array,function(index,value){
        if (array[index]==id) {     //如果是当前匹配的id，则显示对应的标签
            $('.text-input-box-'+id).show().siblings('.text-input-box').hide();
            $('#file-'+id).show().siblings('.file-upload').hide();
            $('#content'+id).show().siblings('.zwbj-box').hide();
            $('#link'+id).show().siblings('.input-link').hide();
            // $("#delete_picture"+id).css("display","block");
            // $("#content_msg"+id).css("display","block");
            if (id=='8') {  
                var ua = navigator.userAgent.toLowerCase();
                if (ua.indexOf("msie 8.0")!=-1 ||ua.indexOf("msie 7.0")!=-1){
                    $("#p"+id).show();
                    $("#pic"+id).show();
                }
                $("#righter-bd").css('margin-top','152px');
                // $("#arrow_in").css("top","200px");
                // $("#arrow_out").css("top","200px");
            } else if (id=="6") {
                var ua = navigator.userAgent.toLowerCase();
                if (ua.indexOf("msie 8.0")!=-1 ||ua.indexOf("msie 7.0")!=-1){
                    $("#p"+id).show();
                    $("#pic"+id).show();
                }
                $("#righter-bd").css('margin-top','280px');
            } else if (id=="5") {
                var ua = navigator.userAgent.toLowerCase();
                if (ua.indexOf("msie 8.0")!=-1 ||ua.indexOf("msie 7.0")!=-1){
                    $("#p"+id).show();
                    $("#pic"+id).show();
                }
                $("#righter-bd").css('margin-top','390px');
            } else if (id=="4") {
                var ua = navigator.userAgent.toLowerCase();
                if (ua.indexOf("msie 8.0")!=-1 ||ua.indexOf("msie 7.0")!=-1){
                    $("#p"+id).show();
                    $("#pic"+id).show();
                }
                $("#righter-bd").css('margin-top','500px');
            } else if (id=="3") {
                var ua = navigator.userAgent.toLowerCase();
                if (ua.indexOf("msie 8.0")!=-1 ||ua.indexOf("msie 7.0")!=-1){
                    $("#p"+id).show();
                    $("#pic"+id).show();
                }
                $("#righter-bd").css('margin-top','600px');
            } else if (id=="2") {
                var ua = navigator.userAgent.toLowerCase();
                if (ua.indexOf("msie 8.0")!=-1 ||ua.indexOf("msie 7.0")!=-1){
                    $("#p"+id).show();
                    $("#pic"+id).show();
                }
                $("#righter-bd").css('margin-top','710px');
            } else if (id=="1") {
                var ua = navigator.userAgent.toLowerCase();
                if (ua.indexOf("msie 8.0")!=-1 ||ua.indexOf("msie 7.0")!=-1){
                    $("#p"+id).show();
                    $("#pic"+id).show();
                }
                $("#righter-bd").css('margin-top','810px');
            } else if (id=="7") {
                var ua = navigator.userAgent.toLowerCase();
                if (ua.indexOf("msie 8.0")!=-1 ||ua.indexOf("msie 7.0")!=-1){
                    $("#p"+id).show();
                    $("#pic"+id).show();
                }
                $("#righter-bd").css('margin-top','0');
            }
        } else {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.indexOf("msie 8.0")!=-1 ||ua.indexOf("msie 7.0")!=-1){
                $("#p"+array[index]).css("display","none");
                $("#pic"+array[index]).css("display","none");
            }
            $('.text-input-box-'+id).show().siblings('.text-input-box').hide();
            $('#file-'+id).show().siblings('.file-upload').hide();
            $('#content'+id).show();
            $('#link'+id).show().siblings('.input-link').hide();
        }
    });
}

$(function(){

// 鼠标经过
$('.sand-box').on({
    mouseenter: function() {
        $(this).find('.s-mask-box').show();
    },
    mouseleave: function() {
        $(this).find('.s-mask-box').hide();
    }
});

// 最多只能输入40个字符
$('.text-input-box').each(function(){
    var $this = $(this);
    var Len = $this.find('input').val().length;
    $this.find('.info-num i').html(40 - Len);
    $this.find('input').keyup(function() {
        var text = $(this).val();
        var Len = text.length;
        $this.find('.info-num i').html(40 - Len);
        if(Len===40) {
            alert('不能再输入了！')
        }
    });
});

// 群发提交信息
$('#qunfa-btn').on('click',function(){
    result = false;
    var files = '';
    var contents = '';
    var titles = '';
    var t_8 = $('#title8').val(),
        src_8 = $('#imghead8').attr('src'),
        content_8 = $('#content8').val(),
        t_7 = $('#title7').val(),
        src_7 = $('#imghead7').attr('src'),
        content_7 = $('#content7').val();
    if(!$.trim(t_7)) {
        $('.title-info').show().html('请输入标题');
        $('#in-title-7').focus();
    }else if(!$.trim(src_7)) {
        $('.cover-info').show().html('请上传图片');
    }else if(!$.trim(content_7)) {
        $('.cont-info').show().html('请填写正文内容');
        $('#content-7').focus();
    }else {
        if ($.trim(t_8).length==0||$.trim(content_8).length==0||src_8=='') {
            alert("多图文至少需要完善完两条消息!");
        } else {
            // if (jQuery.trim(title1).length!=0||jQuery.trim(content1).length!=0||fileName_1!="") {
            //  alert("请完善最后一条消息!");
            // }else {
            var array = ['7','8','1','2','3','4','5','6'];
            $.each(array,function(index,value){
                if ($('#imghead'+array[index]).val()!='') {
                    files += $('#imghead'+array[index]).val()+',';
                }
                if ($('#title'+array[index]).val()!='') {
                    titles += $('#title'+array[index]).val()+',';
                }
                if ($('#content'+array[index]).val()!='') {
                    if ($.trim($('#link'+array[index]).val())!="") {
                        contents += $('#content'+array[index]).val()+' 原文链接：'+$('#link'+array[index]).val()+',';
                    } else {
                        contents += $('#content'+array[index]).val()+",";
                    }
                }
            });

            result = confirm('您确定要发送此文章吗?');
                    
            var msg_type = "text-image";
            var param = [{ "name": "msg_type", value: msg_type},
                    { "name": "titles", value: titles},
                    { "name": "contents", value: contents},
                    { "name": "files", value: files}
                   ];
            //alert(param);
            if (result) {
                // 后台数据交互在这里
                
            }
        }
    }
});

});
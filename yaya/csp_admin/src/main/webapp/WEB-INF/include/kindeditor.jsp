<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${ctxStatic}/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="${ctxStatic}/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="${ctxStatic}/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="${ctxStatic}/kindeditor/lang/zh-CN.js"></script>
<script charset="utf-8" src="${ctxStatic}/kindeditor/plugins/code/prettify.js"></script>


<script>
    KindEditor.ready(function(K) {
        var editor1 = K.create('textarea[name="content"]', {
            cssPath : '${ctxStatic}/kindeditor/plugins/code/prettify.css',
            uploadJson : '${ctx}/website/news/upload', // 指定上传文件的接口
            //fileManagerJson : '/kindeditor/jsp/file_manager_json.jsp', // 指定浏览远程图片的接口，需要上传网络图片的话 放开注释
            allowFileManager : true, // true时鼠标放在表情上可以预览表情
            afterUpload:function(url){// 上传文件后执行的回调函数
                var html = $("#uploadimages").val();
                if(html==""){
                    html = html + url;
                }else{
                    html = html + "&" + url;
                }
                $("#uploadimages").val(html);

            },
            afterBlur: function(){ // 编辑器失去焦点(blur)时执行的回调函数
                this.sync();
            },
            afterCreate : function() { // 设置编辑器创建后执行的回调函数
                var self = this;
                K.ctrl(document, 13, function() {
                    self.sync();
                 //   document.forms['example'].submit();
                });
                K.ctrl(self.edit.doc, 13, function() {
                    self.sync();
                //    document.forms['example'].submit();
                });
            }
        });
        prettyPrint();
    });
</script>
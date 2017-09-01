<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title> 账号设置 </title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta name="Keywords" content="">
    <meta name="description" content="">
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
</head>
<body>



<!-- main -->
<div class="g-main clearfix">
    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-07.png" alt="">
                </div>
                <div class="oh">
                    <p><strong>账号管理</strong></p>
                    <p>账号信息管理</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->

    <div class="tab-hd">

        <ul class="tab-list clearfix">
            <li class="cur">
                <a href="${ctx}/mng/account/info">账号设置<i></i></a>
            </li>
            <li>
                <a href="${ctx}/mng/account/xsInfo">象数管理<i></i></a>
            </li>
        </ul>
    </div>
        <div class="tj-con mar-top-bd">
            <div class="tj-content clearfix">
                <div class="userDetail">
                    <span class="userDetail-icon">公开信息<span class="icon"></span></span>
                    <div class="userDetail-info userDetail-box userDetail-border clearfix">
                        <div class="fl userDetail-img">
                            <p class="img"><img src="${user.headimg}" alt="" id="image"></p>
                            <input type="file" id="headimg" style="display:none" name="file" onchange="toUpload()">
                            <button type="button" class="color-green" onclick="headimg.click()">修改头像</button>
                        </div>
                        <div class="oh">
                            <h2>${user.nickname}</h2>
                            <p>${user.address}</p>
                        </div>
                    </div>
                    <div class="userDetail-desc userDetail-box userDetail-border clearfix">
                        <div class="userDetail-desc-title">
                            <span>介绍</span><i class="rowSpace">|</i><a href="javascript:" class="click-hook" >修改</a>
                        </div>
                        <div class="content" contentEditable="false">
                            <textarea rows="12" style="padding: 5px 5px 5px 5px;"  class="input-html" cols="80" id="sign" readonly="readonly">${user.sign}</textarea>
                        </div>
                    </div>
                    <div class="userDetail-box clearfix">
                        <div class="userDetail-desc-title">
                            <span>密码</span><i class="rowSpace">|</i><a  href="#" id="modify" class="xiugai-button">修改登录密码</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</div>
<!--mask-wrap-->
<div class="mask-wrap">
    <div class="m-layer-bd-1">
        <div class="m-tb-div">
            <table class="m-table-box">
                <thead>
                    <tr>
                        <td colspan="2">
                            <div class="po-relative">
                                <h3 class="font-size-1">确认密码信息</h3>
                                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
                            </div>
                        </td>
                    </tr>
                </thead>

                <form id="modifyPwdForm" name="modifyPwdForm" action="${ctx}/mng/account/modifyPwd">
                   <tbody>
                        <tr>
                            <td class="zt-txt-4" >旧密码</td>
                            <td>
                                <input type="password" class="border-input" id="oldPassword" name="oldPassword">
                            </td>
                        </tr>
                        <tr>
                            <td class="zt-txt-4">新密码</td>
                            <td>
                                <input type="password" class="border-input" id="newPassword" name="newPassword">
                            </td>
                        </tr>
                        <tr>
                            <td class="zt-txt-4">再次确认</td>
                            <td>
                                <input type="password" class="border-input" id="rePassword" name="rePassword">
                            </td>
                        </tr>
                        <tr>
                            <td class="zt-txt-4"></td>
                            <td>
                                <input type="button" id="submitBtn" class="queren-btn submit-button" style="cursor: pointer;" value="确认修改">
                                <input type="button" class="cancel-btn cancel-button" id="cancel" value="取消修改">
                            </td>
                        </tr>
                    </tbody>
                </form>
            </table>
        </div>
    </div>
</div>
<!--/mask-wrap end-->






<script src="${ctxStatic}/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${ctxStatic}/js/util.js"></script>
<script src="${ctxStatic}/js/ajaxfileupload.js"></script>

<script>
    var editing = false;
    var fileUploadUrl = "${ctx}/file/upload";
    $(function(){
        var _userDesc = $(".userDetail-desc");
        _userDesc.find(".click-hook").click(function(){
            $(this).text("保存");
            _userDesc.find(".content").addClass("content-edit").attr("contenteditable","true");
        });

        $("#modify").click(function () {
            $('.mask-wrap').addClass('dis-table');
            $(".m-tb-div").show();

        });

        $(".close-btn-fx").click(function () {
            closeDialog();
        });

        $("#cancel").click(function () {
            closeDialog();
        });


        $(".click-hook").click(function(){
            if(editing){
                $(this).text("修改");
                $("#sign").attr("readonly","readonly");
                $("#sign").css("border","#ccc 0px solid");
                editing = false;
                $.post('${ctx}/mng/account/modifySign',{'sign':$("#sign").val()},function(result){
                    if (result.code != 0){
                        layer.msg("修改介绍失败,可能是字数超过限制");
                    }
                },'json');
            }else{
                $("#sign").removeAttr("readonly");
                $("#sign").css("border","#ccc 1px solid");
                editing = true;
                $(this).text("保存修改");
            }

        });
    });

    $("#submitBtn").click(function(){
        if (check()){
            $.post($("#modifyPwdForm").attr('action'),$("#modifyPwdForm").serialize(),function(result){
                if (result.code == 0){//成功
                    $(".mask-wrap").removeClass("dis-table");
                    layer.msg("修改密码成功");
                }else{//失败
                    layer.msg(result.err);
                }
            },'json');
        }
    });

    function closeDialog(){
        $('.mask-wrap').removeClass('dis-table');
        $('.m-tb-div').hide();
    }

    function check() {
        var oldPWD = $("#oldPassword").val();
        var newPWD = $("#newPassword").val();
        var rePWD = $("#rePassword").val();
        if ($.trim(oldPWD)==''){
            layer.msg('旧密码不能为空');
            return false;
        }
        if ($.trim(newPWD)==''){
            layer.msg('新密码不能为空');
            return false;
        }
        if ($.trim(newPWD) != newPWD){
            layer.msg('新密码不能包含空格');
            return false;
        }
        if ($.trim(newPWD).length < 6 || $.trim(newPWD).length > 32){
            layer.msg('密码长度必须为6-32位');
            return false;
        }
        if ($.trim(newPWD) == $.trim(oldPWD)){
            layer.msg('新密码与旧密码相同');
            return false;
        }
        if ($.trim(rePWD) !=$.trim(newPWD)){
            layer.msg('确认密码与新密码不一致');
            return false;
        }
        return true;
    }


    function toUpload(){
            var filename = $("#headimg").val();
            var extStart = filename.lastIndexOf(".");
            var ext = filename.substring(extStart,filename.length).toUpperCase();
            if(ext != ".BMP" && ext != ".PNG" && ext != ".JPG" && ext != ".JPEG"&&ext != ".TIFF"){
                layer.msg("文件格式不支持，请上传图片类型的文件");
            }else
            upload("headimg","headimg",2*1024*1024,uploadHandler);
    }

    function uploadHandler(result){

        var xdUrl = result.data.relativePath;
        var jdUrl = result.data.absolutePath;
        $("#image").attr("src",jdUrl);
        //保存用户修改
        $.get('${ctx}/mng/account/modifyHeadimg',{'headimg':xdUrl},function(result){

        },'json');
    }

</script>
</body>
</html>
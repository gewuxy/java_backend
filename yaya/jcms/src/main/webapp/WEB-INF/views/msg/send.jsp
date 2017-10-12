<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/5/26
  Time: 13:58
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>消息发送</title>
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/css/iconfont.css">
</head>
<body>
<div class="g-main clearfix">
    <!-- header -->
    <header class="header">
        <div class="header-content">
            <div class="clearfix">
                <div class="fl clearfix">
                    <img src="${ctxStatic}/images/subPage-header-image-02.png" alt="" />
                </div>
                <div class="oh">
                    <p><strong>群发消息</strong></p>
                    <p>重要消息网站直接推送到手机app，不错过每一条通知</p>
                </div>
            </div>
        </div>
    </header>
    <!-- header end -->
    <div class="tab-hd">
        <ul class="tab-list clearfix">
            <li class="cur">
                <a >群发消息<i></i></a>
            </li>
            <li >
                <a href="${ctx}/func/msg/list">已发送<i></i></a>
            </li>
        </ul>
    </div>
    <div class="tab-bd" id="sendView">
        <form action="${ctx}/func/msg/save" method="post" id="msgForm" name="msgForm">
            <input type="hidden" name="sendType" value="1">
        <div class="table-box-div1 ">
            <div class="table-top-box table-top-box-1pxBorder clearfix formrow">
                <strong class="margin-r" id="choiceSendFlat">
                    <span class="checkboxIcon">
                        <input type="checkbox" id="flats_wx" name="flats" value="1" class="chk_1 chk-hook">
                        <label for="flats_wx" class="inline "><i class="ico"></i>&nbsp;&nbsp;发送到微信</label>
                    </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <span class="checkboxIcon">
                        <input type="checkbox" id="flats_app" name="flats" value="0" class="chk_1 chk-hook">
                        <label for="flats_app" class=" inline"><i class="ico"></i>&nbsp;&nbsp;发送到YaYa医师APP</label>
                    </span>
                </strong>
            </div>

            <div class="sftz-box clearfix">
                <div class="hz-detail-message">

                    <div class="formrow formTitle-sftz ">
                        <div class="formControls ">
                            <span class="formPage-select-item  pr">
                                <i class="formPage-select-arrow"></i>
                                <select name="notifyType" class="text-input formPage-select textareaTitle-hook">
                                    <option value="0" i="0">会议提醒</option>
                                    <option value="1" i="1">考试提醒</option>
                                    <!--<option value="textareaTitle3" i="2">调查问卷消息</option>-->
                                </select>
                            </span>
                        </div>
                    </div>

                    <div class="textareaForm">
                        <textarea name="content" cols="70" id="content" rows="10" placeholder="填写消息内容(不超过140字)" maxlength="140"></textarea>
                    </div>

                    <div class="formrow">
                        <span class="checkboxIcon">
                            <input type="hidden" name="msgType" value="1">
                            <input type="checkbox" id="msgTypeBox" name="msgTypeBox" checked value="1" class="chk_1 chk-hook" />
                            <label for="msgTypeBox" class="formTitle "><i class="ico"></i>&nbsp;会议链接</label>
                        </span>
                        <div class="formControls">
                            <span class="formPage-select-item  pr">
                                <i class="formPage-select-arrow"></i>
                                <select name="meetId" id="meetId" class="text-input formPage-select formPage-select-hook" >
                                        <option value=""> --请选择会议-- </option>
                                        <c:forEach items="${meets}" var="meet">
                                            <option value="${meet.id}">${meet.meetName}</option>
                                        </c:forEach>
                                    </select>
                            </span>
                        </div>
                    </div>
                    <hr class="formHr" />
                    <div class="formrow">
                        <div class="formControls">
                            <!--已有目录-->
                            <div class="fl">
                                <a href="javascript:;" class="black-button formButton fx-btn-1 " id="choseGroupBtn">选择分组</a>
                            </div>
                            <div class="oh" style="line-height: 38px;">
                                <div class="metting-bootstrap metting-bootstrap-formFile">
                                    选中的分组<i class="rowSpace">:</i> <span id="choseGroup"></span>
                                </div>
                            </div>
                        </div>

                    </div>
                    <hr class="formHr" />
                    <div class="btn-wrap">
                        <input type="button" class="button back-btn" style="cursor: pointer;" id="submitBtn" value="确认设置">
                    </div>

                </div>


            </div>
        </div>
        </form>
    </div>
    <div class="tab-bd" id="successView" style="display: none;">

        <div class="table-box-div1 ">
            <div class="normalBoxItem">
                <p><img src="${ctxStatic}/images/icon-send-1.png" alt=""></p>
                <p style="font-size:22px; color:#45ccce;">已发送消息</p>
                <div class="formControls t-center" style="margin-top:60px;">
                    <a  class="formButton formButton-max formButtonBlue-02" id="successBtn">返回继续</a>
                </div>
            </div>

        </div>
    </div>

</div>
<div class="mask-wrap">
    <div class="dis-tbcell">
        <div class="distb-box distb-box-min fx-mask-box-1 " >
            <div class="mask-hd clearfix">
                <h3 class="font-size-1">设置分组</h3>
                <span class="close-btn-fx"><img src="${ctxStatic}/images/cha.png"></span>
            </div>
            <div class="clearfix hidden-box">
                <div class="formrow popup-checkbox">

                    <form id="groupForm" name="groupForm">
                        <ul class="fx-li-box" >

                            <li>
                                <span class="checkboxIcon">
                                    <input type="checkbox" id="popup_checkbox_all" class="chk_1 chk-hook">
                                    <label for="popup_checkbox_all" class="popup_checkbox_all_hook"><i class="ico"></i>&nbsp;全部关注医生</label>
                                </span>
                            </li>

                            <c:forEach items="${groupList}" var="g" varStatus="status">
                                <li>
                                <span class="checkboxIcon">
                                    <input type="checkbox" name="groupIds" id="popup_checkbox_${status.index}" value="${g.id    }" class="chk_1 chk-hook">
                                    <label for="popup_checkbox_${status.index}"  class="popup_checkbox_hook"><i class="ico"></i>&nbsp;${g.groupName}</label>
                                </span>
                                </li>
                            </c:forEach>

                        </ul>
                    </form>

                </div>

            </div>
            <div class="sb-btn-box  p-btm-1 t-right">
                <button class="close-button-fx cur" id="choseBtn">确认</button>
                <button class="close-button-fx" id="cancelChoseBtn">取消</button>
            </div>
        </div>
    </div>
</div>

<script>
    $(function(){
       $("#msgType").change(function(){
           var checked = $(this).is(":checked");
           if(checked){
               $("#meetId").removeAttr("disabled");
           }else{
               $("#meetId").attr("disabled","true");
           }
       });

       $("#submitBtn").click(function(){
           if(checkForm()){
               var index = layer.load(1, {
                   shade: [0.1,'#fff'] //0.1透明度的白色背景
               });
               $.post($("#msgForm").attr("action"), $("#msgForm").serialize(), function (data) {
                   layer.close(index);
                   $("#successView").show();
                   $("#sendView").hide();
               },'json');
           }
       });

       $("#successBtn").click(function(){
           $("#successView").hide();
           $("#sendView").show();
       });

       $("#choseGroupBtn").click(function(){
           openChosePopup();
       });

       $(".close-btn-fx, #cancelChoseBtn").click(function(){
           closeChosePopup();
       });

       $("#choseBtn").click(function(){
           $("#choseGroup").html("");
           var groupIds = $("#groupForm").find("input[name='groupIds']");
           if (groupIds.length == 0){
               layer.msg("请选择分组");
               return;
           }
           for(var i = 0 ; i < groupIds.length ; i ++){
               $("#choseGroup").append('<input type="hidden" name="choseGroupIds" value="'+$(groupIds[i]).val()+'"/>' + $(groupIds[i]).siblings("label").text());
               if (i < groupIds.length - 1){
                   $("#choseGroup").append('&nbsp;&nbsp;,&nbsp;&nbsp;');
               }
           }
           closeChosePopup();
       });

        var popupCheckbox = $('label.popup_checkbox_hook');
        var popupCheckboxNum = popupCheckbox.length - 1;

        //弹出窗 - 点击全选触发
        $('.popup_checkbox_all_hook').on('click',function(){
            $(this).toggleClass('checkboxAllCurrent');
            if($(this).hasClass('checkboxAllCurrent')){
                popupCheckbox.find('.ico').addClass('checkboxCurrent');
                popupCheckbox.prev().prop("checked","true");
            } else {
                popupCheckbox.find('.ico').removeClass('checkboxCurrent');
                popupCheckbox.prev().removeProp("checked");
            }
        });
        //弹出窗 - 点击每个选项触发
        popupCheckbox.each(function() {
            $(this).off('.click').on('click', function () {

                if (!($(this).find('.ico').hasClass('checkboxCurrent')) && $(".popup_checkbox_hook .checkboxCurrent").length == popupCheckboxNum) {

                    $('.popup_checkbox_all_hook').addClass('checkboxAllCurrent').find('.ico').addClass('checkboxCurrent');
                    $('.popup_checkbox_all_hook').prev().prop("checked","true");
                } else {
                    $('.popup_checkbox_all_hook').removeClass('checkboxAllCurrent').find('.ico').removeClass('checkboxCurrent');
                    $('.popup_checkbox_all_hook').prev().removeProp("checked");
                }
            })
        });


    });

    function openChosePopup(){
        $('.mask-wrap').addClass('dis-table');
        $(".fx-mask-box-1").show();
    }


    function closeChosePopup(){
        $('.mask-wrap').removeClass('dis-table');
        $(".fx-mask-box-1").hide();
    }

    function checkForm(){
        var hasFlats = false;
        $("input[name='flats']").each(function(){
            if ($(this).is(":checked")){
                hasFlats = true;
                return false;
            }
        });
        if (!hasFlats){
            layer.tips("必须选中至少一个平台", "#choiceSendFlat",{tips: [1, '#000000']});
            return false;
        }

        var content = $("#content").val();
        if($.trim(content) == ''){
            layer.tips("请输入消息内容", "#content",{tips: [1, '#000000']});
            $("#content").focus();
            return false;
        }

        if($("#meetId").val() == ''){
            layer.tips("请选择会议","#meetId");
            return false;
        }

//        if($("#groupId").val() == '' || $("#groupId").val() == undefined){
//            layer.tips("请选择要发送的群组","#groupId");
//            return false;
//        }
        if($("#choseGroup").text() == ''){
            layer.tips("请选择分组", "#choseGroup");
            return false;
        }
        return true;
    }
</script>
</body>
</html>

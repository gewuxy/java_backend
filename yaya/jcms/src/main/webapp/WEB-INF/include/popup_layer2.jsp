<%--
  Created by IntelliJ IDEA.
  User: weilong
  Date: 2017/7/25
  Time: 13:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--弹出层-->
<div class="mask-wrap">
    <div class="dis-tbcell">
        <div class="distb-box fx-mask-box-1 v2-data-login pr">

            <div class="fx-mask-box clearfix">
                <span class="close-btn-fx"><img src="${statics}/images/v2/icon-close.png"></span>
                <div class="formrow">
                    <div class="t-center " style="margin-bottom:25px;"><img src="${statics}/images/v2/logo-login.png"
                                                                            alt=""></div>
                    <div class="v2-login_box"><input type="text" class="input-border textInput"
                                                     placeholder="输入手机号或邮箱登录"></div>
                    <div class="v2-login_box"><input type="text" class="input-border textInput" placeholder="密码"></div>
                    <div class="v2-login_box" style="position:relative">
                        <div class="fl color-gray">
                            <input type="checkbox" id="checkbox_2" class="chk_1">
                            <label for="checkbox_2">
                                <i class="ico"></i>
                                记住我
                            </label>
                        </div>
                        <div class="fr">
                            <a href="http://www.medcn.cn/jiankangjsp/forget_pword.jsp" class="fr color-blue">忘记密码</a>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="clearfix" style="margin-bottom:5px;">
                        <a href="javascript:void(0);" class="v2-login-button v2-blue-button"
                           onclick="loginCheck();">登录</a>
                    </div>
                    <div class="v2-login_box" style="margin-bottom:35px;">
                        <p class="fz12 color-gray">提示：使用该账号可登录 <a href="#" class=" underline">合理用药APP客户端</a></p>
                    </div>
                    <div class="v2-login_bottom">
                        <div class="fl">
                            <span class="fl">还没有账号？</span>
                            <a href="javascript:;" class="color-blue  fx-btn-2">立即注册</a>
                        </div>
                        <div class="fr">
                            <a href="#" class="v2-login-icon v2-weixin"></a>&nbsp;&nbsp;&nbsp;<a href="#"
                                                                                                 class="v2-login-icon v2-qq"></a>
                        </div>

                    </div>
                </div>


            </div>
        </div>
        <div class="distb-box fx-mask-box-2 pr">
            <span class="close-btn-fx"><img src="${statics}/images/v2/icon-close.png"></span>
            <div class="fx-mask-box fx-mask-box-max clearfix">

                <div class="row">
                    <div class="col-lg-6">
                        <div class="formrow v2-reg-area">

                            <p><span class="mintitle">填写注册信息</span></p>
                            <div class="v2-login_box">
                                <p>
                                    <label for="" class="pr">
                                        <input type="text" class="input-border textInput" placeholder="输入手机号或邮箱"><span
                                            class="formIconTips"></span><span class="errorText hide">该手机号码已被注册</span>
                                    </label>
                                </p>
                            </div>
                            <div class="v2-login_box">
                                <p>
                                    <input id="btnSendCode" class="getCodeButton fr" type="button" value="获取验证码"
                                           onclick="sendMessage()"/>
                                    <span class="pr">
                                            <input type="text" class="textInput input-border textInput-min"
                                                   placeholder="验证码"><span class="formIconTips errorIcon"></span><span
                                            class="errorText" style="left:4%">验证码错误</span>
                                        </span>

                                </p>
                            </div>
                            <div class="v2-login_box">
                                <p>
                                    <label for="" class="pr">
                                        <input type="text" class="input-border textInput" placeholder="设置密码"><span
                                            class="formIconTips errorIcon"></span><span class="errorText">密码不一致</span>
                                    </label>
                                </p>
                            </div>
                            <div class="v2-login_box">
                                <p>
                                    <label for="" class="pr">
                                        <input type="text" class="input-border textInput" placeholder="确认密码"><span
                                            class="formIconTips errorIcon"></span><span class="errorText">密码不一致</span>
                                    </label>
                                </p>
                            </div>
                            <div class="v2-login_box">
                                <a href="javascript:;" class="pr textSelect-a">
                                    <i class="textSelect-arrow"></i>
                                    <select name="" class="textSelect textSelect-min">

                                        <option value="请选择">请选择</option>
                                        <option value="合理用药">合理用药</option>
                                        <option value="YaYa医师">YaYa医师</option>
                                        <option value="YaYa药师">YaYa药师</option>
                                        <option value="YaYa助手">YaYa助手</option>
                                        <option value="YaYa医学直播">YaYa医学直播</option>
                                    </select>
                                </a>
                                <span style="margin:0 2%;">省</span>
                                <a href="javascript:;" class="pr textSelect-a">
                                    <i class="textSelect-arrow"></i>
                                    <select name="" class="textSelect textSelect-min">

                                        <option value="请选择">请选择</option>
                                        <option value="合理用药">合理用药</option>
                                        <option value="YaYa医师">YaYa医师</option>
                                        <option value="YaYa药师">YaYa药师</option>
                                        <option value="YaYa助手">YaYa助手</option>
                                        <option value="YaYa医学直播">YaYa医学直播</option>
                                    </select>
                                </a>
                                <span style="margin:0 2%;">市</span>
                            </div>
                            <div class="v2-login_box" style="position:relative">
                                <div class="fl color-gray">
                                    <input type="checkbox" id="checkbox_3" class="chk_1">
                                    <label for="checkbox_3">
                                        <i class="ico"></i>
                                        我已阅读并接受《协议书》
                                    </label>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="clearfix" style="margin-bottom:5px;">
                                <a href="javascript:void(0);" class="v2-login-button v2-blue-button">注册</a>
                            </div>

                        </div>
                    </div>
                    <div class="col-lg-5 col-lg-offset-1">
                        <div class="formrow">
                            <div class="t-center " style="margin:60px 0 105px;"><img
                                    src="${statics}/images/v2/logo-login.png" alt=""></div>
                            <p class="t-center fz12">第三方账户登录</p>
                            <div class="t-center" style="margin-bottom:105px;">
                                <a href="#" class="v2-login-icon v2-login-icon-max v2-weixin-max"></a>&nbsp;&nbsp;&nbsp;<a
                                    href="#" class="v2-login-icon v2-login-icon-max v2-qq-max"></a>
                            </div>
                            <div class="v2-login_box t-center" style="margin-bottom:35px;">
                                <p class="fz12">提示：使用该账号可登录 <a href="#" class=" underline fx-btn-2">合理用药APP客户端</a></p>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount;//当前剩余秒数

    function sendMessage() {
        curCount = count;
        //设置button效果，开始计时
        $("#btnSendCode").attr("disabled", "true");
        $("#btnSendCode").val(curCount + "S");
        $("#btnSendCode").addClass("getCodeButton-current");
        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
        //向后台发送处理数据
//            $.ajax({
//                type: "POST", //用POST方式传输
//                dataType: "text", //数据格式:JSON
//                url: 'Login.ashx', //目标地址
//                data: "dealType=" + dealType +"&uid=" + uid + "&code=" + code,
//                error: function (XMLHttpRequest, textStatus, errorThrown) { },
//                success: function (msg){ }
//            });
    }

    //timer处理函数
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj);//停止计时器
            $("#btnSendCode").removeClass("getCodeButton-current");
            $("#btnSendCode").removeAttr("disabled");//启用按钮
            $("#btnSendCode").val("重新发送验证码");
        }
        else {
            curCount--;
            $("#btnSendCode").val(curCount + "S");
        }
    }
</script>


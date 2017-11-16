<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--弹出层-->
<div class="mask-wrap">
    <div class="dis-tbcell">
        <div class="distb-box fx-mask-box-1 v2-data-login pr">
            <div class="fx-mask-box clearfix" >
                <span class="close-btn-fx"><img src="${ctxStatic}/images/v2/icon-close.png"></span>
                <div class="formrow">
                    <div class="t-center " style="margin-bottom:25px;"><img src="${ctxStatic}/images/v2/logo-login.png" alt=""></div>
                    <div class="login_box login_tip login_tip_error"></div>
                    <form action="${ctx}/login" id="loginForm" name="loginForm" method="post">
                        <div class="v2-login_box">
                            <p>
                                <label for="" class="pr">
                                    <input type="text" id="ccount" name="account" class="input-border textInput" placeholder="输入手机号或邮箱登录"><span class=""></span><span class="errorText hide"></span>
                                </label>
                            </p>
                        </div>
                        <div class="v2-login_box">
                            <p>
                                <label for="" class="pr">
                                    <input type="password" id="password" name="password" class="input-border textInput" placeholder="密码"><span class=""></span><span class="errorText hide"></span>
                                </label>
                            </p>
                        </div>


                        <div class="v2-login_box" style="position:relative">
                            <div class="fl color-gray">
                                <input type="checkbox" id="checkbox_2" name="" class="chk_1">
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
                            <a href="javascript:void(0);" class="v2-login-button v2-blue-button" onclick="loginCheck();">登录</a>
                        </div>
                    </form>
                    <div class="v2-login_box" style="margin-bottom:35px;">
                        <p class="fz12 color-gray">提示：使用该账号可登录 <a href="#" class=" underline">合理用药APP客户端</a> </p>
                    </div>
                    <div class="v2-login_bottom">
                        <div class="fl">
                            <span class="fl">还没有账号？</span>
                            <a href="javascript:;" class="color-blue  fx-btn-2">立即注册</a>
                        </div>
                        <div class="fr">
                            <a href="#" class="v2-login-icon v2-weixin"></a>&nbsp;&nbsp;&nbsp;<a href="#" class="v2-login-icon v2-qq"></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="distb-box fx-mask-box-2 pr">
            <span class="close-btn-fx"><img src="${ctxStatic}/images/v2/icon-close.png"></span>
            <div class="fx-mask-box fx-mask-box-max clearfix" >
                <div class="row">
                    <div class="col-lg-6">
                        <div class="formrow v2-reg-area">
                            <p><span class="mintitle">填写注册信息</span></p>
                            <form action="${ctx}/regist/do_register" id="registForm" name="registForm" method="post">
                                <div class="v2-login_box">
                                    <p>
                                        <label for="" class="pr">
                                            <input type="text" id="registaccount" name="account" class="input-border textInput" placeholder="输入手机号或邮箱"><span class="formIconTips"></span><span class="errorText hide"></span>
                                        </label>
                                    </p>
                                </div>
                                <div class="v2-login_box">
                                    <p>
                                        <input id="btnSendCode" class="getCodeButton fr" type="button" value="获取验证码" onclick="sendMessage()" />
                                        <span  class="pr">
                                                <input type="text" id="captcha" name="captcha" class="textInput input-border textInput-min" placeholder="验证码"><span class="formIconTips "></span><span class="errorText hide" style="left:4%"></span>
                                            </span>
                                    </p>
                                </div>
                                <div class="v2-login_box">
                                    <p>
                                        <label for="" class="pr">
                                            <input type="password" id="registPassword" name="password" class="input-border textInput" placeholder="设置密码"><span class="formIconTips"></span><span class="errorText hide"></span>
                                        </label>
                                    </p>
                                </div>
                                <div class="v2-login_box">
                                    <p>
                                        <label for="" class="pr">
                                            <input type="password" id="commitPassword" name="commitPassword" class="input-border textInput" placeholder="确认密码"><span class="formIconTips"></span><span class="errorText hide"></span>
                                        </label>
                                    </p>
                                </div>
                                <div class="v2-login_box">
                                    <a href="javascript:;" class="pr textSelect-a">
                                        <i class="textSelect-arrow"></i>
                                        <select name="province" id="province" class="textSelect textSelect-min" onchange="changeOption(this.options[this.options.selectedIndex].value)">
                                        </select>
                                    </a>
                                    <span style="margin:0 2%;">省</span>
                                    <a href="javascript:;" class="pr textSelect-a">
                                        <i class="textSelect-arrow"></i>
                                        <select name="city" id="city" class="textSelect textSelect-min">
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
                                        <span class="errorText hide"></span>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="clearfix" style="margin-bottom:5px;">
                                    <a href="javascript:void(0);" class="v2-login-button v2-blue-button" id="registBtn">注册</a>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="col-lg-5 col-lg-offset-1">
                        <div class="formrow">
                            <div class="t-center " style="margin:60px 0 105px;"><img src="${ctxStatic}/images/v2/logo-login.png" alt=""></div>
                            <p class="t-center fz12">第三方账户登录</p>
                            <div class="t-center" style="margin-bottom:105px;">
                                <a href="#" class="v2-login-icon v2-login-icon-max v2-weixin-max"></a>&nbsp;&nbsp;&nbsp;<a href="#" class="v2-login-icon v2-login-icon-max v2-qq-max"></a>
                            </div>
                            <div class="v2-login_box t-center" style="margin-bottom:35px;">
                                <p class="fz12">提示：使用该账号可登录 <a href="#" class=" underline fx-btn-2">合理用药APP客户端</a> </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
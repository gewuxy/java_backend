<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/4/18
  Time: 14:33
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <%@include file="/WEB-INF/include/page_context.jsp"%>
    <meta name="keywords" content="社交，社交平台，医生，论坛，专属医生社交平台，专业医师，专业药师，执业医师，执业药师，执业资格证，医疗工具，实用，药草园">
    <title>YaYa_首个医生移动学术交流平台_药草园</title>
    <link rel="shortcut icon" href="http://www.medcn.cn/favicon.ico">
    <link rel="icon" type="image/ico" href="http://www.medcn.cn/favicon.ico" >
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/login/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/login/css/index.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/login/css/completer.css"/>
</head>

<body>

<!--头部-->
<div id="header" class="header-border">
    <div class="h_con">
        <a href="javascript:;" class="c_left fl">
            <img src="${ctxStatic}/images/logo.png" alt="logo" />
        </a>
        <div class="c_right fr">
            <span>入驻YaYa数字平台请点击</span>
            <a href="http://www.medyaya.cn/prm/web/regist/registchoice" id="apply" class="underline">立即注册</a>
        </div>
    </div>
</div>
<!--内容区域-->
<div class="warp">
    <div class="header-link">
        <div class="page-width clearfix">

            <ul>
                <li><a  class="cur">YaYa医师会议系统</a></li>
                <li><a href="http://www.medyaya.cn/prm">PRM患者管理系统</a></li>
            </ul>

        </div>
    </div>
    <div class="main-item">
        <div class="items_1">
            <!--登陆框-->
            <form action="${ctx}/login" id="loginForm" name="loginForm" method="post">
                <input type="hidden" name="redirectURL" value=""/>
                <div class="items_wrap">
                    <div class="login_con fr">
                        <div class="login_form">
                            <p class="fs14 login_box">账号登录</p>
                            <div class="login_box login_tip login_tip_error"></div>
                            <p class="f_input f_account">
                                <input type="text" name="username" id="username" placeholder="公众号/医者号/会员" />
                                <label for="username" class="ico"></label>
                            <div id="accountTip"></div>

                            </p>
                            <p class="f_input f_password login_box">
                                <input type="password" name="password" id="password" placeholder="请输入密码" />
                                <label for="password" class="ico"></label>

                            </p>
                            <div class="login_box" style="position:relative">
                                <div class="fl">
                                    <input type="checkbox" id="checkbox_1" checked="checked" class="chk_1"/>
                                    <label for="checkbox_1"><i class="ico"></i>同意&nbsp;《<a href="/yayas/service.html" class="underline">服务条款</a>》</label>
                                </div>
                                <div class="fr">
                                    <input type="checkbox" id="checkbox_2" class="chk_1"/>
                                    <label for="checkbox_2"><i class="ico"></i>记住我</label>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="login_box">
                                <a href="javascript:void(0);" class="login_btn" onclick="loginCheck();">登录</a>
                            </div>
                            <div class="login_bottom">
                                <div class="fl">
                                    <span class="fl">还没有账号？</span>
                                    <a href="/zyjsp/zy_register.jsp" class="underline">立即注册</a>
                                </div>
                                <a href="/zyjsp/zy_wjmm.jsp" class="fr">忘记密码</a>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
            </form>
        </div>
        <div class="items_2">
            <div class="items_wrap">
                <div class="w_con_2">
                    <p class="fs28 c_title">
                        UGC平台，每日约有
                        <span class="strong fs32">百场会议</span>
                        发生
                    </p>
                    <p class="fs14 c_desc">
                        医学会、医院、科室、医生个人都可以申请YaYa平台公众号<br />
                        发布学术内容、丰满各自平台。打造一流品牌，让每一个公众号都成为学术内容的创造者
                    </p>
                </div>
            </div>
        </div>
        <div class="items_3">
            <div class="items_wrap">
                <div class="w_con_3">
                    <p class="fs28 c_title">
                        单位掌握后台，
                        <span class="strong fs32">创造大数据</span>
                    </p>
                    <p class="fs14 c_desc">及时、精准推送信息，获取用户学习数据，调整学术策略</p>
                </div>
            </div>
        </div>
        <div class="items_4">
            <div class="items_wrap">
                <div class="w_con_4">
                    <p class="fs28 c_title">
                        <span class="strong fs32">无纸化考试</span>
                        轻松开展<br />
                        考试效率大幅度提高
                    </p>
                    <p class="fs14 c_desc">一键发布试题，用户手机端作答<br />
                        系统评分、生成成绩、分析报告、各项解析一目了然
                    </p>
                </div>
            </div>
        </div>
        <div class="items_5">
            <!--合作机构-->
            <div class="partners items_wrap">
                <div class="p_title fs18">与多家医疗机构合作组建数据平台</div>
                <div class="p_con">
                    <dl>
                        <dt>
                            <img src="${ctxStatic}/login/images/partner-01.png" width="78" height="78" alt="安徽省医学会骨科学分会"/>
                        </dt>
                        <dd>安徽省医学会骨科学分会</dd>
                        <a href="javascript:;" target="_blank"></a>
                    </dl>
                    <dl>
                        <dt>
                            <img src="${ctxStatic}/login/images/partner-02.png" width="78" height="78" alt="安徽医科大学附属安庆市立医院"/>
                        </dt>
                        <dd>安徽医科大学附属安庆市立医院</dd>
                        <a href="http://www.aqslyy.com.cn/" target="_blank"></a>
                    </dl>
                    <dl>
                        <dt>
                            <img src="${ctxStatic}/login/images/partner-03.png" width="78" height="78" alt="白求恩基金管理委员会"/>
                        </dt>
                        <dd>白求恩基金管理委员会</dd>
                        <a href="http://www.bqejj.com/" target="_blank"></a>
                    </dl>
                    <dl>
                        <dt>
                            <img src="${ctxStatic}/login/images/partner-04.png" width="78" height="78" alt="北京神经科学学会"/>
                        </dt>
                        <dd>北京神经科学学会</dd>
                        <a href="http://www.bjsn.org/" target="_blank"></a>
                    </dl>
                    <dl>
                        <dt>
                            <img src="${ctxStatic}/login/images/partner-05.png" width="78" height="78" alt="福建医科大学附属协和医院"/>
                        </dt>
                        <dd>福建医科大学附属协和医院</dd>
                        <a href="http://www.fjxiehe.com/" target="_blank"></a>
                    </dl>
                    <dl>
                        <dt>
                            <img src="${ctxStatic}/login/images/partner-06.png" width="78" height="78" alt="福建医科大学孟超肝胆医院"/>
                        </dt>
                        <dd>福建医科大学孟超肝胆医院</dd>
                        <a href="http://www.fzcrb.com/" target="_blank"></a>
                    </dl>
                    <dl>
                        <dt>
                            <img src="${ctxStatic}/login/images/partner-07.png" width="78" height="78" alt="广州医科大学附属第二医院"/>
                        </dt>
                        <dd>广州医科大学附属第二医院</dd>
                        <a href="http://www.gyey.com/cn/index.aspx" target="_blank"></a>
                    </dl>
                    <dl>
                        <dt>
                            <img src="${ctxStatic}/login/images/partner-08.png" width="78" height="78" alt="广州医科大学附属第一医院"/>
                        </dt>
                        <dd>广州医科大学附属第一医院</dd>
                        <a href="http://www.gyfyy.com/" target="_blank"></a>
                    </dl>
                    <dl>
                        <dt>
                            <img src="${ctxStatic}/login/images/partner-09.png" width="78" height="78" alt="莆田市卫计委"/>
                        </dt>
                        <dd>莆田市卫计委</dd>
                        <a href="http://www.ptws.gov.cn/" target="_blank"></a>
                    </dl>
                    <dl>
                        <dt>
                            <img src="${ctxStatic}/login/images/partner-10.png" width="78" height="78" alt="山东省中医院"/>
                        </dt>
                        <dd>山东省中医院</dd>
                        <a href="http://www.sdzydfy.com/web/index.asp" target="_blank"></a>
                    </dl>
                    <dl>
                        <dt>
                            <img src="${ctxStatic}/login/images/partner-11.png" width="78" height="78" alt="沈阳药科大学"/>
                        </dt>
                        <dd>沈阳药科大学</dd>
                        <a href="http://www.syphu.edu.cn/" target="_blank"></a>
                    </dl>
                    <dl>
                        <dt>
                            <img src="${ctxStatic}/login/images/partner-12.png" width="78" height="78" alt="汤森路透"/>
                        </dt>
                        <dd>汤森路透</dd>
                        <a href="https://www.thomsonreuters.cn/zh.html" target="_blank"></a>
                    </dl>

                    <div class="clear"></div>
                </div>
            </div>
        </div>
        <div class="items_6">
            <!--注册-->
            <div class="items_wrap">
                <div class="w_con_6">
                    <p class="fs32 c_title">欢迎加入YaYa医师</p>
                    <p class="fs16 c_desc">每位医生都是一个品牌</p>
                </div>
                <div class="reg fr">
                    <a href="https://yyks.medyaya.cn/web/regist/registchoice" class="fs18 r_btns" id="reg_now">立即注册</a>
                    <!--<a href="https://yyks.medyaya.cn/web/regist/regist?from=yaya" target="_blank" class="fs18 r_btns" id="apply_now">申请公众号/医者号</a>-->
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>
<!--底部-->
<div class="v2-bottom">
    <div class="page-width clearfix">
        <div class="row">
            <div class="col-lg-2">
                <h4><a href="http://www.medcn.com/about">关于我们</a></h4>
                <ul>
                    <li><a href="http://www.medcn.com/about#about-maps-00">公司简介</a></li>
                    <li><a href="http://www.medcn.com/about#about-maps-02">公司动态</a></li>
                    <li><a href="http://www.medcn.com/about#about-maps-03">合作伙伴</a></li>
                    <li><a href="http://www.medcn.com/about#about-maps-01">我们团队</a></li>
                    <li><a href="http://www.medcn.com/about#about-maps-04">联系我们</a></li>
                </ul>
            </div>
            <div class="col-lg-2">
                <h4><a href="http://www.medcn.com/mc/hlyy">移动中心</a></h4>
                <ul>
                    <li><a href="http://www.medcn.com/mc/yis">YaYa医师</a></li>
                    <li><a href="http://www.medcn.com/mc/yaos">YaYa药师</a></li>
                    <li><a href="http://www.medcn.com/mc/hlyy">合理用药</a></li>
                </ul>
            </div>
            <div class="col-lg-3">
                <h4><a href="http://yyks.medyaya.cn">数字平台</a></h4>
                <ul>
                    <li><a href="http://www.medcn.cn/zy_login.jsp">专业医学会议数字化管理平台</a></li>
                    <li><a href="http://yyks.medyaya.cn">PRM患者管理系统</a></li>
                </ul>
            </div>
            <div class="col-lg-2 none">
                <h4><a href="http://www.medcn.cn/jx_ycy/jsp/integration/home.jsp">数据中心</a></h4>
                <ul>
                    <li><a href="http://www.medcn.cn/jx_ycy/jsp/integration/home.jsp">医药数据</a></li>
                </ul>
            </div>
            <div class="col-lg-2">
                <h4><a href="http://www.medcn.com/help/">帮助与反馈</a></h4>
                <ul>
                    <li><a href="http://www.medcn.com/help/solution">产品解答</a></li>
                    <li><a href="http://www.medcn.com/help/service">服务与收费</a></li>
                    <li><a href="http://www.medcn.com/help/feedback">意见反馈</a></li>
                    <li><a href="http://www.medcn.com/help/contribute">我要投稿</a></li>
                </ul>
            </div>
            <!--<div class="col-lg-1">
                <h4><a href="http://www.medcn.cn/Medicalmall/jk.html">象城</a></h4>
            </div>-->
        </div>
    </div>
</div>
<div class="v2-footer">
    <div class="page-width clearfix">
        <p class="t-center"><a href="http://www.beian.gov.cn/" target="_blank"><img src="${ctxStatic}/images/icp.png" align="absmiddle"/> 粤公网安备 44010602003210号</a> <a href="http://www.miibeian.gov.cn/" target="_blank">粤ICP备12087993号</a> <a target="_blank"
                                                                                                        href="http://www.medcn.com/upload/temp/service_certificate.pdf">互联网药品信息经营资格证：（粤）—经营性—2013—0001</a>    © 2012-2017  敬信公司 版权所有 | <a
                href="http://www.medcn.com/help/disclaimer">免责声明</a> | <a href="http://www.medcn.com/help/updatelog">更新日志</a>
            <%--&nbsp;&nbsp;联系电话 : 020-38601688--%>
        </p>
    </div>
</div>

<script type="text/javascript" src="${ctxStatic}/js/jquery-1.12.4.min.js" ></script>
<script type="text/javascript" src="${ctxStatic}/js/placeholderfriend.js" ></script>
<script type="text/javascript" src="${ctxStatic}/js/completer.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/util.js" ></script>
<!--[if lte IE 8]>
<script type="text/javascript">
    $(function(){
        $(".chk_1").show().siblings().find("i").hide();
    });
</script>
<![endif]-->
<script type="text/javascript">
    var isPostBack = "false";

    //记住密码
    function onLoginLoaded(){
        if(isPostBack == "false")
        {
            GetLastUser();
        }
    }

    function GetLastUser(){
        //var id = "49BAC005-7D5B-4231-8CEA-16939BEACD67";
        var id = "jxloginname";
        var usr = GetCookie(id);
        if(usr != null&&usr!="")
        {
            document.getElementById('username').value = usr;
        }
        else
        {
            document.getElementById('username').value = "";
        }
        var xgmm = "";

        /***************************** 修改密码后，不进行退出操作，直接从首页登录，此时要记录密码是否被修改  **********************/
        if (xgmm!="") {
            if (xgmm=="Y") {		//密码修改过
                //清除cookie中的密码
                ResetCookie();
                $("#chkRememberPwd").attr("checked",false);
                var keep_pwsd = "";
                $("#password").val(keep_pwsd);
            }
        } else {
            GetPwdAndChk();
        }
    }
    function checkUserAndpas(){
        $("#loginForm").submit();
    }
    //点击登录时触发客户端事件
    function SetPwdAndChk() {
        //取用户名
        var usr = document.getElementById('username').value;
        //alert("用户名："+usr);

        //将最后一个用户信息写入到Cookie
        SetLastUser(usr);
        //如果记住密码选项被选中
        if (document.getElementById('chkRememberPwd').checked == true) {
            //取密码值
            var pwd = document.getElementById('password').value;
            //alert("密码："+pwd);
            var expdate = new Date();
            expdate.setTime(expdate.getTime() + 14 * (24 * 60 * 60 * 1000));
            //将用户名和密码写入到Cookie
            SetCookie(usr, pwd, expdate);
        } else {
            //如果没有选中记住密码,则立即过期
            ResetCookie();
        }
    }

    function SetLastUser(usr) {
        //var id = "49BAC005-7D5B-4231-8CEA-16939BEACD67";
        var id = "jxloginname";
        var expdate = new Date();
        //当前时间加上两周的时间
        expdate.setTime(expdate.getTime() + 14 * (24 * 60 * 60 * 1000));
        SetCookie(id, usr, expdate);
    }

    //用户名失去焦点时调用该方法
    function GetPwdAndChk() {
        var usr = document.getElementById('username').value;
        var pwd = GetCookie(escape(usr));
        if (pwd != null&&pwd!="") {
            document.getElementById('chkRememberPwd').checked = true;
            document.getElementById('password').value = pwd;
        } else {
            document.getElementById('chkRememberPwd').checked = false;
            document.getElementById('password').value = "";
        }
    }

    function getcookieByName(name){
        var strcookie = document.cookie;
        if (strcookie!=""&&strcookie!=null) {
            var arrcookie = strcookie.split("; ");		//["jsessionid=xxxxxxxx","",""]
            for(var i=0;i<arrcookie.length;i++){
                var arr = arrcookie[i].split("=");
                if(arr[0]==name) {
                    return arr[1];
                }
            }
        }
        return "";
    }

    function GetCookie(name) {
        var value = getcookieByName(name);
        return unescape(value);
    }

    /**
     function getCookieVal(offset) {
		alert(offset);
		var endstr = document.cookie.indexOf(";", offset);
		if (endstr == -1) endstr = document.cookie.length;
		//endstr = document.cookie.length;
		//endstr = document.cookie.length;
		//alert("getCookieVal "+unescape(document.cookie.substring(document.cookie.lastIndexOf("=")+1, endstr)));
		alert("getCookieVal "+unescape(document.cookie.substring(offset, endstr)));
		return unescape(document.cookie.substring(offset, endstr));
	}  **/

    //写入到Cookie
    function SetCookie(name, value, expires) {
        var argv = SetCookie.arguments;
        //本例中length = 3
        var argc = SetCookie.arguments.length;
        var expires = (argc > 2) ? argv[2] : null;
        var path = (argc > 3) ? argv[3] : null;
        var domain = (argc > 4) ? argv[4] : null;
        var secure = (argc > 5) ? argv[5] : false;
        if (name!=null&&name!="") {
            document.cookie = escape(name) + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + ((path == null) ? "" : ("; path=" + path)) + ((domain == null) ? "" : ("; domain=" + domain)) + ((secure == true) ? "; secure" : "");
        }
    }

    function ResetCookie() {
        var usr = document.getElementById('username').value;
        var expdate = new Date();
        SetCookie(usr, null, expdate);
    }

</script>
<script language='javascript'>
    document.onkeydown=function(e){
        var a=e||window.event;
        if (a.keyCode == 13){
            $("#loginForm").submit();
        }
    }

</script>
<style>

</style>
<script type="text/javascript">
    // 如果在框架或在对话框中，则弹出提示并跳转到首页
    if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
        layer.msg('您的会话已失效,请重新登录',{time:1500},function(){
            top.location = "${ctx}";
        })
    }
    $(function(){
        if("${message}"){
            msgTips("error","${message}");
        }
    })

    //自动补全邮箱地址
    $("#username").completer({
        source: ["163.com","126.com","gmail.com","qq.com","sina.com","sohu.com","sina.cn","hotmail.com","foxmail.com","yahoo.com"],
        separator: "@"
    });

    //改变窗口大小时刷新页面
    window.onresize = function(){
        location = location;
    }

    //登录验证
    function loginCheck(){
        var username = $("#username").val();
        var password = $("#password").val();
        if($.trim(username) == ''){
            msgTips("error","用户名不能为空！");
            $("#username").focus();
            return false;
        }
        if($.trim(password) == '' ){
            msgTips("error","密码不能为空！");
            $("#password").focus();
            return false;
        }
        if (document.getElementById('checkbox_1').checked == false) {
            msgTips("error","请先同意服务条款！");
            return false;
        }
        //将最后一个用户信息写入到Cookie
        SetLastUser(username);
        //如果记住密码选项被选中
        if (document.getElementById('checkbox_2').checked == true) {
            //取密码值
            var pwd = document.getElementById('password').value;
            //alert("密码："+pwd);
            var expdate = new Date();
            expdate.setTime(expdate.getTime() + 14 * (24 * 60 * 60 * 1000));
            var usr = $("#username").val();
            //将用户名和密码写入到Cookie
            SetCookie(usr, pwd, expdate);
        } else {
            //如果没有选中记住密码,则立即过期
            ResetCookie();
        }
        checkUserAndpas();
    }

    function msgTips(type,content){
        $(".login_tip").show();
        $(".login_tip_"+type).html("<i class='ico ico_"+type+"'></i>"+content);
    }

</script>
</body>
</html>

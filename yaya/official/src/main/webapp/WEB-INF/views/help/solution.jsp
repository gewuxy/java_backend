<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>产品解答 | 敬信科技</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
    <%@ include file="/WEB-INF/include/common_js.jsp" %>
</head>
<body>
<div id="wrapper">
<%@include file="/WEB-INF/include/header_help.jsp"%>
<div class="v2-helpPage-main clearfix">
    <div class="page-width clearfix">
        <div class="v2-helpPage-Menu clearfix">
            <ul>
                <li class="current"><a>产品解答</a></li>
                <li><a href="${ctx}/help/service">服务与收费</a></li>
                <li><a href="${ctx}/help/feedback">意见反馈</a></li>
                <li><a href="${ctx}/help/contribute">我要投稿</a></li>
            </ul>
        </div>
        <div class="v2-helpPage-item clearfix">
            <p>请选择您遇到问题的产品</p>
            <!-- S tabs -->
            <div class="tabs-default main-nav">
                <ul class="sf-menu">
                    <li>
                        <a href="#"><span class="currentTitle">合理用药</span></a>
                        <ul class="tabs-nav clearfix ">
                            <li>
                                <a href="javascript:;" class="current">
                                    <span>合理用药</span>
                                </a>
                            </li>
                            <li><a href="javascript:;"><span>YaYa医师</span></a></li>
                            <li><a href="javascript:;"><span>YaYa药师</span></a></li>
                            <li><a href="javascript:;"><span>专业医学学术会议数字化管理平台</span></a></li>
                            <li><a href="javascript:;" class="last"><span>YaYa医学直播</span></a></li>
                        </ul>
                    </li>
                </ul>
            </div>
            <!-- E tabs -->
        </div>
        <div class="v2-helpPage-tabs clearfix">

            <div class="tab-box ">
                <!--合理用药-->
                <div class="tab-box-content">
                    <div class="v2-helpPage-item">
                        <h4>Q: 哪些系统的手机可以安装合理用药YaYa？</h4>
                        <p class="v2-fq-text">A: 合理用药YaYa目前支持iPhone、Android系统。您可以到 <a href="http://www.medcn.com/mc/hlyy" class="color-blue"><img src="${ctxStatic}/images/v2/icon-link.png" alt="">合理用药</a> 下载。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q: 合理用药YaYa可以离线使用吗？</h4>
                        <p class="v2-fq-text">A: 合理用药YaYa暂不支持离线使用，您可以下载到个人中心，进行使用。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q: 如何保护病历的数据信息？</h4>
                        <p class="v2-fq-text">A: 药草园-合理用药信息平台，通过特有的MDI Data Protection安全防护体系的独立云端存储技术，确保病历安全。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:如何管理病历？</h4>
                        <p class="v2-fq-text">A:您可以通过移动终端或者登录<a href="https://www.medcn.com" class="color-blue">www.medcn.com</a>管理您的病历。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:在移动终端的保存的病历如何同步到网站的个人中心？</h4>
                        <p class="v2-fq-text">A:目前可以通过“在wifi下同步”的功能，wifi开启的情况下。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:成功注册的用户名是否都可以在客户端和网站使用？</h4>
                        <p class="v2-fq-text">A:用户名都可以在客户端和网站使用。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:如何找回密码？</h4>
                        <p class="v2-fq-text">A:可以发送您的用户名到 <a href="mailto:service@medcn.cn">service@medcn.cn</a> 找回您的密码。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:当下载量达到限定的数量后怎么办？</h4>
                        <p class="v2-fq-text">A:您可以删掉“旧”的下载内容后再继续下载“新”的内容。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:周边药店显示多少范围以内的药店？</h4>
                        <p class="v2-fq-text">A:iPhone显示的是2公里以内的药店；Android显示的是1公里以内的药店。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:下载的内容保存在哪里？在另外的客户端登录时，是否可以查阅以往的下载？</h4>
                        <p class="v2-fq-text">A:下载的内容保存在客户端里。在另外的客户端登陆时，要重新下载才能查阅。</p>
                    </div>
                </div>
            </div>
            <div class="tab-box clearfix">
                <!--YY医师-->
                <div class="tab-box-content">
                    <div class="v2-helpPage-item">
                        <h4>Q:怎样获取YaYa医师激活码？</h4>
                        <p class="v2-fq-text">A：YaYa医师激活码每个69.9元，您有三种方法获得激活码：<br />1、淘宝网搜索YaYa医师激活码购买；<br />2、YaYa医师每位用户均有10次免费分享激活码的机会；<br />3、发送您的姓名、医院名称、执业资格证号发送到 <a href="mailTo:app@medcn.cn"></a> app@medcn.cn，我们每天会送出20个激活码。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:注册YaYa医师需要什么条件？</h4>
                        <p class="v2-fq-text">A:YaYa医师是专为我国执业注册医师而设的医学会议平台，因此，注册YaYa医师的前提，您必须是一名执业医师，拥有国家认可的执业资格证，才可成功注册成为YaYa医师的用户。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:为什么我分享给好友的激活码，好友一直注册不成功？</h4>
                        <p class="v2-fq-text">A:每位用户均有十次免费分享激活码的机会，您的好友在获得激活码后，注册时填写的信息需要与您在邀请好友的时填写好友的信息一致，否则后台将审核不通过。 </p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:为什么我的会议那么少？</h4>
                        <p class="v2-fq-text">A:新注册用户登录后可看到YaYa专家讲堂的会议，您可通过搜索“搜索”并“关注”感兴趣公众号，即可看到和参加由这些公众号举办的所有会议、活动。 </p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:我能自己发起会议吗？</h4>
                        <p class="v2-fq-text">A:可以的，您可在 <a href="https://yyks.medyaya.cn/web/regist/regist?from=yaya" class="color-blue"><img src="${ctxStatic}/images/v2/icon-link.png" alt="">申请链接</a>   申请开通公众号，通过审核后，便可通过YaYa医师公众号平台自行发起和管理自己的会议。 </p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:为什么有些会议只有PPT?</h4>
                        <p class="v2-fq-text">A:YaYa医师平台会议支持 PPT, PPT+音频，视频 模式，主要由会议发起方决定，如果您想参加的会议只是PPT，是因为发起方只发出PPT。 </p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:如何下载会议中的课件？</h4>
                        <p class="v2-fq-text">A:会议版权归主办方，不支持下载，但在会议主办方不删除会议的情况下，可不限次数阅读。 </p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:我在非wifi的环境下参加会议，耗费流量大吗？</h4>
                        <p class="v2-fq-text">A:YaYa医师会议录制主要是使用了“PPT+音频”的创新技术，它最大的特点是不受流量限制的情况下，保留了学术会议的精华。它所耗的流量非常小。 </p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:为什么我在会议中无法提问？</h4>
                        <p class="v2-fq-text">A：如果你在会议中无法提问，可能是会议发起方设置了会议禁止提问，或会议正在进行中禁止发言。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:为什么我分享给好友的激活码，好友一直注册不成功？</h4>
                        <p class="v2-fq-text">A:每位用户均有十次免费分享激活码的机会，您的好友在获得激活码后，注册时填写的信息需要与您在邀请好友的时填写好友的信息一致，否则后台将审核不通过。: </p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:错过的会议可以回看吗？</h4>
                        <p class="v2-fq-text">A：可以的，所有会议只要主办方未下架，错过的会议可以在精彩回顾列表中找到会看。 </p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:为什么参加某些会议需要支付象数，如何才能获得象数？</h4>
                        <p class="v2-fq-text">A:象数是YaYa医师的虚拟货币，它可以用于支付参加会议费用、查阅医疗数据、指南文献、或在象城兑换商品，也可向敬信药草园申请提现（提现比例按平台的协议规定）
                            会议发起方可根据实际情况设置参加会议所需支付的象数，因此，要参加这些会议，需按要求支付会议发起方设定的象数。
                            <br/>
                            您可通过充值，或参加某些赠送象数的活动获得象数。（具体看《如何获得象数》） </p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:我的象数可以转给另外一个用户吗？</h4>
                        <p class="v2-fq-text">A:可以的，在我的象数里面，输入转让象数的数量、对方的账号和登陆密码即可完成转让。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:我可以创建我的讨论群吗?</h4>
                        <p class="v2-fq-text">A:可以的，具体步骤：“我>群>右上角。。。>创建群” 即可。 </p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:如何找回YaYa医师的账号密码？</h4>
                        <p class="v2-fq-text">A:进到登录界面，点击“忘记密码”>>>在电子邮箱输入框内输入登录账号，点击“提交”；>>>填写的电子邮箱收到重置新密码链接，点击链接后，输入新的密码。使用新密码即可成功登录。</p>
                    </div>
                </div>
            </div>
            <div class="tab-box clearfix">
                <!--YY药师-->
                <div class="tab-box-content">
                    <div class="v2-helpPage-item">
                        <h4>Q：哪些人可以注册成为YaYa药师的用户？</h4>
                        <p class="v2-fq-text">A：YaYa药师是为我国执业药师而开设的专业平台，只要是执业药师，在注册时提供邮箱、真是姓名，药师执业证明就能成为YaYa药师的用户。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q：YaYa药师支持哪些手机版本？</h4>
                        <p class="v2-fq-text">A：目前，YaYa药师支持android 和ios两种系统的移动设备。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q：如何注册YaYa药师？</h4>
                        <p class="v2-fq-text">A：到手机应用商店或app store ，成功下载安装YaYa药师App, ，然后点击新用户注册，输入有效邮箱、真实姓名等信息即可。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q：YaYa医师有哪些主要功能？</h4>
                        <p class="v2-fq-text">A：YaYa药师平台上有药师建议、医师建议、医药新闻、药品说明书四大主要部分，用户可以根据实际需要查询相关信息。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q：如何找回YaYa药师的账号密码？</h4>
                        <p class="v2-fq-text">A：进到登录界面，点击“忘记密码”>>>在电子邮箱输入框内输入登录账号，点击“提交”；>>>填写的电子邮箱收到重置新密码链接，点击链接后，输入新的密码。使用新密码即可成功登录。</p>
                    </div>
                </div>
            </div>
            <div class="tab-box clearfix">
                <!--YY助手-->
                <div class="tab-box-content">
                    <div class="v2-helpPage-item">
                        <h4>Q：需具备哪些条件才能开通YaYa医师公众账号？</h4>
                        <p class="v2-fq-text">A：YaYa医师医学学术会议数字化管理平台平专门为医疗单位，例如医院、科室、学会、药企或医学专家而设的学术会议管理平台。主要您是其中之一，具备国家认可的医疗执业许可证等就能申请开通YaYa医师公众账号？</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q：如何开通YaYa医师公众账号？</h4>
                        <p class="v2-fq-text">A: 点击查看药草园YaYa公众平台服务协议，点击下载申请公众账号表格，将邮件发送到 <a href="mailto:service@medcn.cn">service@medcn.cn</a>  ，或致电: 020-38601688咨询。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:开通YaYa医师公众账号需要收费吗？如何收费？</h4>
                        <p class="v2-fq-text">A:可免费试用三个月，如需要长期使用，可采购不同套餐服务。目前，YaYa医师公众平台提供两种套餐服务，分别为基础版和专业版。具体收费及提供服务如下表：</p>
                        <div class="formrow">
                            <table class="formPriceTable fs14 formInfoTable" style="width:100%" cellspacing="0">
                                <colgroup>
                                    <col class="col3">
                                    <col class="col2">
                                    <col class="col4">
                                    <col class="col4">
                                </colgroup>
                                <tbody>
                                <tr>
                                    <td colspan="2"><strong>服务说明</strong></td>
                                    <td class="color-green1"><strong>基础版</strong></td>
                                    <td class="color-blue"><strong>专业版</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="color-blue"><strong>价格</strong></td>
                                    <td><strong><em>10000元</em></strong></td>
                                    <td><strong><em>50000元</em></strong></td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="color-blue"><strong>使用时间</strong></td>
                                    <td><strong>12个月</strong></td>
                                    <td><strong>12个月</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="color-blue"><strong>会议人数</strong></td>
                                    <td><strong>300人</strong></td>
                                    <td><strong>不限</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="color-blue"><strong>升级服务</strong></td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                </tr>
                                <tr>
                                    <td rowspan="7"><strong>会议功能</strong></td>
                                    <td>在线发布会议数量</td>
                                    <td><strong>50个/年</strong></td>
                                    <td><strong>不限</strong></td>
                                </tr>
                                <tr>
                                    <td>非视频会议</td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                </tr>
                                <tr>
                                    <td>病例讨论/考试</td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                </tr>
                                <tr>
                                    <td>问卷投票</td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                </tr>
                                <tr>
                                    <td>视频会议</td>
                                    <td colspan="2" rowspan="2">
                                        <strong>按实际流量计费，<br>平均每人两小时消耗大概1G流量，1G=10象数</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td>现场直播</td>
                                </tr>
                                <tr>
                                    <td>会议签到</td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                </tr>
                                <tr>
                                    <td><strong>学术资料空间</strong></td>
                                    <td>资料上传</td>
                                    <td><strong>1G课件上传</strong></td>
                                    <td><strong>10G课件上传</strong></td>
                                </tr>
                                <tr>
                                    <td rowspan="2"><strong>资源平台</strong></td>
                                    <td>学术会议转载</td>
                                    <td colspan="2"><strong>学术内容流转平台<br>公众号自行设定</strong></td>
                                </tr>
                                <tr>
                                    <td>专业考试题库</td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                </tr>
                                <tr>
                                    <td rowspan="5"><strong>数据分析</strong></td>
                                    <td>单个会议数据分析</td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                </tr>
                                <tr>
                                    <td>会议总量数据分析</td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                </tr>
                                <tr>
                                    <td>用户维度分析</td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                </tr>
                                <tr>
                                    <td>内容维度分析</td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                </tr>
                                <tr>
                                    <td>转载数据分析</td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                </tr>
                                <tr>
                                    <td rowspan="2"><strong>互动消息</strong></td>
                                    <td>一对一消息</td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                </tr>
                                <tr>
                                    <td>群发消息</td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                    <td><img src="${ctxStatic}/images/v2/icon_true2.png" alt=""></td>
                                </tr>

                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q：如何申请开通套餐？</h4>
                        <p class="v2-fq-text">A:点击 &nbsp;<a href="https://yyks.medyaya.cn/web/regist/regist?from=yaya" class="color-blue"><img src="${ctxStatic}/images/v2/icon-link.png" alt="">申请链接</a> 直接申请，或发邮件至service@medcn.cn 我们会有专门负责的同事与您联系。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q:如何找回密码？</h4>
                        <p class="v2-fq-text">A:可以发送您的用户名到 <img src="${ctxStatic}/images/v2/icon-link.png" alt=""><a href="mailto:service@medcn.cn" class="color-blue">service@medcn.cn</a> 找回您的密码。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q：YaYa医师可以直播会议吗？怎么开通？</h4>
                        <p class="v2-fq-text">A:可以的，为了提高学术会议召开效率，YaYa医师公众号平台为所有公众号提供了视频直播功能服务，已开通YaYa医师公众账号的医疗单位，只需下载YaYa医学直播App，在YaYa医学直播App登录，便可开始直播。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q：YaYa医师“PPT+音频“与YaYa医学直播有什么区别吗？</h4>
                        <p class="v2-fq-text">A:“PPT+音频“是YaYa医师平台独创的会议呈现技术，它保留了会议的精髓部分--会议课件，画面清晰流畅，避开了会议复杂画面，让用户学习起来更加专注，而且用户可根据自己的实际情况随便精准调转到指定页面进行学习。而YaYa医学直播会议，是YaYa医师公众账号召开会议的另一模式，通过手机就能同步会议现场，是真正意义的会议视频直播，它采用了”课件+会场“双镜头设计，通过手机屏幕，参会医生既可看到会议现场又可清晰看到课件。此外，两者收费标准不一样，详细可参考《服务与收费》。</p>
                    </div>
                </div>
            </div>
            <div class="tab-box clearfix">
                <!--YY医学直播-->
                <div class="tab-box-content">
                    <div class="v2-helpPage-item">
                        <h4>Q：什么是YaYa医学直播？</h4>
                        <p class="v2-fq-text">A:  YaYa医学直播是敬信药草园专门为YaYa医师公众平台用户医院、科室、学会或专家个人开发得直播医学会议的专业助手。申请开通YaYa医师公众号的用户，通过YaYa医学直播就能现场直播会议，还可与用户实时在线互动。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q：如何注册YaYa医学直播账号？</h4>
                        <p class="v2-fq-text">A：YaYa医学直播是专为YaYa医师公众号而设的视频直播会议功能，因此，要使用YaYa医学直播功能，必须先在YaYa医师公众平台申请开通账号。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q：YaYa医学直播对手机摄像头有要求吗？</h4>
                        <p class="v2-fq-text">A：没有的，只要直播会议的手机是android 或 ios 系统的智能手机即可。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q：直播的时候怎样与用户互动？</h4>
                        <p class="v2-fq-text">A：在直播画面右侧可看到粉丝的发言，会议主播可根据发言，直接互动即可。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q：我要使用会议直播功能，怎么收费？</h4>
                        <p class="v2-fq-text">A：YaYa医学直播按粉丝观看产生的实际流量进行计费，大概为平均每人两小时消耗大约1G流量，1G流量=10象数。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q：医生参加直播会议需要收费吗？</h4>
                        <p class="v2-fq-text">A：医生参会是否需要付费由会议主办方决定，会议主办方可根据实际情况设置参会付费情况，参会医生在进入”会议室“的时候会有消息提醒。</p>
                    </div>
                    <div class="v2-helpPage-item">
                        <h4>Q：一小时会议直播大概花费多少流量？</h4>
                        <p class="v2-fq-text">A：平均每人每小时消耗大约0.5G流量</p>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
    <%@include file="/WEB-INF/include/footer.jsp"%>
</div>
<div class="gotop-wrapper index-gotop">
    <a class="gotop" href="javascript:;" >回到顶部</a>
</div>
<script src="${ctxStatic}/js/v2/jquery.tools.min.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>组员列表</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
</head>
<body>
<div id="wrapper">
    <%@include file="/WEB-INF/include/header.jsp"%>
    <div class="v2-banner v2-banner-notPadding">
        <!-- S slideshow -->
        <div class="slideshow carousel responsive-carousel clearfix" >
            <div id="responsive-01">
                <div class="carousel-item">
                    <div class="carousel-img">
                        <a>
                            <img src="${ctxStatic}/images/upload/joinBanner.jpg" alt="" />
                        </a>
                    </div>
                </div>
            </div>
            <div class="carousel-btn carousel-btn-fixed" id="carousel-page-05"></div>
        </div>
        <!-- E slideshow -->
    </div>
    <div class="v2-sub-main">
        <div class="v2-full-area">
            <div class="page-width clearfix">
                <div class="module-full-screen-title">
                    <div class="module-title-content">
                        <i class="mark-left"></i>
                        <h3 >我们的福利</h3>
                        <i class="mark-right"></i>
                    </div>
                </div>
                <div class="module-full-screen-content" >
                    <div class="row v2-join-info" >
                        <div class="col-lg-2">
                            <p class="t-center"><img src="${ctxStatic}/images/upload/join-img3.png" alt=""></p>
                            <p class="t-center">5天工作制</p>
                        </div>
                        <div class="col-lg-2">
                            <p class="t-center"><img src="${ctxStatic}/images/upload/join-img6.png" alt=""></p>
                            <p class="t-center">5天带薪年假</p>
                        </div>
                        <div class="col-lg-2">
                            <p class="t-center"><img src="${ctxStatic}/images/upload/join-img2.png" alt=""></p>
                            <p class="t-center">五险一金</p>
                        </div>
                        <div class="col-lg-2">
                            <p class="t-center"><img src="${ctxStatic}/images/upload/join-img1.png" alt=""></p>
                            <p class="t-center">年终奖</p>
                        </div>
                        <div class="col-lg-2">
                            <p class="t-center"><img src="${ctxStatic}/images/upload/join-img4.png" alt=""></p>
                            <p class="t-center">工作餐</p>
                        </div>
                        <div class="col-lg-2">
                            <p class="t-center"><img src="${ctxStatic}/images/upload/join-img5.png" alt=""></p>
                            <p class="t-center">国家法定节假日</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="v2-full-area">
            <div class="page-width clearfix">
                <div class="module-full-screen-title">
                    <div class="module-title-content">
                        <i class="mark-left"></i>
                        <h3 >招聘职位</h3>
                        <i class="mark-right"></i>
                    </div>
                </div>
                <div class="module-full-screen-content" >
                    <div class="v2-join">
                        <div class="v2-join-item">
                            <h3 class="v2-join-item-title clearfix">
                                <img src="${ctxStatic}/images/upload/_join-img1.png" alt="" class="fl">
                                <div class="oh">
                                    <span>Java开发工程师</span>
                                </div>
                            </h3>
                            <div class="v2-join-text clearfix">
                                <p>坐标:广州</p>
                                <p><strong>岗位职责:</strong></p>
                                <p>1、负责网站后台或移动服务端开发，根据产品需求和开发规范独立完成网站后台或移动服务端的编码、测试及相关开发文档编写。</p>
                                <p>2、负责移动服务端的相关接口和功能开发，完成网站后台的业务逻辑开发。</p>
                                <p>3、协同并配合客户端开发，保证项目的整体进度。</p>
                                <p><strong>任职要求:</strong></p>
                                <p>1、本科学历，计算机类相关专业，有3年以上Java开发经验，熟悉面向对象编程和UML语言规范，熟悉J2EE技术架构</p>
                                <p>2、熟悉spring、struts、hibernate、MyBaties/Ibaties等开源框架，理解Web及前端技术（包括 Javascript、ajax、JSON、JQuery等技术）。</p>
                                <p>3、熟练使用MyEclipse/ Eclipse开发工具、SVN版本控制工具及ANT编译。</p>
                                <p>4、熟悉流行的J2EE应用服务器（Apache Tomcat / Weblogic）及其配置。</p>
                                <p>5、熟悉基于MySQL/Oracle的数据库编程， 掌握 SQL 语言和SQL性能优化。</p>
                                <p>6、具备良好的沟通能力和团队协作精神。</p>
                            </div>
                        </div>
                        <div class="v2-join-item">
                            <h3 class="v2-join-item-title clearfix">
                                <img src="${ctxStatic}/images/upload/_join-img2.png" alt="" class="fl">
                                <div class="oh">
                                    <span>软件测试工程师</span>
                                </div>
                            </h3>
                            <div class="v2-join-text clearfix">
                                <p>坐标:广州</p>
                                <p><strong>岗位职责:</strong></p>
                                <p>1、负责手机终端APP的功能测试、稳定性测试、压力性能测试、移动平台兼容性测试、安全测试等方面的测试工作。</p>
                                <p>2、根据产品设计、需求等文档制定测试计划，配置测试环境，设计和执行测试用例。</p>
                                <p>3、执行模块测试、系统测试和回归测试，跟踪产品缺陷直至符合发布标准，并及时对产品质量做分析和评估，推动测试中发现问题及时合理的解决。</p>
                                <p><strong>任职要求:</strong></p>
                                <p>1、熟练掌握测试理论方法以及软件测试流程。</p>
                                <p>2、有Android／iOS 平台1年及以上测试经验，熟悉Android、iOS 系统手机功能测试、自动化测试、性能测试、移动平台兼容性测试及人机交互测试。</p>
                                <p>3、对Java、object-C等语言有所了解。</p>
                                <p>4、熟练使用至少一种测试缺陷管理工具(Quality Center、TestDirector、Mantis)，熟悉版本控制软件(svn、cvs)</p>
                                <p>5、能够独立完成测试计划的制定，测试方案、测试用例和bug报告的规范编写。</p>
                                <p>6、熟练掌握自动化测试命令和工具（adb命令、MonkeyTalk、Appium、QTP）。</p>
                                <p>7、熟悉错误！超链接引用无效。工具LoadRunner者优先。</p>


                            </div>
                        </div>
                        <div class="v2-join-item">
                            <h3 class="v2-join-item-title clearfix">
                                <img src="${ctxStatic}/images/upload/_join-img3.png" alt="" class="fl">
                                <div class="oh">
                                    <span>运维工程师</span>
                                </div>
                            </h3>
                            <div class="v2-join-text clearfix">
                                <p>坐标:广州</p>
                                <p><strong>岗位职责:</strong></p>
                                <p>1、负责公司服务器的维护工作，并能发现潜在问题及解决，保障系统稳定、正常运行；</p>
                                <p>2、支持公司各项目业务上线，负责后端系统故障处理，分析定位原因并解决；</p>
                                <p>3、负责数据库在windows server、linux的安装、日常监控、维护工作； </p>
                                <p>4、负责内、外网测试环境的java运行环境的搭配，如JDK、tomcat的安装、配置；</p>
                                <p>5、负责负载均衡的配置；</p>
                                <p>6、 制定运维技术方案，数据备份、数据监控、应急响应、故障排除、编写数据分析报告等。 </p>
                                <p><strong>任职要求:</strong></p>
                                <p>1、大专或以上学历，深入了解Linux系统以及优化，能够针对业务场景定制Linux参数设置，熟练使用Grep/Awk/Sed等系统工具，擅长(Shell/Perl/Python)脚本语言编程，实现自动化运维；</p>
                                <p>2、熟悉主流开源软件(Nginx/Tomcat/Php-fpm/Flume)的配置及调优；</p>
                                <p>3、了解MySQL可用性架构；</p>
                                <p>4、了解运维高容量、大流量、大并发Web系统的业务知识以及解决方案；有大、中型网站维护实战经验者优先；</p>
                                <p>5、深入理解网站高可用性架构：LVS/Haproxy/F5/容器Docker/微服务以及NoSQL相关（Memcache/Redis）等；</p>
                                <p>6、对DNS体系/CDN原理有一定的了解；</p>
                                <p>7、对技术文档编写规范有一定了解，能阅读英文技术文档；</p>
                                <p>8、良好的沟通和语言表达能力，逻辑清晰，学习能力强，有自我激励能力，乐于承担工作压力，责任心强。 </p>
                            </div>
                        </div>
                        <div class="v2-join-item">
                            <h3 class="v2-join-item-title clearfix">
                                <img src="${ctxStatic}/images/upload/_join-img4.png" alt="" class="fl">
                                <div class="oh">
                                    <span>产品运营</span>
                                </div>
                            </h3>
                            <div class="v2-join-text clearfix">
                                <p>坐标:广州</p>
                                <p><strong>岗位职责:</strong></p>
                                <p>1、全面了解公司产品，提高对产品的认知程度</p>
                                <p>2、制定APP运营方针、策略及各类事件营销活动方案</p>
                                <p>3、负责公司产品APP的日常运维和推广</p>
                                <p>4、负责APP渠道推广与活动支持，制定渠道推广投放策略和计划</p>
                                <p>5、分析用户反馈、运营数据、跟踪并指导运营优化方向，与产品研发团队协调，提升运营效果</p>
                                <p>6、收集以及分析竞争对手、用户在APP方面的使用习惯，协助制订和执行相应的营销策略</p>
                                <p><strong>任职要求:</strong></p>
                                <p>1、大专以上，2年以上的移动APP产品运营管理经验</p>
                                <p>2、熟悉各种手机客户端软件及平台特性（如ios\android等）</p>
                                <p>3、熟悉移动互联网行业，熟悉各种软件商店、论坛的市场规则，对APP的推广和运营有一定的认识</p>
                                <p>4、熟悉APP的各类指标，收集用户行为数据及相应统计方法，具备较强的理解和策划能力</p>
                                <p>5、具备团队合作精神、良好沟通能及执行力。</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="v2-full-area bg-joinEmail" style="padding:96px 0;">
            <div class="page-width clearfix">
                <div class="module-full-screen-title">
                    <div class="module-title-content">
                        <i class="mark-left"></i>
                        <h3 >简历投递邮箱</h3>
                        <i class="mark-right"></i>
                    </div>
                </div>
                <div class="module-full-screen-content" >
                    <p class="t-center" style="font-size:36px; ">hr@medcn.cn</p>
                </div>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/include/footer.jsp"%>
</div>
<%@include file="/WEB-INF/include/markWrap.jsp" %>
<%@ include file="/WEB-INF/include/common_js.jsp" %>
</body>
</html>

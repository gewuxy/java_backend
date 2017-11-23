<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>关于我们 | 敬信科技</title>
    <%@ include file="/WEB-INF/include/common_css.jsp" %>
    <%@ include file="/WEB-INF/include/common_js.jsp" %>

    <script src="${ctxStatic}/js/v2/stickUp.min.js"></script>
    <script src="${ctxStatic}/js/v2/jquery.fancybox-1.3.4.pack.js"></script>
    <script type="text/javascript">
        /*固定栏*/
        jQuery(function($) {
            $(document).ready( function() {
                $('.fixed-nav').stickUp({
                    marginTop: 'auto'
                });
            });
        });


    </script>
    <%--<script type="text/javascript" src="//api.map.baidu.com/api?key=&v=1.4&services=true"></script>--%>
    <%--<script type="text/javascript">--%>
    <%--//创建和初始化地图函数：--%>
    <%--function initMap(){--%>
    <%--createMap();//创建地图--%>
    <%--setMapEvent();//设置地图事件--%>
    <%--addMapControl();//向地图添加控件--%>
    <%--addMarker();//向地图中添加marker--%>
    <%--}--%>

    <%--//创建地图函数：--%>
    <%--function createMap(){--%>
    <%--var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图--%>
    <%--var point = new BMap.Point(113.337982,23.121986);//定义一个中心点坐标--%>
    <%--map.centerAndZoom(point,18);//设定地图的中心点和坐标并将地图显示在地图容器中--%>
    <%--window.map = map;//将map变量存储在全局--%>
    <%--}--%>

    <%--//地图事件设置函数：--%>
    <%--function setMapEvent(){--%>
    <%--map.enableDragging();//启用地图拖拽事件，默认启用(可不写)--%>
    <%--map.enableScrollWheelZoom();//启用地图滚轮放大缩小--%>
    <%--map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)--%>
    <%--map.enableKeyboard();//启用键盘上下左右键移动地图--%>
    <%--}--%>

    <%--//地图控件添加函数：--%>
    <%--function addMapControl(){--%>
    <%--//向地图中添加缩放控件--%>
    <%--var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});--%>
    <%--map.addControl(ctrl_nav);--%>
    <%--//向地图中添加缩略图控件--%>
    <%--var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:0});--%>
    <%--map.addControl(ctrl_ove);--%>
    <%--//向地图中添加比例尺控件--%>
    <%--var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});--%>
    <%--map.addControl(ctrl_sca);--%>
    <%--}--%>

    <%--//标注点数组--%>
    <%--var markerArr = [{title:"敬信药草园",content:"地址：广州市天河区珠江新城兴民路222号天盈广场西塔2603",point:"113.337982|23.121986",isOpen:1,icon:{w:21,h:21,l:0,t:0,x:6,lb:5}}--%>
    <%--];--%>
    <%--//创建marker--%>
    <%--function addMarker(){--%>
    <%--for(var i=0;i<markerArr.length;i++){--%>
    <%--var json = markerArr[i];--%>
    <%--var p0 = json.point.split("|")[0];--%>
    <%--var p1 = json.point.split("|")[1];--%>
    <%--var point = new BMap.Point(p0,p1);--%>
    <%--var iconImg = createIcon(json.icon);--%>
    <%--var marker = new BMap.Marker(point,{icon:iconImg});--%>
    <%--var iw = createInfoWindow(i);--%>
    <%--var label = new BMap.Label(json.title,{"offset":new BMap.Size(json.icon.lb-json.icon.x+10,-20)});--%>
    <%--marker.setLabel(label);--%>
    <%--map.addOverlay(marker);--%>
    <%--label.setStyle({--%>
    <%--borderColor:"#808080",--%>
    <%--color:"#333",--%>
    <%--cursor:"pointer"--%>
    <%--});--%>

    <%--(function(){--%>
    <%--var index = i;--%>
    <%--var _iw = createInfoWindow(i);--%>
    <%--var _marker = marker;--%>
    <%--_marker.addEventListener("click",function(){--%>
    <%--this.openInfoWindow(_iw);--%>
    <%--});--%>
    <%--_iw.addEventListener("open",function(){--%>
    <%--_marker.getLabel().hide();--%>
    <%--});--%>
    <%--_iw.addEventListener("close",function(){--%>
    <%--_marker.getLabel().show();--%>
    <%--});--%>
    <%--label.addEventListener("click",function(){--%>
    <%--_marker.openInfoWindow(_iw);--%>
    <%--});--%>
    <%--if(!!json.isOpen){--%>
    <%--label.hide();--%>
    <%--_marker.openInfoWindow(_iw);--%>
    <%--}--%>
    <%--})()--%>
    <%--}--%>
    <%--}--%>
    <%--//创建InfoWindow--%>
    <%--function createInfoWindow(i){--%>
    <%--var json = markerArr[i];--%>
    <%--var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + json.title + "'>" + json.title + "</b><div class='iw_poi_content'>"+json.content+"</div>");--%>
    <%--return iw;--%>
    <%--}--%>
    <%--//创建一个Icon--%>
    <%--function createIcon(json){--%>
    <%--var icon = new BMap.Icon("http://app.baidu.com/map/images/us_mk_icon.png", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,1),offset:new BMap.Size(json.x,json.h)});;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;--%>
    <%--return icon;--%>
    <%--}--%>

    <%--$(function () {--%>
    <%--initMap();//创建和初始化地图--%>
    <%--})--%>
    <%--</script>--%>
    <script type="text/javascript" src="//api.map.baidu.com/api?v=2.0&ak=XlgUFkD2Gir0u83w725EiRkOK4FX3OQj"></script>

    <script type="text/javascript">

        $(function () {
            // 百度地图API功能
            var map = new BMap.Map("allmap");    // 创建Map实例
            map.centerAndZoom(new BMap.Point(113.337982, 23.121986), 30);  // 初始化地图,设置中心点坐标和地图级别
            map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
            map.setCurrentCity("广州");          // 设置地图显示的城市 此项是必须设置的
            map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
        })
    </script>



</head>
<body>
<div id="wrapper">
    <%@include file="/WEB-INF/include/header_common.jsp"%>
    <div class="v2-sub-main">
        <div class=" v2-about-bg">
            <div class="page-width clearfix">
                <p class="t-center" style="padding-top:240px;"><img src="${ctxStatic}/images/upload/about-title1.png" alt=""></p>
                <p style="font-size:0;"><img src="${ctxStatic}/images/upload/about-title-img.png" alt=""></p>
                <div  id="about-maps-00"  style="padding-top: 20px;">&nbsp;</div>
                <div class="v2-about-item clearfix" >
                    <div class="row">
                        <div class="col-lg-6">
                            <h3>公司简介/company profile</h3>

                            <p>&nbsp;&nbsp;&nbsp;&nbsp;广州敬信药草园信息科技有限公司成立于2012年3月，是我国最早探索医药互联网的企业之一。成立以来，公司秉承“敬事而信”的理念，专注于移动医疗领域的发展和创新。</p>
                            <p>&nbsp;</p>
                            <p>&nbsp;&nbsp;&nbsp;&nbsp;核心产品YaYa医师是我国首个医药行业数字化学术会议Saas平台。产品自上线以来，致力于为医疗单位提供线上会议、培训考核、医生管理、数据建设等功能在内的一站式数字化解决方案。产品以创新的“培训+互联网”设计模式，为医生职业再培训尤其是基层医生的培训提供了优异的技术平台，积极配合了国家分级诊疗战略的落地，获得近千家医疗单位认可。</p>
                            <p>&nbsp;</p>
                            <p>&nbsp;&nbsp;&nbsp;&nbsp;2015年，YaYa医师获得“2015中国医药企业社会责任奖平台创新奖”，得到南方都市报、南方周末、第一财经日报、医药观察家、今日头条等多家媒体的争相报道。</p>
                            <p>&nbsp;</p>
                            <p>&nbsp;&nbsp;&nbsp;&nbsp;未来，公司将继续专注医药行业互联网产品研究，为“健康中国”发展尽一份企业责任。</p>
                        </div>
                        <div class="col-lg-5 col-lg-offset-1">
                            <h4>视频介绍</h4>
                            <div class="v2-about-viedo">
                                <iframe height=350 width=467 src='//player.youku.com/embed/XMjU2NDI4MDc2OA=='
                                        frameborder='0' allowfullscreen></iframe>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <div  id="about-maps-01" style="padding-top: 20px;">&nbsp;</div>
        <div class="v2-full-area v2-banner-gray v2-about-team">
            <div class="page-width clearfix">
                <h3 class="title">我们团队/Our Team  <a href="${ctx}/team" class="fr more v2-about-team-more">点这查看大部队</a></h3>
                <div class="v2-about-team-item">
                    <ul>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team1.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">王昶</span><br />防禁自心</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team3.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">林晓美</span><br />财上平如水，人中直似衡</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team19.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">梁胜蓝</span><br />接受自己的极限，我们才能超越自己</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team23.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">吴玉姬</span><br />Wisdom is to the mind what health is to the body.</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team24.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">佟一鹏</span><br />世界上没有什么事是一顿饭不能解决的，如果有，那就两顿！</div>
                            </a>
                        </li>

                        <li class="last">
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team5.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">王东</span><br />想象力比知识更重要。</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team26.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">陈玉玲</span><br />我们必须拿我们所有的，去换我们所没有的</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team2.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">李欣</span><br />一切胜利都是制度的胜利</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team10.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">姜宇坤</span><br />你有多努力就有多幸运</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team6.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">谭清月</span><br />好的创意都不是脱离逻辑的天马行空</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team18.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">周星杰</span><br />你不坚持和做逃兵有什么区别</div>
                            </a>
                        </li>
                        <li class="last">
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team16.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">胡湛</span><br />积一时之跬步,臻千里之遥程。</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team21.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">李轩</span><br />志高山峰矮，路从脚下伸。</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team9.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">陆海升</span><br />一切皆有可能！</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team13.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">王三妹</span><br />阳光总在风雨后</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team25.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">林桢</span><br />不卑不亢，不骄不躁</div>
                            </a>
                        </li>
                        <li >
                            <a>
                                <div class="v2-about-team-img">
                                    <i class="v2-about-team-bg"></i><img src="${ctxStatic}/images/upload/team7.png" alt=""></div>
                                <div class="v2-about-team-info"><span class="v2-about-team-title">黄幸欣</span><br />越努力越幸运！</div>
                            </a>
                        </li>
                        <li class="last v2-team-join-button">
                            <a href="${ctx}/joinus">
                                <div class="v2-about-team-img"><img src="${ctxStatic}/images/upload/team-join.png" alt=""></div>
                            </a>
                        </li>
                    </ul>



                </div>
            </div>
        </div>
        <div  id="about-maps-02" style="padding-top: 20px;">&nbsp;</div>
        <div class="v2-full-area v2-about-news">
            <div class="page-width clearfix">
                <h3 class="title">公司动态/Company News <a href="${ctx}/news/trends" class="fr more">查看更多</a> </h3>
                <div class="v2-news-graphic ">
                    <c:forEach items="${page.dataList}" var="news">
                        <div class="v2-news-graphic-item clearfix">
                            <div class="fl"><a href="${ctx}/news/viewtrend/${news.id}"><img src="${news.articleImg}" alt=""></a></div>
                            <div class="oh">
                                <h3><a href="${ctx}/news/viewtrend/${news.id}">${news.title}</a></h3>
                                <p class="v2-news-graphic-info">${news.summary}</p>
                                <p><span class="time"><fmt:formatDate value="${news.createTime}" pattern="yyyy/MM/dd"/></span>&nbsp;&nbsp;&nbsp;&nbsp;<span>来源：${news.xfrom}</span></p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div id="about-maps-03" style="padding-top: 20px;">&nbsp;</div>
        <div class="v2-full-area v2-banner-blue v2-about-links" >
            <div class="page-width clearfix">
                <h3 class="title">合作伙伴/Partner  </h3>
                <p>敬信药草园运用互联网技术与创新，致力于为医药行业服务，为患者、医生、医院、科室、学会、医药企业提供针对性的数字化解决方案。</p>
                <div class="v2-about-links-list">
                    <ul>
                        <li>
                            <a href="https://www.thomsonreuters.cn/zh.html" target="_blank">
                                <img src="${ctxStatic}/images/upload/_links_01.png" alt="">
                                <p>THOMSON REUTERS 汤森路透</p>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.fjxiehe.com/" target="_blank">
                                <img src="${ctxStatic}/images/upload/_links_02.png" alt="">
                                <p>福建医科大学附属协和医院</p>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.fzcrb.com/" target="_blank">
                                <img src="${ctxStatic}/images/upload/_links_03.png" alt="">
                                <p>福建医科大学孟超肝胆医院</p>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.bqejj.com/" target="_blank">
                                <img src="${ctxStatic}/images/upload/_links_04.png" alt="">
                                <p>白求恩基金管理委员会</p>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.syphu.edu.cn/" target="_blank">
                                <img src="${ctxStatic}/images/upload/_links_05.png" alt="">
                                <p>沈阳药科大学</p>
                            </a>
                        </li>
                        <li class="last">
                            <a href="http://www.bjsn.org/" target="_blank">
                                <img src="${ctxStatic}/images/upload/_links_06.png" alt="">
                                <p>北京神经科学学会</p>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.gyfyy.com/" target="_blank">
                                <img src="${ctxStatic}/images/upload/_links_07.png" alt="">
                                <p>广州医科大学附属第一医院</p>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.gyey.com/cn/index.aspx" target="_blank">
                                <img src="${ctxStatic}/images/upload/_links_12.png" alt="">
                                <p>广州医科大学附属第二医院</p>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.sdzydfy.com/web/index.asp" target="_blank">
                                <img src="${ctxStatic}/images/upload/_links_08.png" alt="">
                                <p>山东省中医院</p>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.aqslyy.com.cn/" target="_blank">
                                <img src="${ctxStatic}/images/upload/_links_09.png" alt="">
                                <p>安庆市立医院</p>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.ptws.gov.cn/" target="_blank">
                                <img src="${ctxStatic}/images/upload/_links_10.png" alt="">
                                <p>莆田市卫计委</p>
                            </a>
                        </li>
                        <li class="last">
                            <a href="http://www.wangao.com.cn/" target="_blank">
                                <img src="${ctxStatic}/images/upload/_links_11.png" alt="">
                                <p>万高药业</p>
                            </a>
                        </li>
                    </ul>

                </div>
            </div>
        </div>
        <div  id="about-maps-04"  style="padding-top: 20px;">&nbsp;</div>
        <style>
            .BMap_Marker { background:url(${ctxStatic}/images/v2/icon-maps.png) no-repeat !important; }
        </style>
        <div class="v2-full-area v2-about-maps">
            <div class="page-width clearfix">
                <h3 class="title">联系我们/Contact Us</h3>
                <div class="formrow">
                    <label for="" class="formTitle">我在这里</label>
                    <div class="formControls">
                        <p><img src="${ctxStatic}/images/v2/icon-location.png" alt="" style="margin:0 20px 0 10px;">总部地址：广州市天河区珠江新城兴民路222号天盈广场西塔2603</p>
                        <p><img src="${ctxStatic}/images/v2/icon-email.png" alt="" style="top:0; margin:0 20px 0 10px;">邮箱：<a href="mailto:service@medcn.cn">service@medcn.cn</a>
                        </p>
                    </div>
                </div>
            </div>
            <!--百度地图容器-->
            <div id="allmap" style="width:100%;height:250px;">
                &nbsp;</div>

        </div>

    </div>

    <%@include file="/WEB-INF/include/footer.jsp"%>
</div>

</body>
</html>

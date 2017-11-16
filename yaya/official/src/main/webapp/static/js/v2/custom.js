function goTop(){
	$(window).scroll(function(e) {
		if($(window).scrollTop()>100)
			$(".gotop,.v2-help-button").fadeIn(350).css("display","block");
		else
			$(".gotop,.v2-help-button").fadeOut(350).css("display","none");
	});
		
	$(".gotop").click(function(e) {
		$('body,html').animate({scrollTop:0},500);
		return false;
	});		
};

/*打印测试*/
function preview() {
	var bdHtml,sprnStr,eprnStr,prnHtml;
	//设置打印内容高度
	$(".v2-news-detail-item").css({'width':'1040'});
	bdHtml=window.document.body.innerHTML; //获取网页内容

	sprnStr="<!--startprint-->"; //设置打印内容开始区
	eprnStr="<!--endprint-->"; //设置打印内容结束区
	prnHtml=bdHtml.substr(bdHtml.indexOf(sprnStr)+17); //+17是长度的意思，也就是说，以<!--startprint-->字符串后才是内容打印区
	prnHtml=prnHtml.substring(0,prnHtml.indexOf(eprnStr));
	window.document.body.innerHTML=prnHtml;
	window.print();
	window.document.body.innerHTML=bdHtml;
	$(".v2-news-detail-item").css({'width':''});
}


$(document).ready(function() {
	
	
	//返回顶部
	goTop();



	
	// 导航菜单
	$('.main-nav ul.sf-menu').superfish({ 
		delay:       500,
		animation:   {opacity:'fast',height:'show'},
		speed:       'fast',
		autoArrows:  true,
		dropShadows: false
	});
	$('.main-nav ul.sf-menu > li').last().addClass('last').end().hover(function(){ $(this).addClass('nav-hover'); },function(){ $(this).removeClass('nav-hover'); });


	//预览、转载点击
	$(document).mousedown(function(e){
		if($(e.target).parents(".dis-tbcell").length==0 ){
			$(".fx-mask-box-1,.fx-mask-box-2,.fx-mask-box-3,.fx-mask-box-4").addClass("hide");
			$('.mask-wrap, .mask-wrap-2').removeClass('dis-table');
		}
	});


	$('.fx-btn-1,.fx-btn-2,.fx-btn-3,.fx-btn-4, .reback-btn').on('click', function(e){

		$('.mask-wrap, .mask-wrap-2').addClass('dis-table');
		$('.sb-que-box').addClass("show").removeClass("hide");
	});
	var fxMaskBox = $(".distb-box");
	$('.fx-btn-1').on('click', function(){
		$('.fx-mask-box-1').addClass("show").removeClass("hide");
		if($(this).parents($(fxMaskBox)).hasClass("show")){
			$('.fx-mask-box-2,.fx-mask-box-3,.fx-mask-box-4').addClass("hide").removeClass("show");
		};
	});
	$('.fx-btn-2').on('click', function(){
		$('.fx-mask-box-2').addClass("show").removeClass("hide");
		if($(this).parents($(fxMaskBox)).hasClass("show")){
			$('.fx-mask-box-1,.fx-mask-box-3,.fx-mask-box-4').addClass("hide").removeClass("show");
		};
	});
	$('.fx-btn-3').on('click', function(){
		$('.fx-mask-box-3').addClass("show").removeClass("hide");
		if($(this).parents($(fxMaskBox)).hasClass("show")){
			$('.fx-mask-box-1,.fx-mask-box-2,.fx-mask-box-4').addClass("hide").removeClass("show");
		};
	});
	$('.fx-btn-4').on('click', function(){
		$('.fx-mask-box-4').addClass("show").removeClass("hide");
		if($(this).parents($(fxMaskBox)).hasClass("show")){
			$('.fx-mask-box-1,.fx-mask-box-2,.fx-mask-box-3').addClass("hide").removeClass("show");
		};
	});
	$('.close-button-fx, .close-btn-fx').on('click', function(){
		$('.fx-mask-box-1,.fx-mask-box-2,.fx-mask-box-3,.fx-mask-box-4').addClass("hide").removeClass("show");
		$('.mask-wrap, .mask-wrap-2').removeClass('dis-table');
	});

	//tab
	if($(".tabs-nav").length){
		$(".tabs-nav").tabs(".v2-helpPage-tabs > div");
		$(".tabs-nav ").find("a").click(function(){
			$(this).parents(".sf-menu").find(".currentTitle").text($(this).find("span").text());
			$(this).parents(".tabs-nav").css({"display":"none"});
		});

	}

	$(".chk_1").click(function() {

		if($("[name='checkbox']").attr("checked","true")){
			$(this).next().find(".ico").addClass("checkboxCurrent");
		} else {
			$(this).next().find(".ico").removeClass("checkboxCurrent");
		};//全选
	});





});

$(window).load(function() {



})



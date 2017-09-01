$(function(){

// 导航自适配处理
// function resize(){
// 	if($(window).height()>850) {
// 		$('.aside').removeClass('p-re-1');
// 		$('.aside').removeClass('p-re-2');
// 	}else if(700<$(window).height()<850) {
// 		$('.aside').addClass('p-re-1');
// 		$('.aside').removeClass('p-re-2');
// 	}
//
// 	if($(window).height()<700) {
// 		$('.aside').addClass('p-re-2');
// 		$('.aside').removeClass('p-re-1');
// 	}
// }

// $(window).resize(function(){
// 	resize();
// }).resize();

$('.gz-list-tb a').on('click', function(){
	$(this).addClass('cur').siblings().removeClass('cur');
});

//
$('.yz-btn-link').on('click', function(){
	$('.mask-wrap').show();
});
$('.close-btn, .close-button').on('click', function(){
	$('.mask-wrap').hide();
});

$('.xiugai-button').on('click', function(){
	$('.mask-wrap').addClass('dis-table');
});
$('.submit-button, .cancel-button').on('click', function(){
	$('.mask-wrap').removeClass('dis-table');
});

// 下拉select
$('.doc-list li, .select-mode-1').each(function(){
	var $this=$(this);
	$this.find('.hd-txt').on('click', function(){
		if($(this).hasClass('off')) {
			$this.find('dl').hide();
			$(this).removeClass('off');
		}else {
			$this.find('dl').show();
			$this.siblings().find('dl').hide();
			$this.siblings().find('.hd-txt').removeClass('off');
			$(this).addClass('off');
		}
	});
	$this.find('dd').on({
		mouseenter: function() {
			$(this).addClass('dd-hover');
		},
		mouseleave: function() {
			$(this).removeClass('dd-hover');
		},
		click: function() {
			$this.find('.select-val').html($(this).html());
			$this.find('.select-val').attr('val',$(this).attr('val'));
			$this.find('dl').hide();
			$this.find('.hd-txt').removeClass('off');
		}
	});

	// 点击区域外关闭层
	$(document).on('click', function(e){
		if($(e.target).parents('.doc-list li, .select-mode-1').length == 0){
			$this.find('dl').hide();
			$this.find('.hd-txt').removeClass('off');
		}
	});
});

// 新建分组
$('.manage-link').on('click', function(){
	if($(this).hasClass('off')) {
		$('.xj-show-box').hide();
		$(this).removeClass('off');
	}else {
		$('.xj-show-box').show();
		$(this).addClass('off');
	}
});
$('.xj-button').on('click', function(){
	$('.xj-show-box').hide();
	$('.manage-link').removeClass('off');
});

// 鼠标经过效果
$('.tab-con-wrap').eq(0).show();
$('.tab-menu').mouseover(function(){
	var index=$(this).index();
	$(this).addClass('tab-cur').siblings().removeClass('tab-cur');
	$('.tab-con-wrap').eq(index).show().siblings('.tab-con-wrap').hide();
});


// banner 切换
if($('.slider-wrap').length>0) {
	$('.slider-wrap').flexslider({
	    animation: 'slide',      // 切换方式
	    autoPlay: true,          // 自动播放设置
	    slideshowSpeed: 5000,    // 图片切换间隔时间
	    animationDuration: 600,  // 图片切换速度
	    directionNav: true,      // 是否显示左右切换按钮
	    controlNav: true,        // 是否显示下标小圆点
	    prevText: '&lt;',        //左键按钮文字设置
	    nextText: "&gt;",        //右键按钮文字设置
	});
}

// 预览、转载点击
$('.fx-btn-1,.fx-btn-2,.fx-btn-3,.fx-btn-4, .reback-btn').on('click', function(){
	$('.mask-wrap, .mask-wrap-2').addClass('dis-table');
	$('.sb-que-box').show();
});
$('.fx-btn-1').on('click', function(){
	$('.fx-mask-box-1').show();
});
$('.fx-btn-2').on('click', function(){
	$('.fx-mask-box-2').show();
});
$('.fx-btn-3').on('click', function(){
	$('.fx-mask-box-3').show();
});
$('.fx-btn-4').on('click', function(){
	$('.fx-mask-box-4').show();
});
$('.close-button-fx, .close-btn-fx').on('click', function(){
	$('.fx-mask-box-1,.fx-mask-box-2,.fx-mask-box-3,.fx-mask-box-4').hide();
	$('.mask-wrap, .mask-wrap-2').removeClass('dis-table');
});

// 日历
var rander_btn = $('#rander-input');
if(rander_btn.length>0){
	rander_btn.daterangepicker({
		timePicker: true,
		timePickerIncrement: 30,
		format: 'YYYY-MM-DD h:mm A'
		}, function(start, end, label) {
		console.log(start.toISOString(), end.toISOString(), label);
	});
}

// 日历2
var rander_btn1 = $('#rander-input-1');
$('.rander-time').click(function(){
	rander_btn1.click();
});

if(rander_btn1.length>0){
	rander_btn1.daterangepicker({
		timePicker: true,
		timePickerIncrement: 30,
		format: 'YYYY-MM-DD h:mm A'
		}, function(start, end, label) {
		console.log(start.toISOString(), end.toISOString(), label);
	});
}

// 选择日期
$('.data-time a').click(function(){
	$(this).addClass('t-cur').siblings().removeClass('t-cur');
});


$('.open-btn').on('click', function(){
	if( $(this).hasClass('off') ){
		$(this).removeClass('off').html('已开启');
	}else {
		$(this).addClass('off').html('已关闭');
	}
});

// 点击选择会议地址
$('.input-wbox').each(function(){
	var $this=$(this);
	var Input = $this.find('.input-radiobox');
	var conbox =$this.find('.input-conbox');
	Input.on('click', function(){
		var m =$(this).attr('data-sx');
		conbox.eq(m).show().siblings('.input-conbox').hide();
		if($(this).attr('data-sx')=='mo') {
			conbox.hide();
		}

	});
});

$('.fl-img').on('click', function(){
	$(this).addClass('fl-img-click');
});

//
$('.doc-box-show li').each(function(){
	var $this=$(this);
	$this.find('.cover-show').on('mouseover', function(){
		$this.find('.show-box').show();
		$this.find('select').change();
	}).on('mouseleave', function(){
		$this.find('.show-box').hide();
		// $this.find('select').show();
	});
});

// 选择日历
if($('#start').length>0) {
	var start = {
	  elem: '#start',
	  format: 'YYYY-MM-DD hh:mm:ss',
	  min: laydate.now(), //设定最小日期为当前日期
	  max: '2099-06-16 23:59:59', //最大日期
	  istime: true,
	  istoday: false,
	  choose: function(datas){
	     end.min = datas; //开始日选好后，重置结束日的最小日期
	     end.start = datas //将结束日的初始值设定为开始日
	  }
	};
	laydate(start);
}
if($('#end').length>0) {
	var end = {
	  elem: '#end',
	  format: 'YYYY-MM-DD hh:mm:ss',
	  min: laydate.now(),
	  max: '2099-06-16 23:59:59',
	  istime: true,
	  istoday: false,
	  choose: function(datas){
	    start.max = datas; //结束日选好后，重置开始日的最大日期
	  }
	};
	laydate(end);
}

$('#confirm-btn, #confirm-reset').on('click', function(){

	// 发布会议提交
	var content = $('#content').val();
	var fenlei = $('#fenlei').attr('val');
	var start = $('#start').val();
	var end = $('#end').val();
	var textarea = $('#textarea').val();
	var jiabin = $('#jiabin').val();
	var renyuan = $('#renyuan').val();
	var prov = $('#prov').val();
	var city = $('#city').val();
	var keshi = $('#keshi').val();

	if(!$.trim(content)){
		$('.nrq-box .erro-info').show().html('内容不能为空');
		return false;
	}else {
		$('.nrq-box .erro-info').hide().html('');
	}

	if(!$.trim(fenlei)){
		$('.nrq-box .erro-info').show().html('请选择分类');
		return false;
	}else {
		$('.nrq-box .erro-info').hide().html('');
	}

	if(!$.trim(start) || !$.trim(end)){
		$('.time-info').show().html('请选择会议时间');
		return false;
	}else {
		$('.time-info').hide().html('');
	}

	if(!$.trim(textarea)){
		$('.content-info').show().html('请填写会议介绍');
		return false;
	}else {
		$('.content-info').hide().html('');
	}

	if(!$.trim(jiabin)){
		$('.jiabin-info').show().html('请输入嘉宾');
		return false;
	}else {
		$('.jiabin-info').hide().html('');
	}

	// if(!$.trim(renyuan)){
	// 	alert('请选择参与人员');
	// 	return false;
	// }

	if(!$.trim(prov)){
		$('.city-info').show().html('请选择省份');
		return false;
	}else {
		$('.city-info').hide().html('');
	}

	if(!$.trim(city)){
		$('.city-info').show().html('请选择城市');
		return false;
	}else {
		$('.city-info').hide().html('');
	}

	if(!$.trim(keshi)){
		$('.city-info').show().html('请选择科室');
		return false;
	}else {
		$('.city-info').hide().html('');
	}

});

// 点击分组
var fenzuShow = $('.fenzu-show');
$('#fenzu').on('click', function(){
	if($(this).hasClass('off')) {
		fenzuShow.hide();
		$(this).removeClass('off');
	}else {
		fenzuShow.show();
		$(this).addClass('off');
	}
});
$('.fz-button-box button').on('click', function(){
    fenzuShow.hide();
    $('#fenzu').removeClass('off');
});

// 像素管理页面tab切换
$('.tab-xs-menu span').on('click', function(){
    $(this).addClass('cur').siblings().removeClass('cur');
});

//修改状态出现滚动条
var leftMenuHeight = $(".aside").height();
var rightMainHeightTotal = $(".tb-contacts-main").height() + $(".tab-hd").height() + $(".g-main > header").height();
if(leftMenuHeight || rightMainHeightTotal) {
	if(leftMenuHeight < rightMainHeightTotal){
		$(".max-height").css("position","static");
	}
}

//选框样式改版
$(".chk-hook").click(function() {

	if($("[name='checkbox']").attr("checked","true")){
		$(this).next().find(".ico").addClass("checkboxCurrent");
	} else {
		$(this).next().find(".ico").removeClass("checkboxCurrent");
	};//全选
});

    //console.time("start");
//自定义下拉
var formSelect = $(".formPage-select-hook");
formSelect.each(function(){
    if($(this).prop("disabled")){
        $(this).parent().addClass("formPage-select-disabled");
    } else {
        $(this).parent().removeClass("formPage-select-disabled");
        $(this).focus(function(){
            $(this).parent().addClass("formPage-select-cur");
        }).blur(function(){
            $(this).parent().removeClass("formPage-select-cur");
        })
    };
});
    //console.timeEnd("end");


});
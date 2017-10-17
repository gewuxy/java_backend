
/*
    控制密码框选中处理
* */
$(function(){
    $('ul.sf-menu').superfish({
        delay:       500,
        animation:   {opacity:'fast',height:'show'},
        speed:       'fast',
        autoArrows:  true,
        dropShadows: false
    });
    $('ul.sf-menu > li').last().addClass('last').end().hover(function(){ $(this).addClass('nav-hover'); },function(){ $(this).removeClass('nav-hover'); });
})


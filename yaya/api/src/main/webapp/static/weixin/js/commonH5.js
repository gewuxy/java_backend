/**
 * Created by xingjie on 2017/7/19.
 */

/*
    控制密码框选中处理
* */
var changePassWordStatus =  function( passwordInputDOM ){
    //需要是密码框调用
    var _condition = passwordInputDOM.val() != '' && passwordInputDOM.attr('type') == 'password';
    var changeBtn = passwordInputDOM.siblings('.icon-pwdChange');
    var textInput = passwordInputDOM.siblings(':text');
    if(changeBtn.hasClass('pwdChange-on') && _condition ) {
        textInput.removeClass('none').val(passwordInputDOM.val());
        passwordInputDOM.addClass('none');
        changeBtn.removeClass('pwdChange-on').addClass('pwdChange-off');
    } else if(changeBtn.hasClass('pwdChange-off') && _condition ){
        passwordInputDOM.removeClass('none').val(textInput.val());
        textInput.addClass('none');
        changeBtn.removeClass('pwdChange-off').addClass('pwdChange-on');
    } else {
        console.log('调用失败');
    }
};

/*
*   搜索框选中处理
* */

var searchStatus = function(selector){
    var searchBar = selector;
    var searchInput = searchBar.find('.serach-bar-input');
    var searchClear = searchBar.find('.search-clear-button');

    function canselSearch() {
        searchInput.val('');
        searchBar.removeClass('search-bar-focusing');
    }

    searchInput.on('click', function() {
        searchBar.addClass('search-bar-focusing');
    });

    searchInput.on('blur', function() {
        if (!this.value.length) canselSearch();
    })

    searchClear.on('click', function() {
        searchInput.val('');
        searchInput.focus();
    });

}

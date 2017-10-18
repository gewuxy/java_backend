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


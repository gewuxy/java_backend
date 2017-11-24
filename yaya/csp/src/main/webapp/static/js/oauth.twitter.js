/**
 * Created by Liuchangling on 2017/11/9.
 */

var fbToken;
window.fbAsyncInit = function() {
    FB.init({
        appId :'263615967498316',
        xfbml : true,
        version : 'v2.6' //facebook登录版本
    });
};
//异步引入Facebook sdk.js
(function(d, s, id){
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));



window.twttr = (function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0],
        t = window.twttr || {};
    if (d.getElementById(id)) return t;
    js = d.createElement(s);
    js.id = id;
    js.src = "https://platform.twitter.com/widgets.js";
    fjs.parentNode.insertBefore(js, fjs);

    t._e = [];
    t.ready = function (f) {
        t._e.push(f);
    };

    return t;
}(document, "script", "twitter-wjs"));


var log = console.log;
hello.init(
    {'twitter': 'hqhT16vzm2ZJxJary0WOULGfv'},//公司运营的twitter账号的key
    {oauth_proxy: 'https://auth-server.herokuapp.com/proxy'}
);


function twitterLogin(){
    //登录方法，并将twitter 作为参数传入
    // Twitter instance
    var twitter = hello("twitter");
    // Login
    twitter.login().then(function (r) {
        // Get Profile
        return twitter.api('/me');
    }, log).then(function (p) {
        console.log("Connected to twitter"+" as " + p.name);
        //因为得不到token，但是这步已经得到用户所有信息，所以将用户信息转成JSON字符串给后台
        var res = JSON.stringify(p);
        twitterFormSubmit(JSON.parse(res));

    }, log);
}




function facebookLogin(){
    FB.login(function(response) {
        if (response.status === 'connected') {  //登陆状态已连接
            fbToken = response.authResponse.accessToken;
            console.log(fbToken);
            getUserInfo();
        } else if (response.status === 'not_authorized') { //未经授权
            console.log('facebook未经授权');
        } else {
            console.log('不是登陆到Facebook;不知道是否授权');
        }
    });

}

//获取用户信息
function getUserInfo() {
    FB.api('/me?fields=id,name,birthday,gender,hometown,email,devices,picture', function(response) {
        console.log('Successful login for: ' + response);
        facebookFormSubmit(response);
    });
}

/**
 * Created by Liuchangling on 2017/11/9.
 */
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
    {'twitter': 'jAY5jjYsPqvUpULMGCB4ryCdL'},
    {oauth_proxy: 'https://auth-server.herokuapp.com/proxy'}
);


$("#twitter").click(function () {
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
        $("#str").val(res);
        $("#twitterForm").submit();

        // self.location= '${ctx}/mgr/twitterCallback?str='+res;

    }, log);

});

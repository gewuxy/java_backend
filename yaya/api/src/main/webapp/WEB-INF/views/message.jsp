<%--
  Created by IntelliJ IDEA.
  User: lixuan
  Date: 2017/4/28
  Time: 15:30
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title>ws</title>
    <script type="text/javascript" src="http://www.medcn.com/static/js/v2/jquery.min.js"></script>
    <script type="text/javascript">
        var heartbeat_timer = 0;
        var last_health = -1;
        var health_timeout = 3000;

        var uid = '${uid}';

        $(function(){
            //ws = ws_conn( "ws://211.100.41.186:9999" );
            ws = ws_conn("ws://127.0.0.1:8082/api/im?meetId=${meetId}&token=${token}");

            $("#send_btn").click(function(){
                sendMsg();
            });

            $("#mysendbox").keyup(function(event){
                if(event.keyCode == '13'){
                    sendMsg();
                }
            });

            function sendMsg(){
                var content = $("#mysendbox").val();
                var msg = {'senderId':'${uid}','message':content,'msgType':0};

                ws.send(JSON.stringify(msg));
                $("#mysendbox").val("");
            }
        });


        function keepalive( ws ){
            var time = new Date();
            if( last_health != -1 && ( time.getTime() - last_health > health_timeout ) ){
                //此时即可以认为连接断开，可是设置重连或者关闭
                $("#keeplive_box").html( "服务器没有响应." ).css({"color":"red"});
                //ws.close();
            }
            else{
                $("#keeplive_box").html( "连接正常" ).css({"color":"green"});
            }
        }

        //websocket function
        function ws_conn( to_url ){
            to_url = to_url || "";
            if( to_url == "" ){
                return false;
            }

            clearInterval( heartbeat_timer );
            $("#statustxt").html("Connecting...");
            var ws = new WebSocket( to_url );
            ws.onopen=function(){
                $("#statustxt").html("connected.");
                $("#send_btn").attr("disabled", false);
                heartbeat_timer = setInterval( function(){keepalive(ws)}, 5000 );
            }
            ws.onerror=function(){
                $("#statustxt").html("error.");
                $("#send_btn").attr("disabled", true);
                clearInterval( heartbeat_timer );
                $("#keeplive_box").html( "连接出错." ).css({"color":"red"});
            }
            ws.onclose=function(){
                $("#statustxt").html("closed.");
                $("#send_btn").attr("disabled", true);
                clearInterval( heartbeat_timer );
                $("#keeplive_box").html( "连接已关闭." ).css({"color":"red"});
            }

            ws.onmessage=function(msg){
                console.log("received msg="+msg);
                var time = new Date();
                var data = JSON.parse(msg.data);
                $("#chatbox").val( $("#chatbox").val() +data.sender+": "+ data.message + "\n" );
                $("#chatbox").attr("scrollTop",$("#chatbox").attr("scrollHeight"));
            }

            return ws;
        }
    </script>
</head>

<body>


<p>连接状态:&nbsp;&nbsp;<span id="statustxt">连接中...</span></p>
<p>心跳状态:&nbsp;&nbsp;<span id="keeplive_box">检测中...</span></p>

<p>
    <textarea name="chatbox" id="chatbox" cols="55" rows="20" readonly="readonly"></textarea>
</p>
<p>
<input name="mysendbox" type="text" id="mysendbox" size="50" />
&nbsp;
<input type="button" name="send_btn" id="send_btn" value="发送" disabled="disabled" />
<input type="button" onclick="javascript:ws.close()" value="断开"/>
</p>
</body>
</html>

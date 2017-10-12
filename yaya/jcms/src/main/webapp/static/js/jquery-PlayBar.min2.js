
// JavaScript Document
(function($, window){

    $.fn.playBar = function(option){
        //默认参数
        var defaultSetting = {
            allTime:0,
            isAction:true,
            width:0,
            thewidth:0,
            CurrTime:0,//记录当前进度条表示时间
            addHour:0,addMinute:0,addSecond:0,TheHour:0,TheMinute:0,TheSecond:0,
            flag:0,//标志位
            // alltime:0,//总时间毫秒
            addwidth:0,//每次增加的长度
            offsetW:0,//偏移量
            times:0,
            rwidth:0,
            down:false,
            BarMove:false,//正在移动
            lastX:0,NewX:0,//记录前后位置
            t:[]
        };

        //插件配置
        var setting = $.extend(defaultSetting,option);


        var _oSelf = this,
            $this = $(this);


        var t = setting.t;
        var isAction=setting.isAction;
        var width=setting.width;
        var thewidth=setting.thewidth;
        var CurrTime=setting.CurrTime;//记录当前进度条表示时间
        var addHour=setting.addHour,
            addMinute=setting.addMinute,
            addSecond=setting.addSecond,
            TheHour=setting.TheHour,
            TheMinute=setting.TheMinute,
            TheSecond=setting.TheSecond;
        var flag=setting.flag;//标志位
        // var alltime=0;//总时间毫秒
        var addwidth=setting.addwidth;//每次增加的长度
        var offsetW=setting.offsetW;//偏移量
        var times=setting.times;
        var rwidth=setting.rwidth;

        var down=setting.down;
        var BarMove=setting.BarMove;//正在移动
        var lastX=setting.lastX,NewX=setting.lastX;//记录前后位置
        var _thisTime;
        var radioStopTime;



        //初始化函数
        var _init = function() {
            CleanAll();
            var alltime=setting.allTime;
            $this.empty();
            $this.append("<div class='BarControl'><div class='BarBeginTime'>00:00</div></div>");
            $this.find(".BarControl").append('<div class="TheBar"><div class="TimeBall"></div><div class="TheColorBar"></div></div><div class="BarFinishTime">10:35</div>');
            width=$('.TheBar').width();
            times=alltime/1000;
            rwidth=width-8;
            addwidth=(width-10)/times;
            $this.attr("data-time",alltime)
            _thisTime = $this.attr("data-time");
            var t=TransitionTime(alltime);
            $this.find('.BarFinishTime').html(t.StringTime);
            OpenBar(alltime)



            return false;

        }



        //内部使用参数
        var _oEventAlias = {

        };

        // 提供外部函数
            //停止
        this.stop =function(){
            StopBar();
        };
            //播放
        this.begin =function(_time){
            OpenBar(_time);
        };
            //播放时间归零
        this.emptyTime = function(){
            CleanAll();
            _oSelf.find('.TheColorBar').css("width", 0);
            _oSelf.find('.TimeBall').css("left", 0);
        };
            //输出暂停时间
        this.radioStop = function(){
            radioStopTime = _thisTime/1000;
            return radioStopTime;
        };
            //拖动开启
        this.mouseStart = function(){
                //鼠标按下
            $this.find(".TimeBall").on("mousedown",function(event){
                lastX=event.clientX;
                event.preventDefault();
                down=true;
                BarMove=true;
                if(isAction){
                    StopBar();//停止滑动

                }
            });
            $this.mousemove(function(event){
                event.preventDefault();
                NewX=event.clientX;
                if(BarMove){
                    //console.log(changeM);
                    var mcs=NewX-lastX;
                    lastX=NewX;
                    //console.log(mcs+" "+lastX+" "+NewX);
                    if(mcs<0){
                        if(thewidth-(-mcs)>0){
                            thewidth=thewidth-(-mcs);
                        }
                    }else{
                        if(thewidth+mcs<rwidth){
                            thewidth=thewidth+mcs;
                        }else{
                            thewidth=rwidth;
                        }
                    }
                    //console.log(changeM+" "+mcs);
                    timechange();
                    $this.find('.TheColorBar').css("width",thewidth+1);
                    $this.find('.TimeBall').css("left",thewidth);
                    //down=false;
                }
            });
            //文档上鼠标拖动
            $this.mouseup(function(){
                if(BarMove){
                    BarMove=false;
                    down=false;
                    NewX=0;
                    var xo=parseInt(CurrTime/1000);
                    offsetW=thewidth-xo*addwidth;
                    //console.log(thewidth+" "+rwidth+" "+addwidth+" "+offsetW);
                    //console.log(thewidth+addwidth-offsetW+" "+parseInt(CurrTime/1000)*addwidth);
                    if(isAction){
                        OpenBar(_thisTime);//重新开始计时
                    }
                }
            });
        };
            //摧毁





        //私有函数


        //清空值
        function CleanAll(){
            isAction=true;
            thewidth=0;
            CurrTime=0;
            addHour=0;addMinute=0;addSecond=0;TheHour=0;TheMinute=0;TheSecond=0;offsetW=0;thewidth=0;
            flag=0;
        }

        // //文档上鼠标抬起
        function timechange(){
            CurrTime=parseInt(thewidth/rwidth*_thisTime);
            var ltx=TransitionTime(CurrTime);
            if(TheHour>0){
                if(ltx.hHour){
                    _oSelf.find('.BarBeginTime').html(ltx.StringTime);
                }else{
                    _oSelf.find('.BarBeginTime').html("00:"+ltx.StringTime);
                }
            }else{
                _oSelf.find('.BarBeginTime').html(ltx.StringTime);
            }
            addSecond=ltx.Tsec;
            addMinute=ltx.Tmin;
            addHour=ltx.Thour;
        }
        //时间拖动时改变时间
        function changeBar(_time){
            var alltime = _time;

            var second,minute,hour;
            thewidth=thewidth*1+addwidth-offsetW;
            if(offsetW>0){
                offsetW=0;
            }
            if(thewidth<rwidth&&CurrTime<alltime){
                CurrTime=CurrTime+1*1000;//
                addSecond=addSecond+1;
                if(addSecond>59){
                    addSecond=0;
                    addMinute=addMinute+1;
                    if(addMinute>59){
                        addMinute=0;
                        addHour=addHour+1;
                    }
                }//时间累加判断
                if(addSecond>9){
                    second=""+addSecond;
                }else{
                    second="0"+addSecond;
                }
                if(addMinute>9){
                    minute=""+addMinute;
                }else{
                    minute="0"+addMinute;
                }
                if(addHour>9){
                    hour=""+addHour;
                }else{
                    hour="0"+addHour;
                }
                if(addHour>0){
                    flag=1;
                }
                //
                if(flag==0){
                    _oSelf.find('.BarBeginTime').html(minute+":"+second);
                }else{
                    _oSelf.find('.BarBeginTime').html(hour+":"+minute+":"+second);
                }//
            }else{
                //console.log(thewidth+" "+rwidth);
                thewidth=rwidth;
                StopBar();
            }
            _oSelf.find('.TheColorBar').css("width",thewidth+1);
            _oSelf.find('.TimeBall').css("left",thewidth);
        }
        //改变进度条
        function TransitionTime(str){
            var m=parseFloat(str)/1000;
            var time="";
            var second,minute,hour;
            var haveHour=false;
            var ch=0,csx=0,cm=0;
            if(m>=60&&m<60*60){//分钟
                if(parseInt(m/60.0)<10){
                    minute="0"+parseInt(m/60.0);
                }else{
                    minute=parseInt(m/60.0);
                }
                var cs=parseInt(m-parseInt(m/60.0)*60);
                if(cs<10){
                    second="0"+cs;
                }else{
                    second=""+cs;
                }
                TheMinute=parseInt(m/60.0);
                TheSecond=cs;
                cm=TheMinute;
                TheHour=0;
                csx=cs;
                time=minute+":"+second;
                _oSelf.find('.BarBeginTime').html("00:00");
            }else if(m>=60*60){//到达小时
                flag=1;
                haveHour=true;
                ch=parseInt(m/3600.0);
                cm=parseInt((parseFloat(m /3600.0) -  parseInt(m/3600.0)) *60);
                csx=parseInt((parseFloat((parseFloat(m/3600.0) - parseInt(m/3600.0)) *60) - parseInt((parseFloat(m /3600.0) - parseInt(m/3600.0)) *60)) *60);
                if(ch<10){
                    hour="0"+ch;
                }else{
                    hour=""+ch;
                }
                if(cm<10){
                    minute="0"+cm;
                }else{
                    minute=""+cm;
                }
                if(csx<10){
                    second="0"+csx;
                }else{
                    second=""+csx;
                }
                TheHour=ch;
                TheMinute=cm;
                TheSecond=csx;
                time=hour+":"+minute+":"+second;
                _oSelf.find('.BarBeginTime').html("00:00:00");
            }else{//秒
                _oSelf.find('.BarBeginTime').html("00:00");
                csx=parseInt(m);
                if(parseInt(m)>9){
                    second=""+parseInt(m);
                }else{
                    second="0"+parseInt(m);
                }
                TheMinute=0;
                TheSecond=parseInt(m);
                TheHour=0;
                time="00:"+second;
            }//
            var tt={hHour:haveHour,Thour:ch,Tmin:cm,Tsec:csx,StringTime:time};
            return tt;
            //$('.FinishTime').html(time);
        }

        //毫秒转换成分钟小时格式
        function StopBar(){
            if(!down){
                isAction=false;
            }
            clearInterval(t);
        }

        //进度停止
        function OpenBar(_time){
            isAction=true;
            t = window.setInterval(function()
            {
                changeBar(_time);
            }, 1000);
        }




        //启动函数
        _init()


        //启动调用
        return this;





    }
})(jQuery,window);
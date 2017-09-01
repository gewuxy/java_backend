/**
 * Created by xingjie on 2017/2/24.
 */

$(function(){


    $(".btnAdd-metting-faq").click(function(){
        var addContent = $(".metting-addContent").html();
        $(".add-metting-faq-content").show().append(addContent);

        //标题数
        faqTitleNum();

        //类型选择
        $(".xxk_title li").click(function(){
            var xxkjs = $(this).index();
            var defaultLi = $(this).parents(".movie_box").find(".wjdc_list").children("li").eq(1);
            $(this).addClass("on").siblings().removeClass("on");
            $(".xxk_conn").children(".xxk_xzqh_box").eq(xxkjs).show().siblings().hide();
            if(xxkjs == 1){
                defaultLi.html('<label><input name="" type="checkbox" value=""><span>多选选项</span></label>');
            } else if(xxkjs == 2){
                defaultLi.html('<textarea name=""  cols="" rows="" class="input_wenbk btwen_text btwen_text_tk" ></textarea>')
            } else {
                defaultLi.html('<label><input name="" type="radio" value=""><span>选项内容</span></label>');
            }
        });

        //绑定编辑事件
        $(document).off("mouseenter",".movie_box").on("mouseenter",".movie_box", faqOnItem);
        $(document).off("mouseleave",".movie_box").on("mouseleave",".movie_box", faqOutItem);

        $(this).hide();


    });

    function faqOnItem(){
        //鼠标移动添加编辑框
        var	html_cz = "<div class='kzqy_czbut'><a href='javascript:void(0)'  class='bianji color-blue-up'>编辑类型</a><a href='javascript:void(0)' class='del' >删除</a></div>"
        $(this).css({"border":"1px solid #dbdbdb"});
        $(this).children(".wjdc_list").after(html_cz);

        //生成编辑框后绑定各种功能
        //删除
        $(document).off("click",".del").on("click",".del", faqDelete);
        //编辑
        $(document).off("click",".bianji").on("click",".bianji", faqEdit);
        //增加选项
        $(document).off("click",".zjxx").on("click",".zjxx", faqAddOption);
        //删除一行
        $(document).off("click",".del_xm").on("click",".del_xm", faqDeleteRow);
        //取消编辑
        $(document).off("click",".dx_box .qxbj_but,.xxk_conn .qxbj_but").on("click",".dx_box .qxbj_but,.xxk_conn .qxbj_but", faqNotSave);
        //完成编辑（编辑）
        $(document).off("click",".swcbj_but").on("click",".swcbj_but", faqEditSave);
    }

    function faqOutItem(){
        // 移开鼠标后编辑框消失
        $(this).css({"border":"1px solid #fff"});
        $(this).children(".kzqy_czbut").remove();
    }



    function faqTitleNum(){
        var tNum = $(".movie_box").find(".nmb");
        for(var i=0;i < tNum.length;i++){
            var j = i+1;
            tNum.eq(i).text(j);
        }
    }
    //下移
    function faqMoveDown(){
        //文字的长度
        var leng = $(".yd_box").children(".movie_box").length;
        var dqgs = $(this).parent(".kzqy_czbut").parent(".movie_box").index();

        if(dqgs < leng-1){
            var czxx = $(this).parent(".kzqy_czbut").parent(".movie_box");
            var xyghtml = czxx.next().html();
            var syghtml = czxx.html();
            czxx.next().html(syghtml);
            czxx.html(xyghtml);
            //序号
            czxx.children(".wjdc_list").find(".nmb").text(dqgs+1);
            czxx.next().children(".wjdc_list").find(".nmb").text(dqgs+2);
        }else{
            alert("到底了");
        }
    }
    //上移
    function faqMoveUp(){
        //文字的长度
        var leng = $(".yd_box").children(".movie_box").length;
        var dqgs = $(this).parent(".kzqy_czbut").parent(".movie_box").index();
        if(dqgs > 0){
            var czxx = $(this).parent(".kzqy_czbut").parent(".movie_box");
            var xyghtml = czxx.prev().html();
            var syghtml = czxx.html();
            czxx.prev().html(syghtml);
            czxx.html(xyghtml);
            //序号
            czxx.children(".wjdc_list").find(".nmb").text(dqgs+1);
            czxx.prev().children(".wjdc_list").find(".nmb").text(dqgs);

        }else{
            alert("到头了");
        }
    }
    //删除
    function faqDelete(){

        var czxx = $(this).parent(".kzqy_czbut").parent(".movie_box");
        var zgtitle_gs = czxx.parent(".yd_box").find(".movie_box").length;
        var xh_num = 0;
        //重新编号
        czxx.parent(".yd_box").find(".movie_box").each(function() {
            $(".yd_box").children(".movie_box").eq(xh_num).find(".nmb").text(xh_num);
            xh_num++;
            //alert(xh_num);
        });
        czxx.remove();

        faqTitleNum();
    }
    //编辑
    function faqEdit(){
        //编辑的时候禁止其他操作
        $(this).siblings().hide();
        //$(this).parent(".kzqy_czbut").parent(".movie_box").unbind("hover");
        var dxtm = $(".dxuan").html();
        var duoxtm = $(".duoxuan").html();
        var tktm = $(".tktm").html();
        var jztm = $(".jztm").html();
        //接受编辑内容的容器
        var dx_rq = $(this).parent(".kzqy_czbut").parent(".movie_box").find(".dx_box");
        var title = dx_rq.attr("data-t");
        //alert(title);
        //题目选项的个数
        var timlrxm = $(this).parent(".kzqy_czbut").parent(".movie_box").children(".wjdc_list").children("li").length;

        //单选题目
        if(title==0){
            dx_rq.show().html(dxtm);
            //模具题目选项的个数
            var bjxm_length = dx_rq.find(".title_itram").children(".kzjxx_iteam").length;
            var dxtxx_html = dx_rq.find(".title_itram").children(".kzjxx_iteam").html();
            //添加选项题目
            for (var i_tmxx = bjxm_length; i_tmxx < timlrxm-1 ; i_tmxx++) {
                dx_rq.find(".title_itram").append("<div class='kzjxx_iteam'>"+dxtxx_html+"</div>");
            }
            //赋值文本框
            //题目标题
            var texte_bt_val = $(this).parent(".kzqy_czbut").parent(".movie_box").find(".wjdc_list").children("li").eq(0).find(".tm_btitlt").children(".btwenzi").text();
            dx_rq.find(".btwen_text").val(texte_bt_val);
            //遍历题目项目的文字
            var  bjjs=0;
            $(this).parent(".kzqy_czbut").parent(".movie_box").find(".wjdc_list").children("li").each(function() {
                //可选框框
                var ktksfcz = $(this).find("input").hasClass("wenb_input");
                if(ktksfcz){
                    var jsxz_kk = $(this).index();
                    dx_rq.find(".title_itram").children(".kzjxx_iteam").eq(jsxz_kk-1).find("label").remove();
                }
                //题目选项
                var texte_val = $(this).find("span").text();
                dx_rq.find(".title_itram").children(".kzjxx_iteam").eq(bjjs-1).find(".input_wenbk").val(texte_val);
                bjjs++

            });
        }
        //多选题目
        if(title==1){
            dx_rq.show().html(duoxtm);
            //模具题目选项的个数
            var bjxm_length = dx_rq.find(".title_itram").children(".kzjxx_iteam").length;
            var dxtxx_html = dx_rq.find(".title_itram").children(".kzjxx_iteam").html();
            //添加选项题目
            for (var i_tmxx = bjxm_length; i_tmxx < timlrxm-1 ; i_tmxx++) {
                dx_rq.find(".title_itram").append("<div class='kzjxx_iteam'>"+dxtxx_html+"</div>");
                //alert(i_tmxx);
            }
            //赋值文本框
            //题目标题
            var texte_bt_val = $(this).parent(".kzqy_czbut").parent(".movie_box").find(".wjdc_list").children("li").eq(0).find(".tm_btitlt").children(".btwenzi").text();
            dx_rq.find(".btwen_text").val(texte_bt_val);
            //遍历题目项目的文字
            var  bjjs=0;
            $(this).parent(".kzqy_czbut").parent(".movie_box").find(".wjdc_list").children("li").each(function() {
                //可选框框
                var ktksfcz = $(this).find("input").hasClass("wenb_input");
                if(ktksfcz){
                    var jsxz_kk = $(this).index();
                    dx_rq.find(".title_itram").children(".kzjxx_iteam").eq(jsxz_kk-1).find("label").remove();
                }
                //题目选项
                var texte_val = $(this).find("span").text();
                dx_rq.find(".title_itram").children(".kzjxx_iteam").eq(bjjs-1).find(".input_wenbk").val(texte_val);
                bjjs++

            });
        }
        //填空题目
        if(title==2){
            dx_rq.show().html(tktm);
            //赋值文本框
            //题目标题
            var texte_bt_val = $(this).parent(".kzqy_czbut").parent(".movie_box").find(".wjdc_list").children("li").eq(0).find(".tm_btitlt").children(".btwenzi").text();
            dx_rq.find(".btwen_text").val(texte_bt_val);
            $(this).parents(".movie_box").find(".wjdc_list").append("<li></li>");
            $(this).parents(".movie_box").find(".wjdc_list").children("li").eq(1).html('<textarea name=""  cols="" rows="" class="input_wenbk btwen_text btwen_text_tk"></textarea>');

        }
        //矩阵题目
        if(title==3){
            dx_rq.show().html(jztm);
        }
    }
    //增加选项
    function faqAddOption(){
        var zjxx_html =  $(this).prev(".title_itram").children(".kzjxx_iteam").html();
        $(this).prev(".title_itram").append("<div class='kzjxx_iteam'>"+zjxx_html+"</div>");
    }
    //删除一行
    function faqDeleteRow(){
        //获取编辑题目的个数
        var zuxxs_num = $(this).parent(".kzjxx_iteam").parent(".title_itram").children(".kzjxx_iteam").length;
        if(zuxxs_num > 1){
            $(this).parent(".kzjxx_iteam").remove();
        }else{
            console.log("手下留情");
        }
    }
    //取消编辑
    function faqNotSave(){
        var clearBtn = $(this).parents(".dx_box")  || $(this).parents(".xxk_conn");
        clearBtn.empty().hide();
        $(".movie_box").css({"border":"1px solid #fff"});
        $(this).parents(".movie_box").remove();
        $(".btnAdd-metting-faq").show();
    }
    //保存编辑
    function faqEditSave(){
        var jcxxxx = $(this).parent(".bjqxwc_box").parent(".dx_box");
        //编辑题目选项的个数
        var bjtm_xm_length = jcxxxx.find(".title_itram").children(".kzjxx_iteam").length;
        var xmtit_length = jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").length-1;



        //添加选项题目
        //添加选项
        if(bjtm_xm_length > xmtit_length){
            var fzll = jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").eq(1).html();
            for(var toljs_add = 0 ; toljs_add < bjtm_xm_length - xmtit_length ; toljs_add++){
                jcxxxx.parent(".movie_box").children(".wjdc_list").append("<li>"+fzll+"</li>")
            }
        }
        //删除选项
        if(bjtm_xm_length < xmtit_length) {
            for(var toljs = xmtit_length ; toljs > bjtm_xm_length ; toljs--){
                jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").eq(toljs).remove();
            }
        }

        //赋值文本框
        //题目标题
        var texte_bt_val_bj = jcxxxx.find(".btwen_text").val();
        jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").eq(0).find(".tm_btitlt").children(".btwenzi").text(texte_bt_val_bj);
        //遍历题目项目的文字
        var  bjjs_bj=0;
        jcxxxx.children(".title_itram").children(".kzjxx_iteam").each(function() {
            //题目选项
            var texte_val_bj = $(this).find(".input_wenbk").val();
            jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").eq(bjjs_bj+1).find("span").text(texte_val_bj);
            bjjs_bj++
            //可填空
            var kxtk_yf = $(this).find(".fxk").is(':checked');
            if(kxtk_yf){
                //第几个被勾选
                var jsxz = $(this).index();
                //alert(jsxz);
                jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").eq(jsxz+1).find("span").after("<input name='' type='text' class='wenb_input'>");

            }

        });
        var inputType = $(this).parents(".movie_box").find(".dx_box").attr("data-t");
        if(inputType == 2){
            $(this).parents(".movie_box").find(".wjdc_list").append("<li></li>");
            $(this).parents(".movie_box").find(".wjdc_list").children("li").eq(1).html('<textarea name=""  cols="" rows="" class="input_wenbk btwen_text btwen_text_tk"></textarea>');
        }

        //清除
        $(this).parent(".bjqxwc_box").parent(".dx_box").empty().hide();
    }

    //添加问题
    function faqAddList(){
        // alert("触发")
        var jcxxxx = $(this).parent(".bjqxwc_box").parent(".xxk_xzqh_box");

        // var jcxxxx = $(".xxk_box");
        //编辑题目选项的个数
        var bjtm_xm_length = jcxxxx.find(".title_itram").children(".kzjxx_iteam").length;
        var xmtit_length = jcxxxx.parents(".movie_box").find(".wjdc_list").children("li").length-1;


        //添加选项题目
        //添加选项
        if(bjtm_xm_length > xmtit_length){
            var fzll = jcxxxx.parents(".movie_box").find(".wjdc_list").children("li").eq(1).html();
            for(var toljs_add = 0 ; toljs_add < bjtm_xm_length - xmtit_length ; toljs_add++){
                jcxxxx.parents(".movie_box").find(".wjdc_list").append("<li>"+fzll+"</li>")
            }
        }
        //删除选项
        if(bjtm_xm_length < xmtit_length) {
            for(var toljs = xmtit_length ; toljs > bjtm_xm_length ; toljs--){
                jcxxxx.parents(".movie_box").find(".wjdc_list").children("li").eq(toljs).remove();
            }
        }

        //赋值文本框
        //题目标题
        var texte_bt_val_bj = jcxxxx.find(".btwen_text").val();
        jcxxxx.parents(".movie_box").find(".wjdc_list").children("li").eq(0).find(".tm_btitlt").children(".btwenzi").text(texte_bt_val_bj);
        //遍历题目项目的文字
        var  bjjs_bj=0;
        jcxxxx.find(".title_itram").children(".kzjxx_iteam").each(function() {
            //题目选项
            var texte_val_bj = $(this).find(".input_wenbk").val();
            jcxxxx.parents(".movie_box").find(".wjdc_list").children("li").eq(bjjs_bj+1).find("span").text(texte_val_bj);
            bjjs_bj++
            //可填空
            var kxtk_yf = $(this).find(".fxk").is(':checked');
            if(kxtk_yf){
                //第几个被勾选
                var jsxz = $(this).index();
                //alert(jsxz);
                jcxxxx.parents(".movie_box").find(".wjdc_list").children("li").eq(jsxz+1).find("span").after("<input name='' type='text' class='wenb_input'>");

            }
            //选3


        });
        //类型
        var inputType = $(this).parents(".movie_box").find(".xxk_title").children(".on").attr("data-t");
        $(this).parents(".movie_box").find(".dx_box").attr("data-t",inputType);
        if(inputType == 2){
            console.log($(this))
            //$(this).parents(".movie_box").find(".wjdc_list").children("li").eq(1).after("<input name='' type='text' class='wenb_input'>");
            $(this).parents(".movie_box").find(".wjdc_list").append("<li></li>");
            $(this).parents(".movie_box").find(".wjdc_list").children("li").eq(1).html('<textarea name=""  cols="" rows="" class="input_wenbk btwen_text btwen_text_tk"></textarea>');
        }

        //取消class
        $(this).parents(".movie_box").removeClass("xxk_box");

        //清除
        $(this).parent(".bjqxwc_box").parents(".movie_box").css({"border":"1px solid #fff"});
        $(this).parent(".bjqxwc_box").parents(".movie_box").find(".xxk_conn,.xxk_title,.kzqy_czbut").remove();

        //显示按钮
        $(".btnAdd-metting-faq").show();


    }

    $(document).off("click",".add_swcbj_but").on("click",".add_swcbj_but", faqAddList);



});
<%--
  Created by IntelliJ IDEA.
  User: weilong
  Date: 2017/7/25
  Time: 13:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="aside_div" class="col-lg-3 col-lg-offset-1">
    <!--这个是模板，加载完后会异步获取后台数据来重新生成内容-->
    <div id="aside_model_list_div" style="display: none" class="v2-news-list v2-news-list-garyStyle">
        <div class="v2-news-title">
            <a id="aside_category_more" href="#" class="more fr">更多&gt;&gt;</a>
            <h3 id="aside_category_name">在线购药指南</h3>
        </div>
        <div class="v2-news-main">
            <ul id="aside_title_list_div">
                本栏目还没有数据哟！
            </ul>
        </div>
    </div>

</div>
<script type="text/javascript">
    $.get("${base}/search/aside/category",{},function(res){
        //alert(JSON.stringify(res));
        if(typeof(res)=="undefined"||res==null||res.length<=0){
            return;
        }

        $("#aside_model_list_div").css("display","none");
        $aside_div=$("#aside_div");
        for(var i in res){
            var data=res[i];
            $aside_model_list_div=$("#aside_model_list_div").clone(true);
            $aside_model_list_div.attr("id","entity"+i);
            $aside_model_list_div.find("#aside_category_name").html(data.categoryName);
            $aside_model_list_div.find("#aside_category_more").attr("href","${base}"+data.mapping+"/list?categoryId="+data.categoryId+"&searchingText= ");
            if(typeof (data.entityList)=="undefined"){
                $aside_model_list_div.find("#aside_title_list_div").html("本栏目还没有数据哟！");
            }else{
                $aside_title_list_div= $aside_model_list_div.find("#aside_title_list_div");
                $aside_title_list_div.html("");
                for(var j in data.entityList){
                    var entity=data.entityList[j];
                    var title_li='<li><a href="${base}'+data.mapping+'/detail?id='+entity.id+'">"'+entity.title+'</a></li>';
                    $aside_title_list_div.append(title_li);
                }
            }
            $aside_model_list_div.css("display","block");
            $aside_div.append($aside_model_list_div);
        }

    },"json");

</script>
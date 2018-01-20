/**
 * Created by xingjie on 2017/12/6.
 */
$(function(){

    titleArray = [];
    hrefArray = [];
    //#header
    $("#TitleArray").load("index.html .pageListContent",function(){

        var listArray = $(this).find('a');
        listArray.each(function(){
            var itemText = $(this).text();
            var itemhref = $(this).attr('href');
            titleArray.push(itemText);
            hrefArray.push(itemhref);
        });



        // console.log(titleArray);
        // console.log(hrefArray);
    });






});
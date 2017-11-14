/**
 * 初始化图标
 */
function initBar(){
    var option = {
        tooltip: {
            show: true
        },
        legend: {
            data:[]
        },
        xAxis : [
            {
                type : 'category',
                data : []
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                "name":"销量",
                "type":"bar",
                "data":[]
            }
        ]
    };
    myChart.setOption(option);
}
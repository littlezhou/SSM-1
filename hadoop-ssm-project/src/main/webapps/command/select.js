
"use strict";
function line(number) {
    var myChart = echarts.init(document.getElementById('myChart1-'+number));

    //指定图表的配置项和数据
    var option = {
        title: {
            //显示标题
            text: 'Cache统计',
            //标题显示的位置
//                        left: 'center'
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
//                              magicType : {
//                                  show: true,
//                                  type: ['line'],
//                              },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        tooltip: {trigger:'axis'},
        //在示例中我们看到图表中需要的数据为
        //显示需要统计的分类
        xAxis: [
            {
                data: (function() {
                    var arr1 = [];
                    $.ajax({
                        type:"GET",
                        url : 'http://localhost:9871/ssm/v1?op=SHOWCACHE',
                        dataType:"json",
                        async: false,
                        success : function(result) {
                            for (var key in result) {
                                arr1.push(key);
                            }

                        },
                        error : function(errorMsg) {
                            alert("sorry, 请求数据失败");
                            myChart.hideLoading();
                        }
                    })

                    return arr1;
                })()

            }
        ],

        yAxis: {},
        series: [{
            name: '数量',
            type: 'line',
            //        data : [3,2,1,33]
            data: (function() {
                var arr1 = [];
                $.ajax({
                    type:"GET",
                    url : 'http://localhost:9871/ssm/v1?op=SHOWCACHE',
                    dataType:"json",
                    async: false,
                    success : function(result) {
                        for (var key in result) {
                            arr1.push(result[key]);
                        }

                    },
                    error : function(errorMsg) {
                        alert("sorry, 请求数据失败");
                        myChart.hideLoading();
                    }
                })
                return arr1;
            })()
        }]

    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

function bar (number) {
    var myChart = echarts.init(document.getElementById('myChart2-'+number));

    //指定图表的配置项和数据
    var option = {
        title: {
            //显示标题
            text: 'Cache统计',
            //标题显示的位置
            left: 'center'
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
//                              magicType : {
//                                  show: true,
//                                  type: ['bar'],
//                              },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        tooltip: {trigger:'axis'},
        //在示例中我们看到图表中需要的数据为
        //显示需要统计的分类
        xAxis: [
            {
                data: (function() {
                    var arr1 = [];
                    $.ajax({
                        type:"GET",
                        url : 'http://localhost:9871/ssm/v1?op=SHOWCACHE',
                        dataType:"json",
                        async: false,
                        success : function(result) {
                            for (var key in result) {
                                arr1.push(key);
                            }

                        },
                        error : function(errorMsg) {
                            alert("sorry, 请求数据失败");
                            myChart.hideLoading();
                        }
                    })

                    return arr1;
                })()

            }
        ],

        yAxis: {},
        series: [{
            name: '数量',
            type: 'bar',
            //        data : [3,2,1,33]
            data: (function() {
                var arr1 = [];
                $.ajax({
                    type:"GET",
                    url : 'http://localhost:9871/ssm/v1?op=SHOWCACHE',
                    dataType:"json",
                    async: false,
                    success : function(result) {
                        for (var key in result) {
                            arr1.push(result[key]);
                        }

                    },
                    error : function(errorMsg) {
                        alert("sorry, 请求数据失败");
                        myChart.hideLoading();
                    }
                })
                return arr1;
            })()
        }]

    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

function pie(number) {
    var myChart = echarts.init(document.getElementById('myChart3-'+number));
    var option = {
        title : {
            text: 'Cache统计',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data: (function() {
                var arr1 = [];
                $.ajax({
                    type:"GET",
                    url : 'http://localhost:9871/ssm/v1?op=SHOWCACHE',
                    dataType:"json",
                    async: false,
                    success : function(result) {
                        for (var key in result) {
                            arr1.push(key);
                        }

                    },
                    error : function(errorMsg) {
                        alert("sorry, 请求数据失败");
                        myChart.hideLoading();
                    }
                })

                return arr1;
            })()

        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
//                              magicType : {
//                                  show: true,
//                                  type: ['pie', 'funnel'],
//                                  option: {
//                                      funnel: {
//                                          x: '25%',
//                                          width: '50%',
//                                          funnelAlign: 'left',
//                                          max: 1548
//                                      }
//                                  }
//                              },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [
            {
                name:'Cache使用情况',
                type:'pie',
                radius : '55%',
                center: ['50%', '50%'],
                //                  data:[{"name":"cacheCapacity","value":"3"},{"name":"cacheRemaining","value":2},{"name":"cacheUsed","value":1},{"name":"cacheUsedPercentage","value":33}]
                data: (function() {
                    var arr = [];
                    $.ajax({
                        type:"GET",
                        url : 'http://localhost:9871/ssm/v1?op=SHOWCACHE',
                        async : false,
                        dataType:"json",
                        success : function(result) {
                            for (var key in result) {
                                arr.push({"name":key,"value":result[key]});
                            }
                        },
                        error : function(errorMsg) {
                            alert("sorry, 请求数据失败");
                            myChart.hideLoading();
                        }
                    })
                    return arr;
                })()

            }

        ]
    };

    myChart.setOption(option);
}

function select(var3) {
    var number = var3.substring(var3.length-1);
    var type = $("#select-"+number).find(':selected').val();
    if(type === 'line') {
        line(number);
        $("#myChart1-"+number).show().siblings().hide();
    }
    if(type === 'bar') {
        bar(number);
        $("#myChart2-"+number).show().siblings().hide();
    }
    if(type === 'pie') {
        pie(number);
        $("#myChart3-"+number).show().siblings().hide();
    }
}



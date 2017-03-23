/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
(function() {
  "use strict";
  	
	var ring = function() {
		var percent = Math.round(Math.random()*100);
        var ringChart = echarts.init(document.getElementById('ring'));
        var labelTop = {//上层样式
            normal : {
                color :'#5fa33e',
                label : {
                    show : true,
                    position : 'center',
                    formatter : '{b}',
                    textStyle: {
                        baseline : 'bottom',
                        fontSize:26
                    }
                },
                labelLine : {
                    show : false
                }
            }
        };
        var labelFromatter = {//环内样式
            normal : {//默认样式
                label : {//标签
                    formatter : function (a,b,c){return 100 - c + '%'},
                    // labelLine.length：30,  //线长，从外边缘起计算，可为负值
                    textStyle: {//标签文本样式
                        color:'black',
                        align :'center',
                        baseline : 'top'//垂直对其方式
                    }
                }
            },
        };
        var labelBottom = {//底层样式
            normal : {
                color: '#00ff00',
                label : {
                    show : true,
                    position : 'center',
                    fontSize:22
                },
                labelLine : {
                    show : false
                }
            },
            emphasis: {//悬浮式样式
                color: 'rgba( 0,0,0,0)'
            }
        };
        var radius = [50,80];// 半径[内半径，外半径]

        var ringChartOption = {
//            title : {
//                text: 'echarts实现圆环进度条',
//                subtext: '随机数字',
//                x:'center',
//                //正标题样式
//                textStyle: {
//                    fontSize:44,
//                    fontFamily:'Arial',
//                    fontWeight:100,
//                    //color:'#1a4eb0',
//                },
//                //副标题样式
//                subtextStyle: {
//                    fontSize:28,
//                    fontFamily:'Arial',
//                    color:"#1a4eb0",
//                },
//            },
            animation:false,
            tooltip : {         // 提示框. Can be overwrited by series or data
                trigger: 'axis',
                //show: true,   //default true
                showDelay: 0,
                hideDelay: 50,
                transitionDuration:0,
                borderRadius : 8,
                borderWidth: 2,
                padding: 10,    // [5, 10, 15, 20]
            },
            series : [
                {
                    type : 'pie',
                    center : ['50%', '50%'],//圆心坐标（div中的%比例）
                    radius : radius,//半径
                    itemStyle : labelTop,//graphStyleA,//图形样式 // 当查到的数据不存在（并非为0），此属性隐藏
                    data : [
                        {name:'完成率', value:percent,itemStyle : labelTop},
                        {name:percent+'%', value:100-percent, itemStyle : labelBottom}
                    ]
                }
            ]
        };
        ringChart.setOption(ringChartOption);
        if(percent == 100) {
			window.clearInterval(end);
		}
    };
    var end = setInterval(ring,200);
    

})();
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
"use strict";
function run(var1) {
    var number = var1.substring(var1.length-1);
    if (($('#cmd-'+number).val()) == "showCache") {
        var url= '/ssm/v1?op=SHOWCACHE&cmd=' + $('#cmd-'+number).val();
        $("#figure-"+number).show().siblings().hide();
    }else {
        // alert($('#cmd-'+number).val());
        var url = '/ssm/v1?op=RUNCOMMAND&cmd=' + $('#cmd-'+number).val();
        $("#output-"+number).show().siblings().hide();
        $.ajax({
            type: 'PUT',
            url: url
        }).then(function(data) {
            $('#stdout-'+number).html('');
            $('#stderr-'+number).html('');
            for (var j=0;j<data.stdout.length;j++) {
                $('#stdout-'+number).append(data.stdout[j]+'<br>');
            }
            for (var j=0;j<data.stderr.length;j++) {
                $('#stderr-'+number).append(data.stderr[j]+'<br>');
            }
        });
    }
};

var i = 0;
function newDiv(var2) {
    i = i+1;
    var $div = $('<form onsubmit="return false;">'+
        '<div class="input-group" >'+
        '<input type="text" class="form-control" id="cmd-'+i+'" />'+
        '<span class="input-group-btn">'+
        '<button class="btn btn-default" data-loading-text="Running" type="button" onclick="run(this.id)" id="btn-run-cmd-'+i+'">Run!</button>'+
    '</span>'+
    '<span class="input-group-btn">'+
        '<button class="btn btn-default pull-right" id="btn-new-'+i+'" onclick="newDiv(this.id)">new</button>'+
       ' </span>'+
        '</div >'+

        '<div >'+
        '<div id="output-'+i+'" style="display: none;">'+
        'stdout is <p id="stdout-'+i+'"></p>'+
        'stderr is <p id="stderr-'+i+'"></p>'+
        '</div>'+
        '<div id="figure-'+i+'" class="row" style="display: none;">'+
        '<div class="col-xs-10 col-md-10">'+
        '<div id="myChart1-'+i+'" style="display:none;width:800px;height:400px;margin:0 auto;"></div>'+
        '<div id="myChart2-'+i+'" style="display:none;width:800px;height:400px;margin:0 auto;"></div>'+
        '<div id="myChart3-'+i+'" style="display:none;width:800px;height:400px;margin:0 auto;"></div>'+
        '</div>'+
        '<div class="col-xs-2 col-md-2">'+
        '<select class="form-control" id="select-'+i+'" name="chart" onchange="select(this.id)" >'+
        '<option value="line">line chart</option>'+
    '<option value="bar">bar chart</option>'+
    '<option value="pie">pie chart</option>'+
    '</select>'+
    '</div>'+
    '</div>'+

    '<div id="ring-'+i+'" style="width:400px;height:200px;margin:0 auto;">'+

        '</div>'+
        '</div>'+

        '</form>');
    $("#web-console").append($div);

};


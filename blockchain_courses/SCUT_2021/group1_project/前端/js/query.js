'use strict';
import { _url } from '../config/config.js';

$("#form").submit(function(e) {
    // 提交表单
    function add(data) {
        var tbody = $("#tbody")[0];
        $("tr:gt(0)").remove();
        for (var i=0;i<data.length;i++) {
            var tr=document.createElement("tr");

            var td1=document.createElement("td");
            td1.innerText=data[i].in_time; 
            tr.appendChild(td1);

            var td2=document.createElement("td");
            td2.innerText=data[i].out_time; 
            tr.appendChild(td2);

            var td3=document.createElement("td");
            td3.innerText=data[i].port_name; 
            tr.appendChild(td3);

            tbody.appendChild(tr);
        }
    }

    // var a = JSON.parse("[{\"in_time\":\"1\",\"out_time\":\"1\",\"port_name\":\"1\"}]");
    // add(a);
    // e.preventDefault();
    // e.stopPropagation();
    // return;

    $.ajax({
        type: "POST",   
        url: _url + "/v1/query/showPath",
        crossDomain: true,
        data: $("#form").serialize(),
        success: function(res) {
            if(res.msg === "success") {
                $("#out_time").val(res.data.time);
                $("#out_location").val(res.data.location);
                var data = res.data.nodes;
                add(data);
            } else {
                alert("查询失败: " + res.data);
            }
        }
    });

    e.preventDefault();
    e.stopPropagation();
});
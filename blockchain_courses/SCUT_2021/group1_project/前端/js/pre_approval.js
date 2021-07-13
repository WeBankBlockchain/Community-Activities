'use strict';
import { _url } from '../config/config.js';

function add(data) {
    var port = $("#port")[0];
    for (var i=0;i<data.length;i++) {
        var op=document.createElement("option");
        op.innerText=data[i].port;
        op.value=data[i].user;
        port.appendChild(op);
    }
}

$.ajax({
    type: "POST",   
    url: _url + "/v1/query/showPortList",
    crossDomain: true,
    success: function(res) {
        if(res.msg === "success") {
            add(res.data.arrayList);
        } else {
            alert("获取港口列表失败");
        }
    }
});
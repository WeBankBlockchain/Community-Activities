'use strict';
import { _url } from '../config/config.js';

(function() {
    var user=get_cookie("user");
    $("#user").val(user);
})();

$("#form").submit(function(e) {

    // 提交表单
    $.ajax({
        type: "POST",   
        url: _url + "/v1/chain/addNode",
        crossDomain: true,
        data: $("#form").serialize() + '&token=' + get_cookie("token"),
        success: function(res) {
            if(res.msg === "success") {
                alert("添加成功");
            } else {
                alert("添加失败: " + res.data);
            }
        }
    });

    e.preventDefault();
    e.stopPropagation();
});
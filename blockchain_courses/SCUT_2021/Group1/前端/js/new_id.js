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
        url: _url + "/v1/chain/genId",
        crossDomain: true,
        data: $("#form").serialize() + '&token=' + get_cookie("token"),
        success: function(res) {
            if(res.msg === "success") {
                alert("生成成功");
                $("#product_id").val(res.data);
            } else {
                alert("生成失败: " + res.data);
            }
        }
    });

    e.preventDefault();
    e.stopPropagation();
});
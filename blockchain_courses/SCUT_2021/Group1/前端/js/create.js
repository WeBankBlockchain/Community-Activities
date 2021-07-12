'use strict';
import { _url } from '../config/config.js';

(function() {
    var user=get_cookie("user");
    $("#user").val(user);
})();

$("#form").submit(function(e) { 
    // 检查非空
    var form = $(this);
    if (form[0].checkValidity() === false) {
        e.preventDefault();
        e.stopPropagation();
        form.addClass('was-validated');
        return;
    }
    form.addClass('was-validated');
    
    // 提交表单
    $.ajax({
        type: "POST",   
        url: _url + "/v1/chain/initHead",
        crossDomain: true,
        data: $("#form").serialize() + '&token=' + get_cookie("token"),
        success: function(res) {
            if(res.msg === "success") {
                alert("新建成功");
            } else {
                alert("新建失败: ", res.data);
            }
        }
    });
    e.preventDefault();
    e.stopPropagation();
});
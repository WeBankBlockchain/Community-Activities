'use strict';
import { _url } from '../config/config.js';

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
        url: _url + "/v1/usr/signin",
        crossDomain: true,
        data: $("#form").serialize(),
        success: function(res) {
            if(res.msg === "success") {
                alert("登陆成功");
                set_cookie("token", res.data.token);
                set_cookie("user", $("#user").val());
                set_cookie("user_type", res.data.type);
                $(location).attr("href", "query.html");
            } else {
                alert("登陆失败: " + res.data);
            }
        }
    });

    e.preventDefault();
    e.stopPropagation();
});
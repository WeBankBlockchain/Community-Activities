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
        url: _url + "/v1/usr/signup",
        crossDomain: true,
        data: $("#form").serialize(),
        success: function(res) {
            if(res.msg === "success") {
                alert("注册成功");
                $(location).attr("href", "index.html");
            } else {
                alert("注册失败: " + res.data);
            }
        }
    });
    e.preventDefault();
    e.stopPropagation();
});
var globalStrUrlStr = '192.168.245.129:45000';


function getQueryString(name) {
    console.log('获取当前目录参数中的用户id')
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    console.log(r);
    if (r != null && r[2] != "false")
        return unescape(r[2]);
    return false;
}



toHome = function (userId, userName) {
    console.log(userId);
    location.href = "./enterHome.html?" + "userId=" + userId + "&userName" + userName;
}

//申请升级为颁发机构用户,(左菜单栏参数)
upToCer = function (userId, userName) {
    //查询userid是否已有申请过信息
    console.log("跳转函数内" + userId);
    /**
     * 这里填写http请求
     * 
     */
    $.ajax({
        contentType: 'application/json',
        dataType: "json",
        type: "POST",
        url: 'http://' + globalStrUrlStr + '/Info/ApplyAgencyList',
        data: JSON.stringify({
            "applyId": userId,
        }),
        success: function (result) {
            console.log(result);
            var resultDataArr = result.data;

            if (resultDataArr.length == 0) {
                //如果未申请，跳转到申请填写页面
                location.href = "./enterUpToCer.html?" + "userId=" + userId + "&userName=" + userName;
            } else {
                //查看有无申请为企业且未审核的
                for (var i = 0; i < resultDataArr.length; i++) {
                    if (resultDataArr[i].applyRole == "机构" && resultDataArr[i].result == "未审核") {
                        //跳转到已申请请等待页面
                        location.href = "./enterAfterApply.html?" + "userId=" + userId + "&userName=" + userName;
                        return;
                    }
                }
                //否则还是跳转到申请页面
                location.href = "./enterUpToCer.html?" + "userId=" + userId + "&userName=" + userName;
            }
        },
        complete: function (xhr, type, errorThrown) {
            console.log(JSON.stringify(xhr));
            console.log(type);
            console.log(errorThrown);
        },
    })


}

toMymessage = function (userId, userName) {
    console.log(userId);
    $.ajax({
        contentType: 'application/json',
        dataType: "json",
        type: "POST",
        url: 'http://' + globalStrUrlStr + '/Info/ApplyAgencyList',
        data: JSON.stringify({
            "applyId": userId,
        }),
        success: function (result) {
            console.log(result);
            var resultDataArr = result.data;
            var messageNum = resultDataArr.length;
            if (messageNum > 0)
                location.href = "./enterMessage.html?" + "userId=" + userId + "&userName=" + userName;
            else
                //如果无消息，就跳转到消息为空列表
                location.href = "./enterNoMessage.html?" + "userId=" + userId + "&userName=" + userName;
        },
        complete: function (xhr, type, errorThrown) {
            console.log(JSON.stringify(xhr));
            console.log(type);
            console.log(errorThrown);
        },
    })

}

//验证证书函数
verifyCert = function (certNum, userId, userName) {
    console.log(certNum);
    if (certNum == "") {
        $("#message").html("请输入验证码后再提交验证");
        return;
    }

    $.ajax({
        url: 'http://' + globalStrUrlStr + '/CertificateVerify',
        contentType: 'application/json',
        type: 'POST',
        timeout: 5000,
        dataType: 'JSON',
        //上传的数据
        data: JSON.stringify({
            "certificateCode": certNum
        }),
        success: function (res) {
            console.log(res);
            if (res.message == "成功") {
                location.href = "./enterVerifyTrue.html?" + "&userId=" + userId + "&userName=" + userName +
                    "&certNum=" + certNum;
            } else {
                location.href = "./enterVerifyFalse.html?" + "&userId=" + userId + "&userName=" + userName;
            }

        },
        Error: function (xhr, type, errorThrown) {
            console.log(JSON.stringify(xhr));
            console.log(type);
            console.log(errorThrown);
        },

    });


}
//退出函数
toLogin = function () {
    location.href = "../login.html";
}
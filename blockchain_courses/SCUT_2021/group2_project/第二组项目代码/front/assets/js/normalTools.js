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
    location.href = "./normalHome.html?" + "userId=" + userId + "&userName=" + userName;
}

//升级为企业用户
upToEnter = function (userId, userName) {
    //查询userid是否已有申请过信息
    console.log("跳转到申请成为企业页面" + userId);

    //使用用户查询机构、企业认证申请列表，比对该用户是否有申请过
    // $.ajax({
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
                //没有提交过任何申请，可以直接跳到未申请页面
                location.href = "./normalUpToEnter.html?" + "userId=" + userId + "&userName=" + userName;
            } else {
                //查看有无申请为企业且未审核的
                for (var i = 0; i < resultDataArr.length; i++) {
                    if (resultDataArr[i].applyRole == "企业" && resultDataArr[i].result == "未审核") {
                        //跳转到已申请请等待页面
                        location.href = "./normalAfterApply.html?" + "userId=" + userId + "&userName=" + userName;
                        return;
                    }
                }
                //否则还是跳转到申请页面
                location.href = "./normalUpToEnter.html?" + "userId=" + userId + "&userName=" + userName;
            }
        },

    })

}

//申请升级为颁发机构用户,(左菜单栏参数)
upToCer = function (userId, userName) {
    //查询userid是否已有申请过信息
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

            if (resultDataArr.length == 0) {
                //没有提交过任何申请，可以直接跳到未申请页面
                location.href = "./normalUpToCer.html?" + "userId=" + userId + "&userName=" + userName;
            } else {
                //查看有无申请为企业且未审核的
                for (var i = 0; i < resultDataArr.length; i++) {
                    if (resultDataArr[i].applyRole == "机构" && resultDataArr[i].result == "未审核") {
                        //跳转到已申请请等待页面
                        location.href = "./normalAfterApply.html?" + "userId=" + userId + "&userName=" + userName;
                        return;
                    }
                }
                //否则还是跳转到申请页面
                location.href = "./normalUpToCer.html?" + "userId=" + userId + "&userName=" + userName;
            }
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
                location.href = "./normalMessage.html?" + "userId=" + userId + "&userName=" + userName;
            else
                //如果无消息，就跳转到消息为空列表
                location.href = "./normalNoMessage.html?" + "userId=" + userId + "&userName=" + userName;
        },
        complete: function (xhr, type, errorThrown) {
            console.log(JSON.stringify(xhr));
            console.log(type);
            console.log(errorThrown);
        },
    })

}
function get_cookie(name){
    var arr = document.cookie.split("; ");
    for ( var i = 0; i < arr.length; i++) {
        var _arr = arr[i].split("=");
        if (_arr[0] == name){
            return _arr[1];
        }
    }
    return "";
}

function set_cookie(key,value){
    document.cookie=key+"="+value;
}
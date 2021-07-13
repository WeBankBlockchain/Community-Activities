package org.fisco.bcos.trace.pojo;

public class User {

    public String mp_openid;
    public String nick_name;
    public String image_url;
    User(){}
    User(String mpopenid,String nickname,String imageurl){
        mp_openid=mpopenid;
        nick_name=nickname;
        image_url=imageurl;
    }

}

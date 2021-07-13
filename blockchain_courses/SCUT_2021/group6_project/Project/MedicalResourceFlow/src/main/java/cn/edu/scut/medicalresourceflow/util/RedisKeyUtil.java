package cn.edu.scut.medicalresourceflow.util;

public enum RedisKeyUtil {

    CAPTCHA_REGISTER("captcha::register",""),
    CAPTCHA_RESET_PWD("captcha::resetPwd",""),
    RESOURCE_TRANSACTION("resource::transaction",""),
    RESOURCE_USER("resource::user",""),
    RESOURCE_ALL("resource::all",""),
    RESOURCE_PROVINCE_CNT("resource::province_cnt",""),
    USER_RESOURCE_TRANSACTION("user::resource::transaction",""),
    ;

    private final String key;
    private final String msg;

    private RedisKeyUtil(String key,String msg){
        this.key = key;
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public String getMsg() {
        return msg;
    }
}

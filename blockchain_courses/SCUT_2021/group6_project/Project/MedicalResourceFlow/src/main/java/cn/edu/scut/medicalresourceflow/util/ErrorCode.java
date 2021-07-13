package cn.edu.scut.medicalresourceflow.util;
/**
 * @description: 错误纠正码
 * @param:  * @param null
 * @return:
 * @author 知日
 * @date: 2021/1/20 22:32
 */
public enum ErrorCode {
    //200 Success
    SUCCESS(200,"success"),

    // 400 Bad Request
    BAD_REQUEST_COMMON(400,"Bad Request"),

    BAD_REQUEST_FORBIDDEN(403,"Request is forbidden"),


    // 500 Internal Server Error
    SERVER_COMMON(500,"Internal Server throws am exception"),

    // 700 自定义错误
    CUSTOMIZE_PARAM_VALIDATION_ERROR(701, "Param couldn't be validated successfully"),
    CUSTOMIZE_MAIL_SEND_ERROR(702,"Internal Server Mail Sender Throws Error"),
    CUSTOMIZE_PARAM_CAPTCHA_ERROR(703,"Captcha is incorrect"),
    CUSTOMIZE_USER_EXISTED(704,"The user has been registered"),
    CUSTOMIZE_USER_MUTE(705,"The user has been muted"),
    CUSTOMIZE_FILE_APPLICATION_ERROR(706,"The file application is not correct."),
    CUSTOMIZE_TOKEN_ERROR(707,"The token is incorrect."),
    CUSTOMIZE_USER_NOT_EXISTED(708,"The user is not existed"),
    CUSTOMIZE_USER_PASSWORD_ERROR(709,"The password is not correct"),
    CUSTOMIZE_UNAUTHORIZED(710,"Request unauthorized"),
    CUSTOMIZE_RESOURCE_UNABLE(711,"The Resource is not existed or been used."),
    CUSTOMIZE_RESOURCE_NOT_AVAILABLE(712,"The resource's owner is not the account"),
    ;

    private final int status;

    private final String msg;

    ErrorCode(int status, String msg){
        this.status=status;
        this.msg=msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}

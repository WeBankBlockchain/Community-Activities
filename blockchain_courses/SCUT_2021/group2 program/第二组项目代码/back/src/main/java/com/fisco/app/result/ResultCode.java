package com.fisco.app.result;


/**
 * 自定义状态码和响应消息(可以自行增加)
 * @author amer
 */
public enum ResultCode {
    /* 成功 */
    SUCCESS(200, "成功"),

    /* 常规失败 */
    DUPLICATE_REQUEST(403,"重复提交申请"),
    DATABASE_INSERT_FAIL(996, "数据库插入失败"),
    DATABASE_SEARCH_FAIL(997, "数据库查询失败"),
    DATABASE_UPDATE_FAIL(998, "数据库更新失败"),
    COMMON_FAIL(999, "失败"),

    /* 参数错误：1000~1999 */
    PARAM_IS_BLANK(1001, "参数为空"),
    PARAM_NOT_COMPLETE(1002, "参数缺失"),
    PARAM_FORMAT_ERROR(1003,"参数格式错误"),
    PARAM_ERROR(1004, "传入参数错误"),

    /* 用户错误 2000~2999 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在");

    private Integer code;	// 状态码
    private String message;	// 响应消息

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}



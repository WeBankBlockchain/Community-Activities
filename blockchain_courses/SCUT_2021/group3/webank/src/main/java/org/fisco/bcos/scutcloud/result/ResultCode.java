package org.fisco.bcos.scutcloud.result;

public enum ResultCode {

    SUCCESS(200),
    FAIL(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);


//    /* 参数错误：1000~1999 */
//     PARAM_ERROR(1001),  // "参数错误"
//
//    /* 用户错误 2000~2999 */
//    USER_NOT_LOGIN(2001),   // "用户未登录"
//    USER_CREDENTIALS_ERROR(2003),   // "密码错误"
//    USER_ACCOUNT_DISABLE(2005), // "账号不可用"
//    USER_ACCOUNT_NOT_EXIST(2007),   // "账号不存在"
//    USER_ACCOUNT_ALREADY_EXIST(2008),   // "账号已存在"
//
//
//    /* 业务错误 3000~3999 */
//    NO_PERMISSION(3001),    // "没有权限"
//    TIME_PARAM_MISMATCH(3002),  // "时间段逻辑不符"
//    TIME_RECORD_NOEXIST(3004,"数据库中没有该记录"),
//    SCHEDULE_RECORD_EXIST(3005,"该记录已存在");


    public int code;

    ResultCode(int code) {
        this.code = code;
    }

}
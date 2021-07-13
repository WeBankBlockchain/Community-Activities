package cn.edu.scut.medicalresourceflow.util;

/**
 * @author 知日
 * @version 1.0
 * @date 2021/3/3 17:58
 */
public enum EmailOperationContextUtil {

    EMAIL_REGISTER_CN_ZH("用户注册邮箱验证","注册"),
    EMAIL_RESET_PWD_CN_ZH("找回密码邮箱验证","找回密码"),
    ;

    private final String title;
    private final String operation;

    EmailOperationContextUtil(String title, String operation){
        this.title=title;
        this.operation=operation;
    }

    public String getTitle() {
        return title;
    }

    public String getOperation() {
        return operation;
    }
}

package cn.edu.scut.medicalresourceflow.util;

public enum EmailTemplateUtil {
    CAPTCHA_EMAIL_ZH_CN("CaptchaEmail_zh_CN"),
    ;

    private final String template;

    private EmailTemplateUtil(String template){
        this.template=template;
    }

    public String getTemplate() {
        return template;
    }
}

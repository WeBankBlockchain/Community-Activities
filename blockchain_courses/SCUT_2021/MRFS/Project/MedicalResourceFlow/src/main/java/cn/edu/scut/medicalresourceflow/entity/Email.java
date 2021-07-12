package cn.edu.scut.medicalresourceflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 知日
 * @version 1.0
 * @date 2021/2/1 12:06
 */
@Data
@NoArgsConstructor
public class Email implements Serializable {

    @NotEmpty
    private String subject;

    @NotEmpty
    private String content;

    private String from;

    /**
     * @description: 收件人，多个邮箱以逗号分割
     */
    @NotEmpty
    private String to;

    /**
     * @description: 抄送，多个邮箱以逗号分割
     */
    private String cc;

    /**
     * @description: 密送，多个邮箱以逗号分割
     */
    private String bcc;

    /**
     * @description: 右键状态信息
     * true: 发送成功
     * fail: 发送失败
     */
    private String status;

    /**
     * @description: 邮件报错信息
     */
    private String error;

    /**
     * @description: 右键附件
     */

    private Date sentDate;

    public static class EmailBuilder{
        private String subject;
        private String context;
        private String from;
        private String to;
        private String captcha;
        private String operation;
        private String template;

        private TemplateEngine templateEngine;

        public EmailBuilder(){}

        public EmailBuilder subject(String subject){
            this.subject = subject;
            return this;
        }

        public EmailBuilder context(String context){
            this.context = context;
            return this;
        }

        public EmailBuilder from(String from){
            this.from = from;
            return this;
        }

        public EmailBuilder to(String to){
            this.to = to;
            return this;
        }

        public EmailBuilder captcha(String captcha){
            this.captcha = captcha;
            return this;
        }

        public EmailBuilder operation(String operation){
            this.operation = operation;
            return this;
        }

        public EmailBuilder template(String template){
            this.template = template;
            return this;
        }

        public EmailBuilder templateEngine(TemplateEngine templateEngine){
            this.templateEngine = templateEngine;
            return this;
        }

        public Email build(){
            Context context = new Context();
            Email email = new Email();
            context.setVariable("operation",operation);
            context.setVariable("code",captcha);

            email.setSubject(subject);
            email.setContent(templateEngine.process(template,context));
            email.setTo(to);
            return email;
        }
    }

    public static EmailBuilder NULL(){
        return new EmailBuilder();
    }

    @JsonIgnore
    private MultipartFile[] multipartFiles;
}

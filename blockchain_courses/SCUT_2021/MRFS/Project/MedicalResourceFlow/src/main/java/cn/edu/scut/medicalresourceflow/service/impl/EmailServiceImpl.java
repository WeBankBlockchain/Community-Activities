package cn.edu.scut.medicalresourceflow.service.impl;

import cn.edu.scut.medicalresourceflow.entity.Email;
import cn.edu.scut.medicalresourceflow.exception.BusinessException;
import cn.edu.scut.medicalresourceflow.service.EmailService;
import cn.edu.scut.medicalresourceflow.util.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    String from;

    @Resource
    JavaMailSender mailSender;

    @Override
    @Async
    public void sendMail(Email email) {
        try {
            email.setFrom(from);
            sendMimeMail(email);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new BusinessException(ErrorCode.CUSTOMIZE_MAIL_SEND_ERROR,ex.getMessage());
        }
    }

    private void sendMimeMail(Email email) throws Exception{
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailSender.createMimeMessage(),true);
        mimeMessageHelper.setFrom(email.getFrom());
        mimeMessageHelper.setTo(email.getTo().split(","));
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setText(email.getContent(),true);
        if (email.getBcc() != null){
            mimeMessageHelper.setBcc(email.getBcc());
        }
        if (email.getCc() != null){
            mimeMessageHelper.setCc(email.getCc());
        }
        if (email.getMultipartFiles() != null){
            for (MultipartFile multipartFile:email.getMultipartFiles()){
                mimeMessageHelper.addAttachment(multipartFile.getOriginalFilename(),multipartFile);
            }
        }
        if (email.getSentDate() == null){
            email.setSentDate(new Date());
        }
        mimeMessageHelper.setSentDate(email.getSentDate());
        mailSender.send(mimeMessageHelper.getMimeMessage());
        email.setStatus("true");
    }


    @Override
    public String getMailSendFrom() {
        return from;
    }
}

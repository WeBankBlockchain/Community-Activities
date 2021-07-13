package cn.edu.scut.medicalresourceflow.service;

import cn.edu.scut.medicalresourceflow.entity.Email;

public interface EmailService {

    void sendMail(Email email);

    String getMailSendFrom();

}

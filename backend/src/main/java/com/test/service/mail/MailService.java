package com.test.service.mail;


import jakarta.mail.MessagingException;

import com.test.dto.MailInfo;

public interface MailService {
    void sendEmail(MailInfo mailInfo);

    void sendEmailWithAttachment(MailInfo mailInfo) throws MessagingException;
}

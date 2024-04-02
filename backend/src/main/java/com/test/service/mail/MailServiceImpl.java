package com.test.service.mail;

import java.util.ArrayList;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.test.dto.MailAttachment;
import com.test.dto.MailInfo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final String mailFrom = "spectrum.assignment@gmail.com";
    private String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(MailInfo mailInfo) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(getValidMailReceivers(mailInfo));
        msg.setFrom(mailFrom);
        msg.setSubject(mailInfo.getSubject());
        msg.setText(mailInfo.getBody());
        mailSender.send(msg);
    }

    @Override
    public void sendEmailWithAttachment(MailInfo mailInfo) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(getValidMailReceivers(mailInfo));
        helper.setFrom(mailFrom);
        helper.setSubject(mailInfo.getSubject());
        helper.setText(mailInfo.getBody(), mailInfo.isHtml());
        for (MailAttachment attachment : mailInfo.getAttachments()) {
            helper.addAttachment(attachment.getAttachmentName(), attachment.getAttachmentFile());
        }
        mailSender.send(msg);
    }

    private String[] getValidMailReceivers(MailInfo mailInfo) {
        String[] emails = mailInfo.getSendTo();
        List<String> list = filterValidEmails(emails);
        return list.toArray(new String[0]);
    }

    private List<String> filterValidEmails(String[] emails) {
        List<String> list = new ArrayList<>();
        for (String mail : emails) {
            if (StringUtils.hasText(mail) && mail.matches(emailRegex)) {
                list.add(mail);
            }
        }
        return list;
    }
}

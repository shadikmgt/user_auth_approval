package com.test.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MailInfo {
    private String[] sendTo;
    private String subject;
    private String body;
    private List<MailAttachment> attachments = new ArrayList<>();
    private boolean html;

}

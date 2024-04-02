package com.test.dto;

import lombok.Data;

import java.io.File;

@Data
public class MailAttachment {
    private String attachmentName;
    private File attachmentFile;
}

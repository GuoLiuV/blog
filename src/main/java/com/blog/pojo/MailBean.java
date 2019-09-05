package com.blog.pojo;

import java.util.Map;

public class MailBean {
    private String recipientsEmail;
    private String subject;
    private String content;
    private String recordId;
    private String sendType = "html";
    private Map<String, Object> params;
    private Map<String, String> pictures;
    private Map<String, String> attachments;
    private String sendStatus;

    public MailBean() {
    }

    public MailBean(String recipientsEmail, String subject, String content) {
        this.recipientsEmail = recipientsEmail;
        this.subject = subject;
        this.content = content;
    }

    public MailBean(String recipientsEmail, String subject, String content, String recordId) {
        this.recipientsEmail = recipientsEmail;
        this.subject = subject;
        this.content = content;
        this.recordId = recordId;
    }

    public MailBean(String recipientsEmail, String subject, String content, String recordId, String sendType) {
        this.recipientsEmail = recipientsEmail;
        this.subject = subject;
        this.content = content;
        this.recordId = recordId;
        this.sendType = sendType;
    }

    public MailBean(String recipientsEmail, String subject, String content, String recordId, Map<String, String> pictures, Map<String, String> attachments) {
        this.recipientsEmail = recipientsEmail;
        this.subject = subject;
        this.content = content;
        this.recordId = recordId;
        this.pictures = pictures;
        this.attachments = attachments;
    }

    public MailBean(String recipientsEmail, String subject, String content, String recordId, String sendType, Map<String, String> pictures, Map<String, String> attachments) {
        this.recipientsEmail = recipientsEmail;
        this.subject = subject;
        this.content = content;
        this.recordId = recordId;
        this.sendType = sendType;
        this.pictures = pictures;
        this.attachments = attachments;
    }

    public MailBean(String recipientsEmail, String subject, String content, String recordId, String sendType, Map<String, String> pictures, Map<String, String> attachments, Map<String, Object> params) {
        this.recipientsEmail = recipientsEmail;
        this.subject = subject;
        this.content = content;
        this.recordId = recordId;
        this.sendType = sendType;
        this.pictures = pictures;
        this.attachments = attachments;
        this.params = params;
    }

    public String getRecipientsEmail() {
        return this.recipientsEmail;
    }

    public void setRecipientsEmail(String recipientsEmail) {
        this.recipientsEmail = recipientsEmail;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecordId() {
        return this.recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getSendType() {
        return this.sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, String> getPictures() {
        return this.pictures;
    }

    public void setPictures(Map<String, String> pictures) {
        this.pictures = pictures;
    }

    public Map<String, String> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }

    public String getSendStatus() {
        return this.sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }
}

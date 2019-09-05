package com.blog.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blog.pojo.MailBean;
import com.blog.service.EmailSettingInfoService;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class MailSenderExecutor {
    private String username;
    private EmailSettingInfoService emailSettingInfoService;
    private JavaMailSenderImpl javaMailSender;
    private static final String SEND_TYPE_TEXT = "text";
    private static final String SEND_TYPE_HTML = "html";
    private static final String SEND_STATUS_FAILD = "101";
    private static final String SEND_STATUS_SUCCESS = "100";

    public MailSenderExecutor() {}

    public void initMailSetting() {
        boolean empty = ObjectUtils.isEmpty(this.javaMailSender);
        boolean notEmpty = !ObjectUtils.isEmpty(this.javaMailSender);
        boolean fieldEmpty = notEmpty && (StringUtils.isBlank(this.javaMailSender.getUsername()) || StringUtils.isBlank(this.javaMailSender.getPassword()));
        if (empty || fieldEmpty) {
            this.javaMailSender = this.javaMailSender();
        }

    }

    public void senderEmail(MailBean mailBean) {
        this.submit(mailBean);
    }

    private void submit(MailBean m) {
        try {
            ThreadPoolExecutorUtil.submit(new MailSenderExecutor.SendMailThread(m, this.emailSettingInfoService));
        } catch (ExecutionException | InterruptedException var3) {
            UnitedLogger.error("MailSenderExecutor.submit******* 邮件异常 ID=" + m.getRecordId() + "Error:", var3);
            UnitedLogger.debug("MailSenderExecutor.submit******* 邮件异常 ID=" + m.getRecordId() + "Error:", var3);
        }

    }

    private void send(MailBean mailBean) throws UnitedException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.username);
        String[] emails = getEmails(mailBean.getRecipientsEmail());
        message.setTo(emails);
        message.setSubject(mailBean.getSubject());
        message.setText(mailBean.getContent());
        message.setSentDate(new Date());
        this.javaMailSender.send(message);
    }

    private void sendHtml(MailBean mailBean) throws Exception {
        MimeMessage message = this.javaMailSender.createMimeMessage();

        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true, "UTF-8");
        } catch (MessagingException var11) {
            throw new UnitedException("I18N-MSG-999900000230");
        }

        messageHelper.setFrom(this.username);
        String[] emails = getEmails(mailBean.getRecipientsEmail());
        messageHelper.setTo(emails);
        if (org.apache.commons.lang3.ObjectUtils.anyNotNull(new Object[]{mailBean.getSubject()})) {
            messageHelper.setSubject(mailBean.getSubject());
        }

        messageHelper.setText(mailBean.getContent(), true);
        Iterator it;
        Entry entry;
        String cid;
        String filePath;
        File file;
        FileSystemResource fileResource;
        if (org.apache.commons.lang3.ObjectUtils.anyNotNull(new Object[]{mailBean.getPictures()})) {
            it = mailBean.getPictures().entrySet().iterator();

            while(it.hasNext()) {
                entry = (Entry)it.next();
                cid = (String)entry.getKey();
                filePath = (String)entry.getValue();
                if (StringUtils.isBlank(cid) || StringUtils.isBlank(filePath)) {
                    UnitedLogger.debug("请确认每张图片的ID和图片地址是否齐全\n");
                    throw new UnitedException("请确认每张图片的ID和图片地址是否齐全");
                }

                file = new File(filePath);
                if (!file.exists()) {
                    UnitedLogger.debug("图片路径[" + filePath + "]不存在\n");
                    throw new UnitedException("图片路径不存在");
                }

                fileResource = new FileSystemResource(file);
                messageHelper.addInline(cid, fileResource);
            }
        }

        if (org.apache.commons.lang3.ObjectUtils.anyNotNull(new Object[]{mailBean.getAttachments()})) {
            it = mailBean.getAttachments().entrySet().iterator();

            while(it.hasNext()) {
                entry = (Entry)it.next();
                cid = (String)entry.getKey();
                filePath = (String)entry.getValue();
                if (StringUtils.isBlank(cid) || StringUtils.isBlank(filePath)) {
                    UnitedLogger.debug("请确认每个附件的ID和地址是否齐全\n");
                    throw new UnitedException("请确认每个附件的ID和地址是否齐全");
                }

                file = new File(filePath);
                if (!file.exists()) {
                    UnitedLogger.debug("附件路径[" + filePath + "]不存在\n");
                    throw new UnitedException("附件路径不存在");
                }

                fileResource = new FileSystemResource(file);
                messageHelper.addAttachment(cid, fileResource);
            }
        }

        messageHelper.setSentDate(new Date());
        this.javaMailSender.send(message);
    }

    private static String[] getEmails(String emailstr) throws UnitedException {
        String[] toEmailArray = null;
        if (StringUtils.isNotBlank(emailstr)) {
            toEmailArray = emailstr.split(",");
            List<String> toEmailList = new ArrayList();
            if (!org.apache.commons.lang3.ObjectUtils.anyNotNull(toEmailArray)) {
                UnitedLogger.debug("收件人邮箱不得为空\n");
                throw new UnitedException("业务类型不存在");
            }

            String[] var3 = toEmailArray;
            int var4 = toEmailArray.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String s = var3[var5];
                if (StringUtils.isNotBlank(s)) {
                    toEmailList.add(s);
                }
            }

            if (!org.apache.commons.lang3.ObjectUtils.anyNotNull(new Object[]{toEmailList})) {
                UnitedLogger.debug("收件人邮箱不得为空\n");
                throw new UnitedException("业务类型不存在");
            }

            toEmailArray = new String[toEmailList.size()];
            if (org.apache.commons.lang3.ObjectUtils.anyNotNull(new Object[]{toEmailList})) {
                for(int i = 0; i < toEmailList.size(); ++i) {
                    toEmailArray[i] = (String)toEmailList.get(i);
                }
            }
        }

        return toEmailArray;
    }

    private JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();

        try {
            String jsonstr = this.emailSettingInfoService.getEmailSettingInfo();
            if (StringUtils.isNotBlank(jsonstr)) {
                JSONObject json = JSON.parseObject(jsonstr);
                String host = (String)json.get("host");
                Integer port = Integer.parseInt((String)json.get("port"));
                String emailname = (String)json.get("username");
                final String password = (String)json.get("password");
                String auth = (String)json.get("auth");
                String starttls = (String)json.get("starttls");
                String ssl = (String)json.get("ssl");
                sender.setHost(host);
                sender.setPort(port);
                sender.setUsername(emailname);
                sender.setPassword(password);
                sender.setDefaultEncoding("UTF-8");
                Properties p = new Properties();
                p.setProperty("mail.smtp.port", String.valueOf(port));
                p.setProperty("mail.smtp.auth", auth);
                p.setProperty("mail.smtp.starttls.enable", starttls);
                p.setProperty("mail.smtp.ssl.enable", ssl);
                p.setProperty("mail.smtp.socketFactory.port", String.valueOf(port));
                p.setProperty("mail.smtp.socketFactory.fallback", "false");
                p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                Authenticator aut = new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(MailSenderExecutor.this.username, password);
                    }
                };
                Session session = Session.getInstance(p, aut);
                sender.setSession(session);
                sender.setJavaMailProperties(p);
                this.username = sender.getUsername();
            }
        } catch (UnitedException var14) {
            UnitedLogger.error(var14);
        }

        return sender;
    }

    private EmailSettingInfoService getEmailSettingInfoService() {
        return this.emailSettingInfoService;
    }

    @Resource(
            name = "emailSettingInfoService"
    )
    private void setEmailSettingInfoService(EmailSettingInfoService emailSettingInfoService) {
        this.emailSettingInfoService = emailSettingInfoService;
    }

    private class SendMailThread extends RunnableAsyc {
        private MailBean mailBean;
        private EmailSettingInfoService emailSettingInfoService;

        public void run() {
            String status = "101";

            try {
                UnitedLogger.error("线程运行正在发送至 " + this.mailBean.getRecipientsEmail() + " ....\n");
                UnitedLogger.error("------------ 线程运行正在发送 ----------\n");
                if ("text".equals(this.mailBean.getSendType())) {
                    MailSenderExecutor.this.send(this.mailBean);
                } else if ("html".equals(this.mailBean.getSendType())) {
                    MailSenderExecutor.this.sendHtml(this.mailBean);
                }

                UnitedLogger.error("------------ 发送邮件至 " + this.mailBean.getRecipientsEmail() + " 完成 ----------\n");
                status = "100";
            } catch (Exception var3) {
                UnitedLogger.error(var3);
                status = "101";
            }

            if (StringUtils.isNotBlank(this.mailBean.getRecordId())) {
                this.emailSettingInfoService.updateMessageRecordStatusById(status, this.mailBean.getRecordId());
            }

        }

        public SendMailThread(MailBean mailBean, EmailSettingInfoService emailSettingInfoService) {
            this.mailBean = mailBean;
            this.emailSettingInfoService = emailSettingInfoService;
        }
    }
}

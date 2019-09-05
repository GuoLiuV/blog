package com.blog.controller;

import com.blog.pojo.MailBean;
import com.blog.pojo.Result;
import com.blog.pojo.User;
import com.blog.service.UserService;
import com.blog.utils.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("email")
public class EmailController {

    @Resource
    public UserService userService;
    @Resource
    private MailSenderExecutor executor;

    /**
     * 用户界面
     */
    @RequestMapping(value = "init_page.do")
    public String userPage() {
        return "back/user_list_page";
    }

    /**
     * 用户list
     */
    @ResponseBody
    @RequestMapping(value = "send_email.json")
    public Result userList(Model model, HttpServletRequest request, User user) {
        Result result = new Result();
        DataTablePage<User> pageList = new DataTablePage<>(request);
        try {
            String emails = "741175738@qq.com,guoliuv@sina.com";
            if(emails!=null){
                String subject =  "【My_Blog】";
                File templateFile = null;
                HashMap<String, String> dataMap = new HashMap<String, String>();
                //获取路径
                String path=  ClassUtils.getDefaultClassLoader().getResource("").getPath() + "model/email/";

                templateFile = new File(path + "meetingMail.html");
                dataMap.put("#type#", "GUO_LiuWei");
                dataMap.put("#meetingType#", "董事会");
                dataMap.put("#projectName#", "我的博客项目");
                dataMap.put("#meetingtime#", "2019-09-03");

                String content = this.getEmailTemplate(templateFile, dataMap);
                MailBean mailBean = new MailBean();
                executor.initMailSetting();
                mailBean.setSubject(subject);
                mailBean.setContent(content);
                mailBean.setRecipientsEmail(emails);
                executor.senderEmail(mailBean);
            }
            result.setResultCode(ResultEnum.SUCCESS.getValue());
        } catch (Exception e) {
            result.setResultCode(ResultEnum.FAIL.getValue());
            UnitedLogger.error(e);
        }
        return result;
    }



    /**
     * 模板赋值
     */
    public static String getEmailTemplate(File templateFile, Map dataMap) {
        String mailContent = ""; // 发邮件要的内容
        try {
            String templateContent = " ";
            FileInputStream fileinputstream = new FileInputStream(templateFile); // 读取模板文件
            int lenght = fileinputstream.available();
            byte bytes[] = new byte[lenght];
            fileinputstream.read(bytes);
            fileinputstream.close();
            templateContent = new String(bytes, "utf-8");
            Set<Object> keySet = dataMap.keySet();
            for (Object key : keySet) {
                Object value = dataMap.get(key);
                templateContent = templateContent.replaceAll(key.toString(),
                        value.toString());
            }
            mailContent = templateContent; // 发送邮件为html模板格式
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mailContent;
    }
}

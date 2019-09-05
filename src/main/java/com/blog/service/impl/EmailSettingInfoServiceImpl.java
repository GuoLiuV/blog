package com.blog.service.impl;

import com.blog.dao.EmailSettingInfoMapper;
import com.blog.service.EmailSettingInfoService;
import com.blog.utils.UnitedException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("emailSettingInfoService")
public class EmailSettingInfoServiceImpl implements EmailSettingInfoService {
    @Resource( name = "emailSettingInfoMapper")
    private EmailSettingInfoMapper emailSettingInfoMapper;

    public EmailSettingInfoServiceImpl() { }

    public String getEmailSettingInfo() throws UnitedException {
        return emailSettingInfoMapper.getEmailSettingInfo();
    }
    public void updateMessageRecordStatusById(String status, String id) {
        System.out.println("完成啦");
        emailSettingInfoMapper.updateMessageRecordStatusById(status,id);
    }
}

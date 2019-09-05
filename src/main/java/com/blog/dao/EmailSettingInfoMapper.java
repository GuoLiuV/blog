package com.blog.dao;

import com.blog.utils.UnitedException;
import org.springframework.stereotype.Repository;

@Repository("emailSettingInfoMapper")
public interface EmailSettingInfoMapper {
    String getEmailSettingInfo() throws UnitedException;

    void updateMessageRecordStatusById(String status, String id);
}
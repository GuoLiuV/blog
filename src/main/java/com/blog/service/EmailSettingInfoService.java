package com.blog.service;

import com.blog.utils.UnitedException;

public interface EmailSettingInfoService {
    String getEmailSettingInfo() throws UnitedException;

    void updateMessageRecordStatusById(String status, String id);
}

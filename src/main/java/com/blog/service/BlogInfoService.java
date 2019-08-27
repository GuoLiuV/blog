/*
 * All rights Reserved, Designed By DataDriver
 * Copyright:    DataDriver.Inc
 * Company:      Zhuo Wo Infomation Technology (ShangHai) CO.LTD
 */
package com.blog.service;


import com.blog.pojo.BlogInfo;
import com.github.pagehelper.PageInfo;

public interface BlogInfoService {
    PageInfo<BlogInfo> queryAllByLimit(Integer page, Integer rows, BlogInfo pojo);
}
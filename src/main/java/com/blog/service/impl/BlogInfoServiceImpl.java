/*
 * All rights Reserved, Designed By DataDriver
 * Copyright:    DataDriver.Inc
 * Company:      Zhuo Wo Infomation Technology (ShangHai) CO.LTD
 */
package com.blog.service.impl;


import com.blog.dao.BlogInfoDao;
import com.blog.pojo.BlogInfo;
import com.blog.service.BlogInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class BlogInfoServiceImpl implements BlogInfoService {

    @Resource
    private BlogInfoDao bloginfoDao;

    @Override
    public PageInfo<BlogInfo> queryAllByLimit(Integer page, Integer rows, BlogInfo pojo) {
        PageHelper.startPage(page, rows, true);
        List<BlogInfo> list = bloginfoDao.queryAllByLimit(pojo);
        return new PageInfo<>(list);
    }
}
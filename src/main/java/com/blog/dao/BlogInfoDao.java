/*
 * All rights Reserved, Designed By DataDriver
 * Copyright:    DataDriver.Inc
 * Company:      Zhuo Wo Infomation Technology (ShangHai) CO.LTD
 */
package com.blog.dao;


import com.blog.pojo.BlogInfo;

import java.util.List;

public interface BlogInfoDao {
    List<BlogInfo> queryAllByLimit(BlogInfo pojo);
}

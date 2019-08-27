package com.blog.service.impl;

import com.blog.dao.UserDao;
import com.blog.pojo.Permission;
import com.blog.pojo.Role;
import com.blog.pojo.User;
import com.blog.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userMapper;

    @Override
    public PageInfo<User> queryAllByLimit(Integer page, Integer rows, User user) {
        PageHelper.startPage(page, rows, true);
        List<User> list = userMapper.queryAllByLimit(user);
        return new PageInfo<>(list);
    }

    @Override
    public User selectUserName(String loginName) {
        if (StringUtils.isEmpty(loginName)) {
            return null;
        } else {
            User user = new User();
            user.setLoginName(loginName);
            return userMapper.selectUserByCondition(user);
        }
    }

    @Override
    public List<Role> selectUserRoles(String userId) {
        return userMapper.selectUserRoles(userId);
    }

    @Override
    public List<Permission> selectPermissionsByRoleId(String roleId) {
        return userMapper.selectPermissionsByRoleId(roleId);
    }
}

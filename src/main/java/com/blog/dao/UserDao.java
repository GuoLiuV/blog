package com.blog.dao;

import com.blog.pojo.User;
import com.blog.pojo.Role;
import com.blog.pojo.Permission;

import java.util.List;

public interface UserDao {

    List<User> queryAllByLimit(User user);

    User selectUserByCondition(User user);

    List<Role> selectUserRoles(String loginName);

    List<Permission> selectPermissionsByRoleId(String roleId);
}

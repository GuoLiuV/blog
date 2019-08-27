package com.blog.controller;

import com.blog.pojo.User;
import com.blog.service.UserService;
import com.blog.utils.DataTablePage;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    public UserService userService;

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
    @RequestMapping(value = "user_list.json")
    public DataTablePage<User> userList(Model model, HttpServletRequest request, User user) {
        DataTablePage<User> pageList = new DataTablePage<>(request);
        try {
            PageInfo<User> pagePojoList = userService.queryAllByLimit(pageList.getPageNum(), pageList.getLength(), user);
            pageList.transfer(pageList, pagePojoList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", e.getMessage());
        }
        return pageList;
    }
}

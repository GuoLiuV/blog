package com.blog.controller;

import com.blog.service.impl.UserServiceImpl;
import com.blog.utils.RResp;
import com.blog.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class BaseController {
    @Resource
    public UserServiceImpl userService;

    @RequestMapping(value = "index.do")
    public String index() {
        return "blog/index";
    }
    @RequestMapping(value = "about.do")
    public String about() {
        return "blog/about";
    }
    @RequestMapping(value = "blog_list.do")
    public String blog_list() {
        return "blog/blog_list";
    }
    @RequestMapping(value = "blog_post.do")
    public String blog_post() {
        return "blog/blog_post";
    }

    @ResponseBody
    @PostMapping({"loginUser", "userLogin", "toLogin"})
    public RResp loginUser(String userName, String passWord, String rememberMe, HttpSession session) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, passWord);
        //记住我 可以访问 user权限和guide权限
        usernamePasswordToken.setRememberMe(Boolean.parseBoolean(rememberMe));
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
            //完成登录
            session.setAttribute("localUser", subject.getPrincipal());
            //记住当前登录时间
            User user = (User) subject.getPrincipal();
            //userService.insertLoginLastTime(user.getId());
            return RResp.ok("登录成功");
        } catch (IncorrectCredentialsException e) {
            return RResp.error("密码错误");
        } catch (LockedAccountException e) {
            return RResp.error("登录失败，该用户已被冻结");
        } catch (AuthenticationException e) {
            return RResp.error("该用户不存在");
        } catch (Exception e) {
            //返回登录页面
            return RResp.error("系统错误,请联系管理员");

        }
    }
}

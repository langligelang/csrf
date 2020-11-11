package com.maoge.csrf.ctl;

import com.maoge.csrf.annotation.AddCSRFToken;
import com.maoge.csrf.beans.User;
import com.maoge.csrf.config.Constant;
import com.maoge.csrf.service.LoginService;
import com.maoge.csrf.utils.CSRFToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @AddCSRFToken
    public String login(User user,Model model)  {
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            model.addAttribute("error","请输入用户名和密码！");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e) {
            log.error("用户名不存在！", e);
            model.addAttribute("error","用户名不存在！");
            return "error";
        } catch (AuthenticationException e) {
            log.error("账号或密码错误！", e);
            model.addAttribute("error","账号或密码错误！");
            return "error";
        } catch (AuthorizationException e) {
            log.error("没有权限！", e);
            model.addAttribute("error","没有权限！");
            return "error";
        }
        return "indexHtml";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin() {
        return "admin success!";
    }

    @RequiresPermissions("query")
    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String query() {
        User user = loginService.getUserByName(SecurityUtils.getSubject().getPrincipal().toString());
        return "your phone number is:"+user.getPhone();
    }

    @RequiresPermissions("add")
    @ResponseBody
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @com.maoge.csrf.annotation.CSRFToken
    public String modify(String phone,String csrfToken) {
        Constant.phoneAdmin=phone;
        return "modify success!";
    }





}

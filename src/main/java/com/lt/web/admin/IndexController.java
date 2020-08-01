package com.lt.web.admin;

import com.lt.po.User;
import com.lt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class IndexController {

    @Autowired
    private UserService userService;

    //访问 admin 就会跳转到登陆页面
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }


    /**
     * 登录
     */

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession httpSession,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if (user !=null){
            user.setPassword(null); //不要吧密码传到 前面去
            httpSession.setAttribute("user",user);
            return "admin/index";
        }else {
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin"; //登录失败重定向到登录页面
        }
    }

    /**
     * 注销  到登录页面
     */

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}


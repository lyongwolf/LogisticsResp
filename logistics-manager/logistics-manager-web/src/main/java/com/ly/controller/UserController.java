package com.ly.controller;

import com.ly.pojo.User;
import com.ly.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 用户管理
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService service;

    @GetMapping("/query")
    public String query(User user, Model model) throws Exception {
        List<User> list = service.query(user);
        model.addAttribute("list", list);
        return "user/user";
    }
}

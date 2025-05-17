package com.ly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转的控制器
 */
@Controller
public class PageController {
    /**
     * HOME 页面
     * @return
     */
    @RequestMapping(value = {"/", "/login"})
    public String showMain() {
        return "login";
    }

    /**
     * RestFul 风格处理
     * @return
     */
    @RequestMapping("/{path}")
    public String path(@PathVariable String path) {
        return path;
    }
}
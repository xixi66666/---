package com.ycx.graduation_project.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: ycx_graduation_project
 * @description: 商品搜索
 * @author: 杨晨曦
 * @create: 2023-05-14 23:22
 **/

@Controller
public class ProductSearchController {

    @GetMapping("/list.html")
    public String search(){
        return "index";
    }

    @GetMapping("sb")
    public String sb(){
        return "/index";
    }

    @GetMapping("/ycx")
    public String ycx(){
        return "index";
    }


}

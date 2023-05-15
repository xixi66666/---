package com.ycx.graduation_project.product.controller;

import com.ycx.graduation_project.product.feign.SearchFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: ycx_graduation_project
 * @description:
 * @author: 杨晨曦
 * @create: 2023-05-15 01:36
 **/
@Controller
public class SearchController {
    @Autowired
    SearchFeignService searchFeignService;

//    @GetMapping("/list.html")
//    public String list(){
//        System.out.println("进入product");
//        return "redirect:https://search.jd.com/Search?keyword=%E4%BA%AC%E4%B8%9C%E5%95%86%E5%9F%8E%E6%89%8B%E6%9C%BA&enc=utf-8&wq=%E4%BA%AC%E4%B8%9C%E5%95%86%E5%9F%8E%E6%89%8B%E6%9C%BA&pvid=96df37cbd7064c609d1e867911a7c436";
//    }
//    @GetMapping("/list.html")
//    public String list(){
//        System.out.println("进入product");
//        return searchFeignService.search();
//}
@GetMapping("/list.html")
public String list(){
    System.out.println("进入product");
    return "redirect:http://localhost:8090/";
}

}

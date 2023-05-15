package com.ycx.graduation_project.product.feign;

//http://search.gulimall.com/

import com.ycx.common.to.SpuBoundTo;
import com.ycx.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("search")
public interface SearchFeignService {

    @GetMapping("/list.html")
    String search();


    @GetMapping("/ycx")
    String search1();
}

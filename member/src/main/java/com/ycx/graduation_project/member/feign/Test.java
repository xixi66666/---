package com.ycx.graduation_project.member.feign;


import com.ycx.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("coupon")
public interface Test {
    @RequestMapping("coupon/coupon/hudie")
    public R membercoupons();
}

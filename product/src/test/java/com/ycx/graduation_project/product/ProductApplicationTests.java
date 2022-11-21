package com.ycx.graduation_project.product;

import com.ycx.graduation_project.product.entity.BrandEntity;
import com.ycx.graduation_project.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    void contextLoads() {

        BrandEntity byId = brandService.getById(1);
        System.out.println(byId);
    }

}

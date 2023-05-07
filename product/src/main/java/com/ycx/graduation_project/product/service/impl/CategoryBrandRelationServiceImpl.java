package com.ycx.graduation_project.product.service.impl;

import com.ycx.graduation_project.product.entity.BrandEntity;
import com.ycx.graduation_project.product.service.BrandService;
import com.ycx.graduation_project.product.vo.BrandVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycx.common.utils.PageUtils;
import com.ycx.common.utils.Query;

import com.ycx.graduation_project.product.dao.CategoryBrandRelationDao;
import com.ycx.graduation_project.product.entity.CategoryBrandRelationEntity;
import com.ycx.graduation_project.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    CategoryBrandRelationDao categoryBrandRelationDao;

    @Autowired
    BrandService brandService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public List<BrandEntity> getBrandsByCatId(Long catId) {
        List<CategoryBrandRelationEntity> catelogId = categoryBrandRelationDao.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
        List<BrandEntity> res = new ArrayList<>();
        for(CategoryBrandRelationEntity entity : catelogId){
            Long brandId = entity.getBrandId();
            if(brandId != null){
                BrandEntity brand = brandService.getById(brandId);
                res.add(brand);
            }
        }
//        List<BrandEntity> collect = catelogId.stream().map(item -> {
//            Long brandId = item.getBrandId();
//            //查询品牌的详情
//            BrandEntity byId = brandService.getById(brandId);
//            return byId;
//        }).collect(Collectors.toList());
        return res;
    }


}
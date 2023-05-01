package com.ycx.graduation_project.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycx.common.utils.PageUtils;
import com.ycx.common.utils.Query;

import com.ycx.graduation_project.product.dao.CategoryDao;
import com.ycx.graduation_project.product.entity.CategoryEntity;
import com.ycx.graduation_project.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        /*
        1.查出所有分类
        2.组装出树形结构
            先找出以及分类
         */
        List<CategoryEntity> entities = baseMapper.selectList(null);
        List<CategoryEntity> level_1 = entities.stream().filter(categoryEntity -> categoryEntity.getParentCid() == 0).collect(Collectors.toList());
        for(CategoryEntity categoryEntity : level_1){
            categoryEntity.setChildren(getChildrens(categoryEntity,entities));
        }
        return level_1;
    }

    @Override
    public void removeMenusByIds(List<Long> asList) {
        // TODO 1.检查当前删除的菜单，是否被别的地方引用了
        //使用逻辑删除
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();

        //递归查询是否还有父节点
        List<Long> parentPath = findParentPath(catelogId, paths);

        //进行一个逆序排列
        Collections.reverse(parentPath);

        return (Long[]) parentPath.toArray(new Long[parentPath.size()]);
    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {

        //1、收集当前节点id
        paths.add(catelogId);

        //根据当前分类id查询信息
        CategoryEntity byId = this.getById(catelogId);
        //如果当前不是父分类
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }

        return paths;
    }


    private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all) {
        List<CategoryEntity> childrens = new ArrayList<>();
        for(CategoryEntity categoryEntity : all){
            if(root.getCatId().equals(categoryEntity.getParentCid())){
                childrens.add(categoryEntity);
                categoryEntity.setChildren(getChildrens(categoryEntity,all));
            }
        }
        return childrens;
    }

}
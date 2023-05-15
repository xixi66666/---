package com.ycx.graduation_project.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ycx.common.constant.ProductConstant;
import com.ycx.graduation_project.product.dao.AttrAttrgroupRelationDao;
import com.ycx.graduation_project.product.dao.AttrGroupDao;
import com.ycx.graduation_project.product.dao.CategoryDao;
import com.ycx.graduation_project.product.entity.AttrAttrgroupRelationEntity;
import com.ycx.graduation_project.product.entity.AttrGroupEntity;
import com.ycx.graduation_project.product.service.CategoryService;
import com.ycx.graduation_project.product.vo.AttrGroupRelationVo;
import com.ycx.graduation_project.product.vo.AttrRespVo;
import com.ycx.graduation_project.product.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycx.common.utils.PageUtils;
import com.ycx.common.utils.Query;

import com.ycx.graduation_project.product.dao.AttrDao;
import com.ycx.graduation_project.product.entity.AttrEntity;
import com.ycx.graduation_project.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Resource
    private AttrGroupDao attrGroupDao;

    @Resource
    private CategoryDao categoryDao;

    @Resource
    private CategoryService categoryService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }
    /*
    修改规格，需要先将attr中的属性传给AttrEntity ，再保留关联关系这张表：attrAttrgroupRelation
     */
    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        //1.保存基本数据
        BeanUtils.copyProperties(attr,attrEntity);
        this.save(attrEntity);
        //2.保存关联关系
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
        attrAttrgroupRelationEntity.setAttrId(attr.getAttrId());
        attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);

    }
/*
获取 商品系统---平台属性---参数规格
属性实体类里面AttrEntity中的属性不完全，所以使用一个vo：AttrRespVo 来填充一下多出来的字段
 */
    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type","base".equalsIgnoreCase(attrType)?1:0);
        String key = (String) params.get("key");
        if (catelogId != 0) {
            queryWrapper.eq("catelog_id",catelogId);
        }
        if (!StringUtils.isEmpty(key)) {
            //attr_id attr_name
            queryWrapper.and((wrapper) -> {
                wrapper.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> respVos = new ArrayList<>();
        for(AttrEntity attrEntity : records){
            /**
             * records 是查出全部类型是AttrEntity的数组
             * respVos 是将要返回的类型，是vo 多了三个属性 ：catelogName groupName catelogPath
             * 所以：
             *  1、先设置 respVos 的 catelogName ：根据 respVos 里面的属性catelogId 在category这张表进行查找 attrRespVo.setCatelogName(categoryDao.selectById(attrRespVo.getCatelogId()).getName());
             *  2、再设置 groupName ，先根据 respVos 的attrId 在 AttrAttrgroupRelation这张表中找到 attrGroupId 再根据刚找出来的attrGroupId 在 AttrGroup 这张表中找出name
             */
            AttrRespVo attrRespVo = new AttrRespVo();
            //先给基本属性赋值，使用BeanUtils.copyProperties(records,respVos);
            BeanUtils.copyProperties(attrEntity,attrRespVo);
            attrRespVo.setCatelogName(categoryDao.selectById(attrRespVo.getCatelogId()).getName());
            AttrAttrgroupRelationEntity relationEntity =
                    attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrEntity.getAttrId()));
            if(relationEntity != null && relationEntity.getAttrGroupId() != null){
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
            respVos.add(attrRespVo);
        }
        pageUtils.setList(respVos);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo respVo = new AttrRespVo();
        AttrEntity attrEntity = this.getById(attrId);
        //填充基本属性
        BeanUtils.copyProperties(attrEntity,respVo);
        //设置分组信息
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationDao.selectByAttrId(attrId);
        if(attrAttrgroupRelationEntity != null){
            respVo.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());
            AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
            respVo.setGroupName(attrGroupEntity.getAttrGroupName());
        }
        //设置分类信息
        Long catelogId = respVo.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        respVo.setCatelogPath(catelogPath);
        respVo.setCatelogName(categoryDao.selectById(catelogId).getName());

        return respVo;
    }

    /**
     * 更新 商品系统---平台属性---参数规格 因为涉及到 catelogName groupName catelogPath 这三个vo属性，需要更改相关的数据库表AttrAttrgroupRelation中的值
     * @param attr
     */
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        //更新attr表
        this.updateById(attrEntity);
        //更新AttrAttrgroupRelation表
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
        attrAttrgroupRelationEntity.setAttrId(attr.getAttrId());
        attrAttrgroupRelationDao.update(attrAttrgroupRelationEntity,new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attr.getAttrId()));

    }
    /*
    根据分组id查找关联的所有基本属性
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        //1.拿到所有的AttrAttrgroupRelationEntity
        List<AttrAttrgroupRelationEntity> entities = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
        List<Long> attrIds = new ArrayList<>();
        //2.拿到每一个attrId
        for(AttrAttrgroupRelationEntity entity : entities){
            attrIds.add(entity.getAttrId());
        }
        //3.先做一个非空判断
        if(attrIds == null || attrIds.size() == 0){
            return null;
        }
        //4.拿到属性集合
        Collection<AttrEntity> attrEntities = this.listByIds(attrIds);


        return (List<AttrEntity> ) attrEntities;
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        List<AttrAttrgroupRelationEntity> entities = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        //批量删除
        attrAttrgroupRelationDao.deleteBatchRelation(entities);

    }

    /**
     * 获取当前分组没有被关联的所有属性
     * @param params
     * @param attrgroupId
     * @return
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {

        //1、当前分组只能关联自己所属的分类里面的所有属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        //获取当前分类的id
        Long catelogId = attrGroupEntity.getCatelogId();

        //2、当前分组只能关联别的分组没有引用的属性
        //2.1）、当前分类下的其它分组
        List<AttrGroupEntity> groupEntities = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>()
                .eq("catelog_id", catelogId));

        //获取到所有的attrGroupId
        List<Long> collect = groupEntities.stream().map((item) -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());


        //2.2）、这些分组关联的属性
        List<AttrAttrgroupRelationEntity> groupId = attrAttrgroupRelationDao.selectList
                (new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));

        List<Long> attrIds = groupId.stream().map((item) -> {
            return item.getAttrId();
        }).collect(Collectors.toList());

        //2.3）、从当前分类的所有属性移除这些属性
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("catelog_id", catelogId).eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());

        if (attrIds != null && attrIds.size() > 0) {
            queryWrapper.notIn("attr_id", attrIds);
        }

        //判断是否有参数进行模糊查询
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and((w) -> {
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);

        PageUtils pageUtils = new PageUtils(page);


        return pageUtils;
    }

    @Override
    public List<Long> selectSearchAttrs(List<Long> attrIds) {

        List<Long> searchAttrIds = this.baseMapper.selectSearchAttrIds(attrIds);

        return searchAttrIds;
    }

}
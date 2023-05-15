package com.ycx.graduation_project.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycx.common.utils.PageUtils;
import com.ycx.graduation_project.product.entity.SkuInfoEntity;
import com.ycx.graduation_project.product.vo.SkuItemVo;

import java.util.List;
import java.util.Map;

/**
 * sku信息
 *
 * @author Yang Chenxi
 * @email 1253324157@gmail.com
 * @date 2022-11-21 14:26:11
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuInfo(SkuInfoEntity skuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);

    List<SkuInfoEntity> getSkusBySpuId(Long spuId);

    SkuItemVo item(Long skuId);
}


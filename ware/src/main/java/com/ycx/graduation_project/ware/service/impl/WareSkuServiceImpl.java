package com.ycx.graduation_project.ware.service.impl;

import com.ycx.graduation_project.ware.vo.SkuHasStockVo;
import com.ycx.graduation_project.ware.vo.WareSkuLockVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycx.common.utils.PageUtils;
import com.ycx.common.utils.Query;

import com.ycx.graduation_project.ware.dao.WareSkuDao;
import com.ycx.graduation_project.ware.entity.WareSkuEntity;
import com.ycx.graduation_project.ware.service.WareSkuService;
import org.springframework.util.StringUtils;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> wareSkuEntityQueryWrapper = new QueryWrapper<>();
        String sku_id = (String) params.get("skuId");
        if(!StringUtils.isEmpty(sku_id)){
            wareSkuEntityQueryWrapper.eq("sku_id",sku_id);
        }
        String ware_id = (String) params.get("wareId");
        if(!StringUtils.isEmpty(ware_id)){
            wareSkuEntityQueryWrapper.eq("ware_id",ware_id);
        }
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                wareSkuEntityQueryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {

    }

    @Override
    public List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds) {
        return null;
    }

    @Override
    public boolean orderLockStock(WareSkuLockVo vo) {
        return false;
    }

}
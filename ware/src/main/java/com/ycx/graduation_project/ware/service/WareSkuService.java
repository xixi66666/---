package com.ycx.graduation_project.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
//import com.ycx.common.to.OrderTo;
//import com.ycx.common.to.mq.StockLockedTo;
import com.ycx.common.utils.PageUtils;
import com.ycx.graduation_project.ware.entity.WareSkuEntity;
import com.ycx.graduation_project.ware.vo.SkuHasStockVo;
import com.ycx.graduation_project.ware.vo.WareSkuLockVo;
import com.ycx.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author 杨晨曦
 * @email HeJieLin@gulimall.com
 * @date 2023-05-06 19:55:33
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 添加库存
     * @param skuId
     * @param wareId
     * @param skuNum
     */
    void addStock(Long skuId, Long wareId, Integer skuNum);

    /**
     * 判断是否有库存
     * @param skuIds
     * @return
     */
    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);

    /**
     * 锁定库存
     * @param vo
     * @return
     */
    boolean orderLockStock(WareSkuLockVo vo);


    /**
     * 解锁库存
     * @param to
     */
//    void unlockStock(StockLockedTo to);
//
//    /**
//     * 解锁订单
//     * @param orderTo
//     */
//    void unlockStock(OrderTo orderTo);
}


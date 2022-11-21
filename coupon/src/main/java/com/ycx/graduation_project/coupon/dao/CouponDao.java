package com.ycx.graduation_project.coupon.dao;

import com.ycx.graduation_project.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author Yang Chenxi
 * @email 1253324157@gmail.com
 * @date 2022-11-21 16:25:43
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}

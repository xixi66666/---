package com.ycx.graduation_project.member.dao;

import com.ycx.graduation_project.member.entity.GrowthChangeHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 成长值变化历史记录
 * 
 * @author 杨晨曦
 * @email HeJieLin@gulimall.com
 * @date 2023-05-02 19:42:06
 */
@Mapper
public interface GrowthChangeHistoryDao extends BaseMapper<GrowthChangeHistoryEntity> {
	
}

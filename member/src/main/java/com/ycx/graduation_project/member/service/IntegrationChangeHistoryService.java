package com.ycx.graduation_project.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycx.common.utils.PageUtils;
import com.ycx.graduation_project.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * 积分变化历史记录
 *
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


package com.ycx.graduation_project.member.dao;

import com.ycx.graduation_project.member.entity.MemberLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员登录记录
 * 
 * @author Yang Chenxi
 * @email 1253324157@gmail.com
 * @date 2022-11-21 16:34:18
 */
@Mapper
public interface MemberLoginLogDao extends BaseMapper<MemberLoginLogEntity> {
	
}

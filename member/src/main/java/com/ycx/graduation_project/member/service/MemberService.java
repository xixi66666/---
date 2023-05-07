package com.ycx.graduation_project.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycx.common.utils.PageUtils;
import com.ycx.graduation_project.member.entity.MemberEntity;
import  com.ycx.graduation_project.member.exception.PhoneException;
import  com.ycx.graduation_project.member.exception.UsernameException;
import  com.ycx.graduation_project.member.vo.MemberUserLoginVo;
import  com.ycx.graduation_project.member.vo.MemberUserRegisterVo;
import com.ycx.graduation_project.member.vo.SocialUser;

import java.util.Map;

/**
 * 会员
 *
 * @author 杨晨曦
 * @email HeJieLin@gulimall.com
 * @date 2023-05-06 19:42:06
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 用户注册
     * @param vo
     */
    void register(MemberUserRegisterVo vo);

    /**
     * 判断邮箱是否重复
     * @param phone
     * @return
     */
    void checkPhoneUnique(String phone) throws PhoneException;

    /**
     * 判断用户名是否重复
     * @param userName
     * @return
     */
    void checkUserNameUnique(String userName) throws UsernameException;

    /**
     * 用户登录
     * @param vo
     * @return
     */
    MemberEntity login(MemberUserLoginVo vo);

    /**
     * 社交用户的登录
     * @param socialUser
     * @return
     */
    MemberEntity login(SocialUser socialUser) throws Exception;

    /**
     * 微信登录
     * @param accessTokenInfo
     * @return
     */
    MemberEntity login(String accessTokenInfo);
}


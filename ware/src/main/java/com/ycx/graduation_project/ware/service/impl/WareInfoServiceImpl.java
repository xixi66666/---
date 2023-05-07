package com.ycx.graduation_project.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycx.common.utils.PageUtils;
import com.ycx.common.utils.Query;
import com.ycx.common.utils.R;
import com.ycx.graduation_project.ware.dao.WareInfoDao;
import com.ycx.graduation_project.ware.entity.WareInfoEntity;
//import com.ycx.graduation_project.ware.feign.MemberFeignService;
import com.ycx.graduation_project.ware.service.WareInfoService;
import com.ycx.graduation_project.ware.vo.FareVo;
import com.ycx.graduation_project.ware.vo.MemberAddressVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

//    @Autowired
//    private MemberFeignService memberFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<WareInfoEntity> queryWrapper = new QueryWrapper<>();

        String key = (String) params.get("key");

        if (!StringUtils.isEmpty(key)) {
            queryWrapper.eq("id",key)
                    .or().like("name",key)
                    .or().like("address",key)
                    .or().like("areacode",key);
        }


        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public FareVo getFare(Long addrId) {
        return null;
    }

    /**
     * 计算运费
     * @param addrId
     * @return
     */
//    @Override
//    public FareVo getFare(Long addrId) {
//
//        FareVo fareVo = new FareVo();
//
//        //收获地址的详细信息
//        R addrInfo = memberFeignService.info(addrId);
//
//        MemberAddressVo memberAddressVo = addrInfo.getData("memberReceiveAddress",new TypeReference<MemberAddressVo>() {});
//
//        if (memberAddressVo != null) {
//            String phone = memberAddressVo.getPhone();
//            //截取用户手机号码最后一位作为我们的运费计算
//            //1558022051
//            String fare = phone.substring(phone.length() - 10, phone.length()-8);
//            BigDecimal bigDecimal = new BigDecimal(fare);
//
//            fareVo.setFare(bigDecimal);
//            fareVo.setAddress(memberAddressVo);
//
//            return fareVo;
//        }
//        return null;
//    }

    public static void main(String[] args) {
        String phone = "1558022051";
        String fare = phone.substring(phone.length() - 10, phone.length()-8);
        System.out.println(fare);
    }

}
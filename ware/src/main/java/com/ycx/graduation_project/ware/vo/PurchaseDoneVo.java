package com.ycx.graduation_project.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 杨晨曦
 * @createTime: 2023-05-01 20:33
 **/

@Data
public class PurchaseDoneVo {

    //@NotNull(message = "id不允许为空")
    private Long id;

    private List<PurchaseItemDoneVo> items;

}

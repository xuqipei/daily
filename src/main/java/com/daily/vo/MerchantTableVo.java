package com.daily.vo;

import com.daily.entity.DailyMerchantTable;

import java.util.List;

/**
 * 查找指定商户餐桌时返回的视图对象
 * Created by xuqipei on 17-7-17.
 */
public class MerchantTableVo {

    private List<DailyMerchantTable> merchantTableList;
    private Integer freeTableNums;//空闲的餐桌
    private Integer totalTableNums;//餐桌总数

    public List<DailyMerchantTable> getMerchantTableList() {
        return merchantTableList;
    }

    public void setMerchantTableList(List<DailyMerchantTable> merchantTableList) {
        this.merchantTableList = merchantTableList;
    }

    public Integer getFreeTableNums() {
        return freeTableNums;
    }

    public void setFreeTableNums(Integer freeTableNums) {
        this.freeTableNums = freeTableNums;
    }

    public Integer getTotalTableNums() {
        return totalTableNums;
    }

    public void setTotalTableNums(Integer totalTableNums) {
        this.totalTableNums = totalTableNums;
    }
}

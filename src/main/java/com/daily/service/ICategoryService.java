package com.daily.service;

import com.daily.common.ServerResponse;
import com.daily.entity.DailyCategory;

/**
 * Created by Administrator on 2017/7/18.
 */
public interface ICategoryService {
    ServerResponse getCategoryById(Integer id);

    ServerResponse getCategorysByMerchantId(Integer merchantId);

    ServerResponse addCategory(DailyCategory dailyCategory);

    ServerResponse updateCategory(DailyCategory dailyCategory);

    ServerResponse deleteCategory(Integer id);
}

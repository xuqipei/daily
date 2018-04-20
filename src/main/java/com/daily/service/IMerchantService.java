package com.daily.service;

import com.daily.common.ServerResponse;
import com.daily.entity.DailyMerchant;

public interface IMerchantService {
    ServerResponse findAllMerchant(Integer pageNum, Integer pageSize);

    ServerResponse login(String username, String password);

    ServerResponse deleteMerchant(Integer merchantId);

    ServerResponse updateMerchant(DailyMerchant dailyMerchant);
}

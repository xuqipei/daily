package com.daily.service;

import com.daily.common.ServerResponse;
import com.daily.entity.DailyMerchantTable;
import com.daily.entity.DailyUser;

public interface IMerchantTableService {
    ServerResponse getTableByMerchantId(Integer merchantId);

    ServerResponse useTable(DailyUser dailyUser, Integer merchantId, Integer tableId, Integer sitNum);

    ServerResponse addPeopleNum(DailyUser dailyUser, Integer merchantId, Integer tableId, Integer addNum);

    ServerResponse addTable(Integer merchantId, String tableNo, Integer capacity);

    ServerResponse updateTable(DailyMerchantTable dailyMerchantTable);

    ServerResponse deleteTable(Integer tableId);

    ServerResponse freeTable(Integer tableId);

    ServerResponse reserveTable(DailyUser dailyUser, Integer tableId);

    ServerResponse unpayToRun();

    ServerResponse getCurrentTable(String username);

    ServerResponse deleteCurrentTable(Integer tableId);

    boolean isArrived(Integer tableId);

    ServerResponse unUseTable(DailyUser dailyUser, Integer merchantId, Integer tableId);
}

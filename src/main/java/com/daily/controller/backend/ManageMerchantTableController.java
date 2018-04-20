package com.daily.controller.backend;

import com.daily.common.Const;
import com.daily.common.ServerResponse;
import com.daily.entity.DailyMerchantTable;
import com.daily.entity.DailyUser;
import com.daily.service.IMerchantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/table")
public class ManageMerchantTableController {

    @Autowired
    private IMerchantTableService iMerchantTableService;

    @GetMapping("/get_all/{merchantId}")
    public ServerResponse adminGetAllTableByMerchanId(@PathVariable("merchantId") Integer merchantId) {
        return iMerchantTableService.getTableByMerchantId(merchantId);
    }

    @PostMapping("/add")
    public ServerResponse adminAddTable(Integer merchantId, String tableNo, Integer capacity) {
        return iMerchantTableService.addTable(merchantId, tableNo, capacity);
    }

    @PutMapping("update")
    public ServerResponse adminUpdateTable(DailyMerchantTable dailyMerchantTable) {
        return iMerchantTableService.updateTable(dailyMerchantTable);
    }

    @DeleteMapping("delete/{tableId}")
    public ServerResponse adminDeleteTable(@PathVariable("tableId") Integer tableId) {
        return iMerchantTableService.deleteTable(tableId);
    }
}

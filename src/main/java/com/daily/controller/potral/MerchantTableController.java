package com.daily.controller.potral;

import com.daily.common.Const;
import com.daily.common.ServerResponse;
import com.daily.entity.DailyUser;
import com.daily.service.IMerchantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/table")
public class MerchantTableController {

    @Autowired
    private IMerchantTableService iMerchantTableService;

    @GetMapping("/current_selected")
    public ServerResponse aopGetCurrentTable(HttpSession session) {
        DailyUser dailyUser = (DailyUser) session.getAttribute(Const.CURRENT_USER);
        return iMerchantTableService.getCurrentTable(dailyUser.getUsername());
    }

    @GetMapping("/get_all/{merchantId}")
    public ServerResponse getAllTableByMerchanId(@PathVariable("merchantId") Integer merchantId) {
        return iMerchantTableService.getTableByMerchantId(merchantId);
    }

    @GetMapping("/run")
    public ServerResponse aopRun() {
        return iMerchantTableService.unpayToRun();
    }

    @PostMapping("/use/{merchantId}/{tableId}")
    public ServerResponse aopUseTable(@PathVariable("merchantId") Integer merchantId, @PathVariable("tableId") Integer tableId, Integer peopleNumber, HttpSession session) {
        DailyUser dailyUser = (DailyUser) session.getAttribute(Const.CURRENT_USER);
        return iMerchantTableService.useTable(dailyUser, merchantId, tableId, peopleNumber);
    }

    @PutMapping("reserve/{tableId}")
    public ServerResponse aopReserveTable(@PathVariable("tableId") Integer tableId, HttpSession session) {
        DailyUser dailyUser = (DailyUser) session.getAttribute(Const.CURRENT_USER);
        return iMerchantTableService.reserveTable(dailyUser, tableId);
    }

    @PutMapping("add_people/{merchantId}/{tableId}")
    public ServerResponse aopUpdateTable(@PathVariable("merchantId") Integer merchantId, @PathVariable("tableId") Integer tableId, Integer peopleNumber, HttpSession session) {
        DailyUser dailyUser = (DailyUser) session.getAttribute(Const.CURRENT_USER);

        return iMerchantTableService.addPeopleNum(dailyUser, merchantId, tableId, peopleNumber);
    }

    @PutMapping("un_use")
    public ServerResponse aopUnUseTable(Integer merchantId, Integer tableId, HttpSession session) {
        DailyUser dailyUser = (DailyUser) session.getAttribute(Const.CURRENT_USER);
        return iMerchantTableService.unUseTable(dailyUser, merchantId, tableId);
    }

}

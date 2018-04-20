package com.daily.controller.backend;

import com.daily.common.Const;
import com.daily.common.ServerResponse;
import com.daily.entity.DailyMerchant;
import com.daily.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by xuqipei on 17-7-27.
 */
@RestController
@RequestMapping("manage/merchant")
public class ManageMerchantController {

    @Autowired
    private IMerchantService iMerchantService;

    @PostMapping("/login")
    public ServerResponse login(String username, String password, HttpSession session) {
        ServerResponse serverResponse = iMerchantService.login(username, password);
        if (serverResponse.isSuccess()) {
            session.setAttribute(Const.CURRENT_ADMIN, serverResponse.getData());
        }
        return serverResponse;
    }

    @GetMapping("/get_information")
    public ServerResponse getInformation(HttpSession session) {
        DailyMerchant dailyMerchant = (DailyMerchant) session.getAttribute(Const.CURRENT_ADMIN);
        if (dailyMerchant == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return ServerResponse.createBySuccess(dailyMerchant);
    }

    @GetMapping("/logout")
    public ServerResponse logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_ADMIN);
        return ServerResponse.createBySuccess();
    }

    @DeleteMapping("/delete/{merchantId}")
    public ServerResponse adminDeleteMerchant(@PathVariable("merchantId") Integer merchantId) {
        return iMerchantService.deleteMerchant(merchantId);
    }

    @PutMapping("/update")
    public ServerResponse adminUpdateMerchant(DailyMerchant dailyMerchant, HttpSession session) {
        DailyMerchant merchant = (DailyMerchant) session.getAttribute(Const.CURRENT_ADMIN);
        dailyMerchant.setId(merchant.getId());
        ServerResponse serverResponse = iMerchantService.updateMerchant(dailyMerchant);
        if (serverResponse.isSuccess()) {
            session.setAttribute(Const.CURRENT_ADMIN, serverResponse.getData());
        }
        return serverResponse;
    }
}

package com.daily.controller.potral;

import com.daily.common.Const;
import com.daily.common.ServerResponse;
import com.daily.entity.DailyUser;
import com.daily.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuqipei on 17-7-19.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    @PostMapping("/createOrder")
    public ServerResponse create(HttpSession session, Integer merchantId, Integer tableId, String menuId, String count) {

        DailyUser dailyUser = (DailyUser) session.getAttribute(Const.CURRENT_USER);
        if (dailyUser == null) {
            return ServerResponse.createByErrorMessage("请登录后再下单！");
        }
        return iOrderService.createOrder(menuId, count, merchantId, dailyUser.getId(), tableId);
    }

    @GetMapping("/getOrder")
    public ServerResponse getOrder(long orderNo) {
        return iOrderService.getOrderVo(orderNo);
    }

    @GetMapping("/userOrderList")
    public ServerResponse getUserOrderList(HttpSession session) {
        DailyUser dailyUser = (DailyUser) session.getAttribute(Const.CURRENT_USER);
        if (dailyUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iOrderService.getUserOrderList(dailyUser.getId());
    }
}

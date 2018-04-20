package com.daily.controller.backend;

import com.daily.common.ServerResponse;
import com.daily.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xuqipei on 17-7-26.
 */
@RestController
@RequestMapping("/manage/order")
public class ManageOrderController {

    @Autowired
    private IOrderService iOrderService;

    @GetMapping("/merchantOrderList")
    public ServerResponse adminGetMerchantOrderList(Integer merchantId) {
        return iOrderService.getMerchantOrderList(merchantId);
    }

    @DeleteMapping("/delete/{orderId}")
    public ServerResponse adminDeleteOrder(@PathVariable("orderId") Integer orderId) {
        System.out.println("删除操作");
        return iOrderService.deleteOrderById(orderId);
    }

    @GetMapping("/getOrder")
    public ServerResponse adminGetOrder(long orderNo) {
        return iOrderService.getOrderVo(orderNo);
    }
}

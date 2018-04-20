package com.daily.service;


import com.daily.common.ServerResponse;

import java.util.Map;

/**
 * Created by xuqipei on 17-7-19.
 */
public interface IOrderService {
    ServerResponse createOrder(String menuId, String count, Integer merchantId, Integer userID, Integer tableId);

    ServerResponse getOrderVo(long order_no);
    ServerResponse getUserOrderList(Integer user_Id);
    ServerResponse getMerchantOrderList(Integer merchanr_id);

    ServerResponse deleteOrderById(Integer orderId);
}

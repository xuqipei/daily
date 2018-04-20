package com.daily.repository;

import com.daily.entity.DailyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.List;

/**
 * Created by xuqipei on 17-7-20.
 */
public interface OrderRepository extends JpaRepository<DailyOrder, Integer> {
    DailyOrder getByOrderNo(long orderId);

    List<DailyOrder> getAllByUserId(Integer user_Id);

    List<DailyOrder> getAllByMerchantId(Integer merchant_id);

    DailyOrder getByMerchantId(Integer merchantId);

    int countByUserIdAndStatus(Integer userId, Integer status);

    int countByUserIdAndTableIdAndStatus(Integer userId, Integer tableId, Integer status);
}
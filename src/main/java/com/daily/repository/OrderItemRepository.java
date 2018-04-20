package com.daily.repository;

import com.daily.entity.DailyOrder;
import com.daily.entity.DailyOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by xuqipei on 17-7-20.
 */
public interface OrderItemRepository extends JpaRepository<DailyOrderItem,Integer>{

    List<DailyOrderItem> findAllByOrderNo(long orderNO);

}

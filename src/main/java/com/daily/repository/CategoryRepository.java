package com.daily.repository;

import com.daily.entity.DailyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */
public interface CategoryRepository extends JpaRepository<DailyCategory, Integer> {
    List<DailyCategory> findByMerchantId(Integer merchantId);

    @Modifying
    @Query("select d from DailyCategory d where d.merchantId=?1 and d.name=?2")
    int findByNameAndMerchantId(Integer merchantId, String name);
}

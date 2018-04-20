package com.daily.repository;

import com.daily.entity.DailyMerchantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MerchantTableRepository extends JpaRepository<DailyMerchantTable, Integer> {
    List<DailyMerchantTable> findByMerchantId(int merchantId);

    DailyMerchantTable findByIdAndMerchantId(int id, int merchantId);

    @Modifying
    @Query("update DailyMerchantTable d set d.bookStatus=?2 where d.id=?1")
    int updateStatusById(Integer id, Integer status);

    @Modifying
    @Query("update DailyMerchantTable d set d.sitReal=?2 where d.id=?1")
    int updateSitRealById(Integer id, Integer sitReal);

    @Modifying
    @Query("update DailyMerchantTable d SET d.bookStatus=?1, d.sitReal=?2")
    int updateBookStatusAndSitReal(Integer bookStatus, Integer sitReal);
}

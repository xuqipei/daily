package com.daily.repository;

import com.daily.entity.DailyMerchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<DailyMerchant, Integer> {
    DailyMerchant findByLoginNameAndLoginPassword(String username, String password);
}

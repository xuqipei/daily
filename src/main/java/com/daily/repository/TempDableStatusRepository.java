package com.daily.repository;

import com.daily.entity.TempTableStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xuqipei on 17-7-26.
 */
public interface TempDableStatusRepository extends JpaRepository<TempTableStatus, Integer> {

    TempTableStatus findByUsername(String username);

    int deleteByTableId(Integer tableId);
}

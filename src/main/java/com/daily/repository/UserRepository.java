package com.daily.repository;

import com.daily.entity.DailyUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xuqipei on 17-7-18.
 */
public interface UserRepository extends JpaRepository<DailyUser,Integer> {

    DailyUser findByUsernameAndPassword(String username,String password);
    DailyUser findByUsername(String username);
    int deleteByUsername(String username);
}

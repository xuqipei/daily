package com.daily.repository;

import com.daily.entity.DailyCategory;
import com.daily.entity.DailyMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */
public interface MenuRepository extends JpaRepository<DailyMenu, Integer> {
    List<DailyMenu> findByCategoryId(Integer categoryId);

    List<DailyMenu> findByCategoryIdIn(Collection<Integer> categoryIds);
}

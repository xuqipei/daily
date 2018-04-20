package com.daily.controller.backend;

import com.daily.common.ServerResponse;
import com.daily.entity.DailyCategory;
import com.daily.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/7/18.
 */
@RestController
@RequestMapping("/manage/category")
public class ManageCategoryController {
    @Autowired
    private ICategoryService icategoryService;

    //根据菜系ID获取菜系
    @GetMapping("/get_one/{id}")
    public ServerResponse adminGetGategoryById(@PathVariable("id") Integer id) {
        return icategoryService.getCategoryById(id);
    }

    //根据商家ID获取所有菜系
    @GetMapping("/get_all/{merchantId}")
    public ServerResponse adminGetAllGategoryByMerchanId(@PathVariable("merchantId") Integer merchantId) {
        return icategoryService.getCategorysByMerchantId(merchantId);
    }

    //增加菜系
    @PostMapping("/add")
    public ServerResponse adminAddCategory(DailyCategory dailyCategory) {
        return icategoryService.addCategory(dailyCategory);
    }

    //根据商家ID，提供菜系对象修改菜系
    @PutMapping("/update")
    public ServerResponse adminUpdateGategory(DailyCategory dailyCategory) {
        return icategoryService.updateCategory(dailyCategory);
    }

    //根据菜系ID删除菜系
    @DeleteMapping("/delete/{id}")
    public ServerResponse adminDeleteGategory(@PathVariable("id") Integer id) {
        return icategoryService.deleteCategory(id);
    }
}

package com.daily.controller.potral;

import com.daily.common.ServerResponse;
import com.daily.entity.DailyCategory;
import com.daily.repository.CategoryRepository;
import com.daily.service.ICategoryService;
import com.daily.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/7/18.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService icategoryService;

    @GetMapping("/get_all/{merchantId}")
    public ServerResponse getAllGategoryByMerchanId(@PathVariable("merchantId") Integer merchantId) {
        return icategoryService.getCategorysByMerchantId(merchantId);
    }

}

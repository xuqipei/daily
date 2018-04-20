package com.daily.controller.potral;

import com.daily.common.ServerResponse;
import com.daily.service.IMenuService;
import com.daily.service.IMerchantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/7/18.
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService iMenuService;

    @GetMapping("/get_all/{categoryId}")
    public ServerResponse getAllMenuByCategoryId(@PathVariable("categoryId") Integer categoryId) {
        return iMenuService.getMenusByCategoryId(categoryId);
    }

    @GetMapping("/get_all")
    public ServerResponse getAllMenuByCategoryIds(String categoryIds) {
        return iMenuService.getMenusByCategoryIds(categoryIds);
    }
}

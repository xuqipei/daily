package com.daily.controller.backend;

import com.daily.common.ServerResponse;
import com.daily.entity.DailyMenu;
import com.daily.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017/7/18.
 */
@RestController
@RequestMapping("/manage/menu")
public class ManageMenuController {
    @Autowired
    private IMenuService iMenuService;

    //根据菜系ID获取所有菜品
    @GetMapping("/get_all/{categoryId}")
    public ServerResponse adminGetAllMenuByCategoryId(@PathVariable("categoryId") Integer categoryId) {
        return iMenuService.getMenusByCategoryId(categoryId);
    }

    //根据菜品ID获取菜品
    @GetMapping("/get_one/{id}")
    public ServerResponse adminGetMenuById(@PathVariable("id") Integer id) {
        return iMenuService.getMenuById(id);
    }

    //传入菜单对象增加菜品
    @PostMapping("/add")
    public ServerResponse adminAddMenu(DailyMenu dailyMenu) {
        return iMenuService.addMenu(dailyMenu);
    }

    //根据菜品ID传入菜品对象修改菜品
    @PutMapping("/update")
    public ServerResponse adminUpdateMenu(DailyMenu dailyMenu) {
        return iMenuService.updateMenu(dailyMenu);
    }

    //根据菜品ID删除菜品
    @DeleteMapping("/delete/{id}")
    public ServerResponse adminDeleteMenu(@PathVariable("id") Integer id) {
        return iMenuService.deleteMenu(id);
    }

    //传入MultipartFile获取图片URL
    @PostMapping("/upload")
    public ServerResponse adminGetBannerUrl(MultipartFile multipartFile) {
        return iMenuService.getBannerUrl(multipartFile);
    }
}

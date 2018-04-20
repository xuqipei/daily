package com.daily.service.impl;

import com.avos.avoscloud.AVException;
import com.daily.common.ResponseCode;
import com.daily.common.ServerResponse;
import com.daily.entity.DailyMenu;
import com.daily.repository.MenuRepository;
import com.daily.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.daily.leancloud.Leancloud.uploadImages;


/**
 * Created by Administrator on 2017/7/18.
 */
@Service("iMenuService")
public class MenuService implements IMenuService {
    @Autowired
    private MenuRepository menuRepository;

    public ServerResponse getMenuById(Integer id) {
        if (id == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        DailyMenu dailyMenu = menuRepository.findOne(id);
        return ServerResponse.createBySuccess(dailyMenu);
    }

    public ServerResponse getMenusByCategoryId(Integer categoryId) {
        if (categoryId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<DailyMenu> dailyMenuList = menuRepository.findByCategoryId(categoryId);
        return ServerResponse.createBySuccess(dailyMenuList);
    }

    public ServerResponse getMenusByCategoryIds(String ids) {
        if (ids == null || ids == "") {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        String[] categoryIds = ids.split(",");

        List<DailyMenu> dailyMenuList = new ArrayList<>();

        if (categoryIds.length == 0) {
            return ServerResponse.createBySuccess(dailyMenuList);
        }

        List<Integer> categoryIdsInteger = new ArrayList<>();
        for (String id : categoryIds) {
            categoryIdsInteger.add(Integer.valueOf(id));
        }

        dailyMenuList = menuRepository.findByCategoryIdIn(categoryIdsInteger);
        return ServerResponse.createBySuccess(dailyMenuList);
    }

    public ServerResponse addMenu(DailyMenu dailyMenu){
        if (dailyMenu.getName()==null || dailyMenu.getCategoryId()==null || dailyMenu.getPrice()==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try {
            menuRepository.save(dailyMenu);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("添加失败: " + e.getMessage());
        }
        return ServerResponse.createBySuccessMessage("添加成功");
    }

    public ServerResponse updateMenu(DailyMenu dailyMenu){
        if (dailyMenu == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try {
            menuRepository.save(dailyMenu);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("修改菜品信息失败: " + e.getMessage());
        }
        return ServerResponse.createBySuccessMessage("修改菜品信息成功");
    }

    public ServerResponse deleteMenu(Integer id) {
        if (id == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try {
            menuRepository.delete(id);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("删除失败: " + e.getMessage());
        }
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    public ServerResponse getBannerUrl(MultipartFile multipartFile) {
        if (multipartFile == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        String returnUrl = null;
        try {
            returnUrl = uploadImages(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("上传图片失败: " + e.getMessage());
        }
        return ServerResponse.createBySuccessMessage(returnUrl);
    }
}

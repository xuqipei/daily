package com.daily.service.impl;

import com.daily.common.ResponseCode;
import com.daily.common.ServerResponse;
import com.daily.entity.DailyCategory;
import com.daily.repository.CategoryRepository;
import com.daily.repository.MerchantTableRepository;
import com.daily.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2017/7/18.
 */
@Service("iCategoryService")
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public ServerResponse getCategoryById(Integer id) {
        if (id == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        DailyCategory category = categoryRepository.findOne(id);
        return ServerResponse.createBySuccess(category);
    }

    public ServerResponse getCategorysByMerchantId(Integer merchantId) {
        if (merchantId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<DailyCategory> categoryList = categoryRepository.findByMerchantId(merchantId);
        return ServerResponse.createBySuccess(categoryList);
    }

    public ServerResponse addCategory(DailyCategory dailyCategory) {
        if (dailyCategory == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try {
            categoryRepository.save(dailyCategory);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("添加失败: " + e.getMessage());
        }
        return ServerResponse.createBySuccessMessage("添加成功");
    }

    public ServerResponse updateCategory(DailyCategory dailyCategory) {
        if (dailyCategory == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try {
            categoryRepository.save(dailyCategory);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("修改菜系信息失败: " + e.getMessage());
        }
        return ServerResponse.createBySuccessMessage("修改菜系信息成功");
    }

    public ServerResponse deleteCategory(Integer id) {
        if (id == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try {
            categoryRepository.delete(id);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("删除失败: " + e.getMessage());
        }
        return ServerResponse.createBySuccessMessage("删除成功");
    }
}
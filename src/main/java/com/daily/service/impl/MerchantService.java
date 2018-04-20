package com.daily.service.impl;

import com.daily.common.ServerResponse;
import com.daily.entity.DailyMerchant;
import com.daily.repository.MerchantRepository;
import com.daily.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service("iMerchantService")
public class MerchantService implements IMerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    public ServerResponse findAllMerchant(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<DailyMerchant> merchantPage = merchantRepository.findAll(pageRequest);
        return ServerResponse.createBySuccess(merchantPage);
    }

    public ServerResponse login(String username, String password) {
        try {
            DailyMerchant dailyMerchant = merchantRepository.findByLoginNameAndLoginPassword(username, password);
            if (dailyMerchant != null) {
                dailyMerchant.setLoginPassword(null);
                return ServerResponse.createBySuccess(dailyMerchant);
            } else {
                return ServerResponse.createByErrorMessage("登录失败, 用户名或密码错误!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("登录失败, 系统内部错误");
        }
    }

    public ServerResponse updateMerchant(DailyMerchant dailyMerchant) {
        if (dailyMerchant.getMerchantName().length() == 0) {
            return ServerResponse.createBySuccessMessage("没有做任何修改");
        }
        DailyMerchant merchant = merchantRepository.findOne(dailyMerchant.getId());
        merchant.setMerchantName(dailyMerchant.getMerchantName());
        merchant.setBannerUrl(dailyMerchant.getBannerUrl());
        merchant.setAddress(dailyMerchant.getAddress());
        merchant.setContact(dailyMerchant.getContact());
        merchantRepository.saveAndFlush(merchant);
        return ServerResponse.createBySuccessMessage("修改成功, 请重新登录");
    }

    public ServerResponse deleteMerchant(Integer merchantId) {
        merchantRepository.delete(merchantId);
        return ServerResponse.createBySuccessMessage("删除成功");
    }
}

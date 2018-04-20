package com.daily.service;

import com.avos.avoscloud.AVException;
import com.daily.common.ServerResponse;
import com.daily.entity.DailyCategory;
import com.daily.entity.DailyMenu;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.spi.ServiceRegistry;
import javax.swing.plaf.multi.MultiFileChooserUI;

/**
 * Created by Administrator on 2017/7/18.
 */
public interface IMenuService {
    ServerResponse getMenuById(Integer id);

    ServerResponse getMenusByCategoryId(Integer categoryId);

    ServerResponse addMenu(DailyMenu dailyMenu);

    ServerResponse updateMenu(DailyMenu dailyMenu);

    ServerResponse deleteMenu(Integer id);

    ServerResponse getBannerUrl(MultipartFile multipartFile);

    ServerResponse getMenusByCategoryIds(String ids);
}

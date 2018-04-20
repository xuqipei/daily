package com.daily.controller.potral;

import com.daily.common.ServerResponse;
import com.daily.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private IMerchantService iMerchantService;

    @GetMapping("/get_all")
    public ServerResponse getAll(@RequestParam(value = "pageNum", defaultValue = "0", required = false) Integer pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "6", required = false) Integer pageSize) {
        return iMerchantService.findAllMerchant(pageNum, pageSize);
    }
}

package com.daily.controller.potral;

import com.daily.common.Const;
import com.daily.common.ServerResponse;
import com.daily.entity.DailyUser;
import com.daily.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuqipei on 17-7-18.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    /**
     * 注销
     */
    @GetMapping("/logout")
    public ServerResponse logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public ServerResponse checkUser(HttpSession session, String username, String password) {
        ServerResponse serverResponse = iUserService.checkUser(username, password);
        if (serverResponse.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, serverResponse.getData());
        }
        return serverResponse;
    }

    /**
     * 注册
     */
    @PostMapping("/addUser")
    public ServerResponse addUser(String username, String password, String mobilephone,
                                  String nickname, Integer age, String gender) {
        return iUserService.addUser(username, password, mobilephone, nickname, age, gender);
    }

    /**
     * 通过用户名查找单用户
     */
    @PostMapping("/findByUsername")
    public ServerResponse findByUsername(String username) {
        return iUserService.findByUsername(username);
    }

    /**
     * 检查用户是否存在
     */
    @PostMapping("/valid")
    public Object validUser(String username) {
        ServerResponse serverResponse = iUserService.findByUsername(username);
        Map<String, Boolean> result = new HashMap<>();
        result.put("valid", !serverResponse.isSuccess());
        return result;
    }

    /**
     * 通过用户Id查找用户
     */
    @PostMapping("/findOne/{userId}")
    public ServerResponse findOne(@PathVariable("userId") Integer userId) {
        return iUserService.findOne(userId);
    }

    /**
     * 用户列表
     */
    @PostMapping("/userList")
    public ServerResponse userList() {
        return iUserService.userList();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/deleteUser/{userId}")
    public ServerResponse deleteUser(@PathVariable("userId") Integer userId) {
        return iUserService.deleteUser(userId);
    }

    /**
     * 更新用户
     */
    @PutMapping("/updateUser")
    public ServerResponse updateUser(String nickname, String gender, Integer age, String mobilephone) {
        return iUserService.updateUser(nickname, gender, age, mobilephone);
    }

    /**
     * 获取已登录的用户(如果存在)
     */
    @GetMapping("/get_info")
    public ServerResponse getUserInfo(HttpSession session) {
        DailyUser dailyUser = (DailyUser) session.getAttribute(Const.CURRENT_USER);
        if (dailyUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录～");
        }
        return ServerResponse.createBySuccess(dailyUser);
    }


}

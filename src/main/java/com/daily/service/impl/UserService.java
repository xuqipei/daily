package com.daily.service.impl;

import com.daily.common.ResponseCode;
import com.daily.common.ServerResponse;
import com.daily.entity.DailyUser;
import com.daily.repository.UserRepository;
import com.daily.service.IUserService;
import com.daily.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuqipei on 17-7-18.
 */
@Service("iUserService")
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    public ServerResponse checkUser(String username, String password) {
        if (username == null || password == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        DailyUser dailyUser = userRepository.findByUsernameAndPassword(username, password);
        if (dailyUser == null) {
            return ServerResponse.createByErrorMessage("密码或用户名错误！");
        }
        dailyUser.setPassword(null);
        return ServerResponse.createBySuccess(dailyUser);
    }

    public ServerResponse addUser(String username, String password, String mobilephone, String nickname, Integer age, String gender) {
        DailyUser dailyUser = new DailyUser();
        dailyUser.setUsername(username);
        dailyUser.setPassword(password);
        dailyUser.setMobilephone(mobilephone);
        dailyUser.setNickname(nickname);
        dailyUser.setAge(age);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dailyUser.setCreateTime(timestamp);
        dailyUser.setBrithday(timestamp);
        dailyUser.setGender(gender);
        userRepository.save(dailyUser);
        return ServerResponse.createBySuccess(dailyUser);
    }

    public ServerResponse deleteUser(Integer userId) {

        if (userId == null) {
            return ServerResponse.createByErrorMessage("操作失败！");
        }
        userRepository.delete(userId);
        return ServerResponse.createBySuccess("成功删除！");
    }


    /**
     * 通过用户名查找用户
     *
     * @param username
     * @return
     */
    public ServerResponse findByUsername(String username) {
        DailyUser dailyUser = userRepository.findByUsername(username);
        if (dailyUser == null) {
            return ServerResponse.createByErrorMessage("没有该用户！");
        }
        return ServerResponse.createBySuccess(this.getUserVo(dailyUser.getId()));
    }

    /**
     * 通过用户Id查找用户
     *
     * @param userId
     * @return
     */
    public ServerResponse findOne(Integer userId) {
        if (userId == null) {
            return ServerResponse.createByErrorMessage("请输入用户Id！");
        }
        UserVo userVo = this.getUserVo(userId);
        if (userVo == null) {
            return ServerResponse.createByErrorMessage("没有该用户");
        }
        return ServerResponse.createBySuccess(userVo);
    }

    public UserVo getUserVo(Integer userId) {
        DailyUser dailyUser = userRepository.findOne(userId);
        UserVo userVo = new UserVo();
        if (dailyUser != null) {
            userVo.setId(dailyUser.getId());//id
            userVo.setUsername(dailyUser.getUsername());//用户名
            userVo.setNickname(dailyUser.getNickname());//昵称
            userVo.setAge(dailyUser.getAge());//年龄
            userVo.setGender(dailyUser.getGender());//性别
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String birthDay = date.format(dailyUser.getBrithday());
            userVo.setBrithday(birthDay);//生日
            userVo.setMobilephone(dailyUser.getMobilephone());//电话
            return userVo;

        }
        return null;
    }

    /**
     * 获取用户列表
     *
     * @return
     */
    @Transactional
    public ServerResponse userList() {
        List<DailyUser> dailyUsers = userRepository.findAll();
        List<UserVo> list = new ArrayList<>();
        for (DailyUser dailyUser : dailyUsers) {
            list.add(this.getUserVo(dailyUser.getId()));
        }
        return ServerResponse.createBySuccess(list);
    }

    /**
     * 更新用户
     *
     * @param nickname
     * @param gender
     * @param age
     * @param mobilephone
     * @return
     */
    @Transactional
    @Override
    public ServerResponse updateUser(String nickname, String gender, Integer age, String mobilephone) {
        if (nickname == null || gender == null || age == null || mobilephone == null) {
            return ServerResponse.createByErrorMessage("操作失败！");
        }
        DailyUser dailyUser = new DailyUser();
        return null;
    }

}

package com.daily.service;

import com.daily.common.ServerResponse;

/**
 * Created by xuqipei on 17-7-18.
 */
public interface IUserService {
    ServerResponse checkUser(String username,String password);
    ServerResponse addUser(String username, String password, String mobilephone, String nikname, Integer age,String gender);
    ServerResponse deleteUser(Integer userId);
    ServerResponse findByUsername(String username);
    ServerResponse findOne(Integer userId);
    ServerResponse userList();
    ServerResponse updateUser(String nickname,String gender,Integer age,String mobilephone);

}

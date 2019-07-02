package com.qf.service;

import com.qf.entity.BackUser;

import java.util.List;

public interface IBackUserService {

    List<BackUser> queryAll();

    int insertUser(BackUser backUser);

    int updateUserRoles(Integer uid, Integer[] rid);

    BackUser login(String username, String password);
}

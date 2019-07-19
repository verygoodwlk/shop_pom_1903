package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.pass.BCryptUtil;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/18 14:25
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int register(User user) {

        //判断用户名是否唯一
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        User u = userMapper.selectOne(queryWrapper);

        if(u != null){
            return -1;//用户名已经存在
        }

        QueryWrapper queryWrapper2 = new QueryWrapper();
        queryWrapper.eq("email", user.getEmail());
        User u2 = userMapper.selectOne(queryWrapper);

        if(u2 != null){
            return -2;//邮箱已经注册
        }

        user.setPassword(BCryptUtil.hashPassword(user.getPassword()));

        //可以注册
        return userMapper.insert(user);
    }

    @Override
    public User queryByUserName(String username) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);

        return user;
    }

    @Override
    public int updatePassword(String username, String password) {
        User user = queryByUserName(username);
        user.setPassword(BCryptUtil.hashPassword(password));
        return userMapper.updateById(user);
    }

    @Override
    public User login(User user) {

        User u = queryByUserName(user.getUsername());

        if(u != null){

            //用户名正确，接下来校验密码
            boolean flag = BCryptUtil.checkPassword(u.getPassword(), user.getPassword());
            if(flag){
                //登录成功
                return u;
            }
        }

        return null;
    }
}

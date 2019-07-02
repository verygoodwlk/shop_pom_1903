package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.BackUserMapper;
import com.qf.dao.UserRoleMapper;
import com.qf.entity.BackUser;
import com.qf.entity.UserRoleTable;
import com.qf.service.IBackUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/1 15:19
 */
@Service
public class BackUserServiceImpl implements IBackUserService {

    @Autowired
    private BackUserMapper backUserMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<BackUser> queryAll() {
        return backUserMapper.selectList(null);
    }

    @Override
    public int insertUser(BackUser backUser) {
        return backUserMapper.insert(backUser);
    }

    @Override
    @Transactional
    public int updateUserRoles(Integer uid, Integer[] rid) {

        //根据用户id删除用户和角色的所有关系
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        userRoleMapper.delete(queryWrapper);

        //将当前的用户和角色关系进行保存
        for (Integer roleid : rid) {
            UserRoleTable userRoleTable = new UserRoleTable(uid, roleid);
            userRoleMapper.insert(userRoleTable);
        }

        return 1;
    }
}

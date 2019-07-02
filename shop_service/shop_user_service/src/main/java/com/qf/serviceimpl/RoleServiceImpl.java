package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.RoleMapper;
import com.qf.dao.RolePowerMapper;
import com.qf.entity.Role;
import com.qf.entity.RolePowerTable;
import com.qf.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/1 19:56
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePowerMapper rolePowerMapper;

    @Override
    public List<Role> roleList() {
        return roleMapper.selectList(null);
    }

    @Override
    public int insertRole(Role role) {
        return roleMapper.insert(role);
    }

    /**
     * 根据用户id查询所有权限，并且查询出当前用户拥有哪些权限
     * @param uid
     * @return
     */
    @Override
    public List<Role> roleListByUid(Integer uid) {
        return roleMapper.queryListByUid(uid);
    }

    /**
     * 修改角色拥有的权限
     * @param rid
     * @param pids
     * @return
     */
    @Override
    @Transactional
    public int updateRolePowers(Integer rid, Integer[] pids) {

        //根据角色删除该角色所拥有的权限
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("rid", rid);
        rolePowerMapper.delete(queryWrapper);

        //添加角色和权限的关系
        for (Integer pid : pids) {
            rolePowerMapper.insert(new RolePowerTable(rid, pid));
        }

        return 1;
    }
}

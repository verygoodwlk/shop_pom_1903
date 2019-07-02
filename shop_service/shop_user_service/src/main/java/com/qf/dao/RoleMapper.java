package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Role;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/1 19:57
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> queryListByUid(Integer uid);
}

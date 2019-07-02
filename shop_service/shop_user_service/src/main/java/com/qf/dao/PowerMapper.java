package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Power;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/2 9:15
 */
public interface PowerMapper extends BaseMapper<Power> {

    List<Power> queryAllPowers();
}

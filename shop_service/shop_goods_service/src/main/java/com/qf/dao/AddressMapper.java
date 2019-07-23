package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Address;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/23 10:16
 */
public interface AddressMapper extends BaseMapper<Address> {

    int insertAddress(Address address);
}

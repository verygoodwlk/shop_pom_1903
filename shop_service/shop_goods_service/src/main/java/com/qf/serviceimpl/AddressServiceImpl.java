package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.AddressMapper;
import com.qf.entity.Address;
import com.qf.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/23 10:15
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> queryByUid(Integer uid) {
        QueryWrapper qe = new QueryWrapper();
        qe.eq("uid", uid);
        return addressMapper.selectList(qe);
    }

    @Override
    public int insertAddress(Address address) {
        System.out.println("添加地址：" + address);
        return addressMapper.insertAddress(address);
    }

    @Override
    public Address queryByAid(Integer aid) {
        return addressMapper.selectById(aid);
    }
}

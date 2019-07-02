package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.PowerMapper;
import com.qf.entity.Power;
import com.qf.service.IPowerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/2 9:15
 */
@Service
public class PowerServiceImpl implements IPowerService {

    @Autowired
    private PowerMapper powerMapper;

    @Override
    public List<Power> powerList() {
        return powerMapper.queryAllPowers();
    }

    @Override
    public int insert(Power power) {
        return powerMapper.insert(power);
    }
}

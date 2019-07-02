package com.qf.service;

import com.qf.entity.Power;

import java.util.List;

public interface IPowerService {

    List<Power> powerList();

    List<Power> powerListByRid(Integer rid);

    int insert(Power power);
}

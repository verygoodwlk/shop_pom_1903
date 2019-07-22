package com.qf.datasource_more_demo.service;

import com.qf.datasource_more_demo.dao.StuMapper;
import com.qf.datasource_more_demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/22 15:31
 */
@Service
public class StuServiceImpl implements IStuService {

    @Autowired
    private StuMapper stuMapper;

    @Override
    public List<Student> queryAll() {
        return stuMapper.queryAll();
    }
}

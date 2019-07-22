package com.qf.datasource_more_demo.controller;

import com.qf.datasource_more_demo.datasource.DataSourceSelector;
import com.qf.datasource_more_demo.entity.Student;
import com.qf.datasource_more_demo.service.IStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/22 15:28
 */
@Controller
@RequestMapping("/stu")
public class StuController {

    @Autowired
    private IStuService stuService;

    /**
     * 查询学生列表
     * @return
     */
    @RequestMapping("/list")
    public String stuList(Model model){

        DataSourceSelector.setLocal("hunanshen");

        List<Student> students = stuService.queryAll();
        model.addAttribute("students",students);
        return "stulist";
    }
}

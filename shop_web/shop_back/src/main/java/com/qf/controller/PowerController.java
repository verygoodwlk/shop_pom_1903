package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Power;
import com.qf.service.IPowerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/2 9:11
 */
@Controller
@RequestMapping("/power")
public class PowerController {

    @Reference
    private IPowerService powerService;

    /**
     * 查询当前所有权限
     * @return
     */
    @RequestMapping("/list")
    public String powerList(Model model){

        List<Power> powers = powerService.powerList();
        model.addAttribute("powers", powers);

        return "powerlist";
    }

    @ResponseBody
    @RequestMapping("/listajax")
    public List<Power> powerListAjax(){
        return powerService.powerList();
    }

    @RequestMapping("/insert")
    public String insert(Power power){
        powerService.insert(power);
        return "redirect:/power/list";
    }
}

package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Role;
import com.qf.service.IRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/1 19:48
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Reference
    private IRoleService roleService;

    /**
     * 角色列表
     * @return
     */
    @RequestMapping("/list")
    public String roleList(Model model){
        List<Role> roles = roleService.roleList();
        model.addAttribute("roles", roles);
        return "rolelist";
    }

    /**
     * 通过ajax的方式查询所有角色
     * @return
     */
    @RequestMapping("/listajax")
    @ResponseBody
    public List<Role> roleListAjax(Integer uid){
        List<Role> roles = roleService.roleListByUid(uid);
        return roles;
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @RequestMapping("/insert")
    public String roleInsert(Role role){
        roleService.insertRole(role);
        return "redirect:/role/list";
    }
}

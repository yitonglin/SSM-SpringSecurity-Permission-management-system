package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        ModelAndView modelAndView = new ModelAndView();
        List<Permission> permissions = permissionService.findAll();
        modelAndView.addObject("permissionList",permissions);
        modelAndView.setViewName("permission-list");
        return modelAndView;
    }


    @RequestMapping("/save.do")
    public String save(Permission permission){
        permissionService.save(permission);
        return "redirect:findAll.do";
    }


}

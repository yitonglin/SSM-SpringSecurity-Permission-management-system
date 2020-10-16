package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Controller
@RequestMapping("/user")
public class Usercontroller {

    @Autowired
    private IUserService userService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        List<UserInfo> users = userService.findAll();
        modelAndView.addObject("userList",users);
        modelAndView.setViewName("user-list");
        return modelAndView;
    }

    @RequestMapping("/save.do")
    public String save(UserInfo userInfo) throws Exception {
        userService.save(userInfo);
        return "redirect:findAll.do";
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        UserInfo userInfo = userService.findById(id);
        modelAndView.addObject("user",userInfo);
        modelAndView.setViewName("user-show");
        return modelAndView;

    }

    @RequestMapping("/findUserByIdAndAllRole")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(required = true,name = "id") String id){
        ModelAndView modelAndView = new ModelAndView();
        List<Role> roles = userService.findUserByIdAndAllRole(id);
        modelAndView.addObject("roleList",roles);
        modelAndView.addObject("id",id);
        modelAndView.setViewName("user-role-add");
        return modelAndView;
    }


    /**
     * 将选中的角色添加到用户信息中
     * @return
     */
    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(required = true,name = "userId") String userId,@RequestParam(required = true,name ="ids") String[] roleIds){
        System.out.println("*******************userId:  "+userId+"******************************");
        userService.addRoleToUser(userId,roleIds);
        return "redirect:findAll.do";
    }
}

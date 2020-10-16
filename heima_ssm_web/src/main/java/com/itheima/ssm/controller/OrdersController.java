package com.itheima.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.ssm.domain.Orders;
import com.itheima.ssm.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private IOrderService orderService;


//    /**
//     *查询所有的订单---->未分页
//     * @return
//     */
//    @RequestMapping("/findAll.do")
//    public ModelAndView findAll() throws Exception {
//        ModelAndView modelAndView = new ModelAndView();
//        List<Orders> list = orderService.findAll();
//        modelAndView.addObject("ordersList",list);
//        modelAndView.setViewName("orders-list");
//        return modelAndView;
//    }

    /**
     * 查询所有的订单---->已分页
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue ="1")int page,@RequestParam(name = "size",required = true,defaultValue = "4")int size) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        List<Orders> orders = orderService.findAll(page,size);
        //此处的PageInfo类似于pagebean 里面含有list存储此处查询出的产品列表 还有总页码 当前页等信息
        PageInfo pageInfo = new PageInfo(orders);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.setViewName("orders-page-list");
        return modelAndView;
    }



    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id",required = true)String id) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Orders orders = orderService.findById(id);
        modelAndView.addObject("orders",orders);
        modelAndView.setViewName("orders-show");
        return modelAndView;
    }
}

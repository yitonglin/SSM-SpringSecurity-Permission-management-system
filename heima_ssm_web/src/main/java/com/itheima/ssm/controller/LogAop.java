package com.itheima.ssm.controller;
import	java.security.KeyStore.Entry.Attribute;
import	java.lang.reflect.Method;

import com.itheima.ssm.domain.SysLog;
import com.itheima.ssm.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    private ISysLogService sysLogService;


    //获取request对象 用于获取访问的ip地址
    @Autowired
    private HttpServletRequest request;


    private Date visitTime;//访问时间
    private Class clazz;//访问的类
    private Method method;//访问的方法

    //前置通知 拦截controller下的所有方法
    //此处获取开始时间，执行的类和方法
    @Before("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {

        visitTime = new Date();//当前时间即是访问的时间
        clazz = jp.getTarget().getClass();//具体要访问的类
        String methodName = jp.getSignature().getName();//具体访问的方法的名称
        Object[] args = jp.getArgs();//获取访问方法的参数
        //获取具体执行的method对象
        if (args != null && args.length ==0){
            method = clazz.getMethod(methodName);//只能获取无参数的方法
        }else {
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++){
                classArgs[i] = args[i].getClass();
            }
            clazz.getMethod(methodName,classArgs);
        }
    }


    //后置通知 拦截controller下的所有方法
    @After("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint jp) {
        //获取访问的时长
        Long time = new Date().getTime()-visitTime.getTime();

        //获取url
        String url = "";
        if (clazz != null && method != null && clazz != LogAop.class){
            //获取类上的@RequestMapping
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation !=null){
                String[] classValues = classAnnotation.value();
                //获取方法上的@RequestMapping
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null){
                    //获取注解内的数据
                    String[] methodValues = methodAnnotation.value();
                    url = classValues[0]+methodValues[0];
                }
            }
            //获取ip地址
            String ip = request.getRemoteAddr();


            //获取当前操作的用户
            //从上下文中获取当前登录的用户
            SecurityContext context = SecurityContextHolder.getContext();
            //此处的User是springSecurity内部定义的
            User user = (User) context.getAuthentication().getPrincipal();
            String username = user.getUsername();
            //将日志的相关信息封装
                SysLog sysLog = new SysLog();
                sysLog.setExecutionTime(time);
                sysLog.setIp(ip);
                sysLog.setMethod("[类名]"+clazz.getName()+"[方法名]"+method.getName());
                sysLog.setUrl(url);
                sysLog.setUsername(username);
                sysLog.setVisitTime(visitTime);
                sysLogService.save(sysLog);
                
        }
    }








}

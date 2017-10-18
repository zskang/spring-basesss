package com.joyintech.utils;


import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /** 
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境 
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 主要功能:     <br>
     * 注意事项:无  <br>
     * 
     * @return 
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 主要功能:获取对象 <br>
     * 注意事项:无  <br>
     * 
     * @param name 
     * @return Object 一个以所给名字注册的bean的实例 (service注解方式，自动生成以首字母小写的类名为bean name) 
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }
}
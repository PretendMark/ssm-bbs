package com.fly.web.listener;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
@Slf4j
public class InitializeServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //初始化项目绝对路径
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("absolutePath" , servletContext.getContextPath());
        log.info("项目绝对路径{}",servletContext.getContextPath());
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }


}

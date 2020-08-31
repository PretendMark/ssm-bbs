package com.fly.web.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitializeServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //初始化项目绝对路径
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("absolutePath" , servletContext.getContextPath());
        System.out.println("启动时路径："+servletContext.getAttribute("absolutePath"));
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}

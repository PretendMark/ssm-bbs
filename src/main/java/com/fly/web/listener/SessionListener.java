package com.fly.web.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author YuLF
 * @version 1.0
 * @date 2020/10/15 17:51
 * Session监听器
 */
public class SessionListener implements HttpSessionListener {
    //当有新用户创建时，把他得Session添加到Session管理器
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        SessionManager.AddSession(httpSessionEvent.getSession());
    }
    //当有Session销毁时，从Session管理器移除
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        SessionManager.DelSession(session);
    }
}

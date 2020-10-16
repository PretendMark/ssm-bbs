package com.fly.web.listener;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YuLF
 * @version 1.0
 * @date 2020/10/15 17:52
 * Session管理器，保存用户Session
 */
public class SessionManager {

    private static Map<String,Object> map = new HashMap();

    public static Map<String,Object> getSessionMap() {
        return map;
    }

    public static synchronized void AddSession(HttpSession session) {
        if (session != null) {
            map.put(session.getId(), session);
        }
    }

    public static synchronized void DelSession(HttpSession session) {
        if (session != null) {
            map.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null)
            return null;
        return (HttpSession) map.get(session_id);
    }
}

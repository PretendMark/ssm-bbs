package com.fly.web.listener;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @author YuLF
 * @version 1.0
 * @date 2020/10/15 17:46
 * 定时检查Session状态类
 */
@Component
@EnableScheduling
public class UserValidityDetectionChecker {
    /**
     * 用户离开页面多少秒 销毁用户Session,
     * 因为定时检查是定时执行，存在一定得时间差，不过定时任务得执行间隔只要不是太长，那这个时间差就不会太大
     * 比如说：当用户离开页面后，并不是只要达到了120秒就去移除Session，而是需要等待这个定时器再次执行
     * 当用户离开页面120秒后销毁用户Session得某个Key
     */
    private static final Integer USER_LEAVE_TIME = 120;
    /**
     * 定时检查任务执行得间隔时间 每1分钟执行一次定时任务检查
     * 1.拿到所有用户Session进行遍历
     * 2.判断每一个用户得session，userActiveTime(用户活跃时间戳)，关闭当前页面是否超过了指定得秒数
     * 3.关闭页面超过多少秒则移除用户session某个值
     */
    @Scheduled(cron = "0 1/1 * * * ?")
    public void checkUserPage(){
        String needRemoveSession = "UserKey";//需要移除得Session
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "：执行定时任务检查所有Session状态!");
        Map<String,Object> sessionMap = SessionManager.getSessionMap();
        Iterator<Map.Entry<String, Object>> iterator = sessionMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            HttpSession session = SessionManager.getSession(next.getKey());
            //拿到用户在页面得最后一次活跃时间戳
            String userActiveTime = session.getAttribute("userActiveTime")+"";
            if(session.getAttribute(needRemoveSession) != null){
                //用户关闭页面大于指定得秒数，则移除session UserKey这个值
                if(getDateDifference(userActiveTime) > USER_LEAVE_TIME){
                    //移除session中得某个session值  也可以直接移除用户Session
                    session.removeAttribute(needRemoveSession);
                    System.out.println(session.getId()+"离开页面："+USER_LEAVE_TIME + "s，移除"+session.getId()+"得UserKey值");
                }
            }
        }
    }
    /**
     * 通过传入得毫秒时间戳，获取距离当前系统时间得秒数，来判断是否超过指定得秒数
     * @param userActiveDateMs
     * @return
     */
    public static long getDateDifference(String userActiveDateMs){
        return (System.currentTimeMillis() - (Long.parseLong(userActiveDateMs)))/1000;
    }
}

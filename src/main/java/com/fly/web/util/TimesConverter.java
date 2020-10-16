package com.fly.web.util;


import java.util.Calendar;

public class TimesConverter {

    /**
     * 获取当前时间距离凌晨24点的剩余时间秒
     * 便于第二天Redis清除某些记录
     * @return
     */
    public static long getSecifiedTimeRemainingSeconds(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

}

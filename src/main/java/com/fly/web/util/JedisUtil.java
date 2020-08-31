package com.fly.web.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;


@Component
public class JedisUtil {
    /**
     * 查看当前邮箱发送邮件验证码当天是否超过3次  默认3，取决于配置文件，email-verify.properties  Key  verify.day.count
     * @param jedis
     * @param email                 用户邮箱
     * @param verifyCodeDayCount  自定义配置的每天限制发送多少次验证码
     * @return     true==是
     */
    public static boolean EmailSendExceedThreeTimes( Jedis jedis, String email, int verifyCodeDayCount )
    {
        String emailSendCount = jedis.get( email + ":count" );
        /* 配置文件自定义的每天发送邮件验证次数 */
        Boolean isExceed = false;
        /* 1.当redis存储的当前键值为null 今天第一次发送 */
        if ( null == emailSendCount )
        {
            /*  +1次数 */
            jedis.set( email + ":count", 1 + "" );


            /**
             * 设置过期时间，设置当天的次数在凌晨24点清空
             * 数字最大86399 不用担心强转精度问题
             */
            jedis.expire( email + ":count", (int) TimesUtil.getSecifiedTimeRemainingSeconds() );
            return(isExceed);
        }


        /**
         * 2.当次数 <= 验证码当天发送最大次数
         * 只有当redis中没有倒计时，才算+一次发送次数，不然此次请求就是其他操作
         */
        if ( Integer.parseInt( emailSendCount ) <= (verifyCodeDayCount - 1) && jedis.get( email + ":cooling" ) == null )
        {
            /*  +1次数 */
            jedis.incr( email + ":count" );
            return(isExceed);
        }
        /* 3.当次数 = verifyCodeDayCount 说明达到了当天发送邮件码上限 */
        if ( Integer.parseInt( emailSendCount ) >= verifyCodeDayCount )
        {
            isExceed = true;
        }
        return(isExceed);
    }


    /**
     * 获取验证码重新发送的倒计时时间
     * @param jedis
     * @param email
     * @return
     */
    public static long getVerifyCooling( Jedis jedis, String email )
    {
        return jedis.ttl( email + ":cooling" );
    }


    /**
     *
     * @param jedis
     * @param email             用户邮箱
     * @param verifyCodeTime   用户验证码存在时间
     * @param verifyCode        用户验证码
     * @param verifyCodeCoolingTime 用户验证码重新获取时间
     */
    public static void setVerify( Jedis jedis, String email, int verifyCodeTime, String verifyCode, int verifyCodeCoolingTime )
    {
        /* 用户验证码 */
        jedis.set( email + ":verifycode", verifyCode );
        /* 设置验证码过期时间 */
        jedis.expire( email + ":verifycode", verifyCodeTime );
        /* 设置验证码的重新获取时间（倒计时） */
        jedis.set( email + ":cooling", verifyCodeCoolingTime + "" );
        /* 设置验证码重写获取时间 key 的生存时间 */
        jedis.expire( email + ":cooling", verifyCodeCoolingTime );
    }

    /**
     * 免费的不靠谱，各种限制，各种异常
     * 出现任何发送邮件失败异常，回滚次数
     * @param jedis
     * @param email
     */
    public static void RollbackVerifyCodeDayCount(Jedis jedis, String email) {
        if(jedis.get(email + ":count") != null)jedis.decr(email + ":count");
    }
}

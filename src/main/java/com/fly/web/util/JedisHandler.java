package com.fly.web.util;

import lombok.Cleanup;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Component
public class JedisHandler {

    /* 连接池获取一个jedis连接 */
    private static JedisPool jedisPool	= JedisPoolFactory.getJedisPoolInstance();
    /**
     * 查看当前邮箱发送邮件验证码当天是否超过3次  默认3，取决于配置文件，email-verify.properties  Key  verify.day.count
     * @param email                 用户邮箱
     * @param verifyCodeDayCount  自定义配置的每天限制发送多少次验证码
     * @return     true==是
     */
    public static boolean EmailSendExceedThreeTimes(String email, int verifyCodeDayCount )
    {
        //从redis拿到当前用户发送验证码的次数
        String emailSendCount = getRedisKey(getEmailAuthCountKeyVal(email));
        /* 配置文件自定义的每天发送邮件验证次数 */
        Boolean isExceed = false;
        /* 1.当redis存储的当前键值为null 今天第一次发送 */
        if ( null == emailSendCount )
        {
            /*  +1次数 */
            setRedisKey(getEmailAuthCountKey(email),String.valueOf(1));

            /**
             * 设置过期时间，设置当天的次数在凌晨24点清空
             * 数字最大86399 不用担心强转精度问题
             */
            setRedisKeyExpire(getEmailAuthCountKey(email),(int) TimesConverter.getSecifiedTimeRemainingSeconds());
            return(isExceed);
        }


        /**
         * 2.当次数 <= 验证码当天发送最大次数
         * 只有当redis中没有倒计时，才算+一次发送次数，不然此次请求就是其他操作
         */
        String authcodeCoolingTimeVal = getAuthcodeCoolingTimeVal(email);
        if ( Integer.parseInt( emailSendCount ) <= (verifyCodeDayCount - 1) && authcodeCoolingTimeVal == null )
        {
            /*  +1次数 */
            incrRedisKey(getEmailAuthCountKey(email));
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
     *
     * @param email             用户邮箱
     * @param verifyCodeTime   用户验证码存在时间
     * @param verifyCode        用户验证码
     * @param verifyCodeCoolingTime 用户验证码重新获取时间
     */
    public static void setVerify( String email, int verifyCodeTime, String verifyCode, int verifyCodeCoolingTime )
    {
        @Cleanup Jedis	jedis	= jedisPool.getResource();

        String redisUserAuthcodeKey = getRedisUserAuthcodeKey(email);
        /* 用户验证码 */
        setRedisKey(redisUserAuthcodeKey,verifyCode);
        /* 设置验证码过期时间 */
        setRedisKeyExpire(redisUserAuthcodeKey,verifyCodeTime);

        String authcodeCoolingTimeKey = getAuthcodeCoolingTimeKey(email);
        /* 设置验证码的重新获取时间（倒计时） */
        setRedisKey(authcodeCoolingTimeKey,String.valueOf(verifyCodeCoolingTime));
        /* 设置验证码重写获取时间 key 的生存时间 */
        setRedisKeyExpire(authcodeCoolingTimeKey,verifyCodeCoolingTime);
    }

    /**
     * 免费的邮件服务不靠谱，各种限制，各种异常
     * 出现任何发送邮件失败异常，回滚次数
     * @param email
     */
    public static void RollbackVerifyCodeDayCount( String email) {
        String emailAuthCountKey = getEmailAuthCountKey(email);
        if(getRedisKey(emailAuthCountKey) != null)
            decrRedisKey(emailAuthCountKey);
    }

    //===================================BBS Redis operation =============================================
    public static void decrRedisKey(String key){
        @Cleanup Jedis	jedis	= jedisPool.getResource();
        jedis.decr(key);
    }
    public static void incrRedisKey(String key){
        @Cleanup Jedis	jedis	= jedisPool.getResource();
        jedis.incr(key);
    }
    public static void delRedisKey(String key){
        @Cleanup Jedis	jedis	= jedisPool.getResource();
        jedis.del(key);
    }
    public static void setRedisKey(String key,String val){
        @Cleanup Jedis	jedis	= jedisPool.getResource();
        jedis.set(key,val);
    }
    public static String getRedisKey(String key){
        @Cleanup Jedis	jedis	= jedisPool.getResource();
        return jedis.get(key);
    }
    public static void setRedisKeyExpire(String key,int seconds){
        @Cleanup Jedis	jedis	= jedisPool.getResource();
        jedis.expire(key,seconds);
    }
    public static long getRedisKeyTtl(String key){
        @Cleanup Jedis	jedis	= jedisPool.getResource();
        return jedis.ttl(key);
    }

    /**
     * 通过用户邮件和验证码定义 密码私钥在redis的key
     * @param userEmail
     * @param userAuthcode
     * @return
     */
    public static String getRedisPrivateKeyVal(String userEmail,String userAuthcode){
        return getRedisKey(getRedisPrivateKey(userEmail,userAuthcode));
    }
    //===================================BBS Redis Del Val =============================================
    public static void delRedisUserLockKey(String userEmail){
         delRedisKey(getRedisUserLockKey(userEmail));
    }
    //===================================BBS Redis Get Val =============================================
    public static String getRedisUserAuthcodeKeyVal(String userEmail){
        return getRedisKey(getRedisUserAuthcodeKey(userEmail));
    }
    public static String getRedisUserLockKeyVal(String userEmail){
        return getRedisKey(getRedisUserLockKey(userEmail));
    }
    public static String getEmailAuthCountKeyVal(String userEmail){
        return getRedisKey(getEmailAuthCountKey(userEmail));
    }
    public static String getAuthcodeCoolingTimeVal(String userEmail){
        return getRedisKey(getAuthcodeCoolingTimeKey(userEmail));
    }
    /**
     * 获取验证码重新发送的倒计时时间
     * @param userEmail
     * @return
     */
    public static long getAuthcodeCoolingTime( String userEmail )
    {
        return getRedisKeyTtl(getAuthcodeCoolingTimeKey(userEmail));
    }
    //===================================BBS Redis Get Key =============================================
    public static String getLoginFailCountKey(String userEmail){
        return userEmail + ":loginFailCount";
    }
    public static String getAuthcodeCoolingTimeKey(String userEmail){
        return userEmail + ":cooling";
    }
    public static String getEmailAuthCountKey(String userEmail){
        return userEmail + ":count";
    }
    public static String getRedisPrivateKey(String userEmail,String userAuthcode){
        return userEmail + ":" + userAuthcode + ":privateKey";
    }
    public static String getRedisUserAuthcodeKey(String userEmail){
        return userEmail + ":verifycode";
    }
    public static String getRedisUserLockKey(String userEmail){
        return userEmail + ":" + "Lock";
    }
    public static String getRedisUserTokenKey(String userEmail){
        return userEmail + ":token";
    }

}

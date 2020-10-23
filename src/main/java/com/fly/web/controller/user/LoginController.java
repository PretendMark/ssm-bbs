package com.fly.web.controller.user;

import com.fly.web.constant.WebFinal;
import com.fly.web.controller.BaseController;
import com.fly.web.pojo.UserLoginDTO;
import com.fly.web.util.CharacterConverter;
import com.fly.web.util.EncryptionCalculator;
import com.fly.web.util.JedisHandler;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * 登录类
 */
@RequestMapping("/ulogin")
@Controller
public class LoginController extends BaseController implements WebFinal {

    /**
     * 登录验证
     *
     * @param user   用户登录提交的信息
     * @param result 用户登录信息效验结果
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping("/login")
    public String login(@Valid UserLoginDTO user, BindingResult result, HttpServletRequest req) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        /* 1.如果当前用户还没有验证 */
        if (!currentUser.isAuthenticated()) {

            /* 2.验证用户表单数据和密码私钥 */
            /* 2.1 从redis取出加密密码私钥 */
            String redisPrivateKey = JedisHandler.getRedisPrivateKeyVal(user.getUserEmail(), user.getUserVerifyCode());
            /* 2.2 如果后端提交数据验证有错 或者 redis已经不存在这个邮箱的解密私钥了 */
            if (result.hasErrors() || redisPrivateKey == null) {
                /* 2.3 如果有错 把错误信息存到Session 重定向登录页面显示 */
                req.getSession().setAttribute("loginError", this.systemConstant.getProperty("login.check.fail"));
                return LOGIN_FAILED;
            }
            try {
                /* 3. 判断用户是否被锁定 */
                userAreLocked(user.getUserEmail());
                /* 使用私钥解密 并把解密之后的密码交给Shiro验证 */
                String password = EncryptionCalculator.decrypt(user.getUserPassword(), redisPrivateKey);
                UsernamePasswordToken token = new UsernamePasswordToken(user.getUserEmail(), password);
                token.setRememberMe(true);
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                //未知账号异常
                req.getSession().setAttribute("loginError", this.systemConstant.getProperty("login.user.nonentity"));
                return LOGIN_FAILED;
            } catch (IncorrectCredentialsException ice) {
                //5.错误的账号或密码
                try {
                    //记录用户登录失败多少次
                    isLockUser(user.getUserEmail());
                    req.getSession().setAttribute("loginError", this.systemConstant.getProperty("login.account.password.error"));
                    return LOGIN_FAILED;
                } catch (LockedAccountException le) {
                    req.getSession().setAttribute("loginError", le.getMessage());
                    return LOGIN_FAILED;
                }
            } catch (LockedAccountException lae) {
                req.getSession().setAttribute("loginError", lae.getMessage());
                return LOGIN_FAILED;
            } catch (AuthenticationException ae) {
                /* unexpected condition?  error? */
                req.getSession().setAttribute("loginError", this.systemConstant.getProperty("other.exception") + ae.getMessage());
                return LOGIN_FAILED;
            }
        }
        //登录成功 清除登录失败次数
        JedisHandler.delRedisKey(JedisHandler.getLoginFailCountKey(user.getUserEmail()));
        /* 重定向到数据分页功能 */
        return ("redirect:/user/getquestion");
    }

    /**
     * 3.判断用户账户是否已经锁定
     *
     * @param userEmail
     */
    public void userAreLocked(String userEmail) {
        String userLookKey = JedisHandler.getRedisUserLockKeyVal(userEmail);
        /* 3.1 如果用户已经被锁定 */
        if (Boolean.parseBoolean(userLookKey)) {
            //3.2 用户已经锁定则取出被锁的剩余时间
            long redisKeyTtl = JedisHandler.getRedisKeyTtl(JedisHandler.getRedisUserLockKey(userEmail));
            //3.3 从配置文件拿到账号被锁定的提示信息
            String lockInfo = this.systemConstant.getProperty("login.user.blocked");
            //3.4 如果账户被锁的时间大于60秒 则把剩余时间变量替换为多少分钟，否则替换为秒
            if (redisKeyTtl > 60) {
                int minutes = (int) redisKeyTtl / 60;
                throw new LockedAccountException(lockInfo.replace("![lockRemainingTime]", minutes + "分钟!"));
            } else {
                throw new LockedAccountException(lockInfo.replace("![lockRemainingTime]", redisKeyTtl + "秒钟!"));
            }
        }
    }

    /**
     * 记录用户登录失败次数，如果大于等于登录失败最大值，则被锁定该用户
     *
     * @param userEmail
     */
    public void isLockUser(String userEmail) {
        //5.1 通过用户邮件账号拿到redis登录失败次数的key
        String loginFailCountKey = JedisHandler.getLoginFailCountKey(userEmail);
        //5.2 通过key获取值
        String loginFailCountVal = JedisHandler.getRedisKey(loginFailCountKey);
        if (loginFailCountVal != null) {
            //5.3 如果登录失败次数值大于等于管理员设置的最大登录失败值，则直接被锁定
            int loginFailMaxCount = CharacterConverter.parseInt(this.systemConstant.getProperty("login.fail.max.count"), 3);
            //锁定
            if (Integer.parseInt(loginFailCountVal) >= loginFailMaxCount) {
                //5.4 锁定用户 拿到用户redis 中的key进行锁定
                String redisUserLockKey = JedisHandler.getRedisUserLockKey(userEmail);
                JedisHandler.setRedisKey(redisUserLockKey, "True");
                //5.5 设置这个Key的过期时间为管理员设置的锁定时间/秒
                int lockSeconds = Integer.parseInt(this.systemConstant.getProperty("login.lock.minutes")) * 60;
                //5.5 设置登录失败次数redisKey的销毁时间随着锁定状态Key销毁而销毁
                JedisHandler.setRedisKeyExpire(loginFailCountKey,lockSeconds);
                JedisHandler.setRedisKeyExpire(redisUserLockKey,lockSeconds);
                throw new LockedAccountException(this.systemConstant.getProperty("login.user.lock").replace("![loginFailMaxCount]", loginFailCountVal));
            }
            //登录失败错误次数+1
            JedisHandler.incrRedisKey(loginFailCountKey);
        } else {
            //否则就是第一次登录失败
            JedisHandler.setRedisKey(loginFailCountKey, "1");
            //设置登录失败计数 保留时间秒  如果没设置 默认为120
            JedisHandler.setRedisKeyExpire(loginFailCountKey,CharacterConverter.parseInt(this.systemConstant.getProperty("login.fail.keep.seconds"),120));
        }
    }

    /**
     * 清空 登录失败重定向登录页面 设置到Session里面的错误信息
     *
     * @param request
     */
    @RequestMapping("/removeLoginError")
    public void removeLoginError(HttpServletRequest request) {
        request.getSession().removeAttribute("loginError");
    }


    /**
     * 退出登录
     */
    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return LOGIN_FAILED;
    }
}

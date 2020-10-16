package com.fly.web.controller;

import com.fly.web.constant.State;
import com.fly.web.email.TencentEmailHandler;
import com.fly.web.exception.EmailException;
import com.fly.web.pojo.ResultDTO;
import com.fly.web.service.serviceimpl.RegisterServiceimpl;
import com.fly.web.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.servlet.http.HttpServletRequest;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;


/**
 * 这个类为邮箱验证用户的有效性
 */
@Controller
@RequestMapping("/verifyCode")
public class AuthCodeController extends BaseController {

    @Autowired
    private RegisterServiceimpl service;

    @Autowired
    private TencentEmailHandler tencentEmailHandler;

    /**
     * 发送邮件验证码和重发邮件验证码的判断
     *
     * @param email
     * @return
     * @throws EmailException
     * @throws JedisConnectionException
     */
    @RequestMapping(value = "/getVerifyCode")
    @ResponseBody
    public ResultDTO getVerifyCode(@RequestParam(value = "email", required = true) String email) throws EmailException, JedisConnectionException {
        ResultDTO vc = ResultDTO.getInstance();
        /* 0.此邮件在数据库是否注册过 */
        if (service.isExistUser(email)) {
            vc.setState(State.USER_EXIST.value());
            vc.setMessage(State.USER_EXIST.Tips());
            return (vc);
        }


        /**
         * 验证用户昵称重复？
         */


        /* 1.今天发送邮件是否超过3次; 203 */
        if (JedisHandler.EmailSendExceedThreeTimes( email, CharacterConverter.parseInt(this.systemConstant.getProperty("verify.day.count"), 3))) {
            vc.setState(State.EMAIL_AUTHCODE_LIMIT.value());
            vc.setMessage(State.EMAIL_AUTHCODE_LIMIT.Tips());
            return (vc);
        }
        /* 2.redis是否有验证冷却时间，如果有则返回202-验证时间还未完 */
        int verifyCodeCooling = (int) JedisHandler.getAuthcodeCoolingTime(email);
        if (verifyCodeCooling > 1) {
            vc.setState(State.EMAIL_AUTHCODE_COUNTDOWN.value());
            vc.setTime(verifyCodeCooling);
            vc.setMessage(State.EMAIL_AUTHCODE_COUNTDOWN.Tips() + verifyCodeCooling + " seconds");
            return (vc);
        }

        /* 4.上面的条件都不成立、发送邮件， */


        /**
         * 得保证这个邮件发送验证码完全没问题
         * 不然用户收不到
         */
        String verifyCode = CharacterConverter.getSpecifyRandomString(4); /* 生成的验证码 */
        /* 发送邮件 */
        Boolean isSendSucces = tencentEmailHandler.sendMail(email,
                this.systemConstant.getProperty("mail.head.title"),
                this.systemConstant.getProperty("mail.body.content"),
                verifyCode);

        /* 发送邮件成功 */
        if (isSendSucces) {
            /* 获取properties配置文件中的验证码重写获取间隔 冷却时间 如果什么都没有 默认60s */
            int verifyCodeCoolingTime = CharacterConverter.parseInt(this.systemConstant.getProperty("verify.cooling"),60);
            /* 验证码消失时间 默认360s */
            int verifyCodeTime = CharacterConverter.parseInt(this.systemConstant.getProperty("verify.code.time"), 360);
            /* 把验证码消失时间 、验证码 和 验证码重新获取时间 存入redis */
            JedisHandler.setVerify( email, verifyCodeTime, verifyCode, verifyCodeCoolingTime);
            vc.setMessage(State.EMAIL_AUTHCODE_OK.Tips());
            vc.setTime(verifyCodeCoolingTime);
            vc.setState(State.EMAIL_AUTHCODE_OK.value());
            return (vc);
        } else {
            /* 发送邮件出现异常 */
            JedisHandler.RollbackVerifyCodeDayCount( email);
            throw new EmailException(State.EMAIL_SERVICE_FAIL.Tips());
        }
    }


    @RequestMapping("/emailverify")
    @ResponseBody
    public String EmailVerify() {
        String enabled = this.systemConstant.getProperty("verify.enabled");
        if (CharacterConverter.parseBoolean(enabled)) {
            return ("{\"emailverify\":\"true\"}");
        }
        return ("{\"emailverify\":\"false\"}");
    }


    /**
     * 注册验证
     * 验证验证码是否正确(邮件验证码或普通验证码)
     *
     * @param ClientValidateCode
     * @param userEmail
     * @param req
     * @return
     */
    @RequestMapping("/regVerify")
    @ResponseBody
    public ResultDTO regVerify(@RequestParam("v") String ClientValidateCode, @RequestParam("e") String userEmail, HttpServletRequest req) {
        ResultDTO vc = ResultDTO.getInstance();
        /* 判断是否存在用户 */
        if (service.isExistUser(userEmail)) {
            vc.setState(State.USER_EXIST.value());
            vc.setMessage(State.USER_EXIST.Tips());
            return (vc);
        }
        /* 验证码是正确的吗 */
        Boolean isCorrect = false;
        /* 如果邮件验证码开启的就先验证邮件验证码是否正确 */
        String emailVerifyIsEnabled = EmailVerify();
        if (Boolean.parseBoolean(emailVerifyIsEnabled)) {
            /* 如果邮件验证码正确 */
            if (JedisHandler.getRedisUserAuthcodeKeyVal(userEmail).equalsIgnoreCase(ClientValidateCode)) {
                /* 成功后删出redis的邮件验证码,避免第二次注册验证码不发送也能成功 */
                JedisHandler.delRedisUserLockKey(userEmail);
                isCorrect = true;
            }
        } else {
            /* 邮箱验证没有被开启，直接验证普通的验证码 */
            isCorrect = isCorrectVerifyCode(ClientValidateCode, req);
        }
        /* 验证码一致 */
        if (isCorrect) {
            vc.setState(State.AUTH_CODE_OK.value());
            vc.setMessage(State.AUTH_CODE_OK.Tips());
            /* 获取一个指定位数的随机token 用户响应页面之后提交表单验证表单数据有效性，非机器提交 */
            String AuthTokenLength = this.systemConstant.getProperty("register.auth.token.length");
            String AuthTokenExpiresSeconds = this.systemConstant.getProperty("register.auth.token.expires.seconds");
            String token = CharacterConverter.getSpecifyRandomString(CharacterConverter.parseInt(AuthTokenLength,16));
            vc.setToken(token);
            /* 存入redis并设置有效时间 */
            String userTokenKey = JedisHandler.getRedisUserTokenKey(userEmail);
            JedisHandler.setRedisKey(userTokenKey,token);
            JedisHandler.setRedisKeyExpire(userTokenKey,CharacterConverter.parseInt(AuthTokenExpiresSeconds,60));
        } else {
            vc.setState(State.AUTH_CODE_FAIL.value());
            vc.setMessage(State.AUTH_CODE_FAIL.Tips());
        }
        return (vc);
    }


    @RequestMapping("/loginVerify")
    @ResponseBody
    public ResultDTO loginVerify(@RequestParam("v") String ClientValidateCode, @RequestParam("e") String userEmail, HttpServletRequest req) throws NoSuchAlgorithmException {
        ResultDTO vc = ResultDTO.getInstance();

        /* 验证码不一致 */
        if (!isCorrectVerifyCode(ClientValidateCode, req)) {
            vc.setMessage(State.AUTH_CODE_FAIL.Tips());
            vc.setState(State.AUTH_CODE_FAIL.value());
            return (vc);
        }
        /* 用户不存在 */
        if (!service.isExistUser(userEmail)) {
            vc.setState(State.USER_NOT_EXIST.value());
            vc.setMessage(State.USER_NOT_EXIST.Tips());
            return (vc);
        }
        /* 生成RSA公钥与私钥、公钥给前端密码加密 */
        Map<Integer, String> rsa = EncryptionCalculator.getRSA();
        vc.setState(State.AUTH_CODE_EMAIL_OK.value());
        vc.setMessage(State.AUTH_CODE_EMAIL_OK.Tips());
        vc.setToken(rsa.get(0));
        /* 把私钥给redis存起来并设置指定的过期时间，到期之后用户没登录，此次密码无效（避免出现第三方机器登录的效验情况） */
        String redisPrivateKey = JedisHandler.getRedisPrivateKey(userEmail, ClientValidateCode);
        JedisHandler.setRedisKey(redisPrivateKey, rsa.get(1));
        String userPrivateKeyExpires = this.systemConstant.getProperty("login.privatekey.expires.seconds");
        JedisHandler.setRedisKeyExpire(redisPrivateKey,CharacterConverter.parseInt(userPrivateKeyExpires,10));
        return (vc);
    }


    public Boolean isCorrectVerifyCode(String ClientValidateCode, HttpServletRequest req) {
        /* 邮箱验证没有被开启，直接验证普通的验证码 */
        String SessionValidateCode = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY); /* 获取服务端验证码 */
        if (ClientValidateCode.equalsIgnoreCase(SessionValidateCode)) {
            return (true);
        }
        return (false);
    }
}

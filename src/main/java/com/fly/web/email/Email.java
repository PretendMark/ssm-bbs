package com.fly.web.email;

import javax.mail.AuthenticationFailedException;

public interface Email {

    /**
     * 发送邮件
     * @param receptionMail     接收人
     * @param mailHead           邮件标题
     * @param mailContent       邮件内容
     * @param verifyCode        验证码
     * @return
     */
    public Boolean sendMail(String receptionMail,String mailHead,String mailContent,String verifyCode);

    /**
     * 目前这个实现类EmailUtil  由于需要判断是否发送邮件完全成功，所以发送邮件可能需要10~20秒
     */

}

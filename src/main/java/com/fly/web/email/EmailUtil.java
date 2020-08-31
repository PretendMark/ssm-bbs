package com.fly.web.email;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

@Component
public class EmailUtil implements Email {
    private static final String EMAIL_CONFIG = "email-verify.properties";

    @Override
    public Boolean sendMail( String receptionMail, String mailHead, String mailContent, String verifyCode )
    {
        try {
            /* 邮件发送组成信息 */
            Properties prop = new Properties();

            /* 邮件配置信息 */
            Properties p = new Properties();

            p.load( new InputStreamReader( new FileInputStream( EmailUtil.class.getClassLoader().getResource( EMAIL_CONFIG ).getPath() ), "UTF-8" ) );

            /*
             * 进入QQ邮箱–>邮箱设置–>账户，下滑找到POP3/IMAP/SMTP/Exchange/CardDav/CalDav服务，开启POP3/SMTP服务
             * 每一个邮箱的授权码都不一样
             */
            String	sendmail	= p.getProperty( "mail.user.auth.account" ).trim();
            String	sendmailauth	= p.getProperty( "mail.user.auth.code" ).trim();

            /* 设置QQ邮件服务器 */
            prop.setProperty( "mail.host", p.getProperty( "mail.host" ).trim() );

            /* 邮件发送协议 */
            prop.setProperty( "mail.transport.protocol", p.getProperty( "mail.transport.protocol" ).trim() );

            /* 需要验证用户名密码 */
            prop.setProperty( "mail.smtp.auth", p.getProperty( "mail.smtp.auth" ).trim() );

            /* qq邮箱需要设置SSL加密，加上以下代码 */
            MailSSLSocketFactory sf = new MailSSLSocketFactory();

            sf.setTrustAllHosts( true );

            prop.put( "mail.smtp.ssl.enble", p.getProperty( "mail.smtp.ssl.enble" ).trim() );

            prop.put( "mail.smtp.socketFactory", sf );

            /* 使用JavaMail发送邮件的5个步骤 */

            /* 1:创建整个应用程序所需环境信息的Session对象 */
            Session session = Session.getDefaultInstance( prop, new Authenticator()
            {
                public PasswordAuthentication getPasswordAuthentication()
                {
                    /* 发件人邮件 用户名 密码(授权码) */
                    return(new PasswordAuthentication( sendmail, sendmailauth ) );
                }
            } );

            /* Seesion开启Debug模式，控制台打印发送邮件的运行状态 */
            session.setDebug( false );

            /* 2:获取连接对象，通过session对象获得Transport */
            Transport ts = session.getTransport();

            /* 3:使用邮箱和授权码连接上邮件服务器 */
            ts.connect( "smtp.qq.com", sendmail, sendmailauth );

            /* 4:创建邮件 */

            /* 创建邮件对象 */
            MimeMessage message = new MimeMessage( session );

            /* 指定发件人邮箱 */
            message.setFrom( new InternetAddress( sendmail ) );

            /* 指定收件人邮箱 */
            message.setRecipient( Message.RecipientType.TO, new InternetAddress( receptionMail ) );

            message.setSubject( mailHead );

            message.setContent( mailContent.replace( "![verfyCode]", verifyCode ).replace( "![verifyCodeCooling]", p.getProperty( "verify.code.time" ).trim() ), "text/html;charset=UTF-8" );

            ts.sendMessage( message, message.getAllRecipients() );

            ts.close();

            return(true);
        } catch ( Exception e ) {
            e.printStackTrace();
            return(false);
        }
    }
}

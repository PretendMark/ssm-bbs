package com.fly.web.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterHandler {
    public static String getIpAddress( HttpServletRequest request )
    {
        String ip = request.getHeader( "x-forwarded-for" );
        if ( ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase( ip ) )
        {
            /* 多次反向代理后会有多个ip值，第一个ip才是真实ip */
            if ( ip.indexOf( "," ) != -1 )
            {
                ip = ip.split( "," )[0];
            }
        }
        if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
        {
            ip = request.getHeader( "Proxy-Client-IP" );
        }
        if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
        {
            ip = request.getHeader( "WL-Proxy-Client-IP" );
        }
        if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
        {
            ip = request.getHeader( "HTTP_CLIENT_IP" );
        }
        if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
        {
            ip = request.getHeader( "HTTP_X_FORWARDED_FOR" );
        }
        if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
        {
            ip = request.getHeader( "X-Real-IP" );
        }
        if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
        {
            ip = request.getRemoteAddr();
        }
        return(getZone( ip ) );
    }


    public static String getZone( String ip )
    {
        /*
         * 接口地址http://apis.juhe.cn/ip/ip2addr?ip="+ip+"&dtype=&key=54d7b1689c44658a9b83a09250024450这个
         * 接口地址http://ip.ws.126.net/ipquery?ip=
         */
        /* 当获取 "地区" 失败就直接显示中国大陆吧 */
        String result = "中国-大陆";
        try {
            URL			url	= new URL( "http://ip.ws.126.net/ipquery?ip=" + ip );
            HttpURLConnection	httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)" );
            ScriptEngine		js	= new ScriptEngineManager().getEngineByName( "javascript" );
            InputStreamReader	in	= new InputStreamReader( httpUrl.getInputStream(), "GBK" );
            js.eval( in );
            result = js.get( "lo" ) + "-" + js.get( "lc" );
            in.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return(result);
    }
}

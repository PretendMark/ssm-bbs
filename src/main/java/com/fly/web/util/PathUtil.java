package com.fly.web.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;

public class PathUtil {
    /* 默认男女照片存放目录 */
    private static final String DEFAULT_PICTURE = File.separator + "asset" + File.separator + "res" + File.separator + "images" + File.separator + "default-picture" + File.separator;

    public static String getDefaultPicture( String gender )
    {
        WebApplicationContext	webApplicationContext	= ContextLoader.getCurrentWebApplicationContext();
        ServletContext		servletContext		= webApplicationContext.getServletContext();
        if ( gender.equals( "女" ) )
        {
            return(servletContext.getAttribute( "absolutePath" ) + DEFAULT_PICTURE + "woman.jpg");
        }
        return(servletContext.getAttribute( "absolutePath" ) + DEFAULT_PICTURE + "man.jpg");
    }
}

package com.fly.web.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 改用Spring配置得方式读取properties
 * 静态方法需要使用到此类读取
 */
public class PropertiesHandler {
    /**
     * 读取classpath下面指定properties 指定key
     * @param key
     * @param configName
     * @return
     */
    public static String getProperty(String key,String configName)  {
        Properties p = new Properties();
        try {
            String path = PropertiesHandler.class.getClassLoader().getResource(configName).getPath();
            p.load(new InputStreamReader(new FileInputStream(path),"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p.getProperty(key.trim()).trim();
    }

}

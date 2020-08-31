package com.fly.web.util;

import lombok.Cleanup;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class PropertiesUtil {

    /**
     * 读取classpath下面指定properties 指定key
     * @param key
     * @param configName
     * @return
     */
    public static String getProperty(String key,String configName)  {
        Properties p = new Properties();
        try {
            String path = PropertiesUtil.class.getClassLoader().getResource(configName).getPath();
            p.load(new InputStreamReader(new FileInputStream(path),"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p.getProperty(key.trim()).trim();
    }

}

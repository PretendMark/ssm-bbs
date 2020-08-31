package com.fly.web.util;

import java.util.Random;

public class CharacterUtil {

    /**
     * 字符串转换为Int
     * @param propertiesNum
     * @param defaultVal        出现错误或者小于0的默认值
     * @return
     */
    public static int parseInt(String propertiesNum,int defaultVal){
        int res = defaultVal;
        try{
            int i = Integer.parseInt(propertiesNum);
            if(i > 1){res = i;}
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 把字符布尔转换为Boolean，
     * @param property
     * @return
     */
    public static boolean parseBoolean(String property) {
        if(property.trim().equalsIgnoreCase("true")){
            return true;
        } else if(property.trim().equalsIgnoreCase("false")){
            return false;
        } else {
            return true;
        }
    }
    public static String getSpecifyRandomString(int length)
    {
        String charList = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String rev = "";
        Random f = new Random();
        for(int i=0;i<length;i++)
        {
            rev += charList.charAt(Math.abs(f.nextInt())%charList.length());
        }
        return rev;
    }
}

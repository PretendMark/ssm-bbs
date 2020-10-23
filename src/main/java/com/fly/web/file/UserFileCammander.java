package com.fly.web.file;

/**
 * @author YuLF
 * @version 1.0
 * @date 2020/10/23 13:05
 * 文件指挥者：控制用户文件上传、下载、使用子类
 */
public class UserFileCammander {


    private UserFileCammander(){}

    private static volatile UserFileUploadAccessor  userFileUploadAccessor= null;

    /**
     * 用户上传头像 使用那一方式保存图片和获取保存图片的URL
     * @return
     */
    public static UserFileUploadAccessor getUploadPictureInstance(){
        if(userFileUploadAccessor == null){
            synchronized (UserFileCammander.class){
                if(userFileUploadAccessor == null){
                    //使用本地保存用户头像适配器
                    userFileUploadAccessor = new LocalSaveFileAdapter();
                }
            }
        }
        return userFileUploadAccessor;
    }

}

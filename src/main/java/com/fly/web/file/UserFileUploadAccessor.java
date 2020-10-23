package com.fly.web.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author YuLF
 * @version 1.0
 * @date 2020/10/22 22:39
 * 定义用户上传保存文件、获取文件访问路径的接口
 */
public interface UserFileUploadAccessor {

    /**
     * 保存用户头像照片
     * @param userPicture       上传的照片
     * @param userPictureName   存储照片名称
     * @throws IOException
     */
    void saveUserPicture(MultipartFile userPicture, String userPictureName) throws IOException;

    /**
     * 通过用户照片名获取访问路径
     * @param userPictureName
     * @return
     */
    String getUserPictureAccessPath(String userPictureName);


}

package com.fly.web.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;


/**
 * @author YuLF
 * @version 1.0
 * @date 2020/10/22 22:42
 * 本地保存图片适配器
 * 这个子类把上传的文件保存在本地Web目录
 */
@Slf4j
public class LocalSaveFileAdapter implements UserFileUploadAccessor {

    /**
     * 保存用户头像在本地ssm-bbs/web/asset/res/images/user-picture
     * @param userPicture
     */
    @Override
    public void saveUserPicture(MultipartFile userPicture, String userPictureName) throws IOException {
        userPicture.transferTo(new File(getFileSavePath() + userPictureName));
    }

    /**
     * 返回本地/asset/res/images/user-picture图片路径
     * @param userPictureName
     * @return
     */
    @Override
    public String getUserPictureAccessPath(String userPictureName) {
        ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        String fileSavePath = getFileSavePath();
        String absolutePath = fileSavePath.substring(fileSavePath.lastIndexOf(servletContext.getAttribute("absolutePath").toString()), fileSavePath.length());
        log.info("图片保存路径{}",absolutePath);
        return absolutePath + userPictureName;
    }

    private String getFileSavePath(){
        //C:/Users/16500/Desktop/ssm-bbs/classes/artifacts/ssm_bbs/WEB-INF/classes/
        String localClassesPath = LocalSaveFileAdapter.class.getResource("/").getPath().replaceFirst("/", "");
        //C:/Users/16500/Desktop/ssm-bbs/classes/artifacts/ssm_bbs/
        String substring = localClassesPath.substring(0, localClassesPath.lastIndexOf("WEB-INF"));
        String savePath = substring + "asset" + File.separator + "images" + File.separator + "user-picture" + File.separator;
        return savePath;
    }
}

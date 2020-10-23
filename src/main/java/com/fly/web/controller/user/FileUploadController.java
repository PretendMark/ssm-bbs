package com.fly.web.controller.user;

import com.fly.web.constant.State;
import com.fly.web.constant.WebFinal;
import com.fly.web.file.LocalSaveFileAdapter;
import com.fly.web.file.UserFileCammander;
import com.fly.web.file.UserFileUploadAccessor;
import com.fly.web.pojo.ResultDTO;
import com.fly.web.pojo.UserDO;
import com.fly.web.service.serviceimpl.BasicsSttingsServiceimpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * @author YuLF
 * @version 1.0
 * @date 2020/10/22 17:50
 */
@RequestMapping("/user")
@Controller
@Slf4j
public class FileUploadController implements WebFinal {

    @Autowired
    private BasicsSttingsServiceimpl basicsSttingsServiceimpl;

    /**
     * 上传用户头像图片
     * @param userPicture
     * @param request
     * @return
     */
    @RequestMapping("/uploadUserPicture")
    @ResponseBody
    public ResultDTO uploadUserPicture(MultipartFile userPicture, HttpServletRequest request){
        ResultDTO resultDTO = ResultDTO.getInstance();
        try {
            //1.当前用户登录账户
            String userEmailName = (String) SecurityUtils.getSubject().getPrincipal();
            //2.用 用户名-userPicture-文件名  组成文件名，并把文件名存入数据库
            String userPictureName =  userEmailName + "-userPicture-"+userPicture.getOriginalFilename().replace(" ","");
            //3.复制到指定目录
            UserFileUploadAccessor uploadPictureInstance = UserFileCammander.getUploadPictureInstance();
            uploadPictureInstance.saveUserPicture(userPicture,userPictureName);
            //4.文件名存入数据库
            boolean saveSuccess = basicsSttingsServiceimpl.saveUserPicture(userEmailName,userPictureName);
            //更新会话信息
            if(saveSuccess){
                UserDO userInfo = (UserDO)request.getSession().getAttribute("userInfo");
                userInfo.setUserPicture(userPictureName);
                log.info(userEmailName + "头像访问路径："+userInfo.getUserPicture());
                request.getSession().setAttribute("userInfo",userInfo);
            }
            resultDTO.setState(State.FILE_UPLOAD_SUCCESS.value());
            resultDTO.setMessage(State.FILE_UPLOAD_SUCCESS.Tips());
        } catch (Exception e) {
            e.printStackTrace();
            resultDTO.setState(State.FILE_UPLOAD_FAIL.value());
            resultDTO.setMessage(State.FILE_UPLOAD_FAIL.Tips());
        }
        return resultDTO;
    }

}

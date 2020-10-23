package com.fly.web.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/test")
public class DownloadFileTest {
    @RequestMapping("/download")
    public void download(HttpServletResponse response, HttpServletRequest request) throws Exception {
        //读入
        File file = new File("C:\\Users\\16500\\Desktop\\life_think.txt");
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[in.available()];
        int readLength = -1;
        int writeLength = -1;
        readLength = in.read(buffer);
        String s = new String(buffer,"UTF-8");
        System.out.println(s);
        //String ss = changeText(s);  //将文本内容修改了一下
        //写出下载
        //1.设置响应类型
        response.setContentType(request.getServletContext().getMimeType(file.getPath()));
        //2.设置响应头的Content-Dispostion，attachment;filename=filename   URLEncoder.encode()  -  防止在谷歌IE上面中文文件名乱码
        response.setHeader("Content-Disposition", "attachment; filename=" + "life_think.txt");
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(s.getBytes("UTF-8")));
        byte[] bytes = new byte[bis.available()];
        //3.响应写出  不加1和2步骤则是响应文本
        ServletOutputStream outputStream = response.getOutputStream();
        while ((writeLength = bis.read(bytes)) != -1) {
            outputStream.write(bytes,0, writeLength);
            outputStream.flush();
        }
        outputStream.close();
        bis.close();
        in.close();
    }
}

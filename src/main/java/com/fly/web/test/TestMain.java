package com.fly.web.test;

import com.fly.web.constant.State;
import com.fly.web.listener.InitializeServletContextListener;
import com.fly.web.pojo.ProvincialAndCityDO;
import com.fly.web.service.serviceimpl.BasicsSttingsServiceimpl;
import com.fly.web.util.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author YuLF
 * @version 1.0
 * @date 2020/10/12 19:23
 */
@Controller
@RequestMapping("/test")
public class TestMain {

    public static void main(String[] args) {
        String s = "C:/Users/16500/Desktop/ssm-bbs/classes/artifacts/ssm_bbs/asset\\images\\user-picture\\";
        System.out.println(s.substring(s.lastIndexOf("/ssm-bbs",s.length())));
    }


}

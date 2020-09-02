package com.fly.web.controller.user;

import com.fly.web.pojo.ProvincialAndCity;
import com.fly.web.service.serviceimpl.BasicsSttingsServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * 基本设置菜单
 */
@Controller
@RequestMapping( "/user" )
public class BasicsSttingsController {
    @Autowired
    private BasicsSttingsServiceimpl basicsSttingsServiceimpl;


    @RequestMapping( "/basicsettings" )
    public String basicsettings( @RequestParam("uid") String uid, HttpServletRequest request )
    {
        /* 如果Session中省市为空 则获取该用户城市信息 */
        if ( request.getSession().getAttribute( "provincialAndCityList" ) == null )
        {
            /* 省市 */
            List<ProvincialAndCity> provincialAndCityList = basicsSttingsServiceimpl.getProvincialAndCity();
            /* 省 */
            Set<String> set = new TreeSet<String>();
            /* 用set去重省 */
            for ( ProvincialAndCity provincialAndCity : provincialAndCityList )
            {
                set.add( provincialAndCity.getProvincial() );
            }
            for ( ProvincialAndCity p : provincialAndCityList )
            {
                System.out.println( p.getProvincial() + "-" + p.getCity() );
            }
            request.getSession().setAttribute( "provincialAndCityList", provincialAndCityList );
            request.getSession().setAttribute( "provincialSet", set );
        }
        System.out.println();
        return("redirect:/asset/user/private/set.jsp#info");
    }
}

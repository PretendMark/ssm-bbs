package com.fly.web.controller.user;

import com.fly.web.constant.WebFinal;
import com.fly.web.pojo.QuestionDO;
import com.fly.web.pojo.UserDO;
import com.fly.web.service.serviceimpl.LoginServiceimpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 登录成功的数据查询
 */
@RequestMapping( "/user" )
@Controller
public class LoginSuccessController implements WebFinal {
    @Autowired
    private LoginServiceimpl loginServiceimpl;


    @RequestMapping( "/getquestion" )
    public String getQuestion( @RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, HttpServletRequest request )
    {
        /* 只在增删改之后才会重新从数据库获取数据存session */
        if ( request.getSession().getAttribute( "questionPageInfo" ) != null && request.getSession().getAttribute( "favoriteQuestion" ) != null )
        {
            return USER_LOGIN_SUCCESS;
        }
        /* 使用pageHelper设置紧跟着的查询的数据页数 */
        if ( page <= 0 )
        {
            page = 1;
        }
        PageHelper.startPage( page, pageSize );
        Object userEmail = SecurityUtils.getSubject().getPrincipal();

        /* 当前用户信息 */
        UserDO userInfo = loginServiceimpl.getUserInfo( userEmail );
        /* 当前用户发表的帖子 */
        List<QuestionDO> publish = loginServiceimpl.getAllQuestion( userEmail );
        /* 查询用户收藏的帖子 */
        List<QuestionDO> favorite = loginServiceimpl.getUserCollectQuestion( userEmail );


        /* 设置“我发布的帖子”分页的一些属性，默认分页显示pageSize条 */
        PageInfo<QuestionDO> myPublishQuestionPageinfo = new PageInfo<QuestionDO>( publish, pageSize );
        /* 设置“我收藏的帖子”分页的一些属性，默认分页显示pageSize条 */
        PageInfo<QuestionDO> myFavoriteQuestionPageinfo = new PageInfo<QuestionDO>( favorite, pageSize );

        /* 设置“用户信息至Session” */
        request.getSession().setAttribute( "userInfo", userInfo );
        request.getSession().setAttribute( "PublishQuestionPageInfo", myPublishQuestionPageinfo );
        request.getSession().setAttribute( "FavoriteQuestionPageInfo", myFavoriteQuestionPageinfo );

        return USER_LOGIN_SUCCESS;
    }
}

package com.fly.web.pojo;

import com.fly.web.util.DateConverter;

import java.io.Serializable;
import java.util.Date;

/**
 * 帖子
 * 实现序列化接口，mybatis 缓存机制需要用到
 */
public class QuestionDO implements Serializable {
    private Integer qid;
    private String questionTitle;//帖子标题
    private String questionCreateDate;//帖子创建日期
    private String questionFavoriteDate;//帖子被收藏日期(为了方便存bbs_favorite表的收藏时间和bbs_question表里面字段没关系)
    private Integer questionReward;//帖子悬赏
    private String questionDone;//帖子状态
    private Integer questionCommentNum;//帖子评论（回答）数量
    private Integer questionAccessNum;//帖子访问数量
    private Integer uid;//帖子个人用户
    private Integer sid;
    private String questionContent;//帖子内容

    public String getQuestionFavoriteDate() {
        return questionFavoriteDate;
    }

    public void setQuestionFavoriteDate(String questionFavoriteDate) {
        this.questionFavoriteDate = dateTransform(questionFavoriteDate);
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle == null ? null : questionTitle.trim();
    }

    public String getQuestionCreateDate() {
        return questionCreateDate;
    }

    public void setQuestionCreateDate(String questionCreateDate) {
        this.questionCreateDate = dateTransform(questionCreateDate);
    }

    /**
     * 时间戳转换为正常的日期格式
     *
     * @param str
     * @return
     */
    public String dateTransform(String str) {
        return DateConverter.fromToday(new Date(Long.parseLong(String.valueOf(str))));
    }

    public Integer getQuestionReward() {
        return questionReward;
    }

    public void setQuestionReward(Integer questionReward) {
        this.questionReward = questionReward;
    }

    public String getQuestionDone() {
        return questionDone;
    }

    public void setQuestionDone(String questionDone) {
        this.questionDone = questionDone == null ? null : questionDone.trim();
    }

    public Integer getQuestionCommentNum() {
        return questionCommentNum;
    }

    public void setQuestionCommentNum(Integer questionCommentNum) {
        this.questionCommentNum = questionCommentNum;
    }

    public Integer getQuestionAccessNum() {
        return questionAccessNum;
    }

    public void setQuestionAccessNum(Integer questionAccessNum) {
        this.questionAccessNum = questionAccessNum;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent == null ? null : questionContent.trim();
    }
}